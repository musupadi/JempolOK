package com.destinyapp.jempolok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {
    @SerializedName("accessToken")
    @Expose
    public String accessToken;

    @SerializedName("usernameUser")
    @Expose
    public String usernameUser;

    @SerializedName("namaUser")
    @Expose
    public String namaUser;

    @SerializedName("fotoUser")
    @Expose
    public String fotoUser;

    @SerializedName("tglAktif")
    @Expose
    public String tglAktif;

    @SerializedName("levelUser")
    @Expose
    public String levelUser;

    @SerializedName("statusUser")
    @Expose
    public String statusUser;

    //Report
    @SerializedName("id_report")
    @Expose
    public String id_report;

    @SerializedName("id_pengguna")
    @Expose
    public String id_pengguna;

    @SerializedName("id_pelaksana")
    @Expose
    public String id_pelaksana;

    @SerializedName("nama_report")
    @Expose
    public String nama_report;

    @SerializedName("deskripsi_report")
    @Expose
    public String deskripsi_report;

    @SerializedName("kegiatan_pemeliharaan")
    @Expose
    public String kegiatan_pemeliharaan;

    @SerializedName("lokasi")
    @Expose
    public String lokasi;


    @SerializedName("detail_lokasi")
    @Expose
    public String detail_lokasi;

    @SerializedName("kecamatan_report")
    @Expose
    public String kecamatan_report;

    @SerializedName("status_report")
    @Expose
    public String status_report;

    @SerializedName("alasan_reject")
    @Expose
    public String alasan_reject;

    @SerializedName("id_user")
    @Expose
    public String id_user;

    @SerializedName("username_user")
    @Expose
    public String username_user;

    @SerializedName("id_kecamatan")
    @Expose
    public String id_kecamatan;

    @SerializedName("nama_kecamatan")
    @Expose
    public String nama_kecamatan;

    @SerializedName("id_report_tgl")
    @Expose
    public String id_report_tgl;

    @SerializedName("tgl_report")
    @Expose
    public String tgl_report;

    @SerializedName("tgl_send_pelaksana")
    @Expose
    public String tgl_send_pelaksana;

    //Teknisi
    @SerializedName("id_teknisi")
    @Expose
    public String id_teknisi;

    @SerializedName("nama_teknisi")
    @Expose
    public String nama_teknisi;

    @SerializedName("deskripsi_teknisi")
    @Expose
    public String deskripsi_teknisi;

    @SerializedName("tgl_input_teknisi")
    @Expose
    public String tgl_input_teknisi;

    @SerializedName("status_teknisi")
    @Expose
    public String status_teknisi;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsernameUser() {
        return usernameUser;
    }

    public void setUsernameUser(String usernameUser) {
        this.usernameUser = usernameUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getFotoUser() {
        return fotoUser;
    }

    public void setFotoUser(String fotoUser) {
        this.fotoUser = fotoUser;
    }

    public String getTglAktif() {
        return tglAktif;
    }

    public void setTglAktif(String tglAktif) {
        this.tglAktif = tglAktif;
    }

    public String getLevelUser() {
        return levelUser;
    }

    public void setLevelUser(String levelUser) {
        this.levelUser = levelUser;
    }

    public String getStatusUser() {
        return statusUser;
    }

    public void setStatusUser(String statusUser) {
        this.statusUser = statusUser;
    }

    public String getId_report() {
        return id_report;
    }

    public void setId_report(String id_report) {
        this.id_report = id_report;
    }

    public String getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(String id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getId_pelaksana() {
        return id_pelaksana;
    }

    public void setId_pelaksana(String id_pelaksana) {
        this.id_pelaksana = id_pelaksana;
    }

    public String getNama_report() {
        return nama_report;
    }

    public void setNama_report(String nama_report) {
        this.nama_report = nama_report;
    }

    public String getDeskripsi_report() {
        return deskripsi_report;
    }

    public void setDeskripsi_report(String deskripsi_report) {
        this.deskripsi_report = deskripsi_report;
    }

    public String getKegiatan_pemeliharaan() {
        return kegiatan_pemeliharaan;
    }

    public void setKegiatan_pemeliharaan(String kegiatan_pemeliharaan) {
        this.kegiatan_pemeliharaan = kegiatan_pemeliharaan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDetail_lokasi() {
        return detail_lokasi;
    }

    public void setDetail_lokasi(String detail_lokasi) {
        this.detail_lokasi = detail_lokasi;
    }

    public String getKecamatan_report() {
        return kecamatan_report;
    }

    public void setKecamatan_report(String kecamatan_report) {
        this.kecamatan_report = kecamatan_report;
    }

    public String getStatus_report() {
        return status_report;
    }

    public void setStatus_report(String status_report) {
        this.status_report = status_report;
    }

    public String getAlasan_reject() {
        return alasan_reject;
    }

    public void setAlasan_reject(String alasan_reject) {
        this.alasan_reject = alasan_reject;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername_user() {
        return username_user;
    }

    public void setUsername_user(String username_user) {
        this.username_user = username_user;
    }

    public String getId_kecamatan() {
        return id_kecamatan;
    }

    public void setId_kecamatan(String id_kecamatan) {
        this.id_kecamatan = id_kecamatan;
    }

    public String getNama_kecamatan() {
        return nama_kecamatan;
    }

    public void setNama_kecamatan(String nama_kecamatan) {
        this.nama_kecamatan = nama_kecamatan;
    }

    public String getId_report_tgl() {
        return id_report_tgl;
    }

    public void setId_report_tgl(String id_report_tgl) {
        this.id_report_tgl = id_report_tgl;
    }

    public String getTgl_report() {
        return tgl_report;
    }

    public void setTgl_report(String tgl_report) {
        this.tgl_report = tgl_report;
    }

    public String getTgl_send_pelaksana() {
        return tgl_send_pelaksana;
    }

    public void setTgl_send_pelaksana(String tgl_send_pelaksana) {
        this.tgl_send_pelaksana = tgl_send_pelaksana;
    }

    public String getId_teknisi() {
        return id_teknisi;
    }

    public void setId_teknisi(String id_teknisi) {
        this.id_teknisi = id_teknisi;
    }

    public String getNama_teknisi() {
        return nama_teknisi;
    }

    public void setNama_teknisi(String nama_teknisi) {
        this.nama_teknisi = nama_teknisi;
    }

    public String getDeskripsi_teknisi() {
        return deskripsi_teknisi;
    }

    public void setDeskripsi_teknisi(String deskripsi_teknisi) {
        this.deskripsi_teknisi = deskripsi_teknisi;
    }

    public String getTgl_input_teknisi() {
        return tgl_input_teknisi;
    }

    public void setTgl_input_teknisi(String tgl_input_teknisi) {
        this.tgl_input_teknisi = tgl_input_teknisi;
    }

    public String getStatus_teknisi() {
        return status_teknisi;
    }

    public void setStatus_teknisi(String status_teknisi) {
        this.status_teknisi = status_teknisi;
    }
}
