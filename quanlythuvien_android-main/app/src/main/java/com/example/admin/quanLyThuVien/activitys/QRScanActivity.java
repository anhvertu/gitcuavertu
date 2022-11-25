package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.models.DocGia;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private BarcodeReader barcodeReader;
    private CommonVariables commonVariables;
    private String loai = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        Intent intent = getIntent();
        loai = intent.getStringExtra("loai");
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }

    @Override
    public void onScanned(final Barcode barcode) {
        //tạo tiếng bip
        barcodeReader.playBeep();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (barcode.displayValue.length() > 0) {
                    barcodeReader.pauseScanning();
                    Boolean kt =null;
//                    Toast.makeText(QuetMaActivity.this, barcode.displayValue.toString(), Toast.LENGTH_SHORT).show();

//                    getDuLieuDauSach(barcode.displayValue.toString());
//
                    switch (loai) {
                        case "ds":
                            kt = getDuLieuDauSach(barcode.displayValue);
                            break;
                        case "dg":
                            kt = getDuLieuDocGia(barcode.displayValue);
                            break;
                    }

                    if (kt!=null && kt == false) {
                        Toast.makeText(QRScanActivity.this, getResources().getString(R.string.khong_tim_thay)+" " + barcode.displayValue, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

    }

    private Boolean getDuLieuDocGia(String displayValue) {
        DataClient dataClient = APIUtils.getData();
        Call<List<DocGia>> call = dataClient.GetDocGiaUser(displayValue);
        call.enqueue(new Callback<List<DocGia>>() {
            @Override
            public void onResponse(Call<List<DocGia>> call, Response<List<DocGia>> response) {
                ArrayList<DocGia> docGiaArrayList = (ArrayList<DocGia>) response.body();
                if (docGiaArrayList != null && docGiaArrayList.size() > 0) {
                    DocGia docGia = new DocGia(docGiaArrayList.get(0).getMaDocGia(), docGiaArrayList.get(0).getTen(), docGiaArrayList.get(0).getEmail()
                            , docGiaArrayList.get(0).getNgaySinh(), docGiaArrayList.get(0).getGioiTinh(), docGiaArrayList.get(0).getSoDienThoai()
                            , docGiaArrayList.get(0).getDiaChi(), docGiaArrayList.get(0).getNgayLamThe(), docGiaArrayList.get(0).getAnhDocGia());
                    Intent intent = new Intent(QRScanActivity.this, UserInfoActivity.class);
                    intent.putExtra("thongTinDocGia", docGia);
//                    intent.putExtra("ThongTinTaiKhoan", taiKhoan);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<DocGia>> call, Throwable t) {

            }
        });
        return false;
    }

    private boolean getDuLieuDauSach(final String ma) {
        if (ma.length() > 0) {
            DataClient dataClient = APIUtils.getData();
            Call<List<DauSach>> listCall = dataClient.GetDuLieuDauSach(ma);
            listCall.enqueue(new Callback<List<DauSach>>() {
                @Override
                public void onResponse(Call<List<DauSach>> call, Response<List<DauSach>> response) {
                    ArrayList<DauSach> arrayList = (ArrayList<DauSach>) response.body();
                    if (arrayList.size() > 0 && arrayList.get(0).getMaDauSach().equals(ma)) {
                        if (arrayList.get(0) != null) {
                            DauSach dauSach = new DauSach();
                            dauSach.setMaDauSach(arrayList.get(0).getMaDauSach());
                            dauSach.setTenDauSach(arrayList.get(0).getTenDauSach());
                            dauSach.setMaTheLoai(arrayList.get(0).getMaTheLoai());
                            dauSach.setMaNhaXuatBan(arrayList.get(0).getMaNhaXuatBan());
                            dauSach.setMaTacGia(arrayList.get(0).getMaTacGia());
                            dauSach.setNoiDungTomLuoc(arrayList.get(0).getNoiDungTomLuoc());
                            dauSach.setSoLuong(arrayList.get(0).getSoLuong());
                            dauSach.setMaViTri(arrayList.get(0).getMaViTri());
                            dauSach.setNgayXuatBan(arrayList.get(0).getNgayXuatBan());
                            dauSach.setSoTrang(arrayList.get(0).getSoTrang());
                            dauSach.setGia(arrayList.get(0).getGia());
                            dauSach.setAnhDauSach(arrayList.get(0).getAnhDauSach());
                            dauSach.setSoDanhGia(arrayList.get(0).getSoDanhGia());
                            dauSach.setSoNguoiDanhGia(arrayList.get(0).getSoNguoiDanhGia());

                            Toast.makeText(QRScanActivity.this, getResources().getString(R.string.tim_thay_ma) + dauSach.getMaDauSach(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(QRScanActivity.this, BookInforActivity.class);
                            intent.putExtra("thongTinDauSach", dauSach);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DauSach>> call, Throwable t) {
                }
            });
        }
        return false;

    }


    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        barcodeReader.pauseScanning();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeReader.resumeScanning();
    }
}
