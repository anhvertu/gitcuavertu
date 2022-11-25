package com.example.admin.quanLyThuVien.Retrofit2;


//cung cấp đường dẫn ra
public class APIUtils {
    public static final String Base_Url = "http://192.168.56.1/QuanLyThuVien/";

    public static DataClient getData() {
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
