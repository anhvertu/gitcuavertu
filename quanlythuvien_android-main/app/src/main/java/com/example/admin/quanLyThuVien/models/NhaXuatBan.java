package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.quanLyThuVien.activitys.CommonVariables;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhaXuatBan implements Parcelable {
    @SerializedName("maNhaXuatBan")
    @Expose
    private int maNhaXuatBan;
    @SerializedName("tenNhaXuatBan")
    @Expose
    private String tenNhaXuatBan;

    public NhaXuatBan() {
    }

    public NhaXuatBan(int maNhaXuatBan, String tenNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
        this.tenNhaXuatBan = tenNhaXuatBan;
    }

    @Override
    public String toString() {
        return "NhaXuatBan{" +
                "maNhaXuatBan=" + maNhaXuatBan +
                ", tenNhaXuatBan='" + tenNhaXuatBan + '\'' +
                '}';
    }

    protected NhaXuatBan(Parcel in) {
        maNhaXuatBan = in.readInt();
        tenNhaXuatBan = in.readString();
    }

    public static final Creator<NhaXuatBan> CREATOR = new Creator<NhaXuatBan>() {
        @Override
        public NhaXuatBan createFromParcel(Parcel in) {
            return new NhaXuatBan(in);
        }

        @Override
        public NhaXuatBan[] newArray(int size) {
            return new NhaXuatBan[size];
        }
    };

    public int getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(int maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public String getTenNhaXuatBan() {
        return tenNhaXuatBan;
    }

    public void setTenNhaXuatBan(String tenNhaXuatBan) {
        this.tenNhaXuatBan = tenNhaXuatBan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maNhaXuatBan);
        dest.writeString(tenNhaXuatBan);
    }

}
