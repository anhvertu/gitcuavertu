package com.example.admin.quanLyThuVien.activitys;

import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.models.NhaXuatBan;
import com.example.admin.quanLyThuVien.models.TacGia;
import com.example.admin.quanLyThuVien.models.TheLoai;
import com.example.admin.quanLyThuVien.models.ViTri;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetDeMucActivity {

    private DataClient dataClient;
    private CommonVariables commonVariables;

    public GetDeMucActivity() {
    }

    public GetDeMucActivity(DataClient dataClient, CommonVariables commonVariables) {
        this.dataClient = dataClient;
        this.commonVariables = commonVariables;
    }

    public  void getDanhSachViTri() {
        Call<List<ViTri>> call = dataClient.GetListViTri("key");
        call.enqueue(new Callback<List<ViTri>>() {
            @Override
            public void onResponse(Call<List<ViTri>> call, Response<List<ViTri>> response) {
                ArrayList<ViTri> viTriArrayList = (ArrayList<ViTri>) response.body();
                if (viTriArrayList != null && viTriArrayList.size() > 0) {
                    commonVariables.setmDSViTri(viTriArrayList);
//                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSViTri().get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ViTri>> call, Throwable t) {

            }
        });


    }

    public void getDanhSachNhaXuatBan() {
        Call<List<NhaXuatBan>> call = dataClient.GetListNhaXuatBan("key");
        call.enqueue(new Callback<List<NhaXuatBan>>() {
            @Override
            public void onResponse(Call<List<NhaXuatBan>> call, Response<List<NhaXuatBan>> response) {
                ArrayList<NhaXuatBan> nhaXuatBanArrayList = (ArrayList<NhaXuatBan>) response.body();
                if (nhaXuatBanArrayList != null && nhaXuatBanArrayList.size() > 0) {
                    commonVariables.setmDSNhaXuatBan(nhaXuatBanArrayList);
//                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSNhaXuatBan().get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhaXuatBan>> call, Throwable t) {

            }
        });
    }

    public void getDanhSachTacGia() {
                    Call<List<TacGia>> call = dataClient.GetListTacGia("key");
                    call.enqueue(new Callback<List<TacGia>>() {
                        @Override
                        public void onResponse(Call<List<TacGia>> call, Response<List<TacGia>> response) {
                            ArrayList<TacGia> tacGiaArrayList = (ArrayList<TacGia>) response.body();
                            if (tacGiaArrayList != null && tacGiaArrayList.size() > 0) {
                                commonVariables.setmDSTacGia(tacGiaArrayList);
//                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSTacGia().get(0).toString(), Toast.LENGTH_SHORT).show();
                            }
            }

            @Override
            public void onFailure(Call<List<TacGia>> call, Throwable t) {

            }
        });
    }

    public void getDanhSachTheLoai() {
        Call<List<TheLoai>> call = dataClient.GetListTheLoai("key");
        call.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> theLoaiArrayList = (ArrayList<TheLoai>) response.body();
                if (theLoaiArrayList != null && theLoaiArrayList.size() > 0) {
                    commonVariables.setmDSTheLoai(theLoaiArrayList);
//                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSTheLoai().get(0).toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }
}
