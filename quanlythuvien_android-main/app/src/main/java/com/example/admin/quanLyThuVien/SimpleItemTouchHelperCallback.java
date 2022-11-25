package com.example.admin.quanLyThuVien;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.example.admin.quanLyThuVien.interfaces.ItemTouchListenner;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private ItemTouchListenner mListenner;
    private CommonVariables commonVariables;

    public SimpleItemTouchHelperCallback(ItemTouchListenner mListenner, CommonVariables commonVariables) {
        this.mListenner = mListenner;
        this.commonVariables = commonVariables;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (!commonVariables.getQuyenUser().equals("DG")) {
            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlag, swipeFlag);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView,
                          RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mListenner.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListenner.swipe(viewHolder.getAdapterPosition(), direction);
    }
}