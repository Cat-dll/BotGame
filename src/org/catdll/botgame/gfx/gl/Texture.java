package org.catdll.botgame.gfx.gl;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL40;
import org.lwjgl.system.MemoryUtil;
import java.nio.*;

import static org.lwjgl.opengl.GL40.*;

// TODO: Implements IBindable
public class Texture
{
    private int id;

    private int width;

    private int height;

    public Texture(ByteBuffer data, int width, int height)
    {
        if (data == null)
            throw new NullPointerException("Cannot create texture! The data is invalid!");

        this.id = GL40.glGenTextures();
        this.bind();
        GL40.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        GL40.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        GL40.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL40.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        GL40.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        this.unbind();
    }

    public void bind()
    {
        GL40.glBindTexture(GL_TEXTURE_2D, this.id);
    }

    public void unbind()
    {
        GL40.glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static Texture loadTexture(String path)
    {
        Texture texture;

        try
        {
            File file = new File(path);
            BufferedImage image = ImageIO.read(file);

            int w = image.getWidth();
            int h = image.getHeight();

            ByteBuffer rawBuffer = MemoryUtil.memAlloc(w * h * 4);
            int[] pixels = image.getRGB(0, 0, w, h, null, 0, w);

            // NOTE(catdll): Convert ARGB to RGBA
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    int idx = y * w + x;
                    rawBuffer.put((byte)((pixels[idx] >> 16) & 0xFF));
                    rawBuffer.put((byte)((pixels[idx] >> 8) & 0xFF));
                    rawBuffer.put((byte)((pixels[idx]) & 0xFF));
                    rawBuffer.put((byte)((pixels[idx] >> 24) & 0xFF));
                }
            }

            rawBuffer.flip();

            texture = new Texture(rawBuffer, image.getWidth(), image.getHeight());
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }

        return texture;

    }

    public void dispose()
    {
        GL40.glDeleteTextures(this.id);
        this.id = GL_NONE;
    }

    public int getId()
    {
        return this.id;
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
