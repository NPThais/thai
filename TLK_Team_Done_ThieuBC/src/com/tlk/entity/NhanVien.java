
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class NhanVien {
    private String tendangnhap;
    private String matkhau;
    private String hovaten;
    private String email;
    private boolean vaitro;

    public NhanVien() {
    }

    public NhanVien(String tendangnhap, String matkhau, String hovaten, String email, boolean vaitro) {
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.hovaten = hovaten;
        this.email = email;
        this.vaitro = vaitro;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getHovaten() {
        return hovaten;
    }

    public void setHovaten(String hovaten) {
        this.hovaten = hovaten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVaitro() {
        return vaitro;
    }

    public void setVaitro(boolean vaitro) {
        this.vaitro = vaitro;
    }

    @Override
    public String toString() {
        return this.tendangnhap+"\t\t"+this.matkhau+"\t"+this.hovaten+"\t"+this.email+"\t"+this.vaitro;
    }
    
    
}
