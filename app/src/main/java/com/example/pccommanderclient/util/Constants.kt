package com.example.pccommanderclient.util

object Constants {
    const val URL_REGEX =
        "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"
    const val INTENT_TYPE_TEXT_PLAIN = "text/plain"
}

object MediaKeys {
    const val PLAY_STOP = "play/stop"
    const val PREVIOUS = "previous"
    const val NEXT = "next"
    const val VOLUME_UP = "volume_up"
    const val VOLUME_DOWN = "volume_down"
}

object Commands {
    const val HIBERNATE = "rundll32.exe powrprof.dll SetSuspendState Sleep"
    fun startChromeWithUrl(clipboardData: String) = "cmd /c start chrome $clipboardData"
}