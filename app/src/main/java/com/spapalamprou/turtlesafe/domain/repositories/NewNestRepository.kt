package com.spapalamprou.turtlesafe.domain.repositories

import com.spapalamprou.turtlesafe.domain.models.NewNestModel

/**
 * Repository interface that handles new nest data.
 */
interface NewNestRepository {

    /**
     * Saves a new nest record to the local database and associates it with a morning survey.
     *
     * @param newNest The NewNestModel containing the nest data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest.
     * @return The ID of the inserted new nest record in the database.
     */
    suspend fun saveToDatabase(newNest: NewNestModel, morningSurveyId: Long): Long

    /**
     * Uploads a photo of the new nest to the server, if a photo URI exists in the NewNestModel.
     *
     * @param newNest The NewNestModel containing the new nest data.
     */
    suspend fun sendPhotoToServer(newNest: NewNestModel)

    /**
     * Retrieves a list of nest codes from the server based on area, beach, sector, and subsector.
     *
     * @param area The area where the nests are located.
     * @param beach The beach where the nests are located.
     * @param sector The optional sector where the nests are located.
     * @param subsector The subsector where the nests are located.
     * @return A list of nest codes matching the criteria provided.
     */
    suspend fun getFromServer(
        area: String,
        beach: String,
        sector: String?,
        subsector: String?
    ): List<String>
}