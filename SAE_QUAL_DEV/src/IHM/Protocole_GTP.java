package IHM;

import Jeu.*;

import java.util.HashMap;
import java.util.Scanner;

import player.ConsolePlayer;
import player.RandomPlayer;

public class Protocole_GTP {


    HashMap<String, IPlayer> Joueurs;

    public Protocole_GTP(String[] args){
        Joueurs = new HashMap<>();

        if(args.length == 0){
            Joueurs.put("BLACK",new ConsolePlayer());
            Joueurs.put("WHITE",new ConsolePlayer());
        }
        else if(args.length == 1){
            if(args[0].toUpperCase().compareTo("BLACK")==0){
                Joueurs.put("BLACK",new RandomPlayer());
                Joueurs.put("WHITE",new ConsolePlayer());
            }
            else if(args[0].toUpperCase().compareTo("WHITE")==0){
                Joueurs.put("WHITE",new RandomPlayer());
                Joueurs.put("BLACK",new ConsolePlayer());
            }
            else{
                Joueurs.put("BLACK",new ConsolePlayer());
                Joueurs.put("WHITE",new ConsolePlayer());
            }
        }
        else{
            if(args[0].toUpperCase().compareTo("BLACK")==0)
                Joueurs.put("BLACK",new RandomPlayer());
            else if(args[1].toUpperCase().compareTo("BLACK")==0)
                Joueurs.put("BLACK",new RandomPlayer());
            else
                Joueurs.put("BLACK",new ConsolePlayer());
            if(args[0].toUpperCase().compareTo("WHITE")==0)
                Joueurs.put("WHITE",new RandomPlayer());
            else if(args[1].toUpperCase().compareTo("WHITE")==0)
                Joueurs.put("WHITE",new RandomPlayer());
            else
                Joueurs.put("WHITE",new ConsolePlayer());
        }
    }

    public void jeu(){

        boolean play_black = true;
        Tableau MonTab = new Tableau();
        int taille = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            if(Joueurs.get("BLACK").getClass().getSimpleName().compareTo("RandomPlayer")==0 && Joueurs.get("WHITE").getClass().getSimpleName().compareTo("RandomPlayer")==0){
               boolean black_passe=false;
               boolean white_passe=false;
                try{
                    MonTab.play(Joueurs.get("BLACK").play(MonTab,"BLACK","00"));

                }catch(Exception e){
                    black_passe=true;
                }
                System.out.println(MonTab.Show_board());
                try{
                    MonTab.play(Joueurs.get("WHITE").play(MonTab,"WHITE","00"));

                }catch(Exception e){
                    white_passe = true;
                }
                System.out.println(MonTab.Show_board());
                if(white_passe && black_passe)
                    return;
            }

            else{
                String commande_zpasse = scanner.nextLine();
                commande_zpasse = commande_zpasse.trim();
                int numero_donné = 0;
                String instruction_donnee = "";
                String arg [] = new String[0];
                boolean commande_valide = false;
                String commande_passe[];
                if(commande_zpasse.length() != 0){
                    commande_passe = commande_zpasse.split(" ");
                    if(!Main.estNombre(commande_passe[0])){
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
                        if(!Main.estNombre(commande_passe[0]))
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
                        else if(!(Main.estNombre(arg[0]))){
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
                    else if(instruction_donnee.compareTo("PLAYER") == 0){
                        if (arg.length!=2)
                            System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" unknown command");
                        else if(arg[0].toUpperCase().compareTo("BLACK")==0){
                            Joueurs.remove("BLACK");
                            Joueurs.put("BLACK",new RandomPlayer());
                        }
                        else{
                            Joueurs.remove("WHITE");
                            Joueurs.put("WHITE",new RandomPlayer());
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
                                MonTab.play(Joueurs.get(arg[0].toUpperCase()).play(MonTab,arg[0].toUpperCase(),arg[1]));
                                System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                                if(Joueurs.get("BLACK").getClass().getSimpleName().compareTo("RandomPlayer")==0 ){
                                    boolean pierreposée = false;
                                    do{
                                        try{
                                            MonTab.play(Joueurs.get("BLACK").play(MonTab,"BLACK","00"));
                                            pierreposée = true;
                                        }catch(Exception e){
                                            if(MonTab.estPlein())
                                                return;
                                        }
                                    }while(pierreposée=false);

                                }
                                if(Joueurs.get("WHITE").getClass().getSimpleName().compareTo("RandomPlayer")==0 ){
                                    boolean pierreposée = false;
                                    do{
                                        try{
                                            MonTab.play(Joueurs.get("WHITE").play(MonTab,"WHITE","00"));
                                            pierreposée = true;
                                        }catch(Exception e){
                                            System.out.println("njdjnd");
                                            if(MonTab.estPlein())
                                                return;
                                        }
                                    }while(pierreposée=false);

                                }

                            }catch(Exception e){
                                System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" invalid color or coordinate");
                            }
                        }
                    }
                    else if(instruction_donnee.compareTo("query_boardsize") == 0){
                        System.out.println(MonTab.query_boardsize());
                    }
                    else if(instruction_donnee.compareTo("SHOWBOARD") == 0){
                        System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                        System.out.println(MonTab.Show_board());
                    }
                    else if(instruction_donnee.compareTo("QUIT") == 0){
                        System.out.println("=" + (numero_donné == 0 ? "" : numero_donné));
                        return;
                    }
                    else if(instruction_donnee.compareTo("GENMOVE") == 0){
                        try{
                            Coord coord = MonTab.play(Joueurs.get(1).play(MonTab,arg[0],"00"));
                            System.out.println("=" + (numero_donné == 0 ? "" : numero_donné)+" "+coord.getX()+coord.getY());
                        }
                        catch(Exception e){
                            System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" "+e.getLocalizedMessage());

                        }

                    }
                    else{
                        System.out.println("?"+ (numero_donné == 0 ? "" : numero_donné)+" unknown command");
                    }
                }

            }

        } while (true);
    }
}
