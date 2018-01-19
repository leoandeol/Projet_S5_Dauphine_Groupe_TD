import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systeme {

    private Etudiants etudiants;
    private EDT edt;

    public Systeme(){
        etudiants = new Etudiants();
        edt = new EDT();
    }

    public boolean estCharge(){
        return etudiants.estCharge()&&edt.estCharge();
    }

    public void chargerFichiers(String pathEdu, String pathEdt) {
        try {
            List<String> lines_edu = Files.readAllLines(Paths.get(pathEdu));
            List<String> lines_edt = Files.readAllLines(Paths.get(pathEdt));
            etudiants.charger(lines_edu);
            edt.charger(lines_edt);
            System.out.println("Les deux fichiers ont été chargés avec succès");
        } catch(IOException e){
            System.out.println("Un des fichiers n'a pas été trouvé ou n'est pas lisible");
        }
    }

    public void affecter(){
        System.out.println("Tentative d'affectation avec "+CoursTD.MAX_ETUD+" étudiants au maximum par groupe de TD");
        if(!verifierPlaceTD())
            System.out.println("Il n'y a pas assez de place dans les groupes pour placer tous les étudiants");
        Map<String, Cours> cours = edt.getListeCours();
        /*for(Etudiant etud : etudiants.getEtudiants()){
            for(Map.Entry<String, Cours> entree : cours.entrySet()){
                if(entree.getValue().getTypeCours()==Cours.TypeCours.COURS_MAGISTRAL&&etud.estInscrit(entree.getKey())){
                    etud.affecterCours(entree.getValue());
                    entree.getValue().ajouterEtudiant(etud);
                }
                else if(entree.getValue().getTypeCours()==Cours.TypeCours.COURS_TD) {
                    for (int i = 0; i < edt.getNbTds().get(entree.getKey()); i++) {
                        if((entree.getKey()+"_"+i))
                    }
                }
            }
        }*/
        for(Etudiant etud : etudiants.getEtudiants()){
            for(String cours_inscrit : etud.getCoursInscrit()){
                if(cours.containsKey(cours_inscrit)){
                    cours.get(cours_inscrit).ajouterEtudiant(etud);
                    etud.affecterCours(cours.get(cours_inscrit));
                }
                //todo tester horaires si deux tds en meme temps
                if(edt.getNbTds().containsKey(cours_inscrit)) {
                    for (int i = 1; i <= edt.getNbTds().get(cours_inscrit); i++) {
                        if (!cours.get((cours_inscrit + "_" + i)).complet()) {
                            cours.get((cours_inscrit + "_" + i)).ajouterEtudiant(etud);
                            etud.affecterCours(cours.get(cours_inscrit+"_"+i));
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Affectation terminée");
    }

    public void exporterAffectations(){
        StringBuilder str = new StringBuilder(1024);
        str.append("id;");
        String[] cours = edt.getListeCours().keySet().toArray(new String[edt.getListeCours().keySet().size()]);
        for(int i = 0; i < cours.length; i++){
            str.append(cours[i]);
            if(i==(cours.length-1))
                str.append("\n");
            else
                str.append(";");
        }
        for(int i = 0; i < etudiants.getEtudiants().length; i++){
            str.append(etudiants.getEtudiants()[i].getId()+";");
            for(int j = 0; j < cours.length; j++){
                if(etudiants.getEtudiants()[i].estAffecte(edt.getListeCours().get(cours[j])))
                    str.append("1");
                else
                    str.append("0");
                if(j==(cours.length-1))
                    str.append("\n");
                else
                    str.append(";");
            }
        }
        String fin = str.toString();
        try {
            Files.write(Paths.get("./export.csv"), fin.getBytes());
            System.out.println("Affectations exportées sous le nom : 'export.csv'");
        } catch (IOException e){
            System.out.println("Il y a eu une erreur lors de l'écriture du fichier de sortie");
        }
    }

    public void changerMaxEtud(int maxEtud){
        CoursTD.MAX_ETUD = maxEtud;
    }

    private boolean verifierPlaceTD(){
        Map<String, Integer> nbtds = edt.getNbTds();
        for(Map.Entry<String, Integer> entree : nbtds.entrySet()){
            System.out.println(entree.getKey()+": "+etudiants.nbEtudiantsInscrits(entree.getKey())+" inscrits et "+entree.getValue()+" places");
            if((entree.getValue()*CoursTD.MAX_ETUD)<etudiants.nbEtudiantsInscrits(entree.getKey())){
                return false;
            }
        }
        return true;
    }

}
