package com.example.admin.quanLyThuVien.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.activitys.CataLogActivity;
import com.example.admin.quanLyThuVien.activitys.GetDeMucActivity;
import com.example.admin.quanLyThuVien.models.TacGia;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<TacGia> arrayList;
    private Context mContext;
    private CommonVariables commonVariables;
    private DataClient dataClient;

    public AuthorAdapter(CommonVariables commonVariables, DataClient dataClient , Context context, List<TacGia> arrayList) {
        this.commonVariables = commonVariables;
        this.dataClient =dataClient;
        this.mContext = context;
        this.arrayList = arrayList;
    }

    public void addData(List<TacGia> data) {
        if (data == null) {
            return;
        }
        arrayList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.catalog_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mContext,arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(arrayList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(arrayList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void swipe(int position, int direction) {
        if(!commonVariables.getQuyenUser().equals("DG"))
            XacNhanXoa(arrayList.get(position),position);
    }

    private void XacNhanXoa(final TacGia TacGia, final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setTitle(mContext.getResources().getString(R.string.app_name));
        dialogBuilder.setIcon(R.mipmap.ic_logo_xanh);
        dialogBuilder.setMessage(mContext.getResources().getString(R.string.ban_co_muon_xoa)+TacGia.getTenTacGia()+" " + mContext.getResources().getString(R.string.khong)+" ?");
        dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.co), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xoaTacGia(TacGia,position);
            }
        });

        dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.khong), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GetDeMucActivity getDeMucActivity = new GetDeMucActivity(dataClient, commonVariables);
                getDeMucActivity.getDanhSachTacGia();
                Intent intent = new Intent(mContext, CataLogActivity.class);
                intent.putExtra("deMuc","1");
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    private void xoaTacGia(final TacGia TacGia, final int position) {
        Call<String> call = dataClient.DeleteDeMuc(TacGia.getMaTacGia(),"0");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String kq = response.body();
                if(kq.equals("Succsess")){
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.da_xoa)+" "+mContext.getResources().getString(R.string.the_loai)+" " + TacGia.getTenTacGia(), Toast.LENGTH_SHORT).show();
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                }
                else Toast.makeText(mContext, mContext.getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTen;
        private ImageButton imgSua;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTenDeMuc);
            imgSua = itemView.findViewById(R.id.imgSuaDanhSachDeMuc);
            if(!commonVariables.getQuyenUser().equals("DG"))
                imgSua.setVisibility(View.VISIBLE);
        }

        private void bindData(final Context mContext, final TacGia data) {
            if (data == null) {
                return;
            }
            txtTen.setText(data.getTenTacGia());
            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    suaTacGia(data);

                }
            });
        }
    }


    public void suaTacGia(final TacGia TacGia) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.add_book_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner = dialogView.findViewById(R.id.spSuaTinhTrangSach);
        spinner.setVisibility(View.GONE);
        final EditText edtSuaTen = dialogView.findViewById(R.id.edtThemSuaSachText);
        final EditText edtNum = dialogView.findViewById(R.id.edtThemSuaSach);
        edtNum.setVisibility(View.GONE);
        edtSuaTen.setVisibility(View.VISIBLE);
        edtSuaTen.setText(TacGia.getTenTacGia());

        dialogBuilder.setTitle(mContext.getResources().getString(R.string.sua_tac_gia));
        dialogBuilder.setMessage(mContext.getResources().getString(R.string.nhap_ten_tac_gia));
        dialogBuilder.setPositiveButton(mContext.getResources().getString(R.string.sua), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    String ten = edtSuaTen.getText().toString().trim();
                    if (ten.isEmpty()) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.vui_long_nhap_ten), Toast.LENGTH_LONG).show();
                    } else {

                        suaTen(TacGia,ten);
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogBuilder.setNegativeButton(mContext.getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    private void suaTen(TacGia TacGia, String ten) {
        Call<String> call = dataClient.UpdateDeMuc(TacGia.getMaTacGia(),ten,"1");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                if(result.equals("Succsess")){
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
                    GetDeMucActivity getDeMucActivity = new GetDeMucActivity(dataClient, commonVariables);
                    getDeMucActivity.getDanhSachTacGia();
                    Intent intent = new Intent(mContext, CataLogActivity.class);
                    intent.putExtra("deMuc","1");
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
