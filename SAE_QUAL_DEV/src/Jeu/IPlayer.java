package Jeu;

public interface IPlayer {

    public Pierre play(Tableau tab, String couleur, String coord) ;

    public Boolean SonTour(Tableau tab, String couleur);
}
