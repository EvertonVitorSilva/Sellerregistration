package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SellerDao sd = DaoFactory.creatSellerDao();

        Seller sl = sd.findById(3);
        System.out.println("============ TEST 1 ==============");
        System.out.println(sl);
        System.out.println("============ TEST 2 ==============");
        Departament departament = new Departament(2, null);
        List<Seller> list = sd.findByDepartament(departament);
        for(Seller obj : list){
            System.out.println("-------------------------------");
            System.out.println(obj);
            System.out.println("-------------------------------");
        }
        System.out.println("============ TEST 3 ==============");
        List<Seller> listDep = sd.findAll();
        for(Seller obj : listDep){
            System.out.println("-------------------------------");
            System.out.println(obj);
            System.out.println("-------------------------------");

        }
        System.out.println("============ TEST 4 ==============");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, departament);
        sd.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());
        System.out.println("============ TEST 5 ==============");
        sl = sd.findById(3);
        sl.setName("Martha Grey");
        sd.update(sl);
        System.out.println("Update Complete");
        System.out.println("============ TEST 6 ==============");
        sd.deletById(2);
        System.out.println("Deleted!");
    }
}
