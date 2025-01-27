package com.spapalamprou.turtlesafe.domain.useCases

import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import javax.inject.Inject

/**
 * Use case class to retrieve nest codes based on location parameters.
 *
 * @property newNestRepository The repository responsible for communicating with the
 * server to retrieve nest codes.
 */
class GetNestCodeUseCase @Inject constructor(
    private val newNestRepository: NewNestRepository
) {

    /**
     * Retrieves a list of nest codes from the server based on the given location details.
     *
     * @param area The area where the nests are located.
     * @param beach The beach where the nests are located.
     * @param sector The sector of the beach.
     * @param subsector The subsector of the beach.
     * @return A list of strings representing the nest codes retrieved from the server.
     */
    suspend fun getNestCode(
        area: String,
        beach: String,
        sector: String?,
        subsector: String?
    ): List<String> {
        return newNestRepository.getFromServer(area, beach, sector, subsector)
    }
}