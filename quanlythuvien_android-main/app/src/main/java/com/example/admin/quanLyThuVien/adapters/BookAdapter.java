package com.example.admin.quanLyThuVien.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.activitys.BookActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class BookAdapter extends ArrayAdapter<DauSach> implements Filterable {

    private SparseBooleanArray selectedListItemsIds;
    private List multipleSelectionList;
    private CommonVariables commonVariables;
    private List<DauSach> dauSachList, mStringFilterList;
    private Context mContext;
    private DauSachFilter dauSachFilter;


    public BookAdapter(@NonNull Context context, @NonNull List<DauSach> objects) {
        super(context, 0, objects);
        selectedListItemsIds = new SparseBooleanArray();
        this.multipleSelectionList = objects;
        this.dauSachList = objects;
        this.mStringFilterList = objects;
    }

    @Override
    public int getCount() {
        return dauSachList.size();
    }

    @Override
    public DauSach getItem(int i) {
        return dauSachList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        RatingBar rbDanhGia;
        TextView txtTenDauSach, txtTacGiaDauSach, txtTheLoaiDauSach, txtSoLuongSach, txtSoNguoiDanhGia, txtDanhGiaDauSach;
        ImageView imgAnhDauSach, imgMenuSach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        commonVariables = (CommonVariables) getContext().getApplicationContext();
        ViewHolder holder = new ViewHolder();
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list, parent, false);
            holder.imgAnhDauSach = listView.findViewById(R.id.imgAnhDauSach);
            holder.txtSoLuongSach = listView.findViewById(R.id.txtSoLuongDauSach);
            holder.txtTacGiaDauSach = listView.findViewById(R.id.txtTacGiaDauSach);
            holder.txtTheLoaiDauSach = listView.findViewById(R.id.txtTheLoaiDauSach);
            holder.txtTenDauSach = listView.findViewById(R.id.txtTenDauSach);
            holder.txtSoNguoiDanhGia = listView.findViewById(R.id.txtSoNguoiDanhGiaDauSach);
            holder.txtDanhGiaDauSach = listView.findViewById(R.id.txtDanhGiaDauSach);
            holder.rbDanhGia = listView.findViewById(R.id.rbDanhGiaDauSach);
            holder.imgMenuSach = listView.findViewById(R.id.menuDauSach);
            listView.setTag(holder);
        } else {
            holder = (ViewHolder) listView.getTag();
        }

        final DauSach dauSachAdapter = getItem(position);

        holder.txtTenDauSach.setText(dauSachAdapter.getTenDauSach());
        holder.txtTheLoaiDauSach.setText(commonVariables.getTenTheLoai(dauSachAdapter.getMaTheLoai()));
        holder.txtTacGiaDauSach.setText(commonVariables.getTenTacGia(dauSachAdapter.getMaTacGia()));
        holder.txtSoLuongSach.setText(dauSachAdapter.getSoLuong() + "\t" + listView.getResources().getString(R.string.quyen));
        holder.txtSoNguoiDanhGia.setText("(" + dauSachAdapter.getSoNguoiDanhGia() + ")");

        if (dauSachAdapter.getSoNguoiDanhGia() > 0 && dauSachAdapter.getSoDanhGia() > 0) {
            float tam = dauSachAdapter.getSoDanhGia() / dauSachAdapter.getSoNguoiDanhGia();
            holder.rbDanhGia.setRating(tam);
            DecimalFormat decimalFormat = new DecimalFormat("#.0");
            holder.txtDanhGiaDauSach.setText(decimalFormat.format(tam) + "/5");
        } else {
            holder.rbDanhGia.setVisibility(View.GONE);
            holder.txtSoNguoiDanhGia.setTextColor(getContext().getResources().getColor(R.color.gray));
            holder.txtSoNguoiDanhGia.setText(getContext().getResources().getString(R.string.chua_co_danh_gia));
            holder.txtDanhGiaDauSach.setVisibility(View.GONE);
        }

        Picasso.get().load(APIUtils.Base_Url + "images/" + (dauSachAdapter.getAnhDauSach()))
                .placeholder(R.mipmap.ic_book)
                .error(R.mipmap.ic_book_error)
                .fit()
                .into(holder.imgAnhDauSach);

        holder.imgMenuSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_xem_ds_sach) {
                            commonVariables.setMaDauSachHienTai(dauSachAdapter.getMaDauSach());
                            Intent intent = new Intent(getContext(), BookActivity.class);
                            intent.putExtra("thongTinDauSach", dauSachAdapter);
                            getContext().startActivity(intent);
                            ((Activity) getContext()).finish();
                        }
                        return false;
                    }
                });
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_book, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        return listView;
    }

    @Override
    public void remove(DauSach dauSach) {
        multipleSelectionList.remove(dauSach);
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !selectedListItemsIds.get(position));
    }

    public void removeSelection() {
        selectedListItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            selectedListItemsIds.put(position, value);
        else
            selectedListItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return selectedListItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return selectedListItemsIds;
    }


    public void xepKetQua(List<DauSach> filterList) {
        commonVariables = (CommonVariables) getContext().getApplicationContext();
        final String xep = commonVariables.getTimTheo();
        Collections.sort(filterList, new Comparator<DauSach>() {
            @Override
            public int compare(DauSach d1, DauSach d2) {
                if (!xep.isEmpty()) {
                    switch (xep) {
                        case "Số lượt xem sách":
                            break;
                        case "Số lượt mượn sách ":
                            break;
                        case "Số điểm rating trung bình":
                            if (d1.getSoNguoiDanhGia() > 0 && d1.getSoDanhGia() > 0 &&
                                    d2.getSoNguoiDanhGia() > 0 && d2.getSoDanhGia() > 0) {
                                return (int) (Float.valueOf(d2.getSoDanhGia() / d2.getSoNguoiDanhGia()).compareTo(Float.valueOf(d1.getSoDanhGia() / d1.getSoNguoiDanhGia()))); // To compare integer values
                            }
                            break;
                    }
                }
                return 0;
            }
        });
    }

    @Override
    public Filter getFilter() {
        if (dauSachFilter == null) {
            dauSachFilter = new DauSachFilter();
        }
        return dauSachFilter;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    //kiểm tra với từ khóa nhập ở tìm kiếm
    private class DauSachFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String str = constraint.toString().toUpperCase();
            str = removeAccent(str);
            Log.e("constraint", str);
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<DauSach> filterList = new ArrayList<DauSach>();
                for (int i = 0; i < dauSachList.size(); i++) {
                    if (
                            removeAccent(dauSachList.get(i).getTenDauSach().toUpperCase()).contains(removeAccent(constraint.toString().toUpperCase()))
//                            || (bienToanCuc.getTenTacGia(dauSachList.get(i).getMaTacGia()).toUpperCase()).contains(constraint.toString().toUpperCase())
                                    || removeAccent(commonVariables.getTenTacGia(dauSachList.get(i).getMaTacGia()).toUpperCase()).contains(removeAccent(constraint.toString().toUpperCase()))
                    ) {
                        Log.d("FFFFF", dauSachList.get(i).getTenDauSach().toUpperCase());
                        Log.d("FFFFF", constraint.toString().toUpperCase());
                        DauSach dauSach = dauSachList.get(i);
                        filterList.add(dauSach);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            commonVariables = (CommonVariables) getContext().getApplicationContext();
            dauSachList = (ArrayList<DauSach>) results.values;
            if (!commonVariables.getTimTheo().equals("Không") && commonVariables != null) {
                xepKetQua(dauSachList);
            }
            notifyDataSetChanged();
        }
    }
}
