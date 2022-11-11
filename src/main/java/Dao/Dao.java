package Dao;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;


public class Dao {
    private static String url ,user ,password;

    public  Dao(String url ,String user ,String password){
        this.url=url;
        this.user = user;
        this.password=password;

    }


    public static String encryptMD5(String testoChiaro){
        String key = DigestUtils.md5Hex(testoChiaro).toUpperCase();
        return key;
    }
    public static boolean checkMD5(String password, String testoChiaro){
        if (password.equals(encryptMD5(testoChiaro).toUpperCase()))
            return true;
        else
            return false;
    }
    public static void registerDriver() {
        try { // registrazione del driver JDBC per MySQL
            DriverManager.registerDriver(new
                    com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static Connection getConnection() {
        try {
            Dao.registerDriver();
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Successful Connection");
            return con;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void closeCon(Connection con) {
        try {

        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

    }


    //Utente---------------------------------------------------------------------
    public static ArrayList<Utente> dumpTableUtente() {
        Connection con = null;
        ArrayList<Utente> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From utente");

            while (rs.next()) {

                Utente u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"));
                dump.add(u);
            }

            System.out.println("Successful Dump");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


        return dump;
    }


    public static Utente getUtente(String mail){
        Connection con = null;
        Utente u = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT * FROM utente Where Email = ? ;");
            prs.setString(1, mail);


            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                 u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"));

            }
            System.out.println("Successful get");

            return u;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return u;
    }


    public static int  insertUtente(Utente u) {
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("INSERT INTO `utente` (`ID`, `Email`, `Password`, `Nome`, `Cognome`, `Ruolo`, `PF`, `Stelle`) VALUES (NULL, ?,?,?,?,?, NULL, '0');");


            prs.setString(1, u.getEmail());
            prs.setString(2, encryptMD5(u.getPassword()));
            prs.setString(3, u.getNome());
            prs.setString(4, u.getCognome());
            prs.setString(5, u.getRuolo());



            return prs.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return 0;
    }


    public static void deleteUtente(String email) {
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("DELETE FROM  utente WHERE Email=? ");
            prs.setString(1, email);


            prs.executeUpdate();


            System.out.println("Successful Delete");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

    }

    public void addFoto(int id,String foto_path){
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("UPDATE `utente` SET `PF` = ? WHERE `utente`.`ID` = ?;");
            prs.setString(1,foto_path);
            prs.setInt(2,id);



            prs.executeUpdate();


            System.out.println("Successful Update");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


    }

    public void updateStelle(int id,int stelle){
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("UPDATE `utente` SET `Stelle` = ? WHERE `utente`.`ID` = ?;");
            prs.setString(1,Integer.toString(stelle));
            prs.setInt(2,id);



            prs.executeUpdate();


            System.out.println("Successful Update");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


    }


    public int getIDbyUtente(String email){

        Connection con = null;
        int ID= -1;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT ID FROM utente WHERE Email = ?;");
            prs.setString(1,email);




            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                ID = rs.getInt("ID");

            }

            if(ID==-1){
                System.out.println("Fail Get");
            }
            else {
                System.out.println("Successful Get");
            }


            return ID;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return ID;
    }

    public Utente getUtenteByID(int ID){
        Connection con = null;
        Utente u=null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT * FROM utente WHERE ID = ?;");
            prs.setInt(1,ID);




            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"));


            }

            if(u==null){
                System.out.println("Fail Get");
            }
            else {
                System.out.println("Successful Get");
            }


            return u;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return u;
    }


    //Utente--------------------------------------------------------------------


    //Corso------------------------------------------------------------------

    public static ArrayList<Corso> dumpTableCorso() {
        Connection con = null;
        ArrayList<Corso> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From corso");

            while (rs.next()) {
                Corso u = new Corso(rs.getInt("ID"),rs.getString("nome"));
                dump.add(u);
            }

            System.out.println("Successful Dump" +
                    "");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


        return dump;
    }

    public static void insertCorso(Corso c) {
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("INSERT INTO corso (ID,nome) Values(null,?);");
            prs.setString(1, c.getNome());


            prs.executeUpdate();


            System.out.println("Successful Insert");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


    }

    public static void deleteCorso(String corso) {
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("DELETE FROM  corso WHERE nome=? ");
            prs.setString(1, corso);


            prs.executeUpdate();


            System.out.println("Successful Delete");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

    }

    public Corso getCorsoByID(int ID){
        Connection con = null;
        Corso c=null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT * FROM corso WHERE ID = ?;");
            prs.setInt(1,ID);




            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                c = new Corso(rs.getInt("ID"),rs.getString("nome"));


            }

            if(c==null){
                System.out.println("Fail Get");
            }
            else {
                System.out.println("Successful Get");
            }


            return c;



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return c;
    }

    //Corso-----------------------------------------------------------------------






    //Lezione------------------------------------------------------------------------------------------

    public static ArrayList<Lezione> dumpTableLezione() {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From lezione");

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


        return dump;
    }


    public static void insertLezione(Lezione l) {
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("INSERT INTO `lezione` (`Data`, `Ora`, `Stato`, `Corso_ID`, `Docente_ID`, `Utente_ID`) VALUES (?, ?, ?, ?, ?, null);");
            prs.setString(1, l.getData());
            prs.setString(2, l.getOra());
            prs.setString(3, l.getStato());
            prs.setInt(4, l.getCorso_ID());
            prs.setInt(5, l.getDocente_ID());


            prs.executeUpdate();


            System.out.println("Successful Insert");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


    }


    public static void deleteLezione(String data, String ora,int docente){
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("DELETE FROM  lezione WHERE Docente_ID=? AND Ora= ? AND Data = ? ; ");
            prs.setInt(1,docente);
            prs.setString(2,ora);
            prs.setString(3,data);


            prs.executeUpdate();


            System.out.println("Successful Delete");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

    }

    public static void prenotaLezione(String data,String ora,int Docente_ID,int Utente_ID){
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("UPDATE `lezione` SET `Utente_ID` =? , `Stato` = 'Prenotata' WHERE `lezione`.`Data` = ? AND `lezione`.`Ora` = ? AND `lezione`.`Docente_ID` = ?;");
            prs.setInt(1,Utente_ID);
            prs.setString(2,data);
            prs.setString(3,ora);
            prs.setInt(4,Docente_ID);


            prs.executeUpdate();


            System.out.println("Successful Prenotazione");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }



    }

    public static void annullaLezione(String data,String ora,int Docente_ID){
        Connection con = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("UPDATE `lezione` SET `Utente_ID` = '0' , `Stato` = 'Libera' WHERE `lezione`.`Data` = ? AND `lezione`.`Ora` = ? AND `lezione`.`Docente_ID` = ?;");
            prs.setString(1,data);
            prs.setString(2,ora);
            prs.setInt(3,Docente_ID);


            prs.executeUpdate();


            System.out.println("Successful Prenotazione");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }



    }



    public ArrayList<Lezione> getLezioneByUtente(String mail){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();
            int ID = getIDbyUtente(mail);

            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Utente_ID = ?;");
            prs.setInt(1,ID);


            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


        return dump;
    }

    public ArrayList<Lezione> getLezioneByUtenteAndByDay(String mail,String data){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();
            int ID = getIDbyUtente(mail);

            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Utente_ID = ? AND Data = ?;");
            prs.setInt(1,ID);
            prs.setString(2,data);


            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                Lezione u = new Lezione(rs.getString("Data"), rs.getString("Ora"),rs.getString("Stato"),rs.getInt("Corso_ID"),rs.getInt("Docente_ID"),rs.getInt("Utente_ID"));
                dump.add(u);
            }

            System.out.println("Successful Dump ");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }


        return dump;
    }


    //Lezione-----------------------------------------------------


    //Helper



    //Helper





}