package org.catdll.botgame.gfx;

public class Color
{
    public final static Color WHITE = new Color(255, 255, 255);

    public final static Color BLACK = new Color(3, 3, 3);

    public final static Color RED = new Color(242, 34, 34);

    public final static Color BLUE = new Color(25, 136, 255);

    public final static Color GREEN = new Color(24, 150, 20);

    public final static Color YELLOW = new Color(232, 229, 39);

    public final static Color PINK = new Color(255, 69, 233);

    public final static Color BROWN = new Color(66, 30, 9);

    public final static Color PURPLE = new Color(107, 37, 219);

    public final static Color AQUA = new Color(10, 216, 247);

    public final static Color LIME = new Color(44, 250, 25);

    public final static Color GRAY = new Color(94, 94, 94);

    public float r;

    public float g;

    public float b;

    public float a;

    public Color(float r, float g, float b)
    {
        this(r, g, b, 255.0F);
    }

    public Color(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color normalized()
    {
        float normR = this.r / 255.0F;
        float normG = this.g / 255.0F;
        float normB = this.b / 255.0F;
        float normA = this.a / 255.0F;

        return new Color(normR, normG, normB, normA);
    }
}
