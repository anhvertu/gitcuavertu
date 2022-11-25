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
import com.example.admin.quanLyThuVien.models.TacGia;
import com.example.admin.quanLyThuVien.models.TheLoai;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorFragment extends Fragment {

    private View rootView;
    private CommonVariables commonVariables;
    private ArrayList<TacGia> dsTacGia;

    public AuthorFragment() {
    }


    public ArrayList<TacGia> getDsTacGia() {
        return dsTacGia;
    }

    public void setDsTheLoai(ArrayList<TacGia> dsTacGia) {
        this.dsTacGia = dsTacGia;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tacgia_list, container, false);

        commonVariables = (CommonVariables) getActivity().getApplicationContext();


        getDanhSachTacGia(dsTacGia);

        return rootView;
    }

    public void getDanhSachTacGia( ArrayList<TacGia> arrayList) {
        DataClient dataClient = APIUtils.getData();
        Call<List<TacGia>> call = dataClient.GetListTacGia("key");
        call.enqueue(new Callback<List<TacGia>>() {
            @Override
            public void onResponse(Call<List<TacGia>> call, Response<List<TacGia>> response) {
                ArrayList<TacGia> TacGiaArrayList = (ArrayList<TacGia>) response.body();
                if (TacGiaArrayList != null && TacGiaArrayList.size() > 0) {
                    themTacGia(TacGiaArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<TacGia>> call, Throwable t) {

            }
        });
    }


    private void themTacGia(ArrayList<TacGia> TacGiaArrayList) {
        dsTacGia = new ArrayList<TacGia>();
        for (int i = 0; i < TacGiaArrayList.size(); i++) {
            TacGia TacGia = new TacGia(TacGiaArrayList.get(i).getMaTacGia(), TacGiaArrayList.get(i).getTenTacGia());
            if (TacGia != null)
                dsTacGia.add(TacGia);
        }



    }
}
