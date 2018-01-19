/**
 * Classe pour les TD, hérité de COurs
 */
public class CoursTD extends Cours {

    /**
     * Nombre maximum d'étudiants par TD
     */
    public static int MAX_ETUD = 40;

    private int numtd;

    /**
     * Constructeur du CoursTD
     * @param nom le nom du TD
     * @param numtd le numéro du TD
     */
    public CoursTD(String nom, int numtd) {
        super(nom, TypeCours.COURS_TD);
        this.numtd = numtd;
    }

    /**
     * Verifier si le cours est complet
     * @return true si il est complet, sinon false
     */
    @Override
    public boolean complet() {
        return this.etudiantsInscrits().length >= MAX_ETUD;
    }

    /**
     * Inscrit un étudiant dans ce cours
     * @param e l'étudiant à inscrire
     * @return true si l'inscription a réussi, sinon false
     */
    @Override
    public boolean inscrire(Etudiant e) {
        if (complet())
            return false;
        ajouterEtudiant(e);
        return true;
    }
}
