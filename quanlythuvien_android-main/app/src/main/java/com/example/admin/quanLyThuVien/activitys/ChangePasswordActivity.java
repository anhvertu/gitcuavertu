package com.example.admin.quanLyThuVien.activitys;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;

import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtMaXacThuc, edtMatKhau, edtXacNhanMatKhau;
    private String maXacThuc = "", taiKhoan = "";
    private Button btnGuiLaiMa, btnXacNhan,btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mappingView();

        Intent intent = getIntent();
        maXacThuc = intent.getStringExtra("maXacThuc");
        taiKhoan = intent.getStringExtra("taiKhoan");

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidPassword()) {
                    changePassword();
                }
            }
        });

        btnGuiLaiMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                setResult(Activity.RESULT_OK, intent1);
                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                finish();
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void changePassword() {
        DataClient dataClient = APIUtils.getData();
        retrofit2.Call<String> call = dataClient.UpdateTaiKhoan(taiKhoan, edtMatKhau.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                String kq = response.body();
                if (kq.equals("Succsess")) {
                    Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.doi_mat_khau_thanh_cong), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                    intent.putExtra(LoginActivity.TAI_KHOAN, taiKhoan);
                    setResult(Activity.RESULT_OK, intent);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean isValidPassword() {
        if (edtMaXacThuc.getText().toString().trim().equals(maXacThuc)) {
            if (edtMatKhau.getText().toString().trim().length() > 0 && edtXacNhanMatKhau.getText().toString().trim().length() > 0) {
                if (edtMatKhau.getText().toString().trim().equals(edtXacNhanMatKhau.getText().toString().trim())) {
                    return true;
                } else {
                    edtXacNhanMatKhau.setError(getResources().getString(R.string.xac_nhan_mat_khau_sai));
                    edtXacNhanMatKhau.setText("");
                    Toast.makeText(this, getResources().getString(R.string.xac_nhan_mat_khau_sai), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (edtMatKhau.getText().toString().trim().isEmpty()) {
                    edtMatKhau.setError(getResources().getString(R.string.mat_khau_khong_duoc_trong));
                }
                if (edtXacNhanMatKhau.getText().toString().trim().isEmpty()) {
                    edtXacNhanMatKhau.setError(getResources().getString(R.string.mat_khau_khong_duoc_trong));
                }
            }

        } else {
            Toast.makeText(this, "Mã xác thực không đúng!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void mappingView() {
        edtMatKhau = findViewById(R.id.edtMatKhauMoi);
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhauMoi);
        edtMaXacThuc = findViewById(R.id.edtNhapMaXacThuc);
        btnGuiLaiMa = findViewById(R.id.btnGuiLaiMa);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnThoat = findViewById(R.id.btnThoatDoiMatKhau);

    }
}
