package ru.danila.cockroaches;

public class GameObject {
    float x, y;
    float width, height;
    float dx, dy;
    boolean isAlive = true;

    GameObject(float x, float y){
        this.x = x;
        this.y = y;
    }

    void move(){
        x += dx;
        y += dy;
    }

    boolean overlap(GameObject o){
        return (Math.abs(x-o.x) < (width / 2 + o.width/2))
                && (Math.abs(y-o.y) < (height / 3 + o.height/3));
    }

    boolean hit(float hit_x, float hit_y) {
        return (this.x <= hit_x && hit_x <= (this.x + width))
                && (this.y <= hit_y && hit_y <= (this.y + height));
    }
}
