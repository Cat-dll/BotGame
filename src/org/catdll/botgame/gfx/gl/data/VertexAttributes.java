package org.catdll.botgame.gfx.gl.data;

import java.util.*;

public class VertexAttributes implements Iterable<VertexAttribute>
{
    public final static int MAX_VERTEX_ATTRIBUTES = 16;

    private ArrayList<VertexAttribute> attributes;

    private int vertexSize;

    private boolean isVertexSizeDirty;

    public VertexAttributes()
    {
        this.attributes = new ArrayList<VertexAttribute>();
        this.isVertexSizeDirty = true;
        this.vertexSize = 0;
    }

    public void addAttribute(VertexAttribute attribute)
    {
        if (this.attributes.size() + 1 < MAX_VERTEX_ATTRIBUTES)
        {
            this.attributes.add(attribute);
            this.isVertexSizeDirty = true;
        }
    }

    public void removeAttribute(VertexAttribute attribute)
    {
        if (!this.attributes.isEmpty())
        {
            this.attributes.remove(attribute);
            this.isVertexSizeDirty = true;
        }
    }

    public int getVertexSize()
    {
        int size = 0;

        if (this.isVertexSizeDirty)
        {
            for (VertexAttribute attribute : this)
                size += attribute.getSize();
            this.isVertexSizeDirty = false;
        }

        return size;
    }

    @Override
    public Iterator<VertexAttribute> iterator() {
        return new Iterator<VertexAttribute>() {
            private int currentIdx = 0;

            @Override
            public boolean hasNext()
            {
                return currentIdx < attributes.size() && attributes.get(currentIdx) != null;
            }

            @Override
            public VertexAttribute next()
            {
                return attributes.get(currentIdx++);
            }
        };
    }
}
