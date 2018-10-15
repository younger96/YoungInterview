//package com.example.chenqiuyang.younginterview.packing.ui;
//
//import android.content.Context;
//import android.support.v7.app.AppCompatDialog;
//import android.view.Gravity;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.example.youngnetwork.R;
//import com.example.youngnetwork.util.DimenUtil;
//import com.wang.avi.AVLoadingIndicatorView;
//
//import java.util.ArrayList;
//
///**
// * Created by young on 18-3-22.
// * 设置加载框的布局和显示加载框
// */
//
//public class YoungLoader {
//    private static final int LOADER_SIZE_SCALE = 8;
//    private static final int LOADER_OFFSET_SCALE = 10;
//
//    //管理我们的LoadingDialog,方便统一关闭
//    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
//    //设置统一的样式
//    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();
//
//    public static void showLoading(Context context,String type){
//        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.LoadingDialog);
//        final AVLoadingIndicatorView avLoadingIndicatorView =
//                LoaderCreator.create(type,context);
//        dialog.setContentView(avLoadingIndicatorView);
//
//        int deviceWidth = DimenUtil.getScreenWidth();
//        int deviceHeight = DimenUtil.getScreenHeight();
//        final Window dialogWindow = dialog.getWindow();
//        if(dialogWindow != null){
//            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//            lp.width = deviceWidth/LOADER_SIZE_SCALE;
//            lp.height = deviceHeight/LOADER_SIZE_SCALE;
//            lp.height = lp.height+deviceHeight/LOADER_OFFSET_SCALE;
//            lp.gravity = Gravity.CENTER;
//        }
//
//        LOADERS.add(dialog);
//        dialog.show();
//    }
//
//    //使用默认的Loader样式
//    public static void showLoading(Context context){
//        showLoading(context,DEFAULT_LOADER);
//    }
//
//    //加入枚举类型的Loader的参数
//    public static void showLoading(Context context,Enum<LoaderStyle> styleEnum){
//        showLoading(context,styleEnum.name());
//    }
//
//    public static void stopLoading(){
//        for(AppCompatDialog dialog:LOADERS){
//            if (dialog!=null){
//                if(dialog.isShowing()){
//                    dialog.cancel();//可以回调
////                    dialog.dismiss();//单纯地消失,没有回调
//                }
//            }
//        }
//    }
//
//
//}
