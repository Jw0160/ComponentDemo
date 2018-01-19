package com.common.common_base.widget.dialog.loading;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.common.common_base.R;

public class LodingDataView extends LinearLayout {

	private LoadingView loadingView;
	
	public LodingDataView(Context context) {
		this(context, null);
	}

	public LodingDataView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.dialog_loding,this);
		initView();
	}

	/**
	 * initialization
	 */
	private void initView() {
		loadingView = (LoadingView) findViewById(R.id.progress_view);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return ((Activity) getContext()).dispatchKeyEvent(event);
	}

	public void gc(){
		loadingView.setVisibility(GONE);
		loadingView = null;
	}
}