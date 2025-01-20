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
            System.out.println(obj);
        }

    }
}
