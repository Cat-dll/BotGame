package org.catdll.botgame.gfx.gl.shader;

import org.catdll.botgame.gfx.gl.IType;

import static org.lwjgl.opengl.GL40.*;

public enum ShaderType implements IType
{
    VERTEX("Vertex", GL_VERTEX_SHADER),
    FRAGMENT("Fragment", GL_FRAGMENT_SHADER);

    private final int id;

    private String name;

    ShaderType(String name, int id)
    {
        this.id = id;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}