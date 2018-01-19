import java.util.ArrayList;
import java.util.List;

public class Creneau {
    private List<Cours> cours;

    public Creneau(){
        cours = new ArrayList<Cours>();
    }

    public Cours[] getCours(){
        return (Cours[])this.cours.toArray();
    }

    public void ajouterCours(Cours c){
        cours.add(c);
    }
}
