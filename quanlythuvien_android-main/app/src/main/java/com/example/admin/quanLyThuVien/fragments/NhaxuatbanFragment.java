package com.example.admin.quanLyThuVien.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.models.NhaXuatBan;
import com.example.admin.quanLyThuVien.models.TacGia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhaxuatbanFragment extends Fragment {

    private View rootView;
    private CommonVariables commonVariables;
    private ArrayList<NhaXuatBan> dsNhaXuatBan;

    public NhaxuatbanFragment() {
    }


    public ArrayList<NhaXuatBan> getDsNhaXuatBan() {
        return dsNhaXuatBan;
    }

    public void setDsTheLoai(ArrayList<TacGia> dsTacGia) {
        this.dsNhaXuatBan = dsNhaXuatBan;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sach_list, container, false);

        commonVariables = (CommonVariables) getActivity().getApplicationContext();


        getDanhSachNhaxuatban(dsNhaXuatBan);

        return rootView;
    }

    public void getDanhSachNhaxuatban( ArrayList<NhaXuatBan> arrayList) {
        DataClient dataClient = APIUtils.getData();
        Call<List<NhaXuatBan>> call = dataClient.GetListNhaXuatBan("key");
        call.enqueue(new Callback<List<NhaXuatBan>>() {
            @Override
            public void onResponse(Call<List<NhaXuatBan>> call, Response<List<NhaXuatBan>> response) {
                ArrayList<NhaXuatBan> NhaXuatBanArrayList = (ArrayList<NhaXuatBan>) response.body();
                if (NhaXuatBanArrayList != null && NhaXuatBanArrayList.size() > 0) {
                    themNhaXuatBan(NhaXuatBanArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<NhaXuatBan>> call, Throwable t) {

            }
        });
    }


    private void themNhaXuatBan(ArrayList<NhaXuatBan> NhaXuatBanArrayList) {
        dsNhaXuatBan = new ArrayList<NhaXuatBan>();
        for (int i = 0; i < NhaXuatBanArrayList.size(); i++) {
            NhaXuatBan NhaXuatBan = new NhaXuatBan(NhaXuatBanArrayList.get(i).getMaNhaXuatBan(), NhaXuatBanArrayList.get(i).getTenNhaXuatBan());
            if (NhaXuatBan != null)
                dsNhaXuatBan.add(NhaXuatBan);
        }



    }
}
