package Jeu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tableau {
    private HashMap<Integer,HashMap<Integer, Pierre>> MesPierres;

    private int taille = 0; //Utilité ?

    private static final int taille_standard = 9;

    public Tableau(int taille_){
        if(taille_ <2 || taille_ > 25 ){
            throw new IllegalArgumentException("La taille n'est pas bonne"); //Pas sur qu'on accepte seulement ces dimensions
        }
        taille = taille_;
        MesPierres = new HashMap <> ();

    }

    public Tableau(){
        this(taille_standard);
    }

    //Dessiner le tableau
    public String seDessiner(){
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
                if(MesPierres.containsKey(j)){
                    if(MesPierres.get(j).containsKey(taille - i - 1))
                        dessin.append(MesPierres.get(j).get(taille - i -1).toString()).append(" ");
                    else
                        dessin.append(".").append(" ");
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

    public void play(String couleur, String coord){
        couleur = couleur.toUpperCase();
        coord = coord.toUpperCase();

        int x = coord.charAt(0) - 'A';
        couleur = couleur.toUpperCase();
        int y = coord.charAt(1) -'1';
        if(x < 0 || x >= taille || y < 0 || y >= taille)
            throw new IllegalArgumentException("invalid color or coordinate");

        if(MesPierres.containsKey(x)){
            if(MesPierres.get(x).containsKey(y))
                throw new IllegalArgumentException("illegal move");
            else{
                Pierre pierre = new Pierre(couleur);
                MesPierres.get(x).put(y,pierre);
            }

        }
        else{
            Pierre pierre = new Pierre(couleur);
            MesPierres.put(x,new HashMap<>());
            MesPierres.get(x).put(y,pierre);
        }
        /* APPEL DE CAPTURE MAIS NECESSIE AJOUT DU SCORE
        boolean estCapture = capture(couleur, x, y, MesPierres);
        if (estCapture) {
            MesPierres.get(x).remove(y);
        }
        */

    }


    public boolean capture(String couleur, int x, int y, HashMap<Integer,HashMap<Integer, Pierre>> MesPierres) {
        Pierre pierre = MesPierres.get(x).get(y);

        if (pierre == null) {
            return false;
        }

        // recupere les voisin de la pierre aux coord specifiees
        List<Pierre> voisins = pierre.findVoisins(MesPierres, x, y);

        for (Pierre voisin : voisins) {
            if (voisin != null && voisin.getCouleur() != pierre.getCouleur()) {
                List<Pierre> liberties = voisin.findVoisins(MesPierres, x, y);
                boolean estLibre = false;

                for (Pierre liberty : liberties) {
                    if (liberty == null) {
                        estLibre = true;
                        break;
                    }
                }

                // Si le voisin n'a pas de liberte
                if (!estLibre) {
                    return true;
                }
            }
        }

        return false;
    }



}
