package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocGia implements Parcelable {
    @SerializedName("maDocGia")
    @Expose
    private String maDocGia;
    @SerializedName("ten")
    @Expose
    private String ten;
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
    @SerializedName("ngayLamThe")
    @Expose
    private String ngayLamThe;
    @SerializedName("anhDocGia")
    @Expose
    private String anhDocGia;

    public DocGia() {
    }

    public DocGia(String maDocGia, String ten, String email, String ngaySinh, String gioiTinh, String soDienThoai, String diaChi, String ngayLamThe, String anhDocGia) {
        this.maDocGia = maDocGia;
        this.ten = ten;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.ngayLamThe = ngayLamThe;
        this.anhDocGia = anhDocGia;
    }

    protected DocGia(Parcel in) {
        maDocGia = in.readString();
        ten = in.readString();
        email = in.readString();
        ngaySinh = in.readString();
        gioiTinh = in.readString();
        soDienThoai = in.readString();
        diaChi = in.readString();
        ngayLamThe = in.readString();
        anhDocGia = in.readString();
    }

    public static final Creator<DocGia> CREATOR = new Creator<DocGia>() {
        @Override
        public DocGia createFromParcel(Parcel in) {
            return new DocGia(in);
        }

        @Override
        public DocGia[] newArray(int size) {
            return new DocGia[size];
        }
    };

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
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

    public String getNgayLamThe() {
        return ngayLamThe;
    }

    public void setNgayLamThe(String ngayLamThe) {
        this.ngayLamThe = ngayLamThe;
    }

    public String getAnhDocGia() {
        return anhDocGia;
    }

    public void setAnhDocGia(String anhDocGia) {
        this.anhDocGia = anhDocGia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(maDocGia);
        parcel.writeString(ten);
        parcel.writeString(email);
        parcel.writeString(ngaySinh);
        parcel.writeString(gioiTinh);
        parcel.writeString(soDienThoai);
        parcel.writeString(diaChi);
        parcel.writeString(ngayLamThe);
        parcel.writeString(anhDocGia);
    }
}
