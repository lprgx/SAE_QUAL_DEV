import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String cde;
        do {
            Scanner scanner = new Scanner(System.in);
            cde = scanner.nextLine();

            if (cde.compareTo("formation") == 0) {
                System.out.println("La première chaîne est inférieure à la deuxième.");

            } else if (cde.compareTo("formation") == 0) {
                System.out.println("La première chaîne est inférieure à la deuxième.");
            }
        } while (cde.compareTo("quit") != 0);
    }

}
