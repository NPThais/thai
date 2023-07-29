
package com.tlk.panel;

import com.tlk.dao.TopingDAO;
import com.tlk.entity.KhuyenMai;
import com.tlk.entity.Toping;
import com.tlk.main.PanelInteractionListener;
import com.tlk.utils.Auth;
import com.tlk.utils.CurrentDate;
import com.tlk.utils.MsgBox;
import com.tlk.utils.XDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class TopingPanel extends javax.swing.JPanel {
    TopingDAO dao = new TopingDAO();
    int row=-1;
    
    private PanelInteractionListener listener;
    public void setPanelInteractionListener(PanelInteractionListener listener) {
        this.listener = listener;
    }

    public TopingPanel() {
        initComponents();
        fillToTable();
        if(!Auth.isManager()){
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnCapNhat.setEnabled(false);
        }
    }
    
    void fillToTable(){
        DefaultTableModel model = (DefaultTableModel) tblToping.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{"Mã ", "Tên Toping", "Giá"});
        try {
//            String keyword = txtTimKiem1.getText();
            List<Toping> list = dao.selectAll();
            for (Toping tp : list) {
               
                Object[] row = {tp.getMatoping(), tp.getTentoping(), String.format("%,.0f", tp.getGiatoping())};
                model.addRow(row);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }
    
     public void edit() {
        int matp = (int) tblToping.getValueAt(this.row, 0);
        Toping tp = dao.selectByID(matp);
        this.setFrom(tp);

//        this.updateStatus();
    }
     
     void setFrom(Toping tp) {
       
        if(tp.getMatoping()!=null){
            txtMaToping.setText(String.valueOf(tp.getMatoping()));
        } else{
            txtMaToping.setText("");
        }
        
        txtTenToping.setText(tp.getTentoping());
        
        if (tp.getGiatoping()!= null) {
            txtGiaToping.setText(String.format("%.0f", tp.getGiatoping()));
        } else {
            txtGiaToping.setText("");
        }
        

    }
     
     void clearForm() {
        tblToping.clearSelection();
        Toping tp = new Toping();
        this.setFrom(tp);
        this.row = -1;
//        this.updateStatus();
    }
     
    Toping getForm() {
        Toping tp = new Toping();
        if(!txtMaToping.getText().equals("")){
            tp.setMatoping(Integer.parseInt(txtMaToping.getText()));
        }
        tp.setTentoping(txtTenToping.getText());
        tp.setGiatoping(Double.parseDouble(txtGiaToping.getText()));
        
        return tp;
    }
     
     boolean checkDataThem() {
        if(txtTenToping.getText().equals("")){
            MsgBox.alert(this, "Bạn chưa nhập tên Toping");
            return false;
        }
         try {
             double gia = Double.parseDouble(txtGiaToping.getText());
             if(gia<=0){
                 MsgBox.alert(this, "Vui lòng nhập giá >0");
                 return false;
             }
         } catch (Exception e) {
             MsgBox.alert(this, "Bạn vui lòng nhập số cho giá");
             return false;
         }
       return true;
    }
     

    public void themToping() {
        if (checkDataThem()) {

            Toping tp = getForm();
            try {
                dao.insert(tp);
                this.fillToTable();
                this.clearForm();
                MsgBox.alert(this, "Thêm mới thành công");
                if (listener != null) {
                    listener.onFillChkToping();// Gọi phương thức onFillTableChiTiet() từ interface
                }
                //                    insertLichSu(cd.getMaCD(), "Insert");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm mới thất bại");
            }
        }
    }
    
    public void capnhatToping() {
        if (row != -1) {
            if (checkDataThem()) {
                Toping tp = getForm();
                if (MsgBox.confirm(this, "Bạn có chắc muốn cập nhật Toping này")) {
                    try {
                        dao.update(tp);
                        this.fillToTable();
                        MsgBox.alert(this, "Cập nhật thành công");
//                                insertLichSu(nv, "Update");
                        if (listener != null) {
                            listener.onFillChkToping();// Gọi phương thức onFillTableChiTiet() từ interface
                        }
                    } catch (Exception e) {
                        MsgBox.alert(this, "Cập nhật thất bại");

                    }
                }
            }
        } else {
            MsgBox.alert(this, "Bạn chưa chọn Toping nào");
        }
    }
    
    public void xoaToping(){
        if(row!=-1){
            Toping tp = getForm();
            if (MsgBox.confirm(this, "Bạn có chắc muốn xoá Toping này")) {
                    try {
                        dao.delete(tp.getMatoping());
                        this.fillToTable();
                        MsgBox.alert(this, "xoá thành thành công");
//                                insertLichSu(nv, "Update");
                        if (listener != null) {
                            listener.onFillChkToping();// Gọi phương thức onFillTableChiTiet() từ interface
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgBox.alert(this, "xoá thất bại thất bại");

                    }
                }
        } else{
            MsgBox.alert(this, "Bạn chưa chọn Toping nào");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.tlk.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblToping = new javax.swing.JTable();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        panelBorder15 = new com.tlk.swing.PanelBorder();
        jLabel39 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtMaToping = new com.tlk.swing.MyTextField();
        txtTenToping = new com.tlk.swing.MyTextField();
        txtGiaToping = new com.tlk.swing.MyTextField();
        panelBorder3 = new com.tlk.swing.PanelBorder();
        btnThem = new com.tlk.swing.ButtonBig1();
        btnMoi = new com.tlk.swing.ButtonBig1();
        btnCapNhat = new com.tlk.swing.ButtonBig1();
        btnXoa = new com.tlk.swing.ButtonBig1();
        jPanel1 = new javax.swing.JPanel();

        panelBorder1.setBackground(new java.awt.Color(204, 204, 204));

        tblToping.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã", "Tên Toping", "Giá"
            }
        ));
        tblToping.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTopingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblToping);
        if (tblToping.getColumnModel().getColumnCount() > 0) {
            tblToping.getColumnModel().getColumn(0).setMinWidth(150);
            tblToping.getColumnModel().getColumn(0).setMaxWidth(150);
            tblToping.getColumnModel().getColumn(2).setMinWidth(150);
            tblToping.getColumnModel().getColumn(2).setMaxWidth(150);
        }

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder2.setBackground(new java.awt.Color(204, 204, 204));

        panelBorder15.setBackground(new java.awt.Color(51, 102, 255));
        panelBorder15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Thông");

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Tin");

        jLabel42.setBackground(new java.awt.Color(255, 255, 255));
        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Toping");

        javax.swing.GroupLayout panelBorder15Layout = new javax.swing.GroupLayout(panelBorder15);
        panelBorder15.setLayout(panelBorder15Layout);
        panelBorder15Layout.setHorizontalGroup(
            panelBorder15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBorder15Layout.setVerticalGroup(
            panelBorder15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder15Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtMaToping.setEditable(false);
        txtMaToping.setHint("Mã Toping ");
        txtMaToping.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtTenToping.setHint("Tên Toping");
        txtTenToping.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtGiaToping.setHint("Giá");
        txtGiaToping.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addComponent(panelBorder15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaToping, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                    .addComponent(txtTenToping, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGiaToping, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(txtMaToping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTenToping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtGiaToping, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addComponent(panelBorder15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBorder3.setBackground(new java.awt.Color(153, 153, 255));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnCapNhat.setText("Cập nhật");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        btnXoa.setText("Xoá");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder3Layout = new javax.swing.GroupLayout(panelBorder3);
        panelBorder3.setLayout(panelBorder3Layout);
        panelBorder3Layout.setHorizontalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBorder3Layout.setVerticalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 392, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBorder2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBorder3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblTopingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTopingMouseClicked
        if(evt.getClickCount()==1){
            this.row = tblToping.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblTopingMouseClicked

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        themToping();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaToping();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        capnhatToping();
    }//GEN-LAST:event_btnCapNhatActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonBig1 btnCapNhat;
    private com.tlk.swing.ButtonBig1 btnMoi;
    private com.tlk.swing.ButtonBig1 btnThem;
    private com.tlk.swing.ButtonBig1 btnXoa;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder15;
    private com.tlk.swing.PanelBorder panelBorder2;
    private com.tlk.swing.PanelBorder panelBorder3;
    private javax.swing.JTable tblToping;
    private com.tlk.swing.MyTextField txtGiaToping;
    private com.tlk.swing.MyTextField txtMaToping;
    private com.tlk.swing.MyTextField txtTenToping;
    // End of variables declaration//GEN-END:variables
}
