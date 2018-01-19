import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe qui regroupe les étudiants et permet de les gerer globallement
 */
public class Etudiants {
    private List<Etudiant> etudiants;
    private boolean charge;

    /**
     * Constructeur par défaut
     */
    public Etudiants() {
        charge = false;
        etudiants = new ArrayList<>();
    }

    /**
     * Renvoie la liste des étudiants
     * @return la liste des étudiants
     */
    public Etudiant[] getEtudiants() {
        return etudiants.toArray(new Etudiant[etudiants.size()]);
    }

    /**
     * Vérifie si la liste des étudiants à été chargée
     * @return true si la liste est chargée, false sinon
     */
    public boolean estCharge() {
        return charge;
    }

    /**
     * Charge la liste des étudiants
     * @param lignes Une liste de lignes (String) qui correspond au contenu du fichier etudiants
     */
    public void charger(List<String> lignes) {
        //on eloigne la premiere ligne, que l'on garde de coté
        String l1 = lignes.get(0);
        lignes.remove(0);
        List<String> noms_cours = Arrays.asList(l1.split(";"));
        for (int j = 0; j < lignes.size(); j++) {
            //pour chaque ligne, on la coupe en ; et si elle est vide on passe
            String[] ligne_coupee = lignes.get(j).split(";");
            if (ligne_coupee.length == 1 && ligne_coupee[0].equals(""))
                continue;
            // on cree un etudiant et on lui assigne tous les cours ou y'a un "1" sur sa ligne
            Etudiant e = new Etudiant(Integer.parseInt(ligne_coupee[0]));
            for (int i = 1; i < ligne_coupee.length; i++) {
                if (ligne_coupee[i].equals("1")) {
                    e.inscrireCours(noms_cours.get(i));
                }
            }
            //on l'ajoute à la liste
            etudiants.add(e);
        }
        //on notifie le chargement
        charge = true;
    }

    /**
     * Permet d'ajouter un étudiant manuellement
     * @param e l'étudiant à ajouter
     */
    public void ajouterEtudiant(Etudiant e) {
        etudiants.add(e);
    }

    /**
     * Renvoie le nombre d'étudiants inscrits à un certain cours
     * @param c le nom du cours
     * @return le nombre d'étudiants inscrits
     */
    public int nbEtudiantsInscrits(String c) {
        int compteur = 0;
        for (Etudiant e : etudiants) {
            if (e.estInscrit(c))
                compteur++;
        }
        return compteur;
    }
}
