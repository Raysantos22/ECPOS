package com.example.possystembw


import android.util.Log
import com.example.possystembw.DAO.ARApi
import com.example.possystembw.DAO.ApiResponse
import com.example.possystembw.DAO.AttendanceApi
import com.example.possystembw.DAO.AttendanceService
import com.example.possystembw.DAO.CategoryApi
import com.example.possystembw.DAO.CustomerApi
import com.example.possystembw.DAO.DiscountApiService
import com.example.possystembw.DAO.LineDetailsApi
import com.example.possystembw.DAO.LoyaltyCardApi
import com.example.possystembw.DAO.MixMatchApi
import com.example.possystembw.DAO.NumberSequenceApi
import com.example.possystembw.DAO.ProductApi
import com.example.possystembw.DAO.StaffApi
import com.example.possystembw.DAO.StockCountingApi
import com.example.possystembw.DAO.StoreExpenseApi
import com.example.possystembw.DAO.TransactionApi
import com.example.possystembw.DAO.TransactionSyncApi
import com.example.possystembw.DAO.UserApi
import com.example.possystembw.DAO.WindowApiService
import com.example.possystembw.DAO.WindowTableApi
import com.example.possystembw.database.CartItem
import com.example.possystembw.database.Product
import com.example.possystembw.database.TransactionRecord
import com.example.possystembw.database.WindowTable
import com.example.possystembw.ui.RequestApiService
import com.example.possystembw.ui.ViewModel.CustomDateAdapter
import com.example.possystembw.ui.ViewModel.CustomDateDeserializer
import com.example.possystembw.ui.ViewModel.WindowTableListDeserializer
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object RetrofitClient {
    //    private const val BASE_URL = "http://173.252.167.180/"
//    private const val BASE_URL = "https://ecticketph.com/"
//        private const val BASE_URL = "http://173.252.167.180/"

//    private const val BASE_URL = "https://nodeserver.com.ecticketph.com/"

//    private const val BASE_URL = "https://ecposeljin.com.ecticketph.com/"

    //   local
//      private const val BASE_URL = "http://10.151.5.145:3000/"
//   private const val BASE_URL = "http://10.151.5.239:8000/"
//    private const val BASE_URL = "http://localhost:3000/"

//    private const val BASE_URL = "http://localhost:3000/"


//    private const val BASE_URL = "https://ecposmiddleware-aj1882pz3-progenxs-projects.vercel.app/"

    private const val BASE_URL = "https://mwaremiddleware.vercel.app/"
    private const val WORLD_TIME_API = "http://worldtimeapi.org/api/"

    private const val TAG = "RetrofitClient"

    private val gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, CustomDateAdapter())
        .registerTypeAdapter(
            object : TypeToken<List<WindowTable>>() {}.type,
            WindowTableListDeserializer()
        )
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


    // Add this to check if you can reach the server
    fun isServerReachable(): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress("10.151.5.145", 3000), 2000)
            socket.close()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Server unreachable: ${e.message}")
            false
        }
    }

    private val worldTimeRetrofit = Retrofit.Builder()
        .baseUrl(WORLD_TIME_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val attendanceApi: AttendanceApi by lazy {
        retrofit.create(AttendanceApi::class.java)
    }
    val requestApiService: RequestApiService by lazy {
        retrofit.create(RequestApiService::class.java)
    }
    val stockCountingApi: StockCountingApi by lazy {
        retrofit.create(StockCountingApi::class.java)
    }

    val lineDetailsApi: LineDetailsApi by lazy {
        retrofit.create(LineDetailsApi::class.java)
    }

    val attendanceService: AttendanceService by lazy {
        AttendanceService() // Remove the attendanceApi parameter
    }
    val staffApi: StaffApi by lazy {
        retrofit.create(StaffApi::class.java)
    }

    val storeExpenseApi: StoreExpenseApi by lazy {
        retrofit.create(StoreExpenseApi::class.java)
    }
    val loyaltyCardApi: LoyaltyCardApi by lazy {
        retrofit.create(LoyaltyCardApi::class.java)
    }
    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val arApi: ARApi by lazy {
        retrofit.create(ARApi::class.java)
    }
    val numberSequenceApi: NumberSequenceApi by lazy {
        retrofit.create(NumberSequenceApi::class.java)

    }
    val transactionSyncApi: TransactionSyncApi by lazy {
        retrofit.create(TransactionSyncApi::class.java)
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

    //    val transactionApi: TransactionApi by lazy {
//        val gson = GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            .serializeNulls()
//            .create()
//
//        val loggingInterceptor = HttpLoggingInterceptor { message ->
//            Log.d(TAG, message)
//        }.apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(loggingInterceptor)
//            .addInterceptor { chain ->
//                val original = chain.request()
//                val requestBody = original.body
//
//                // Ensure we're not sending an empty body
//                if (requestBody == null || requestBody.contentLength() == 0L) {
//                    Log.e(TAG, "Empty request body detected!")
//                }
//
//                val request = original.newBuilder()
//                    .header("Content-Type", "application/json")
//                    .method(original.method, requestBody)
//                    .build()
//
//                // Log the complete request for debugging
//                Log.d(TAG, "Request URL: ${request.url}")
//                Log.d(TAG, "Request Headers: ${request.headers}")
//                Log.d(TAG, "Request Body: ${requestBody?.toString()}")
//
//                chain.proceed(request)
//            }
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(TransactionApi::class.java)
//    }
//
//}
    val transactionApi: TransactionApi by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, CustomDateDeserializer()) // Add this line
            .setDateFormat("yyyy-MM-dd HH:mm:ss")  // Keep simple format for serialization
            .setPrettyPrinting()
            .serializeNulls()
            .create()

        // ... rest of your existing code stays the same

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

                if (requestBody == null || requestBody.contentLength() == 0L) {
                    Log.e(TAG, "Empty request body detected!")
                }

                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method, requestBody)
                    .build()

                Log.d(TAG, "Request URL: ${request.url}")
                Log.d(TAG, "Request Headers: ${request.headers}")

                val response = chain.proceed(request)

                if (!response.isSuccessful && response.code == 500) {
                    val errorBody = response.body?.string()
                    Log.e(TAG, "API 500 ERROR: $errorBody")
                    return@addInterceptor response.newBuilder()
                        .body(ResponseBody.create(response.body?.contentType(), errorBody ?: ""))
                        .build()
                }

                response
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
