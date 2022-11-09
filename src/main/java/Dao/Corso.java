package Dao;

 public class Corso {




        private int ID;
        private String nome;

        public Corso(int ID,String nome){
            this.nome = nome;
            this.ID = ID;
        }




     public String getNome() {
        return nome;
    }

     public int getID() {
         return ID;
     }

     @Override
     public String toString() {
         return "Corso{" +
                 "ID=" + ID +
                 ", nome='" + nome + '\'' +
                 '}';
     }
 }
