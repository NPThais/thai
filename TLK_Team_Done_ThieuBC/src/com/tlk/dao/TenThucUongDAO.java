
package com.tlk.dao;

import com.tlk.entity.TenThucUong;
import com.tlk.jdbc.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author MACBOOK Pro
 */
public class TenThucUongDAO extends TlkDAO<TenThucUong, Object>{
    String SELECT_ALL_SQL = "select SP_Ten from SanPham ";
    String SELECT_BY_ID_SQL = "select SP_Ten from SanPham where SP_MaLoaiSP=? group by SP_Ten";
    
    @Override
    public void insert(TenThucUong entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(TenThucUong entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public TenThucUong selectByID(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<TenThucUong> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    
    public List<TenThucUong> selectByTen(Integer maloaisp){
        String sql = "select SP_Ten from SanPham where SP_MaLoaiSP=? group by SP_Ten";
        return this.selectBySql(sql, maloaisp);
    }

    @Override
    protected List<TenThucUong> selectBySql(String sql, Object... args) {
        List<TenThucUong>list = new ArrayList<TenThucUong>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                TenThucUong entity = new TenThucUong();
//  String INSERT_SQL = "INSERT INTO SanPham  (SP_Ma, SP_Ten, SP_DonGia, SP_Size, SP_Hinh, SP_MaLoaiSP) VALUES (?,?,?,?,?,?)";   
                entity.setTenThucUong(rs.getString("SP_Ten"));
               
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
