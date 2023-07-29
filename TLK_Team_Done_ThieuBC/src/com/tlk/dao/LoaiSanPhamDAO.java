
package com.tlk.dao;

import com.tlk.entity.LoaiSanPham;
import com.tlk.jdbc.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author MACBOOK Pro
 */
public class LoaiSanPhamDAO extends TlkDAO<LoaiSanPham, Object>{
    String INSERT_SQL = "INSERT INTO LoaiSanPham (LoaiSP_Ten) VALUES (?)";
    String UPDATE_SQL = "UPDATE LoaiSanPham SET LoaiSP_Ten =? WHERE LoaiSP_Ma=?";
    String DELETE_SQL = "DELETE FROM LoaiSanPham WHERE LoaiSP_Ma=?";
    String SELECT_ALL_SQL = "select * from LoaiSanPham where LoaiSP_TrangThaiTonTai=1";
    String SELECT_BY_ID_SQL = "select * from LoaiSanPham where LoaiSP_Ma=?";
    
    @Override
    public void insert(LoaiSanPham entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTenloaisanpham());
    }

    @Override
    public void update(LoaiSanPham entity) {
         JdbcHelper.update(UPDATE_SQL, entity.getTenloaisanpham(),entity.getMaloaisanpham());
    }

    @Override
    public void delete(Object id) {
       JdbcHelper.update(DELETE_SQL,id);
    }

    @Override
    public LoaiSanPham selectByID(Object id) {
         List<LoaiSanPham> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public LoaiSanPham selectByName(Object id) {
         
         String SELECT_BY_NAME_SQL = "select * from LoaiSanPham where LoaiSP_Ten like ?";
         List<LoaiSanPham> list = this.selectBySql(SELECT_BY_NAME_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<LoaiSanPham> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<LoaiSanPham> selectBySql(String sql, Object... args) {
           List<LoaiSanPham>list = new ArrayList<LoaiSanPham>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                LoaiSanPham entity = new LoaiSanPham();
//  String INSERT_SQL = "INSERT INTO SanPham  (SP_Ma, SP_Ten, SP_DonGia, SP_Size, SP_Hinh, SP_MaLoaiSP) VALUES (?,?,?,?,?,?)";   
                entity.setMaloaisanpham(rs.getInt("LoaiSP_Ma"));
                entity.setTenloaisanpham(rs.getString("LoaiSP_Ten"));
               
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
        
    }
    
}
