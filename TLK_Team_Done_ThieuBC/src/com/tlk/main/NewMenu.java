
package com.tlk.main;

import com.tlk.compoment.TachHoaDon;
import com.tlk.dao.HoaDonChiTietDAO;
import com.tlk.entity.HoaDonChiTiet;
import com.tlk.utils.Auth;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewMenu extends javax.swing.JPanel {
    private Color defaultColor = new Color(255, 255, 255);

    public NewMenu() {
        initComponents();
        hoaDonAndChiTietPanel1.setVisible(true);
        khuyeMaiPanel1.setVisible(false);
        
        sanPhamPanel1.setVisible(false);
//        baoCaoPanel1.setVisible(false);
//        settingPanel1.setVisible(false);
        topingPanel1.setVisible(false);
        userPanel1.setVisible(false);
         doiMatKhauPanel1.setVisible(false);

        PanelInteractionListener panelListener = new PanelInteractionListener() {
            @Override
            public void onFillTableHoaDon() {
                hoaDonAndChiTietPanel1.fillToTable();
                hoaDonAndChiTietPanel1.UpdateTongThanhToan();// Gọi phương thức fillToTable() trong HoaDonPanel
            }

            @Override
            public void onFillTableChiTiet() {
                hoaDonAndChiTietPanel1.fillToTableChiTiet(); // Gọi phương thức fillToTableChiTiet() trong ChiTietPanel
            }

            @Override
            public void onFillComboBoxKhuyenMai() {
                hoaDonAndChiTietPanel1.fillComboBoxKhuyenMai();
            }

            @Override
            public void onFillChkToping() {
                hoaDonAndChiTietPanel1.fillChkToping();
            }

            @Override
            public void onFillCboLoai() {
                hoaDonAndChiTietPanel1.fillComBoboxLoaiSanPham();
            }
        };

        hoaDonAndChiTietPanel1.setPanelInteractionListener(panelListener);
        khuyeMaiPanel1.setPanelInteractionListener(panelListener);
        topingPanel1.setPanelInteractionListener(panelListener);
        sanPhamPanel1.setPanelInteractionListener(panelListener);
        


    }
    
    
    
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnKetThuc = new com.tlk.swing.ButtonBig();
        btnDangXuat = new com.tlk.swing.ButtonBig();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();
        hoaDonAndChiTietPanel1 = new com.tlk.panel.HoaDonAndChiTietPanel();
        doiMatKhauPanel1 = new com.tlk.panel.DoiMatKhauPanel();
        khuyeMaiPanel1 = new com.tlk.panel.KhuyenMaiPanel();
        sanPhamPanel1 = new com.tlk.panel.SanPhamPanel();
        userPanel1 = new com.tlk.panel.UserPanel();
        topingPanel1 = new com.tlk.panel.TopingPanel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1200, 675));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelMoving.setOpaque(false);
        panelMoving.setPreferredSize(new java.awt.Dimension(250, 100));
        panelMoving.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelMovingMouseDragged(evt);
            }
        });
        panelMoving.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelMovingMousePressed(evt);
            }
        });
        panelMoving.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelMoving.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 150, 10));

        jLabel16.setFont(new java.awt.Font("Segoe Script", 1, 25)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("TLK Team");
        panelMoving.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 160, -1));

        add(panelMoving, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 100));
        panelMoving.getAccessibleContext().setAccessibleName("");

        jPanel2.setBackground(new java.awt.Color(38, 92, 172));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(152, 45));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel2MouseExited(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/add_user_group_woman_man_24px.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Khuyến mãi");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 250, 40));

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(139, 45));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Đơn hàng");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/add_user_group_woman_man_24px.png"))); // NOI18N
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 250, 40));

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 45));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Sản phẩm");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/home_24px.png"))); // NOI18N
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 250, 40));

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(138, 45));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nhân sự");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/tiles_26px.png"))); // NOI18N
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 250, 40));

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(222, 45));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel6MouseExited(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Đổi mật khẩu");
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/account_24px.png"))); // NOI18N
        jPanel6.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 250, 40));

        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(179, 45));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Toping");
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N
        jPanel7.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 250, 40));

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(222, 45));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Báo cáo doanh thu");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, -1, 40));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/account_24px.png"))); // NOI18N
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 250, 40));

        btnKetThuc.setForeground(new java.awt.Color(41, 32, 203));
        btnKetThuc.setText("Kết thúc");
        btnKetThuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnKetThucMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKetThucMouseExited(evt);
            }
        });
        btnKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetThucActionPerformed(evt);
            }
        });
        add(btnKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 630, 110, -1));

        btnDangXuat.setForeground(new java.awt.Color(41, 32, 203));
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDangXuatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDangXuatMouseExited(evt);
            }
        });
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        add(btnDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 110, -1));

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setPreferredSize(new java.awt.Dimension(950, 675));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/icons8-user-70.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(861, 0, -1, -1));

        lblTenNhanVien.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblTenNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTenNhanVien.setText("Phan Vĩnh Lợi");
        jPanel1.add(lblTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(605, 20, 250, -1));
        jPanel1.add(hoaDonAndChiTietPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 950, 710));
        jPanel1.add(doiMatKhauPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 950, 600));
        jPanel1.add(khuyeMaiPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 950, 610));
        jPanel1.add(sanPhamPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 950, 610));
        jPanel1.add(userPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 950, 610));
        jPanel1.add(topingPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 950, 680));
    }// </editor-fold>//GEN-END:initComponents

    private void panelMovingMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMovingMouseDragged

    }//GEN-LAST:event_panelMovingMouseDragged

    private void panelMovingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMovingMousePressed

    }//GEN-LAST:event_panelMovingMousePressed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        khuyeMaiPanel1.setVisible(true);
       
        sanPhamPanel1.setVisible(false);
