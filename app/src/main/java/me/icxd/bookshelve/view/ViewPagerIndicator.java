package me.icxd.bookshelve.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import me.icxd.bookshelve.R;

/**
 * ViewPagerIndicator
 * Created by HaPBoy on 5/10/16.
 */
public class ViewPagerIndicator extends HorizontalScrollView {

    private Context mContext;

    private List<String> mTitles; // 接收传递过来的title
    private ViewPager mViewPager; // 接收关联的ViewPager

    private LinearLayout llTabRoot; // Tab布局
    private RelativeLayout rlRoot; // 根布局
    private View vLine; // 横线
    private RelativeLayout.LayoutParams lineLayoutParams;

    private int mTabVisibleCount; // 可见tab的数量
    private int mSizeText; // tab标签文字大小(sp)
    private int mColorTextNormal; // 正常字体颜色
    private int mColorTextHighlight; // 高亮字体颜色
    private int mColorLine; // 横线颜色
    private int mHeightLine; // 线高(dp)

    private static final int COUNT_DEFAULT_TAB = 4; // 默认可见tab为4个
    private static final int SIZE_TEXT = 16; // 默认tab标签文字大小(sp)
    private static final int COLOR_TEXT_NORMAL = Color.parseColor("#000000"); // 默认正常字体颜色
    private static final int COLOR_TEXT_HIGHLIGHT = Color.parseColor("#FFFFFF"); // 默认高亮字体颜色
    private static final int COLOR_LINE = Color.parseColor("#000000"); // 默认横线颜色
    private static final int HEIGHT_LINE = 2; // 默认线高(dp)

    public ViewPagerIndicator(Context context) {
        super(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 获取XML中的配置属性
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = attributes.getInt(R.styleable.ViewPagerIndicator_tab_visible_count, COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0) {
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }
        mSizeText = attributes.getInt(R.styleable.ViewPagerIndicator_tab_text_size, SIZE_TEXT);
        mColorTextNormal = attributes.getInt(R.styleable.ViewPagerIndicator_tab_text_normal_color, COLOR_TEXT_NORMAL);
        mColorTextHighlight = attributes.getInt(R.styleable.ViewPagerIndicator_tab_text_highlight_color, COLOR_TEXT_HIGHLIGHT);
        mColorLine = attributes.getInt(R.styleable.ViewPagerIndicator_tab_line_color, COLOR_LINE);
        mHeightLine = attributes.getInt(R.styleable.ViewPagerIndicator_tab_line_height, HEIGHT_LINE);
        attributes.recycle();

        initViews();
    }

    private void initViews() {
        setHorizontalScrollBarEnabled(false);

        // 根布局
        rlRoot = new RelativeLayout(mContext);
        rlRoot.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.addView(rlRoot);

        // Tab布局
        llTabRoot = new LinearLayout(mContext);
        llTabRoot.setOrientation(LinearLayout.HORIZONTAL);
        llTabRoot.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        rlRoot.addView(llTabRoot);

        // 横线
        vLine = new View(mContext);
        lineLayoutParams = new RelativeLayout.LayoutParams(getScreenWidth() / mTabVisibleCount, DpToPx(mHeightLine));
        lineLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        vLine.setLayoutParams(lineLayoutParams);
        vLine.setBackgroundColor(mColorLine);
        rlRoot.addView(vLine);
    }

    /**
     * xml加载完成之后，回调此方法
     * 设置每个tab的LayoutParams
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = llTabRoot.getChildCount();
        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            View view = llTabRoot.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(params);
        }

        // 横线长度
        lineLayoutParams.width = getScreenWidth() / mTabVisibleCount;
        vLine.setLayoutParams(lineLayoutParams);
    }

    /**
     * 动态设置tab的数量
     *
     * @param count
     */
    public void setVisibleTabCount(int count) {
        mTabVisibleCount = count;
        onFinishInflate();
    }

    /**
     * 动态设置tab
     *
     * @param titles
     */
    public void setTabItemTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            llTabRoot.removeAllViews();
            mTitles = titles;
            for (String title : mTitles) {
                llTabRoot.addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    /**
     * 根据title创建tab
     *
     * @param title
     * @return view
     */
    private View generateTextView(String title) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getScreenWidth() / mTabVisibleCount, LayoutParams.MATCH_PARENT);

        TextView textView = new TextView(getContext());
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mSizeText);
        textView.setTextColor(mColorTextNormal);
        textView.setLayoutParams(params);
        return textView;
    }


    /**
     * 跟随ViewPager移动
     *
     * @param position
     * @param positionOffset
     */
    public void scroll(int position, float positionOffset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        // 当Tab处于移动至最后一个时
        // position + 1 - (mTabVisibleCount - 1) + positionOffset
        if (position >= (mTabVisibleCount - 2) && positionOffset > 0 && llTabRoot.getChildCount() > mTabVisibleCount) {
            if (mTabVisibleCount != 1) {
                scrollTo((int) ((position - (mTabVisibleCount - 2) + positionOffset) * tabWidth), 0);
            } else {
                scrollTo((int) ((position + positionOffset) * tabWidth), 0);
            }
        }

        // 移动横线
        lineLayoutParams.leftMargin = (int) ((position + positionOffset) * tabWidth);
        vLine.setLayoutParams(lineLayoutParams);
    }

    /**
     * 设置关联的ViewPager
     *
     * @param viewpager
     * @param position
     */
    public void setViewPager(ViewPager viewpager, int position) {
        mViewPager = viewpager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滑动Tab和横线
                scroll(position, positionOffset);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(position);
        highLightTextView(position);
    }

    /**
     * 高亮被点击的tab
     *
     * @param position
     */
    private void highLightTextView(int position) {
        resetTextViewColor();
        View view = llTabRoot.getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mColorTextHighlight);
        }
    }

    /**
     * 重置tab文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < llTabRoot.getChildCount(); i++) {
            View view = llTabRoot.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(mColorTextNormal);
            }
        }
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent() {
        int childCount = llTabRoot.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int j = i;
            View view = llTabRoot.getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

    /**
     * 获取屏幕的宽度
     *
     * @return screenWidth
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 将DP转换为PX
     *
     * @param dp 要转换的像素无关单位
     * @return int
     */
    private int DpToPx(double dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
