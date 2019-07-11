package com.example.danhba;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adapter.AdapterDanhBa;
import com.example.database.Database;
import com.example.model.DanhBa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fabThem;
    Database database;

    ListView lvDanhBa;
    ArrayList<DanhBa> dsDanhBa;
    AdapterDanhBa adapterDanhBa;

    CoordinatorLayout lnlMau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        addDatabase();

    }
    //MenuOption:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Menu Option
        getMenuInflater().inflate(R.menu.menu_option,menu);
        //Menu SearchView
        getMenuInflater().inflate(R.menu.menu_tim_kiem,menu);
        MenuItem menuItem =menu.findItem(R.id.menuTimKiem);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterDanhBa.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                adapterDanhBa.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuMauDo){
            lnlMau.setBackgroundColor(Color.RED);
        }else if(item.getItemId()==R.id.menuMauXanh){
            lnlMau.setBackgroundColor(Color.BLUE);
        }else{
            lnlMau.setBackgroundColor(Color.YELLOW);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDatabase() {
        //Tạo database
        database = new Database(this, "DANHBA.sqlite", null, 1);

        //Tạo table cho database
        database.queryData("CREATE TABLE IF NOT EXISTS KhachHang(Id INTEGER PRIMARY KEY AUTOINCREMENT, Ten VARCHAR(50), SDT VARCHAR(20))");

        // Truy vấn dữ liệu từ database
        truyvanDL();


    }

    private void truyvanDL() {
        Cursor cursor = database.getData("SELECT*FROM KhachHang");
        dsDanhBa.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            dsDanhBa.add(new DanhBa(id, ten, sdt));
        }
        adapterDanhBa.notifyDataSetChanged();
    }

    // Hiển thị dialog Xóa
    public void dialogXoa(final int id,String tenCV ,String soDienThoai ) {
        final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có chắc chắn muốn xóa \"" + tenCV + "\" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.queryData("DELETE FROM KhachHang WHERE Id = '" + id + "'");
                Toast.makeText(MainActivity.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                truyvanDL();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    public void capNhat(int id, String ten,String phone){
        dsDanhBa.clear();
        database.queryData("UPDATE KhachHang SET Ten ='" + ten + "',SDT = '"+phone+"' WHERE Id = '" + id + "' ");
        adapterDanhBa.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        truyvanDL();
    }

    private void addEvents() {
        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityThem.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            DanhBa danhBa = (DanhBa) data.getSerializableExtra("DB");
            database.queryData("INSERT INTO KhachHang VALUES(null,'" + danhBa.getName() + "','" + danhBa.getPhone() + "')");
            truyvanDL();
            Toast.makeText(this, "Thêm Thành  Công!" , Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        lnlMau = findViewById(R.id.mau);
        fabThem = findViewById(R.id.fabThem);
        lvDanhBa = findViewById(R.id.lvDanhBa);
        dsDanhBa = new ArrayList<>();
        adapterDanhBa = new AdapterDanhBa(MainActivity.this, R.layout.item, dsDanhBa);
        lvDanhBa.setAdapter(adapterDanhBa);

    }
}
