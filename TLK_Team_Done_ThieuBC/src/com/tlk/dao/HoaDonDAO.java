
package com.tlk.dao;

import com.tlk.entity.HoaDon;
import java.util.List;
import java.sql.ResultSet;
import com.tlk.jdbc.JdbcHelper;
import com.tlk.utils.XDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author MACBOOK Pro
 */
public class HoaDonDAO extends TlkDAO<HoaDon, Object>{
    String INSERT_SQL = "INSERT INTO HoaDon  (HD_NgayTao, HD_TrangThai, HD_TongThanhToan, HD_MaKM ,HD_TongThanhToanKM, HD_GhiChu, HD_TenDangNhap) VALUES (GETDATE(),1,0,?,0,?,?)";
    String UPDATE_SQL = "UPDATE HoaDon set HD_GhiChu=?,HD_MaKM=? where HD_Ma=?";
    String DELETE_SQL = "UPDATE HoaDon set HD_TrangThaiTonTai=0 where HD_Ma=?";
    String SELECT_ALL_SQL = "select * from HoaDon where HD_TrangThaiTonTai = 1 and HD_TrangThai = 1";
    String SELECT_BY_ID_SQL = "select * from HoaDon where HD_Ma=?";
    
    @Override
    public void insert(HoaDon entity) {
       JdbcHelper.update(INSERT_SQL ,entity.getMakhuyenmai(),entity.getGhichu(),entity.getTendangnhap());
    }
    
     public void insertKhongKhuyenMai(HoaDon entity) {
        String INSERTNO_SQL = "INSERT INTO HoaDon  (HD_NgayTao, HD_TrangThai, HD_TongThanhToan ,HD_TongThanhToanKM, HD_GhiChu, HD_TenDangNhap) VALUES (GETDATE(),1,0,0,?,?)";
        JdbcHelper.update(INSERTNO_SQL ,entity.getGhichu(),entity.getTendangnhap());
    }

    @Override
    public void update(HoaDon entity) {
         JdbcHelper.update(UPDATE_SQL, entity.getGhichu(),entity.getMakhuyenmai(),entity.getMahoadon());    
    }
    
    public void updateThanhToan(Object giaTriThanhToan, Object mahd) {
        String UPDATE_THANHTOAN_SQL = "UPDATE HoaDon set HD_TongThanhToan=? where HD_Ma=?";
         JdbcHelper.update(UPDATE_THANHTOAN_SQL, giaTriThanhToan, mahd);    
    }
    
    public void updateThanhToanKM(Object giaTriThanhToanKM, Object mahd) {
        String UPDATE_THANHTOANKM_SQL = "UPDATE HoaDon set HD_TongThanhToanKM=? where HD_Ma=?";
         JdbcHelper.update(UPDATE_THANHTOANKM_SQL, giaTriThanhToanKM, mahd);    
    }
    
    public void updateTrangThaiThanhToan( Object mahd) {
        String UPDATE_TRANGTHAI_THANHTOAN_SQL = "UPDATE HoaDon set HD_TrangThai=0 where HD_Ma=?";
         JdbcHelper.update(UPDATE_TRANGTHAI_THANHTOAN_SQL, mahd);    
    }

    @Override
    public void delete(Object id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public HoaDon selectByID(Object id) {
          List<HoaDon> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);

    }
    
    public HoaDon selectTop1ByMa() {
        String sql_select = "select * from HoaDon order by  HD_Ma desc";
         List<HoaDon> list = this.selectBySql(sql_select);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public List<HoaDon> selectAllHoaDonByDateThanhToan(String ngaytao) {
        String sql_select = "SELECT * FROM HoaDon  WHERE CONVERT(date, HD_NgayTao) = ? and HD_TrangThai=0 and HD_TrangThaiTonTai=1";
        List<HoaDon> list = this.selectBySql(sql_select, ngaytao);
        return list;
    }
    
    public List<String> selectAllHoaDonDate() {
        String sql_select = "SELECT CONVERT(date, HD_NgayTao) AS NgayTao FROM HoaDon WHERE HD_TrangThaiTonTai = 1 AND HD_TrangThai = 0 GROUP BY CONVERT(date, HD_NgayTao) ORDER BY CONVERT(date, HD_NgayTao) DESC";
        List<String> date = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql_select);
            while(rs.next()){
                Date ngayTao = rs.getDate("NgayTao");
                String formattedDate = formatDate(ngayTao, "dd-MM-yyyy");
                date.add(formattedDate);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
       return date;
    }
    
    private String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
}
        

    @Override
    public List<HoaDon> selectAll() {
       return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
          List<HoaDon>list = new ArrayList<HoaDon>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                HoaDon entity = new HoaDon();
//   HD_NgayTao, HD_TrangThai, HD_TongThanhToan, HD_TongThanhToanKM, HD_GhiChu, HD_MaKM, HD_TenDangNhap) VALUES (?,?,?,?,?,?,?)";
                entity.setMahoadon(rs.getString("HD_Ma"));
                entity.setNgaytao(rs.getString("HD_NgayTao"));
                entity.setTrangthai(rs.getBoolean("HD_TrangThai"));
                entity.setTongthanhtoan(rs.getDouble("HD_TongThanhToan"));
                entity.setTongthanhtoanKM(rs.getDouble("HD_TongThanhToanKM"));
                entity.setGhichu(rs.getString("HD_GhiChu"));
                 entity.setMakhuyenmai(rs.getString("HD_MaKM"));
                  entity.setTendangnhap(rs.getString("HD_TenDangNhap"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
                throw new RuntimeException(ex);
        }
    }
    
}
