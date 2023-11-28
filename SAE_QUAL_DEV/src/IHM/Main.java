package IHM;

import Jeu.Tableau;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Tableau MonTab = new Tableau();

        int taille = 0;
        Scanner scanner = new Scanner(System.in);

        do {
            String commande_zpasse = scanner.nextLine();
            commande_zpasse = commande_zpasse.trim();
            int numero_donné = 0;
            String instruction_donnee = "";
            String arg = ""; //Plus tard, on peut faire un tableau d'argument
            boolean commande_valide = false;
            String commande_passe[];
            if(commande_zpasse.length() != 0){
                commande_passe = commande_zpasse.split(" ");
                if(!estNombre(commande_passe[0])){
                    commande_valide = true;
                    instruction_donnee = commande_passe[0];
                    if (commande_passe.length>1)
                        arg = commande_passe[1];

                }
                else {
                    numero_donné = Integer.parseInt(commande_passe[0]);
                    if (commande_passe.length > 1) {
                        instruction_donnee = commande_passe[1];
                        if (commande_passe.length>2)
                            arg = commande_passe[2];
                        commande_valide = true;
                    }
                }
            }
            if(commande_valide) {
                if (instruction_donnee.compareTo("boardsize") == 0) {
                    if(arg.compareTo("") != 0 && estNombre(arg)){
                        taille = Integer.parseInt(arg);
                        try {
                            MonTab = new Tableau(taille);
                            System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                        }
                        catch(IllegalArgumentException e){
                            System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" unacceptable size");
                        }
                    }
                    else {
                        System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" boardsize not an integer");
                    }
                }
                else if (instruction_donnee.compareTo("clear_board") == 0) {
                    MonTab.ClearTheBoard();
                    System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                }
                else if (instruction_donnee.compareTo("genmove ") == 0) {
                    System.out.println("clear_board");
                }
                else if (instruction_donnee.compareTo("play") == 0) {
                    System.out.println("clear_board");

                }
                else if(instruction_donnee.compareTo("query_boardsize") == 0){
                    System.out.println(MonTab.query_boardsize());
                }
                else if(instruction_donnee.compareTo("showboard") == 0){
                    System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                    System.out.println(MonTab.seDessiner());
                }
                else if(instruction_donnee.compareTo("quit") == 0){
                    System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                    return;
                }
                else{
                    System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" unknown command");
                }
            }
        } while (true);
    }


    private static boolean estNombre(String chaine) {
        try {
            // Tenter de convertir la chaîne en int
            Integer.parseInt(chaine);
            return true; // Si la conversion réussit, alors la chaîne est un nombre
        } catch (NumberFormatException e) {
            return false; // Si la conversion échoue, la chaîne n'est pas un nombre
        }
    }

}
