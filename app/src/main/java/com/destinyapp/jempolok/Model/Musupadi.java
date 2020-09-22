package com.destinyapp.jempolok.Model;

import android.util.Base64;

public class Musupadi {
    public String AUTH(String auth){
        String authHeader = "Bearer "+auth;
        return authHeader;
    }

}
