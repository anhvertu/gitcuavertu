package com.example.admin.quanLyThuVien.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViTri implements Parcelable {
    @SerializedName("maViTri")
    @Expose
    private int maViTri;
    @SerializedName("tenViTri")
    @Expose
    private String tenViTri;

    protected ViTri(Parcel in) {
        maViTri = in.readInt();
        tenViTri = in.readString();
    }

    public static final Creator<ViTri> CREATOR = new Creator<ViTri>() {
        @Override
        public ViTri createFromParcel(Parcel in) {
            return new ViTri(in);
        }

        @Override
        public ViTri[] newArray(int size) {
            return new ViTri[size];
        }
    };

    @Override
    public String toString() {
        return "ViTri{" +
                "maViTri=" + maViTri +
                ", tenViTri='" + tenViTri + '\'' +
                '}';
    }

    public int getMaViTri() {
        return maViTri;
    }

    public void setMaViTri(int maViTri) {
        this.maViTri = maViTri;
    }

    public String getTenViTri() {
        return tenViTri;
    }

    public void setTenViTri(String tenViTri) {
        this.tenViTri = tenViTri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maViTri);
        dest.writeString(tenViTri);
    }
}
