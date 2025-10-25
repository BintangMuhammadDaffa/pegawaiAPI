package com.pegawai.pegawaiAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "divisi")
public class Divisi {

    @Id
    @Column(name = "id_div")
    private Integer idDiv;

    @Column(name = "nama_div")
    private String namaDiv;

    @Column(name = "anggaran")
    private BigDecimal anggaran;
}
