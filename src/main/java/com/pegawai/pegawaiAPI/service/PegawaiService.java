package com.pegawai.pegawaiAPI.service;

import com.pegawai.pegawaiAPI.entity.Pegawai;
import com.pegawai.pegawaiAPI.repository.DivisiPegawaiViewRepo;
import com.pegawai.pegawaiAPI.repository.PegawaiRepo;
import com.pegawai.pegawaiAPI.repository.projection.DivisiPegawaiView;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PegawaiService {
    private final PegawaiRepo pegawaiRepo;
    private final DivisiPegawaiViewRepo divisiPegawaiViewRepo;

    public PegawaiService(PegawaiRepo pegawaiRepo, DivisiPegawaiViewRepo divisiPegawaiViewRepo) {
        this.pegawaiRepo = pegawaiRepo;
        this.divisiPegawaiViewRepo = divisiPegawaiViewRepo;
    }

    public List<DivisiPegawaiView> getAllView() {
        return divisiPegawaiViewRepo.findAllFromView();
    }

    public List<Pegawai> searchByName(String nama) {
        return pegawaiRepo.findByNamaContainingIgnoreCase(nama);
    }

    public Pegawai create(Pegawai p) {
        return pegawaiRepo.save(p);
    }

    public Optional<Pegawai> update(Integer id, Pegawai p) {
        return pegawaiRepo.findById(id).map(existing -> {
            existing.setNama(p.getNama());
            existing.setEmail(p.getEmail());
            existing.setIdDiv(p.getIdDiv());
            return pegawaiRepo.save(existing);
        });
    }

    public boolean delete(Integer id) {
        return pegawaiRepo.findById(id).map(e -> {
            pegawaiRepo.deleteById(id);
            return true;
        }).orElse(false);
    }
}
