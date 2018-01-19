import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Etudiants {
    private List<Etudiant> etudiants;
    private boolean charge;

    public Etudiant[] getEtudiants(){
        return etudiants.toArray(new Etudiant[etudiants.size()]);
    }

    public Etudiants(){
        charge = false;
        etudiants = new ArrayList<>();
    }

    public boolean estCharge(){
        return charge;
    }

    public void charger(List<String> lignes){
        String l1 = lignes.get(0);
        lignes.remove(0);
        List<String> noms_cours = Arrays.asList(l1.split(";"));
        for(int j = 0; j < lignes.size(); j++){
            String[] ligne_coupee = lignes.get(j).split(";");
            if(ligne_coupee.length==1&&ligne_coupee[0].equals(""))
                continue;
            Etudiant e = new Etudiant(Integer.parseInt(ligne_coupee[0]));
            for(int i = 1; i < ligne_coupee.length; i++){
                if(ligne_coupee[i].equals("1")){
                    e.inscrireCours(noms_cours.get(i));
                }
            }
            etudiants.add(e);
        }
        charge=true;
    }

    public void ajouterEtudiant(Etudiant e){
        etudiants.add(e);
    }

    public int nbEtudiantsInscrits(String c){
        int compteur = 0;
        for(Etudiant e : etudiants){
            if(e.estInscrit(c))
                compteur++;
        }
        return compteur;
    }
}
