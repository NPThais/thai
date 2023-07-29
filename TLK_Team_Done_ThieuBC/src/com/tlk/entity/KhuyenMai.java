
package com.tlk.entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author MACBOOK Pro
 */
public class KhuyenMai {
    private String makhuyenmai;
    private Date ngaytao;
    private boolean giamtheophantram;
    private Double giatrikhuyenmai;
    private Date ngaybatdau;
    private Date ngayketthuc;
    private String trangthaiapdung;

    public KhuyenMai() {
    }

    public KhuyenMai(String makhuyenmai, Date ngaytao, boolean giamtheophantram, Double giatrikhuyenmai, Date ngaybatdau, Date ngayketthuc, String trangthaiapdung) {
        this.makhuyenmai = makhuyenmai;
        this.ngaytao = ngaytao;
        this.giamtheophantram = giamtheophantram;
        this.giatrikhuyenmai = giatrikhuyenmai;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
        this.trangthaiapdung = trangthaiapdung;
    }

    public String getMakhuyenmai() {
        return makhuyenmai;
    }

    public void setMakhuyenmai(String makhuyenmai) {
        this.makhuyenmai = makhuyenmai;
    }

    public Date getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Date ngaytao) {
        this.ngaytao = ngaytao;
    }

    public boolean isGiamtheophantram() {
        return giamtheophantram;
    }

    public void setGiamtheophantram(boolean giamtheophantram) {
        this.giamtheophantram = giamtheophantram;
    }

    public Double getGiatrikhuyenmai() {
        return giatrikhuyenmai;
    }

    public void setGiatrikhuyenmai(Double giatrikhuyenmai) {
        this.giatrikhuyenmai = giatrikhuyenmai;
    }

    public Date getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(Date ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public Date getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(Date ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public String getTrangthaiapdung() {
        return trangthaiapdung;
    }

    public void setTrangthaiapdung(String trangthaiapdung) {
        this.trangthaiapdung = trangthaiapdung;
    }

    
    @Override
    public String toString() {
        String giatri;
        if (isGiamtheophantram()) {
            giatri = "Giảm " + String.format("%,.0f", getGiatrikhuyenmai()) + "%";
        } else {
            giatri = "Giảm " + String.format("%,.0f", getGiatrikhuyenmai()) + "VND";

        }
        return giatri;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        KhuyenMai other = (KhuyenMai) obj;
        return Objects.equals(this.getMakhuyenmai(), other.getMakhuyenmai());
    }

}
