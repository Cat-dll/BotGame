package org.catdll.botgame;

import org.catdll.botgame.gfx.gl.*;
import org.catdll.botgame.time.*;

public class Game implements Runnable
{
    public final static int WINDOW_WIDTH = 1024;

    public final static int WINDOW_HEIGHT = 768;

    public final static int GAME_WIDTH = 256;

    public final static int GAME_HEIGHT = 192;

    public GameWindow window;

    public Time time;

    private Texture texture;

    public void init()
    {
       // this.window = new GameWindow("Pixel Survival", GAME_WINDOW_WIDTH, GAME_WINDOW_HEIGHT);
        //this.renderer = new Renderer(this.window, 256, 192);
        this.time = new Time();
        this.window = new GameWindow("Super Window", WINDOW_WIDTH, WINDOW_HEIGHT);
        this.texture = Texture.loadTexture("res/texture/canyon_sheets.png");
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
        System.out.println("Tick!");
    }

    public void render()
    {

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
