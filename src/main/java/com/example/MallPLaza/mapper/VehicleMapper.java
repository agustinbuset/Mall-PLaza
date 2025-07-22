package com.example.MallPLaza.mapper;

import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.models.Vehicle;
import org.mapstruct.Mapper;

@Mapper
public class VehicleMapper {

    public static VehicleDTO toDto(Vehicle entity){
        return VehicleDTO.builder()
                .plate(entity.getPlate())
                .owner(entity.getOwner())
                .color(entity.getColor())
                .model(entity.getModel())
                .brand(entity.getBrand())
                .arrival(entity.getArrival())
                .build();
    }

    public static Vehicle toEntity(VehicleDTO dto){
        return Vehicle.builder()
                .plate(dto.getPlate())
                .owner(dto.getOwner())
                .color(dto.getColor())
                .model(dto.getModel())
                .brand(dto.getBrand())
                .arrival(dto.getArrival())
                .build();
    }
}
