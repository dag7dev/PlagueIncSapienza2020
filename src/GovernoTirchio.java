// risparmia $$$ mette in quarantena a batch quando e' obbligato

import javax.sound.sampled.Clip;

public class GovernoTirchio extends GovernoBase{
    private boolean PrimaAllerta;
    private boolean SecondaAllerta;
    private int giorniAllerta;
    private boolean firstBatch;
    private boolean secondBatch;
    private boolean fineAllerta;

    public GovernoTirchio(int nIncontri, int n_individui, int tampone, int risorse, Virus virus, boolean musicEnabled, Clip mainOST) {
        super(nIncontri, n_individui, tampone, risorse, virus, musicEnabled, mainOST);
        this.PrimaAllerta = true;
        this.SecondaAllerta = true;
        this.fineAllerta = true;
    }

    @Override
    public void strategiaGoverno() {
        //caso d'allerta
        if(allertato) System.out.println("sono in allerta da " + giorniAllerta++);


         if((risorse < risorseIniziali - ((1 * risorseIniziali)/100)) && PrimaAllerta){
             allertato = true;
             PrimaAllerta = false;
             quarantenaAutomatizzata = false;
             firstBatch = true;

        }


        //caso di StopAllerta   TOTALE
        if(giorniAllerta >= virus.getDurata() && fineAllerta){
            quarantenaAutomatizzata = true;
            fineAllerta = false;
            for(Persona x : quarantena){
                if(!x.isMalato())  taxi.add(x);
            }
            allertato = false;
        }

        //cosa fare durante l'allerta


        //1 batch
        if(firstBatch){
            firstBatch = false;
            System.out.println("prima ondata di persone in quarantena");
            for(int i = 0; i<popolo.size()/2;i++){
                ambulanza.add(popolo.get(i));
            }
        }

        //2 batch
        if(giorniAllerta>=virus.getDurata()/2 && SecondaAllerta){
            SecondaAllerta = false;
            secondBatch = true;
            for(Persona x : quarantena){
                if(!x.isMalato() && !taxi.contains(x)) taxi.add(x);
            }
        }
        if(secondBatch){
            secondBatch = false;
            System.out.println("seconda ondata di persone in quarantena");

            ambulanza.addAll(popolo);
        }
    }
}
