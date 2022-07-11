package org.catdll.botgame.gfx.gl.shader;

import org.catdll.botgame.gfx.gl.IBindable;

import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL40.*;

public class ShaderProgram implements IBindable
{
    private int id;

    private final Shader vertexShader;

    private final Shader fragmentShader;

    private boolean isUse;

    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource)
    {
        this.id = GL40.glCreateProgram();

        this.vertexShader = new Shader(vertexShaderSource, ShaderType.VERTEX);
        this.fragmentShader = new Shader(fragmentShaderSource, ShaderType.FRAGMENT);

        this.attachShader(this.vertexShader);
        this.attachShader(this.fragmentShader);

        GL40.glLinkProgram(this.id);
        if (glGetProgrami(this.id, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException(GL40.glGetProgramInfoLog(this.id));

        this.isUse = false;
    }

    private void checkIsUse()
    {
        if (!this.isUse)
            throw new IllegalStateException("The program must be used in the current context before operating it!");
    }

    public void setUniform(String name, int value)
    {
        this.checkIsUse();
        GL40.glUniform1i(GL40.glGetUniformLocation(this.id, name), value);
    }

    public void setUniform(String name, float value)
    {
        this.checkIsUse();
        GL40.glUniform1f(GL40.glGetUniformLocation(this.id, name), value);
    }

    public void setUniform(String name, double value)
    {
        this.checkIsUse();
        GL40.glUniform1d(GL40.glGetUniformLocation(this.id, name), value);
    }

    public void setUniform(String name, float[] value)
    {
        this.checkIsUse();
        GL40.glUniformMatrix4fv(GL40.glGetUniformLocation(this.id, name), false, value);
    }

    public void bind()
    {
        if (this.isUse)
            return;

        glUseProgram(this.id);
        this.isUse = true;
    }

    public void unbind()
    {
        if (!this.isUse)
            return;

        glUseProgram(GL_NONE);
        this.isUse = false;
    }

    public void attachShader(Shader shader)
    {
        if (!glIsShader(shader.getId()))
            throw new RuntimeException("Cannot attach shader, the shader is invalid!");

        GL40.glAttachShader(this.id, shader.getId());
    }

    public void detachShader(Shader shader)
    {
        if (!glIsShader(shader.getId()))
            throw new RuntimeException("Cannot detach shader, the shader is invalid!");

        GL40.glDetachShader(this.id, shader.getId());
    }

    @Override
    public void dispose()
    {
        this.unbind();
        this.detachShader(vertexShader);
        this.detachShader(fragmentShader);

        this.vertexShader.dispose();
        this.fragmentShader.dispose();

        GL40.glDeleteProgram(this.id);
        this.id = GL_NONE;
    }

    @Override
    public boolean isBind()
    {
        return this.isUse;
    }

    @Override
    public int getId()
    {
        return this.id;
    }
}
