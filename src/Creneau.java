import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de savoir combien de cours se déroulent en meme temps
 */
public class Creneau {
    private List<Cours> cours;

    /**
     * Constructeur par défaut
     */
    public Creneau() {
        cours = new ArrayList<Cours>();
    }

    /**
     * Renvoie le tableau des cours
     * @return le tableau des cours
     */
    public Cours[] getCours() {
        return this.cours.toArray(new Cours[this.cours.size()]);
    }

    /**
     * Ajoute un cours à la liste
     * @param c le cours à ajouter
     */
    public void ajouterCours(Cours c) {
        cours.add(c);
    }
}
