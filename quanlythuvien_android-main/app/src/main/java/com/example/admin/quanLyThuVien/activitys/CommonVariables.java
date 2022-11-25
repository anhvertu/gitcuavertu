package com.example.admin.quanLyThuVien.activitys;

import android.app.Application;

import com.example.admin.quanLyThuVien.models.DocGia;
import com.example.admin.quanLyThuVien.models.NhaXuatBan;
import com.example.admin.quanLyThuVien.models.NhanVien;
import com.example.admin.quanLyThuVien.models.TacGia;
import com.example.admin.quanLyThuVien.models.TheLoai;
import com.example.admin.quanLyThuVien.models.ViTri;

import java.util.List;

public class CommonVariables extends Application {

    private List<TheLoai> mDSTheLoai;
    private List<ViTri> mDSViTri;
    private List<TacGia> mDSTacGia;
    private List<NhaXuatBan> mDSNhaXuatBan;

    private String quyenUser = "";
    private String maUser = "";
    private DocGia docGiaUser;
    private NhanVien nhanVienUser;

    private String maDauSachCuoi = "";
    private String maDauSachHienTai = "";

    private String maDocGiaCuoi = "";
    private String maDocGiaHienTai = "";

    private String maNhanVienCuoi = "";
    private String maNhanVienHienTai = "";


    private String maSachCuoi = "";
    private String timTheo = "Không";

    public List<TheLoai> getmDSTheLoai() {
        return mDSTheLoai;
    }
    public void setmDSTheLoai(List<TheLoai> mDSTheLoai) {
        this.mDSTheLoai = mDSTheLoai;
    }

    public List<ViTri> getmDSViTri() {
        return mDSViTri;
    }
    public void setmDSViTri(List<ViTri> mDSViTri) {
        this.mDSViTri = mDSViTri;
    }

    public List<TacGia> getmDSTacGia() {
        return mDSTacGia;
    }
    public void setmDSTacGia(List<TacGia> mDSTacGia) {
        this.mDSTacGia = mDSTacGia;
    }

    public List<NhaXuatBan> getmDSNhaXuatBan() {
        return mDSNhaXuatBan;
    }
    public void setmDSNhaXuatBan(List<NhaXuatBan> mDSNhaXuatBan) {
        this.mDSNhaXuatBan = mDSNhaXuatBan;
    }

    public String getTenTheLoai(int maTheLoai) {
        for (int i = 0; i < mDSTheLoai.size(); i++)
            if (mDSTheLoai.get(i).getMaTheLoai() == maTheLoai)
                return mDSTheLoai.get(i).getTenTheLoai();
        return null;
    }

    public String getTenViTri(int maViTri) {
        for (int i = 0; i < mDSViTri.size(); i++)
            if (mDSViTri.get(i).getMaViTri() == maViTri)
                return mDSViTri.get(i).getTenViTri();
        return null;
    }

    public String getTenTacGia(int maTacGia) {
        for (int i = 0; i < mDSTacGia.size(); i++)
            if (mDSTacGia.get(i).getMaTacGia() == maTacGia)
                return mDSTacGia.get(i).getTenTacGia();
        return null;
    }

    public String getTenNhaXuatBan(int maNhaXuatBan) {
        for (int i = 0; i < mDSNhaXuatBan.size(); i++)
            if (mDSNhaXuatBan.get(i).getMaNhaXuatBan() == maNhaXuatBan)
                return mDSNhaXuatBan.get(i).getTenNhaXuatBan();
        return null;
    }

    public int getMaTheLoai(String tenTheLoai) {
        for (int i = 0; i < mDSTheLoai.size(); i++)
            if (mDSTheLoai.get(i).getTenTheLoai().equals(tenTheLoai))
                return mDSTheLoai.get(i).getMaTheLoai();
        return 0;
    }

    public int getMaViTri(String tenViTri) {
        for (int i = 0; i < mDSViTri.size(); i++)
            if (mDSViTri.get(i).getTenViTri().equals(tenViTri))
                return mDSViTri.get(i).getMaViTri();
        return 0;
    }

    public int getMaTacGia(String tenTacGia) {
        for (int i = 0; i < mDSTacGia.size(); i++)
            if (mDSTacGia.get(i).getTenTacGia().equals(tenTacGia))
                return mDSTacGia.get(i).getMaTacGia();
        return 0;
    }

