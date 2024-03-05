package ch.bfh.cas.mad.tasktimetrackerapp.pdfExport

import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import java.io.ByteArrayOutputStream
import java.io.IOException

class PdfExportHelper(private val context: Context) {

    fun createAndSavePdf(fileName: String, entries: List<Entry>, projectOrTaskDescription: String) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 12f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }

        // Draw the main header
        val headerPaint = Paint(paint).apply {
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = Color.parseColor("#4dabf7")
        }
        val headerText = "Task  Time Tracker App - Exported Entries"
        page.canvas.drawText(headerText, 10f, 25f, headerPaint)

        // Draw the project or task header
        val projectOrTaskHeaderPaint = Paint(headerPaint).apply {
            textSize = 16f
        }
        val projectOrTaskHeaderText = projectOrTaskDescription
        page.canvas.drawText(projectOrTaskHeaderText, 10f, 50f, projectOrTaskHeaderPaint)

        // Render each entry on a new line
        var yPosition = 75f
        for (entry in entries) {
            val entryData = "ID: ${entry.id}, Description: ${entry.description}, TimeStamp: ${entry.timeStamp}"
            page.canvas.drawText(entryData, 10f, yPosition, paint)
            yPosition += paint.descent() - paint.ascent()
        }

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

    /**
     * This function saves the generated PDF file to the Downloads directory.
     *
     * @param fileName The name of the file to be saved.
     * @param pdfContent The content of the PDF file in byte array format.
     *
     * The function first creates a ContentValues object and sets the necessary attributes for the file.
     * It then gets a content resolver and inserts a new record into the MediaStore's Downloads directory.
     * If the URI returned from the insert operation is not null, it opens an output stream and writes the PDF content to it.
     * If the URI is null or any other IOException occurs, it throws an IOException.
     * After the PDF is successfully saved, it shows a Toast message indicating the success of the operation.
     * If any error occurs during the process, it shows a Toast message indicating the failure of the operation.
     */
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

            // Zeige eine Toast-Nachricht an, wenn das Exportieren erfolgreich war
            Toast.makeText(context, "PDF successfully exported to Download Folder", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            // Zeige eine Toast-Nachricht an, wenn das Exportieren failed
            Toast.makeText(context, "PDF export failed", Toast.LENGTH_SHORT).show()
            throw IOException("Failed to save PDF.", e)
        }
    }
}
