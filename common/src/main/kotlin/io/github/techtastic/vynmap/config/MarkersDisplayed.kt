package io.github.techtastic.vynmap.config

enum class MarkersDisplayed {
    ALL,
    POLYLINE,
    ICON,
    NONE;

    fun canShowAnyMarkers(): Boolean =
            this != NONE

    fun canShowPolylineMarkers(): Boolean =
            this == POLYLINE || this == ALL

    fun canShowIconMarkers(): Boolean =
            this == ICON || this == ALL
}