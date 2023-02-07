package Dao;

public class Docenza {
    private int Docente,Corso;
    private String data_start,Data_end;

    private String active;

    public Docenza(int docente, int corso, String data_start, String data_end,String active) {
        Docente = docente;
        Corso = corso;
        this.data_start = data_start;
        Data_end = data_end;
        this.active= active;
    }

    public int getDocente() {
        return Docente;
    }

    public void setDocente(int docente) {
        Docente = docente;
    }

    public int getCorso() {
        return Corso;
    }

    public void setCorso(int corso) {
        Corso = corso;
    }

    public String getData_start() {
        return data_start;
    }

    public void setData_start(String data_start) {
        this.data_start = data_start;
    }

    public String getData_end() {
        return Data_end;
    }

    public void setData_end(String data_end) {
        Data_end = data_end;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
