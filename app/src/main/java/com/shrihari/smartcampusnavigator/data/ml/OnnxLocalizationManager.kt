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

    private val environment = OrtEnvironment.getEnvironment()

    private val session: OrtSession

    init {

        val modelBytes = context.assets
            .open("random_forest_model.onnx")
            .readBytes()

        session = environment.createSession(modelBytes)

    }

    fun predictNode(
        rssi: FloatArray
    ): String {

        val inputTensor = OnnxTensor.createTensor(

            environment,

            FloatBuffer.wrap(rssi),

            longArrayOf(1, 7)

        )

        val result = session.run(

            mapOf(
                "float_input" to inputTensor
            )

        )

        val prediction = result[0].value as LongArray

        val predictedClass = prediction[0].toInt()

        return "N${predictedClass + 13}"
    }

}