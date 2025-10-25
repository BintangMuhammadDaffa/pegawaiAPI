package com.pegawai.pegawaiAPI.controller;

import com.pegawai.pegawaiAPI.dto.ApiResponse;
import com.pegawai.pegawaiAPI.entity.Pegawai;
import com.pegawai.pegawaiAPI.repository.projection.DivisiPegawaiView;
import com.pegawai.pegawaiAPI.service.PegawaiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pegawai")
public class PegawaiController {
    private final PegawaiService pegawaiService;

    public PegawaiController(PegawaiService pegawaiService){
        this.pegawaiService = pegawaiService;
    }
    // 1. GET all from view
    @GetMapping("/view")
    public ResponseEntity<ApiResponse> getAllFromView() {
        List<DivisiPegawaiView> data = pegawaiService.getAllView();
        return ResponseEntity.ok(new ApiResponse(true, "Data retrieved successfully", data));
    }

    // 2. GET search by name (substring)
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchByName(@RequestParam("nama") String nama) {
        List<Pegawai> data = pegawaiService.searchByName(nama);
        return ResponseEntity.ok(new ApiResponse(true, "Search completed successfully", data));
    }

    // 3. POST create
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Pegawai p) {
        Pegawai saved = pegawaiService.create(p);
        return ResponseEntity.created(URI.create("/api/pegawai/" + saved.getIdPeg())).body(new ApiResponse(true, "Pegawai created successfully", saved));
    }

    // 4. PUT update
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Integer id, @RequestBody Pegawai p) {
        return pegawaiService.update(id, p)
                .map(updated -> ResponseEntity.ok(new ApiResponse(true, "Pegawai updated successfully", updated)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        boolean deleted = pegawaiService.delete(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse(true, "Pegawai deleted successfully", null));
        }
        return ResponseEntity.notFound().build();
    }
}
