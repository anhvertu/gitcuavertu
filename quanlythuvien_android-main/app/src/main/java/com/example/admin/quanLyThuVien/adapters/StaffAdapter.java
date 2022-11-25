package com.example.admin.quanLyThuVien.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.models.NhanVien;
import com.squareup.picasso.Picasso;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class StaffAdapter extends ArrayAdapter<NhanVien> implements Filterable {

    private SparseBooleanArray selectedListItemsIds;
    private List multipleSelectionList;
    private CommonVariables commonVariables;
    private List<NhanVien> NhanVienList, mStringFilterList;
    private Context mContext;
    NguoiDungFilter NhanVienFilter;

    public StaffAdapter(@NonNull Context context, @NonNull List<NhanVien> objects) {
        super(context, 0, objects);
        selectedListItemsIds = new SparseBooleanArray();
        this.multipleSelectionList = objects;
        this.NhanVienList = objects;
        mStringFilterList = objects;
    }

    @Override
    public int getCount() {
        return NhanVienList.size();
    }

    @Override
    public NhanVien getItem(int i) {
        return NhanVienList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder {
        TextView txtTen, txtNgaySinh, txtGioiTinh, txtEmail;
        ImageView imgAnhNhanVien, imgMenuNhanVien;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_list, parent, false);
            holder.imgAnhNhanVien = listView.findViewById(R.id.imgAnhUser);
            holder.txtEmail = listView.findViewById(R.id.txtEmailUser);
            holder.txtNgaySinh = listView.findViewById(R.id.txtNgaySinhUser);
            holder.txtGioiTinh = listView.findViewById(R.id.txtGioiTinhUser);
            holder.txtTen = listView.findViewById(R.id.txtTenUser);
            holder.imgMenuNhanVien = listView.findViewById(R.id.menuUser);
            listView.setTag(holder);
        } else {
            holder = (ViewHolder) listView.getTag();
        }

        final NhanVien nguoiDungAdapter = getItem(position);

        holder.txtTen.setText(nguoiDungAdapter.getTen());
        holder.txtEmail.setText(nguoiDungAdapter.getEmail());
        holder.txtGioiTinh.setText(nguoiDungAdapter.getGioiTinh());
        holder.txtNgaySinh.setText(nguoiDungAdapter.getNgaySinh());
        Picasso.get().load(APIUtils.Base_Url + "images/" + (nguoiDungAdapter.getAnhNhanVien()))
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_book_error)
                .fit()
                .into(holder.imgAnhNhanVien);

        holder.imgMenuNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
//                        if (item.getItemId() == R.id.menu_xem_ds_sach) {
//                            bienToanCuc = (BienToanCuc) getContext().getApplicationContext();
//                            bienToanCuc.setMaNguoiDungHienTai(nguoiDungAdapter.getMaNguoiDung());
//                            Intent intent = new Intent(getContext(), ListSachActivity.class);
//                            intent.putExtra("thongTinNguoiDung", nguoiDungAdapter);
//                            getContext().startActivity(intent);
//                            ((Activity) getContext()).finish();
//                        }
                        return false;
                    }
                });
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu_user, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        return listView;
    }

    @Override
    public void remove(NhanVien NhanVien) {
        multipleSelectionList.remove(NhanVien);
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


    public void xepKetQua(List<NhanVien> filterList) {
        commonVariables = (CommonVariables) getContext().getApplicationContext();
        final String xep = commonVariables.getTimTheo();
        Collections.sort(filterList, new Comparator<NhanVien>() {
            @Override
            public int compare(NhanVien d1, NhanVien d2) {
                if (!xep.isEmpty()) {
                    switch (xep) {
                        case "Số lượt xem sách":
                            break;
                        case "Số lượt mượn sách ":
                            break;
                        case "Số điểm rating trung bình":
//                            if (d1.getSoNguoiDanhGia() > 0 && d1.getSoDanhGia() > 0 &&
//                                    d2.getSoNguoiDanhGia() > 0 && d2.getSoDanhGia() > 0) {
//                                return Float.valueOf(d2.getSoDanhGia() / d2.getSoNguoiDanhGia()).compareTo(Float.valueOf(d1.getSoDanhGia() / d1.getSoNguoiDanhGia())); // To compare integer values
//                            }
                            break;
                    }
                }
                return 0;
            }
        });

    }

    @Override
    public Filter getFilter() {
        if (NhanVienFilter == null) {
            NhanVienFilter = new NguoiDungFilter();
        }
        return NhanVienFilter;
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    //kiểm tra với từ khóa nhập ở tìm kiếm
    private class NguoiDungFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

//            String str = constraint.toString().toUpperCase();
//            Log.e("constraint", str);
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<NhanVien> filterList = new ArrayList<NhanVien>();
                for (int i = 0; i < NhanVienList.size(); i++) {
                    if (removeAccent(NhanVienList.get(i).getTen().toUpperCase()).contains(removeAccent(constraint.toString().toUpperCase()))) {
                        NhanVien NhanVien = NhanVienList.get(i);
                        filterList.add(NhanVien);
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
//            bienToanCuc = (BienToanCuc) getContext().getApplicationContext();
//            if (!bienToanCuc.getTimTheo().equals("Không") && bienToanCuc!=null) {
            NhanVienList = (ArrayList<NhanVien>) results.values;
//            xepKetQua(NhanVienList);
//            }
            notifyDataSetChanged();
        }
    }
}
