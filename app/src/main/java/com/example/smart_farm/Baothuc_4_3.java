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

import static com.example.smart_farm.LuongAdapter.Id_Luong;
import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class Baothuc_4_3 extends AppCompatActivity {
    public DatabaseReference mDatabase,mReff;
    public ListView lv_BaoThuc;
    public ImageButton btnAdd_BaoThuc,btnDelete_Baothuc;

    public String Year,Month,Day,Hour,Minute,name,stsBaothuc="";

    public static ArrayList<Baothuc> mangBaoThuc;
    public Baothuc_Adapter adapter_baothuc;
    public static ArrayList<String> mID_Baothuc=new ArrayList<>();

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
        setContentView(R.layout.activity_baothuc_4_3);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        lv_BaoThuc= findViewById(R.id.lv_BaoThuc);
        btnAdd_BaoThuc= findViewById(R.id.btn_image_add_alarm);
        btnDelete_Baothuc= findViewById(R.id.btn_image_delete_alarm);

        /*---------************************************************/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mReff = mDatabase.child("Device").child(Id_Thiet_Bi).child("Baothuc");

        mangBaoThuc= new ArrayList<Baothuc>();
        // Thêm phẩn tử vào mảng theo object

        GetMang();

        //--------------------------------thiết lập apdapter cho nó-------------------------------
        adapter_baothuc = new Baothuc_Adapter(Baothuc_4_3.this, R.layout.line_baothuc,mangBaoThuc);
        lv_BaoThuc.setAdapter(adapter_baothuc); // Đổ dữ liệu ra màn hình


        /*-------------*************************************************/
        btnAdd_BaoThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Baothuc_4_3.this, "thêm báo thức", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete_Baothuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Baothuc_4_3.this, "xóa báo thức", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetMang() {
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //mDatabase.child("Device/"+Id_Thiet_Bi+"/Baothuc").addChildEventListener(new ChildEventListener() {
        mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String keybaothuc= Objects.requireNonNull(dataSnapshot.getKey()).trim();

                mID_Baothuc.add(keybaothuc);

                Year= (String) dataSnapshot.child("Year").getValue();
                Month= (String) dataSnapshot.child("Month").getValue();
                Day= (String) dataSnapshot.child("Day").getValue();
                Hour= (String) dataSnapshot.child("Hour").getValue();
                Minute= (String) dataSnapshot.child("Minute").getValue();
                //name= (String) dataSnapshot.child("Name").getValue();
                stsBaothuc= (String) dataSnapshot.child("stsBaothuc").getValue();

                mangBaoThuc.add(new Baothuc(Year,Month,Day,Hour,Minute,stsBaothuc)); // thêm phẩn tử vào mảng theo object

                adapter_baothuc.notifyDataSetChanged(); // gọi hàm này để luôn hiển thị lại
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Year= (String) dataSnapshot.child("Year").getValue();
                Month= (String) dataSnapshot.child("Month").getValue();
                Day= (String) dataSnapshot.child("Day").getValue();
                Hour= (String) dataSnapshot.child("Hour").getValue();
                Minute= (String) dataSnapshot.child("Minute").getValue();
                //name= (String) dataSnapshot.child("Name").getValue();
                stsBaothuc= (String) dataSnapshot.child("stsBaothuc").getValue();

                dataSnapshot.getValue();

                String key= dataSnapshot.getKey();
                int index= mID_Baothuc.indexOf(key);

                mangBaoThuc.set(index,new Baothuc(Year,Month,Day,Hour,Minute,stsBaothuc)); // thêm phẩn tử vào mảng theo object
                adapter_baothuc.notifyDataSetChanged(); // gọi hàm này để luôn hiển thị lại
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
