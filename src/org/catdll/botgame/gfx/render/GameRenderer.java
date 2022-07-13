package org.catdll.botgame.gfx.render;

import org.catdll.botgame.*;
import org.catdll.botgame.gfx.Allocator;
import org.catdll.botgame.gfx.Camera;
import org.catdll.botgame.gfx.SpriteBatch;
import org.lwjgl.opengl.GL40;

public class GameRenderer implements IRender
{
    private Game game;

    private int width;

    private int height;

    private Camera mainCamera;

    private SpriteBatch batch;


    public GameRenderer(Game game)
    {
        Allocator.init();

        this.game = game;
        this.width = Game.GAME_WIDTH;
        this.height = Game.GAME_HEIGHT;

        this.batch = new SpriteBatch();
        this.mainCamera = new Camera(0, 0, this.width, this.height);
    }

    public void clearBackground()
    {
        GL40.glClear(GL40.GL_COLOR_BUFFER_BIT);
        GL40.glClearColor(0.1F, 0.2F, 0.3F, 1.0F);
    }

    @Override
    public void render()
    {
        GL40.glViewport(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        this.clearBackground();

        this.batch.setTransformMatrix(mainCamera.getTransformMatrix());
        this.batch.begin();

        this.batch.end();
    }

    public void dispose()
    {
        this.batch.dispose();
        Allocator.dispose();
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }
}
