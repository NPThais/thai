package com.tlk.dao;

import com.tlk.entity.SanPham;
import com.tlk.jdbc.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author MACBOOK Pro
 */
public class SanPhamDAO extends TlkDAO<SanPham, Object> {

    String INSERT_SQL = "INSERT INTO SanPham  (SP_Ten, SP_DonGia, SP_Size, SP_Hinh, SP_MaLoaiSP) VALUES (?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE  SanPham SET SP_Ten =?, SP_DonGia =?, SP_Size =?, SP_Hinh =?, SP_MaLoaiSP =? where SP_Ma=?";
    String DELETE_SQL = "UPDATE SanPham SET SP_TrangThaiTonTai=0 where SP_Ma=?";
    String SELECT_ALL_SQL = "select*from SanPham where SP_TrangThaiTonTai=1";
    String SELECT_BY_ID_SQL = "select * from SanPham where SP_Ma=?";

    @Override
    public void insert(SanPham entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTensanpham(), entity.getGia(), entity.getSize(), entity.getHinh(), entity.getLoaisanpham());
    }

    @Override
    public void update(SanPham entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTensanpham(), entity.getGia(), entity.getSize(), entity.getHinh(), entity.getLoaisanpham(), entity.getMasanpham());
    }

    @Override
    public void delete(Object id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public SanPham selectByID(Object id) {
        List<SanPham> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<String> selectBySanPhamWithName(Integer maloaisp) {
        String sql = "select SP_Ten from SanPham where SP_MaLoaiSP=? group by SP_Ten";
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, maloaisp);
            while (rs.next()) {
                String tensp = rs.getString("SP_Ten");
                list.add(tensp);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<SanPham> selectBySanPham(Integer maloaisp) {
        String sql = "select * from SanPham where SP_MaLoaiSP=?";
        return this.selectBySql(sql, maloaisp);
    }

    public List<SanPham> selectBySanPhamAndTen(Integer maloaisp, String tensp) {
        String sql = "select * from SanPham where SP_MaLoaiSP=? and SP_Ten like ?";
        return this.selectBySql(sql, maloaisp, tensp);
    }

    @Override
    public List<SanPham> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<SanPham>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
//  String INSERT_SQL = "INSERT INTO SanPham  (SP_Ma, SP_Ten, SP_DonGia, SP_Size, SP_Hinh, SP_MaLoaiSP) VALUES (?,?,?,?,?,?)";   
                entity.setMasanpham(rs.getString("SP_Ma"));
                entity.setTensanpham(rs.getString("SP_Ten"));
                entity.setGia(rs.getDouble("SP_DonGia"));
                entity.setSize(rs.getString("SP_Size"));
                entity.setHinh(rs.getString("SP_Hinh"));
                entity.setLoaisanpham(rs.getInt("SP_MaLoaiSP"));

                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<SanPham> selectAllByLoaiSanPham(String maKH) {
        String sql = "SELECT *FROM SanPham inner join LoaiSanPham  on  LoaiSP_Ma=SP_MaLoaiSP where LoaiSP_Ten like ? ";
        return this.selectBySql(sql, maKH);
    }

    public List<SanPham> selectAllSapXepTheoThapDenCao() {
        String sql = "SELECT * FROM SanPham where SP_TrangThaiTonTai=1  ORDER BY SP_DonGia ASC;";
        return this.selectBySql(sql);
    }

    public List<SanPham> selectAllSapXepTheoCaoDenThap() {
        String sql = "SELECT * FROM SanPham where SP_TrangThaiTonTai=1 ORDER BY SP_DonGia DESC;";
        return this.selectBySql(sql);
    }

    public List<SanPham> selectByKeyWord(String keyWord) {
        String sql = "SELECT*FROM SanPham WHERE SP_Ten LIKE ? AND SP_TrangThaiTonTai=1";
        return this.selectBySql(sql, "%" + keyWord + "%");
    }

}
