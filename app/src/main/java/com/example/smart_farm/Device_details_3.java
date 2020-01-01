package com.example.smart_farm;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class Device_details_3 extends AppCompatActivity {

    TextView txtHumidityAir,txtTemperature,txtWeather;
    public DatabaseReference mDatabase,myRef1,myRef2;
    ImageButton btn_image_Control;
    Switch sw_Mode_automation;
    Integer nhietdo_setting,nhietdo_hientai,doam_setting,doam_hientai;

    public static final String CHANNEL_ID="Channel1";

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
        setContentView(R.layout.activity_device_details_3);

        /*-------------Phần tạo Nut back--------------------*/
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);// hiển thị nút Up ở Home icon

        txtHumidityAir = findViewById(R.id.txt_Humidity_Air);
        txtTemperature = findViewById(R.id.txt_Temperature_Air);
        txtWeather =findViewById(R.id.txt_Weather);
        btn_image_Control= findViewById(R.id.btn_image_Control);
        sw_Mode_automation=findViewById(R.id.sw_Mode);


        /*---------------------------------------------------------------------------------------*/
        /*---------------Update-----------------------*/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myRef1 = mDatabase.child("Device").child(Id_Thiet_Bi).child("Moitruong");
        myRef2 = mDatabase.child("Device").child(Id_Thiet_Bi).child("Setting");

        Updateparameter();
        /*--------------------------------------------*/
        btn_image_Control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Device_details_3.this, "Vào chế độ điều khiển van", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(Device_details_3.this, All_Valve_3_1.class);
                //startActivity(intent);
                createNotificationChannels();
            }
        });
        /*---------------------------------------------------------------------------------------*/
        sw_Mode_automation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sw_Mode_automation.isChecked()){
                    myRef1.child("Mode").setValue("1");
                }
                else
                    {
                    myRef1.child("Mode").setValue("0");
                }
            }
        });
    }

    public void Updateparameter()
    {
        myRef1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Parameter_air device_details= dataSnapshot.getValue(Parameter_air.class);
                assert device_details != null; //---------------------------------------------------------------------------------------------
                txtTemperature.setText(device_details.Nhietdo);
                txtHumidityAir.setText(device_details.Doam);

                Sosanh(device_details.Nhietdo,device_details.Doam); // để đưa ra thông báo

                if ((device_details.Thoitiet).equals("1"))
                {
                    txtWeather.setText("Mưa");
                }
                else txtWeather.setText("Không mưa");


                if ((device_details.Mode).equals("1"))
                {
                    sw_Mode_automation.setChecked(true);
                    sw_Mode_automation.setText("ON");
                }
                else {
                    sw_Mode_automation.setChecked(false);
                    sw_Mode_automation.setText("OFF");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Device_details_3.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Sosanh(final String nhietdo, final String doam) {
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doam_setting=Integer.parseInt(String.valueOf(dataSnapshot.child("Doam_Setting").getValue()));
                nhietdo_setting=Integer.parseInt(String.valueOf(dataSnapshot.child("Nhietdo_Setting").getValue()));
                doam_hientai=Integer.parseInt(doam);
                nhietdo_hientai=Integer.parseInt(nhietdo);

                if (doam_hientai > doam_setting) {
                    //CanhBao("ĐỘ ẨM MÔI TRƯỜNG QUÁ NGƯỠNG");
                    //createNotificationChannels();
                }
                if (nhietdo_hientai > nhietdo_setting) {
                    //createNotificationChannels();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void createNotificationChannels() {

        /*Intent intent = new Intent(this, Device_details_3.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)  // Thêm biểu tượng thông báo
                .setContentTitle("VIETNAM")                     // Tạo tiêu đề cho thông báo
                .setContentText("quá ngưỡng")                 // Tạo nội dung cho thông báo
                // .setStyle(new NotificationCompat.BigTextStyle()
                //         .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true); // nó sẽ tự động xóa notification khi người dùng chạm vào nó

        // notificationId is a unique int for each notification that you must define
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 1;
        notificationManager.notify(notificationId, mBuilder.build());
        Toast.makeText(this, "Đã vào đây", Toast.LENGTH_SHORT).show();*/


        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, Device_details_3.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }

}
