package ch.bfh.cas.mad.tasktimetrackerapp.pdfExport

import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.IOException

class PdfExportHelper(private val context: Context) {

    fun createAndSavePdf(fileName: String, textContent: Array<String>) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }

        // Simple text rendering
        page.canvas.drawText(textContent.toString(), 10f, 25f, paint)

        pdfDocument.finishPage(page)

        val outputStream = ByteArrayOutputStream()
        try {
            pdfDocument.writeTo(outputStream)
            savePdfToDownloads(fileName, outputStream.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            pdfDocument.close()
        }
    }

    private fun savePdfToDownloads(fileName: String, pdfContent: ByteArray) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        try {
            uri?.let {
                resolver.openOutputStream(it).use { outputStream ->
                    outputStream?.write(pdfContent)
                }
            } ?: throw IOException("Failed to create new MediaStore record.")
        } catch (e: IOException) {
            throw IOException("Failed to save PDF.", e)
        }
    }
}
