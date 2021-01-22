package com.learning.uploadimage;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("status")

    private String status;
    @SerializedName("message")

    private String message;


    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }
}
