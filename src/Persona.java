import java.util.Random;

public class Persona {
    private int salute;     /*    salute 0 = sano - non contagioso     /verde
                                 salute 1 = asintomatico - contagioso /giallo
                                 salute 2 = sintomatico - contagioso  /rosso
                                 salute 3 = guarito - immune          /blu
                                 salute 4 = morto                     /nero
                           */

    private int giorni;     // da quando la persona e' infetta e va fino alla fine della durata del virus (se non muore)

    private boolean hasVirus;              // ha il virus o meno. Potrebbe averlo anche essendo verde.
    private boolean toImmunity;         // si curerà o peggiorerà.
    private boolean toDeath;            // si curerà o morirà.

    public static int numero;
    private int nome;

    private Virus virus;
    private boolean mascherina;

    private Random random;

    public Persona(Virus virus){
        /*
            ogni persona ha un suo nome ( numero ), non ha il virus e
            i giorni iniziano da 0 ( si contano solo quando si infetta ).
        */

        this.giorni = 0;
        this.nome = numero++;
        this.hasVirus = false;
        this.virus = virus;

        toImmunity = false;
        toDeath = false;

        mascherina = false;

        random = new Random();
    }

    ////////UTILITIES///////////////////////////////////////////////////////////////////////////////
    public void life(){
        //Ogni persona vivrà la sua vita in movimento. Se la persona è infetta, si iniziano a contare i giorni.

        if(this.hasVirus) {
            giorni += 1;

            if (giorni == virus.getDurata() / 6) {   //finito il periodo di incubazione, diventa gialla (1)
                setSalute(1);

                //lanciamo la moneta -> tiro sintomaticita
                toImmunity = ! (virus.getSintomaticita() > random.nextInt(100));
            }

            else if (giorni == virus.getDurata() / 3 && !toImmunity) {        //se verso_imm è false e siamo arrivati a 1/3 della durata del virus,
                setSalute(2);                                                  //la persona diventa rossa

                //lanciamo la moneta, se true la persona muore
                toDeath = virus.getLetalita() > random.nextInt(100);

                toImmunity = !toDeath;    //verso_immunità è l'opposto di versoMorte ( se muore, non diventerà immune ).
            }

            else if (giorni >= virus.getDurata() && toImmunity){    //altrimenti, se superata la durata del virus senza aver sviluppato
                setSalute(3);                                          //la persona si salva e diventa immune.
                hasVirus = false;                                      //non avrà più il virus.
            }

            else if ((giorni > (virus.getDurata() / 3 ) + virus.getDurata() /10) && toDeath) {         //se abbiamo superato la fase rossa (1/3 + 1 giorno) e
                setSalute(4);                                                               //la persona muore.
            }

           // stampaStatoDebug();
        }
    }

    private void stampaStatoDebug() {
        switch(salute) {
            case 1:
                System.out.println("Persona " + nome + " è diventata contagiosa");
                break;
            case 2:
                System.out.println("Persona " + nome + " è diventata critica");
                break;
            case 3:
                System.out.println("Persona " + nome + " è morta");
                break;
            case 4:
                System.out.println("Persona " + nome + " è diventata immune");
                break;
        }
    }


    public boolean isSano() {
        //per vedere se una persona è verde
        return salute == 0;
    }

    public boolean isAsintomatico(){
        //per vedere se una persona è gialla
        return salute == 1;
    }
    public boolean isMalato(){
        //per vedere se una persona è rossa. BISOGNA CURARE
        return salute == 2;
    }

    public boolean isContagioso(){
        //per vedere se una persona è gialla o rossa. (GIALLO NO CURA)
        return salute == 1 || salute == 2;
    }

    public boolean isGuarito(){
        //per vedere se una persona è blu.
        return salute == 3;
    }

    public boolean isDead() {
        //per vedere se una persona è morta.
        return salute == 4;
    }

    ////////GETTERS AND SETTERS/////////////////////////////////////////////////////////////////////////////////////////
    public String getNome() { return Integer.toString(nome); }
    public boolean getHasVirus(){
        return hasVirus;
    }
    public int getGiorni() {
        return giorni;
    }

    public int getSalute() { return salute; }
    public boolean getMascherina() { return mascherina; }


    public void setInfected(){  // la persona viene infetta
        hasVirus = true;
    }
    public void setDurata(int durata) {
        this.giorni = durata;
    }
    public void setSalute(int salute) {
        this.salute = salute;
    }

    public void setGiorni(int giorni) {
        this.giorni = giorni;
    }



    public void setMascherina(boolean mascherina) { this.mascherina = mascherina; }

}
