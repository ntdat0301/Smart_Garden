package com.example.smart_farm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.smart_farm.LuongAdapter.Id_Luong;
import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class SettingParameter_3_3 extends AppCompatActivity {

    public DatabaseReference mDatabase;
    Button btnSet;
    EditText edtHmax,edtHmin,edtH_Evi,edtT_Evi,edt_Nutri;
    public Boolean ktra;
    String Hmax,Hmin,T_Evi,H_Evi,Nutri_Evi;

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
        setContentView(R.layout.activity_setting_parameter_3_3);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        btnSet=findViewById(R.id.btn_AcceptSetting);
        edtHmax=findViewById(R.id.edt_Humidity_Max_Land);
        edtHmin=findViewById(R.id.edt_Humidity_Min_Land);
        edtH_Evi=findViewById(R.id.edt_Humidity_Air);
        edtT_Evi=findViewById(R.id.edt_Temperature_Evi);
        edt_Nutri=findViewById(R.id.edt_Nutri);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ktra = false;

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaiDat();
                if (ktra==true)
                    {
                        // gửi dữ liệu lên firebase và trở về màn hình 4
                        SendData();
                        Toast.makeText(SettingParameter_3_3.this, "Cài đặt thông số thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingParameter_3_3.this, Control_A_Valve_4.class);
                        startActivity(intent);
                    }
                ktra=true;
            }
        });
    }

    private void CaiDat() {

        Hmax=edtHmax.getText().toString();
        Hmin=edtHmin.getText().toString();
        T_Evi=edtT_Evi.getText().toString();
        H_Evi=edtH_Evi.getText().toString();
        Nutri_Evi=edt_Nutri.getText().toString();
        if (Hmax.equals("")||Hmin.equals("")||T_Evi.equals("")||H_Evi.equals("")||Nutri_Evi.equals(""))
            {
                ktra=false;
                Toast.makeText(this, "Có một dữ liệu cài đặt đang trống", Toast.LENGTH_SHORT).show();
            }
        else
            {

                if ((Integer.parseInt(Hmax)) <= (Integer.parseInt(Hmin))) {
                        Toast.makeText(this, "Độ ẩm đât ngưỡng trên cài đặt bé hơn hoặc bằng ngưỡng dưới", Toast.LENGTH_SHORT).show();
                        ktra=false;
                    }
                if ((Integer.parseInt(Hmax) > 100)){
                        Toast.makeText(this, "Ngưỡng trên cài đặt lớn hơn 100%", Toast.LENGTH_SHORT).show();
                        ktra=false;
                    }
                if ((Integer.parseInt(Hmin) > 100)){
                        Toast.makeText(this, "Ngưỡng dưới cài đặt lớn hơn 100%", Toast.LENGTH_SHORT).show();
                        ktra=false;
                    }
                if ((Integer.parseInt(H_Evi) > 100)){
                        Toast.makeText(this, "Độ ẩm không khí cài đặt lớn hơn 100%", Toast.LENGTH_SHORT).show();
                        ktra=false;
                    }
            }

    }

    private void SendData() {
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Hmax").setValue(Hmax);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Hmin").setValue(Hmin);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/Setting/Nhietdo_Setting").setValue(T_Evi);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/Setting/Doam_Setting").setValue(T_Evi);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/Setting/Nutri_Setting").setValue(Nutri_Evi);
    }
}
