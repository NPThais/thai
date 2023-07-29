
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class LoaiSanPham {
    private int maloaisanpham;
    private String tenloaisanpham;

    public LoaiSanPham() {
    }

    public LoaiSanPham(int maloaisanpham, String tenloaisanpham) {
        this.maloaisanpham = maloaisanpham;
        this.tenloaisanpham = tenloaisanpham;
    }

    public int getMaloaisanpham() {
        return maloaisanpham;
    }

    public void setMaloaisanpham(int maloaisanpham) {
        this.maloaisanpham = maloaisanpham;
    }

    public String getTenloaisanpham() {
        return tenloaisanpham;
    }

    public void setTenloaisanpham(String tenloaisanpham) {
        this.tenloaisanpham = tenloaisanpham;
    }

    @Override
    public String toString() {
        return String.valueOf(this.tenloaisanpham);
    }
    
     
}
