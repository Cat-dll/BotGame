package org.catdll.botgame;

import org.catdll.botgame.gfx.render.*;
import org.catdll.botgame.time.*;

public class Game implements Runnable
{
    public final static int WINDOW_WIDTH = 1024;

    public final static int WINDOW_HEIGHT = 768;

    public final static int GAME_WIDTH = 256;

    public final static int GAME_HEIGHT = 192;

    public GameWindow window;

    public GameRenderer renderer;

    public Time time;

    public void init()
    {
        this.time = new Time();

        this.window = new GameWindow("Super Window", WINDOW_WIDTH, WINDOW_HEIGHT);
        this.renderer = new GameRenderer(this);
    }

    @Override
    public void run()
    {
        this.init();

        while (!this.window.isClose())
        {
            time.beginMeasuringTime();

            this.window.update();

            if (time.isTickable())
                this.tick();

            this.render();

            time.endMeasuringTime();
        }

        this.dispose();
    }

    public void tick()
    {

    }

    public void render()
    {
        this.renderer.render();
    }

    public void dispose()
    {
        this.window.close();
    }

    public static void main(String[] args)
    {
        (new Thread(new Game())).start();
    }
}
