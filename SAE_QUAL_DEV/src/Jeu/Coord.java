package Jeu;

public class Coord {
    int x;
    int y;

    public Coord(int x_, int y_){

        x=x_;
        y=y_;
    }
    public String toString() {
        return x+""+y;
    }

    public boolean compareTo(Coord a){
        if(a.x == this.x && a.y == this.y)
            return true;
        else
            return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
