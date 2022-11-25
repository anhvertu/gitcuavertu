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

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.activitys.UserInfoActivity;
import com.example.admin.quanLyThuVien.adapters.StaffAdapter;
import com.example.admin.quanLyThuVien.models.NhanVien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffFragment extends Fragment {

    private ArrayList<NhanVien> dsNhanVien;
    private StaffAdapter adapter;
    private View rootView;
    private CommonVariables commonVariables;
    private ListView listView;
    private DataClient dataClient;

    //tìm kiếm
    public void timKiem(String query) {
        Log.e("QueryFragment", query);
        adapter.getFilter().filter(query);
    }

    public StaffFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sach_list, container, false);
        commonVariables = (CommonVariables) getActivity().getApplication();
        dsNhanVien = new ArrayList<NhanVien>();
        getDanhSachNguoiDung();
        return rootView;
    }

    public void getDanhSachNguoiDung() {
        dataClient = APIUtils.getData();
        Call<List<NhanVien>> listCall = dataClient.GetListNhanVien("key");
        listCall.enqueue(new Callback<List<NhanVien>>() {
            @Override
            public void onResponse(Call<List<NhanVien>> call, Response<List<NhanVien>> response) {
                ArrayList<NhanVien> nguoiDungArrayList = (ArrayList<NhanVien>) response.body();
                if (nguoiDungArrayList != null && nguoiDungArrayList.size() > 0) {
                    themNhanVien(nguoiDungArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<NhanVien>> call, Throwable t) {
                Log.d("XXXXX", t.getMessage());
            }
        });
    }

    public void themNhanVien(ArrayList<NhanVien> NhanVienArrayList) {
        commonVariables.setMaNhanVienCuoi(NhanVienArrayList.get(NhanVienArrayList.size() - 1).getMaNhanVien());
        for (int i = 0; i < NhanVienArrayList.size(); i++) {
            NhanVien NhanVien = new NhanVien(NhanVienArrayList.get(i).getMaNhanVien(), NhanVienArrayList.get(i).getTen(), NhanVienArrayList.get(i).getChucVu(), NhanVienArrayList.get(i).getEmail()
                    , NhanVienArrayList.get(i).getNgaySinh(), NhanVienArrayList.get(i).getGioiTinh(), NhanVienArrayList.get(i).getSoDienThoai()
                    , NhanVienArrayList.get(i).getDiaChi(), NhanVienArrayList.get(i).getNgayVaoLam(), NhanVienArrayList.get(i).getAnhNhanVien());
            dsNhanVien.add(NhanVien);
//            if (!bienToanCuc.getQuyenUser().equals("DG")) {
//                if (NhanVien.getMaNhanVien().equals(bienToanCuc.getMaUser()))
//                    bienToanCuc.setNhanVienUser(NhanVien);
//            }
        }

        if (dsNhanVien != null && dsNhanVien.size() > 0) {
            adapter = new StaffAdapter(getActivity(), dsNhanVien);
            listView = (ListView) rootView.findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    intent.putExtra("thongTinNhanVien", dsNhanVien.get(position));
//                    intent.putExtra("ThongTinTaiKhoan", taiKhoan);
                    startActivity(intent);
                }
            });

            listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    final int checkedCount = listView.getCheckedItemCount();
                    mode.setTitle(checkedCount + getActivity().getResources().getString(R.string.da_chon));
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
                        case R.id.menu_xoa:
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

    private void xacNhanXoa(final ActionMode mode) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getResources().getString(R.string.xoa_nhan_vien));
        dialogBuilder.setIcon(R.mipmap.ic_delete_xanh);
        dialogBuilder.setMessage(getResources().getString(R.string.ban_co_muon_xoa_nhan_vien));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.co), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoaNhanVien(mode);
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

    private void xoaNhanVien(ActionMode mode) {
        SparseBooleanArray selected = adapter.getSelectedIds();
        String hinhAnh = "";
        NhanVien NhanVienChon;
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                NhanVienChon = adapter.getItem(selected.keyAt(i));
                hinhAnh = NhanVienChon.getAnhNhanVien();
                hinhAnh = hinhAnh.substring(hinhAnh.lastIndexOf("/"));
                final String tenNhanVien = "\t" + NhanVienChon.getTen() + "\t";

                DataClient dataClient = APIUtils.getData();
                Call<String> callbackXoa = dataClient.DeleteNhanVien(NhanVienChon.getMaNhanVien().trim(), hinhAnh.trim());
                callbackXoa.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Ok")) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.xoa) + tenNhanVien + getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CCCC", t.getMessage());
                        Toast.makeText(getActivity(), getResources().getString(R.string.xoa) + tenNhanVien + getResources().getString(R.string.that_bai), Toast.LENGTH_SHORT).show();
                    }
                });
                // NhanVien selectedListItem = adapter.getItem(selected.keyAt(i));
//                                        // Remove selected items using ids
//                                        adapter.remove(selectedListItem);
            }
        }
        getActivity().finish();
        getActivity().startActivity(getActivity().getIntent());
//                                Intent intent = new Intent(getActivity(), HomeActivity.class);
//                                startActivity(intent);
        mode.finish();
    }
}
