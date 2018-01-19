import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de représenter un bloc horaire dans l'emploi du temps
 */
public class Creneau {
    private String jour;
    private int ordre; //numero du creneau dans ce jour

    /**
     * Constructeur
     */
    public Creneau(String jour, int ordre) {
        this.jour=jour;
        this.ordre=ordre;
    }

    /**
     * Retourne le jour de ce creneau
     * @return le jour du creneau
     */
    public String getJour(){
        return jour;
    }

    /**
     * Retourne le numero du creneau dans la journée
     * @return le numero du creneau dans la journée
     */
    public int getOrdre(){
        return ordre;
    }

    /**
     * Permet de comparer deux Creneaux
     * @param o un autre creneau
     * @return true si les creneaux sont identiques, sinon false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Creneau creneau = (Creneau) o;

        if (ordre != creneau.ordre) return false;
        return jour.equals(creneau.jour);
    }
}
