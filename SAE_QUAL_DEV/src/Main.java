import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String cde[];
        Integer i = 1; //  Numéro de l'instruction

        do {
            Scanner scanner = new Scanner(System.in);
             cde = scanner.nextLine().split(" "); // Divise la chaine tapé dans un tableau
            if(cde[0].compareTo(i.toString())==0){      // Vérifie si le numéro d'instruction est bien valide
                i++;                                    //Incrémente pour passer au numéro de l'instruction suivante
                if (cde[1].compareTo("boardsize ") == 0) {
                    System.out.println("boardsize");

                } else if (cde[1].compareTo("clear_board") == 0) {
                    System.out.println("clear_board");
                    
                }else if (cde[1].compareTo("genmove ") == 0) {
                    System.out.println("clear_board");

                }else if (cde[1].compareTo("play") == 0) {
                    System.out.println("clear_board");
                }
            }
            else {
                cde = new String [2]; // Pour éviter que la vérification dans le while fasse une erreur;
                cde[1] = "";          // Pour éviter que la vérification dans le while fasse une erreur;
                System.out.println("probleme");
            }
        } while (cde[1].compareTo("quit") != 0);
    }

}
