package org.catdll.botgame;

public interface IScreen extends IDisposable
{
    void tick();

    void update();

    void render();
}