    public int getMaNhaXuatBan(String tenNhaXuatBan) {
        for (int i = 0; i < mDSNhaXuatBan.size(); i++)
            if (mDSNhaXuatBan.get(i).getTenNhaXuatBan().equals(tenNhaXuatBan))
                return mDSNhaXuatBan.get(i).getMaNhaXuatBan();
        return 0;
    }


    public DocGia getDocGiaUser() {
        return docGiaUser;
    }

    public void setDocGiaUser(DocGia docGiaUser) {
        this.docGiaUser = docGiaUser;
    }

    public NhanVien getNhanVienUser() {
        return nhanVienUser;
    }

    public void setNhanVienUser(NhanVien nhanVienUser) {
        this.nhanVienUser = nhanVienUser;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public String getQuyenUser() {
        return quyenUser;
    }

    public void setQuyenUser(String quyenUser) {
        this.quyenUser = quyenUser;
    }

    public String getMaDocGiaCuoi() {
        return maDocGiaCuoi;
    }

    public void setMaDocGiaCuoi(String maDocGiaCuoi) {
        this.maDocGiaCuoi = maDocGiaCuoi;
    }

    public String getMaDocGiaHienTai() {
        return maDocGiaHienTai;
    }

    public void setMaDocGiaHienTai(String maDocGiaHienTai) {
        this.maDocGiaHienTai = maDocGiaHienTai;
    }

    public String getMaNhanVienCuoi() {
        return maNhanVienCuoi;
    }

    public void setMaNhanVienCuoi(String maNhanVienCuoi) {
        this.maNhanVienCuoi = maNhanVienCuoi;
    }

    public String getMaNhanVienHienTai() {
        return maNhanVienHienTai;
    }

    public void setMaNhanVienHienTai(String maNhanVienHienTai) {
        this.maNhanVienHienTai = maNhanVienHienTai;
    }

    public String getTimTheo() {
        return timTheo;
    }

    public void setTimTheo(String timTheo) {
        this.timTheo = timTheo;
    }

    public String getMaDauSachCuoi() {
        return maDauSachCuoi;
    }

    public void setMaDauSachCuoi(String maDauSachCuoi) {
        this.maDauSachCuoi = maDauSachCuoi;
    }

    public String getMaDauSachHienTai() {
        return maDauSachHienTai;
    }

    public void setMaDauSachHienTai(String maDauSachHienTai) {
        this.maDauSachHienTai = maDauSachHienTai;
    }

    public String getMaSachCuoi() {
        return maSachCuoi;
    }

    public void setMaSachCuoi(String maSachCuoi) {
        this.maSachCuoi = maSachCuoi;
    }

    public int soSachCuoi() {
        String temp = maSachCuoi.substring(2);
        return Integer.parseInt(temp);
    }

    public int kiemTraSoSach() {
        int tam = soSachCuoi(), dem = 0;
        if (tam == 9 || tam == 99 || tam == 999 || tam == 9999) dem++;
        while (tam > 0) {
            tam /= 10;
            dem++;
        }
        return dem;
    }


    public int soDocGiaCuoi() {
        String temp = maDocGiaCuoi.substring(3);
        return Integer.parseInt(temp);
    }

    public int kiemTraSoDocGia() {
        int tam = soDocGiaCuoi(), dem = 0;
        if (tam == 9 || tam == 99 || tam == 999 || tam == 9999) dem++;
        while (tam > 0) {
            tam /= 10;
            dem++;
        }
        return dem;
    }

    public int soNhanVienCuoi() {
        String temp = maNhanVienCuoi.substring(3);
        return Integer.parseInt(temp);
    }

    public int kiemTraSoNhanVien() {
        int tam = soNhanVienCuoi(), dem = 0;
        if (tam == 9 || tam == 99 || tam == 999 || tam == 9999) dem++;
        while (tam > 0) {
            tam /= 10;
            dem++;
        }
        return dem;
    }


    public int soDauSachCuoi() {
        String temp = maDauSachCuoi.substring(3);
        return Integer.parseInt(temp);
    }

    public int kiemTraSo() {
        int tam = soDauSachCuoi(), dem = 0;
        if (tam == 9 || tam == 99 || tam == 999 || tam == 9999) dem++;
        while (tam > 0) {
            tam /= 10;
            dem++;
        }
        return dem;
    }
}
