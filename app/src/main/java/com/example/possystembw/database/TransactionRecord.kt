package com.example.possystembw.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.Date

@Entity(tableName = "transactions")
data class TransactionRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "transaction_id") val transactionId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "subtotal") val subtotal: Double,
    @ColumnInfo(name = "vat_rate") val vatRate: Double,
    @ColumnInfo(name = "vat_amount") val vatAmount: Double,
    @ColumnInfo(name = "discount_rate") val discountRate: Double,
    @ColumnInfo(name = "discount_amount") val discountAmount: Double,
    @ColumnInfo(name = "total") val total: Double,
    @ColumnInfo(name = "receipt_number") val receiptNumber: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "payment_method") val paymentMethod: String,
    @ColumnInfo(name = "ar") val ar: Double,
    @ColumnInfo(name = "window_number") val windowNumber: Int = 1,
    @ColumnInfo(name = "partial_payment_amount") val partialPaymentAmount: Double = 0.0,
    @ColumnInfo(name = "comment") val comment: String = "",
    // Additional fields from the second table
    @ColumnInfo(name = "linenum") val lineNum: Int? = null,
    @ColumnInfo(name = "receipted") val receiptId: String? = null,
    @ColumnInfo(name = "itemid") val itemId: String? = null,
    @ColumnInfo(name = "itemgroup") val itemGroup: String? = null,
    @ColumnInfo(name = "netprice") val netPrice: Double? = null,
    @ColumnInfo(name = "costamount") val costAmount: Double? = null,
    @ColumnInfo(name = "netamount") val netAmount: Double? = null,
    @ColumnInfo(name = "grossamount") val grossAmount: Double? = null,
    @ColumnInfo(name = "custaccount") val customerAccount: String? = null,
    @ColumnInfo(name = "store") val store: String? = null,
    @ColumnInfo(name = "priceoverride") val priceOverride: Double? = null,
    @ColumnInfo(name = "staff") val staff: String? = null,
    @ColumnInfo(name = "discofferid") val discountOfferId: String? = null,
    @ColumnInfo(name = "linedscamount") val lineDiscountAmount: Double? = null,
    @ColumnInfo(name = "linediscpct") val lineDiscountPercentage: Double? = null,
    @ColumnInfo(name = "custdiscamount") val customerDiscountAmount: Double? = null,
    @ColumnInfo(name = "unit") val unit: String? = null,
    @ColumnInfo(name = "unitqty") val unitQuantity: Double? = null,
    @ColumnInfo(name = "unitprice") val unitPrice: Double? = null,
    @ColumnInfo(name = "taxamount") val taxAmount: Double? = null,
    @ColumnInfo(name = "createddate") val createdDate: String?, // String instead of Date
    @ColumnInfo(name = "remarks") val remarks: String? = null,
    @ColumnInfo(name = "inventbatchid") val inventoryBatchId: String? = null,
    @ColumnInfo(name = "inventbatchexpdate") val inventoryBatchExpiryDate: String? = null,
    @ColumnInfo(name = "giftcard") val giftCard: String? = null,
    @ColumnInfo(name = "returntransactionid") val returnTransactionId: String? = null,
    @ColumnInfo(name = "returnqty") val returnQuantity: Double? = null,
    @ColumnInfo(name = "creditmemonumber") val creditMemoNumber: String? = null,
    @ColumnInfo(name = "taxinclinprice") val taxIncludedInPrice: Double? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "returnlineid") val returnLineId: Double? = null,
    @ColumnInfo(name = "priceunit") val priceUnit: Double? = null,
    @ColumnInfo(name = "netamountnotincltax") val netAmountNotIncludingTax: Double? = null,
    @ColumnInfo(name = "storetaxgroup") val storeTaxGroup: String? = null,
    @ColumnInfo(name = "currency") val currency: String? = null,
    @ColumnInfo(name = "taxexempt") val taxExempt: Double? = null,
    @ColumnInfo(name = "isSelected") var isSelected: Boolean = false,
    @ColumnInfo(name = "isReturned") val isReturned: Boolean = false,
    @ColumnInfo(name = "discountType") val discountType: String = "", // Or nullable String?
    @ColumnInfo(name = "overriddenPrice") val overriddenPrice: Double? = null,
    @ColumnInfo(name = "originalPrice") val originalPrice: Double? = null,
    @ColumnInfo(name = "store_key") var storeKey: String, // Added for unique store identification
    @ColumnInfo(name = "store_sequence") var storeSequence: String, // Added for store-specific sequencing
    @ColumnInfo(name = "syncstatusrecord") var syncStatusRecord: Boolean = false
)
