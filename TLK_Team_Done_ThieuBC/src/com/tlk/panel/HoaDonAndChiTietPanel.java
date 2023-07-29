
package com.tlk.panel;

import com.tlk.compoment.GopHoaDon;
import static com.tlk.compoment.GopHoaDon.mahdgop;
import com.tlk.compoment.ScrollPanelThu;
import com.tlk.compoment.TachHoaDon;
import com.tlk.dao.ChiTietTopingDAO;
import com.tlk.dao.HoaDonChiTietDAO;
import com.tlk.dao.HoaDonDAO;
import com.tlk.dao.KhuyenMaiDAO;
import com.tlk.dao.LoaiSanPhamDAO;
import com.tlk.dao.NhanVienDAO;
import com.tlk.dao.SanPhamDAO;
import com.tlk.dao.TenThucUongDAO;
import com.tlk.dao.TopingDAO;
import com.tlk.entity.ChiTietToping;
import com.tlk.entity.HoaDon;
import com.tlk.entity.HoaDonChiTiet;
import com.tlk.entity.KhuyenMai;
import com.tlk.entity.LoaiSanPham;
import com.tlk.entity.NhanVien;
import com.tlk.entity.SanPham;
import com.tlk.entity.TenThucUong;
import com.tlk.entity.Toping;
import com.tlk.main.DemoPrint;
import com.tlk.main.PanelInteractionListener;
import com.tlk.main.TachHoaDonListener;
import com.tlk.utils.Auth;
import com.tlk.utils.MsgBox;
import com.tlk.utils.XDate;
import com.tlk.utils.XImage2;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class HoaDonAndChiTietPanel extends javax.swing.JPanel {
    HoaDonDAO dao = new HoaDonDAO();
    NhanVienDAO nvdao = new NhanVienDAO();
    KhuyenMaiDAO kmdao = new KhuyenMaiDAO();
    LoaiSanPhamDAO loaidao = new LoaiSanPhamDAO();
    SanPhamDAO spdao = new SanPhamDAO();
    HoaDonChiTietDAO ctdao = new HoaDonChiTietDAO();
    TenThucUongDAO tudao = new TenThucUongDAO();
    TopingDAO tpdao = new TopingDAO();
    ChiTietTopingDAO cttpdao = new ChiTietTopingDAO();
    
    int row = -1;
    int rowChiTiet = -1;
    public static String mahd = null;
    Double thanhtoan = 0.0,khuyenmai = 0.0,tongthanhtoan =0.0;
    String TenAnh = null;
    
    //Khởi tạo biến interface
    private PanelInteractionListener listener;
    public void setPanelInteractionListener(PanelInteractionListener listener) {
        this.listener = listener;
    }
    
    public HoaDonAndChiTietPanel() {
       initComponents();
       tabs.setSelectedIndex(0);
       fillToTable();
       fillComboBoxKhuyenMai();
       fillComBoboxLoaiSanPham();
       fillComboBoxSize();
       fillComboBoxDa();
       fillComboBoxDuong();
       fillToTableChiTiet();
       
       
       
       
       
    }
    
    
    
    public static KhuyenMai khuyemaiapdung;

    public void fillComboBoxKhuyenMai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhuyenMai.getModel();
        model.removeAllElements();
        model.addElement("Không áp dụng");
        List<KhuyenMai> list = kmdao.selectAllDangApDung();
        for (KhuyenMai km : list) {
            model.addElement(km);
        }
        
    }
    
    public void fillChkToping(){
        this.scrollPanelThu1.addchk();
        System.out.println("thành công");
    }


    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
//        model.setColumnIdentifiers(new String[]{"Mã ", "Ngày tạo", "Trạng thái", "Khuyến mãi", "Tổng thanh toán"});
        try {
//            String keyword = txtTimKiem1.getText();
            List<HoaDon> list = dao.selectAll();
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }

    void setFrom(HoaDon nh) {
        txtMaHoaDon.setText(nh.getMahoadon());
        String ngaytao = "";
        if (nh.getNgaytao() != null) {
            ngaytao = XDate.convertDateFormat(nh.getNgaytao(), "dd-MM-yyyy HH:mm");
        }
       
        txtNgayTao.setText(ngaytao);
        if (nh.getTongthanhtoanKM() != null) {
            txtTongThanhToan.setText(String.valueOf(String.format("%,.0f", nh.getTongthanhtoanKM())));
        } else {
            txtTongThanhToan.setText("");
        }
        NhanVien nv = nvdao.selectByID(nh.getTendangnhap());
        if (nv != null) {
            txtNhanVien.setText(nv.getHovaten());
        }
//        if(nh.getMakhuyenmai()!=null){
//            KhuyenMai km = kmdao.selectByID(nh.getMakhuyenmai());
//            System.out.println(km.toString());
//            if(km!=null){
//                cboKhuyenMai.setSelectedItem(km);
//            }
//        }
        if (nh.getMakhuyenmai() != null) {
            String selectedKhuyenMaiID = nh.getMakhuyenmai();
            if (cboKhuyenMai.getItemCount() > 0) {
                for (int i = 0; i < cboKhuyenMai.getItemCount(); i++) {
                    Object item = cboKhuyenMai.getItemAt(i);
                    if (item instanceof KhuyenMai) {
                        KhuyenMai km = (KhuyenMai) item;
                        if (km.getMakhuyenmai().equals(selectedKhuyenMaiID)) {
                            cboKhuyenMai.setSelectedItem(item);
                            break;
                        }
                    }
                }
            }
        } else {
            cboKhuyenMai.setSelectedIndex(0);
        }

        txtGhiChu.setText(nh.getGhichu());
    }

    public void edit() {
        String mahd = (String) tblHoaDon.getValueAt(this.row, 0);
        HoaDon hd = dao.selectByID(mahd);
        this.setFrom(hd);

//        this.updateStatus();
    }

    void clearFrom() {
        HoaDon nh = new HoaDon();
        this.setFrom(nh);
        txtNhanVien.setText("");
        this.row = -1;
        tblHoaDon.clearSelection();
        cboKhuyenMai.setSelectedIndex(0);
//        this.updateStatus();
    }

    HoaDon getForm() {
        HoaDon hd = new HoaDon();
        hd.setGhichu(txtGhiChu.getText());
        if (!cboKhuyenMai.getSelectedItem().equals("Không áp dụng")) {
            hd.setMakhuyenmai(khuyemaiapdung.getMakhuyenmai());
        }
        hd.setTendangnhap(Auth.user.getTendangnhap());
        return hd;
    }

    boolean checkDataThem() {

        return true;
    }

    public void ThemHoaDon() {
        if (checkDataThem()) {

            if (!cboKhuyenMai.getSelectedItem().equals("Không áp dụng")) {
                khuyemaiapdung = (KhuyenMai) cboKhuyenMai.getSelectedItem();
                HoaDon hd = getForm();
                try {
                    dao.insert(hd);
                    this.fillToTable();
                    this.clearFrom();
                    MsgBox.alert(this, "Thêm mới thành công");
                    int rowCount = tblHoaDon.getRowCount();
                    this.row = rowCount-1;
                    mahd = String.valueOf(tblHoaDon.getValueAt(rowCount - 1, 0));
                    System.out.println(mahd);
                    fillToTableChiTiet();
                    this.thanhtoan = 0.0;
                    this.khuyenmai = 0.0;
                    this.tongthanhtoan = 0.0;
                    capNhatGiaTri();
                    //                    insertLichSu(cd.getMaCD(), "Insert");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Thêm mới thất bại");
                }
            } else {
                try {
                    HoaDon hd = getForm();
                    dao.insertKhongKhuyenMai(hd);
                    this.fillToTable();
                    this.clearFrom();
                    MsgBox.alert(this, "Thêm mới thành công");
                    int rowCount = tblHoaDon.getRowCount();
                    this.row = rowCount-1;
                    mahd = String.valueOf(tblHoaDon.getValueAt(rowCount - 1, 0));
                    fillToTableChiTiet();
                    this.thanhtoan = 0.0;
                    this.khuyenmai = 0.0;
                    this.tongthanhtoan = 0.0;
                    capNhatGiaTri();
                    //                    insertLichSu(cd.getMaCD(), "Insert");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Thêm mới thất bại");
                }
            }
        }
    }
    
    void huyHoaDon(){
        if(this.row!=-1){
            String mahdHuy = (String) tblHoaDon.getValueAt(row, 0);
            if(MsgBox.confirm(this, "Bạn chắc chắn muốn huỷ đơn hàng này")){
                dao.delete(mahd);
                MsgBox.alert(this, "Huỷ thành công");
                fillToTable();
                clearFrom();
            }
        } else{
            MsgBox.alert(this, "Bạn chưa chọn đơn hàng nào để xoá");
        }
    }
    
    void capNhatHoaDon(){
        if(this.row!=-1){
            String mahdCapNhat= (String) tblHoaDon.getValueAt(row, 0);
            if(MsgBox.confirm(this, "Bạn chắc chắn muốn cập nhật đơn hàng này")){
                if (!cboKhuyenMai.getSelectedItem().equals("Không áp dụng")){
                    khuyemaiapdung = (KhuyenMai) cboKhuyenMai.getSelectedItem();
                    HoaDon hd = getForm();
                    hd.setMahoadon(mahdCapNhat);
                    try {
                        dao.update(hd);
                        MsgBox.alert(this, "Cập nhật thành công");
                        this.edit();
    //                    int rowCount = tblHoaDon.getRowCount();
    //                    mahd = String.valueOf(tblHoaDon.getValueAt(rowCount - 1, 0));
    //                    //                    insertLichSu(cd.getMaCD(), "Insert");
                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgBox.alert(this, "Cập nhật thất bại");
                    }
                } else {
                    HoaDon hd = getForm();
                    hd.setMahoadon(mahdCapNhat);
                    try {
                        dao.update(hd);
                        MsgBox.alert(this, "Cập nhật thành công");
                        this.edit();
                        //                    insertLichSu(cd.getMaCD(), "Insert");
                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgBox.alert(this, "Cập nhật thất bại");
                    }
                }
                this.UpdateTongThanhToan();
                this.capNhatGiaTri();
                this.fillToTable();
                tblHoaDon.setRowSelectionInterval(row, row);
            }
        } else{
            MsgBox.alert(this, "Bạn chưa chọn đơn hàng nào để cập nhật");
        }
    }
    
    void thanhToanHoaDon(){
        System.out.println(this.mahd);
        
        dao.updateTrangThaiThanhToan(mahd);
        fillToTable();
        mahd="";
        fillToTableChiTiet();
        fillToTableThanhToan();
        clearFrom();
        clearFromChiTiet();
        MsgBox.alert(this, "Thanh toán hoàn tất");
        tabs.setSelectedIndex(0);
    }
    
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
    
    void fillComboBoxDa(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDuong1.getModel();
        model.removeAllElements();
        model.addElement("0%");
        model.addElement("25%");
        model.addElement("50%");
        model.addElement("75%");
        model.addElement("100%");
        cboDuong1.setSelectedItem("50%");
    }
    void fillComboBoxDuong(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDa1.getModel();
        model.removeAllElements();
        model.addElement("0%");
        model.addElement("25%");
        model.addElement("50%");
        model.addElement("75%");
        model.addElement("100%");
        cboDa1.setSelectedItem("50%");
    }
    
    public void fillComBoboxLoaiSanPham(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoai1.getModel();
        model.removeAllElements();
        List<LoaiSanPham> list = loaidao.selectAll();
        for (LoaiSanPham loai : list) {
            model.addElement(loai);
        }
        this.fillComboBoxSanPham();
    }

    void fillComboBoxSanPham() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenNuoc.getModel();
        model.removeAllElements();
        LoaiSanPham loaisp = (LoaiSanPham) cboLoai1.getSelectedItem();
        if (loaisp != null) {
            List<TenThucUong> list = tudao.selectByTen(loaisp.getMaloaisanpham());
            for (TenThucUong tu : list) {
                model.addElement(tu);
            }
        }
        this.fillComboBoxSize();
    }

    void fillComboBoxSize() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSize.getModel();
        model.removeAllElements();
        LoaiSanPham loaisp = (LoaiSanPham) cboLoai1.getSelectedItem();
        TenThucUong tu = (TenThucUong) cboTenNuoc.getSelectedItem();
