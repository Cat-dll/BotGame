package org.catdll.botgame.gfx.gl.data;

import org.catdll.botgame.gfx.gl.IType;

import static org.lwjgl.opengl.GL40.*;

public enum BufferType implements IType
{
    VERTEX_BUFFER("Vertex buffer", GL_ARRAY_BUFFER),
    INDEX_BUFFER("Index buffer", GL_ELEMENT_ARRAY_BUFFER);

    private int id;

    private String name;

    BufferType(String name, int id)
    {
        this.name = name;
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
