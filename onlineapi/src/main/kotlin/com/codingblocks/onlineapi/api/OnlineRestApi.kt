package com.codingblocks.onlineapi.api

import com.codingblocks.onlineapi.models.DoubtStats
import com.codingblocks.onlineapi.models.Extension
import com.codingblocks.onlineapi.models.Leaderboard
import com.codingblocks.onlineapi.models.PerformanceResponse
import com.codingblocks.onlineapi.models.RankResponse
import com.codingblocks.onlineapi.models.RatingModel
import com.codingblocks.onlineapi.models.ResetRunAttempt
import com.codingblocks.onlineapi.models.SpinResponse
import com.codingblocks.onlineapi.models.Feedback
import com.codingblocks.onlineapi.models.SendFeedback
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OnlineRestApi {
    @Deprecated("Progress is part of run")
    @GET("v2/run_attempts/{runid}/progress")
    fun getMyCourseProgress(@Path("runid") id: String): Call<HashMap<Any, Any>>

    @GET("v2/lectures/otp")
    suspend fun getOtp(
        @Query("videoId") videoId: String,
        @Query("sectionId") sectionId: String,
        @Query("runAttemptId") runAttemptId: String,
        @Query("offline") offline: Boolean = false
    ): Response<JsonObject>

    @POST("jwt/login?android=true")
    @FormUrlEncoded
    suspend fun getToken(@Field("code") code: String): Response<JsonObject>

    @GET("v2/progresses/stats/{id}")
    suspend fun getMyStats(
        @Path("id") id: String
    ): Response<PerformanceResponse>

    @GET("v2/courses/{id}/rating")
    suspend fun getCourseRating(@Path("id") id: String): RatingModel

    @GET("v2/runs/{runId}/enroll")
    suspend fun enrollTrial(@Path("runId") id: String): Response<JsonObject>

    @GET("v2/runs/{runId}/buy")
    suspend fun addToCart(@Path("runId") id: String): Response<JsonObject>

    @GET("v2/runs/{runid}/leaderboard")
    suspend fun leaderboardById(
        @Path("runid") id: String
    ): Response<List<Leaderboard>>

    @GET("v2/users/myReferral")
    suspend fun myReferral(): Response<JsonObject>

    @GET("v2/runs/cart")
    suspend fun getCart(): Response<JsonObject>

    @PATCH("v2/runs/purchase")
    suspend fun updateCart(@Body body: HashMap<String, Any>): Response<JsonObject>

    @GET("v2/runs/clear_cart")
    suspend fun clearCart(): Response<JsonObject>

    @POST("jwt/refresh/?android=true")
    @FormUrlEncoded
    suspend fun refreshToken(@Field("refresh_token") refresh_token: String): Response<JsonObject>

    @POST("v2/progresses/reset")
    suspend fun resetProgress(@Body runAttemptId: ResetRunAttempt): Response<ResponseBody>

    @GET("v2/run_attempts/{runAttemptId}/requestApproval")
    suspend fun requestApproval(@Path("runAttemptId") id: String): Response<ResponseBody>

    @GET("v2/runs/products/{id}")
    fun getExtensions(@Path("id") productId: Int): Call<Extension>

    @POST("v2/runs/extensions/{id}/buy")
    fun buyExtension(@Path("id") extensionId: Int): Call<JsonObject>

    @GET("v2/doubts/stats/{id}")
    suspend fun doubtStats(@Path("id") userId: String): Response<DoubtStats>

    @GET("v2/users/me/chatSignature")
    fun getSignature(): Call<JsonObject>

    @POST("v2/chats/{id}")
    suspend fun getChatId(@Path("id") doubtId: String): Response<JsonObject>

    @POST("jwt/otp/v2")
    @FormUrlEncoded
    suspend fun getOtp(@FieldMap params: HashMap<String, String>): Response<JsonObject>

    @POST("users/find")
    @FormUrlEncoded
    suspend fun findUser(@FieldMap params: HashMap<String, String>): Response<JsonArray>

    @POST("jwt/otp/v2/{id}/verify")
    suspend fun verifyOtp(@Path("id") uniqueId: String, @Body params: HashMap<String, Any>): Response<JsonObject>

    @PATCH("users/verifymobile")
    suspend fun verifyMobile(@Body params: Map<String, String>): Response<JsonObject>

    @POST("users")
    suspend fun createUser(@Body params: Map<String, String>): Response<JsonObject>

    @PATCH("users/{id}")
    @FormUrlEncoded
    suspend fun updateUser(
        @Path("id") id: String,
        @FieldMap params: Map<String, String>
    ): Response<JsonObject>

    @POST("jwt/login")
    @FormUrlEncoded
    suspend fun getJwtWithEmail(@FieldMap params: Map<String, String>): Response<JsonObject>

    @POST("jwt/otp/v2/{id}/login")
    @FormUrlEncoded
    suspend fun getJwtWithClaim(
        @Path("id") uniqueId: String,
        @FieldMap params: Map<String, String> = hashMapOf("client" to "android")
    ): Response<JsonObject>

    @POST("v2/run_attempts/purchase")
    @FormUrlEncoded
    suspend fun capturePayment(@FieldMap params: Map<String, String>): Response<JsonObject>

    @POST("v2/hubspot/lead")
    suspend fun generateLead(@Body body: HashMap<String, Any>): Response<JsonObject>

    @GET("v2/hb/performance")
    suspend fun getHackerBlocksPerformance(): Response<RankResponse>

    @GET("v2/spins/stats")
    suspend fun spinStats(): Response<JsonObject>

    @POST("v2/spins/draw")
    suspend fun drawSpin(): Response<SpinResponse>

    @POST("v2/runs/addOrder")
    suspend fun addOrder(): Response<JsonObject>

    @POST("v2/courses/{id}/rating")
    suspend fun sendFeedback(
        @Path("id") id: String,
        @Body json: SendFeedback
    ): Response<Feedback>

    @GET("v2/courses/{id}/rating")
    suspend fun getFeedback(
        @Path("id") id: String
    ): Response<Feedback>
}
