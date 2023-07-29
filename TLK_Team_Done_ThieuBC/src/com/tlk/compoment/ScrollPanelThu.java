package com.tlk.compoment;

import com.tlk.dao.TopingDAO;
import com.tlk.entity.Toping;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class ScrollPanelThu extends javax.swing.JPanel {

    TopingDAO tpdao = new TopingDAO();

    public ScrollPanelThu() {
        initComponents();
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        TopingDuocChon = new ArrayList<>();
        addchk();
        
        
    }

    public static List<Toping> TopingDuocChon;

//    void addchk(){
//        List<Toping> list =  tpdao.selectAll();
//        int dem = 0;
//        
//        for (Toping tp : list) {
//            String namechkToping = "chk"+tp.getTentoping();
//            String toping = tp.getTentoping()+" -- "+String.format("%.0f", tp.getGiatoping());
//            JCheckBox namechkToping1 = new JCheckBox(toping, false);
//            namechkToping1.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    TopingDuocChon.add(tp);
//                    System.out.println("chon"+tp.getTentoping());
//                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
//                    TopingDuocChon.remove(tp);
//                    System.out.println("Bo chon"+tp.getTentoping());
//                }
//            }
//        });
//            jPanel1.add(namechkToping1);
//            dem++;
//             
//        }
//        Dimension preferredSize = new Dimension(200, dem*25);
//        jPanel1.setPreferredSize(preferredSize);
//    }
   public void addchk() {
        jPanel1.removeAll(); // Xóa hết các JCheckBox cũ trước khi thêm mới
        List<Toping> list = tpdao.selectAll();
        int dem = 0;

        for (Toping tp : list) {
            String namechkToping = "chk" + tp.getTentoping();
            String toping = tp.getTentoping() + " -- " + String.format("%.0f", tp.getGiatoping());
            JCheckBox namechkToping1 = new JCheckBox(toping, false);

            final Toping finalTp = tp; // Tạo biến cuối cùng finalTp để sử dụng trong phạm vi của itemStateChanged
            namechkToping1.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        TopingDuocChon.add(finalTp);
                        System.out.println("chon" + finalTp.getTentoping());
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        TopingDuocChon.remove(finalTp);
                        System.out.println("Bo chon" + finalTp.getTentoping());
                    }
                }
            });

            jPanel1.add(namechkToping1);
            dem++;
        }

        Dimension preferredSize = new Dimension(200, dem * 25);
        jPanel1.setPreferredSize(preferredSize);
        jPanel1.revalidate(); // Yêu cầu panel làm mới lại giao diện sau khi thay đổi
    }

    public void clearSelections() {
        for (Component component : jPanel1.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                checkBox.setSelected(false);
            }
        }
        TopingDuocChon.clear();
    }
    
    private List<String> selectedTopingNames = new ArrayList<>();
    
    public void clearSelectedTopings() {
        selectedTopingNames.clear();
    }

    public void selectToping(String tenToping) {
//        selectedTopingNames.clear();
        if (!selectedTopingNames.contains(tenToping)) {
            selectedTopingNames.add(tenToping);
        }
        for (Component component : jPanel1.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                String topingText = checkBox.getText();
                String topingName = topingText.substring(0, topingText.indexOf(" -- "));
                checkBox.setSelected(selectedTopingNames.contains(topingName));
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(500, 500));

        jPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 250));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
