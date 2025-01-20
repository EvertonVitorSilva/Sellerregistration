package model.dao;

import model.entities.Departament;

import java.util.List;

public interface DepartamentDao {

    public void insert(Departament obj);
    public void update(Departament obj);
    public void deletById(Departament id);
    public Departament findById(int id);
    public List<Departament> findAll();

}
