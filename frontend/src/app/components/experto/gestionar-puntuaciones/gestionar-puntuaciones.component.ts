import { Component, OnInit } from '@angular/core';
import { PruebaService, PruebaItemsEvaluacionDTO, CrearPruebaDTO, PruebaDTO } from '../../../services/prueba.service';
import { ItemService, ItemDTO, CrearItemDTO } from '../../../services/item.service';  // Asegúrate de importar ItemService
import { ParticipanteService, ParticipanteDTO } from '../../../services/participante.service';
import { EvaluacionItemService, EvaluacionItemDTO, EvaluacionItemResponseDTO } from '../../../services/evaluacion-item.service';
import { EvaluacionService, EvaluacionDTO, EvaluacionResponseDTO, PruebaSimpleDTO } from '../../../services/evaluacion.service';
import { PdfService,  } from '../../../services/pdf.service';
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
  pruebasPorEspecialidadEdit: PruebaSimpleDTO[] = [];
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
  evaluacionEditando: EvaluacionResponseDTO | null = null;  // Evaluación para edición

  nombreParticipante: string = '';  
  apellidoParticipante: string = ''; 
  enunciadoPrueba: string = ''; 
  

  // Mensaje que se mostrará al usuario
  mensaje: string = '';
  mensajeTipo: string = '';  // Puede ser 'success' o 'error'
  pesoError: boolean = false;
  mensajeError: string = '';  // Mensaje de error específico

  mostrarFormulario: boolean = false;  // Controla la visibilidad del formulario de prueba
  mostrarFormularioItem: boolean = false; // Controla la visibilidad del formulario de ítem
  mostrarFormularioEvaluacion: boolean = false;
  mostrarFormularioEdicion: boolean = false; 

  nuevaPrueba: CrearPruebaDTO = { enunciado: '', puntuacion_maxima: 10, especialidadId: 0 }; // Formulario para crear una nueva prueba
  nuevoItem: CrearItemDTO = { descripcion: '', peso: 1, gradosConsecucion: 0 }; // Formulario para crear un ítem

  constructor(
    private pruebaService: PruebaService,
    private participanteService: ParticipanteService,
    private itemService: ItemService,  // Usamos ItemService
    private evaluacionItemService: EvaluacionItemService,
    private evaluacionService: EvaluacionService,
    private authService: AuthService,
    private pdfService: PdfService
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
          this.participantes = data.map(participante => ({
            ...participante,
            nombreCompleto: `${participante.nombre} ${participante.apellidos}`
          }));
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
        this.nuevaPrueba = { enunciado: '', puntuacion_maxima: 10, especialidadId: especialidadId };
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

 // Actualizar los grados de consecución basado en el peso (y asegurarse de que esté dentro del rango 1-10)
 actualizarGradosConsecucion(): void {
  // Validar que el peso esté dentro del rango permitido (1 a 10)
  if (this.nuevoItem.peso < 1) {
    this.nuevoItem.peso = 1;  // Si es menor que 1, lo fijamos a 1
    this.pesoError = true; // Activar el error si el peso es menor que 1
  } else if (this.nuevoItem.peso > 10) {
    this.nuevoItem.peso = 10;  // Si es mayor que 10, lo fijamos a 10
    this.pesoError = true; // Activar el error si el peso es mayor que 10
  } else {
    this.pesoError = false; // Si está dentro del rango, no mostrar error
  }

  // Ahora actualizamos los grados de consecución
  this.nuevoItem.gradosConsecucion = this.nuevoItem.peso ? this.nuevoItem.peso * 10 : 0;
}


  // Crear un nuevo ítem
  crearItem(): void {
     // Verificar si el peso es válido
  if (this.pesoError) {
    // Si hay un error con el peso, no continuamos
    return;
  }
    if (!this.nuevoItem.descripcion || this.nuevoItem.peso < 1 || this.nuevoItem.peso > 10) {
      alert("Debe completar todos los campos del ítem.");
      return;
    }

    // Primero, actualizar los grados de consecución
    this.actualizarGradosConsecucion();

    console.log('Creando ítem:', this.nuevoItem);
    
    this.itemService.agregarItem(this.nuevoItem).subscribe(
      (item) => {
        this.items.push(item);  // Agregar el nuevo ítem a la lista de ítems
        this.nuevoItem = { descripcion: '', peso: 1, gradosConsecucion: 0 };  // Reiniciar el formulario
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

 // Método para validar la valoración de cada ítem
 validarValoracion(selectedItem: any): void {
  if (selectedItem.valoracion === null || isNaN(selectedItem.valoracion)) {
    selectedItem.valoracion = null; // Si el valor no es un número, lo deja vacío
    return;
  }
  if (selectedItem.valoracion < 0 || selectedItem.valoracion > selectedItem.item.peso) {
    selectedItem.valoracion = null; // Si está fuera del rango, lo deja vacío
  }
}

// Verifica si todas las valoraciones son válidas antes de permitir guardar
esEvaluacionValida(): boolean {
  return this.selectedItems.every(
    (item) => item.valoracion !== null && item.valoracion >= 0 && item.valoracion <= item.item.peso
  );
}

 // Función para guardar la evaluación principal y las evaluaciones de los ítems seleccionados
guardarEvaluacion(): void {
  this.mensajeError = '';

    if (!this.esEvaluacionValida()) {
      this.mensajeError = 'Hay valoraciones fuera del rango permitido. Corríjalas antes de continuar.';
      return;
    }


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

// Función para manejar la selección de participante en la edición
onSeleccionarParticipante(): void {
  if (this.selectedParticipanteIdEdit) {
    this.evaluacionService.obtenerPruebasPorParticipante(this.selectedParticipanteIdEdit).subscribe(pruebas => {
      this.pruebasPorEspecialidadEdit = pruebas;
      console.log('Pruebas obtenidas para el participante:', this.pruebasPorEspecialidadEdit);
    });
  } else {
    console.error('Debe seleccionar un participante.');
  }
}

// Función para manejar la selección de prueba en la edición
onSeleccionarPrueba(): void {
  if (this.selectedParticipanteIdEdit && this.selectedPruebaIdEdit) {
    this.cargarEvaluacionesPorParticipanteYPrueba(this.selectedParticipanteIdEdit, this.selectedPruebaIdEdit);
    console.log('Cargando evaluaciones para participante ID:', this.selectedParticipanteIdEdit, 'y prueba ID:', this.selectedPruebaIdEdit);
  } else {
    console.error('Debe seleccionar un participante y una prueba.');
  }
}

// Función para cargar las evaluaciones basadas en el participante y la prueba seleccionados
cargarEvaluacionesPorParticipanteYPrueba(participanteId: number, pruebaId: number): void {
  console.log(`Cargando evaluaciones para participante ID: ${participanteId} y prueba ID: ${pruebaId}`);
  this.evaluacionService.obtenerPorParticipanteYPrueba(participanteId, pruebaId).subscribe(evaluaciones => {
    console.log('Evaluaciones obtenidas:', evaluaciones);
    if (evaluaciones && evaluaciones.length > 0) {
      this.evaluacionEditando = evaluaciones[0];
      this.selectedParticipanteIdEdit = this.evaluacionEditando.idParticipante;
      this.selectedPruebaIdEdit = this.evaluacionEditando.pruebaId;

      // Obtener los ítems de la evaluación
      this.evaluacionItemService.obtenerEvaluacionItemsPorEvaluacion(this.evaluacionEditando.idEvaluacion).subscribe(items => {
        this.evaluacionItemsEditando = items;
        this.selectedItemsEdit = items.map(itemEvaluacion => ({
          item: this.items.find(item => item.idItem === itemEvaluacion.itemId)!,
          valoracion: itemEvaluacion.valoracion
        }));
        console.log('Ítems seleccionados para edición:', this.selectedItemsEdit);
      });

      this.mostrarFormularioEdicion = true;
    } else {
      console.error('No se encontraron evaluaciones para el participante y la prueba seleccionados.');
    }
  });
}


// Función para guardar la edición de la evaluación
guardarEdicion(): void {
  if (!this.selectedParticipanteIdEdit || this.selectedItemsEdit.length === 0) {
    alert("Debe seleccionar un participante y al menos un ítem.");
    return;
  }

  if (!this.evaluacionEditando) {
    console.error("No hay evaluación para editar.");
    return;
  }

  console.log('Guardando edición de evaluación:', this.evaluacionEditando);

  // Crear el objeto de evaluación editada
  const evaluacionDTO: EvaluacionDTO = {
    participanteId: Number(this.selectedParticipanteIdEdit),
    usuarioId: Number(this.authService.getUsuarioIdFromToken()), 
    pruebaId: Number(this.selectedPruebaIdEdit),
    notaFinal: this.notaFinal // Calculada previamente si es necesario
  };

  console.log('Datos de la evaluación editada:', evaluacionDTO);

  // Actualizar la evaluación principal
  this.evaluacionService.actualizarEvaluacion(this.evaluacionEditando.idEvaluacion, evaluacionDTO).subscribe(evaluacionGuardada => {
    console.log('Evaluación principal actualizada:', evaluacionGuardada);

    // Actualizar las evaluaciones de ítems
    this.selectedItemsEdit.forEach(selectedItem => {
      const evaluacionItemDTO: EvaluacionItemDTO = {
        evaluacionId: evaluacionGuardada.idEvaluacion,
        itemId: selectedItem.item.idItem,
        valoracion: selectedItem.valoracion
      };

      console.log('Datos de la evaluación de ítem editada:', evaluacionItemDTO);

      // Obtener el id de EvaluacionItem correspondiente
      const evaluacionItem = this.evaluacionItemsEditando.find(ei => ei.itemId === selectedItem.item.idItem);

      if (evaluacionItem) {
        console.log('Actualizando evaluación de ítem con ID:', evaluacionItem.idEvaluacionItem);
        // Actualizar cada evaluación de ítem
        this.evaluacionItemService.actualizarEvaluacionItem(evaluacionItem.idEvaluacionItem, evaluacionItemDTO).subscribe(evaluacionItemGuardada => {
          console.log("Evaluación de ítem actualizada exitosamente:", evaluacionItemGuardada);
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
generarPDF(): void {
  if (!this.selectedParticipanteId || !this.selectedPruebaId || this.selectedItems.length === 0) {
    alert("Debe seleccionar un participante, una prueba y al menos un ítem.");
    return;
  }

  // Recalcular la nota final basado en los ítems seleccionados
  this.calcularNotaFinal();

  // Convertir los IDs a número (ya deberían ser number gracias a [ngValue])
  const idParticipante = Number(this.selectedParticipanteId);
  const idPrueba = Number(this.selectedPruebaId);

  // Buscar el participante en la lista usando la propiedad idParticipante
  const participanteSeleccionado = this.participantes.find(
    p => Number(p.idParticipante) === idParticipante
  );
  if (participanteSeleccionado) {
    this.nombreParticipante = participanteSeleccionado.nombre;
    this.apellidoParticipante = participanteSeleccionado.apellidos;
  } else {
    console.error('Participante no encontrado');
    return;
  }

  // Buscar la prueba en la lista de pruebas usando la propiedad idPrueba
  const pruebaSeleccionada = this.pruebasPorEspecialidad.find(
    p => Number(p.idPrueba) === idPrueba
  );
  if (pruebaSeleccionada) {
    this.enunciadoPrueba = pruebaSeleccionada.enunciado;
  } else {
    console.error('Prueba no encontrada');
    return;
  }

  // Construir el objeto con los datos que espera el backend,
  // usando los nombres de propiedades que definiste en tu EvaluacionRequest
  const datosEvaluacion = {
    participanteId: idParticipante,
    participante: this.nombreParticipante, // Nombre del participante
    apellidos: this.apellidoParticipante,    // Apellidos
    pruebaId: idPrueba,
    prueba: this.enunciadoPrueba,              // Enunciado de la prueba
    items: this.selectedItems.map(item => ({
      itemId: item.item.idItem,
      descripcion: item.item.descripcion,
      peso: item.item.peso,
      valoracion: item.valoracion
    })),
    notaFinal: this.notaFinal
  };

  console.log("Enviando datos al backend para generar PDF:", datosEvaluacion);

  // Llamar al servicio para generar el PDF
  this.pdfService.generarPDF(datosEvaluacion).subscribe(
    (response: Blob) => {
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);

      // Crear un enlace para descargar el archivo
      const a = document.createElement('a');
      a.href = url;
      a.download = `Evaluacion_${idParticipante}_${idPrueba}.pdf`;
      document.body.appendChild(a);
      a.click();

      // Limpiar el objeto URL después de la descarga
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);

      console.log("PDF descargado exitosamente.");
    },
    (error) => {
      console.error("Error al generar el PDF:", error);
      alert("Hubo un problema al generar el PDF. Inténtelo de nuevo.");
    }
  );
}


}