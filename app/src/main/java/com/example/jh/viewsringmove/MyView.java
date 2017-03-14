package com.example.jh.viewsringmove;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者：jinhui on 2017/2/20
 * 邮箱：1004260403@qq.com
 */

public class MyView extends View implements Runnable {

    Paint paint = new Paint();
    Bitmap bitmap;
    boolean isRun = true;
    //锚点
    int x = 0, y = 0;   //图片锚点
    int speed = 20;
    //定义位置
    int RIGHT = 0x01;
    int LEFT = 0x02;
    int UP = 0x03;
    int DOWN = 0x04;
    //定义方向
    int dir = RIGHT;

    int bitmapWidth, bitmapHight;

    public MyView(Context context) {
        super(context);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        //得到图片宽高
        bitmapWidth = bitmap.getWidth();
        bitmapHight = bitmap.getHeight();
        //不需要触摸事件,但是精灵移动属于耗时操作
        new Thread(this).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, x, y, paint);
        //移动位置
        if (dir == RIGHT) {
            x += speed;
        } else if (dir == DOWN) {
            y += speed;
        } else if (dir == LEFT) {
            x -= speed;
        } else if (dir == UP) {
            y -= speed;
        }
        //方向
        if (x > this.getWidth() - bitmapWidth) {
            dir = DOWN;
            //
            x = this.getWidth() - bitmapWidth;
        } else if (y > this.getHeight() - bitmapHight) {
            dir = LEFT;
            y = this.getHeight() - bitmapHight;
        } else if (x < 0) {
            dir = UP;
            x = 0;
        } else if (y < 0) {
            dir = RIGHT;
            y = 0;
        }
    }

    @Override
    public void run() {
        while (isRun) {
            /**不刷新界面是没有效果的,位置、反向逻辑写在ondraw（）里，因为不断线程运行中不断调用----*/
            this.postInvalidate();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
