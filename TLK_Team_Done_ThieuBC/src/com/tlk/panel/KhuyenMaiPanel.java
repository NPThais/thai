package com.tlk.panel;

import com.tlk.dao.KhuyenMaiDAO;
import com.tlk.entity.HoaDon;
import com.tlk.entity.KhuyenMai;
import com.tlk.entity.NhanVien;
import com.tlk.main.PanelInteractionListener;
import com.tlk.utils.Auth;
import com.tlk.utils.XDate;
import com.tlk.utils.CurrentDate;
import com.tlk.utils.MsgBox;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class KhuyenMaiPanel extends javax.swing.JPanel {
    private HoaDonAndChiTietPanel hoaDonAndChiTietPanel;
    KhuyenMaiDAO dao = new KhuyenMaiDAO();

    int row = -1;
    //Khởi tạo interface
    private PanelInteractionListener listener;
    public void setPanelInteractionListener(PanelInteractionListener listener) {
        this.listener = listener;
    }
    
    public KhuyenMaiPanel() {
        initComponents();
        
        updateKhuyenMaiChuaApDungHangNgay();
        updateKhuyenMaiDangApDungHangNgay();
        fillToTable();
        clearForm();
        if(!Auth.isManager()){
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnCapNhat.setEnabled(false);
        }
        
    }

    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhuyenMai.getModel();
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{"Mã ", "Giá trị", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"});
        try {
//            String keyword = txtTimKiem1.getText();
            List<KhuyenMai> list = dao.selectAll();
            for (KhuyenMai km : list) {
                String giatri;
                if(km.isGiamtheophantram()){
                    giatri ="Giảm "+ String.format("%,.0f", km.getGiatrikhuyenmai()) +"%";
                } else{
                    giatri ="Giảm "+ String.format("%,.0f", km.getGiatrikhuyenmai()) +"VND";
                }
//                String khuyenmai = km.getMakhuyenmai();
//
//                System.out.println("km:" + khuyenmai);
                Object[] row = {km.getMakhuyenmai(), giatri, XDate.toString(km.getNgaybatdau(), "dd-MM-yyyy"), XDate.toString(km.getNgayketthuc(), "dd-MM-yyyy"), km.getTrangthaiapdung()};
                model.addRow(row);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }
    
    void updateKhuyenMaiChuaApDungHangNgay(){
        List<KhuyenMai> km = dao.selectAllChuaApDung();
        String homnay = CurrentDate.getCurrentDateString();
        
        for (KhuyenMai khuyenMai : km) {
//            System.out.println("nbd"+khuyenMai.getNgaybatdau());
//            System.out.println("ht"+homnay.toString());
            if(khuyenMai.getNgaybatdau().compareTo(XDate.toDate(homnay, "dd-MM-yyyy"))<=0
                    &&khuyenMai.getNgayketthuc().compareTo(XDate.toDate(homnay, "dd-MM-yyyy"))>0){
//                System.out.println(khuyenMai.getMakhuyenmai()+"ok");
                dao.updateTrangThai("Đang áp dụng", khuyenMai.getMakhuyenmai());
            }
            else{
//                System.out.println(khuyenMai.getMakhuyenmai()+"---");
            }
            
        }
    }
    
    void updateKhuyenMaiDangApDungHangNgay(){
        List<KhuyenMai> km = dao.selectAllDangApDung();
        String homnay = CurrentDate.getCurrentDateString();
        for (KhuyenMai khuyenMai : km) {
//            System.out.println("nbd"+khuyenMai.getNgaybatdau());
//            System.out.println("ht"+homnay.toString());
            if(khuyenMai.getNgaybatdau().compareTo(XDate.toDate(homnay, "dd-MM-yyyy"))<0
                    &&khuyenMai.getNgayketthuc().compareTo(XDate.toDate(homnay, "dd-MM-yyyy"))<0){
//                System.out.println(khuyenMai.getMakhuyenmai()+"ok");
                dao.updateTrangThai("Ngưng áp dụng", khuyenMai.getMakhuyenmai());
                
            }
            else{
//                System.out.println(khuyenMai.getMakhuyenmai()+"---");
            }
            
        }
    }

    void setFrom(KhuyenMai km) {
        txtMaKhuyenMai.setText(km.getMakhuyenmai());
        if (km.getGiatrikhuyenmai() != null) {
            rdoGiamTheoPhanTram.setSelected(km.isGiamtheophantram());
            rdoGiamTheoGia.setSelected(!km.isGiamtheophantram());
        } else {
            HinhThucGiamGia.clearSelection();
        }
        if (km.getGiatrikhuyenmai() != null) {
            txtGiaTriGiamGia.setText(String.format("%.0f", km.getGiatrikhuyenmai()));
        } else {
            txtGiaTriGiamGia.setText("");
        }
        if (km.getNgaybatdau() != null) {
            txtNgayBatDau.setText(XDate.toString(km.getNgaybatdau(), "dd-MM-yyyy"));
        } else {
            txtNgayBatDau.setText("");
        }
        if (km.getNgayketthuc() != null) {
            txtNgayKetThuc.setText(XDate.toString(km.getNgayketthuc(), "dd-MM-yyyy"));
        } else {
            txtNgayKetThuc.setText("");
        }

//        System.out.println(km.getTrangthaiapdung());
        if (km.getTrangthaiapdung() != null) {
            if (km.getTrangthaiapdung().equals("Chưa áp dụng")) {
                rdoChuaApDung.setSelected(true);
            } else if (km.getTrangthaiapdung().equals("Đang áp dụng")) {
                rdoDangApDung.setSelected(true);
            } else {
                rdoNgungApDung.setSelected(true);
            }
        } else {
            TrangThaiApDung.clearSelection();
        }
    }

    public void edit() {
        String makm = (String) tblKhuyenMai.getValueAt(this.row, 0);
        KhuyenMai km = dao.selectByID(makm);
        this.setFrom(km);

//        this.updateStatus();
    }

    void clearForm() {
        tblKhuyenMai.clearSelection();
        KhuyenMai nh = new KhuyenMai();
        this.setFrom(nh);
        this.row = -1;
//        this.updateStatus();
    }

    KhuyenMai getForm() {
        KhuyenMai km = new KhuyenMai();
        km.setMakhuyenmai(txtMaKhuyenMai.getText());
        km.setNgaytao(CurrentDate.getCurrentDate());
        km.setGiamtheophantram(rdoGiamTheoPhanTram.isSelected());
        km.setGiatrikhuyenmai(Double.parseDouble(txtGiaTriGiamGia.getText()));
        km.setNgaybatdau(XDate.toDate(txtNgayBatDau.getText(), "dd-MM-yyyy"));
        km.setNgayketthuc(XDate.toDate(txtNgayKetThuc.getText(), "dd-MM-yyyy"));
        String trangthai;
        if (rdoChuaApDung.isSelected()) {
            trangthai = "Chưa áp dụng";
        } else if (rdoDangApDung.isSelected()) {
            trangthai = "Đang áp dụng";
        } else {
            trangthai = "Ngưng áp dụng";
        }
        km.setTrangthaiapdung(trangthai);
        return km;
    }

    boolean checkDataThem() {
        if (txtMaKhuyenMai.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã khuyến mãi");
            return false;
        }
        if (!rdoGiamTheoPhanTram.isSelected() && !rdoGiamTheoGia.isSelected()) {
            MsgBox.alert(this, "Bạn chưa chọn hình thức khuyến mãi");
            return false;
        }
        if (txtGiaTriGiamGia.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã khuyến mãi");
            return false;
        }
        try {
            double giatri = Double.parseDouble(txtGiaTriGiamGia.getText());
            if (rdoGiamTheoPhanTram.isSelected()) {
                if (giatri > 100 || giatri <= 0) {
                    MsgBox.alert(this, "Giá trị khuyến mãi trong khoảng 1%-100%");
                    return false;
                }
            } else {
                if (giatri <= 0) {
                    MsgBox.alert(this, "Giá trị khuyến mãi phải lớn hơn 0");
                    return false;
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn vui lòng nhập số");
            return false;
        }
        if (txtNgayBatDau.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa chọn ngày bắt đầu");
            return false;
        }
        if (txtNgayKetThuc.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa chọn ngày kết thúc");
            return false;
        }
        
        rdoChuaApDung.setSelected(true);
        return true;
    }
    
    boolean checkDataUpdate() {
        if (txtMaKhuyenMai.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã khuyến mãi");
            return false;
        }
        if (!rdoGiamTheoPhanTram.isSelected() && !rdoGiamTheoGia.isSelected()) {
            MsgBox.alert(this, "Bạn chưa chọn hình thức khuyến mãi");
            return false;
        }
        if (txtGiaTriGiamGia.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa nhập mã khuyến mãi");
            return false;
        }
        try {
            double giatri = Double.parseDouble(txtGiaTriGiamGia.getText());
            if (rdoGiamTheoPhanTram.isSelected()) {
                if (giatri > 100 || giatri <= 0) {
                    MsgBox.alert(this, "Giá trị khuyến mãi trong khoảng 1%-100%");
                    return false;
                }
            } else {
                if (giatri <= 0) {
                    MsgBox.alert(this, "Giá trị khuyến mãi phải lớn hơn 0");
                    return false;
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Bạn vui lòng nhập số");
            return false;
        }
        if (txtNgayBatDau.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa chọn ngày bắt đầu");
            return false;
        }
        if (txtNgayKetThuc.getText().equals("")) {
            MsgBox.alert(this, "Bạn chưa chọn ngày kết thúc");
            return false;
        }
        return true;
    }

    public void themKhuyenMai() {
        if (checkDataThem()) {

            List<KhuyenMai> list = dao.selectAll();
            for (KhuyenMai km1 : list) {
                if (txtMaKhuyenMai.getText().equalsIgnoreCase(km1.getMakhuyenmai())) {
                    MsgBox.alert(this, "Mã khuyến mãi đã tồn tại");
                    return;
                }
            }
            KhuyenMai km = getForm();
            try {
                dao.insert(km);
                this.fillToTable();
                this.clearForm();
                MsgBox.alert(this, "Thêm mới thành công");
                //                    insertLichSu(cd.getMaCD(), "Insert");
                updateKhuyenMaiChuaApDungHangNgay();
                updateKhuyenMaiDangApDungHangNgay();
                fillToTable();
                if (listener != null) {
                    listener.onFillComboBoxKhuyenMai();// Gọi phương thức onFillTableChiTiet() từ interface
                }
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Thêm mới thất bại");
            }
        }
    }

    public void capnhatKhuyenMai() {
        if (row != -1) {
            if (checkDataUpdate()) {
                KhuyenMai km = getForm();
                if (MsgBox.confirm(this, "Bạn có chắc muốn cập nhật khuyến mãi này")) {
                    try {
                        dao.update(km);
                        this.fillToTable();
                        MsgBox.alert(this, "Cập nhật thành công");
//                                insertLichSu(nv, "Update");
                        if (listener != null) {
                            listener.onFillComboBoxKhuyenMai();// Gọi phương thức onFillTableChiTiet() từ interface
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgBox.alert(this, "Cập nhật thất bại");

                    }
                }
            }
        } else {
            MsgBox.alert(this, "Bạn chưa chọn khuyến mãi nào");
        }
    }
    
    public void xoaKhuyenMai(){
        if(row!=-1){
            KhuyenMai km = getForm();
            if (MsgBox.confirm(this, "Bạn có chắc muốn xoá khuyến mãi này")) {
                    try {
                        dao.delete(km.getMakhuyenmai());
                        this.fillToTable();
                        MsgBox.alert(this, "xoá thành thành công");
//                                insertLichSu(nv, "Update");
                        if (listener != null) {
                            listener.onFillComboBoxKhuyenMai();// Gọi phương thức onFillTableChiTiet() từ interface
                        }

                    } catch (Exception e) {
                        MsgBox.alert(this, "xoá thất bại thất bại");

                    }
                }
        } else{
            MsgBox.alert(this, "Bạn chưa chọn khuyến mãi nào");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HinhThucGiamGia = new javax.swing.ButtonGroup();
        dateBatDau = new com.raven.datechooser.DateChooser();
        dateKetThuc = new com.raven.datechooser.DateChooser();
        TrangThaiApDung = new javax.swing.ButtonGroup();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        txtMaKhuyenMai = new com.tlk.swing.MyTextField();
        txtGiaTriGiamGia = new com.tlk.swing.MyTextField();
        txtNgayBatDau = new com.tlk.swing.MyTextField();
        button2 = new com.tlk.swing.Button();
        btnHomNay = new com.tlk.swing.Button();
        txtNgayKetThuc = new com.tlk.swing.MyTextField();
        button3 = new com.tlk.swing.Button();
        jLabel1 = new javax.swing.JLabel();
        panelBorder4 = new com.tlk.swing.PanelBorder();
        rdoChuaApDung = new javax.swing.JRadioButton();
        rdoDangApDung = new javax.swing.JRadioButton();
        rdoNgungApDung = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        panelBorder5 = new com.tlk.swing.PanelBorder();
        jLabel2 = new javax.swing.JLabel();
        rdoGiamTheoPhanTram = new javax.swing.JRadioButton();
        rdoGiamTheoGia = new javax.swing.JRadioButton();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhuyenMai = new javax.swing.JTable();
        panelBorder3 = new com.tlk.swing.PanelBorder();
        btnThem = new com.tlk.swing.ButtonBig1();
        buttonBig12 = new com.tlk.swing.ButtonBig1();
        btnCapNhat = new com.tlk.swing.ButtonBig1();
        btnXoa = new com.tlk.swing.ButtonBig1();

        dateBatDau.setForeground(new java.awt.Color(51, 102, 255));
        dateBatDau.setTextRefernce(txtNgayBatDau);

        dateKetThuc.setForeground(new java.awt.Color(51, 102, 255));
        dateKetThuc.setTextRefernce(txtNgayKetThuc);

        setBackground(new java.awt.Color(255, 255, 255));

        panelBorder1.setBackground(new java.awt.Color(153, 153, 255));

        txtMaKhuyenMai.setHint("Mã khuyễn mãi");
        txtMaKhuyenMai.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtGiaTriGiamGia.setHint("Giá trị giảm giá");
        txtGiaTriGiamGia.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtNgayBatDau.setHint("Ngày bắt đầu");
        txtNgayBatDau.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        button2.setBackground(new java.awt.Color(51, 102, 255));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("...");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        btnHomNay.setBackground(new java.awt.Color(51, 102, 255));
        btnHomNay.setForeground(new java.awt.Color(255, 255, 255));
        btnHomNay.setText("Hôm nay");
        btnHomNay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomNayActionPerformed(evt);
            }
        });

        txtNgayKetThuc.setHint("Ngày kết thúc");
        txtNgayKetThuc.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        button3.setBackground(new java.awt.Color(51, 102, 255));
        button3.setForeground(new java.awt.Color(255, 255, 255));
        button3.setText("...");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("KHUYẾN MÃI");

        TrangThaiApDung.add(rdoChuaApDung);
        rdoChuaApDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoChuaApDung.setText("Chưa áp dụng");

        TrangThaiApDung.add(rdoDangApDung);
        rdoDangApDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoDangApDung.setText("Đang áp dụng");

        TrangThaiApDung.add(rdoNgungApDung);
        rdoNgungApDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNgungApDung.setText("Ngưng áp dụng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Trạng thái áp dụng");

        javax.swing.GroupLayout panelBorder4Layout = new javax.swing.GroupLayout(panelBorder4);
        panelBorder4.setLayout(panelBorder4Layout);
        panelBorder4Layout.setHorizontalGroup(
            panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdoDangApDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoChuaApDung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoNgungApDung, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder4Layout.setVerticalGroup(
            panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoChuaApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoDangApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoNgungApDung, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Hình thức");

        HinhThucGiamGia.add(rdoGiamTheoPhanTram);
        rdoGiamTheoPhanTram.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoGiamTheoPhanTram.setText("Giảm theo % thanh toán");

        HinhThucGiamGia.add(rdoGiamTheoGia);
        rdoGiamTheoGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoGiamTheoGia.setText("Giảm giá theo số tiền");

        javax.swing.GroupLayout panelBorder5Layout = new javax.swing.GroupLayout(panelBorder5);
        panelBorder5.setLayout(panelBorder5Layout);
        panelBorder5Layout.setHorizontalGroup(
            panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder5Layout.createSequentialGroup()
                .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2))
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoGiamTheoGia, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoGiamTheoPhanTram))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBorder5Layout.setVerticalGroup(
            panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder5Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoGiamTheoPhanTram, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoGiamTheoGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder1Layout.createSequentialGroup()
                        .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                                        .addComponent(panelBorder5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtGiaTriGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panelBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelBorder1Layout.createSequentialGroup()
                                        .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelBorder1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addComponent(txtMaKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(panelBorder5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(txtGiaTriGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(panelBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        panelBorder2.setBackground(new java.awt.Color(204, 204, 204));

        tblKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhuyenMai);

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        panelBorder3.setBackground(new java.awt.Color(153, 153, 255));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        buttonBig12.setText("Mới");
        buttonBig12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig12ActionPerformed(evt);
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
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonBig12, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBorder3Layout.setVerticalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelBorder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBorder3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhuyenMaiMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblKhuyenMai.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblKhuyenMaiMouseClicked

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        dateBatDau.showPopup();
        
    }//GEN-LAST:event_button2ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        dateKetThuc.showPopup();
    }//GEN-LAST:event_button3ActionPerformed

    private void btnHomNayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomNayActionPerformed
        dateBatDau.toDay();
        
    }//GEN-LAST:event_btnHomNayActionPerformed

    private void buttonBig12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig12ActionPerformed
        clearForm();
    }//GEN-LAST:event_buttonBig12ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        themKhuyenMai();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        capnhatKhuyenMai();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaKhuyenMai();
    }//GEN-LAST:event_btnXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup HinhThucGiamGia;
    private javax.swing.ButtonGroup TrangThaiApDung;
    private com.tlk.swing.ButtonBig1 btnCapNhat;
    private com.tlk.swing.Button btnHomNay;
    private com.tlk.swing.ButtonBig1 btnThem;
    private com.tlk.swing.ButtonBig1 btnXoa;
    private com.tlk.swing.Button button2;
    private com.tlk.swing.Button button3;
    private com.tlk.swing.ButtonBig1 buttonBig12;
    private com.raven.datechooser.DateChooser dateBatDau;
    private com.raven.datechooser.DateChooser dateKetThuc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder2;
    private com.tlk.swing.PanelBorder panelBorder3;
    private com.tlk.swing.PanelBorder panelBorder4;
    private com.tlk.swing.PanelBorder panelBorder5;
    private javax.swing.JRadioButton rdoChuaApDung;
    private javax.swing.JRadioButton rdoDangApDung;
    private javax.swing.JRadioButton rdoGiamTheoGia;
    private javax.swing.JRadioButton rdoGiamTheoPhanTram;
    private javax.swing.JRadioButton rdoNgungApDung;
    private javax.swing.JTable tblKhuyenMai;
    private com.tlk.swing.MyTextField txtGiaTriGiamGia;
    private com.tlk.swing.MyTextField txtMaKhuyenMai;
    private com.tlk.swing.MyTextField txtNgayBatDau;
    private com.tlk.swing.MyTextField txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
