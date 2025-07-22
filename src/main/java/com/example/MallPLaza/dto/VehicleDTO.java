package com.example.MallPLaza.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO implements Serializable {

    @NotBlank(message = "El Nº de patente es obligatorio")
    @Pattern(
            regexp = "^\\w{4}\\d{2}$",
            message = "la patente debe tener el formato XXXX##"
    )
    private String plate;

    @NotBlank(message = "La marca es obligatoria")
    private String brand;

    @NotBlank(message = "El modelo es obligatorio")
    private String model;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotBlank(message = "El nombre del dueño es obligatorio")
    private String owner;

    private Timestamp arrival;
}
