package com.tlk.panel;

import com.tlk.dao.LoaiSanPhamDAO;
import com.tlk.dao.SanPhamDAO;
import com.tlk.entity.LoaiSanPham;
import com.tlk.entity.SanPham;
import com.tlk.main.PanelInteractionListener;
import com.tlk.utils.Auth;
import com.tlk.utils.CustomAlertTrue;
import com.tlk.utils.MsgBox;
import com.tlk.utils.XImage2;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import javax.swing.table.DefaultTableModel;

/**
 * //
 *
 *
 * @author MACBOOK Pro
 */
public class SanPhamPanel extends javax.swing.JPanel {

    SanPhamDAO spdao = new SanPhamDAO();
    LoaiSanPhamDAO loaiDao = new LoaiSanPhamDAO();

    JFileChooser jtc = new JFileChooser("D:\\DuAn1\\TLK_Team");
    int row = -1;
    String TenAnh = null;
    
    private PanelInteractionListener listener;
    public void setPanelInteractionListener(PanelInteractionListener listener) {
        this.listener = listener;
    }
    /**
     * Creates new form HoaDon
     */
    private static List<String> date = new ArrayList<>();
    private static List<String> date2 = new ArrayList<>();

    public SanPhamPanel() {
        initComponents();
        this.row = -1;
        this.updateStatus();
        tblSanPham.fixTable(jScrollPane1);

        fillcomboboxLoaiSP1();
        addComboboxSize();
        fillcomboboxLoaiSP();

        fillToTable();
        addComboboxSapXep();
        clearForm2();

    }

    void updateStatus() {
        boolean edit = (this.row >= 0);

        // trạng thái form
        btnThemSP.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
        // Trạng thái Điều hướng

    }

    public static List<String> getData() {

        date.add("M");
        date.add("L");

        return date;
    }

    public static List<String> getData2() {
        date2.add("Từ thấp đến cao");
        date2.add("Từ cao đến thấp");
        return date2;
    }

    public void addComboboxSize() {
        List<String> data = getData();
        for (String dt : data) {
            cboSIze.addItem(dt);
        }
        cboSIze.setSelectedIndex(-1);
    }

    public void addComboboxSapXep() {
        List<String> data = getData2();
        for (String dt : data) {
            cboSapXepTheoGia.addItem(dt);
        }

    }

    void fillcomboboxLoaiSP1() {
  cboLoaiSP1.setSelectedIndex(-1);
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiSP1.getModel();
        model.removeAllElements();
        List<LoaiSanPham> list = loaiDao.selectAll();
        for (LoaiSanPham cd : list) {
            model.addElement(cd.getTenloaisanpham());
        }

      

    }

    void fillcomboboxLoaiSP() { 
        cboLoaiSP.setSelectedIndex(-1);
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiSP.getModel();
        model.removeAllElements();
        List<LoaiSanPham> list = loaiDao.selectAll();
        for (LoaiSanPham cd : list) {
            model.addElement(cd.getTenloaisanpham());
        }
       
    }

