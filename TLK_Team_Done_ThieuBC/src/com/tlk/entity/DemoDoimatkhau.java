/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tlk.entity;

/**
 *
 * @author MACBOOK Pro
 */
public class DemoDoimatkhau {
     private String tendangnhap;
     private String email;
     private String matkhaumoi;
     private String matkhaunhaplai;

    public DemoDoimatkhau() {
    }

    public DemoDoimatkhau(String tendangnhap, String email, String matkhaumoi, String matkhaunhaplai) {
        this.tendangnhap = tendangnhap;
        this.email = email;
        this.matkhaumoi = matkhaumoi;
        this.matkhaunhaplai = matkhaunhaplai;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhaumoi() {
        return matkhaumoi;
    }

    public void setMatkhaumoi(String matkhaumoi) {
        this.matkhaumoi = matkhaumoi;
    }

    public String getMatkhaunhaplai() {
        return matkhaunhaplai;
    }

    public void setMatkhaunhaplai(String matkhaunhaplai) {
        this.matkhaunhaplai = matkhaunhaplai;
    }
     
     
}
