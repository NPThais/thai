
package com.tlk.dao;

import com.tlk.entity.ChiTietToping;
import com.tlk.jdbc.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


public class ChiTietTopingDAO extends TlkDAO<ChiTietToping, Object>{
    String INSERT_SQL = "INSERT INTO ChiTietToping (CTTP_MaCTHD, CTTP_MaTP) VALUES (?,?)";
    String UPDATE_SQL = "UPDATE HoaDonChiTiet set HDCT_MaHD=?, HDCT_MaSP=?, HDCT_SoLuong=?, HDCT_TiLeDuong=?, HDCT_TiLeDuong=? ,HDCT_ThanhTien=? where HDCT_Ma=?";
    String DELETE_SQL = "DELETE  from ChiTietToping where CTTP_MaCTHD=?";
    String SELECT_ALL_SQL = "select * from ChiTietToping";
    String SELECT_BY_ID_SQL = "select * from HoaDonChiTiet where HDCT_Ma=?";
    
    @Override
    public void insert(ChiTietToping entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaChiTietHoaDon(), entity.getMaToping());
    }

    @Override
    public void update(ChiTietToping entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object id) {
        JdbcHelper.update(DELETE_SQL, id);
    }
    
    @Override
    public ChiTietToping selectByID(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ChiTietToping> selectAll() {
         return this.selectBySql(SELECT_ALL_SQL);
    }
    
    public List<ChiTietToping> selectAllByID(int macthd) {
        String SELECT_ALLID_SQL = "select * from ChiTietToping where CTTP_MaCTHD = ?";
        return this.selectBySql(SELECT_ALLID_SQL,macthd);
    }

    @Override
    protected List<ChiTietToping> selectBySql(String sql, Object... args) {
       List<ChiTietToping>list = new ArrayList<ChiTietToping>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                ChiTietToping entity = new ChiTietToping();

                entity.setMaChiTietToping(rs.getInt("CTTP_Ma"));
                entity.setMaChiTietHoaDon(rs.getInt("CTTP_MaCTHD"));
                entity.setMaToping(rs.getInt("CTTP_MaTP"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
