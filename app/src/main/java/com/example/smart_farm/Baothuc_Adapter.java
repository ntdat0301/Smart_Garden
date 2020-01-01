package com.example.smart_farm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.smart_farm.Baothuc_4_3.mID_Baothuc;
import static com.example.smart_farm.LuongAdapter.Id_Luong;
import static com.example.smart_farm.ThietBiAdapter.Id_Thiet_Bi;

public class Baothuc_Adapter extends BaseAdapter {

    public DatabaseReference mDatabase,myRef2;
    public static String Id_BaoThuc,name;
    //public static String name_BaoThuc;
    private ProgressDialog progressBar;

    private Context myContext;
    private int myLayout;
    private ArrayList<Baothuc> arrayBaoThuc;

    public Baothuc_Adapter(Context context, int layout, ArrayList<Baothuc> baothucArrayList)
    {
        myContext= context;
        myLayout= layout;
        arrayBaoThuc= baothucArrayList;
    }


    @Override
    public int getCount() {
        return arrayBaoThuc.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;

    }

    private class ViewHolder{
        TextView txtBaothuc;
        Switch stsBaothuc;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if ( convertView ==null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(myLayout, null);
            holder = new ViewHolder();
            // ánh xạ
            mDatabase = FirebaseDatabase.getInstance().getReference();
            myRef2 = mDatabase.child("Device/"+Id_Thiet_Bi+"/LUONG/"+Id_Luong+"/Baothuc");
            holder.txtBaothuc = convertView.findViewById(R.id.txt_BaoThuc);
            holder.stsBaothuc= convertView.findViewById(R.id.sw_BaoThuc_OnOff);

            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }

        Baothuc baothuc= arrayBaoThuc.get(position);
        Id_BaoThuc="";

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Id_BaoThuc= mID_Baothuc.get(position);
                XuLy();
                Intent intent=new Intent(myContext, Setting_baothuc_5.class); //----------------------------------
                myContext.startActivity(intent);
            }
        });

        name = baothuc.getHour()+":"+baothuc.getMinute()+"   "+baothuc.getDay()+"/"
                    +baothuc.getMonth()+"/"+baothuc.getYear();

        holder.txtBaothuc.setText(name);

       if (baothuc.getStsBaothuc().equals("ON"))
            {
                holder.stsBaothuc.setChecked(true);
            }
        else holder.stsBaothuc.setChecked(false);

        //name_BaoThuc=baothuc.getName();


        holder.stsBaothuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.stsBaothuc.isChecked()){
                    myRef2.child("/"+mID_Baothuc.get(position)+"/stsBaothuc").setValue("ON");
                    Toast.makeText(myContext, "Báo thức đã được bật", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    myRef2.child("/"+mID_Baothuc.get(position)+"/stsBaothuc").setValue("OFF");
                    Toast.makeText(myContext, "Báo thức đã tắt", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private void XuLy() {
        // Khởi tạo progressBar với đối là Context
        progressBar = new ProgressDialog(myContext);
        // Cho phép hủy progressBar nếu ấn nút Back
        progressBar.setCancelable(true);
        progressBar.onBackPressed();
        // Đặt tiêu đề cho ProgressBar
        progressBar.setMessage("Đang xử lý, vui lòng đợi...");
        // Đặt giao diện cho ProgressBar
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Đặt giá trị đầu tiên, đây là giá trị thể hiện mức độ hoàn thành công
        // việc có thang từ 0 - > 100
        // do hiện tại công việc chưa hoàn thành được chút nào nên ta đặt là 0
        progressBar.setProgress(0);
        // Đặt cho giá trị lớn nhất thể hiện mức độ hoàn thành công việc là 100
        progressBar.setMax(100);
        // Hiện ProgressBar
        progressBar.show();

        // Tạo một luồng xử lý công việc.
        new Baothuc_Adapter.MyThread().start();
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            // xử lý công việc cần hoàn thành
            for (int i = 0; i < 30; i++) {
                // Tạm dừng 1s, thực tế thì chỗ này là xử lý công việc
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // tính xem công việc đã hoàn thành bao nhiêu phần trăm và đưa lên progressbar
                progressBar.setProgress((i * 100) / 30);
            }
            // đóng brogressbar.
            progressBar.dismiss();
        }
    }

}
