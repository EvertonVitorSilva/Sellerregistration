package model.dao.impl;

import db.DB;
import db.DbExeception;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.*;
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
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller\n" +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId) \n" +
                            "VALUES \n" +
                            "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbExeception("Unexpected error! any rows affected");
            }

        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        } finally {
            DB.clouseStatement(st);

        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentID = ? \n" +
                            "WHERE id = ?", Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        } finally {
            DB.clouseStatement(st);

        }

    }

    @Override
    public void deletById(int id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "DELETE FROM seller WHERE id = ?"
            );

            st.setInt(1, id);
            int rows = st.executeUpdate();
            if(rows == 0){
                throw new DbExeception("ID not exists");
            }
        } catch (SQLException e){
            throw new DbExeception(e.getMessage());
        } finally {
            DB.clouseStatement(st);
        }
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName \n" +
                            "FROM seller INNER JOIN department \n" +
                            "ON seller.DepartmentId = department.Id\n" +
                            "ORDER BY Name"
            );

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
