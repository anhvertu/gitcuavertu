package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.CalenderHandler;
import com.example.admin.quanLyThuVien.ImageHandler;
import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.models.DocGia;
import com.example.admin.quanLyThuVien.models.NhanVien;
import com.example.admin.quanLyThuVien.models.TaiKhoan;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserInfoActivity extends ImageHandler {

    private EditText edtTen, edtEmail, edtMatKhau, edtXacNhanMatKhau, edtTaiKhoan, edtNgaySinh, edtDienThoai, edtDiaChi, edtNgayLamThe;
    private TextView txtTaoMatKhauMoi;
    private ImageButton btnChonAnh;
    private ImageView imgAnh, imgNgaySinh, imgNgayLamThe;
    private Toolbar toolbar;
    private CalendarView calen;
    private View viewCalen;
    private String ma, ten, chucVu, email, ngaySinh, gioiTinh, dienThoai, diaChi, ngayLam, taiKhoan, matKhau, xacNhanMatKhau, anh, anhCu;
    private RelativeLayout rlCalenInclu, rlNgayLamThe;
    private LinearLayout llTaoMatKhauMoi, llChucVu;
    private Button btnCalenHuy, btnCalenOk;
    private DocGia docGia;
    private NhanVien nhanVien;
    private TaiKhoan TaiKhoan;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton rdbNam, rdbNu;
    private int kiemTraNgay = 0;
    private CalenderHandler calenderHandler;
    private DataClient dataClient;
    private TextInputLayout tilNgayLam;
    private Spinner spChucVu;
    private ArrayAdapter adapterChucVu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);

        anhXa();

        txtTaoMatKhauMoi.setVisibility(View.VISIBLE);
        llTaoMatKhauMoi.setVisibility(View.GONE);
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.tilMatKhauUser);
        textInputLayout.setHint(getResources().getString(R.string.mat_khau_moi));
        edtTaiKhoan.setEnabled(false);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        docGia = (DocGia) intent.getParcelableExtra("thongTinDocGia");
        nhanVien = intent.getParcelableExtra("thongTinNhanVien");
        TaiKhoan = intent.getParcelableExtra("ThongTinTaiKhoan");

        if (docGia == null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.sua_nhan_vien));
            ma = nhanVien.getMaNhanVien();
            ten = nhanVien.getTen();
            chucVu = nhanVien.getChucVu();
            ngaySinh = nhanVien.getNgaySinh();
            gioiTinh = nhanVien.getGioiTinh();
            email = nhanVien.getEmail();
            dienThoai = nhanVien.getSoDienThoai();
            diaChi = nhanVien.getDiaChi();
            ngayLam = nhanVien.getNgayVaoLam();
            anh = nhanVien.getAnhNhanVien();
            tilNgayLam.setHint(getResources().getString(R.string.ngay_vao_lam));
            llChucVu.setVisibility(View.VISIBLE);
            spChucVu.setEnabled(false);
            adapterChucVu = new ArrayAdapter(this, android.R.layout.simple_list_item_1, new String[]{"Thủ thư", "Kỹ thuật", "Ban quản lý"});
            spChucVu.setAdapter(adapterChucVu);
        } else if (nhanVien == null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.sua_doc_gia));
            ma = docGia.getMaDocGia();
            ten = docGia.getTen();
            chucVu = "";
            ngaySinh = docGia.getNgaySinh();
            gioiTinh = docGia.getGioiTinh();
            email = docGia.getEmail();
            dienThoai = docGia.getSoDienThoai();
            diaChi = docGia.getDiaChi();
            ngayLam = docGia.getNgayLamThe();
            anh = docGia.getAnhDocGia();
        }

        getSuaUser(TaiKhoan);

        initThongTinChonNgay();

        txtTaoMatKhauMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (llTaoMatKhauMoi.getVisibility()) {
                    case View.GONE:
                        llTaoMatKhauMoi.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        llTaoMatKhauMoi.setVisibility(View.GONE);
                        break;

                }
            }
        });

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

    private void initThongTinChonNgay() {
        rlCalenInclu.setVisibility(View.INVISIBLE);
        calenderHandler = new CalenderHandler(this);
        rlNgayLamThe.setVisibility(View.VISIBLE);

        imgNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderHandler.setDefaultDate(edtNgaySinh.getText().toString(), calen);
                rlCalenInclu.setVisibility(View.VISIBLE);
                kiemTraNgay = 1;
            }
        });


        imgNgayLamThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderHandler.setDefaultDate(edtNgayLamThe.getText().toString(), calen);
                rlCalenInclu.setVisibility(View.VISIBLE);
                kiemTraNgay = 2;
            }
        });

        viewCalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraNgay = 0;
                rlCalenInclu.setVisibility(View.INVISIBLE);
            }
        });
        btnCalenHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kiemTraNgay = 0;
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

        edtNgayLamThe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calenderHandler.validateDate(s, start, before, count, edtNgayLamThe);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        calen.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if (kiemTraNgay == 1) {
                    ngaySinh = calenderHandler.setDate(i, i1, i2);
//                    Toast.makeText(SuaThongTinUserActivity.this, ngaySinh, Toast.LENGTH_SHORT).show();
                } else if (kiemTraNgay == 2) {
                    ngayLam = calenderHandler.setDate(i, i1, i2);
//                    Toast.makeText(SuaThongTinUserActivity.this, ngayLam, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCalenOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraNgay == 1) {
                    edtNgaySinh.setText(ngaySinh);
                } else if (kiemTraNgay == 2) {
                    edtNgayLamThe.setText(ngayLam);
                }
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
//                Log.d("DDD", mCurrentPhotoPath);

