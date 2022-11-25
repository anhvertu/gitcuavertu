package com.example.admin.quanLyThuVien.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.fragments.BooksFragment;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private String maSach, tenSach, tinhTrangSach, choMuon, maDauSach;
    private DauSach dauSach;
    private CommonVariables commonVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        commonVariables = (CommonVariables) getApplication();
        Intent intent = getIntent();
        dauSach = intent.getParcelableExtra("thongTinDauSach");

        BooksFragment booksFragment = (BooksFragment) getSupportFragmentManager().findFragmentById(R.id.list_sach_fragment);

        bottomNavigationView = findViewById(R.id.list_sach_bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.getMenu().removeItem(R.id.menu_quet);

        if (commonVariables.getQuyenUser().equals("DG")) {
            bottomNavigationView.setVisibility(View.GONE);
        }

        toolbar = findViewById(R.id.toolbar_list_sach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.ds_sach));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (!commonVariables.getQuyenUser().equals("DG"))
                switch (item.getItemId()) {
                    case R.id.menu_them:
                        themSach();
                        break;
                }
            return true;
        }
    };

    private void themSach() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_book_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner = dialogView.findViewById(R.id.spSuaTinhTrangSach);
        spinner.setVisibility(View.GONE);
        final EditText edtThemSach = dialogView.findViewById(R.id.edtThemSuaSach);

        dialogBuilder.setTitle(getResources().getString(R.string.them_sach));
        dialogBuilder.setMessage(getResources().getString(R.string.nhap_so_luong_sach));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.them), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    String soSach = edtThemSach.getText().toString().trim();
                    if (soSach.isEmpty() || Integer.parseInt(soSach) == 0) {
                        Toast.makeText(BookActivity.this, getResources().getString(R.string.vui_long_nhap_so_sach), Toast.LENGTH_LONG).show();
                        themSach();
                    } else {

                        String soSanh = commonVariables.getMaSachCuoi();

                        for (int j = 0; j < Integer.parseInt(soSach); j++) {
                            guiDuLieuLen();
                        }
                        if (!commonVariables.getMaSachCuoi().equals(soSach))
                            Toast.makeText(BookActivity.this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                        //TODO here any local checks if password or user is valid
                    }
                } catch (Exception e) {
                    Toast.makeText(BookActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogBuilder.setNegativeButton(getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    private void guiDuLieuLen() {

        tenSach = dauSach.getTenDauSach();
        maDauSach = dauSach.getMaDauSach();
        tinhTrangSach = "Tốt";
        choMuon = "Chưa cho Mượn";

        maSach = "S";
        if (commonVariables != null && !commonVariables.getMaSachCuoi().equals("")) {
            int tam = 5 - commonVariables.kiemTraSoSach();
            while (tam > 0) {
                maSach += "0";
                tam--;
            }
            int tam1 = commonVariables.soSachCuoi() + 1;
            maSach += tam1;
            commonVariables.setMaSachCuoi(maSach);
        } else {
            maSach += "00001";
        }

        if (maSach.length() > 0 && tenSach.length() > 0 && tinhTrangSach.length() > 0
                && choMuon.length() > 0 && maDauSach.length() > 0) {
            DataClient insertData = APIUtils.getData();
            Call<String> callBack = insertData.InsertSach(maSach, tenSach, tinhTrangSach, choMuon, maDauSach);
            callBack.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String ketQua = response.body();
                    if (ketQua.equals("Succsess")) {
                    } else {
                        Toast.makeText(BookActivity.this, ketQua.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}
