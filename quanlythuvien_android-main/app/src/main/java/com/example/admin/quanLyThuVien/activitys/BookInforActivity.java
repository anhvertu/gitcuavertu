package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.CalenderHandler;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.adapters.RatingAdapter;
import com.example.admin.quanLyThuVien.models.DanhGiaBinhLuan;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookInforActivity extends AppCompatActivity {

    private TextView txtMaDauSach, txtTenDauSach, txtLoaiSach, txtNhaXuatBan, txtTacGia, txtNoiDungTomLuoc, txtSoLuong, txtViTri, txtNgayXuatBan, txtSoTrang, txtGia, txtDanhGia, txtSoNguoiDanhGia, txtChamDanhGia, txtChuThichDanhGia, txtTatCaDanhGia;
    private RatingBar rbDanhGia, rbVietDanhGia;
    private Button btnGuiDanhGia, btnHuyDanhGia;
    private EditText edtVietDanhGia;
    private ImageView imgAnhSach, imgAnhSachTo;
    private LinearLayout llNoiDungDanhGia, llDanhGiaDauSach;
    private Toolbar toolbar;
    private DauSach dauSach;
    private CommonVariables commonVariables;
    private RecyclerView rvBinhLuan;
    private List<DanhGiaBinhLuan> list;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_infor);
        anhXa();
        commonVariables = (CommonVariables) getApplication();

        initToolbar();
        Intent intent = getIntent();
        dauSach = (DauSach) intent.getParcelableExtra("thongTinDauSach");
        setDauSach(dauSach);

        initThongTinDauSach();
        initBinhLuan();
    }

    private void initBinhLuan() {
        list = new ArrayList<>();
        rvBinhLuan.setLayoutManager(new LinearLayoutManager(this));
        final RatingAdapter adapter = new RatingAdapter(rvBinhLuan, this, list);
        rvBinhLuan.setAdapter(adapter);

        Call call = APIUtils.getData().GetDuLieuBinhLuan(dauSach.getMaDauSach(), list.size());
        call.enqueue(new Callback<List<DanhGiaBinhLuan>>() {
            @Override
            public void onResponse(Call<List<DanhGiaBinhLuan>> call, Response<List<DanhGiaBinhLuan>> response) {
                List<DanhGiaBinhLuan> dGBLArrayList = (ArrayList<DanhGiaBinhLuan>) response.body();
                if (dGBLArrayList != null && dGBLArrayList.size() > 0) {
                    int end = 3;
                    if (dGBLArrayList.size() < 3)
                        end = dGBLArrayList.size();
                    for (int i = 0; i < end; i++) {
                        list.add(new DanhGiaBinhLuan(dGBLArrayList.get(i).getMaDanhGia(), dGBLArrayList.get(i).getTenNguoiDung()
                                , dGBLArrayList.get(i).getMoTa(), dGBLArrayList.get(i).getSoDanhGia(), dGBLArrayList.get(i).getSoLuotThich(), dGBLArrayList.get(i).getNgayDang()
                                , dGBLArrayList.get(i).getAnhNguoiDung(), dGBLArrayList.get(i).getMaDauSach()));
                    }
                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();
                }
            }

            @Override
            public void onFailure(Call<List<DanhGiaBinhLuan>> call, Throwable t) {
            }
        });

    }

    private void initThongTinDauSach() {
        imgAnhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAnhSachTo.setVisibility(View.VISIBLE);
            }
        });
        imgAnhSachTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAnhSachTo.setVisibility(View.GONE);
            }
        });

        llNoiDungDanhGia.setVisibility(View.GONE);
        if (!commonVariables.getQuyenUser().equals("DG")) {
            txtChamDanhGia.setVisibility(View.GONE);
        } else {
            txtChamDanhGia.setVisibility(View.VISIBLE);
        }

        txtChamDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llNoiDungDanhGia.setVisibility(View.VISIBLE);
                txtChamDanhGia.setVisibility(View.GONE);
            }
        });

        btnHuyDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookInforActivity.this, "Đã hủy đánh giá", Toast.LENGTH_SHORT).show();
                Toast.makeText(BookInforActivity.this, "Nếu có thời gian xin hãy để lại đánh giá của bạn", Toast.LENGTH_SHORT).show();
                rbVietDanhGia.setRating(0);
                edtVietDanhGia.setText("");
                txtChamDanhGia.setVisibility(View.VISIBLE);
                llNoiDungDanhGia.setVisibility(View.GONE);
            }
        });

        btnGuiDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbVietDanhGia.getRating() == 0 && edtVietDanhGia.getText().toString().isEmpty()) {
                    Toast.makeText(BookInforActivity.this, "Vui lòng chấm sao, để lại đánh giá của bạn", Toast.LENGTH_SHORT).show();
                } else {
                    dauSach.setSoNguoiDanhGia(dauSach.getSoNguoiDanhGia() + 1);
                    dauSach.setSoDanhGia((int) (dauSach.getSoDanhGia() + rbVietDanhGia.getRating()));
                    guiDuLieuLen();
                    if (dauSach.getSoDanhGia() > 0 && dauSach.getSoNguoiDanhGia() > 0) {
                        if (rbDanhGia.getVisibility() == View.GONE)
                            rbDanhGia.setVisibility(View.VISIBLE);
                        if (txtDanhGia.getVisibility() == View.GONE)
                            txtDanhGia.setVisibility(View.VISIBLE);
                        txtSoNguoiDanhGia.setText("(" + dauSach.getSoNguoiDanhGia() + ")");
                        float tam = dauSach.getSoDanhGia() / dauSach.getSoNguoiDanhGia();
                        rbDanhGia.setRating(tam);
                        DecimalFormat decimalFormat = new DecimalFormat("#.0");
                        txtDanhGia.setText(decimalFormat.format(tam) + "/5");
                    }

                    rbVietDanhGia.setRating(0);
                    edtVietDanhGia.setText("");
                    txtChamDanhGia.setVisibility(View.VISIBLE);
                    llNoiDungDanhGia.setVisibility(View.GONE);
                }
            }
        });

        rbVietDanhGia.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtChuThichDanhGia.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        txtChuThichDanhGia.setText(getResources().getString(R.string.chu_thich_mot_sao));
                        break;
                    case 2:
                        txtChuThichDanhGia.setText(getResources().getString(R.string.chu_thich_hai_sao));
                        break;
                    case 3:
                        txtChuThichDanhGia.setText(getResources().getString(R.string.chu_thich_ba_sao));
                        break;
                    case 4:
                        txtChuThichDanhGia.setText(getResources().getString(R.string.chu_thich_bon_sao));
                        break;
                    case 5:
                        txtChuThichDanhGia.setText(getResources().getString(R.string.chu_thich_nam_sao));
                        break;
                    default:
                        txtChuThichDanhGia.setText("");
                }
            }
        });

        txtTatCaDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookInforActivity.this, RatingActivity.class);
                intent1.putExtra("dauSach", dauSach);
                startActivity(intent1);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_chi_tiet_dau_sach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.chi_tiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookInforActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_infor, menu);
        if (commonVariables.getQuyenUser().equals("DG"))
            menu.removeItem(R.id.menuSua);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSua:
