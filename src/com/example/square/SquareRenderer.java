package com.example.square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.widget.Toast;

class SquareRenderer implements GLSurfaceView.Renderer {

	private Square[] mSquare = new Square[3];
	private Context context;
	
	private int screenWidth;
	private int screenHeight;

	private float vertices1[] = {

			4.0f, 4.0f, 0.0f, // V1 - bottom left

			4.0f, 6.0f, 0.0f, // V2 - top left

			6.0f, 4.0f, 0.0f, // V3 - bottom right

			6.0f, 6.0f, 0.0f // V4 - top right
	};

	public SquareRenderer(Context c) {

		context = c;
		mSquare[0] = new Square(vertices1,5.0f,5.0f);
	}

	public void onDrawFrame(GL10 gl) {
		
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		mSquare[0].draw(gl);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		screenWidth = width;
		screenHeight = height;
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, 10, 0, 10, 1, -1);// parallel projection
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
		mSquare[0].loadGLTexture(gl, this.context);

		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	
	public void moveSquare(float touchX, float touchY) {
		
		float xTo;
		float yTo;
		
		// conversion from touch coordinates to world coordinates
		xTo = (touchX/(float)screenWidth)*10.0f; // multiply by frustum width
		yTo = (1-touchY/(float)screenHeight)*10.0f;
		
		Toast.makeText(context,
				"height="+yTo+" width="+xTo,
				Toast.LENGTH_SHORT).show();
		
		mSquare[0].moveRunning(xTo,yTo);
	}
	
	

}