package cdavis10.ggc.edu.blackjack;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

public class YouWinActivity extends Activity {
	AnimationDrawable coinAnimation;
	MediaPlayer mpCoin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_win);
		ImageView coinFrame = (ImageView) findViewById(R.id.imgCoinAnimation);
		coinFrame.setBackgroundResource(R.drawable.falling_chips_animation);
		coinAnimation = (AnimationDrawable) coinFrame.getBackground();
		coinAnimation.start();
		
		mpCoin = MediaPlayer.create(this, R.raw.coin_drop);
		mpCoin.start();
		
		
		
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				finish();
			}
		};
		Timer opening = new Timer();
		opening.schedule(task, 5000);
	}
}
