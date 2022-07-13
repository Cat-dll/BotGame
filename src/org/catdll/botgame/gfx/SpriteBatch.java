package org.catdll.botgame.gfx;

import org.catdll.botgame.IDisposable;
import org.catdll.botgame.Utils;
import org.catdll.botgame.gfx.gl.*;
import org.catdll.botgame.gfx.gl.shader.*;
import org.catdll.botgame.gfx.gl.data.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL40;
import static org.lwjgl.opengl.GL40.*;

import java.nio.*;
import java.awt.*;

public class SpriteBatch implements IDisposable
{
    public static final int SPRITE_INDEX_COUNT = 6;

    public static final int SPRITE_VERTEX_COUNT = 4;

    public static final int MAX_BATCH_SIZE = 8192;

    private final int size;

    private int spriteCount;

    private Texture currentTexture;

    private IntBuffer indexData;

    private FloatBuffer vertexData;

    private BufferObject vertexBuffer;

    private BufferObject indexBuffer;

    private ShaderProgram program;

    private VertexArray vao;

    private Matrix4f transformMatrix;

    private float[] rawMatrix = new float[4 * 4];

    private boolean isDrawing;

    public SpriteBatch()
    {
        this(MAX_BATCH_SIZE);
    }

    public SpriteBatch(int batchSize)
    {
        this.size = batchSize;

        this.program = this.createShaders();

        this.vertexBuffer = new BufferObject(BufferType.VERTEX_BUFFER);
        this.indexBuffer = new BufferObject(BufferType.INDEX_BUFFER);
        this.vao = new VertexArray();

        this.vao.bind();
        VertexAttributes attributes = new VertexAttributes();
        attributes.addAttribute(VertexAttribute.POSITION);
        attributes.addAttribute(VertexAttribute.COLOR);
        attributes.addAttribute(VertexAttribute.TEXCOORD);

        int batchSizeInBytes = batchSize * SPRITE_VERTEX_COUNT * attributes.getVertexSize();

        this.vertexBuffer.bind();
        this.vertexBuffer.allocate(batchSizeInBytes);
        this.vertexData = Allocator.allocate(batchSizeInBytes).asFloatBuffer();


        this.indexBuffer.bind();
        this.generateIndexes(size);

        this.vao.setVertexAttributes(attributes);

        this.spriteCount = 0;
        this.isDrawing = false;
        this.currentTexture = null; // TODO: Create an empty texture

        this.setTransformMatrix(new Matrix4f().identity());
    }

    private ShaderProgram createShaders()
    {
        String vertexShaderSource = new String(Utils.loadFileData("res/shader/vertex.shd"));
        String fragmentShaderSource = new String(Utils.loadFileData("res/shader/fragment.shd"));

        ShaderProgram program = new ShaderProgram(vertexShaderSource, fragmentShaderSource);
        program.bind();
        program.setUniform("tex", 0);

        return program;
    }

    private void generateIndexes(int length)
    {
        int sizeInBytes = length * SPRITE_INDEX_COUNT * Integer.BYTES;

        this.indexBuffer.allocate(sizeInBytes);
        IntBuffer indexData = Allocator.allocate(sizeInBytes).asIntBuffer();

        for (int i = 0; i < length; i++)
        {
            indexData.put(new int[] {
               i, i + 1, i + 2,
               i, i + 2, i + 3
            });
        }

        indexData.flip();
        indexBuffer.setData(indexData, 0);
    }

    private void flush()
    {
        vertexData.flip();
        this.vertexBuffer.setData(vertexData, 0);

        this.vao.bind();
        this.program.bind();

        GL40.glActiveTexture(GL_TEXTURE0);
        this.currentTexture.bind();

        this.program.setUniform("transform", rawMatrix);

        int indicesCount = this.spriteCount * SPRITE_INDEX_COUNT;
        GL40.glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0);

        this.spriteCount = 0;
        this.vertexData.clear();
    }

    public void begin()
    {
        if (this.isDrawing)
            throw new IllegalStateException("The rendering has already begin, you must call end() before!");

        this.isDrawing = true;
        this.spriteCount = 0;

        GL40.glEnable(GL_BLEND);
        GL40.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        GL40.glDisable(GL_DEPTH_TEST);
        GL40.glDepthMask(false);
    }

    public void renderSprite(Sprite sprite, float x, float y)
    {
        if (!this.isDrawing)
            throw new IllegalStateException("Cannot render sprite! You must call begin() before!");

        if (sprite == null)
            throw new NullPointerException("Cannot render sprite! The sprite is invalid!");

        Texture texture = sprite.getTexture();
        if (texture != this.currentTexture)
        {
            if (this.currentTexture != null)
                this.flush();

            this.currentTexture = texture;
        }

        float w = (float)sprite.getSize().x;
        float h = (float)sprite.getSize().y;

        x = sprite.isFlipX() ? x + w : x;
        y = sprite.isFlipY() ? y + h : y;

        w = sprite.isFlipX() ? 0 - w : w;
        h = sprite.isFlipY() ? 0 - h : h;

        Color normColor = sprite.getColor().normalized();

        Rectangle region = sprite.getRegion();
        float u0 = (float)region.x / texture.getWidth();
        float v0 = (float)region.y / texture.getHeight();
        float u1 = (float)region.width / texture.getWidth();
        float v1 = (float)region.height / texture.getHeight();

        /*
           Coordinate:
               x1        x2
                 _________
                |        |
                |        |
                |        |
                ----------
               x0       x3
         */

        float[] data = new float[] {
            x, y,          normColor.r, normColor.g, normColor.b, normColor.a,    u0, v0,
            x, y + h,      normColor.r, normColor.g, normColor.b, normColor.a,    u0, v0 + v1,
            x + w, y + h,  normColor.r, normColor.g, normColor.b, normColor.a,    u0 + u1, v0 + v1,
            x + w, y,      normColor.r, normColor.g, normColor.b, normColor.a,    u0 + u1, v0
        };

        this.vertexData.put(data);

        this.spriteCount++;
    }

    public void end()
    {
        if (!this.isDrawing)
            throw new IllegalStateException("The rendering has already ended, you must call begin() before!");

        this.flush();

        GL40.glEnable(GL_DEPTH_TEST);
        GL40.glDepthMask(true);

        GL40.glDisable(GL_BLEND);

        this.isDrawing = false;
        this.currentTexture = null;
    }


    @Override
    public void dispose()
    {
        this.vertexBuffer.dispose();
        this.indexBuffer.dispose();

        Allocator.free(this.vertexData);
        Allocator.free(this.indexData);

        this.vao.dispose();
    }

    public int getSize()
    {
        return this.size;
    }

    public Matrix4f getTransformMatrix()
    {
        return this.transformMatrix;
    }

    public void setTransformMatrix(Matrix4f matrix)
    {
        if (matrix == null)
            throw new NullPointerException("Cannot set transform matrix! The matrix is invalid!");

        this.transformMatrix = matrix;
        this.rawMatrix = transformMatrix.get(rawMatrix);
    }
}