public class Virus {
    //ogni virus ha i suoi parametri
    private int infettivita;
    private int sintomaticita;
    private int letalita;
    private int durata;

    public Virus(int infettivita, int sintomaticita, int letalita, int durata){
        this.infettivita = infettivita;
        this.sintomaticita = sintomaticita;
        this.letalita = letalita;
        this.durata = durata;
    }

//////////////////////GETTERS AND SETTERS///////////////////////////////////////////////////////////////////////////////////

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setInfettivita(int infettivita) {
        this.infettivita = infettivita;
    }

    public void setLetalita(int letalita) {
        this.letalita = letalita;
    }

    public void setSintomaticita(int sintomaticita) {
        this.sintomaticita = sintomaticita;
    }

    public int getDurata() {
        return durata;
    }

    public int getInfettivita() {
        return infettivita;
    }

    public int getLetalita() {
        return letalita;
    }

    public int getSintomaticita() {
        return sintomaticita;
    }

}
