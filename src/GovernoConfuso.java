import javax.sound.sampled.Clip;

public class GovernoConfuso extends GovernoBase {

    /*
        Il governo si allerta quando ci sono 1/5 dei morti o 1/6 delle persone in quarantena
        Se il governo è allertato si entra nella prima fase dove a tutti vengono imposte delle mascherine. Diminuiscono gli incontri.
        In più si fanno dei tamponi su 1/4 del popolo stando attenti ai soldi = se non ho abbastanza soldi per fare i tamponi su 1/4
        del popolo, allora ne faccio su 1/6.  if (risorse < popolo.size()/ 4 * costo){
                                                    faccio tamponi su popolo.size()/2}

        Se la persona è malata la metto in quarantena.

        Quando metà quarantena sta bene(guariti) e viene rimessa in libertà allora stoppo l'allerta e entro in una fase "normale".

     */
    boolean crisiEconomica = false;
    boolean allertato = false;
    boolean cimiteroFirst = true;

    public GovernoConfuso(int nIncontri, int nIndividui, int tampone, int risorse, Virus virus, boolean musicEnabled, Clip mainOST) {
        super(nIncontri, nIndividui, tampone, risorse, virus, musicEnabled, mainOST);
    }

    @Override
    public void strategiaGoverno() {
        Persona persona;

        if((cimitero.size() > popolo.size()/ 5 && cimiteroFirst) || quarantena.size() > popolo.size() / 6){
            allertato = true;
            cimiteroFirst = false;
            quarantenaAutomatizzata = false;

        }

        if(risorse < quarantena.size() * 3 * costo){
            crisiEconomica = true;
        }

        if(crisiEconomica){
            quarantenaAutomatizzata = true;
            for (int i=0; i < quarantena.size()/2; i++){
                persona = quarantena.get(i);
                if(!taxi.contains(persona)) {
                    persona.setMascherina(true);
                    taxi.add(persona);
                }
            }
        }

        if(allertato){
            for(Persona ref: popolo){
                ref.setMascherina(true);
            }

            for(int i = 0; i < popolo.size() / 4; i++){

                persona = popolo.get(random.nextInt(popolo.size()));

                if (risorse < popolo.size() / 4 * costo){
                    for(int j = 0; j < popolo.size() / 8; j++){
                        persona = popolo.get(random.nextInt(popolo.size()));
                        if ( tampone(persona) && !ambulanza.contains(persona)){
                            ambulanza.add(persona);
                        }
                    }
                }
                if ( tampone(persona) && !ambulanza.contains(persona) ){
                    ambulanza.add(persona);
                }
            }
            for (Persona value : quarantena) {
                persona = value;
                if (persona.isGuarito()) {
                    taxi.add(persona);
                }
            }
        }
    }
}