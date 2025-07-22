package com.example.MallPLaza;

import com.example.MallPLaza.dto.VehicleDTO;
import com.example.MallPLaza.services.VehicleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehiclesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @Test
    void shouldReturnNotNullWhenSearchingAllVehicles() throws Exception {
        Mockito.when(vehicleService.getAllVehicles()).thenReturn(TestConstants.allVehiclesDTOList());

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenVehicleDoesNotExist() throws Exception {

        Mockito.when(vehicleService.getVehicle("AAA111"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/vehicles/AAA111"))
                .andExpect(status().isNotFound());
    }
}