    void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {

            String keyWord = txtTim.getText();
            List<SanPham> list = spdao.selectByKeyWord(keyWord);
            for (SanPham nv : list) {
                Object[] row = {
                    nv.getMasanpham(),
                    nv.getTensanpham(),
                    nv.getSize(),
                    String.format("%.0f", nv.getGia())+" VND",
                    nv.getLoaisanpham()

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            //  MsgBox.alert(this, "Lỗi dữ liệu");
        }
    }

    void fillToTable2(String selectedLoaiSanPham) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectAllByLoaiSanPham(selectedLoaiSanPham);
            for (SanPham nv : list) {
                Object[] row = {
                    nv.getMasanpham(),
                    nv.getTensanpham(),
                    nv.getSize(),
                    nv.getGia(),
                    nv.getLoaisanpham()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            //  MsgBox.alert(this, "Lỗi dữ liệu");
        }
    }

    SanPham getForm() {
        SanPham cd = new SanPham();

        cd.setTensanpham(txtTenSP.getText());

        cd.setGia(Double.parseDouble(txtGiaSP.getText()));
        String selectedValue = (String) cboSIze.getSelectedItem();
        cd.setSize(selectedValue);

        LoaiSanPham loaidao = loaiDao.selectByName(cboLoaiSP1.getSelectedItem());
        if (loaidao != null) {
            int selectedValue2 = (int) loaidao.getMaloaisanpham();
            cd.setLoaisanpham(selectedValue2);
        } else {
            MsgBox.alert(this, "Lỗi dữ liệu");
        }

        if (lblanh.getToolTipText() != null) {
            cd.setHinh(lblanh.getToolTipText());
        } else {
            cd.setHinh("No Image");
        }

        return cd;
    }

    void setForm(SanPham cd) {
        txtMaSP.setText(cd.getMasanpham());
        txtTenSP.setText(cd.getTensanpham());
        txtGiaSP.setText(String.format("%.0f", cd.getGia()));
        int loai = cd.getLoaisanpham();
//        int index2 = -1;

        LoaiSanPham loaidao = loaiDao.selectByID(tblSanPham.getValueAt(row, 4));
//   

        if (loaidao != null) {
            cboLoaiSP1.setSelectedItem(loaidao.getTenloaisanpham());
        } else {
            MsgBox.alert(this, "Lỗi dữ liệu");
        }

        String size = cd.getSize();
        int index = -1;
        for (int i = 0; i < cboSIze.getItemCount(); i++) {
            if (cboSIze.getItemAt(i).equals(size)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            cboSIze.setSelectedIndex(index);
        }

        if (cd.getHinh() != null && !cd.getHinh().equalsIgnoreCase("No Image")) {
            ImageIcon imageIcon = XImage2.read(cd.getHinh());
            Image image = imageIcon.getImage();

            int lblanhWidth = lblanh.getWidth();
            int lblanhHeight = lblanh.getHeight();

            Image resizedImage = image.getScaledInstance(lblanhWidth, lblanhHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            lblanh.setText("");
            lblanh.setIcon(resizedIcon);
            lblanh.setToolTipText(cd.getHinh());
        } else {
            lblanh.setText("Hình ảnh");
            lblanh.setIcon(null);
            TenAnh = null;
        }
    }

    void chonAnh() {
        JFileChooser jtc = new JFileChooser();

        // Thiết lập thư mục gốc cho JFileChooser ở thư mục hiện tại của project
        String currentDirectory = System.getProperty("user.dir");
        jtc.setCurrentDirectory(new File(currentDirectory));
        if (jtc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {

            File file = jtc.getSelectedFile();
            if (file == null) {

                return;
            }
            XImage2.save(file); // Lưu ình vào thư mục logos
            ImageIcon icon = XImage2.read(file.getName());
            Image image = icon.getImage();

            int lblanhWidth = lblanh.getWidth();
            int lblanhHeight = lblanh.getHeight();
            Image resizedImage = image.getScaledInstance(lblanhWidth, lblanhHeight, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            lblanh.setText("");
            lblanh.setIcon(resizedIcon);
            lblanh.setToolTipText(file.getName());//GIữ tên hình trong tôltip
            TenAnh = null;
        } else {
            MsgBox.alert(this, "Vui lòng chỉ chọn ảnh");
        }

    }

    void edit() {
        String maNV = (String) tblSanPham.getValueAt(this.row, 0);
        SanPham nv = spdao.selectByID(maNV);
        this.setForm(nv);

        this.updateStatus();
    }

    void clearForm() {
        SanPham nv = new SanPham();
        //this.setForm(nv);
        this.row = -1;
        this.updateStatus();
        // this.fillToTable();
        tblSanPham.clearSelection();
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtGiaSP.setText("");
        cboSIze.setSelectedIndex(-1);
        cboLoaiSP1.setSelectedIndex(-1);
        cboLoaiSP.setSelectedIndex(-1);
        lblanh.setText("Hình ảnh");
        lblanh.setIcon(null);
        TenAnh = null;
        cboSapXepTheoGia.setSelectedIndex(-1);
    }

    void clearForm2() {

        cboSIze.setSelectedIndex(-1);
        cboLoaiSP1.setSelectedIndex(-1);
        cboLoaiSP.setSelectedIndex(-1);
        lblanh.setText("Hình ảnh");
        lblanh.setIcon(null);
        TenAnh = null;
        cboSapXepTheoGia.setSelectedIndex(-1);
    }

    void insert() {
        SanPham cd = getForm();
        try {
            spdao.insert(cd);
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
            MsgBox.alert(this, "Bạn chưa chọn sản phẩm cần cập nhật");
            return;
        }

        SanPham sp = getForm();
        sp.setMasanpham(txtMaSP.getText());
        if (Auth.isManager()) {

            try {
                spdao.update(sp);
                fillToTable();
                clearForm();
                MsgBox.alert(this, "Cập nhật thành công");
//              MsgBox.alert2("Cập nhật thành công");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Lỗi cập nhật sản phẩm");
            }
        } else {
            MsgBox.alert(this, "Bạn không có quyền Cập nhật thông tin ");
            clearForm();
        }

    }

    private void delete() {
        if (Auth.isManager()) {

            if (row != -1) {
                String maNV = txtMaSP.getText();
                if (MsgBox.confirm(this, "Bạn có chắc muốn xoá sản phẩm này")) {
                    try {
                        spdao.delete(maNV);
                        this.fillToTable();
                        clearForm();
                        MsgBox.alert( this,"Xoá sản phẩm thành công");
//                                insertLichSu(nv, "Update");

                    } catch (Exception e) {
                        MsgBox.alert(this, "xoá thất bại");

                    }
                }
            } else {
                MsgBox.alert(this, "Bạn chưa chọn sản phẩm nào");
            }
        } else {
            MsgBox.alert(this, "Bạn không có quyền Xóa thông tin ");
            clearForm();
        }

    }

    void fillToTableThapDenCao() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectAllSapXepTheoThapDenCao();
            for (SanPham nv : list) {
                Object[] row = {
                    nv.getMasanpham(),
                    nv.getTensanpham(),
                    nv.getSize(),
                    nv.getGia(),
                    nv.getLoaisanpham()

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            //  MsgBox.alert(this, "Lỗi dữ liệu");
        }
    }

    void fillToTableCaoDenThap() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = spdao.selectAllSapXepTheoCaoDenThap();
            for (SanPham nv : list) {
                Object[] row = {
                    nv.getMasanpham(),
                    nv.getTensanpham(),
                    nv.getSize(),
                    nv.getGia(),
                    nv.getLoaisanpham()

                };
                model.addRow(row);
            }
        } catch (Exception e) {
            //  MsgBox.alert(this, "Lỗi dữ liệu");
        }
    }

    boolean Validate() {
        String errorMessage = "";
        if (txtTenSP.getText().isEmpty()) {
            errorMessage += "Tên sản phẩm đang trống\n";
        }
        if (txtGiaSP.getText().isEmpty()) {
            errorMessage += "Giá sản phẩm đang trống\n";
        } else {
            double gia = 0;
            try {
                gia = Double.parseDouble(txtGiaSP.getText());
                if (gia < 1000 || gia > 1000000) {
                    errorMessage += "Giá sản phẩm chưa hợp lí\n";
                }
            } catch (Exception e) {
                errorMessage += "Giá tiền sai định dạng\n";
            }
        }
        if (cboLoaiSP1.getSelectedIndex() == -1) {
            errorMessage += "Chưa chọn loại sản phẩm\n";
        }
        if (cboSIze.getSelectedIndex() == -1) {
            errorMessage += "Chưa chọn Size\n";
        }
        if (!errorMessage.equals("")) {
            MsgBox.alert(this, errorMessage);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        panelRound2 = new com.tlk.swing.PanelRound();
        txtMaSP = new com.tlk.swing.MyTextField();
        txtTenSP = new com.tlk.swing.MyTextField();
        txtGiaSP = new com.tlk.swing.MyTextField();
        btnThemSP = new com.tlk.swing.ButtonOutLine();
        btnXoa = new com.tlk.swing.ButtonOutLine();
        btnMoi = new com.tlk.swing.ButtonOutLine();
        btnCapNhat = new com.tlk.swing.ButtonOutLine();
        button1 = new com.tlk.swing.Button();
        cboLoaiSP1 = new combobox.Combobox();
        cboSIze = new combobox.Combobox();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        lblanh = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelRound1 = new com.tlk.swing.PanelRound();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new tabledark.TableDark();
        txtTim = new com.tlk.swing.MyTextField();
        btnTim = new com.tlk.swing.Button();
        cboLoaiSP = new combobox.Combobox();
        cboSapXepTheoGia = new combobox.Combobox();

        setBackground(new java.awt.Color(204, 204, 204));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaSP.setEditable(false);
        txtMaSP.setBackground(new java.awt.Color(255, 255, 255));
        txtMaSP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 153)));
        txtMaSP.setForeground(new java.awt.Color(0, 51, 51));
        txtMaSP.setHint("Mã sản phâm");
        panelRound2.add(txtMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 260, 40));

        txtTenSP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtTenSP.setForeground(new java.awt.Color(0, 51, 51));
        txtTenSP.setHint("Tên sản phẩm");
        panelRound2.add(txtTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 260, 40));

