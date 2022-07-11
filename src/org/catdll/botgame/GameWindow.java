package org.catdll.botgame;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameWindow
{
    private long window_id;

    private String title;

    private int width;

    private int height;

    private boolean isFocus;

    private boolean isClose;

    public GameWindow(String title, int width, int height)
    {
        this.title = title;
        this.width = width;
        this.height = height;

        this.isClose = false;
        this.isFocus = true;

        this.initializeGlfw();
        this.window_id = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        this.setupCallback();

        glfwMakeContextCurrent(this.window_id);
        glfwSwapInterval(1);

        GL.createCapabilities();
    }

    private void initializeGlfw()
    {
        if (!glfwInit())
            throw new RuntimeException("Cannot initialize " + glfwGetVersionString());

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CLIENT_API, GLFW_OPENGL_API);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
    }

    private void setupCallback()
    {
        glfwSetErrorCallback((err, msgPtr) -> {
            String msg = memASCIISafe(msgPtr);
            if (msg != null)
                throw new RuntimeException(String.format("[%d] GLFW Error Callback: %s", err, msg));
        });

        glfwSetWindowCloseCallback(this.window_id, (close) -> isClose = true);

        glfwSetWindowFocusCallback(this.window_id, (win, focus) -> isFocus = focus);

        glfwSetWindowSizeCallback(this.window_id, (win, w, h) -> {
           width = w;
           height = h;
        });
    }

    public void update()
    {
        glfwSwapBuffers(this.window_id);
        glfwPollEvents();
    }

    public void close()
    {
        glfwDestroyWindow(this.window_id);
        glfwTerminate();

        this.window_id = 0;
        this.isClose = true;
    }

    public void setTitle(String title)
    {
        this.title = title;
        glfwSetWindowTitle(this.window_id, title);
    }

    public void setWidth(int width)
    {
        this.width = width;
        glfwSetWindowSize(this.window_id, this.width, this.height);
    }

    public void setHeight(int height)
    {
        this.height = height;
        glfwSetWindowSize(this.window_id, this.width, this.height);
    }

    public boolean isClose()
    {
        return this.isClose;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }
}
