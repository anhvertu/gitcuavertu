package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhanVien implements Parcelable {
    @SerializedName("maNhanVien")
    @Expose
    private String maNhanVien;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("chucVu")
    @Expose
    private String chucVu;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ngaySinh")
    @Expose
    private String ngaySinh;
    @SerializedName("gioiTinh")
    @Expose
    private String gioiTinh;
    @SerializedName("soDienThoai")
    @Expose
    private String soDienThoai;
    @SerializedName("diaChi")
    @Expose
    private String diaChi;
    @SerializedName("ngayVaoLam")
    @Expose
    private String ngayVaoLam;
    @SerializedName("anhNhanVien")
    @Expose
    private String anhNhanVien;

    public NhanVien() {
    }

    public NhanVien(String maNhanVien, String ten, String chucVu, String email, String ngaySinh, String gioiTinh, String soDienThoai, String diaChi, String ngayVaoLam, String anhNhanVien) {
        this.maNhanVien = maNhanVien;
        this.ten = ten;
        this.chucVu = chucVu;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngayVaoLam = ngayVaoLam;
        this.anhNhanVien = anhNhanVien;
    }

    protected NhanVien(Parcel in) {
        maNhanVien = in.readString();
        ten = in.readString();
        chucVu = in.readString();
        email = in.readString();
        ngaySinh = in.readString();
        gioiTinh = in.readString();
        soDienThoai = in.readString();
        diaChi = in.readString();
        ngayVaoLam = in.readString();
        anhNhanVien = in.readString();
    }

    public static final Creator<NhanVien> CREATOR = new Creator<NhanVien>() {
        @Override
        public NhanVien createFromParcel(Parcel in) {
            return new NhanVien(in);
        }

        @Override
        public NhanVien[] newArray(int size) {
            return new NhanVien[size];
        }
    };

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getAnhNhanVien() {
        return anhNhanVien;
    }

    public void setAnhNhanVien(String anhNhanVien) {
        this.anhNhanVien = anhNhanVien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maNhanVien);
        dest.writeString(ten);
        dest.writeString(chucVu);
        dest.writeString(email);
        dest.writeString(ngaySinh);
        dest.writeString(gioiTinh);
        dest.writeString(soDienThoai);
        dest.writeString(diaChi);
        dest.writeString(ngayVaoLam);
        dest.writeString(anhNhanVien);
    }
}
