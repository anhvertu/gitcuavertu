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
import com.example.admin.quanLyThuVien.models.TheLoai;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {


    private View rootView;
    private CommonVariables commonVariables;
    private ArrayList<TheLoai> dsTheLoai;

    public CategoryFragment() {
    }


    public ArrayList<TheLoai> getDsTheLoai() {
        return dsTheLoai;
    }

    public void setDsTheLoai(ArrayList<TheLoai> dsTheLoai) {
        this.dsTheLoai = dsTheLoai;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sach_list, container, false);

        commonVariables = (CommonVariables) getActivity().getApplicationContext();


        getDanhSachTheLoai(dsTheLoai);

        return rootView;
    }

    public void getDanhSachTheLoai( ArrayList<TheLoai> arrayList) {
        DataClient dataClient = APIUtils.getData();
        Call<List<TheLoai>> call = dataClient.GetListTheLoai("key");
        call.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> theLoaiArrayList = (ArrayList<TheLoai>) response.body();
                if (theLoaiArrayList != null && theLoaiArrayList.size() > 0) {
                    themTheLoai(theLoaiArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }


    private void themTheLoai(ArrayList<TheLoai> theLoaiArrayList) {
        dsTheLoai = new ArrayList<TheLoai>();
        for (int i = 0; i < theLoaiArrayList.size(); i++) {
            TheLoai theLoai = new TheLoai(theLoaiArrayList.get(i).getMaTheLoai(), theLoaiArrayList.get(i).getTenTheLoai());
            if (theLoai != null)
                dsTheLoai.add(theLoai);
        }



        }






}
