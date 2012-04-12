package com.string;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.os.Message;
import android.util.Log;

import com.string.core.StringOGL;
import com.string.core.markers.MarkerInfoMatrixBased;
import com.walkin.walkin.StringOGLTutorial;

public class RendererES2 extends Renderer
{
	// Uniform index.
	private static final int UNIFORM_MVP   = 0;
	private static final int UNIFORM_COLOR = 1;
	private int[] uniforms = new int[2];

	// Attribute index.
	private static final int ATTRIB_VERTEX = 0;
	private static final int ATTRIB_NORMAL = 1;
	
	public boolean DEBUG = true;
	public static int isnum99999999=0;
	int num_s=0;
	private static final String vertShaderSrc = ""
		+ "uniform mat4 mvp;"
		+ "uniform vec4 color;"
		+ "attribute vec4 position;"
		+ "attribute vec3 normal;"
		+ "varying vec4 colorVarying;"
		+ "void main() {"
	    + "gl_Position = mvp * position;"
	    + "colorVarying = color * -(mvp * vec4(normal, 0)).z;"
	    + "}";
	
	private static final String fragShaderSrc = ""
		+ "varying lowp vec4 colorVarying;"
		+ "void main() {"
		+ "gl_FragColor = colorVarying;"
		+ "}";

	
	public RendererES2()
	{
		mCube = new CubeES2(0.2f);
	}
	
	private int compileShader(int type, String source)
	{
		if (source == null)
			throw new IllegalArgumentException("Invalid shader source (null).");
		
	    int shader = GLES20.glCreateShader(type);
	    GLES20.glShaderSource(shader, source);
	    GLES20.glCompileShader(shader);
	    
    	String log = GLES20.glGetShaderInfoLog(shader);
    	if (log != null)
    		Log.i("RendererES2.compileShader", "Shader compile log: " + log);
    	
    	int[] status = new int[1];
	    GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, status, 0);
	    if (status[0] == 0)
	    {
	        GLES20.glDeleteShader(shader);
	        return 0;
	    }
	    
