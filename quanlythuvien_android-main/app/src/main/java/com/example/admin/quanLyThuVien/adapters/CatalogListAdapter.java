package com.example.admin.quanLyThuVien.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.admin.quanLyThuVien.R;

import java.util.Collections;
import java.util.List;

public class CatalogListAdapter extends RecyclerView.Adapter<CatalogListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> arrayList;

    public CatalogListAdapter(List<String> arrayList) {
        this.arrayList = arrayList;
    }

    public void addData(List<String> data) {
        if (data == null) {
            return;
        }
        arrayList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.catalog_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void swipe(int position, int direction) {
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTen;
        private ImageButton imgSua;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenDeMuc);
            imgSua = itemView.findViewById(R.id.imgSuaDanhSachDeMuc);
            imgSua.setVisibility(View.VISIBLE);
        }

        private void bindData(String data) {
            if (data == null) {
                return;
            }
            txtTen.setText(data);
        }
    }
}


