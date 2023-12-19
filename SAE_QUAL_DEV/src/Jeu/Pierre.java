package Jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pierre {
    private enum TypeCouleur {
        WHITE,
        BLACK
    }

    public String getCouleur(){
        return couleur.toString();
    }
    //AJOUT DE LA STRUCTURE DE DONNEES COORD
    public record Coord(int x, int y){
        @Override
        public String toString() {
            return x+""+y;
        }
    };

    private static final Coord[] voisins = {
            new Coord(1, 0),
            new Coord(0, 1),
            new Coord(-1, 0),
            new Coord(0, -1)
    };
    public Coord coord;

    public List<Pierre> findVoisins(HashMap<Integer, HashMap<Integer, Pierre>> tableau, int x, int y){
        List<Pierre> voisinsList = new ArrayList<>();
        for(Coord c : voisins){
            int newX = x + c.y();

            int newY = y + c.x();
            if(tableau.containsKey(newX)){
                if(tableau.get(newX).containsKey(newY))
                    voisinsList.add(tableau.get(newX).get(newY));
            }
        }
        return voisinsList; // on retourne la liste de voisins
    }


    private TypeCouleur couleur;

    public Pierre(String couleur, int x, int y){
        if(couleur.compareTo("WHITE")!=0 && couleur.compareTo("BLACK")!=0)
            throw new IllegalArgumentException("invalid color or coordinate");
        if(couleur.compareTo("WHITE")==0) {
            this.couleur = TypeCouleur.WHITE;
        }
        else
            this.couleur = TypeCouleur.BLACK;
        coord = new Coord(x,y);
    }

    public String toString(){
        if(couleur == TypeCouleur.BLACK){
            return "X";
        }
        else
            return "O";
    }
}

