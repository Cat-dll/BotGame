package org.catdll.botgame.gfx.gl;

public interface IBindable extends IGLObject
{
    void bind();

    void unbind();

    boolean isBind();
}
