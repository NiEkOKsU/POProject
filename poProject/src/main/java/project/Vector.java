package project;

import java.util.Objects;

public class Vector {
    final int x;
    final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(%d, %d)".formatted(x,y);
    }

    public boolean precedes(Vector other){
        if (x <= other.x && y <= other.y){
            return true;
        }
        return false;
    }

    public boolean follows(Vector other){
        if (x >= other.x && y >= other.y){
            return true;
        }
        return false;
    }

    public Vector upperRight(Vector other){
        if (x < other.x && y < other.y){
            return new Vector(other.x,other.y);
        } else if (x < other.x && y > other.y) {
            return new Vector(other.x,y);
        } else if (x > other.x && y < other.y){
            return new Vector(x,other.y);
        } else {
            return new Vector(x,y);
        }
    }

    public Vector lowerLeft(Vector other){
        if (x < other.x && y < other.y){
            return new Vector(x,y);
        } else if (x < other.x && y > other.y) {
            return new Vector(x,other.y);
        } else if (x > other.x && y < other.y){
            return new Vector(other.x,y);
        } else {
            return new Vector(other.x,other.y);
        }
    }

    public Vector add(Vector other){
        return new Vector(x + other.x, y + other.y);
    }

    public Vector subtract(Vector other){
        return new Vector(x - other.x, y - other.y);
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector))
            return false;
        Vector that = (Vector) other;
        return x == that.x && y == that.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}
