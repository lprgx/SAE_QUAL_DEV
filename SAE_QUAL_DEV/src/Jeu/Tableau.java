package Jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tableau {

    HashMap<String,Boolean> tour;

    private HashMap<Coord, Pierre> MesPierres;

    private HashMap<Coord, Pierre> MesPierresCapturées;
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
        tour = new HashMap<>();
        tour.put("BLACK",true);
        tour.put("WHITE",false);

    }

    public Tableau(){
        this(taille_standard);
    }

    public boolean contientPierre(Coord coord){
        if(MesPierres.containsKey(coord))
            return true;
        else
            return false;
    }

    //Dessiner le tableau
    public String Show_board(){
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
                if(contientPierre(new Coord(j,taille-i-1)))
                    dessin.append(MesPierres.get(new Coord(j,taille-i-1)).toString()).append(" ");
                else
                    dessin.append(".").append(" ");
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
        MesPierres = new HashMap <> ();
        MesPierresCapturées = new HashMap <> ();
        NbBlackCapturés = 0;
        NbWhiteCapturés = 0;
    }

    public int query_boardsize(){
        return taille;
    }

    public Coord play(Pierre pierre){
        MesPierres.put(new Coord(pierre.coord.getX(),pierre.coord.getY()),pierre);

        if(liberte(pierre, new ArrayList<>()) == 0){
            MesPierres.remove(pierre.coord);
            throw new IllegalArgumentException("illegal move");
        }

        capture(pierre);
        MesPierresCapturées.clear();
        tour.put("BLACK",tour.get("WHITE"));
        tour.put("WHITE",!(tour.get("BLACK")));
        return pierre.coord;
    }


    public int liberte(Pierre pierre,ArrayList<Coord> PierreVisitées ){
        PierreVisitées.add(new Coord(pierre.coord.getX(), pierre.coord.getY()));
        int mesliberte = 0 ;
        List<Pierre> Voisins = pierre.findVoisins(MesPierres);
        if((pierre.coord.getX() == 0 && pierre.coord.getY() == taille-1) || (pierre.coord.getX() == 0 && pierre.coord.getY() == 0) || (pierre.coord.getX() == taille-1 && pierre.coord.getY() == 0) ||(pierre.coord.getX() == taille-1 && pierre.coord.getY() == taille -1)  ){
            if (Voisins.size() < 2)
                return 2 - Voisins.size();
        }
        else if(pierre.coord.getX() == 0 || pierre.coord.getY() == taille-1 || pierre.coord.getY() == 0 || pierre.coord.getX() == taille-1){
            if (Voisins.size() < 3)
                return 3 - Voisins.size();
        }
        else if(Voisins.size() < 4)
            return 4 - Voisins.size();

        for(Pierre pierres : Voisins){
            if (pierres.getCouleur().compareTo(pierre.getCouleur())==0){
                boolean pierredéjàvisité = false;
                for(Coord pierrevisitées : PierreVisitées){
                    if(pierrevisitées.getX() == pierres.coord.getX() && pierres.coord.getY() == pierrevisitées.getY())
                        pierredéjàvisité = true;
                }
                if(pierredéjàvisité == false)
                    mesliberte += liberte(pierres, PierreVisitées);
            }

        }
        return mesliberte;
    }

    private void capture(Pierre pierre){
        List <Pierre> Voisins = pierre.findVoisins(MesPierres);
        for(Pierre pierrevoisins : Voisins){
            int nbliberté = liberte(pierrevoisins, new ArrayList<>());
            if(nbliberté == 0){
                ArrayList<Pierre> LesPierresCapturés = new ArrayList<>();
                LesPierresCapturés.add(pierrevoisins);
                LesPierresCapturés.addAll(GetPierreCapture(pierrevoisins));
            }
        }



    }

    public List GetPierreCapture(Pierre pierre){
        List<Pierre> Voisins = pierre.findVoisins(MesPierres);
        ArrayList<Pierre> PierresCapturées = new ArrayList<>();
        if(!(MesPierresCapturées.containsKey((pierre.coord)))){
            MesPierres.remove(pierre.coord);
            MesPierresCapturées.put(pierre.coord, pierre);
            PierresCapturées.add(pierre);
            PierresCapturées.addAll(GetPierreCapture(pierre));
            if(pierre.getCouleur().compareTo("BLACK") == 0)
                NbBlackCapturés++;
            else
                NbWhiteCapturés++;
        }

        PierresCapturées.add(pierre);
        for(Pierre pierresCapturées : Voisins){
            if(pierresCapturées.getCouleur().compareTo(pierre.getCouleur())==0){
                if(!(MesPierresCapturées.containsKey(pierresCapturées.coord))){
                    MesPierres.remove(pierresCapturées.coord);
                    MesPierresCapturées.put(pierresCapturées.coord, pierresCapturées);
                    PierresCapturées.add(pierresCapturées);
                    PierresCapturées.addAll(GetPierreCapture(pierresCapturées));
                    if(pierresCapturées.getCouleur().compareTo("BLACK") == 0)
                        NbBlackCapturés++;
                    else
                        NbWhiteCapturés++;
                }
            }

        }
        return PierresCapturées;
    }



    public int getTaille(){
        return taille;
    }

    public boolean estPlein(){
        for(int i = 0 ; i < taille ; i++){
            for(int j = 0; j < taille ; j++){
                if(!(MesPierres.containsKey(new Coord(i,j)))){
                   //   MesPierres.put(new Coord(i,j),new Pierre("BLACK",i,j));
                    if(liberte(new Pierre("BLACK",i,j),new ArrayList<>())!=0)
                        return false;
                   // MesPierres.remove(new Coord(i,j));
                   // MesPierres.put(new Coord(i,j),new Pierre("WHITE",i,j));
                    if(liberte(new Pierre("WHITE",i,j),new ArrayList<>())!=0)
                        return false;
                   // MesPierres.remove(new Coord(i,j));
                }
            }
        }
        return true;
    }

    public boolean getTour(String couleur){
        return tour.get(couleur);
    }

}
