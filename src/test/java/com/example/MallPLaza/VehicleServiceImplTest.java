package com.example.MallPLaza;

import com.example.MallPLaza.dao.VehicleDAO;
import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.exceptions.DuplicatePlateException;
import com.example.MallPLaza.models.Vehicle;
import com.example.MallPLaza.services.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleDAO vehicleDAO;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void shouldReturnVehicleWhenPlateExists() {
        Vehicle vehicle = new Vehicle("AAA111", "Red", "Toyota", "Corolla", "John", Timestamp.valueOf("2023-01-01 10:00:00"));
        Mockito.when(vehicleDAO.findByPlate("AAA111")).thenReturn(Optional.of(vehicle));

        Optional<VehicleDTO> result = vehicleService.getVehicle("AAA111");

        assertTrue(result.isPresent());
        assertEquals("AAA111", result.get().getPlate());
    }

    @Test
    void shouldThrowWhenSavingDuplicatePlate() {
        VehicleDTO vehicleDTO = new VehicleDTO("AAA111", "Red", "Toyota", "Corolla", "John", null);
        Mockito.when(vehicleDAO.existsByPlate("AAA111")).thenReturn(true);

        assertThrows(DuplicatePlateException.class, () -> vehicleService.saveVehicle(vehicleDTO));
    }

    @Test
    void shouldSaveNewVehicle() throws DuplicatePlateException {
        VehicleDTO dto = new VehicleDTO("BBB222", "Blue", "Mazda", "3", "Alice", null);
        Mockito.when(vehicleDAO.existsByPlate("BBB222")).thenReturn(false);

        Vehicle saved = new Vehicle("BBB222", "Blue", "Mazda", "3", "Alice", Timestamp.valueOf("2023-01-01 10:00:00"));
        Mockito.when(vehicleDAO.save(Mockito.any(Vehicle.class))).thenReturn(saved);

        VehicleDTO result = vehicleService.saveVehicle(dto);

        assertEquals("BBB222", result.getPlate());
        assertEquals("Mazda", result.getBrand());
    }

    @Test
    void shouldReturnAllVehicles() {

        Mockito.when(vehicleDAO.findAll()).thenReturn(TestConstants.allVehiclesList());

        List<VehicleDTO> result = vehicleService.getAllVehicles();

        assertEquals(3, result.size());
        assertEquals("AAAA11", result.get(0).getPlate());
    }
}
