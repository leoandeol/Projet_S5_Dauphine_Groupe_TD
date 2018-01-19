import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet de gérer l'emploi du temps
 */
public class EDT {
    private Map<String, Creneau[]> creneaux;
    private Map<Integer, String> cles;
    private Map<String, Integer> nb_tds;
    private Map<String, Cours> liste_cours;
    private boolean charge;

    /**
     * Constructeur par défaut
     */
    public EDT() {
        creneaux = new HashMap<>();
        cles = new HashMap<>();
        nb_tds = new HashMap<>();
        liste_cours = new HashMap<>();
        charge = false;
    }

    /**
     * Renvoie le dictionnaire des noms des cours et des cours associés
     * @return le dictionnaire des noms des cours et des cours associés
     */
    public Map<String, Cours> getListeCours() {
        return liste_cours;
    }

    /**
     * Verifie si l'emploi du temps a été chargé
     * @return true si il a été chargé, false sinon
     */
    public boolean estCharge() {
        return charge;
    }

    /**
     * Charge l'emploi du temps
     * @param lignes La liste des lignes (String) du fichier de l'emploi du temps
     */
    public void charger(List<String> lignes) {
        List<String> doubles_compteur = new ArrayList<>();
        String l1 = lignes.get(0);
        lignes.remove(0);
        String[] ligne1_coupee = l1.split(";");
        for (int i = 0; i < ligne1_coupee.length; i++) {
            creneaux.put(ligne1_coupee[i], new Creneau[lignes.size() - 1]);
            cles.put(i, ligne1_coupee[i]);
        }
        for (int j = 0; j < lignes.size(); j++) {
            String[] ligne_coupee = lignes.get(j).split(";");
            if (ligne_coupee.length == 1 && ligne_coupee[0].equals("")){
                continue;
            }
            for (int i = 0; i < ligne_coupee.length; i++) {
                String[] cours = ligne_coupee[i].split(" ");
                Creneau cren = new Creneau();
                for (String c : cours) {
                    Cours cou;
                    String[] tab = c.split("_");
                    if (tab.length == 1 && tab[0].equals("")) {
                        creneaux.get(cles.get(i))[j] = cren;
                        continue;
                    }
                    if (tab.length == 1) {
                        cou = new CoursMagistral(tab[0]);
                        liste_cours.put(c, cou);
                    } else {
                        if (!doubles_compteur.contains(c)) {
                            cou = new CoursTD(tab[0], Integer.parseInt(tab[1]));
                            if (nb_tds.containsKey(tab[0])) {
                                nb_tds.put(tab[0], (Integer) (nb_tds.get(tab[0]) + 1));
                            } else {
                                nb_tds.put(tab[0], 1);
                            }
                            doubles_compteur.add(c);
                            liste_cours.put(c, cou);
                        } else {
                            cou = liste_cours.get(c);
                        }
                    }
                    cren.ajouterCours(cou);
                }
                creneaux.get(cles.get(i))[j] = cren;
            }
        }
        charge = true;
    }

    /**
     * Renvoie le dictionnaire des noms des cours et le nombre de tds associés
     * @return le dictionnaire des noms des cours et le nombre de tds associés
     */
    public Map<String, Integer> getNbTds() {
        return nb_tds;
    }
}
