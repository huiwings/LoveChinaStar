package com.huiwings.lovestar.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.huiwings.lovestar.R;


public class LoveChinaView extends View {

    private final String TAG = getClass().getSimpleName();

    private Paint mPaint;//红旗画笔
    //private Paint starPaint;//星星画笔
    private int width, height;//红旗宽高  比例为3:2


    public LoveChinaView(Context context) {
        super(context);
        init();
    }

    public LoveChinaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoveChinaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoveChinaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = (int) (((float) width) * (2f / 3f));

        Log.d(TAG, "w:" + width + "\nh:" + height);

        drawRedFlag(mPaint, canvas);
        drawStar(mPaint, canvas);

    }

    //绘制红旗背景
    private void drawRedFlag(Paint paint, Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.flag_red));
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRect(rectF, paint);
    }

    /*
    一、为便于确定五星之位置，先将旗面对分为四个相等的长方形，将左上方之长方形上下划为十等分，左右划为十五等分；
    二、大五角星的中心点，在该长方形上五下五、左五右十之处。其画法为：以此点为圆心，以三等分为半径作一圆。
        在此圆周上，定出五个等距离的点，其一点须位于圆之正上方。然后将此五点中各相隔的两点相联，使各成一直线。
        此五直线所构成之外轮廓线，即为所需之大五角星。五角星之一个角尖正向上方；
    三、四颗小五角星的中心点，第一点在该长方形上二下八、左十右五之处，第二点在上四下六、左十二右三之处，
        第三点在上七下三、左十二右三之处，第四点在上九下一、左十右五之处。其画法为：以以上四点为圆心，
        各以一等分为半径，分别作四个圆。在每个圆上各定出五个等距离的点，其中均须各有一点位于大五角星中心点与
        以上四个圆心的各联结线上。然后用构成大五角星的同样方法，构成小五角星。此四颗小五角星均各有一个
        角尖正对大五角星的中心点
     */
    //绘制星星
    private void drawStar(Paint paint, Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(getResources().getColor(R.color.star_ye));

        int w = width / 30;//辅助宽度
        int h = height / 20;//辅助高度

        Path path = createStarPath(new PointF(w * 6, h * 5), w * 3, -90);
        canvas.drawPath(path, paint);

        Path star01 = createStarPath(new PointF(w * 10, h * 2), w, (float) (Math.atan2(3, -5) / Math.PI * 180));
        Path star02 = createStarPath(new PointF(w * 12, h * 4), w, (float) (Math.atan2(1, -7) / Math.PI * 180));
        Path star03 = createStarPath(new PointF(w * 12, h * 7), w, (float) (Math.atan2(-2, 7) / Math.PI * 180));
        Path star04 = createStarPath(new PointF(w * 10, h * 9), w, (float) (Math.atan2(-4, 5) / Math.PI * 180 - 90));


        canvas.drawPath(star01, paint);
        canvas.drawPath(star02, paint);
        canvas.drawPath(star03, paint);
        canvas.drawPath(star04, paint);
        path.close();
        star01.close();
        star02.close();
        star03.close();
        star04.close();
    }


    /**
     * 创建星星Path
     *
     * @param centerPointF 中心点
     * @param radius       半径
     * @param rotate       角度
     * @return Path
     */
    private Path createStarPath(PointF centerPointF, float radius, float rotate) {

        final double arc = Math.PI / 5;
        final double rad = Math.sin(Math.PI / 10) / Math.sin(3 * Math.PI / 10);
        Path path = new Path();
        path.moveTo(1, 0);
        for (int idx = 0; idx < 5; idx++) {
            path.lineTo((float) (rad * Math.cos((1 + 2 * idx) * arc)),
                    (float) (rad * Math.sin((1 + 2 * idx) * arc)));
            path.lineTo((float) (Math.cos(2 * (idx + 1) * arc)),
                    (float) (Math.sin(2 * (idx + 1) * arc)));
        }
        path.close();
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        matrix.postScale(radius, radius);
        matrix.postTranslate(centerPointF.x, centerPointF.y);
        path.transform(matrix);
        return path;
    }
}
