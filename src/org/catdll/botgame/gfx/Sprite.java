package org.catdll.botgame.gfx;

import org.joml.*;
import java.awt.*;
import org.catdll.botgame.gfx.gl.*;

public class Sprite
{
    private Texture texture;

    private Rectangle region;

    private Color color;

    private Vector2i origin;

    private Vector2f scale;

    private float rotation;

    private boolean flipX;

    private boolean flipY;

    public Sprite(Texture texture)
    {
        this(texture, Color.WHITE);
    }

    public Sprite(Texture texture, Color color)
    {
        this.texture = texture;
        this.color = color;

        this.origin = new Vector2i(0);
        this.region = new Rectangle(0, 0, texture.getWidth(), texture.getHeight());

        this.scale = new Vector2f(1, 1);

        this.rotation = 0.0F;

        this.flipX = false;
        this.flipY = false;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public void setTexture(Texture texture)
    {
        if (texture == null)
            throw new NullPointerException("Cannot set texture to the sprite! The texture is invalid!");

        this.texture = texture;
    }

    public Rectangle getRegion()
    {
        return region;
    }

    public void setRegion(Rectangle region)
    {
        this.region = region;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public Vector2i getOrigin()
    {
        return origin;
    }

    public void setOrigin(Vector2i origin)
    {
        this.origin = origin;
    }

    public Vector2i getSize()
    {
        Vector2i size = new Vector2i();
        size.x = (int)(this.region.getWidth() * scale.x);
        size.y = (int)(this.region.getHeight() * scale.y);

        return size;
    }

    public Vector2f getScale()
    {
        return this.scale;
    }

    public void setScale(Vector2f scale)
    {
        this.scale = scale;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public boolean isFlipX()
    {
        return flipX;
    }

    public void setFlipX(boolean flipX)
    {
        this.flipX = flipX;
    }

    public boolean isFlipY()
    {
        return flipY;
    }

    public void setFlipY(boolean flipY)
    {
        this.flipY = flipY;
    }
}
