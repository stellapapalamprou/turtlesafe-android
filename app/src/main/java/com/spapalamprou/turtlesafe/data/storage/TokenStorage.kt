package com.spapalamprou.turtlesafe.data.storage

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * An interface for managing access and refresh tokens.
 *
 * This interface provides methods to get, set, and delete access and refresh tokens.
 */
interface TokenStorage {
    /**
     * Retrieves the access token.
     *
     * @return The access token as a String, or null if it does not exist.
     */
    fun getAccessToken(): String?

    /**
     * Stores the access token.
     *
     * @param token The access token to be stored.
     */
    fun setAccessToken(token: String)

    /**
     * Retrieves the refresh token.
     *
     * @return The refresh token as a String, or null if it does not exist.
     */
    fun getRefreshToken(): String?

    /**
     * Stores the refresh token.
     *
     * @param token The refresh token to be stored.
     */
    fun setRefreshToken(token: String)

    /**
     * Deletes all stored tokens.
     */
    fun deleteAll()
}

/**
 * An implementation of the TokenStorage interface.
 *
 * @param context The context used to access SharedPreferences.
 */
class TokenStorageImp @Inject constructor(
    context: Context
) : TokenStorage {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("tokenStorage", Context.MODE_PRIVATE)

    override fun getAccessToken(): String? {
        return preferences.getString("accessToken", null)
    }

    override fun setAccessToken(token: String) {
        preferences.edit().putString("accessToken", token).commit()
    }

    override fun getRefreshToken(): String? {
        return preferences.getString("refreshToken", null)
    }

    override fun setRefreshToken(token: String) {
        preferences.edit().putString("refreshToken", token).commit()
    }

    override fun deleteAll() {
        preferences.edit().clear().commit()
    }
}