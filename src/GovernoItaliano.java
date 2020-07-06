import javax.sound.sampled.Clip;
import java.util.Random;

public class GovernoItaliano extends GovernoBase {
    // variabili di istanza
    private boolean incontriPreFase = false;
    private boolean fase1Init = false;
    private boolean fase2Init = false;
    private int giorniAllerta = 0;

    public GovernoItaliano(int nIncontri, int nIndividui, int tampone, int risorse, Virus virus, boolean musicEnabled, Clip mainOST) {
        super(nIncontri, nIndividui, tampone, risorse, virus, musicEnabled, mainOST);
    }


    @Override
    public void strategiaGoverno() {
        // se muore 1 centesimo delle persone o se smettono di lavorare 1/100 della popolazione
        if (cimitero.size() > nIndividui / 100 || quarantena.size() > nIndividui / 100) {     //si allerta a causa del cimitero alla fine
            allertato = true;
            System.out.println("Sono allertato");
        }

        // 3 / 2 = rapporto
        if (giorniAllerta > virus.getDurata() * 3 / 2) {
            System.out.println("Non sono allertato");
            quarantenaAutomatizzata = true;
            allertato = false;
        }

        if (allertato) {
            giorniAllerta += 1;

            if (giorniAllerta > virus.getDurata() / 5 && giorniAllerta < virus.getDurata() / 2) {    //la fase 1 è durata realmente circa 40 giorni
                System.out.println("Entro nella fase 1");
                fase1();
            } else if (giorniAllerta >= virus.getDurata() / 2) {
                System.out.println("Entro nella fase 2");
                fase2();
            } else {
                System.out.println("Entro nella prefase");
                prefase();
            }

        }
    }


    private void prefase() {         //Le persone si mettono la mascherina (non sempre). Perciò ogni giorno una persona ha il 80% di metterla. Metterla
        if (!incontriPreFase) {       //chi ha la mascherina non contagia ma può essere contagiata.
            nIncontri /= 2;
            incontriPreFase = true;
        }

        // se capita prima del quaranta si mette la mascherina

        for (Persona persona : popolo) persona.setMascherina(random.nextInt(100) < 40);
        //System.out.println("Persona " + persona.nome + " si mette la mascherina");

    }

    private void fase1() {           //fase 1 mascherina forzata (quella col filtro).
                                    // Non c'è bisogno di mettere la mascherina alle persone
                                   // si presuppone che tutti la abbiano
        Random random = new Random();
        Persona reference;

        if (!fase1Init) {
            fase1 = true;
            fase1Init = true;

            quarantenaAutomatizzata = false;

            nIncontri /= 75;   // numero abbastanza arbitrario che fa abbattere num incontri

            // voglio mettere un decimo della popolazione iniziale
            // in quarantena
            while (ambulanza.size() < popolo.size() / 10) {
                reference = popolo.get(random.nextInt(popolo.size()));
                if (!ambulanza.contains(reference)) {
                    ambulanza.add(reference);
                }
            }
            System.out.println("Sono stati messi in quarantena " + ambulanza.size() + "persone");
        } else {
            // prendo un decimill della popolazione e fagli il tampone
            // il +1 ti serve per evitare problemi es. governi troppo piccoli
            for (int i = 0; i < (nIndividui / 10000) + 1; i++) {
                reference = popolo.get(random.nextInt(popolo.size()));
                if (tampone(reference) && !ambulanza.contains(reference)) {
                    ambulanza.add(reference);
                }
            }
        }

    }

    private void fase2() {
        //fase 2: rate di circa 10000 persone al giorno. C'e' l'obbligo della mascherina chirugica
        //che diminuisce il rischio di essere contaggiato del 50%.
        //gli incontri vengono aumentati di poco del 15%. Ogni giorno ci sarà un incontro in più.
        Random random = new Random();
        Persona reference;
        if (!fase2Init) {
            fase1 = false;
            fase2Init = true;
            nIncontri = nIncontri + (nIncontri * 15 / 100);
        }

        nIncontri += 1;    // ogni giorno si possono incontrare piu persone

        // il primo for serve per fare il tampone a 1000 persone casuali
        // in quarantena: tutte le persone meno i morti
        for (int i = 0; i < 1000; i++) {
            reference = quarantena.get(random.nextInt(quarantena.size()));
            if (reference.isMalato()) {
                continue;
            }
            if (random.nextBoolean() && !tampone(reference) && !taxi.contains(reference)) {        //faccio il tampone a caso
                taxi.add(reference);
            }
        }

        // fare un millesimo delle persone a caso
        for (int i = 0; i < (nIndividui / 10000) + 1; i++) {
            reference = popolo.get(random.nextInt(popolo.size()));
            if (tampone(reference) && !ambulanza.contains(reference)) {
                ambulanza.add(reference);
            }
        }
        System.out.println("Sono risultate positive al tampone " + ambulanza.size());
    }
}