//                Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
//                imgAnh.setImageBitmap(myBitmap);
                setPic(imgAnh);

            }
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
        email = edtEmail.getText().toString().trim();
        taiKhoan = edtTaiKhoan.getText().toString().trim();
        matKhau = edtMatKhau.getText().toString();
        xacNhanMatKhau = edtXacNhanMatKhau.getText().toString();
        dienThoai = edtDienThoai.getText().toString().trim();
        diaChi = edtDiaChi.getText().toString().trim();
        ngaySinh = edtNgaySinh.getText().toString().trim();
        ngayLam = edtNgayLamThe.getText().toString().trim();
        if (rdbNam.isChecked()) {
            gioiTinh = getResources().getString(R.string.nam);
        } else if (rdbNu.isChecked()) {
            gioiTinh = getResources().getString(R.string.nu);
        }
        anhCu = "";


        if (ma.length() > 0 && ten.length() > 0 && email.length() > 0
                && gioiTinh.length() > 0 && diaChi.length() >= 0 && dienThoai.length() > 0
                && ngaySinh.length() > 0 && ngayLam.length() > 0) {
            File file = new File(mCurrentPhotoPath);
            dataClient = APIUtils.getData();

            String file_path = "";
            if (mCurrentPhotoPath.length() == 0) {
                if (nhanVien == null) {
                    guiDuLieuDocGia(anh, anhCu);
                } else if (docGia == null) {
                    guiDuLieuNhanVien(anh, anhCu);
                }
                return;
            }

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
//                        Log.d("DDDD", message);
                        if (message.length() > 0) {
                            String anhUser = APIUtils.Base_Url + "images/" + message;
                            if (nhanVien == null) {
                                guiDuLieuDocGia(anhUser, anh);
                            } else if (docGia == null) {
                                guiDuLieuNhanVien(anhUser, anh);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
//                    Log.d("DDD", t.getMessage());

                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.hay_nhap_du_thong_tin), Toast.LENGTH_SHORT).show();
        }
    }


    public void guiDuLieuNhanVien(final String anhNhanVien, String anhCu) {

        if (!anhCu.isEmpty() && anhCu.length() > 0)
            anhCu = anhCu.substring(anhCu.lastIndexOf("/"));

        Call<String> callBack = dataClient.UpdateNhanVien(ma, ten, chucVu, email, ngaySinh, gioiTinh, dienThoai, diaChi, ngayLam, anhNhanVien, anhCu);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                String result = response.body();
                Log.d("XXXX", result);
                if (result.equals("Succsess1")) {

                    if (matKhau.length() > 0 && matKhau.equals(xacNhanMatKhau)) {
//                        Toast.makeText(SuaThongTinUserActivity.this, taiKhoan + " " + matKhau, Toast.LENGTH_SHORT).show();
                        Call<String> call1 = dataClient.UpdateTaiKhoan(taiKhoan, matKhau);
                        call1.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketQua = response.body();
                                if (ketQua.equals("Succsess")) {
                                    Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                                    setSuaNhanVien(nhanVien, anhNhanVien);
                                    Intent intent = new Intent(EditUserInfoActivity.this, UserInfoActivity.class);
                                    intent.putExtra("thongTinNhanVien", nhanVien);
                                    intent.putExtra("ThongTinTaiKhoan", TaiKhoan);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("LOI", t.getMessage());
                                Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
                                return;


                            }
                        });
                    } else if (matKhau.length() > 0 && !matKhau.equals(xacNhanMatKhau)) {
                        edtXacNhanMatKhau.setText("");
                        edtXacNhanMatKhau.setError(getResources().getString(R.string.xac_nhan_mat_khau_sai));
                        Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.xac_nhan_mat_khau_sai), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                    setSuaNhanVien(nhanVien, anhNhanVien);
                    Intent intent = new Intent(EditUserInfoActivity.this, UserInfoActivity.class);
                    intent.putExtra("thongTinNhanVien", nhanVien);
                    intent.putExtra("ThongTinTaiKhoan", TaiKhoan);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("DDD", t.getMessage());
            }
        });
    }

    public void guiDuLieuDocGia(final String anhDocGia, String anh) {
        if (!anh.isEmpty() && anh.length() > 0)
            anh = anh.substring(anh.lastIndexOf("/"));

        Call<String> callBack = dataClient.UpdateDocGia(ma, ten, email, ngaySinh, gioiTinh, dienThoai, diaChi, ngayLam, anhDocGia, anh);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                String result = response.body();
                Log.d("XXXX", result);
                if (result.equals("Succsess1")) {

                    if (matKhau.length() > 0 && matKhau.equals(xacNhanMatKhau)) {
//                        Toast.makeText(SuaThongTinUserActivity.this, taiKhoan + " " + matKhau, Toast.LENGTH_SHORT).show();
                        Call<String> call1 = dataClient.UpdateTaiKhoan(taiKhoan, matKhau);
                        call1.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                String ketQua = response.body();
                                if (ketQua.equals("Succsess")) {
                                    Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                                    setSuaDocGia(docGia, anhDocGia);
                                    Intent intent = new Intent(EditUserInfoActivity.this, UserInfoActivity.class);
                                    intent.putExtra("thongTinDocGia", docGia);
                                    intent.putExtra("ThongTinTaiKhoan", TaiKhoan);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("LOI", t.getMessage());
                                Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
                                return;


                            }
                        });
                    } else if (matKhau.length() > 0 && !matKhau.equals(xacNhanMatKhau)) {
                        edtXacNhanMatKhau.setText("");
                        edtXacNhanMatKhau.setError(getResources().getString(R.string.xac_nhan_mat_khau_sai));
                        Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.xac_nhan_mat_khau_sai), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(EditUserInfoActivity.this, getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                    setSuaDocGia(docGia, anhDocGia);
                    Intent intent = new Intent(EditUserInfoActivity.this, UserInfoActivity.class);
                    intent.putExtra("thongTinDocGia", docGia);
                    intent.putExtra("ThongTinTaiKhoan", TaiKhoan);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("DDD", t.getMessage());
            }
        });
    }


    public void setSuaNhanVien(NhanVien nhanVien, String anhNhanVien) {
        nhanVien.setMaNhanVien(ma);
        nhanVien.setTen(ten);
        nhanVien.setEmail(email);
        nhanVien.setChucVu(chucVu);
        nhanVien.setNgaySinh(ngaySinh);
        nhanVien.setGioiTinh(gioiTinh);
        nhanVien.setSoDienThoai(dienThoai);
        nhanVien.setDiaChi(diaChi);
        nhanVien.setNgayVaoLam(ngayLam);
        nhanVien.setAnhNhanVien(anhNhanVien);
    }

    public void setSuaDocGia(DocGia docGia, String anh) {
        docGia.setMaDocGia(ma);
        docGia.setTen(ten);
        docGia.setEmail(email);
        docGia.setNgaySinh(ngaySinh);
        docGia.setGioiTinh(gioiTinh);
        docGia.setSoDienThoai(dienThoai);
        docGia.setDiaChi(diaChi);
        docGia.setNgayLamThe(ngayLam);
        docGia.setAnhDocGia(anh);
    }

    public void getSuaUser(TaiKhoan taiKhoan) {
        edtTen.setText(ten);
        edtEmail.setText(email);
        if (taiKhoan != null)
            edtTaiKhoan.setText(taiKhoan.getTaiKhoan());
//        edtMatKhau.setText(docGia.getMatKhau());
        edtNgaySinh.setText(ngaySinh);
        edtDienThoai.setText(dienThoai);
        edtDiaChi.setText(diaChi);
        edtNgayLamThe.setText(ngayLam);
        if (nhanVien != null) {
            int viTrispChucVu = adapterChucVu.getPosition(chucVu);
            spChucVu.setSelection(viTrispChucVu);
        }
        if (gioiTinh.equals(getResources().getString(R.string.nam))) {
            rdbNam.setChecked(true);
            rdbNam.setTextColor(Color.BLACK);
        } else {
            rdbNu.setChecked(true);
            rdbNu.setTextColor(Color.BLACK);
        }

        Picasso.get().load( APIUtils.Base_Url + "images/" + (anh))
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_book_error)
                .resize(180, 240)
                .centerCrop()
                .into(imgAnh);

    }

    public void anhXa() {
        txtTaoMatKhauMoi = findViewById(R.id.txtTaoMatKhauMoiUser);
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
        imgNgayLamThe = findViewById(R.id.imgThemSuaNgayLamUser);
        imgAnh = findViewById(R.id.imgThemSuaAnhUser);
        calen = findViewById(R.id.calen);
        viewCalen = findViewById(R.id.viewCalen);
        btnCalenHuy = findViewById(R.id.btnCalenCancel);
        btnCalenOk = findViewById(R.id.btnCalenOk);
        spChucVu = findViewById(R.id.spThemSuaChucVuUser);
        rlCalenInclu = findViewById(R.id.clUser);
        rlNgayLamThe = findViewById(R.id.rlNgayLamUser);
        llTaoMatKhauMoi = findViewById(R.id.llTaoMatKhauMoiUser);
        llChucVu = findViewById(R.id.llThemSuaChucVuUser);
        radioGroupGioiTinh = findViewById(R.id.rdgGioiTinhUser);
        rdbNam = findViewById(R.id.rdbNam);
        rdbNu = findViewById(R.id.rdbNu);
        toolbar = findViewById(R.id.toolbar_them_sua_user);
        tilNgayLam = findViewById(R.id.tilNgayLamUser);
    }
}
