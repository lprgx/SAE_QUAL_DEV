package Jeu;

public class Tableau {
    private int tab[][];
    private int taille; //Utilité ?



    public Tableau(int taille_){
        if(taille_ != 19 && taille_ != 13 && taille_ != 9 ){
            throw new IllegalArgumentException("La taille n'est pas bonne"); //Pas sur qu'on accepte seulement ces dimensions
        }
        tab = new int [taille_][taille_];
        taille = taille_;
    }

    //Dessiner le tableau
    public String seDessiner(){

        return "";
    }

    public void ClearTheBoard(){
        tab = new int [taille][taille];
    }

}
