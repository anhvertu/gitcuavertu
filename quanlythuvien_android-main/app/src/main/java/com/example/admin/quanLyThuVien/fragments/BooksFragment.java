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
import com.example.admin.quanLyThuVien.activitys.BookActivity;
import com.example.admin.quanLyThuVien.models.Sach;
import com.example.admin.quanLyThuVien.adapters.BooksAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment {

    private ArrayList<Sach> dsSach;
    private BooksAdapter adapter;
    private View rootView;
    private ListView listView;
    private CommonVariables commonVariables;

    public BooksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sach_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        commonVariables = (CommonVariables) getActivity().getApplication();
        dsSach = new ArrayList<Sach>();
        commonVariables = (CommonVariables) getActivity().getApplication();
        setMaSachCuoi();
        getDanhSachSach();
        return rootView;
    }

    private void setMaSachCuoi() {
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.GetMaSachCuoi("key");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ketQua = response.body();
                if (!ketQua.isEmpty() && ketQua != null) {
                    commonVariables.setMaSachCuoi(ketQua);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void getDanhSachSach() {
        DataClient dataClient = APIUtils.getData();
        Call<List<Sach>> listCall = dataClient.GetListSach(commonVariables.getMaDauSachHienTai());
        listCall.enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> response) {
                ArrayList<Sach> sachArrayList = (ArrayList<Sach>) response.body();
                if (sachArrayList != null && sachArrayList.size() > 0) {
                    themSach(sachArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {
            }
        });
    }


    public void themSach(ArrayList<Sach> sachArrayList) {
        for (int i = 0; i < sachArrayList.size(); i++) {
            Sach sach = new Sach();
            sach.setMaSach(sachArrayList.get(i).getMaSach());
            sach.setTenSach(sachArrayList.get(i).getTenSach());
            sach.setTinhTrangSach(sachArrayList.get(i).getTinhTrangSach());
            sach.setChoMuon(sachArrayList.get(i).getChoMuon());
            sach.setMaDauSach(sachArrayList.get(i).getMaDauSach());
            dsSach.add(sach);

            if (dsSach != null && dsSach.size() > 0) {

                adapter = new BooksAdapter(getActivity(), dsSach);

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        Intent intent = new Intent(getActivity(), ThongTinDauSachActivity.class);
//                        intent.putExtra("thongTinDauSach", dsSach.get(position));
//                        startActivity(intent);
                    }
                });

                if(!commonVariables.getQuyenUser().equals("DG"))
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
    }
    private void xacNhanXoa(final ActionMode mode) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getResources().getString(R.string.xoa_sach));
        dialogBuilder.setIcon(R.mipmap.ic_delete_xanh);
        dialogBuilder.setMessage(getResources().getString(R.string.ban_co_muon_xoa_cuon));
        dialogBuilder.setPositiveButton(getResources().getString(R.string.co), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoaSach(mode);
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

    private void xoaSach(ActionMode mode) {
        // call getSelectedIds method from customListViewAdapter
        SparseBooleanArray selected = adapter.getSelectedIds();
        // Captures all selected ids with a loop
        String hinhAnh = "";
        Sach sachChon;
        int kiemTraSize = selected.size();

        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                sachChon = adapter.getItem(selected.keyAt(i));
                final String tenSach = "\t" + sachChon.getTenSach() + "\t";

                DataClient dataClient = APIUtils.getData();
                Call<String> callbackXoa = dataClient.DeleteSach(sachChon.getMaSach().trim());
                callbackXoa.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("Ok")) {
//                                                    Toast.makeText(getActivity(), getResources().getString(R.string.xoa)   + getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
                        }
//                                                    Toast.makeText(getActivity(), "tốt", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("CCCC", t.getMessage());
                        Toast.makeText(getActivity(), getResources().getString(R.string.xoa)+ " "+ getResources().getString(R.string.that_bai), Toast.LENGTH_SHORT).show();
                    }
                });
                // DauSach selectedListItem = adapter.getItem(selected.keyAt(i));
//                                        // Remove selected items using ids
//                                        adapter.remove(selectedListItem);
            }
        }
        Toast.makeText(getActivity(), getResources().getString(R.string.xoa)+ ""+ getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(getActivity(), BookActivity.class);
        startActivity(intent);
        mode.finish();
    }
}
