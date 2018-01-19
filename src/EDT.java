import java.util.*;

/**
 * Classe qui permet de gérer l'emploi du temps
 */
public class EDT {
    private Map<Cours, List<Creneau>> creneaux;
    private Map<String, Integer> nb_tds;
    private Map<String, Cours> liste_cours;
    private boolean charge;

    /**
     * Constructeur par défaut
     */
    public EDT() {
        creneaux = new HashMap<>();
        nb_tds = new HashMap<>();
        liste_cours = new HashMap<>();
        charge = false;
    }

    /**
     * Retourne la liste des creneaux par cours
     * @return la liste des creneaux par cours
     */
    public Map<Cours, List<Creneau>> getCreneaux(){
        return creneaux;
    }

    /**
     * Renvoie le dictionnaire des noms des cours et des cours associés
     *
     * @return le dictionnaire des noms des cours et des cours associés
     */
    public Map<String, Cours> getListeCours() {
        return liste_cours;
    }

    /**
     * Verifie si l'emploi du temps a été chargé
     *
     * @return true si il a été chargé, false sinon
     */
    public boolean estCharge() {
        return charge;
    }

    /**
     * Charge l'emploi du temps et rempli les differentes listes et dictionnaires qu'on utilise après
     *
     * @param lignes La liste des lignes (String) du fichier de l'emploi du temps
     */
    public void charger(List<String> lignes) {
        //on va eviter de compter les matieres en double, on aurait pu utiliser un Set pour le meme resultat
        List<String> doubles_compteur = new ArrayList<>();
        //on garde la premiere ligne de coté pour les jours
        String l1 = lignes.get(0);
        lignes.remove(0);
        String[] ligne1_coupee = l1.split(";");
        List<String> jours = new ArrayList<>();
        for (int i = 0; i < ligne1_coupee.length; i++) {
            jours.add(ligne1_coupee[i]);
        }
        //pour chaque ligne
        for (int j = 0; j < lignes.size(); j++) {
            //on découpe la ligne par ';' et si la ligne est vide, on passe à la suivante
            String[] ligne_coupee = lignes.get(j).split(";");
            if (ligne_coupee.length == 1 && ligne_coupee[0].equals("")) {
                continue;
            }
            for (int i = 0; i < ligne_coupee.length; i++) {
                //on coupe notre case en " " pour séparer les cours sur le meme creneau
                String[] cours = ligne_coupee[i].split(" ");
                Creneau cren = new Creneau(jours.get(i), j);
                for (String c : cours) {
                    Cours cou;
                    String[] tab = c.split("_");
                    //pas de cours on passe
                    if (tab.length == 1 && tab[0].equals("")) {
                        continue;
                    }
                    //On va ajouter un TD ou CM suivant le nom, et on va ajouter dans les bonnes structures de données
                    if (!doubles_compteur.contains(c)) {
                        if (tab.length == 1)
                            cou = new CoursMagistral(tab[0]);
                        else {
                            cou = new CoursTD(tab[0], Integer.parseInt(tab[1]));
                            if (nb_tds.containsKey(tab[0])) {
                                nb_tds.put(tab[0], (nb_tds.get(tab[0]) + 1));
                            } else {
                                nb_tds.put(tab[0], 1);
                            }
                        }
                        List<Creneau> l = new ArrayList<>();
                        l.add(cren);
                        creneaux.put(cou, l);
                        doubles_compteur.add(c);
                        liste_cours.put(c, cou);
                    } else {
                        cou = liste_cours.get(c);
                        creneaux.get(cou).add(cren);
                    }
                }
            }
        }
        //on notifie que l'edt est chargé
        charge = true;
    }

    /**
     * Cette fonction classe les cours(matières) en fonction des priorités de leur affectation
     * @return la liste des cours, classée
     */
    public List<String> classementCoursParPriorite(){
        Map<Cours, Integer> tmp = new HashMap<>();
        //On  va calculer le nombre de TDs de chaque autre matière sur le meme créneau pour chaque cours
        for(Map.Entry<Cours, List<Creneau>> entree : creneaux.entrySet()){
            int compte = 0;
            for(Map.Entry<Cours, List<Creneau>> entree2 : creneaux.entrySet()){
                if(!entree.getKey().getNomCours().equals(entree2.getKey().getNomCours())){
                    for(Creneau a : entree.getValue()){
                        for(Creneau b : entree2.getValue()){
                            if(a.equals(b)){
                                compte++;
                            }
                        }
                    }
                }
            }
            tmp.put(entree.getKey(), compte);
        }
        // On regroupe les cours par matière
        Map<String, Integer> tmp2 = new HashMap<>();
        for(Map.Entry<Cours, Integer> entree : tmp.entrySet()){
            if(tmp2.containsKey(entree.getKey().getNomCours())){
                tmp2.put(entree.getKey().getNomCours(),
                        tmp2.get(entree.getKey().getNomCours())+entree.getValue());
            } else {
                tmp2.put(entree.getKey().getNomCours(), entree.getValue());
            }
        }
        //On transforme la map en liste pour la classer
        List<Map.Entry<String, Integer>> llist = new LinkedList<Map.Entry<String, Integer>>(tmp2.entrySet());
        Collections.sort(llist, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //Si y'a pas de TD alors c'est pas important
                int classement_td = 5;
                //si il y a des tds on classe en fonction du nombre de tds
                if(nb_tds.containsKey(o1.getKey())&&nb_tds.containsKey(o2.getKey())){
                    classement_td = (nb_tds.get(o1.getKey()).compareTo(nb_tds.get(o2.getKey())));
                }
                //et on soustrait le classement en fonction du degré de conflit
                return classement_td-(o1.getValue().compareTo(o2.getValue()));
            }
        });
        //on transforme ça en liste, pas besoin de garder les valeurs
        List<String> sortie = new ArrayList<>();
        for(Map.Entry<String, Integer> entree : llist){
            sortie.add(entree.getKey());
        }
        return sortie;
    }

    /**
     * Calcule le degré de conflit d'un cours
     * @param co le cours
     * @return Le degré de conflit du cours
     */
    public Integer degreConflitCours(Cours co){
        int compte = 0;
        //On  va calculer le nombre de TDs d'une autre matière sur le meme créneau
        for(Map.Entry<Cours, List<Creneau>> entree2 : creneaux.entrySet()){
            if(!co.getNomCours().equals(entree2.getKey().getNomCours())){
                for(Creneau a : creneaux.get(co)){
                    for(Creneau b : entree2.getValue()){
                        if(a.equals(b)){
                            compte++;
                        }
                    }
                }
            }
        }
        return compte;
    }

    /**
     * Renvoie le dictionnaire des noms des cours et le nombre de tds associés
     *
     * @return le dictionnaire des noms des cours et le nombre de tds associés
     */
    public Map<String, Integer> getNbTds() {
        return nb_tds;
    }
}
