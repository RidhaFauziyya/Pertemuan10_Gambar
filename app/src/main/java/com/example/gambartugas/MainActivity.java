package com.example.gambartugas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Canvas mCanvas;
    private Paint mPaint = new Paint(); //digunakan untuk menggambar objek
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG); //digunakan untuk membuat tulisan atau label
    private Bitmap mBitmap;
    private ImageView mImageView;

    private static final int OFFSET = 100; //untuk mengatur posisi dari objek
    private int mOffset = OFFSET;
    private int mRadius = 450;
    private int mX = 330;
    private int mY = 700;
    private static final int MULTIPLER = 14;
    private Rect mBounds = new Rect();


    private int mColorBackground;
    private int mColorHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorHome = ResourcesCompat.getColor(getResources(), R.color.colorHome, null);

        mPaint.setColor(mColorBackground);
        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.white, null));
        mPaintText.setTextSize(70);

        mImageView = findViewById(R.id.myimageview);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSomething(v);
            }
        });
    }

    public void drawSomething(View view){
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth/2;
        int halfHeight = vHeight/2;

        if (mOffset == OFFSET){
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap); //baru draw text
            mCanvas.drawColor(mColorBackground); //untuk memberi warna pada background

            mCanvas.drawText(getString(R.string.keep_tapping), 100, 150, mPaintText);
            mOffset += OFFSET;
        }
        else {
            if (mOffset >= 100 && mOffset <= 300){
                mPaint.setColor(mColorHome - MULTIPLER * mOffset);
                mCanvas.drawCircle(mOffset+mX, mOffset+mY,  mRadius, mPaint);
                mOffset += OFFSET;
                mX -= 100;
                mY -= 100;
                mRadius -= 150;
            }
            else {
                mRadius = mRadius - 100;

                mPaint.setColor(mColorBackground);
                mCanvas.drawCircle(halfWidth-70, halfHeight-190, mRadius, mPaint);

                mPaint.setColor(mColorBackground);
                mCanvas.drawCircle(halfWidth+70, halfHeight-190, mRadius, mPaint);

                mPaint.setColor(mColorBackground);
                mCanvas.drawCircle(halfWidth-70, halfHeight-40, mRadius, mPaint);

                mPaint.setColor(mColorBackground);
                mCanvas.drawCircle(halfWidth+70, halfHeight-40, mRadius, mPaint);

                String text = getString(R.string.done);

                // Get bounding box for text to calculate where to draw it.
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);

                // Calculate x and y for text so it's centered.
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY() + 450;
                mCanvas.drawText(text, x, y, mPaintText);

            }
        }
        view.invalidate();
    }
}