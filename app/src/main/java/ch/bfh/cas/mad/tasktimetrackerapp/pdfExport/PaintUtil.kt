package ch.bfh.cas.mad.tasktimetrackerapp.pdfExport

import android.graphics.Paint
import android.graphics.Typeface


object PaintUtil {
    fun createTextPaint(textSize: Int, textColor: Int): Paint {
        val paint = Paint()
        paint.color = textColor // Setzen der Textfarbe
        paint.textSize = textSize.toFloat() // Setzen der Textgröße
        paint.setTypeface(
            Typeface.create(
                Typeface.DEFAULT,
                Typeface.NORMAL
            )
        ) // Setzen des Schrifttyps
        paint.isAntiAlias = true // Aktivieren der Kantenglättung
        return paint
    }
}