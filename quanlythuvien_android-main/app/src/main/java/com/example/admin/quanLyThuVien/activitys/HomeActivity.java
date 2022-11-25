package com.example.admin.quanLyThuVien.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;
import com.example.admin.quanLyThuVien.fragments.BookFragment;
import com.example.admin.quanLyThuVien.fragments.ReaderFragment;
import com.example.admin.quanLyThuVien.adapters.CatalogAdapter;
import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.fragments.StaffFragment;
import com.example.admin.quanLyThuVien.models.DocGia;
import com.example.admin.quanLyThuVien.models.NhanVien;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar, tbSort;
    private BottomNavigationView navigation;
    private MaterialSearchView searchView;
    private ViewPager viewPager;
    private CatalogAdapter adapter;
    private TabLayout tabLayout;
    private LinearLayout llXapXep;
    private ImageView imgXapXep;
    private String[] arrXapXep;
    private CommonVariables commonVariables;
    private String tuKhoaTim = "";
    private int vitriTapHienTai = 0;
    private ImageView imgAnhDaiDien;
    private TextView txtTenUser, txtEmailUser, txtQuyenUser;
    private View headerLayout;
    DataClient dataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        commonVariables = (CommonVariables) getApplication();
        arrXapXep = new String[]{getResources().getString(R.string.khong), getResources().getString(R.string.so_luot_xem_sach), getResources().getString(R.string.so_luot_muon_sach), getResources().getString(R.string.so_diem_rating_trung_binh)};
        initDeMuc();
        navigation = (BottomNavigationView) findViewById(R.id.bottomNav_home);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (commonVariables.getQuyenUser().equals("DG"))
            navigation.getMenu().removeItem(R.id.menu_them);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.thu_vien);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.ic_menu_black_24dp);

        llXapXep = findViewById(R.id.llXapXep);
        imgXapXep = findViewById(R.id.imgXapXepHome);
        llXapXep.setVisibility(View.GONE);

        mDrawerLayout = findViewById(R.id.drawer_lyHome);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        khoiTaoDanhSach();

        nvDrawer = findViewById(R.id.nav_view);
        khoiTaoNoiDungDrawer();
//        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                // Respond when the drawer's position changes
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                // Respond when the drawer is opened
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                // Respond when the drawer is closed
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//                // Respond when the drawer motion state changes
//            }
//        });

        searchView = findViewById(R.id.search_view_home);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                if (vitriTapHienTai == 0) {
                    llXapXep.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSearchViewClosed() {
                llXapXep.setVisibility(View.GONE);
                khoiTaoDanhSach();
                commonVariables.setTimTheo(getResources().getString(R.string.khong));
            }
        });

        xapXepTheo();
        setChonTap(tabLayout);
    }

    private void initDeMuc() {
        dataClient = APIUtils.getData();
        GetDeMucActivity getDeMucActivity = new GetDeMucActivity(dataClient, commonVariables);
        getDeMucActivity.getDanhSachTheLoai();
        getDeMucActivity.getDanhSachTacGia();
        getDeMucActivity.getDanhSachNhaXuatBan();
        getDeMucActivity.getDanhSachViTri();

//        dataClient = APIUtils.getData();
//        getDanhSachTheLoai();
//        getDanhSachTacGia();
//        getDanhSachNhaXuatBan();
//        getDanhSachViTri();
//
    }

