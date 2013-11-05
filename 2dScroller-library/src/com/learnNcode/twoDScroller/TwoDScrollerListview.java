/*
 * Copyright 2013 - learnNcode (learnncode@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */


package com.learnNcode.twoDScroller;


import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ListView;

public class TwoDScrollerListview extends ListView {

	private int displayHeight;

	/* Size of internal Views */
	private float mChildRatio = 0.9f;

	/* Height of all children */
	private int mChildrenHeight;

	/* Height / 2 */
	private int mChildrenHeightMiddle;

	/* Height center of the ViewGroup */
	private int mHeightCenter;


	/* Number of pixel between the top of two Views */
	private int mSpaceBetweenViews = 20;

	/* Translation between two Views*/
	private int mTranslate = 12;

	/* Status of translation */
	private boolean mTranslatateEnbabled = false;

	/** Paint object to draw with */
	private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);


	public TwoDScrollerListview(Context context, AttributeSet attrs) {
		super(context, attrs);

		mPaint.setAntiAlias(true);

		setStaticTransformationsEnabled(true);

		displayHeight = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();

	}

	/**
	 * To start list from center.
	 * 
	 * @param context
	 * 				{@link Context}.
	 */
	public void startListFromCenter(Context context) {

		View dummyHeader = new View(context);
		AbsListView.LayoutParams dummyHeaderParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, (displayHeight/2  - 70)); 
		dummyHeader.setBackgroundColor(Color.TRANSPARENT);
		dummyHeader.setLayoutParams(dummyHeaderParams);
		addHeaderView(dummyHeader);
	}

	/**
	 * To end list at center.
	 * 
	 * @param context
	 * 					{@link Context}.
	 */
	public void endListAtCenter(Context context) {
		View dummyFooter = new View(context);
		AbsListView.LayoutParams dummyFooterParams = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, (displayHeight/2 - 70)); /////
		dummyFooter.setBackgroundColor(Color.TRANSPARENT);
		dummyFooter.setLayoutParams(dummyFooterParams);
		addFooterView(dummyFooter);
	}


	/**
	 * Define translation between 2 views
	 * 
	 * @param translate
	 * 					{@link integer} value.
	 */
	public void setTranslation(int translate) {
		mTranslatateEnbabled = true;
		mTranslate = translate;
	}

	/**
	 * To disable translation.
	 */
	public void disableTranslate(){
		mTranslatateEnbabled = false;
	}


	/**
	 * To play sound.
	 * 
	 * @param context
	 * 					{@link Context}
	 * 
	 * @param soundID
	 * 					sound id.
	 * 	
	 * @param soundPool
	 * 					{@link SoundPool} instance.
	 * 
	 */
	public void playSound(Context context, int soundID, SoundPool soundPool){
		OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {
			public void onAudioFocusChange(int focusChange) {
				if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
					// Lower the volume
				} else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
					// Raise it back to normal
				}
			}
		};
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

		// Request audio focus for playback
		int result = audioManager.requestAudioFocus(afChangeListener,
				// Use the music stream.
				AudioManager.STREAM_MUSIC,
				// Request permanent focus.
				AudioManager.AUDIOFOCUS_GAIN);

		if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
			// Start playback.
			soundPool.play(soundID, 10, 10, 1, 0,1f );
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		final int specWidthSize  = MeasureSpec.getSize(widthMeasureSpec);
		final int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

		mHeightCenter   = (int) (specHeightSize * mChildRatio);
		mChildrenHeight = (int) (specHeightSize * mChildRatio);

		mChildrenHeightMiddle = mChildrenHeight/2;
		setMeasuredDimension(specWidthSize, specHeightSize);
	}



	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {

		int topCenterView = mHeightCenter - mChildrenHeightMiddle;
		int childTop      = Math.max(0,child.getTop());
		float offset      = (-childTop + topCenterView)/ (float) mSpaceBetweenViews;
		final Matrix matrix = t.getMatrix();
		if (offset != 0) {
			float absOffset = Math.abs(offset);
			t.clear();
			t.setTransformationType(Transformation.TYPE_MATRIX);

			float px = child.getLeft() + (child.getWidth()) / 2;
			float py = child.getTop() + (child.getHeight()) / 2; 

			if (mTranslatateEnbabled){
				matrix.setTranslate(mTranslate * absOffset, 0);
			}

			if (offset > 0) {
				matrix.preTranslate(-px,0);
				matrix.postTranslate(px,0);
			} else {
				matrix.preTranslate(-px, -py);
				matrix.postTranslate(px, py);
			}
		}
		return true;
	}

}
