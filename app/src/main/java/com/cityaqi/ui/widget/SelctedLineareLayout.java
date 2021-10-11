package com.cityaqi.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class SelctedLineareLayout extends LinearLayout implements OnClickListener{
	private int selectedPosition		=	-1;

	
	private OnItemSelectListener onItemSelectListener;
	

	public OnItemSelectListener getOnItemSelectListener() {
		return onItemSelectListener;
	}

	public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
		this.onItemSelectListener = onItemSelectListener;
	}


	public interface OnItemSelectListener
	{
		public void onSelect(int position, View childView, View view);
	}

	public SelctedLineareLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
	}

	public SelctedLineareLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SelctedLineareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelctedLineareLayout(Context context) {
		super(context);
	}

	private void setTabClick() 
	{
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.setTag(i);
			view.setOnClickListener(this);
			
		}
	}

	public void setselected(int position) {
		if(selectedPosition==-1)
			 setTabClick();
		selectedPosition		=	position;
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.setEnabled(!(i == position));
			setSetectd(view, i == position);
		}
	}
	public int getselected() 
	{
		return selectedPosition;
	}

	private void setSetectd(View view, boolean selected) {
		view.setSelected(selected);

		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View chieldView = ((ViewGroup) view).getChildAt(i);

				setSetectd(chieldView, selected);
			}
		}

	}

	@Override
	public void onClick(View v) {
		
		int position =Integer.parseInt(v.getTag().toString());
		setselected(position);
		if(onItemSelectListener!=null)
		{
			onItemSelectListener.onSelect(position, v, this);
		}
		
	}
	

}