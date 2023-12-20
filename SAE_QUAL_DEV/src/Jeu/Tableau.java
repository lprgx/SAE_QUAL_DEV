package Jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
        MesPierres = new HashMap <> ();
        MesPierresCapturées = new HashMap <> ();
        NbBlackCapturés = 0;
        NbWhiteCapturés = 0;
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
        if(liberte(new Pierre(couleur,x,y), new ArrayList<>()) == 0)
            throw new IllegalArgumentException("illegal move");

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


    private int liberte(Pierre pierre,ArrayList<Coord> PierreVisitées ){
        PierreVisitées.add(new Coord(pierre.coord.getX(), pierre.coord.getY()));
        int mesliberte = 0 ;
        List<Pierre> Voisins = pierre.findVoisins(MesPierres,pierre.coord.getX(),pierre.coord.getY());
        if(pierre.coord.getX() == 0 && pierre.coord.getY() == 0) {
            if (Voisins.size() < 2)
                return 2 - Voisins.size();
        }
        else if(pierre.coord.getX() == 0 || pierre.coord.getY() == 0){
            if (Voisins.size() < 3)
                return 3 - Voisins.size();
        }
        else if(Voisins.size() < 4)
            return 4 - Voisins.size();


        for(Pierre pierres : Voisins){
            if (pierres.getCouleur() == pierre.getCouleur()){
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
        ArrayList<Coord> PierreVisitées = new ArrayList<>();
        List <Pierre> Voisins = pierre.findVoisins(MesPierres,pierre.coord.getX(),pierre.coord.getY());
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
        List<Pierre> Voisins = pierre.findVoisins(MesPierres, pierre.coord.getX(),pierre.coord.getY());
        ArrayList<Pierre> PierresCapturées = new ArrayList<>();
        if(MesPierresCapturées.containsKey(pierre.coord.getX())){
            if(MesPierresCapturées.get(pierre.coord.getX()).containsKey(pierre.coord.getY())){

            }
            else{
                MesPierres.get(pierre.coord.getX()).remove(pierre.coord.getY());
                MesPierresCapturées.get(pierre.coord.getX()).put(pierre.coord.getY(),new Pierre (pierre.getCouleur(), pierre.coord.getX(), pierre.coord.getY()));
                PierresCapturées.add(pierre);
                PierresCapturées.addAll(GetPierreCapture(pierre));
                if(pierre.getCouleur() == "BLACK")
                    NbBlackCapturés++;
                else
                    NbWhiteCapturés++;

            }

        }
        else{
            MesPierres.get(pierre.coord.getX()).remove(pierre.coord.getY());
            MesPierresCapturées.put(pierre.coord.getX(),new HashMap<>());
            MesPierresCapturées.get(pierre.coord.getX()).put(pierre.coord.getY(),new Pierre (pierre.getCouleur(), pierre.coord.getX(), pierre.coord.getY()));
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
                if(MesPierresCapturées.containsKey(pierresCapturées.coord.getX())){
                    if(MesPierresCapturées.get(pierresCapturées.coord.getX()).containsKey(pierresCapturées.coord.getY())){

                    }
                    else{
                        MesPierres.get(pierresCapturées.coord.getX()).remove(pierresCapturées.coord.getY());
                        MesPierresCapturées.get(pierresCapturées.coord.getX()).put(pierresCapturées.coord.getY(),new Pierre (pierresCapturées.getCouleur(), pierresCapturées.coord.getX(), pierresCapturées.coord.getY()));
                        PierresCapturées.add(pierresCapturées);
                        PierresCapturées.addAll(GetPierreCapture(pierresCapturées));
                        if(pierresCapturées.getCouleur() == "BLACK")
                            NbBlackCapturés++;
                        else
                            NbWhiteCapturés++;
                    }

                }
                else{
                    MesPierres.get(pierresCapturées.coord.getX()).remove(pierresCapturées.coord.getY());
                    MesPierresCapturées.put(pierresCapturées.coord.getX(),new HashMap<>());
                    MesPierresCapturées.get(pierresCapturées.coord.getX()).put(pierresCapturées.coord.getY(),new Pierre (pierresCapturées.getCouleur(), pierresCapturées.coord.getX(), pierresCapturées.coord.getY()));
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

    public String genmove(String couleur){
        ArrayList <Coord> CoordPasOccupées = new ArrayList<>();
        for(int i = 0; i< taille ; ++i){
            for(int j = 0; j < taille ; ++j){
                if(MesPierres.containsKey(i)){
                    if(MesPierres.get(i).containsKey(j)){
                    }
                    else{
                        CoordPasOccupées.add(new Coord(i,j+1));
                    }
                }
                else{
                    CoordPasOccupées.add(new Coord(i,j+1));
                }
            }
        }

        for(int i = 0 ; i < CoordPasOccupées.size() ; ++i){
            if(liberte(new Pierre(couleur,CoordPasOccupées.get(i).getX(),CoordPasOccupées.get(i).getY()), new ArrayList<Coord>())==0){
                CoordPasOccupées.remove(i);
            }
        }
        Random random = new Random();
        int MesCoord = random.nextInt(CoordPasOccupées.size());
        String a = CoordPasOccupées.get(MesCoord).toString();
        int coordX = a.charAt(0) - '0' + 'A';
        char y = (char)coordX;
        StringBuilder fff = new StringBuilder();
        fff.append(y);
        fff.append(a.charAt(1));
        play(couleur.toUpperCase(),fff.toString());

        return fff.toString();
    }

}
