package com.spapalamprou.turtlesafe.data.api

import com.spapalamprou.turtlesafe.domain.models.NewNestModel
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

/**
 * Interface representing the API for retrieving new nest data and uploads nest-related photos to the server.
 */
interface NewNestApi {

    /**
     * Retrieves nest data based on the specified area, beach, sector, and subsector.
     *
     * @param area The area in which the nests are located.
     * @param beach The beach where the nests are found.
     * @param sector The sector of the beach, or null if empty.
     * @param subsector The subsector of the beach, or null if empty.
     * @return A list of GetNewNestJsonResponse containing the nest codes.
     */
    @GET("api/nests")
    suspend fun getNestData(
        @Query("area") area: String,
        @Query("beach") beach: String,
        @Query("sector") sector: String?,
        @Query("subsector") subsector: String?
    ): List<GetNewNestJsonResponse>

    /**
     * Uploads a photo associated with a new nest to the server.
     *
     * @param photo The photo to be uploaded.
     * @param newNestId The id of the new nest to which the photo is associated.
     */
    @Multipart
    @POST("api/upload/photos")
    suspend fun uploadPhoto(
        @Part photo: MultipartBody.Part,
        @Part("new_nest_id") newNestId: RequestBody
    )
}

/**
 * Data class representing the details of a new nest.
 *
 * @property layingDate The date when the nest was laid.
 * @property area The area in which the nest is located.
 * @property beach The beach where the nest is located.
 * @property sector The sector of the beach where the nest is located.
 * @property subsector The subsector of the beach where the nest is located.
 * @property nestCode The unique code assigned to the nest.
 * @property trackType The type of track observed near the nest.
 * @property gpsLatitude The GPS latitude of the nest's location.
 * @property gpsLongtitude The GPS longtitude of the nest's location.
 * @property protectedNestSwitch Boolean indicating if the nest is protected.
 * @property protectionMeasures The measures taken to protect the nest.
 * @property turtleTags Any tags attached to the turtle.
 * @property emergenceOrEvent Information on the emergence or event related to the nest.
 * @property depthTopEgg The depth (in meters) from the surface to the top egg.
 * @property distanceToSea The distance (in meters) from the nest to the sea.
 * @property commentsOrRemarks Any comments or remarks about the nest.
 */
data class NewNestJson(
    @SerializedName("laying_date") val layingDate: String,
    @SerializedName("area") val area: String,
    @SerializedName("beach") val beach: String,
    @SerializedName("sector") val sector: String,
    @SerializedName("subsector") val subsector: String,
    @SerializedName("nest_code") val nestCode: String,
    @SerializedName("track_type") val trackType: String,
    @SerializedName("gps_latitude") val gpsLatitude: Double,
    @SerializedName("gps_longtitude") val gpsLongtitude: Double,
    @SerializedName("protected_nest_switch") val protectedNestSwitch: Boolean,
    @SerializedName("protection_measures") val protectionMeasures: String,
    @SerializedName("turtle_tags") val turtleTags: String,
    @SerializedName("emergence_or_event") val emergenceOrEvent: String,
    @SerializedName("depth_top_egg") val depthTopEgg: Double,
    @SerializedName("distance_to_sea") val distanceToSea: Double,
    @SerializedName("comments_or_remarks") val commentsOrRemarks: String
)

/**
 * Extension function to serialize a NewNestModel to a NewNestJson format.
 *
 * @return The NewNestJson representation of the NewNestModel.
 */
fun NewNestModel.asJson(): NewNestJson {
    return NewNestJson(
        layingDate = layingDate.changeDateFormat(),
        area = area,
        beach = beach,
        sector = sector,
        subsector = subsector,
        nestCode = nestCode,
        trackType = trackType,
        gpsLatitude = gpsLatitude,
        gpsLongtitude = gpsLongtitude,
        protectedNestSwitch = protectedNestSwitch,
        protectionMeasures = protectionMeasures,
        turtleTags = turtleTags,
        depthTopEgg = depthTopEgg,
        distanceToSea = distanceToSea,
        emergenceOrEvent = emergenceOrEvent,
        commentsOrRemarks = commentsOrRemarks
    )
}

/**
 * Data class representing the server's response containing the nest code.
 *
 * @property nestCode The unique code assigned to a new nest.
 */
data class GetNewNestJsonResponse(
    @SerializedName("nest_code") val nestCode: String
)