package com.example.smart_farm;
import android.widget.Switch;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Parameter_air {


    public String Doam;
    public String Nhietdo;
    public String Thoitiet;
    public String Mode;

    public Parameter_air() {
    }
    public Parameter_air(String doam, String nhietdo, String thoitiet,String mode) {
        Doam = doam;
        Nhietdo = nhietdo;
        Thoitiet = thoitiet;
        Mode = mode;

    }
}
