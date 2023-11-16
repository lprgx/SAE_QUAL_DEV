package Jeu;

public class Tableau {
    private Pierre tab[][];
    private int taille; //Utilité ?



    public Tableau(int taille_){
        if(taille_ != 19 && taille_ != 13 && taille_ != 9 ){
            throw new IllegalArgumentException("La taille n'est pas bonne"); //Pas sur qu'on accepte seulement ces dimensions
        }
        tab = new Pierre [taille_][taille_];
        taille = taille_;
    }

    //Dessiner le tableau
    public String seDessiner(){

        //TODO remplir le tableau avec les chiffres et colonnes

        StringBuilder dessin = new StringBuilder();

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                dessin.append(tab[i][j]).append("\t");
            }
            dessin.append("\n");
        }

        return dessin.toString();
    }


    public void ClearTheBoard(){
        tab = new Pierre [taille][taille];
    }

    public int query_boardsize(){
        return taille;
    }

}
