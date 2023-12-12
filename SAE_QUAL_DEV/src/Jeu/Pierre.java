package Jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pierre {
    private enum TypeCouleur {
        WHITE,
        BLACK
    }

    public TypeCouleur getCouleur(){
        return couleur;
    }
    //AJOUT DE LA STRUCTURE DE DONNEES COORD
    private record Coord(int x, int y){};

    private static final Coord[] voisins = {
            new Coord(1, 0),
            new Coord(0, 1),
            new Coord(-1, 0),
            new Coord(0, -1)
    };

    public List<Pierre> findVoisins(HashMap<Integer, HashMap<Integer, Pierre>> tableau, int x, int y){
        List<Pierre> voisinsList = new ArrayList<>();
        for(Coord c : voisins){
            int newX = x + c.y();
            int newY = y + c.x();
                voisinsList.add(tableau.get(newX).get(newY));
        }
        return voisinsList; // on retourne la liste de voisins
    }


    private TypeCouleur couleur;

    public Pierre(String couleur){
        if(couleur.compareTo("WHITE")!=0 && couleur.compareTo("BLACK")!=0)
            throw new IllegalArgumentException("invalid color or coordinate");
        if(couleur.compareTo("WHITE")==0) {
            this.couleur = TypeCouleur.WHITE;
        }
        else
            this.couleur = TypeCouleur.BLACK;
    }

    public String toString(){
        if(couleur == TypeCouleur.BLACK){
            return "X";
        }
        else
            return "O";
    }
}

