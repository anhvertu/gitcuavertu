package com.example.admin.quanLyThuVien.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.fragments.BookFragment;
import com.example.admin.quanLyThuVien.fragments.ReaderFragment;
import com.example.admin.quanLyThuVien.fragments.StaffFragment;

public class CatalogAdapter extends SmartFragmentStatePagerAdapter {
    private Context mContext;
    private static int NUM_ITEMS = 3;

    public CatalogAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new BookFragment();
                case 1:
                    return new ReaderFragment();
                default:
                    return new StaffFragment();
            }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Đầu Sách";
                case 1:
                    return "Độc giả";
                default:
                    return "Nhân viên";
            }
    }

    @Override
    public int getCount() {
        CommonVariables commonVariables = (CommonVariables) mContext.getApplicationContext();
        if (commonVariables.getQuyenUser().equals("DG"))
            return 1;

        return NUM_ITEMS;
    }

    // Force a refresh of the page when a different fragment is displayed
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
