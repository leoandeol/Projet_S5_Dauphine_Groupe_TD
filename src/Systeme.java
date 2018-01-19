import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Classe responsable des calculs et de la logique : C de MVC
 */
public class Systeme {

    private Etudiants etudiants;
    private EDT edt;

    /**
     * Constructeur par défaut du système
     */
    public Systeme() {
        etudiants = new Etudiants();
        edt = new EDT();
    }

    /**
     * Verifie si les deux fichiers sont chargés
     *
     * @return true si ils sont tous deuxchargés, false sinon
     */
    public boolean estCharge() {
        return etudiants.estCharge() && edt.estCharge();
    }

    /**
     * Charge les fichiers essentiels au système
     *
     * @param pathEdu le chemin vers le fichier .csv contenant la liste des étudiants et les cours auquel ils sont inscrits
     * @param pathEdt le chemin vers le fichier .csv content l'emploi du temps
     */
    public void chargerFichiers(String pathEdu, String pathEdt) {
        try {
            //on lit toutes les lignes des deux fichiers et on appelle les fonctions de chargement des deux classes respectives
            List<String> lines_edu = Files.readAllLines(Paths.get(pathEdu));
            List<String> lines_edt = Files.readAllLines(Paths.get(pathEdt));
            etudiants.charger(lines_edu);
            edt.charger(lines_edt);
            System.out.println("Les deux fichiers ont été chargés avec succès");
        } catch (IOException e) {
            System.out.println("Un des fichiers n'a pas été trouvé ou n'est pas lisible");
        }
    }

    /**
     * Affecte les étudiants à différents groupes de TD en respectant les capacités et les emplois du temps
     * On affecte en premier les TDs qui sont les moins nombreux
     *
     * @return false si on ne peut pas affecter tout le monde, true si tout se passe bien
     */
    public boolean affecter() {
        //on verifie si y'a assez de place dans tous les TDs
        System.out.println("Tentative d'affectation avec " + CoursTD.MAX_ETUD + " étudiants au maximum par groupe de TD");
        if (!verifierPlaceTD()) {
            System.out.println("Il n'y a pas assez de place dans les groupes pour placer tous les étudiants");
            return false;
        }
        //On recupere la liste des Matieres->Cours et les Matieres classées par priorité
        Map<String, Cours> cours = edt.getListeCours();

        List<String> liste_cours_iteration = edt.classementCoursParPriorite();

        for (Etudiant etud : etudiants.getEtudiants()) {
            //on va vouloir garder l'ordre mais retirer tout les cours qui ne sont pas pris par l'étudiant
            List<String> cours_etudiant_inscrit = Arrays.asList(etud.getCoursInscrit());
            List<String> liste_etudiant = new ArrayList<>();
            for (String s : liste_cours_iteration) {
                if (cours_etudiant_inscrit.contains(s)) {
                    liste_etudiant.add(s);
                }
            }
            //pour chaque cours de l'étudiant
            for (String cours_inscrit : liste_etudiant) {
                //si c'est un CM on l'ajoute
                if (cours.containsKey(cours_inscrit)) {
                    cours.get(cours_inscrit).ajouterEtudiant(etud);
                    etud.affecterCours(cours.get(cours_inscrit));
                }
                //si c'est un TD
                if (edt.getNbTds().containsKey(cours_inscrit)) {
                    //on va vérifier qu'il s'inscrit bien à exactement un td
                    boolean inscrit_un_td = false;
                    //on fait une liste pour choisir quel TD doit etre affecté en priorité
                    List<Cours> tds = new ArrayList<>();
                    for (int i = 1; i <= edt.getNbTds().get(cours_inscrit); i++) {
                        if (!cours.get((cours_inscrit + "_" + i)).complet() && !etud.conflit(edt, cours.get((cours_inscrit + "_" + i)))) {
                            tds.add(cours.get(cours_inscrit + "_" + i));
                        }
                    }
                    //on les classe par priorité décroissante
                    Collections.sort(tds, new Comparator<Cours>() {
                        @Override
                        public int compare(Cours o1, Cours o2) {
                            return -(edt.degreConflitCours(o1).compareTo(edt.degreConflitCours(o2)));
                        }
                    });
                    // on affecte le premier
                    for (Cours c : tds) {
                        c.ajouterEtudiant(etud);
                        etud.affecterCours(c);
                        inscrit_un_td = true;
                        break;
                    }
                    //si on ne peut pas l'inscrire, il y a erreur : conflit
                    if (!inscrit_un_td) {
                        System.out.println("Il y a un conflit d'horaires pour l'étudiant : " + etud.getId() +
                                " sur l'affectation d'un TD du cours : " + cours_inscrit);
                        return false;
                    }
                }
            }
        }
        System.out.println("Affectation terminée");
        return true;
    }

