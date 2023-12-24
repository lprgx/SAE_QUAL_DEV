package Jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Pierre {


    public String getCouleur(){
        return couleur.toString();
    }

    //AJOUT DE LA STRUCTURE DE DONNEES COORD
    private static final Coord[] voisins = {
            new Coord(1, 0),
            new Coord(0, 1),
            new Coord(-1, 0),
            new Coord(0, -1)
    };
    public Coord coord;

    public List<Pierre> findVoisins(HashMap<Coord,Pierre> tableau, int x, int y){
        List<Pierre> voisinsList = new ArrayList<>();
        for(Coord c : voisins){
            int newX = x + c.getY();

            int newY = y + c.getX();
            if(tableau.containsKey(new Coord(newX,newY)))
                voisinsList.add(tableau.get(new Coord(newX,newY)));
        }
        return voisinsList; // on retourne la liste de voisins
    }


    private Couleur couleur;

    public Pierre(String couleur, int x, int y){
        this.couleur = new Couleur(couleur);
        coord = new Coord(x,y);
    }

    public String toString(){

        if(couleur.toString() == "BLACK"){
            return "X";
        }
        else
            return "O";

    }
}

