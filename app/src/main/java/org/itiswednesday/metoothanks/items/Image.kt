package org.itiswednesday.metoothanks.items

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.MenuItem
import org.itiswednesday.metoothanks.CanvasView
import org.itiswednesday.metoothanks.R


class Image(bitmap: Bitmap, hostView: CanvasView) : Item(hostView, bitmap.width, bitmap.height) {
    override fun handleMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_image_rotate_clockwise        -> rotateImage(90f)
            R.id.action_image_rotate_counterclockwise -> rotateImage(-90f)
            else                                      -> return false
        }

        canvas.postInvalidate()

        return true
    }

    override val itemMenuID: Int = R.menu.item_image_menu

    var bitmapDrawable = BitmapDrawable(hostView.context.resources, bitmap).apply {
        setBounds(0, 0, right, bottom)
        gravity = Gravity.FILL
    }

    override fun draw(canvas: Canvas) {
        paint.alpha = 255

        with(bitmapDrawable) {
            setBounds(left, top, right, bottom)
            draw(canvas)
        }
        this.canvas.postInvalidate()
    }

    private fun rotateImage(angle: Float) {
        fun rotate(source: Bitmap, angle: Float): Bitmap =
                Bitmap.createBitmap(source, 0, 0, source.width, source.height,
                                    Matrix().apply { postRotate(angle) }, true)

        swapWidthHeight()
        bitmapDrawable = BitmapDrawable(canvas.resources, rotate(bitmapDrawable.bitmap, angle))
        canvas.postInvalidate()
    }

    private fun swapWidthHeight() {
        val tmp = height
        bottom = top + width
        right = left + tmp
    }
}

