package model.dao.impl;

import db.DB;
import db.DbExeception;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
}
