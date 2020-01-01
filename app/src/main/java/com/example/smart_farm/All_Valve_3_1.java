package com.example.smart_farm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class All_Valve_3_1 extends AppCompatActivity {
    public DatabaseReference mDatabase;
    public ListView lvVan;
    ImageButton btnAdd_Luong,btnDelete_Luong;
    public String nameVan="";
    public String doam="";

    public static ArrayList<Luong> mangLuong;
    public LuongAdapter adapter_luong;
    public static ArrayList<String> mID_Luong=new ArrayList<>();

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
        setContentView(R.layout.activity_all_valve_3_1);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        lvVan= findViewById(R.id.lv_Van);
        btnAdd_Luong= findViewById(R.id.btn_image_Add);
        btnDelete_Luong= findViewById(R.id.btn_image_Delete);
        /*************************************************/
        mangLuong= new ArrayList<Luong>();
        // Thêm phẩn tử vào mảng theo object

        //--------------------------------thiết lập apdapter cho nó-------------------------------
        adapter_luong = new LuongAdapter(All_Valve_3_1.this, R.layout.line_value_3_1_1,mangLuong);
        lvVan.setAdapter(adapter_luong); // Đổ dữ liệu ra màn hình

        GetMang();
        /**************************************************/
        btnAdd_Luong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(All_Valve_3_1.this, "add luong", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete_Luong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(All_Valve_3_1.this, "delete luong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GetMang() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String keyluong= Objects.requireNonNull(dataSnapshot.getKey()).trim();
                    mID_Luong.add(keyluong);
                    String pathname= "name";
                    String pathedoam="Doam";
                    nameVan= (String) dataSnapshot.child(pathname).getValue();
                    doam= (String) dataSnapshot.child(pathedoam).getValue();
                    mangLuong.add(new Luong(nameVan,doam)); // thêm phẩn tử vào mảng theo object
                    adapter_luong.notifyDataSetChanged(); // gọi hàm này để luôn hiển thị lại
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String pathname= "name";
                String pathedoam="Doam";
                nameVan= (String) dataSnapshot.child(pathname).getValue();
                doam= (String) dataSnapshot.child(pathedoam).getValue();

                String key= dataSnapshot.getKey();
                int index= mID_Luong.indexOf(key);

                mangLuong.set(index,new Luong(nameVan,doam)); // thêm phẩn tử vào mảng theo object

                adapter_luong.notifyDataSetChanged();
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
