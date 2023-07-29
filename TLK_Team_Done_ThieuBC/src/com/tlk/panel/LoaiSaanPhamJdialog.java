/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.tlk.panel;

import com.tlk.dao.LoaiSanPhamDAO;
import com.tlk.entity.LoaiSanPham;
import com.tlk.utils.MsgBox;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JAVA DEV
 */
public class LoaiSaanPhamJdialog extends javax.swing.JDialog {

    LoaiSanPhamDAO ldao = new LoaiSanPhamDAO();
    int row = -1;

    /**
     * Creates new form LoaiSaanPhamJdialog
     */
    public LoaiSaanPhamJdialog() {
        // super( modal);
        initComponents();
        this.row = -1;
        this.updateStatus();
        tblLoaiSP.fixTable(jScrollPane1);
        fillToTable();
        setLocationRelativeTo(null);

    }

    void edit() {
        String maNV = (String) tblLoaiSP.getValueAt(this.row, 1);
        LoaiSanPham nv = ldao.selectByName(maNV);
        this.setForm(nv);
        // tabs.setSelectedIndex(0);
        this.updateStatus();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);

        // trạng thái form
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa1.setEnabled(edit);
        // Trạng thái Điều hướng

    }

    void clearForm() {
        LoaiSanPham nv = new LoaiSanPham();
        this.setForm(nv);
        this.row = -1;
        this.updateStatus();
        txtLoaiSP.setText("");
        tblLoaiSP.clearSelection();

    }

    boolean Validate() {
        String errorMessage = "";
        if (txtLoaiSP.getText().isEmpty()) {
            errorMessage += "Tên loại sản phẩm bỏ trống";
        } else {

        }

        if (!errorMessage.equals("")) {
            MsgBox.alert(this, errorMessage);
            return false;
        }
        return true;
    }

    void insert() {
        LoaiSanPham cd = getForm();
        try {
            ldao.insert(cd);
            this.fillToTable();
            this.clearForm();

            MsgBox.alert(this, "Thêm thành Công");
            //  tabs.setSelectedIndex(1);
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm thành Công");
        }
    }

    void update() {
        if (row == -1) {
            MsgBox.alert(this, "Vui lòng chọn một dòng để cập nhật");
            return;
        }

        LoaiSanPham ls = getForm();
        ls.setMaloaisanpham((int) tblLoaiSP.getValueAt(row, 0)); // Set the ID based on the selected row

        try {
            ldao.update(ls);
            fillToTable();
            clearForm();
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi cập nhật");
        }
    }

    void delete() {
        if (row == -1) {
            MsgBox.alert(this, "Vui lòng chọn một dòng để xóa");
            return;
        }

        int maLoaiSP = (int) tblLoaiSP.getValueAt(row, 0); // Get the ID from the selected row

        if (MsgBox.confirm(this, "Bạn có chắc muốn xóa loại sản phẩm này?")) {
            try {
                ldao.delete(maLoaiSP);
                fillToTable();
                clearForm();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi xóa");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiSP = new tabledark.TableDark();
        btnXoa1 = new com.tlk.swing.Button();
        btnCapNhat = new com.tlk.swing.Button();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        txtLoaiSP = new com.tlk.swing.MyTextField();
        btnThem = new com.tlk.swing.Button();
        btnMoi = new com.tlk.swing.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));
        panelBorder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLoaiSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "MÃ", "TÊN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLoaiSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiSP);
        if (tblLoaiSP.getColumnModel().getColumnCount() > 0) {
            tblLoaiSP.getColumnModel().getColumn(0).setMinWidth(70);
            tblLoaiSP.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        panelBorder1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 310, 150));

        btnXoa1.setBackground(new java.awt.Color(0, 153, 153));
        btnXoa1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa1.setText("XÓA");
        btnXoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa1ActionPerformed(evt);
            }
        });
        panelBorder1.add(btnXoa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 80, 40));

        btnCapNhat.setBackground(new java.awt.Color(0, 153, 153));
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("CẬP NHẬT");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        panelBorder1.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 80, 40));

        jPanel1.add(panelBorder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 440, 180));

        panelBorder2.setBackground(new java.awt.Color(255, 255, 255));
        panelBorder2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLoaiSP.setBackground(new java.awt.Color(204, 255, 255));
        txtLoaiSP.setForeground(new java.awt.Color(102, 102, 102));
        txtLoaiSP.setCaretColor(new java.awt.Color(102, 102, 102));
        txtLoaiSP.setHint("Loai");
        txtLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoaiSPActionPerformed(evt);
            }
        });
        panelBorder2.add(txtLoaiSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 240, -1));

        btnThem.setBackground(new java.awt.Color(0, 153, 153));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        panelBorder2.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 80, 40));

        btnMoi.setBackground(new java.awt.Color(0, 153, 153));
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        panelBorder2.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 80, 40));

        jPanel1.add(panelBorder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 440, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoaiSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoaiSPActionPerformed

    private void tblLoaiSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSPMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblLoaiSP.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblLoaiSPMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        if (Validate() == true) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed

        if (Validate() == true) {
            update();

        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa1ActionPerformed

        if (Validate() == true) {
            delete();

        }
    }//GEN-LAST:event_btnXoa1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        fillToTable();
    }//GEN-LAST:event_formWindowClosed

    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiSP.getModel();
        model.setRowCount(0);
        try {
            List<LoaiSanPham> list = ldao.selectAll();
            for (LoaiSanPham nv : list) {
                Object[] row = {
                    nv.getMaloaisanpham(),
                    nv.getTenloaisanpham()

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            //  MsgBox.alert(this, "Lỗi dữ liệu");
        }
    }

    LoaiSanPham getForm() {
        LoaiSanPham cd = new LoaiSanPham();

        cd.setTenloaisanpham(txtLoaiSP.getText());

        return cd;
    }

    void setForm(LoaiSanPham cd) {
        txtLoaiSP.setText(cd.getTenloaisanpham());

    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoaiSaanPhamJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoaiSaanPhamJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoaiSaanPhamJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoaiSaanPhamJdialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
//        LoaiSaanPhamJdialog dialog = new LoaiSaanPhamJdialog();
//          dialog.setVisible(true);
        //</editor-fold>

        /* Create and display the dialog */
//        LoaiSaanPhamJdialog dialog = new LoaiSaanPhamJdialog();
//          dialog.setVisible(true);
        //</editor-fold>

        /* Create and display the dialog */
//        LoaiSaanPhamJdialog dialog = new LoaiSaanPhamJdialog();
//          dialog.setVisible(true);
        //</editor-fold>

        /* Create and display the dialog */
//        LoaiSaanPhamJdialog dialog = new LoaiSaanPhamJdialog();
//          dialog.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.Button btnCapNhat;
    private com.tlk.swing.Button btnMoi;
    private com.tlk.swing.Button btnThem;
    private com.tlk.swing.Button btnXoa1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder2;
    private tabledark.TableDark tblLoaiSP;
    private com.tlk.swing.MyTextField txtLoaiSP;
    // End of variables declaration//GEN-END:variables
}
