package com.michaelgrundvig.storage.drawer

import mu.KotlinLogging
import java.net.InetAddress

enum class Status {
    Online, Offline
}

data class CameraController(val ip: InetAddress) {

    private val logger = KotlinLogging.logger {}

    var status: Status = Status.Offline

    init {
        logger.warn { "do stuff!" }
    }
}

data class MotorController(val ip: InetAddress) {
    var status: Status = Status.Offline
    var drawerHomed: Boolean = false
    var cameraHomed: Boolean = false
}
