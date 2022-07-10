package org.catdll.botgame.gfx.gl.data;

import org.catdll.botgame.gfx.gl.IGLObject;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL40.*;

public class VertexArray implements IGLObject
{
    private int id;

    private boolean isBind;

    public VertexArray()
    {
        this.id = GL40.glGenVertexArrays();
        this.isBind = false;
    }

    private void setAttribute(int index, int count, int type, boolean normalized, int stride, long ptr)
    {
        GL40.glDisableVertexAttribArray(index);

        switch (type)
        {
            case GL_BYTE,
                 GL_UNSIGNED_BYTE,
                 GL_SHORT,
                 GL_UNSIGNED_SHORT,
                 GL_INT,
                 GL_UNSIGNED_INT
                -> GL40.glVertexAttribIPointer(index, count, type, stride, ptr);

            case GL_HALF_FLOAT,
                 GL_FLOAT,
                 GL_DOUBLE,
                 GL_INT_2_10_10_10_REV,
                 GL_UNSIGNED_INT_2_10_10_10_REV,
                 GL_UNSIGNED_INT_10F_11F_11F_REV
                 -> GL40.glVertexAttribPointer(index, count, type, normalized, stride, ptr);
        }

        GL40.glEnableVertexAttribArray(index);
    }

    public void setVertexAttributes(VertexAttributes vertexAttributes)
    {
        if (!isBind)
            throw new IllegalStateException("Cannot add vertex attributes! Vertex array is not bind!");

        int stride = vertexAttributes.getVertexSize();
        int index = 0;
        long ptr = 0;

        for (VertexAttribute attribute : vertexAttributes)
        {
           this.setAttribute(index, attribute.count(), attribute.type(), attribute.normalized(), stride, ptr);
           index++;
           ptr += attribute.getSize();
        }
    }

    public void bind()
    {
        if (isBind)
            return;

        GL40.glBindVertexArray(this.id);
        this.isBind = true;
    }

    public void unbind()
    {
        if (!isBind)
            return;

        GL40.glBindVertexArray(0);
        this.isBind = false;
    }

    @Override
    public void dispose()
    {
        this.unbind();
        GL40.glDeleteVertexArrays(this.id);

        this.id = GL_NONE;
    }

    @Override
    public int getId()
    {
        return this.id;
    }
}
