package model.dao.impl;

import db.DB;
import db.DbExeception;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {


    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deletById(Seller id) {

    }

    @Override
    public Seller findById(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                   "SELECT seller.*,department.Name as DepName \n" +
                           "FROM seller INNER JOIN department \n" +
                           "ON seller.DepartmentId = department.Id \n" +
                           "WHERE seller.Id = ?");

                   st.setInt(1, id);
                   rs = st.executeQuery();
                   if (rs.next()){
                       Departament dep = instantiateDepartament(rs);
                       Seller obj = instatiateSeller(rs, dep);
                       return obj;
                   }
                   return null;

    }catch (SQLException e){
            throw new DbExeception(e.getMessage());
        } finally{
            DB.clouseStatement(st);
            DB.closeResultSet(rs);

        }

    }

    private Seller instatiateSeller(ResultSet rs, Departament dep) throws SQLException {
        Seller obj = new Seller();
        new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartament(dep);
        return obj;
    }

    private Departament instantiateDepartament(ResultSet rs) throws SQLException {
        Departament dep = new Departament();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }

    @Override
    public List<Seller> findByDepartament(Departament departament) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName \n" +
                            "FROM seller INNER JOIN department \n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "WHERE DepartmentId = ? \n" +
                            "ORDER BY Name"
            );
            st.setInt(1, departament.getId());
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();
            while (rs.next()){

                Departament dep = map.get(rs.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartament(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller obj = instatiateSeller(rs, dep);
                list.add(obj);

            }
            return list;


        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        }finally{
            DB.clouseStatement(st);
            DB.closeResultSet(rs);
        }

    }
}
