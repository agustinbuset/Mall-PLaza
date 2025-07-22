package com.example.MallPLaza;

import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.mapper.VehicleMapper;
import com.example.MallPLaza.models.Vehicle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestConstants {

    public static List<Vehicle> allVehiclesList(){
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(Vehicle.builder()
                .plate("AAAA11")
                .brand("Marca 1")
                .model("Modelo 1")
                .color("Blanco")
                .owner("user 1").arrival(Timestamp.valueOf(LocalDateTime.now()))
                .build());
        vehicles.add(Vehicle.builder()
                .plate("BBBB11")
                .brand("Marca 1")
                .model("Modelo 1")
                .color("Negro")
                .owner("user 2")
                .arrival(Timestamp.valueOf(LocalDateTime.now()))
                .build());
        vehicles.add(Vehicle.builder()
                .plate("DDDD11")
                .brand("Marca 2")
                .model("Modelo 1")
                .color("Blanco")
                .owner("user 3")
                .arrival(Timestamp.valueOf(LocalDateTime.now()))
                .build());

        return vehicles;
    }

    public static List<VehicleDTO> allVehiclesDTOList() {
        return allVehiclesList().stream().map(VehicleMapper::toDto).toList();
    }
}
