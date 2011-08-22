package com.example.android.samples.earthanimation;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EarthAnimationActivity extends Activity {

    private static final int DELAY = 40;

	private static final String TAG = "EarthAnimationActivity";

	private enum Direction {
		FORWARD, REVERSE
	};

	private Button mEarthButton;

	private AnimationDrawable mEarthButtonAnimation;

	private Direction mDirection = Direction.FORWARD;

	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

        mEarthButton = (Button)findViewById(R.id.earth_button);
        mEarthButtonAnimation = (AnimationDrawable)mEarthButton.getBackground();
        
        mEarthButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ( ! mEarthButtonAnimation.isRunning() ) {
					mEarthButtonAnimation.start();
					mEarthButton.setText(R.string.click_me_to_stop);
				}
				else {
					mEarthButtonAnimation.stop();
					final int resId;
					if ( mDirection == Direction.FORWARD ) {
						resId = R.anim.earth_animation_rev;
						mDirection = Direction.REVERSE;
					}
					else {
						resId = R.anim.earth_animation;
						mDirection = Direction.FORWARD;
					}
					mEarthButton.setBackgroundResource(resId);
					mEarthButtonAnimation = (AnimationDrawable) mEarthButton.getBackground();
					mEarthButton.setText(R.string.click_me_to_start);
				}
			}
        	
        });
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		(new Timer(false)).schedule(new AnimationTimer(mEarthButtonAnimation), DELAY);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mEarthButtonAnimation.stop();
	}

	private static class AnimationTimer extends TimerTask {
		AnimationDrawable animation;
		
		public AnimationTimer(AnimationDrawable animation) {
			this.animation = animation;
		}

		@Override
		public void run() {
			animation.start();
			this.cancel();
		}
		
	}
    
}