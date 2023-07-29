
package com.tlk.dao;

import com.tlk.entity.HoaDonChiTiet;
import java.util.List;
import java.sql.ResultSet;
import com.tlk.jdbc.JdbcHelper;
import com.tlk.main.PanelInteractionListener;
import java.util.ArrayList;
/**
 *
 * @author MACBOOK Pro
 */
public class HoaDonChiTietDAO extends TlkDAO<HoaDonChiTiet, Object>{
    String INSERT_SQL = "INSERT INTO HoaDonChiTiet  (HDCT_MaHD, HDCT_MaSP, HDCT_SoLuong, HDCT_TiLeDuong, HDCT_TiLeDa,HDCT_ThanhTien) VALUES (?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDonChiTiet set  HDCT_MaSP=?, HDCT_SoLuong=?, HDCT_TiLeDuong=?, HDCT_TiLeDa=? ,HDCT_ThanhTien=? where HDCT_Ma=?";
    String DELETE_SQL = "DELETE  from HoaDonChiTiet where HDCT_Ma=?";
    String SELECT_ALL_SQL = "select * from HoaDonChiTiet";
    String SELECT_BY_ID_SQL = "select * from HoaDonChiTiet where HDCT_Ma=?";
    
   
    
    @Override
    public void insert(HoaDonChiTiet entity) {
      JdbcHelper.update(INSERT_SQL, entity.getMahoadon(),entity.getMasanpham(),entity.getSoluong(),entity.getTileduong(), entity.getTileda(),entity.getThanhtien());
    }

    @Override
    public void update(HoaDonChiTiet entity) {
       JdbcHelper.update(UPDATE_SQL, entity.getMasanpham(),entity.getSoluong(),entity.getTileduong(), entity.getTileda(),entity.getThanhtien(),entity.getMahoadonchitiet());
    }
    
    public void updateMaHD(String mahd, Integer macthd) {
       String UPDATE_MAHD_SQL = "UPDATE HoaDonChiTiet set  HDCT_MaHD=? where HDCT_Ma=?";
       JdbcHelper.update(UPDATE_MAHD_SQL,mahd, macthd);
       
    }
    
    public void updateThanhTien(Double thanhtien, int mahdct) {
       String UPDATE_THANHTIEN_SQL = "UPDATE HoaDonChiTiet set HDCT_ThanhTien=? where HDCT_Ma=?";
       JdbcHelper.update(UPDATE_THANHTIEN_SQL, thanhtien, mahdct);
    }

    @Override
    public void delete(Object id) {
       JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public HoaDonChiTiet selectByID(Object id) {
         List<HoaDonChiTiet> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }
    
    public List<HoaDonChiTiet> selectAllChiTiet(Object id) {
        String sql = "select * from HoaDonChiTiet where HDCT_MaHD=?";
        List<HoaDonChiTiet> list = this.selectBySql(sql, id);
        return list;
    }

    @Override
    protected List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
           List<HoaDonChiTiet>list = new ArrayList<HoaDonChiTiet>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                HoaDonChiTiet entity = new HoaDonChiTiet();
// String INSERT_SQL = "INSERT INTO HoaDonChiTiet  (HDCT_MaHD, HDCT_MaSP, HDCT_SoLuong, HDCT_TiLeDuongDa, HDCT_ThanhTien) VALUES (?,?,?,?,?)";
                entity.setMahoadonchitiet(rs.getInt("HDCT_Ma"));
                entity.setMahoadon(rs.getString("HDCT_MaHD"));
                entity.setMasanpham(rs.getString("HDCT_MaSP"));
                entity.setSoluong(rs.getInt("HDCT_SoLuong"));
                entity.setTileduong(rs.getString("HDCT_TiLeDuong"));
                entity.setTileda(rs.getString("HDCT_TiLeDa"));
                entity.setThanhtien(rs.getDouble("HDCT_ThanhTien"));
               
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
