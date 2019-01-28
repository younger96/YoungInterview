package com.example.chenqiuyang.younginterview.system;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;

import com.example.chenqiuyang.younginterview.customView.util.SizeUtils;
import com.example.chenqiuyang.younginterview.packing.app.App;
import com.example.chenqiuyang.younginterview.util.OSUtils;
import com.example.chenqiuyang.younginterview.util.ScreenUtils;
import com.example.chenqiuyang.younginterview.util.Utils;
import com.githang.statusbar.StatusBarCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static com.example.chenqiuyang.younginterview.util.OSUtils.ROM.MIUI;

/**
 * Created by lihaibiao on 2018/6/23 15:29
 * E-Mail Address：lihaibiaowork@gmail.com
 */
public class SkyStatusBarUtils {
    private static final String TAG = "SkyStatusBarUtils";
    public static final int NOTCH_MODE_DEFAULT = 0;
    public static final int NOTCH_MODE_SHORT_EDGES = 1;
    public static final int NOTCH_MODE_NEVER = 2;

    public static class Attributes{
        public boolean immersion = true;//是否启用沉浸式
        public int statusBarColor = Color.TRANSPARENT;//状态栏颜色
        public int navigationBarColor = Color.BLACK;//底部导航栏颜色
    }

    //在setContentView之后调用
    public static void immersion(Window window,Attributes attributes){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //7.0以上的状态栏有一层灰色的蒙层，把他去掉
//            try {
//                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
//                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
//                field.setAccessible(true);
//                field.setInt(window.getDecorView(), Color.TRANSPARENT);  //改为透明
//            } catch (Exception e) {
//            }
//        }
        window.setStatusBarColor(attributes.statusBarColor);
        window.setNavigationBarColor(attributes.navigationBarColor);
        if(attributes.immersion){
            int flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //flag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            window.getDecorView().setSystemUiVisibility(flag);

        }
//        if(attributes.fitSystemWindow){
//            final ViewGroup contentView = window.findViewById(Window.ID_ANDROID_CONTENT);
//            final View childView = contentView.getChildAt(0);
//            if (childView != null) {
//                childView.setFitsSystemWindows(true);
//            }
//        }
    }

    public static void setStatusIconHide(Window window,boolean visible){
        View decor = window.getDecorView();
        int ui = decor.getSystemUiVisibility();
        if (visible) {
            ui |= View.INVISIBLE;
        } else {
            ui &= ~View.INVISIBLE;
        }
        decor.setSystemUiVisibility(ui);
    }

    //状态栏文字颜色，true为黑色，false为白色
    public static void setLightStatusBar(Window window,boolean light){
        StatusBarCompat.setLightStatusBar(window,light);
    }

    public static int getStatusBarHeight(){
        return Utils.getStatusBarHeight();
    }

    public static int getNotchHeight(Window window){
        return getNotchRects(window)[0].height();
    }

    public static boolean isNotchScreen(Window window){
        return getNotchRects(window)[0].height() > 0;
    }

    private static Rect[] sNotchRects = null;

    /**
     * 获取刘海、下巴胡子缺口区域
     * @param window
     * @return [0]刘海区域，[1]下巴胡子区域
     */
    @NonNull
    public static Rect[] getNotchRects(Window window){
        if(sNotchRects == null){
            final boolean notchScreen;
            //第1项是状态栏的刘海，第2项是底部的胡子
            Rect[] rects = new Rect[]{new Rect(),new Rect()};
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                //8.0以下都不是刘海屏幕
                notchScreen = false;
            }else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
                int[] notchWh = new int[]{0,0};
                switch (OSUtils.getRomType()){
                    case MIUI:
                        notchScreen = hasNotchInScreenAtXiaomi();
                        if(notchScreen){
                            notchWh = getNotchSizeAtXiaomi();
                        }
                        break;
                    case EMUI:
                        notchScreen = hasNotchInScreenAtHuawei(App.getApplicationInstance());
                        if(notchScreen){
                            notchWh = getNotchSizeAtHuawei(App.getApplicationInstance());
                        }
                        break;
                    case ColorOS:
                        //OPPO
                        notchScreen = App.getApplicationInstance().getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
                        if(notchScreen){
                            notchWh = getNotchSizeAtOppo();
                        }
                        break;
                    case FuntouchOS:
                        notchScreen = hasNotchInScreenAtVivo(App.getApplicationInstance());
                        if(notchScreen){
                            notchWh = getNotchSizeAtVivo();
                        }
                        break;
                    default:
                        notchScreen = false;
                        break;
                }
                if(notchWh[0] > 0){
                    int left = (ScreenUtils.getScreenWidth() - notchWh[0]) / 2;
                    rects[0].set(left,0,left + notchWh[0],notchWh[1]);
                }
            }else{
                WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
                if(windowInsets == null){
                    //throw new RuntimeException("this view has attach to window ?");
                    notchScreen = false;
                }else{
                    DisplayCutout dc = windowInsets.getDisplayCutout();
                    List<Rect> rectList = dc.getBoundingRects();
                    notchScreen = rectList != null && !rectList.isEmpty();
                    if(notchScreen){
                        rects[0] = rectList.get(0);
                        if(rectList.size() > 1){
                            rects[1] = rectList.get(1);
                        }
                    }
                }
            }
            sNotchRects = rects;
        }
        return sNotchRects;
    }

    private static String getProperty(String key) {
        String propertyValue = "";
        try {
            Process process = new ProcessBuilder().command("/system/bin/getprop", key)
                .redirectErrorStream(true).start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            propertyValue = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    private static boolean hasNotchInScreenAtXiaomi(){
        boolean notchScreen = false;
        try{
            notchScreen = Integer.parseInt(getProperty("ro.miui.notch")) == 1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return notchScreen;
    }

    private static boolean hasNotchInScreenAtHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    //获取刘海尺寸：width、height
    //int[0]值为刘海宽度 int[1]值为刘海高度
    private static int[] getNotchSizeAtHuawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    //获取刘海尺寸：width、height
    //int[0]值为刘海宽度 int[1]值为刘海高度
    private static int[] getNotchSizeAtXiaomi() {
        int[] ret = new int[]{0,0};
        Context context = App.getApplicationInstance();
        ret[0] = getAndroidResDimen(context,"notch_width",560);
        ret[1] = getAndroidResDimen(context,"notch_height",89);
        return ret;
    }

    private static final int NOTCH_IN_SCREEN_VOIO=0x00000020;//是否有凹槽
    private static final int ROUNDED_IN_SCREEN_VOIO=0x00000008;//是否有圆角
    private static boolean hasNotchInScreenAtVivo(Context context){
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class FtFeature = cl.loadClass("com.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport",int.class);
            ret = (boolean) get.invoke(FtFeature,NOTCH_IN_SCREEN_VOIO);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    //获取刘海尺寸：width、height
    //int[0]值为刘海宽度 int[1]值为刘海高度
    private static int[] getNotchSizeAtVivo() {
        return new int[]{ SizeUtils.dp2px(100), SizeUtils.dp2px(27)};
    }

    //获取刘海尺寸：width、height
    //int[0]值为刘海宽度 int[1]值为刘海高度
    private static int[] getNotchSizeAtOppo() {
        return new int[]{324,80};
    }

    private static int getAndroidResDimen(Context context,String resName,int def){
        int ret = def;
        try{
            int resourceId = context.getResources().getIdentifier(resName, "dimen", "android");
            if (resourceId > 0) {
                ret = context.getResources().getDimensionPixelSize(resourceId);
            }
        }catch (Exception e){
        }
        return ret;
    }


}
