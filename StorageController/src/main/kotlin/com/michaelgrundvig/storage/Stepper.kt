package com.michaelgrundvig.storage

data class Stepper(
    val id:Int,
    var homed:Boolean = false,
    var position: Int = -1)

//// Vertical steppers that control other steppers
//data class MultiplexingStepper(
//    val id:Int,
//    val steps:Int = 200,
//    var homed:Boolean = false,
//    var position: Int = -1,
//    val steppers: Map<Int, Stepper>) {
//
//}
//
//class Cabinet {
//
//    val drawerSteppers = mapOf(
//        0 to Stepper(1, 200),
//        1 to Stepper(1, 400),
//        2 to Stepper(1, 600),
//        3 to Stepper(1, 800),
//        4 to Stepper(1, 1000),
//        5 to Stepper(1, 1200)
//    )
//
//    val drawerVertical = MultiplexingStepper(0, steppers = drawerSteppers)
//
//    val cameraSteppers = mapOf(
//        0 to Stepper(1, 200),
//        1 to Stepper(1, 400),
//        2 to Stepper(1, 600),
//        3 to Stepper(1, 800),
//        4 to Stepper(1, 1000),
//        5 to Stepper(1, 1200)
//    )
//
//    val cameraVertical = MultiplexingStepper(2, steppers = cameraSteppers)
//
//    fun homeStepper() {
//
//    }
//}
