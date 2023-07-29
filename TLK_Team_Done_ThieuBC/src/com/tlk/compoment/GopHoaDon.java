
package com.tlk.compoment;

import com.tlk.dao.ChiTietTopingDAO;
import com.tlk.dao.HoaDonChiTietDAO;
import com.tlk.dao.HoaDonDAO;
import com.tlk.dao.KhuyenMaiDAO;
import com.tlk.dao.SanPhamDAO;
import com.tlk.dao.TopingDAO;
import com.tlk.entity.ChiTietToping;
import com.tlk.entity.HoaDon;
import com.tlk.entity.HoaDonChiTiet;
import com.tlk.entity.KhuyenMai;
import com.tlk.entity.SanPham;
import com.tlk.entity.Toping;
import com.tlk.main.TachHoaDonListener;
import static com.tlk.panel.HoaDonAndChiTietPanel.mahd;
import com.tlk.utils.MsgBox;
import com.tlk.utils.XDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class GopHoaDon extends javax.swing.JDialog {
    HoaDonChiTietDAO ctdao = new HoaDonChiTietDAO();
    TopingDAO tpdao = new TopingDAO();
    ChiTietTopingDAO cttpdao = new ChiTietTopingDAO();
    SanPhamDAO spdao = new SanPhamDAO();
    HoaDonDAO hddao = new HoaDonDAO();
    KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
    
    private String mahd1;
    
    public static String mahdgop;
    
    private TachHoaDonListener tachHoaDonListener;

    public void addTachHoaDonListener(TachHoaDonListener listener) {
        tachHoaDonListener = listener;
    }
    
    public GopHoaDon(java.awt.Frame parent, boolean modal, Object mahd) {
        super(parent, modal);
        this.mahd1 = (String) mahd;
        initComponents();
        fillToTableChiTiet();
        fillToTable();
    }

    private GopHoaDon(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    void fillToTableChiTiet() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet1.getModel();
        model.setRowCount(0);
//        model.setColumnIdentifiers(new String[]{"Tên sản phẩm", "Size", "Số lượng", "Thành tiền", "Tỉ lệ đường-đá"});
        try {

            List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd1);
            for (HoaDonChiTiet hdct : list) {
                SanPham sptim = spdao.selectByID(hdct.getMasanpham());
                String dsToping = "";
                List<ChiTietToping> cttp = cttpdao.selectAllByID(hdct.getMahoadonchitiet());
                for (ChiTietToping chiTietToping : cttp) {
                    if (!dsToping.equals("")) {
                        dsToping = dsToping + ", ";
                    }
                    Toping tpduocchon = tpdao.selectByID(chiTietToping.getMaToping());
                    dsToping = dsToping + tpduocchon.getTentoping();
                }

                String tileduongda = hdct.getTileduong() + " Đường -- " + hdct.getTileda() + " Đá";

                 Boolean hdctDuocChon = false;


                Object[] row = {hdct.getMahoadonchitiet(), sptim.getTensanpham(), sptim.getSize(), hdct.getSoluong(), String.format("%,.0f", hdct.getThanhtien()), hdctDuocChon};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }
    
    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
//        model.setColumnIdentifiers(new String[]{"Mã ", "Ngày tạo", "Trạng thái", "Khuyến mãi", "Tổng thanh toán"});
        try {
//            String keyword = txtTimKiem1.getText();
            List<HoaDon> list = hddao.selectAll();
            for (HoaDon hd : list) {
                
                String giatri;

                
                String ngaytao = XDate.convertDateFormat(hd.getNgaytao(), "dd-MM-yyyy HH:mm");
        
                Object[] row = {hd.getMahoadon(), ngaytao, String.format("%.0f", hd.getTongthanhtoanKM())};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }
    
    public List<Integer> getSelectedMachitiethoadon() {
        List<Integer> selectedMahoadon = new ArrayList<>();

        // Duyệt qua từng hàng của bảng tblChiTiet1
        for (int row = 0; row < tblChiTiet1.getRowCount(); row++) {
            Boolean hdctDuocChon = (Boolean) tblChiTiet1.getValueAt(row, tblChiTiet1.getColumnCount() - 1);

            // Kiểm tra xem checkbox của hàng hiện tại có được chọn hay không
            if (hdctDuocChon != null && hdctDuocChon) {
                // Nếu checkbox được chọn, lấy giá trị hd.getMahoadonchitiet() của hàng tương ứng
                Integer mahoadon = (Integer) tblChiTiet1.getValueAt(row, 0);
                selectedMahoadon.add(mahoadon);
            }
        }

        return selectedMahoadon;
    }
    
    public List<String> getSelectedMahoadon() {
        List<String> selectedMahoadon = new ArrayList<>();

        // Duyệt qua từng hàng của bảng tblChiTiet1
        for (int row = 0; row < tblHoaDon.getRowCount(); row++) {
            Boolean hdctDuocChon = (Boolean) tblHoaDon.getValueAt(row, tblHoaDon.getColumnCount() - 1);

            // Kiểm tra xem checkbox của hàng hiện tại có được chọn hay không
            if (hdctDuocChon != null && hdctDuocChon) {
                // Nếu checkbox được chọn, lấy giá trị hd.getMahoadonchitiet() của hàng tương ứng
                String mahoadon = (String) tblHoaDon.getValueAt(row, 0);
                selectedMahoadon.add(mahoadon);
            }
        }

        return selectedMahoadon;
    }
    
    boolean checkData(){
        return true;
    }
    
    void gopHoaDon(){
        List<Integer> macthdDuocChon = this.getSelectedMachitiethoadon();
        List<String> mahdduocchon = this.getSelectedMahoadon();
        System.out.println(mahdduocchon.size());
        if(!macthdDuocChon.isEmpty()){
            if(mahdduocchon.size()==1 && !mahdduocchon.isEmpty()){
                for (Integer macthd : macthdDuocChon) {
                    ctdao.updateMaHD(mahdduocchon.get(0), macthd);
                }
                mahdgop = mahdduocchon.get(0);
                fillToTableChiTiet();
                fillToTable();
                List<HoaDonChiTiet> listct = ctdao.selectAllChiTiet(mahd1);
                if(listct.isEmpty()){
                    System.out.println("delete");
                    hddao.delete(mahd1);
                }
                updateTongThanhToan(mahdduocchon.get(0));
                if (tachHoaDonListener != null) {
                    System.out.println("tt");
                    tachHoaDonListener.tachHoaDonClosed();
                }
                fillToTableChiTiet();
                fillToTable();
                MsgBox.alert(this, "Gộp hoá đơn thành công");
            } else{
                MsgBox.alert(this, "Vui lòng chọn một hoá đơn bạn muốn gộp");
            }
        } else{
            MsgBox.alert(this, "Vui lòng chọn đơn hàng bạn muốn gộp");
        }
    }
    
     void updateTongThanhToan(String mahd){
        HoaDon hoadonSuDung = hddao.selectByID(mahd);
        KhuyenMai kmApDungTrenDonHang = kmdao.selectByID(hoadonSuDung.getMakhuyenmai());
         
        Double tongThanhToan = 0.0;
        Double tienKhuyenMai = 0.0;
        Double tongThanhToanSauKhuyenMai = 0.0;
        
        List<HoaDonChiTiet> hdct = ctdao.selectAllChiTiet(mahd);
        for (HoaDonChiTiet hoaDonChiTiet : hdct) {
            tongThanhToan += hoaDonChiTiet.getThanhtien();
        }
        hddao.updateThanhToan(tongThanhToan, mahd);
        if(kmApDungTrenDonHang!=null){
            if (kmApDungTrenDonHang.isGiamtheophantram()) {
                tienKhuyenMai=(tongThanhToan/100)*kmApDungTrenDonHang.getGiatrikhuyenmai(); 
                tongThanhToanSauKhuyenMai = tongThanhToan - tienKhuyenMai;               
                hddao.updateThanhToanKM(tongThanhToanSauKhuyenMai, mahd);
                System.out.println("tkm: "+tienKhuyenMai+"  --Ttsc: "+tongThanhToanSauKhuyenMai);

            } else{
                tienKhuyenMai = kmApDungTrenDonHang.getGiatrikhuyenmai();
                
                tongThanhToanSauKhuyenMai = tongThanhToan - tienKhuyenMai;
                
                hddao.updateThanhToanKM(tongThanhToanSauKhuyenMai, mahd);
                System.out.println("tkm: "+tienKhuyenMai+"  --Ttsc: "+tongThanhToanSauKhuyenMai);
            }
        } else{
            
            tongThanhToanSauKhuyenMai = tongThanhToan;
            
            hddao.updateThanhToanKM(tongThanhToanSauKhuyenMai, mahd);
        }
        
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelBorder12 = new com.tlk.swing.PanelBorder();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTiet1 = new javax.swing.JTable();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        btnHuy = new com.tlk.swing.ButtonBig1();
        btnTachHoaDon = new com.tlk.swing.ButtonBig1();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(139, 195, 207));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Gộp");
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 90, -1));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Hoá");
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 90, -1));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Đơn");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 90, -1));

        panelBorder12.setBackground(new java.awt.Color(153, 153, 255));

        tblChiTiet1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên thức uống", "Size", "Số lượng", "Thành tiền", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblChiTiet1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTiet1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblChiTiet1);
        if (tblChiTiet1.getColumnModel().getColumnCount() > 0) {
            tblChiTiet1.getColumnModel().getColumn(1).setMinWidth(180);
            tblChiTiet1.getColumnModel().getColumn(1).setMaxWidth(180);
        }

        javax.swing.GroupLayout panelBorder12Layout = new javax.swing.GroupLayout(panelBorder12);
        panelBorder12.setLayout(panelBorder12Layout);
        panelBorder12Layout.setHorizontalGroup(
            panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder12Layout.setVerticalGroup(
            panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder1.setBackground(new java.awt.Color(153, 153, 255));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã hoá đơn", "Ngày Tạo", "Tổng thanh toán", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(100);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(100);
            tblHoaDon.getColumnModel().getColumn(1).setMinWidth(200);
            tblHoaDon.getColumnModel().getColumn(1).setMaxWidth(200);
            tblHoaDon.getColumnModel().getColumn(2).setMinWidth(100);
            tblHoaDon.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelBorder2.setBackground(new java.awt.Color(153, 153, 255));

        btnHuy.setText("Huỷ");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnTachHoaDon.setText("Gộp hoá đơn");
        btnTachHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTachHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTachHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTachHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelBorder12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblChiTiet1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTiet1MouseClicked
        if (evt.getClickCount() == 1) {
            //            scrollPanelThu1.clearSelections();
            //            this.rowChiTiet = tblChiTiet1.getSelectedRow();
            //            this.editHoaDonChiTiet();
        }
    }//GEN-LAST:event_tblChiTiet1MouseClicked

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnTachHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTachHoaDonActionPerformed
        gopHoaDon();
    }//GEN-LAST:event_btnTachHoaDonActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(GopHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GopHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GopHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GopHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GopHoaDon dialog = new GopHoaDon(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonBig1 btnHuy;
    private com.tlk.swing.ButtonBig1 btnTachHoaDon;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder12;
    private com.tlk.swing.PanelBorder panelBorder2;
    private javax.swing.JTable tblChiTiet1;
    private javax.swing.JTable tblHoaDon;
    // End of variables declaration//GEN-END:variables
}
