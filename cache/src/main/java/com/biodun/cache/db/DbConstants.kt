package com.biodun.cache.db

object DbConstants {
    const val NEWS_RESULT_TABLE_NAME = "CacheNewsResultTable"
    const val GET_NEWS_RESULTS_QUERY = "SELECT * FROM $NEWS_RESULT_TABLE_NAME"
    const val DELETE_NEWS_RESULTS_QUERY = "DELETE FROM $NEWS_RESULT_TABLE_NAME"
}
