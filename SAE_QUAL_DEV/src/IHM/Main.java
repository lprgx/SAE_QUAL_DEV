package IHM;

import Jeu.Tableau;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        int numero_instruction = 1; //  Numéro de l'instruction
        String instruction_donnee = "";
        Tableau MonTab = null;
        int numero_donné = 0;
        int taille = 0;
        String arg = ""; //Plus tard, on peut faire un tableau d'argument
        Scanner scanner = new Scanner(System.in);

        do {
            boolean commande_valide = false;

            String commande_passe[];
            commande_passe = scanner.nextLine().split(" ");
            if(!estNombre(commande_passe[0])){

                commande_valide = true;
                instruction_donnee = commande_passe[0];
                if (commande_passe.length>1)
                    arg = commande_passe[1];
            }
            else if( Integer.parseInt(commande_passe[0])  != numero_instruction){
                System.out.println("Numéro d'instruction incorrect");
            }
            else {
                numero_donné = Integer.parseInt(commande_passe[0]);
                if (commande_passe.length > 1) {
                    instruction_donnee = commande_passe[1];
                    if (commande_passe.length>2)
                        arg = commande_passe[2];
                    commande_valide = true;
                } else {
                    System.out.println("Absence d'instruction");
                }
            }
            if(commande_valide) {
                if (instruction_donnee.compareTo("boardsize") == 0) {
                    if(arg.compareTo("") != 0 ){
                        if(estNombre(arg)){
                            taille = Integer.parseInt(arg);
                            try {
                                MonTab = new Tableau(taille);
                                System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                                numero_instruction++;//Incrémente pour passer au numéro de l'instruction suivante
                            }
                            catch(IllegalArgumentException e){
                                System.out.println("taille incorrecte");
                            }
                        }
                        else {
                            System.out.println("Il faut donner une taille au tableau");
                        }
                    }
                    else {
                        System.out.println("Il faut donner une taille au tableau");
                    }
                }
                else if (instruction_donnee.compareTo("clear_board") == 0) {
                    if(numero_instruction>1){
                        MonTab.ClearTheBoard();
                        System.out.println("=" + numero_instruction);
                        numero_instruction++;//Incrémente pour passer au numéro de l'instruction suivante
                    }
                    else{
                        System.out.println(" Vous n'avez pas créer votre tableau à l'aide de la commande boardsize");
                    }

                }
                else if (instruction_donnee.compareTo("genmove ") == 0) {
                    if(numero_instruction>1){
                        System.out.println("clear_board");
                        numero_instruction++;//Incrémente pour passer au numéro de l'instruction suivante
                    }
                    else{
                        System.out.println(" Vous n'avez pas créer votre tableau à l'aide de la commande boardsize");
                    }

                }
                else if (instruction_donnee.compareTo("play") == 0) {
                    if(numero_instruction>1) {
                        System.out.println("clear_board");
                        numero_instruction++;//Incrémente pour passer au numéro de l'instruction suivante
                    }
                    else{
                        System.out.println(" Vous n'avez pas créer votre tableau à l'aide de la commande boardsize");
                    }
                }
                else if(instruction_donnee.compareTo("query_boardsize") == 0){
                    if(numero_instruction>1)
                        System.out.println(MonTab.query_boardsize());
                    else
                        System.out.println(" Vous n'avez pas créer votre tableau à l'aide de la commande boardsize");
                }
                else if(instruction_donnee.compareTo("showboard") == 0){
                    if(numero_instruction>1){
                        System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                        System.out.println(MonTab.seDessiner());
                        numero_instruction++;
                    }
                    else
                        System.out.println(" Vous n'avez pas créer votre tableau à l'aide de la commande boardsize");
                }
                else{
                    System.out.println("Instruction inconnue");

                }
            }




        } while (instruction_donnee.compareTo("quit") != 0);
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
