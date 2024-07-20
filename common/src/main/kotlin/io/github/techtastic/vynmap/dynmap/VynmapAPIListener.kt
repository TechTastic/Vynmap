package io.github.techtastic.vynmap.dynmap

import dev.architectury.event.events.common.TickEvent
import io.github.techtastic.vynmap.PlatformUtils
import io.github.techtastic.vynmap.VynmapMod.MOD_ID
import io.github.techtastic.vynmap.config.IVynmapConfig
import net.minecraft.server.level.ServerLevel
import org.dynmap.DynmapCommonAPI
import org.dynmap.DynmapCommonAPIListener
import org.dynmap.markers.*
import org.joml.Vector3d
import org.joml.primitives.AABBdc
import org.valkyrienskies.core.api.ships.QueryableShipData
import org.valkyrienskies.core.api.ships.ServerShip
import org.valkyrienskies.mod.common.shipObjectWorld
import kotlin.random.Random

object VynmapAPIListener: DynmapCommonAPIListener() {
    private var commonAPI: DynmapCommonAPI? = null
    private val config
        get() = PlatformUtils.getConfig()

    fun register() {
        DynmapCommonAPIListener.register(this)
        TickEvent.SERVER_LEVEL_POST.register(this::updateMarkers)
    }

    override fun apiEnabled(api: DynmapCommonAPI?) {
        if (api == null)
            return
        this.commonAPI = api
    }

    fun updateMarkers(level: ServerLevel) {
        val world = PlatformUtils.getWorldName(level)
        val allShips = level.shipObjectWorld.allShips
        val loadedShips = level.shipObjectWorld.loadedShips

        // Get or Create MarkerSet
        val markerSet = getOrCreateMarkerSet() ?: return

        // Update Markers
        val showIconMarkers = config.getMarkersDisplayed().canShowIconMarkers()
        clearUnusedIconMarkers(markerSet, allShips)
        if (showIconMarkers)
            allShips.forEach { ship -> renderShipIconMarker(ship, markerSet, world, loadedShips.contains(ship.id)) }

        // Update Polyline Markers
        val showPolyLineMarker = config.getMarkersDisplayed().canShowPolylineMarkers()
        clearUnusedPolylineMarkers(markerSet, allShips)
        if (showPolyLineMarker)
            allShips.forEach { ship -> renderShipPolylineMarker(ship, markerSet, world) }
    }


    private fun clearUnusedIconMarkers(markerSet: MarkerSet, data: QueryableShipData<ServerShip>) {
        markerSet.markers?.forEach { marker -> clearUnusedMarker(marker, data, this.config.getMarkersDisplayed().canShowIconMarkers()) }
    }
    private fun clearUnusedPolylineMarkers(markerSet: MarkerSet, data: QueryableShipData<ServerShip>) {
        markerSet.polyLineMarkers?.forEach { marker -> clearUnusedMarker(marker, data, this.config.getMarkersDisplayed().canShowPolylineMarkers()) }
    }
    private fun clearUnusedMarker(marker: GenericMarker, data: QueryableShipData<ServerShip>, enabled: Boolean) {
        val id = marker.markerID.replace("ship", "").toLong()
        if (data.getById(id) == null || !enabled)
            marker.deleteMarker()
    }

    private fun renderShipIconMarker(data: ServerShip, markerSet: MarkerSet, world: String, isLoaded: Boolean) {
        val pos = data.transform.positionInWorld
        val label = createShipLabel(data, config)
        val icon = if (isLoaded)
            getOrCreateLoadedIcon()
        else
            getOrCreateUnloadedIcon()

        val marker: Marker = markerSet.findMarker("ship${data.id}") ?: run {
            markerSet.createMarker("ship${data.id}", label, true, world, pos.x(), pos.y(), pos.z(), icon, true)
            return
        }
        marker.description = label
        marker.setLocation(world, pos.x(), pos.y(), pos.z())
        marker.markerIcon = icon
    }
    private fun renderShipPolylineMarker(data: ServerShip, markerSet: MarkerSet, world: String) {
        val arrays = getArraysFromAABB(data.worldAABB)
        val label = createShipLabel(data, config)
        val marker: PolyLineMarker = markerSet.findPolyLineMarker("ship${data.id}") ?: run {
            val self = markerSet.createPolyLineMarker("ship${data.id}", label, true, world, arrays.first, arrays.second, arrays.third, true)
            self?.setLineStyle(5, self.lineOpacity, Random.nextInt(0x000000, 0xFFFFFF))
            self ?: return
        }
        marker.description = label
        marker.setCornerLocations(arrays.first, arrays.second, arrays.third)
    }


