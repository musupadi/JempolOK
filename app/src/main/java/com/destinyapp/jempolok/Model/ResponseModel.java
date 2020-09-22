package com.destinyapp.jempolok.Model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("statusCode")
    @Expose
    @Nullable
    public String statusCode;

    @SerializedName("statusMessage")
    @Expose
    @Nullable
    public String statusMessage;

    @SerializedName("data")
    @Nullable
    public DataModel data = new DataModel();

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }


}
