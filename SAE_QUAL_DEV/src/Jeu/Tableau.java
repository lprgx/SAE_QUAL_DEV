package Jeu;

public class Tableau {
    private Pierre tab[][];
    private int taille; //Utilité ?



    public Tableau(int taille_){
        if(taille_ <2 || taille > 25 ){
            throw new IllegalArgumentException("La taille n'est pas bonne"); //Pas sur qu'on accepte seulement ces dimensions
        }
        tab = new Pierre [taille_][taille_];
        taille = taille_;
        for (int i = 0; i < taille ; i++) {
            for (int j = 0; j < taille ; j++) {
                tab [i][j] = new Pierre ("ROUGE");

            }
        }
    }

    //Dessiner le tableau
    public String seDessiner(){
        //TODO remplir le tableau avec les chiffres et colonnes
        StringBuilder dessin = new StringBuilder();

        dessin.append("    ");
        char lettre = 'A';
        for (int i = 0 ; i < taille ; ++i){
            dessin.append(lettre).append("  ");
            lettre += 1;
        }
        dessin.append("\n");
        int mataille = taille;
        for (int i = 0; i < taille ; i++) {
            if(mataille < 10 )
                dessin.append(" ");
            dessin.append(mataille).append("  ");
            for (int j = 0; j < taille ; j++) {
                dessin.append(tab[i][j].toString()).append("  ");
            }
            dessin.append(mataille).append("  ");
            dessin.append("\n");
            mataille = mataille -1;
        }

        dessin.append("    ");
         lettre = 'A';
        for (int i = 0 ; i < taille ; ++i){
            dessin.append(lettre).append("  ");
            lettre += 1;
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
