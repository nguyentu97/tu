package com.example.danhba;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.DanhBa;

public class ActivityChiTiet extends MainActivity {
    TextView txtTenCT, txtDienThoaiCT;
    Button btnSuaCT, btnThoatCT;
    Intent intent;
    DanhBa danhBa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnSuaCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityChiTiet.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_sua, null);
                final TextView txtTenCN, txtDienThoaiCN;
                Button btnCapNhat, btnHuyCapNhat;
                txtTenCN = view.findViewById(R.id.txtTenCN);
                txtDienThoaiCN = view.findViewById(R.id.txtDienThoaiCN);
                btnCapNhat = view.findViewById(R.id.btnCapNhat);
                btnHuyCapNhat = view.findViewById(R.id.btnHuyCapNhat);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();


                txtTenCN.setText(danhBa.getName());
                txtDienThoaiCN.setText(danhBa.getPhone());
                btnCapNhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xuLyCapNhat(txtTenCN, txtDienThoaiCN, danhBa);
                        alertDialog.dismiss();
                    }
                });
                btnHuyCapNhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
        btnThoatCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void xuLyCapNhat(TextView txtTenCN, TextView txtDienThoaiCN, DanhBa danhBa) {
        danhBa.setName(txtTenCN.getText().toString());
        danhBa.setPhone(txtDienThoaiCN.getText().toString());
        txtTenCT.setText(danhBa.getName());
        txtDienThoaiCT.setText(danhBa.getPhone());
        capNhat(danhBa.getId(), danhBa.getName(), danhBa.getPhone());
    }

    private void addControl() {
        txtTenCT = findViewById(R.id.txtTenCT);
        txtDienThoaiCT = findViewById(R.id.txtDienThoaiCT);
        btnSuaCT = findViewById(R.id.btnSuaCT);
        btnThoatCT = findViewById(R.id.btnThoatCT);

        intent = getIntent();
        danhBa = (DanhBa) intent.getSerializableExtra("danhba");
        txtTenCT.setText(danhBa.getName());
        txtDienThoaiCT.setText(danhBa.getPhone());

    }
}
