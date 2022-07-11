package org.catdll.botgame.gfx.gl.data;

import java.nio.*;

import org.catdll.botgame.Utils;
import org.catdll.botgame.gfx.gl.GLUtils;
import org.catdll.botgame.gfx.gl.IBindable;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL40.*;

public class BufferObject implements IBindable
{
    private final BufferType type;

    private int id;

    private int size;

    private boolean isBind;

    public BufferObject(BufferType type)
    {
        this.id = GL40.glGenBuffers();
        this.size = 0;
        this.type = type;
        this.isBind = false;
    }

    private void checkBinding()
    {
        if (!isBind)
            throw new IllegalStateException("You must bind the buffer before operating on it!");
    }

    public void allocate(int size)
    {
        this.checkBinding();

        if (size < 1)
            throw new IllegalArgumentException("The size to be allocated must be larger than zero!");

        GL40.glBufferData(this.type.getId(), size, GL_DYNAMIC_DRAW);
        if (glGetError() != GL_NO_ERROR)
            throw new RuntimeException("An error was occurred when allocate buffer data!");

        this.size = size;
    }

    public void setData(Buffer data, int offset)
    {
        this.checkBinding();

        if (offset < 0)
            throw new IllegalArgumentException("Cannot copy data in the buffer with a negative offset!");

        int rawBufferSize = Utils.getBufferDataSize(data) * data.remaining();
        if (rawBufferSize + offset > this.size)
            throw new IndexOutOfBoundsException("The data to be copied is out of bound of the buffer!");

        GLUtils.bufferSubData(this.type.getId(), offset, data);
    }

    @Override
    public void dispose()
    {
        this.unbind();
        GL40.glDeleteBuffers(this.id);

        this.id = GL_NONE;
    }

    @Override
    public void bind()
    {
        if (this.isBind)
            return;

        GL40.glBindBuffer(this.type.getId(), this.id);
        this.isBind = true;
    }

    @Override
    public void unbind()
    {
        if (!this.isBind)
            return;

        GL40.glBindBuffer(this.type.getId(), GL_NONE);
        this.isBind = false;
    }

    @Override
    public boolean isBind()
    {
        return this.isBind;
    }

    @Override
    public int getId()
    {
        return this.id;
    }
}
