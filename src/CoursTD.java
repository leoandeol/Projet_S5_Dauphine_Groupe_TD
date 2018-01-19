public class CoursTD extends Cours {

    public static int MAX_ETUD = 40;

    private int numtd;

    public CoursTD(String nom, int numtd){
        super(nom,TypeCours.COURS_TD);
        this.numtd = numtd;
    }

    @Override
    public boolean complet() {
        return this.etudiantsInscrits().length>=MAX_ETUD;
    }

    @Override
    public boolean inscrire(Etudiant e) {
        if(complet())
            return false;
        ajouterEtudiant(e);
        return true;
    }
}
