package com.shrihari.smartcampusnavigator.data.model

data class FingerprintSample(

    val node: String,

    val timestamp: Long,

    val session: Int,

    val rssiValues: Map<String, Int>

)