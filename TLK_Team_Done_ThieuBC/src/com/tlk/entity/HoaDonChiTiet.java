
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class HoaDonChiTiet {
    private Integer mahoadonchitiet;
    private String mahoadon;
    private String masanpham;
    private Integer soluong;
    private String tileduong;
    private String tileda;
    private double thanhtien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(Integer mahoadonchitiet, String mahoadon, String masanpham, Integer soluong, String tileduong, String tileda, double thanhtien) {
        this.mahoadonchitiet = mahoadonchitiet;
        this.mahoadon = mahoadon;
        this.masanpham = masanpham;
        this.soluong = soluong;
        this.tileduong = tileduong;
        this.tileda = tileda;
        this.thanhtien = thanhtien;
    }

    public Integer getMahoadonchitiet() {
        return mahoadonchitiet;
    }

    public void setMahoadonchitiet(Integer mahoadonchitiet) {
        this.mahoadonchitiet = mahoadonchitiet;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }

    public String getTileduong() {
        return tileduong;
    }

    public void setTileduong(String tileduong) {
        this.tileduong = tileduong;
    }

    public String getTileda() {
        return tileda;
    }

    public void setTileda(String tileda) {
        this.tileda = tileda;
    }

    public double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(double thanhtien) {
        this.thanhtien = thanhtien;
    }

  
    
    
    
}
