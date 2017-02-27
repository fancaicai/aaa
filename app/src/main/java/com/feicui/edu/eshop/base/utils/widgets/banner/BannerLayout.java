package com.feicui.edu.eshop.base.utils.widgets.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.feicui.edu.eshop.R;

/**
 * 自定义的轮播图控件
 * 1. 自动轮播
 * 2. 数据可随意设置(适配器的问题)
 * 3. 自动和手动的冲突
 */

public class BannerLayout extends RelativeLayout {
    // 代码中使用控件
    public BannerLayout(Context context) {
        super(context);
        init(context);
    }
    // 布局中使用，但是未设置style
    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }
    // 设置了style
    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    // 布局的填充和初始化相关
    private void init(Context context) {
// Merge标签一定要设置ViewGroup和attachToRoot为true
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout,this,true);
    }
}
