package org.abimon.khroma.requests

data class RazerChromaApplication(
    val title: String,
    val description: String,
    val author: RazerChromaApplicationAuthor,
    val device_supported: Array<String>,
    val category: String
) {
    constructor(
        title: String,
        description: String,
        author: RazerChromaApplicationAuthor,
        device_supported: Array<RazerChromaDevice>,
        category: String
    ): this(title, description, author, device_supported.map(RazerChromaDevice::tag).toTypedArray(), category)

    init {
        check(title.length <= 64)
        check(description.length <= 256)
        check(device_supported.isNotEmpty())
        check(category == "application" || category == "game")
    }
}

data class RazerChromaApplicationAuthor(
    val name: String,
    val contact: String
) {
    init {
        check(name.length <= 64)
        check(contact.length <= 64)
    }
}

enum class RazerChromaDevice(val tag: String) {
    KEYBOARD("keyboard"),
    MOUSE("mouse"),
    HEADSET("headset"),
    MOUSEPAD("mousepad"),
    KEYPAD("keypad"),
    CHROMALINK("chromalink")
}

class RazerChromaApplicationBuilder {
    var title: String = "Razer Chroma SDK RESTful Test Application"
    var description: String = "This is a REST interface test application"
    var author: RazerChromaApplicationAuthor = RazerChromaApplicationAuthor("Chroma Developer", "www.razerzone.com")
    var device_supported: MutableList<String> = ArrayList()
    var category: String = "application"

    fun MutableList<String>.add(device: RazerChromaDevice) = add(device.tag)

    fun buildAuthor(init: RazerChromaApplicationAuthorBuilder.() -> Unit): RazerChromaApplicationAuthor {
        val builder = RazerChromaApplicationAuthorBuilder()
        builder.init()
        return builder.build()
    }

    fun build(): RazerChromaApplication = RazerChromaApplication(title, description, author, device_supported.toTypedArray(), category)
}

class RazerChromaApplicationAuthorBuilder {
    var name: String = "Chroma Developer"
    var contact: String = "www.razerzone.com"

    fun build(): RazerChromaApplicationAuthor = RazerChromaApplicationAuthor(name, contact)
}

fun buildChromaApplication(init: RazerChromaApplicationBuilder.() -> Unit): RazerChromaApplication {
    val builder = RazerChromaApplicationBuilder()
    builder.init()
    return builder.build()
}