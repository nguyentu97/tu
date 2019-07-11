package com.example.danhba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.DanhBa;

public class ActivityThem extends AppCompatActivity {
    TextView txtTen,txtDienThoai;
    Button btnLuu,btnThoat;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);

        addControll();
        addEvents();
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTen.getText().toString().equals("")||txtDienThoai.getText().toString().equals("")){
                    Toast.makeText(ActivityThem.this, "Mời bạn nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    xuLyLuu();
                    finish();
                }
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void xuLyLuu() {
        int id = 1;
        intent = new Intent();
        String ten = txtTen.getText().toString();
        String dienThoai = txtDienThoai.getText().toString();
        DanhBa danhBa = new DanhBa(id, ten, dienThoai);
        intent.putExtra("DB",danhBa);
        setResult(2,intent);
    }

    private void addControll() {
        txtTen = findViewById(R.id.txtTen);
        txtDienThoai = findViewById(R.id.txtDienThoai);
        btnLuu = findViewById(R.id.btnLuu);
        btnThoat = findViewById(R.id.btnThoat);
    }
}
