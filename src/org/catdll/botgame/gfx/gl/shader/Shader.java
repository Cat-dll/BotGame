package org.catdll.botgame.gfx.gl.shader;

import java.io.*;

import org.catdll.botgame.gfx.gl.IGLObject;

import org.lwjgl.opengl.GL40;
import static org.lwjgl.opengl.GL40.*;


public class Shader implements IGLObject
{
    private int id;

    public Shader(String source, ShaderType type)
    {
        if (source == null)
            throw new NullPointerException("Cannot create shader! The provided source is invalid!");

        this.id = GL40.glCreateShader(type.getId());
        GL40.glShaderSource(this.id, source);
        GL40.glCompileShader(this.id);

        if (GL40.glGetShaderi(this.id, GL_COMPILE_STATUS) == GL_FALSE)
        {
            String log = GL40.glGetShaderInfoLog(this.id);
            throw new RuntimeException(log);
        }
    }

    public Shader(byte[] source, ShaderType type)
    {
        this(new String(source), type);
    }

    public static Shader loadShader(String path, ShaderType type)
    {
        Shader shader;

        try (FileInputStream input = new FileInputStream(path))
        {
            byte[] shaderSource = input.readAllBytes();
            shader = new Shader(shaderSource, type);
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }

        return shader;
    }

    @Override
    public void dispose()
    {
        this.id = GL_NONE;
        GL40.glDeleteShader(this.id);
    }

    @Override
    public int getId()
    {
        return this.id;
    }
}