//        System.out.println(sp1);
        if (tu != null) {
            List<SanPham> list1 = spdao.selectBySanPhamAndTen(loaisp.getMaloaisanpham(), tu.getTenThucUong());
            for (SanPham sp : list1) {
                model.addElement(sp);
            }
        }
    }

    void fillToFormChiTiet() {
//        SanPham sp = (SanPham) cboSize.getSelectedItem();
        SanPham sp1 = (SanPham) cboSize.getSelectedItem();
        if (sp1 != null) {
            txtTenSP1.setText(sp1.getTensanpham());
            txtGiaSP1.setText(String.valueOf(String.format("%,.0f", sp1.getGia())));
            txtSizeSP1.setText(sp1.getSize());

            if (sp1.getHinh() != null && !sp1.getHinh().equalsIgnoreCase("No Image")) {
       
                ImageIcon imageIcon = XImage2.read(sp1.getHinh());
                Image image = imageIcon.getImage();
   
                int lblanhWidth1 = lblanh.getWidth();
                int lblanhHeight1 = lblanh.getHeight();
                System.out.println("anh w"+lblanhWidth1+"  h"+lblanhHeight1);
   
                Image resizedImage = image.getScaledInstance(125, 144, Image.SCALE_SMOOTH);
           
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

                lblanh.setText("");
                lblanh.setIcon(resizedIcon);

                lblanh.setToolTipText(sp1.getHinh());

            } else {
                lblanh.setText("Hình ảnh");
                lblanh.setIcon(null);
                TenAnh = null;
            }
        }
    }

    public void fillToTableChiTiet() {
        DefaultTableModel model = (DefaultTableModel) tblChiTiet1.getModel();
        model.setRowCount(0);
//        model.setColumnIdentifiers(new String[]{"Tên sản phẩm", "Size", "Số lượng", "Thành tiền", "Tỉ lệ đường-đá"});
        try {
            List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd);
            for (HoaDonChiTiet hdct : list) {
                SanPham sptim = spdao.selectByID(hdct.getMasanpham());
                String dsToping = "";
                List<ChiTietToping> cttp = cttpdao.selectAllByID(hdct.getMahoadonchitiet());
                for (ChiTietToping chiTietToping : cttp) {
                    if(!dsToping.equals("")){
                        dsToping = dsToping+", ";
                    }
                    Toping tpduocchon = tpdao.selectByID(chiTietToping.getMaToping());
                    dsToping = dsToping+tpduocchon.getTentoping();
                }
                
                String tileduongda = hdct.getTileduong() + " Đường -- " + hdct.getTileda() + " Đá";

                Object[] row = {hdct.getMahoadonchitiet(), sptim.getTensanpham(), sptim.getSize(), dsToping, tileduongda, hdct.getSoluong(), String.format("%,.0f", hdct.getThanhtien())};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }

    void setFromChiTiet(HoaDonChiTiet nh) {

        if (nh.getMasanpham() != null) {
            SanPham sp = spdao.selectByID(nh.getMasanpham());
            txtTenSP1.setText(sp.getTensanpham());
            txtGiaSP1.setText(String.format("%,.0f",sp.getGia()));
            txtSizeSP1.setText(sp.getSize());
            
            if (sp.getHinh() != null && !sp.getHinh().equalsIgnoreCase("No Image")) {
                ImageIcon imageIcon = XImage2.read(sp.getHinh());
                Image image = imageIcon.getImage();

                int lblanhWidth = lblanh.getWidth();
                int lblanhHeight = lblanh.getHeight();

                Image resizedImage = image.getScaledInstance(lblanhWidth, lblanhHeight, Image.SCALE_SMOOTH);
                ImageIcon resizedIcon = new ImageIcon(resizedImage);
                lblanh.setText("");
                lblanh.setIcon(resizedIcon);
                lblanh.setToolTipText(sp.getHinh());
            } else {
                lblanh.setText("Hình ảnh");
                lblanh.setIcon(null);
                TenAnh = null;
            }
            
        } else {
            txtTenSP1.setText("");
            txtGiaSP1.setText("");
            txtSizeSP1.setText("");
        }

        if (nh.getTileduong() != null) {
            cboDa1.setSelectedItem(nh.getTileduong());
            cboDuong1.setSelectedItem(nh.getTileda());
        } else {
            cboDa1.setSelectedItem("50%");
            cboDuong1.setSelectedItem("50%");
        }
        if (nh.getSoluong() != null) {
            txtSoLuong1.setText(String.valueOf(nh.getSoluong()));
        } else {
            txtSoLuong1.setText("1");
        }

        if (nh.getMasanpham() != null) {
            List<ChiTietToping> cttp = new ArrayList<>();
            cttp = cttpdao.selectAllByID(nh.getMahoadonchitiet());
            if (!cttp.isEmpty()) {
                scrollPanelThu1.clearSelectedTopings();
                for (ChiTietToping chiTietToping : cttp) {
//                    scrollPanelThu1.clearSelections();
//                    System.out.println(chiTietToping.getMaToping());
                    Toping tpduocchon = tpdao.selectByID(chiTietToping.getMaToping());
                    scrollPanelThu1.selectToping(tpduocchon.getTentoping());
                }
            } else {
                scrollPanelThu1.clearSelections();
            }
        }

    }
    
    void editHoaDonChiTiet(){
        int mahdct =  (int) tblChiTiet1.getValueAt(this.rowChiTiet, 0);
        HoaDonChiTiet hdct = ctdao.selectByID(mahdct);
        this.setFromChiTiet(hdct);
    }
    
    void clearFromChiTiet() {
        HoaDonChiTiet nh = new HoaDonChiTiet();
        this.setFromChiTiet(nh);
        this.rowChiTiet = -1;
        tblChiTiet1.clearSelection();
//        this.updateStatus();
        scrollPanelThu1.clearSelections();
    }
    
    
     
    boolean checkDataThemChiTiet(){
        if(cboSize.getSelectedItem()==null){
            MsgBox.alert(this, "Bạn chưa chọn thức uống");
            return false;
        }
        if(txtTenSP1.getText().equals("")){
            MsgBox.alert(this, "Bạn chưa chọn thức uống");
            return false;
        }
        return true;
    }
    
    HoaDonChiTiet getFormChiTiet(){
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        SanPham sp = (SanPham) cboSize.getSelectedItem();
        hdct.setMahoadon(mahd);
        hdct.setMasanpham(sp.getMasanpham());
        
        int soluong = Integer.parseInt(txtSoLuong1.getText());
        hdct.setSoluong(soluong);
        
        double thanhtien = soluong*sp.getGia();
        System.out.println(thanhtien);
        hdct.setThanhtien(thanhtien);
        
        hdct.setTileduong(String.valueOf(cboDuong1.getSelectedItem()));
        hdct.setTileda(String.valueOf(cboDa1.getSelectedItem()));
        
        return hdct;
    }
    
    void ThemHoaDonChiTiet(){
        if (checkDataThemChiTiet()) {
            List<Toping> tp = ScrollPanelThu.TopingDuocChon;
            Integer mahdct = null;
            Double tienToping = 0.0;
            for (Toping toping : tp) {
                System.out.println(toping.getTentoping());
            }
                
                HoaDonChiTiet hd = getFormChiTiet();
                try {
                    ctdao.insert(hd);
                    this.fillToTableChiTiet();
                    
                    int rowCount = tblChiTiet1.getRowCount();
                    if (rowCount > 0) {
                        mahdct = (Integer) tblChiTiet1.getValueAt(rowCount-1, 0);
                    }
                    
                    for (Toping toping : tp) {
                        ChiTietToping cttpThem = new ChiTietToping();
                        cttpThem.setMaChiTietHoaDon(mahdct);
                        cttpThem.setMaToping(toping.getMatoping());
                        cttpdao.insert(cttpThem);
                        tienToping += toping.getGiatoping();
                    }
                    Double tongTien = hd.getThanhtien() + (tienToping*hd.getSoluong());
                    ctdao.updateThanhTien(tongTien, mahdct);
                    this.fillToTableChiTiet();
                    this.clearFromChiTiet();
                    
                    UpdateTongThanhToan();
                    
//                    MsgBox.alert(this, "Thêm mới thành công");
//                  insertLichSu(cd.getMaCD(), "Insert");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Thêm mới thất bại");
                }
            }
    
    }
    
    void xoaChiTietHoaDon(){
        if(this.rowChiTiet!=-1){
            Integer macthdXoa = (Integer) tblChiTiet1.getValueAt(rowChiTiet, 0);
            cttpdao.delete(macthdXoa);
            ctdao.delete(macthdXoa);
            fillToTableChiTiet();
            clearFromChiTiet();
            
            UpdateTongThanhToan();
            
        } else{
            MsgBox.alert(this, "Bạn chưa chọn trường nào để xoá");
        }
    }
    
    void capNhatChiTietHoaDon(){
        if(this.rowChiTiet!=-1){
            Integer macthdCapnhat = (Integer) tblChiTiet1.getValueAt(rowChiTiet, 0);
            HoaDonChiTiet hdct = getFormChiTiet();
            hdct.setMahoadonchitiet(macthdCapnhat);
            ctdao.update(hdct);
            
            Double tienToping = 0.0;
            Double tongTien = 0.0;
            
            //Lấy ra các Toping đang được chọn và so sánh với Toping đã chọn của đơn câp nhật trước đó
            List<Toping> tp = ScrollPanelThu.TopingDuocChon;
            List<ChiTietToping> cttpTruocCapNhat = cttpdao.selectAllByID(macthdCapnhat);
            List<Toping> tpTruocCapNhat = new ArrayList<>();
            for (ChiTietToping chiTietToping : cttpTruocCapNhat) {
                tpTruocCapNhat.add(tpdao.selectByID(chiTietToping.getMaToping()));
            }
            
            //So sánh hai danh sách Toping
            if(tp.equals(tpTruocCapNhat)){
                for (Toping toping : tp) {
                    tienToping += toping.getGiatoping();
                } 
            } else{
                cttpdao.delete(hdct.getMahoadonchitiet());
                for (Toping toping : tp) {
                    ChiTietToping cttpThem = new ChiTietToping();
                    cttpThem.setMaChiTietHoaDon(macthdCapnhat);
                    cttpThem.setMaToping(toping.getMatoping());
                    cttpdao.insert(cttpThem);
                    tienToping += toping.getGiatoping();
                }
            }
            tongTien = hdct.getThanhtien() + (tienToping*hdct.getSoluong());
            ctdao.updateThanhTien(tongTien, macthdCapnhat);

            fillToTableChiTiet();
            clearFromChiTiet();
            
            UpdateTongThanhToan();
            
        } else{
            MsgBox.alert(this, "Bạn chưa chọn trường nào để cập nhật");
        }
    }
    
    
    public void UpdateTongThanhToan() {
        HoaDon hoadonSuDung = dao.selectByID(mahd);
        KhuyenMai kmApDungTrenDonHang = kmdao.selectByID(hoadonSuDung.getMakhuyenmai());
        if(kmApDungTrenDonHang!=null){
            String tenkm= "";
            if(kmApDungTrenDonHang.isGiamtheophantram()){
                tenkm = "KHUYẾN MÃI "+String.format("%.0f", kmApDungTrenDonHang.getGiatrikhuyenmai())+"%";
            } else{
                tenkm = "KHUYẾN MÃI "+String.format("%.0f", kmApDungTrenDonHang.getGiatrikhuyenmai())+"VND";
            }
            tblLoaikm.setText(tenkm);
        } else{
            tblLoaikm.setText("KHUYẾN MÃI");
        }
        Double tongThanhToan = 0.0;
        Double tienKhuyenMai = 0.0;
        Double tongThanhToanSauKhuyenMai = 0.0;

        List<HoaDonChiTiet> hdct = ctdao.selectAllChiTiet(hoadonSuDung.getMahoadon());
        for (HoaDonChiTiet hoaDonChiTiet : hdct) {
            tongThanhToan += hoaDonChiTiet.getThanhtien();
        }
        this.thanhtoan = tongThanhToan;
//        System.out.println("ttt: "+tongThanhToan);
        dao.updateThanhToan(tongThanhToan, mahd);
        if(kmApDungTrenDonHang!=null){
            if (kmApDungTrenDonHang.isGiamtheophantram()) {
                tienKhuyenMai=(tongThanhToan/100)*kmApDungTrenDonHang.getGiatrikhuyenmai();
                this.khuyenmai = tienKhuyenMai;
                tongThanhToanSauKhuyenMai = tongThanhToan - tienKhuyenMai;
                this.tongthanhtoan = tongThanhToanSauKhuyenMai;
                dao.updateThanhToanKM(tongThanhToanSauKhuyenMai, mahd);
                System.out.println("tkm: "+tienKhuyenMai+"  --Ttsc: "+tongThanhToanSauKhuyenMai);

            } else{
                tienKhuyenMai = kmApDungTrenDonHang.getGiatrikhuyenmai();
                this.khuyenmai = tienKhuyenMai;
                tongThanhToanSauKhuyenMai = tongThanhToan - tienKhuyenMai;
                this.tongthanhtoan = tongThanhToanSauKhuyenMai;
                dao.updateThanhToanKM(tongThanhToanSauKhuyenMai, mahd);
                System.out.println("tkm: "+tienKhuyenMai+"  --Ttsc: "+tongThanhToanSauKhuyenMai);
            }
        } else{
            this.khuyenmai =0.0;
            tongThanhToanSauKhuyenMai = tongThanhToan;
            this.tongthanhtoan = tongThanhToanSauKhuyenMai;
            dao.updateThanhToanKM(tongThanhToanSauKhuyenMai, mahd);
        }
        
        fillToTable();
        tblHoaDon.setRowSelectionInterval(row, row);
    }
    
    void capNhatGiaTri(){
        txtGiaTriTongThanhToan.setText(String.format("%,.0f", this.thanhtoan) +" VND");
        txtGiaTriKhuyenMai.setText(String.format("%,.0f", this.khuyenmai) +" VND");
        txtGiaTriThanhTien.setText(String.format("%,.0f", this.tongthanhtoan) +" VND");
    }
    
    void fillToTableThanhToan() {
        DefaultTableModel model = (DefaultTableModel) tblThanhToan.getModel();
        model.setRowCount(0);
//        model.setColumnIdentifiers(new String[]{"Tên sản phẩm", "Size", "Số lượng", "Thành tiền", "Tỉ lệ đường-đá"});
        try {
            List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd);
            for (HoaDonChiTiet hdct : list) {
                SanPham sptim = spdao.selectByID(hdct.getMasanpham());
                String dsToping = "";
                List<ChiTietToping> cttp = cttpdao.selectAllByID(hdct.getMahoadonchitiet());
                for (ChiTietToping chiTietToping : cttp) {
                    if(!dsToping.equals("")){
                        dsToping = dsToping+", ";
                    }
                    Toping tpduocchon = tpdao.selectByID(chiTietToping.getMaToping());
                    dsToping = dsToping+tpduocchon.getTentoping();
                }
                
                String tileduongda = hdct.getTileduong() + " Đường -- " + hdct.getTileda() + " Đá";

                Object[] row = {hdct.getMahoadonchitiet(), sptim.getTensanpham(), sptim.getSize(), dsToping, tileduongda, hdct.getSoluong(), String.format("%,.0f", hdct.getThanhtien())};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
        }
    }
    
    void gopHoaDon(){
        if(this.row!=-1){
            TachHoaDonListener tachListener = new TachHoaDonListener() {
                @Override
                public void tachHoaDonClosed() {
                    fillToTable();
                    UpdateTongThanhToan();
                }

            };
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            GopHoaDon gop =  new GopHoaDon(parentFrame, true,mahd);
            gop.addTachHoaDonListener(tachListener);
            gop.setVisible(true);
        } else{
            MsgBox.alert(this, "Bạn chưa chọn hoá dơn nào để gộp");
        }
    }
    
    void tachHoaDon(){
        if(this.row!=-1){
           TachHoaDonListener tachListener = new TachHoaDonListener(){
           @Override
           public void tachHoaDonClosed() {
              fillToTable();
              UpdateTongThanhToan();
           }
           
            };
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            TachHoaDon tach = new TachHoaDon(parentFrame, true, mahd);
            tach.addTachHoaDonListener(tachListener);
            tach.setVisible(true);
        } else{
            MsgBox.alert(this, "Bạn chưa chọn hoá đơn nào để tách");
        }
    }
    
//-------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------
    Double bHeight = 0.0;
    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double bodyHeight = bHeight;
        double headerHeight = 5.0;
        double footerHeight = 5.0;
        double width = cm_to_pp(8);
        double height = cm_to_pp(headerHeight + bodyHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cm_to_pp(1));

        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }
    
    protected static double cm_to_pp(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }
