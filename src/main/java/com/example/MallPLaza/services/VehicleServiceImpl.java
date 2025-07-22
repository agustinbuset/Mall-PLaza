package com.example.MallPLaza.services;

import com.example.MallPLaza.dao.VehicleDAO;
import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.exceptions.DuplicatePlateException;
import com.example.MallPLaza.exceptions.NonExistentPlateException;
import com.example.MallPLaza.mapper.VehicleMapper;
import com.example.MallPLaza.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    private VehicleDAO vehicleDAO;

    public VehicleServiceImpl(VehicleDAO dao){
        this.vehicleDAO=dao;
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<VehicleDTO> vehicles = new ArrayList<>();
        vehicleDAO.findAll().iterator()
                .forEachRemaining(vehicle -> vehicles.add(VehicleMapper.toDto(vehicle)));
        return vehicles;
    }

    @Override
    public Optional<VehicleDTO> getVehicle(String id) {

        return vehicleDAO.findByPlate(id).map(VehicleMapper::toDto);
    }

    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicle) throws DuplicatePlateException {

        if(vehicleDAO.existsByPlate(vehicle.getPlate())){
            throw new DuplicatePlateException("Ya existe un auto con esa patente");
        }else{

            if (vehicle.getArrival()==null) vehicle.setArrival(Timestamp.valueOf(LocalDateTime.now()));

            Vehicle savedEntity = vehicleDAO.save(VehicleMapper.toEntity(vehicle));
            return VehicleMapper.toDto(savedEntity);
        }
    }

    @Override
    public VehicleDTO updateVehicle(String plate, VehicleDTO vehicle) throws NonExistentPlateException {
        if(vehicleDAO.existsByPlate(plate)){

            Vehicle savedEntity = vehicleDAO.save(VehicleMapper.toEntity(vehicle));
            return VehicleMapper.toDto(savedEntity);
        }else{
            throw new NonExistentPlateException("No existen vehiculos con la patente ingresada");
        }
    }

    @Override
    public void deleteVehicle(String plate) {
        Optional<Vehicle> toDelete = vehicleDAO.findByPlate(plate);

        toDelete.ifPresent(v-> vehicleDAO.delete(v));
    }


}
