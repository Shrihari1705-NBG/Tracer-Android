package com.shrihari.smartcampusnavigator.data.ml

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.FloatBuffer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnnxLocalizationManager @Inject constructor(

    @ApplicationContext
    private val context: Context

) {

    private val environment =
        OrtEnvironment.getEnvironment()

    private val session: OrtSession

    init {

        val modelBytes =
            context.assets
                .open("random_forest_model.onnx")
                .readBytes()

        session =
            environment.createSession(modelBytes)
    }


    fun predictNode(
        rssi: FloatArray
    ): String {

        require(rssi.size == 15) {
            "Random Forest ONNX model requires 15 RSSI features, but received ${rssi.size}"
        }

        val inputTensor =
            OnnxTensor.createTensor(
                environment,
                FloatBuffer.wrap(rssi),
                longArrayOf(1, 15)
            )

        inputTensor.use {

            val result =
                session.run(
                    mapOf(
                        "input" to inputTensor
                    )
                )

            result.use {

                val output =
                    result[0].value

                @Suppress("UNCHECKED_CAST")
                val predictions =
                    output as Array<String>

                return predictions[0]
            }
        }
    }
}