//    public  void getDanhSachViTri() {
//        Call<List<ViTri>> call = dataClient.GetListViTri("key");
//        call.enqueue(new Callback<List<ViTri>>() {
//            @Override
//            public void onResponse(Call<List<ViTri>> call, Response<List<ViTri>> response) {
//                ArrayList<ViTri> viTriArrayList = (ArrayList<ViTri>) response.body();
//                if (viTriArrayList != null && viTriArrayList.size() > 0) {
//                    bienToanCuc.setmDSViTri(viTriArrayList);
////                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSViTri().get(0).toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ViTri>> call, Throwable t) {
//
//            }
//        });
//
//
//    }
//
//    public void getDanhSachNhaXuatBan() {
//        Call<List<NhaXuatBan>> call = dataClient.GetListNhaXuatBan("key");
//        call.enqueue(new Callback<List<NhaXuatBan>>() {
//            @Override
//            public void onResponse(Call<List<NhaXuatBan>> call, Response<List<NhaXuatBan>> response) {
//                ArrayList<NhaXuatBan> nhaXuatBanArrayList = (ArrayList<NhaXuatBan>) response.body();
//                if (nhaXuatBanArrayList != null && nhaXuatBanArrayList.size() > 0) {
//                    bienToanCuc.setmDSNhaXuatBan(nhaXuatBanArrayList);
////                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSNhaXuatBan().get(0).toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<NhaXuatBan>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void getDanhSachTacGia() {
//        Call<List<TacGia>> call = dataClient.GetListTacGia("key");
//        call.enqueue(new Callback<List<TacGia>>() {
//            @Override
//            public void onResponse(Call<List<TacGia>> call, Response<List<TacGia>> response) {
//                ArrayList<TacGia> tacGiaArrayList = (ArrayList<TacGia>) response.body();
//                if (tacGiaArrayList != null && tacGiaArrayList.size() > 0) {
//                    bienToanCuc.setmDSTacGia(tacGiaArrayList);
////                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSTacGia().get(0).toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TacGia>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    public void getDanhSachTheLoai() {
//
//        Call<List<TheLoai>> call = dataClient.GetListTheLoai("key");
//        call.enqueue(new Callback<List<TheLoai>>() {
//            @Override
//            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
//                ArrayList<TheLoai> theLoaiArrayList = (ArrayList<TheLoai>) response.body();
//                if (theLoaiArrayList != null && theLoaiArrayList.size() > 0) {
//                    bienToanCuc.setmDSTheLoai(theLoaiArrayList);
////                    Toast.makeText(HomeActivity.this, bienToanCuc.getmDSTheLoai().get(0).toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
//
//            }
//        });
//    }

    private void setChonTap(TabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (!commonVariables.getQuyenUser().equals("DG"))
                    if (tab.getPosition() == 1) {
                        vitriTapHienTai = 1;
                        llXapXep.setVisibility(View.GONE);
                        searchView.setHint(getResources().getString(R.string.ten_doc_gia));
                        navigation.getMenu().findItem(R.id.menu_them).setTitle(getResources().getString(R.string.them_doc_gia));
                        navigation.getMenu().findItem(R.id.menu_quet).setTitle(getResources().getString(R.string.quet_ma_doc_gia));
                    } else if (tab.getPosition() == 0) {
                        searchView.setHint(getResources().getString(R.string.hint_tim_kiem));
                        vitriTapHienTai = 0;
                        navigation.getMenu().findItem(R.id.menu_them).setTitle(getResources().getString(R.string.them_dau_sach));
                        navigation.getMenu().findItem(R.id.menu_quet).setTitle(getResources().getString(R.string.quet_ma_dau_sach));
                    } else if (tab.getPosition() == 2) {
                        searchView.setHint(getResources().getString(R.string.ten_nhan_vien));
                        llXapXep.setVisibility(View.GONE);
                        vitriTapHienTai = 2;
                        navigation.getMenu().findItem(R.id.menu_them).setTitle(getResources().getString(R.string.them_nhan_vien));
                        navigation.getMenu().findItem(R.id.menu_quet).setTitle(getResources().getString(R.string.quet_ma_nhan_vien));
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void khoiTaoNoiDungDrawer() {

        headerLayout = nvDrawer.getHeaderView(0);
        imgAnhDaiDien = headerLayout.findViewById(R.id.imgAnhUser);
        txtTenUser = headerLayout.findViewById(R.id.txtTenUser);
        txtEmailUser = headerLayout.findViewById(R.id.txtEmailUser);
        txtQuyenUser = headerLayout.findViewById(R.id.txtQuyenUser);

        DataClient dataClient = APIUtils.getData();
        if (commonVariables.getQuyenUser().equals("DG")) {
            Call<List<DocGia>> call = dataClient.GetDocGiaUser(commonVariables.getMaUser());
            call.enqueue(new Callback<List<DocGia>>() {
                @Override
                public void onResponse(Call<List<DocGia>> call, Response<List<DocGia>> response) {
                    ArrayList<DocGia> docGiaArrayList = (ArrayList<DocGia>) response.body();
                    if (docGiaArrayList != null && docGiaArrayList.size() > 0) {
                        setThongTinDocGiaUser(docGiaArrayList.get(0));
                    }
                }

                @Override
                public void onFailure(Call<List<DocGia>> call, Throwable t) {

                }
            });
        } else {
            Call<List<NhanVien>> call = dataClient.GetNhanVienUser(commonVariables.getMaUser());
            call.enqueue(new Callback<List<NhanVien>>() {
                @Override
                public void onResponse(Call<List<NhanVien>> call, Response<List<NhanVien>> response) {
                    ArrayList<NhanVien> nhanVienArrayList = (ArrayList<NhanVien>) response.body();
                    if (nhanVienArrayList != null && nhanVienArrayList.size() > 0) {
                        setThongTinNhanVienUser(nhanVienArrayList.get(0));
                    }
                }

                @Override
                public void onFailure(Call<List<NhanVien>> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
                }
            });
        }

        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        chonDrawerItem(menuItem);
                        return true;
                    }
                });

    }

    private void setThongTinNhanVienUser(NhanVien nhanVien) {
        Picasso.get().load(APIUtils.Base_Url + "images/" + (nhanVien.getAnhNhanVien()))
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_book_error)
                .fit()
                .centerCrop()
                .into(imgAnhDaiDien);
        txtTenUser.setText(nhanVien.getTen());
        txtEmailUser.setText(nhanVien.getEmail());
        txtQuyenUser.setText(nhanVien.getChucVu());

        if (nhanVien.getMaNhanVien().equals(commonVariables.getMaUser()))
            commonVariables.setNhanVienUser(nhanVien);

    }

    private void setThongTinDocGiaUser(DocGia docGia) {
        Picasso.get().load(APIUtils.Base_Url + "images/" + (docGia.getAnhDocGia()))
                .placeholder(R.mipmap.ic_avatar)
                .error(R.mipmap.ic_book_error)
                .fit()
                .centerCrop()
                .into(imgAnhDaiDien);
        txtTenUser.setText(docGia.getTen());
        txtEmailUser.setText(docGia.getEmail());
        txtQuyenUser.setText(R.string.doc_gia);


        if (docGia.getMaDocGia().equals(commonVariables.getMaUser()))
            commonVariables.setDocGiaUser(docGia);
    }

    private void chonDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_thong_tin_user:
                Intent intent = new Intent(HomeActivity.this, UserInfoActivity.class);
                if (commonVariables.getQuyenUser().equals("DG")) {
                    if (commonVariables.getDocGiaUser() != null) {
                        intent.putExtra("ThongTinDocGiaUser", commonVariables.getDocGiaUser());
                    }
                } else {
                    if (commonVariables.getNhanVienUser() != null) {
                        intent.putExtra("ThongTinNhanVienUser", commonVariables.getNhanVienUser());
                    }
                }
                startActivity(intent);
                break;


            case R.id.menu_quan_ly_de_muc:
                chonDeMuc();
                break;


            case R.id.menu_dang_xuat:
                Intent intent1 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    private void chonDeMuc() {
        final int[] imageIdArr = {R.mipmap.ic_category, R.mipmap.ic_author, R.mipmap.ic_nxb, R.mipmap.ic_position};
        final String[] listDeMuc = {"\t\t\t" + getResources().getString(R.string.the_loai), "\t\t\t" + getResources().getString(R.string.tac_gia)
                , "\t\t\t" + getResources().getString(R.string.nha_xuat_ban), "\t\t\t" + getResources().getString(R.string.vi_tri)};


        final String CUSTOM_ADAPTER_IMAGE = "image";
        final String CUSTOM_ADAPTER_TEXT = "text";

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setIcon(R.mipmap.ic_logo_xanh);
        builder.setTitle(getResources().getString(R.string.ds_de_muc));

        List<Map<String, Object>> dialogItemList = new ArrayList<Map<String, Object>>();

        int listItemLen = listDeMuc.length;
        for (int i = 0; i < listItemLen; i++) {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put(CUSTOM_ADAPTER_IMAGE, imageIdArr[i]);
            itemMap.put(CUSTOM_ADAPTER_TEXT, listDeMuc[i]);
            dialogItemList.add(itemMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(HomeActivity.this, dialogItemList,
                R.layout.catalog_list,
                new String[]{CUSTOM_ADAPTER_IMAGE, CUSTOM_ADAPTER_TEXT},
                new int[]{R.id.imgIconDeMuc, R.id.txtTenDeMuc});

        builder.setAdapter(simpleAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int itemIndex) {
//                Toast.makeText(HomeActivity.this, "bạn chọn: " + listDeMuc[itemIndex], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeActivity.this, CataLogActivity.class);
                String data = "";
                switch (itemIndex) {
                    case 0:
                        data = "0";
                        break;
                    case 1:
                        data = "1";
                        break;
                    case 2:
                        data = "2";
                        break;
                    default:
                        data = "3";
                        break;

                }
                intent.putExtra("deMuc", data);
                startActivity(intent);
            }
        });

        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    public void khoiTaoDanhSach() {
        viewPager = (ViewPager) findViewById(R.id.vpSach);
        adapter = new CatalogAdapter(HomeActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) findViewById(R.id.tlSach);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void timKiem(String newText) {
        PagerAdapter pagerAdapter = (PagerAdapter) viewPager.getAdapter();
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            Fragment viewPagerFragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, i);
            if (viewPagerFragment != null && viewPagerFragment.isAdded()) {
                if (viewPagerFragment instanceof BookFragment) {
                    BookFragment bookFragment = (BookFragment) viewPagerFragment;
                    if (bookFragment != null) {
                        bookFragment.timKiemDS(newText); // Calling the method beginSearch of ChatFragment
                    }
                } else if (viewPagerFragment instanceof ReaderFragment) {
                    ReaderFragment readerFragment = (ReaderFragment) viewPagerFragment;
                    if (readerFragment != null) {
                        readerFragment.timKiemDG(newText); // Calling the method beginSearch of GroupsFragment
                    }
                } else if (viewPagerFragment instanceof StaffFragment) {
                    StaffFragment staffFragment = (StaffFragment) viewPagerFragment;
                    if (staffFragment != null) {
                        staffFragment.timKiem(newText); // Calling the method beginSearch of GroupsFragment
                    }
                }
            }
        }

    }


    public void xapXepTheo() {
        imgXapXep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.add_book_dialog, null);
                dialogBuilder.setView(dialogView);

                final EditText edtThemSach = dialogView.findViewById(R.id.edtThemSuaSach);
                edtThemSach.setVisibility(View.GONE);

                final Spinner spXapXep = dialogView.findViewById(R.id.spSuaTinhTrangSach);
                final ArrayAdapter<String> adp = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_spinner_item, arrXapXep);
                spXapXep.setAdapter(adp);

                int viTriXep = adp.getPosition(commonVariables.getTimTheo());
                spXapXep.setSelection(viTriXep);

                dialogBuilder.setTitle(getResources().getString(R.string.xap_xep_dau_sach));
                dialogBuilder.setMessage(getResources().getString(R.string.chon_tieu_chi_xap_xep));
                dialogBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String xapXep = spXapXep.getSelectedItem().toString().trim();
                        if (!xapXep.isEmpty()) {
                            commonVariables.setTimTheo(xapXep);
                            timKiem(tuKhoaTim);
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
        });
    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate and initialize the bottom menu
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menu_tim_kiem);
        searchView.setMenuItem(item);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                timKiem(query);
                return true;
