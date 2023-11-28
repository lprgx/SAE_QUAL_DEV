package Jeu;

public class Pierre {
    private enum TypeCouleur {
        BLANCHE,
        NOIRE
    }
    private TypeCouleur couleur;


    public Pierre(String couleur){
        if(couleur.compareTo("BLANCHE")==0) {
            this.couleur = TypeCouleur.BLANCHE;
        }
    }

    public String toString(){

        return ".";
    }


}
