package org.catdll.botgame.gfx;

import org.lwjgl.system.rpmalloc.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

public class Allocator
{
    private static boolean isInit;

    public static void init()
    {
        if (isInit)
            return;

        isInit = true;

        RPMallocConfig config = RPMallocConfig.malloc().error_callback((msg_ptr) -> {
            String msg = "";

            if (msg_ptr != NULL)
                msg = memASCII(msg_ptr);

            throw new RuntimeException("Allocator throw an exception!\n\n" + msg);
        });

        RPmalloc.rpmalloc_initialize_config(config);
    }

    public static ByteBuffer allocate(int size)
    {
        ByteBuffer buffer = RPmalloc.rpmalloc(size);
        if (buffer == null)
            throw new NullPointerException("Cannot allocate " + size + " bytes!");

        return buffer;
    }

    public static void free(Buffer data)
    {
        if (data == null)
            throw new NullPointerException("Cannot free memory! The data is invalid!");

        long address = memAddress(data);
        RPmalloc.nrpfree(address);
    }

    public static void dispose()
    {
        if (!isInit)
            return;

        isInit = false;
        RPmalloc.rpmalloc_finalize();
    }
}
