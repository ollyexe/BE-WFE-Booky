package Dao;

import Dao.*;

import java.sql.Connection;
import java.util.ArrayList;

public class main {

    public static void main(String[] args) {
        Dao dao = new Dao("jdbc:mysql://localhost:3306/db_book","dbhelper","73C88AAFCFC9701657356F643382EBE40E2B8660C");



        dao.getLezioneByUtenteAndByDay("luigi@gmail.com","2022-11-09");
for(Lezione l :dao.getLezioneByUtenteAndByDay("luigi@gmail.com","2022-11-10")){
    System.out.println(l.toString());
}




    }


}
