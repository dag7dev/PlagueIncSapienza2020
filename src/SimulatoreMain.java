import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;

public class SimulatoreMain {
    public static void main(String[] args) throws InterruptedException {
        boolean manualInput = false;     // abilita l'input manuale
        boolean musicEnabled = false;   // abilita l'audio
        Clip mainOST = null;

        if(musicEnabled) mainOST = playSound("OSTCoffin");

        Scanner scanner = new Scanner(System.in);
        int nIndividui;         //la mia popolazione
        int nIncontri;          //quante persone un individuo incontra al giorno
        int costo;               //costo tampone (c)
        int risorse;             //r<p*c  (risorse  <  popolazione*costoTampone)
        int infettivita;         //quanto è probabile che un individuo venga infettato
        int sintomaticita;       //probabilità che un contagiato sviluppi sintomi
        int letalita;            //mortalità
        int durata;              //durata del virus
        int nEsecuzioni;
        System.out.print("Vuoi attivare l'audio? [Y/N]: " );
        musicEnabled = scanner.next().equalsIgnoreCase("Y") ? true : false;
        System.out.print ("Vuoi inserire i parametri manualmente? [Y/N] ");
        manualInput = scanner.next().equalsIgnoreCase("Y") ? true : false;
        // input da tastiera
        if (manualInput){
            /////////// INPUT ASPETTI GENERALI ///////////
            System.out.print("Quante simulazioni vuoi fare a governo?  ");
            nEsecuzioni = scanner.nextInt();
            System.out.print("Popolazione iniziale: ");
            nIndividui = scanner.nextInt();
            //System.out.println("Popolazione iniziale: " + nIndividui);
            System.out.print("Costo tampone: ");
            costo = scanner.nextInt();
         //   System.out.println("Costo tampone: " + costo);
            risorse = (costo* nIndividui) -1;
        //    System.out.println("Risorse iniziali: " + risorse);
            System.out.print("Numero incontri giornalieri: ");
            nIncontri = scanner.nextInt();
        //    System.out.println("Numero incontri giornalieri: " + nIncontri);

            ///////////INPUT ASPETTI SANITARI//////////////////////////////////////////////////////////////////////77
            System.out.print("Percentuale di infettività: ");
            infettivita = scanner.nextInt();
            System.out.println("Percentuale di infettività: " + infettivita);
            System.out.print("Percentuale di sintomaticità: ");
            sintomaticita = scanner.nextInt();
            System.out.println("Percentuale di sintomaticità: " + sintomaticita);
            System.out.print("Percentuale di letalità: ");
            letalita = scanner.nextInt();
            System.out.print("Durata: ");
            durata = scanner.nextInt();
            System.out.println("Durata: " + durata);
        }

        else{
            nIndividui = 500000;                             // popolazione
            nIncontri = 30;                                 // incontri giornalieri
            costo = 10;                                     // costo tampone (c)
            risorse = (10 * nIndividui * costo) -1;      //r<10*p*c  risorse<popolazione*costoTampone
            infettivita = 65;                             // probabilita' di venire infettato
            sintomaticita = 35;                         // probabilità che un contagiato sviluppi sintomi
            letalita = 80;                             // mortalità
            durata = 45;                              // giorni
            nEsecuzioni = 1;
        }

        Virus virus = new Virus(infettivita, sintomaticita, letalita, durata);
        Esito esitoBase = new Esito("base");
        Esito esitoItaliano = new Esito("italiano");
        Esito esitoTirchio = new Esito("tirchio");
        Esito esitoKorea = new Esito("korea");
        Esito esitoConfuso = new Esito("confuso");

        for (int i = 0; i < nEsecuzioni; i++){
            esitoBase.addBollettino(new GovernoBase(nIncontri, nIndividui, costo, risorse, virus, musicEnabled, mainOST).simulazione());
            esitoItaliano.addBollettino(new GovernoItaliano(nIncontri, nIndividui, costo, risorse, virus, musicEnabled, mainOST).simulazione());
            esitoTirchio.addBollettino(new GovernoTirchio(nIncontri, nIndividui, costo, risorse, virus, musicEnabled, mainOST).simulazione());
            esitoKorea.addBollettino(new GovernoNorthKorea(nIncontri, nIndividui, costo, risorse, virus, musicEnabled, mainOST).simulazione());
            esitoConfuso.addBollettino(new GovernoConfuso(nIncontri, nIndividui, costo, risorse, virus, musicEnabled, mainOST).simulazione());
        }

        printUtil(esitoBase);
        printUtil(esitoItaliano);
        printUtil(esitoTirchio);
        printUtil(esitoKorea);
        printUtil(esitoConfuso);
    }

    public static Clip playSound(String sound) {
        File file = new File(System.getProperty("user.dir") + "/src/sounds/" + sound + ".wav");
        Clip clip = null;

        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        }catch (Exception e){
        }

        return clip;
    }

    public static void printUtil(Esito e) {
        System.out.println("==========");
        System.out.println("Stampo tutti i bollettini di " + e.getNome().toUpperCase() + ":");

        e.stampaTuttiBollettini();

        System.out.println();
        System.out.println("Simulazioni effettuate: " + e.getHowManySimulations());
        System.out.println("La media dei morti e' " + (int) e.getMediaMorti());
        System.out.println("La media dei vivi e' " + (int) e.getMediaVivi());
        System.out.println("La media delle risorse e' " + (int) e.getMediaRisorse());
    }
}