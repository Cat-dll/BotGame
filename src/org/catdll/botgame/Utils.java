package org.catdll.botgame;

import java.io.*;
import java.nio.*;

public class Utils
{
    public static byte[] loadFileData(String path)
    {
        byte[] data;

        try (FileInputStream input = new FileInputStream(path))
        {
            data = input.readAllBytes();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }

        return data;
    }

    public static int getBufferDataSize(Buffer buffer)
    {
        int size;

        switch (buffer)
        {
            case ByteBuffer buf -> size = Byte.SIZE;
            case ShortBuffer buf -> size = Short.BYTES;
            case IntBuffer buf -> size = Integer.BYTES;
            case LongBuffer buf -> size = Long.BYTES;
            case DoubleBuffer buf -> size = Double.BYTES;
            case FloatBuffer buf -> size = Float.BYTES;
            default -> throw new IllegalStateException("Cannot get buffer data size, the buffer is invalid!");
        }

        return size;
    }
}
