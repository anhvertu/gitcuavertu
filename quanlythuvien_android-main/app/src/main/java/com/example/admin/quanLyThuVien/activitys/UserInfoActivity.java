package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.models.DocGia;
import com.example.admin.quanLyThuVien.models.NhanVien;
import com.example.admin.quanLyThuVien.models.TaiKhoan;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    private TextView txtMa, txtTen, txtchucVu, txtTaiKhoan, txtNgaySinh, txtGioiTinh, txtEmail, txtSoDienThoai, txtDiaChi, txtNgayLam;
    private String ma, ten, chucVu, ngaySinh, gioiTinh, email, soDienThoai, diaChi, ngayLam, anh;
    private ImageView imgAnh, imgAnhTo;
    private Toolbar toolbar;
    private DocGia docGia;
    private NhanVien nhanVien;
    private CommonVariables commonVariables;
    private TaiKhoan taiKhoan;

    public UserInfoActivity() {
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        anhXa();

        commonVariables = (CommonVariables) getApplication();

        toolbar = findViewById(R.id.toolbar_chi_tiet_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        docGia = (DocGia) intent.getParcelableExtra("thongTinDocGia");
        nhanVien = intent.getParcelableExtra("thongTinNhanVien");

        if (docGia != null || nhanVien != null) {
            getSupportActionBar().setTitle(R.string.thong_tin_chi_tiet);
        } else if (docGia == null && nhanVien == null) {
            getSupportActionBar().setTitle(R.string.thong_tin_cua_toi);
            if (commonVariables.getQuyenUser().equals("DG")) {
                if (docGia == null) {
//                getSupportActionBar().setTitle(R.string.thong_tin_cua_toi);
                    docGia = intent.getParcelableExtra("ThongTinDocGiaUser");
                }
            } else {
                if (nhanVien == null) {
                    nhanVien = intent.getParcelableExtra("ThongTinNhanVienUser");
//                getSupportActionBar().setTitle(R.string.thong_tin_cua_toi);
                }
            }
        }

        if (docGia == null) {
            txtchucVu.setVisibility(View.VISIBLE);
            ma = nhanVien.getMaNhanVien();
            ten = nhanVien.getTen();
            chucVu = nhanVien.getChucVu();
            ngaySinh = nhanVien.getNgaySinh();
            gioiTinh = nhanVien.getGioiTinh();
            email = nhanVien.getEmail();
            soDienThoai = nhanVien.getSoDienThoai();
            diaChi = nhanVien.getDiaChi();
            ngayLam = nhanVien.getNgayVaoLam();
            anh = nhanVien.getAnhNhanVien();
            getTaiKhoan(nhanVien.getMaNhanVien());
        } else if (nhanVien == null) {
            ma = docGia.getMaDocGia();
            ten = docGia.getTen();
            chucVu = "";
            ngaySinh = docGia.getNgaySinh();
            gioiTinh = docGia.getGioiTinh();
            email = docGia.getEmail();
            soDienThoai = docGia.getSoDienThoai();
            diaChi = docGia.getDiaChi();
            ngayLam = docGia.getNgayLamThe();
            anh = docGia.getAnhDocGia();
            getTaiKhoan(docGia.getMaDocGia());
        }

        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAnhTo.setVisibility(View.VISIBLE);
            }
        });
        imgAnhTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAnhTo.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_infor, menu);
        menu.removeItem(R.id.menuChiaSe);
        if (commonVariables.getQuyenUser().equals("DG"))
            menu.removeItem(R.id.menuSua);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!commonVariables.getQuyenUser().equals("DG"))
            switch (item.getItemId()) {
                case R.id.menuSua:
                    Intent intent = new Intent(UserInfoActivity.this, EditUserInfoActivity.class);
                    if (docGia != null) {
                        intent.putExtra("thongTinDocGia", docGia);
                    } else if (nhanVien != null) {
                        intent.putExtra("thongTinNhanVien", nhanVien);
                    }
                    intent.putExtra("ThongTinTaiKhoan", taiKhoan);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        return super.onOptionsItemSelected(item);
    }

    public void getTaiKhoan(String ma) {
        DataClient dataClient = APIUtils.getData();
        Call<List<TaiKhoan>> call = dataClient.GetDuLieuTaiKhoan(ma);
        call.enqueue(new Callback<List<TaiKhoan>>() {
            @Override
            public void onResponse(Call<List<TaiKhoan>> call, Response<List<TaiKhoan>> response) {
                ArrayList<TaiKhoan> taiKhoanArrayList = (ArrayList<TaiKhoan>) response.body();
//                Toast.makeText(getContext(), taiKhoanArrayList.size()+"", Toast.LENGTH_SHORT).show();
                if (taiKhoanArrayList != null && taiKhoanArrayList.size() > 0) {
                    taiKhoan = new TaiKhoan(taiKhoanArrayList.get(0).getTaiKhoan(), taiKhoanArrayList.get(0).getMatKhau(), taiKhoanArrayList.get(0).getQuyenHan(), taiKhoanArrayList.get(0).getMaNguoiDung());
                    setUser(taiKhoan);
                } else {
                    setUser(taiKhoan);
                }
            }

            @Override
            public void onFailure(Call<List<TaiKhoan>> call, Throwable t) {
                Toast.makeText(UserInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUser(TaiKhoan taiKhoan) {
        txtMa.setText(ma);
        txtTen.setText(ten);
        txtchucVu.setText(chucVu);
        txtNgaySinh.setText(ngaySinh);

        if (taiKhoan != null) {
            txtTaiKhoan.setText(getResources().getString(R.string.tai_khoan) + "\t\t\t" + taiKhoan.getTaiKhoan());
        } else {
            txtTaiKhoan.setText(getResources().getString(R.string.tai_khoan));
        }

        txtEmail.setText(getResources().getString(R.string.email) + "\t\t\t" + email);
        txtGioiTinh.setText(getResources().getString(R.string.gioi_tinh) + "\t\t\t" + gioiTinh);
        txtSoDienThoai.setText(getResources().getString(R.string.dien_thoai) + "\t\t\t" + soDienThoai);
        txtDiaChi.setText(getResources().getString(R.string.dia_chi) + "\t\t\t" + diaChi);
        if (chucVu.equals("")) {
            txtNgayLam.setText(getResources().getString(R.string.ngay_lam_the) + "\t\t\t" + ngayLam);
        } else {
            txtNgayLam.setText(getResources().getString(R.string.ngay_vao_lam) + "\t\t\t" + ngayLam);
        }

        Picasso.get().load(APIUtils.Base_Url + "images/" + (anh))
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_book_error)
                .resize(240, 320)
                .centerCrop()
                .into(imgAnh);
        Picasso.get().load(APIUtils.Base_Url + "images/" + (anh))
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_book_error)
                .into(imgAnhTo);
    }

    private void anhXa() {
        txtMa = findViewById(R.id.txtMaUserTT);
        txtTen = findViewById(R.id.txtTenUserTT);
        txtchucVu = findViewById(R.id.txtChucVuUserTT);
        txtNgaySinh = findViewById(R.id.txtNgaySinhUserTT);
        txtTaiKhoan = findViewById(R.id.txtTaiKhoanUserTT);
        txtEmail = findViewById(R.id.txtEmailUserTT);
//        txtMatKhau = findViewById(R.id.txtMatKhauNguoiDungTT);
        txtGioiTinh = findViewById(R.id.txtGioiTinhUserTT);
        txtSoDienThoai = findViewById(R.id.txtSoDienThoaiUserTT);
        txtDiaChi = findViewById(R.id.txtDiaChiUserTT);
        txtNgayLam = findViewById(R.id.txtNgayLamUserTT);
        imgAnh = findViewById(R.id.imgAnhUserTT);
        imgAnhTo = findViewById(R.id.imgAnhUserToTT);
    }
}
