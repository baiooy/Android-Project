package com.string;

import android.opengl.GLES20;

public class CubeES2 extends Cube
{
	public CubeES2(float scale)
	{
		super(scale);
	}
	
	public void draw()
	{
		GLES20.glEnableVertexAttribArray(0);
		GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, mVertices);
		GLES20.glEnableVertexAttribArray(1);
		GLES20.glVertexAttribPointer(1, 3, GLES20.GL_FLOAT, false, 0, mNormals);
	
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_SHORT, mIndices);
	}
}
