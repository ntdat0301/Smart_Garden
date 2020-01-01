package com.example.smart_farm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.smart_farm.LuongAdapter.Id_Luong;
import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class Control_A_Valve_4 extends AppCompatActivity {
    public DatabaseReference mDatabase;
    TextView txt_sts_Van;
    ImageButton btnSetting_Van,btnAlarm_Van,btn_stsVan;

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
    }

    /*-------Phần bổ trợ thêm cho nut Back-----------------*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /*----------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control__a__valve_4);
        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        txt_sts_Van=findViewById(R.id.txt_sts_Van);
        btnSetting_Van=findViewById(R.id.btn_Setting);
        btnAlarm_Van=findViewById(R.id.btn_Alarm);
        btn_stsVan=findViewById(R.id.btn_image_OnOff);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Hienthitrangthaivan2();

        btnSetting_Van.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Control_A_Valve_4.this, SettingParameter_3_3.class);
                startActivity(intent);
            }
        });

        btnAlarm_Van.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Control_A_Valve_4.this, Baothuc_4_3.class);
                startActivity(intent);
            }
        });
        btn_stsVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Hienthitrangthaivan();

            }
        });
    }

    private void Hienthitrangthaivan() {
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((Objects.requireNonNull(dataSnapshot.child("stsVan").getValue()).toString()).equals("ON"))
                {
                    mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/stsVan").setValue("OFF");
                    txt_sts_Van.setText("OFF");
                    txt_sts_Van.setTextColor(Color.RED);
                    btn_stsVan.setBackgroundResource(R.drawable.background_button_van_off);
                }
                else
                {
                    mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/stsVan").setValue("ON");
                    txt_sts_Van.setText("ON");
                    txt_sts_Van.setTextColor(Color.GREEN);
                    btn_stsVan.setBackgroundResource(R.drawable.background_button_van_on);
                }

                    }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Hienthitrangthaivan2() {
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    txt_sts_Van.setText(Objects.requireNonNull(dataSnapshot.child("stsVan").getValue()).toString());
                    if((Objects.requireNonNull(dataSnapshot.child("stsVan").getValue()).toString()).equals("ON")){
                        txt_sts_Van.setTextColor(Color.GREEN);
                        btn_stsVan.setBackgroundResource(R.drawable.background_button_van_on);
                    }
                    else
                    {
                        txt_sts_Van.setTextColor(Color.RED);
                        btn_stsVan.setBackgroundResource(R.drawable.background_button_van_off);
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
