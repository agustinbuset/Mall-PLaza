package com.example.MallPLaza.services;

import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.exceptions.DuplicatePlateException;
import com.example.MallPLaza.exceptions.NonExistentPlateException;
import com.example.MallPLaza.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    List<VehicleDTO> getAllVehicles();

    Optional<VehicleDTO> getVehicle(String id);

    VehicleDTO saveVehicle(VehicleDTO vehicle) throws DuplicatePlateException;
    VehicleDTO updateVehicle(String plate, VehicleDTO vehicle) throws NonExistentPlateException;
    void deleteVehicle(String plate);}
