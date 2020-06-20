package com.doan.quanlycachly.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ResponseProfile {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quoc_tich")
    @Expose
    private String quocTich;
    @SerializedName("CMND")
    @Expose
    private String cMND;
    @SerializedName("dia_chi")
    @Expose
    private String diaChi;
    @SerializedName("gioi_tinh")
    @Expose
    private String gioiTinh;
    @SerializedName("ho_ten")
    @Expose
    private String hoTen;
    @SerializedName("nam_sinh")
    @Expose
    private Integer namSinh;
    @SerializedName("tinh_thanh")
    @Expose
    private String tinhThanh;
    @SerializedName("quan_huyen")
    @Expose
    private String quanHuyen;
    @SerializedName("phuong_xa")
    @Expose
    private String phuongXa;
    @SerializedName("lo_trinh_di_chuyen")
    @Expose
    private String loTrinhDiChuyen;
    @SerializedName("buon_non")
    @Expose
    private String buonNon;
    @SerializedName("dau_hong")
    @Expose
    private String dauHong;
    @SerializedName("ho")
    @Expose
    private String ho;
    @SerializedName("kho_tho")
    @Expose
    private String khoTho;
    @SerializedName("sot")
    @Expose
    private String sot;
    @SerializedName("tieu_chay")
    @Expose
    private String tieuChay;

    @SerializedName("ngay_bat_dau")
    @Expose
    private Date ngayBatDau;

    @SerializedName("so_ngay")
    @Expose
    private int soNgayCL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getCMND() {
        return cMND;
    }

    public void setCMND(String cMND) {
        this.cMND = cMND;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Integer getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(Integer namSinh) {
        this.namSinh = namSinh;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getLoTrinhDiChuyen() {
        return loTrinhDiChuyen;
    }

    public void setLoTrinhDiChuyen(String loTrinhDiChuyen) {
        this.loTrinhDiChuyen = loTrinhDiChuyen;
    }

    public String getBuonNon() {
        return buonNon;
    }

    public void setBuonNon(String buonNon) {
        this.buonNon = buonNon;
    }

    public String getDauHong() {
        return dauHong;
    }

    public void setDauHong(String dauHong) {
        this.dauHong = dauHong;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getKhoTho() {
        return khoTho;
    }

    public void setKhoTho(String khoTho) {
        this.khoTho = khoTho;
    }

    public String getSot() {
        return sot;
    }

    public void setSot(String sot) {
        this.sot = sot;
    }

    public String getTieuChay() {
        return tieuChay;
    }

    public void setTieuChay(String tieuChay) {
        this.tieuChay = tieuChay;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    @SuppressLint("SimpleDateFormat")
    public void setNgayBatDau(String ngayBatDau) {
        SimpleDateFormat format;

        if (ngayBatDau.endsWith("Z")) {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        }

        try {
            Date time = format.parse(ngayBatDau);
            assert time != null;

            this.ngayBatDau =time;
        } catch (Exception e) {
            this.ngayBatDau = new Date();
        }
    }

    public int getSoNgayCL() {
        return soNgayCL;
    }

    public void setSoNgayCL(int soNgayCL) {
        this.soNgayCL = soNgayCL;
    }
}
