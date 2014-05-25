package com.example.square;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.RemoteViews.ActionException;

public class MainActivity extends Activity {

	private SquareRenderer sr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);

		// if (savedInstanceState == null) {
		// getFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment())
		// .commit();
		// }
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		GLSurfaceView Gview = new GLSurfaceView(this);
		sr = new SquareRenderer(true, getApplicationContext());
		Gview.setRenderer(sr);
		setContentView(Gview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			float x = event.getX();
			float y = event.getY();
//			Toast.makeText(getApplicationContext(), "x=" + x + " y=" + y,
//					Toast.LENGTH_SHORT).show();
			sr.moveSquare(x,y);
			break;

		default:
			break;
		}

		return true;
	}

}
