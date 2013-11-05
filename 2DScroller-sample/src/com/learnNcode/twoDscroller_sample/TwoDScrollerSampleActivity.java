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

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Toast;

import com.learnNcode.twoDScroller.TwoDScrollerListview;

public class TwoDScrollerSampleActivity extends ListActivity {


	boolean reverse = true;
	private TwoDScrollerListview twoDScrollerListviewInstance;
	private ArrayList<String> itemList = new ArrayList<String>();
	private ScrollAdapter adapter = null;


	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.chatscroller_list_view);

		twoDScrollerListviewInstance = (TwoDScrollerListview)getListView();

		for(int i = 0; i < 20; i++){
			itemList.add("" + i);
		}
		adapter = new ScrollAdapter(TwoDScrollerSampleActivity.this, 0, 0, itemList, twoDScrollerListviewInstance);

		twoDScrollerListviewInstance.setDivider(null);
		twoDScrollerListviewInstance.endListAtCenter(getApplicationContext());
		twoDScrollerListviewInstance.startListFromCenter(getApplicationContext());
		setListAdapter(adapter);
		twoDScrollerListviewInstance.setTranslation(12);
		twoDScrollerListviewInstance.setDividerHeight(10);


	}

	public void showMessage(String message) {
		Toast.makeText(TwoDScrollerSampleActivity.this, message, Toast.LENGTH_SHORT).show();
	}

	public float converToPix(int val){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, this.getResources().getDisplayMetrics());
		return  px;
	}

}