
package com.tlk.compoment;

import com.tlk.dao.NhanVienDAO;
import com.tlk.entity.DemoDoimatkhau;
import com.tlk.entity.NhanVien;
import com.tlk.swing.Button;
import com.tlk.swing.ButtonOutLine;
import com.tlk.swing.MyPasswordField;
import com.tlk.swing.MyTextField;
import com.tlk.utils.Auth;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.plaf.synth.Region;
import net.miginfocom.swing.MigLayout;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {
    NhanVienDAO dao = new NhanVienDAO();
    public String getMatkhau(){
        return mk1;
    }
    private String mk1;
    
    public String getLoiDangNhap(){
        return loi;
    }
    private String loi;
    
    public boolean getLoi(){
        return ttloi;
    }
    private boolean ttloi;
    
    
    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
        initComponents();
        initRegister(eventRegister);
        initLogin(eventLogin);
        login.setVisible(false);
        register.setVisible(true);
        
    }
    
    private void initRegister(ActionListener eventRegister){
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]25[]push"));
        JLabel label = new JLabel("QUÊN MẬT KHẨU");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(39, 71, 171));
        register.add(label);
        
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/tlk/icon/user.png")));
        txtUser.setHint("Tên đăng nhập");
        register.add(txtUser,"w 60%");
        
        MyTextField txtEmail = new MyTextField();
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/com/tlk/icon/mail.png")));
        txtEmail.setHint("Email");
        register.add(txtEmail,"w 60%");
        
        MyPasswordField txtPass1 = new MyPasswordField();
        txtPass1.setPrefixIcon(new ImageIcon(getClass().getResource("/com/tlk/icon/pass.png")));
        txtPass1.setHint("Mật khẩu mới");
        register.add(txtPass1,"w 60%");
        
        MyPasswordField txtPass2 = new MyPasswordField();
        txtPass2.setPrefixIcon(new ImageIcon(getClass().getResource("/com/tlk/icon/pass.png")));
        txtPass2.setHint("Xác nhận mật khẩu");
        register.add(txtPass2,"w 60%");
        
        Button cmd = new Button();
        cmd.setBackground(new Color(39, 71, 171));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventRegister);
        cmd.setText("ĐỔI MẬT KHẨU");
        register.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String userName = txtUser.getText().trim();
//                String email = txtEmail.getText().trim();
//                String pass1 = String.valueOf(txtPass1.getPassword());
//                String pass2 = String.valueOf(txtPass2.getPassword());
//                mk1 = new DemoDoimatkhau(userName, email, pass1, pass2);
                loi = "";
                if (txtUser.getText().equals("")) {
                    loi = "Tên đăng nhập không được để trống";
                    ttloi = false;
                    return;
                }
                if (txtEmail.getText().equals("")) {
                    loi = "Email không được để trống";
                    ttloi = false;
                    return;
                }
                if (String.valueOf(txtPass1.getPassword()).equals("")) {
                    loi = "Mật khẩu không được để trống";
                    ttloi = false;
                    return;
                }
                if (String.valueOf(txtPass2.getPassword()).equals("")) {
                    loi = "Vui lòng nhập lại mật khẩu   ";
                    ttloi = false;
                    return;
                }
                String manv = txtUser.getText();
                String email = txtEmail.getText();
                String matkhau = String.valueOf(txtPass1.getPassword());
                NhanVien nhanVien = dao.selectByID(manv);
                if (nhanVien == null) {
                    loi="Không tìm thấy nhân viên "+manv+" trong hệ thống";
                      ttloi=false;
                      return;

                } else if (!email.equals(nhanVien.getEmail())) {
                      loi="Email không chính xác";
                      ttloi=false;
                      return;
                } else if(!String.valueOf(txtPass1.getPassword()).equals(String.valueOf(txtPass2.getPassword()))){
                    loi="Xác nhận mật khẩu không chính xác";
                      ttloi=false;
                      return;
                    
                } else{
                    mk1=String.valueOf(txtPass1.getPassword());
                    Auth.user = nhanVien;
                    ttloi = true;
                }
                
                
            }
        });
        
    }

    private void initLogin(ActionListener eventLogin){
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("ĐĂNG NHẬP");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(39, 71, 171));
        login.add(label);
        
        MyTextField txtTenDangNhap = new MyTextField();
        txtTenDangNhap.setPrefixIcon(new ImageIcon(getClass().getResource("/com/tlk/icon/mail.png")));
        txtTenDangNhap.setHint("Tên đăng nhập");
        login.add(txtTenDangNhap,"w 60%");
        
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/tlk/icon/pass.png")));
        txtPass.setHint("Mật khẩu");
        login.add(txtPass,"w 60%");
        
        JButton cmdForget = new JButton("Forget your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sannserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
        
        Button cmd = new Button();
        cmd.setBackground(new Color(39, 71, 171));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLogin);
        cmd.setText("ĐĂNG NHẬP");
        login.add(cmd, "w 40%, h 40");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loi="";
                  if(txtTenDangNhap.getText().equals("")){
                      loi="Tên đăng nhập không được để trống";
                      ttloi=false;
                      return;
                  }
                  if(String.valueOf(txtPass.getPassword()).equals("")){
                      loi="Mật khẩu không được để trống";
                      ttloi=false;
                      return;
                  }
                String manv = txtTenDangNhap.getText();
                String matkhau = String.valueOf(txtPass.getPassword());
//                System.out.println("l"+matkhau+"l");
                NhanVien nhanVien = dao.selectByID(manv);
//                System.out.println("l"+nhanVien.getMatkhau()+"l");
                if (nhanVien == null) {
                    loi="Sai tên đăng nhập";
                      ttloi=false;
                      return;

                } else if (!matkhau.equals(nhanVien.getMatkhau())) {
                      loi="Sai mật khẩu";
                      ttloi=false;
                      return;
                } else {
                    Auth.user = nhanVien;
                    
                    ttloi = true;
                }
                
            }
        });
    }
    

    
    public void showRegister(boolean show){
        if(show){
            register.setVisible(true);
            login.setVisible(false);
            
        } else{
            register.setVisible(false);
            login.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

 
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
