package com.pegawai.pegawaiAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pegawai.pegawaiAPI.dto.ApiResponse;
import com.pegawai.pegawaiAPI.entity.Pegawai;
import com.pegawai.pegawaiAPI.repository.PegawaiRepo;
import com.pegawai.pegawaiAPI.repository.projection.DivisiPegawaiView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
class PegawaiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PegawaiRepo pegawaiRepo;

    @Test
    void testGetAllFromView() throws Exception {
        mockMvc.perform(get("/api/pegawai/view"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Data retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray()); // Based on sample data
    }

    @Test
    void testSearchByName() throws Exception {
        mockMvc.perform(get("/api/pegawai/search").param("nama", "Alice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Search completed successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].nama").value("Alice Wonder"));
    }

    @Test
    void testCreatePegawai() throws Exception {
        Pegawai newPegawai = new Pegawai();
        newPegawai.setIdPeg(4);
        newPegawai.setNama("Alice Wonder");
        newPegawai.setEmail("alice@example.com");
        newPegawai.setIdDiv(1);

        String json = objectMapper.writeValueAsString(newPegawai);

        mockMvc.perform(post("/api/pegawai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pegawai created successfully"))
                .andExpect(jsonPath("$.data.nama").value("Alice Wonder"));

        // Verify in DB
        assertThat(pegawaiRepo.findById(4)).isPresent();
    }

    @Test
    void testUpdatePegawai() throws Exception {
        Pegawai updatedPegawai = new Pegawai();
        updatedPegawai.setNama("John Updated");
        updatedPegawai.setEmail("john.updated@example.com");
        updatedPegawai.setIdDiv(1);

        String json = objectMapper.writeValueAsString(updatedPegawai);

        mockMvc.perform(put("/api/pegawai/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pegawai updated successfully"))
                .andExpect(jsonPath("$.data.nama").value("John Updated"));
    }

    @Test
    void testUpdatePegawaiNotFound() throws Exception {
        Pegawai updatedPegawai = new Pegawai();
        updatedPegawai.setNama("Not Found");
        updatedPegawai.setEmail("not.found@example.com");
        updatedPegawai.setIdDiv(1);

        String json = objectMapper.writeValueAsString(updatedPegawai);

        mockMvc.perform(put("/api/pegawai/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeletePegawai() throws Exception {
        mockMvc.perform(delete("/api/pegawai/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Pegawai deleted successfully"));

        // Verify in DB
        assertThat(pegawaiRepo.findById(10)).isEmpty();
    }

    @Test
    void testDeletePegawaiNotFound() throws Exception {
        mockMvc.perform(delete("/api/pegawai/999"))
                .andExpect(status().isNotFound());
    }
}
