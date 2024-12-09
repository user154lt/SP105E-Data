import java.awt.Color
import java.util.*

class SP105EDataProvider {

    enum class StepDirection{
        INCREASE,
        DECREASE,
    }

    enum class RGBOrder {
        RGB,
        RBG,
        GRB,
        GBR,
        BRG,
        BGR
    }

    val serviceUUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb")
    val characteristicUUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb")

    fun makePowerToggleData() = byteArrayOf(
        0x38.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        0xAA.toByte(),
    )

    fun makeColorData(color: Color) = byteArrayOf(
        0x9A.toByte(),
        color.red.toByte(),
        color.green.toByte(),
        color.blue.toByte(),
        0x1E.toByte(),
    )

    fun makePatternData(index: Int) = byteArrayOf(
        0x38.toByte(),
        index.coerceIn(1..200).toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        0x2C.toByte(),
    )

    fun makeSpeedData(stepDirection: StepDirection) = byteArrayOf(
        0x38.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        if (stepDirection == StepDirection.INCREASE) 0x03.toByte() else 0x09.toByte()
    )

    fun makeBrightnessData(stepDirection: StepDirection) = byteArrayOf(
        0x38.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        if (stepDirection == StepDirection.INCREASE) 0x2A.toByte() else 0x28.toByte(),
    )

    fun makePixelNumberData(pixels: Int): ByteArray {
        val pixelBytes = makePixelBytes(pixels.coerceIn(1..2048))
        return byteArrayOf(
            0x38.toByte(),
            pixelBytes.first,
            pixelBytes.second,
            0x9A.toByte(),
            0x2D.toByte(),
        )
    }

    private fun makePixelBytes(pixels: Int): Pair<Byte, Byte>{
        val upperByte = pixels shr 8
        val lowerByte = (pixels shl 8) shr 8
        return Pair(upperByte.toByte(), lowerByte.toByte())
    }

    fun makePixelRGBOrder(order: RGBOrder) = byteArrayOf(
        0x38.toByte(),
        order.ordinal.toByte(),
        0x9A.toByte(),
        0x9A.toByte(),
        0x3C.toByte()
    )
}