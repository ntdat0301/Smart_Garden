package com.example.smart_farm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.smart_farm.Baothuc_Adapter.Id_BaoThuc;
import static com.example.smart_farm.LuongAdapter.Id_Luong;
import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class Setting_baothuc_5 extends AppCompatActivity {

    public DatabaseReference mDatabase;
    Button btn_Setting_Alarm;
    EditText edt_Year,edt_Month,edt_Day,edt_Hour,edt_Minute;
    public Boolean ktra;
    String Year,Month,Day,Hour,Minute;

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
        setContentView(R.layout.activity_setting_baothuc_5);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        btn_Setting_Alarm=findViewById(R.id.btn_Setting_Alarm);
        edt_Year=findViewById(R.id.edt_Year);
        edt_Month=findViewById(R.id.edt_Month);
        edt_Day=findViewById(R.id.edt_Day);
        edt_Hour=findViewById(R.id.edt_Hour);
        edt_Minute=findViewById(R.id.edt_Minute);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ktra = false;

        btn_Setting_Alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaiDat();
                if (ktra==true)
                {
                    // gửi dữ liệu lên firebase và trở về màn hình 4
                    SendData();
                    Toast.makeText(Setting_baothuc_5.this, "Cài đặt thông số thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Setting_baothuc_5.this, Baothuc_4_3.class);
                    startActivity(intent);
                }
                ktra=true;
            }
        });
    }

    private void CaiDat() {

        Year=edt_Year.getText().toString();
        Month=edt_Month.getText().toString();
        Day=edt_Day.getText().toString();
        Hour=edt_Hour.getText().toString();
        Minute=edt_Minute.getText().toString();

        if (Year.equals("")||Month.equals("")||Day.equals("")||Hour.equals("")||Minute.equals(""))
        {
            ktra=false;
            Toast.makeText(this, "Có một dữ liệu cài đặt đang trống", Toast.LENGTH_SHORT).show();
        }
        else
        {

            if ((Integer.parseInt(Month)) > 12) {
                Toast.makeText(this, "Tháng lớn hơn 12", Toast.LENGTH_SHORT).show();
                ktra=false;
            }
            if ((Integer.parseInt(Day) > 31)){
                Toast.makeText(this, "Xin mời nhập lại ngày", Toast.LENGTH_SHORT).show();
                ktra=false;
            }
            if ((Integer.parseInt(Hour) > 23)){
                Toast.makeText(this, "Xin mời nhập lại giờ", Toast.LENGTH_SHORT).show();
                ktra=false;
            }
            if ((Integer.parseInt(Minute) > 59)){
                Toast.makeText(this, "Xin mời nhập lại phút", Toast.LENGTH_SHORT).show();
                ktra=false;
            }
        }

    }

    private void SendData() {
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc/"+Id_BaoThuc+"/Year").setValue(Year);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc/"+Id_BaoThuc+"/Month").setValue(Month);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc/"+Id_BaoThuc+"/Day").setValue(Day);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc/"+Id_BaoThuc+"/Hour").setValue(Hour);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc/"+Id_BaoThuc+"/Minute").setValue(Minute);
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc/"+Id_BaoThuc+"/stsBaothuc").setValue("ON");
    }
}
