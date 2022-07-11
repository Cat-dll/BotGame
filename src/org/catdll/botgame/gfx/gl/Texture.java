package org.catdll.botgame.gfx.gl;

import java.nio.*;

import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.opengl.GL40.*;

import org.lwjgl.stb.*;


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
        GL40.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
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

        try (MemoryStack stack = MemoryStack.stackPush())
        {
            ByteBuffer data;

            IntBuffer w = stack.ints(0);
            IntBuffer h = stack.ints(0);
            IntBuffer channels = stack.ints(0);
            data = STBImage.stbi_load(path, w, h, channels, 0);

            texture = new Texture(data, w.get(0), h.get(0));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Cannot load " + path + "!");
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
