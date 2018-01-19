import java.util.ArrayList;
import java.util.List;

public class Etudiant {
    public int getId() {
        return id;
    }

    private int id;

    private List<String> cours_inscrit;
    private List<Cours> cours_affecte;

    public Etudiant (int id){
        this.id = id;
        cours_inscrit = new ArrayList<String>();
        cours_affecte = new ArrayList<Cours>();
    }

    public void inscrireCours(String s){ cours_inscrit.add(s); }
    public void affecterCours(Cours c){
        cours_affecte.add(c);
    }


    public String[] getCoursInscrit(){
        return cours_inscrit.toArray(new String[cours_inscrit.size()]);
    }
    public Cours[] getCoursAffecte(){
        return cours_affecte.toArray(new Cours[cours_affecte.size()]);
    }

    public boolean estInscrit(String c){
        return cours_inscrit.contains(c);
    }

    public boolean estAffecte(Cours c){
        return cours_affecte.contains(c);
    }
}
