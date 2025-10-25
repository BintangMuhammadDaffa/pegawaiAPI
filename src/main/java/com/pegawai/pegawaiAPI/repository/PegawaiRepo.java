package com.pegawai.pegawaiAPI.repository;

import com.pegawai.pegawaiAPI.entity.Pegawai;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PegawaiRepo extends JpaRepository<Pegawai, Integer> {
    List<Pegawai> findByNamaContainingIgnoreCase(String nama);
}
