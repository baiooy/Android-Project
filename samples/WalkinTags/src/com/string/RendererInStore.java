package com.string;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.string.core.StringOGL;
import com.string.core.StringRenderer;
import com.string.core.markers.MarkerInfoMatrixBased;
import com.walkin.walkin.StringOGLTutorialInStore;

import android.opengl.GLES10;
import android.os.Message;
import android.util.Log;

public class RendererInStore implements StringRenderer
{
	protected Cube mCube;
	public static int isnum99999999=0;
	int num_s=0;
	public RendererInStore()
	{
		mCube = new Cube(0.2f);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		// NOTE: Do not use the gl argument, it is only passed to keep the compiler happy.
		
		if (mProjectionMatrix == null)
			return;
		
		GLES10.glViewport(
			mViewport[0], mViewport[1],
			mViewport[2], mViewport[3]);
	
		GLES10.glMatrixMode(GLES10.GL_PROJECTION);
		GLES10.glPushMatrix();
		GLES10.glLoadMatrixf(mProjectionMatrix, 0);
		GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
		GLES10.glPushMatrix();
		
		GLES10.glEnable(GLES10.GL_DEPTH_TEST);
		GLES10.glEnable(GLES10.GL_CULL_FACE);
		GLES10.glCullFace(GLES10.GL_BACK);
		GLES10.glFrontFace(GLES10.GL_CW);
		GLES10.glEnable(GLES10.GL_LIGHTING);
		GLES10.glEnable(GLES10.GL_LIGHT0);

		for (MarkerInfoMatrixBased marker : StringOGL.getDataMatrixBased(10))
		{
		/*	float[] diffuse = new float[4];
			diffuse[marker.imageID % 3] = 1f;
			
			GLES10.glLightfv(GLES10.GL_LIGHT0, GLES10.GL_DIFFUSE, diffuse, 0);

			GLES10.glDisable(GL10.GL_TEXTURE_2D);
	
			GLES10.glLoadIdentity();
				
			GLES10.glMultMatrixf(marker.transform, 0);
			GLES10.glTranslatef(0, 0, -mCube.scale());
			GLES10.glColor4f(marker.color[0], marker.color[1], marker.color[2], 1.0f);*/
			 int uniID =marker.uniqueInstanceID;
			isNumMarker(marker.imageID,uniID);
			mCube.draw();
		}
		
		GLES10.glPopMatrix();
		GLES10.glMatrixMode(GLES10.GL_PROJECTION);
		GLES10.glPopMatrix();
		GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
	}
	public int isNumMarker(int num,int uniID) {
		num_s=isnum99999999++;
		Log.e("Rock", num_s + "nnnnnnnnnnnnnnnnnn");
			if (num_s==1) {
    			Message message = Message.obtain(StringOGLTutorialInStore.getFrameMarkersBackHandler(),num);
    			message.sendToTarget();
			//	Log.d("Rock", "111111");
    		}
		return num;

	}
	protected float[] mProjectionMatrix = null;
	public void setProjectionMatrix(float[] matrix)
	{
		mProjectionMatrix = matrix;
	}
	
	protected int mViewport[] = { 0, 0, 0, 0 };
	public void setViewport(int x, int y, int width, int height)
	{
		mViewport[0] = x;
		mViewport[1] = y;
		mViewport[2] = width;
		mViewport[3] = height;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// Not used, required for compilation.
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		GLES10.glEnable(GLES10.GL_DITHER);
	}
	
	@Override
	public void onSurfaceDestroyed()
	{
		// Normally we'd de-initialize all our textures and GL context related data here.
	}

}
