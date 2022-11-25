package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaiKhoan implements Parcelable {
    @SerializedName("taiKhoan")
    @Expose
    private String taiKhoan;
    @SerializedName("matKhau")
    @Expose
    private String matKhau;
    @SerializedName("quyenHan")
    @Expose
    private String quyenHan;
    @SerializedName("maNguoiDung")
    @Expose
    private String maNguoiDung;

    public TaiKhoan() {
    }

    public TaiKhoan(String taiKhoan, String matKhau, String quyenHan, String maNguoiDung) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.quyenHan = quyenHan;
        this.maNguoiDung = maNguoiDung;
    }

    protected TaiKhoan(Parcel in) {
        taiKhoan = in.readString();
        matKhau = in.readString();
        quyenHan = in.readString();
        maNguoiDung = in.readString();
    }

    public static final Creator<TaiKhoan> CREATOR = new Creator<TaiKhoan>() {
        @Override
        public TaiKhoan createFromParcel(Parcel in) {
            return new TaiKhoan(in);
        }

        @Override
        public TaiKhoan[] newArray(int size) {
            return new TaiKhoan[size];
        }
    };

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQuyenHan() {
        return quyenHan;
    }

    public void setQuyenHan(String quyenHan) {
        this.quyenHan = quyenHan;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taiKhoan);
        dest.writeString(matKhau);
        dest.writeString(quyenHan);
        dest.writeString(maNguoiDung);
    }
}
