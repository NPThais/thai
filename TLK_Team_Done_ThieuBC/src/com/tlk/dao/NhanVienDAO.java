
package com.tlk.dao;

import com.tlk.entity.NhanVien;
import com.tlk.jdbc.JdbcHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MACBOOK Pro
 */
public class NhanVienDAO extends TlkDAO<NhanVien, String>{
    String INSERT_SQL = "INSERT INTO NhanVien (NV_TenDangNhap, NV_MatKhau, NV_HoTen, NV_Email, NV_VaiTro) VALUES (?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien set NV_MatKhau=?, NV_HoTen=?, NV_Email=?, NV_VaiTro=? where NV_TenDangNhap=?";
    String DELETE_SQL = "DELETE FROM NhanVien where NV_TenDangNhap=?";
    String SELECT_ALL_SQL = "select * from NhanVien";
    String SELECT_BY_ID_SQL = "select * from NhanVien where NV_TenDangNhap=?";
    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTendangnhap(), entity.getMatkhau(), entity.getHovaten(),entity.getEmail(), entity.isVaitro());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMatkhau(), entity.getHovaten(), entity.getEmail(), entity.isVaitro(),entity.getTendangnhap());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public NhanVien selectByID(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    
    public void updatemk(String mk, String tendangnhap){
        String sql = "UPDATE NhanVien set NV_MatKhau=? where NV_TenDangNhap=?";
        JdbcHelper.update(sql, mk, tendangnhap);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien>list = new ArrayList<NhanVien>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                NhanVien entity = new NhanVien();
      //     String INSERT_SQL = "INSERT INTO NhanVien (NV_TenDangNhap, NV_MatKhau, NV_HoTen, NV_Email, NV_VaiTro) VALUES (?,?,?,?,?)";
                entity.setTendangnhap(rs.getString("NV_TenDangNhap"));
                entity.setMatkhau(rs.getString("NV_MatKhau"));
                entity.setHovaten(rs.getString("NV_HoTen"));
                entity.setEmail(rs.getString("NV_Email"));
                entity.setVaitro(rs.getBoolean("NV_VaiTro"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
        
    }
    
       public List<NhanVien> selectByKeyWord(String keyWord){
        String sql="SELECT*FROM NhanVien WHERE NV_HoTen LIKE ? AND NV_TrangThaiTonTai=1";
        return this.selectBySql(sql,"%"+keyWord+"%");
    }
    
}
