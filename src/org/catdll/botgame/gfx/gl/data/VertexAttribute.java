package org.catdll.botgame.gfx.gl.data;

import org.catdll.botgame.gfx.gl.GLUtils;

import static org.lwjgl.opengl.GL40.*;

public record VertexAttribute(int count, int type, boolean normalized)
{
    public final static VertexAttribute POSITION = new VertexAttribute(2, GL_FLOAT, false);

    public final static VertexAttribute COLOR = new VertexAttribute(4, GL_FLOAT, false);

    public final static VertexAttribute TEXCOORD = new VertexAttribute(2, GL_FLOAT, false);

    public int getSize()
    {
        int size = 0;
        for (int i = 0; i < count; i++)
            size += GLUtils.getTypeSize(type);

        return size;
    }

}
