package com.example.admin.quanLyThuVien.activitys;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.adapters.RatingAdapter;
import com.example.admin.quanLyThuVien.interfaces.ILoadmore;
import com.example.admin.quanLyThuVien.models.DanhGiaBinhLuan;
import com.example.admin.quanLyThuVien.models.DauSach;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RatingAdapter adapter;
    private List<DanhGiaBinhLuan> list;
    public DauSach dauSach;
    private Toolbar toolbar;
    private boolean dahetDuLieu;
    Call<List<DanhGiaBinhLuan>> call;
    List<DanhGiaBinhLuan> dGBLArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        final Intent intent = getIntent();
        dauSach = intent.getParcelableExtra("dauSach");

        toolbar = findViewById(R.id.toolbar_danh_sach);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.danh_gia_binh_luan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.rcDanhSach);

        list = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RatingAdapter(mRecyclerView, this, list);
        mRecyclerView.setAdapter(adapter);

        getCommentList();
//        adapter.notifyDataSetChanged();
        dahetDuLieu = false;

        //Set Load more event
        adapter.setLoadMore(new ILoadmore() {
            @Override
            public void onLoadMore() {
//                    Toast.makeText(DanhGiaBinhLuanActivity.this, list.size(), Toast.LENGTH_SHORT).show();
                if (list.size() <= 200) // Change max size
                {
                    list.add(null);
                    adapter.notifyItemInserted(list.size() - 1);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 5; i < list.size(); i++) {
                                if (list.get(i) == null) {
//                                        Toast.makeText(DanhGiaBinhLuanActivity.this, "nul ở "+i, Toast.LENGTH_SHORT).show();
                                    list.remove(i);
                                    adapter.notifyItemRemoved(i);
                                }
                            }

                            if (dahetDuLieu) {
                                Toast.makeText(RatingActivity.this, "Đã tải hết dữ liệu!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            getCommentList();
                            if (!dahetDuLieu) {
                                adapter.notifyDataSetChanged();
                                adapter.setLoaded();
                            }
                        }
                    }, 2000); // Time to load
                }
            }
        });

    }

    private void getCommentList() {
//        Toast.makeText(this, list.size() + "", Toast.LENGTH_SHORT).show();
        call = APIUtils.getData().GetDuLieuBinhLuan(dauSach.getMaDauSach(), list.size());
        call.enqueue(new Callback<List<DanhGiaBinhLuan>>() {
            @Override
            public void onResponse(Call<List<DanhGiaBinhLuan>> call, Response<List<DanhGiaBinhLuan>> response) {
                dGBLArrayList = (ArrayList<DanhGiaBinhLuan>) response.body();
                if (dGBLArrayList != null && dGBLArrayList.size() > 0) {
                    addComment(dGBLArrayList);
                } else {
                    dahetDuLieu = true;
//                    Toast.makeText(DanhGiaBinhLuanActivity.this, "Đã tải hết dữ liệu1!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<DanhGiaBinhLuan>> call, Throwable t) {
                Toast.makeText(RatingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addComment(List<DanhGiaBinhLuan> dGBLArrayList) {
        for (int i = 0; i < dGBLArrayList.size(); i++) {
            list.add(new DanhGiaBinhLuan(dGBLArrayList.get(i).getMaDanhGia(), dGBLArrayList.get(i).getTenNguoiDung()
                    , dGBLArrayList.get(i).getMoTa(), dGBLArrayList.get(i).getSoDanhGia(), dGBLArrayList.get(i).getSoLuotThich(), dGBLArrayList.get(i).getNgayDang()
                    , dGBLArrayList.get(i).getAnhNguoiDung(), dGBLArrayList.get(i).getMaDauSach()));
        }
    }
}
