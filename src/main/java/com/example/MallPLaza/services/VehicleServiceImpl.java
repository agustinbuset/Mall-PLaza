package com.example.MallPLaza.services;

import com.example.MallPLaza.dao.VehicleDAO;
import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.exceptions.DuplicatePlateException;
import com.example.MallPLaza.exceptions.NonExistentPlateException;
import com.example.MallPLaza.mapper.VehicleMapper;
import com.example.MallPLaza.models.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService{

    Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private final VehicleDAO vehicleDAO;

    public VehicleServiceImpl(VehicleDAO dao){
        this.vehicleDAO=dao;
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        logger.info("getting all vehicles");
        List<VehicleDTO> vehicles = new ArrayList<>();
        vehicleDAO.findAll().iterator()
                .forEachRemaining(vehicle -> vehicles.add(VehicleMapper.toDto(vehicle)));
        return vehicles;
    }

    @Override
    public Optional<VehicleDTO> getVehicle(String id) {

        logger.info("getting vehicle with id: {}", id);
        return vehicleDAO.findByPlate(id).map(VehicleMapper::toDto);
    }

    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicle) throws DuplicatePlateException {

        if(vehicleDAO.existsByPlate(vehicle.getPlate())){
            throw new DuplicatePlateException("Ya existe un auto con esa patente");
        }else{

            if (vehicle.getArrival()==null) vehicle.setArrival(Timestamp.valueOf(LocalDateTime.now()));

            logger.info("saving vehicle: {}", vehicle);
            Vehicle savedEntity = vehicleDAO.save(VehicleMapper.toEntity(vehicle));
            return VehicleMapper.toDto(savedEntity);
        }
    }

    @Override
    public VehicleDTO updateVehicle(String plate, VehicleDTO vehicle) throws NonExistentPlateException {

        if(vehicleDAO.existsByPlate(plate)){
            logger.info("updating vehicle: {}", vehicle);
            Vehicle savedEntity = vehicleDAO.save(VehicleMapper.toEntity(vehicle));
            return VehicleMapper.toDto(savedEntity);
        }else{
            throw new NonExistentPlateException("No existen vehiculos con la patente ingresada");
        }
    }

    @Override
    public void deleteVehicle(String plate) {
        Optional<Vehicle> toDelete = vehicleDAO.findByPlate(plate);

        logger.info("vehicle found and deleting: {}", toDelete);

        toDelete.ifPresent(vehicleDAO::delete);
    }


}
