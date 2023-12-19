package IHM;

import Jeu.Tableau;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean play_black = true;
        Tableau MonTab = new Tableau();
        int taille = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            String commande_zpasse = scanner.nextLine();
            commande_zpasse = commande_zpasse.trim();
            int numero_donné = 0;
            String instruction_donnee = "";
            String arg [] = new String[0]; //Plus tard, on peut faire un tableau d'argument
            boolean commande_valide = false;
            String commande_passe[];
            if(commande_zpasse.length() != 0){
                commande_passe = commande_zpasse.split(" ");
                if(!estNombre(commande_passe[0])){
                    commande_valide = true;
                    instruction_donnee = commande_passe[0];
                    arg = new String [commande_passe.length-1];
                }
                else {

                    if (commande_passe.length > 1) {
                        instruction_donnee = commande_passe[1];
                        commande_valide = true;
                        numero_donné = Integer.parseInt(commande_passe[0]);
                        arg = new String [commande_passe.length-2];
                    }
                }
                for (int i = 0; i < arg.length ; ++i){
                    if(!estNombre(commande_passe[0]))
                        arg[i] = commande_passe[i+1];
                    else
                        arg[i] = commande_passe[i+2];
                }
            }

            if(commande_valide) {
                instruction_donnee = instruction_donnee.toUpperCase();
                if (instruction_donnee.compareTo("BOARDSIZE") == 0) {
                    if(arg.length == 0){
                        System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" boardsize not an integer");
                    }
                    else if(!(estNombre(arg[0]))){
                        System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" boardsize not an integer");
                    }
                    else
                    {
                        taille = Integer.parseInt(arg[0]);
                        try {
                            MonTab = new Tableau(taille);
                            System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                        }
                        catch(IllegalArgumentException e){
                            System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" unacceptable size");
                        }
                    }

                }
                else if (instruction_donnee.compareTo("CLEAR_BOARD") == 0) {
                    MonTab.ClearTheBoard();
                    System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                }
                else if (instruction_donnee.compareTo("genmove ") == 0) {
                    System.out.println("clear_board");
                }
                else if (instruction_donnee.compareTo("PLAY") == 0) {
                    if(arg.length != 2)
                        System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+ " invalid color or coordinate");
                    else{
                        try{
                            MonTab.play(arg[0],arg[1]);
                            System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));


                        }catch(IllegalArgumentException e){
                            System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" "+e.getLocalizedMessage());
                        }
                    }
                }
                else if(instruction_donnee.compareTo("query_boardsize") == 0){
                    System.out.println(MonTab.query_boardsize());
                }
                else if(instruction_donnee.compareTo("SHOWBOARD") == 0){
                    System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                    System.out.println(MonTab.seDessiner());
                }
                else if(instruction_donnee.compareTo("QUIT") == 0){
                    System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                    return;
                }
                else if(instruction_donnee.compareTo("GENMOVE") == 0){
                    try{
                        String Coord = MonTab.genmove(arg[0]);
                        System.out.println("=" + (numero_donné == 0 ? "" : numero_donné)+" "+Coord);
                    }
                    catch(Exception e){
                        System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" "+e.getLocalizedMessage());

                    }

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
