
package com.tlk.dao;

import com.tlk.entity.KhuyenMai;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.tlk.jdbc.JdbcHelper;
/**
 *
 * @author MACBOOK Pro
 */
public class KhuyenMaiDAO extends TlkDAO<KhuyenMai, Object>{
    String INSERT_SQL = "INSERT INTO KhuyenMai (KM_Ma,KM_NgayTao, KM_TheoPhanTram, KM_GiaTriKhuyenMai, KM_NgayBatDau, KM_NgayKetThuc, KM_TrangThaiApDung) VALUES (?,GETDATE(), ?, ?, ?, ?, ?);";
    String UPDATE_SQL = "UPDATE KhuyenMai SET KM_TheoPhanTram =?, KM_GiaTriKhuyenMai =?, KM_NgayBatDau =?, KM_NgayKetThuc =?, KM_TrangThaiApDung =? where KM_Ma=?";
    String DELETE_SQL = "UPDATE KhuyenMai SET KM_TrangThaiTonTai=0 where KM_Ma=?";
    String SELECT_ALL_SQL = "select * from KhuyenMai where KM_TrangThaiTonTai = 1";
    String SELECT_BY_ID_SQL = "select * from KhuyenMai where KM_Ma=?";
    
    @Override
    public void insert(KhuyenMai entity) {
       JdbcHelper.update(INSERT_SQL, entity.getMakhuyenmai(),entity.isGiamtheophantram(),entity.getGiatrikhuyenmai(),entity.getNgaybatdau(), entity.getNgayketthuc(),entity.getTrangthaiapdung());
    }

    @Override
    public void update(KhuyenMai entity) {
       JdbcHelper.update(UPDATE_SQL, entity.isGiamtheophantram(),entity.getGiatrikhuyenmai(),entity.getNgaybatdau(), entity.getNgayketthuc(), entity.getTrangthaiapdung(), entity.getMakhuyenmai());
    }

    @Override
    public void delete(Object id) {
        JdbcHelper.update(DELETE_SQL, id);
       }

    @Override
    public KhuyenMai selectByID(Object id) {
           List<KhuyenMai> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhuyenMai> selectAll() {
       return this.selectBySql(SELECT_ALL_SQL);
    }
    
    public List<KhuyenMai> selectAllChuaApDung() {
        String sql = "select * from KhuyenMai where KM_TrangThaiApDung=N'Chưa áp dụng' and KM_TrangThaiTonTai=1";
       return this.selectBySql(sql);
    }
    
    public List<KhuyenMai> selectAllDangApDung() {
       String sql = "select * from KhuyenMai where KM_TrangThaiApDung=N'Đang áp dụng' and KM_TrangThaiTonTai=1";
       return this.selectBySql(sql);
    }
    
    public void updateTrangThai(String trangthai, String ma) {
        String UPDATETRANGTHAI_SQL = "UPDATE KhuyenMai SET  KM_TrangThaiApDung =? where KM_Ma=?";
       JdbcHelper.update(UPDATETRANGTHAI_SQL, trangthai,ma);
    }

    @Override
    protected List<KhuyenMai> selectBySql(String sql, Object... args) {
            List<KhuyenMai>list = new ArrayList<KhuyenMai>();
        try{
            ResultSet rs = JdbcHelper.query(sql, args);
            while(rs.next()){
                KhuyenMai entity = new KhuyenMai();
   
                entity.setMakhuyenmai(rs.getString("KM_Ma"));
                entity.setNgaytao(rs.getDate("KM_NgayTao"));
                entity.setGiamtheophantram(rs.getBoolean("KM_TheoPhanTram"));
                entity.setGiatrikhuyenmai(rs.getDouble("KM_GiaTriKhuyenMai"));
                entity.setNgaybatdau(rs.getDate("KM_NgayBatDau"));
                entity.setNgayketthuc(rs.getDate("KM_NgayKetThuc"));
                entity.setTrangthaiapdung(rs.getString("KM_TrangThaiApDung").trim());
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
        
    }
    
}
