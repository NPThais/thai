
package com.tlk.panel;

import com.tlk.dao.NhanVienDAO;
import com.tlk.utils.Auth;
import com.tlk.utils.MsgBox;


public class DoiMatKhauPanel extends javax.swing.JPanel {
    NhanVienDAO dao = new NhanVienDAO();

    public DoiMatKhauPanel() {
        initComponents();
    }

    public void doiMatKhau(){
        String manv = txtTenDangNhap.getText();
        String matKhau = new String(txtMatKhau.getPassword());
        String matKhauMoi = new String(txtMatKhau1.getPassword());
        String matKhauMoi2 = new String(txtMatKhau2.getPassword());
        if(!manv.equalsIgnoreCase(Auth.user.getTendangnhap())){
            MsgBox.alert(this, "Sai tên đăng nhập");
        }
        else if(!matKhau.equals(Auth.user.getMatkhau().trim())){
            System.out.println(matKhau);
            System.out.println(Auth.user.getMatkhau());
            MsgBox.alert(this,"Sai mật khẩu!");
        }
        else if(!matKhauMoi.equals(matKhauMoi2)){
            MsgBox.alert(this,"Xác nhận mật khẩu không đúng!");
        }
        else{
            Auth.user.setMatkhau(matKhauMoi);
            dao.update(Auth.user);
            MsgBox.alert(this,"Đổi mật khẩu thành công!");
        }
    }
    
    void clearForm(){
        txtTenDangNhap.setText("");
        txtTenDangNhap.setText("");
        txtTenDangNhap.setText("");
        txtMatKhau2.setText("");
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTenDangNhap = new com.tlk.swing.MyTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMatKhau = new com.tlk.swing.MyPasswordField();
        txtMatKhau1 = new com.tlk.swing.MyPasswordField();
        txtMatKhau2 = new com.tlk.swing.MyPasswordField();
        btnThucHien = new com.tlk.swing.ButtonBig1();
        btnHuy = new com.tlk.swing.ButtonBig1();
        jPanel1 = new javax.swing.JPanel();

        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("ĐỔI MẬT KHẨU");
        add(jLabel1);
        jLabel1.setBounds(679, 34, 180, 32);

        txtTenDangNhap.setHint("Tên đăng nhập\n\n");
        add(txtTenDangNhap);
        txtTenDangNhap.setBounds(599, 164, 300, 38);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Tên đăng nhập");
        add(jLabel2);
        jLabel2.setBounds(599, 124, 119, 25);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mật khẩu");
        add(jLabel3);
        jLabel3.setBounds(599, 214, 74, 25);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Mật khẩu mới");
        add(jLabel4);
        jLabel4.setBounds(599, 304, 110, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Xác nhận mật khẩu");
        add(jLabel5);
        jLabel5.setBounds(599, 394, 151, 25);

        txtMatKhau.setHint("Mật khẩu\n");
        add(txtMatKhau);
        txtMatKhau.setBounds(599, 254, 300, 38);

        txtMatKhau1.setHint("Mật khẩu mới");
        add(txtMatKhau1);
        txtMatKhau1.setBounds(599, 344, 300, 38);

        txtMatKhau2.setHint("Xác nhận mật khẩu");
        add(txtMatKhau2);
        txtMatKhau2.setBounds(599, 434, 300, 38);

        btnThucHien.setText("Thực hiện");
        btnThucHien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThucHienActionPerformed(evt);
            }
        });
        add(btnThucHien);
        btnThucHien.setBounds(666, 525, 113, 39);

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        add(btnHuy);
        btnHuy.setBounds(791, 525, 117, 39);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));
        add(jPanel1);
        jPanel1.setBounds(0, 0, 550, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThucHienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThucHienActionPerformed
        doiMatKhau();
    }//GEN-LAST:event_btnThucHienActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        clearForm();
    }//GEN-LAST:event_btnHuyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonBig1 btnHuy;
    private com.tlk.swing.ButtonBig1 btnThucHien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private com.tlk.swing.MyPasswordField txtMatKhau;
    private com.tlk.swing.MyPasswordField txtMatKhau1;
    private com.tlk.swing.MyPasswordField txtMatKhau2;
    private com.tlk.swing.MyTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
