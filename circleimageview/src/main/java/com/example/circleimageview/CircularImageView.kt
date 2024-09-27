package com.example.circleimageview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CircularImageView (context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    private var borderWidth = 4f
    private var circleCenter = 0f
    private var imageRadius = 0f

    init {
        paintBorder.color = Color.WHITE // Set default border color
        borderWidth = 8f // Set default border width
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable ?: return

        val bitmap = getBitmapFromDrawable(drawable)
        if (bitmap == null) {
            super.onDraw(canvas)
            return
        }

        setup()

        canvas.drawCircle(circleCenter, circleCenter, imageRadius + borderWidth, paintBorder)
        canvas.drawCircle(circleCenter, circleCenter, imageRadius, paint)

        paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        canvas.drawCircle(circleCenter, circleCenter, imageRadius, paint)
    }

    private fun setup() {
        circleCenter = (width.coerceAtMost(height) / 2).toFloat()
        imageRadius = circleCenter - borderWidth
    }

    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}