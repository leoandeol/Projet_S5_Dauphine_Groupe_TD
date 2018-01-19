import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe contenant les informations sur un étudiant
 */
public class Etudiant {
    private int id;
    private List<String> cours_inscrit;
    private List<Cours> cours_affecte;

    /**
     * Constructeur d'Etudiant
     * @param id ID de l'étudiant
     */
    public Etudiant(int id) {
        this.id = id;
        cours_inscrit = new ArrayList<String>();
        cours_affecte = new ArrayList<Cours>();
    }

    /**
     * Getter sur l'ID
     * @return l'ID de l'étudiant
     */
    public int getId() {
        return id;
    }

    /**
     * Note qu'un élève est inscrit à un cours
     * @param s Nom du cours
     */
    public void inscrireCours(String s) {
        cours_inscrit.add(s);
    }

    /**
     * Affecte l'étudiant à un Cours (CM ou TD) donc un groupe
     * @param c le cours
     */
    public void affecterCours(Cours c) {
        cours_affecte.add(c);
    }


    /**
     * Renvoie la liste des noms de cours où l'étudiant est inscrit
     * @return la liste des noms de cours
     */
    public String[] getCoursInscrit() {
        return cours_inscrit.toArray(new String[cours_inscrit.size()]);
    }

    /**
     * Renvoie la liste des cours où l'étudiant est affecté
     * @return la liste des cours
     */
    public Cours[] getCoursAffecte() {
        return cours_affecte.toArray(new Cours[cours_affecte.size()]);
    }

    /**
     * Verifie si l'étudiant est inscrit à un cours
     * @param c le cours
     * @return true si il est inscrit, false sinon
     */
    public boolean estInscrit(String c) {
        return cours_inscrit.contains(c);
    }

    /**
     * Verifie si l'étudiant est affecté à un cours
     * @param c le cours
     * @return true si il est affecté, false sinon
     */
    public boolean estAffecte(Cours c) {
        return cours_affecte.contains(c);
    }

    /**
     * Verifie si un nouveau cours rentre en conflit avec les actuels au niveau de l'edt
     * @param edt l'emploi du temps
     * @param cours le nouveau cours
     * @return true si il y a conflit, false sinon
     */
    public boolean conflit(EDT edt, Cours cours) {
        for(Cours c_a : cours_affecte){

        }
        return false;
    }
}
