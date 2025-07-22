package com.example.MallPLaza.controllers;

import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.exceptions.DuplicatePlateException;
import com.example.MallPLaza.exceptions.NonExistentPlateException;
import com.example.MallPLaza.services.VehicleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehiclesController {

    Logger logger = LoggerFactory.getLogger(VehiclesController.class);

    private final VehicleService vehicleService;

    public VehiclesController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping()
    public ResponseEntity<List<VehicleDTO>> getVehicles(){
        logger.info("Getting all vehicles....");
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable String id){
        logger.info("Getting vehicle with id:{}", id);

        return vehicleService.getVehicle(id)
                .map(vehicle -> new ResponseEntity<>(vehicle, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicle) throws DuplicatePlateException {
        logger.info("Adding vehicle: {}", vehicle);
        return new ResponseEntity<>(vehicleService.saveVehicle(vehicle),HttpStatus.CREATED);
    }

    @PutMapping("/{plate}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable String plate, @Valid @RequestBody VehicleDTO vehicle) throws NonExistentPlateException {
        logger.info("Updating vehicle with plate {}: {}", plate, vehicle);

        VehicleDTO savedVehicle = vehicleService.updateVehicle(plate, vehicle);
        return new ResponseEntity<>(savedVehicle,HttpStatus.OK);
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<VehicleDTO> deleteVehicle(@PathVariable String plate){
        logger.info("Deleting vehicle id {}", plate);
        vehicleService.deleteVehicle(plate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
