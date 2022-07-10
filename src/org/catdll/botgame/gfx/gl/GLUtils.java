package org.catdll.botgame.gfx.gl;

import org.lwjgl.opengl.GL40;

import java.nio.*;

import static org.lwjgl.opengl.GL40.*;

public class GLUtils
{
    public static int getTypeSize(int type)
    {
        switch (type)
        {
            // TODO: Add more GL types
            case GL_BYTE:
            case GL_UNSIGNED_BYTE:
                return Byte.BYTES;
            case GL_SHORT:
            case GL_UNSIGNED_SHORT:
                return Short.BYTES;
            case GL_UNSIGNED_INT:
            case GL_INT:
            case GL_FLOAT:
                return Integer.BYTES;
            case GL_DOUBLE:
                return Double.BYTES;
            default:
                throw new IllegalStateException("Cannot get OpenGL type size! Invalid type specified!");
        }
    }

    public static void bufferSubData(int target, int offset, Buffer data)
    {
        switch (data)
        {
            case ByteBuffer buf -> GL40.glBufferSubData(target, offset, buf);
            case ShortBuffer buf -> GL40.glBufferSubData(target, offset, buf);
            case IntBuffer buf -> GL40.glBufferSubData(target, offset, buf);
            case LongBuffer buf -> GL40.glBufferSubData(target, offset, buf);
            case DoubleBuffer buf -> GL40.glBufferSubData(target, offset, buf);
            case FloatBuffer buf -> GL40.glBufferSubData(target, offset, buf);
            default -> throw new IllegalStateException("Cannot copy data! The buffer data type is unsupported!");
        }

        if (glGetError() != GL_NO_ERROR)
            throw new RuntimeException("An error was occured when allocate buffer data!");
    }
}
