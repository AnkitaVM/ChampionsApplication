package com.example.championsapplication.data.db


const val DATABASE_CHAMPIONS = "champions_db"
const val DATABASE_VERSION = 1

class ChampionTable {
    companion object {
        const val TABLE_CHAMPION = "champion"
        const val COLUMN_ID = "id"
        const val COLUMN_KEY = "key"
        const val COLUMN_NAME = "name"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BLURB = "blurb"
        const val COLUMN_IMAGE_FULL = "image_full"
    }
}

