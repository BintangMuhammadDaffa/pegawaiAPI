package com.pegawai.pegawaiAPI.repository.projection;

import java.math.BigDecimal;

public interface DivisiPegawaiView {
    Integer getIdPeg();
    String getNama();
    String getEmail();
    String getNamaDiv();
    BigDecimal getAnggaran();
    Integer getIdDiv();
}
