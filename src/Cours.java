import java.util.ArrayList;
import java.util.List;

public abstract class Cours {
    public enum TypeCours{
        COURS_MAGISTRAL,
        COURS_TD
    }

    private String nomCours;
    private TypeCours typeCours;
    private List<Etudiant> inscrits;

    public Cours(String nom, TypeCours t){
        this.nomCours = nom;
        this.typeCours = t;
        this.inscrits = new ArrayList<>();
    }

    public abstract boolean complet();
    public abstract boolean inscrire(Etudiant e);
    public Etudiant[] etudiantsInscrits(){return inscrits.toArray(new Etudiant[inscrits.size()]);}
    protected void ajouterEtudiant(Etudiant e){inscrits.add(e);}

    public TypeCours getTypeCours() {
        return typeCours;
    }
}
