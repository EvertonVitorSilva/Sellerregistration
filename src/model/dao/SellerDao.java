package model.dao;

import model.entities.Departament;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    public void insert(Seller obj);
    public void update(Seller obj);
    public void deletById(Seller id);
    public Seller findById(int id);
    public List<Seller> findAll();
    List<Seller> findByDepartament(Departament departament);
}
