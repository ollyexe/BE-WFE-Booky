package Dao;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.codec.digest.DigestUtils;


public class Dao {
    private static String url ,user ,password;

    public  Dao(String url ,String user ,String password){
        Dao.url =url;
        Dao.user = user;
        Dao.password =password;

    }


    public static String encryptMD5(String testoChiaro){

        return DigestUtils.md5Hex(testoChiaro).toUpperCase();
    }
    public static boolean checkMD5(String password, String testoChiaro){
        return password.equals(encryptMD5(testoChiaro).toUpperCase());
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
            if(con==null){
                throw new Error("Connection.connection.getConnection() : connessione non riuscita");
            }
            else{
                return con;
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void closeCon(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

                if(rs.getString("Attivo").equals("true")){

                    Utente u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"),rs.getString("Attivo"));
                    dump.add(u);
                }

            }

            System.out.println("Successful Dump of : " + dump.size());


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


    public static Utente getUtente(String email){
        Connection con = null;
        Utente u = null;

        try {

            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.getUtente().error():User with this mail doesnt  exists");

            }
            PreparedStatement prs = con.prepareStatement("SELECT * FROM utente Where Email = ? ;");
            prs.setString(1, email);
            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                 u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"), rs.getString("Attivo"));

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


    public static boolean insertUtente(Utente u) {
        Connection con = null;

        try {

            con = Dao.getConnection();
            if(utenteExists(u.getEmail())){
                throw new Error("Utente.insertUser.error():User with this mail already exists");

            }
            PreparedStatement prs = con.prepareStatement("INSERT INTO utente (ID, Email, Password, Nome, Cognome, Ruolo, PF, Stelle,Attivo) VALUES (NULL, ?,?,?,?,?, NULL, '0','true');");
            prs.setString(1, u.getEmail());
            prs.setString(2, encryptMD5(u.getPassword()));
            prs.setString(3, u.getNome());
            prs.setString(4, u.getCognome());
            prs.setString(5, u.getRuolo());
            prs.executeUpdate();


            return utenteExists(u.getEmail());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return false;
    }


    public static boolean utenteExists(String email){
        Connection con = null;
        ArrayList<String> existing_users = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Email From utente  ; ");

            while (rs.next()) {



                    existing_users.add(rs.getString("Email"));


            }

            return existing_users.contains(email);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return false;
    }


    public static boolean deleteUtente(String email) {
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.deleteUtente.error():User with this mail doesnt exists");

            }


            PreparedStatement prs = con.prepareStatement("UPDATE utente SET Attivo = 'false' WHERE utente.Email = ?;");
            prs.setString(1, email);


            prs.executeUpdate();


            if(utenteIsActive(email)){
                throw new Error("Utente.deleteUser.error():Unsuccesful deletion");
            }
            else{
                return true;
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return false;

    }


    public static boolean utenteIsActive(String email){
        Connection con = null;
        ArrayList<String> existing_users = new ArrayList<>();
        try {


            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.utenteIsActive.error():User with this mail doesnt exists");

            }
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Email From utente Where Attivo = 'true' ; ");

            while (rs.next()) {



                existing_users.add(rs.getString("Email"));


            }

            return existing_users.contains(email);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return false;
    }


    public boolean addFoto(String email,String foto_path){
            Connection con = null;

            try {
                con = Dao.getConnection();
                if(!utenteExists(email)){
                    throw new Error("Utente.addFoto.error():User with this mail doesnt exists");

                }


                PreparedStatement prs = con.prepareStatement("UPDATE utente SET PF = ? WHERE utente.Email = ?;");
                prs.setString(1,foto_path);
                prs.setString(2,email);



                prs.executeUpdate();

                Utente u = getUtente(email);


                return u.getPf().equals(foto_path);


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }


            }

        return false;
        }


    public boolean updateStelle(String email){
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.utenteIsActive.error():User with this mail doesnt exists");

            }

            Utente u = getUtente(email);
            // getMediaValutazioniByDocente
            //double stelle=
            double stelle = 5;



            PreparedStatement prs = con.prepareStatement("UPDATE utente SET Stelle = ? WHERE utente.Email = ?;");
            prs.setDouble(1,stelle);
            prs.setString(2,email);



            prs.executeUpdate();


            if(getUtente(email).getStelle()==stelle){
                return true;
            }else{
                throw new Error("Utente.updateStelle.error(): update non riuscito");

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return false;
    }

    public int getNumeroValutazioni(String email){
        Connection con = null;

        try {
            con = Dao.getConnection();
            if(!utenteExists(email)){
                throw new Error("Utente.utenteIsActive.error():User with this mail doesnt exists");

            }

            Utente u = getUtente(email);
            // getMediaValutazioniByDocente
            //double stelle=
            int numero_valutazioni = 0;



            PreparedStatement prs = con.prepareStatement("SELECT count(Valutazione) AS numero_valutazioni FROM lezione WHERE Docente_ID = ? AND Valutazione>0 AND Stato='Conclusa';");
            prs.setInt(1,u.getID());



            ResultSet rs = prs.executeQuery();

            if(rs.next()){
                numero_valutazioni= rs.getInt("numero_valutazioni");
            }


            return numero_valutazioni;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        return -1;
    }



    //Utente--------------------------------------------------------------------


    //Corso------------------------------------------------------------------


    public ArrayList<Corso> dumpTableCorso() {
        Connection con = null;
        ArrayList<Corso> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From corso");

            while (rs.next()&& rs.getString("Attivo").equals("true")) {
                Corso u = new Corso(rs.getInt("ID"),rs.getString("nome"),rs.getString("Attivo"));
                dump.add(u);
            }

            System.out.println("Successful Dump of : " + dump.size());


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

    public static boolean corsoExists(String corso){
        Connection con = null;
        ArrayList<String> existing_courses = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Nome From Corso  ; ");

            while (rs.next()) {



                existing_courses.add(rs.getString("Nome"));


            }

            return existing_courses.contains(corso);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return false;
    }

    public static boolean corsoIsActive(String corso){
        Connection con = null;
        ArrayList<String> existing_courses = new ArrayList<>();
        try {


            con = Dao.getConnection();
            if(!corsoExists(corso)){
                throw new Error("Corso.corsoIsActive.error():Corso with this name doesnt exists");

            }
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select Nome From Corso Where Attivo = 'true' ; ");

            while (rs.next()) {



                existing_courses.add(rs.getString("nome"));


            }

            return existing_courses.contains(corso);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return false;
    }




    public boolean deleteCorso(String corso) {
        Connection con = null;

        try {
            con = Dao.getConnection();

            if(!corsoExists(corso)){
                throw new Error("Corso.addFoto.error():User with this mail doesnt exists");

            }


            PreparedStatement prs = con.prepareStatement("UPDATE corso SET Attivo = 'false' WHERE corso.Nome = ?;");
            prs.setString(1, corso);


            prs.executeUpdate();


            if(corsoIsActive(corso)){
                throw new Error("Corso.deleteUser.error():Unsuccesful deletion");
            }
            else{
                return true;
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }
        return false;
    }

    /*
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
                System.out.println("Fail Get Corso");
            }
            else {
                System.out.println("Successful Get Corso");
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
    public ArrayList<Lezione> getLeasonsStory(String mail){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();
            int ID = getIDbyUtente(mail);

            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Utente_ID = ? AND Stato = 'Conclusa' ;");
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

    public ArrayList<Utente> getAllProfessori(){
        Connection con = null;
        ArrayList<Utente> docenti = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select * From utente Where ruolo = 'docente';");

            while (rs.next()) {

                Utente u = new Utente(rs.getInt("ID"), rs.getString("Email"), rs.getString("Password"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Ruolo"),rs.getString("PF"),rs.getInt("Stelle"));
                docenti.add(u);
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


        return docenti;
    }

    public ArrayList<Corso> getAllCorsiDisponibili(){
        Connection con = null;

        ArrayList<Corso> corsi_disponibili = new ArrayList<>();
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("Select distinct(Corso_ID) From lezione;");

            while (rs.next()) {

                Corso c =getCorsoByID(rs.getInt("Corso_ID"));
                corsi_disponibili.add(c);
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


        return corsi_disponibili;
    }

    public Corso getCorsoByNome(String corso){
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        Corso c=null;
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();
            PreparedStatement prs = con.prepareStatement("Select * From corso Where Nome = ?;");
            prs.setString(1,corso);


            ResultSet rs = prs.executeQuery();

            if (rs.next()) {
                c= new Corso(rs.getInt("ID"), rs.getString("Nome"));

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


        return c;
    }

    public ArrayList<Lezione> getLezioniLibereByCorso(String corso) {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        Corso c = getCorsoByNome(corso);
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();
            int ID_corso = c.getID();


            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Corso_ID = ? AND Stato = 'Libera' ;");
            prs.setInt(1,ID_corso);



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
    public ArrayList<Lezione> getLezioniLibereByDocente(String docente) {
        Connection con = null;
        ArrayList<Lezione> dump = new ArrayList<>();
        Utente doc = getUtenteByID(getIDbyUtente(docente));
        try {
            con = Dao.getConnection();
            Statement st = con.createStatement();



            PreparedStatement prs = con.prepareStatement("Select * From lezione Where Docente_ID = ? AND Stato = 'Libera' ;");
            prs.setInt(1,doc.getID());



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

    //Helper


    */


}