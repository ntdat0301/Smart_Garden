package com.example.smart_farm;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.EventListener;

import static com.example.smart_farm.Login_1.usernameUSER;

public class List_Device_2 extends AppCompatActivity {
    public DatabaseReference mDatabase;

    Button btnRefresh;
    Button btnAdd,btnDelete;


    public static String userN;
    public String namedevice="";

    public ListView LVdevice;
    public static ArrayList<ThietBi2> mangThietBi;
    public ThietBiAdapter adapter;

    public static ArrayList<String> mID=new ArrayList<>();
    /*--- Khi back trở lại thì sẽ trở lại chương trình activity ban đầu---------------*/
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

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device_2);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        /*------------ANH XẠ---------------------*/
        LVdevice =findViewById(R.id.lv_Luong);
        btnAdd= findViewById(R.id.btn_Add);
        btnRefresh=findViewById(R.id.btn_Refresh);
        btnDelete=findViewById(R.id.btn_Delete);
        mangThietBi = new ArrayList<ThietBi2>();
        //--------------------------------thiết lập apdapter cho nó-------------------------------
        adapter = new ThietBiAdapter(List_Device_2.this, R.layout.line_device_2_1,mangThietBi);
        LVdevice.setAdapter(adapter);

        //---------------------Thêm thông tin vào dòng listview----------------------------------
        GetTenThietBi();

        //---------------------------Bắt sự kiện cho button thêm device--------------------------
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(List_Device_2.this, Add_Device_2_1.class);
                startActivity(intent);
            }
        });

        /* -------Click vào button Refresh thì load lại các thiết bị của mình--------------*/
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(List_Device_2.this, "Refresh device", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(List_Device_2.this, "delete device", Toast.LENGTH_SHORT).show();
            }
        });
        //------------------------------------------------------------------------------------
    }


    public void GetTenThietBi()
    {
        userN=usernameUSER.substring(0,(usernameUSER).length()-10);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("User/"+userN).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                namedevice= (String) dataSnapshot.child("name").getValue();
                mangThietBi.add(new ThietBi2(namedevice));
                String ididid=dataSnapshot.getKey();
                mID.add(ididid);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String keyy= dataSnapshot.getKey();
                if (keyy.equals("name"))
                {
                    namedevice=dataSnapshot.getValue().toString();
                    String key= dataSnapshot.getKey();
                    int index= mID.indexOf(key);
                    mangThietBi.set(index,new ThietBi2(namedevice));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
