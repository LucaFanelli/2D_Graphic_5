package com.example.square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

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
	private int[] textures = new int[4];
	private float vertices[];
	private int bitmap_height;
	private int bitmap_width;

	private long lastTime = 0;
	private long frameTime = 100;
	private int counter_frame = 0;

	public Square(float[] vertices) {
		this.vertices = vertices;

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

		long now = System.currentTimeMillis();
		
		// update texture after frame time
		if(now-lastTime>frameTime) {
			counter_frame++;
			lastTime = now;
		}

		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[counter_frame % 4]);

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

		// // save bitmap dimension in pixel
		// bitmap_height = bitmap.getHeight();
		// bitmap_width = bitmap.getWidth();

		// generate one texture pointer
		gl.glGenTextures(4, textures, 0);

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

		// Clean up
		bitmap.recycle();

	}

	// private float[] generateTexCoord(float x, float y, float width, float
	// height) {
	//
	// float[] ret = new float[4 * 2];
	// ret[0] = x / bitmap_width; // top left corner
	// ret[1] = y / bitmap_height;
	//
	// ret[2] = x / bitmap_width; // bottom left corner
	// ret[3] = ret[1] + height / bitmap_height;
	//
	// ret[4] = ret[0] + width / bitmap_width; // top right corner
	// ret[5] = y / bitmap_height;
	//
	// ret[6] = ret[0] + width / bitmap_width; // bottom right corner
	// ret[7] = ret[1] + height / bitmap_height;
	//
	// return ret;
	// }
	//
	// private int getKeyFrameNumber(float stateTime) {
	//
	// float frame_duration = 1000.0f;
	// int ret = (int) (stateTime/frame_duration);
	// return ret%2;
	// }

}
