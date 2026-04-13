package com.easylife.diary.domain.models

enum class MapProvider(val value: String) {
    OPEN_STREET_MAP("osm"),
    GOOGLE("google");

    companion object {
        fun from(value: String?): MapProvider {
            return entries.firstOrNull { it.value == value } ?: OPEN_STREET_MAP
        }
    }
}
