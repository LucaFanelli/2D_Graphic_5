package com.example.square;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Texture {
	int[] textureIds;
	GL10 gl;
	Square square;
	Context context;
	public Texture(Square s,Context a,GL10 g){
		gl=g;
		square=s;
		context=a;
		
		
	}
	
	
	void loadTexture(){
		textureIds = new int[1];
		gl.glGenTextures(1, textureIds, 0);
		int textureId = textureIds[0];
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.mega);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		bitmap.recycle();
		
	}

}


