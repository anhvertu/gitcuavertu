package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.CalenderHandler;
import com.example.admin.quanLyThuVien.ImageHandler;
import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends ImageHandler {

    private EditText edtTen, edtEmail, edtNgaySinh, edtDienThoai, edtDiaChi, edtNgayLamThe, edtMatKhau, edtTaiKhoan, edtXacNhanMatKhau;
    private ImageButton btnChonAnh;
    private ImageView imgAnh, imgNgaySinh;
    private Toolbar toolbar;
    private CalendarView calen;
    private View viewCalen;
    private String ma, ten, chucVu = "", email, ngaySinh, gioiTinh, dienThoai, diaChi, ngayLam, taiKhoan, matKhau, xacNhanMatKhau;
    private RelativeLayout rlCalenInclu;
    private Button btnCalenHuy, btnCalenOk;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton rdbNam, rdbNu;
    private CalenderHandler calenderHandler;
    private CommonVariables commonVariables;
    private TextInputLayout textInputLayoutMatKhau, textInputLayoutTaiKhoan, tilNgayLam;
    private DataClient dataClient;
    private String loaiUser = "";
    private Spinner spChucVu;
    private ArrayAdapter adapterChucVu;
    private LinearLayout llChucVu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);
        anhXa();
        commonVariables = (CommonVariables) getApplication();
        textInputLayoutTaiKhoan.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.them_doc_gia));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        loaiUser = intent.getStringExtra("loaiUser");

        if (!loaiUser.equals("DG")) {
            getSupportActionBar().setTitle(getResources().getString(R.string.them_nhan_vien));
            tilNgayLam.setHint(getResources().getString(R.string.ngay_vao_lam));
            llChucVu.setVisibility(View.VISIBLE);
            adapterChucVu = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{getResources().getString(R.string.thu_thu), getResources().getString(R.string.ki_thuat), getResources().getString(R.string.ban_quan_ly)});
            spChucVu.setAdapter(adapterChucVu);
        }

        initChonNgay();

        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted())
                    choosePhoto();
            }
        });
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted())
                    takePhoto();
            }
        });

        radioGroupGioiTinh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                doRadioGroupGioiTinh(radioGroup, i);
            }
        });
    }

    private void initChonNgay() {
        rlCalenInclu.setVisibility(View.INVISIBLE);
        calenderHandler = new CalenderHandler(this);
        textInputLayoutMatKhau.setVisibility(View.VISIBLE);
        edtNgayLamThe.setText(getNgayHienTai());
        edtNgayLamThe.setEnabled(false);

        imgNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderHandler.setDefaultDate("01/01/2000", calen);
                rlCalenInclu.setVisibility(View.VISIBLE);
            }
        });

        viewCalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCalenInclu.setVisibility(View.INVISIBLE);
            }
        });
        btnCalenHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCalenInclu.setVisibility(View.INVISIBLE);
            }
        });

        edtNgaySinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calenderHandler.validateDate(s, start, before, count, edtNgaySinh);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        calen.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                ngaySinh = calenderHandler.setDate(i, i1, i2);
            }
        });

        btnCalenOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNgaySinh.setText(ngaySinh);
                rlCalenInclu.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Khi radio group "giới tính" có thay đổi.
    private void doRadioGroupGioiTinh(RadioGroup radioGroup, int i) {
        int checkedRadioId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioId == R.id.rdbNam) {
            gioiTinh = getResources().getString(R.string.nam);
            rdbNam.setTextColor(Color.BLACK);
            rdbNu.setTextColor(Color.GRAY);
        } else if (checkedRadioId == R.id.rdbNu) {
            gioiTinh = getResources().getString(R.string.nu);
            rdbNu.setTextColor(Color.BLACK);
            rdbNam.setTextColor(Color.GRAY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == REQUEST_PiCK_IMG) {
                Uri imgUri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(imgUri);
                imgAnh.setImageURI(imgUri);
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                imgAnh.setImageBitmap(myBitmap);
            }
    }

    public String getNgayHienTai() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int mon = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        String d = "", m = "";
        if (day < 10) {
            d = "0" + day;
        } else {
            d = day + "";
        }

        if (mon < 10) {
            m = "0" + mon;
        } else {
            m = mon + "";
        }
        return d + "/" + m + "/" + year;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLuu:
                guiDuLieuLen();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void guiDuLieuLen() {
        ten = edtTen.getText().toString().trim();
        if (!loaiUser.equals("DG"))
            chucVu = spChucVu.getSelectedItem().toString().trim();
        email = edtEmail.getText().toString().trim();
        dienThoai = edtDienThoai.getText().toString().trim();
        taiKhoan = edtTaiKhoan.getText().toString().trim();
        matKhau = edtMatKhau.getText().toString().trim();
        xacNhanMatKhau = edtXacNhanMatKhau.getText().toString().trim();
        diaChi = edtDiaChi.getText().toString().trim();
        ngaySinh = edtNgaySinh.getText().toString();
        ngayLam = getNgayHienTai();
        Log.d("XXXX", ngayLam);
        if (rdbNam.isChecked()) {
            gioiTinh = getResources().getString(R.string.nam);
        } else if (rdbNu.isChecked()) {
            gioiTinh = getResources().getString(R.string.nu);
        }

        setMa();
        if (chucVu.isEmpty())
            chucVu = "DG";
//        Toast.makeText(this, bienToanCuc.getMaDocGiaCuoi(), Toast.LENGTH_SHORT).show();

        if (ma.length() > 0 && ten.length() > 0 && email.length() > 0
                && gioiTinh.length() > 0 && diaChi.length() >= 0 && dienThoai.length() > 0
                && ngaySinh.length() > 0 && ngayLam.length() > 0 && taiKhoan.length() > 0
                && matKhau.length() > 0 && xacNhanMatKhau.length() > 0) {
            if (matKhau.equals(xacNhanMatKhau) && !matKhau.equals("")) {

                dataClient = APIUtils.getData();
                Call<ResponseBody> call = dataClient.CheckTaiKhoan(taiKhoan);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String ketQua = response.body().string();
//                            Toast.makeText(ThemUserActivity.this, ketQua, Toast.LENGTH_SHORT).show();
                            if (ketQua.equals("0")) {
                                themUser();
                            } else {
                                edtTaiKhoan.setText("");
                                edtTaiKhoan.setError(getResources().getString(R.string.tai_khoan_ton_tai));
                                Toast.makeText(AddUserActivity.this, getResources().getString(R.string.tai_khoan_ton_tai), Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            } else {
                edtXacNhanMatKhau.setText("");
                edtXacNhanMatKhau.setError(getResources().getString(R.string.xac_nhan_mat_khau_sai));
                Toast.makeText(this, getResources().getString(R.string.xac_nhan_mat_khau_sai), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.hay_nhap_du_thong_tin), Toast.LENGTH_SHORT).show();
        }
    }

    private void setMa() {
        ma = loaiUser;
        if (loaiUser.equals("DG")) {
            if (commonVariables != null && !commonVariables.getMaDocGiaCuoi().equals("")) {
                int tam = 5 - commonVariables.kiemTraSoDocGia();
                while (tam > 0) {
                    ma += "0";
                    tam--;
                }
                int tam1 = commonVariables.soDocGiaCuoi() + 1;
                ma += tam1;
                commonVariables.setMaDocGiaCuoi(ma);
            } else {
                ma += "00001";
            }
        } else {
            if (commonVariables != null && !commonVariables.getMaNhanVienCuoi().equals("")) {
                int tam = 3 - commonVariables.kiemTraSoNhanVien();
                while (tam > 0) {
                    ma += "0";
                    tam--;
                }
                int tam1 = commonVariables.soNhanVienCuoi() + 1;
                ma += tam1;
                commonVariables.setMaNhanVienCuoi(ma);
            } else {
                ma += "001";
            }
        }
    }

    private void themUser() {
        File file = new File(mCurrentPhotoPath);
        String file_path = "";
        if (mCurrentPhotoPath.startsWith("JPEG") == true) {
            file_path = mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf('/'));
        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            file_path = "JPEG_" + timeStamp + "_" + ".jpg";

        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);


        Call<String> callback = dataClient.UploadPhoto(body);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null) {
                    String message = response.body();
                    if (message.length() > 0) {
                        String anhDocGia = APIUtils.Base_Url + "images/" + message;
                        if (loaiUser.equals("DG")) {
                            guiDocGia(anhDocGia);
                        } else {
                            guiNhanVien(anhDocGia);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("DDD", t.getMessage());
            }
        });

    }

    private void guiNhanVien(String anhNhanVien) {
        Call<String> call = dataClient.InsertNhanVien(ma, ten, chucVu, email, ngaySinh, gioiTinh, dienThoai, diaChi, ngayLam, anhNhanVien);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.equals("Succsess")) {
                    themTaiKhoan();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void themTaiKhoan() {
        Call<String> call1 = dataClient.InsertTaiKhoan(taiKhoan, matKhau, chucVu, ma);
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result1 = response.body();
                if (result1.equals("Succsess")) {
                    Toast.makeText(AddUserActivity.this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddUserActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void guiDocGia(String anhDocGia) {
        Call<String> callBack = dataClient.InsertDocGia(ma, ten, email, ngaySinh, gioiTinh, dienThoai, diaChi, ngayLam, anhDocGia);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if (result.equals("Succsess")) {
                    themTaiKhoan();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void anhXa() {
        edtTen = findViewById(R.id.edtThemSuaTenUser);
        edtEmail = findViewById(R.id.edttThemSuaEmailUser);
        edtTaiKhoan = findViewById(R.id.edtThemSuaTaiKhoanUser);
        edtMatKhau = findViewById(R.id.edtThemSuaMatKhauUser);
        edtXacNhanMatKhau = findViewById(R.id.edtThemSuaXacNhanMatKhauUser);
        edtDiaChi = findViewById(R.id.edtThemSuaDiaChiUser);
        edtDienThoai = findViewById(R.id.edtThemSuaDienThoaiUser);
        edtNgaySinh = findViewById(R.id.edtThemSuaNgaySinhUser);
        edtNgayLamThe = findViewById(R.id.edtThemSuaNgayLamUser);
        btnChonAnh = findViewById(R.id.btnThemSuaChonAnhUser);
        imgNgaySinh = findViewById(R.id.imgThemSuaNgaySinhUser);
        imgAnh = findViewById(R.id.imgThemSuaAnhUser);
        calen = findViewById(R.id.calen);
        viewCalen = findViewById(R.id.viewCalen);
        btnCalenHuy = findViewById(R.id.btnCalenCancel);
        btnCalenOk = findViewById(R.id.btnCalenOk);
        rlCalenInclu = findViewById(R.id.clUser);
        radioGroupGioiTinh = findViewById(R.id.rdgGioiTinhUser);
        rdbNam = findViewById(R.id.rdbNam);
        rdbNu = findViewById(R.id.rdbNu);
        toolbar = findViewById(R.id.toolbar_them_sua_user);
//        llNgayLamThe = findViewById(R.id.llNgayLamThe);
        llChucVu = findViewById(R.id.llThemSuaChucVuUser);
        spChucVu = findViewById(R.id.spThemSuaChucVuUser);
        textInputLayoutMatKhau = findViewById(R.id.tilMatKhauUser);
        textInputLayoutTaiKhoan = findViewById(R.id.tilTaiKhoanUser);
        tilNgayLam = findViewById(R.id.tilNgayLamUser);
    }
}
