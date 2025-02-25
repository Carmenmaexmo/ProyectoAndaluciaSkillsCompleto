import { Component, OnInit } from '@angular/core';
import { PruebaService, PruebaItemsEvaluacionDTO, CrearPruebaDTO, PruebaDTO } from '../../../services/prueba.service';
import { ItemService, ItemDTO, CrearItemDTO } from '../../../services/item.service';  // Asegúrate de importar ItemService
import { ParticipanteService, ParticipanteDTO } from '../../../services/participante.service';
import { EvaluacionItemService, EvaluacionItemDTO, EvaluacionItemResponseDTO } from '../../../services/evaluacion-item.service';
import { EvaluacionService, EvaluacionDTO, EvaluacionResponseDTO } from '../../../services/evaluacion.service';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestionar-puntuaciones',
  templateUrl: './gestionar-puntuaciones.component.html',
  styleUrls: ['./gestionar-puntuaciones.component.css'],
  imports: [CommonModule, FormsModule]
})
export class GestionarPuntuacionesComponent implements OnInit {
  pruebasPorEspecialidadItems: PruebaItemsEvaluacionDTO[] = [];
  pruebasPorEspecialidad: PruebaDTO[] = [];
  pruebas: CrearPruebaDTO[] = [];
  items: ItemDTO[] = [];  // Lista de ítems creados
  participantes: ParticipanteDTO[] = [];
  selectedItems: { item: ItemDTO, valoracion: number }[] = [];  // Ítems seleccionados para evaluar con sus valoraciones
  selectedParticipanteId: number = 0;  // ID del participante seleccionado
  selectedPruebaId: number = 0;  // ID de la prueba seleccionada
  notaFinal: number = 0;  // Nota final calculada
  evaluaciones: EvaluacionDTO[] = [];  // Lista de evaluaciones creadas
  evaluacionesItems: EvaluacionItemDTO[] = [];  // Lista de evaluaciones de ítems creadas
  selectedParticipanteIdEdit: number = 0;
  selectedPruebaIdEdit: number = 0;
  selectedItemsEdit: { item: ItemDTO, valoracion: number }[] = []; // Ítems para edición
  evaluacionItemsEditando: EvaluacionItemResponseDTO[] = [];  
  evaluacionEditando: EvaluacionResponseDTO | null = null;

  // Mensaje que se mostrará al usuario
  mensaje: string = '';
  mensajeTipo: string = '';  // Puede ser 'success' o 'error'

  mostrarFormulario: boolean = false;  // Controla la visibilidad del formulario de prueba
  mostrarFormularioItem: boolean = false; // Controla la visibilidad del formulario de ítem
  mostrarFormularioEvaluacion: boolean = false;
  mostrarFormularioEdicion: boolean = false; 

  nuevaPrueba: CrearPruebaDTO = { enunciado: '', puntuacion_maxima: 0, especialidadId: 0 }; // Formulario para crear una nueva prueba
  nuevoItem: CrearItemDTO = { descripcion: '', peso: 0, gradosConsecucion: 0 }; // Formulario para crear un ítem

  constructor(
    private pruebaService: PruebaService,
    private participanteService: ParticipanteService,
    private itemService: ItemService,  // Usamos ItemService
    private evaluacionItemService: EvaluacionItemService,
    private evaluacionService: EvaluacionService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const especialidadIdStr = this.authService.getEspecialidadFromToken();
    if (especialidadIdStr) {
      const especialidadId = Number(especialidadIdStr);
      if (!isNaN(especialidadId)) {
        this.pruebaService.obtenerPruebasPorEspecialidadWithItems(especialidadId).subscribe(data => {
          this.pruebasPorEspecialidadItems = data;
        });
        this.pruebaService.obtenerPruebasPorEspecialidad(especialidadId).subscribe(data => {
          this.pruebasPorEspecialidad = data;
        });
        this.participanteService.obtenerParticipantesPorEspecialidad(especialidadId).subscribe(data => {
          this.participantes = data;
          console.log('Participantes:', this.participantes);
        });
        this.itemService.obtenerItems().subscribe(data => {
          this.items = data;  // Obtener todos los ítems disponibles
        });
      }
    }
  }

