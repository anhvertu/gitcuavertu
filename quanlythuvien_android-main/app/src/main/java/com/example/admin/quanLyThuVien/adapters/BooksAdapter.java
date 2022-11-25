package com.example.admin.quanLyThuVien.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.models.Sach;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class BooksAdapter extends ArrayAdapter<Sach> {

    private SparseBooleanArray selectedListItemsIds;
    private List multipleSelectionList;
    private String[] arrTrangThai = {"Tốt", "Bị hư", "Bị mất", "Thanh lý", "Không được phép mượn"};
    private CommonVariables commonVariables;

    public BooksAdapter(@NonNull Context context, @NonNull List<Sach> objects) {
        super(context, 0, objects);
        selectedListItemsIds = new SparseBooleanArray();
        this.multipleSelectionList = objects;
    }

    private class ViewHolder {
        TextView txtTenSach, txtMaSach, txtTinhTrangSach, txtChoMuon;
        ImageView imgMenuSach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        commonVariables = (CommonVariables) getContext().getApplicationContext();
        ViewHolder holder = new ViewHolder();
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_infor, parent, false);
            holder.txtTenSach = listView.findViewById(R.id.txtTenSach);
            holder.txtMaSach = listView.findViewById(R.id.txtMaSach);
            holder.txtTinhTrangSach = listView.findViewById(R.id.txtTinhTrangSach);
            holder.txtChoMuon = listView.findViewById(R.id.txtChoMuonSach);
            holder.imgMenuSach = listView.findViewById(R.id.menuSach);
            listView.setTag(holder);
        } else {
            holder = (ViewHolder) listView.getTag();
        }

        final Sach sachAdapter = getItem(position);

        holder.txtTenSach.setText(sachAdapter.getTenSach());
        holder.txtMaSach.setText(sachAdapter.getMaSach());
        holder.txtTinhTrangSach.setText(sachAdapter.getTinhTrangSach());
        holder.txtChoMuon.setText(sachAdapter.getChoMuon());
        if(commonVariables.getQuyenUser().equals("DG"))
            holder.imgMenuSach.setVisibility(View.GONE);

        holder.imgMenuSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_sua_sach) ;
                        suaTrangThai(sachAdapter);
                        return false;
                    }
                });
                popupMenu.getMenuInflater().inflate(R.menu.menu_edit_book, popupMenu.getMenu());
                popupMenu.show();
            }
        });

        return listView;
    }

    private void suaTrangThai(final Sach sach) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.add_book_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edtThemSach = dialogView.findViewById(R.id.edtThemSuaSach);
        edtThemSach.setVisibility(View.GONE);

        final Spinner spSuaTinhTrang = dialogView.findViewById(R.id.spSuaTinhTrangSach);
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrTrangThai);
        spSuaTinhTrang.setAdapter(adp);

        int viTriTinhTrangSach = adp.getPosition(sach.getTinhTrangSach());
        spSuaTinhTrang.setSelection(viTriTinhTrangSach);

        dialogBuilder.setTitle(getContext().getResources().getString(R.string.sua_trang_thai));
        dialogBuilder.setMessage(getContext().getResources().getString(R.string.chon_trang_thai));
        dialogBuilder.setPositiveButton(getContext().getResources().getString(R.string.sua), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String trangThai = spSuaTinhTrang.getSelectedItem().toString().trim();

                if (!trangThai.isEmpty()) {
                    DataClient dataClient = APIUtils.getData();
                    retrofit2.Call<String> call = dataClient.UpdateSach(sach.getMaSach(), sach.getTenSach(), trangThai, sach.getChoMuon(), sach.getMaDauSach());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                            String result = response.body();
                            if (result.equals("Succsess1")) {
                                Toast.makeText(getContext(), getContext().getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                                ((Activity) getContext()).finish();
                                getContext().startActivity(((Activity) getContext()).getIntent());
//                                Intent intent = new Intent(getContext(), ListSachActivity.class);
//                                getContext().startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(retrofit2.Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });

        dialogBuilder.setNegativeButton(getContext().getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }


    @Override
    public void remove(Sach sach) {
        multipleSelectionList.remove(sach);
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
}
