package org.catdll.botgame;

import java.io.*;

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
}