        txtGiaSP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 153), 1, true));
        txtGiaSP.setForeground(new java.awt.Color(0, 51, 51));
        txtGiaSP.setHint("Giá sản phẩm");
        txtGiaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaSPActionPerformed(evt);
            }
        });
        panelRound2.add(txtGiaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 260, 40));

        btnThemSP.setBackground(new java.awt.Color(153, 153, 255));
        btnThemSP.setText("THÊM");
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });
        panelRound2.add(btnThemSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 120, 40));

        btnXoa.setBackground(new java.awt.Color(153, 153, 255));
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        panelRound2.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, 120, 40));

        btnMoi.setBackground(new java.awt.Color(153, 153, 255));
        btnMoi.setText("MỚI");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        panelRound2.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 120, 40));

        btnCapNhat.setBackground(new java.awt.Color(153, 153, 255));
        btnCapNhat.setText("CẬP  NHẬT");
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        panelRound2.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 120, 40));

        button1.setBackground(new java.awt.Color(153, 153, 153));
        button1.setForeground(new java.awt.Color(255, 255, 255));
        button1.setText("Thêm loại");
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button1MouseExited(evt);
            }
        });
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        panelRound2.add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 100, 40));

        cboLoaiSP1.setBackground(new java.awt.Color(102, 255, 255));
        cboLoaiSP1.setLabeText("Loai sản phẩm");
        cboLoaiSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiSP1ActionPerformed(evt);
            }
        });
        panelRound2.add(cboLoaiSP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 142, 250, 40));

        cboSIze.setBackground(new java.awt.Color(102, 255, 255));
        cboSIze.setLabeText("Kích thước");
        panelRound2.add(cboSIze, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 82, 260, 40));

        jPanel1.add(panelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 650, 260));

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        lblanh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblanh.setForeground(new java.awt.Color(102, 102, 102));
        lblanh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblanh.setText("Hình ảnh");
        lblanh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102), 3));
        lblanh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblanhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(panelBorder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 250, 260));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 290));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(51, 51, 0));
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã ", "Tên sản phẩm", "Size", "Giá", "Loại sản phẩm"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(150);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(150);
            tblSanPham.getColumnModel().getColumn(1).setMinWidth(250);
            tblSanPham.getColumnModel().getColumn(1).setMaxWidth(250);
            tblSanPham.getColumnModel().getColumn(2).setMinWidth(100);
            tblSanPham.getColumnModel().getColumn(2).setMaxWidth(100);
            tblSanPham.getColumnModel().getColumn(3).setMinWidth(230);
            tblSanPham.getColumnModel().getColumn(3).setMaxWidth(230);
        }

        panelRound1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 910, 230));

        txtTim.setHint("Nhập tên sản phẩm");
        txtTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimActionPerformed(evt);
            }
        });
        panelRound1.add(txtTim, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 200, 40));

        btnTim.setBackground(new java.awt.Color(102, 255, 255));
        btnTim.setForeground(new java.awt.Color(102, 102, 102));
        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });
        panelRound1.add(btnTim, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 80, 40));

        cboLoaiSP.setBackground(new java.awt.Color(102, 255, 255));
        cboLoaiSP.setLabeText("Loai");
        cboLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiSPActionPerformed(evt);
            }
        });
        panelRound1.add(cboLoaiSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 40));

        cboSapXepTheoGia.setBackground(new java.awt.Color(102, 255, 255));
        cboSapXepTheoGia.setLabeText("Sắp xếp");
        cboSapXepTheoGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSapXepTheoGiaActionPerformed(evt);
            }
        });
        panelRound1.add(cboSapXepTheoGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 12, 220, 40));

        jPanel2.add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 930, 310));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 950, 320));
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblSanPham.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void lblanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblanhMouseClicked
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblanhMouseClicked

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        if (txtTim.getText().isEmpty()) {
            fillToTable();

        } else {
            fillToTable();
            clearForm();
            txtTim.setText("");
        }
    }//GEN-LAST:event_btnTimActionPerformed

    private void txtTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimActionPerformed

    private void cboLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiSPActionPerformed
        if (cboLoaiSP.getSelectedIndex() != -1) {
            String selectedLoaiSanPham = (String) cboLoaiSP.getSelectedItem();
            fillToTable2(selectedLoaiSanPham);
        } else {
            fillToTable();
        }
    }//GEN-LAST:event_cboLoaiSPActionPerformed

    private void cboSapXepTheoGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSapXepTheoGiaActionPerformed
        if (cboSapXepTheoGia.getSelectedIndex() != -1) {
            if (cboSapXepTheoGia.getSelectedIndex() == 0) {
                fillToTableThapDenCao();
            } else {
                fillToTableCaoDenThap();
            }
        } else {
            fillToTable();
        }

    }//GEN-LAST:event_cboSapXepTheoGiaActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        LoaiSaanPhamJdialog dialog = new LoaiSaanPhamJdialog();
        dialog.setVisible(true);
    }//GEN-LAST:event_button1ActionPerformed

    private void button1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseExited
        fillcomboboxLoaiSP1();
        cboLoaiSP1.setSelectedIndex(-1);
        fillcomboboxLoaiSP();
        cboLoaiSP.setSelectedIndex(-1);
        if (listener != null) {
            listener.onFillCboLoai();
        }
    }//GEN-LAST:event_button1MouseExited

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (Validate() == true) {
            update();
            if (listener != null) {
                listener.onFillCboLoai();
            }
        }

    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
        fillToTable();

    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed

        if (Validate() == true) {
            delete();
            if (listener != null) {
                listener.onFillCboLoai();
            }   
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed

        if (Auth.isManager()) {
            if (Validate() == true) {
                insert();
                if (listener != null) {
                    listener.onFillCboLoai();
                }
            }
        } else {
            MsgBox.alert(this, "Bạn không có quyền thêm sản phẩm");
            clearForm();
        }
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void txtGiaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaSPActionPerformed

    private void cboLoaiSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiSP1ActionPerformed

    }//GEN-LAST:event_cboLoaiSP1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonOutLine btnCapNhat;
    private com.tlk.swing.ButtonOutLine btnMoi;
    private com.tlk.swing.ButtonOutLine btnThemSP;
    private com.tlk.swing.Button btnTim;
    private com.tlk.swing.ButtonOutLine btnXoa;
    private com.tlk.swing.Button button1;
    private combobox.Combobox cboLoaiSP;
    private combobox.Combobox cboLoaiSP1;
    private combobox.Combobox cboSIze;
    private combobox.Combobox cboSapXepTheoGia;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblanh;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelRound panelRound1;
    private com.tlk.swing.PanelRound panelRound2;
    private tabledark.TableDark tblSanPham;
    private com.tlk.swing.MyTextField txtGiaSP;
    private com.tlk.swing.MyTextField txtMaSP;
    private com.tlk.swing.MyTextField txtTenSP;
    private com.tlk.swing.MyTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