//
    public class BillPrintable implements Printable {

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {
            
            List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd);
            
            int r = list.size();
            ImageIcon icon = new ImageIcon("AnhChuyenDe/1.jpg");
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;
                double width = pageFormat.getImageableWidth();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
                try {
                    int y = 20;
                    int yShift = 10;
                    int headerRectHeight = 15;
                    // int headerRectHeighta=40;

                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
//                    g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);
                    y += yShift + 30;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += yShift;
                    g2d.drawString("         TLK_Team.com        ", 12, y);
                    y += yShift;
                    g2d.drawString("   No 00000 Address Line One ", 12, y);
                    y += yShift;
                    g2d.drawString("   Address Line 02 SRI LANKA ", 12, y);
                    y += yShift;
                    g2d.drawString("   www.facebook.com/TLK_Team ", 12, y);
                    y += yShift;
                    g2d.drawString("        +94700000000      ", 12, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 12, y);
                    y += headerRectHeight;

                    g2d.drawString(" Tên thức uống         Thành tiền   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += headerRectHeight;
                    int i =1;
                    for (HoaDonChiTiet hoaDonChiTiet : list) {
                        SanPham sp = spdao.selectByID(hoaDonChiTiet.getMasanpham());
                        g2d.drawString(" "+i+". " + sp.getTensanpham() +" -- "+sp.getGia() +"                      ", 10, y);
                        y += yShift;
                        
                       
                        List<ChiTietToping> cttp = cttpdao.selectAllByID(hoaDonChiTiet.getMahoadonchitiet());
                        for (ChiTietToping chiTietToping : cttp) {
                            Toping tp = tpdao.selectByID(chiTietToping.getMaToping());
                            g2d.drawString("    "+ tp.getTentoping() +" -- "+tp.getGiatoping()+"                      ", 10, y);
                            y += yShift;
                            
                        }
                        
                        
                        g2d.drawString("        "+"SL: " + " x " + hoaDonChiTiet.getSoluong(), 10, y);
                        g2d.drawString(String.format("%,.0f", hoaDonChiTiet.getThanhtien()), 160, y);
                        y += yShift;
                        i++;
                    }
                    
                     

                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Tổng cộng:               " + thanhtoan + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Giảm giá:                 " + khuyenmai + "   ", 10, y);
                    y += yShift;
                    g2d.drawString("-------------------------------------", 10, y);
                    y += yShift;
                    g2d.drawString(" Thành tiền:               " + tongthanhtoan + "   ", 10, y);
                    y += yShift;

                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
                    y += yShift;
                    g2d.drawString("*************************************", 10, y);
                    y += yShift;
                    g2d.drawString("       SOFTWARE BY:TLK_Team          ", 10, y);
                    y += yShift;
                    g2d.drawString("   CONTACT: contact@TLK_Team.com       ", 10, y);
                    y += yShift;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }
    
//    public class BillPrintable implements Printable {
//
//        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
//                throws PrinterException {
//            
//            List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd);
//            
//            int r = list.size();
//            ImageIcon icon = new ImageIcon("AnhChuyenDe/1.jpg");
//            int result = NO_SUCH_PAGE;
//            if (pageIndex == 0) {
//
//                Graphics2D g2d = (Graphics2D) graphics;
//                double width = pageFormat.getImageableWidth();
//                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
//
//                //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
//                try {
//                    int y = 20;
//                    int yShift = 10;
//                    int headerRectHeight = 15;
//                    // int headerRectHeighta=40;
//
//                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
////                    g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);
//                    y += yShift + 30;
//                    g2d.drawString("-------------------------------------", 12, y);
//                    y += yShift;
//                    g2d.drawString("         TLK_Team.com        ", 12, y);
//                    y += yShift;
//                    g2d.drawString("   No 00000 Address Line One ", 12, y);
//                    y += yShift;
//                    g2d.drawString("   Address Line 02 SRI LANKA ", 12, y);
//                    y += yShift;
//                    g2d.drawString("   www.facebook.com/TLK_Team ", 12, y);
//                    y += yShift;
//                    g2d.drawString("        +94700000000      ", 12, y);
//                    y += yShift;
//                    g2d.drawString("-------------------------------------", 12, y);
//                    y += headerRectHeight;
//
//                    g2d.drawString(" Tên thức uống         Thành tiền   ", 10, y);
//                    y += yShift;
//                    g2d.drawString("-------------------------------------", 10, y);
//                    y += headerRectHeight;
//                    
//                    for (HoaDonChiTiet hoaDonChiTiet : list) {
//                        SanPham sp = spdao.selectByID(hoaDonChiTiet.getMasanpham());
//                        g2d.drawString(" " + sp.getTensanpham() + "                            ", 10, y);
//                        y += yShift;
//                        
//                        String dsToping = "";
//                        List<ChiTietToping> cttp = cttpdao.selectAllByID(hoaDonChiTiet.getMahoadonchitiet());
//                        for (ChiTietToping chiTietToping : cttp) {
//                            if (!dsToping.equals("")) {
//                                dsToping = dsToping + ", ";
//                            }
//                            Toping tpduocchon = tpdao.selectByID(chiTietToping.getMaToping());
//                            dsToping = dsToping + tpduocchon.getTentoping();
//                        }
//                        g2d.drawString("     " + dsToping + "                            ", 10, y);
//                        y += yShift;
//                        
//                        g2d.drawString("      " + sp.getGia() + " * " + hoaDonChiTiet.getSoluong(), 10, y);
//                        g2d.drawString(String.format("%,.0f", hoaDonChiTiet.getThanhtien()), 160, y);
//                        y += yShift;
//                    }
//                    
//                     
//
//                    g2d.drawString("-------------------------------------", 10, y);
//                    y += yShift;
//                    g2d.drawString(" Tổng cộng:               " + thanhtoan + "   ", 10, y);
//                    y += yShift;
//                    g2d.drawString("-------------------------------------", 10, y);
//                    y += yShift;
//                    g2d.drawString(" Giảm giá:                 " + khuyenmai + "   ", 10, y);
//                    y += yShift;
//                    g2d.drawString("-------------------------------------", 10, y);
//                    y += yShift;
//                    g2d.drawString(" Thành tiền:               " + tongthanhtoan + "   ", 10, y);
//                    y += yShift;
//
//                    g2d.drawString("*************************************", 10, y);
//                    y += yShift;
//                    g2d.drawString("       THANK YOU COME AGAIN            ", 10, y);
//                    y += yShift;
//                    g2d.drawString("*************************************", 10, y);
//                    y += yShift;
//                    g2d.drawString("       SOFTWARE BY:TLK_Team          ", 10, y);
//                    y += yShift;
//                    g2d.drawString("   CONTACT: contact@TLK_Team.com       ", 10, y);
//                    y += yShift;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                result = PAGE_EXISTS;
//            }
//            return result;
//        }
//    }
    
    void inHoaDon(){
        List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd);
        Double ctsize = 0.0;
        for (HoaDonChiTiet hoaDonChiTiet : list) {
            List<ChiTietToping> listcttp =cttpdao.selectAllByID(hoaDonChiTiet.getMahoadonchitiet());
            ctsize += listcttp.size();
        }
         bHeight = Double.valueOf(list.size()+ctsize);
        //JOptionPane.showMessageDialog(rootPane, bHeight);
        
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
             pj.print();
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        panelBorder12 = new com.tlk.swing.PanelBorder();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        panelBorder13 = new com.tlk.swing.PanelBorder();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        cboKhuyenMai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTongThanhToan = new com.tlk.swing.MyTextField();
        txtNgayTao = new com.tlk.swing.MyTextField();
        txtMaHoaDon = new com.tlk.swing.MyTextField();
        txtNhanVien = new com.tlk.swing.MyTextField();
        panelBorder15 = new com.tlk.swing.PanelBorder();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        panelBorder14 = new com.tlk.swing.PanelBorder();
        buttonBig14 = new com.tlk.swing.ButtonBig1();
        btnGopHoaDon = new com.tlk.swing.ButtonBig1();
        buttonBig13 = new com.tlk.swing.ButtonBig1();
        btnTachHoaDon = new com.tlk.swing.ButtonBig1();
        btnMoi1 = new com.tlk.swing.ButtonBig1();
        btnMoi = new com.tlk.swing.ButtonBig1();
        lblSoluong = new javax.swing.JPanel();
        button1 = new com.tlk.swing.Button();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cboLoai = new javax.swing.JComboBox<>();
        cboSanPham = new javax.swing.JComboBox<>();
        lblHinh = new javax.swing.JLabel();
        txtTensp = new com.tlk.swing.MyTextField();
        txtGiasp = new com.tlk.swing.MyTextField();
        txtSizesp = new com.tlk.swing.MyTextField();
        jLabel8 = new javax.swing.JLabel();
        cboDa = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboDuong = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txtSoLuong = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChiTiet = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        buttonBig16 = new com.tlk.swing.ButtonBig1();
        buttonBig17 = new com.tlk.swing.ButtonBig1();
        buttonBig11 = new com.tlk.swing.ButtonBig1();
        buttonBig18 = new com.tlk.swing.ButtonBig1();
        buttonBig19 = new com.tlk.swing.ButtonBig1();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelBorder1 = new com.tlk.swing.PanelBorder();
        cboLoai1 = new javax.swing.JComboBox<>();
        cboTenNuoc = new javax.swing.JComboBox<>();
        cboSize = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelBorder3 = new com.tlk.swing.PanelBorder();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        panelBorder2 = new com.tlk.swing.PanelBorder();
        buttonBig1 = new com.tlk.swing.ButtonBig();
        buttonBig2 = new com.tlk.swing.ButtonBig();
        buttonBig3 = new com.tlk.swing.ButtonBig();
        buttonBig4 = new com.tlk.swing.ButtonBig();
        panelBorder4 = new com.tlk.swing.PanelBorder();
        txtSizeSP1 = new com.tlk.swing.MyTextField();
        txtTenSP1 = new com.tlk.swing.MyTextField();
        txtGiaSP1 = new com.tlk.swing.MyTextField();
        cboDuong1 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        cboDa1 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtSoLuong1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        scrollPanelThu1 = new com.tlk.compoment.ScrollPanelThu();
        lblanh = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTiet1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        button2 = new com.tlk.swing.Button();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelBorder11 = new com.tlk.swing.PanelBorder();
        buttonBig21 = new com.tlk.swing.ButtonBig1();
        jPanel6 = new javax.swing.JPanel();
        panelBorder5 = new com.tlk.swing.PanelBorder();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblThanhToan = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        button3 = new com.tlk.swing.Button();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        panelBorder6 = new com.tlk.swing.PanelBorder();
        panelBorder7 = new com.tlk.swing.PanelBorder();
        jLabel31 = new javax.swing.JLabel();
        txtGiaTriTongThanhToan = new javax.swing.JLabel();
        panelBorder8 = new com.tlk.swing.PanelBorder();
        tblLoaikm = new javax.swing.JLabel();
        txtGiaTriKhuyenMai = new javax.swing.JLabel();
        panelBorder9 = new com.tlk.swing.PanelBorder();
        jLabel33 = new javax.swing.JLabel();
        txtGiaTriThanhTien = new javax.swing.JLabel();
        panelBorder10 = new com.tlk.swing.PanelBorder();
        buttonBig110 = new com.tlk.swing.ButtonBig1();
        buttonBig111 = new com.tlk.swing.ButtonBig1();
        buttonBig112 = new com.tlk.swing.ButtonBig1();

        setOpaque(false);
        setLayout(null);

        tabs.setBackground(new java.awt.Color(255, 204, 204));
        tabs.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tabs.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabs.setPreferredSize(new java.awt.Dimension(950, 715));

        jPanel1.setOpaque(false);

        jPanel9.setBackground(new java.awt.Color(139, 195, 207));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setBackground(new java.awt.Color(255, 255, 255));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Hàng");
        jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 90, -1));

        jLabel35.setBackground(new java.awt.Color(255, 255, 255));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Quản");
        jPanel9.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 90, -1));

        jLabel36.setBackground(new java.awt.Color(255, 255, 255));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Lý");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 90, -1));

        jLabel37.setBackground(new java.awt.Color(255, 255, 255));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Đơn");
        jPanel9.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 90, -1));

        panelBorder12.setBackground(new java.awt.Color(153, 153, 255));

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
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setMinWidth(80);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(80);
            tblHoaDon.getColumnModel().getColumn(1).setMinWidth(150);
            tblHoaDon.getColumnModel().getColumn(1).setMaxWidth(150);
            tblHoaDon.getColumnModel().getColumn(2).setMinWidth(150);
            tblHoaDon.getColumnModel().getColumn(2).setMaxWidth(150);
            tblHoaDon.getColumnModel().getColumn(3).setMinWidth(160);
            tblHoaDon.getColumnModel().getColumn(3).setMaxWidth(160);
            tblHoaDon.getColumnModel().getColumn(4).setMinWidth(120);
            tblHoaDon.getColumnModel().getColumn(4).setMaxWidth(120);
        }

        javax.swing.GroupLayout panelBorder12Layout = new javax.swing.GroupLayout(panelBorder12);
        panelBorder12.setLayout(panelBorder12Layout);
        panelBorder12Layout.setHorizontalGroup(
            panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        panelBorder12Layout.setVerticalGroup(
            panelBorder12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBorder13.setBackground(new java.awt.Color(204, 204, 204));

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        cboKhuyenMai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Khuyến mãi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Ghi chú");

        txtTongThanhToan.setEditable(false);
        txtTongThanhToan.setHint("Tổng tiền");
        txtTongThanhToan.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtNgayTao.setEditable(false);
        txtNgayTao.setHint("Ngày tạo");
        txtNgayTao.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setHint("Mã hoá đơn");
        txtMaHoaDon.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N

        txtNhanVien.setEditable(false);
        txtNhanVien.setHint("Nhân viên");
        txtNhanVien.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/edit_property_24px.png"))); // NOI18N
        txtNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhanVienActionPerformed(evt);
            }
        });

        panelBorder15.setBackground(new java.awt.Color(51, 102, 255));
        panelBorder15.setForeground(new java.awt.Color(255, 255, 255));

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Thông");

        jLabel40.setBackground(new java.awt.Color(255, 255, 255));
        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Hàng");

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Tin");

        jLabel42.setBackground(new java.awt.Color(255, 255, 255));
        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("Đơn");

        javax.swing.GroupLayout panelBorder15Layout = new javax.swing.GroupLayout(panelBorder15);
        panelBorder15.setLayout(panelBorder15Layout);
        panelBorder15Layout.setHorizontalGroup(
            panelBorder15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder15Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(panelBorder15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        panelBorder15Layout.setVerticalGroup(
            panelBorder15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder15Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addGap(12, 12, 12)
                .addComponent(jLabel42)
                .addGap(12, 12, 12)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorder13Layout = new javax.swing.GroupLayout(panelBorder13);
        panelBorder13.setLayout(panelBorder13Layout);
        panelBorder13Layout.setHorizontalGroup(
            panelBorder13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder13Layout.createSequentialGroup()
                .addComponent(panelBorder15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBorder13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBorder13Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder13Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(panelBorder13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTongThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        panelBorder13Layout.setVerticalGroup(
            panelBorder13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder13Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelBorder13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBorder13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder13Layout.createSequentialGroup()
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(15, Short.MAX_VALUE))
            .addComponent(panelBorder15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        panelBorder14.setBackground(new java.awt.Color(153, 153, 255));

        buttonBig14.setText("Huỷ");
        buttonBig14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig14ActionPerformed(evt);
            }
        });

        btnGopHoaDon.setText("Gộp hoá đơn");
        btnGopHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGopHoaDonActionPerformed(evt);
            }
        });

        buttonBig13.setText("Thêm");
        buttonBig13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig13ActionPerformed(evt);
            }
        });

        btnTachHoaDon.setText("Tách Hoá đơn");
        btnTachHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTachHoaDonActionPerformed(evt);
            }
        });

        btnMoi1.setText("Cập nhật");
        btnMoi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoi1ActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder14Layout = new javax.swing.GroupLayout(panelBorder14);
        panelBorder14.setLayout(panelBorder14Layout);
        panelBorder14Layout.setHorizontalGroup(
            panelBorder14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGopHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTachHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBig14, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonBig13, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBorder14Layout.setVerticalGroup(
            panelBorder14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder14Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelBorder14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBig13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig14, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTachHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGopHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBorder12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelBorder13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBorder14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelBorder12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
        );

        tabs.addTab("tab1", jPanel1);

        lblSoluong.setOpaque(false);

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/icons8-previous-25.png"))); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("HOÁ ĐƠN CHI TIẾT");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Loại đồ uống");

        cboLoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiActionPerformed(evt);
            }
        });

        cboSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboSanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSanPhamActionPerformed(evt);
            }
        });

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTensp.setHint("Tên sản phẩm");

        txtGiasp.setHint("Giá");

        txtSizesp.setHint("Size");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Đường");

        cboDa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDaActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Đá");

        cboDuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Số lượng");

        jButton2.setText("-");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtSoLuong.setBackground(new java.awt.Color(255, 255, 255));
        txtSoLuong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoLuong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSoLuong.setText("1");
        txtSoLuong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuong.setOpaque(true);

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tên sản phẩm", "Size", "Số lượng", "Thành tiền", "Tỉ lệ đường -- đá"
            }
        ));
        jScrollPane3.setViewportView(tblChiTiet);
        if (tblChiTiet.getColumnModel().getColumnCount() > 0) {
            tblChiTiet.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblChiTiet.getColumnModel().getColumn(1).setPreferredWidth(80);
            tblChiTiet.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblChiTiet.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblChiTiet.getColumnModel().getColumn(4).setPreferredWidth(200);
        }

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tổng thanh toán");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("1.000.000");

        buttonBig16.setText("Thêm");

        buttonBig17.setText("Cập nhật");

        buttonBig11.setText("Xoá");

        buttonBig18.setText("Thanh toán và in hoá đơn");
        buttonBig18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        buttonBig19.setText("Thanh toán");
        buttonBig19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Toping");

        javax.swing.GroupLayout lblSoluongLayout = new javax.swing.GroupLayout(lblSoluong);
        lblSoluong.setLayout(lblSoluongLayout);
        lblSoluongLayout.setHorizontalGroup(
            lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblSoluongLayout.createSequentialGroup()
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblSoluongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(lblSoluongLayout.createSequentialGroup()
                                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtSizesp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                                .addComponent(txtGiasp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addGroup(lblSoluongLayout.createSequentialGroup()
                                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(buttonBig16, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(buttonBig17, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(buttonBig11, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel5)
                                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                                .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cboSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(jLabel4))))
                    .addGroup(lblSoluongLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(15, 15, 15)
                                .addComponent(cboDa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(jLabel9)
                                .addGap(15, 15, 15)
                                .addComponent(cboDuong, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)))
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblSoluongLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(57, 57, 57))
                    .addGroup(lblSoluongLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(lblSoluongLayout.createSequentialGroup()
                                .addComponent(buttonBig18, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonBig19, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );
        lblSoluongLayout.setVerticalGroup(
            lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(lblSoluongLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(lblSoluongLayout.createSequentialGroup()
                        .addComponent(txtTensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSizesp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGiasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboDa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboDuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBig11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(107, 107, 107))
            .addGroup(lblSoluongLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lblSoluongLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)))
                    .addGroup(lblSoluongLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(lblSoluongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonBig18, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonBig19, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        tabs.addTab("tab2", lblSoluong);

        panelBorder1.setBackground(new java.awt.Color(204, 204, 204));
        panelBorder1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboLoai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoai1ActionPerformed(evt);
            }
        });
        panelBorder1.add(cboLoai1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 170, 37));

        cboTenNuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenNuoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNuocActionPerformed(evt);
            }
        });
        panelBorder1.add(cboTenNuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 200, 37));

        cboSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSizeActionPerformed(evt);
            }
        });
        panelBorder1.add(cboSize, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 180, 36));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Loại");
        panelBorder1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Nước uống");
        panelBorder1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Size");
        panelBorder1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        panelBorder3.setBackground(new java.awt.Color(51, 102, 255));
        panelBorder3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Chọn");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Thức");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Uống");

        javax.swing.GroupLayout panelBorder3Layout = new javax.swing.GroupLayout(panelBorder3);
        panelBorder3.setLayout(panelBorder3Layout);
        panelBorder3Layout.setHorizontalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder3Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addGap(45, 45, 45))
        );
        panelBorder3Layout.setVerticalGroup(
            panelBorder3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBorder1.add(panelBorder3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 90));

        panelBorder2.setBackground(new java.awt.Color(139, 195, 207));

        buttonBig1.setBackground(new java.awt.Color(255, 255, 255));
        buttonBig1.setText("Cập nhật");
        buttonBig1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig1ActionPerformed(evt);
            }
        });

        buttonBig2.setBackground(new java.awt.Color(255, 255, 255));
        buttonBig2.setText("Thêm");
        buttonBig2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig2ActionPerformed(evt);
            }
        });

        buttonBig3.setBackground(new java.awt.Color(255, 255, 255));
        buttonBig3.setText("Xoá");
        buttonBig3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig3ActionPerformed(evt);
            }
        });

        buttonBig4.setBackground(new java.awt.Color(255, 255, 255));
        buttonBig4.setText("Mới");
        buttonBig4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder2Layout = new javax.swing.GroupLayout(panelBorder2);
        panelBorder2.setLayout(panelBorder2Layout);
        panelBorder2Layout.setHorizontalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonBig4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelBorder2Layout.setVerticalGroup(
            panelBorder2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(buttonBig2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonBig1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonBig3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonBig4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        panelBorder4.setBackground(new java.awt.Color(204, 204, 255));

        txtSizeSP1.setEditable(false);
        txtSizeSP1.setHint("Size");
        txtSizeSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSizeSP1ActionPerformed(evt);
            }
        });

        txtTenSP1.setEditable(false);
        txtTenSP1.setHint("Tên sản phẩm");
        txtTenSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSP1ActionPerformed(evt);
            }
        });

        txtGiaSP1.setEditable(false);
        txtGiaSP1.setHint("Giá");
        txtGiaSP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaSP1ActionPerformed(evt);
            }
        });

        cboDuong1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboDuong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDuong1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Đường");

        cboDa1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Đá");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Số lượng");

        jButton3.setText("-");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtSoLuong1.setBackground(new java.awt.Color(255, 255, 255));
        txtSoLuong1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoLuong1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSoLuong1.setText("1");
        txtSoLuong1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSoLuong1.setOpaque(true);

        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        lblanh.setBackground(new java.awt.Color(255, 255, 255));
        lblanh.setOpaque(true);

        javax.swing.GroupLayout panelBorder4Layout = new javax.swing.GroupLayout(panelBorder4);
        panelBorder4.setLayout(panelBorder4Layout);
        panelBorder4Layout.setHorizontalGroup(
            panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblanh, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenSP1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(txtSizeSP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtGiaSP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addComponent(scrollPanelThu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder4Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelBorder4Layout.createSequentialGroup()
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18))
                        .addGap(12, 12, 12)
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboDa1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboDuong1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(412, 412, 412))
        );
        panelBorder4Layout.setVerticalGroup(
            panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPanelThu1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelBorder4Layout.createSequentialGroup()
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(7, 7, 7)
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDa1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelBorder4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder4Layout.createSequentialGroup()
                                .addComponent(txtTenSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(txtGiaSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(txtSizeSP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblanh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        tblChiTiet1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên thức uống", "Size", "Toping", "Tỉ lệ Đường -- Đá", "Số lượng", "Thành tiền"
            }
        ));
        tblChiTiet1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTiet1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblChiTiet1);
        if (tblChiTiet1.getColumnModel().getColumnCount() > 0) {
            tblChiTiet1.getColumnModel().getColumn(0).setMinWidth(60);
            tblChiTiet1.getColumnModel().getColumn(0).setMaxWidth(60);
            tblChiTiet1.getColumnModel().getColumn(1).setMinWidth(150);
            tblChiTiet1.getColumnModel().getColumn(1).setMaxWidth(150);
            tblChiTiet1.getColumnModel().getColumn(2).setMinWidth(60);
            tblChiTiet1.getColumnModel().getColumn(2).setMaxWidth(60);
            tblChiTiet1.getColumnModel().getColumn(4).setMinWidth(150);
            tblChiTiet1.getColumnModel().getColumn(4).setMaxWidth(150);
            tblChiTiet1.getColumnModel().getColumn(5).setMinWidth(70);
            tblChiTiet1.getColumnModel().getColumn(5).setMaxWidth(70);
            tblChiTiet1.getColumnModel().getColumn(6).setMinWidth(90);
            tblChiTiet1.getColumnModel().getColumn(6).setMaxWidth(90);
        }

        jPanel5.setBackground(new java.awt.Color(139, 195, 207));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/icons8-previous-25.png"))); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });
        jPanel5.add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 78, 37));

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("HÀNG");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 90, -1));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("CHI");
        jPanel5.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 90, -1));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("TIẾT");
        jPanel5.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 90, -1));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("ĐƠN");
        jPanel5.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 90, -1));

        panelBorder11.setBackground(new java.awt.Color(153, 153, 255));

        buttonBig21.setText("Thanh toán");
        buttonBig21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        buttonBig21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder11Layout = new javax.swing.GroupLayout(panelBorder11);
        panelBorder11.setLayout(panelBorder11Layout);
        panelBorder11Layout.setHorizontalGroup(
            panelBorder11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonBig21, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        panelBorder11Layout.setVerticalGroup(
            panelBorder11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder11Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(buttonBig21, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4)
                    .addComponent(panelBorder11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(panelBorder4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBorder2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(panelBorder11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("tab3", jPanel2);

        panelBorder5.setBackground(new java.awt.Color(204, 204, 255));

        tblThanhToan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên thức uống", "Size", "Toping", "Tỉ lệ Đường -- Đá", "Số lượng", "Thành tiền"
            }
        ));
        tblThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThanhToanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblThanhToan);
        if (tblThanhToan.getColumnModel().getColumnCount() > 0) {
            tblThanhToan.getColumnModel().getColumn(0).setMinWidth(60);
            tblThanhToan.getColumnModel().getColumn(0).setMaxWidth(60);
            tblThanhToan.getColumnModel().getColumn(1).setMinWidth(150);
            tblThanhToan.getColumnModel().getColumn(1).setMaxWidth(150);
            tblThanhToan.getColumnModel().getColumn(2).setMinWidth(60);
            tblThanhToan.getColumnModel().getColumn(2).setMaxWidth(60);
            tblThanhToan.getColumnModel().getColumn(4).setMinWidth(150);
            tblThanhToan.getColumnModel().getColumn(4).setMaxWidth(150);
            tblThanhToan.getColumnModel().getColumn(5).setMinWidth(70);
            tblThanhToan.getColumnModel().getColumn(5).setMaxWidth(70);
            tblThanhToan.getColumnModel().getColumn(6).setMinWidth(90);
            tblThanhToan.getColumnModel().getColumn(6).setMaxWidth(90);
        }

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 102, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("KIỂM TRA HOÁ ĐƠN");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorder5Layout = new javax.swing.GroupLayout(panelBorder5);
        panelBorder5.setLayout(panelBorder5Layout);
        panelBorder5Layout.setHorizontalGroup(
            panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder5Layout.createSequentialGroup()
                .addGroup(panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBorder5Layout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        panelBorder5Layout.setVerticalGroup(
            panelBorder5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder5Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(139, 195, 207));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tlk/icon/icons8-previous-25.png"))); // NOI18N
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });
        jPanel7.add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 78, 37));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Toán");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 100, -1));

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Xác");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 100, -1));

        jLabel29.setBackground(new java.awt.Color(255, 255, 255));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Nhận");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 100, -1));

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Thanh");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 100, -1));

        panelBorder6.setBackground(new java.awt.Color(204, 204, 204));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 102, 255));
        jLabel31.setText("TỔNG THANH TOÁN");

        txtGiaTriTongThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtGiaTriTongThanhToan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtGiaTriTongThanhToan.setText("270,000 VND");

        javax.swing.GroupLayout panelBorder7Layout = new javax.swing.GroupLayout(panelBorder7);
        panelBorder7.setLayout(panelBorder7Layout);
        panelBorder7Layout.setHorizontalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaTriTongThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelBorder7Layout.setVerticalGroup(
            panelBorder7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGiaTriTongThanhToan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblLoaikm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tblLoaikm.setForeground(new java.awt.Color(51, 102, 255));
        tblLoaikm.setText("KHUYẾN MÃI ");

        txtGiaTriKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtGiaTriKhuyenMai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtGiaTriKhuyenMai.setText("270,000 VND");

        javax.swing.GroupLayout panelBorder8Layout = new javax.swing.GroupLayout(panelBorder8);
        panelBorder8.setLayout(panelBorder8Layout);
        panelBorder8Layout.setHorizontalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtGiaTriKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(panelBorder8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(tblLoaikm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelBorder8Layout.setVerticalGroup(
            panelBorder8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblLoaikm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGiaTriKhuyenMai)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 102, 255));
        jLabel33.setText("THÀNH TIỀN");

        txtGiaTriThanhTien.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtGiaTriThanhTien.setForeground(new java.awt.Color(255, 51, 51));
        txtGiaTriThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtGiaTriThanhTien.setText("270,000 VND");

        javax.swing.GroupLayout panelBorder9Layout = new javax.swing.GroupLayout(panelBorder9);
        panelBorder9.setLayout(panelBorder9Layout);
        panelBorder9Layout.setHorizontalGroup(
            panelBorder9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder9Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(txtGiaTriThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panelBorder9Layout.setVerticalGroup(
            panelBorder9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGiaTriThanhTien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelBorder6Layout = new javax.swing.GroupLayout(panelBorder6);
        panelBorder6.setLayout(panelBorder6Layout);
        panelBorder6Layout.setHorizontalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(panelBorder7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBorder8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(panelBorder9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelBorder6Layout.setVerticalGroup(
            panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelBorder6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelBorder8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        panelBorder10.setBackground(new java.awt.Color(153, 153, 255));

        buttonBig110.setText("XÁC NHẬN");
        buttonBig110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig110ActionPerformed(evt);
            }
        });

        buttonBig111.setText("HUỶ");
        buttonBig111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig111ActionPerformed(evt);
            }
        });

        buttonBig112.setText("XÁC NHẬN VÀ IN HOÁ ĐƠN");
        buttonBig112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBig112ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder10Layout = new javax.swing.GroupLayout(panelBorder10);
        panelBorder10.setLayout(panelBorder10Layout);
        panelBorder10Layout.setHorizontalGroup(
            panelBorder10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBorder10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(buttonBig111, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonBig112, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(buttonBig110, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        panelBorder10Layout.setVerticalGroup(
            panelBorder10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder10Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelBorder10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonBig111, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig110, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBig112, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBorder5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelBorder6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelBorder5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBorder10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabs.addTab("tab4", jPanel6);

        add(tabs);
        tabs.setBounds(0, 0, 950, 700);
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBig112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig112ActionPerformed
        inHoaDon();
        thanhToanHoaDon();
    }//GEN-LAST:event_buttonBig112ActionPerformed

    private void buttonBig111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig111ActionPerformed
        tabs.setSelectedIndex(2);
    }//GEN-LAST:event_buttonBig111ActionPerformed

    private void buttonBig110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig110ActionPerformed
        thanhToanHoaDon();
    }//GEN-LAST:event_buttonBig110ActionPerformed

    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        tabs.setSelectedIndex(2);
    }//GEN-LAST:event_button3ActionPerformed

    private void tblThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThanhToanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblThanhToanMouseClicked

    private void buttonBig21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig21ActionPerformed
        List<HoaDonChiTiet> list = ctdao.selectAllChiTiet(mahd);
        if(list.size()!=0){
            fillToTableThanhToan();
            capNhatGiaTri();
            tabs.setSelectedIndex(3);
        }else{
            MsgBox.alert(this, "Bạn chưa chọn một sản phẩm nào nên không thể thanh toán");
        }
        
        //thanhToanHoaDon();
    }//GEN-LAST:event_buttonBig21ActionPerformed

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
        clearFromChiTiet();
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_button2ActionPerformed

    private void tblChiTiet1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTiet1MouseClicked
        if (evt.getClickCount() == 1) {
            scrollPanelThu1.clearSelections();
            this.rowChiTiet = tblChiTiet1.getSelectedRow();
            this.editHoaDonChiTiet();
        }
    }//GEN-LAST:event_tblChiTiet1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int sl = Integer.parseInt(txtSoLuong1.getText());
        if(sl<10){
            txtSoLuong1.setText(String.valueOf(sl+1));
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int sl = Integer.parseInt(txtSoLuong1.getText());
        if(sl>1){
            txtSoLuong1.setText(String.valueOf(sl-1));
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cboDuong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDuong1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDuong1ActionPerformed

    private void txtGiaSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaSP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaSP1ActionPerformed

    private void txtTenSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSP1ActionPerformed

    private void txtSizeSP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSizeSP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSizeSP1ActionPerformed

    private void buttonBig4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig4ActionPerformed
        clearFromChiTiet();
    }//GEN-LAST:event_buttonBig4ActionPerformed

    private void buttonBig3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig3ActionPerformed
        xoaChiTietHoaDon();
    }//GEN-LAST:event_buttonBig3ActionPerformed

    private void buttonBig2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig2ActionPerformed
        ThemHoaDonChiTiet();
    }//GEN-LAST:event_buttonBig2ActionPerformed

    private void buttonBig1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig1ActionPerformed
        capNhatChiTietHoaDon();
    }//GEN-LAST:event_buttonBig1ActionPerformed

    private void cboSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSizeActionPerformed
        fillToFormChiTiet();
        scrollPanelThu1.clearSelections();
    }//GEN-LAST:event_cboSizeActionPerformed

    private void cboTenNuocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNuocActionPerformed
        fillComboBoxSize();
    }//GEN-LAST:event_cboTenNuocActionPerformed

    private void cboLoai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoai1ActionPerformed
        fillComboBoxSanPham();
    }//GEN-LAST:event_cboLoai1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int sl = Integer.parseInt(txtSoLuong.getText());
        if(sl<10){
            txtSoLuong.setText(String.valueOf(sl+1));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int sl = Integer.parseInt(txtSoLuong.getText());
        if(sl>1){
            txtSoLuong.setText(String.valueOf(sl-1));
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cboDaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDaActionPerformed

    private void cboSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSanPhamActionPerformed
        fillToFormChiTiet();
    }//GEN-LAST:event_cboSanPhamActionPerformed

    private void cboLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiActionPerformed
        fillComboBoxSanPham();
    }//GEN-LAST:event_cboLoaiActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_button1ActionPerformed

    private void btnMoi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoi1ActionPerformed
        capNhatHoaDon();
    }//GEN-LAST:event_btnMoi1ActionPerformed

    private void buttonBig14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig14ActionPerformed
        huyHoaDon();
    }//GEN-LAST:event_buttonBig14ActionPerformed

    private void btnGopHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGopHoaDonActionPerformed
        gopHoaDon();
    }//GEN-LAST:event_btnGopHoaDonActionPerformed

    private void btnTachHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTachHoaDonActionPerformed
        tachHoaDon();
    }//GEN-LAST:event_btnTachHoaDonActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearFrom();

    }//GEN-LAST:event_btnMoiActionPerformed

    private void buttonBig13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBig13ActionPerformed
        ThemHoaDon();
        tabs.setSelectedIndex(2);
    }//GEN-LAST:event_buttonBig13ActionPerformed

    private void txtNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhanVienActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblHoaDon.getSelectedRow();
            this.edit();
            mahd=txtMaHoaDon.getText();
            UpdateTongThanhToan();
            fillToTableChiTiet();
            tabs.setSelectedIndex(2);
        }
        if (evt.getClickCount() == 2) {
//            this.row = tblHoaDon.getSelectedRow();
//            this.edit();
//            mahd=txtMaHoaDon.getText();
//            UpdateTongThanhToan();
//            fillToTableChiTiet();
            
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    @Override
    protected void paintComponent(Graphics gr) {
        Graphics2D g2 = (Graphics2D) gr;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#bdc3c7"), 0, getHeight(), Color.decode("#2c3e50"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintComponent(gr);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tlk.swing.ButtonBig1 btnGopHoaDon;
    private com.tlk.swing.ButtonBig1 btnMoi;
    private com.tlk.swing.ButtonBig1 btnMoi1;
    private com.tlk.swing.ButtonBig1 btnTachHoaDon;
    private com.tlk.swing.Button button1;
    private com.tlk.swing.Button button2;
    private com.tlk.swing.Button button3;
    private com.tlk.swing.ButtonBig buttonBig1;
    private com.tlk.swing.ButtonBig1 buttonBig11;
    private com.tlk.swing.ButtonBig1 buttonBig110;
    private com.tlk.swing.ButtonBig1 buttonBig111;
    private com.tlk.swing.ButtonBig1 buttonBig112;
    private com.tlk.swing.ButtonBig1 buttonBig13;
    private com.tlk.swing.ButtonBig1 buttonBig14;
    private com.tlk.swing.ButtonBig1 buttonBig16;
    private com.tlk.swing.ButtonBig1 buttonBig17;
    private com.tlk.swing.ButtonBig1 buttonBig18;
    private com.tlk.swing.ButtonBig1 buttonBig19;
    private com.tlk.swing.ButtonBig buttonBig2;
    private com.tlk.swing.ButtonBig1 buttonBig21;
    private com.tlk.swing.ButtonBig buttonBig3;
    private com.tlk.swing.ButtonBig buttonBig4;
    private javax.swing.JComboBox<String> cboDa;
    private javax.swing.JComboBox<String> cboDa1;
    private javax.swing.JComboBox<String> cboDuong;
    private javax.swing.JComboBox<String> cboDuong1;
    private javax.swing.JComboBox<String> cboKhuyenMai;
    private javax.swing.JComboBox<String> cboLoai;
    private javax.swing.JComboBox<String> cboLoai1;
    private javax.swing.JComboBox<String> cboSanPham;
    private javax.swing.JComboBox<String> cboSize;
    private javax.swing.JComboBox<String> cboTenNuoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPanel lblSoluong;
    private javax.swing.JLabel lblanh;
    private com.tlk.swing.PanelBorder panelBorder1;
    private com.tlk.swing.PanelBorder panelBorder10;
    private com.tlk.swing.PanelBorder panelBorder11;
    private com.tlk.swing.PanelBorder panelBorder12;
    private com.tlk.swing.PanelBorder panelBorder13;
    private com.tlk.swing.PanelBorder panelBorder14;
    private com.tlk.swing.PanelBorder panelBorder15;
    private com.tlk.swing.PanelBorder panelBorder2;
    private com.tlk.swing.PanelBorder panelBorder3;
    private com.tlk.swing.PanelBorder panelBorder4;
    private com.tlk.swing.PanelBorder panelBorder5;
    private com.tlk.swing.PanelBorder panelBorder6;
    private com.tlk.swing.PanelBorder panelBorder7;
    private com.tlk.swing.PanelBorder panelBorder8;
    private com.tlk.swing.PanelBorder panelBorder9;
    private com.tlk.compoment.ScrollPanelThu scrollPanelThu1;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTiet;
    private javax.swing.JTable tblChiTiet1;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JLabel tblLoaikm;
    private javax.swing.JTable tblThanhToan;
    private javax.swing.JTextArea txtGhiChu;
    private com.tlk.swing.MyTextField txtGiaSP1;
    private javax.swing.JLabel txtGiaTriKhuyenMai;
    private javax.swing.JLabel txtGiaTriThanhTien;
    private javax.swing.JLabel txtGiaTriTongThanhToan;
    private com.tlk.swing.MyTextField txtGiasp;
    private com.tlk.swing.MyTextField txtMaHoaDon;
    private com.tlk.swing.MyTextField txtNgayTao;
    private com.tlk.swing.MyTextField txtNhanVien;
    private com.tlk.swing.MyTextField txtSizeSP1;
    private com.tlk.swing.MyTextField txtSizesp;
    private javax.swing.JLabel txtSoLuong;
    private javax.swing.JLabel txtSoLuong1;
    private com.tlk.swing.MyTextField txtTenSP1;
    private com.tlk.swing.MyTextField txtTensp;
    private com.tlk.swing.MyTextField txtTongThanhToan;
    // End of variables declaration//GEN-END:variables

    
}
