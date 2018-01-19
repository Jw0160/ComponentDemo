package com.common.common_base.widget.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 弹出框的ViewHolder
 * 用于设置数据，设置资源
 */
	public class DialogViewHolder{
	private final SparseArray<View> mViews;
	private View mDialogView;

	private DialogViewHolder(Context context, int layoutId) {
		this.mViews = new SparseArray();
		mDialogView = View.inflate(context, layoutId, null);
	}

	public static DialogViewHolder get(Context context, int layoutId) {
		return new DialogViewHolder(context, layoutId);
	}

	public View getConvertView() {
		return mDialogView;
	}

	/**
	 * Set the string for TextView
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public DialogViewHolder setText(int viewId, CharSequence text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * Set the string for TextView
	 *
	 * @param viewId
	 * @param res
	 * @return
	 */
	public DialogViewHolder setText(int viewId, int res) {
		TextView view = getView(viewId);
		view.setText(res);
		return this;
	}

	/**
	 * 设置图片背景资源
	 * @param viewId   VIEW ID
	 * @param resouceId 资源ID
	 * @return
	 */
	public DialogViewHolder setImageBackgroundResource(int viewId, int resouceId) {
		ImageView image = getView(viewId);
		image.setBackgroundResource(resouceId);
		return this;
	}
	
	/**
	 * 设置图片资源
	 * @param viewId   VIEW ID
	 * @param resouceId 资源ID
	 * @return
	 */
	public DialogViewHolder setImageResource(int viewId, int resouceId) {
		ImageView image = getView(viewId);
		image.setImageResource(resouceId);
		return this;
	}

	/**
	 * set text view visible
	 * 
	 * @param viewId 资源id
	 * @return
	 */
	public DialogViewHolder setViewInViSible(int viewId) {
		View view = getView(viewId);
		view.setVisibility(View.INVISIBLE);
		return this;
	}

	/**
	 * set text view visible
	 * 
	 * @param viewId 资源id
	 * @return
	 */
	public DialogViewHolder setViewViSible(int viewId) {
		View view = getView(viewId);
		view.setVisibility(View.VISIBLE);
		return this;
	}

	/**
	 * set view gone
	 * 
	 * @param viewId 资源id
	 * @return
	 */
	public DialogViewHolder setViewGone(int viewId) {
		View view = getView(viewId);
		view.setVisibility(View.GONE);
		return this;
	}
	/**
	 * set view enable
	 *
	 * @param viewId 资源id
	 * @return
	 */
	public DialogViewHolder setViewEnable(int viewId,boolean enable) {
		View view = getView(viewId);
		view.setEnabled(enable);
		return this;
	}

	/**
	 * 绑定view的点击事件
	 * @param viewId
	 * @param onClick
	 * @return
	 */
	public DialogViewHolder setOnClick(int viewId, OnClickListener onClick) {
		View view = getView(viewId);
		view.setOnClickListener(onClick);
		return this;
	}

	/**
	 * Through control the Id of the access to control, if not join views
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mDialogView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
}
