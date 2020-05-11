import java.util.ArrayList;

public class Esito {                // throw al main, main cattura e da al esito che mette bollettino.
    // variabili di istanza
    ArrayList<Bollettino> listaBollettini;  // contiene tutti i bollettini delle simulazioni fatte finora

    public Esito(){
        listaBollettini = new ArrayList<>();
    }

    public void addBollettino(Bollettino b){
        listaBollettini.add(b);
    }

    public int getHowManySimulations() {
        return listaBollettini.size();
    }

    // viene calcolata la media delle morti di tutte le simulazioni
    public double getMediaMorti() {
        double accMorti = 0;

        // per ogni bollettino prendi il numero dei morti
        for(Bollettino b: listaBollettini) {
            accMorti += b.getMorti();
        }

        return accMorti / getHowManySimulations();
    }

    public void stampaTuttiBollettini() {
        for (int i = 0; i < listaBollettini.size(); i++) {
            System.out.println("Bollettino n." + (i+1) + ": "); // array starts from zero
            listaBollettini.get(i).stampaDebug();
            System.out.println(listaBollettini.get(i).getMorti());
        }
    }

}

