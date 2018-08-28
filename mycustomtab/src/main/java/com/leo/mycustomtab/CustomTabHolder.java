package com.leo.mycustomtab;

import android.content.Context;
import android.view.View;

/**
 * Created by leo on 2018/8/27.
 */
public interface CustomTabHolder<T> {
    /**
     * 创建view
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * @param context
     * @param position
     * @param datas
     */
    void onBind(Context context, int position, T datas);
}