//        baoCaoPanel1.setVisible(false);
//        settingPanel1.setVisible(false);
        userPanel1.setVisible(false);
        topingPanel1.setVisible(false);
        doiMatKhauPanel1.setVisible(false);
        hoaDonAndChiTietPanel1.setVisible(false);
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        jPanel2.setBackground(new Color(38,92,172));
        jPanel2.setOpaque(true);
    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseExited
        jPanel2.setBackground(defaultColor);
        jPanel2.setOpaque(false);
    }//GEN-LAST:event_jPanel2MouseExited

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        hoaDonAndChiTietPanel1.setVisible(true);
//        settingPanel1.setVisible(false);
//        baoCaoPanel1.setVisible(false);
        khuyeMaiPanel1.setVisible(false);
        userPanel1.setVisible(false);
        sanPhamPanel1.setVisible(false);
        topingPanel1.setVisible(false);
        doiMatKhauPanel1.setVisible(false);
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        jPanel3.setBackground(new Color(38,92,172));
        jPanel3.setOpaque(true);
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited
        jPanel3.setBackground(defaultColor);
        jPanel3.setOpaque(false);
    }//GEN-LAST:event_jPanel3MouseExited

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        sanPhamPanel1.setVisible(true);
        doiMatKhauPanel1.setVisible(false);
        khuyeMaiPanel1.setVisible(false);
//        baoCaoPanel1.setVisible(false);
//        settingPanel1.setVisible(false);
        userPanel1.setVisible(false);
        topingPanel1.setVisible(false);
        hoaDonAndChiTietPanel1.setVisible(false);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        jPanel4.setBackground(new Color(38,92,172));
        jPanel4.setOpaque(true);
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        jPanel4.setBackground(defaultColor);
        jPanel4.setOpaque(false);
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        userPanel1.setVisible(true);
        doiMatKhauPanel1.setVisible(false);
        khuyeMaiPanel1.setVisible(false);
//        baoCaoPanel1.setVisible(false);
//        settingPanel1.setVisible(false);
        sanPhamPanel1.setVisible(false);
        topingPanel1.setVisible(false);
        hoaDonAndChiTietPanel1.setVisible(false);
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        jPanel5.setBackground(new Color(38,92,172));
        jPanel5.setOpaque(true);
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        jPanel5.setBackground(defaultColor);
        jPanel5.setOpaque(false);
    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        doiMatKhauPanel1.setVisible(true);
        khuyeMaiPanel1.setVisible(false);
//        baoCaoPanel1.setVisible(false);
        sanPhamPanel1.setVisible(false);
//        settingPanel1.setVisible(false);
        userPanel1.setVisible(false);
        topingPanel1.setVisible(false);
        hoaDonAndChiTietPanel1.setVisible(false);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseEntered
        jPanel6.setBackground(new Color(38,92,172));
        jPanel6.setOpaque(true);
    }//GEN-LAST:event_jPanel6MouseEntered

    private void jPanel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseExited
        jPanel6.setBackground(defaultColor);
        jPanel6.setOpaque(false);
    }//GEN-LAST:event_jPanel6MouseExited

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        topingPanel1.setVisible(true);
//        baoCaoPanel1.setVisible(false);
        khuyeMaiPanel1.setVisible(false);
        doiMatKhauPanel1.setVisible(false);
        sanPhamPanel1.setVisible(false);
//        settingPanel1.setVisible(false);
        userPanel1.setVisible(false);
        hoaDonAndChiTietPanel1.setVisible(false);
        
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        jPanel7.setBackground(new Color(38,92,172));
        jPanel7.setOpaque(true);
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        jPanel7.setBackground(defaultColor);
        jPanel7.setOpaque(false);
    }//GEN-LAST:event_jPanel7MouseExited

    private void btnKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetThucActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnKetThucActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnKetThucMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKetThucMouseEntered
        btnKetThuc.setBackground(new Color(242,242,242));
    }//GEN-LAST:event_btnKetThucMouseEntered

    private void btnKetThucMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKetThucMouseExited
        btnKetThuc.setBackground(new Color(204,204,204));
    }//GEN-LAST:event_btnKetThucMouseExited

    private void btnDangXuatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangXuatMouseEntered
        btnDangXuat.setBackground(new Color(242,242,242));
    }//GEN-LAST:event_btnDangXuatMouseEntered

    private void btnDangXuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangXuatMouseExited
        btnDangXuat.setBackground(new Color(204,204,204));
    }//GEN-LAST:event_btnDangXuatMouseExited

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel8MouseExited
    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#373B44"), 0, getHeight(), Color.decode("#4286f4"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintComponent(gr);

    }

    private int x;
    private int y;

    public void initMoving(JFrame frame) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }

        });
        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                frame.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonBig btnDangXuat;
    private com.tlk.swing.ButtonBig btnKetThuc;
    private com.tlk.panel.DoiMatKhauPanel doiMatKhauPanel1;
    private com.tlk.panel.HoaDonAndChiTietPanel hoaDonAndChiTietPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator1;
    private com.tlk.panel.KhuyenMaiPanel khuyeMaiPanel1;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JPanel panelMoving;
    private com.tlk.panel.SanPhamPanel sanPhamPanel1;
    private com.tlk.panel.TopingPanel topingPanel1;
    private com.tlk.panel.UserPanel userPanel1;
    // End of variables declaration//GEN-END:variables
}
