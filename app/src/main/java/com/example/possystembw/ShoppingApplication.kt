package com.example.possystembw

import android.app.Application
import android.util.Log
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import com.example.possystembw.data.AppDatabase
import com.example.possystembw.data.CartRepository
import com.example.possystembw.data.ProductRepository
import com.example.possystembw.ui.SessionManager

class ShoppingApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val productApi by lazy { RetrofitClient.productApi }
    val categoryApi by lazy { RetrofitClient.categoryApi }

    val repository by lazy {
        ProductRepository(
            productDao = database.productDao(),
            categoryDao = database.categoryDao(),
            productApi = productApi,
            categoryApi = categoryApi,
            visibilityDao = database.productVisibilityDao(),
            application = this  // Pass the application context
        )
    }

    val cartRepository by lazy { CartRepository(database.cartDao()) }

    override fun onCreate() {
        super.onCreate()
        // Initialize SessionManager if not already done elsewhere
        SessionManager.init(this)

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e(TAG, "Uncaught exception in thread ${thread.name}", throwable)
        }
    }

    companion object {
        private const val TAG = "ShoppingApplication"
    }
}



//<?xml version="1.0" encoding="utf-8"?>
//<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//xmlns:app="http://schemas.android.com/apk/res-auto"
//xmlns:tools="http://schemas.android.com/tools"
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:background="#eff6ff"
//tools:context=".ui.Window1">
//
//<!-- DrawerLayout wrapped inside ConstraintLayout -->
//<androidx.drawerlayout.widget.DrawerLayout
//android:id="@+id/drawer_layout"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:background="#eff6ff"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<!-- Main Content -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:layout_width="match_parent"
//android:layout_height="match_parent">
//
//<!-- Header Section -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/headerLayout"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:background="@color/white"
//android:elevation="4dp"
//android:padding="8dp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<!-- Hamburger Menu Button -->
//<ImageButton
//android:id="@+id/hamburgerButton"
//android:layout_width="40dp"
//android:layout_height="40dp"
//android:background="?attr/selectableItemBackgroundBorderless"
//android:src="@drawable/baseline_menu_24"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent"
//app:tint="@color/navy" />
//
//<!-- Search Bar -->
//<androidx.cardview.widget.CardView
//android:id="@+id/searchCardView"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="8dp"
//android:layout_marginEnd="8dp"
//app:cardCornerRadius="8dp"
//app:cardElevation="2dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toEndOf="@+id/hamburgerButton"
//app:layout_constraintTop_toTopOf="parent">
//
//<androidx.appcompat.widget.SearchView
//android:id="@+id/searchView"
//android:layout_width="match_parent"
//android:layout_height="40dp"
//android:background="@color/white"
//app:closeIcon="@drawable/baseline_close_24"
//app:iconifiedByDefault="false"
//app:queryHint="Search products..."
//app:searchIcon="@drawable/baseline_search_24" />
//
//</androidx.cardview.widget.CardView>
//
//<!-- Store Name TextView (Hidden for mobile) -->
//<TextView
//android:id="@+id/storeNameTextView"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="Store: "
//android:textColor="@color/black"
//android:textSize="12sp"
//android:textStyle="bold"
//android:visibility="gone"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="parent" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//<!-- Search and Categories Section -->
//<LinearLayout
//android:id="@+id/searchCategoryLayout"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:background="@color/white"
//android:elevation="2dp"
//android:orientation="vertical"
//android:padding="8dp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/headerLayout">
//
//<!-- Categories RecyclerView -->
//<androidx.recyclerview.widget.RecyclerView
//android:id="@+id/categoryRecyclerView"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:scrollbars="horizontal" />
//
//<!-- Products Title TextView -->
//<TextView
//android:id="@+id/textView3"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="8dp"
//android:text="Products"
//android:textColor="@color/black"
//android:textSize="16sp"
//android:visibility="gone"
//android:textStyle="bold" />
//
//</LinearLayout>
//
//<!-- Main Content Area -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/mainContentLayout"
//android:layout_width="0dp"
//android:layout_height="0dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/searchCategoryLayout">
//
//<!-- Products RecyclerView -->
//<androidx.recyclerview.widget.RecyclerView
//android:id="@+id/recyclerview"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:padding="4dp"
//android:scrollbars="vertical"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent"
//tools:listitem="@layout/item_product" />
//
//<!-- Loading Progress Bar -->
//<ProgressBar
//android:id="@+id/loadingProgressBar"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:visibility="gone"
//app:layout_constraintBottom_toBottomOf="@id/recyclerview"
//app:layout_constraintEnd_toEndOf="@id/recyclerview"
//app:layout_constraintStart_toStartOf="@id/recyclerview"
//app:layout_constraintTop_toTopOf="@id/recyclerview" />
//
//<!-- Cart Toggle Button -->
//<com.google.android.material.floatingactionbutton.FloatingActionButton
//android:id="@+id/cartToggleButton"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginEnd="16dp"
//android:layout_marginBottom="16dp"
//android:src="@drawable/baseline_shopping_cart_24"
//app:backgroundTint="@color/navy"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:tint="@android:color/white" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//<!-- Barcode Scanner Overlay -->
//<FrameLayout
//android:id="@+id/barcodeScannerOverlay"
//android:layout_width="280dp"
//android:layout_height="280dp"
//android:elevation="10dp"
//android:visibility="gone"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<androidx.cardview.widget.CardView
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//app:cardCornerRadius="16dp"
//app:cardElevation="8dp">
//
//<androidx.constraintlayout.widget.ConstraintLayout
//android:layout_width="match_parent"
//android:layout_height="match_parent">
//
//<androidx.camera.view.PreviewView
//android:id="@+id/previewView"
//android:layout_width="match_parent"
//android:layout_height="match_parent" />
//
//<View
//android:layout_width="180dp"
//android:layout_height="180dp"
//android:background="@drawable/scanner_overlay"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent" />
//
//<ImageButton
//android:id="@+id/closeButton"
//android:layout_width="30dp"
//android:layout_height="30dp"
//android:layout_margin="8dp"
//android:background="@drawable/circular_button_background"
//android:padding="4dp"
//android:src="@android:drawable/ic_menu_close_clear_cancel"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="parent"
//app:tint="@android:color/white" />
//
//<TextView
//android:id="@+id/scannerStatus"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginBottom="16dp"
//android:text="Scanning..."
//android:textColor="@android:color/white"
//android:textSize="14sp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//</androidx.cardview.widget.CardView>
//
//</FrameLayout>
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//<!-- Navigation Drawer -->
//<com.google.android.material.navigation.NavigationView
//android:id="@+id/nav_view"
//android:layout_width="wrap_content"
//android:layout_height="match_parent"
//android:layout_gravity="start"
//android:fitsSystemWindows="true"
//app:headerLayout="@layout/nav_header_mobile"
//app:menu="@menu/drawer_menu_window1" />
//
//</androidx.drawerlayout.widget.DrawerLayout>
//
//<!-- Cart Bottom Sheet Content (Initially Hidden) -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/cartBottomSheet"
//android:layout_width="match_parent"
//android:layout_height="match_parent"
//android:layout_gravity="bottom"
//android:background="@drawable/bottom_sheet_background"
//android:elevation="8dp"
//android:visibility="gone"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<!-- Cart Header -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/cartHeaderLayout"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:background="@color/navy"
//android:padding="16dp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<TextView
//android:id="@+id/cartTitle"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:text="Shopping Cart"
//android:textColor="@android:color/white"
//android:textSize="18sp"
//android:textStyle="bold"
//android:visibility="gone"
//app:layout_constraintBottom_toTopOf="@+id/cartControlsLayout"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent" />
//
//<!-- Cart Controls Layout -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/cartControlsLayout"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginTop="12dp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/cartTitle">
//
//<!-- Staff Selector -->
//<androidx.cardview.widget.CardView
//android:id="@+id/staffSelectorContainer"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginEnd="8dp"
//android:clickable="true"
//android:focusable="true"
//app:cardBackgroundColor="#ffffff"
//app:cardCornerRadius="12dp"
//app:cardElevation="4dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toStartOf="@+id/printerIndicator"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent">
//
//<LinearLayout
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:background="?attr/selectableItemBackground"
//android:gravity="center_vertical"
//android:orientation="horizontal"
//android:padding="12dp">
//
//<ImageView
//android:id="@+id/staffIcon"
//android:layout_width="20dp"
//android:layout_height="20dp"
//android:src="@drawable/baseline_people_24"
//app:tint="@color/navy" />
//
//<TextView
//android:id="@+id/staffNameTextView"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="8dp"
//android:layout_weight="1"
//android:ellipsize="end"
//android:maxLines="1"
//android:text="Select Staff"
//android:textColor="@color/navy"
//android:textSize="14sp"
//android:textStyle="bold" />
//
//<ImageView
//android:id="@+id/dropdownIcon"
//android:layout_width="16dp"
//android:layout_height="16dp"
//android:layout_marginStart="4dp"
//android:src="@drawable/baseline_arrow_drop_down_24"
//app:tint="@color/navy" />
//
//</LinearLayout>
//</androidx.cardview.widget.CardView>
//
//<!-- Printer Indicator -->
//<ImageView
//android:id="@+id/printerIndicator"
//android:layout_width="24dp"
//android:layout_height="24dp"
//android:layout_marginEnd="8dp"
//android:src="@drawable/baseline_print_24"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toStartOf="@+id/insertButton"
//app:layout_constraintTop_toTopOf="parent"
//app:tint="@android:color/white" />
//
//<!-- Update Button -->
//<Button
//android:id="@+id/insertButton"
//android:layout_width="40dp"
//android:layout_height="40dp"
//android:background="@drawable/update_button_background"
//android:drawableStart="@drawable/baseline_system_update_alt_24"
//android:gravity="center"
//android:padding="4dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toStartOf="@+id/cartActionsLayout"
//app:layout_constraintTop_toTopOf="parent" />
//
//<!-- Cart Action Buttons -->
//<LinearLayout
//android:id="@+id/cartActionsLayout"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginEnd="8dp"
//android:orientation="horizontal"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toStartOf="@+id/closeCartButton"
//app:layout_constraintTop_toTopOf="parent">
//
//<!-- Barcode Scan Button -->
//<ImageButton
//android:id="@+id/barcodeScanButton"
//android:layout_width="36dp"
//android:layout_height="36dp"
//android:layout_marginEnd="8dp"
//android:src="@drawable/baseline_qr_code_scanner_24"
///>
//
//<!-- Clear Cart Button -->
//<ImageButton
//android:id="@+id/clearCartButton"
//android:layout_width="36dp"
//android:layout_height="36dp"
//android:background="@drawable/circular_button_background"
//android:src="@drawable/restart"
//app:tint="@android:color/white" />
//
//</LinearLayout>
//
//<!-- Close Cart Button -->
//<ImageButton
//android:id="@+id/closeCartButton"
//android:layout_width="36dp"
//android:layout_height="36dp"
//android:background="@drawable/circular_button_background"
//android:src="@drawable/baseline_close_24"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="parent"
//app:tint="@android:color/white" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//<!-- Cart Items RecyclerView -->
//<androidx.recyclerview.widget.RecyclerView
//android:id="@+id/recyclerviewcart"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:padding="8dp"
//app:layout_constraintBottom_toTopOf="@+id/cartFooterLayout"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/cartHeaderLayout"
//tools:listitem="@layout/cart_item_layout" />
//
//<!-- Cart Footer -->
//<androidx.constraintlayout.widget.ConstraintLayout
//android:id="@+id/cartFooterLayout"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:background="@color/white"
//android:elevation="4dp"
//android:padding="16dp"
//app:layout_constraintBottom_toBottomOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent">
//
//<!-- Comment Section -->
//<TextView
//android:id="@+id/commentView"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//android:textColor="@color/black"
//android:textSize="12sp"
//android:textStyle="italic"
//android:visibility="gone"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent" />
//
//<!-- Subtotal Row -->
//<TextView
//android:id="@+id/subtotalLabel"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="Subtotal"
//android:textColor="@color/black"
//android:textSize="14sp"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/commentView" />
//
//<TextView
//android:id="@+id/totalAmountTextView"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="₱0.00"
//android:textColor="@color/black"
//android:textSize="14sp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="@id/subtotalLabel" />
//
//<!-- Discount Row -->
//<TextView
//android:id="@+id/discountTextView"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="4dp"
//android:text="Discount"
//android:textColor="@color/black"
//android:textSize="14sp"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/subtotalLabel" />
//
//<TextView
//android:id="@+id/discountAmountText"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="₱0.00"
//android:textColor="@color/black"
//android:textSize="14sp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="@id/discountTextView" />
//
//<!-- VAT Amount Row -->
//<TextView
//android:id="@+id/vatLabel"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="4dp"
//android:text="VAT Amount"
//android:textColor="@color/black"
//android:textSize="14sp"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/discountTextView" />
//
//<TextView
//android:id="@+id/vatAmountText"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="₱0.00"
//android:textColor="@color/black"
//android:textSize="14sp"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="@id/vatLabel" />
//
//<!-- Partial Payment Row -->
//<TextView
//android:id="@+id/partialLabel"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="4dp"
//android:text="Partial Payment"
//android:textColor="@color/black"
//android:textSize="14sp"
//android:visibility="gone"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/vatLabel" />
//
//<TextView
//android:id="@+id/partialPaymentTextView"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="₱0.00"
//android:textColor="@color/black"
//android:textSize="14sp"
//android:visibility="gone"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="@id/partialLabel" />
//
//<!-- Total Row -->
//<TextView
//android:id="@+id/totalTextView"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:layout_marginTop="8dp"
//android:text="Total"
//android:textColor="@color/black"
//android:textSize="18sp"
//android:textStyle="bold"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/partialLabel" />
//
//<TextView
//android:id="@+id/finalTotalText"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:text="₱0.00"
//android:textColor="@color/black"
//android:textSize="18sp"
//android:textStyle="bold"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintTop_toTopOf="@id/totalTextView" />
//<ProgressBar
//android:id="@+id/cartLoadingIndicator"
//android:layout_width="wrap_content"
//android:layout_height="wrap_content"
//android:visibility="gone" />
//<!-- Action Buttons -->
//<LinearLayout
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginTop="16dp"
//android:orientation="horizontal"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@id/totalTextView">
//
//<!-- Options Button -->
//<Button
//android:id="@+id/optionsButton"
//android:layout_width="0dp"
//android:layout_height="48dp"
//android:layout_marginEnd="8dp"
//android:layout_weight="1"
//android:text="Options"
//android:textColor="@color/navy"
//android:textSize="14sp" />
//
//<!-- Pay Button -->
//<Button
//android:id="@+id/payButton"
//android:layout_width="0dp"
//android:layout_height="48dp"
//android:layout_weight="2"
//android:text="Pay Now"
//android:textColor="@android:color/white"
//android:textSize="16sp"
//android:textStyle="bold" />
//
//</LinearLayout>
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
//<!-- Hidden views for compatibility with existing code -->
//<View
//android:id="@+id/priceOverrideButton"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:visibility="gone" />
//
//<View
//android:id="@+id/discountButton"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:visibility="gone" />
//
//<View
//android:id="@+id/partialPaymentButton"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:visibility="gone" />
//
//<View
//android:id="@+id/voidPartialPaymentButton"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:visibility="gone" />
//
//<View
//android:id="@+id/Reportsbutton"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:visibility="gone" />
//
//<View
//android:id="@+id/viewTransactionButton"
//android:layout_width="0dp"
//android:layout_height="0dp"
//android:visibility="gone" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>