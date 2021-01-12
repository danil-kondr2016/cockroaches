package ru.danila.cockroaches;

public class Cheese extends GameObject {
    Cheese() {
        super(0, 0);
        width = height = 256;
        x = MyGdxGame.SCR_WIDTH/2;
        y = MyGdxGame.SCR_HEIGHT/2;
    }

    @Override
    void move() {
        super.move();
        if(x<width/2) {
            dx = 0;
            x = width/2;
        }
        if(x>MyGdxGame.SCR_WIDTH-width/2){
            dx = 0;
            x = MyGdxGame.SCR_WIDTH-width/2;
        }
    }
}
