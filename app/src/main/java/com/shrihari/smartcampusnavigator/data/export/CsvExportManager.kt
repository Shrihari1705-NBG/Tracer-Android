package com.shrihari.smartcampusnavigator.data.export

import android.content.Context
import com.shrihari.smartcampusnavigator.data.model.FingerprintSample
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CsvExportManager(
    private val context: Context
) {

    fun exportSamples(
        node: String,
        samples: List<FingerprintSample>
    ): File {

        // Folder
        val exportFolder = File(
            context.getExternalFilesDir(null),
            "Tracer"
        )

        if (!exportFolder.exists()) {
            exportFolder.mkdirs()
        }

        // File Name
        val formatter = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        )

        val fileName =
            "Tracer_${node}_${formatter.format(Date())}.csv"

        val csvFile = File(
            exportFolder,
            fileName
        )

        csvFile.bufferedWriter().use { writer ->

            // --------------------------------------------------
            // CSV Header
            // --------------------------------------------------

            writer.append(
                "Timestamp,Node,B1,B2,B3,B4,B5,B6,B7"
            )

            writer.newLine()

            // --------------------------------------------------
            // CSV Rows
            // --------------------------------------------------

            samples.forEach { sample ->

                writer.append(
                    sample.timestamp.toString()
                )

                writer.append(",")

                writer.append(sample.node)

                for (i in 1..7) {

                    writer.append(",")

                    writer.append(

                        sample.rssiValues[
                            "TRACER_B$i"
                        ]?.toString() ?: "-100"

                    )

                }

                writer.newLine()

            }

        }

        return csvFile

    }

}