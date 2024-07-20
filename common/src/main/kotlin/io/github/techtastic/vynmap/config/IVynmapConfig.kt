package io.github.techtastic.vynmap.config

interface IVynmapConfig {
    fun useUnloadedIcon(): Boolean
    fun getMarkersDisplayed(): MarkersDisplayed
    fun showShipID(): Boolean
    fun showShipMass(): Boolean
    fun showShipVelocity(): Boolean
}