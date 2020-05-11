// TODO: malati.size puo essere un contatore

import java.util.ArrayList;
import java.util.Random;

public class GovernoBase {
    protected int nIndividui;          //la mia popolazione
    protected int nIncontri;           //quante persone uno incontra al giorno
    protected int costo;                // costo tampone (c)
    protected int risorse;              //r<p*c  risorse<popolazione*costoTampone
    protected int giorni;
    protected boolean allertato;                          //servirà ai governi singoli che creeremo
    protected ArrayList<Persona> popolo;                 //formato da tutti i vivi gialli, verdi e blu
    protected ArrayList<Persona> quarantena;            //se una persona ha i sintomi deve stare ferma
    protected ArrayList<Persona> cimitero;             //i morti, serve per fare una media complessiva
    protected ArrayList<Persona> malati;              //coloro che infettano (gialli; i rossi sono fermi)
    protected ArrayList<Persona> sani;               // PLACEHOLDER
    protected ArrayList<Persona> personeIncontrate;
    protected Virus virus;
    protected ArrayList<Persona> coffin;
    protected ArrayList<Persona> ambulanza;
    protected ArrayList <Persona> taxi;
    protected boolean quarantenaAutomatizzata;
    protected boolean fase1, fase2;
    protected Random random;

    public GovernoBase(int nIncontri, int n_individui, int tampone, int risorse, Virus virus){
        this.nIncontri = nIncontri;
        this.nIndividui = n_individui;
        this.costo = tampone;
        this.risorse = risorse;
        this.virus = virus;

        coffin = new ArrayList<>();
        ambulanza = new ArrayList<>();
        taxi = new ArrayList<>();

        popolo = new ArrayList<>();                  //formato da tutti i vivi gialli, verdi e blu
        quarantena = new ArrayList<>();              //se una persona ha i sintomi deve stare ferma
        cimitero = new ArrayList<>();                //i morti, serve per fare una media complessiva
        malati = new ArrayList<>();                  //coloro che infettano (gialli; i rossi sono fermi)

        quarantenaAutomatizzata = true;
        fase1 = false;
        fase2 = false;

        random = new Random();
        allertato = false;
        giorni = 0;

        creazionePopolo();
    }

    private void creazionePopolo() {
        // aggiungiamo le persone al popolo
        for (int i = 0; i < nIndividui; i++) {
            popolo.add(new Persona(virus));
        }
    }

    public ArrayList<Persona> incontri(Persona persona) {
        //serve per trovare delle persone random da incontrare messe in un array
        ArrayList<Persona> persone_incontrate = new ArrayList<>();

        if(popolo.size() == 0){
            return persone_incontrate;
        }

        for(int i = 0; i < nIncontri; i++){
            Persona reference;
            boolean pochiSuperstiti = false;

            do {
                reference = popolo.get(random.nextInt(popolo.size()));    //serve come persona reference (leggi giù)
                if (popolo.size() -1 <= nIncontri){          //se il popolo ha meno persone degli incontri
                    for(Persona x: popolo){
                        persone_incontrate.add(x);
                    }
                    // sono pochi superstiti = esco dal while
                    pochiSuperstiti = true;
                }
            }
            while(persona.equals(reference) && !pochiSuperstiti);

            if(pochiSuperstiti) { break; }  // non posso fare il doppio break quindi ho creato un flag

            persone_incontrate.add(reference);
        }

        return persone_incontrate;
    }


    public Bollettino simulazione() {
        Persona.numero = 0; // serve solo per il nome

        //prendiamo una persona a caso e la rendiamo gialla ( si inizia con tutti verdi e uno giallo )
        Persona paziente_zero = popolo.get(random.nextInt(popolo.size()));
        malati.add(paziente_zero);
        inizioContagio(paziente_zero);

        paziente_zero.setSalute(1); // rendo gialla
        paziente_zero.setGiorni(virus.getDurata() / 6 - 1);

        return ricorsiva();
    }

    // questa qui la overrida ogni governo
    public void strategia_governo(){ //QUI VA LA STRATEGIA DEI SINGOLI GOVERNI!!!
    }

