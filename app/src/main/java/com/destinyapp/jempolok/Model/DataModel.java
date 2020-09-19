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
}
