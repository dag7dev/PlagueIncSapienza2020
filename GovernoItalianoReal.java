import java.util.Random;

public class GovernoItalianoReal extends GovernoBase {
    boolean ridotto;
    int giorniAllerta = 5;

    public GovernoItalianoReal(int nIncontri, int nIndividui, int tampone, int risorse, Virus virus){
        super(nIncontri, nIndividui, tampone, risorse, virus);
    }



    @Override
    public void strategia_governo() {
        if(cimitero.size() >= nIndividui * 10 / 100) {
            allertato = true;
        }

        if(quarantena.size() == 0) {
            allertato = false;
        }

        Random r = new Random();
        Persona p;

        if(allertato) {
            if(giorniAllerta < 0) {
                // fai tamponi casuali su circa il 15% della popolazione
                for (int i = 0; i < popolo.size() * 5 / 10; i++) {     // lo fa sul 15%
                    p = popolo.get(r.nextInt(popolo.size()));   // prende una persona a caso

                    // se fa il tampone ed e' positivo se ne va
                    if (tampone(p) && !ambulanza.contains(p)) {  // lo fa circa sul 15%
                        ambulanza.add(p);
                    }
                }

                giorniAllerta--;
            }
            else{
                System.out.println("SUGAR DADDY HA FINITO IL LAVORO QUI, MO V'ATTACCATE!");
            }

            // ridurre il numero di incontri se la quarantena aumenta
            if(quarantena.size() >= popolo.size() * 5 / 100 && !ridotto) {
                ridotto = true;
                nIncontri /= 2;
            }

            System.out.println("sono allertato! SUGAR RUSH!");
        }
    }
}
