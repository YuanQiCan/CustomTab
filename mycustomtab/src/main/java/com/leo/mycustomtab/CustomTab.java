package com.leo.mycustomtab;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2018/8/27.
 */
public class CustomTab<T> extends LinearLayout implements View.OnClickListener{

    private List<View> mTabView;
    private List<T> mTabDatas;
    private OnTabCheckListener mOnTabCheckListener;

    private CustomTabHolderCreator mCreator;

    private CustomTabHolder mHolder;

    private int OnCurrentPosition =0;


    public CustomTab(Context context) {
        super(context);
        init();
    }

    public CustomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init()
    {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        mTabView = new ArrayList<>();
        mTabDatas = new ArrayList<>();
    }

    public void setPagers(List<T> data,CustomTabHolderCreator creator)
    {

        mHolder = creator.createCustomHolder();
        for (int i = 0 ; i < data.size(); i++)
        {
            View view = mHolder.createView(getContext());
            mHolder.onBind(getContext(),i,data.get(i));


            view.setTag(mTabView.size());
            view.setOnClickListener(this);

            mTabView.add(view);
            mTabDatas.add(data.get(i));

            addView(view);
        }


    }



    public void setCurrentItem(int position)
    {
        if (position >= mTabDatas.size() || position<0)
        {
            position = 0;
        }
        mTabView.get(position).performClick();//**************************

        updatestate(position);

    }

    private void updatestate(int position) //更新状态
    {

        mOnTabCheckListener.onUpdateCheckState(position,mTabView,mTabDatas);
    }

    public void onUpdateTabView(List<T> TabDatas){
        mTabDatas = TabDatas;
        mOnTabCheckListener.onUpdateCheckState(OnCurrentPosition,mTabView,mTabDatas);
    }

    public interface OnTabCheckListener<T>{
        public void onTabSelected(View v, int position);

        public void onUpdateCheckState(int position, List<View> TabViews, List<T> TabDatas);

    }



    /**
     * 注册监听点击函数
     * @param onTabCheckListener
     */
    public void setOnTabCheckListener(OnTabCheckListener onTabCheckListener)
    {
        mOnTabCheckListener = onTabCheckListener;
    }

    @Override
    public void onClick(View v) { //单击事件， 点击后，更新点击 者的状态，并更换页面内容
        int position = (int) v.getTag();
        OnCurrentPosition = position;
        if (mOnTabCheckListener != null)
        {
            mOnTabCheckListener.onTabSelected(v,position); //并更换内容
            Toast.makeText(getContext(),position+"", Toast.LENGTH_SHORT).show();
        }

        updatestate(position); //更新底部状态
    }

    @Override
    protected void onDetachedFromWindow() { //************************************
        super.onDetachedFromWindow();
        if (mTabView != null)
        {
            mTabView.clear();
        }
        if (mTabView != null)
        {
            mTabDatas.clear();
        }
    }

    @Override
    protected void onAttachedToWindow() {//******************************************
        super.onAttachedToWindow();

        for (int i = 0 ; i < mTabView.size();i++)
        {
            View view = mTabView.get(i);
            int width = getResources().getDisplayMetrics().widthPixels/(mTabDatas.size());
            LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
        }
    }
}
