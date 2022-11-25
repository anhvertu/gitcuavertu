package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sach implements Parcelable {
    @SerializedName("maSach")
    @Expose
    private String maSach;
    @SerializedName("tenSach")
    @Expose
    private String tenSach;
    @SerializedName("tinhTrangSach")
    @Expose
    private String tinhTrangSach;
    @SerializedName("choMuon")
    @Expose
    private String choMuon;
    @SerializedName("maDauSach")
    @Expose
    private String maDauSach;


    public Sach() {
    }

    public Sach(String maSach, String tenSach, String tinhTrangSach, String choMuon, String maDauSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tinhTrangSach = tinhTrangSach;
        this.choMuon = choMuon;
        this.maDauSach = maDauSach;
    }

    protected Sach(Parcel in) {
        maSach = in.readString();
        tenSach = in.readString();
        tinhTrangSach = in.readString();
        choMuon = in.readString();
        maDauSach = in.readString();
    }

    public static final Creator<Sach> CREATOR = new Creator<Sach>() {
        @Override
        public Sach createFromParcel(Parcel in) {
            return new Sach(in);
        }

        @Override
        public Sach[] newArray(int size) {
            return new Sach[size];
        }
    };

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTinhTrangSach() {
        return tinhTrangSach;
    }

    public void setTinhTrangSach(String tinhTrangSach) {
        this.tinhTrangSach = tinhTrangSach;
    }

    public String getChoMuon() {
        return choMuon;
    }

    public void setChoMuon(String choMuon) {
        this.choMuon = choMuon;
    }

    public String getMaDauSach() {
        return maDauSach;
    }

    public void setMaDauSach(String maDauSach) {
        this.maDauSach = maDauSach;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(maSach);
        parcel.writeString(tenSach);
        parcel.writeString(tinhTrangSach);
        parcel.writeString(choMuon);
        parcel.writeString(maDauSach);
    }
}