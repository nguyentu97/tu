package com.example.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danhba.ActivityChiTiet;
import com.example.danhba.MainActivity;
import com.example.danhba.R;
import com.example.model.DanhBa;

import java.util.List;

public class AdapterDanhBa extends ArrayAdapter<DanhBa> {
    MainActivity context;
    int resource;
    List<DanhBa> objects;
    Intent intent;
    public AdapterDanhBa(MainActivity context, int resource, List<DanhBa> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtName = row.findViewById(R.id.txtName);
        TextView txtPhone = row.findViewById(R.id.txtPhone);
        Button btnXoa = row.findViewById(R.id.btnXoa);
        ImageView imgCall = row.findViewById(R.id.imgCall);
        ImageView imgSMS = row.findViewById(R.id.imgSMS);
        ImageView imgDetail = row.findViewById(R.id.imgDetail);

        final DanhBa danhBa = this.objects.get(position);
        txtName.setText(danhBa.getName());
        txtPhone.setText(danhBa.getPhone());

        Animation animation = AnimationUtils.loadAnimation(this.context,R.anim.animationlistview);
        row.startAnimation(animation);

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCall(danhBa);
            }
        });
        imgSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySMS(danhBa);
            }
        });
        imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDetail(danhBa);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXoa(danhBa);
            }
        });

        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.dialogXoa(danhBa.getId(),danhBa.getName(),danhBa.getPhone());
                return false;
            }
        });
        return row;
    }

    private void xuLyXoa(DanhBa danhBa) {
        context.dialogXoa(danhBa.getId(),danhBa.getName(),danhBa.getPhone());
    }

    private void xuLyDetail(DanhBa danhBa) {
        intent = new Intent(context, ActivityChiTiet.class);
        danhBa = new DanhBa(danhBa.getId(),danhBa.getName(),danhBa.getPhone());
        intent.putExtra("danhba",danhBa);
        context.startActivity(intent);
    }

    private void xuLySMS(DanhBa danhBa) {
        Uri uri = Uri.parse("sms:"+danhBa.getPhone());
        intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra("sms_body","");
        intent.setData(uri);
        this.context.startActivity(intent);
    }

    private void xuLyCall(DanhBa danhBa) {
        Uri uri = Uri.parse("tel:"+danhBa.getPhone());
        intent = new Intent(Intent.ACTION_CALL);
        intent.setData(uri);
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,new String[] {Manifest.permission.CALL_PHONE},1);
            return;
        }
        this.context.startActivity(intent);
    }
}
