package com.example.chenqiuyang.younginterview.customView.util;

import android.content.Context;
;import com.example.chenqiuyang.younginterview.packing.app.App;

public final class ResourceHelper {
	
	private static final String TAG = "SkyPlan.ResourceHelper";

	private static float density = -1.0f;

	private ResourceHelper() {

	}

	public static int fromDPToPix(Context context, int dp) {
		return Math.round(getDensity(context) * dp);
	}

	public static int fromPixToDP(Context context, int px) {
		return Math.round(px / getDensity(context));
	}

	public static float getDensity(Context context) {
		if (context == null) {
			context = App.getApplicationInstance();
		}
		if (density < 0) {
			density = context.getResources().getDisplayMetrics().density;
		}

		return density;
	}

//	private static IResourceLoader loader = null;
//	private static float density = -1.0f;
//
//	/**
//	 * interface for instance
//	 */
//	public interface IResourceLoader {
//
//		int getColor(Context context, int resId);
//
//		ColorStateList getColorStateList(Context context, int resId);
//
//		Drawable getDrawable(Context context, int resId);
//
//		Drawable getDrawableForDensity(int resId, int density);
//
//		int getDimensionPixelSize(int resId);
//
//		String getString(int resId);
//
//	}
//
//	/**
//	 * if res is an xml, use getColorStateList
//	 * @param context
//	 * @param resId
//	 * @return
//	 */
//	public static int getColor(Context context, int resId) {
//		if (loader == null) {
//			if (null == context) {
//				Log.e(TAG, "get color, resId %d, but context is null", resId);
//				return 0;
//			}
//			return context.getResources().getColor(resId);
//		}
//
//		return loader.getColor(context, resId);
//	}
//
//	public static ColorStateList getColorStateList(Context context, int resId) {
//		if (loader == null) {
//			if (null == context) {
//				Log.e(TAG, "get color state list, resId %d, but context is null", resId);
//				return null;
//			}
//			return context.getResources().getColorStateList(resId);
//		}
//
//		return loader.getColorStateList(context, resId);
//	}
//
//	public static Drawable getDrawable(Context context, int resId) {
//		if (loader == null) {
//			if (null == context) {
//				Log.e(TAG, "get drawable, resId %d, but context is null", resId);
//				return null;
//			}
//			return context.getResources().getDrawable(resId);
//		}
//
//		return loader.getDrawable(context, resId);
//	}
//
//	private static SparseIntArray dimensionPixelCache = new SparseIntArray();
//
//	/**
//	 *
//	 * @param context
//	 * @param resId
//	 * @return
//	 */
//	// 此接口字体调节的时候会跟着变化
//	public static int getDimensionPixelSize(Context context, int resId) {
//		float fontScale = scale;
//		if ( fontScale > ResourceHelper.TEXT_SIZE_HUGER) { // 最大两档字体,仅用于会话和朋友圈的阅读区域
//			fontScale = ResourceHelper.TEXT_SIZE_HUGER;
//		}
//		if (loader == null) {
//			if (null == context) {
//				Log.e(TAG, "get dimension pixel size, resId %d, but context is null", resId);
//				return 0;
//			}
//			int size = dimensionPixelCache.get(resId, 0);
//			if (0 == size) {
//				size = context.getResources().getDimensionPixelSize(resId);
//				dimensionPixelCache.put(resId, size);
//			}
////			if (isUserLargeText(context)) {
////				return (int) (size * scale);
////			}
//			return (int) (size * fontScale);
//		}
//
//		int size = dimensionPixelCache.get(resId, 0);
//		if (0 == size) {
//			size = loader.getDimensionPixelSize(resId);
//			dimensionPixelCache.put(resId, size);
//		}
////		if (isUserLargeText(context)) {
//			return (int) (size * fontScale);
////		} else {
////			return size;
////		}
//	}
//
//	//此接口用于一般的DP和px转换
//	public static int getDPSize(Context context, int resId) {
//		if (loader == null) {
//			if (null == context) {
//				Log.e(TAG, "get dimension pixel size, resId %d, but context is null", resId);
//				return 0;
//			}
//			int size = dimensionPixelCache.get(resId, 0);
//			if (0 == size) {
//				size = context.getResources().getDimensionPixelSize(resId);
//				dimensionPixelCache.put(resId, size);
//			}
//			return size;
//		}
//
//		int size = dimensionPixelCache.get(resId, 0);
//		if (0 == size) {
//			size = loader.getDimensionPixelSize(resId);
//			dimensionPixelCache.put(resId, size);
//		}
//		return size;
//	}
//
//	public static String getString(Context context, int resId) {
//		if (loader == null) {
//			if (null == context) {
//				Log.e(TAG, "get string, resId %d, but context is null", resId);
//				return "";
//			}
//			return context.getResources().getString(resId);
//		}
//
//		return loader.getString(resId);
//	}
//
//	public static float getDensity(Context context) {
//		if (context == null) {
//			context = MMApplicationContext.getContext();
//		}
//		if (density < 0) {
//			density = context.getResources().getDisplayMetrics().density;
//		}
//
//		return density;
//	}
//
//	public static int fromDPToPix(Context context, int dp) {
//		return Math.round(getDensity(context) * dp);
//	}
//
//	public static int fromPixToDP(Context context, int px) {
//		return Math.round(px / getDensity(context));
//	}
//
//	/**
//	 * Text Size
//	 */
//	private static float scale = 0.0f;
//	private static final String TEXT_SIZE_SCALE_KEY = "text_size_scale_key";
//
//	public static final float TEXT_SIZE_NORMAL = 1.0f;
//	public static final float TEXT_SIZE_SMALL = 0.875f; // - 1/8
//	public static final float TEXT_SIZE_LARGE = 1.125f; // + 1/8
//	public static final float TEXT_SIZE_SUPER = 1.250f; // + 1/8
//	public static final float TEXT_SIZE_HUGE = 1.375f; // + 1/8
//	public static final float TEXT_SIZE_HUGER = 1.625f; // + 1/4
//	public static final float TEXT_SIZE_HUGERS = 1.875f; // + 1/4
//	public static final float TEXT_SIZE_HUGERSS = 2.025f; // + 1/4
//
//	public static final float HARDCODE_SMALL_TEXT_SIZE = 14.0f;
//	public static final float HARDCODE_BASIC_TEXT_SIZE = 16.0f;
//	public static final float HARDCODE_LARGE_TEXT_SIZE = 18.0f;
//	public static final float HARDCODE_SUPER_TEXT_SIZE = 20.0f;
//	public static final float HARDCODE_HUGE_TEXT_SIZE = 22.0f;
//	public static final float HARDCODE_HUGER_TEXT_SIZE = 26.0f;
//	public static final float HARDCODE_HUGERS_TEXT_SIZE = 28.0f;
//	public static final float HARDCODE_HUGERSS_TEXT_SIZE = 30.0f;
//
//	public static float getScaleSize(Context context) {
//		if (scale == 0.0f) {
//			if (null == context) {
//				scale = TEXT_SIZE_NORMAL;
//			} else {
//				SharedPreferences sp = context.getSharedPreferences(MMApplicationContext.getDefaultPreferencePath(), 0);
//				scale = sp.getFloat(TEXT_SIZE_SCALE_KEY, 1.0f);
//			}
//		}
//		return scale;
//	}
//
//	public static int getReportFontSize (Context context) {
//		float fontScale = getScaleSize(context);
//		if (fontScale == TEXT_SIZE_SMALL) {
//            return 1;
//		} else if (fontScale == TEXT_SIZE_NORMAL){
//			return 2;
//		} else if (fontScale == TEXT_SIZE_LARGE){
//			return 3;
//		} else if (fontScale == TEXT_SIZE_SUPER){
//			return 4;
//		} else if (fontScale == TEXT_SIZE_HUGE){
//			return 5;
//		} else if (fontScale == TEXT_SIZE_HUGER){
//			return 6;
//		} else if (fontScale == TEXT_SIZE_HUGERS){
//			return 7;
//		} else if (fontScale == TEXT_SIZE_HUGERSS){
//			return 8;
//		}
//		return 2;
//	}
//
//
//	public static float getLimitedScaleSize(Context context) {
//		float widgetScale = 1.0f;
//		if (isUserLargeText(context)) {
//			widgetScale = 1.3f;
//
//		}
//		return widgetScale;
//	}
//
//	public static float getWidgetScaleSize(Context context) {
//		float widgetScale = 1.0f;
//		if (isUserLargeText(context)) {
//			widgetScale = 1.2f;
//
//		}
//		return widgetScale;
//	}
//
//	public static boolean hasSetScaleSize(Context context) {
//		SharedPreferences sp = context.getSharedPreferences(MMApplicationContext.getDefaultPreferencePath(), 0);
//		float value = sp.getFloat(TEXT_SIZE_SCALE_KEY, -1);
//		return value > 0;
//	}
//
//	public static void setScaleSize(Context context, float scale) {
//		SharedPreferences sp = context.getSharedPreferences(MMApplicationContext.getDefaultPreferencePath(), 0);
//		SharedPreferences.Editor editor = sp.edit();
//		editor.putFloat(TEXT_SIZE_SCALE_KEY, scale);
//		editor.commit();
//		ResourceHelper.scale = scale;
//	}
//
//	public static void changeScaleSize(Context context) {
//		if (!LocaleUtil.isChineseAppLang()) {
//			if (isUserLargeText(context)) {
//				setScaleSize(context,TEXT_SIZE_LARGE);
//			}
//		}
//
//	}
//
//	public static boolean isUserLargeText(Context context) {
//		scale = getScaleSize(context);
//		return Float.compare(scale, TEXT_SIZE_LARGE) > 0;
//	}
//
//	public static boolean isUserSmallText(Context context) {
//		scale = getScaleSize(context);
//		if (scale == TEXT_SIZE_SMALL){
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//    public static int getWidthPixels(Context context) {
//        if (loader == null) {
//            if (null == context) {
//                Log.e(TAG, "get widthPixels but context is null");
//                return 0;
//            }
//            int width = context.getResources().getDisplayMetrics().widthPixels;
//            return width;
//        }
//        return 0;
//    }
//
//    public static int getHeightPixels(Context context) {
//        if (loader == null) {
//            if (null == context) {
//                Log.e(TAG, "get heightPixels but context is null");
//                return 0;
//            }
//            int height = context.getResources().getDisplayMetrics().heightPixels;
//            return height;
//        }
//        return 0;
//    }
}
