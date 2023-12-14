package Jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tableau {
    private HashMap<Integer,HashMap<Integer, Pierre>> MesPierres;
    private HashMap<Integer,HashMap<Integer, Pierre>> MesPierresCapturées;
    private int NbBlackCapturés;
    private int NbWhiteCapturés;

    private int taille = 0; //Utilité ?

    private static final int taille_standard = 9;

    public Tableau(int taille_){
        if(taille_ <2 || taille_ > 25 ){
            throw new IllegalArgumentException("La taille n'est pas bonne"); //Pas sur qu'on accepte seulement ces dimensions
        }
        taille = taille_;
        MesPierres = new HashMap <> ();
        MesPierresCapturées = new HashMap <> ();
        NbBlackCapturés = 0;
        NbWhiteCapturés = 0;

    }

    public Tableau(){
        this(taille_standard);
    }

    //Dessiner le tableau
    public String seDessiner(){
        StringBuilder dessin = new StringBuilder();
        dessin.append("   ");
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
            dessin.append(mataille).append(" ");
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
            if(mataille == 2)
                dessin.append("\t\t\t\t\t\t\t").append("WHITE (0) has captured "+NbBlackCapturés+" stones\n" );
            else if(mataille == 1)
                dessin.append("\t\t\t\t\t\t\t").append("BLACK (X) has captured "+NbWhiteCapturés+" stones\n");
            else
                dessin.append("\n");
            mataille = mataille -1;

        }

        dessin.append("   ");
        lettre = 'A';
        for (int i = 0 ; i < taille ; ++i){
            dessin.append(lettre).append(" ");
            lettre += 1;
        }

        return dessin.toString();
    }


    public void ClearTheBoard(){
        //a coder
    }

    public int query_boardsize(){
        return taille;
    }

    public void play(String couleur, String coord){
        if(coord.length() != 2)
            throw new IllegalArgumentException("invalid color or coordinate");

        Pierre pierre;
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
                 pierre = new Pierre(couleur,x,y);
                MesPierres.get(x).put(y,pierre);
            }

        }
        else{
             pierre = new Pierre(couleur,x,y);
            MesPierres.put(x,new HashMap<>());
            MesPierres.get(x).put(y,pierre);
        }
        capture(pierre);
    }


    public int liberte(Pierre pierre,ArrayList<Pierre.Coord> PierreVisitées ){
        PierreVisitées.add(new Pierre.Coord(pierre.coord.x(), pierre.coord.y()));
        int mesliberte = 0 ;
        List<Pierre> Voisins = pierre.findVoisins(MesPierres,pierre.coord.x(),pierre.coord.y());
        if(pierre.coord.x() == 0 && pierre.coord.y() == 0) {
            if (Voisins.size() < 2)
                return 2 - Voisins.size();
        }
        else if(pierre.coord.x() == 0 || pierre.coord.y() == 0){
            if (Voisins.size() < 3)
                return 3 - Voisins.size();
        }
        else if(Voisins.size() < 4)
            return 4 - Voisins.size();


        for(Pierre pierres : Voisins){
            if (pierres.getCouleur() == pierre.getCouleur()){
                boolean pierredéjàvisité = false;
                for(Pierre.Coord pierrevisitées : PierreVisitées){
                    if(pierrevisitées.x() == pierres.coord.x() && pierres.coord.y() == pierrevisitées.y())
                        pierredéjàvisité = true;
                }
                if(pierredéjàvisité == false)
                    mesliberte += liberte(pierres, PierreVisitées);
            }

        }
        return mesliberte;
    }

    public void capture(Pierre pierre){
        ArrayList<Pierre.Coord> PierreVisitées = new ArrayList<>();
        List <Pierre> Voisins = pierre.findVoisins(MesPierres,pierre.coord.x(),pierre.coord.y());
        for(Pierre pierrevoisins : Voisins){
            int nbliberté = liberte(pierrevoisins, PierreVisitées);
            if(nbliberté == 0){
                ArrayList<Pierre> LesPierresCapturés = new ArrayList<>();
                LesPierresCapturés.add(pierrevoisins);
                LesPierresCapturés.addAll(GetPierreCapture(pierrevoisins));
            }
        }



    }

    public List GetPierreCapture(Pierre pierre){
        List<Pierre> Voisins = pierre.findVoisins(MesPierres, pierre.coord.x(),pierre.coord.y());
        ArrayList<Pierre> PierresCapturées = new ArrayList<>();
        if(MesPierresCapturées.containsKey(pierre.coord.x())){
            if(MesPierresCapturées.get(pierre.coord.x()).containsKey(pierre.coord.y())){

            }
            else{
                MesPierres.get(pierre.coord.x()).remove(pierre.coord.y());
                MesPierresCapturées.get(pierre.coord.x()).put(pierre.coord.y(),new Pierre (pierre.getCouleur(), pierre.coord.x(), pierre.coord.y()));
                PierresCapturées.add(pierre);
                PierresCapturées.addAll(GetPierreCapture(pierre));
                if(pierre.getCouleur() == "BLACK")
                    NbBlackCapturés++;
                else
                    NbWhiteCapturés++;

            }

        }
        else{
            MesPierres.get(pierre.coord.x()).remove(pierre.coord.y());
            MesPierresCapturées.put(pierre.coord.x(),new HashMap<>());
            MesPierresCapturées.get(pierre.coord.x()).put(pierre.coord.y(),new Pierre (pierre.getCouleur(), pierre.coord.x(), pierre.coord.y()));
            PierresCapturées.add(pierre);
            PierresCapturées.addAll(GetPierreCapture(pierre));
            if(pierre.getCouleur() == "BLACK")
                NbBlackCapturés++;
            else
                NbWhiteCapturés++;
        }
        PierresCapturées.add(pierre);
        for(Pierre pierresCapturées : Voisins){
            if(pierresCapturées.getCouleur()==pierre.getCouleur()){
                if(MesPierresCapturées.containsKey(pierresCapturées.coord.x())){
                    if(MesPierresCapturées.get(pierresCapturées.coord.x()).containsKey(pierresCapturées.coord.y())){

                    }
                    else{
                        MesPierres.get(pierresCapturées.coord.x()).remove(pierresCapturées.coord.y());
                        MesPierresCapturées.get(pierresCapturées.coord.x()).put(pierresCapturées.coord.y(),new Pierre (pierresCapturées.getCouleur(), pierresCapturées.coord.x(), pierresCapturées.coord.y()));
                        PierresCapturées.add(pierresCapturées);
                        PierresCapturées.addAll(GetPierreCapture(pierresCapturées));
                        if(pierresCapturées.getCouleur() == "BLACK")
                            NbBlackCapturés++;
                        else
                            NbWhiteCapturés++;
                    }

                }
                else{
                    MesPierres.get(pierresCapturées.coord.x()).remove(pierresCapturées.coord.y());
                    MesPierresCapturées.put(pierresCapturées.coord.x(),new HashMap<>());
                    MesPierresCapturées.get(pierresCapturées.coord.x()).put(pierresCapturées.coord.y(),new Pierre (pierresCapturées.getCouleur(), pierresCapturées.coord.x(), pierresCapturées.coord.y()));
                    PierresCapturées.add(pierresCapturées);
                    PierresCapturées.addAll(GetPierreCapture(pierresCapturées));
                    if(pierresCapturées.getCouleur() == "BLACK")
                        NbBlackCapturés++;
                    else
                        NbWhiteCapturés++;
                }
            }

        }
        return PierresCapturées;
    }

}
