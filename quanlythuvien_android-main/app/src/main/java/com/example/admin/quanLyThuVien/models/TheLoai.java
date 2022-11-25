package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TheLoai implements Parcelable {
    @SerializedName("maTheLoai")
    @Expose
    private int maTheLoai;
    @SerializedName("tenTheLoai")
    @Expose
    private String tenTheLoai;

    public TheLoai() {
    }

    public TheLoai(int maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
    }

    protected TheLoai(Parcel in) {
        maTheLoai = in.readInt();
        tenTheLoai = in.readString();
    }


    @Override
    public String toString() {
        return "TheLoai{" +
                "maTheLoai=" + maTheLoai +
                ", tenTheLoai='" + tenTheLoai + '\'' +
                '}';
    }

    public static final Creator<TheLoai> CREATOR = new Creator<TheLoai>() {
        @Override
        public TheLoai createFromParcel(Parcel in) {
            return new TheLoai(in);
        }

        @Override
        public TheLoai[] newArray(int size) {
            return new TheLoai[size];
        }
    };

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maTheLoai);
        dest.writeString(tenTheLoai);
    }
}
