package com.example.admin.quanLyThuVien.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    /* LAY_LAI_MAT_KHAU là một giá trị int dùng để định danh mỗi request. Khi nhận được kết quả, hàm callback sẽ trả về cùng LAY_LAI_MAT_KHAU này để ta có thể xác định và xử lý kết quả. */
    private static final int LAY_LAI_MAT_KHAU = 20;
    public static final String TAI_KHOAN = "taiKhoan";
    private EditText edtTaiKhoan, edtMatKhau;
    private TextView txtQuenMatKhau;
    private Button btnDangNhap;
    private String taiKhoan, matKhau;
    private CommonVariables commonVariables;
    private ImageButton imgbtnThoat;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mappingView();
        commonVariables = (CommonVariables) getApplication();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        imgbtnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                // Start QuenMatKhauActivity với equest code  vừa được khai báo trước đó
                intent.putExtra(TAI_KHOAN, edtTaiKhoan.getText().toString().trim());
                // Đặt resultCode là Activity.RESULT_OK to
                // thể hiện đã thành công và có chứa kết quả trả về
                setResult(Activity.RESULT_OK, intent);
                startActivityForResult(intent, LAY_LAI_MAT_KHAU);
            }
        });

    }

    private void exit() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getResources().getString(R.string.app_name));
        dialogBuilder.setIcon(R.mipmap.ic_logo_xanh);
        dialogBuilder.setMessage(getResources().getString(R.string.ban_co_muon_thoat));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.co), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
                System.exit(0);
                finish();
            }
        });

        dialogBuilder.setNegativeButton(getResources().getString(R.string.khong), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    // Khi kết quả được trả về từ Activity khác, hàm onActivityResult sẽ được gọi.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Kiểm tra requestCode có trùng với LAY_LAI_MAT_KHAU vừa dùng
        if (requestCode == LAY_LAI_MAT_KHAU) {
            // resultCode được set bởi QuenMatKhauActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                edtTaiKhoan.setText(data.getStringExtra(TAI_KHOAN));
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void login() {
        if (!isValidLogin()) {
            return;
        }
        if (!isOnline()) {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.khong_co_internet), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.dang_xu_ly));
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        DataClient dataClient = APIUtils.getData();
                        Call<ResponseBody> call = dataClient.CheckLogin(taiKhoan, matKhau);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        // get String from response
                                        String ketQua = response.body().string();
                                        if (!ketQua.equals("NULL")) {
                                            JSONObject data = new JSONObject(ketQua);
                                            dangNhapThanhCong(data.get("quyenHan").toString(), data.get("maNguoiDung").toString());
                                        } else
                                            loginFail();
                                        // Do whatever you want with the String
                                    } catch (IOException | JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                }, 1000);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void dangNhapThanhCong(String quyen, String maNguoiDung) {
        btnDangNhap.setEnabled(true);
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.danh_nhap_thanh_cong), Toast.LENGTH_SHORT).show();
        commonVariables.setQuyenUser(quyen);
        commonVariables.setMaUser(maNguoiDung);
        progressDialog.dismiss();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginFail() {
        progressDialog.dismiss();
        btnDangNhap.setEnabled(true);
        Toast.makeText(getBaseContext(), getString(R.string.sai_tai_khoan_mat_khau), Toast.LENGTH_LONG).show();
        edtMatKhau.setText("");
    }

    private boolean isValidLogin() {
        boolean ketQua = true;
        taiKhoan = edtTaiKhoan.getText().toString().trim();
        matKhau = edtMatKhau.getText().toString().trim();
        if (taiKhoan.isEmpty() || taiKhoan.equals("")) {
            edtTaiKhoan.setError(getString(R.string.kiem_tra_tai_khoan));
            ketQua = false;
        } else
            edtTaiKhoan.setError(null);
        if (matKhau.isEmpty() || matKhau.equals("")) {
            edtMatKhau.setError(getString(R.string.kiem_tra_mat_khau));
            ketQua = false;
        } else
            edtMatKhau.setError(null);
        return ketQua;

    }

    private void mappingView() {
        edtTaiKhoan = findViewById(R.id.edtTaiKhoan);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        txtQuenMatKhau = findViewById(R.id.edtQuenMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        imgbtnThoat = findViewById(R.id.imgBtnThoatUngDung);
    }
}
