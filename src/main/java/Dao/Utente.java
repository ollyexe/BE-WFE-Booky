package Dao;

public class Utente {
    private int ID;
    private String email;
    private  String password;
    private String nome;
    private String cognome;
    private String ruolo;

    private String pf;  //profile foto
    private int stelle;

    public Utente(int ID,String email, String password,String nome,String cognome, String ruolo){
        this.ID=ID;
        this.email=email;
        this.password=password;
        this.nome=nome;
        this.cognome  = cognome;
        this.ruolo = ruolo;
    }
    public Utente(int ID,String email, String password,String nome,String cognome, String ruolo,String PF){
        this.ID=ID;
        this.email=email;
        this.password=password;
        this.nome=nome;
        this.cognome  = cognome;
        this.ruolo = ruolo;
        this.pf=PF;

    }

    public Utente(int ID,String email, String password,String nome,String cognome, String ruolo,String PF,int Stelle){
        this.ID=ID;
        this.email=email;
        this.password=password;
        this.nome=nome;
        this.cognome  = cognome;
        this.ruolo = ruolo;
        this.pf=PF;
        this.stelle=Stelle;

    }
    public Utente(int ID,String email, String password,String nome,String cognome, String ruolo,int Stelle){
        this.ID=ID;
        this.email=email;
        this.password=password;
        this.nome=nome;
        this.cognome  = cognome;
        this.ruolo = ruolo;
        this.stelle=Stelle;

    }


    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getRuolo() {
        return ruolo;
    }


    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }




    public int getStelle() {
        return stelle;
    }

    public void setStelle(int stelle) {
        this.stelle = stelle;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "ID=" + ID +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", ruolo='" + ruolo + '\'' +
                ", pf='" + pf + '\'' +
                ", stelle=" + stelle +
                '}';
    }
}
