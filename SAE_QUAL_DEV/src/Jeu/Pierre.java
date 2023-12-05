package Jeu;

import java.util.ArrayList;
import java.util.List;

public class Pierre {
    private enum TypeCouleur {
        WHITE,
        BLACK
    }

    //AJOUT DE LA STRUCTURE DE DONNEES COORD
    private record Coord(int x, int y){};

    private static final Coord[] voisins = {
            new Coord(1, 0),
            new Coord(0, 1),
            new Coord(-1, 0),
            new Coord(0, -1)
    };

    public List<Coord> findVoisins(int x, int y){
        List<Coord> voisinsList = new ArrayList<>();
        for(Coord c : voisins){
            voisinsList.add(new Coord(x+c.y(), y+c.x()));
            // x est censé représenter l'axe des ordonnées de la pierre et l'inverse pour y
        }
        return voisinsList; // on retourne la liste de voisin
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