  // Crear una nueva prueba
  crearPrueba(): void {
    if (!this.nuevaPrueba.enunciado || this.nuevaPrueba.puntuacion_maxima <= 0) {
      alert("Debe completar todos los campos correctamente.");
      return;
    }

    const especialidadIdStr = this.authService.getEspecialidadFromToken();
    const especialidadId = especialidadIdStr ? Number(especialidadIdStr) : null;

    if (!especialidadId) {
      alert("Error: No se pudo obtener la especialidad del usuario.");
      return;
    }

    this.nuevaPrueba.especialidadId = especialidadId;

    this.pruebaService.agregarPrueba(this.nuevaPrueba).subscribe(
      (prueba) => {
        this.pruebas.push(prueba);
        this.nuevaPrueba = { enunciado: '', puntuacion_maxima: 0, especialidadId: especialidadId };
        this.mostrarFormulario = false;
        this.mensaje = 'Prueba agregada exitosamente!';
        this.mensajeTipo = 'success';
        setTimeout(() => this.mensaje = '', 3000);
      },
      (error) => {
        console.error('Error al guardar la prueba:', error);
        this.mensaje = 'Hubo un error al guardar la prueba. Inténtalo de nuevo.';
        this.mensajeTipo = 'error';
        setTimeout(() => this.mensaje = '', 3000);
      }
    );
  }

  // Crear un nuevo ítem
  crearItem(): void {
    if (!this.nuevoItem.descripcion || this.nuevoItem.peso <= 0) {
      alert("Debe completar todos los campos del ítem.");
      return;
    }
    console.log('Creando ítem:', this.nuevoItem);

    this.itemService.agregarItem(this.nuevoItem).subscribe(
      (item) => {
        this.items.push(item);  // Agregar el nuevo ítem a la lista de ítems
        this.nuevoItem = { descripcion: '', peso: 0, gradosConsecucion: 0 };  // Reiniciar el formulario
        this.mostrarFormularioItem = false;  // Ocultar el formulario después de guardar
        this.mensaje = 'Ítem agregado exitosamente!';
        this.mensajeTipo = 'success';
        setTimeout(() => this.mensaje = '', 3000);
      },
      (error) => {
        console.error('Error al guardar el ítem:', error);
        this.mensaje = 'Hubo un error al guardar el ítem. Inténtalo de nuevo.';
        this.mensajeTipo = 'error';
        setTimeout(() => this.mensaje = '', 3000);
      }
    );
  }

  // Función para seleccionar/deseleccionar ítems en la evaluación
  seleccionarItem(item: ItemDTO, event: any): void {
    if (event.target.checked) {
      // Si se selecciona, agregarlo con valor inicial 0 si no existe ya
      if (!this.selectedItems.find(i => i.item.idItem === item.idItem)) {
        this.selectedItems.push({ item, valoracion: 0 });
      }
    } else {
      // Si se deselecciona, eliminarlo de la lista
      this.selectedItems = this.selectedItems.filter(i => i.item.idItem !== item.idItem);
    }
    console.log('Ítems seleccionados:', this.selectedItems); // Verifica en la consola
  }

  // Función para calcular la nota final basada en los ítems seleccionados y su peso
  calcularNotaFinal(): void {
    // Suma de todas las valoraciones
    const sumaValoraciones = this.selectedItems.reduce((total, selectedItem) => {
      return total + selectedItem.valoracion;
    }, 0);
  
    // Suma de todos los pesos
    const sumaPesos = this.selectedItems.reduce((total, selectedItem) => {
      return total + selectedItem.item.peso;
    }, 0);
  
    // Calcula la nota final como la proporción de valoraciones sobre pesos, ajustada a una escala de 10
    this.notaFinal = (sumaValoraciones / sumaPesos) * 10;
  
    console.log('Nota Final Calculada:', this.notaFinal);
  }  
  

