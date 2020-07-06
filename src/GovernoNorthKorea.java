import javax.sound.sampled.Clip;

public class GovernoNorthKorea extends GovernoBase {
    public GovernoNorthKorea(int nIncontri, int nIndividui, int tampone, int risorse, Virus virus, boolean musicEnabled, Clip mainOST){
        super(nIncontri, nIndividui, tampone, risorse, virus, musicEnabled, mainOST);
    }

    @Override
    public void strategiaGoverno(){
        /*
        questo governo ha la sfera di cristallo e sa esattamente
        chi è infetto e dopo provvede a metterlo in quarantena.
         */

        if(quarantena.size() > nIndividui/50){
            allertato = true;
        }

        if(allertato) {
            System.out.println("AMMAZZO TUTTI. SPARO A VISTA");

            Persona reference;
            allertato = false;

            coffin.addAll(quarantena);

            for (int i = 0; i < 3000; i++){
                reference = popolo.get(random.nextInt(popolo.size()));
                if(!coffin.contains(reference)){
                    coffin.add(reference);
                }
            }
        }
    }
}
