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
import com.example.admin.quanLyThuVien.activitys.HomeActivity;
import com.example.admin.quanLyThuVien.activitys.UserInfoActivity;
import com.example.admin.quanLyThuVien.adapters.ReaderAdapter;
import com.example.admin.quanLyThuVien.models.DocGia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReaderFragment extends Fragment {

    private ArrayList<DocGia> dsDocGia;
    private ReaderAdapter adapter;
    private View rootView;
    private CommonVariables commonVariables;
    private ListView listView;
    private DataClient dataClient;

    //tìm kiếm
    public void timKiemDG(String query) {
        Log.e("QueryFragment", query);
        adapter.getFilter().filter(query);
    }

    public ReaderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sach_list, container, false);
        commonVariables = (CommonVariables) getActivity().getApplication();
        dsDocGia = new ArrayList<DocGia>();
        getDanhSachNguoiDung();
        return rootView;
    }


    public void getDanhSachNguoiDung() {
        dataClient = APIUtils.getData();
        Call<List<DocGia>> listCall = dataClient.GetListDocGia("key");
        listCall.enqueue(new Callback<List<DocGia>>() {
            @Override
            public void onResponse(Call<List<DocGia>> call, Response<List<DocGia>> response) {
                ArrayList<DocGia> nguoiDungArrayList = (ArrayList<DocGia>) response.body();
                if (nguoiDungArrayList != null && nguoiDungArrayList.size() > 0) {
                    themDocGia(nguoiDungArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<DocGia>> call, Throwable t) {

            }
        });
    }

    public void themDocGia(ArrayList<DocGia> docGiaArrayList) {
        commonVariables.setMaDocGiaCuoi(docGiaArrayList.get(docGiaArrayList.size() - 1).getMaDocGia());
        for (int i = 0; i < docGiaArrayList.size(); i++) {
            DocGia docGia = new DocGia(docGiaArrayList.get(i).getMaDocGia(), docGiaArrayList.get(i).getTen(), docGiaArrayList.get(i).getEmail()
                    , docGiaArrayList.get(i).getNgaySinh(), docGiaArrayList.get(i).getGioiTinh(), docGiaArrayList.get(i).getSoDienThoai()
                    , docGiaArrayList.get(i).getDiaChi(), docGiaArrayList.get(i).getNgayLamThe(), docGiaArrayList.get(i).getAnhDocGia());
            dsDocGia.add(docGia);

//            if (bienToanCuc.getQuyenUser().equals("DG")) {
//                if (docGia.getMaDocGia().equals(bienToanCuc.getMaUser()))
//                    bienToanCuc.setDocGiaUser(docGia);
//            }
        }

        if (dsDocGia != null && dsDocGia.size() > 0) {
            adapter = new ReaderAdapter(getActivity(), dsDocGia);
            listView = (ListView) rootView.findViewById(R.id.list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    getTaiKhoan(dsDocGia.get(position));
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    intent.putExtra("thongTinDocGia", dsDocGia.get(position));
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
        dialogBuilder.setTitle(getResources().getString(R.string.xoa_doc_gia));
        dialogBuilder.setIcon(R.mipmap.ic_delete_xanh);
        dialogBuilder.setMessage(getResources().getString(R.string.ban_co_muon_xoa_doc_gia));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.co), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoaDocGia(mode);
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

    private void xoaDocGia(ActionMode mode) {
        DocGia docGiaChon;
        String hinhAnh = "";
        SparseBooleanArray selected = adapter.getSelectedIds();
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                docGiaChon = adapter.getItem(selected.keyAt(i));
                hinhAnh = docGiaChon.getAnhDocGia();
                hinhAnh = hinhAnh.substring(hinhAnh.lastIndexOf("/"));
                final String tenDocGia = "\t" + docGiaChon.getTen() + "\t";

                DataClient dataClient = APIUtils.getData();
                Call<String> callbackXoa = dataClient.DeleteDocGia(docGiaChon.getMaDocGia().trim(), hinhAnh.trim());
                callbackXoa.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Ok")) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.xoa) + tenDocGia + getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CCCC", t.getMessage());
                        Toast.makeText(getActivity(), getResources().getString(R.string.xoa) + tenDocGia + getResources().getString(R.string.that_bai), Toast.LENGTH_SHORT).show();
                    }
                });
                // DocGia selectedListItem = adapter.getItem(selected.keyAt(i));
//                                        // Remove selected items using ids
//                                        adapter.remove(selectedListItem);
            }
        }
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().finish();
        getActivity().startActivity(getActivity().getIntent());

        mode.finish();
    }
}
