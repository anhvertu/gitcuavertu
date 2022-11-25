package com.example.admin.quanLyThuVien.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.interfaces.ILoadmore;
import com.example.admin.quanLyThuVien.models.DanhGiaBinhLuan;
import com.squareup.picasso.Picasso;

import java.util.List;

class RatingViewHolder extends RecyclerView.ViewHolder {
    public TextView ten, ngay, soLuotThich, moTa;
    public RatingBar rbDanhGia;
    public ImageView anhNguoiDung, iconthich;

    public RatingViewHolder(View itemView) {
        super(itemView);
        ten = itemView.findViewById(R.id.txtTenNguoiDungDanhGia);
        ngay = itemView.findViewById(R.id.txtNgayDang);
        soLuotThich = itemView.findViewById(R.id.txtSoNguoiThich);
        moTa = itemView.findViewById(R.id.txtNguoiDungBinhLuan);
        rbDanhGia = itemView.findViewById(R.id.rbNguoiDungDanhGia);
        anhNguoiDung = itemView.findViewById(R.id.imgAnhNguoiDungDanhGia);
        iconthich = itemView.findViewById(R.id.imgThichBinhLuan);
        soLuotThich.setVisibility(View.GONE);
    }

    public RatingViewHolder(View itemView, TextView ten, TextView ngay, TextView soThich, TextView binhluan, RatingBar rbDanhGia, ImageView anhNguoiDung, ImageView iconthich) {
        super(itemView);
        this.ten = ten;
        this.ngay = ngay;
        this.soLuotThich = soThich;
        this.moTa = binhluan;
        this.rbDanhGia = rbDanhGia;
        this.anhNguoiDung = anhNguoiDung;
        this.iconthich = iconthich;
    }
}

public class RatingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DanhGiaBinhLuan> danhGiaBinhLuanList;
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    ILoadmore loadMore;
    boolean isLoading;
    Activity activity;
    int visibleThreshold = 6;
    int lastVisibleItem, totalItemCount;

    public RatingAdapter(RecyclerView recyclerView, final Activity activity, List<DanhGiaBinhLuan> danhGiaBinhLuanList) {
        this.danhGiaBinhLuanList = danhGiaBinhLuanList;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && (totalItemCount) <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return danhGiaBinhLuanList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadmore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.rating_list, parent, false);

            vh = new RatingViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loading, parent, false);

            vh = new LoadingViewHolder(v);
            ((LoadingViewHolder) vh).progressBar.setVisibility(View.VISIBLE);

        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RatingViewHolder) {
            DanhGiaBinhLuan binhLuan = danhGiaBinhLuanList.get(position);

            ((RatingViewHolder) holder).ten.setText(binhLuan.getTenNguoiDung());
            ((RatingViewHolder) holder).ngay.setText(binhLuan.getNgayDang());

            ((RatingViewHolder) holder).soLuotThich.setText(binhLuan.getSoLuotThich() + "");

            ((RatingViewHolder) holder).moTa.setText(binhLuan.getMoTa());
            Picasso.get().load(APIUtils.Base_Url + "images/" + (binhLuan.getAnhNguoiDung()))
                    .fit()
                    .centerCrop()
                    .into(((RatingViewHolder) holder).anhNguoiDung);

            ((RatingViewHolder) holder).iconthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            ((RatingViewHolder) holder).rbDanhGia.setRating(binhLuan.getSoDanhGia());
        } else if (holder instanceof LoadingViewHolder) {
            if (!isLoading)
                ((LoadingViewHolder) holder).progressBar.setVisibility(View.VISIBLE);
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return danhGiaBinhLuanList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }
}