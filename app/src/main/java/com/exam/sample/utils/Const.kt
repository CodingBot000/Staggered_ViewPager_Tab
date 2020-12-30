package com.exam.sample.utils

import com.exam.sample.R

class Const {
    companion object  {
        const val LOG_TAG = "GIPHY_TASK"
        const val API_KEY = "TXdYkIoK90rIz9Mz3QAIKsKgHyO073Vn"
        const val BASE_URL = "https://api.giphy.com/v1/"

        const val EXTRA_KEY_INTERACTION = "INTERACTION"
        const val LIMIT = 15 //The maximum number of objects to return. (Default: “25”)
        const val OFFSET_DEFAULT = 0  //Specifies the starting position of the results. Defaults to 0.
        const val EMIT_INTERVAL = 1000L
        val TAB_TITLES = arrayListOf(R.string.tabNameTrending, R.string.tabNameArtists,
            R.string.tabNameClips)

        const val DB_NAME = "GiphyFavoriteDB.db"
        const val DB_SELECT = "SELECT"
        const val DB_INSERT = "INSERT"
        const val DB_DELETE = "DELETE"
        const val DB_UPDATE = "UPDATE"

        const val USE_FOREGROUND =  false
        const val WORKER_TAG = "WORKER_CHANGE_DATA"
        const val NOTI_ID =  0
        const val NOTIFICATION_CHANNEL_ID = "10001"
        const val NOTIFICATION_CHANNEL_NAME = "giphy_new_data"
        const val DATA_CHANGE_CHECKING_INTERVAL = 20 * 1000.toLong() // 10 seconds


        val COLORS_RAINBOW = arrayListOf<Int>( R.color.colorRed, R.color.colorOrange, R.color.colorYellow,
            R.color.colorGreen, R.color.colorBlue, R.color.colorIndigo, R.color.colorPurple)

        var SCREEN_WIDTH = 0F
        var SCREEN_WIDTH_HALF = 0F

        const val HTTP_LOG = true
    }
}