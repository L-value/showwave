package com.example.showwave;

import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private SurfaceHolder holder;
	private Paint paint;
	final int WIDTH = 420;
	final int HEIGHT = 420;
	final int X_OFFSET = 5;
	int cx = X_OFFSET;
	int centerY = HEIGHT/2;
	Timer timer = new Timer();
	TimerTask task = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.show);
		holder = surfaceView.getHolder();
		holder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				drawBack(holder);
				
			}
		});
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(3);
		Button sin = (Button) findViewById(R.id.sin);
		Button cos = (Button) findViewById(R.id.cos);
		sin.setOnClickListener(this);
		cos.setOnClickListener(this);
	}
	protected void drawBack(SurfaceHolder holder2) {
		Canvas canvas = holder2.lockCanvas();
		canvas.drawColor(Color.WHITE);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2);
		//»æÖÆ×ø±ê
		canvas.drawLine(X_OFFSET, centerY, WIDTH, centerY, paint);
		canvas.drawLine(X_OFFSET, 40, X_OFFSET, HEIGHT, paint);
		holder2.unlockCanvasAndPost(canvas);
		holder2.lockCanvas(new Rect(0,0,0,0));
		holder2.unlockCanvasAndPost(canvas);
	}
	@Override
	public void onClick(final View v) {
		drawBack(holder);
		cx = X_OFFSET;
		if (task != null) {
			task.cancel();
		}
		task = new TimerTask() {
			
			@Override
			public void run() {
				int cy = v.getId() == R.id.sin?centerY - (int)(100 * Math.sin((cx - 5)*2*Math.PI/150))
						:centerY - (int)(100*Math.cos((cx - 5)*2*Math.PI/150));
				Canvas canvas = holder.lockCanvas(new Rect(cx,cy-2,cx+2,cy+2));
				canvas.drawPoint(cx, cy, paint);
				cx++;
				if (cx > WIDTH) {
					task.cancel();
					task = null;
				}
				holder.unlockCanvasAndPost(canvas);
			}
		};
		timer.schedule(task, 0, 30);
		
		
//		switch (v.getId()) {
//		case R.id.sin:
//			
//			break;
//		case R.id.cos:
//			
//			break;
//		default:
//			break;
//		}
		
	}

}
