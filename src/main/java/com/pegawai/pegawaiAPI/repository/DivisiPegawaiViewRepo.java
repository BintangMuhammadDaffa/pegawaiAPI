package com.pegawai.pegawaiAPI.repository;

import com.pegawai.pegawaiAPI.entity.Pegawai;
import com.pegawai.pegawaiAPI.repository.projection.DivisiPegawaiView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DivisiPegawaiViewRepo extends Repository<Pegawai, Integer> {
    @Query(value = "SELECT p.id_peg AS idPeg, p.nama AS nama, p.email AS email, " +
            "d.nama_div AS namaDiv, d.anggaran AS anggaran " +
            "FROM pegawai p JOIN divisi d ON p.id_div = d.id_div",
            nativeQuery = true)
    List<DivisiPegawaiView> findAllFromView();
}
