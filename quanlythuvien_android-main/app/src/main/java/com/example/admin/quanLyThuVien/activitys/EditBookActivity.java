package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.ImageHandler;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBookActivity extends ImageHandler {

    private Spinner spTheLoai, spViTri;
    private ArrayList<String> arrTheLoai, arrNhaXuatBan, arrTacGia, arrViTri;
    private ArrayAdapter<String> adapterTheLoai, adapterNXB, adapterTacGia, adapterViTri;
    private EditText edtTenDauSach, edtNoiDungTomLuoc, edtNgayXuatBan, edtSotrang, edtGia;
    private AutoCompleteTextView actNhaXuatBan, actTacGia;
    private ImageButton btnChonAnhDauSach;
    private ImageView imgAnhDauSachTT, imgNgayXuatBan;
    private Toolbar toolbar;
    private CalendarView calenNgayXuatBan;
    private View viewNgayXuatBan;
    private String maDauSach, tenDauSach, ngayXuatBan, noiDungTomLuoc, txtcalenNgayXuatBan;
    private float tongSoDanhGia;
    private int soTrang, gia, soLuong, soNguoiDanhGia, maTheLoai, maNhaXuatBan, maTacGia, maViTri;
    private RelativeLayout rlCalenNgayXuatBan;
    private Button btnNgayXuatBanHuy, btnNgayXuatBanOk;
    private DauSach dauSach;
    private CommonVariables commonVariables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        anhXa();
        rlCalenNgayXuatBan.setVisibility(View.INVISIBLE);
        viewNgayXuatBan.setVisibility(View.INVISIBLE);

        commonVariables = (CommonVariables) getApplication();
        initDeMuc();
        imgNgayXuatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCalenNgayXuatBan.setVisibility(View.VISIBLE);
                viewNgayXuatBan.setVisibility(View.VISIBLE);
            }
        });

        viewNgayXuatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCalenNgayXuatBan.setVisibility(View.INVISIBLE);
                viewNgayXuatBan.setVisibility(View.INVISIBLE);
            }
        });
        btnNgayXuatBanHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlCalenNgayXuatBan.setVisibility(View.INVISIBLE);
                viewNgayXuatBan.setVisibility(View.INVISIBLE);
            }
        });

        edtNgayXuatBan.addTextChangedListener(mDateEntryWatcher);

        calenNgayXuatBan.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String d = "", m = "";
                i1 += 1;
                if (i1 < 10) {
                    m += "0" + i1;
                } else {
                    m += i1;
                }
                if (i2 < 10) {
                    d += "0" + i2;
                } else {
                    d += i2;
                }
                txtcalenNgayXuatBan = d + "/" + m + "/" + i;
            }
        });

        btnNgayXuatBanOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtcalenNgayXuatBan == null)
                    Toast.makeText(EditBookActivity.this, "qqqqqqqq", Toast.LENGTH_SHORT).show();
                edtNgayXuatBan.setText(txtcalenNgayXuatBan);
                rlCalenNgayXuatBan.setVisibility(View.INVISIBLE);
                viewNgayXuatBan.setVisibility(View.INVISIBLE);
            }
        });

        Intent intent = getIntent();
        dauSach = (DauSach) intent.getParcelableExtra("thongTinDauSach");
        getSuaDauSach(dauSach);

        toolbar = findViewById(R.id.toolbar_them_sua_dau_sach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.sua_thong_tin_sach));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgAnhDauSachTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted())
                    choosePhoto();
            }
        });
        btnChonAnhDauSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted())
                    takePhoto();
            }
        });
    }


    private void initDeMuc() {
        arrTheLoai = new ArrayList<String>();
        for (int i = 0; i < commonVariables.getmDSTheLoai().size(); i++) {
            arrTheLoai.add(commonVariables.getmDSTheLoai().get(i).getTenTheLoai());
        }

        arrViTri = new ArrayList<String>();
        for (int i = 0; i < commonVariables.getmDSViTri().size(); i++) {
            arrViTri.add(commonVariables.getmDSViTri().get(i).getTenViTri());
        }

        arrTacGia = new ArrayList<String>();
        for (int i = 0; i < commonVariables.getmDSTacGia().size(); i++) {
            arrTacGia.add(commonVariables.getmDSTacGia().get(i).getTenTacGia());
        }

        arrNhaXuatBan = new ArrayList<String>();
        for (int i = 0; i < commonVariables.getmDSNhaXuatBan().size(); i++) {
            arrNhaXuatBan.add(commonVariables.getmDSNhaXuatBan().get(i).getTenNhaXuatBan());
        }

        adapterTheLoai = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrTheLoai);
        spTheLoai.setAdapter(adapterTheLoai);
        adapterViTri = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrViTri);
        spViTri.setAdapter(adapterViTri);

        adapterNXB = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrNhaXuatBan);
        actNhaXuatBan.setAdapter(adapterNXB);
        actNhaXuatBan.setThreshold(1);

        adapterTacGia = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrTacGia);
        actTacGia.setAdapter(adapterTacGia);
        actTacGia.setThreshold(1);
    }

    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length() == 2 && before == 0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 31) {
                    isValid = false;
                } else {
                    working += "/";
                    edtNgayXuatBan.setText(working);
                    edtNgayXuatBan.setSelection(working.length());
                }
            } else if (working.length() == 5 && before == 0) {
                String enterMonth = working.substring(3);
                if (Integer.parseInt(enterMonth) < 1 || Integer.parseInt(enterMonth) > 12) {
                    isValid = false;
                } else {
                    working += "/";
                    edtNgayXuatBan.setText(working);
                    edtNgayXuatBan.setSelection(working.length());
                }
            } else if (working.length() == 10 && before == 0) {
                String enteredYear = working.substring(6);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                if (Integer.parseInt(enteredYear) > currentYear || Integer.parseInt(enteredYear) < 1900) {
                    isValid = false;
                }
            } else if (working.length() != 10) {
                isValid = false;
            }

            if (!isValid) {
                edtNgayXuatBan.setError(getResources().getString(R.string.sai_dinh_dang_ngay));
            } else {
                edtNgayXuatBan.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == REQUEST_PiCK_IMG) {
                Uri imgUri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(imgUri);
                imgAnhDauSachTT.setImageURI(imgUri);
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
//                Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_SHORT).show();
                Log.d("DDD", mCurrentPhotoPath);

                Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                imgAnhDauSachTT.setImageBitmap(myBitmap);

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
        maDauSach = dauSach.getMaDauSach();
        tenDauSach = edtTenDauSach.getText().toString().trim();
        maTheLoai = commonVariables.getMaTheLoai(spTheLoai.getSelectedItem().toString().trim());
        maNhaXuatBan = commonVariables.getMaNhaXuatBan(actNhaXuatBan.getText().toString().trim());
        maTacGia = commonVariables.getMaTacGia(actTacGia.getText().toString().trim());
        noiDungTomLuoc = edtNoiDungTomLuoc.getText().toString().trim();
        maViTri = commonVariables.getMaViTri(spViTri.getSelectedItem().toString().trim());
        soTrang = Integer.parseInt(edtSotrang.getText().toString().trim());
        ngayXuatBan = edtNgayXuatBan.getText().toString();
        gia = Integer.parseInt(edtGia.getText().toString().trim());
        soLuong = dauSach.getSoLuong();
        tongSoDanhGia = dauSach.getSoDanhGia();
        soNguoiDanhGia = dauSach.getSoNguoiDanhGia();

        if (maDauSach.length() > 0 && tenDauSach.length() > 0 && maTheLoai > 0 && maNhaXuatBan > 0
                && maTacGia > 0 && noiDungTomLuoc.length() > 0 && soLuong >= 0 && maViTri > 0
                && gia > 0 && soTrang > 0 && ngayXuatBan.length() > 0 && tongSoDanhGia >= 0 && soNguoiDanhGia >= 0) {
            File file = new File(mCurrentPhotoPath);
//            try {
//
//                file = new Compressor(this)
//                        .setMaxWidth(480)
//                        .setMaxHeight(640)
//                        .setQuality(75)
//                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                        .compressToFile(actualFile);
//
////                file = new Compressor(this)
////                        .setMaxWidth(640)
////                        .setMaxHeight(480)
////                        .setQuality(70)
////                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
////                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
////                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
////                        .compressToFile(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            String file_path = "";
            if (mCurrentPhotoPath.length() == 0) {
                guiDuLieuCon(dauSach.getAnhDauSach());
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

            DataClient dataClient = APIUtils.getData();

            retrofit2.Call<String> callback = dataClient.UploadPhoto(body);
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                    if (response != null) {
                        String message = response.body();
                        Log.d("DDD", message);
                        if (message.length() > 0) {
                            String anhDauSach = APIUtils.Base_Url + "images/" + message;
                            guiDuLieuCon(anhDauSach);
                        }
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<String> call, Throwable t) {
                    Log.d("DDD", t.getMessage());

                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.nhap_day_du), Toast.LENGTH_SHORT).show();
        }
    }

    public void guiDuLieuCon(final String anhDauSach) {

        final DataClient updateData = APIUtils.getData();
        retrofit2.Call<String> callBack = updateData.UpdateDauSach(maDauSach, tenDauSach, maTheLoai, maNhaXuatBan, maTacGia, noiDungTomLuoc, soLuong, maViTri, ngayXuatBan, soTrang, gia, anhDauSach, tongSoDanhGia, soNguoiDanhGia);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                String result = response.body();
                Log.d("DDD", result);
                if (result.equals("Succsess1")) {
                    Toast.makeText(EditBookActivity.this, getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                    setSuaDauSach(dauSach, anhDauSach);
                    Intent intent = new Intent(EditBookActivity.this, BookInforActivity.class);
                    intent.putExtra("thongTinDauSach", dauSach);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                Log.d("DDD", t.getMessage());
            }
        });
    }


    public void setSuaDauSach(DauSach dauSach, String anh) {
        dauSach.setMaDauSach(maDauSach);
        dauSach.setTenDauSach(tenDauSach);
        dauSach.setMaTacGia(maTacGia);
        dauSach.setSoLuong(soLuong);
        dauSach.setNoiDungTomLuoc(noiDungTomLuoc);
        dauSach.setMaNhaXuatBan(maNhaXuatBan);
        dauSach.setMaTheLoai(maTheLoai);
        dauSach.setMaViTri(maViTri);
        dauSach.setNgayXuatBan(ngayXuatBan);
        dauSach.setSoTrang(soTrang);
        dauSach.setGia(gia);
        dauSach.setAnhDauSach(anh);
        dauSach.setSoDanhGia(tongSoDanhGia);
        dauSach.setSoNguoiDanhGia(soNguoiDanhGia);

    }

    public void getSuaDauSach(DauSach dauSach) {
        edtTenDauSach.setText(dauSach.getTenDauSach());
        actNhaXuatBan.setText(commonVariables.getTenNhaXuatBan(dauSach.getMaNhaXuatBan()));
        actTacGia.setText(commonVariables.getTenTacGia(dauSach.getMaTacGia()));
        edtNoiDungTomLuoc.setText(dauSach.getNoiDungTomLuoc());
        edtNgayXuatBan.setText(dauSach.getNgayXuatBan());
        edtSotrang.setText(dauSach.getSoTrang() + "");
        edtGia.setText(dauSach.getGia() + "");
        int viTrispTheLoaiDauSach = adapterTheLoai.getPosition(commonVariables.getTenTheLoai(dauSach.getMaTheLoai()));
        spTheLoai.setSelection(viTrispTheLoaiDauSach);
        int viTriViTriDauSach = adapterViTri.getPosition(commonVariables.getTenViTri(dauSach.getMaViTri()));
        spViTri.setSelection(viTriViTriDauSach);

        Picasso.get().load(APIUtils.Base_Url + "images/" + (dauSach.getAnhDauSach()))
                .placeholder(R.mipmap.ic_book)
                .error(R.mipmap.ic_book_error)
                .fit()
                .into(imgAnhDauSachTT);

    }

    public void anhXa() {
        actNhaXuatBan = findViewById(R.id.actThemSuaNhaXuatBanDauSachTT);
        actTacGia = findViewById(R.id.actThemSuaTacGiaDauSachTT);
        edtNoiDungTomLuoc = findViewById(R.id.edtThemSuaTomLuocNoiDungDauSachTT);
        edtNgayXuatBan = findViewById(R.id.edtThemSuaNgayXuatBanTT);
        edtTenDauSach = findViewById(R.id.edtThemSuaTenDauSachTT);
        edtSotrang = findViewById(R.id.edtThemSuaSoTrangTT);
        edtGia = findViewById(R.id.edtThemSuaGiaTT);
        btnChonAnhDauSach = findViewById(R.id.btnThemSuaChonAnhDauSach);
        imgNgayXuatBan = findViewById(R.id.imgNgayXuatBanTT);
        imgAnhDauSachTT = findViewById(R.id.imgThemSuaAnhDauSachTT);
        spTheLoai = findViewById(R.id.spThemSuaTheLoaiDauSachTT);
        spViTri = findViewById(R.id.spThemSuaViTriDauSachTT);
        calenNgayXuatBan = findViewById(R.id.calenNgayXuatBanTT);
        viewNgayXuatBan = findViewById(R.id.viewNgayXuatBanTT);
        rlCalenNgayXuatBan = findViewById(R.id.rlCalenNgayXuatBanTT);
        btnNgayXuatBanHuy = findViewById(R.id.btnCalenCancelTT);
        btnNgayXuatBanOk = findViewById(R.id.btnCalenOkTT);
    }

}
