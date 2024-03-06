package ch.bfh.cas.mad.tasktimetrackerapp.csvExport

import com.opencsv.CSVWriter
import java.io.FileWriter
import java.io.IOException
import android.os.Environment
import android.widget.Toast
import android.content.Context
class CSVExportHelper(private val context: Context) {

    /**
     * This function exports the provided data to a CSV file in the Downloads directory.
     *
     * @param data The data to be exported, represented as a list of string lists. Each inner list represents a row in the CSV file.
     * @param fileName The name of the file to be saved.
     *
     * The function first constructs the full path where the file will be saved by appending the fileName to the path of the Downloads directory.
     * It then creates a FileWriter and a CSVWriter.
     * It writes each row of data to the CSV file using the CSVWriter.
     * If the data is successfully written to the file, it shows a Toast message indicating the success of the operation.
     * If any error occurs during the process, it shows a Toast message indicating the failure of the operation.
     * Finally, it closes the CSVWriter and the FileWriter.
     */
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