package Dao;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) {
        Dao dao = new Dao("jdbc:mysql://localhost:3306/db_book", "dbhelper", "73C88AAFCFC9701657356F643382EBE40E2B8660C");




        System.out.println(dao.getMaterieByProfessore("barbero@gmail.com").toString());
    }

}
