package com.example.smart_farm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.smart_farm.All_Valve_3_1.mID_Luong;

public class LuongAdapter extends BaseAdapter {

    public static String Id_Luong;
    public static String name_device_pulic;
    public static String doam_device_pulic;
    ProgressDialog progressBar;

    Context myContext;
    int myLayout;
    ArrayList<Luong> arrayLuong;

    public LuongAdapter(Context context, int layout, ArrayList<Luong> array_Luong)
    {
        myContext= context;
        myLayout= layout;
        arrayLuong= array_Luong;
    }

    @Override
    public int getCount() {
        return arrayLuong.size();
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
        TextView txtTenLuong;
        TextView DoAm_Luong;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if ( convertView ==null) {
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(myLayout, null);
            holder = new ViewHolder();
            // ánh xạ
            holder.txtTenLuong = (TextView) convertView.findViewById(R.id.txtLuong);
            holder.DoAm_Luong = (TextView) convertView.findViewById(R.id.txtHumidity);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }

        Luong luong= arrayLuong.get(position);

        Id_Luong="";
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Id_Luong= mID_Luong.get(position);
                XuLy();
                Intent intent=new Intent(myContext,Control_A_Valve_4.class);
                ((All_Valve_3_1) myContext).startActivity(intent);
            }
        });

        holder.txtTenLuong.setText(luong.getName());
        name_device_pulic=luong.getName();
        holder.DoAm_Luong.setText(luong.getDoam());
        doam_device_pulic=luong.getDoam();
        return convertView;
    }

    public void XuLy() {
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
        new LuongAdapter.MyThread().start();
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
