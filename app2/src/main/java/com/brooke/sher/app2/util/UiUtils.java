package com.brooke.sher.app2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * UI工具集
 */
public final class UiUtils {
    private UiUtils() {
    }

    /**
     * view中单位换算 px转dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * view中单位换算 dip转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 测量View的宽和高
     * <p/>
     * 例： measureView(headView); headContentHeight = headView.getMeasuredHeight(); headContentWidth = headView.getMeasuredWidth();
     *
     * @param child
     */
    public static void measureView(View child) {
        LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 得到所有叶子节点的View列表
     *
     * @param root
     * @return
     */
    public static List<View> getViewGroupAllLeafs(ViewGroup root) {

        List<View> ret = new ArrayList<View>();

        if (root.getChildCount() != 0) {

            for (int i = 0; i < root.getChildCount(); i++) {

                try {
                    ViewGroup node = (ViewGroup) root.getChildAt(i);
                    ret.addAll(getViewGroupAllLeafs(node));
                } catch (Exception e) {// 非ViewGroup 为View
                    ret.add(root.getChildAt(i));
                }
            }
        }
        return ret;
    }

    /**
     * 得到所有子节点的View列表 (包括中间节点)
     *
     * @param root
     * @return
     */
    public static List<View> getViewGroupAll(ViewGroup root) {

        List<View> ret = new ArrayList<View>();

        if (root.getChildCount() != 0) {

            for (int i = 0; i < root.getChildCount(); i++) {

                try {
                    ret.add(root.getChildAt(i));
                    ViewGroup node = (ViewGroup) root.getChildAt(i);
                    ret.addAll(getViewGroupAll(node));
                } catch (Exception e) {// 非ViewGroup 为View
                    ret.add(root.getChildAt(i));
                }
            }
        }
        return ret;
    }

    /**
     * 将view转化为bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {

        if (view.getLayoutParams() == null) {
            view.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
        }

        measureView(view);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.destroyDrawingCache();
        view.buildDrawingCache();

        return view.getDrawingCache();
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕像素密度
     *
     * @param context
     * @return
     */
    public static int getWindowDensity(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取listview的整体高度（包括padding和间隔线）
     *
     * @param lv
     * @return
     */
    public static int getListViewMesureHeight(ListView lv) {
        int height = 0;
        try {
            ListAdapter la = lv.getAdapter();
            int count = la.getCount();
            for (int i = 0; i < count; i++) {
                View listItem = la.getView(i, null, lv);
                listItem.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height += listItem.getMeasuredHeight();
            }
            height += lv.getDividerHeight() * (count - 1);
            height += lv.getPaddingTop();
            height += lv.getPaddingBottom();
        } catch (Exception e) {
        }
        return height;
    }

    /**
     * 设置listview的高度为基于所有子item
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        LayoutParams params = listView.getLayoutParams();
        params.height = getListViewMesureHeight(listView);
        listView.setLayoutParams(params);
    }

    /**
     * 缩放图片
     *
     * @param bitmap      源图片
     * @param width       缩放后图片你的宽度
     * @param height      缩放后图片的高度
     * @param unformScale 是否等比缩放，当为true会按宽高缩放比最大值进行宽高等比缩放
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height, boolean unformScale) {
        int bWidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleX = width / (bWidth * 1f);
        float scaleY = height / (bHeight * 1f);
        // 等比缩放处理
        if (unformScale) {
            if (scaleX > scaleY)
                scaleY = scaleX;
            else
                scaleX = scaleY;
        }
        matrix.postScale(scaleX, scaleY);
        // 利用矩阵缩放不会照成内存溢出
        return Bitmap.createBitmap(bitmap, 0, 0, bWidth, bHeight, matrix, true);
    }

}
