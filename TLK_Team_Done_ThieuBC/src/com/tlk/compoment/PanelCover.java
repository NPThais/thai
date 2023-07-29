
package com.tlk.compoment;

import com.tlk.swing.ButtonOutLine;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class PanelCover extends javax.swing.JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###");
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private ButtonOutLine button;
    private boolean isLogin;
    
    public PanelCover() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }
    private void init(){
        title  = new JLabel("Chào mừng bạn đã trở lại");
        title.setFont(new Font("sannserif", 1, 30));
        title.setForeground(new Color(245, 245, 245));
        add(title);
        
        description = new JLabel("Tiếp tục công việc còn dang dở");
        description.setForeground(new Color(245, 245, 245));
        add(description);
        
        description1 = new JLabel("Đăng nhập ngay thôi!");
        description1.setForeground(new Color(245, 245, 245));
        add(description1);
        
        button = new ButtonOutLine();
        button.setBackground(new Color(255, 255, 255));
        button.setForeground(new Color(255, 255, 255));
        button.setText("ĐĂNG NHẬP");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.actionPerformed(e);
            }
        });
        add(button, "w 60%, h 40");
    }
    
    public void registerLeft(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0-"+ v +"% 0 0");
        layout.setComponentConstraints(description, "pad 0-"+ v +"% 0 0");
        layout.setComponentConstraints(description1, "pad 0-"+ v +"% 0 0");
    }
    
    public void registerRight(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0-"+ v +"% 0 0");
        layout.setComponentConstraints(description, "pad 0-"+ v +"% 0 0");
        layout.setComponentConstraints(description1, "pad 0-"+ v +"% 0 0");
    }

    public void loginLeft(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0"+ v +"% 0 "+ v +"%");
        layout.setComponentConstraints(description, "pad 0"+ v +"% 0 "+ v +"%");
        layout.setComponentConstraints(description1, "pad 0"+ v +"% 0 "+ v +"%");
    }
    
     public void loginRight(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0"+ v +"% 0 "+ v +"%");
        layout.setComponentConstraints(description, "pad 0"+ v +"% 0 "+ v +"%");
        layout.setComponentConstraints(description1, "pad 0"+ v +"% 0 "+ v +"%");
    }
    
    public void login(boolean login){
        if(this.isLogin != login){
            if(login){
                title.setText("Xin chào!");
                description.setText("Trường hợp bạn quên mật khẩu");
                description1.setText("Lấy lại mật khẩu dễ dàng với email");
                button.setText("QUÊN MẬT KHẨU");
            } else{
                title.setText("Chào mừng bạn đã trở lại");
                description.setText("Tiếp tục công việc còn dang dở");
                description1.setText("Đăng nhập ngay thôi!");
                button.setText("ĐĂNG NHẬP");
            }
            this.isLogin = login;
        }
    }

    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        GradientPaint gra =  new GradientPaint(0, 0, new Color(39, 71, 171), 0, getHeight(), new Color(39, 71, 171 ));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        super.paintComponent(gr); 
    }

    public void addEvent(ActionListener event){
        this.event =  event;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
