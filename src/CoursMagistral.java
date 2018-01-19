public class CoursMagistral extends Cours {

    public CoursMagistral(String nom){
        super(nom,TypeCours.COURS_MAGISTRAL);
    }

    @Override
    public boolean complet() {
        return false;
    }

    @Override
    public boolean inscrire(Etudiant e) {
        ajouterEtudiant(e);
        return true;
    }
}
