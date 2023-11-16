package Jeu;

public class Pierre {
    private enum TypeCouleur {
        ROUGE,
        NOIRE
    }
    private TypeCouleur couleur;


    public Pierre(String couleur){
        if(couleur.compareTo("ROUGE")==0) {
            this.couleur = TypeCouleur.ROUGE;

        }
    }


}
