/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tlk.panel;


import com.tlk.dao.NhanVienDAO;
import com.tlk.entity.NhanVien;
import com.tlk.utils.Auth;
import com.tlk.utils.MsgBox;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MACBOOK Pro
 */
public class UserPanel extends javax.swing.JPanel {

    NhanVienDAO nvDao = new NhanVienDAO();

    int row = -1;

    public UserPanel() {
        initComponents();
        tblNhanVien2.fixTable(jScrollPane1);
        this.filltoTable();
        this.row=-1;
        this.updateStatus();
    }

    String hidePassword(String password) {
        StringBuilder hiddenPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            hiddenPassword.append('*');
        }
        return hiddenPassword.toString();
    }

    void filltoTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien2.getModel();
        model.setRowCount(0);
        try {
            String keyWord = txtTimTenNV.getText();
            List<NhanVien> list = nvDao.selectByKeyWord(keyWord);
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getTendangnhap(),
                    hidePassword(nv.getMatkhau()),
                    nv.getHovaten(),
                    nv.getEmail(),
                    nv.isVaitro() ? "Quản lí" : "Nhân viên"

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi dữ liệu");
        }

    }
        private void timKiem() {
        this.filltoTable();
        this.clearForm();
        this.row = -1;
        this.updateStatus();

    }

    void setForm(NhanVien nv) {
        txtTenDangNhap.setText(nv.getTendangnhap());
        txtMatKhau.setText(nv.getMatkhau());
        txtHoTen.setText(nv.getHovaten());
        txtEmail.setText(nv.getEmail());
        rdoNhanVien.setSelected(!nv.isVaitro());
        rdoQuanLi.setSelected(nv.isVaitro());
    }

    void edit() {
        String maNV = (String) tblNhanVien2.getValueAt(this.row, 0);
        NhanVien nv = nvDao.selectByID(maNV);
        this.setForm(nv);
       this.updateStatus();
    }

    void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        this.row = -1;
        this.updateStatus();
        tblNhanVien2.clearSelection();

        groupVaiTro.clearSelection();

