package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DanhGiaBinhLuan implements Parcelable {
    @SerializedName("maDanhGia")
    @Expose
    private String maDanhGia;
    @SerializedName("tenNguoiDung")
    @Expose
    private String tenNguoiDung;
    @SerializedName("moTa")
    @Expose
    private String moTa;
    @SerializedName("soDanhGia")
    @Expose
    private int soDanhGia;
    @SerializedName("soLuotThich")
    @Expose
    private int soLuotThich;
    @SerializedName("ngayDang")
    @Expose
    private String ngayDang;
    @SerializedName("anhNguoiDung")
    @Expose
    private String anhNguoiDung;
    @SerializedName("maDauSach")
    @Expose
    private String maDauSach;


    public DanhGiaBinhLuan() {
    }

    public DanhGiaBinhLuan(String maDanhGia, String tenNguoiDung, String moTa, int soDanhGia, int soLuotThich, String ngayDang, String anhNguoiDung, String maDauSach) {
        this.maDanhGia = maDanhGia;
        this.tenNguoiDung = tenNguoiDung;
        this.moTa = moTa;
        this.soDanhGia = soDanhGia;
        this.soLuotThich = soLuotThich;
        this.ngayDang = ngayDang;
        this.anhNguoiDung = anhNguoiDung;
        this.maDauSach = maDauSach;
    }

    protected DanhGiaBinhLuan(Parcel in) {
        maDanhGia = in.readString();
        tenNguoiDung = in.readString();
        moTa = in.readString();
        soDanhGia = in.readInt();
        soLuotThich = in.readInt();
        ngayDang = in.readString();
        anhNguoiDung = in.readString();
        maDauSach = in.readString();
    }

    public static final Creator<DanhGiaBinhLuan> CREATOR = new Creator<DanhGiaBinhLuan>() {
        @Override
        public DanhGiaBinhLuan createFromParcel(Parcel in) {
            return new DanhGiaBinhLuan(in);
        }

        @Override
        public DanhGiaBinhLuan[] newArray(int size) {
            return new DanhGiaBinhLuan[size];
        }
    };

    public String getMaDanhGia() {
        return maDanhGia;
    }

    public void setMaDanhGia(String maDanhGia) {
        this.maDanhGia = maDanhGia;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getSoDanhGia() {
        return soDanhGia;
    }

    public void setSoDanhGia(int soDanhGia) {
        this.soDanhGia = soDanhGia;
    }

    public int getSoLuotThich() {
        return soLuotThich;
    }

    public void setSoLuotThich(int soLuotThich) {
        this.soLuotThich = soLuotThich;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getAnhNguoiDung() {
        return anhNguoiDung;
    }

    public void setAnhNguoiDung(String anhNguoiDung) {
        this.anhNguoiDung = anhNguoiDung;
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
        parcel.writeString(maDanhGia);
        parcel.writeString(tenNguoiDung);
        parcel.writeString(moTa);
        parcel.writeInt(soDanhGia);
        parcel.writeInt(soLuotThich);
        parcel.writeString(ngayDang);
        parcel.writeString(anhNguoiDung);
        parcel.writeString(maDauSach);
    }
}
