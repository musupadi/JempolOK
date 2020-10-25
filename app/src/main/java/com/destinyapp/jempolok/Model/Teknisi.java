package com.destinyapp.jempolok.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teknisi {
    @SerializedName("id_teknisi_report")
    @Expose
    public String id_teknisi_report;

    @SerializedName("id_report")
    @Expose
    public String id_report;

    @SerializedName("id_teknisi")
    @Expose
    public String id_teknisi;

    public String getId_teknisi_report() {
        return id_teknisi_report;
    }

    public void setId_teknisi_report(String id_teknisi_report) {
        this.id_teknisi_report = id_teknisi_report;
    }

    public String getId_report() {
        return id_report;
    }

    public void setId_report(String id_report) {
        this.id_report = id_report;
    }

    public String getId_teknisi() {
        return id_teknisi;
    }

    public void setId_teknisi(String id_teknisi) {
        this.id_teknisi = id_teknisi;
    }
}
