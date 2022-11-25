package com.example.admin.quanLyThuVien.Retrofit2;


import com.example.admin.quanLyThuVien.models.DanhGiaBinhLuan;
import com.example.admin.quanLyThuVien.models.DauSach;
import com.example.admin.quanLyThuVien.models.DocGia;
import com.example.admin.quanLyThuVien.models.NhaXuatBan;
import com.example.admin.quanLyThuVien.models.NhanVien;
import com.example.admin.quanLyThuVien.models.Sach;
import com.example.admin.quanLyThuVien.models.TacGia;
import com.example.admin.quanLyThuVien.models.TaiKhoan;
import com.example.admin.quanLyThuVien.models.TheLoai;
import com.example.admin.quanLyThuVien.models.ViTri;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

//gửi nhận đến server
public interface DataClient {

    //gửi dạng file
    @Multipart
    @POST("updateHinhAnh.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);

    //gửi dạng chuỗi
    //thêm đầu sách
    @FormUrlEncoded
    @POST("insertDauSach.php")
    Call<String> InsertDauSach(@Field("maDauSach") String maDauSach
            , @Field("tenDauSach") String tenDauSach
            , @Field("maTheLoai") int maTheLoai
            , @Field("maNhaXuatBan") int maNhaXuatBan
            , @Field("maTacGia") int maTacGia
            , @Field("noiDungTomLuoc") String noiDungTomLuoc
            , @Field("soLuong") int soLuong
            , @Field("maViTri") int maViTri
            , @Field("ngayXuatBan") String ngayXuatBan
            , @Field("soTrang") int soTrang
            , @Field("gia") int gia
            , @Field("anhDauSach") String anhDauSach
            , @Field("soDanhGia") float soDanhGia
            , @Field("soNguoiDanhGia") int soNguoiDanhGia);

    //gửi dạng chuỗi
    //thêm sách
    @FormUrlEncoded
    @POST("insertSach.php")
    Call<String> InsertSach(@Field("maSach") String maSach
            , @Field("tenSach") String tenSach
            , @Field("tinhTrangSach") String tinhTrangSach
            , @Field("choMuon") String choMuon
            , @Field("maDauSach") String maDauSach);

    //gửi dạng chuỗi
    //thêm người dùng
    @FormUrlEncoded
    @POST("insertDocGia.php")
    Call<String> InsertDocGia(@Field("maDocGia") String maDocGia
            , @Field("ten") String ten
            , @Field("email") String email
            , @Field("ngaySinh") String ngaySinh
            , @Field("gioiTinh") String gioiTinh
            , @Field("soDienThoai") String soDienThoai
            , @Field("diaChi") String diaChi
            , @Field("ngayLamThe") String ngayLamThe
            , @Field("anhDocGia") String anhDocGia);

    //thêm nhân viên
    @FormUrlEncoded
    @POST("insertNhanVien.php")
    Call<String> InsertNhanVien(@Field("maNhanVien") String maNhanVien
            , @Field("ten") String ten
            , @Field("chucVu") String chucVu
            , @Field("email") String email
            , @Field("ngaySinh") String ngaySinh
            , @Field("gioiTinh") String gioiTinh
            , @Field("soDienThoai") String soDienThoai
            , @Field("diaChi") String diaChi
            , @Field("ngayVaoLam") String ngayVaoLam
            , @Field("anhNhanVien") String anhNhanVien);

    //thêm bình luận
    @FormUrlEncoded
    @POST("insertDanhGiaBinhLuan.php")
    Call<String> InsertDanhGiaBinhLuan(@Field("moTa") String moTa
            , @Field("soDanhGia") int soDanhGia
            , @Field("soLuotThich") int soLuotThich
            , @Field("ngayDang") String ngayDang
            , @Field("maDocGia") String maDocGia
            , @Field("maDauSach") String maDauSach);

    //thêm tài Khoản
    @FormUrlEncoded
    @POST("insertTaiKhoan.php")
    Call<String> InsertTaiKhoan(@Field("taiKhoan") String taiKhoan
            , @Field("matKhau") String matKhau
            , @Field("quyenHan") String quyenHan
            , @Field("maNguoiDung") String maNguoiDung);

    //thêm đề mục
    @FormUrlEncoded
    @POST("insertDeMuc.php")
    Call<String> InsertDeMuc(@Field("ten") String ten
            , @Field("loaiDeMuc") String loaiDeMuc);

    //lấy dữ liệu ds đầu sách
    @FormUrlEncoded
    @POST("getDanhSachDauSach.php")
    Call<List<DauSach>> GetListDauSach(@Field("key") String key);

    //lấy dữ liệu ds sách
    @FormUrlEncoded
    @POST("getDanhSachSach.php")
    Call<List<Sach>> GetListSach(@Field("maDauSach") String maDauSach);

    //lấy dữ liệu ds độc giả
    @FormUrlEncoded
    @POST("getDanhSachDocGia.php")
    Call<List<DocGia>> GetListDocGia(@Field("key") String key);

    //lấy dữ liệu ds nhân viên
    @FormUrlEncoded
    @POST("getDanhSachNhanVien.php")
    Call<List<NhanVien>> GetListNhanVien(@Field("key") String key);

    //lấy dữ liệu đầu sách
    @FormUrlEncoded
    @POST("getDauSachData.php")
    Call<List<DauSach>> GetDuLieuDauSach(@Field("maDauSach") String maDauSach);

    //lấy dữ liệu user dộc giả
    @FormUrlEncoded
    @POST("getDocGiaUser.php")
    Call<List<DocGia>> GetDocGiaUser(@Field("maUser") String maUser);

    //lấy dữ liệu user là nhân viên
    @FormUrlEncoded
    @POST("getNhanVienUser.php")
    Call<List<NhanVien>> GetNhanVienUser(@Field("maUser") String maUser);

    //lấy dữ danh sách binh luận
    @FormUrlEncoded
    @POST("getDanhSachDanhGiaBinhLuan.php")
    Call<List<DanhGiaBinhLuan>> GetDuLieuBinhLuan(@Field("maDauSach") String maDauSach
            , @Field("batDau") int batDau);

    //lấy dữ liệu sách
    @FormUrlEncoded
    @POST("getSachData.php")
    Call<List<Sach>> GetDuLieuSach(@Field("maSach") String maSach);

    //lấy danh sach tài khoản
    @FormUrlEncoded
    @POST("getDanhSachTaiKhoan.php")
    Call<List<TaiKhoan>> GetListTaiKhoan(@Field("key") String key);

    //lấy du lieu tài khoản
    @FormUrlEncoded
    @POST("getTaiKhoanData.php")
    Call<List<TaiKhoan>> GetDuLieuTaiKhoan(@Field("maNguoiDung") String maNguoiDung);

    //lấy mã sách cuối
    @FormUrlEncoded
    @POST("getMaSachCuoi.php")
    Call<String> GetMaSachCuoi(@Field("key") String key);

    //lấy dữ danh sách thể loại
    @FormUrlEncoded
    @POST("getDanhSachTheLoai.php")
    Call<List<TheLoai>> GetListTheLoai(@Field("key") String key);

    //lấy dữ danh sách vị trí
    @FormUrlEncoded
    @POST("getDanhSachViTri.php")
    Call<List<ViTri>> GetListViTri(@Field("key") String key);

    //lấy dữ danh sách tác giả
    @FormUrlEncoded
    @POST("getDanhSachTacGia.php")
    Call<List<TacGia>> GetListTacGia(@Field("key") String key);

    //lấy dữ danh sách nhà xuất bản
    @FormUrlEncoded
    @POST("getDanhSachNhaXuatBan.php")
    Call<List<NhaXuatBan>> GetListNhaXuatBan(@Field("key") String key);

    //gửi dạng chuỗi
    //cật nhật đầu sách
    @FormUrlEncoded
    @POST("updateDauSach.php")
    Call<String> UpdateDauSach(@Field("maDauSach") String maDauSach
            , @Field("tenDauSach") String tenDauSach
            , @Field("maTheLoai") int maTheLoai
            , @Field("maNhaXuatBan") int maNhaXuatBan
            , @Field("maTacGia") int maTacGia
            , @Field("noiDungTomLuoc") String noiDungTomLuoc
            , @Field("soLuong") int soLuong
            , @Field("maViTri") int maViTri
            , @Field("ngayXuatBan") String ngayXuatBan
            , @Field("soTrang") int soTrang
            , @Field("gia") int gia
            , @Field("anhDauSach") String anhDauSach
            , @Field("soDanhGia") float soDanhGia
            , @Field("soNguoiDanhGia") int soNguoiDanhGia);

    //gửi dạng chuỗi
    //cật nhật sách
    @FormUrlEncoded
    @POST("updateSach.php")
    Call<String> UpdateSach(@Field("maSach") String maSach
            , @Field("tenSach") String tenSach
            , @Field("tinhTrangSach") String tinhTrangSach
            , @Field("choMuon") String choMuon
            , @Field("maDauSach") String maDauSach);

    //gửi dạng chuỗi
    //cật nhật đọc giả
    @FormUrlEncoded
    @POST("updateDocGia.php")
    Call<String> UpdateDocGia(@Field("maDocGia") String maDocGia
            , @Field("ten") String ten
            , @Field("email") String email
            , @Field("ngaySinh") String ngaySinh
            , @Field("gioiTinh") String gioiTinh
            , @Field("soDienThoai") String soDienThoai
            , @Field("diaChi") String diaChi
            , @Field("ngayLamThe") String ngayLamThe
            , @Field("anhDocGia") String anhDocGia
            , @Field("anhCu") String anhCu);


    //cật nhật nhân viên
    @FormUrlEncoded
    @POST("updateNhanVien.php")
    Call<String> UpdateNhanVien(@Field("maNhanVien") String maNhanVien
            , @Field("ten") String ten
            , @Field("chucVu") String chucVu
            , @Field("email") String email
            , @Field("ngaySinh") String ngaySinh
            , @Field("gioiTinh") String gioiTinh
            , @Field("soDienThoai") String soDienThoai
            , @Field("diaChi") String diaChi
            , @Field("ngayVaoLam") String ngayVaoLam
            , @Field("anhNhanVien") String anhNhanVien
            , @Field("anhCu") String anhCu);

    //cật nhật tài khoản
    @FormUrlEncoded
    @POST("updateTaiKhoan.php")
    Call<String> UpdateTaiKhoan(@Field("taiKhoan") String taiKhoan
            , @Field("matKhau") String matKhau);

    //cật nhật đề mục
    @FormUrlEncoded
    @POST("updateDeMuc.php")
    Call<String> UpdateDeMuc(@Field("ma") int ma
            , @Field("ten") String ten
            , @Field("loaiDeMuc") String loaiDeMuc);


    //xóa đầu sách
    @FormUrlEncoded
    @POST("deleteDauSach.php")
    Call<String> DeleteDauSach(@Field("maDauSach") String maDauSach
            , @Field("anhDauSach") String anhDauSach);

    //xóa sách
    @FormUrlEncoded
    @POST("deleteSach.php")
    Call<String> DeleteSach(@Field("maSach") String maSach);

    //xóa đọc giả
    @FormUrlEncoded
    @POST("deleteDocGia.php")
    Call<String> DeleteDocGia(@Field("maDocGia") String maDocGia
            , @Field("anhDocGia") String anhDocGia);

    //xóa nhân viên
    @FormUrlEncoded
    @POST("deleteNhanVien.php")
    Call<String> DeleteNhanVien(@Field("maNhanVien") String maNhanVien
            , @Field("anhNhanVien") String anhNhanVien);

    //xóa đề mục
    @FormUrlEncoded
    @POST("deleteDeMuc.php")
    Call<String> DeleteDeMuc(@Field("ma") int ma
            , @Field("loaiDeMuc") String loaiDeMuc);

    //kiểm tra dăng nhập
    @FormUrlEncoded
    @POST("kiemTraLogin.php")
    Call<ResponseBody> CheckLogin(@Field("taiKhoan") String taiKhoan
            , @Field("matKhau") String matKhau);

    //kiểm tra tài khoản
    @FormUrlEncoded
    @POST("checkTaiKhoan.php")
    Call<ResponseBody> CheckTaiKhoan(@Field("taiKhoan") String taiKhoan);

    //kiểm tra tài khoản
    @FormUrlEncoded
    @POST("checkDoiMatKhau.php")
    Call<ResponseBody> CheckDoiMatKhau(@Field("email") String email);
}
