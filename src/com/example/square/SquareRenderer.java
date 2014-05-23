package com.example.square;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;

import java.lang.Math;

class SquareRenderer implements GLSurfaceView.Renderer {
	private boolean mTranslucentBackground;
	private Square[] mSquare = new Square[3];
	private Texture texture;
	private float mTransY;
	private float mTransAngle;
	private float mAngle;
	private Context context;

	private float vertices1[] = {

	-1.0f, -1.0f, 0.0f, // V1 - bottom left

			-1.0f, 1.0f, 0.0f, // V2 - top left

			1.0f, -1.0f, 0.0f, // V3 - bottom right

			1.0f, 1.0f, 0.0f // V4 - top right
	};
	private float vertices2[] = {

	-3.0f, -1.0f, 0.0f, // V1 - bottom left

			-3.0f, 1.0f, 0.0f, // V2 - top left

			-1.0f, -1.0f, 0.0f, // V3 - bottom right

			-1.0f, 1.0f, 0.0f // V4 - top right
	};
	private float vertices3[] = {

	1.0f, -1.0f, 0.0f, // V1 - bottom left

			1.0f, 1.0f, 0.0f, // V2 - top left

			3.0f, -1.0f, 0.0f, // V3 - bottom right

			3.0f, 1.0f, 0.0f // V4 - top right
	};

	public SquareRenderer(boolean useTranslucentBackground, Context c) {
		mTranslucentBackground = useTranslucentBackground;
		context = c;
		mSquare[0] = new Square(vertices1);
		mSquare[1] = new Square(vertices2);
		mSquare[2] = new Square(vertices3);
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		gl.glLoadIdentity();
		gl.glTranslatef((float) -Math.cos(mTransY), (float) -Math.sin(mTransY), -0.0f);
		mSquare[0].draw(gl);
		
		gl.glLoadIdentity();
		gl.glScalef((float) (Math.abs( Math.sin(mTransY))),(float) (Math.abs( Math.sin(mTransY))),1.0f);
		mSquare[1].draw(gl);
		
		gl.glLoadIdentity();
		gl.glRotatef(mTransAngle, 1, 1, 1); 
		mSquare[2].draw(gl);
		
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	

		mTransY += .035f;
		mTransAngle+=1;
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		// gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10); // perspective projection
		// (?)
		gl.glOrthof(-5, 5, -5, 5, 1, -1);// parallel projection
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
		mSquare[0].loadGLTexture(gl, this.context);
		mSquare[1].loadGLTexture(gl, this.context);
		mSquare[2].loadGLTexture(gl, this.context);

		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); // Black Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

}