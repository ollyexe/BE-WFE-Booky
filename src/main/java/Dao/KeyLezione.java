package Dao;

import java.util.Objects;

public class KeyLezione {

    String ora;
    String data;
    int Docente;
    int Corso;

    public KeyLezione(String ora, String data, int docente,int corso){
        this.Docente=docente;
        this.data=data;
        this.ora=ora;
        this.Corso=corso;
    }

    public int getDocente() {
        return Docente;
    }
    public int getCorso() {
        return Corso;
    }

    public String getData() {
        return data;
    }

    public String getOra() {
        return ora;
    }

    @Override
    public String toString() {
        return "keyLezione{" +
                "ora='" + ora + '\'' +
                ", data='" + data + '\'' +
                ", Docente=" + Docente +
                ", Corso=" + Corso +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyLezione that = (KeyLezione) o;
        return Docente == that.Docente && Objects.equals(ora, that.ora) && Objects.equals(data, that.data) && Corso==that.Corso;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ora, data, Docente,Corso);
    }
}
