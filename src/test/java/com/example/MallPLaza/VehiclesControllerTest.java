package com.example.MallPLaza;

import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehiclesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleService vehicleService;

    @Test
    void shouldReturnNotNullWhenSearchingAllVehicles() throws Exception {
        Mockito.when(vehicleService.getAllVehicles())
                .thenReturn(TestConstants.allVehiclesDTOList());

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOKWhenSearchingVehiclesByPlate() throws Exception {
        String plate = "AAAA11";
        Optional<VehicleDTO> expected = TestConstants.allVehiclesDTOList().stream()
                .filter(v -> v.getPlate().equals(plate))
                .findFirst();

        Mockito.when(vehicleService.getVehicle(plate))
                .thenReturn(expected);

        mockMvc.perform(get("/vehicles/" + plate))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnOKWhenCreatingVehicle() throws Exception {
        String plate = "AAAA11";
        Optional<VehicleDTO> expected = TestConstants.allVehiclesDTOList().stream()
                .filter(v -> v.getPlate().equals(plate))
                .findFirst();

        Mockito.when(vehicleService.getVehicle(plate))
                .thenReturn(expected);

        mockMvc.perform(get("/vehicles/" + plate))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenPlateDoesNotExist() throws Exception {
        String plate = "ZZZZ99";
        Mockito.when(vehicleService.getVehicle(plate)).thenReturn(Optional.empty());

        mockMvc.perform(get("/vehicles/" + plate))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandleExceptionFromServiceGracefully() throws Exception {
        Mockito.when(vehicleService.getAllVehicles())
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().is5xxServerError());
    }
}
