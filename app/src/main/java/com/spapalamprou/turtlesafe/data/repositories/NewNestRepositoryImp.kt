package com.spapalamprou.turtlesafe.data.repositories

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.spapalamprou.turtlesafe.data.api.NewNestApi
import com.spapalamprou.turtlesafe.data.database.dao.NewNestDao
import com.spapalamprou.turtlesafe.data.database.entities.asEntity
import com.spapalamprou.turtlesafe.domain.models.NewNestModel
import com.spapalamprou.turtlesafe.domain.repositories.NewNestRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of NewNestRepository that handles the management of new nest data.
 *
 * @param context The Context used for accessing resources and managing file operations.
 * @param newNestDao Data access object for performing database operations on local database.
 * @param newNestApi API service used to interact with the server for nest-related data and photo uploads.
 */
class NewNestRepositoryImp @Inject constructor(
    private val context: Context,
    private val newNestDao: NewNestDao,
    private val newNestApi: NewNestApi
) : NewNestRepository {

    /**
     * Saves a new nest record to the local database and associates it with a morning survey.
     *
     * @param newNest The NewNestModel containing the nest data to be saved.
     * @param morningSurveyId The ID of the morning survey associated with the nest.
     * @return The ID of the inserted new nest record in the database.
     */
    override suspend fun saveToDatabase(newNest: NewNestModel, morningSurveyId: Long): Long {
        return newNestDao.insert(newNest.asEntity(morningSurveyId))
    }

    /**
     * Uploads a photo of the new nest to the server, if a photo URI exists in the NewNestModel.
     *
     * @param newNest The NewNestModel containing the new nest data.
     */
    override suspend fun sendPhotoToServer(newNest: NewNestModel) {

        if (newNest.photoUri != null) {
            val uri = Uri.parse(newNest.photoUri)
            val photo = fileFromContentUri(
                context = context,
                contentUri = uri
            )
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), photo)

            newNestApi.uploadPhoto(
                photo = MultipartBody.Part.createFormData("photo", photo.name, requestBody),
                newNestId = RequestBody.create(
                    "text/plain".toMediaTypeOrNull(),
                    newNest.nestCode
                )
            )
        }
    }

    /**
     * Retrieves a list of nest codes from the server based on area, beach, sector, and subsector.
     *
     * @param area The area where the nests are located.
     * @param beach The beach where the nests are located.
     * @param sector The optional sector where the nests are located.
     * @param subsector The subsector where the nests are located.
     * @return A list of nest codes matching the criteria provided.
     */
    override suspend fun getFromServer(
        area: String,
        beach: String,
        sector: String?,
        subsector: String?
    ): List<String> {
        val response = newNestApi.getNestData(area, beach, sector, subsector)
        return response.map { item ->
            item.nestCode
        }
    }

    /**
     * Creates a temporary file from the content URI provided.
     *
     * @param context The Context used.
     * @param contentUri The URI representing the photo's content URI.
     * @return A File object representing the temporary file created from the content URI.
     */
    private fun fileFromContentUri(context: Context, contentUri: Uri): File {
        val randomNumber = Random.nextInt(10000000)
        val fileExtension = getFileExtension(context, contentUri)
        val fileName =
            "temporary_file_${randomNumber}" + if (fileExtension != null) ".$fileExtension" else ""
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            if (inputStream != null) {
                copy(inputStream, oStream)
            }
            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return tempFile
    }

    /**
     * Retrieves the file extension from the content URI, if available.
     *
     * @param context The Context used.
     * @param uri The Uri representing the file's content URI.
     * @return A String representing the file extension.
     */
    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    /**
     * Copies data from an input stream to an output stream, used during file operations.
     *
     * @param source The InputStream representing the source data.
     * @param target The OutputStream where the data will be written.
     * @throws IOException If an I/O error occurs during the copy process.
     */
    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buffer = ByteArray(8192)
        var length: Int
        while (source.read(buffer).also { length = it } > 0) {
            target.write(buffer, 0, length)
        }
    }
}