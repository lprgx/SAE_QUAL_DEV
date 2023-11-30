package Jeu;

public class Pierre {
    private enum TypeCouleur {
        WHITE,
        BLACK
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

