package com.tlk.panel;

import com.tlk.dao.HoaDonDAO;
import com.tlk.dao.KhuyenMaiDAO;
import com.tlk.entity.HoaDon;
import com.tlk.entity.KhuyenMai;
import com.tlk.utils.XDate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class BaoCaoPanel extends javax.swing.JPanel {
    HoaDonDAO dao = new HoaDonDAO();
    KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
    
    public BaoCaoPanel() {
        initComponents();
        fillToComBoBoxNgay();
//        showLineChart();
        showPieChart();
    }
    
    void fillToComBoBoxNgay(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNgay.getModel();
        model.removeAllElements();
       
        List<String> list = dao.selectAllHoaDonDate();
        for (String date : list) {
            model.addElement(date);
        }
        
    }
    
    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            String dateString = (String) cboNgay.getSelectedItem();

            if (dateString != null && !dateString.isEmpty()) {

                SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy");

                // Định dạng ngày-tháng-năm mới (yyyy-MM-dd)
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Chuyển đổi chuỗi ngày thành đối tượng Date
                Date date = originalFormat.parse(dateString);

                // Chuyển đổi đối tượng Date thành chuỗi mới có định dạng yyyy-MM-dd
                String newDateString = targetFormat.format(date);

                List<HoaDon> list = dao.selectAllHoaDonByDateThanhToan(newDateString);
                
                for (HoaDon hd : list) {
                
                String giatri;
                if (hd.getMakhuyenmai() != null) {
                    KhuyenMai km = kmdao.selectByID(hd.getMakhuyenmai());
                    if (km.isGiamtheophantram()) {
                        giatri = "Giảm " + String.format("%,.0f", km.getGiatrikhuyenmai()) + "%";
                    } else {
                        giatri = "Giảm " + String.format("%,.0f", km.getGiatrikhuyenmai()) + "VND";

                    }
                } else{
                    giatri = "Không áp dụng";
                }
                
                String ngaytao = XDate.convertDateFormat(hd.getNgaytao(), "dd-MM-yyyy HH:mm");
        
                Object[] row = {hd.getMahoadon(), ngaytao, hd.isTrangthai() ? "Chưa thanh toán" : "Đã thanh toán", giatri, String.format("%,.0f", hd.getTongthanhtoanKM())+" VND", hd.getGhichu()};
                model.addRow(row);
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    
     public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
      barDataset.setValue( "IPhone 5s" , new Double( 20 ) );  
      barDataset.setValue( "SamSung Grand" , new Double( 20 ) );   
      barDataset.setValue( "MotoG" , new Double( 50 ) );    
      barDataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
      barDataset.setValue( "Iphone X" , new Double( 30 ) );
      
      //create chart
       JFreeChart piechart = ChartFactory.createPieChart("Doanh thu",barDataset, false,true,false);//explain
      
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
        piePlot.setSectionPaint("IPhone 5s", new Color(255,255,102));
        piePlot.setSectionPaint("SamSung Grand", new Color(102,255,102));
        piePlot.setSectionPaint("MotoG", new Color(255,102,153));
        piePlot.setSectionPaint("Nokia Lumia", new Color(0,204,204));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        //create chartPanel to display chart(graph)
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panePieChart.removeAll();
        panePieChart.add(barChartPanel, BorderLayout.CENTER);
        panePieChart.validate();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panePieChart = new javax.swing.JPanel();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        cboNgay = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        panelBorder3 = new com.tlk.swing.PanelBorder();
        buttonBig11 = new com.tlk.swing.ButtonBig1();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panePieChart.setBackground(new java.awt.Color(204, 204, 255));
        panePieChart.setLayout(new java.awt.BorderLayout());
        add(panePieChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 430, 290));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 290, Short.MAX_VALUE)
        );

        add(panelBorder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 270, 290));

        panelBorder2.setBackground(new java.awt.Color(255, 255, 255));

        cboNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNgayActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Ngày");

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cboNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        add(panelBorder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 210, -1));

        jLabel24.setBackground(new java.awt.Color(51, 51, 51));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Báo Cáo");
        add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 120, -1));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã hoá đơn", "Ngày tạo", "Trạng thái", "Khuyến mãi", "Tổng thanh toán", "Ghi chú"
            }
        ));
        jScrollPane1.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(100);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(100);
            tblHoaDon.getColumnModel().getColumn(1).setMinWidth(150);
            tblHoaDon.getColumnModel().getColumn(1).setMaxWidth(150);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 930, 280));

        panelBorder3.setBackground(new java.awt.Color(255, 255, 255));

        buttonBig11.setText("Xem chi tiết");

        javax.swing.GroupLayout panelBorder3Layout = new javax.swing.GroupLayout(panelBorder3);
        panelBorder3.setLayout(panelBorder3Layout);
        panelBorder3Layout.setHorizontalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonBig11, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder3Layout.setVerticalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder3Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(buttonBig11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        add(panelBorder3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 210, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void cboNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNgayActionPerformed
        fillToTable();
    }//GEN-LAST:event_cboNgayActionPerformed
//    public void showLineChart() {
//        //create dataset for the graph
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.setValue(200, "Amount", "january");
//        dataset.setValue(150, "Amount", "february");
//        dataset.setValue(18, "Amount", "march");
//        dataset.setValue(100, "Amount", "april");
//        dataset.setValue(80, "Amount", "may");
//        dataset.setValue(250, "Amount", "june");
//
//        //create chart
//        JFreeChart linechart = ChartFactory.createLineChart("contribution", "monthly", "amount",
//                dataset, PlotOrientation.VERTICAL.VERTICAL, false, true, false);
//
//        //create plot object
//        CategoryPlot lineCategoryPlot = linechart.getCategoryPlot();
//        // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
//        lineCategoryPlot.setBackgroundPaint(Color.white);
//
//        //create render object to change the moficy the line properties like color
//        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
//        Color lineChartColor = new Color(204, 0, 51);
//        lineRenderer.setSeriesPaint(0, lineChartColor);
//
//        //create chartPanel to display chart(graph)
//        ChartPanel lineChartPanel = new ChartPanel(linechart);
//        panelLineChart.removeAll();
//        panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
//        panelLineChart.validate();
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonBig1 buttonBig11;
    private javax.swing.JComboBox<String> cboNgay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panePieChart;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder2;
    private com.tlk.swing.PanelBorder panelBorder3;
    private javax.swing.JTable tblHoaDon;
    // End of variables declaration//GEN-END:variables
}
