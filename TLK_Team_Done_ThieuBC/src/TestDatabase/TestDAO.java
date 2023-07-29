/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestDatabase;

import com.tlk.dao.KhuyenMaiDAO;
import com.tlk.dao.LoaiSanPhamDAO;
import com.tlk.dao.NhanVienDAO;
import com.tlk.dao.SanPhamDAO;
import com.tlk.entity.KhuyenMai;
import com.tlk.entity.LoaiSanPham;
import com.tlk.entity.NhanVien;
import com.tlk.entity.SanPham;
import java.util.List;

/**
 *
 * @author JAVA DEV
 */
public class TestDAO {
    public static void main(String[] args) {
 
        NhanVienDAO nh=new NhanVienDAO();
       
          //nh.insert(new NhanVien("NV001","999999","Phan Châu trinh","chautrinhhgamil.com",true));
         // nh.update(new NhanVien("tendangnhap1","123456","NGuyen Van Anh","vananhgmail.com",false));
        //  nh.delete("NV001");
//           List<NhanVien> list=nh.selectAll();
//          for (NhanVien nh1 : list) {
//            System.out.println("-- " + nh1.toString());
//        }
         //    NguoiHoc nguoiHocById = nh.selecById("NH001");
//        if (nguoiHocById != null) {
//            System.out.println("ID: " + nguoiHocById.toString());
//        } else {
//            System.out.println("NOT NGUOIHOC ID");
//        }

//        ThongKeDao tk=new ThongKeDao();
//        List<Object[]> ls=tk.getLuongNguoiHoc();
//        for(Object[] tk1:ls){
//            System.out.println("Thống kê Người học-> "+"Năm: "+tk1[0]+"\t"+"Số lượng NH: "+tk1[1]+"\t"+"Đầu tiên: "+tk1[2]+"\t"+"Cuối cùng: "+tk1[3]);
//        }
//        NhanVien nv=nh.selectByID("tendangnhap1");
//         if (nv != null) {
//           System.out.println("ID: " + nv.toString());
//      } else {
//         System.out.println("NOT NGUOIHOC ID");
//      }
         SanPhamDAO sp_dao=new SanPhamDAO();
         //  String INSERT_SQL = "INSERT INTO SanPham  (SP_Ma, SP_Ten, SP_DonGia, SP_Size, SP_Hinh, SP_MaLoaiSP) VALUES (?,?,?,?,?,?)";   
        // sp_dao.insert(new SanPham("Tra sua",23000,"L",1,""));
//          List<SanPham> list2=sp_dao.selectAll();
//          for (SanPham nh1 : list2) {
//            System.out.println("-- " + nh1.toString());
//        }
//          LoaiSanPhamDAO loaiSP=new LoaiSanPhamDAO();
//           List<LoaiSanPham> lsp=loaiSP.selectAll();
//          for (LoaiSanPham nh1 : lsp) {
//            System.out.println("-- " + nh1.toString());
//        }
            KhuyenMaiDAO kh=new KhuyenMaiDAO();
           List<KhuyenMai> lsp2=kh.selectAll();
          for (KhuyenMai nh2 : lsp2) {
            System.out.println("-- " + nh2.toString());
        }
    }
}
