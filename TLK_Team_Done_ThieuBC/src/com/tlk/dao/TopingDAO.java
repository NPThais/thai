
package com.tlk.dao;

import com.tlk.entity.Toping;
import com.tlk.jdbc.JdbcHelper;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;


public class TopingDAO extends TlkDAO<Toping, Object>{
    String INSERT_SQL = "INSERT INTO Toping (TP_Ten, TP_Gia) VALUES(?,?)";
    String UPDATE_SQL = "UPDATE Toping set TP_Ten=?, TP_Gia=? where TP_Ma=?";
    String DELETE_SQL = "update Toping set TP_TrangThaiTonTai=0 where TP_Ma=?";
    String SELECT_ALL_SQL = "select * from Toping where TP_TrangThaiTonTai=1";
    String SELECT_BY_ID_SQL = "select * from Toping where TP_Ma=?";
    
    @Override
    public void insert(Toping entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTentoping(), entity.getGiatoping());
    }

    @Override
    public void update(Toping entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTentoping(), entity.getGiatoping(), entity.getMatoping());
    }

    @Override
    public void delete(Object id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public Toping selectByID(Object id) {
        List<Toping> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Toping> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Toping> selectBySql(String sql, Object... args) {
        List<Toping>list = new ArrayList<Toping>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                Toping entity = new Toping();
//  String INSERT_SQL = "INSERT INTO SanPham  (SP_Ma, SP_Ten, SP_DonGia, SP_Size, SP_Hinh, SP_MaLoaiSP) VALUES (?,?,?,?,?,?)";   
                entity.setMatoping(rs.getInt("TP_Ma"));
                entity.setTentoping(rs.getString("TP_Ten"));
                entity.setGiatoping(rs.getDouble("TP_Gia"));
                
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    
}
