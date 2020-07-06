import java.util.ArrayList;

public class Esito {                // throw al main, main cattura e da al esito che mette bollettino.
    // variabili di istanza
    private ArrayList<Bollettino> listaBollettini;  // contiene tutti i bollettini delle simulazioni fatte finora
    private final String nome;

    public Esito(String nome){
        listaBollettini = new ArrayList<>();
        this.nome = nome;
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
    public double getMediaVivi() {
        double accVivi = 0;

        // per ogni bollettino prendi il numero dei morti
        for(Bollettino b: listaBollettini) {
            accVivi += b.getVivi();
        }

        return accVivi / getHowManySimulations();
    }
    public double getMediaRisorse() {
        double accRisorse = 0;

        // per ogni bollettino prendi il numero dei morti
        for(Bollettino b: listaBollettini) {
            accRisorse += b.getRisorse();
        }

        return accRisorse / getHowManySimulations();
    }

    public void stampaTuttiBollettini() {
        for (int i = 0; i < listaBollettini.size(); i++) {
            System.out.println("Bollettino n." + (i+1) + ": "); // array starts from zero
            listaBollettini.get(i).stampaDebug();
            System.out.println("Sono morte " + listaBollettini.get(i).getMorti() + " persone");
        }
    }

    public String getNome() {
        return nome;
    }
}

