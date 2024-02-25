package ch.bfh.cas.mad.tasktimetrackerapp.pdfExport

import android.content.Context
import android.graphics.Paint
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Environment

import ch.bfh.cas.mad.tasktimetrackerapp.pdfExport.PaintUtil.createTextPaint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.roundToInt


object PdfExportHelper {

    var textPaint: Paint = createTextPaint(12, Color.BLACK ) // 12pt Textgröße, schwarze Farbe

    fun createPdf(context: Context?, content: Array<String?>) {
        // Erstellen eines neuen PDF-Dokuments
        val document = PdfDocument()

        // Erstellen einer Seite für das Dokument
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = document.startPage(pageInfo)

        // Hier können Sie den Inhalt auf die Seite zeichnen
        var yPosition = 20
        for (line in content) {
            page.canvas.drawText(
                line!!,
                10f,
                yPosition.toFloat(),
                textPaint
            ) // 'paint' ist eine Instanz von Paint, konfiguriert für Ihren Text
            yPosition += (textPaint.descent() - textPaint.ascent()).roundToInt()        }

        // Beenden der Seite
        document.finishPage(page)

        // Speichern des Dokuments in einer Datei
        val directoryPath = Environment.getExternalStorageDirectory().path + "/MeinePDFs/"
        val file = File(directoryPath, "MeinExport.pdf")
        try {
            file.parentFile?.mkdirs() // Stellen Sie sicher, dass das Verzeichnis existiert
            document.writeTo(FileOutputStream(file))
            // PDF erfolgreich gespeichert
        } catch (e: IOException) {
            e.printStackTrace()
            // Fehler beim Speichern des PDFs
        }

        // Schließen des Dokuments
        document.close()
    }
}

