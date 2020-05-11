public class GovernoCustom extends GovernoBase {
    /*
   Il governo che creerete dovrà agire solo nel metodo "strategia_governo" che va a fare l'override al metodo del padre
   (in cui, in sintesi, il padre non fa nulla). Siete libero di crearvi funzioni, metodo e di fare il overriding di altri
   parametri della classe padre ma a vostro rischio e pericolo. Attenzione:per nessun motivo le classi:
   governo_illuminato, governo_ fantoccio vanno toccate. Siete liberi di AGGIUNGERE a persona cose ma non potete cancellare
   nulla.
   Qui sotto c'è l'impostazione del lavoro che dovrete fare. Da notare che una strategia del genere maderà al collasso subito il
   governo perchè mettere tutt in quarantena significa non fare più soldi.
   Altra cosa importante: nei prossimi giorni verrà modificato quando si farà il tiro della moneta (per ora lo facciamo il giorno
   dopo il cambio di stato)
   la lista ambulanza è usata per trasportare le persone dal popolo in quarantena.
   la lista coffin serve per mettere i morti da qualche parte
   la lista taxi serve per mettere le persone dalla quarantena nel popolo
     */
    public GovernoCustom(int n_incontri, int n_individui, int tampone, int risorse, Virus virus){
        super(n_incontri, n_individui, tampone, risorse, virus);
    }
    @Override
    public void strategia_governo(){
        /*
        questo governo ha la sfera di cristallo e sa esattamente chi è infetto e dopo provvede a metterlo in quarantena.
         */

        if(malati.size() == 1 && !quarantena.contains(malati.get(0))){
            ambulanza.add(malati.get(0));
        }
    }
    /*

     */
}