    private fun getOrCreateLoadedIcon(): MarkerIcon? =
            commonAPI?.markerAPI?.getMarkerIcon("loaded_ship") ?: run {
                PlatformUtils.getDynmapServer()?.let {
                    val icon = commonAPI?.markerAPI?.createMarkerIcon("loaded_ship", "ship", it.openResource(MOD_ID, PlatformUtils.getIconPath()))
                    icon
                }
            }

    private fun getOrCreateUnloadedIcon(): MarkerIcon? =
            commonAPI?.markerAPI?.getMarkerIcon("unloaded_ship") ?: run {
                PlatformUtils.getDynmapServer()?.let {
                    val icon = commonAPI?.markerAPI?.createMarkerIcon("unloaded_ship", "ship", it.openResource(MOD_ID, "assets/vynmap/dynmap/unloaded"))
                    icon
                }
            }

    private fun getOrCreateMarkerSet(): MarkerSet? =
            commonAPI?.markerAPI?.getMarkerSet(MOD_ID) ?: run {
                commonAPI?.markerAPI?.createMarkerSet(MOD_ID, "Vynmap Markers", null, true)
            }

    // Dont judge, idk why they dont just let me put in an AABB to be drawn with PolylineMarker
    private fun getArraysFromAABB(aabb: AABBdc): Triple<DoubleArray, DoubleArray, DoubleArray> {
        val x = arrayListOf<Double>()
        val y = arrayListOf<Double>()
        val z = arrayListOf<Double>()

        val path = listOf(
                Vector3d(aabb.minX(), aabb.minY(), aabb.minZ()), // 1
                Vector3d(aabb.minX(), aabb.maxY(), aabb.minZ()), // 1
                Vector3d(aabb.minX(), aabb.maxY(), aabb.maxZ()), // 1
                Vector3d(aabb.minX(), aabb.minY(), aabb.maxZ()), // 1
                Vector3d(aabb.minX(), aabb.minY(), aabb.minZ()), // 1
                Vector3d(aabb.minX(), aabb.maxY(), aabb.minZ()), // 2
                Vector3d(aabb.maxX(), aabb.maxY(), aabb.minZ()), // 2
                Vector3d(aabb.maxX(), aabb.minY(), aabb.minZ()), // 2
                Vector3d(aabb.minX(), aabb.minY(), aabb.minZ()), // 2
                Vector3d(aabb.maxX(), aabb.minY(), aabb.minZ()), // 2
                Vector3d(aabb.maxX(), aabb.minY(), aabb.maxZ()), // 3
                Vector3d(aabb.maxX(), aabb.maxY(), aabb.maxZ()), // 3
                Vector3d(aabb.maxX(), aabb.maxY(), aabb.minZ()), // 3
                Vector3d(aabb.maxX(), aabb.maxY(), aabb.maxZ()), // 3
                Vector3d(aabb.minX(), aabb.maxY(), aabb.maxZ()), // 4
                Vector3d(aabb.minX(), aabb.minY(), aabb.maxZ()), // 4
                Vector3d(aabb.maxX(), aabb.minY(), aabb.maxZ()) // 4
        )

        path.forEach {
            x.add(it.x)
            y.add(it.y)
            z.add(it.z)
        }

        return Triple(
                x.toDoubleArray(),
                y.toDoubleArray(),
                z.toDoubleArray()
        )
    }

    private fun createShipLabel(ship: ServerShip, config: IVynmapConfig): String {
        var label = "<h1>${ship.slug}</h1>"

        if (config.showShipID()) label += "<p><strong>Ship ID: </strong>${ship.id}</p>"
        if (config.showShipMass()) label += "<p><strong>Ship Mass: </strong>${ship.inertiaData.mass}</p>"

        return label
    }
}