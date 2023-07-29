
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class TenThucUong {
    private String tenThucUong;

    public TenThucUong() {
    }

    public TenThucUong(String tenThucUong) {
        this.tenThucUong = tenThucUong;
    }

    public String getTenThucUong() {
        return tenThucUong;
    }

    public void setTenThucUong(String tenThucUong) {
        this.tenThucUong = tenThucUong;
    }
    
    @Override
    public String toString() {
        return this.tenThucUong;
    }
    
    
}
