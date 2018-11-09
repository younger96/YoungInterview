//package com.example.chenqiuyang.younginterview.customView.view.irre;
//
//import android.animation.Animator;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//
//import com.example.chenqiuyang.younginterview.customView.util.SizeUtils;
//import com.skyplan.moment.libbasic.utils.SizeUtils;
//import com.skyplan.moment.libcomm.image.ImageLoader;
//import com.skyplan.moment.libcomm.info.SnapInfo;
//import com.skyplan.moment.libcomm.info.SnapRecordInfo;
//import com.skyplan.moment.libui.widget.PreviewRoundedImageView;
//import com.skyplan.moment.plugin.space.R;
//import com.skyplan.moment.utils.FP;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import static spcgi.Spcgicommdef.enSnapType.SNAP_TYPE_IMG_VALUE;
//import static spcgi.Spcgicommdef.enSnapType.SNAP_TYPE_VIDEO_VALUE;
//
///**
// * Created by yangtian on 2018/7/5.
// * E-Mail Address: 443275705@qq.com
// */
//
//public class IrregularImagesView extends FrameLayout {
//
//    public static final String TAG = "IrregularImagesView";
//
//    private static final int STATE_ORIGINAL = 1;
//    private static final int STATE_IRREGULAR = 2;
//
//    private Random tSmallRandom = new Random(SizeUtils.dp2px(5));
//    private Random tMidRandom = new Random(SizeUtils.dp2px(10));
//    private Random rSmallRandom = new Random(5);
//
//    private List<PreviewRoundedImageView> imageViewList = new ArrayList<>();
//    private List<Map<String, ObjectAnimator>> animatorMapList = new ArrayList<>();
//    private AnimatorSet curAnimSet;
//
//    private int state = STATE_ORIGINAL;
//    private int model; //1:单行 2:双行 3:单个 4:两个
//
//    public IrregularImagesView(@NonNull Context context) {
//        super(context);
//    }
//
//    public IrregularImagesView(@NonNull Context context,
//                               @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    protected float getScaleFactor() {
//        return 1.f;
//    }
//
//    public void setImageViewList(List<SnapRecordInfo> snapRecordInfos) {
//        if (snapRecordInfos.size() > 3) {
//            model = 2;
//        } else if (snapRecordInfos.size() == 1) {
//            model = 3;
//        } else if (snapRecordInfos.size() == 2) {
//            model = 4;
//        } else {
//            model = 1;
//        }
//        if (FP.empty(imageViewList)) {
//            initViews(snapRecordInfos);
//        } else {
//            updateViews(snapRecordInfos);
//        }
//    }
//
//    private void updateViews(List<SnapRecordInfo> snapRecordInfos) {
//        int snapSize = snapRecordInfos.size() > 6 ? 6 : snapRecordInfos.size();
//        int viewSize = imageViewList.size();
//        if (snapSize >= viewSize) {
//            animatorMapList.clear();
//            for (int i = 0; i < snapSize; i++) {
//                if (i < viewSize) {
//                    updateView(imageViewList.get(i), snapRecordInfos.get(i));
//                    initAnimator(imageViewList.get(i), i);
//                } else {
//                    initImageView(snapRecordInfos.get(i).getLocalSnapInfo(), i);
//                }
//            }
//        } else {
//            for (int i = viewSize - 1; i >= snapSize; i--) {
//                removeView(imageViewList.get(i));
//            }
//            imageViewList = imageViewList.subList(0, snapSize);
//            animatorMapList.clear();
//            for (int i = 0; i < snapSize; i++) {
//                PreviewRoundedImageView imageView = imageViewList.get(i);
//                updateView(imageView, snapRecordInfos.get(i));
//                initAnimator(imageView, i);
//            }
//        }
//    }
//
//    private void updateView(ImageView view, SnapRecordInfo recordInfo) {
//        SnapInfo snapInfo = recordInfo.getLocalSnapInfo();
//        if (snapInfo == null) {
//            return;
//        }
//        if (model != 2) {
//            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
//            layoutParams.bottomMargin = (int) (SizeUtils.dp2px(20) * getScaleFactor());
//            view.setLayoutParams(layoutParams);
//        } else {
//            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
//            layoutParams.bottomMargin = 0;
//            view.setLayoutParams(layoutParams);
//        }
//        if (snapInfo.getType() == SNAP_TYPE_IMG_VALUE
//            && snapInfo.getSnap_img() != null
//            && !TextUtils.isEmpty(
//            snapInfo.getSnap_img().getThumbUrl())) {
//            ImageLoader.loadThumbCenterCrop(view, snapInfo.getSnap_img().getThumbUrl(),
//                R.drawable.bg_default_thumb);
//        } else if (snapInfo.getType() == SNAP_TYPE_VIDEO_VALUE
//            && snapInfo.getSnap_video() != null
//            && !TextUtils.isEmpty(
//            snapInfo.getSnap_video().getThumbUrl())) {
//            ImageLoader.loadThumbCenterCrop(view,
//                snapInfo.getSnap_video().getThumbUrl(), R.drawable.bg_default_thumb);
//        }
//    }
//
//    private void initViews(List<SnapRecordInfo> snapRecordInfos) {
//        int size = snapRecordInfos.size() > 6 ? 6 : snapRecordInfos.size();
//        for (int i = 0; i < size; i++) {
//            SnapRecordInfo recordInfo = snapRecordInfos.get(i);
//            SnapInfo snap = recordInfo.getLocalSnapInfo();
//            initImageView(snap, i);
//        }
//    }
//
//    public void startAnim() {
//        startAnimDelay(0);
//    }
//
//    public void startAnimDelay(long delay) {
//        if (state == STATE_IRREGULAR) {
//            return;
//        }
//        curAnimSet = new AnimatorSet();
//        List<Animator> animators = new ArrayList<>();
//        for (Map<String, ObjectAnimator> map : animatorMapList) {
//            ObjectAnimator transXAnim = map.get("translationX");
//            ObjectAnimator transYAnim = map.get("translationY");
//            ObjectAnimator rotateAnim = map.get("rotation");
//            animators.add(transXAnim);
//            animators.add(transYAnim);
//            animators.add(rotateAnim);
//        }
//        curAnimSet.setDuration(200).playTogether(animators);
//        curAnimSet.setStartDelay(delay);
//        curAnimSet.start();
//        state = STATE_IRREGULAR;
//    }
//
//    public void switchToAnimEnd() {
//        curAnimSet = new AnimatorSet();
//        List<Animator> animators = new ArrayList<>();
//        for (Map<String, ObjectAnimator> map : animatorMapList) {
//            ObjectAnimator transXAnim = map.get("translationX");
//            ObjectAnimator transYAnim = map.get("translationY");
//            ObjectAnimator rotateAnim = map.get("rotation");
//            animators.add(transXAnim);
//            animators.add(transYAnim);
//            animators.add(rotateAnim);
//        }
//        curAnimSet.setDuration(10).playTogether(animators);
//        curAnimSet.start();
//        //curAnimSet.setDuration(200).playTogether(animators);
//    }
//
//    public void resetUIState() {
//        if (state == STATE_ORIGINAL) {
//            return;
//        }
//        if (curAnimSet != null && curAnimSet.isRunning()) {
//            curAnimSet.cancel();
//        }
//        for (PreviewRoundedImageView roundedImageView : imageViewList) {
//            roundedImageView.setTranslationX(0);
//            roundedImageView.setTranslationY(0);
//            roundedImageView.setRotation(0);
//        }
//        state = STATE_ORIGINAL;
//    }
//
//    private void initImageView(SnapInfo snapInfo, int index) {
//        PreviewRoundedImageView roundedImageView = new PreviewRoundedImageView(getContext());
//        roundedImageView.updateRadius((int) (SizeUtils.dp2px(10) * getScaleFactor()));
//        LayoutParams layoutParams =
//            new LayoutParams(SizeUtils.dp2px(getItemImageWidth()), SizeUtils.dp2px(
//                getItemImageViewHeight()));
//        layoutParams.gravity = Gravity.CENTER;
//        if (model != 2) {
//            layoutParams.bottomMargin = (int)(SizeUtils.dp2px(20) * getScaleFactor());
//        }
//        if (index == 4) {
//            addView(roundedImageView, 1, layoutParams);
//        } else {
//            addView(roundedImageView, 0, layoutParams);
//        }
//
//        if (snapInfo.getType() == SNAP_TYPE_IMG_VALUE
//            && snapInfo.getSnap_img() != null
//            && !TextUtils.isEmpty(
//            snapInfo.getSnap_img().getThumbUrl())) {
//            ImageLoader.loadThumbCenterCrop(roundedImageView, snapInfo.getSnap_img().getThumbUrl(),
//                R.drawable.bg_default_thumb);
//        } else if (snapInfo.getType() == SNAP_TYPE_VIDEO_VALUE
//            && snapInfo.getSnap_video() != null
//            && !TextUtils.isEmpty(
//            snapInfo.getSnap_video().getThumbUrl())) {
//            ImageLoader.loadThumbCenterCrop(roundedImageView,
//                snapInfo.getSnap_video().getThumbUrl(), R.drawable.bg_default_thumb);
//        }
//
//        initAnimator(roundedImageView, index);
//        imageViewList.add(roundedImageView);
//    }
//
//    protected int getItemImageViewHeight() {
//        return 178;
//    }
//
//    protected int getItemImageWidth() {
//        return 110;
//    }
//
//    private void initAnimator(PreviewRoundedImageView roundedImageView, int index) {
//        float translationX = getAnimatorTranslationX(index);
//        float translationY = getAnimatorTranslationY(index);
//        float rotation = getAnimatorRotate(index);
//
//        Map<String, ObjectAnimator> animatorMap = new HashMap<>();
//        ObjectAnimator transXAnim =
//            ObjectAnimator.ofFloat(roundedImageView, "translationX", 0, translationX);
//        ObjectAnimator transYAnim =
//            ObjectAnimator.ofFloat(roundedImageView, "translationY", 0, translationY);
//        ObjectAnimator rotateAnim =
//            ObjectAnimator.ofFloat(roundedImageView, "rotation", 0, rotation);
//        animatorMap.put(transXAnim.getPropertyName(), transXAnim);
//        animatorMap.put(transYAnim.getPropertyName(), transYAnim);
//        animatorMap.put(rotateAnim.getPropertyName(), rotateAnim);
//        animatorMapList.add(animatorMap);
//    }
//
//    private float getAnimatorTranslationY(int index) {
//        int offset = model == 2 ? 20 : 0;
//        switch (index) {
//            case 0:
//                return SizeUtils.dp2px(offset + 20) * getScaleFactor() + tSmallRandomOffset();
//            case 1:
//                return SizeUtils.dp2px(offset) * getScaleFactor() + tSmallRandomOffset();
//            case 2:
//                return SizeUtils.dp2px(offset - 10) * getScaleFactor() + tSmallRandomOffset();
//            case 3:
//                return -SizeUtils.dp2px(30) * getScaleFactor() + tMidRandomOffset();
//            case 4:
//                return -SizeUtils.dp2px(20) * getScaleFactor() + tMidRandomOffset();
//            case 5:
//                return -SizeUtils.dp2px(40) * getScaleFactor() + tMidRandomOffset();
//            default:
//                break;
//        }
//        return 0;
//    }
//
//    private float getAnimatorTranslationX(int index) {
//        if (model == 3) {
//            return 0;
//        } if (model == 4) {
//            if (index == 0) {
//                return -SizeUtils.dp2px(30) * getScaleFactor();
//            } else {
//                return SizeUtils.dp2px(30) * getScaleFactor();
//            }
//        }
//        switch (index) {
//            case 0:
//                return -SizeUtils.dp2px(80) * getScaleFactor();
//            case 1:
//                return 0;
//            case 2:
//                return SizeUtils.dp2px(80) * getScaleFactor();
//            case 3:
//                return -SizeUtils.dp2px(60) * getScaleFactor();
//            case 4:
//                return SizeUtils.dp2px(10) * getScaleFactor();
//            case 5:
//                return SizeUtils.dp2px(60) * getScaleFactor();
//            default:
//                break;
//        }
//        return 0;
//    }
//
//    private float getAnimatorRotate(int index) {
//        if (model == 3) {
//            return rSmallRandom.nextFloat() - 10;
//        }
//        switch (index) {
//            case 0:
//                return rSmallRandom.nextFloat() - 15;
//            case 1:
//                return rSmallRandom.nextFloat() + 5;
//            case 2:
//                return rSmallRandom.nextFloat() + 10;
//            case 3:
//                return rSmallRandom.nextFloat() - 20;
//            case 4:
//                return rSmallRandom.nextFloat() - 10;
//            case 5:
//                return rSmallRandom.nextFloat() + 15;
//            default:
//                break;
//        }
//        return 0;
//    }
//
//    private float tSmallRandomOffset() {
//        return tSmallRandom.nextFloat() * getScaleFactor();
//    }
//
//    private float tMidRandomOffset() {
//        return (tMidRandom.nextFloat() - SizeUtils.dp2px(10)) * getScaleFactor();
//    }
//}
