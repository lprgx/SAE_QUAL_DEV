package Jeu;

import java.util.Objects;

public class Couleur {
    private enum TypeCouleur {
        WHITE,
        BLACK
    }
    TypeCouleur couleur;

    public Couleur(String c){
        if(c.compareTo("WHITE")!=0 && c.compareTo("BLACK")!=0)
            throw new IllegalArgumentException("invalid color or coordinate");
        if(c.compareTo("WHITE")==0) {
            this.couleur = TypeCouleur.WHITE;
        }
        else
            this.couleur = TypeCouleur.BLACK;
    }
    @Override
    public String toString(){
        if (couleur == TypeCouleur.BLACK)
            return"BLACK";
        else
            return "WHITE";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Couleur other = (Couleur) obj;
        if(((Couleur) obj).couleur.toString().compareTo(couleur.toString()) == 0)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(couleur);
    }
}


