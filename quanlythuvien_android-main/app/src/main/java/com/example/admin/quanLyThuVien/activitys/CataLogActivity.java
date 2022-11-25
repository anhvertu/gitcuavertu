package com.example.admin.quanLyThuVien.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.SimpleItemTouchHelperCallback;
import com.example.admin.quanLyThuVien.adapters.AuthorAdapter;
import com.example.admin.quanLyThuVien.adapters.CategoryAdapter;
import com.example.admin.quanLyThuVien.adapters.NhaXuatBanAdapter;
import com.example.admin.quanLyThuVien.adapters.ViTriAdapter;
import com.example.admin.quanLyThuVien.interfaces.ItemTouchListenner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CataLogActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CategoryAdapter categoryAdapter;
    private AuthorAdapter authorAdapter;
    private NhaXuatBanAdapter nhaXuatBanAdapter;
    private Toolbar toolbar;
    private String demuc, tieuDe;
    private CommonVariables commonVariables;
    private DataClient dataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        commonVariables = (CommonVariables) getApplication();
        Intent intent = getIntent();
        demuc = intent.getStringExtra("deMuc");
        initToolbar();
        initReyclerView();
    }

    public void init() {
        switch (demuc) {
            case "0":
                tieuDe = getResources().getString(R.string.danh_sach_the_loai);
                break;
            case "1":
                tieuDe = getResources().getString(R.string.danh_sach_tac_gia);
                break;
            case "2":
                tieuDe = getResources().getString(R.string.danh_sach_nha_xuat_ban);
                break;
            default:
                tieuDe = getResources().getString(R.string.danh_sach_vi_tri);
                break;
        }
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar_danh_sach);
        setSupportActionBar(toolbar);
        init();
        getSupportActionBar().setTitle(tieuDe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initReyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rcDanhSach);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataClient = APIUtils.getData();

        switch (demuc) {
            case "0":
                categoryAdapter = new CategoryAdapter(commonVariables, dataClient, CataLogActivity.this, commonVariables.getmDSTheLoai());
                mRecyclerView.setAdapter(categoryAdapter);
                break;
            case "1":
                authorAdapter = new AuthorAdapter(commonVariables, dataClient, CataLogActivity.this, commonVariables.getmDSTacGia());
                mRecyclerView.setAdapter(authorAdapter);
                break;
            case "2":
                nhaXuatBanAdapter = new NhaXuatBanAdapter(commonVariables, dataClient, CataLogActivity.this, commonVariables.getmDSNhaXuatBan());
                mRecyclerView.setAdapter(nhaXuatBanAdapter);
                break;
            case "3":
                ViTriAdapter ViTriAdapter = new ViTriAdapter(commonVariables, dataClient, CataLogActivity.this, commonVariables.getmDSViTri());
                mRecyclerView.setAdapter(ViTriAdapter);
                break;
        }

        addItemTouchCallback(mRecyclerView);
    }


    private void addItemTouchCallback(RecyclerView recyclerView) {
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(new ItemTouchListenner() {
            @Override
            public void onMove(int oldPosition, int newPosition) {

                switch (demuc) {
                    case "0":
                        categoryAdapter.onMove(oldPosition, newPosition);
                        break;
                    case "1":
                        authorAdapter.onMove(oldPosition, newPosition);
                        break;
                }
            }

            @Override
            public void swipe(int position, int direction) {

                switch (demuc) {
                    case "0":
                        categoryAdapter.swipe(position, direction);
                        break;
                    case "1":
                        authorAdapter.swipe(position, direction);
                        break;
                }
            }
        }, commonVariables);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_catalog, menu);
        menu.removeItem(R.id.menuChiaSe);
        if (commonVariables.getQuyenUser().equals("DG"))
            menu.removeItem(R.id.menu_them_de_muc);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_them_de_muc:
                if (!commonVariables.getQuyenUser().equals("DG")) {
                    addCatalog();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCatalog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_book_dialog, null);
        dialogBuilder.setView(dialogView);

        final Spinner spinner = dialogView.findViewById(R.id.spSuaTinhTrangSach);
        spinner.setVisibility(View.GONE);
        final EditText edtThem = dialogView.findViewById(R.id.edtThemSuaSachText);
        final EditText edtNum = dialogView.findViewById(R.id.edtThemSuaSach);
        edtNum.setVisibility(View.GONE);
        edtThem.setVisibility(View.VISIBLE);

        switch (demuc) {
            case "0":
                dialogBuilder.setTitle(getResources().getString(R.string.them_the_loai));
                dialogBuilder.setMessage(getResources().getString(R.string.nhap_ten_the_loai));
                break;
            case "1":
                dialogBuilder.setTitle(getResources().getString(R.string.them_tac_gia));
                dialogBuilder.setMessage(getResources().getString(R.string.nhap_ten_tac_gia));
                break;
            case "2":
                dialogBuilder.setTitle(getResources().getString(R.string.them_nha_xuat_ban));
                dialogBuilder.setMessage(getResources().getString(R.string.nhap_ten_nha_xuat_ban));
                break;
            case "3":
                dialogBuilder.setTitle(getResources().getString(R.string.them_vi_tri));
                dialogBuilder.setMessage(getResources().getString(R.string.nhap_ten_vi_tri));
                break;
        }

        dialogBuilder.setPositiveButton(getResources().getString(R.string.them), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    String ten = edtThem.getText().toString().trim();
                    if (ten.isEmpty() || ten.equals("0")) {
                        Toast.makeText(CataLogActivity.this, getResources().getString(R.string.vui_long_nhap_ten), Toast.LENGTH_LONG).show();
                        addCatalog();
                    } else {
                        add(ten);
                    }
                } catch (Exception e) {
                    Toast.makeText(CataLogActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogBuilder.setNegativeButton(getResources().getString(R.string.huy), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    private void add(String ten) {
        final DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.InsertDeMuc(ten, demuc);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String ketQua = response.body();
                if (ketQua.equals("Succsess")) {
                    Toast.makeText(CataLogActivity.this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
                    GetDeMucActivity getDeMucActivity = new GetDeMucActivity(dataClient, commonVariables);
                    Intent intent = new Intent(getApplication(), CataLogActivity.class);
                    switch (demuc) {
                        case "0":
                            getDeMucActivity.getDanhSachTheLoai();
                            intent.putExtra("deMuc", "0");
                            break;
                        case "1":
                            getDeMucActivity.getDanhSachTacGia();
                            intent.putExtra("deMuc", "1");
                            break;
                        case "2":
                            getDeMucActivity.getDanhSachNhaXuatBan();
                            intent.putExtra("deMuc", "2");
                            break;
                        case "3":
                            getDeMucActivity.getDanhSachViTri();
                            intent.putExtra("deMuc", "3");
                            break;

                    }
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CataLogActivity.this, ketQua.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }
}
