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

        // --------------------------------------------------
        // Export Folder
        // --------------------------------------------------

        val exportFolder = File(
            context.getExternalFilesDir(null),
            "Tracer"
        )

        if (!exportFolder.exists()) {
            exportFolder.mkdirs()
        }

        // --------------------------------------------------
        // File Name
        // --------------------------------------------------

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

        // --------------------------------------------------
        // Write CSV
        // --------------------------------------------------

        csvFile.bufferedWriter().use { writer ->

            // --------------------------------------------------
            // CSV Header
            // --------------------------------------------------

            writer.append(
                "Timestamp,Node,Session,B1,B2,B3,B4,B5,B6,B7,B8,B9,B10,B11,B12,B13,B14,B15"
            )

            writer.newLine()

            // --------------------------------------------------
            // CSV Rows
            // --------------------------------------------------

            samples.forEach { sample ->

                // Timestamp
                writer.append(
                    sample.timestamp.toString()
                )

                writer.append(",")

                // Node
                writer.append(
                    sample.node
                )

                writer.append(",")

                // Session
                writer.append(
                    sample.session.toString()
                )

                // --------------------------------------------------
                // Export all 15 Tracer Beacons
                // Missing beacon = -100 RSSI
                // --------------------------------------------------

                for (i in 1..15) {

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