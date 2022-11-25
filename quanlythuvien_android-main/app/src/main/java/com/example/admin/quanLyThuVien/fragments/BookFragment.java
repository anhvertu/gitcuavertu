package com.example.admin.quanLyThuVien.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.activitys.HomeActivity;
import com.example.admin.quanLyThuVien.activitys.BookInforActivity;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.adapters.BookAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment {
    private ArrayList<DauSach> dsDauSach;
    private BookAdapter adapter;
    private View rootView;
    private CommonVariables commonVariables;
    private ListView listView;

    //tìm kiếm
    public void timKiemDS(String query) {
        Log.e("QueryFragment", query);
        adapter.getFilter().filter(query);
    }

    public BookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sach_list, container, false);
        commonVariables = (CommonVariables) getActivity().getApplication();
        dsDauSach = new ArrayList<DauSach>();
        getDanhSachDauSach();
        return rootView;
    }

    public void getDanhSachDauSach() {
        DataClient dataClient = APIUtils.getData();
        Call<List<DauSach>> listCall = dataClient.GetListDauSach("key");
        listCall.enqueue(new Callback<List<DauSach>>() {
            @Override
            public void onResponse(Call<List<DauSach>> call, Response<List<DauSach>> response) {
                ArrayList<DauSach> dauSachArrayList = (ArrayList<DauSach>) response.body();
                if (dauSachArrayList != null && dauSachArrayList.size() > 0) {
                    themDauSach(dauSachArrayList);
//                    for (int i = 0; i < dauSachArrayList.size(); i++) {
//                        dsDauSach.add(new DauSach(dauSachArrayList.get(i).getMaDauSach(), dauSachArrayList.get(i).getTenSach()
//                                , dauSachArrayList.get(i).getLoaiSach(), dauSachArrayList.get(i).getNhaXuatBan()
//                                , dauSachArrayList.get(i).getTacGia(), dauSachArrayList.get(i).getNoiDungTomLuoc()
//                                , dauSachArrayList.get(i).getSoLuong(), dauSachArrayList.get(i).getAnhDauSach()));
//                        Toast.makeText(getActivity(), dsDauSach.get(0).getTenSach(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<List<DauSach>> call, Throwable t) {
//                Log.d("DDDD", "không có dữ liệu");
            }
        });
    }

    public void themDauSach(ArrayList<DauSach> dauSachArrayList) {
        commonVariables.setMaDauSachCuoi(dauSachArrayList.get(dauSachArrayList.size() - 1).getMaDauSach());
        for (int i = 0; i < dauSachArrayList.size(); i++) {
            final DauSach dauSach = new DauSach();
            dauSach.setMaDauSach(dauSachArrayList.get(i).getMaDauSach());
            dauSach.setTenDauSach(dauSachArrayList.get(i).getTenDauSach());
            dauSach.setMaTheLoai(dauSachArrayList.get(i).getMaTheLoai());
            dauSach.setMaNhaXuatBan(dauSachArrayList.get(i).getMaNhaXuatBan());
            dauSach.setMaTacGia(dauSachArrayList.get(i).getMaTacGia());
            dauSach.setNoiDungTomLuoc(dauSachArrayList.get(i).getNoiDungTomLuoc());
            dauSach.setSoLuong(dauSachArrayList.get(i).getSoLuong());
            dauSach.setMaViTri(dauSachArrayList.get(i).getMaViTri());
            dauSach.setNgayXuatBan(dauSachArrayList.get(i).getNgayXuatBan());
            dauSach.setSoTrang(dauSachArrayList.get(i).getSoTrang());
            dauSach.setGia(dauSachArrayList.get(i).getGia());
            dauSach.setAnhDauSach(dauSachArrayList.get(i).getAnhDauSach());
            dauSach.setSoDanhGia(dauSachArrayList.get(i).getSoDanhGia());
            dauSach.setSoNguoiDanhGia(dauSachArrayList.get(i).getSoNguoiDanhGia());
            dsDauSach.add(dauSach);

            if (dsDauSach != null && dsDauSach.size() > 0) {
                adapter = new BookAdapter(getActivity(), dsDauSach);
                listView = (ListView) rootView.findViewById(R.id.list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(getActivity(), BookInforActivity.class);
                        intent.putExtra("thongTinDauSach", dsDauSach.get(position));
                        startActivity(intent);
                    }
                });

                listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);
                listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        final int checkedCount = listView.getCheckedItemCount();
                        // Set the CAB title according to total checked items
                        mode.setTitle(checkedCount + getActivity().getResources().getString(R.string.da_chon));
                        // Calls toggleSelection method from ListViewAdapter Class
                        adapter.toggleSelection(position);
                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_chon_het:
                                return false;
                            case R.id.menu_xoa:
                                // call getSelectedIds method from customListViewAdapter
                                // Captures all selected ids with a loop
                                xacNhanXoa(mode);
                                return true;
                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        adapter.removeSelection();
                    }
                });
            }
        }
    }


    private void xacNhanXoa(final ActionMode mode) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getResources().getString(R.string.xoa_dau_sach));
        dialogBuilder.setIcon(R.mipmap.ic_delete_xanh);
        dialogBuilder.setMessage(getResources().getString(R.string.ban_co_muon_xoa_dau_sach));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.co), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoaDauSach(mode);
            }
        });

        dialogBuilder.setNegativeButton(getResources().getString(R.string.khong), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mode.finish();
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    private void xoaDauSach(ActionMode mode) {
        DauSach dauSachChon;
        String hinhAnh = "";
        SparseBooleanArray selected = adapter.getSelectedIds();
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                dauSachChon = adapter.getItem(selected.keyAt(i));
                if (dauSachChon.getSoLuong() > 0) {
                    Toast.makeText(getActivity(), dauSachChon.getTenDauSach() + getResources().getString(R.string.con_sach), Toast.LENGTH_SHORT).show();
                    return;
                }
                hinhAnh = dauSachChon.getAnhDauSach();
                hinhAnh = hinhAnh.substring(hinhAnh.lastIndexOf("/"));
                final String tenDauSach = "\t" + dauSachChon.getTenDauSach() + "\t";

                DataClient dataClient = APIUtils.getData();
                Call<String> callbackXoa = dataClient.DeleteDauSach(dauSachChon.getMaDauSach().trim(), hinhAnh.trim());
                callbackXoa.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Ok")) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.xoa) + tenDauSach + getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CCCC", t.getMessage());
                        Toast.makeText(getActivity(), getResources().getString(R.string.xoa) + tenDauSach + getResources().getString(R.string.that_bai), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            mode.finish();
        }
    }
}
