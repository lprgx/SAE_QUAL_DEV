package Jeu;

import java.util.HashMap;
import java.util.Map;

public class Tableau {
    private Map<Coord,Pierre> MesPierres;
    private int taille = 0; //Utilité ?
    private static final int taille_standard = 9;

    public Tableau(int taille_){
        if(taille_ <2 || taille_ > 25 ){
            throw new IllegalArgumentException("La taille n'est pas bonne"); //Pas sur qu'on accepte seulement ces dimensions
        }
        taille = taille_;
        MesPierres = new HashMap <Coord,Pierre>();
    }
    public Tableau(){
        this(taille_standard);
    }

    //Dessiner le tableau
    public String seDessiner(){
        //TODO remplir le tableau avec les chiffres et colonnes
        StringBuilder dessin = new StringBuilder();

        dessin.append("    ");
        char lettre = 'A';
        for (int i = 0 ; i < taille ; ++i){
            dessin.append(lettre).append(" ");
            lettre += 1;
        }
        dessin.append("\n");
        int mataille = taille;
        for (int i = 0; i < taille ; i++) {
            if(mataille < 10 )
                dessin.append(" ");
            dessin.append(mataille).append("  ");
            for (int j = 0; j < taille ; j++) {

                if(MesPierres.containsKey(new Coord(i,j))){

                }
                else{
                    dessin.append(".").append(" ");
                }
            }
            dessin.append(mataille).append("  ");
            dessin.append("\n");
            mataille = mataille -1;
        }

        dessin.append("    ");
        lettre = 'A';
        for (int i = 0 ; i < taille ; ++i){
            dessin.append(lettre).append(" ");
            lettre += 1;
        }
        return dessin.toString();
    }


    public void ClearTheBoard(){
        MesPierres.clear();
    }

    public int query_boardsize(){
        return taille;
    }

    private class Coord{
        private int x;
        private int y;

        public Coord(int a, int b){
            x=a;
            y=b;

        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
    }

}