    public Bollettino ricorsiva() {
        giorni++;

        // RECAP GIORNO PER GIORNO
        printRecap();

        // CASI BASE
        if(risorse <= 0){
            System.out.println("Denaro terminato!");
            //System.out.println("L'Europa non ci aiuta, l'America non finanzia l'OMS. Abbiamo finito i money!");
            return new Bollettino(2, cimitero.size(), popolo.size()+ quarantena.size(), risorse);
        }
        if (popolo.size() == 0 && quarantena.size() == 0){
            System.out.println("Tutti morti");
            //System.out.println("Humanity has just lost her last defender!");
            return new Bollettino(1, cimitero.size(), popolo.size()+ quarantena.size(), risorse);
        }
        if(malati.size() == 0 && popolo.size() > 0){
            System.out.println("Salvi!");
            // System.out.println("WE DID IT GUYS. CORONAVIRUS IS GONE!");
            return new Bollettino(0, cimitero.size(), popolo.size() + quarantena.size(), risorse);
        }

        // (?) TODO: bisogna implementare r0 = V * D * I come condizione di uscita

        coffin      = new ArrayList<>();     // coffin: manda al cimitero
        ambulanza   = new ArrayList<>();    // ambulanza: quarantena
        taxi        = new ArrayList<>();   // taxi: manda nel popolo

        for(Persona persona : popolo) {      //per ogni persona nel mio popolo (cioè ancora in vita
                                            // FARE UN FOR PER I MALATI: PENSA A LORO, NON FARE COME TRUMP
            //ogni persona vivrà la sua vita
            persona.life();

            if (persona.isGuarito()){
                malati.remove(persona);
            }
            else if(persona.isDead()){
                coffin.add(persona);
            }
            else if(persona.isMalato()){
                risorse = risorse - (costo*3) -1;
                if(random.nextBoolean()){           //buonsenso
                    ambulanza.add(persona);
                }
            }
        }

        ////// PARTE PER NON SMINCHIARE L'ITERATORE ///////
        popolo.removeAll(coffin);
        malati.remove(coffin);
        popolo.removeAll(ambulanza);
        ///////////////////////////////////////////////////

        for(Persona persona : quarantena){
            //ogni persona in quarantena costa 1 risorsa giornaliera
            risorse = risorse - 1;

            if(persona.isMalato()){                 //se è malata si aggiungono le cure (3*costo)
                risorse -= (costo * 3);
            }

            persona.life();

            if (persona.isGuarito() && quarantenaAutomatizzata) {
                taxi.add(persona);
                malati.remove(persona);
            }
            else if (persona.isDead()) {
                coffin.add(persona);
                malati.remove(persona);
            }
        }

        ////// PARTE PER NON SMINCHIARE L'ITERATORE //////
        quarantena.removeAll(taxi);
        quarantena.removeAll(coffin);
        quarantena.addAll(ambulanza);   // manda le persone effettivamente in quarantena

        cimitero.addAll(coffin);    // manda le persone al cimitero
        popolo.addAll(taxi);        // rimanda le persone nel popolo
        malati.removeAll(coffin);   // STAT

        ambulanza = new ArrayList<>();
        taxi = new ArrayList<>();
        coffin = new ArrayList<>();
        //////////////////////////////////////////////////////////////////////

        ///// GOVERNO ALLERTA /////
        strategia_governo();

        quarantena.removeAll(coffin);
        popolo.removeAll(coffin);
        cimitero.addAll(coffin);
        malati.removeAll(coffin);

        quarantena.removeAll(taxi);
        popolo.addAll(taxi);

        popolo.removeAll(ambulanza);
        quarantena.addAll(ambulanza);

        runIncontri();  // fa incontrare effettivamente la gente

        return ricorsiva();
    }

    // fa effettivamente incontrare le persone
    private void runIncontri() {
        for (Persona persona : popolo){
            if(malati.size() >= popolo.size() || fase1){
                break;
            }
            if(persona.isMalato() || persona.getMascherina()){
                continue;
            }

            personeIncontrate = incontri(persona);

            for(Persona incontrata : personeIncontrate){
                //  System.out.println("La persona " +persona.nome + " si sta incontrando con la persona " +incontrata.nome);
                if (incontrata.getMascherina()){
                    if(virus.getInfettivita() > random.nextInt(100) &&
                            !malati.contains(incontrata) &&
                            persona.isContagioso() &&
                            random.nextBoolean()){    // hai il 50% di essere contagiato con la mascherina
                        // hai una chance in piu

                        inizioContagio(incontrata);
                        malati.add(incontrata);
                    }
                }
                else if (virus.getInfettivita() > random.nextInt(100) &&
                        !malati.contains(incontrata) &&
                        persona.isContagioso()){

                    inizioContagio(incontrata);
                    malati.add(incontrata);
                }

                if( incontrata.isContagioso() &&
                    virus.getInfettivita() > random.nextInt(100) &&
                    !malati.contains(persona)){

                    inizioContagio(persona);
                    malati.add(persona);
                }
            }
        }
    }

    private void printRecap() {
        System.out.println("Giorno " + giorni);
        System.out.println("RECAP: \nsono infetti " + malati.size() );
        System.out.println("Sono vivi in totale " + (quarantena.size()+popolo.size()));
        System.out.println("Sono in quarantena " + quarantena.size());
        System.out.println("Sono morti " + cimitero.size());
        System.out.println("Sono in movimento " + popolo.size());
        System.out.println("Ci sono " + risorse + "risorse");
    }

    private void inizioContagio(Persona paziente){
        //la persona si infetta se non è blu o se non è già infettata (virus = true)
        if(!paziente.isGuarito() && (paziente.getSalute() == 0)) {
            //System.out.println("Persona " + paziente.nome + " è stata infettata");
            paziente.setInfected();
        }
    }


    public boolean tampone(Persona persona){            //torna true se una persona è rossa oppure gialla
        risorse = risorse - costo;
        return persona.isContagioso();
    }
}
