package com.michaelgrundvig.storage.drawer

import com.michaelgrundvig.storage.*
import io.jsondb.annotation.Document
import io.jsondb.annotation.Id
import java.net.InetAddress

@Document(collection = "Drawers", schemaVersion = "1.0")
data class DrawerDTO(
    @Id
    val id: Int = 0,
    val slideVertical: Int = 0,
    val cameraVertical: Int = 0,
    val cameraStepperId: Int,
    val slideStepperId: Int,
    val rows: Int = 0,
    val columns: Int = 0,
    val cameraControllerIp: String = "",
) {

    constructor(drawer: Drawer) : this(
        id = drawer.id,
        slideVertical = drawer.slideVertical,
        cameraVertical = drawer.cameraVertical,
        cameraStepperId = drawer.cameraStepperId,
        slideStepperId = drawer.slideStepperId,
        rows = drawer.rows,
        columns = drawer.columns,
        cameraControllerIp = drawer.cameraController.ip.toString()
    )

    fun toDrawer():Drawer {
        return Drawer(
            id = this.id,
            slideVertical = this.slideVertical,
            cameraVertical = this.cameraVertical,
            cameraStepperId = this.cameraStepperId,
            slideStepperId = this.slideStepperId,
            rows = this.rows,
            columns = this.columns,
            cameraController = CameraController(InetAddress.getByName(cameraControllerIp))
        )
    }
}


data class Drawer(
    val id: Int,
    val slideVertical: Int,
    val cameraVertical: Int,
    val cameraStepperId: Int,
    val slideStepperId: Int,
    val rows: Int,
    val columns: Int,
    val cameraController: CameraController) {

    private val cameraStepper: Stepper = Stepper(cameraStepperId)
    private val slideStepper: Stepper = Stepper(slideStepperId)

}

class Cabinet(
        cameraMultiplexerId:Int,
        slideMultiplexerId:Int,
        private val drawers:Map<Int, Drawer>,
        private val arduino: ArduinoService) {

    private val cameraStepper: Stepper = Stepper(cameraMultiplexerId)
    private val slideStepper: Stepper = Stepper(slideMultiplexerId)

    private var activeChain = emptyList<Command>()

    fun executeCommandChain(commands:List<Command>) {
        if(activeChain.isNotEmpty()) {
            throw Error("Can't create a new command chain without aborting the existing")
        }
        activeChain = commands
    }

    fun homeCameraMultiplexer() {
        val cmd = HomeStepper(cameraStepper.id) {
            cameraStepper.homed = true
            cameraStepper.position = 0
        }
        arduino.sendCommand(cmd)
    }

    fun homeSlideMultiplexer() {
        val cmd = HomeStepper(slideStepper.id) {
            slideStepper.homed = true
            slideStepper.position = 0
        }
        arduino.sendCommand(cmd)
    }
//
//    fun moveCameraMultiplexerToDrawer(drawerId:Int) {
//        if(!cameraStepper.homed) {
//            throw Error("Trying to move the camera multiplexer but it isn't homed")
//        }
//        val drawer = drawers[drawerId]
//        val cmd = MoveStepper() {
//            drawer.cameraVertical
//        }
//
//    }

}