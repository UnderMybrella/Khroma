package org.abimon.khroma.responses

data class RazerChromaResult(val result: Int) {
    val error: EnumRzError? = EnumRzError.valueFor(result)
}

enum class EnumRzError(val num: Int, val desc: String? = null) {
    RZRESULT_INVALID(-1, "Invalid"),
    RZRESULT_SUCCESS(0, "Success"),
    RZRESULT_ACCESS_DENIED(5, "Access denied"),
    RZRESULT_INVALID_HANDLE(6, "Invalid handle"),
    RZRESULT_NOT_SUPPORTED(50, "Not supported"),
    RZRESULT_INVALID_PARAMETER(87, "Invalid parameter"),
    RZRESULT_SERVICE_NOT_ACTIVE(1062, "The service has not been started"),
    RZRESULT_SINGLE_INSTANCE_APP(1152, "Cannot start more than one instance of the specified program"),
    RZRESULT_DEVICE_NOT_CONNECTED(1167, "Device not connected"),
    RZRESULT_NOT_FOUND(1168, "Element not found"),
    RZRESULT_REQUEST_ABORTED(1235, "Request aborted"),
    RZRESULT_ALREADY_INITIALIZED(1247, "An attempt was made to perform an initialization operation when initialization has already been completed"),
    RZRESULT_RESOURCE_DISABLED(4309, "Resource not available or disabled"),
    RZRESULT_DEVICE_NOT_AVAILABLE(4319, "Device not available or supported"),
    RZRESULT_NOT_VALID_STATE(5023, "The group or resource is not in the correct state to perform the requested operation"),
    RZRESULT_NO_MORE_ITEMS(259, "No more items"),
    RZRESULT_FAILED(2147500037.toInt(), "General failure");

    operator fun component1(): Int = num
    operator fun component2(): String? = desc

    companion object {
        fun valueFor(id: Int): EnumRzError? = values().firstOrNull { error -> error.num == id }
    }
}