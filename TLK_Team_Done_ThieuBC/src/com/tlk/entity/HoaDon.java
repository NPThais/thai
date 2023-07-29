
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class HoaDon {
    private String mahoadon;
    private String ngaytao;
    private boolean trangthai;
    private Double tongthanhtoan;
    private Double tongthanhtoanKM;
    private String ghichu;
    private String makhuyenmai;
    private String tendangnhap;

    public HoaDon() {
    }

    public HoaDon(String mahoadon, String ngaytao, boolean trangthai, Double tongthanhtoan, Double tongthanhtoanKM, String ghichu, String makhuyenmai, String tendangnhap) {
        this.mahoadon = mahoadon;
        this.ngaytao = ngaytao;
        this.trangthai = trangthai;
        this.tongthanhtoan = tongthanhtoan;
        this.tongthanhtoanKM = tongthanhtoanKM;
        this.ghichu = ghichu;
        this.makhuyenmai = makhuyenmai;
        this.tendangnhap = tendangnhap;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        this.ngaytao = ngaytao;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public Double getTongthanhtoan() {
        return tongthanhtoan;
    }

    public void setTongthanhtoan(Double tongthanhtoan) {
        this.tongthanhtoan = tongthanhtoan;
    }

    public Double getTongthanhtoanKM() {
        return tongthanhtoanKM;
    }

    public void setTongthanhtoanKM(Double tongthanhtoanKM) {
        this.tongthanhtoanKM = tongthanhtoanKM;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMakhuyenmai() {
        return makhuyenmai;
    }

    public void setMakhuyenmai(String makhuyenmai) {
        this.makhuyenmai = makhuyenmai;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    

   
}
