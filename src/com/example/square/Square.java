package com.example.square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/**
 * A vertex shaded square.
 */
class Square {
	private FloatBuffer mFVertexBuffer;
	private ByteBuffer mColorBuffer;
	private ByteBuffer mIndexBuffer;
	private FloatBuffer textureBuffer;
	private int[] textures = new int[5];
	private float vertices[];

	private Vector2D pos;
	private Vector2D posTo = new Vector2D(0, 2.0f);

	private long lastTime = System.currentTimeMillis();
	private long maxFrameTime = 100; // in msec
	private long frameTime = 0;
	private int counter_frame = 0;

	private boolean running = false;
	private float yDir = 1.0f; // direction to the top
	private float velocity = 1.5f; // in unit/second
	private float space = 0.0f;


	public Square(float[] vertices, float pos_x, float pos_y) {

		this.vertices = vertices;
		pos = new Vector2D(pos_x, pos_y);

		float textureVertices[] = { 0.0f, 1.0f, // bottom left (V2)
				0.0f, 0.0f, // top left (V1)
				1.0f, 1.0f, // bottom right (V4)
				1.0f, 0.0f // top right (V3)
		};
		byte maxColor = (byte) 255;
		byte colors[] = { maxColor, maxColor, 0, maxColor, 0, maxColor,
				maxColor, maxColor, 0, 0, 0, maxColor, maxColor, 0, maxColor,
				maxColor };
		byte indices[] = { 0, 3, 1, 0, 2, 3 };
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mFVertexBuffer = vbb.asFloatBuffer();
		mFVertexBuffer.put(vertices);
		mFVertexBuffer.position(0);
		mColorBuffer = ByteBuffer.allocateDirect(colors.length);
		mColorBuffer.put(colors);
		mColorBuffer.position(0);
		mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
		mIndexBuffer.put(indices);
		mIndexBuffer.position(0);
		vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		textureBuffer = vbb.asFloatBuffer();
		textureBuffer.put(textureVertices);
		textureBuffer.position(0);
	}

	public void draw(GL10 gl) {

		// draw frame
		long now = System.currentTimeMillis();

		// update texture after frame time
		if (frameTime > maxFrameTime) {
			if (running)
				counter_frame++;
			frameTime = 0;
		} else
			frameTime += (now - lastTime);

		if (running) {
			float dy = velocity * (now - lastTime) / 1000;

			space += dy * yDir;
			pos.add(0, dy * yDir); // multiply by yDir for direction along y
									// axis

			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(0.0f, space, -0.0f);

			if (yDir > 0) {
				if (pos.y >= posTo.y) {
					running = false;

				}
			} else {
				if (pos.y <= posTo.y) {
					running = false;

				}
			}
		}

		// bind the previously generated texture
		if(running)
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[counter_frame % 4]);
		else
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);

		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		lastTime = now;

	}

	public void loadGLTexture(GL10 gl, Context context) {
		// loading texture
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mega1);

		Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mega2);

		// loading texture
		Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mega3);

		Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mega4);

		Bitmap bitmap5 = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.mega_stop);

		// // save bitmap dimension in pixel
		// bitmap_height = bitmap.getHeight();
		// bitmap_width = bitmap.getWidth();

		// generate one texture pointer
		gl.glGenTextures(5, textures, 0);

		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from
		// our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// second
		// texture-----------------------------------------------------------
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from
		// our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap2, 0);

		// third
		// texture-----------------------------------------------------------
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[2]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from
		// our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap3, 0);

		// fourth
		// texture-----------------------------------------------------------
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[3]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from
		// our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap4, 0);

		// five mega stop
		// texture-----------------------------------------------------------
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		// Use Android GLUtils to specify a two-dimensional texture image from
		// our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap5, 0);

		// Clean up
		bitmap.recycle();

	}

	public void moveRunning(float xTo, float yTo) {
		running = true;
		posTo = new Vector2D(xTo, yTo);
		if (pos.y > posTo.y)
			yDir = -1.0f;
		else
			yDir = 1.0f;
	}

}
