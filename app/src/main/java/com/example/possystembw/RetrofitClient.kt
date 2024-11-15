package com.example.possystembw

import android.util.Log
import com.example.possystembw.DAO.ARApi
import com.example.possystembw.DAO.ApiResponse
import com.example.possystembw.DAO.CategoryApi
import com.example.possystembw.DAO.CustomerApi
import com.example.possystembw.DAO.DiscountApiService
import com.example.possystembw.DAO.MixMatchApi
import com.example.possystembw.DAO.ProductApi
import com.example.possystembw.DAO.TransactionApi
import com.example.possystembw.DAO.UserApi
import com.example.possystembw.DAO.WindowApiService
import com.example.possystembw.DAO.WindowTableApi
import com.example.possystembw.database.CartItem
import com.example.possystembw.database.Product
import com.example.possystembw.database.TransactionRecord
import com.example.possystembw.ui.ViewModel.CustomDateAdapter
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://10.151.5.145:3000/"
    private const val TAG = "RetrofitClient"

    private val gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, CustomDateAdapter())
        .setLenient()
        .create()

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    }

    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val arApi: ARApi by lazy {
        retrofit.create(ARApi::class.java)
    }


    val customerApi: CustomerApi by lazy {
        retrofit.create(CustomerApi::class.java)
    }

    /* val transactionApi: TransactionApi by lazy {
        retrofit.create(TransactionApi::class.java)
    }*/
    val categoryApi: CategoryApi by lazy {
        retrofit.create(CategoryApi::class.java)
    }
    val mixMatchApi: MixMatchApi by lazy {
        retrofit.create(MixMatchApi::class.java)
    }

    // Customized Product API with additional logging
    val instance: ProductApi by lazy {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            Log.i(TAG, "Retrofit instance created successfully")
            val api = retrofit.create(ProductApi::class.java)

            object : ProductApi by api {
                override suspend fun uploadProducts(products: List<Product>): Response<ApiResponse> {
                    Log.d(TAG, "Uploading ${products.size} products")
                    Log.d(TAG, "Products: $products")
                    return api.uploadProducts(products)
                }

                override suspend fun uploadCartItems(cartItems: List<CartItem>): Response<ApiResponse> {
                    Log.d(TAG, "Uploading ${cartItems.size} cart items")
                    Log.d(TAG, "Cart items: $cartItems")
                    return api.uploadCartItems(cartItems)
                }

                override suspend fun uploadTransactions(transactions: List<TransactionRecord>): Response<ApiResponse> {
                    Log.d(TAG, "Uploading ${transactions.size} transactions")
                    Log.d(TAG, "Transactions: $transactions")
                    return api.uploadTransactions(transactions)
                }

                override suspend fun getCartItems(): Response<List<CartItem>> {
                    Log.d(TAG, "Fetching cart items")
                    return api.getCartItems()
                }

                override suspend fun getTransactions(): Response<List<TransactionRecord>> {
                    Log.d(TAG, "Fetching transactions")
                    return api.getTransactions()
                }


                override suspend fun insertProduct(product: Product): Response<ApiResponse> {
                    Log.d(TAG, "Inserting new product: $product")
                    return api.insertProduct(product)
                }

                override suspend fun insertProductFromApi(): Response<ApiResponse> {
                    Log.d(TAG, "Inserting new product from API")
                    return api.insertProductFromApi()
                }

                override suspend fun getProducts(): Response<List<Product>> {
                    Log.d(TAG, "Fetching products")
                    return api.getProducts()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error creating Retrofit instance: ${e.message}")
            throw e
        }
    }

    // Discount API instance
    val discountApiService: DiscountApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(DiscountApiService::class.java)
    }

    // WindowTable API instance
    val windowTableApi: WindowTableApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WindowTableApi::class.java)
    }

    // Window API instance
    val apiService: WindowApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WindowApiService::class.java)
    }

    // User API instance
    val userApi: UserApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserApi::class.java)
    }
    val transactionApi: TransactionApi by lazy {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create()

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBody = original.body

                // Ensure we're not sending an empty body
                if (requestBody == null || requestBody.contentLength() == 0L) {
                    Log.e(TAG, "Empty request body detected!")
                }

                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method, requestBody)
                    .build()

                // Log the complete request for debugging
                Log.d(TAG, "Request URL: ${request.url}")
                Log.d(TAG, "Request Headers: ${request.headers}")
                Log.d(TAG, "Request Body: ${requestBody?.toString()}")

                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TransactionApi::class.java)
    }

}