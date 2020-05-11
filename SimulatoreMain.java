import java.util.Scanner;

public class SimulatoreMain {
    public static void main(String[] args) {
        boolean testing = true;  //stiamo testando ancora
        Scanner scanner = new Scanner(System.in);
        int n_individui;         //la mia popolazione
        int n_incontri;          //quante persone un individuo incontra al giorno
        int costo;               //costo tampone (c)
        int risorse;             //r<p*c  (risorse  <  popolazione*costoTampone)
        int infettivita;         //quanto è probabile che un individuo venga infettato
        int sintomaticita;       //probabilità che un contagiato sviluppi sintomi
        int letalita;            //mortalità
        int durata;              //durata del virus

        if (!testing){
            /////////// INPUT ASPETTI GENERALI ///////////
            System.out.print("Popolazione iniziale: ");
            n_individui = scanner.nextInt();
            System.out.println("Popolazione iniziale: " + n_individui);
            System.out.print("Costo tampone: ");
            costo = scanner.nextInt();
            System.out.println("Costo tampone: " + costo);
            risorse = (costo* n_individui) -1;
            System.out.println("Risorse iniziali: " + risorse);
            System.out.print("Numero incontri giornalieri: ");
            n_incontri = scanner.nextInt();
            System.out.println("Numero incontri giornalieri: " + n_incontri);

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
            //100000
            n_individui = 10000;                             // popolazione
            n_incontri = 5;                                 // incontri giornalieri
            costo = 1;                                     // costo tampone (c)
            risorse = (10 * n_individui * costo) -1;      //r<10*p*c  risorse<popolazione*costoTampone

            infettivita = 5;                             // probabilita' di venire infettato
            sintomaticita = 25;                         // probabilità che un contagiato sviluppi sintomi
            letalita = 10;                             // mortalità
            durata = 14;                              // giorni
        }

        Virus virus = new Virus(infettivita, sintomaticita, letalita, durata);
        Esito esito = new Esito();

        for (int i = 0; i < 1; i++){
            esito.addBollettino(new GovernoNorthKorea(n_incontri, n_individui, costo, risorse, virus).simulazione());
        }

        for (int i = 0; i < 1; i++){
            esito.addBollettino(new GovernoCenciarelloso(n_incontri, n_individui, costo, risorse, virus).simulazione());
        }

        // LA RESA DEI CONTI
        System.out.println();
        System.out.println("Stampo tutti i bollettini:");

        esito.stampaTuttiBollettini();

        System.out.println();
        System.out.println("Simulazioni effettuate: " + esito.getHowManySimulations());
        System.out.println("La media dei morti e' " + (int) esito.getMediaMorti());
    }
}