txtMatKhau.setEditable(true);
rdoNhanVien.setEnabled(true);
rdoQuanLi.setEnabled(true);
        clearForm2();
    }

    void clearForm2() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtEmail.setText("");
        txtHoTen.setText("");

    }

    void updateStatus() {
        boolean edit = (this.row >= 0);

       
       txtTenDangNhap.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhap.setEnabled(edit);
        btnXoa.setEnabled(edit);
       

    }
     void CheckQuanLi(){
         if(Auth.isManager()){
             
         }
     }
    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setTendangnhap(txtTenDangNhap.getText());
        nv.setMatkhau(new String(txtMatKhau.getPassword()));
        nv.setHovaten(txtHoTen.getText());
        nv.setEmail(txtEmail.getText());

        if (rdoQuanLi.isSelected()) {
            nv.setVaitro(true);  // Thiết lập vai trò là "Quản lí"
        } else if (rdoNhanVien.isSelected()) {
            nv.setVaitro(false); // Thiết lập vai trò là "Nhân viên"
        }
        return nv;
    }

    void insert() {
        NhanVien nv = getForm();

        try {
            nvDao.insert(nv);
            this.filltoTable();
              this.clearForm();
            MsgBox.alert(this, "Thêm thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm thất bại!");
        }

    }
    void update() {
        NhanVien nv = getForm();

        if (Auth.user.getTendangnhap().equals(nv.getTendangnhap())) {
            rdoQuanLi.setEnabled(false);
            rdoNhanVien.setEnabled(false);

            if (Validate()) {
                if (MsgBox.confirm(this, "Bạn có chắc muốn cập nhật thông tin của bạn")) {
                    try {
                        nvDao.update(nv);
                        this.filltoTable();
                        this.clearForm();
                        MsgBox.alert(this, "Cập nhật thành công");
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại!");
                    }
                }
            }
            return;
        }

        NhanVien nvtp = nvDao.selectByID(txtTenDangNhap.getText());
        if (Auth.isManager()) {
            if (Auth.isManager() != nvtp.isVaitro()) {
                rdoQuanLi.setEnabled(false);
                rdoNhanVien.setEnabled(false);
                if (Validate()) {
                    if (MsgBox.confirm(this, "Bạn có chắc chắn muốn cập nhật nhân viên này")) {
                        try {
                            nvDao.update(nv);
                            this.filltoTable();
                            this.clearForm();
                            MsgBox.alert(this, "Cập nhật thành công");
                        } catch (Exception e) {
                            MsgBox.alert(this, "Cập nhật thất bại!");
                        }
                    }
                }
            } else {
                MsgBox.alert(this, "Bạn không có quyền cập nhật thông tin của trưởng phòng");
            }

        } else {
            MsgBox.alert(this, "Bạn không có quyền Cập nhật thông tin nhân viên");
            clearForm();
        }

    }

    void detele() {
        if(!Auth.isManager()){
             MsgBox.alert(this, "Bạn không có quyền Xóa nhân viên");
        }else{
              String maNV = txtTenDangNhap.getText();
           if(maNV.equals(Auth.user.getTendangnhap())){
                  MsgBox.alert(this, "Bạn không được xóa chính bạn");

           }else if (MsgBox.confirm(this, "Bạn có muốn xóa nhân viên này không?")) {
                try {
                    nvDao.delete(maNV);
                    this.filltoTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại!");
                }
            
        }
      }
    }
    boolean Validate(){
         String errorMessage = "";
         if(txtTenDangNhap.getText().isEmpty()){
             errorMessage+="Tên đăng nhập đang bỏ trống\n";
         }
         if(txtMatKhau.getText().isEmpty()){
              errorMessage+="Mật khẩu đang bỏ trống\n";
         }
         if(txtHoTen.getText().isEmpty()){
               errorMessage+="Họ tên đang bỏ trống\n";
         }
         if(txtEmail.getText().isEmpty()){
               errorMessage+="Email đang bỏ trống\n";
         }
         if(!rdoQuanLi.isSelected()&&!rdoNhanVien.isSelected()){
              errorMessage+="Chưa chọn vai trò\n";
         }
           if (!errorMessage.equals("")) {
            MsgBox.alert(this, errorMessage);
            return false;
        }
        return true;
    }
    void checkQuanLi(){
        if(Auth.isManager()){
            txtMatKhau.setEditable(false);
              String maNV = (String) tblNhanVien2.getValueAt(this.row, 0);
            if(maNV.equals(Auth.user.getTendangnhap())){
                rdoQuanLi.setEnabled(false);
                rdoNhanVien.setEnabled(false);
            }else{
                rdoQuanLi.setEnabled(true);
                rdoNhanVien.setEnabled(true);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        groupVaiTro = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new com.tlk.swing.MyTextField();
        txtHoTen = new com.tlk.swing.MyTextField();
        txtTenDangNhap = new com.tlk.swing.MyTextField();
        txtMatKhau = new com.tlk.swing.MyPasswordField();
        rdoQuanLi = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        btnThem = new com.tlk.swing.ButtonOutLine();
        btnMoi = new com.tlk.swing.ButtonOutLine();
        btnCapNhap = new com.tlk.swing.ButtonOutLine();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien2 = new tabledark.TableDark();
        txtTimTenNV = new com.tlk.swing.MyTextField();
        btnTim = new com.tlk.swing.ButtonBig();
        btnXoa = new com.tlk.swing.ButtonOutLine();

        jLabel1.setText("jLabel1");

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBorder1.setBackground(new java.awt.Color(0, 204, 204));
        panelBorder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Mật khẩu");
        panelBorder1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Vai trò");
        panelBorder1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tên đăng nhập");
        panelBorder1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Họ và tên");
        panelBorder1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Email");
        panelBorder1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, -1));

        txtEmail.setHint("Email");
        panelBorder1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 300, -1));

        txtHoTen.setHint("Họ tên");
        panelBorder1.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 300, -1));

        txtTenDangNhap.setHint("Tên đăng nhập");
        panelBorder1.add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 300, -1));

        txtMatKhau.setHint("************");
        panelBorder1.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 300, -1));

        groupVaiTro.add(rdoQuanLi);
        rdoQuanLi.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rdoQuanLi.setForeground(new java.awt.Color(255, 255, 255));
        rdoQuanLi.setText("Quản lí");
        rdoQuanLi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoQuanLiActionPerformed(evt);
            }
        });
        panelBorder1.add(rdoQuanLi, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        groupVaiTro.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rdoNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        rdoNhanVien.setText("Nhân viên");
        panelBorder1.add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, -1, -1));

        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        panelBorder1.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 90, 40));

        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        panelBorder1.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 200, 90, 40));

        btnCapNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhap.setText("CẬP  NHẬT");
        btnCapNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCapNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhapActionPerformed(evt);
            }
        });
        panelBorder1.add(btnCapNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 200, 90, 40));

        jPanel1.add(panelBorder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 930, 250));

        panelBorder2.setBackground(new java.awt.Color(153, 153, 153));
        panelBorder2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNhanVien2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên đăng nhập", "Mật khẩu", "Họ tên", "Email", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVien2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien2);

        panelBorder2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 910, 200));

        txtTimTenNV.setHint("Tên nhân viên");
        panelBorder2.add(txtTimTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 280, -1));

        btnTim.setBackground(new java.awt.Color(255, 255, 255));
        btnTim.setForeground(new java.awt.Color(51, 153, 255));
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });
        panelBorder2.add(btnTim, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 80, 40));

        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("XÓA");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        panelBorder2.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 280, 130, 40));

        jPanel1.add(panelBorder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 930, 330));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 610));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhapActionPerformed
       update();
    }//GEN-LAST:event_btnCapNhapActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if(Auth.isManager()){
            if(Validate()){
                insert();
            }
        }else{
               MsgBox.alert(this, "Bạn không có quyền Thêm thông tin ");
               clearForm();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void rdoQuanLiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoQuanLiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoQuanLiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        detele();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        timKiem();
        txtTimTenNV.setText("");
    }//GEN-LAST:event_btnTimActionPerformed

    private void tblNhanVien2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVien2MouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblNhanVien2.getSelectedRow();
            txtMatKhau.setEditable(false);
            if(!Auth.isManager()){
                rdoNhanVien.setEnabled(false);
                rdoQuanLi.setEnabled(false);
            }
            this.edit();
            
        }
    }//GEN-LAST:event_tblNhanVien2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonOutLine btnCapNhap;
    private com.tlk.swing.ButtonOutLine btnMoi;
    private com.tlk.swing.ButtonOutLine btnThem;
    private com.tlk.swing.ButtonBig btnTim;
    private com.tlk.swing.ButtonOutLine btnXoa;
    private javax.swing.ButtonGroup groupVaiTro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder2;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoQuanLi;
    private tabledark.TableDark tblNhanVien2;
    private com.tlk.swing.MyTextField txtEmail;
    private com.tlk.swing.MyTextField txtHoTen;
    private com.tlk.swing.MyPasswordField txtMatKhau;
    private com.tlk.swing.MyTextField txtTenDangNhap;
    private com.tlk.swing.MyTextField txtTimTenNV;
    // End of variables declaration//GEN-END:variables
}
