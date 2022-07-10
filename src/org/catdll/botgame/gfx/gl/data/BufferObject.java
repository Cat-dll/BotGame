package org.catdll.botgame.gfx.gl.data;

import static org.lwjgl.opengl.GL40.*;

import org.catdll.botgame.gfx.gl.GLUtils;
import org.catdll.botgame.gfx.gl.IBindable;
import org.lwjgl.opengl.*;
import java.nio.*;

public class BufferObject implements IBindable
{
    private static int currentBindBuffer;

    private int id;

    private int size;

    private BufferType type;

    public BufferObject(BufferType type)
    {
        this.id = GL40.glGenBuffers();
        this.size = 0;
        this.type = type;
    }

    private void checkBinding()
    {
        if (!this.isBind())
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
    }

    public void setData(Buffer data, int offset)
    {
        this.checkBinding();

        if (offset < 0)
            throw new IllegalArgumentException("Cannot copy data in the buffer with a negative offset!");

        GLUtils.bufferSubData(this.type.getId(), offset, data);
    }

    public void dispose()
    {
        this.unbind();
        GL40.glDeleteBuffers(this.id);

        this.id = GL_NONE;
    }

    @Override
    public void bind()
    {
        if (isBind())
            return;

        GL40.glBindBuffer(this.type.getId(), this.id);
        currentBindBuffer = this.id;
    }

    @Override
    public void unbind()
    {
        if (!isBind())
            return;

        GL40.glBindBuffer(this.type.getId(), GL_NONE);
        currentBindBuffer = GL_NONE;
    }

    @Override
    public boolean isBind()
    {
        return this.id == currentBindBuffer;
    }

    @Override
    public int getCurrentBind()
    {
        return currentBindBuffer;
    }

    @Override
    public int getId()
    {
        return this.id;
    }
}
