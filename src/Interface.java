import java.util.Scanner;

/**
 * Classe responsable de l'interface avec l'utilisateur : V de MVC
 */
public class Interface {

    private final String EDU_DEFAUT = "etu.csv";
    private final String EDT_DEFAUT = "edt.csv";
    Scanner sc;
    Systeme sys;

    /**
     * constructeur de l'interface
     */
    Interface() {
        sc = new Scanner(System.in);
        sys = new Systeme();
    }

    /**
     * Main
     *
     * @param args inutilisé
     */
    public static void main(String[] args) {
        System.out.println("Système de création de TDs\n");
        System.out.println("Par défaut, les groupes de TDs sont de : " + CoursTD.MAX_ETUD);
        Interface p = new Interface();
        p.boucle();
    }

    /**
     * boucle centrale de l'interface
     */
    public void boucle() {
        int entree = 0;
        do {

            System.out.println("Choisissez\n" +
                    "0. Quitter le programme\n" +
                    "1. Charger des fichiers d'entrée par défaut\n" +
                    "2. Charger des fichiers d'entrée spécifiques\n" +
                    "3. Affecter les étudiants à des groupes\n" +
                    "4. Changer le nombre maximal d'étudiants par groupe\n" +
                    "5. Afficher le nombre minimal d'étudiants par TD nécesssaire\n"+
                    "Entrez le numéro de l'opération que vous souhaitez exécuter :");
            entree = sc.nextInt();
            switch (entree) {
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
                case 5:
                    afficherMinEtudNeces();
                    break;
                default:
                    System.out.println("Entrée non reconnue ...\n Veuillez réessayer");
                    break;
            }
        } while (true);
    }

    /**
     * Affiche le nombre minimum d'étudiants nécessaire pour que tout le monde soit affecté
     */
    private void afficherMinEtudNeces() {
        System.out.println("Nombre d'étudiants minimum par groupe de TD nécessaire : "+sys.nombreEtudMin());
    }

    /**
     * Charge les fichiers d'entrée par défaut
     */
    public void chargerEntreeDefaut() {
        sys.chargerFichiers(EDU_DEFAUT, EDT_DEFAUT);
    }

    /**
     * Charge les fichiers d'entrée spécifiques
     */
    public void chargerEntreeSpecifique() {
        System.out.println("Entrer le chemin vers la liste des étudiants (.csv)");
        String edu = sc.next();
        System.out.println("Entrer le chemin vers l'emploi du temps (.csv)");
        String edt = sc.next();
        sys.chargerFichiers(edu, edt);
    }

    /**
     * Verifie si on peut affecter tous les étudiants, et si oui les affecte et exporte le résultat dans un fichier
     */
    public void affecterEtudiants() {
        if (!sys.estCharge()) {
            System.out.println("Il faut charger des entrées d'abord..;");
            return;
        }
        if(sys.affecter())
            sys.exporterAffectations();
    }

    /**
     * Fonction qui demande un entier positif et qui va actualiser le nombre max d'étudiants par TDs
     */
    public void changerMaxEtud() {
        int a = -1;
        do {
            System.out.println("Entrer le nouveau maximum d'étudiants");
            a = sc.nextInt();
        } while (a < 1);
        sys.changerMaxEtud(a);
    }
}