//                return false;
            }

            //tìm kiếm trong khi nhập
            @Override
            public boolean onQueryTextChange(String newText) {
//                if (!newText.isEmpty()) {
                tuKhoaTim = newText;
                timKiem(newText);
//                }
//                timKiem(tuKhoaTim);
                return false;
            }
        });
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (commonVariables.getQuyenUser().equals("DG")) {
                if (item.getItemId() == R.id.menu_quet) {
                    Intent intent1 = new Intent(HomeActivity.this, QRScanActivity.class);
                    switch (vitriTapHienTai) {
                        case 0:
                            intent1.putExtra("loai", "ds");
                            break;
                        case 1:
                            intent1.putExtra("loai", "dg");
                            break;
                        default:
                            intent1.putExtra("loai", "nv");
                            break;
                    }
                    startActivity(intent1);
                    finish();
                }
            } else {
                switch (item.getItemId()) {
                    case R.id.menu_them:
                        Intent intent = null;
                        switch (vitriTapHienTai) {
                            case 0:
                                intent = new Intent(HomeActivity.this, AddBookActivity.class);
                                break;
                            case 1:
                                intent = new Intent(HomeActivity.this, AddUserActivity.class);
                                intent.putExtra("loaiUser", "DG");
                                break;
                            default:
                                intent = new Intent(HomeActivity.this, AddUserActivity.class);
                                intent.putExtra("loaiUser", "NV");
                                break;
                        }

                        startActivity(intent);
                        finish();
                        break;
                    case R.id.menu_quet:
                        Intent intent1 = new Intent(HomeActivity.this, QRScanActivity.class);
                        switch (vitriTapHienTai) {
                            case 0:
                                intent1.putExtra("loai", "ds");
                                break;
                            case 1:
                                intent1.putExtra("loai", "dg");
                                break;
                            default:
                                intent1.putExtra("loai", "nv");
                                break;
                        }
                        startActivity(intent1);
                        finish();
//                    Toast.makeText(HomeActivity.this, "Quét", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
