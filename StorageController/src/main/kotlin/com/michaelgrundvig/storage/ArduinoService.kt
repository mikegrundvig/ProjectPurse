package com.michaelgrundvig.storage

import com.rm5248.serial.SerialPort
import com.rm5248.serial.SerialPortBuilder
import java.util.concurrent.ConcurrentHashMap

class ArduinoService(portName:String) {

    private val sentCommands: ConcurrentHashMap<Int, Command> = ConcurrentHashMap()
    private var messageNumber = 0

    private val serialPort = SerialPortBuilder()
        .setBaudRate(SerialPort.BaudRate.B115200)
        .setPort(portName)
        .build()

    fun sendCommand(cmd:Command) {

        // Get the new message number
        val newMessageNumber = messageNumber++

        // First track the command
        sentCommands[newMessageNumber] = cmd

        // Send the command to the arduino
        serialPort.outputStream.write(
            "${newMessageNumber}:${cmd.message}".toByteArray()
        )
    }

    fun readResponse(): String {
        var line = ""
        while(true) {
            val char = serialPort.inputStream.read().toChar()
            if(char == '\n') {
                break
            } else if(char == '\r') {
                // do nothing
            } else {
                line += char
            }
        }
        return line
    }

}

interface Command {
    val message: String
    var status: CommandStatus
    //fun validateResponse(response: String): Boolean = response == "${messageNumber}:ok"
}

enum class CommandStatus {
    Created, Sent, Acknowledged, Aborted, Complete, Error
}

class HomeStepper(
    stepperId:Int,
    onSuccess: () -> Unit
    ) : Command {

    override var status = CommandStatus.Created
    override val message = "h:${stepperId}\n"
}

class MoveStepper(
        stepperId:Int,
        targetPosition:Int,
        currentPosition:Int?,
        onSuccess: () -> Unit
    ) : Command {

    override var status = CommandStatus.Created
    override val message:String

    init {
        message = if(currentPosition == null) {
            "m:${stepperId}:${targetPosition}\n"
        } else {
            "m:${stepperId}:${targetPosition}:${currentPosition}\n"
        }
    }
}