//                truyenSach.duLieuDauSach(dauSach);
                Intent intent = new Intent(BookInforActivity.this, EditBookActivity.class);
                intent.putExtra("thongTinDauSach", dauSach);
                startActivity(intent);
                break;

            case R.id.menuDanhSachSach:
                commonVariables.setMaDauSachHienTai(dauSach.getMaDauSach());
                Intent intent1 = new Intent(BookInforActivity.this, BookActivity.class);
                intent1.putExtra("thongTinDauSach", dauSach);
                startActivity(intent1);
                finish();
                break;
            default:
                String noiDUngChiase = getResources().getString(R.string.ten_sach) + txtTenDauSach.getText().toString().trim() + ", " +
                        getResources().getString(R.string.tac_gia) + txtTacGia.getText().toString().trim() + "\n" + getResources().getString(R.string.tom_luoc_chi_chiet)
                        + txtNoiDungTomLuoc.getText().toString().trim();
//                Log.d("CCCC", noiDUngChiase);
                if (noiDUngChiase != null) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, noiDUngChiase);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void guiDuLieuLen() {
        String maDocGia = "", moTa = "", ngay = "";
        if (commonVariables != null && !commonVariables.getMaUser().isEmpty()) {
            maDocGia = commonVariables.getMaUser();
        }
        moTa = edtVietDanhGia.getText().toString().trim();
        int soSao = (int) rbVietDanhGia.getRating();

        if (moTa.isEmpty())
            moTa = "...";

        Calendar calendar = Calendar.getInstance();
        ngay = new CalenderHandler(this).setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        if (moTa.length() > 0 && soSao >= 0 & ngay.length() > 0 && maDocGia.length() > 0 && dauSach.getMaDauSach().length() > 0) {
            final DataClient dataClient = APIUtils.getData();
            Call<String> callBack = dataClient.InsertDanhGiaBinhLuan(moTa, soSao, 0, ngay, maDocGia, dauSach.getMaDauSach());
            callBack.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String result = response.body();
                    Log.d("DDD", result);
                    if (result.equals("Succsess")) {
                        Toast.makeText(BookInforActivity.this, "Đã gửi đánh giá của bạn", Toast.LENGTH_SHORT).show();
                        Toast.makeText(BookInforActivity.this, "Cảm ơn bạn đã đánh giá", Toast.LENGTH_SHORT).show();
                        initBinhLuan();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("DDD", t.getMessage());
                }
            });

        }
    }


    private void setDauSach(DauSach dauSach) {
        txtMaDauSach.setText(dauSach.getMaDauSach());
        txtTenDauSach.setText(dauSach.getTenDauSach());
        txtLoaiSach.setText(getResources().getString(R.string.the_loai) + "\t\t\t" + commonVariables.getTenTheLoai(dauSach.getMaTheLoai()));
        txtNhaXuatBan.setText(getResources().getString(R.string.nha_xuat_ban) + "\t\t\t" + commonVariables.getTenNhaXuatBan(dauSach.getMaNhaXuatBan()));
        txtTacGia.setText(commonVariables.getTenTacGia(dauSach.getMaTacGia()));
        txtNoiDungTomLuoc.setText(dauSach.getNoiDungTomLuoc());
        txtSoLuong.setText(getResources().getString(R.string.so_luong) + "\t\t\t" + dauSach.getSoLuong() + "\t" + getResources().getString(R.string.quyen));
        txtViTri.setText(getResources().getString(R.string.vi_tri) + "\t\t\t" + commonVariables.getTenViTri(dauSach.getMaViTri()));
        txtNgayXuatBan.setText(getResources().getString(R.string.ngay_xuat_ban) + "\t\t\t" + dauSach.getNgayXuatBan());
        txtSoTrang.setText(getResources().getString(R.string.so_trang) + "\t\t\t" + dauSach.getSoTrang() + "\t\t" + getResources().getString(R.string.trang));
        txtGia.setText(getResources().getString(R.string.gia_sach) + "\t\t\t\t" + dauSach.getGia() + "\t" + getResources().getString(R.string.vnd));

        if (dauSach.getSoDanhGia() > 0 && dauSach.getSoNguoiDanhGia() > 0) {
            txtSoNguoiDanhGia.setText("(" + dauSach.getSoNguoiDanhGia() + ")");
            float tam = dauSach.getSoDanhGia() / dauSach.getSoNguoiDanhGia();
            rbDanhGia.setRating(tam);
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            txtDanhGia.setText(decimalFormat.format(tam) + "/5");
        } else {
            llDanhGiaDauSach.setVisibility(View.GONE);
            rbDanhGia.setVisibility(View.GONE);
            txtDanhGia.setVisibility(View.GONE);
            txtSoNguoiDanhGia.setTextColor(getResources().getColor(R.color.gray));
            txtSoNguoiDanhGia.setText(getResources().getString(R.string.chua_co_danh_gia));
        }

        Picasso.get().load(APIUtils.Base_Url + "images/" + (dauSach.getAnhDauSach()))
                .placeholder(R.mipmap.ic_book)
                .error(R.mipmap.ic_book_error)
                .into(imgAnhSachTo);


        Picasso.get().load(APIUtils.Base_Url + "images/" + (dauSach.getAnhDauSach()))
                .placeholder(R.mipmap.ic_book)
                .error(R.mipmap.ic_book_error)
                .fit()
                .into(imgAnhSach);

    }

    private void anhXa() {
        txtMaDauSach = findViewById(R.id.txtMaDauSachTT);
        txtTenDauSach = findViewById(R.id.txtTenDauSachTT);
        txtLoaiSach = findViewById(R.id.txtTheLoaiDauSachTT);
        txtNhaXuatBan = findViewById(R.id.txtNhaXuatBanDauSachTT);
        txtTacGia = findViewById(R.id.txtTacGiaDauSachTT);
        txtNoiDungTomLuoc = findViewById(R.id.txtTomLuocNoiDungDauSachTT);
        txtSoLuong = findViewById(R.id.txtSoLuongDauSachTT);
        txtViTri = findViewById(R.id.txtViTriDauSachTT);
        txtNgayXuatBan = findViewById(R.id.txtNgayXuatBanDauSachTT);
        txtSoTrang = findViewById(R.id.txtSoTrangDauSachTT);
        txtGia = findViewById(R.id.txtGiaDauSachTT);
        txtDanhGia = findViewById(R.id.txtDanhGiaDauSachTT);
        txtSoNguoiDanhGia = findViewById(R.id.txtSoNguoiDanhGiaDauSachTT);
        txtChamDanhGia = findViewById(R.id.txtChamDanhGiaTT);
        txtChuThichDanhGia = findViewById(R.id.txtChuThichDanhGiaTT);
        txtTatCaDanhGia = findViewById(R.id.txtTatCaDanhGiaTT);
        imgAnhSach = findViewById(R.id.imgAnhDauSachTT);
        imgAnhSachTo = findViewById(R.id.imgAnhDauSachTo);
        rbDanhGia = findViewById(R.id.rbDanhGiaDauSachTT);
        rbVietDanhGia = findViewById(R.id.rbVietDanhGiaDauSachTT);
        llNoiDungDanhGia = findViewById(R.id.llNoiDungDanhGia);
        llDanhGiaDauSach = findViewById(R.id.llDanhGiaDauSach);
        rvBinhLuan = findViewById(R.id.rcBinhLuanDauSach);
        btnGuiDanhGia = findViewById(R.id.btnGuiDanhGiaTT);
        btnHuyDanhGia = findViewById(R.id.btnHuyDanhGiaTT);
        edtVietDanhGia = findViewById(R.id.edtVietDanhGiaDauSachTT);
    }
}
