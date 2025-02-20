package com.example.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDTO {
    private Integer idItem;
    private String descripcion;
    private Integer peso;
    private Integer gradosConsecucion;
}
