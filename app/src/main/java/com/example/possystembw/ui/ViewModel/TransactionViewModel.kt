package com.example.possystembw.ui.ViewModel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.possystembw.DAO.DateUtils
import com.example.possystembw.DAO.TransactionRecordRequest
import com.example.possystembw.DAO.TransactionSummaryRequest
import com.example.possystembw.DAO.TransactionSyncRequest
import com.example.possystembw.DAO.TransactionSyncResponse
import com.example.possystembw.DAO.ZReportUpdateResponse
import com.example.possystembw.RetrofitClient
import com.example.possystembw.data.AppDatabase
import com.example.possystembw.data.NumberSequenceRemoteRepository
import com.example.possystembw.data.NumberSequenceRepository
import com.example.possystembw.data.TransactionRepository
import com.example.possystembw.database.TransactionRecord
import com.example.possystembw.database.TransactionSummary
import com.example.possystembw.ui.SessionManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TransactionViewModel(
    val repository: TransactionRepository,
    private val numberSequenceRemoteRepository: NumberSequenceRemoteRepository // Changed from NumberSequenceRepository
) : ViewModel() {
    private val _transactions = MutableLiveData<List<TransactionSummary>>()
    val transactions: LiveData<List<TransactionSummary>> = _transactions
    private val _syncStatus = MutableLiveData<Result<TransactionSyncResponse>>()
    val syncStatus: LiveData<Result<TransactionSyncResponse>> = _syncStatus

    init {
        repository.startPeriodicSync(viewModelScope)
    }

    private val _transactionItems = MutableLiveData<List<TransactionRecord>>()
    val transactionItems: LiveData<List<TransactionRecord>> = _transactionItems

    private val _zReportUpdateStatus = MutableLiveData<Result<ZReportUpdateResponse>>()
    val zReportUpdateStatus: LiveData<Result<ZReportUpdateResponse>> = _zReportUpdateStatus

    suspend fun updateTransactionsZReport(
        storeId: String,
        zReportId: String
    ): Result<ZReportUpdateResponse> {
        return try {
            val result = repository.updateTransactionsZReport(storeId, zReportId)
            _zReportUpdateStatus.postValue(result)
            result
        } catch (e: Exception) {
            val failure = Result.failure<ZReportUpdateResponse>(e)
            _zReportUpdateStatus.postValue(failure)
            failure
        }
    }

    suspend fun generateTransactionId(storeId: String): String {
        return repository.generateTransactionId(storeId)
    }

    //    fun loadTransactions() {
//        viewModelScope.launch {
//            _transactions.value = repository.getTransactions()
//        }
//    }
//    fun loadTransactions() {
//        viewModelScope.launch {
//            // Fetch transactions and sort them in descending order based on transaction ID
//            _transactions.value = repository.getTransactions().sortedByDescending { it.transactionId }
//        }
//    }
// Add this to your TransactionViewModel.kt
    fun syncSingleTransaction(transactionId: String) {
        viewModelScope.launch {
            try {
                Log.d("SYNC_DEBUG", "Starting single transaction sync for $transactionId")

                val summary = repository.getTransactionSummary(transactionId)
                if (summary == null) {
                    Log.e("SYNC_DEBUG", "Transaction summary not found for ID: $transactionId")
                    _syncStatus.value = Result.failure(Exception("Transaction not found"))
                    return@launch
                }

                val records = repository.getTransactionRecords(transactionId)
                if (records.isEmpty()) {
                    Log.e("SYNC_DEBUG", "No records found for transaction ID: $transactionId")
                    _syncStatus.value = Result.failure(Exception("No records found"))
                    return@launch
                }

                // Log details for debugging
                Log.d(
                    "SYNC_DEBUG",
                    "Syncing transaction $transactionId with ${records.size} records"
                )
                Log.d("SYNC_DEBUG", "Summary: ${Gson().toJson(summary)}")
                Log.d("SYNC_DEBUG", "First record: ${Gson().toJson(records.firstOrNull())}")

                val result = repository.syncTransaction(summary, records)
                _syncStatus.value = result

                result.onSuccess {
                    Log.d("SYNC_DEBUG", "Successfully synced transaction $transactionId")
                }.onFailure { error ->
                    Log.e(
                        "SYNC_DEBUG",
                        "Failed to sync transaction $transactionId: ${error.message}"
                    )
                }
            } catch (e: Exception) {
                Log.e("SYNC_DEBUG", "Exception during single transaction sync: ${e.message}", e)
                _syncStatus.value = Result.failure(e)
            }
        }
    }

    fun loadTransactions() {
        viewModelScope.launch {
            try {
                val currentStoreId = SessionManager.getCurrentUser()?.storeid
                if (currentStoreId != null) {
                    val result = repository.getTransactionsByStore(currentStoreId)
                    _transactions.value = result
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadTransactionItems(transactionId: String) {
        viewModelScope.launch {
            try {
                val items = repository.getTransactionItems(transactionId)
                _transactionItems.postValue(items)
                Log.d(
                    "TransactionViewModel",
                    "Loaded ${items.size} items for transaction $transactionId"
                )
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error loading transaction items", e)
                _transactionItems.postValue(emptyList())
            }
        }
    }

    suspend fun getTransactionItems(transactionId: String): List<TransactionRecord> {
        return withContext(Dispatchers.IO) {
            repository.getTransactionItems(transactionId)
        }
    }

    suspend fun getTransactionsByDateRange(
        startDate: Date,
        endDate: Date
    ): List<TransactionSummary> {
        return withContext(Dispatchers.IO) {
            repository.getTransactionsByDateRange(startDate, endDate)
        }
    }

    fun clearTransactionItems() {
        _transactionItems.value = emptyList()
    }

    fun updateTransactionSummary(transaction: TransactionSummary) {
        viewModelScope.launch {
            try {
                repository.updateTransactionSummary(transaction)
                Log.d(
                    "TransactionViewModel",
                    "Updated transaction summary for ${transaction.transactionId}"
                )
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error updating transaction summary", e)
            }
        }
    }

    fun updateTransactionRecords(records: List<TransactionRecord>) {
        viewModelScope.launch {
            try {
                repository.updateTransactionRecords(records)
                Log.d("TransactionViewModel", "Updated ${records.size} transaction records")
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error updating transaction records", e)
            }
        }
    }


    suspend fun insertTransactionSummary(transaction: TransactionSummary) {
        withContext(Dispatchers.IO) {
            repository.insertTransactionSummary(transaction)
        }
    }

    suspend fun insertTransactionRecords(records: List<TransactionRecord>) {
        withContext(Dispatchers.IO) {
            repository.insertTransactionRecords(records)
        }
    }

    fun updateRefundReceiptId(transactionId: String, refundReceiptId: String) {
        viewModelScope.launch {
            try {
                repository.updateRefundReceiptId(transactionId, refundReceiptId)
                Log.d("TransactionViewModel", "Updated refund receipt ID for $transactionId")
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error updating refund receipt ID", e)
            }
        }
    }

    //    fun startAutoSync(context: Context) {
//        viewModelScope.launch {
//            while (true) {
//                try {
//                    // Remove network connectivity check to avoid blocking sync
//                    val currentStore = SessionManager.getCurrentUser()?.storeid ?: continue
//
//                    // Get ALL transactions, not just for the current store
//                    val allTransactions = repository.getTransactions()
//
//                    // Sync ALL transactions in a single batch
//                    allTransactions.forEach { transaction ->
//                        try {
//                            // Get all records for the transaction
//                            val records = repository.getTransactionRecords(transaction.transactionId)
//
//                            // Sync without extensive preprocessing
//                            val syncRequest = TransactionSyncRequest(
//                                transactionSummary = TransactionSummaryRequest(
//                                    transactionid = transaction.transactionId,
//                                    type = transaction.type,
//                                    receiptid = transaction.receiptId,
//                                    store = transaction.store,
//                                    storeKey = transaction.storeKey,
//                                    storeSequence = transaction.storeSequence,
//                                    staff = transaction.staff,
//                                    custaccount = transaction.customerAccount,
//                                    netamount = formatDecimal(transaction.netAmount),
//                                    costamount = formatDecimal(transaction.costAmount),
//                                    grossamount = formatDecimal(transaction.grossAmount),
//                                    partialpayment = formatDecimal(transaction.partialPayment),
//                                    transactionstatus = transaction.transactionStatus,
//                                    discamount = formatDecimal(transaction.discountAmount),
//                                    cashamount = formatDecimal(transaction.totalAmountPaid),
//                                    custdiscamount = formatDecimal(transaction.customerDiscountAmount),
//                                    totaldiscamount = formatDecimal(transaction.totalDiscountAmount),
//                                    numberofitems = transaction.numberOfItems.toString(),
//                                    currency = transaction.currency,
//                                    createddate = formatDate(transaction.createdDate),
//                                    priceoverride = transaction.priceOverride?.toInt(),
//                                    comment = transaction.comment,
//                                    taxinclinprice = formatDecimal(transaction.taxIncludedInPrice),
//                                    netamountnotincltax = formatDecimal(transaction.vatableSales),
//                                    window_number = transaction.windowNumber,
//                                    cash = formatDecimal(transaction.cash),
//                                    gcash = formatDecimal(transaction.gCash),
//                                    paymaya = formatDecimal(transaction.payMaya),
//                                    card = formatDecimal(transaction.card),
//                                    loyaltycard = formatDecimal(transaction.loyaltyCard),
//                                    charge = formatDecimal(transaction.charge),
//                                    foodpanda = formatDecimal(transaction.foodpanda),
//                                    grabfood = formatDecimal(transaction.grabfood),
//                                    representation = formatDecimal(transaction.representation)
//                                ),
//                                transactionRecords = records.map { record ->
//                                    TransactionRecordRequest(
//                                        transactionid = record.transactionId,
//                                        linenum = record.lineNum.toString(),
//                                        receiptid = record.receiptId ?: "",
//                                        itemid = record.itemId ?: "",
//                                        storeKey = record.storeKey,
//                                        storeSequence = record.storeSequence,
//                                        itemname = record.name ?: "",
//                                        itemgroup = record.itemGroup ?: "",
//                                        price = formatDecimal(record.price),
//                                        netprice = formatDecimal(record.netPrice),
//                                        qty = record.quantity.toString(),
//                                        discamount = formatDecimal(record.discountAmount),
//                                        costamount = formatDecimal(record.costAmount),
//                                        netamount = formatDecimal(record.netAmount),
//                                        grossamount = formatDecimal(record.grossAmount),
//                                        custaccount = record.customerAccount ?: "WALK-IN",
//                                        store = transaction.store,
//                                        priceoverride = record.priceOverride?.toInt() ?: 0,
//                                        paymentmethod = transaction.paymentMethod,
//                                        staff = record.staff ?: "Unknown Staff",
//                                        linedscamount = formatDecimal(record.lineDiscountAmount ?: 0.0),
//                                        linediscpct = formatDecimal(record.lineDiscountPercentage ?: 0.0),
//                                        custdiscamount = formatDecimal(record.customerDiscountAmount ?: 0.0),
//                                        unit = record.unit ?: "PCS",
//                                        unitqty = formatDecimal(record.unitQuantity ?: record.quantity.toDouble()),
//                                        unitprice = formatDecimal(record.unitPrice ?: record.price),
//                                        taxamount = formatDecimal(record.taxAmount),
//                                        createddate = formatDate(record.createdDate ?: Date()),
//                                        remarks = record.comment ?: "",
//                                        taxinclinprice = formatDecimal(record.taxIncludedInPrice),
//                                        description = record.description ?: "",
//                                        discofferid = record.discountOfferId?.takeIf { it.isNotBlank() } ?:  "",
//                                        inventbatchid = null,
//                                        inventbatchexpdate = null,
//                                        giftcard = null,
//                                        returntransactionid = null,
//                                        returnqty = null,
//                                        creditmemonumber = null,
//                                        returnlineid = null,
//                                        priceunit = null,
//                                        netamountnotincltax = null,
//                                        storetaxgroup = null,
//                                        currency = null,
//                                        taxexempt = null
//                                    )
//                                }
//                            )
//
//                            // Sync without extensive error handling
//                            val response = repository.syncTransaction(transaction, records)
//                            response.onSuccess {
//                                repository.transactionDao.updateSyncStatus(transaction.transactionId, true)
//                            }
//                        } catch (e: Exception) {
//                            Log.e("AutoSync", "Error syncing transaction ${transaction.transactionId}", e)
//                        }
//                    }
//
//                    // Reduced delay to make sync more frequent
//                    delay(2000)
//                } catch (e: Exception) {
//                    Log.e("AutoSync", "Error during sync cycle", e)
//                    delay(2000)
//                }
//            }
//        }
//    }
    fun startAutoSync(context: Context) {
        viewModelScope.launch {
            while (true) {
                try {
                    val currentStore = SessionManager.getCurrentUser()?.storeid ?: continue
                    val allTransactions = repository.getTransactions()

                    allTransactions.forEach { transaction ->
                        try {
                            val records =
                                repository.getTransactionRecords(transaction.transactionId)

                            val syncRequest = TransactionSyncRequest(
                                transactionSummary = TransactionSummaryRequest(
                                    transactionid = transaction.transactionId,
                                    type = transaction.type,
                                    receiptid = transaction.receiptId,
                                    store = transaction.store,
                                    storeKey = transaction.storeKey,
                                    storeSequence = transaction.storeSequence,
                                    staff = transaction.staff,
                                    custaccount = transaction.customerAccount,
                                    netamount = formatDecimal(transaction.netAmount),
                                    costamount = formatDecimal(transaction.costAmount),
                                    grossamount = formatDecimal(transaction.grossAmount),
                                    partialpayment = formatDecimal(transaction.partialPayment),
                                    transactionstatus = transaction.transactionStatus,
                                    discamount = formatDecimal(transaction.discountAmount),
                                    cashamount = formatDecimal(transaction.totalAmountPaid),
                                    custdiscamount = formatDecimal(transaction.customerDiscountAmount),
                                    totaldiscamount = formatDecimal(transaction.totalDiscountAmount),
                                    numberofitems = transaction.numberOfItems.toString(),
                                    currency = transaction.currency,
                                    // FIXED: Direct string assignment - no conversion needed
                                    createddate = transaction.createdDate,
                                    priceoverride = transaction.priceOverride?.toInt(),
                                    comment = transaction.comment,
                                    taxinclinprice = formatDecimal(transaction.taxIncludedInPrice),
                                    netamountnotincltax = formatDecimal(transaction.vatableSales),
                                    window_number = transaction.windowNumber,
                                    cash = formatDecimal(transaction.cash),
                                    gcash = formatDecimal(transaction.gCash),
                                    paymaya = formatDecimal(transaction.payMaya),
                                    card = formatDecimal(transaction.card),
                                    loyaltycard = formatDecimal(transaction.loyaltyCard),
                                    charge = formatDecimal(transaction.charge),
                                    foodpanda = formatDecimal(transaction.foodpanda),
                                    grabfood = formatDecimal(transaction.grabfood),
                                    representation = formatDecimal(transaction.representation)
                                ),
                                transactionRecords = records.map { record ->
                                    TransactionRecordRequest(
                                        transactionid = record.transactionId,
                                        linenum = record.lineNum.toString(),
                                        receiptid = record.receiptId ?: "",
                                        itemid = record.itemId ?: "",
                                        storeKey = record.storeKey,
                                        storeSequence = record.storeSequence,
                                        itemname = record.name ?: "",
                                        itemgroup = record.itemGroup ?: "",
                                        price = formatDecimal(record.price),
                                        netprice = formatDecimal(record.netPrice),
                                        qty = record.quantity.toString(),
                                        discamount = formatDecimal(record.discountAmount),
                                        costamount = formatDecimal(record.costAmount),
                                        netamount = formatDecimal(record.netAmount),
                                        grossamount = formatDecimal(record.grossAmount),
                                        custaccount = record.customerAccount ?: "WALK-IN",
                                        store = transaction.store,
                                        priceoverride = record.priceOverride?.toInt() ?: 0,
                                        paymentmethod = transaction.paymentMethod,
                                        staff = record.staff ?: "Unknown Staff",
                                        linedscamount = formatDecimal(
                                            record.lineDiscountAmount ?: 0.0
                                        ),
                                        linediscpct = formatDecimal(
                                            record.lineDiscountPercentage ?: 0.0
                                        ),
                                        custdiscamount = formatDecimal(
                                            record.customerDiscountAmount ?: 0.0
                                        ),
                                        unit = record.unit ?: "PCS",
                                        unitqty = formatDecimal(
                                            record.unitQuantity ?: record.quantity.toDouble()
                                        ),
                                        unitprice = formatDecimal(record.unitPrice ?: record.price),
                                        taxamount = formatDecimal(record.taxAmount),
                                        // FIXED: Direct string assignment - no conversion needed
                                        createddate = record.createdDate ?: getCurrentDateString(),
                                        remarks = record.comment ?: "",
                                        taxinclinprice = formatDecimal(record.taxIncludedInPrice),
                                        description = record.description ?: "",
                                        discofferid = record.discountOfferId?.takeIf { it.isNotBlank() }
                                            ?: "",
                                        inventbatchid = null,
                                        inventbatchexpdate = null,
                                        giftcard = null,
                                        returntransactionid = null,
                                        returnqty = null,
                                        creditmemonumber = null,
                                        returnlineid = null,
                                        priceunit = null,
                                        netamountnotincltax = null,
                                        storetaxgroup = null,
                                        currency = null,
                                        taxexempt = null
                                    )
                                }
                            )

                            val response = repository.syncTransaction(transaction, records)
                            response.onSuccess {
                                repository.transactionDao.updateSyncStatus(
                                    transaction.transactionId,
                                    true
                                )
                            }
                        } catch (e: Exception) {
                            Log.e(
                                "AutoSync",
                                "Error syncing transaction ${transaction.transactionId}",
                                e
                            )
                        }
                    }

                    delay(2000)
                } catch (e: Exception) {
                    Log.e("AutoSync", "Error during sync cycle", e)
                    delay(2000)
                }
            }
        }
    }

    // REMOVED: formatDateToString function - not needed anymore
    // ADDED: getCurrentDateString function for creating new transactions
    fun getCurrentDateString(): String {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            format.format(Date())
        } catch (e: Exception) {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
        }
    }


    fun String.toTimestamp(): Long {
        if (this.isEmpty()) return System.currentTimeMillis()

        return try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val date = format.parse(this)
            date?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
                    capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        } else {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.isConnected == true
        }
    }

    private fun formatDecimal(value: Double?): String {
        return try {
            String.format(Locale.US, "%.2f", value ?: 0.0)
        } catch (e: Exception) {
            "0.00"
        }
    }

    private fun formatDate(date: Date?): String {
        return DateUtils.formatToPhilippineTime(date)
    }


    fun syncTransaction(transactionId: String) {
        viewModelScope.launch {
            try {
                val summary = repository.getTransactionSummary(transactionId)
                val records = repository.getTransactionRecords(transactionId)
                val result = repository.syncTransaction(summary, records)

                result.onSuccess {
                    // Only mark as synced if truly successful
                    repository.transactionDao.updateSyncStatus(transactionId, true)
                }.onFailure { error ->
                    // If sync fails, mark as unsynced and log the error
                    repository.transactionDao.updateSyncStatus(transactionId, false)
                    Log.e(
                        "TransactionViewModel",
                        "Sync failed for transaction $transactionId",
                        error
                    )
                }

                _syncStatus.value = result
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error during sync", e)
                _syncStatus.value = Result.failure(e)

                // Ensure the transaction is marked as unsynced in case of any exception
                repository.transactionDao.updateSyncStatus(transactionId, false)
            }
        }
    }

    fun syncUnsentTransactions() {
        viewModelScope.launch {
            try {
                repository.syncUnsentTransactions()
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error syncing unsent transactions", e)
            }
        }
    }

    class TransactionViewModelFactory(
        private val repository: TransactionRepository,
        private val numberSequenceRemoteRepository: NumberSequenceRemoteRepository
    ) : ViewModelProvider.Factory {

        companion object {
            fun create(context: Context): TransactionViewModelFactory {
                val database = AppDatabase.getDatabase(context)
                val transactionDao = database.transactionDao()
                val numberSequenceRemoteDao = database.numberSequenceRemoteDao()

                val numberSequenceRemoteRepository = NumberSequenceRemoteRepository(
                    numberSequenceApi = RetrofitClient.numberSequenceApi,
                    numberSequenceRemoteDao = numberSequenceRemoteDao,
                    transactionDao = transactionDao  // Add this parameter
                )

                val transactionRepository = TransactionRepository(
                    transactionDao = transactionDao,
                    numberSequenceRemoteRepository = numberSequenceRemoteRepository
                )

                return TransactionViewModelFactory(
                    transactionRepository,
                    numberSequenceRemoteRepository
                )
            }
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TransactionViewModel(repository, numberSequenceRemoteRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}