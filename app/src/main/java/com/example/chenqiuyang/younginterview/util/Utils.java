package com.example.chenqiuyang.younginterview.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.chenqiuyang.younginterview.customView.util.SizeUtils;
import com.example.chenqiuyang.younginterview.multiThread.thread_pool.ThreadPoolManager;
import com.example.chenqiuyang.younginterview.packing.app.App;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by haibiao on 2017/11/7.
 */

public class Utils {
    public static Context getApp(){
        return App.getApplicationInstance();
    }

    static Context getContext(){
        return getApp();
    }

    public static boolean contain(String[] target,String[] sub){
        if(sub.length == 0) return true;
        if(sub.length > target.length) return false;
        List<String> targetList = Arrays.asList(target);
        for(int i = 0 ; i < sub.length ; i++){
            if(targetList.indexOf(sub[i]) < 0){
                return false;
            }
        }
        return true;
    }

    public static boolean isEmptyText(CharSequence text){
        if(text == null || text.length() == 0){
            return true;
        }
        for(int i = 0 ; i < text.length() ; i++){
            if(!Character.isWhitespace(text.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static String nonNull(String s){
        return s == null ? "" : s;
    }

    public static boolean isAPI(int api){
        return Build.VERSION.SDK_INT >= api;
    }

    public static void assertOnUiThread(){
        if(Looper.myLooper() == Looper.getMainLooper()){
            throw new IllegalStateException("assertOnUiThread");
        }
    }

    public static void assertNotOnUiThread(){
        if(Looper.myLooper() != Looper.getMainLooper()){
            throw new IllegalStateException("assertNotOnUiThread");
        }
    }

    private static Handler sMainHandler = new Handler(Looper.getMainLooper());

    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnUiThread(Runnable r){
        if(isMainThread()){
            r.run();
        }else{
            sMainHandler.post(r);
        }
    }

    private static MediaScannerConnection sMediaScannerConnection;
    public static void startMediaScanner(final String scanPath, final String mimeType){
//        ThreadPoolManager.ThreadPool.shorter().execute(new Runnable() {
//            @Override public void run() {
//                sMediaScannerConnection = new MediaScannerConnection(getApp(),
//                new MediaScannerConnection.MediaScannerConnectionClient() {
//                    @Override
//                    public void onMediaScannerConnected() {
//                        sMediaScannerConnection.scanFile(scanPath, mimeType);
//                    }
//
//                    @Override
//                    public void onScanCompleted(String s, Uri uri) {
//                        sMediaScannerConnection.disconnect();
//                    }
//                });
//                sMediaScannerConnection.connect();
//            }
//        });
    }

    public static <T> String join(CharSequence delimiter, T... elements) {
        return join(delimiter,Arrays.asList(elements));
    }

    public static String join(CharSequence delimiter,Iterable<?> elements) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(elements);
        Iterator<?> iterator = elements.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()){
            sb.append(String.valueOf(iterator.next()));
            if(iterator.hasNext()){
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static String[] toStringArray(Iterable<?> elements){
        if(elements == null) return new String[0];
        List<String> list = new ArrayList<>();
        Iterator<?> iterator = elements.iterator();
        while (iterator.hasNext()){
            list.add(String.valueOf(iterator.next()));
        }
        return list.toArray(new String[list.size()]);
    }


    public static boolean isNotificationsEnabled() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(getContext());
        return manager.areNotificationsEnabled();
    }

    //没有准确的方法获取底部导航栏当前是否显示，慎重调用此方法来获取导航栏高度
    //此方法返回的高度只是系统配置的高度，并不知道当前导航栏是否显示
//    private static int sNavigationBarHeight = -1;
//    private static final Point mNvgSize = new Point();
//    private static final Point mNvgRealSize = new Point();
//    public static boolean isNavigationBarShow(){
//        WindowManager wm = (WindowManager) getApp().getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        display.getSize(mNvgSize);
//        display.getRealSize(mNvgRealSize);
//        return mNvgSize.y != mNvgRealSize.y;
//    }
//    public static int getNavigationBarHeight() {
////        if(!isNavigationBarShow()){
////            return 0;
////        }
//        if(sNavigationBarHeight > -1){
//            return sNavigationBarHeight;
//        }
//        int result = -1;
//        Context context = getApp();
//        try{
//            int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
//            if (resourceId > 0) {
//                result = context.getResources().getDimensionPixelSize(resourceId);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(result == -1){
//            try{
//                Class<?> c = Class.forName("com.android.internal.R$dimen");
//                Object o = c.newInstance();
//                Field field = c.getField("navigation_bar_height");
//                int id = (Integer) field.get(o);
//                if(id > 0){
//                    result = context.getResources().getDimensionPixelSize(id);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        if(result == -1){
//            result = 0;
//        }
//        sNavigationBarHeight = result;
//        return result;
//    }

    private static int sStatusBarHeight = 0;
    public static int getStatusBarHeight(){
        if(sStatusBarHeight > 0){
            return sStatusBarHeight;
        }
        Context context = getApp();
        int result = 0;
        try{
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result <= 0){
            try{
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int id = (Integer) field.get(o);
                result = context.getResources().getDimensionPixelSize(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(result <= 0){
            result = SizeUtils.dp2px(25);
        }
        sStatusBarHeight = result;
        return result;
    }

    public static int getColorCompat(int resId){
        if(Utils.isAPI(23)){
            return getApp().getColor(resId);
        }
        return getApp().getResources().getColor(resId);
    }

    public static <T> T getViewTag(View view,int id,T defValue){
        Object tag = view.getTag(id);
        if(tag == null) return defValue;
        try{
            return (T) tag;
        }catch (Exception e){
            return defValue;
        }
    }

    private static InputMethodManager inputMethodManager;
    private static InputMethodManager getIMM(){
        if(inputMethodManager == null){
            inputMethodManager = (InputMethodManager) getApp().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return inputMethodManager;
    }

    public static void toggleSoftInput(){
        InputMethodManager imm = getIMM();
        if(imm != null){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideSoftInput(View view){
        InputMethodManager imm = getIMM();
        if(imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public static void showSoftInput(View view){
        InputMethodManager imm = getIMM();
        if(imm != null){
            imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        }
    }

    public static void avoidDoubleClick(final View view){
        view.setClickable(false);
        view.postDelayed(new Runnable() {
            @Override public void run() {
               view.setClickable(true);
            }
        },400);
    }

    private static Vibrator sVibrator;

//    public static void vibrator(){
//        if(sVibrator == null){
//            sVibrator = (Vibrator) getApp().getSystemService(Activity.VIBRATOR_SERVICE);
//        }
//        long vibrateTime = 15;
//        try{
//            sVibrator.vibrate(new long[] { 0, vibrateTime, 50, vibrateTime }, -1);
//            sMainHandler.postDelayed(new Runnable() {
//                @Override public void run() {
//                    sVibrator.cancel();
//                }
//            },vibrateTime * 7);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public static boolean copyToClipboard(CharSequence text){
        try{
            ClipData clipData = ClipData.newPlainText("", text);
            ClipboardManager manager = ((ClipboardManager) getApp().getSystemService(Context.CLIPBOARD_SERVICE));
            if(manager != null){
                manager.setPrimaryClip(clipData);
                return true;
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void drawEmojiCompatText(CharSequence stringWithEmoji, Canvas canvas, TextPaint textPaint, int left, int top) {
        StaticLayout tmpStaticLayout = new StaticLayout(stringWithEmoji, textPaint, ScreenUtils.getScreenWidth(), Layout.Alignment.ALIGN_NORMAL, 1.f, 1.f, true);

        StaticLayout staticLayout = new StaticLayout(stringWithEmoji, textPaint, (int)(tmpStaticLayout.getLineWidth(0)) + 10, Layout.Alignment.ALIGN_NORMAL, 1.f, 1.f, true);
        canvas.save();
        canvas.translate(left, top);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public static void drawEmojiCompatText(CharSequence stringWithEmoji, Canvas canvas, Paint textPaint, int left, int top) {
        drawEmojiCompatText(stringWithEmoji, canvas, new TextPaint(textPaint), left, top);
    }

    public static int measurEmojiStr(CharSequence emojiCharsequence, Paint textPaint) {
        StaticLayout tmpStaticLayout = new StaticLayout(emojiCharsequence, new TextPaint(textPaint), ScreenUtils.getScreenWidth(), Layout.Alignment.ALIGN_NORMAL, 1.f, 1.f, true);
        return (int)(tmpStaticLayout.getLineWidth(0)) + 10;
    }

    public static int rgba2argb(int rgba){
        return ((rgba & 0xFF) << 24) | (rgba >>> 8);
    }

    public static void main(String[] args){
        for(int i = 0 ; i < 10000 ; i++){
            int color = (int) (Math.random() * Integer.MIN_VALUE);
            System.out.println("src = " + Integer.toHexString(color) + ",dest = " + Integer.toHexString(rgba2argb(color)));
        }
    }
}