    /**
     * exporte les affectations (groupe de tds) vers un fichier .csv et des commentaires vers un .txt
     */
    public void exporterAffectations() {
        //on construit la premiere ligne avec les noms des CM et TD
        StringBuilder str = new StringBuilder(1024);
        str.append("id;");
        String[] cours = edt.getListeCours().keySet().toArray(new String[edt.getListeCours().keySet().size()]);
        for (int i = 0; i < cours.length; i++) {
            str.append(cours[i]);
            if (i == (cours.length - 1))
                str.append("\n");
            else
                str.append(";");
        }
        //pour chaque etudiant on fait une ligne et on marque à quel CM et TD il participe
        for (int i = 0; i < etudiants.getEtudiants().length; i++) {
            str.append(etudiants.getEtudiants()[i].getId() + ";");
            for (int j = 0; j < cours.length; j++) {
                if (etudiants.getEtudiants()[i].estAffecte(edt.getListeCours().get(cours[j])))
                    str.append("1");
                else
                    str.append("0");
                if (j == (cours.length - 1))
                    str.append("\n");
                else
                    str.append(";");
            }
        }
        String fin = str.toString();
        try {
            //on exporte ces données
            Files.write(Paths.get("./export.csv"), fin.getBytes());
            str = new StringBuilder(1024);
            //On fait un fichier bonus qui indique le remplissage des groupes
            str.append("Nombre d'étudiants par groupe :\n");
            for (Map.Entry<String, Cours> entree : edt.getListeCours().entrySet()) {
                str.append(entree.getKey() + ": " + entree.getValue().etudiantsInscrits().length + (entree.getValue().getTypeCours() == Cours.TypeCours.COURS_TD ? "/40" : "") + " étudiants\n");
            }
            Files.write(Paths.get("./export.txt"), str.toString().getBytes());
            System.out.println("Affectations exportées sous le nom : 'export.csv' et commentaires sous le nom 'export.csv'");
        } catch (IOException e) {
            System.out.println("Il y a eu une erreur lors de l'écriture du fichier de sortie");
        }
    }

    /**
     * Change le nombre maximum d'étudiants par groupe de TD
     *
     * @param maxEtud le nouveau nombre max d'étudiants
     */
    public void changerMaxEtud(int maxEtud) {
        CoursTD.MAX_ETUD = maxEtud;
    }

    /**
     * Verifier si les TDs ont tous assez de place pour pouvoir affecter tous les étudiants
     *
     * @return true si il y a assez de place
     */
    private boolean verifierPlaceTD() {
        Map<String, Integer> nbtds = edt.getNbTds();
        for (Map.Entry<String, Integer> entree : nbtds.entrySet()) {
            if ((entree.getValue() * CoursTD.MAX_ETUD) < etudiants.nbEtudiantsInscrits(entree.getKey())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Renvoie le nombre d'étudiants nécessaire au minimum par TD pour que l'on puisse affecter tout le monde
     *
     * @return Le nombre d'étudiants par TD ou -1 si il y a eu une erreur
     */
    public int nombreEtudMin() {
        int count = 1;
        Map<String, Integer> nbtds = edt.getNbTds();
        //on limite la recherche à 60 étudiants par groupe de TD
        while (count < 60) {
            boolean b = true;
            for (Map.Entry<String, Integer> entree : nbtds.entrySet()) {
                if ((entree.getValue() * count) < etudiants.nbEtudiantsInscrits(entree.getKey())) {
                    b = false;
                }
            }
            if (b)
                return count;
            else
                count++;
        }
        return -1;
    }

}
