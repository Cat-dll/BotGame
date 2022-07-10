package org.catdll.botgame.time;

public class Time
{
    public static final int TPS = 60;

    public static final int FPS = 60;

    public static final long NS_PER_SEC = 1_000_000_000;

    public static final long NS_PER_TICK = (long)((1.0D / TPS) * NS_PER_SEC);


    private double frametime;

    private long frames;

    private long ticks, tickTimer;

    private int fps;

    private long old;

    private long current;

    private boolean isTickable;

    public Time()
    {
        this.current = 0;
        this.old = 0;

        this.frametime = 0.0D;
        this.frames = 0;
        this.ticks = 0;
        this.fps = 0;
        this.tickTimer = 0;
        this.isTickable = false;
    }

    public static long now()
    {
        return System.nanoTime();
    }

    public void beginMeasuringTime()
    {
        old = now();
        if (tickTimer >= NS_PER_TICK)
        {
            tickTimer = 0;
            isTickable = true;
            ticks++;
        }
    }

    public void endMeasuringTime()
    {
        current = now();

        long elapsed = (current - old);
        frametime = (double)elapsed / NS_PER_SEC;
        fps = (int)Math.round(1.0D /  frametime);
        frames++;

        tickTimer += elapsed;
        isTickable = false;
    }

    public long getTicks()
    {
        return this.ticks;
    }

    public int getFPS()
    {
        return this.fps;
    }

    public double getFrametimes()
    {
        return this.frametime;
    }

    public long getFrames()
    {
        return this.frames;
    }

    public boolean isTickable()
    {
        return this.isTickable;
    }
}
