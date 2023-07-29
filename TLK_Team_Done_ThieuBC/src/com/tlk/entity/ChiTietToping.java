
package com.tlk.entity;


public class ChiTietToping {
    private Integer maChiTietToping;
    private Integer maChiTietHoaDon;
    private Integer maToping;

    public ChiTietToping() {
    }

    public ChiTietToping(Integer maChiTietToping, Integer maChiTietHoaDon, Integer maToping) {
        this.maChiTietToping = maChiTietToping;
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.maToping = maToping;
    }

    public Integer getMaChiTietToping() {
        return maChiTietToping;
    }

    public void setMaChiTietToping(Integer maChiTietToping) {
        this.maChiTietToping = maChiTietToping;
    }

    public Integer getMaChiTietHoaDon() {
        return maChiTietHoaDon;
    }

    public void setMaChiTietHoaDon(Integer maChiTietHoaDon) {
        this.maChiTietHoaDon = maChiTietHoaDon;
    }

    public Integer getMaToping() {
        return maToping;
    }

    public void setMaToping(Integer maToping) {
        this.maToping = maToping;
    }
    
    
}
