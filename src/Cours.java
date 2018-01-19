import java.util.ArrayList;
import java.util.List;

public abstract class Cours {
    private String nomCours;
    private TypeCours typeCours;
    private List<Etudiant> inscrits;

    /**
     * Constructeur pour "Cours"
     * @param nom Nom du cours
     * @param t Type du cours
     */
    public Cours(String nom, TypeCours t) {
        this.nomCours = nom;
        this.typeCours = t;
        this.inscrits = new ArrayList<>();
    }

    /**
     * Definit le prototype d'une méthode qui verifie si le cours est plein
     * @return true si le cours est plein, false sinon
     */
    public abstract boolean complet();

    /**
     * Definit le prototype d'une fonction qui permet d'inscrire un étudiant au cours
     * @param e l'étudiant à inscrire
     * @return true si l'inscription a pu se faire, false sinon
     */
    public abstract boolean inscrire(Etudiant e);

    /**
     * Permet de recuperer le tableau des étudiants
     * @return le tableau des étudiants
     */
    public Etudiant[] etudiantsInscrits() {
        return inscrits.toArray(new Etudiant[inscrits.size()]);
    }

    protected void ajouterEtudiant(Etudiant e) {
        inscrits.add(e);
    }

    /**
     * Permet de recuperer le type de cours
     * @return le type du cours
     */
    public TypeCours getTypeCours() {
        return typeCours;
    }

    /**
     * Permet de recuperer le nom de cours
     * @return le nom du cours
     */
    public String getNomCours() {
        return nomCours;
    }

    public enum TypeCours {
        COURS_MAGISTRAL,
        COURS_TD
    }
}
