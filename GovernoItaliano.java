import java.util.Random;

public class GovernoItaliano extends GovernoBase {

    public GovernoItaliano(int n_incontri, int nIndividui, int tampone, int risorse, Virus virus){
        super(n_incontri, nIndividui, tampone, risorse, virus);
    }

    @Override
    public void strategia_governo() {
        if (quarantena.size() > nIndividui/1000){
            allertato = true;
        }
        if(quarantena.size() < nIndividui/100){
            System.out.println("NON SONO ALLERTATO");
            allertato = false;
        }
        if(allertato){
            System.out.println("SONO ALLERTATO");
            Random random = new Random();
            Persona reference;
            for (int i = 0; i<quarantena.size()/40; i++){
                reference = popolo.get(random.nextInt(popolo.size()));
                if (tampone(reference)){
                    ambulanza.add(reference);
                    popolo.remove(reference);
                }
            }
            System.out.println("Sono risultati positivi al tampone " + ambulanza .size());
        }
    }
}
