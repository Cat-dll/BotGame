package org.catdll.botgame.gfx;

import org.joml.*;
import java.awt.*;

public class Camera
{
    private Vector2f position;

    private Vector2i size;

    private float rotation;

    private Matrix4f transformMatrix;

    private boolean isTransformMatrixDirty;

    public Camera(float posX, float posY, int width, int height)
    {
        this(new Vector2f(posX, posY), new Vector2i(width, height));
    }

    public Camera(Vector2f position, Vector2i size)
    {
        this.position = position;
        this.size = size;
        this.rotation = 0.0F;

        this.transformMatrix = new Matrix4f();
        this.isTransformMatrixDirty = true;
    }

    private void calculateMatrix()
    {
        if (!this.isTransformMatrixDirty)
            return;

        this.transformMatrix = new Matrix4f().identity()
            .setOrtho2D(0, size.x, size.y, 0)
            .translate(position.x, position.y, 0)
            .rotate(this.rotation, 0, 0, 1);

        this.isTransformMatrixDirty = false;
    }

    public void scale(Vector2f scale)
    {
        this.size.x = (int)(size.x / scale.x);
        this.size.y = (int)(size.y / scale.y);
        this.isTransformMatrixDirty = true;
    }

    public void scale(int scaleX, int scaleY)
    {
        this.scale(new Vector2f(scaleX, scaleY));
    }

    public void rotate(float angle)
    {
        this.rotation = angle;
        this.isTransformMatrixDirty = true;
    }

    public void translate(Vector2f offset)
    {
        this.position.x += offset.x;
        this.position.y += offset.y;
        this.isTransformMatrixDirty = true;
    }


    public void translate(float offsetX, float offsetY)
    {
        this.translate(new Vector2f(offsetX, offsetY));
    }


    public Rectangle getBound()
    {
        return new Rectangle((int)this.position.x, (int)this.position.y, this.size.x, this.size.y);
    }

    public Vector2f getPosition()
    {
        return position;
    }

    public void setPosition(Vector2f position)
    {
        this.position = position;
    }

    public Vector2i getSize()
    {
        return size;
    }

    public void setSize(Vector2i size)
    {
        this.size = size;
    }

    public float getRotation()
    {
        return rotation;
    }

    public void setRotation(float rotation)
    {
        this.rotation = rotation;
    }

    public Matrix4f getTransformMatrix()
    {
        this.calculateMatrix();
        return transformMatrix;
    }
}
