
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class SanPham {
    private String masanpham;
    private String tensanpham;
    private double gia;
    private String size;
    private int loaisanpham;
    private String Hinh;

    public SanPham() {
    }

    public SanPham(String masanpham, String tensanpham, double gia, String size, int loaisanpham, String Hinh) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.gia = gia;
        this.size = size;
        this.loaisanpham = loaisanpham;
        this.Hinh = Hinh;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getLoaisanpham() {
        return loaisanpham;
    }

    public void setLoaisanpham(int loaisanpham) {
        this.loaisanpham = loaisanpham;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    @Override
    public String toString() {
        return "Size -- "+this.size;
    }
 
   
    
    
}
