package com.example.MallPLaza.dao;

import com.example.MallPLaza.models.Vehicle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleDAO extends CrudRepository<Vehicle, String> {

    Optional<Vehicle> findByPlate(String id);

    boolean existsByPlate(String plate);
}
