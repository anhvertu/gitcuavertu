package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TacGia implements Parcelable {
    @SerializedName("maTacGia")
    @Expose
    private int maTacGia;
    @SerializedName("tenTacGia")
    @Expose
    private String tenTacGia;


    public TacGia() {
    }

    public TacGia(int maTacGia, String tenTacGia) {
        this.maTacGia = maTacGia;
        this.tenTacGia = tenTacGia;
    }

    @Override
    public String toString() {
        return "TacGia{" +
                "maTacGia=" + maTacGia +
                ", tenTacGia='" + tenTacGia + '\'' +
                '}';
    }

    protected TacGia(Parcel in) {
        maTacGia = in.readInt();
        tenTacGia = in.readString();
    }

    public static final Creator<TacGia> CREATOR = new Creator<TacGia>() {
        @Override
        public TacGia createFromParcel(Parcel in) {
            return new TacGia(in);
        }

        @Override
        public TacGia[] newArray(int size) {
            return new TacGia[size];
        }
    };

    public int getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(int maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maTacGia);
        dest.writeString(tenTacGia);
    }
}