	    return shader;
	}

	private boolean linkProgram(int prog)
	{
		GLES20.glLinkProgram(prog);
		String log = GLES20.glGetProgramInfoLog(prog);
	    if (log != null)
	    	Log.i("RendererES2.linkProgram", "Program link log: " + log);
	    
	    int[] status = new int[1];
	    GLES20.glGetProgramiv(prog, GLES20.GL_LINK_STATUS, status, 0);
	    return (status[0] != 0);
	}

	private boolean validateProgram(int prog)
	{
		GLES20.glValidateProgram(prog);
		String log = GLES20.glGetProgramInfoLog(prog);
		if (log != null)
	    	Log.i("RendererES2.linkProgram", "Program validate log: " + log);
	    
		int[] status = new int[1];
		GLES20.glGetProgramiv(prog, GLES20.GL_VALIDATE_STATUS, status, 0);
		return (status[0] != 0);
	}

	private int program = 0;
	private boolean loadShaders()
	{
		// Create shader program.
	    program = GLES20.glCreateProgram();
	    
	    // Create and compile vertex shader.
	    int vertShader = compileShader(GLES20.GL_VERTEX_SHADER, vertShaderSrc);
	    if(vertShader == 0)
	    {
	    	Log.w("RendererES2.loadShaders", "Failed to compile vertex shader.");
	    	return false;
	    }
	    
	    // Create and compile fragment shader.
	    int fragShader = compileShader(GLES20.GL_FRAGMENT_SHADER, fragShaderSrc);
	    if(fragShader == 0)
	    {
	    	Log.w("RendererES2.loadShaders", "Failed to compile fragment shader.");
	    	return false;
	    }
	    
	    // Attach vertex shader to program.
	    GLES20.glAttachShader(program, vertShader);
	    
	    // Attach fragment shader to program.
	    GLES20.glAttachShader(program, fragShader);
	    
	    // Bind attribute locations.
	    // This needs to be done prior to linking.
	    GLES20.glBindAttribLocation(program, ATTRIB_VERTEX, "position");
	    GLES20.glBindAttribLocation(program, ATTRIB_NORMAL, "normal");
	    
	    // Link program.
	    if (!linkProgram(program))
	    {
	        Log.w("RendererES2.loadShaders", "Failed to link program: " + program);
	        
	        if (vertShader != 0) GLES20.glDeleteShader(vertShader);
	    	if (fragShader != 0) GLES20.glDeleteShader(fragShader);
			if (program    != 0) GLES20.glDeleteProgram(program);

			vertShader = 0;
			fragShader = 0;
			program = 0;
	        return false;
	    }
	    
	    // Get uniform locations.
	    uniforms[UNIFORM_MVP  ] = GLES20.glGetUniformLocation(program, "mvp");
		uniforms[UNIFORM_COLOR] = GLES20.glGetUniformLocation(program, "color");
	    
	    // Release vertex and fragment shaders.
	    if (vertShader != 0)
	    	GLES20.glDeleteShader(vertShader);
	    if (fragShader != 0)
	    	GLES20.glDeleteShader(fragShader);
	    return true;
	}
	
	@SuppressWarnings("unused")
	private static float[] multMatrix(float[] a, float[] b)
	{
		if(a == null) return b;
		if(b == null) return a;
		
		float[] mult = new float[16];
		for (int j = 0; j < 16; j += 4)
		{
			for (int i = 0; i < 4; i++)
			{
				mult[j + i] = 0.0f;
				for (int k = 0, ks = 0; k < 4; k++, ks += 4)
					mult[j + i] += a[ks + i] * b[j + k];
			}
		}
		
		return mult;
	}
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		// NOTE: Do not use the gl argument, it is only passed to keep the compiler happy.
		
		if (mProjectionMatrix == null)
			return;
		
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glCullFace(GLES20.GL_BACK);
		GLES20.glFrontFace(GLES20.GL_CW);
		
		GLES20.glViewport(
			mViewport[0], mViewport[1],
			mViewport[2], mViewport[3]);
		
		GLES20.glUseProgram(program);
		
		// Validate program before drawing. This is a good check, but only really necessary in a debug build.
		// DEBUG macro must be defined in your debug configurations if that's not already the case.
		if (DEBUG && !validateProgram(program))
		{
			Log.w("RendererES2.onDrawFrame", "Failed to validate program: " + program);
			return;
		}
		
		for (MarkerInfoMatrixBased marker : StringOGL.getDataMatrixBased(10))
		{
			/*float[] translationMatrix = new float[] {
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, -mCube.scale(), 1.0f};
			float[] mvpMatrix = multMatrix(mProjectionMatrix, multMatrix(marker.transform, translationMatrix));
			GLES20.glUniformMatrix4fv(uniforms[UNIFORM_MVP], 1, false, mvpMatrix, 0);

			float[] diffuse = new float[4];
			diffuse[marker.imageID % 3] = 1f;
			GLES20.glUniform4fv(uniforms[UNIFORM_COLOR], 1, diffuse, 0);*/
//			Log.w("Rock", marker.uniqueInstanceID+"bbbbbbbbb");
			
			 int uniID =marker.uniqueInstanceID;
			// unque=isnum99999999++;
			isNumMarker(marker.imageID,uniID);
			mCube.draw();
		}
	}
	
	public int isNumMarker(int num,int uniID) {
		
		num_s=isnum99999999++;
		Log.e("Rock", num_s + "nnnnnnnnnnnnnnnnnn");
			if (num_s==1) {
    			Message message = Message.obtain(StringOGLTutorial.getFrameMarkersBackHandler(),num);
    			message.sendToTarget();
			//	Log.d("Rock", "111111");
    		}
		return num;

	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		if (!loadShaders())
			throw new RuntimeException("Failed to load shaders, can't render tutorial.");
		GLES20.glEnable(GLES20.GL_DITHER);
	}
}