 // Función para guardar la evaluación principal y las evaluaciones de los ítems seleccionados
guardarEvaluacion(): void {
  if (!this.selectedParticipanteId || this.selectedItems.length === 0) {
    alert("Debe seleccionar un participante y al menos un ítem.");
    return;
  }

  // Calcular la nota final
  this.calcularNotaFinal();

  // Crear el objeto de evaluación principal
  const evaluacionDTO: EvaluacionDTO = {
    participanteId: Number(this.selectedParticipanteId),
    usuarioId: Number(this.authService.getUsuarioIdFromToken()), 
    pruebaId: Number(this.selectedPruebaId),
    notaFinal: this.notaFinal
  };    

  console.log('Guardando evaluación:', evaluacionDTO);

  // Guardar la evaluación principal
  this.evaluacionService.agregarEvaluacion(evaluacionDTO).subscribe(evaluacionGuardada => {
    console.log("Evaluación guardada con ID:", evaluacionGuardada.idEvaluacion);
    
    // Crear cada EvaluacionItemDTO individualmente con el id de la evaluación recién guardada
    this.selectedItems.forEach(selectedItem => {
      const evaluacionItemDTO: EvaluacionItemDTO = {
        evaluacionId: evaluacionGuardada.idEvaluacion,  // ID correcto
        itemId: Number(selectedItem.item.idItem),
        valoracion: Number(selectedItem.valoracion)
      };

      console.log('Guardando evaluación de ítem:', evaluacionItemDTO);

      // Guardar cada EvaluacionItemDTO de forma individual
      this.evaluacionItemService.agregarEvaluacionItem(evaluacionItemDTO).subscribe(() => {
        console.log("Evaluación de ítem agregada exitosamente");
      }, error => {
        console.error("Error al guardar evaluación de ítem:", error);
      });
    });

    // Limpiar los campos después de guardar
    this.selectedItems = [];
    this.selectedParticipanteId = 0;
    this.selectedPruebaId = 0;
    this.notaFinal = 0;
    this.mensaje = 'Evaluación y evaluación de ítems guardadas exitosamente!';
    this.mensajeTipo = 'success';
    setTimeout(() => this.mensaje = '', 3000);
  });
}

// Función para cargar la evaluación que se va a editar
editarEvaluacion(idEvaluacion: number): void {
  // Llamada al servicio para obtener la evaluación con los ítems y valoraciones
  this.evaluacionService.obtenerporid(idEvaluacion).subscribe(evaluacion => {
    this.evaluacionEditando = evaluacion;  // Asignar la evaluación obtenida a evaluacionEditando
    this.selectedParticipanteIdEdit = evaluacion.idParticipante;
    this.selectedPruebaIdEdit = evaluacion.idPrueba;

    // Cargar los ítems seleccionados y sus valoraciones
    this.selectedItemsEdit = evaluacion.items.map(itemEvaluacion => ({
      item: this.items.find(item => item.idItem === itemEvaluacion.itemId)!,  // Asumiendo que ya tienes la lista de ítems
      valoracion: itemEvaluacion.valoracion
    }));

    // Asignar los ítems de la evaluación a evaluacionItemsEditando
    this.evaluacionItemsEditando = evaluacion.items;

    this.mostrarFormularioEdicion = true;  // Mostrar el formulario de edición
  });
}

// Función para seleccionar/deseleccionar ítems en la edición
seleccionarItemEditar(item: ItemDTO, event: any): void {
  if (event.target.checked) {
    // Si se selecciona, agregarlo con su valoracion actual
    if (!this.selectedItemsEdit.find(i => i.item.idItem === item.idItem)) {
      const valoracion = this.selectedItemsEdit.find(i => i.item.idItem === item.idItem)?.valoracion || 0;
      this.selectedItemsEdit.push({ item, valoracion });
    }
  } else {
    // Si se deselecciona, eliminarlo de la lista
    this.selectedItemsEdit = this.selectedItemsEdit.filter(i => i.item.idItem !== item.idItem);
  }
}

// Función para verificar si el ítem está seleccionado
isItemSelected(item: ItemDTO): boolean {
  return this.selectedItemsEdit.some(i => i.item.idItem === item.idItem);
}
guardarEdicion(): void {
  if (!this.selectedParticipanteIdEdit || this.selectedItemsEdit.length === 0) {
    alert("Debe seleccionar un participante y al menos un ítem.");
    return;
  }

  if (!this.evaluacionEditando) {
    console.error("No hay evaluación para editar.");
    return;
  }

  // Crear el objeto de evaluación editada
  const evaluacionDTO: EvaluacionDTO = {
    participanteId: Number(this.selectedParticipanteIdEdit),
    usuarioId: Number(this.authService.getUsuarioIdFromToken()), 
    pruebaId: Number(this.selectedPruebaIdEdit),
    notaFinal: this.notaFinal // Calculada previamente si es necesario
  };

  // Actualizar la evaluación principal
  this.evaluacionService.actualizarEvaluacion(this.evaluacionEditando.idEvaluacion, evaluacionDTO).subscribe(evaluacionGuardada => {
    // Actualizar las evaluaciones de ítems
    this.selectedItemsEdit.forEach(selectedItem => {
      const evaluacionItemDTO: EvaluacionItemDTO = {
        evaluacionId: evaluacionGuardada.idEvaluacion,
        itemId: selectedItem.item.idItem,
        valoracion: selectedItem.valoracion
      };

      // Obtener el id de EvaluacionItem correspondiente
      const evaluacionItem = this.evaluacionItemsEditando.find(ei => ei.itemId === selectedItem.item.idItem);

      if (evaluacionItem) {
        // Actualizar cada evaluación de ítem
        this.evaluacionItemService.actualizarEvaluacionItem(evaluacionItem.idEvaluacionItem, evaluacionItemDTO).subscribe(evaluacionItemGuardada => {
          console.log("Evaluación de ítem actualizada exitosamente");
        }, error => {
          console.error("Error al actualizar evaluación de ítem:", error);
        });
      }
    });

    // Limpiar campos después de guardar
    this.selectedItemsEdit = [];
    this.selectedParticipanteIdEdit = 0;
    this.selectedPruebaIdEdit = 0;
    this.mostrarFormularioEdicion = false; // Ocultar formulario de edición
    this.mensaje = 'Evaluación y evaluación de ítems actualizadas exitosamente!';
    this.mensajeTipo = 'success';
    setTimeout(() => this.mensaje = '', 3000);
  });
}
}