package ch.bfh.cas.mad.tasktimetrackerapp.CSVExport

import com.opencsv.CSVWriter
import java.io.FileWriter
import java.io.IOException
import android.os.Environment
import android.widget.Toast
import android.content.Context
class CSVExportHelper(private val context: Context) {

    fun exportToCsv(data: List<List<String>>, fileName: String) {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
        val fullPath = "$path/$fileName"

        var fileWriter: FileWriter? = null
        var csvWriter: CSVWriter? = null

        try {
            fileWriter = FileWriter(fullPath)
            csvWriter = CSVWriter(fileWriter)

            // Schreiben Sie die Daten in die CSV-Datei
            for (row in data) {
                csvWriter.writeNext(row.toTypedArray())
            }

            // Zeige eine Toast-Nachricht an, wenn das Exportieren erfolgreich war
            Toast.makeText(context, "CSV successfully exported to Download Folder", Toast.LENGTH_SHORT).show()


        } catch (e: Exception) {
            e.printStackTrace()

            // Zeige eine Toast-Nachricht an, wenn das Exportieren fehlgeschlagen ist
            Toast.makeText(context, "CSV export failed", Toast.LENGTH_SHORT).show()

        } finally {
            try {
                csvWriter?.close()
                fileWriter?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}