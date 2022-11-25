package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class DauSach implements Parcelable {


    @SerializedName("maDauSach")
    @Expose
    private String maDauSach;
    @SerializedName("tenDauSach")
    @Expose
    private String tenDauSach;
    @SerializedName("maTheLoai")
    @Expose
    private int maTheLoai;
    @SerializedName("maNhaXuatBan")
    @Expose
    private int maNhaXuatBan;
    @SerializedName("maTacGia")
    @Expose
    private int maTacGia;
    @SerializedName("noiDungTomLuoc")
    @Expose
    private String noiDungTomLuoc;
    @SerializedName("soLuong")
    @Expose
    private int soLuong;
    @SerializedName("maViTri")
    @Expose
    private int maViTri;
    @SerializedName("ngayXuatBan")
    @Expose
    private String ngayXuatBan;
    @SerializedName("soTrang")
    @Expose
    private int soTrang;
    @SerializedName("gia")
    @Expose
    private int gia;
    @SerializedName("anhDauSach")
    @Expose
    private String anhDauSach;
    @SerializedName("soDanhGia")
    @Expose
    private float soDanhGia;
    @SerializedName("soNguoiDanhGia")
    @Expose
    private int soNguoiDanhGia;


    public DauSach() {
    }

    public DauSach(String maDauSach, String tenDauSach, int maTheLoai, int maNhaXuatBan, int maTacGia, String noiDungTomLuoc, int soLuong, int maViTri, String ngayXuatBan, int soTrang, int gia, String anhDauSach, float soDanhGia, int soNguoiDanhGia) {
        this.maDauSach = maDauSach;
        this.tenDauSach = tenDauSach;
        this.maTheLoai = maTheLoai;
        this.maNhaXuatBan = maNhaXuatBan;
        this.maTacGia = maTacGia;
        this.noiDungTomLuoc = noiDungTomLuoc;
        this.soLuong = soLuong;
        this.maViTri = maViTri;
        this.ngayXuatBan = ngayXuatBan;
        this.soTrang = soTrang;
        this.gia = gia;
        this.anhDauSach = anhDauSach;
        this.soDanhGia = soDanhGia;
        this.soNguoiDanhGia = soNguoiDanhGia;
    }

    protected DauSach(Parcel in) {
        maDauSach = in.readString();
        tenDauSach = in.readString();
        maTheLoai = in.readInt();
        maNhaXuatBan = in.readInt();
        maTacGia = in.readInt();
        noiDungTomLuoc = in.readString();
        soLuong = in.readInt();
        maViTri = in.readInt();
        ngayXuatBan = in.readString();
        soTrang = in.readInt();
        gia = in.readInt();
        anhDauSach = in.readString();
        soDanhGia = in.readFloat();
        soNguoiDanhGia = in.readInt();
    }

    public static final Creator<DauSach> CREATOR = new Creator<DauSach>() {
        @Override
        public DauSach createFromParcel(Parcel in) {
            return new DauSach(in);
        }

        @Override
        public DauSach[] newArray(int size) {
            return new DauSach[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maDauSach);
        dest.writeString(tenDauSach);
        dest.writeInt(maTheLoai);
        dest.writeInt(maNhaXuatBan);
        dest.writeInt(maTacGia);
        dest.writeString(noiDungTomLuoc);
        dest.writeInt(soLuong);
        dest.writeInt(maViTri);
        dest.writeString(ngayXuatBan);
        dest.writeInt(soTrang);
        dest.writeInt(gia);
        dest.writeString(anhDauSach);
        dest.writeFloat(soDanhGia);
        dest.writeInt(soNguoiDanhGia);
    }


    public String getMaDauSach() {
        return maDauSach;
    }

    public void setMaDauSach(String maDauSach) {
        this.maDauSach = maDauSach;
    }

    public String getTenDauSach() {
        return tenDauSach;
    }

    public void setTenDauSach(String tenDauSach) {
        this.tenDauSach = tenDauSach;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public int getMaNhaXuatBan() {
        return maNhaXuatBan;
    }

    public void setMaNhaXuatBan(int maNhaXuatBan) {
        this.maNhaXuatBan = maNhaXuatBan;
    }

    public int getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(int maTacGia) {
        this.maTacGia = maTacGia;
    }

    public String getNoiDungTomLuoc() {
        return noiDungTomLuoc;
    }

    public void setNoiDungTomLuoc(String noiDungTomLuoc) {
        this.noiDungTomLuoc = noiDungTomLuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaViTri() {
        return maViTri;
    }

    public void setMaViTri(int maViTri) {
        this.maViTri = maViTri;
    }

    public String getNgayXuatBan() {
        return ngayXuatBan;
    }

    public void setNgayXuatBan(String ngayXuatBan) {
        this.ngayXuatBan = ngayXuatBan;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getAnhDauSach() {
        return anhDauSach;
    }

    public void setAnhDauSach(String anhDauSach) {
        this.anhDauSach = anhDauSach;
    }

    public float getSoDanhGia() {
        return soDanhGia;
    }

    public void setSoDanhGia(float soDanhGia) {
        this.soDanhGia = soDanhGia;
    }

    public int getSoNguoiDanhGia() {
        return soNguoiDanhGia;
    }

    public void setSoNguoiDanhGia(int soNguoiDanhGia) {
        this.soNguoiDanhGia = soNguoiDanhGia;
    }
}

