/**
 * Classe pour les CM, hérité de cours
 */
public class CoursMagistral extends Cours {

    /**
     * Constructeur de CM
     * @param nom le nom du cours
     */
    public CoursMagistral(String nom) {
        super(nom, TypeCours.COURS_MAGISTRAL);
    }

    /**
     * Verifie si le CM est complet
     * @return toujours false
     */
    @Override
    public boolean complet() {
        return false;
    }

    /**
     * Inscrit un étudiant dans le cours
     * @param e l'étudiant à inscrire
     * @return toujours true
     */
    @Override
    public boolean inscrire(Etudiant e) {
        ajouterEtudiant(e);
        return true;
    }
}
