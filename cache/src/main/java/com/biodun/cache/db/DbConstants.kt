package com.biodun.cache.db

object DbConstants {

    const val DATABASE_NAME = "news_db"

    const val NEWS_TABLE = "news_table"

    const val COLUMN_NEWS_TYPE = "news_type"

    const val GET_NEWS_QUERY = "SELECT * FROM $NEWS_TABLE WHERE $COLUMN_NEWS_TYPE = :newsType"
    const val DELETE_NEWS_QUERY = "DELETE FROM $NEWS_TABLE WHERE $COLUMN_NEWS_TYPE = :newsType"

    const val BUSINESS_NEWS = "Business News"
    const val ENTERTAINMENT_NEWS = "Entertainment News"
    const val SCIENCE_NEWS = "Science News"
    const val SPORTS_NEWS = "Sports News"
    const val HEALTH_NEWS = "Health News"
    const val TECHNOLOGY_NEWS = "Technology News"
    const val GENERAL_NEWS = "General News"

    const val DATA_NOT_FOUND = "Data not found in database"

    const val DB_VERSION = 1
}
