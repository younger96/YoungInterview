package com.example.chenqiuyang.younginterview.customView.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import com.example.chenqiuyang.younginterview.customView.util.tasks.IVResultCallback;
import com.example.chenqiuyang.younginterview.customView.util.tasks.VBaseOperator;


/**
 * created by jw200 at 2018/6/21 13:09
 **/
public class ImageRotate extends VBaseOperator {

    private static final int SAMPLE_WIDTH = 54;
    private static final int SAMPLE_HEIGHT = 96;

    private String mFile;

    private int mOrientation;

    private Bitmap src;

    private Bitmap[] bitmaps;

    private Bitmap[] results;

    private boolean isSRCFromFile = false;

    public Bitmap[] getResults() {
        return results;
    }

    public ImageRotate(String file) {
        this.mFile = file;
    }

    public ImageRotate(Bitmap bitmap) {
        this.src = bitmap;
    }

    @Deprecated
    @Override
    public void start(IVResultCallback callback) {
        super.start(callback);
    }

    public void startLoad(IVResultCallback callback, int rotation) {
        this.mOrientation = rotation;
        super.start(callback);
    }

    public static Bitmap loadBitmapFromFile(String file) {
        if (!TextUtils.isEmpty(file)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file, options);
            options.inSampleSize = (int) (1.0f * options.outWidth / SAMPLE_WIDTH);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(file, options);
        }
        return null;
    }

    private Bitmap getBitmap0(Bitmap result, Rect destTop, Rect destBottom) {
        if (src != null && result != null) {
            Canvas resultCanvas = new Canvas(result);
            resultCanvas.drawColor(Color.BLACK);
            int srcHeight = src.getHeight();
            int srcWidth = src.getWidth();
            int rectHeight = (int) (1.0f * srcHeight / 4);
            Rect srcTop = new Rect(0, 0, srcWidth, rectHeight);
            Rect srcBottom = new Rect(0, srcHeight - rectHeight, srcWidth, srcHeight);
            resultCanvas.drawBitmap(src, srcTop, destTop, new Paint(Paint.ANTI_ALIAS_FLAG));
            resultCanvas.drawBitmap(src, srcBottom, destBottom, new Paint(Paint.ANTI_ALIAS_FLAG));
            return result;
        }
        return null;
    }

    private Bitmap getBitmap90(Bitmap result, Rect destTop, Rect destBottom) {
        if (src != null && result != null) {
            int srcHeight = src.getHeight();
            int srcWidth = src.getWidth();
            Rect topSrc = new Rect(0, 0, (int) (srcWidth / 4.0f), srcHeight);
            Rect bottomSrc = new Rect((int) (srcWidth * 3.0f / 4.0f), 0, srcWidth, srcHeight);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            matrix.postTranslate(srcHeight, 0);
            Bitmap top = Bitmap.createBitmap(src, topSrc.left, topSrc.top,
                topSrc.width(), topSrc.height(), matrix, false);

            Canvas resultCanvas = new Canvas(result);
            resultCanvas.drawColor(Color.BLACK);
            Rect rotatedTop = new Rect(0, 0, srcHeight, (int) (srcWidth / 4.0f));
            resultCanvas.drawBitmap(top, rotatedTop, destTop, new Paint(Paint.ANTI_ALIAS_FLAG));
            top.recycle();
            Bitmap bottom =
                Bitmap.createBitmap(src, bottomSrc.left, bottomSrc.top, bottomSrc.width(),
                    bottomSrc.height(), matrix, false);
            resultCanvas.drawBitmap(bottom, rotatedTop, destBottom,
                new Paint(Paint.ANTI_ALIAS_FLAG));
            bottom.recycle();
            return result;
        }
        return null;
    }

    private Bitmap rotate180(Bitmap bitmap) {
        if (bitmap != null) {
            Matrix matrix = new Matrix();
            matrix.setRotate(180);
            matrix.postTranslate(bitmap.getWidth(), bitmap.getHeight());
            Bitmap result =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                    false);
            return result;
        }
        return null;
    }

    @Override
    protected int operate() {
        if (src == null) {
            isSRCFromFile = true;
            src = loadBitmapFromFile(mFile);
        }
        if (bitmaps == null) {
            bitmaps = new Bitmap[4];
        }
        if (bitmaps[0] == null || bitmaps[1] == null) {
            if (src != null) {
                Rect destTop = new Rect(0, 0, this.SAMPLE_WIDTH, (int) (this.SAMPLE_HEIGHT / 2.0f));
                Rect destBottom = new Rect(0, (int) (this.SAMPLE_HEIGHT / 2.0f), this.SAMPLE_WIDTH,
                    this.SAMPLE_HEIGHT);

                Bitmap bm0 =
                    Bitmap.createBitmap(SAMPLE_WIDTH, SAMPLE_HEIGHT, Bitmap.Config.ARGB_8888);
                bm0 = getBitmap0(bm0, destTop, destBottom);

                Bitmap bm90 =
                    Bitmap.createBitmap(SAMPLE_WIDTH, SAMPLE_HEIGHT, Bitmap.Config.ARGB_8888);
                bm90 = getBitmap90(bm90, destTop, destBottom);

                if (results == null) {
                    results = new Bitmap[2];
                }
                bitmaps[0] = ImageUtils.fastBlur(bm0, 1.0f, 25);
                bitmaps[1] = ImageUtils.fastBlur(bm90, 1.0f, 25);
            }
        }
        if (bitmaps == null || bitmaps[0] == null || bitmaps[1] == null) {
            return -1;
        }
        if (results == null) {
            results = new Bitmap[2];
        }
        if (mOrientation % 360 == 0) {
            results[0] = rotate180(bitmaps[1]);
            results[1] = bitmaps[0];
        } else if (mOrientation % 360 == 90) {
            results[0] = bitmaps[0];
            results[1] = bitmaps[1];
        } else if (mOrientation % 360 == 180) {
            results[0] = bitmaps[1];
            results[1] = rotate180(bitmaps[0]);
        } else if (mOrientation % 360 == 270) {
            results[0] = rotate180(bitmaps[0]);
            results[1] = rotate180(bitmaps[1]);
        }
        return 0;
    }

    public void onDestroy() {
        if (results != null) {
            for (Bitmap bm : results) {
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                }
            }
            results = null;
        }
        if (bitmaps != null) {
            for (Bitmap bm : bitmaps) {
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                }
            }
            bitmaps = null;
        }
        if (src != null && !src.isRecycled() && isSRCFromFile) {
            src.recycle();
            src = null;
        }
    }
}
