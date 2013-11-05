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


package com.learnNcode.twoDscroller_sample;

import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.learnNcode.twoDScroller.TwoDScrollerListview;

public class ScrollAdapter extends ArrayAdapter<String> {

	private Context context;
	private int soundID;
	private SoundPool soundPool;
	private List<String> objects;
	private TwoDScrollerListview twoDScrollerListviewInstance;

	public ScrollAdapter(Context context, int resource, int textViewResourceId, List<String> objects, TwoDScrollerListview twoDScrollerListviewInstance) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
		this.twoDScrollerListviewInstance = twoDScrollerListviewInstance;
		// Load the sound              
		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);         
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {                
			@Override                
			public void onLoadComplete(SoundPool soundPool, int sampleId,    
					int status) {                            
			}             
		});              
		soundID = soundPool.load(context, R.raw.tock, 1);

	}


	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if(convertView ==  null){
			convertView = LayoutInflater.from(context).inflate(R.layout.row, parent,false);
		}

		TextView label = (TextView)convertView.findViewById(R.id.displayText);
		label.setText(objects.get(position));

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Clicked on " + position, Toast.LENGTH_SHORT).show();
			}
		});

		//To play sound
		twoDScrollerListviewInstance.playSound(context, soundID, soundPool);

		return convertView;
	}
}
