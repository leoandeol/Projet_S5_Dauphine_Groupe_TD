import java.util.Scanner;

public class Interface {

    Scanner sc;
    Systeme sys;

    private final String EDU_DEFAUT = "etu.csv";
    private final String EDT_DEFAUT = "edt.csv";


    Interface(){
        sc = new Scanner(System.in);
        sys = new Systeme();
    }

    public void boucle(){
        int entree = 0;
        do{

            System.out.println("Choisissez\n" +
                    "0. Quitter le programme\n" +
                    "1. Charger des fichiers d'entrée par défaut\n" +
                    "2. Charger des fichiers d'entrée spécifiques\n" +
                    "3. Affecter les étudiants à des groupes\n" +
                    "4. Changer le nombre maximal d'étudiants par groupe\n" +
                    "Entrez le numéro de l'opération que vous souhaitez exécuter :");
            entree = sc.nextInt();
            switch(entree){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    chargerEntreeDefaut();
                    break;
                case 2:
                    chargerEntreeSpecifique();
                    break;
                case 3:
                    affecterEtudiants();
                    break;
                case 4:
                    changerMaxEtud();
                    break;
                default:
                    System.out.println("Entrée non reconnue ...\n Veuillez réessayer");
                    break;
            }
        }while(true);
    }

    public void chargerEntreeDefaut(){
        sys.chargerFichiers(EDU_DEFAUT, EDT_DEFAUT);
    }

    public void chargerEntreeSpecifique(){
        System.out.println("Entrer le chemin vers la liste des étudiants (.csv)");
        String edu = sc.next();
        System.out.println("Entrer le chemin vers l'emploi du temps (.csv)");
        String edt = sc.next();
        sys.chargerFichiers(edu, edt);
    }

    public void affecterEtudiants(){
        if(!sys.estCharge()){
            System.out.println("Il faut charger des entrées d'abord..;");
            return;
        }
        sys.affecter();
        sys.exporterAffectations();
    }

    public void changerMaxEtud(){
        System.out.println("Entrer le nouveau maximum d'étudiants");
        int a = sc.nextInt();
        sys.changerMaxEtud(a);
    }


    public static void main(String[] args){
        System.out.println("Système de création de TDs\n");
        Interface p = new Interface();
        p.boucle();
    }
}
