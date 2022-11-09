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


    public static Utente getUtente(int ID){
        Connection con = null;
        Utente u = null;

        try {
            con = Dao.getConnection();


            PreparedStatement prs = con.prepareStatement("SELECT * FROM utente Where ID = ?");
            prs.setInt(1, ID);


            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                 u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"));

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


            PreparedStatement prs = con.prepareStatement("DELETE FROM  lezione WHERE Docente_ID=? AND Ora= ? AND Data = ?  ");
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


    //Lezione-----------------------------------------------------






}