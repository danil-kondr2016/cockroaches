package ru.danila.cockroaches;

import com.badlogic.gdx.math.MathUtils;

public class Cockroach extends GameObject {
    float rotate;
    float speed = 0.005f;
    float xdist, ydist;

    Cockroach() {
        super(0, 0);
        width = height = 100;

        int ypos = MathUtils.random(1);

        if (ypos == 0) {
            x = 10;
        } else {
            x = MyGdxGame.SCR_WIDTH - 10;
        }
        y = MathUtils.random(10, MyGdxGame.SCR_HEIGHT - 10);

        xdist = MyGdxGame.SCR_WIDTH / 2 - x;
        ydist = MyGdxGame.SCR_HEIGHT / 2 - y;

        float hypot = (float)Math.sqrt(xdist * xdist + ydist * ydist);
        if (y > MyGdxGame.SCR_HEIGHT / 2)
            rotate = (MathUtils.asin(xdist / hypot) / MathUtils.PI) * 180 + 180;
        else
            rotate = -(MathUtils.asin(xdist / hypot) / MathUtils.PI) * 180;
    }

    @Override
    void move() {
        x += xdist*speed;
        y += ydist*speed;
    }
}
