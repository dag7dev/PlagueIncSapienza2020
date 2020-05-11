import java.util.Random;

public class GovernoItaliano extends GovernoBase {

    public GovernoItaliano(int n_incontri, int n_individui, int tampone, int risorse, Virus virus){
        super(n_incontri, n_individui, tampone, risorse, virus);
    }

    @Override
    public void strategia_governo() {
        if (quarantena.size() > n_individui/1000){
            allertato = true;
        }
        if(quarantena.size() < n_individui/100){
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
