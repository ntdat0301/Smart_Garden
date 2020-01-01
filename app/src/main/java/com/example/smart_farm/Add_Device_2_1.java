package com.example.smart_farm;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.smart_farm.Login_1.usernameUSER;


public class Add_Device_2_1 extends AppCompatActivity {
    public DatabaseReference reff,mDatame,mData;
    public String namedevice="";
    int size;
    EditText edtID,edtNameDevice;
    Button btnAdd;
    ID_name id_name;
    public static ArrayList<String> OldDevice= new ArrayList<>();
    public String username;
    /*-------Phần bổ trợ thêm cho nut Back-----------------*/
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    /*----------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device_2_1);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        edtID= findViewById(R.id.editID);
        edtNameDevice=findViewById(R.id.edit_NameDevice);
        btnAdd=findViewById(R.id.btnAddDevice);

        id_name=new ID_name();

        username=usernameUSER.substring(0,(usernameUSER).length()-10);

        mDatame= FirebaseDatabase.getInstance().getReference();
        reff=mDatame.child("User/"+username);

        mData= mDatame.child("Device");
        FindArray();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KiemTra();
            }
        });
    }

    private void KiemTra() {
        String edt=edtID.getText().toString().trim();
        String name=edtNameDevice.getText().toString().trim();

        if (edt.equals("") || name.equals(""))
            {
                Toast.makeText(this, "Id hoặc tên đang bị trống", Toast.LENGTH_SHORT).show();
            }
        else
        {
            id_name.setID(edt);
            id_name.setName(name);

            Boolean test=false;
            if (size == 0) Toast.makeText(this, "Không có thiết bị nào có sẵn," +
                    " vui lòng bật thiết bị trước", Toast.LENGTH_SHORT).show();
            else
            {
                for (int k=0;k < size ;k++)
                {

                    if ((OldDevice.get(k)).equals(id_name.getID()))
                    {
                        test=true;
                    }
                    if (test==true)
                    {
                        UpdateData(id_name.getID(),id_name.getName());
                        test=false;
                        /*-----------------------*/
                        reff.child(edt).setValue(id_name);
                        /*------------------------*/
                       Intent intent= new Intent(Add_Device_2_1.this,List_Device_2.class);
                       startActivity(intent);
                       break;
                    }
                    else
                        Toast.makeText(this, "Thiết bị chưa được cài đặt. Hãy bật thiết bị của bạn trước", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    private void FindArray() {
        //Chuong trinh proceesbar
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                OldDevice.add(dataSnapshot.getKey().toString());
                size=OldDevice.size();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    private void UpdateData(final String idid,final  String namename) {
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mDatame.child("Device/"+idid+"/"+"name").setValue(namename);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                mDatame.child("Device/"+idid+"/"+"name").setValue(namename);
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
