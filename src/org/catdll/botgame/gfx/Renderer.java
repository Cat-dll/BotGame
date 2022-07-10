package org.catdll.botgame.gfx;

import org.catdll.botgame.*;
import org.catdll.botgame.gfx.gl.Texture;

import java.awt.image.BufferStrategy;
import java.util.*;
import java.awt.*;

/*
public class Renderer
{
    public final static int MAX_CAMERA_STACK_SIZE = 16;

    private GameWindow window;

    private Graphics2D graphics;

    private BufferStrategy bufferStrategy;

    private Camera mainCamera;

    private Stack<Camera> cameraStacks;

    private Color backgroundColor;

    private int width;

    private int height;

    public Renderer(GameWindow window, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.BLACK;

        this.mainCamera = new Camera(0, 0, width, height);

        this.cameraStacks = new Stack<Camera>();
        this.setupCamera();

        this.window = window;

       // Canvas canvas = window.getCanvas();
        //canvas.createBufferStrategy(2);

        //this.bufferStrategy = canvas.getBufferStrategy();
        //this.graphics = (Graphics2D)bufferStrategy.getDrawGraphics();
    }

    private void setupCamera()
    {
        this.cameraStacks.clear();
        this.cameraStacks.add(mainCamera);
    }

    public boolean pushCamera(Camera camera)
    {
        if (camera == null)
            throw new NullPointerException("Cannot push camera to the stack! Null reference to camera!");

        boolean result = cameraStacks.size() + 1 < MAX_CAMERA_STACK_SIZE;
        if (result)
            cameraStacks.push(camera);

        return result;
    }

    public Camera popCamera()
    {
        Camera camera = this.mainCamera;

        if (cameraStacks.size() > 1)
            camera = cameraStacks.pop();

        return camera;

    }

    public void prepareFrame()
    {
        graphics.setBackground(backgroundColor);
        graphics.fillRect(0, 0, this.width * 4, this.height * 4);
        this.setupCamera();
    }

    public void renderTexture(Texture texture, float x, float y)
    {
        renderTexture(texture, x, y, Color.WHITE);
    }

    public void renderTexture(Texture texture, float x, float y, Color color)
    {
        if (texture == null)
            throw new NullPointerException("Cannot render texture! Texture is null!");

        Camera currentCamera = this.getCurrentCamera();
        graphics.translate(currentCamera.getPosX() + x, currentCamera.getPosY() + y);

        graphics.rotate(Math.toRadians(getCurrentCamera().rotation));

        double scaleX = (double)this.window.getWidth() / this.width;
        double scaleY = (double)this.window.getHeight() / this.height;
        graphics.scale(scaleX, scaleY);

        //graphics.drawImage(texture.getSource(), 0, 0, color, null);
    }

    public void dispose()
    {
        this.mainCamera = null;
        this.cameraStacks.clear();

        this.graphics.dispose();
    }

    public Camera getCurrentCamera()
    {
        return this.cameraStacks.peek();
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setBackgroundColor(Color color)
    {
        this.backgroundColor = color;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Color getBackgroundColor()
    {
        return this.backgroundColor;
    }

}*/