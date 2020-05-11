public class Bollettino {
    /*
    CASI BASE della funzione ricorsiva!

    0. La malattia viene debellata: almeno un individuo rimane in vita e
       tutti i vivi sono sani o guariti;
    1. La malattia vince: tutti morti;
    2. Collasso: avviene quando terminano le risorse.
     */
    private final int errorCode;
    private final int morti;
    private final int vivi;
    private final int risorse;

    public Bollettino(int errorCode, int morti, int vivi, int risorse){
        this.errorCode = errorCode;
        this.morti = morti;
        this.vivi = vivi;
        this.risorse = risorse;
    }

    // metodi di get
    public int getErrorCode() {
        return errorCode;
    }

    public int getMorti() {
        return morti;
    }

    public int getVivi() {
        return vivi;
    }

    public int getRisorse() {
        return risorse;
    }

    public void stampaDebug() {
        switch (getErrorCode()) {
            case 0:
                System.out.println("MALATTIA DEBELLATA");
                System.out.println("Sono morte " + getMorti());
                break;
            case 1:
                System.out.println("LA MALATTIA HA VINTO");
                break;
            case 2:
                System.out.println("NON CI SONO SOLDI ");
                break;
            default:
                break;
        }
    }

    public String toString() {
        String s = "";

        s += "errorCode = " + errorCode + "\n" +
        "morti = " + morti+ "\n" +
        "vivi = " + vivi+ "\n" +
        "risorse = " + risorse+ "\n";

        return s;
    }

}
