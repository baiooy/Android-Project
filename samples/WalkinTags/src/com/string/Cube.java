package com.string;

import java.nio.*;
import android.opengl.GLES10;

public class Cube
{
	protected FloatBuffer mVertices, mNormals;
	protected ShortBuffer mIndices;
	protected float       mCubeScale;
	
	public Cube(float scale)
	{
		mCubeScale = scale;
		
		float[] cornerVertices = new float[8 * 3];
		
		ByteBuffer verticesBB = ByteBuffer.allocateDirect(6 * 4 * 3 * 4);
		verticesBB.order(ByteOrder.nativeOrder());
		mVertices = verticesBB.asFloatBuffer();
		ByteBuffer normalsBB = ByteBuffer.allocateDirect(6 * 4 * 3 * 4);
		normalsBB.order(ByteOrder.nativeOrder());
		mNormals = normalsBB.asFloatBuffer();
        ByteBuffer indicesBB = ByteBuffer.allocateDirect(36 * 2);
        indicesBB.order(ByteOrder.nativeOrder());
        mIndices = indicesBB.asShortBuffer();
		
    	for (int i = 0; i < 8; i++)
    	{
    		cornerVertices[i * 3 + 0] = ((i & 1       ) * 2f - 1f) * mCubeScale;
    		cornerVertices[i * 3 + 1] = (((i >> 1) & 1) * 2f - 1f) * mCubeScale;
    		cornerVertices[i * 3 + 2] = ((i >> 2      ) * 2f - 1f) * mCubeScale;
    	}
    	
    	for (int i = 0; i < 6; i++)
    	{
    		int firstAxis = i % 3;
    		int secondAxis = (i + 1) % 3;
    		int thirdAxis = (i + 2) % 3;
    		int side = i / 3;
    		
    		for (int j = 0; j < 4; j++)
    		{
    			int cornerIndex = ((j & 1) << firstAxis) | ((j >> 1) << secondAxis) | (side << thirdAxis);
    			
    			for (int k = 0; k < 3; k++)
    			{
    				mVertices.put((i * 4 + j) * 3 + k, cornerVertices[cornerIndex * 3 + k]);
    				mNormals.put((i * 4 + j) * 3 + k, 0f);
    			}
    			
    			mNormals.put((i * 4 + j) * 3 + thirdAxis, side * 2f - 1f);
    		}
    		
    		for (int j = 0; j < 3; j++)
    		{
    			mIndices.put(i * 6 + j, (short)(i * 4 + j ^ side));
    			mIndices.put(i * 6 + 3 + j, (short)(i * 4 + (3 - j) ^ side));
    		}
    	}
	}
	
	public void draw()
	{
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, mVertices);
		GLES10.glEnableClientState(GLES10.GL_NORMAL_ARRAY);
		GLES10.glNormalPointer(GLES10.GL_FLOAT, 0, mNormals);
	
        GLES10.glDrawElements(GLES10.GL_TRIANGLES, 36, GLES10.GL_UNSIGNED_SHORT, mIndices);
	}
	
	public float scale()
	{
		return mCubeScale;
	}
}
