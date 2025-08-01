package com.example.possystembw.ui

import android.view.animation.AnimationUtils
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.possystembw.R
import com.example.possystembw.adapter.ProductAdapter
import com.example.possystembw.adapter.CartAdapter
import com.example.possystembw.ui.ViewModel.ProductViewModel
import com.example.possystembw.ui.ViewModel.CartViewModel
import com.example.possystembw.ui.ViewModel.CartViewModelFactory
import com.example.possystembw.database.Product
import com.example.possystembw.database.CartItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.possystembw.AutoDatabaseTransferManager
import com.example.possystembw.DAO.TransactionDao
import com.example.possystembw.data.AppDatabase
import com.example.possystembw.data.CashFundRepository
import com.example.possystembw.database.Cashfund
import com.example.possystembw.database.TransactionRecord
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.possystembw.RetrofitClient
import com.example.possystembw.adapter.CategoryAdapter
import com.example.possystembw.adapter.DiscountAdapter
import com.example.possystembw.adapter.PriceOverrideAdapter
import com.example.possystembw.adapter.TransactionAdapter
import com.example.possystembw.data.CartRepository
import com.example.possystembw.data.CategoryRepository
import com.example.possystembw.data.DiscountRepository
import com.example.possystembw.data.TransactionRepository
import com.example.possystembw.database.Category
import com.example.possystembw.database.Discount
import com.example.possystembw.database.TransactionSummary
import com.example.possystembw.databinding.ActivityWindow1Binding
import com.example.possystembw.ui.ViewModel.BluetoothPrinterHelper
import com.example.possystembw.ui.ViewModel.CategoryViewModel
import com.example.possystembw.ui.ViewModel.CategoryViewModelFactory
import com.example.possystembw.ui.ViewModel.DiscountViewModel
import com.example.possystembw.ui.ViewModel.DiscountViewModelFactory
import com.example.possystembw.ui.ViewModel.TransactionViewModel
import com.example.possystembw.ui.ViewModel.WindowViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.first
import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.DatePickerDialog
import android.app.Dialog
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Switch
import androidx.appcompat.widget.SearchView // Change this import
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.possystembw.DAO.TenderDeclarationDao
import com.example.possystembw.DAO.ZReadDao
import com.example.possystembw.adapter.LineGroupAdapter
import com.example.possystembw.adapter.MixMatchAdapter
import com.example.possystembw.adapter.TransactionItemsAdapter
import com.example.possystembw.data.ARRepository
import com.example.possystembw.data.CustomerRepository
import com.example.possystembw.data.MixMatchRepository
import com.example.possystembw.data.NumberSequenceRemoteRepository
//import com.example.possystembw.data.NumberSequenceRemoteRepository
import com.example.possystembw.data.NumberSequenceRepository
import com.example.possystembw.database.Customer
import com.example.possystembw.database.LineGroupWithDiscounts
import com.example.possystembw.database.MixMatch
import com.example.possystembw.database.MixMatchWithDetails
import com.example.possystembw.database.ProductBundle
import com.example.possystembw.database.TenderDeclaration
import com.example.possystembw.database.Window
import com.example.possystembw.database.ZRead
import com.example.possystembw.ui.ViewModel.ARViewModel
import com.example.possystembw.ui.ViewModel.ARViewModelFactory
import com.example.possystembw.ui.ViewModel.CustomerViewModel
import com.example.possystembw.ui.ViewModel.CustomerViewModelFactory
import com.example.possystembw.ui.ViewModel.MixMatchViewModel
import com.example.possystembw.ui.ViewModel.MixMatchViewModelFactory
import com.example.possystembw.ui.ViewModel.TransactionSyncService
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import java.nio.charset.Charset
import java.util.TimeZone
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import android.webkit.WebView
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.ScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.possystembw.DAO.NumberSequenceRemoteDao
import com.example.possystembw.MainActivity
import com.example.possystembw.Repository.StaffRepository
import com.example.possystembw.adapter.PromoSuggestionAdapter
import com.example.possystembw.adapter.PurchaseHistoryAdapter
import com.example.possystembw.adapter.StaffAdapter
import com.example.possystembw.database.PrinterSettings
import com.example.possystembw.database.StaffEntity
import com.example.possystembw.ui.ViewModel.PrinterSettingsViewModel
import com.example.possystembw.ui.ViewModel.StaffViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.isActive
import kotlin.math.ceil
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.media.MediaPlayer
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.text.TextUtils
import android.text.style.ReplacementSpan
import java.io.ByteArrayOutputStream
import android.util.Base64
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.possystembw.DeviceUtils
import com.example.possystembw.adapter.ItemSalesAdapter
import com.example.possystembw.data.LoyaltyCardRepository
import com.example.possystembw.database.LoyaltyCard
import com.example.possystembw.ui.ViewModel.LoyaltyCardViewModel
import com.example.possystembw.ui.ViewModel.LoyaltyCardViewModelFactory
import com.example.possystembw.ui.ViewModel.NumberSequenceAutoChecker
import com.example.possystembw.ui.ViewModel.setupNumberSequenceChecker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.lang.reflect.Array.set
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin

class Window1 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityWindow1Binding
    private lateinit var autoDatabaseTransferManager: AutoDatabaseTransferManager
    private lateinit var productViewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var arViewModel: ARViewModel
    private lateinit var customerViewModel: CustomerViewModel
    private val TAG = "Window1"
    private var windowId: Int = -1
    private lateinit var transactionDao: TransactionDao
    private lateinit var cashFundRepository: CashFundRepository
    private lateinit var database: AppDatabase
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var windowViewModel: WindowViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var discountViewModel: DiscountViewModel
    private var partialPaymentAmount: Double = 0.0
    private var isPartialPaymentJustApplied: Boolean = false
    private var transactionComment: String = ""
    private var lastTransactionId = 0
    private var lastReceiptNumber = 0
    private var lastTransactionNumber = 0
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var transactionAdapter: TransactionAdapter
    private val BLUETOOTH_PERMISSION_REQUEST_CODE = 1
    private lateinit var bluetoothPrinterHelper: BluetoothPrinterHelper
    private lateinit var zReadButton: Button
    private lateinit var xReadButton: Button
    private var lastZReadTime: Long = 0
    private var isPulloutCashFundProcessed = false
    private var isTenderDeclarationProcessed = false
    private var tenderDeclaration: TenderDeclaration? = null
    private var currentCashFund: Double = 0.0
    private var isZReadPerformed: Boolean = false
    private lateinit var refreshJob: Job
    private lateinit var viewTransactionButton: Button
    private lateinit var returnTransactionButton: Button
    private lateinit var viewModel: TransactionViewModel
    private var isCashFundEntered = false
    private var partialPaymentApplied: Boolean = false
    private lateinit var tenderDeclarationDao: TenderDeclarationDao
    private var returnDialog: AlertDialog? = null
    private var isObserving = false
    private var transactionDetailsDialog: AlertDialog? = null
    private var transactionItemsObserver: Observer<List<TransactionRecord>>? = null
    private lateinit var searchView: SearchView
    private var currentBundle: ProductBundle? = null
    private var currentSelections = mutableMapOf<Int, Product>()
    private lateinit var mixMatchViewModel: MixMatchViewModel
    private val selectedItems = mutableMapOf<String, MutableList<CartItem>>()
    private lateinit var progressDialog: AlertDialog
    private lateinit var zReadDao: ZReadDao
    private lateinit var numberSequenceRepository: NumberSequenceRepository


    private lateinit var numberSequenceRemoteRepository: NumberSequenceRemoteRepository
    private lateinit var numberSequenceRemoteDao: NumberSequenceRemoteDao

    private lateinit var sidebarLayout: ConstraintLayout
    private lateinit var sidebarToggleButton: ImageButton
    private lateinit var buttonContainer: LinearLayout
    private lateinit var insertButton: Button
    private lateinit var toggleButton: ImageButton
    private lateinit var overlayLayout: ConstraintLayout
    private lateinit var searchCardView: CardView
    private var isSidebarExpanded = true
    private var transactionSyncService: TransactionSyncService? = null  // Make nullable
    private lateinit var loadingDialog: AlertDialog


    private lateinit var webView: WebView
    private lateinit var webViewLoadingOverlay: FrameLayout
    private lateinit var webViewLoadingText: TextView

    private lateinit var webViewContainer: RelativeLayout

    private var currentQuery: String? = null
    private lateinit var storeNameTextView: TextView
    private lateinit var printerSettingsViewModel: PrinterSettingsViewModel
    private var printerIndicator: ImageView? = null

    private var customNumpadContainer: GridLayout? = null

    private lateinit var staffViewModel: StaffViewModel
    private lateinit var staffNameTextView: TextView

    private lateinit var takePicture: ActivityResultLauncher<Void?>
    private lateinit var pickImage: ActivityResultLauncher<String>

    private var currentDialog: AlertDialog? = null
    private var currentImageView: ImageView? = null
    private var currentStaff: StaffEntity? = null
    private var currentProfileSetCallback: ((StaffEntity) -> Unit)? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    private lateinit var loyaltyCardDialog: AlertDialog
    private lateinit var loyaltyCardViewModel: LoyaltyCardViewModel
    private lateinit var LoyaltyCardRepository: LoyaltyCardRepository
    private lateinit var LoyaltyCardViewModelFactory: LoyaltyCardViewModelFactory

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var barcodeScanner: BarcodeScanner
    private var isScanningEnabled = false

    // Add this constant with your other constants
    private val CAMERA_PERMISSION_REQUEST_CODE = 123

    private lateinit var mediaPlayer: MediaPlayer
    private var isProcessingBarcode = false
    private var lastScannedBarcode: Long? = null
    private var lastScanTime: Long = 0
    private val SCAN_COOLDOWN = 1000

        private var isTransactionDisabledAfterZRead = false
    private var midnightHandler: Handler? = null
    private var midnightRunnable: Runnable? = null

    private var drawerLayout: DrawerLayout? = null
    private var navigationView: NavigationView? = null
    private var hamburgerButton: ImageButton? = null
    private var cartBottomSheet: ConstraintLayout? = null
    private var cartToggleButton: FloatingActionButton? = null
    private var isCartVisible = false
    private var isMobileLayout = false
    private var isProcessingPayment = false

    private lateinit var sequenceChecker: NumberSequenceAutoChecker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceUtils.setOrientationBasedOnDevice(this)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        // Set orientation based on device

        binding = ActivityWindow1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            DeviceUtils.setOrientationBasedOnDevice(this)

            // Detect layout type
            detectLayoutType()
            loadDiscountsForWindow()

            // Initialize views based on layout
            initializeLayoutSpecificViews()

            // Initialize database FIRST
            database = AppDatabase.getDatabase(this)

            // Initialize all DAOs immediately after database
            transactionDao = database.transactionDao()
            tenderDeclarationDao = database.tenderDeclarationDao()
            zReadDao = database.zReadDao()

            // Initialize repositories BEFORE ViewModels
            initializeRepositories()

            // Initialize remote repositories
            val numberSequenceApi = RetrofitClient.numberSequenceApi
            val numberSequenceRemoteDao = database.numberSequenceRemoteDao()
            numberSequenceRemoteRepository = NumberSequenceRemoteRepository(
                numberSequenceApi,
                numberSequenceRemoteDao,
                transactionDao  // ADD THIS LINE
            )
            // Initialize ViewModels
            printerSettingsViewModel = ViewModelProvider(this)[PrinterSettingsViewModel::class.java]

            val staffDao = AppDatabase.getDatabase(application).staffDao()
            val staffApi = RetrofitClient.staffApi
            val staffRepository = StaffRepository(staffApi, staffDao)
            staffViewModel = ViewModelProvider(
                this,
                StaffViewModel.StaffViewModelFactory(staffRepository)
            )[StaffViewModel::class.java]

            val loyaltyCardDao = AppDatabase.getDatabase(application).loyaltyCardDao()
            val loyaltyCardApi = RetrofitClient.loyaltyCardApi
            val loyaltyCardRepository = LoyaltyCardRepository(loyaltyCardDao, loyaltyCardApi)
            val loyaltyCardFactory = LoyaltyCardViewModelFactory(loyaltyCardRepository)
            loyaltyCardViewModel = ViewModelProvider(this, loyaltyCardFactory)[LoyaltyCardViewModel::class.java]

            debugStaffRole()

            initializeActivityLaunchers()
            setupActivityResultLaunchers()
            StaffManager.init(this)

            setupStaffSelection()
            initializeStaffViews()

            // Now safely initialize ViewModels (after DAOs and repositories are initialized)
            safeInitializeViewModels()

            getWindowId()
            initializeViewModels()
            setupRecyclerViews()
            observeViewModels()

            // Layout-specific setup BEFORE setupButtons()
            if (isMobileLayout) {
                setupMobileSpecificFeatures()
            } else {
                initializeSidebarComponents()
                initializeOverlayComponents()
                setupSidebar()
                setupOverlay()
            }

            setupButtonListeners()
            checkForExistingCashFund()
            setupPartialPaymentDisplay()
            setupCommentButton()
            setupDeleteCommentButton()
            setupCommentHandling()
            setupTransactionView()
            checkBluetoothPermissions()
            initializeZXReadButtons()
            loadLastTransactionNumber()
            setupCashManagementButtons()

            BluetoothPrinterHelper.initialize(this)
            bluetoothPrinterHelper = BluetoothPrinterHelper.getInstance()
            setupViewModel()
            setupButtons() // This will use the safe version
            connectToPrinter()
            setupSearchView()
            productViewModel.selectCategory(null)
            initializeProgressDialog()
            updateCategoriesAndRecreate()
            observeCategoriesAndProducts()
            initializeMixMatch()
            transactionViewModel.startAutoSync(this)
            setupSequenceButton()

            updateHeaderInfo()

            // Common setup with safe error handling
            setupPrinterIndicator()
            startPrinterConnectionCheck()
            setupPriceOverrideButton() // This will use the safe version
            setupBarcodeScanning()
            setupBarcodeScanButton()
            initializeCameraX()

            // Safe button setup with error handling
            safeSetupButtons()
            sequenceChecker = setupNumberSequenceChecker(this)

            // Auto-check silently
            sequenceChecker.checkAndUpdateSequence(showToast = false)

            // Auto-reconnect to last printer
            if (!bluetoothPrinterHelper.isConnected()) {
                val prefs = getSharedPreferences("BluetoothPrinter", Context.MODE_PRIVATE)
                val lastPrinterAddress = prefs.getString("last_printer_address", null)
                if (lastPrinterAddress != null) {
                    lifecycleScope.launch {
                        try {
                            bluetoothPrinterHelper.connect(lastPrinterAddress)
                            updatePrinterIndicator()
                        } catch (e: Exception) {
                            Log.e(TAG, "Error reconnecting to last printer", e)
                        }
                    }
                }
            }

            Log.d(TAG, "✅ onCreate completed successfully for mobile mode")

        } catch (e: Exception) {
            Log.e(TAG, "❌ Error during onCreate", e)
            Toast.makeText(this, "Initialization Error: ${e.message}", Toast.LENGTH_LONG).show()
            // Don't finish the activity immediately, try to continue with basic functionality
        }
    }

    private fun safeSetupButtons() {
        try {
            // Safe setup for Reports button
            findViewById<Button>(R.id.Reportsbutton)?.setOnClickListener {
                val intent = Intent(this, ReportsActivity::class.java)
                startActivity(intent)
            }

            // Safe setup for price override button (already handled in setupPriceOverrideButton)

            // Safe setup for printer indicator
            findViewById<ImageView>(R.id.printerIndicator)?.let {
                printerIndicator = it
                updatePrinterIndicator()
            }

            Log.d(TAG, "✅ Safe button setup completed")
        } catch (e: Exception) {
            Log.e(TAG, "❌ Error in safe button setup", e)
        }
    }
    private fun detectLayoutType() {
        // Check what views actually exist in the loaded layout
        val drawerLayoutView = findViewById<DrawerLayout>(R.id.drawer_layout)
        val sidebarLayoutView = findViewById<ConstraintLayout>(R.id.sidebarLayout)

        val isTabletDevice = DeviceUtils.isTablet(this)
        val hasDrawer = drawerLayoutView != null
        val hasSidebar = sidebarLayoutView != null

        Log.d(TAG, "=== LAYOUT DETECTION ===")
        Log.d(TAG, "Device type: ${if (isTabletDevice) "Tablet" else "Phone"}")
        Log.d(TAG, "Has DrawerLayout: $hasDrawer")
        Log.d(TAG, "Has SidebarLayout: $hasSidebar")

        // Determine layout type based on actual layout loaded
        isMobileLayout = hasDrawer && !hasSidebar

        Log.d(TAG, "Final decision: ${if (isMobileLayout) "Mobile" else "Tablet"} mode")
    }


    private fun initializeLayoutSpecificViews() {
        if (isMobileLayout) {
            // Mobile-specific views
            drawerLayout = findViewById(R.id.drawer_layout)
            navigationView = findViewById(R.id.nav_view)
            hamburgerButton = findViewById(R.id.hamburgerButton)
            cartBottomSheet = findViewById(R.id.cartBottomSheet)
            cartToggleButton = findViewById(R.id.cartToggleButton)

            Log.d(TAG, "✅ Mobile views initialized")
            Log.d(TAG, "DrawerLayout: ${drawerLayout != null}")
            Log.d(TAG, "NavigationView: ${navigationView != null}")
            Log.d(TAG, "HamburgerButton: ${hamburgerButton != null}")
            Log.d(TAG, "CartBottomSheet: ${cartBottomSheet != null}")
            Log.d(TAG, "CartToggleButton: ${cartToggleButton != null}")
        } else {
            // Tablet-specific views will be initialized in existing methods
            Log.d(TAG, "✅ Tablet layout detected")
        }
    }

    private fun setupMobileSpecificFeatures() {
        try {
            // Setup navigation drawer
            navigationView?.setNavigationItemSelectedListener(this)

            // Update store name in navigation header
            navigationView?.getHeaderView(0)?.let { headerView ->
                val navStoreName = headerView.findViewById<TextView>(R.id.nav_store_name)
                val currentStore = SessionManager.getCurrentUser()?.storeid ?: "Unknown Store"
                navStoreName?.text = "Store: $currentStore"
            }

            // Setup hamburger button
            hamburgerButton?.setOnClickListener {
                drawerLayout?.openDrawer(GravityCompat.START)
            }

            // Setup cart toggle
            cartToggleButton?.setOnClickListener {
                toggleCartVisibility()
            }

            // Setup cart close button
            findViewById<ImageButton>(R.id.closeCartButton)?.setOnClickListener {
                hideCart()
            }

            // Setup options button for mobile
            findViewById<ImageButton>(R.id.optionsButton)?.setOnClickListener {
                showOptionsDialog()
            }

            Log.d(TAG, "✅ Mobile features setup complete")
        } catch (e: Exception) {
            Log.e(TAG, "❌ Mobile features setup failed", e)
        }
    }

    private fun toggleCartVisibility() {
        if (isCartVisible) {
            hideCart()
        } else {
            showCart()
        }
    }

    private fun showCart() {
        cartBottomSheet?.let { bottomSheet ->
            bottomSheet.visibility = View.VISIBLE
            bottomSheet.translationY = bottomSheet.height.toFloat()
            bottomSheet.animate()
                .translationY(0f)
                .setDuration(300)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
            isCartVisible = true
            cartToggleButton?.hide()
        }
    }

    private fun hideCart() {
        cartBottomSheet?.let { bottomSheet ->
            bottomSheet.animate()
                .translationY(bottomSheet.height.toFloat())
                .setDuration(300)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction {
                    bottomSheet.visibility = View.GONE
                    isCartVisible = false
                    cartToggleButton?.show()
                }
                .start()
        }
    }

    private fun showOptionsDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_options_custom, null)

        val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle4)
            .setView(dialogView)
            .create()

        // Apply custom background
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Set up click listeners for each option card
        setupOptionClickListeners(dialogView, dialog)

        // Cancel button
        dialogView.findViewById<ImageButton>(R.id.cancelButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        // Add entrance animation
        dialogView.alpha = 0f
        dialogView.scaleX = 0.8f
        dialogView.scaleY = 0.8f
        dialogView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
    private fun setupOptionClickListeners(dialogView: View, dialog: AlertDialog) {
        // Discount option
        dialogView.findViewById<CardView>(R.id.discountCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showDiscountDialog()
            }
        }

        // Set Price option
        dialogView.findViewById<CardView>(R.id.setPriceCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showPriceOverrideDialog()
            }
        }

        // Partial Payment option
        dialogView.findViewById<CardView>(R.id.partialPaymentCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showPartialPaymentDialog()
            }
        }

        // Void Partial Payment option
        dialogView.findViewById<CardView>(R.id.voidPartialCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showVoidPartialPaymentDialog()
            }
        }

        // Add Comment option
        dialogView.findViewById<CardView>(R.id.addCommentCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showAddCommentDialog()
            }
        }


        // QR Scanner option - Fixed for mobile mode
        dialogView.findViewById<CardView>(R.id.qrScannerCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                openQRScanner()
            }
        }
        dialogView.findViewById<CardView>(R.id.printerCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                // Handle printer indicator click (same logic as your existing printer indicator)
                if (!bluetoothPrinterHelper.isConnected()) {
                    // Try to reconnect to last known printer first
                    val prefs = getSharedPreferences("BluetoothPrinter", Context.MODE_PRIVATE)
                    val lastPrinterAddress = prefs.getString("last_printer_address", null)

                    if (lastPrinterAddress != null) {
                        lifecycleScope.launch {
                            try {
                                val connected = bluetoothPrinterHelper.connect(lastPrinterAddress)
                                if (!connected) {
                                    // If reconnection fails, open printer settings
                                    val intent = Intent(this@Window1, PrinterSettingsActivity::class.java)
                                    startActivity(intent)
                                }
                                updatePrinterIndicator()
                            } catch (e: Exception) {
                                Log.e(TAG, "Error reconnecting to printer", e)
                                // Open printer settings on connection error
                                val intent = Intent(this@Window1, PrinterSettingsActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        // No last known printer, open printer settings directly
                        val intent = Intent(this@Window1, PrinterSettingsActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    // Show printer status dialog when connected
                    showPrinterStatusDialog()
                }
            }
        }

        // Insert Button option - NEW
        dialogView.findViewById<CardView>(R.id.updateCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()

                // Show loading dialog
                val loadingDialog = createLoadingDialog("Syncing data...")
                loadingDialog.show()

                // Handle insert button click (same logic as your existing insert button)
                lifecycleScope.launch {
                    try {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                "Fetching all data from API...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        coroutineScope {
                            val productsJob = async {
                                updateLoadingText(loadingDialog, "Syncing products...")
                                productViewModel.insertAllProductsFromApi()
                            }
                            val discountsJob = async {
                                try {
                                    updateLoadingText(loadingDialog, "Syncing discounts...")
                                    discountViewModel.fetchDiscounts()
                                } catch (e: Exception) {
                                    Log.e("Window1", "Error fetching discounts", e)
                                }
                            }
                            val arTypesJob = async {
                                try {
                                    updateLoadingText(loadingDialog, "Syncing AR types...")
                                    arViewModel.refreshARTypes()
                                } catch (e: Exception) {
                                    Log.e("Window1", "Error fetching AR types", e)
                                }
                            }
                            val customersJob = async {
                                try {
                                    updateLoadingText(loadingDialog, "Syncing customers...")
                                    customerViewModel.refreshCustomers()
                                } catch (e: Exception) {
                                    Log.e("Window1", "Error fetching customers", e)
                                }
                            }
                            val mixMatchJob = async {
                                try {
                                    updateLoadingText(loadingDialog, "Syncing promotions...")
                                    mixMatchViewModel.refreshMixMatches()
                                } catch (e: Exception) {
                                    Log.e("Window1", "Error fetching mix & match data", e)
                                }
                            }

                            val results = awaitAll(
                                productsJob,
                                discountsJob,
                                arTypesJob,
                                customersJob,
                                mixMatchJob
                            )

                            updateLoadingText(loadingDialog, "Finalizing...")
                            productViewModel.loadAlignedProducts()

                            val statusMessages = mutableListOf<String>()

                            if (results[0] != null) {
                                statusMessages.add("Products updated")
                            }

                            discountViewModel.discounts.value?.let {
                                statusMessages.add("Discounts updated (${it.size} items)")
                            }

                            (arViewModel.arTypes.value as? List<*>)?.let {
                                statusMessages.add("AR Types updated (${it.size} items)")
                            }

                            (customerViewModel.customers.value as? List<*>)?.let {
                                statusMessages.add("Customers updated (${it.size} items)")
                            }

                            mixMatchViewModel.mixMatches.value.let {
                                statusMessages.add("Mix & Match offers updated (${it.size} items)")
                            }

                            withContext(Dispatchers.Main) {
                                loadingDialog.dismiss()

                                if (statusMessages.isNotEmpty()) {
                                    // Show success dialog
                                    showSuccessDialog(statusMessages.joinToString("\n"))
                                    updateCategoriesAndRecreate()
                                    refreshProductAdapter()
                                } else {
                                    Toast.makeText(
                                        this@Window1,
                                        "No data was updated. Please check your connection.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("Window1", "Error syncing data", e)
                        withContext(Dispatchers.Main) {
                            loadingDialog.dismiss()
                            Toast.makeText(
                                this@Window1,
                                "Sync failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        // Cash Drawer option
        dialogView.findViewById<CardView>(R.id.cashDrawerCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                val intent = Intent(this, ReportsActivity::class.java)
                startActivity(intent)
            }
        }

        // Daily Journal option
        dialogView.findViewById<CardView>(R.id.dailyJournalCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showTransactionListDialog()
            }
        }

        // Change Fund option
        dialogView.findViewById<CardView>(R.id.changeFundCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showCashFundDialog()
            }
        }

        // Pullout Fund option
        dialogView.findViewById<CardView>(R.id.pulloutFundCard).setOnClickListener {
            animateCardClick(it) {
                dialog.dismiss()
                showPulloutCashFundDialog()
            }
        }
    }
    private fun createLoadingDialog(message: String): AlertDialog {
        val dialogView = layoutInflater.inflate(R.layout.dialog_loading, null)
        val messageText = dialogView.findViewById<TextView>(R.id.loadingMessage)
        messageText.text = message

        return AlertDialog.Builder(this, R.style.CustomDialogStyle3)
            .setView(dialogView)
            .setCancelable(false)
            .create().apply {
                window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            }
    }

    private fun updateLoadingText(dialog: AlertDialog, message: String) {
        runOnUiThread {
            dialog.findViewById<TextView>(R.id.loadingMessage)?.text = message
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(this, R.style.CustomDialogStyle3)
            .setTitle("✅ Sync Successful")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()
            .apply {
                window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                show()
            }
    }
    private fun animateCardClick(view: View, action: () -> Unit) {
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(100)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(100)
                    .withEndAction {
                        action()
                    }
                    .start()
            }
            .start()
    }
    private fun openQRScanner() {
        try {
            if (!isScannerAvailable()) {
                Toast.makeText(this, "Barcode scanner not available in mobile layout", Toast.LENGTH_SHORT).show()
                return
            }

            if (checkCameraPermission()) {
                // Initialize camera provider if not already done
                if (!::cameraProviderFuture.isInitialized) {
                    setupBarcodeScanning()
                }
                showBarcodeScannerOverlay()
            } else {
                requestCameraPermission()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error opening QR scanner", e)
            Toast.makeText(this, "Error opening QR scanner: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_reports -> {
                val intent = Intent(this, ReportsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_stock_counting -> {
                val intent = Intent(this, StockCountingActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_web_reports -> {
                navigateToMainWithUrl("https://eljin.org/reports", "REPORTS")
            }
            R.id.nav_customers -> {
                navigateToMainWithUrl("https://eljin.org/customers", "CUSTOMER")
            }
            R.id.nav_loyalty_card -> {
                navigateToMainWithUrl("https://eljin.org/loyalty-cards", "Loyalty Card")
            }
            R.id.nav_stock_transfer -> {
                navigateToMainWithUrl("https://eljin.org/StockTransfer", "Stock Transfer")
            }
            R.id.nav_attendance -> {
                val intent = Intent(this, AttendanceActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_printer_settings -> {
                val intent = Intent(this, PrinterSettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_cash_drawer -> {
                val intent = Intent(this, ReportsActivity::class.java)
                startActivity(intent)
            }

            R.id.nav_logout -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        when {
            isMobileLayout && drawerLayout?.isDrawerOpen(GravityCompat.START) == true -> {
                drawerLayout?.closeDrawer(GravityCompat.START)
            }
            isMobileLayout && isCartVisible -> {
                hideCart()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }
    private fun initializeCameraX() {
        try {
            ProcessCameraProvider.getInstance(this)
        } catch (e: Exception) {
            Log.e("MyApplication", "Error initializing CameraX", e)
        }
    }
    private fun initializeActivityLaunchers() {
        // Initialize with empty implementations - the real handlers will be set in showProfilePictureDialog
        takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { }
        pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { }
    }

    private fun initializeStaffViews() {
        staffNameTextView = findViewById(R.id.staffNameTextView)
        staffNameTextView.text = "Staff: ${StaffManager.getCurrentStaff()}"
        staffNameTextView.setOnClickListener {
            showStaffSelectionDialog()
        }
    }
    private fun initializeDatabase() {
        // Remove numberSequenceRemoteRepository initialization from here since it's done in onCreate
        tenderDeclarationDao = database.tenderDeclarationDao()

        val repository = TransactionRepository(
            database.transactionDao(),
            numberSequenceRemoteRepository
        )
        transactionSyncService = TransactionSyncService(repository)
        transactionSyncService?.startSyncService(lifecycleScope)
    }
    override fun onResume() {
        super.onResume()
        // Check session validity and refresh
        if (!SessionManager.isSessionValid()) {
            // Redirect to login if session is invalid
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        // Refresh session timestamp on activity resume
        SessionManager.refreshSession()
        updatePrinterIndicator()
//        checkForExistingCashFund() // And/or here


    }
    private fun setupUpdateButtonAnimation() {
        insertButton = findViewById(R.id.insertButton)

        insertButton.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.animate()
                        .scaleX(0.95f)
                        .scaleY(0.95f)
                        .setDuration(100)
                        .start()
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    v.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                    false
                }
                else -> false
            }
        }
    }


    private fun initializeSidebarComponents() {
        try {
            sidebarLayout = findViewById(R.id.sidebarLayout)
            sidebarToggleButton = findViewById(R.id.toggleButton1)
            buttonContainer = findViewById(R.id.buttonContainer)

            // Set initial state
            isSidebarExpanded = true
            buttonContainer.visibility = View.VISIBLE
            buttonContainer.alpha = 1f
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing sidebar components", e)
        }
    }

    private fun setupObservers() {

        lifecycleScope.launch {
            loyaltyCardViewModel.allLoyaltyCards.collect { cards ->
                Log.d("MainActivity", "Received loyalty cards: ${cards.size}")
                // Handle the loyalty cards here
                // For example, update UI or process the cards
            }
        }
        // Add staff observers here
        lifecycleScope.launch {
            staffViewModel.staffData.collect { staffList ->
                Log.d("MainActivity", "Staff updated: ${staffList.size} members")
                // You can add additional handling here if needed
            }
        }

        // Add staff error observer
        staffViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

    }


    private fun initializeOverlayComponents() {
        try {
            toggleButton = findViewById(R.id.toggleButton)
            overlayLayout = findViewById(R.id.overlayLayout)
            searchCardView = findViewById(R.id.searchCardView)
            insertButton = findViewById(R.id.insertButton)

            // Add ripple effect to the button
            insertButton.background = ContextCompat.getDrawable(this, R.drawable.update_button_background)
            insertButton.isClickable = true
            insertButton.isFocusable = true
//            insertButton.foreground = ContextCompat.getDrawable(this, recyclerViewLineGroupsandroid.R.attr.selectableItemBackground)

            // Set initial state
            overlayLayout.visibility = View.GONE
            searchCardView.visibility = View.VISIBLE
            insertButton.visibility = View.VISIBLE
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing overlay components", e)
        }
    }

    private fun setupSidebar() {
        try {
            sidebarToggleButton.setOnClickListener {
                if (isSidebarExpanded) {
                    collapseSidebar()
                } else {
                    expandSidebar()
                }
            }
            setupSidebarButtons()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up sidebar", e)
        }
    }

    private fun setupOverlay() {
        try {
            toggleButton.setOnClickListener {
                val isOverlayVisible = overlayLayout.visibility == View.VISIBLE

                // Toggle visibility with animation
                if (isOverlayVisible) {
                    overlayLayout.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .withEndAction {
                            overlayLayout.visibility = View.GONE
                            searchCardView.visibility = View.VISIBLE
                            insertButton.visibility = View.VISIBLE
                        }
                } else {
                    overlayLayout.alpha = 0f
                    overlayLayout.visibility = View.VISIBLE
                    searchCardView.visibility = View.GONE
                    insertButton.visibility = View.GONE
                    overlayLayout.animate()
                        .alpha(1f)
                        .setDuration(200)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up overlay", e)
        }
    }

    fun setupSidebarButtons() {
        findViewById<ImageButton>(R.id.button2).setOnClickListener {
            val intent = Intent(this, ReportsActivity::class.java)
            startActivity(intent)
            showToast("Reports")
        }
        findViewById<ImageButton>(R.id.button3).setOnClickListener {
            navigateToMainWithUrl("https://eljin.org/order", "ORDERING")
        }

        findViewById<ImageButton>(R.id.stockcounting).setOnClickListener {
            val intent = Intent (this, StockCountingActivity::class.java)
            startActivity(intent)
            showToast("Stock Counting")

        }
        findViewById<ImageButton>(R.id.button5).setOnClickListener {
            navigateToMainWithUrl("https://eljin.org/StockTransfer", "Stock Transfer")
        }

        findViewById<ImageButton>(R.id.button6).setOnClickListener {
            navigateToMainWithUrl("https://eljin.org/reports", "REPORTS")
        }

        findViewById<ImageButton>(R.id.waste).setOnClickListener {
            navigateToMainWithUrl("https://eljin.org/waste", "WASTE")
        }

        findViewById<ImageButton>(R.id.partycakes).setOnClickListener {
            navigateToMainWithUrl("https://eljin.org/loyalty-cards", "PARTYCAKES")
        }

        findViewById<ImageButton>(R.id.customer).setOnClickListener {
            navigateToMainWithUrl("https://eljin.org/customers", "CUSTOMER")
        }

        findViewById<ImageButton>(R.id.button7).setOnClickListener {
            navigateToMainWithUrl(null, "POS SYSTEM")
        }

        findViewById<ImageButton>(R.id.printerSettingsButton).setOnClickListener {
            val intent = Intent(this, PrinterSettingsActivity::class.java)
            startActivity(intent)
            showToast("PRINTER SETTINGS")
        }
        findViewById<ImageButton>(R.id.attendanceButton).setOnClickListener {
            val intent = Intent(this, AttendanceActivity::class.java)
            startActivity(intent)
            showToast("ATTENDANCE")
        }
    findViewById<ImageButton>(R.id.button8).setOnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
    private fun savePrinterToSettings(device: BluetoothDevice) {
        lifecycleScope.launch {
            try {
                printerSettingsViewModel.addPrinter(
                    name = device.name ?: "Unknown Printer",
                    macAddress = device.address,
                    isDefault = false,
                    windowId = 1  // Since this is Window1
                )
            } catch (e: Exception) {
                Log.e("Window1", "Error saving printer: ${e.message}")
            }
        }
    }
    private fun setupPrinterIndicator() {
        findViewById<ImageView>(R.id.printerIndicator)?.let { indicator ->
            printerIndicator = indicator
            updatePrinterIndicator()

            // Enhanced click listener with printer settings option
            indicator.setOnClickListener {
                if (!bluetoothPrinterHelper.isConnected()) {
                    // Try to reconnect to last known printer first
                    val prefs = getSharedPreferences("BluetoothPrinter", Context.MODE_PRIVATE)
                    val lastPrinterAddress = prefs.getString("last_printer_address", null)

                    if (lastPrinterAddress != null) {
                        lifecycleScope.launch {
                            try {
                                val connected = bluetoothPrinterHelper.connect(lastPrinterAddress)
                                if (!connected) {
                                    // If reconnection fails, open printer settings
                                    val intent = Intent(this@Window1, PrinterSettingsActivity::class.java)
                                    startActivity(intent)
                                }
                                updatePrinterIndicator()
                            } catch (e: Exception) {
                                Log.e(TAG, "Error reconnecting to printer", e)
                                // Open printer settings on connection error
                                val intent = Intent(this@Window1, PrinterSettingsActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        // No last known printer, open printer settings directly
                        val intent = Intent(this@Window1, PrinterSettingsActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    // Show printer status dialog when connected
                    showPrinterStatusDialog()
                }
            }
        }
    }
    private fun showPrinterStatusDialog() {
        val printerAddress = bluetoothPrinterHelper.getCurrentPrinterAddress()
        val prefs = getSharedPreferences("BluetoothPrinter", Context.MODE_PRIVATE)
        val printerName = prefs.getString("last_printer_name", "Unknown Printer")

        AlertDialog.Builder(this)
            .setTitle("Printer Status")
            .setMessage("Connected to: $printerName\nAddress: $printerAddress")
            .setPositiveButton("Test Print") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val testContent = """
                        ===========================
                              TEST PRINT
                        ===========================
                        Printer: $printerName
                        Date: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}
                        ===========================
                        Test print successful!
                        ===========================
                        
                        
                        
                    """.trimIndent()

                        bluetoothPrinterHelper.printGenericReceipt(testContent)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error during test print", e)
                    }
                }
            }
            .setNeutralButton("Settings") { _, _ ->
                val intent = Intent(this, PrinterSettingsActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Disconnect") { _, _ ->
                lifecycleScope.launch {
                    bluetoothPrinterHelper.disconnect()
                    updatePrinterIndicator()
                }
            }
            .show()
    }
    private fun navigateToMainWithUrl(url: String?, message: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            url?.let { putExtra("web_url", it) }
        }
        message?.let { showToast(it) }
        startActivity(intent)
    }


    //    private fun logout() {
//        // Clear the session
//        SessionManager.clearCurrentUser()
//
//        // Cancel any ongoing jobs
//        if (::refreshJob.isInitialized) {
//            refreshJob.cancel()
//        }
//
//        // Navigate back to login screen
//        val intent = Intent(this, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()
//    }
    private fun showToast(message: String) {
        Toast.makeText(this@Window1, message, Toast.LENGTH_SHORT).show()
    }
    private fun collapseSidebar() {
        // Prevent multiple animations from running simultaneously
        if (!isSidebarExpanded) return

        val animatorSet = AnimatorSet()
        val sidebarWidth = ValueAnimator.ofInt(sidebarLayout.width, dpToPx(24)).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                sidebarLayout.updateLayoutParams {
                    width = value
                }
                updateContentMargins(value)
            }
        }

        // Create animations for components
        val buttonFade = ObjectAnimator.ofFloat(buttonContainer, View.ALPHA, 1f, 0f).apply {
            duration = 150
        }

        val titleFade = ObjectAnimator.ofFloat(findViewById(R.id.ecposTitle), View.ALPHA, 1f, 0f).apply {
            duration = 150
        }

        val toggleRotation = ObjectAnimator.ofFloat(sidebarToggleButton, View.ROTATION, 0f, 180f).apply {
            duration = 300
        }

        val toggleMargin = ValueAnimator.ofInt(dpToPx(90), dpToPx(8)).apply {
            duration = 300
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                sidebarToggleButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    marginStart = value
                }
            }
        }

        animatorSet.playTogether(sidebarWidth, buttonFade, titleFade, toggleRotation, toggleMargin)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                // Initial setup if needed
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                buttonContainer.visibility = View.GONE
                isSidebarExpanded = false
            }
        })

        animatorSet.start()
    }

    private fun expandSidebar() {
        // Prevent multiple animations from running simultaneously
        if (isSidebarExpanded) return

        val animatorSet = AnimatorSet()
        val sidebarWidth = ValueAnimator.ofInt(sidebarLayout.width, dpToPx(100)).apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                sidebarLayout.updateLayoutParams {
                    width = value
                }
                updateContentMargins(value)
            }
        }

        // Reset initial states
        buttonContainer.apply {
            visibility = View.VISIBLE
            alpha = 0f
        }

        // Create animations for components
        val buttonFade = ObjectAnimator.ofFloat(buttonContainer, View.ALPHA, 0f, 1f).apply {
            duration = 150
            startDelay = 150 // Start after sidebar expansion begins
        }

        val titleFade = ObjectAnimator.ofFloat(findViewById(R.id.ecposTitle), View.ALPHA, 0f, 1f).apply {
            duration = 150
            startDelay = 150
        }

        val toggleRotation = ObjectAnimator.ofFloat(sidebarToggleButton, View.ROTATION, 180f, 0f).apply {
            duration = 300
        }

        val toggleMargin = ValueAnimator.ofInt(dpToPx(8), dpToPx(90)).apply {
            duration = 300
            addUpdateListener { animator ->
                val value = animator.animatedValue as Int
                sidebarToggleButton.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    marginStart = value
                }
            }
        }

        animatorSet.playTogether(sidebarWidth, buttonFade, titleFade, toggleRotation, toggleMargin)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                isSidebarExpanded = true
            }
        })

        animatorSet.start()
    }

    private fun updateContentMargins(sidebarWidth: Int) {
        // Update margins for all affected views
//        val marginStart = sidebarWidth + dpToPx(16)

        // Update recyclerview margins
        findViewById<RecyclerView>(R.id.categoryRecyclerView)?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.marginStart = marginStart
        }

        findViewById<ScrollView>(R.id.scrollView2)?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.marginStart = marginStart
        }

        // Update search card view position
        findViewById<CardView>(R.id.searchCardView)?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.marginStart = marginStart
        }
    }
    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

        private fun showTransactionListDialog() {
            val dialogView = layoutInflater.inflate(R.layout.dialog_transaction_list, null)
            val recyclerView = dialogView.findViewById<RecyclerView>(R.id.transactionRecyclerView)
            val searchEditText = dialogView.findViewById<EditText>(R.id.searchEditText)
            val searchButton = dialogView.findViewById<ImageButton>(R.id.searchButton)
            val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
            val datePickerButton = dialogView.findViewById<Button>(R.id.datePickerButton)
            val itemSalesButton = dialogView.findViewById<Button>(R.id.itemSalesButton)

            // Apply mobile-specific styling
            if (isMobileLayout) {
                searchEditText.textSize = 12f
                closeButton.textSize = 11f
                datePickerButton.textSize = 10f
                itemSalesButton.textSize = 10f

                // Adjust padding for mobile
                val paddingDp = (8 * resources.displayMetrics.density).toInt()
                dialogView.setPadding(paddingDp, paddingDp/2, paddingDp, paddingDp/2)
            }

            // Set current date as default
            val currentDate = Calendar.getInstance()
            datePickerButton.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(currentDate.time)
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Create adapter with sorting logic
            val transactionAdapter = TransactionAdapter { transaction ->
                showTransactionDetailsDialog(transaction)
            }.apply {
                setSortComparator { t1, t2 ->
                    Log.d(TAG, "Dialog sorting: ${t1.transactionId} vs ${t2.transactionId}")
                    t2.createdDate.compareTo(t1.createdDate)
                }
            }
            recyclerView.adapter = transactionAdapter


        // FIXED: Date picker functionality with proper date range handling
        datePickerButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    Log.d(TAG, "=== DIALOG DATE PICKER CLICKED ===")
                    Log.d(TAG, "Selected: year=$year, month=$month, day=$day")

                    val selectedDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val endDate = Calendar.getInstance().apply {
                        time = selectedDate.time
                        set(Calendar.HOUR_OF_DAY, 23)
                        set(Calendar.MINUTE, 59)
                        set(Calendar.SECOND, 59)
                        set(Calendar.MILLISECOND, 999)
                    }

                    Log.d(TAG, "Dialog selectedDate: ${selectedDate.time}")
                    Log.d(TAG, "Dialog endDate: ${endDate.time}")

                    // Update button text
                    datePickerButton.text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(selectedDate.time)

                    lifecycleScope.launch {
                        try {
                            val currentStoreId = SessionManager.getCurrentUser()?.storeid
                            Log.d(TAG, "Dialog currentStoreId: $currentStoreId")

                            if (currentStoreId != null) {
                                val transactions = withContext(Dispatchers.IO) {
                                    // Use the updated formatDateToString function
                                    val startDateStr = formatDateToString(selectedDate.time)
                                    val endDateStr = formatDateToString(endDate.time)

                                    Log.d(TAG, "=== DIALOG QUERY ===")
                                    Log.d(TAG, "Dialog querying transactions from $startDateStr to $endDateStr")

                                    val result = transactionDao.getTransactionsByDateRange(startDateStr, endDateStr)

                                    Log.d(TAG, "Dialog raw result: ${result.size} transactions")
                                    result.take(3).forEach { transaction ->
                                        Log.d(TAG, "Dialog transaction: ${transaction.transactionId} - ${transaction.createdDate}")
                                    }

                                    result
                                }

                                Log.d(TAG, "Dialog found ${transactions.size} transactions for selected date")
                                transactionAdapter.setTransactions(transactions)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Dialog error loading transactions for date range", e)
                            Toast.makeText(
                                this@Window1,
                                "Error loading transactions: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        // Load initial transactions for current date
        lifecycleScope.launch {
            try {
                val currentStoreId = SessionManager.getCurrentUser()?.storeid
                Log.d(TAG, "=== DIALOG INITIAL LOAD ===")
                Log.d(TAG, "Dialog initial currentStoreId: $currentStoreId")

                if (currentStoreId != null) {
                    // Create today's date range - THE KEY DIFFERENCE: UTC TIMEZONE!
                    val todayStart = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val todayEnd = Calendar.getInstance().apply {
                        time = todayStart.time
                        set(Calendar.HOUR_OF_DAY, 23)
                        set(Calendar.MINUTE, 59)
                        set(Calendar.SECOND, 59)
                        set(Calendar.MILLISECOND, 999)
                    }

                    Log.d(TAG, "Dialog UTC todayStart: ${todayStart.time}")
                    Log.d(TAG, "Dialog UTC todayEnd: ${todayEnd.time}")

                    val transactions = withContext(Dispatchers.IO) {
                        val startDateStr = formatDateToString(todayStart.time)
                        val endDateStr = formatDateToString(todayEnd.time)

                        Log.d(TAG, "Dialog initial query: $startDateStr to $endDateStr")

                        val result = transactionDao.getTransactionsByDateRange(startDateStr, endDateStr)

                        Log.d(TAG, "Dialog initial result: ${result.size} transactions")
                        result.take(3).forEach { transaction ->
                            Log.d(TAG, "Dialog initial: ${transaction.transactionId} - ${transaction.createdDate}")
                        }

                        result
                    }

                    Log.d(TAG, "Dialog setting ${transactions.size} initial transactions")
                    transactionAdapter.setTransactions(transactions)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Dialog error loading initial transactions", e)
            }
        }

        // Rest of your existing code for search functionality, dialog setup, etc...
        // Search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                transactionAdapter.filter(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        searchButton.setOnClickListener {
            transactionAdapter.filter(searchEditText.text.toString())
        }

        val titleView = TextView(this@Window1)
//        titleView.text = "Transaction History"
        titleView.textSize = if (isMobileLayout) 16f else 18f
        titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
        titleView.setPadding(
            if (isMobileLayout) 50 else 24,  // left
            if (isMobileLayout) 50 else 20,  // top
            if (isMobileLayout) 16 else 24,  // right
            if (isMobileLayout) 8 else 12    // bottom
        )
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTypeface(null, Typeface.BOLD)

        val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//            .setCustomTitle(titleView)
            .setView(dialogView)
            .create()

        dialog.setOnDismissListener {
            transactionAdapter.cleanup()
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.apply {
            setBackgroundDrawableResource(R.drawable.dialog_background)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        dialog.show()

        // Apply mobile styling after dialog is shown
        applyMobileDialogStyling(dialog)
    }
    private fun showTransactionDetailsDialog(transaction: TransactionSummary) {
        Log.d(TAG, "Showing transaction details dialog for transaction ID: ${transaction.transactionId}")

        transactionDetailsDialog?.dismiss()

        val dialogView = layoutInflater.inflate(R.layout.dialog_transaction_details, null)
        val detailsTextView = dialogView.findViewById<TextView>(R.id.transactionDetailsTextView)
        val printButton = dialogView.findViewById<Button>(R.id.printButton)
        val returnButton = dialogView.findViewById<Button>(R.id.returnButton)
        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)

        lifecycleScope.launch {
            try {
                val items = withContext(Dispatchers.IO) {
                    transactionViewModel.getTransactionItems(transaction.transactionId)
                }

                // Use the same logic as generateReceiptContent but formatted for dialog display
                val receiptContent = buildTransactionDetailsContent(transaction, items)

                detailsTextView.text = receiptContent
            } catch (e: Exception) {
                Log.e(TAG, "Error loading transaction details", e)
                Toast.makeText(
                    this@Window1,
                    "Error loading transaction details: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        transactionDetailsDialog = AlertDialog.Builder(this, R.style.CustomDialogStyle1)
            .setView(dialogView)
            .create()

        transactionDetailsDialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        printButton.setOnClickListener {
            printReceiptWithItems(transaction)
            transactionDetailsDialog?.dismiss()
        }

        returnButton.setOnClickListener {
            transactionDetailsDialog?.dismiss()
            showReturnTransactionDialog(transaction)
        }

        closeButton.setOnClickListener {
            transactionDetailsDialog?.dismiss()
        }

        transactionDetailsDialog?.show()
    }

    // New helper function that uses the same logic as generateReceiptContent
    private fun buildTransactionDetailsContent(
        transaction: TransactionSummary,
        items: List<TransactionRecord>
    ): String {
        val isReturn = transaction.type == 2

        // Determine if this is a pure AR transaction or has mixed payments (same logic as generateReceiptContent)
        val hasOtherPayments = transaction.cash > 0 || transaction.gCash > 0 ||
                transaction.payMaya > 0 || transaction.card > 0 ||
                transaction.loyaltyCard > 0 || transaction.charge > 0 ||
                transaction.foodpanda > 0 || transaction.grabfood > 0 ||
                transaction.representation > 0

        val isPureAR = transaction.type == 3 && !hasOtherPayments
        val isMixedPayment = hasOtherPayments && transaction.type == 3

        return buildString {
            // BIR Info
            appendLine("TIN: Your TIN Number")
            appendLine("MIN: Your MIN")
            appendLine("Store: ${transaction.store}")
            appendLine("═".repeat(30))

            // Transaction Info with proper receipt type
            when {
                isReturn -> appendLine("RETURN RECEIPT")
                isMixedPayment -> appendLine("MIXED PAYMENT RECEIPT")
                isPureAR -> appendLine("AR RECEIPT")
                else -> appendLine("OFFICIAL RECEIPT")
            }

            // FIXED: Handle string date format properly using the same method from BluetoothPrinterHelper
            val formattedDate = formatTransactionDateSafely(transaction.createdDate)
            appendLine("Cashier: ${transaction.staff}")
            appendLine("Date: $formattedDate")
            appendLine("SI#: ${transaction.receiptId}")

            // For return receipts, show original transaction reference
            if (isReturn && !transaction.refundReceiptId.isNullOrEmpty()) {
                appendLine("Original SI#: ${transaction.refundReceiptId}")
            }

            appendLine("═".repeat(30))

            // Items Section
            appendLine("Item                    Price   Qty    Total")
            appendLine("═".repeat(30))

            items.forEach { item ->
                val effectivePrice = if (item.priceOverride != null && item.priceOverride!! > 0.0) {
                    item.priceOverride!!
                } else {
                    item.price
                }

                // For returns, show the actual returned quantity and amounts
                val displayQuantity = if (isReturn) kotlin.math.abs(item.quantity) else item.quantity
                val displayPrice = effectivePrice
                val itemTotal = if (isReturn) {
                    kotlin.math.abs(effectivePrice * item.quantity) // Show positive amount for display
                } else {
                    effectivePrice * item.quantity
                }

                // Item format
                if (item.isReturned && !isReturn) {
                    appendLine("(Returned)")
                }
                appendLine("${item.name.take(22).padEnd(22)} ${
                    String.format("%7.2f", displayPrice)
                } ${String.format("%3d", displayQuantity)} ${String.format("%9.2f", itemTotal)}")

                // Discounts (show positive amounts for readability)
                when (item.discountType?.uppercase()) {
                    "PERCENTAGE", "PWD", "SC" -> {
                        val discountAmount = if (isReturn) {
                            kotlin.math.abs(itemTotal * (item.discountRate ?: 0.0))
                        } else {
                            itemTotal * (item.discountRate ?: 0.0)
                        }
                        if (discountAmount > 0) {
                            appendLine(" Disc(${item.discountType}):${String.format("%22.2f", discountAmount)}")
                        }
                    }
                    "FIXED", "FIXEDTOTAL" -> {
                        val discountAmount = if (isReturn) {
                            kotlin.math.abs(item.discountAmount ?: 0.0)
                        } else {
                            item.discountAmount ?: 0.0
                        }
                        if (discountAmount > 0) {
                            appendLine(" Disc(Fixed):${String.format("%25.2f", discountAmount)}")
                        }
                    }
                }
            }

            appendLine("═".repeat(30))

            // Totals - for returns, show positive amounts for readability but indicate they are returns
            if (isReturn) {
                appendLine("Return Amount:${String.format("%25.2f", kotlin.math.abs(transaction.grossAmount))}")
                if (transaction.totalDiscountAmount != 0.0) {
                    appendLine("Less Discount:${String.format("%26.2f", kotlin.math.abs(transaction.totalDiscountAmount))}")
                }
                appendLine("Net Return:${String.format("%27.2f", kotlin.math.abs(transaction.netAmount))}")
                appendLine("Refund Amount:${String.format("%25.2f", kotlin.math.abs(transaction.totalAmountPaid))}")
            } else {
                appendLine("Gross Amount:${String.format("%27.2f", transaction.grossAmount)}")
                if (transaction.totalDiscountAmount > 0) {
                    appendLine("Less Discount:${String.format("%26.2f", transaction.totalDiscountAmount)}")
                }
                appendLine("Net Amount:${String.format("%29.2f", transaction.netAmount)}")
            }

            // Payment Method Details Section - Show for all non-return transactions (same as generateReceiptContent)
            if (!isReturn) {
                appendLine("═".repeat(30))
                appendLine("PAYMENT DETAILS")
                appendLine("═".repeat(30))

                // Check for all payment methods used - Include AR calculation
                val paymentMethods = mutableListOf<Pair<String, Double>>()

                if (transaction.cash > 0) paymentMethods.add("Cash" to transaction.cash)
                if (transaction.gCash > 0) paymentMethods.add("GCash" to transaction.gCash)
                if (transaction.payMaya > 0) paymentMethods.add("PayMaya" to transaction.payMaya)
                if (transaction.card > 0) paymentMethods.add("Card" to transaction.card)
                if (transaction.loyaltyCard > 0) paymentMethods.add("Loyalty Card" to transaction.loyaltyCard)
                if (transaction.charge > 0) paymentMethods.add("Charge" to transaction.charge)
                if (transaction.foodpanda > 0) paymentMethods.add("FoodPanda" to transaction.foodpanda)
                if (transaction.grabfood > 0) paymentMethods.add("GrabFood" to transaction.grabfood)
                if (transaction.representation > 0) paymentMethods.add("Representation" to transaction.representation)

                // Calculate AR amount (difference between net amount and cash payments)
                val totalCashPayments = paymentMethods.sumOf { it.second }
                val arAmount = transaction.netAmount - totalCashPayments

                // Add AR payment if there's an outstanding amount or if it's an AR transaction
                if (arAmount > 0.01 || (transaction.type == 3 && paymentMethods.isEmpty())) {
                    paymentMethods.add("Accounts Receivable" to if (arAmount > 0.01) arAmount else transaction.netAmount)
                }

                // Check if this is a split payment or has partial payment
                val isSplitPayment = paymentMethods.size > 1
                val hasPartialPayment = transaction.partialPayment > 0

                if (isSplitPayment) {
                    appendLine("SPLIT PAYMENT:")
                    paymentMethods.forEach { (method, amount) ->
                        appendLine("${method.padEnd(25)} ${String.format("%12.2f", amount)}")
                    }
                    appendLine("═".repeat(30))
                    appendLine("Total Amount:${String.format("%26.2f", transaction.netAmount)}")

                    // Show breakdown of payments
                    val cashPayments = paymentMethods.filter { it.first != "Accounts Receivable" }
                    val arPayments = paymentMethods.filter { it.first == "Accounts Receivable" }

                    if (cashPayments.isNotEmpty()) {
                        val totalPaid = cashPayments.sumOf { it.second }
                        appendLine("Amount Paid:${String.format("%28.2f", totalPaid)}")
                    }
                    if (arPayments.isNotEmpty()) {
                        val totalAR = arPayments.sumOf { it.second }
                        appendLine("On Account:${String.format("%30.2f", totalAR)}")
                    }

                } else if (paymentMethods.isNotEmpty()) {
                    val (method, amount) = paymentMethods.first()
                    appendLine("Payment Method: $method")
                    if (method == "Accounts Receivable") {
                        appendLine("On Account:${String.format("%30.2f", amount)}")
                        appendLine("Amount Paid:${String.format("%28.2f", 0.0)}")
                    } else {
                        appendLine("Amount Paid:${String.format("%28.2f", amount)}")
                    }
                }

                // Handle partial payments
                if (hasPartialPayment) {
                    appendLine("═".repeat(30))
                    appendLine("PARTIAL PAYMENT SUMMARY:")
                    appendLine("Previous Payment:${String.format("%24.2f", transaction.partialPayment)}")
                    appendLine("This Payment:${String.format("%28.2f", transaction.totalAmountPaid)}")
                    appendLine("Total Paid:${String.format("%30.2f", transaction.partialPayment + transaction.totalAmountPaid)}")
                }

                // Only show change for non-AR transactions or when change is actually given
                if (!isPureAR || transaction.changeGiven > 0) {
                    appendLine("Change:${String.format("%33.2f", transaction.changeGiven)}")
                }
            } else {
                // For return transactions, show refund method details
                appendLine("═".repeat(30))
                appendLine("REFUND DETAILS")
                appendLine("═".repeat(30))

                val refundMethods = mutableListOf<Pair<String, Double>>()

                // Collect refund methods (negative amounts become positive for display)
                if (transaction.cash < 0) refundMethods.add("Cash Refund" to kotlin.math.abs(transaction.cash))
                if (transaction.gCash < 0) refundMethods.add("GCash Refund" to kotlin.math.abs(transaction.gCash))
                if (transaction.payMaya < 0) refundMethods.add("PayMaya Refund" to kotlin.math.abs(transaction.payMaya))
                if (transaction.card < 0) refundMethods.add("Card Refund" to kotlin.math.abs(transaction.card))
                if (transaction.loyaltyCard < 0) refundMethods.add("Loyalty Refund" to kotlin.math.abs(transaction.loyaltyCard))
                if (transaction.charge < 0) refundMethods.add("Charge Refund" to kotlin.math.abs(transaction.charge))
                if (transaction.foodpanda < 0) refundMethods.add("FoodPanda Refund" to kotlin.math.abs(transaction.foodpanda))
                if (transaction.grabfood < 0) refundMethods.add("GrabFood Refund" to kotlin.math.abs(transaction.grabfood))
                if (transaction.representation < 0) refundMethods.add("Representation Refund" to kotlin.math.abs(transaction.representation))

                if (refundMethods.size > 1) {
                    appendLine("SPLIT REFUND:")
                    refundMethods.forEach { (method, amount) ->
                        appendLine("${method.padEnd(25)} ${String.format("%12.2f", amount)}")
                    }
                    appendLine("═".repeat(30))
                    appendLine("Total Refund:${String.format("%26.2f", refundMethods.sumOf { it.second })}")
                } else if (refundMethods.isNotEmpty()) {
                    val (method, amount) = refundMethods.first()
                    appendLine("Refund Method: ${method.replace(" Refund", "")}")
                    appendLine("Refund Amount:${String.format("%26.2f", amount)}")
                }
            }

            // VAT Information - show positive amounts for readability
            appendLine("═".repeat(30))
            if (isReturn) {
                appendLine("VATable Sales:${String.format("%26.2f", kotlin.math.abs(transaction.vatableSales))}")
                appendLine("VAT Amount:${String.format("%29.2f", kotlin.math.abs(transaction.vatAmount))}")
            } else {
                appendLine("VATable Sales:${String.format("%26.2f", transaction.vatableSales)}")
                appendLine("VAT Amount:${String.format("%29.2f", transaction.vatAmount)}")
            }
            appendLine("VAT Exempt:${String.format("%29.2f", 0.0)}")

            // Footer
            appendLine("═".repeat(30))
            if (!isReturn) {
                appendLine("ID/PWD/OSCA#:")
                appendLine("Name:")
                appendLine("Signature:")
                appendLine("Comment: ${transaction.comment ?: "N/A"}")

            } else {
                // FIXED: Use getCurrentDateFormatted() instead of formatCurrentDate()
                appendLine("Return processed on: ${getCurrentDateFormatted()}")
                if (!transaction.comment.isNullOrEmpty()) {
                    appendLine("Return Reason: ${transaction.comment}")
                }
            }

            if ((!transaction.customerAccount.isNullOrBlank() && transaction.customerAccount != "Walk-in Customer") ||
                (!transaction.customerName.isNullOrBlank() && transaction.customerName != "Walk-in Customer")
            ) {
                appendLine("═".repeat(30))
                appendLine("Customer Account: ${transaction.customerAccount}")
                appendLine("Customer Name: ${transaction.customerName ?: "N/A"}")
            }

            appendLine("═".repeat(30))
            appendLine("Valid for 5 years from PTU date")
            appendLine("POS Provider: IT WARRIORS")
        }
    }

    // FIXED: Consolidated date formatting functions to avoid conflicts
    private fun formatTransactionDateSafely(dateString: String): String {
        return try {
            if (dateString.isEmpty()) {
                return "Unknown Date"
            }

            // Try multiple input formats for the string date
            val inputFormats = listOf(
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ss"
            )

            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

            for (format in inputFormats) {
                try {
                    val inputFormat = SimpleDateFormat(format, Locale.US)
                    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val date = inputFormat.parse(dateString)
                    if (date != null) {
                        return outputFormat.format(date)
                    }
                } catch (e: Exception) {
                    // Try next format
                    continue
                }
            }

            // If all parsing fails, return the original string
            dateString
        } catch (e: Exception) {
            Log.e(TAG, "Error formatting date: ${e.message}")
            dateString.ifEmpty { "Unknown Date" }
        }
    }

    // FIXED: Simple current date formatter that doesn't conflict with Date objects
    private fun getCurrentDateFormatted(): String {
        return try {
            val currentDate = Date() // This creates a new Date object with current time
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            formatter.format(currentDate)
        } catch (e: Exception) {
            Log.e(TAG, "Error formatting current date: ${e.message}")
            "Date unavailable"
        }
    }

    private fun printReceiptWithItems(transaction: TransactionSummary) {
        lifecycleScope.launch {
            try {
                val items = withContext(Dispatchers.IO) {
                    // Fetch original transaction items
                    val originalItems = transactionViewModel.getTransactionItems(transaction.transactionId)

                    // If it's a return transaction (type 2)
                    if (transaction.type == 2) {
                        // Try to get the original transaction items using the refund receipt ID
                        val originalTransactionId = transaction.refundReceiptId
                        if (originalTransactionId != null) {
                            // Get original transaction items
                            val fullOriginalItems = transactionViewModel.getTransactionItems(originalTransactionId)

                            // Filter to only show returned items
                            val returnedItems = originalItems.filter { it.quantity < 0 }

                            // Combine original items with return items, ensuring we show the context of the full original transaction
                            fullOriginalItems.map { originalItem ->
                                // Check if this item was partially or fully returned
                                val matchingReturnItem = returnedItems.find {
                                    it.itemId == originalItem.itemId &&
                                            it.lineNum == originalItem.lineNum
                                }

                                // Mark original item as returned if it was returned
                                if (matchingReturnItem != null) {
                                    originalItem.copy(
                                        isReturned = true,
                                        returnQuantity = abs(matchingReturnItem.quantity.toDouble())
                                    )
                                } else {
                                    originalItem
                                }
                            }
                        } else {
                            // Fallback to original items if no original transaction found
                            originalItems
                        }
                    } else {
                        // For non-return transactions, use original items
                        originalItems
                    }
                }

                if (items.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "No items found for this transaction",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                // Log the reprint first
                val transactionLogger = TransactionLogger(this@Window1)
                transactionLogger.logReprint(
                    transaction = transaction,
                    items = items
                )

                // Try to connect to printer if not already connected
                if (!bluetoothPrinterHelper.isConnected()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Failed to connect to printer, but reprint was logged",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                val receiptType = when (transaction.type) {
                    2 -> BluetoothPrinterHelper.ReceiptType.RETURN
                    else -> BluetoothPrinterHelper.ReceiptType.REPRINT
                }

                val receiptContent = bluetoothPrinterHelper.generateReceiptContent(
                    transaction,
                    items,
                    receiptType
                )

                // FIXED: Proper way to combine string and byte array
                bluetoothPrinterHelper.outputStream?.write(receiptContent.toByteArray())
                bluetoothPrinterHelper.outputStream?.write("\n\n\n\n\n\n\n\n\n\n\n\n".toByteArray())

                // Cut command as separate operation
                val cutCommand = byteArrayOf(0x1D, 0x56, 0x00)
                bluetoothPrinterHelper.outputStream?.write(cutCommand)
                bluetoothPrinterHelper.outputStream?.flush()

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Receipt reprinted and logged successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error during reprint process: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error during reprint process: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    // FIXED: Enhanced filtering to properly exclude returned items
    private fun showReturnTransactionDialog(transaction: TransactionSummary) {
        lifecycleScope.launch {
            if (!checkSupervisorAccess()) return@launch

            Log.d(TAG, "Showing return transaction dialog for transaction ID: ${transaction.transactionId}")

            // Dismiss any existing dialog
            returnDialog?.dismiss()
            returnDialog = null

            try {
                // Show a loading indicator
                val loadingDialog = AlertDialog.Builder(this@Window1)
                    .setMessage("Loading transaction items...")
                    .setCancelable(false)
                    .create()
                loadingDialog.show()

                // Load transaction items
                val items = withContext(Dispatchers.IO) {
                    transactionViewModel.getTransactionItems(transaction.transactionId)
                }

                loadingDialog.dismiss()

                // FIXED: Enhanced filtering to exclude returned items properly
                val returnable = items.filter { item ->
                    val isValidQuantity = item.quantity > 0
                    val isNotReturned = !item.isReturned
                    val hasNoReturnTransaction = item.returnTransactionId.isNullOrEmpty()
                    val hasNoReturnQuantity = (item.returnQuantity ?: 0.0) == 0.0

                    // Log for debugging
                    Log.d(TAG, "Item: ${item.name}")
                    Log.d(TAG, "  - Quantity: ${item.quantity}")
                    Log.d(TAG, "  - isReturned: ${item.isReturned}")
                    Log.d(TAG, "  - returnTransactionId: ${item.returnTransactionId}")
                    Log.d(TAG, "  - returnQuantity: ${item.returnQuantity}")
                    Log.d(TAG, "  - Can return: ${isValidQuantity && isNotReturned && hasNoReturnTransaction}")

                    isValidQuantity && isNotReturned && hasNoReturnTransaction && hasNoReturnQuantity
                }

                Log.d(TAG, "Total items: ${items.size}, Returnable items: ${returnable.size}")

                // ADD THIS VERIFICATION LOG:
                Log.d(TAG, "=== ITEMS BEING PASSED TO ADAPTER ===")
                returnable.forEachIndexed { index, item ->
                    Log.d(TAG, "Adapter Item $index: ${item.name} - isReturned: ${item.isReturned}")
                }
                Log.d(TAG, "=== END ADAPTER VERIFICATION ===")

                Log.d(TAG, "Total items: ${items.size}, Returnable items: ${returnable.size}")

                if (returnable.isEmpty()) {
                    val returnedCount = items.count { it.isReturned || !it.returnTransactionId.isNullOrEmpty() }
                    val message = if (returnedCount > 0) {
                        "All items in this transaction have already been returned ($returnedCount items returned)"
                    } else {
                        "No items available for return in this transaction"
                    }

                    Toast.makeText(this@Window1, message, Toast.LENGTH_LONG).show()
                    return@launch
                }

                val dialogView = layoutInflater.inflate(R.layout.dialog_return_transaction, null)
                val recyclerView = dialogView.findViewById<RecyclerView>(R.id.returnItemsRecyclerView)
                val remarksEditText = dialogView.findViewById<TextInputEditText>(R.id.remarksEditText)
                val returnButton = dialogView.findViewById<Button>(R.id.returnButton)
                val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
                val warningTextView = dialogView.findViewById<TextView>(R.id.warningTextView)

                // Find UI elements
                val selectAllButton = dialogView.findViewById<Button>(R.id.selectAllButton)
                val deselectAllButton = dialogView.findViewById<Button>(R.id.deselectAllButton)
                val selectionCountTextView = dialogView.findViewById<TextView>(R.id.selectionCountTextView)

                // Apply mobile-specific styling
                if (isMobileLayout) {
                    remarksEditText.textSize = 12f
                    returnButton.textSize = 11f
                    cancelButton.textSize = 11f
                    selectAllButton?.textSize = 10f
                    deselectAllButton?.textSize = 10f
                    selectionCountTextView?.textSize = 12f
                    warningTextView.textSize = 11f

                    // Adjust padding for mobile
                    val paddingDp = (12 * resources.displayMetrics.density).toInt()
                    dialogView.setPadding(paddingDp, paddingDp/2, paddingDp, paddingDp/2)
                }

                // Check for partial payment
                val hasPartialPayment = returnable.any { it.partialPaymentAmount > 0 }

                // Show warning if partial payment exists
                if (hasPartialPayment) {
                    warningTextView.visibility = View.VISIBLE
                    val partialAmount = returnable.sumOf { it.partialPaymentAmount }
                    warningTextView.text = "⚠️ This transaction has a partial payment of ₱${String.format("%.2f", partialAmount)}. All items must be returned together."
                } else {
                    warningTextView.visibility = View.GONE
                }

                // Show summary of available vs returned items
                val totalItems = items.size
                val returnedItems = items.size - returnable.size
                if (returnedItems > 0) {
                    val summaryText = "Items available for return: ${returnable.size} of $totalItems (${returnedItems} already returned)"
                    warningTextView.text = if (hasPartialPayment) {
                        "${warningTextView.text}\n\n$summaryText"
                    } else {
                        summaryText
                    }
                    warningTextView.visibility = View.VISIBLE
                }

                // Declare adapter variable first with lateinit
                lateinit var returnItemsAdapter: TransactionItemsAdapter

                // Create adapter
                returnItemsAdapter = TransactionItemsAdapter(
                    items = returnable.toMutableList(), // ← Make sure this is 'returnable', not 'items'
//                    isMobileLayout = isMobileLayout, // Pass mobile layout flag
                    onItemSelected = { item, isSelected ->
                        updateReturnButtonState(returnButton, returnItemsAdapter, remarksEditText)
                        selectionCountTextView?.let { updateSelectionCount(it, returnItemsAdapter) }
                    },
                    onSelectionChanged = { count ->
                        selectionCountTextView?.let { updateSelectionCount(it, returnItemsAdapter) }
                        updateReturnButtonState(returnButton, returnItemsAdapter, remarksEditText)

                        if (selectAllButton != null && deselectAllButton != null) {
                            updateSelectButtons(selectAllButton, deselectAllButton, returnItemsAdapter)
                        }
                    }
                )

                recyclerView.layoutManager = LinearLayoutManager(this@Window1)
                recyclerView.adapter = returnItemsAdapter

                // Set up selection control button listeners
                selectAllButton?.setOnClickListener {
                    returnItemsAdapter.selectAllItems(true)
                }

                deselectAllButton?.setOnClickListener {
                    returnItemsAdapter.selectAllItems(false)
                }

                // Add remarks text watcher for validation
                remarksEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        updateReturnButtonState(returnButton, returnItemsAdapter, remarksEditText)
                    }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })

                // Initialize UI state
                selectionCountTextView?.let { updateSelectionCount(it, returnItemsAdapter) }
                if (selectAllButton != null && deselectAllButton != null) {
                    updateSelectButtons(selectAllButton, deselectAllButton, returnItemsAdapter)
                }
                updateReturnButtonState(returnButton, returnItemsAdapter, remarksEditText)

                val titleView = TextView(this@Window1)
//                titleView.text = "Return Transaction"
                titleView.textSize = if (isMobileLayout) 16f else 18f
                titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
                titleView.setPadding(
                    if (isMobileLayout) 50 else 24,  // left
                    if (isMobileLayout) 50 else 20,  // top
                    if (isMobileLayout) 16 else 24,  // right
                    if (isMobileLayout) 8 else 12    // bottom
                )
                titleView.gravity = Gravity.CENTER_VERTICAL
                titleView.setTypeface(null, Typeface.BOLD)

                returnDialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//                    .setCustomTitle(titleView)
                    .setView(dialogView)
                    .setCancelable(false)
                    .create()

                returnDialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

                returnButton.setOnClickListener {
                    processReturn(
                        returnDialog!!,
                        transaction,
                        returnItemsAdapter,
                        remarksEditText,
                        returnButton
                    )
                }

                cancelButton.setOnClickListener {
                    returnDialog?.dismiss()
                }

                returnDialog?.show()

                // Apply mobile styling after dialog is shown
                returnDialog?.let { applyMobileDialogStyling(it) }

                Log.d(TAG, "Return dialog shown for transaction ${transaction.transactionId}")

            } catch (e: Exception) {
                Log.e(TAG, "Error loading transaction items", e)
                Toast.makeText(
                    this@Window1,
                    "Error loading transaction items: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

// ADDITIONAL FIX: Make sure the return processing properly marks items as returned
// Add this to your processActualReturn function after database operations:

    private fun markItemsAsReturned(originalItems: List<TransactionRecord>, returnedItemIds: List<String>) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Update the original transaction items to mark them as returned
                val updatedItems = originalItems.map { item ->
                    val itemKey = "${item.itemId}_${item.lineNum}"
                    if (returnedItemIds.contains(itemKey)) {
                        item.copy(
                            isReturned = true,
                            returnTransactionId = "RETURNED", // Or actual return transaction ID
                            returnQuantity = item.quantity.toDouble()
                        )
                    } else {
                        item
                    }
                }

                // Update in database
                transactionDao.updateTransactionRecords(updatedItems)

                Log.d(TAG, "Successfully marked ${returnedItemIds.size} items as returned")

            } catch (e: Exception) {
                Log.e(TAG, "Error marking items as returned", e)
            }
        }
    }

// ENHANCED: Better TransactionItemsAdapter validation
// Add this method to your TransactionItemsAdapter class:




    // ENHANCED: Helper functions for UI state management
    private fun updateSelectionCount(textView: TextView, returnItemsAdapter: TransactionItemsAdapter) {
        val selectedCount = returnItemsAdapter.getSelectedItemCount()
        val totalCount = returnItemsAdapter.itemCount
        val selectedValue = returnItemsAdapter.getSelectedItemsValue()

        textView.text = "$selectedCount of $totalCount selected (₱${String.format("%.2f", selectedValue)})"
    }

    private fun updateSelectButtons(selectAllButton: Button, deselectAllButton: Button, returnItemsAdapter: TransactionItemsAdapter) {
        val hasSelected = returnItemsAdapter.hasSelectedItems()
        val allSelected = returnItemsAdapter.areAllSelectableItemsSelected()

        selectAllButton.isEnabled = !allSelected
        deselectAllButton.isEnabled = hasSelected
    }

    private fun updateReturnButtonState(
        button: Button,
        returnItemsAdapter: TransactionItemsAdapter,
        remarksEditText: TextInputEditText
    ) {
        val selectedItems = returnItemsAdapter.getSelectedItems()
        val remarks = remarksEditText.text.toString().trim()
        val validationError = returnItemsAdapter.validateSelection()

        val isValid = selectedItems.isNotEmpty() &&
                remarks.isNotEmpty() &&
                validationError == null

        button.isEnabled = isValid

        // Update button text based on state
        button.text = when {
            selectedItems.isEmpty() -> "SELECT ITEMS TO RETURN"
            remarks.isEmpty() -> "ENTER RETURN REASON"
            validationError != null -> "INVALID SELECTION"
            else -> "PROCESS RETURN (${selectedItems.size} items)"
        }
    }

    private fun getSelectedItems(items: List<TransactionRecord>): List<TransactionRecord> {
        return items.filter { it.isSelected }
    }

    private fun updateReturnButtonText(button: Button, selectedItems: List<TransactionRecord>) {
        button.text = "Process Return"
    }

    private fun showReturnPreviewDialog(
        originalTransaction: TransactionSummary,
        selectedItems: List<TransactionRecord>,
        remarks: String
    ) {
        lifecycleScope.launch {
            try {
                // Calculate return amounts
                val returnCalculation = calculateReturnAmounts(originalTransaction, selectedItems)

                // Generate return receipt content preview
                val returnReceiptContent = generateReturnReceiptPreview(
                    originalTransaction,
                    selectedItems,
                    returnCalculation,
                    remarks
                )

                withContext(Dispatchers.Main) {
                    val dialogView = layoutInflater.inflate(R.layout.dialog_return_preview, null)
                    val receiptTextView = dialogView.findViewById<TextView>(R.id.returnReceiptTextView)
                    val summaryContainer = dialogView.findViewById<LinearLayout>(R.id.returnSummaryContainer)
                    val confirmButton = dialogView.findViewById<Button>(R.id.confirmReturnButton)
                    val cancelButton = dialogView.findViewById<Button>(R.id.cancelReturnButton)

                    // Apply mobile-specific styling
                    if (isMobileLayout) {
                        receiptTextView.textSize = 8f
                        confirmButton.textSize = 11f
                        cancelButton.textSize = 11f

                        // Adjust padding for mobile
                        val paddingDp = (12 * resources.displayMetrics.density).toInt()
                        dialogView.setPadding(paddingDp, paddingDp/2, paddingDp, paddingDp/2)
                    }

                    // Set monospace font for receipt preview
                    receiptTextView.typeface = Typeface.MONOSPACE
                    receiptTextView.text = returnReceiptContent

                    // Add return summary
                    addReturnSummaryViews(summaryContainer, returnCalculation, selectedItems.size)

                    val titleView = TextView(this@Window1)
//                    titleView.text = "Return Transaction Preview"
                    titleView.textSize = if (isMobileLayout) 16f else 18f
                    titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
                    titleView.setPadding(
                        if (isMobileLayout) 50 else 24,  // left
                        if (isMobileLayout) 50 else 20,  // top
                        if (isMobileLayout) 16 else 24,  // right
                        if (isMobileLayout) 8 else 12    // bottom
                    )
                    titleView.gravity = Gravity.CENTER_VERTICAL
                    titleView.setTypeface(null, Typeface.BOLD)

                    val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//                        .setCustomTitle(titleView)
                        .setView(dialogView)
                        .setCancelable(true)
                        .create()

                    confirmButton.setOnClickListener {
                        dialog.dismiss()
                        // Proceed with actual return processing
                        lifecycleScope.launch {
                            try {
                                val loadingDialog = AlertDialog.Builder(this@Window1)
                                    .setMessage("Processing return...")
                                    .setCancelable(false)
                                    .create()
                                loadingDialog.show()

                                processActualReturn(originalTransaction, selectedItems, remarks)
                                loadingDialog.dismiss()

                                Toast.makeText(
                                    this@Window1,
                                    "Return processed successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@Window1,
                                    "Error processing return: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    cancelButton.setOnClickListener {
                        dialog.dismiss()
                    }

                    dialog.window?.apply {
                        setBackgroundDrawableResource(R.drawable.dialog_background)
                        setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    }

                    dialog.show()

                    // Apply mobile styling after dialog is shown
                    applyMobileDialogStyling(dialog)
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error generating return preview", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error generating return preview: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    data class ReturnCalculation(
        val returnGrossAmount: Double,
        val returnDiscountAmount: Double,
        val returnNetAmount: Double,
        val returnVatAmount: Double,
        val refundCash: Double,
        val refundCard: Double,
        val refundGCash: Double,
        val refundPayMaya: Double,
        val refundLoyaltyCard: Double,
        val refundCharge: Double,
        val refundFoodpanda: Double,
        val refundGrabfood: Double,
        val refundRepresentation: Double
    )

    private fun calculateReturnAmounts(
        originalTransaction: TransactionSummary,
        selectedItems: List<TransactionRecord>
    ): ReturnCalculation {
        var returnGrossAmount = 0.0
        var returnDiscountAmount = 0.0
        var returnNetAmount = 0.0
        var returnVatAmount = 0.0

        // Calculate amounts ONLY for selected items
        selectedItems.forEach { item ->
            // Calculate effective price
            val effectivePrice = if (item.priceOverride != null && item.priceOverride > 0.0) {
                item.priceOverride
            } else {
                item.price
            }

            // Calculate item total
            val itemTotal = effectivePrice * item.quantity

            // Calculate discount amount for this item
            val itemDiscountAmount = when (item.discountType?.uppercase()) {
                "PERCENTAGE", "PWD", "SC" -> {
                    val discountRate = item.discountRate ?: (item.lineDiscountPercentage ?: 0.0) / 100.0
                    itemTotal * discountRate
                }
                "FIXED" -> {
                    item.discountAmount ?: 0.0
                }
                "FIXEDTOTAL" -> {
                    item.discountAmount ?: 0.0
                }
                else -> 0.0
            }

            // Calculate net amount for this item
            val itemNetAmount = itemTotal - itemDiscountAmount

            // Calculate VAT for this item
            val itemVatAmount = itemNetAmount * 0.12 / 1.12

            // Add to return totals
            returnGrossAmount += itemTotal
            returnDiscountAmount += itemDiscountAmount
            returnNetAmount += itemNetAmount
            returnVatAmount += itemVatAmount
        }

        // FIXED: Calculate payment method proportions based on SELECTED ITEMS' net amount
        val paymentProportion = if (originalTransaction.netAmount > 0) {
            returnNetAmount / originalTransaction.netAmount
        } else {
            0.0
        }

        return ReturnCalculation(
            returnGrossAmount = returnGrossAmount,
            returnDiscountAmount = returnDiscountAmount,
            returnNetAmount = returnNetAmount,
            returnVatAmount = returnVatAmount,
            refundCash = if (originalTransaction.cash > 0) originalTransaction.cash * paymentProportion else 0.0,
            refundCard = if (originalTransaction.card > 0) originalTransaction.card * paymentProportion else 0.0,
            refundGCash = if (originalTransaction.gCash > 0) originalTransaction.gCash * paymentProportion else 0.0,
            refundPayMaya = if (originalTransaction.payMaya > 0) originalTransaction.payMaya * paymentProportion else 0.0,
            refundLoyaltyCard = if (originalTransaction.loyaltyCard > 0) originalTransaction.loyaltyCard * paymentProportion else 0.0,
            refundCharge = if (originalTransaction.charge > 0) originalTransaction.charge * paymentProportion else 0.0,
            refundFoodpanda = if (originalTransaction.foodpanda > 0) originalTransaction.foodpanda * paymentProportion else 0.0,
            refundGrabfood = if (originalTransaction.grabfood > 0) originalTransaction.grabfood * paymentProportion else 0.0,
            refundRepresentation = if (originalTransaction.representation > 0) originalTransaction.representation * paymentProportion else 0.0
        )
    }
    private fun generateReturnReceiptPreview(
        originalTransaction: TransactionSummary,
        selectedItems: List<TransactionRecord>,
        calculation: ReturnCalculation,
        remarks: String
    ): String {
        return buildString {
            appendLine("ELJIN CORP")
            appendLine("TIN: Your TIN Number")
            appendLine("MIN: Your MIN")
            appendLine("Store: ${originalTransaction.store}")
            appendLine("═".repeat(45))
            appendLine("RETURN RECEIPT")
            appendLine("Cashier: ${getCurrentStaff()}")
            appendLine("Date: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}")
            appendLine("Original SI#: ${originalTransaction.receiptId}")
            appendLine("═".repeat(45))
            appendLine("Item                    Price   Qty    Total")
            appendLine("═".repeat(45))

            selectedItems.forEach { item ->
                val effectivePrice = if (item.priceOverride != null && item.priceOverride > 0.0) {
                    item.priceOverride
                } else {
                    item.price
                }

                val itemTotal = effectivePrice * item.quantity

                appendLine("${item.name.take(22).padEnd(22)} ${
                    String.format("%7.2f", effectivePrice)
                } ${String.format("%3d", item.quantity)} ${String.format("%9.2f", itemTotal)}")

                // Show discounts if any
                when (item.discountType?.uppercase()) {
                    "PERCENTAGE", "PWD", "SC" -> {
                        val discountRate = item.discountRate ?: (item.lineDiscountPercentage ?: 0.0) / 100.0
                        val discountAmount = itemTotal * discountRate
                        if (discountAmount > 0) {
                            appendLine(" Disc(${item.discountType}):${String.format("%22.2f", discountAmount)}")
                        }
                    }
                    "FIXED", "FIXEDTOTAL" -> {
                        val discountAmount = item.discountAmount ?: 0.0
                        if (discountAmount > 0) {
                            appendLine(" Disc(Fixed):${String.format("%25.2f", discountAmount)}")
                        }
                    }
                }
            }

            appendLine("═".repeat(45))
            appendLine("Return Amount:${String.format("%25.2f", calculation.returnGrossAmount)}")
            if (calculation.returnDiscountAmount > 0) {
                appendLine("Less Discount:${String.format("%26.2f", calculation.returnDiscountAmount)}")
            }
            appendLine("Net Return:${String.format("%27.2f", calculation.returnNetAmount)}")
            appendLine("═".repeat(45))
            appendLine("REFUND DETAILS")
            appendLine("═".repeat(45))

            // Show refund methods
            val refundMethods = mutableListOf<Pair<String, Double>>()
            if (calculation.refundCash > 0) refundMethods.add("Cash Refund" to calculation.refundCash)
            if (calculation.refundCard > 0) refundMethods.add("Card Refund" to calculation.refundCard)
            if (calculation.refundGCash > 0) refundMethods.add("GCash Refund" to calculation.refundGCash)
            if (calculation.refundPayMaya > 0) refundMethods.add("PayMaya Refund" to calculation.refundPayMaya)
            if (calculation.refundLoyaltyCard > 0) refundMethods.add("Loyalty Refund" to calculation.refundLoyaltyCard)
            if (calculation.refundCharge > 0) refundMethods.add("Charge Refund" to calculation.refundCharge)
            if (calculation.refundFoodpanda > 0) refundMethods.add("FoodPanda Refund" to calculation.refundFoodpanda)
            if (calculation.refundGrabfood > 0) refundMethods.add("GrabFood Refund" to calculation.refundGrabfood)
            if (calculation.refundRepresentation > 0) refundMethods.add("Representation Refund" to calculation.refundRepresentation)

            if (refundMethods.size > 1) {
                appendLine("SPLIT REFUND:")
                refundMethods.forEach { (method, amount) ->
                    appendLine("${method.padEnd(25)} ${String.format("%12.2f", amount)}")
                }
                appendLine("═".repeat(45))
                appendLine("Total Refund:${String.format("%26.2f", refundMethods.sumOf { it.second })}")
            } else if (refundMethods.isNotEmpty()) {
                val (method, amount) = refundMethods.first()
                appendLine("Refund Method: ${method.replace(" Refund", "")}")
                appendLine("Refund Amount:${String.format("%26.2f", amount)}")
            }

            appendLine("═".repeat(45))
            appendLine("VATable Sales:${String.format("%26.2f", calculation.returnNetAmount / 1.12)}")
            appendLine("VAT Amount:${String.format("%29.2f", calculation.returnVatAmount)}")
            appendLine("VAT Exempt:${String.format("%29.2f", 0.0)}")
            appendLine("═".repeat(45))
            appendLine("Return Reason: $remarks")
            appendLine("═".repeat(45))
            appendLine("Valid for 5 years from PTU date")
            appendLine("POS Provider: IT WARRIORS")
        }
    }

    private fun addReturnSummaryViews(
        container: LinearLayout,
        calculation: ReturnCalculation,
        itemCount: Int
    ) {
        container.removeAllViews()

        // Items count
        addSummaryRow(container, "Items to Return:", "$itemCount items")

        // Gross amount
        addSummaryRow(container, "Gross Amount:", "₱${String.format("%.2f", calculation.returnGrossAmount)}")

        // Discount amount
        if (calculation.returnDiscountAmount > 0) {
            addSummaryRow(container, "Less Discount:", "₱${String.format("%.2f", calculation.returnDiscountAmount)}")
        }

        // Net amount
        addSummaryRow(container, "Net Return Amount:", "₱${String.format("%.2f", calculation.returnNetAmount)}", true)

        // Add divider
        val divider = View(container.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            setBackgroundColor(container.context.resources.getColor(android.R.color.darker_gray, null))
        }
        container.addView(divider)

        // Refund breakdown
        val refundMethods = mutableListOf<Pair<String, Double>>()
        if (calculation.refundCash > 0) refundMethods.add("Cash" to calculation.refundCash)
        if (calculation.refundCard > 0) refundMethods.add("Card" to calculation.refundCard)
        if (calculation.refundGCash > 0) refundMethods.add("GCash" to calculation.refundGCash)
        if (calculation.refundPayMaya > 0) refundMethods.add("PayMaya" to calculation.refundPayMaya)
        if (calculation.refundLoyaltyCard > 0) refundMethods.add("Loyalty Card" to calculation.refundLoyaltyCard)
        if (calculation.refundCharge > 0) refundMethods.add("Charge" to calculation.refundCharge)
        if (calculation.refundFoodpanda > 0) refundMethods.add("FoodPanda" to calculation.refundFoodpanda)
        if (calculation.refundGrabfood > 0) refundMethods.add("GrabFood" to calculation.refundGrabfood)
        if (calculation.refundRepresentation > 0) refundMethods.add("Representation" to calculation.refundRepresentation)

        if (refundMethods.isNotEmpty()) {
            // Refund header
            val refundHeader = TextView(container.context).apply {
                text = "Refund Breakdown:"
                textSize = 14f
                setTypeface(null, Typeface.BOLD)
                setTextColor(container.context.resources.getColor(android.R.color.black, null))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 8
                    bottomMargin = 4
                }
            }
            container.addView(refundHeader)

            refundMethods.forEach { (method, amount) ->
                addSummaryRow(container, "  $method:", "₱${String.format("%.2f", amount)}")
            }
        }
    }

    private fun addSummaryRow(
        container: LinearLayout,
        label: String,
        value: String,
        isBold: Boolean = false
    ) {
        val rowLayout = LinearLayout(container.context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 4
                bottomMargin = 4
            }
        }

        val labelTextView = TextView(container.context).apply {
            text = label
            textSize = if (isBold) 16f else 14f
            if (isBold) setTypeface(null, Typeface.BOLD)
            setTextColor(container.context.resources.getColor(android.R.color.black, null))
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val valueTextView = TextView(container.context).apply {
            text = value
            textSize = if (isBold) 16f else 14f
            if (isBold) setTypeface(null, Typeface.BOLD)
            gravity = Gravity.END
            setTextColor(
                if (isBold) {
                    container.context.resources.getColor(android.R.color.holo_red_dark, null)
                } else {
                    container.context.resources.getColor(android.R.color.black, null)
                }
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        rowLayout.addView(labelTextView)
        rowLayout.addView(valueTextView)
        container.addView(rowLayout)
    }
    private fun processReturn(
        returnDialog: AlertDialog,
        transaction: TransactionSummary,
        adapter: TransactionItemsAdapter,
        remarksEditText: TextInputEditText,
        returnButton: Button
    ) {
        val selectedItems = adapter.getSelectedItems()
        val remarks = remarksEditText.text.toString().trim()
        val validationError = adapter.validateSelection()

        when {
            validationError != null -> {
                Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show()
            }
            selectedItems.isEmpty() -> {
                Toast.makeText(this, "Please select items to return", Toast.LENGTH_SHORT).show()
            }
            remarks.isEmpty() -> {
                remarksEditText.error = "Return reason is required"
                remarksEditText.requestFocus()
            }
            else -> {
                // Show confirmation dialog before processing
                val selectedValue = adapter.getSelectedItemsValue()
                val confirmationMessage = """
                Are you sure you want to return ${selectedItems.size} item(s)?
                
                Total return value: ₱${String.format("%.2f", selectedValue)}
                Reason: $remarks
                .
            """.trimIndent()

                val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle1)
                    .setTitle("Confirm Return")
                    .setMessage(confirmationMessage)
                    .setPositiveButton("CONFIRM TO SEE PRINT PREVIEW") { _, _ ->
                        returnDialog.dismiss()
                        showReturnPreviewDialog(transaction, selectedItems, remarks)
                    }
                    .setNegativeButton("CANCEL", null)
                    .create()
                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                    dialog.show()
            }
        }
    }

    // Separate method for actual return processing
    private fun processActualReturn(
        originalTransaction: TransactionSummary,
        selectedItems: List<TransactionRecord>,
        remarks: String
    ) {
        lifecycleScope.launch {
            try {
                val currentStore = SessionManager.getCurrentUser()?.storeid
                    ?: throw IllegalStateException("No store ID found in current session")


                val returnTransactionId = numberSequenceRemoteRepository.getNextTransactionNumber(currentStore)
                val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
                val storeSequence = "$currentStore-${returnTransactionId.split("-").last()}"

                val originalTransactionItems = transactionViewModel.getTransactionItems(originalTransaction.transactionId)

                // FIXED: Calculate return amounts ONLY for selected items (not entire transaction)
                var returnGrossAmount = 0.0
                var returnTotalDiscountAmount = 0.0
                var returnNetAmount = 0.0
                var returnVatAmount = 0.0

                val returnedItems = selectedItems.map { item ->
                    val effectivePrice = if (item.priceOverride != null && item.priceOverride!! > 0.0) {
                        item.priceOverride!!
                    } else {
                        item.price
                    }

                    // Calculate amounts for THIS SPECIFIC ITEM ONLY
                    val itemTotal = effectivePrice * item.quantity

                    val itemDiscountAmount = when (item.discountType?.uppercase()) {
                        "PERCENTAGE", "PWD", "SC" -> {
                            val discountRate = item.discountRate ?: (item.lineDiscountPercentage ?: 0.0) / 100.0
                            itemTotal * discountRate
                        }
                        "FIXED" -> {
                            item.discountAmount ?: 0.0
                        }
                        "FIXEDTOTAL" -> {
                            item.discountAmount ?: 0.0
                        }
                        else -> 0.0
                    }

                    val itemNetAmount = itemTotal - itemDiscountAmount
                    val itemVatAmount = itemNetAmount * 0.12 / 1.12

                    // FIXED: Add to return totals (as NEGATIVE values for returns)
                    returnGrossAmount += -itemTotal // Negative for returns
                    returnTotalDiscountAmount += -itemDiscountAmount // Negative for returns
                    returnNetAmount += -itemNetAmount // Negative for returns
                    returnVatAmount += -itemVatAmount // Negative for returns

                    TransactionRecord(
                        transactionId = returnTransactionId,
                        name = item.name,
                        price = item.price,
                        quantity = -item.quantity, // Negative quantity
                        subtotal = -itemTotal, // Negative subtotal
                        vatRate = item.vatRate,
                        vatAmount = -itemVatAmount, // Negative VAT amount
                        discountRate = item.discountRate,
                        discountAmount = -itemDiscountAmount, // Negative discount amount
                        total = -itemNetAmount, // Negative net total
                        receiptNumber = returnTransactionId,
                        paymentMethod = originalTransaction.paymentMethod,
                        ar = 0.0,
                        comment = "Return: $remarks",
                        lineNum = item.lineNum,
                        itemId = item.itemId,
                        itemGroup = item.itemGroup,
                        netPrice = item.netPrice,
                        costAmount = item.costAmount?.let { -it }, // Negative cost amount
                        netAmount = -itemNetAmount, // Negative net amount
                        grossAmount = -itemTotal, // Negative gross amount
                        customerAccount = item.customerAccount,
                        store = item.store,
                        priceOverride = item.priceOverride,
                        staff = getCurrentStaff(),
                        discountOfferId = item.discountOfferId,
                        lineDiscountAmount = -itemDiscountAmount, // Negative line discount
                        lineDiscountPercentage = item.lineDiscountPercentage,
                        customerDiscountAmount = item.customerDiscountAmount?.let { -it },
                        taxAmount = item.taxAmount?.let { -it }, // Negative tax amount
                        isReturned = true,
                        returnTransactionId = originalTransaction.transactionId,
                        returnQuantity = item.quantity.toDouble(),
                        returnLineId = item.lineNum?.toDouble() ?: 0.0,
                        storeKey = storeKey,
                        storeSequence = storeSequence,
                        unit = item.unit,
                        unitQuantity = item.unitQuantity,
                        unitPrice = item.unitPrice,
                        createdDate = formatDateToString(Date()),
                        taxExempt = item.taxExempt,
                        currency = item.currency,
                        discountType = item.discountType,
                        netAmountNotIncludingTax = item.netAmountNotIncludingTax?.let { -it },
                        taxIncludedInPrice = -itemVatAmount
                    )
                }

                // Update original transaction items to mark them as returned
                val updatedOriginalItems = originalTransactionItems.map { originalItem ->
                    val matchingReturnItem = selectedItems.find {
                        it.itemId == originalItem.itemId &&
                                it.lineNum == originalItem.lineNum
                    }

                    if (matchingReturnItem != null) {
                        originalItem.copy(
                            isReturned = true,
                            returnTransactionId = returnTransactionId,
                            returnQuantity = matchingReturnItem.quantity.toDouble().absoluteValue
                        )
                    } else {
                        originalItem
                    }
                }

                val vatRate = 0.12
                val returnVatableSales = returnNetAmount / (1 + vatRate)

                // FIXED: Calculate cost amount ONLY for selected items
                val returnCostAmount = selectedItems.sumOf { item ->
                    (item.costAmount ?: 0.0) * item.quantity
                }

                // FIXED: Calculate payment method proportions based on SELECTED ITEMS' net amount
                val selectedItemsNetAmount = kotlin.math.abs(returnNetAmount) // Get absolute value for proportion calculation
                val paymentProportion = if (originalTransaction.netAmount > 0) {
                    selectedItemsNetAmount / originalTransaction.netAmount
                } else {
                    0.0
                }

                // FIXED: Create return transaction summary with calculated amounts for SELECTED ITEMS ONLY
                val returnTransactionSummary = TransactionSummary(
                    transactionId = returnTransactionId,
                    type = 2, // Return transaction type
                    receiptId = returnTransactionId,
                    store = originalTransaction.store,
                    staff = getCurrentStaff(),
                    storeKey = storeKey,
                    storeSequence = storeSequence,
                    customerAccount = originalTransaction.customerAccount,

                    // FIXED: Use calculated amounts for SELECTED ITEMS ONLY (all negative for returns)
                    netAmount = returnNetAmount, // Already negative, represents only selected items
                    costAmount = -kotlin.math.abs(returnCostAmount), // Only selected items' cost
                    grossAmount = returnGrossAmount, // Already negative, represents only selected items
                    partialPayment = 0.0,
                    transactionStatus = 1,
                    discountAmount = returnTotalDiscountAmount, // Already negative, only selected items
                    customerDiscountAmount = -selectedItems.sumOf { kotlin.math.abs(it.customerDiscountAmount ?: 0.0) },
                    totalDiscountAmount = returnTotalDiscountAmount, // Already negative, only selected items
                    numberOfItems = -selectedItems.sumOf { it.quantity.toDouble() }, // Only selected items count
                    refundReceiptId = originalTransaction.transactionId,
                    currency = originalTransaction.currency,
                    zReportId = null,
                    createdDate = formatDateToString(Date()),
                    priceOverride = 0.0,
                    comment = "Return: $remarks",
                    receiptEmail = null,
                    markupAmount = 0.0,
                    markupDescription = null,
                    taxIncludedInPrice = returnVatAmount, // Already negative, only selected items
                    windowNumber = originalTransaction.windowNumber,
                    paymentMethod = originalTransaction.paymentMethod,
                    customerName = originalTransaction.customerName,
                    vatAmount = returnVatAmount, // Already negative, only selected items
                    vatExemptAmount = 0.0,
                    vatableSales = returnVatableSales, // Already negative, only selected items
                    discountType = originalTransaction.discountType,
                    totalAmountPaid = returnNetAmount, // Already negative, amount to be refunded for selected items
                    changeGiven = 0.0, // No change for returns

                    // FIXED: Calculate NEGATIVE payment method amounts proportionally based on SELECTED ITEMS
                    gCash = if (originalTransaction.gCash > 0) -(originalTransaction.gCash * paymentProportion) else 0.0,
                    payMaya = if (originalTransaction.payMaya > 0) -(originalTransaction.payMaya * paymentProportion) else 0.0,
                    cash = if (originalTransaction.cash > 0) -(originalTransaction.cash * paymentProportion) else 0.0,
                    card = if (originalTransaction.card > 0) -(originalTransaction.card * paymentProportion) else 0.0,
                    loyaltyCard = if (originalTransaction.loyaltyCard > 0) -(originalTransaction.loyaltyCard * paymentProportion) else 0.0,
                    charge = if (originalTransaction.charge > 0) -(originalTransaction.charge * paymentProportion) else 0.0,
                    foodpanda = if (originalTransaction.foodpanda > 0) -(originalTransaction.foodpanda * paymentProportion) else 0.0,
                    grabfood = if (originalTransaction.grabfood > 0) -(originalTransaction.grabfood * paymentProportion) else 0.0,
                    representation = if (originalTransaction.representation > 0) -(originalTransaction.representation * paymentProportion) else 0.0,
                    syncStatus = false
                )

                // Perform database operations
                transactionDao.apply {
                    // Update original transaction items to mark returned items
                    updateTransactionRecords(updatedOriginalItems)

                    // Insert return transaction summary
                    insertTransactionSummary(returnTransactionSummary)

                    // Insert return transaction records
                    insertAll(returnedItems)

                    // Update original transaction's refund receipt ID
                    updateRefundReceiptId(originalTransaction.transactionId, returnTransactionId)
                }

                // Sync the return transaction
                transactionViewModel.syncTransaction(returnTransactionId)

                val transactionLogger = TransactionLogger(this@Window1)
                transactionLogger.logReturn(
                    returnTransaction = returnTransactionSummary,
                    originalTransaction = originalTransaction,
                    returnedItems = returnedItems,
                    remarks = remarks
                )

                // Update inventory
                updateInventory(returnedItems)

                withContext(Dispatchers.Main) {
                    printReturnReceipt(returnTransactionSummary, returnedItems, remarks)
                }

            } catch (e: Exception) {
                Log.e("ReturnTransaction", "Error processing return: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error processing return: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun processReturnTransaction(
        transaction: TransactionSummary,
        items: List<TransactionRecord>,
        remarks: String
    ) {
        lifecycleScope.launch {
            try {
                // Get current store from session
                val currentStore = SessionManager.getCurrentUser()?.storeid
                    ?: throw IllegalStateException("No store ID found in current session")

                // Generate return transaction ID using number sequence
                val returnTransactionId = numberSequenceRemoteRepository.getNextTransactionNumber(currentStore)

                // Get store key and generate store-specific sequence
                val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
                val storeSequence = "$currentStore-${returnTransactionId.split("-").last()}"

                // Get original transaction records to update
                val originalTransactionItems = transactionViewModel.getTransactionItems(transaction.transactionId)

                // Calculate return amounts based on actual items being returned WITH PROPER DISCOUNT HANDLING
                var returnGrossAmount = 0.0
                var returnTotalDiscountAmount = 0.0
                var returnNetAmount = 0.0
                var returnVatAmount = 0.0

                // Create return transaction records with proper discount calculations
                val returnedItems = items.map { item ->
                    // Calculate effective price (same as receipt logic)
                    val effectivePrice = if (item.priceOverride != null && item.priceOverride > 0.0) {
                        item.priceOverride
                    } else {
                        item.price
                    }

                    // Calculate item total (same as receipt: effectivePrice * quantity)
                    val itemTotal = effectivePrice * item.quantity

                    // Calculate discount amount for this item WITH PROPER DISCOUNT LOGIC
                    val itemDiscountAmount = when (item.discountType?.uppercase()) {
                        "PERCENTAGE", "PWD", "SC" -> {
                            // For percentage discounts, apply the discount rate to the item total
                            val discountRate = item.discountRate ?: (item.lineDiscountPercentage ?: 0.0) / 100.0
                            itemTotal * discountRate
                        }
                        "FIXED" -> {
                            // For fixed discounts, multiply discount by quantity
                            (item.discountAmount ?: 0.0)
                        }
                        "FIXEDTOTAL" -> {
                            // For fixed total discounts, use the discount amount as is
                            item.discountAmount ?: 0.0
                        }
                        else -> 0.0
                    }

                    // Calculate net amount for this item (itemTotal - discount)
                    val itemNetAmount = itemTotal - itemDiscountAmount

                    // Calculate VAT for this item
                    val itemVatAmount = itemNetAmount * 0.12 / 1.12
                    val currentDateString = formatDateToString(Date())

                    // Add to return totals (as NEGATIVE values)
                    returnGrossAmount += -itemTotal // Negative for returns
                    returnTotalDiscountAmount += -itemDiscountAmount // Negative for returns
                    returnNetAmount += -itemNetAmount // Negative for returns
                    returnVatAmount += -itemVatAmount // Negative for returns

                    TransactionRecord(
                        transactionId = returnTransactionId,
                        name = item.name,
                        price = item.price,
                        quantity = -item.quantity, // Negative quantity
                        subtotal = -itemTotal, // Negative subtotal
                        vatRate = item.vatRate,
                        vatAmount = -itemVatAmount, // Negative VAT amount
                        discountRate = item.discountRate, // Keep the rate the same
                        discountAmount = -itemDiscountAmount, // Negative discount amount
                        total = -itemNetAmount, // Negative net total
                        receiptNumber = returnTransactionId,
                        paymentMethod = transaction.paymentMethod,
                        ar = 0.0,
                        comment = "Return: $remarks",
                        lineNum = item.lineNum,
                        itemId = item.itemId,
                        itemGroup = item.itemGroup,
                        netPrice = item.netPrice,
                        costAmount = item.costAmount?.let { -it }, // Negative cost amount
                        netAmount = -itemNetAmount, // Negative net amount
                        grossAmount = -itemTotal, // Negative gross amount
                        customerAccount = item.customerAccount,
                        store = item.store,
                        priceOverride = item.priceOverride,
                        staff = getCurrentStaff(),
                        discountOfferId = item.discountOfferId,
                        lineDiscountAmount = -itemDiscountAmount, // Negative line discount
                        lineDiscountPercentage = item.lineDiscountPercentage,
                        customerDiscountAmount = item.customerDiscountAmount?.let { -it },
                        taxAmount = item.taxAmount?.let { -it }, // Negative tax amount
                        isReturned = true,
                        returnTransactionId = transaction.transactionId,
                        returnQuantity = item.quantity.toDouble(),
                        returnLineId = item.lineNum?.toDouble() ?: 0.0,
                        storeKey = storeKey,
                        storeSequence = storeSequence,
                        unit = item.unit,
                        unitQuantity = item.unitQuantity,
                        unitPrice = item.unitPrice,
                        createdDate = currentDateString,// Current date for the return
                        taxExempt = item.taxExempt,
                        currency = item.currency,
                        discountType = item.discountType,
                        netAmountNotIncludingTax = item.netAmountNotIncludingTax?.let { -it },
                        taxIncludedInPrice = -itemVatAmount
                    )
                }

                // Update original transaction items to mark them as returned
                val updatedOriginalItems = originalTransactionItems.map { originalItem ->
                    val matchingReturnItem = items.find {
                        it.itemId == originalItem.itemId &&
                                it.lineNum == originalItem.lineNum
                    }

                    if (matchingReturnItem != null) {
                        originalItem.copy(
                            isReturned = true,
                            returnTransactionId = returnTransactionId,
                            returnQuantity = matchingReturnItem.quantity.toDouble().absoluteValue
                        )
                    } else {
                        originalItem
                    }
                }

                // Calculate VAT amounts (same logic as receipt)
                val vatRate = 0.12
                val returnVatableSales = returnNetAmount / (1 + vatRate)

                // Calculate cost amount for returned items
                val returnCostAmount = items.sumOf { item ->
                    (item.costAmount ?: 0.0) * item.quantity
                }

                // Calculate payment method proportions based on net amount
                val paymentProportion = if (transaction.netAmount > 0) {
                    kotlin.math.abs(returnNetAmount) / transaction.netAmount
                } else {
                    0.0
                }

                // Create return transaction summary with calculated amounts (all negative for returns)
                val returnTransactionSummary = TransactionSummary(
                    transactionId = returnTransactionId,
                    type = 2, // Return transaction type
                    receiptId = returnTransactionId,
                    store = transaction.store,
                    staff = getCurrentStaff(),
                    storeKey = storeKey,
                    storeSequence = storeSequence,
                    customerAccount = transaction.customerAccount,
                    // Use calculated amounts for the specific items being returned (ALL NEGATIVE)
                    netAmount = returnNetAmount, // Already negative
                    costAmount = -kotlin.math.abs(returnCostAmount),
                    grossAmount = returnGrossAmount, // Already negative
                    partialPayment = 0.0,
                    transactionStatus = 1,
                    discountAmount = returnTotalDiscountAmount, // Already negative
                    customerDiscountAmount = -items.sumOf { kotlin.math.abs(it.customerDiscountAmount ?: 0.0) },
                    totalDiscountAmount = returnTotalDiscountAmount, // Already negative
                    numberOfItems = -items.sumOf { it.quantity.toDouble() },
                    refundReceiptId = transaction.transactionId,
                    currency = transaction.currency,
                    zReportId = null,
                    createdDate = formatDateToString(Date()),
                    priceOverride = 0.0,
                    comment = "Return: $remarks",
                    receiptEmail = null,
                    markupAmount = 0.0,
                    markupDescription = null,
                    taxIncludedInPrice = returnVatAmount, // Already negative
                    windowNumber = transaction.windowNumber,
                    paymentMethod = transaction.paymentMethod,
                    customerName = transaction.customerName,
                    vatAmount = returnVatAmount, // Already negative
                    vatExemptAmount = 0.0,
                    vatableSales = returnVatableSales, // Already negative
                    discountType = transaction.discountType,
                    totalAmountPaid = returnNetAmount, // Already negative (amount to be refunded)
                    changeGiven = 0.0, // No change for returns

                    // IMPORTANT: Calculate NEGATIVE payment method amounts proportionally
                    gCash = if (transaction.gCash > 0) -(transaction.gCash * paymentProportion) else 0.0,
                    payMaya = if (transaction.payMaya > 0) -(transaction.payMaya * paymentProportion) else 0.0,
                    cash = if (transaction.cash > 0) -(transaction.cash * paymentProportion) else 0.0,
                    card = if (transaction.card > 0) -(transaction.card * paymentProportion) else 0.0,
                    loyaltyCard = if (transaction.loyaltyCard > 0) -(transaction.loyaltyCard * paymentProportion) else 0.0,
                    charge = if (transaction.charge > 0) -(transaction.charge * paymentProportion) else 0.0,
                    foodpanda = if (transaction.foodpanda > 0) -(transaction.foodpanda * paymentProportion) else 0.0,
                    grabfood = if (transaction.grabfood > 0) -(transaction.grabfood * paymentProportion) else 0.0,
                    representation = if (transaction.representation > 0) -(transaction.representation * paymentProportion) else 0.0,
                    syncStatus = false
                )

                // Perform database operations
                transactionDao.apply {
                    // Update original transaction items to mark returned items
                    updateTransactionRecords(updatedOriginalItems)

                    // Insert return transaction summary
                    insertTransactionSummary(returnTransactionSummary)

                    // Insert return transaction records
                    insertAll(returnedItems)

                    // Update original transaction's refund receipt ID
                    updateRefundReceiptId(transaction.transactionId, returnTransactionId)
                }

                // Sync the return transaction
                transactionViewModel.syncTransaction(returnTransactionId)

                val transactionLogger = TransactionLogger(this@Window1)
                transactionLogger.logReturn(
                    returnTransaction = returnTransactionSummary,
                    originalTransaction = transaction,
                    returnedItems = returnedItems,
                    remarks = remarks
                )

                // Update inventory
                updateInventory(returnedItems)

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Return processed successfully with discounts applied",
                        Toast.LENGTH_SHORT
                    ).show()
                    printReturnReceipt(returnTransactionSummary, returnedItems, remarks)
                }
            } catch (e: Exception) {
                Log.e("ReturnTransaction", "Error processing return: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error processing return: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun printReturnReceipt(
    transaction: TransactionSummary,
    returnedItems: List<TransactionRecord>,
    remarks: String
) {
    lifecycleScope.launch {
        try {
            // Try to connect to printer if not already connected
            if (!bluetoothPrinterHelper.isConnected()) {
                Toast.makeText(this@Window1, "Please connect to a printer first", Toast.LENGTH_SHORT).show()
                return@launch
            }

            // Print return receipt
            if (bluetoothPrinterHelper.printReceipt(
                    transaction,
                    returnedItems,
                    BluetoothPrinterHelper.ReceiptType.RETURN
                )) {

                // Add cut command after printing
                val cutCommand = byteArrayOf(0x1D, 0x56, 0x00)
                bluetoothPrinterHelper.outputStream?.write(cutCommand)
                bluetoothPrinterHelper.outputStream?.flush()

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Window1, "Return receipt printed successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Window1, "Failed to print return receipt", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("PrintReceipt", "Error printing return receipt: ${e.message}", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@Window1,
                    "Error printing return receipt: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


    private fun updateInventory(returnItems: List<TransactionRecord>) {
        // Implement your inventory update logic here
        // This might involve increasing the stock of returned items
    }


    private fun showClearCartConfirmationDialog() {
        val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle1)
            .setTitle("Clear Cart")
            .setMessage("Are you sure you want to clear all items from the cart?")
            .setPositiveButton("Yes") { _, _ ->
                clearCart()
            }
            .setNegativeButton("No", null)
            .create()

        // Apply the custom background
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        dialog.show()
    }

    private fun clearCart() {
        showLoadingState(true)
        lifecycleScope.launch {
            try {
                if (partialPaymentAmount == 0.0) {
                    cartViewModel.deleteAll(windowId)
                    transactionComment = "" // Clear the comment

                    delay(100) // Ensure deletion completes

                    withContext(Dispatchers.Main) {
                        updateTotalAmountWithComment()
                        showLoadingState(false)
                        Toast.makeText(this@Window1, "Cart cleared", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        showLoadingState(false)
                        Toast.makeText(
                            this@Window1,
                            "Cannot clear cart with partial payment",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error clearing cart: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    showLoadingState(false)
                    Toast.makeText(
                        this@Window1,
                        "Error clearing cart. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    /*   private fun refreshUI() {
        productViewModel.refreshProducts()
        categoryViewModel.refreshCategories()
        productViewModel.loadAlignedProducts()
        productViewModel.selectCategory(null) // Reset category filter
    }*/


    override fun onPause() {
        super.onPause()
        // Remove the disconnectPrinter() call
    }
//    override fun onDestroy() {
////        mediaPlayer.release()
//        transactionSyncService?.stopSyncService()
//        super.onDestroy()
//        if (::refreshJob.isInitialized) {
//            refreshJob.cancel()
//        }
//        if (::mediaPlayer.isInitialized) {
//            mediaPlayer.release()
//        }
//        disconnectPrinter()
//        returnDialog?.dismiss()
//        midnightHandler?.removeCallbacks(midnightRunnable ?: return)
//        midnightHandler = null
//        midnightRunnable = null
//    }
override fun onDestroy() {
    transactionSyncService?.stopSyncService()
    super.onDestroy()

    if (::refreshJob.isInitialized) {
        refreshJob.cancel()
    }

    if (::mediaPlayer.isInitialized) {
        try {
            mediaPlayer.stop()
            mediaPlayer.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error releasing mediaPlayer", e)
        }
    }

    disconnectPrinter()
    returnDialog?.dismiss()
    midnightHandler?.removeCallbacks(midnightRunnable ?: return)
    midnightHandler = null
    midnightRunnable = null
}



    private fun setupSearchView() {
        searchView = binding.searchView

        // Configure SearchView appearance
        val searchEditText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText.setBackgroundResource(android.R.color.transparent)
        searchEditText.setTextColor(Color.BLACK)
        searchEditText.setHintTextColor(Color.GRAY)

        // Enable the close button (X)
        searchView.setIconifiedByDefault(false)
        val closeButton = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeButton.visibility = View.VISIBLE
        closeButton.setOnClickListener {
            searchView.setQuery("", false)
            searchView.clearFocus()
            productViewModel.clearSearch()
            hideKeyboard(searchView)
            // Reload products after clearing search
            loadWindowSpecificProducts()
        }

        // Setup suggestions adapter
        val suggestions = mutableListOf<String>()
        val searchAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            suggestions
        )

        // Handle search query changes
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                productViewModel.filterProducts(query)
                searchView.clearFocus()
                hideKeyboard(searchView)
                // Reload products with new search
                loadWindowSpecificProducts()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    closeButton.visibility = View.GONE
                    productViewModel.clearSearch()
                } else {
                    closeButton.visibility = View.VISIBLE
                    productViewModel.filterProducts(newText)
                }
                // Reload products with current search
                loadWindowSpecificProducts()
                return true
            }
        })

        // Update suggestions when products change
        productViewModel.visibleProducts.observe(this) { products ->
            val newSuggestions = products.mapNotNull { product ->
                listOfNotNull(
                    product.itemName.takeIf { it.isNotBlank() },
                    product.itemGroup.takeIf { it.isNotBlank() },
                    product.barcode.toString().takeIf { it != "0" }
                )
            }.flatten().distinct()

            searchAdapter.clear()
            searchAdapter.addAll(newSuggestions)
        }
    }
//    private fun hideKeyboard(view: View) {
//        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//        imm?.hideSoftInputFromWindow(view.windowToken, 0)
//    }
    private fun filterProductsForCurrentWindow(query: String?) {
        lifecycleScope.launch {
            try {
                val window = windowViewModel.allWindows.first().find { it.id == windowId }
                if (window != null) {
                    val description = window.description.uppercase()
                    val productsToFilter = if (productViewModel.isSearchActive.value) {
                        productViewModel.currentSearchResults.value
                    } else {
                        productViewModel.visibleProducts.value ?: emptyList()
                    }


                    // First filter by window type
                    val windowFilteredProducts = when {
                        description.contains("GRABFOOD") -> {
                            productsToFilter.filter { it.grabfood > 0 }
                                .map { it.copy(price = it.grabfood) }
                        }
                        description.contains("FOODPANDA") -> {
                            productsToFilter.filter { it.foodpanda > 0 }
                                .map { it.copy(price = it.foodpanda) }
                        }
                        description.contains("MANILARATE") -> {
                            productsToFilter.filter { it.manilaprice > 0 }
                                .map { it.copy(price = it.manilaprice) }
                        }
                        description.contains("PARTYCAKES") -> {
                            productsToFilter.filter {
                                it.itemGroup.equals("PARTY CAKES", ignoreCase = true)
                            }
                        }
                        description.contains("PURCHASE") -> {
                            productsToFilter.filter {
                                it.price > 0 && it.grabfood == 0.0 &&
                                        it.foodpanda == 0.0 && it.manilaprice == 0.0
                            }
                        }
                        else -> productsToFilter
                    }

                    // Apply search filter using the persistent query
                    val finalProducts = if (!productViewModel.isSearchActive.value && !query.isNullOrBlank()) {
                        windowFilteredProducts.filter { product ->
                            product.itemName.contains(query, ignoreCase = true) ||
                                    product.itemGroup.contains(query, ignoreCase = true)
                        }
                    } else {
                        windowFilteredProducts
                    }

                    withContext(Dispatchers.Main) {
                        productAdapter.submitList(finalProducts)
                        updateAvailableCategories(finalProducts)
                    }
                }
            } catch (e: Exception) {
                Log.e("Window1", "Error filtering products", e)
            }
        }
    }

    private fun updateAvailableCategories(products: List<Product>) {
        val availableCategories = products
            .map { it.itemGroup.uppercase() }
            .distinct()
            .mapNotNull { itemGroup ->
                categoryViewModel.categories.value?.find {
                    it.name.uppercase() == itemGroup
                }
            }
            .sortedBy { it.name }

        val displayCategories = listOf(
            Category(-1, "All"),
            Category(-2, "Mix & Match")
        ) + availableCategories

        categoryAdapter.setCategories(displayCategories)
    }

    private fun filterProducts(query: String?) {
        productViewModel.filterProducts(query)
    }

    private fun showVoidPartialPaymentDialog() {
        if (partialPaymentApplied) {
            val titleView = TextView(this@Window1)
            titleView.text = "Void Partial Payment"
            titleView.textSize = if (isMobileLayout) 16f else 18f
            titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
            titleView.setPadding(
                if (isMobileLayout) 50 else 24,  // left
                if (isMobileLayout) 50 else 20,  // top
                if (isMobileLayout) 16 else 24,  // right
                if (isMobileLayout) 8 else 12    // bottom
            )
            titleView.gravity = Gravity.CENTER_VERTICAL
            titleView.setTypeface(null, Typeface.BOLD)

            val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
                .setCustomTitle(titleView)
                .setMessage("Are you sure you want to void the partial payment?")
                .setPositiveButton("Yes") { _, _ ->
                    voidPartialPayment()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog.show()

            // Apply mobile styling after dialog is shown
            applyMobileDialogStyling(dialog)
        } else {
            Toast.makeText(this@Window1, "No partial payment applied", Toast.LENGTH_SHORT).show()
        }
    }
//    private fun voidPartialPayment() {
//        lifecycleScope.launch {
//            try {
//                val cartItems = cartViewModel.getAllCartItems(windowId).first()
//                val removedAmount = cartItems.firstOrNull()?.partialPayment ?: 0.0
//
//                cartViewModel.resetPartialPayment(windowId)
//                partialPaymentAmount = 0.0
//                partialPaymentApplied = false
//
//                updateTotalAmount(cartItems)
//                Toast.makeText(this@Window1, "Partial payment voided", Toast.LENGTH_SHORT).show()
//
//                // Print the simplified void partial payment receipt
//                printVoidPartialPaymentReceipt(removedAmount, cartItems)
//
//                /* refreshUI()*/
//            } catch (e: Exception) {
//                Log.e(TAG, "Error voiding partial payment", e)
//                Toast.makeText(this@Window1, "Error voiding partial payment", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
private fun voidPartialPayment() {
    showLoadingState(true)
    lifecycleScope.launch {
        try {
            if (!checkSupervisorAccess()) {
                withContext(Dispatchers.Main) {
                    showLoadingState(false)
                }
                return@launch
            }

            val cartItems = cartViewModel.getAllCartItems(windowId).first()
            val removedAmount = cartItems.firstOrNull()?.partialPayment ?: 0.0

            cartViewModel.resetPartialPayment(windowId)
            partialPaymentAmount = 0.0
            partialPaymentApplied = false

            delay(100) // Ensure operations complete

            val updatedCartItems = cartViewModel.getAllCartItems(windowId).first()

            withContext(Dispatchers.Main) {
                updateTotalAmount(updatedCartItems)
                showLoadingState(false)
                Toast.makeText(this@Window1, "Partial payment voided", Toast.LENGTH_SHORT).show()
            }

            printVoidPartialPaymentReceipt(removedAmount, cartItems)
        } catch (e: Exception) {
            Log.e(TAG, "Error voiding partial payment: ${e.message}", e)
            withContext(Dispatchers.Main) {
                showLoadingState(false)
                Toast.makeText(this@Window1, "Error voiding partial payment", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

    private fun printVoidPartialPaymentReceipt(removedAmount: Double, cartItems: List<CartItem>) {
        val content = StringBuilder()
        content.append(0x1B.toChar()) // ESC
        content.append('!'.toChar())  // Select print mode
        content.append(0x01.toChar()) // Smallest text size

        // Set minimum line spacing
        content.append(0x1B.toChar()) // ESC
        content.append('3'.toChar())  // Select line spacing
        content.append(50.toChar())
        content.append("==============================\n")
        content.append("    VOID PARTIAL PAYMENT      \n")
        content.append("==============================\n")
        content.append(
            "Date: ${
                SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()).format(
                    Date()
                )
            }\n"
        )
        content.append("Window: $windowId\n")
        content.append("------------------------------\n")
        content.append("Total Removed Amount: P${String.format("%.2f", removedAmount)}\n")
        content.append("------------------------------\n")
        content.append("Products:\n")

        cartItems.forEach { item ->
            content.append("${item.productName}\n")
            content.append(
                "  ${item.quantity} x P${
                    String.format(
                        "%.2f",
                        item.price
                    )
                } = P${String.format("%.2f", item.quantity * item.price)}\n"
            )
        }

        content.append("------------------------------\n")
        content.append("Partial payment has been voided.\n")
        content.append("==============================\n")
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(0x1B.toChar()) // ESC
        content.append('!'.toChar())  // Select print mode
        content.append(0x00.toChar()) // Reset to normal size

        content.append(0x1B.toChar()) // ESC
        content.append('2'.toChar())
        // Print the receipt
        printReceiptWithBluetoothPrinter(content.toString())
    }

    private fun getWindowId() {
        windowId = intent.getIntExtra("WINDOW_ID", -1)
        Log.d(TAG, "Window ID: $windowId")
        if (windowId == -1) {
            Toast.makeText(this, "Error: No window ID provided", Toast.LENGTH_LONG).show()
            finish()
            throw IllegalStateException("No window ID provided")
        }
    }


    private fun initializeRepositories() {
        if (!::database.isInitialized) {
            database = AppDatabase.getDatabase(this)
        }

        if (!::transactionDao.isInitialized) {
            transactionDao = database.transactionDao()
        }
        if (!::tenderDeclarationDao.isInitialized) {
            tenderDeclarationDao = database.tenderDeclarationDao()
        }
        if (!::zReadDao.isInitialized) {
            zReadDao = database.zReadDao()
        }

        val cartDao = database.cartDao()
        val cartRepository = CartRepository(cartDao)

        val cashFundDao = database.cashFundDao()
        cashFundRepository = CashFundRepository(cashFundDao)

        numberSequenceRepository = NumberSequenceRepository(database.numberSequenceDao())

        val numberSequenceApi = RetrofitClient.numberSequenceApi
        val numberSequenceRemoteDao = database.numberSequenceRemoteDao()
        if (!::numberSequenceRemoteRepository.isInitialized) {
            // FIXED: Add transactionDao parameter
            numberSequenceRemoteRepository = NumberSequenceRemoteRepository(
                numberSequenceApi,
                numberSequenceRemoteDao,
                transactionDao  // ADD THIS LINE
            )
        }

        Log.d(TAG, "✅ All repositories initialized successfully")
    }
    private fun setupTransactionView() {
        val numberSequenceRepository = NumberSequenceRepository(database.numberSequenceDao())
        val transactionRepository = TransactionRepository(
            database.transactionDao(),
            numberSequenceRemoteRepository
        )
        val factory = TransactionViewModel.TransactionViewModelFactory(
            repository = transactionRepository,
            numberSequenceRemoteRepository = numberSequenceRemoteRepository
        )
        transactionViewModel = ViewModelProvider(this, factory).get(TransactionViewModel::class.java)

        transactionAdapter = TransactionAdapter { transaction ->
            showTransactionDetailsDialog(transaction)
        }
    }
    private fun initializeNumberSequence() {
        lifecycleScope.launch {
            try {
                val currentUser = SessionManager.getCurrentUser()
                val storeId = currentUser?.storeid

                if (!storeId.isNullOrEmpty()) {
                    Log.d(TAG, "Initializing number sequence for store: $storeId")

                    val result = numberSequenceRemoteRepository.initializeNumberSequence(storeId)
                    result.onSuccess {
                        Log.d(TAG, "Number sequence initialized successfully")
                        // Also sync with server
                        numberSequenceRemoteRepository.syncNumberSequenceWithServer(storeId)
                    }.onFailure { error ->
                        Log.e(TAG, "Failed to initialize number sequence", error)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error during number sequence initialization", e)
            }
        }
    }
    private fun initializeViewModels() {
        productViewModel = ViewModelProvider(
            this,
            ProductViewModel.ProductViewModelFactory(application)
        ).get(ProductViewModel::class.java)

        val cartRepository = CartRepository(database.cartDao())
        val cartViewModelFactory = CartViewModelFactory(cartRepository)
        cartViewModel = ViewModelProvider(this, cartViewModelFactory)[CartViewModel::class.java]
        cartViewModel.setCurrentWindow(windowId)

        val categoryApi = RetrofitClient.categoryApi
        val categoryDao = database.categoryDao()
        val categoryRepository = CategoryRepository(categoryApi, categoryDao)
        categoryViewModel = ViewModelProvider(
            this,
            CategoryViewModelFactory(categoryRepository)
        ).get(CategoryViewModel::class.java)

        val discountApiService = RetrofitClient.discountApiService
        val discountDao = database.discountDao()
        val discountRepository = DiscountRepository(discountApiService, discountDao)
        val discountViewModelFactory = DiscountViewModelFactory(discountRepository)
        discountViewModel = ViewModelProvider(this, discountViewModelFactory)[DiscountViewModel::class.java]

        windowViewModel = ViewModelProvider(this).get(WindowViewModel::class.java)
        productViewModel.loadAlignedProducts()

        val arApi = RetrofitClient.arApi
        val arDao = database.arDao()
        val arRepository = ARRepository(arApi, arDao)
        val arViewModelFactory = ARViewModelFactory(arRepository)
        arViewModel = ViewModelProvider(this, arViewModelFactory)[ARViewModel::class.java]

        val customerApi = RetrofitClient.customerApi
        val customerDao = database.customerDao()
        val customerRepository = CustomerRepository(customerApi, customerDao)
        val customerViewModelFactory = CustomerViewModelFactory(customerRepository)
        customerViewModel = ViewModelProvider(this, customerViewModelFactory)[CustomerViewModel::class.java]

        val numberSequenceRepository = NumberSequenceRepository(database.numberSequenceDao())
        val transactionRepository = TransactionRepository(
            transactionDao,
            numberSequenceRemoteRepository
        )

        val factory = TransactionViewModel.TransactionViewModelFactory(
            repository = transactionRepository,
            numberSequenceRemoteRepository = numberSequenceRemoteRepository
        )
        transactionViewModel = ViewModelProvider(this, factory)[TransactionViewModel::class.java]

        transactionViewModel.syncStatus.observe(this@Window1) { result ->
            result.fold(
                onSuccess = { response ->
                    Toast.makeText(
                        this@Window1,
                        "Transaction synced successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onFailure = { error ->
                    Log.e("Sync", "Failed to sync: ${error.message}")
                    Toast.makeText(
                        this@Window1,
                        "Failed to sync transaction: ${error.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
        }
    }

        private fun safeInitializeViewModels() {
        try {
            initializeViewModels()
        } catch (e: Exception) {
            Log.e(TAG, "Critical error initializing ViewModels", e)
            Toast.makeText(this, "Failed to initialize app components", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    private fun initializeMixMatch() {
        val mixMatchDao = database.mixMatchDao()
        val mixMatchApi = RetrofitClient.mixMatchApi  // Use the API from RetrofitClient
        val mixMatchRepository = MixMatchRepository(mixMatchDao, mixMatchApi)
        val factory = MixMatchViewModelFactory(mixMatchRepository)
        mixMatchViewModel = ViewModelProvider(this, factory)[MixMatchViewModel::class.java]

        // Observe mix match data
        lifecycleScope.launch {
            mixMatchViewModel.mixMatches.collect { mixMatches ->
                // Update UI as needed
            }
        }

        // Trigger initial refresh
        mixMatchViewModel.refreshMixMatches()
    }


//    private fun showMixMatchDialog() {
//        try {
//            val mixMatches = mixMatchViewModel.mixMatches.value
//            Log.d("MixMatch", "Available mix matches: ${mixMatches.size}")
//            mixMatches.forEach { mixMatch ->
//                Log.d(
//                    "MixMatch", """
//                ID: ${mixMatch.mixMatch.id}
//                Description: ${mixMatch.mixMatch.description}
//                Discount Type: ${mixMatch.mixMatch.discountType}
//                Discount Value: ${mixMatch.mixMatch.discountPctValue}
//                Line Groups: ${mixMatch.lineGroups.size}
//            """.trimIndent()
//                )
//            }
//            if (mixMatches.isEmpty()) {
//                Toast.makeText(this, "No mix & match offers available", Toast.LENGTH_SHORT).show()
//                return
//            }
//
//            val adapter = MixMatchAdapter(mixMatches) { selectedMixMatch ->
//                showMixMatchProductSelection(selectedMixMatch)
//            }
//
//            AlertDialog.Builder(this, R.style.CustomDialogStyle)
//                .setTitle("Mix & Match Offers")
//                .setAdapter(adapter) { dialog, _ ->
//                    dialog.dismiss()
//                }
//                .show()
//        } catch (e: Exception) {
//            Log.e("MixMatch", "Error showing dialog", e)
//            Toast.makeText(this, "Unable to show mix & match offers", Toast.LENGTH_SHORT).show()
//        }
//    }
private fun showMixMatchDialog() {
    try {
        val mixMatches = mixMatchViewModel.mixMatches.value
        Log.d("MixMatch", "Available mix matches: ${mixMatches.size}")

        if (mixMatches.isEmpty()) {
            Toast.makeText(this, "No mix & match offers available", Toast.LENGTH_SHORT).show()
            return
        }

        val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle)
            .setTitle("Mix & Match Offers")
            .create()

        val listView = ListView(this).apply {
            adapter = MixMatchAdapter(mixMatches) { selectedMixMatch ->
                dialog.dismiss()
                showMixMatchProductSelection(selectedMixMatch)
            }
            divider = null
            dividerHeight = 1
            setPadding(16, 16, 16, 16)
        }

        dialog.setView(listView)
        dialog.show()
    } catch (e: Exception) {
        Log.e("MixMatch", "Error showing dialog", e)
        Toast.makeText(this, "Unable to show mix & match offers", Toast.LENGTH_SHORT).show()
    }
}


private fun showMixMatchProductSelection(mixMatch: MixMatchWithDetails) {
    try {
        val dialogView = layoutInflater.inflate(R.layout.dialog_mix_match_product_selection, null)

        val dialog = AlertDialog.Builder(
            this,
            com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert
        )
            .setView(dialogView)
            .create()

        // Initialize views
        val titleView = dialogView.findViewById<TextView>(R.id.textViewMixMatchTitle)
        val descriptionView = dialogView.findViewById<TextView>(R.id.textViewMixMatchDescription)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerViewLineGroups)
        val applyButton = dialogView.findViewById<Button>(R.id.buttonApply)

        titleView?.text = mixMatch.mixMatch.description
        descriptionView?.text = getDiscountDescription(mixMatch.mixMatch)

        // Get ProductViewModel instance
        val productViewModel = ViewModelProvider(this, ProductViewModel.ProductViewModelFactory(application))
            .get(ProductViewModel::class.java)

        // Set up RecyclerView with ProductViewModel
        recyclerView?.layoutManager = LinearLayoutManager(this)
        val lineGroupAdapter = LineGroupAdapter(mixMatch.lineGroups, productViewModel)
        recyclerView?.adapter = lineGroupAdapter

        applyButton?.setOnClickListener {
            val selections = lineGroupAdapter.getSelections()

            if (validateSelections(mixMatch, selections)) {
                applyMixMatchToCart(mixMatch, selections, 1) // Using fixed quantity of 1
                dialog.dismiss()
            } else {
                Toast.makeText(
                    this,
                    "Please select required items for all groups",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog.show()
    } catch (e: Exception) {
        Log.e("MixMatch", "Error showing product selection dialog", e)
        Toast.makeText(this, "Unable to show product selection", Toast.LENGTH_SHORT).show()
    }
}

    private fun getDiscountDescription(mixMatch: MixMatch): String {
        return when (mixMatch.discountType) {
            0 -> "Deal Price: P${mixMatch.dealPriceValue}"
            1 -> "Discount: ${mixMatch.discountPctValue}%"
            2 -> "Discount: P${mixMatch.discountAmountValue}"
            else -> "Special Offer"
        }
    }

    private fun validateSelections(
        mixMatch: MixMatchWithDetails,
        selections: Map<Int, String>
    ): Boolean {
        return try {
            mixMatch.lineGroups.all { lineGroup ->
                val hasValidSelection = selections[lineGroup.lineGroup.id]?.let { itemIdentifier ->
                    // Match by either ID or name
                    lineGroup.discountLines.any { line ->
                        line.itemId.toString().trim() == itemIdentifier.trim() ||
                                line.itemId?.trim() == itemIdentifier.trim()
                    }
                } ?: false

                if (!hasValidSelection) {
                    Log.w(
                        "MixMatch",
                        "Invalid or missing selection for line group: ${lineGroup.lineGroup.id}"
                    )
                }

                hasValidSelection
            }
        } catch (e: Exception) {
            Log.e("MixMatch", "Error validating selections", e)
            false
        }
    }

    private fun applyMixMatchToCart(
        mixMatch: MixMatchWithDetails,
        selections: Map<Int, String>,
        bundleQuantity: Int
    ) {
        lifecycleScope.launch {
            try {
                val bundleId = System.currentTimeMillis().toInt()
                var totalBundlePrice = 0.0
                val selectedItems = mutableListOf<Triple<Product, LineGroupWithDiscounts, Int>>()

                // First pass: collect selected products
                selections.forEach { (lineGroupId, itemIdentifier) ->
                    val lineGroup = mixMatch.lineGroups.find { it.lineGroup.id == lineGroupId }
                    val discountLine = lineGroup?.discountLines?.find { line ->
                        line.itemId?.toString()?.trim() == itemIdentifier.trim()
                    }

                    if (discountLine == null) {
                        throw Exception("Discount line not found for identifier: $itemIdentifier")
                    }

                    val product = productViewModel.findProduct(discountLine.itemId?.toString())
                        ?: throw Exception("Product not found with itemId: ${discountLine.itemId}")

                    // Store lineGroup for lineNum info
                    selectedItems.add(Triple(product, lineGroup!!, discountLine.qty))
                    totalBundlePrice += product.price * discountLine.qty
                }

                // Calculate discounts based on mix match type
                val (discountPerItem, discountType) = when (mixMatch.mixMatch.discountType) {
                    0 -> { // Deal Price
                        val dealPrice = mixMatch.mixMatch.dealPriceValue
                        val totalDiscount = totalBundlePrice - dealPrice
                        val itemCount = selectedItems.sumOf { it.third }
                        Pair(totalDiscount / itemCount, "DEAL")
                    }
                    1 -> { // Percentage
                        val percentageDiscount = mixMatch.mixMatch.discountPctValue
                        Pair(percentageDiscount, "PERCENTAGE")
                    }
                    2 -> { // Fixed Total
                        val fixedDiscount = mixMatch.mixMatch.discountAmountValue
                        val itemCount = selectedItems.sumOf { it.third }
                        Pair(fixedDiscount / itemCount, "FIXEDTOTAL")
                    }
                    else -> Pair(0.0, "")
                }

                // Second pass: add items to cart with lineNum
                selectedItems.forEachIndexed { index, (product, lineGroup, qty) ->
                    val effectivePrice = product.price
                    val itemTotal = effectivePrice * qty
                    val lineNum = lineGroup.lineGroup.noOfItemsNeeded

                    val cartItem = CartItem(
                        productId = product.id,
                        productName = product.itemName,
                        price = product.price,
                        quantity = qty,
                        windowId = windowId,
                        bundleId = bundleId,
                        mixMatchId = mixMatch.mixMatch.description,
                        discountType = discountType,
                        discount = discountPerItem,
                        discountAmount = when (discountType.uppercase()) {
                            "PERCENTAGE" -> itemTotal * (discountPerItem / 100)
                            "FIXED" -> discountPerItem * qty
                            "FIXEDTOTAL" -> discountPerItem
                            else -> 0.0
                        },
                        vatAmount = (itemTotal * 0.12 / 1.12),
                        vatExemptAmount = 0.0,
                        netAmount = itemTotal,
                        bundleSelections = selections.toString(),
                        itemGroup = product.itemGroup,
                        itemId = product.itemid,
                        lineNum = lineNum  // Set the lineNum
                    )

                    cartViewModel.insert(cartItem)
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Mix & Match items added to cart",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Log.e("MixMatch", "Error applying mix match to cart", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error adding mix match items to cart: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun Any?.debugLog(tag: String, prefix: String = "") {
        Log.d(tag, "$prefix${this?.toString() ?: "null"}")
    }
    private fun setupRecyclerViews() {
        setupProductRecyclerView()
        setupCategoryRecyclerView()
        setupCartRecyclerView()
    }

//    private fun setupProductRecyclerView() {
//        productAdapter = ProductAdapter { product -> addToCart(product) }
//
//        // Calculate number of columns based on screen width and density
//        val displayMetrics = resources.displayMetrics
//        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
//
//        // Adjust column width based on screen size
//        val desiredColumnWidthDp = when {
//            screenWidthDp >= 900 -> 200f // For larger tablets
//            screenWidthDp >= 600 -> 100f // For smaller tablets
//            else -> 160f // For phones
//        }
//
//        val columnCount = (screenWidthDp / desiredColumnWidthDp).toInt().coerceIn(3,4)
//
//        binding.recyclerview.apply {
//            adapter = productAdapter
//            layoutManager = GridLayoutManager(this@Window1, columnCount)
//
//            // Add item decoration for consistent spacing
//            val spacing = (1 * resources.displayMetrics.density).toInt()
//            addItemDecoration(object : RecyclerView.ItemDecoration() {
//                override fun getItemOffsets(
//                    outRect: Rect,
//                    view: View,
//                    parent: RecyclerView,
//                    state: RecyclerView.State
//                ) {
//                    outRect.set(spacing, spacing, spacing, spacing)
//                }
//            })
//        }
//    }
    // In your Window1.kt file, update the initializeStaffStoreInfo function


    fun RecyclerView.setupOptimizedGrid(
        adapter: ProductAdapter,
        columnCount: Int,
        spacing: Int
    ) {
        this.apply {
            // Set fixed size for better performance
            setHasFixedSize(true)

            // Enable view cache
            setItemViewCacheSize(20)

            // Disable predictive animations for better performance
            itemAnimator = null

            // Set layout manager with span size lookup
            val layoutManager = GridLayoutManager(context, columnCount).apply {
                recycleChildrenOnDetach = true
            }
            this.layoutManager = layoutManager

            // Add spacing decoration
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.apply {
                        left = spacing
                        right = spacing
                        top = spacing
                        bottom = spacing
                    }
                }
            })

            // Enable prefetch
            (layoutManager as GridLayoutManager).initialPrefetchItemCount = columnCount * 4

            this.adapter = adapter
        }
    }

    // Updated setup function
    private fun setupProductRecyclerView() {
        productAdapter = ProductAdapter { product ->
            addToCart(product)
        }
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        // Adjusted column widths for better appearance
        val desiredColumnWidthDp = when {
            screenWidthDp >= 900 -> 220f // Larger tablets
            screenWidthDp >= 600 -> 180f // Smaller tablets
            else -> 170f // Phones
        }
        val columnCount = (screenWidthDp / desiredColumnWidthDp).toInt().coerceIn(2, 4)
        binding.recyclerview.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(this@Window1, columnCount)
            // Enhanced spacing decoration
            val spacing = (1 * resources.displayMetrics.density).toInt()
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.apply {
                        left = spacing
                        right = spacing
                        top = spacing
                        bottom = spacing
                    }
                }
            })
        }
    }
    private fun setupViewModel() {
//        val transactionRepository = TransactionRepository(database.transactionDao())
//        val factory = TransactionViewModel.TransactionViewModelFactory(transactionRepository)
//        transactionViewModel =
//            ViewModelProvider(this, factory).get(TransactionViewModel::class.java)
        val numberSequenceRepository = NumberSequenceRepository(database.numberSequenceDao())
        val transactionRepository = TransactionRepository(
            database.transactionDao(),
            numberSequenceRemoteRepository
        )

        val factory = TransactionViewModel.TransactionViewModelFactory(
            repository = transactionRepository,
            numberSequenceRemoteRepository = numberSequenceRemoteRepository
        )
        transactionViewModel = ViewModelProvider(this, factory).get(TransactionViewModel::class.java)

        // Fixed flow collection
        lifecycleScope.launch {
            combine(
                productViewModel.visibleProducts.asFlow(),
                categoryViewModel.categories,
                productViewModel.isLoading
            ) { products: List<Product>, categories: List<Category>, isLoading: Boolean ->
                Triple(products, categories, isLoading)
            }.collect { (products, categories, isLoading) ->
                if (!isLoading && products.isNotEmpty()) {
                    updateUI(products, categories)
                }
            }
        }
    }


    private fun updateUI(products: List<Product>, categories: List<Category>) {
        val allCategory = Category(-1, "All")
        val mixMatchCategory = Category(-2, "Mix & Match")
        val activeCategories = listOf(allCategory, mixMatchCategory) +
                categories.filter { category ->
                    products.any { product ->
                        product.itemGroup.equals(category.name, ignoreCase = true)
                    }
                }.sortedBy { it.name }

        runOnUiThread {
            categoryAdapter.setCategories(activeCategories)
            productAdapter.submitList(products)
        }
    }

    private fun setupButtons() {
        try {
            // Safe setup for viewTransactionButton
            val viewTransactionView = findViewById<View>(R.id.viewTransactionButton)

            when (viewTransactionView) {
                is Button -> {
                    viewTransactionView.setOnClickListener {
                        showTransactionListDialog()
                    }
                    Log.d(TAG, "✅ View transaction button setup as Button")
                }
                is View -> {
                    viewTransactionView.setOnClickListener {
                        showTransactionListDialog()
                    }
                    Log.d(TAG, "✅ View transaction button setup as View")
                }
                null -> {
                    Log.w(TAG, "⚠️ viewTransactionButton not found in layout")
                }
            }

            // Safe setup for Reports button
            val reportsView = findViewById<View>(R.id.Reportsbutton)
            reportsView?.setOnClickListener {
                val intent = Intent(this, ReportsActivity::class.java)
                startActivity(intent)
            }

            // Safe setup for printer indicator
            val printerIndicatorView = findViewById<ImageView>(R.id.printerIndicator)
            printerIndicatorView?.let {
                printerIndicator = it
                updatePrinterIndicator()
            }

        } catch (e: Exception) {
            Log.e(TAG, "❌ Error setting up buttons", e)
            // Don't crash the app, just log the error
        }
    }
    private fun updateCategoriesAndRecreate() {
        lifecycleScope.launch {
            val products = productViewModel.visibleProducts.value ?: emptyList()
            val categories = categoryViewModel.categories.first()

            Log.d(TAG, "Total products: ${products.size}")
            Log.d(TAG, "Total categories: ${categories.size}")

            val allCategory = Category(-1, "All")
            val validCategories = categories.filter { category ->
                val productsInCategory = products.filter { it.itemGroup == category.name }
                Log.d(TAG, "Category ${category.name}: ${productsInCategory.size} products")
                category.name != "Uncategorized" && productsInCategory.isNotEmpty()
            }

            Log.d(TAG, "Valid categories: ${validCategories.size}")

            withContext(Dispatchers.Main) {
                val categoriesToDisplay = listOf(allCategory) + validCategories
                categoryAdapter.setCategories(categoriesToDisplay)

                // Don't select any category by default
                categoryAdapter.setSelectedCategory(null)
                productViewModel.selectCategory(null)

                Log.d(TAG, "Categories set in adapter: ${categoriesToDisplay.size}")
            }

            // Instead of recreating, just refresh the product list
            withContext(Dispatchers.Main) {
                productViewModel.loadAlignedProducts()
            }
        }
    }

    private fun displayAlignedProducts(alignedProducts: Map<Category, List<Product>>) {
        categoryAdapter.setCategories(alignedProducts.keys.toList())
        // Implement additional UI updates if needed
    }


    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            Log.d("CategoryClick", "========== CATEGORY CLICKED ==========")
            Log.d("CategoryClick", "Category clicked: '${category.name}' (ID: ${category.groupId})")

            when (category.name) {
                "All" -> {
                    Log.d("CategoryClick", "Loading all products for window")
                    loadWindowSpecificProducts()
                }
                "Mix & Match" -> {
                    Log.d("CategoryClick", "Opening Mix & Match dialog")
                    showMixMatchDialog()
                }
                else -> {
                    Log.d("CategoryClick", "Filtering by category: '${category.name}'")
                    filterProductsByWindowAndCategory(category)
                }
            }
            Log.d("CategoryClick", "========================================")
        }

        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@Window1, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        // Observe categories and products
        lifecycleScope.launch {
            combine(
                productViewModel.visibleProducts.asFlow(),
                categoryViewModel.categories,
                windowViewModel.allWindows
            ) { products, categories, windows ->
                Triple(products, categories, windows)
            }.collect { (products, categories, windows) ->
                val currentWindow = windows.find { it.id == windowId }
                if (currentWindow != null) {
                    updateCategoriesForWindow(currentWindow, categories, products)
                }
            }
        }
    }
    private fun filterProductsByWindowAndCategory(category: Category) {
        lifecycleScope.launch {
            try {
                Log.d("CategoryFilter", "======= FILTERING BY CATEGORY =======")
                Log.d("CategoryFilter", "Target category: '${category.name}'")

                val window = windowViewModel.allWindows.first().find { it.id == windowId }
                if (window != null) {
                    val description = window.description.uppercase()
                    Log.d("CategoryFilter", "Window description: '$description'")

                    // Get all products first
                    val allProducts = productViewModel.visibleProducts.value ?: emptyList()
                    Log.d("CategoryFilter", "STEP 1 - All products count: ${allProducts.size}")

                    // Show first few products to see their categories
                    allProducts.take(5).forEach { product ->
                        Log.d("CategoryFilter", "Sample product: '${product.itemName}' -> category: '${product.itemGroup}'")
                    }

                    // First filter by window type
                    val windowFilteredProducts = getWindowFilteredProducts(allProducts, description)
                    Log.d("CategoryFilter", "STEP 2 - After window filtering: ${windowFilteredProducts.size} products")

                    // Show categories of window-filtered products
                    val windowCategories = windowFilteredProducts.map { it.itemGroup }.distinct()
                    Log.d("CategoryFilter", "Available categories in window: ${windowCategories.joinToString(", ") { "'$it'" }}")

                    // Then filter by category with detailed logging
                    val categoryFilteredProducts = windowFilteredProducts.filter { product ->
                        val productCategory = product.itemGroup
                        val targetCategory = category.name

                        val matches = when (targetCategory) {
                            "All" -> {
                                Log.d("CategoryFilter", "Product '${product.itemName}': ALL category -> MATCH")
                                true
                            }
                            "Uncategorized" -> {
                                val knownCategories = windowFilteredProducts
                                    .map { it.itemGroup.uppercase() }
                                    .distinct()

                                val isUncategorized = knownCategories.none { cat ->
                                    productCategory.equals(cat, ignoreCase = true)
                                }
                                Log.d("CategoryFilter", "Product '${product.itemName}': Uncategorized check -> ${if (isUncategorized) "MATCH" else "NO MATCH"}")
                                isUncategorized
                            }
                            else -> {
                                // Try multiple matching strategies
                                val exactMatch = productCategory.equals(targetCategory, ignoreCase = true)
                                val containsMatch = productCategory.contains(targetCategory, ignoreCase = true)
                                val reverseContainsMatch = targetCategory.contains(productCategory, ignoreCase = true)

                                Log.d("CategoryFilter", "Product '${product.itemName}':")
                                Log.d("CategoryFilter", "  Product category: '$productCategory'")
                                Log.d("CategoryFilter", "  Target category: '$targetCategory'")
                                Log.d("CategoryFilter", "  Exact match: $exactMatch")
                                Log.d("CategoryFilter", "  Contains match: $containsMatch")
                                Log.d("CategoryFilter", "  Reverse contains: $reverseContainsMatch")

                                val finalMatch = exactMatch || containsMatch
                                Log.d("CategoryFilter", "  Final result: ${if (finalMatch) "MATCH" else "NO MATCH"}")

                                finalMatch
                            }
                        }

                        matches
                    }

                    Log.d("CategoryFilter", "STEP 3 - After category filtering: ${categoryFilteredProducts.size} products")

                    // Show which products made it through
                    if (categoryFilteredProducts.isEmpty()) {
                        Log.w("CategoryFilter", "❌ NO PRODUCTS FOUND for category '${category.name}'!")
                        Log.w("CategoryFilter", "Available products in window were:")
                        windowFilteredProducts.take(10).forEach { product ->
                            Log.w("CategoryFilter", "  - '${product.itemName}' (category: '${product.itemGroup}')")
                        }
                    } else {
                        Log.d("CategoryFilter", "✅ Found ${categoryFilteredProducts.size} products:")
                        categoryFilteredProducts.take(5).forEach { product ->
                            Log.d("CategoryFilter", "  - '${product.itemName}' (category: '${product.itemGroup}')")
                        }
                    }

                    withContext(Dispatchers.Main) {
                        productAdapter.submitList(categoryFilteredProducts)
                        findViewById<TextView>(R.id.textView3)?.text =
                            "Products (${categoryFilteredProducts.size})"
                        Log.d("CategoryFilter", "✅ UI updated with ${categoryFilteredProducts.size} products")
                    }
                } else {
                    Log.e("CategoryFilter", "❌ Window not found with ID: $windowId")
                }
                Log.d("CategoryFilter", "=====================================")
            } catch (e: Exception) {
                Log.e("CategoryFilter", "❌ Error filtering products by category", e)
            }
        }
    }
    private fun getWindowFilteredProducts(allProducts: List<Product>, description: String): List<Product> {
        Log.d("WindowFilter", "Filtering ${allProducts.size} products for window: '$description'")

        val filtered = when {
            description.contains("GRABFOOD") -> {
                val result = allProducts.filter { it.grabfood > 0 }
                    .map { it.copy(price = it.grabfood) }
                Log.d("WindowFilter", "GRABFOOD filter: ${result.size} products (grabfood > 0)")
                result
            }
            description.contains("FOODPANDA") -> {
                val result = allProducts.filter { it.foodpanda > 0 }
                    .map { it.copy(price = it.foodpanda) }
                Log.d("WindowFilter", "FOODPANDA filter: ${result.size} products (foodpanda > 0)")
                result
            }
            description.contains("MANILARATE") -> {
                val result = allProducts.filter { it.manilaprice > 0 }
                    .map { it.copy(price = it.manilaprice) }
                Log.d("WindowFilter", "MANILARATE filter: ${result.size} products (manilaprice > 0)")
                result
            }
            description.contains("MALLPRICE") -> {
                val result = allProducts.filter { it.mallprice > 0 }
                    .map { it.copy(price = it.mallprice) }
                Log.d("WindowFilter", "MALLPRICE filter: ${result.size} products (mallprice > 0)")
                result
            }
            description.contains("GRABFOODMALL") -> {
                val result = allProducts.filter { it.grabfoodmall > 0 }
                    .map { it.copy(price = it.grabfoodmall) }
                Log.d("WindowFilter", "GRABFOODMALL filter: ${result.size} products (grabfoodmall > 0)")
                result
            }
            description.contains("FOODPANDAMALL") -> {
                val result = allProducts.filter { it.foodpandamall > 0 }
                    .map { it.copy(price = it.foodpandamall) }
                Log.d("WindowFilter", "FOODPANDAMALL filter: ${result.size} products (foodpandamall > 0)")
                result
            }
            description.contains("PARTYCAKES") -> {
                val result = allProducts.filter {
                    it.itemGroup.equals("PARTY CAKES", ignoreCase = true)
                }
                Log.d("WindowFilter", "PARTYCAKES filter: ${result.size} products (itemGroup = 'PARTY CAKES')")
                result
            }
            description.contains("PURCHASE") -> {
                // Show ALL products in PURCHASE window
                val result = allProducts
                Log.d("WindowFilter", "PURCHASE filter: ${result.size} products (showing all)")
                result
            }
            else -> {
                Log.d("WindowFilter", "No specific window filter, using all ${allProducts.size} products")
                allProducts
            }
        }

        return filtered
    }

    private fun updateCategoriesForWindow(
        currentWindow: Window,
        allCategories: List<Category>,
        allProducts: List<Product>
    ) {
        val description = currentWindow.description.uppercase()
        Log.d("CategorySetup", "======= SETTING UP CATEGORIES =======")
        Log.d("CategorySetup", "Window: '$description'")

        // Filter products based on window type using the same logic
        val windowProducts = getWindowFilteredProducts(allProducts, description)
        Log.d("CategorySetup", "Window products count: ${windowProducts.size}")

        // Get unique categories from filtered products
        val categoriesInWindow = windowProducts
            .map { it.itemGroup }
            .distinct()
            .filter { it.isNotBlank() }
            .mapNotNull { itemGroup ->
                allCategories.find { it.name.equals(itemGroup, ignoreCase = true) }
            }
            .sortedBy { it.name }

        Log.d("CategorySetup", "Categories found in window products:")
        categoriesInWindow.forEach { category ->
            val count = windowProducts.count { it.itemGroup.equals(category.name, ignoreCase = true) }
            Log.d("CategorySetup", "  - '${category.name}': $count products")
        }

        // Always include "All" and "Mix & Match" categories
        val displayCategories = listOf(
            Category(-1, "All"),
            Category(-2, "Mix & Match")
        ) + categoriesInWindow

        Log.d("CategorySetup", "Final categories for display: ${displayCategories.map { it.name }}")

        // Update category adapter
        runOnUiThread {
            categoryAdapter.setCategories(displayCategories)
            Log.d("CategorySetup", "✅ Set ${displayCategories.size} categories in adapter")
        }
        Log.d("CategorySetup", "=====================================")
    }
    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

    private fun reloadCategoriesAndProducts() {
        lifecycleScope.launch {
            try {
                binding.loadingProgressBar.visibility = View.VISIBLE
                productViewModel.refreshProductsAndCategories()
                categoryViewModel.refreshCategories()
            } catch (e: Exception) {
                Log.e(TAG, "Error reloading categories and products", e)
                Toast.makeText(
                    this@Window1,
                    "Error reloading data: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                binding.loadingProgressBar.visibility = View.GONE
            }
        }
    }

    private fun observeCategoriesAndProducts() {
        lifecycleScope.launch {
            combine(
                productViewModel.visibleProducts.asFlow(),
                categoryViewModel.categories,
                productViewModel.isLoading
            ) { products, categories, isLoading ->
                Triple(products, categories, isLoading)
            }.collect { (products, categories, isLoading) ->
                if (!isLoading) {
                    updateUI(products, categories)
                }
            }
        }
    }


//    private fun initializeZXReadButtons() {
//        zReadButton = findViewById(R.id.zReadButton)
//        xReadButton = findViewById(R.id.xReadButton)
//
//        zReadButton.setOnClickListener { performZRead() }
//        xReadButton.setOnClickListener { performXRead() }
//
//        // Initialize lastZReadTime (you might want to store and retrieve this from SharedPreferences)
//        lastZReadTime = getLastZReadTimeFromPreferences()
//    }
    private fun initializeZXReadButtons() {
        try {
            // Use safe calls to avoid null pointer exceptions
            zReadButton = findViewById<Button>(R.id.zReadButton) ?: run {
                Log.w(TAG, "zReadButton not found in layout")
                return
            }

            xReadButton = findViewById<Button>(R.id.xReadButton) ?: run {
                Log.w(TAG, "xReadButton not found in layout")
                return
            }

            // Set click listeners
            zReadButton.setOnClickListener {
                performZRead()
            }

            xReadButton.setOnClickListener {
                performXRead()
            }
            lastZReadTime = getLastZReadTimeFromPreferences()

            Log.d(TAG, "Z/X Read buttons initialized successfully")

        } catch (e: Exception) {
            Log.e(TAG, "Error initializing Z/X Read buttons", e)
            // Continue without these buttons if they don't exist
        }
    }
    private fun getLastZReadTimeFromPreferences(): Long {
        val sharedPreferences = getSharedPreferences("ZReadPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("lastZReadTime", System.currentTimeMillis())
    }

    private fun saveLastZReadTimeToPreferences(time: Long) {
        val sharedPreferences = getSharedPreferences("ZReadPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putLong("lastZReadTime", time)
            apply()
        }
    }


//private fun performXRead() {
//    if (!checkBluetoothPermissions()) {
//        Toast.makeText(this, "Bluetooth permission is required to print X-Read", Toast.LENGTH_LONG).show()
//        return
//    }
//
//    val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle)
//        .setTitle("Confirm X-Read")
//        .setMessage("Are you sure you want to perform an X-Read?")
//        .setPositiveButton("Yes") { _, _ ->
//            lifecycleScope.launch {
//                try {
//                    // Get unprocessed transactions
//                    val transactions = transactionDao.getAllUnprocessedTransactions()
//                    val currentTenderDeclaration = tenderDeclarationDao.getLatestTenderDeclaration()
//
//                    // Always create transaction logger for BIR purposes
//                    val transactionLogger = TransactionLogger(this@Window1)
//                    transactionLogger.logXRead(
//                        transactions = transactions,
//                        tenderDeclaration = currentTenderDeclaration
//                    )
//
//                    // Always print report regardless of transactions
//                    printXReadWithBluetoothPrinter(transactions, currentTenderDeclaration)
//
//                    withContext(Dispatchers.Main) {
//                        if (transactions.isEmpty()) {
//                            Toast.makeText(
//                                this@Window1,
//                                "X-Read completed. No transactions in this period.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            Toast.makeText(
//                                this@Window1,
//                                "X-Read completed successfully",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//
//                } catch (e: Exception) {
//                    Log.e("XRead", "Error performing X-Read", e)
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "Error performing X-Read: ${e.message}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            }
//        }
//        .setNegativeButton("No") { dialog, _ ->
//            dialog.dismiss()
//        }
//        .create()
//
//    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
//    dialog.show()
//}
private fun performXRead() {
    if (!checkBluetoothPermissions()) {
        Toast.makeText(this, "Bluetooth permission is required to print X-Read", Toast.LENGTH_LONG).show()
        return
    }

    lifecycleScope.launch {
        try {
            val today = Date()
            val todayStart = Calendar.getInstance().apply {
                time = today
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            val todayEnd = Calendar.getInstance().apply {
                time = today
                set(Calendar.HOUR_OF_DAY, 23)
                set(Calendar.MINUTE, 59)
                set(Calendar.SECOND, 59)
                set(Calendar.MILLISECOND, 999)
            }.time

            // FIXED: Convert Date to String for DAO call
            val transactions = transactionDao.getTransactionsByDateRange(
                formatDateToString(todayStart),
                formatDateToString(todayEnd)
            )
            val currentTenderDeclaration = tenderDeclarationDao.getLatestTenderDeclaration()
            val hasZReadToday = hasZReadForToday()

            val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
                .setTitle("Confirm X-Read")
                .setMessage(
                    if (hasZReadToday) {
                        "X-Read will show today's actual sales data.\n(Z-Read completed for today)"
                    } else {
                        "X-Read will show current sales data for today."
                    }
                )
                .setPositiveButton("Yes") { _, _ ->
                    lifecycleScope.launch {
                        try {
                            val transactionLogger = TransactionLogger(this@Window1)
                            transactionLogger.logXRead(
                                transactions = transactions,
                                tenderDeclaration = currentTenderDeclaration
                            )

                            printXReadWithBluetoothPrinter(transactions, currentTenderDeclaration)

                            withContext(Dispatchers.Main) {
                                val message = when {
                                    transactions.isEmpty() -> "X-Read completed. No transactions for today."
                                    hasZReadToday -> "X-Read completed. Showing today's actual sales (Z-Read already done)."
                                    else -> "X-Read completed successfully"
                                }

                                Toast.makeText(
                                    this@Window1,
                                    message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } catch (e: Exception) {
                            Log.e("XRead", "Error performing X-Read", e)
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@Window1,
                                    "Error performing X-Read: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog.show()

        } catch (e: Exception) {
            Log.e("XRead", "Error in X-Read setup", e)
            Toast.makeText(
                this@Window1,
                "Error setting up X-Read: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

    // Helper function to check if Z-Read exists for today
    private suspend fun hasZReadForToday(): Boolean {
        return withContext(Dispatchers.IO) {
            val currentDateString = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val existingZRead = zReadDao.getZReadByDate(currentDateString)
            existingZRead != null
        }
    }


    private fun performZRead() {
        // Check prerequisites
        if (!isPulloutCashFundProcessed || !isTenderDeclarationProcessed) {
            Toast.makeText(
                this,
                "Please complete the following steps first:\n1. Pull-out Cash Fund\n2. Tender Declaration",
                Toast.LENGTH_LONG
            ).show()
            return
        }

//        if (!checkBluetoothPermissions()) {
//            Toast.makeText(
//                this,
//                "Bluetooth permission is required to print Z-Read",
//                Toast.LENGTH_LONG
//            ).show()
//            return
//        }

        lifecycleScope.launch {
            try {
                val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
                    .setTitle("Confirm Z-Read")
                    .setMessage("Are you sure you want to perform a Z-Read? This will reset all transaction data.")
                    .setPositiveButton("Yes") { _, _ ->
                        processZRead()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()

                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()
            } catch (e: Exception) {
                Log.e("ZRead", "Error showing Z-Read dialog", e)
                Toast.makeText(
                    this@Window1,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    private fun processZRead() {
        // Check prerequisites
        if (!isPulloutCashFundProcessed || !isTenderDeclarationProcessed) {
            Toast.makeText(
                this,
                "Please complete Pull-out Cash Fund and Tender Declaration first",
                Toast.LENGTH_LONG
            ).show()
            return
        }

//        if (!checkBluetoothPermissions()) {
//            Toast.makeText(
//                this,
//                "Bluetooth permission is required to print Z-Read",
//                Toast.LENGTH_LONG
//            ).show()
//            return
//        }

        lifecycleScope.launch {
            try {
                val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
                    .setTitle("Confirm Z-Read")
                    .setMessage("Are you sure you want to perform a Z-Read? This will reset all transaction data.")
                    .setPositiveButton("Yes") { _, _ ->
                        performZReadProcess()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()

                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()
            } catch (e: Exception) {
                Log.e("ZRead", "Error showing Z-Read dialog", e)
                Toast.makeText(
                    this@Window1,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    private fun performZReadProcess() {
        lifecycleScope.launch {
            try {
                // 1. First fetch all unprocessed transactions
                val transactions = transactionDao.getAllUnprocessedTransactions()

                // 2. Check if there are transactions to process
                if (transactions.isEmpty()) {
                    Toast.makeText(
                        this@Window1,
                        "No unprocessed transactions found for Z-Read",
                        Toast.LENGTH_LONG
                    ).show()
                    return@launch
                }

                // 3. Generate Z-Report ID
                val zReportId = generateZReportId()
                val storeId = SessionManager.getCurrentUser()?.storeid ?: run {
                    Toast.makeText(this@Window1, "Store ID not found", Toast.LENGTH_LONG).show()
                    return@launch
                }

                // 4. Process the Z-Read with the already fetched transactions
                continueZReadProcess(zReportId, transactions)

                // 5. Only after successful printing, update the transactions with Z-Report ID
                val result = transactionViewModel.updateTransactionsZReport(storeId, zReportId)

                result.fold(
                    onSuccess = { response ->
                        Log.d(TAG, "Successfully updated Z-Report IDs: ${response.message}")
                    },
                    onFailure = { throwable ->
                        Log.e(TAG, "Failed to update Z-Report IDs", throwable)
                        Toast.makeText(
                            this@Window1,
                            "Error updating Z-Report IDs: ${throwable.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error in Z-Read process", e)
                Toast.makeText(
                    this@Window1,
                    "Error performing Z-Read: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    private fun continueZReadProcess(zReportId: String, transactions: List<TransactionSummary>) {
        lifecycleScope.launch {
            try {
                val totalAmount = transactions.sumOf { it.totalAmountPaid }

                // Get tender declaration
                val currentTenderDeclaration = tenderDeclarationDao.getLatestTenderDeclaration()
                if (currentTenderDeclaration == null) {
                    Toast.makeText(
                        this@Window1,
                        "Tender declaration not found. Please process tender declaration first.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@launch
                }
                // Create Z-Read record
                val zRead = ZRead(
                    zReportId = zReportId,
                    date = getCurrentDate(),
                    time = getCurrentTime(),
                    totalTransactions = transactions.size,
                    totalAmount = totalAmount
                )

                // Save Z-Read record
                zReadDao.insert(zRead)

                // Print Z-Read report
                val zReadContent = bluetoothPrinterHelper.buildReadReport(
                    transactions,
                    isZRead = true,
                    zReportId = zReportId,
                    tenderDeclaration = currentTenderDeclaration
                )

                // Create transaction logger for BIR purposes
                val transactionLogger = TransactionLogger(this@Window1)
                transactionLogger.logZRead(
                    zReportId = zReportId,
                    transactions = transactions,
                    tenderDeclaration = currentTenderDeclaration
                )

                // Print the report
                if (bluetoothPrinterHelper.printGenericReceipt(zReadContent)) {
                    Log.d("PrintZRead", "Z-Read report content sent successfully")
                    delay(1000)

                    val cutCommand = byteArrayOf(0x1D, 0x56, 0x00).toString(Charset.defaultCharset())
                    if (bluetoothPrinterHelper.printGenericReceipt(cutCommand)) {
                        Log.d("PrintZRead", "Z-Read report printed and cut successfully")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                if (transactions.isEmpty()) "Z-Read completed. No transactions to report."
                                else "Z-Read report printed successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            resetAfterZRead()
                        }
                    }
                } else {
                    Log.e("PrintZRead", "Failed to print Z-Read report")
                    Toast.makeText(
                        this@Window1,
                        "Failed to print Z-Read report",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error in Z-Read continuation process", e)
                Toast.makeText(
                    this@Window1,
                    "Error completing Z-Read: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkCashFundBeforeTransaction() {
        if (isZReadPerformed && currentCashFund <= 0) {
            showCashFundDialog()
        }
    }

    private fun resetAfterZRead() {
        lifecycleScope.launch {
            try {
                // Reset time tracking for next Z-Read cycle
                lastZReadTime = System.currentTimeMillis()
                saveLastZReadTimeToPreferences(lastZReadTime)

                // Clear tender declaration
                tenderDeclarationDao.deleteAll()
                tenderDeclaration = null
                isTenderDeclarationProcessed = false

                // Clear cash fund
                cashFundRepository.deleteAll()
                currentCashFund = 0.0

                // Reset status flags
                resetCashManagementStatus()
                isCashFundEntered = false

                withContext(Dispatchers.Main) {
                    disableTransactions()
                    Toast.makeText(
                        this@Window1,
                        "All data has been reset. Please enter new cash fund to continue.",
                        Toast.LENGTH_LONG
                    ).show()
                    showCashFundDialog()
                }

                // Set Z-Read performed flag
                isZReadPerformed = true

            } catch (e: Exception) {
                Log.e("ZRead", "Error resetting after Z-Read", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error resetting data: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun checkTenderDeclarationReset() {
        lifecycleScope.launch {
            val currentTenderDeclaration = tenderDeclarationDao.getLatestTenderDeclaration()
            if (currentTenderDeclaration == null) {
                Log.d("TenderDeclaration", "Tender declaration has been reset successfully")
            } else {
                Log.e("TenderDeclaration", "Tender declaration was not reset properly")
            }
        }
    }

private fun printXReadWithBluetoothPrinter(
    transactions: List<TransactionSummary>,
    tenderDeclaration: TenderDeclaration?
) {
    lifecycleScope.launch {
        try {
            // Try to connect to printer if not already connected
            if (!bluetoothPrinterHelper.isConnected()) {
                Toast.makeText(this@Window1, "Please connect to a printer first", Toast.LENGTH_SHORT).show()
                return@launch
            }

            Log.d("PrintXRead", "Attempting to print X-Read report")
            val xReadContent = bluetoothPrinterHelper.buildReadReport(
                transactions,
                isZRead = false,
                tenderDeclaration = tenderDeclaration
            )

            if (bluetoothPrinterHelper.printGenericReceipt(xReadContent)) {
                Log.d("PrintXRead", "X-Read report printed successfully")
                delay(1000) // Give printer time to process

                val message = if (transactions.isEmpty()) {
                    "X-Read report printed successfully (No transactions)"
                } else {
                    "X-Read report printed successfully"
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Window1, message, Toast.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Window1, "Failed to print X-Read report", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Log.e("PrintXRead", "Error printing X-Read report: ${e.message}")
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@Window1,
                    "Error printing X-Read report: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

//private fun printXReadWithBluetoothPrinter(
//    transactions: List<TransactionSummary>,
//    tenderDeclaration: TenderDeclaration?
//) {
//    try {
//        val printerMacAddress = "DC:0D:30:70:09:19"
//        if (!bluetoothPrinterHelper.isConnected()) {
//            Log.d("PrintXRead", "Attempting to connect to printer")
//            if (!bluetoothPrinterHelper.connect(printerMacAddress)) {
//                Log.e("PrintXRead", "Failed to connect to printer")
//                Toast.makeText(this, "Failed to connect to printer", Toast.LENGTH_SHORT).show()
//                return
//            }
//        }
//
//        Log.d("PrintXRead", "Attempting to print X-Read report")
//        val xReportId = "X-${System.currentTimeMillis()}"  // Generate X-Read ID
//        val xReportId = "X-${System.currentTimeMillis()}"  // Generate X-Read ID
//        val xReadContent = bluetoothPrinterHelper.buildReadReport(
//            transactions,
//            isZRead = false,
//            tenderDeclaration = tenderDeclaration
//        )
//
//        if (bluetoothPrinterHelper.printGenericReceipt(xReadContent)) {
//            // Add TransactionLogger here
//            val transactionLogger = TransactionLogger(this)
//            transactionLogger.logXRead(
////                xReportId = xReportId,
//                transactions = transactions,
//                tenderDeclaration = tenderDeclaration ?: return,
////                created = lastZReadTime,
////                endTime = System.currentTimeMillis()
//            )
//
//            Log.d("PrintXRead", "X-Read report printed successfully")
//            Thread.sleep(1000)
//            val cutCommand = ""
//            if (bluetoothPrinterHelper.printGenericReceipt(cutCommand)) {
//                Log.d("PrintXRead", "Cut command sent successfully")
//                Toast.makeText(this, "X-Read report printed and cut successfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Log.e("PrintXRead", "Failed to send cut command")
//                Toast.makeText(this, "X-Read report printed, but cutting failed", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Log.e("PrintXRead", "Failed to print X-Read report")
//            Toast.makeText(this, "Failed to print X-Read report", Toast.LENGTH_SHORT).show()
//        }
//    } catch (e: Exception) {
//        Log.e("PrintXRead", "Error printing X-Read report: ${e.message}")
//        Toast.makeText(this, "Error printing X-Read report: ${e.message}", Toast.LENGTH_LONG).show()
//    }
//}
    private fun resetCashManagementStatus() {
        isPulloutCashFundProcessed = false
        isTenderDeclarationProcessed = false
        isZReadPerformed = false
    }

    private fun getCurrentTime(): String {
        val timeZone = TimeZone.getTimeZone("Asia/Manila")
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return sdf.format(Date())
    }

        private fun showCashFundDialog() {
        lifecycleScope.launch {
            val hasZReadToday = hasZReadForToday()
            if (hasZReadToday && isAfterMidnight()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Cash fund operations are disabled. Z-Read completed for today. Please wait until next day.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return@launch
            }

            if (isCashFundEntered) {
                showCurrentCashFundStatus()
                return@launch
            }

            val dialogView = layoutInflater.inflate(R.layout.dialog_cash_fund, null)
            val editTextCashFund = dialogView.findViewById<EditText>(R.id.editTextCashFund)

            // Apply mobile-specific styling to EditText
            if (isMobileLayout) {
                editTextCashFund.textSize = 16f
                dialogView.findViewById<EditText>(R.id.editTextStatus)?.textSize = 16f
            }
            val titleView = TextView(this@Window1)
            titleView.text = "Enter Cash Fund"
            titleView.textSize = if (isMobileLayout) 16f else 18f
            titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
            titleView.setPadding(
                if (isMobileLayout) 50 else 24,  // left
                if (isMobileLayout) 50 else 20,  // top
                if (isMobileLayout) 16 else 24,  // right
                if (isMobileLayout) 8 else 12    // bottom
            )
            titleView.gravity = Gravity.CENTER_VERTICAL
            titleView.setTypeface(null, Typeface.BOLD)
            val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
                .setCustomTitle(titleView) // Use setCustomTitle instead of setTitle
                .setView(dialogView)
                .setPositiveButton("Submit") { _, _ ->
                    val cashFund = editTextCashFund.text.toString().toDoubleOrNull()
                    if (cashFund != null && cashFund > 0) {
                        currentCashFund = cashFund
                        saveCashFund(cashFund, "INITIAL")
                        if (bluetoothPrinterHelper.printCashFundReceipt(cashFund, "INITIAL")) {
                            Toast.makeText(
                                this@Window1,
                                "Cash Fund Receipt printed successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@Window1,
                                "Failed to print Cash Fund Receipt",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        isCashFundEntered = true
                        enableTransactions()
                    } else {
                        Toast.makeText(
                            this@Window1,
                            "Please enter a valid amount greater than zero",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            withContext(Dispatchers.Main) {
                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                dialog.show()

                // Apply mobile styling after dialog is shown
                applyMobileDialogStyling(dialog)
            }
        }
    }
    private fun applyMobileDialogStyling(dialog: AlertDialog) {
        if (!isMobileLayout) return

        try {
            // Adjust button text sizes and padding
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.let { button ->
                button.textSize = 12f
                button.setPadding(2, 1, 12, 1)
//                button.text = "Submit" // Keep text short for mobile
            }

            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.let { button ->
                button.textSize = 12f
                button.setPadding(2, 1, 12, 1)
                button.text = "Cancel"
            }

            // Adjust dialog title
            val titleView = dialog.findViewById<TextView>(android.R.id.title)
            titleView?.let { title ->
                title.textSize = 14f
                title.setPadding(16, 12, 16, 8)
            }

            // Adjust dialog width for mobile
            dialog.window?.let { window ->
                val layoutParams = window.attributes
                val displayMetrics = resources.displayMetrics
                layoutParams.width = (displayMetrics.widthPixels * 0.8).toInt()
                window.attributes = layoutParams
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error applying mobile dialog styling", e)
        }
    }
    private fun showCurrentCashFundStatus() {
        val titleView = TextView(this@Window1)
        titleView.text = "Cash Fund Status"
        titleView.textSize = if (isMobileLayout) 16f else 18f
        titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
        titleView.setPadding(
            if (isMobileLayout) 50 else 24,  // left
            if (isMobileLayout) 50 else 20,  // top
            if (isMobileLayout) 16 else 24,  // right
            if (isMobileLayout) 8 else 12    // bottom
        )
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTypeface(null, Typeface.BOLD)

        val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
            .setCustomTitle(titleView)
            .setMessage(
                "Current Cash Fund: ₱${
                    String.format(
                        "%.2f",
                        currentCashFund
                    )
                }\n\nYou can manage the cash fund through the Pull-out Cash Fund option."
            )
            .setPositiveButton("OK", null)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        dialog.show()

        // Apply mobile styling after dialog is shown
        applyMobileDialogStyling(dialog)
    }

    private fun enableTransactions() {
        binding.payButton.isEnabled = true
        binding.clearCartButton.isEnabled = true
        binding.insertButton.isEnabled = true
        binding.priceOverrideButton?.isEnabled = true
        binding.discountButton?.isEnabled = true
        binding.partialPaymentButton?.isEnabled = true
        binding.voidPartialPaymentButton?.isEnabled = true
    }


    private fun disableTransactions() {
        binding.payButton.isEnabled = false
        binding.clearCartButton.isEnabled = false
        binding.insertButton.isEnabled = false
        binding.priceOverrideButton?.isEnabled = false
        binding.discountButton?.isEnabled = false
        binding.partialPaymentButton?.isEnabled = false
        binding.voidPartialPaymentButton?.isEnabled = false
    }

    private fun showPulloutCashFundDialog() {
        lifecycleScope.launch {
            try {
                // Check if Z-Read has been performed today
                val hasZReadToday = hasZReadForToday()

                if (hasZReadToday) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Pull-out cash fund operations are disabled. Z-Read completed for today.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@launch
                }

                if (currentCashFund <= 0) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Window1, "No cash fund available to pull out", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                withContext(Dispatchers.Main) {
                    val titleView = TextView(this@Window1)
                    titleView.text = "Pull Out Cash Fund"
                    titleView.textSize = if (isMobileLayout) 16f else 18f
                    titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
                    titleView.setPadding(
                        if (isMobileLayout) 50 else 24,  // left
                        if (isMobileLayout) 50 else 20,  // top
                        if (isMobileLayout) 16 else 24,  // right
                        if (isMobileLayout) 8 else 12    // bottom
                    )
                    titleView.gravity = Gravity.CENTER_VERTICAL
                    titleView.setTypeface(null, Typeface.BOLD)

                    val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
                        .setCustomTitle(titleView)
                        .setMessage("Current Cash Fund: ₱${String.format("%.2f", currentCashFund)}")
                        .setPositiveButton("Pull Out") { _, _ ->
                            processPulloutCashFund(currentCashFund)
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                    dialog.show()

                    // Apply mobile styling after dialog is shown
                    applyMobileDialogStyling(dialog)
                }
            } catch (e: Exception) {
                Log.e("PulloutCashFund", "Error showing pull-out cash fund dialog", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error showing pull-out cash fund dialog: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun showTenderDeclarationDialog() {
        lifecycleScope.launch {
            try {
                // Check if Z-Read has been performed today
                val hasZReadToday = hasZReadForToday()

                if (hasZReadToday) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Tender declaration operations are disabled. Z-Read completed for today.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@launch
                }

                if (!isPulloutCashFundProcessed) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Please process pull-out cash fund first",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    return@launch
                }

                withContext(Dispatchers.Main) {
                    val dialogView = layoutInflater.inflate(R.layout.dialog_tender_declaration, null)

                    // Find all EditText views for cash denominations
                    val cashEditTexts = listOf(
                        dialogView.findViewById<EditText>(R.id.editText1000),
                        dialogView.findViewById<EditText>(R.id.editText500),
                        dialogView.findViewById<EditText>(R.id.editText200),
                        dialogView.findViewById<EditText>(R.id.editText100),
                        dialogView.findViewById<EditText>(R.id.editText50),
                        dialogView.findViewById<EditText>(R.id.editText20),
                        dialogView.findViewById<EditText>(R.id.editText10),
                        dialogView.findViewById<EditText>(R.id.editText5),
                        dialogView.findViewById<EditText>(R.id.editText1)
                    )

                    val denominations = listOf(1000, 500, 200, 100, 50, 20, 10, 5, 1)

                    val textViewTotalCash = dialogView.findViewById<TextView>(R.id.textViewTotalCash)
                    val linearLayoutArTypes = dialogView.findViewById<LinearLayout>(R.id.linearLayoutArTypes)
                    val textViewTotalAr = dialogView.findViewById<TextView>(R.id.textViewTotalAr)

                    // Just set hints instead of default text
                    cashEditTexts.forEach { editText ->
                        editText.hint = "0"
                    }

                    // Simple text watcher that just calculates total
                    cashEditTexts.forEach { editText ->
                        editText.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                                calculateTotalCash(cashEditTexts, denominations, textViewTotalCash)
                            }

                            override fun beforeTextChanged(
                                s: CharSequence?,
                                start: Int,
                                count: Int,
                                after: Int
                            ) {
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        })
                    }

                    val arEditTexts = mutableListOf<EditText>()

                    // Setup AR types - collect only once and then show dialog
                    lifecycleScope.launch {
                        try {
                            // Get AR types from ViewModel
                            arViewModel.arTypes.collect { arTypes ->
                                linearLayoutArTypes.removeAllViews()
                                arEditTexts.clear()

                                arTypes.forEach { arType ->
                                    if (arType.ar != "Cash") {
                                        addArTypeToLayout(
                                            arType.ar,
                                            linearLayoutArTypes,
                                            arEditTexts,
                                            textViewTotalAr
                                        )
                                    }
                                }
                                calculateTotalAr(arEditTexts, textViewTotalAr)

                                // Show dialog after AR types are loaded
                                val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
                                    .setTitle("Tender Declaration")
                                    .setView(dialogView)
                                    .setPositiveButton("Confirm") { _, _ ->
                                        val totalCash =
                                            textViewTotalCash.text.toString().replace("Total Cash: ₱", "").toDoubleOrNull()
                                                ?: 0.0
                                        val arAmounts = arEditTexts.associate { editText ->
                                            val arType = editText.tag as? String ?: "Unknown"
                                            val amount = editText.text.toString().toDoubleOrNull() ?: 0.0
                                            arType to amount
                                        }
                                        processTenderDeclaration(totalCash, arAmounts)
                                    }
                                    .setNegativeButton("Cancel") { dialog, _ ->
                                        dialog.dismiss()
                                    }
                                    .create()

                                dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                                dialog.show()

                                // Break out of collect after first emission
                                return@collect
                            }
                        } catch (e: Exception) {
                            Log.e("TenderDeclaration", "Error loading AR types", e)
                            Toast.makeText(
                                this@Window1,
                                "Error loading AR types: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("TenderDeclaration", "Error showing tender declaration dialog", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error showing tender declaration dialog: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun addArTypeToLayout(
        arType: String,
        layout: LinearLayout,
        editTexts: MutableList<EditText>,
        totalArTextView: TextView
    ) {
        val arTypeLayout = layoutInflater.inflate(R.layout.item_ar_type, null)
        arTypeLayout.setBackgroundColor(Color.parseColor("#FFE0E0E0"))

        val arTypeLabel = arTypeLayout.findViewById<TextView>(R.id.textViewArTypeLabel)
        val arTypeEditText = arTypeLayout.findViewById<EditText>(R.id.editTextArTypeAmount)

        arTypeLabel.text = arType
        arTypeEditText.tag = arType

        // Just set basic properties
        arTypeEditText.hint = "0.00"
        arTypeEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        arTypeEditText.setTextColor(Color.BLACK)
        arTypeEditText.setHintTextColor(Color.GRAY)

        editTexts.add(arTypeEditText)

        // Simple text watcher that just updates the total
        arTypeEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                calculateTotalAr(editTexts, totalArTextView)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Keep the decimal filter
        arTypeEditText.filters = arrayOf(DecimalDigitsInputFilter(10, 2))

        layout.addView(arTypeLayout)
    }

    private fun calculateTotalAr(editTexts: List<EditText>, textViewTotalAr: TextView) {
        val total = editTexts.sumOf {
            it.text.toString().takeIf { it.isNotEmpty() }?.toDoubleOrNull() ?: 0.0
        }
        textViewTotalAr.text = String.format("Total AR: ₱%.2f", total)
    }

    private fun calculateTotalCash(
        editTexts: List<EditText>,
        denominations: List<Int>,
        textViewTotalCash: TextView
    ) {
        var total = 0.0
        editTexts.forEachIndexed { index, editText ->
            val count = editText.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0
            total += count * denominations[index]
        }
        textViewTotalCash.text = String.format("Total Cash: ₱%.2f", total)
    }

    private class DecimalDigitsInputFilter(
        private val maxDigits: Int,
        private val decimalDigits: Int
    ) : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            val builder = StringBuilder(dest.toString())
            builder.replace(dstart, dend, source?.subSequence(start, end).toString())
            return if (!builder.toString()
                    .matches(("^\\d{0,$maxDigits}(\\.\\d{0,$decimalDigits})?$").toRegex())
            ) {
                if (source?.isEmpty() == true) dest?.subSequence(dstart, dend) else ""
            } else null
        }
    }

    private fun processTenderDeclaration(cashAmount: Double, arAmounts: Map<String, Double>) {
        if (cashAmount <= 0) {
            Toast.makeText(this, "Cash amount must be greater than zero", Toast.LENGTH_SHORT).show()
            return
        }

        if (arAmounts.isEmpty()) {
            Toast.makeText(this, "Please enter AR amounts", Toast.LENGTH_SHORT).show()
            return
        }

        val gson = Gson()
        val arAmountsJson = gson.toJson(arAmounts)

        tenderDeclaration = TenderDeclaration(
            cashAmount = cashAmount,
            arPayAmount = arAmounts.values.sum(),
            date = getCurrentDate(),
            time = getCurrentTime(),
            arAmounts = arAmountsJson
        )
        lifecycleScope.launch {
            tenderDeclarationDao.insert(tenderDeclaration!!)
        }
        isTenderDeclarationProcessed = true
        if (bluetoothPrinterHelper.printTenderDeclarationReceipt(cashAmount, arAmounts)) {
            Toast.makeText(
                this,
                "Tender Declaration Receipt printed successfully",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "Failed to print Tender Declaration Receipt", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveCashFund(cashFund: Double, status: String) {
        lifecycleScope.launch {
            val cashFundEntity =
                Cashfund(cashFund = cashFund, status = status, date = getCurrentDate())
            cashFundRepository.insert(cashFundEntity)
        }
    }

    private fun processPulloutCashFund(pulloutAmount: Double) {
        currentCashFund = 0.0
        isPulloutCashFundProcessed = true
        if (bluetoothPrinterHelper.printPulloutCashFundReceipt(pulloutAmount)) {
            Toast.makeText(
                this,
                "Pull-out Cash Fund Receipt printed successfully",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "Failed to print Pull-out Cash Fund Receipt", Toast.LENGTH_SHORT)
                .show()
        }
        // disableTransactions()
    }


    private fun getCurrentDate(): String {
        val timeZone = TimeZone.getTimeZone("Asia/Manila")
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            this.timeZone = timeZone
        }
        return sdf.format(Date())
    }

    private fun checkForExistingCashFund() {
        lifecycleScope.launch {
            try {
                val currentDate = getCurrentDate()
                val existingCashFund = cashFundRepository.getCashFundByDate(currentDate)

                if (existingCashFund != null) {
                    currentCashFund = existingCashFund.cashFund
                } else {
                    showCashFundDialog()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking for existing cash fund", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Window1, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun generateZReportId(): String {
        val sequentialNumber = getNextSequentialNumber()
        return String.format("%08d", sequentialNumber)
    }

    private fun getNextSequentialNumber(): Int {
        val sharedPreferences = getSharedPreferences("ZReadPreferences", Context.MODE_PRIVATE)
        val currentNumber = sharedPreferences.getInt("lastSequentialNumber", 0)
        val nextNumber = currentNumber + 1

        with(sharedPreferences.edit()) {
            putInt("lastSequentialNumber", nextNumber)
            apply()
        }

        return nextNumber
    }

    private fun setupCashManagementButtons() {
        binding.cashFundButton?.setOnClickListener {
            showCashFundDialog()
        }
        binding.pulloutCashFundButton?.setOnClickListener {
            if (currentCashFund > 0) {
                showPulloutCashFundDialog()
            } else {
                Toast.makeText(this, "No cash fund available to pull out", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.tenderDeclarationButton?.setOnClickListener {
            showTenderDeclarationDialog()
        }
    }

    private fun checkBluetoothPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startPrinterConnectionCheck() {
        lifecycleScope.launch {
            while (true) {
                updatePrinterIndicator()
                delay(5000) // Check every 5 seconds
            }
        }
    }
    private fun setupPrinterControls() {
        // Only update the indicator status
        updatePrinterIndicator()

        // Setup click listener for manual connection
        printerIndicator?.setOnClickListener {
            if (!bluetoothPrinterHelper.isConnected()) {
                connectToPrinter() // Manual connection when clicked
            } else {
                Toast.makeText(this, "Printer already connected", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun startPrinterStatusCheck() {
        lifecycleScope.launch {
            while (isActive) {
                updatePrinterIndicator()
                delay(5000) // Check every 5 seconds
            }
        }
    }
    private fun openPrinterSettings() {
        startActivity(Intent(this, PrinterSettingsActivity::class.java))
    }
//    private fun updatePrinterIndicator() {
//        printerIndicator?.let { indicator ->
//            val isConnected = bluetoothPrinterHelper.isConnected()
//            indicator.setColorFilter(
//                ContextCompat.getColor(
//                    this,
//                    if (isConnected) android.R.color.holo_green_light
//                    else android.R.color.holo_red_light
//                ),
//                PorterDuff.Mode.SRC_IN
//            )
//
//            // Update tooltip/content description for accessibility
//            indicator.contentDescription = if (isConnected) {
//                "Printer connected (click for status)"
//            } else {
//                "Printer disconnected (click to connect)"
//            }
//        }
//    }
private fun updatePrinterIndicator() {
    // Check both tablet and mobile layouts for printer indicator
    val tabletPrinterIndicator = findViewById<ImageView>(R.id.printerIndicator)
    val mobilePrinterIndicator = cartBottomSheet?.findViewById<ImageView>(R.id.printerIndicator)

    val indicators = listOfNotNull(tabletPrinterIndicator, mobilePrinterIndicator)

    indicators.forEach { indicator ->
        val isConnected = bluetoothPrinterHelper.isConnected()

        if (isConnected) {
            // Connected - Green color
            indicator.setColorFilter(
                ContextCompat.getColor(this, R.color.connected_green),
                PorterDuff.Mode.SRC_IN
            )
            indicator.alpha = 1.0f
        } else {
            // Disconnected - Red color
            indicator.setColorFilter(
                ContextCompat.getColor(this, R.color.disconnected_red),
                PorterDuff.Mode.SRC_IN
            )
            indicator.alpha = 0.7f
        }
    }

    Log.d(TAG, "Printer indicator updated - Connected: ${bluetoothPrinterHelper.isConnected()}")
}
    private suspend fun getLastConnectedPrinter(): PrinterSettings? {
        return withContext(Dispatchers.IO) {
            // Get all saved printers ordered by last connected date
            printerSettingsViewModel.dao.getAllPrinters().firstOrNull()?.firstOrNull { it.lastConnected != null }
        }
    }

//    private suspend fun connectToAvailablePrinter(): Boolean {
//        if (!checkBluetoothPermissions()) return false
//
//        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        val bluetoothAdapter = bluetoothManager.adapter
//
//        if (bluetoothAdapter?.isEnabled != true) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@Window1, "Please enable Bluetooth", Toast.LENGTH_SHORT).show()
//            }
//            return false
//        }
//
//        val savedPrinters = printerSettingsViewModel.allPrinters.first()
//        if (savedPrinters.isEmpty()) {
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@Window1,
//                    "No saved printers. Please add a printer in settings.",
//                    Toast.LENGTH_LONG).show()
//                // Optionally open printer settings
//                startActivity(Intent(this@Window1, PrinterSettingsActivity::class.java))
//            }
//            return false
//        }
//
//        for (printer in savedPrinters) {
//            if (bluetoothPrinterHelper.connect(printer.macAddress)) {
//                return true
//            }
//        }
//
//        return false
//    }

    //    private fun printReceiptWithBluetoothPrinter(
//        transaction: TransactionSummary,
//        items: List<TransactionRecord>,
//        receiptType: BluetoothPrinterHelper.ReceiptType,
//        isARReceipt: Boolean = false,
//        copyType: String = ""
//
//    ) {
//        if (!checkBluetoothPermissions()) {
//            Toast.makeText(this, "Bluetooth permission is required to print", Toast.LENGTH_LONG)
//                .show()
//            return
//        }
//
//        try {
//            val printerMacAddress = "DC:0D:30:70:09:0F"  // Replace with your printer's MAC address
//
//            if (!bluetoothPrinterHelper.isConnected() && !bluetoothPrinterHelper.connect(
//                    printerMacAddress
//                )
//            ) {
//                Toast.makeText(this, "Failed to connect to printer", Toast.LENGTH_SHORT).show()
//                return
//            }
//
//            val receiptContent = bluetoothPrinterHelper.generateReceiptContent(
//                transaction,
//                items,
//                receiptType,
//                isARReceipt,
//                copyType
//            )
//
//            val printSuccess = bluetoothPrinterHelper.printGenericReceipt(receiptContent)
//
//            if (printSuccess) {
//                Toast.makeText(this, "Receipt printed successfully", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Failed to print receipt", Toast.LENGTH_SHORT).show()
//            }
//
//            // Send cut command after printing
////            bluetoothPrinterHelper.cutPaper()
//
//        } catch (se: SecurityException) {
//            Log.e("PrintReceipt", "SecurityException: ${se.message}")
//            Toast.makeText(this, "Bluetooth permission denied", Toast.LENGTH_LONG).show()
//        } catch (e: Exception) {
//            Log.e("PrintReceipt", "Error printing receipt: ${e.message}")
//            Toast.makeText(this, "Error printing receipt: ${e.message}", Toast.LENGTH_LONG).show()
//        }
//    }
private fun printReceiptWithBluetoothPrinter(
    transaction: TransactionSummary,
    items: List<TransactionRecord>,
    receiptType: BluetoothPrinterHelper.ReceiptType,
    isARReceipt: Boolean = false,
    copyType: String = ""
) {
    if (!checkBluetoothPermissions()) {
        Toast.makeText(this, "Bluetooth permission is required to print", Toast.LENGTH_LONG).show()
        return
    }

    lifecycleScope.launch {
        try {
            if (!bluetoothPrinterHelper.isConnected()) {
                Toast.makeText(this@Window1, "Please connect to a printer first", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val receiptContent = bluetoothPrinterHelper.generateReceiptContent(
                transaction,
                items,
                receiptType,
                isARReceipt,
                copyType
            )

            val printSuccess = bluetoothPrinterHelper.printGenericReceipt(receiptContent)
            withContext(Dispatchers.Main) {
                if (printSuccess) {
                    Toast.makeText(this@Window1, "Receipt printed successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Window1, "Failed to print receipt", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("PrintReceipt", "Error printing receipt: ${e.message}")
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Window1, "Error printing receipt: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

    // Make sure this function is defined
    private fun sendCutCommand() {
        val cutCommand = "\u001D\u0056\u0000"  // GS V 0 for full cut
        bluetoothPrinterHelper.printGenericReceipt(cutCommand)
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            BLUETOOTH_PERMISSION_REQUEST_CODE -> {
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission was granted, you can print now
//                    // You might want to call printReceiptWithBluetoothPrinter here if it was initiated by user action
//                } else {
//                    // Permission denied, show a message to the user
//                    Toast.makeText(
//                        this,
//                        "Bluetooth permission is required to print receipts",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//                return
//            }
//        }
//    }
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    when (requestCode) {
        CAMERA_PERMISSION_REQUEST_CODE -> {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showBarcodeScannerOverlay()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required for QR scanning",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
    private fun isScannerAvailable(): Boolean {
        val scannerOverlay = findViewById<FrameLayout>(R.id.barcodeScannerOverlay)
        return scannerOverlay != null
    }
    private fun updateTotalAmountWithComment() {
        lifecycleScope.launch {
            try {
                val cartItems = cartViewModel.getAllCartItems(windowId).first()
                withContext(Dispatchers.Main) {
                    updateTotalAmount(cartItems)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error updating total amount with comment: ${e.message}", e)
            }
        }
    }

    private fun setupCartObservation() {
        lifecycleScope.launch {
            try {
                cartViewModel.currentWindowCartItems.collect { cartItems ->
                    Log.d(TAG, "Received ${cartItems.size} cart items for window $windowId")
                    withContext(Dispatchers.Main) {
                        (binding.recyclerviewcart.adapter as CartAdapter).submitList(cartItems)
                        updateTotalAmount(cartItems)
                        updateCartUI(cartItems)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in cart observation: ${e.message}", e)
            }
        }

        lifecycleScope.launch {
            try {
                cartViewModel.getAllCartItems(windowId).collect { cartItems ->
                    Log.d(TAG, "Retrieved ${cartItems.size} cart items")
                    withContext(Dispatchers.Main) {
                        (binding.recyclerviewcart.adapter as CartAdapter).submitList(cartItems)
                        updateTotalAmount(cartItems)
                        updateCartUI(cartItems)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error collecting cart items: ${e.message}", e)
            }
        }
    }

    private fun setupPartialPaymentDisplay() {
        binding.partialPaymentTextView.visibility = View.GONE
    }

    private fun observeViewModels() {
        lifecycleScope.launch {
            // Use the main alignedProducts (which now contains only visible products)
            productViewModel.alignedProducts.collect { alignedProducts ->
                displayAlignedProducts(alignedProducts)
            }
        }

        lifecycleScope.launch {
            windowViewModel.allWindows.collect { windows ->
                val currentWindow = windows.find { it.id == windowId }
                if (currentWindow != null) {
                    loadWindowSpecificProducts()
                }
            }
        }

        lifecycleScope.launch {
            productViewModel.filteredProducts.collectLatest { products ->
                productAdapter.submitList(products)
            }
        }
        lifecycleScope.launch {
            combine(
                windowViewModel.allWindows,
                productViewModel.visibleProducts.asFlow()
            ) { windows, products ->
                loadWindowSpecificProducts()
            }.collect { result ->
                // Add collector block
                // This will be called whenever loadWindowSpecificProducts() completes
                Log.d("Window1", "Window-based product filtering completed")
            }
        }
//        lifecycleScope.launch {
//            combine(
//                windowViewModel.allWindows,
//                productViewModel.visibleProducts.asFlow()
//            ) { windows, products ->
//                loadWindowSpecificProducts()
//            }.collectLatest { result ->
//                Log.d("Window1", "Window-based product filtering completed")
//            }
//        }
        lifecycleScope.launch {
            cartViewModel.currentWindowCartItems.collect { cartItems ->
                Log.d(TAG, "Received ${cartItems.size} cart items for window $windowId")
                (binding.recyclerviewcart.adapter as CartAdapter).submitList(cartItems)
                updateTotalAmount(cartItems)
                updateCartUI(cartItems)
            }
        }

//        productViewModel.visibleProducts.observe(this) { products ->
//            productAdapter.submitList(products)
//        }

        productViewModel.visibleProducts.observe(this) { products ->
            Log.d("Window1", "Visible products updated: ${products.size}")
            loadWindowSpecificProducts() // Reload with new visible products
        }

        lifecycleScope.launch {
            categoryViewModel.categories.collect { categories ->
                categoryAdapter.setCategories(categories)
            }

            lifecycleScope.launch {
                categoryViewModel.categories.collectLatest { categories ->
                    productViewModel.visibleProducts.value?.let { products ->
                        val nonEmptyCategories = categories.filter { category ->
                            products.any { it.itemGroup == category.name }
                        }
                        val allCategory = Category(-1, "All")
                        categoryAdapter.setCategories(listOf(allCategory) + nonEmptyCategories)
                    }
                }
            }

            categoryViewModel.error.collect { errorMessage ->
                errorMessage?.let {
                    Log.e(TAG, "Category Error: $it")
                    Toast.makeText(this@Window1, it, Toast.LENGTH_LONG).show()
                }
            }

            lifecycleScope.launch {
                cartViewModel.getAllCartItems(windowId).collect { cartItems ->
                    Log.d(TAG, "Retrieved ${cartItems.size} cart items")
                    (binding.recyclerviewcart.adapter as CartAdapter).submitList(cartItems)
                    updateTotalAmount(cartItems)
                    updateCartUI(cartItems)
                }
            }
        }

        lifecycleScope.launch {
            cartViewModel.totalWithVat.collect { totalWithVat ->
                binding.totalAmountTextView.text =
                    String.format("P%.2f", totalWithVat)
            }
        }

        lifecycleScope.launch {
            partialPaymentAmount = cartViewModel.getTotalPartialPayment(windowId)

        }

        /*  productViewModel.filteredProducts.observe(this) { products ->
            productAdapter.submitList(products)
        }*/

        productViewModel.operationStatus.observe(this, Observer { result ->
            result.onSuccess { products ->
                Toast.makeText(
                    this,
                    "${products.size} products refreshed successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }.onFailure { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupButtonListeners() {
        binding.payButton.setOnClickListener {
            showPaymentDialog()
        }
        binding.clearCartButton.setOnClickListener {
            showClearCartConfirmationDialog()
        }

        binding.priceOverrideButton?.setOnClickListener {
            showPriceOverrideDialog()
        }

        binding.discountButton?.setOnClickListener {
            showDiscountDialog()
        }

        binding.partialPaymentButton?.setOnClickListener {
            showPartialPaymentDialog()
        }

        binding.voidPartialPaymentButton?.setOnClickListener {
            showVoidPartialPaymentDialog()
        }

        binding.insertButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    binding.loadingProgressBar.isVisible = true

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Fetching all data from API...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    coroutineScope {
                        val productsJob = async { productViewModel.insertAllProductsFromApi() }
                        val discountsJob = async {
                            try {
                                discountViewModel.fetchDiscounts()
                            } catch (e: Exception) {
                                Log.e("Window1", "Error fetching discounts", e)
                            }
                        }
                        val arTypesJob = async {
                            try {
                                arViewModel.refreshARTypes()
                            } catch (e: Exception) {
                                Log.e("Window1", "Error fetching AR types", e)
                            }
                        }
                        val customersJob = async {
                            try {
                                customerViewModel.refreshCustomers()
                            } catch (e: Exception) {
                                Log.e("Window1", "Error fetching customers", e)
                            }
                        }
                        val mixMatchJob = async {
                            try {
                                mixMatchViewModel.refreshMixMatches()
                            } catch (e: Exception) {
                                Log.e("Window1", "Error fetching mix & match data", e)
                            }
                        }

                        val results = awaitAll(
                            productsJob,
                            discountsJob,
                            arTypesJob,
                            customersJob,
                            mixMatchJob
                        )

                        productViewModel.loadAlignedProducts()

                        val statusMessages = mutableListOf<String>()

                        if (results[0] != null) {
                            statusMessages.add("Products updated")
                        }

                        discountViewModel.discounts.value?.let {
                            statusMessages.add("Discounts updated (${it.size} items)")
                        }

                        (arViewModel.arTypes.value as? List<*>)?.let {
                            statusMessages.add("AR Types updated (${it.size} items)")
                        }

                        (customerViewModel.customers.value as? List<*>)?.let {
                            statusMessages.add("Customers updated (${it.size} items)")
                        }

                        mixMatchViewModel.mixMatches.value.let {
                            statusMessages.add("Mix & Match offers updated (${it.size} items)")
                        }

                        withContext(Dispatchers.Main) {
                            if (statusMessages.isNotEmpty()) {
                                Toast.makeText(
                                    this@Window1,
                                    statusMessages.joinToString("\n"),
                                    Toast.LENGTH_LONG
                                ).show()
                                updateCategoriesAndRecreate()
                                refreshProductAdapter()
                            } else {
                                Toast.makeText(
                                    this@Window1,
                                    "No data was updated. Please check your connection.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Window1", "Error syncing data", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } finally {
                    binding.loadingProgressBar.isVisible = false
                }
            }
        }
    }

    private fun refreshProductAdapter() {
        lifecycleScope.launch {
            // Force refresh the visible products alignment
            productViewModel.refreshVisibleProducts()

            // Wait a bit for the refresh to complete
            delay(100)

            // Then update the adapter with current visible products
            productViewModel.visibleProducts.value?.let { visibleProducts ->
                productAdapter.submitList(visibleProducts)

                // Reapply current category filter if one is selected
                productViewModel.selectedCategory.value?.let { selectedCategory ->
                    productViewModel.selectCategory(selectedCategory)
                }
            }
        }
    }
    private fun initializeProgressDialog() {
        val progressView = layoutInflater.inflate(R.layout.progress_dialog, null)
        progressDialog = AlertDialog.Builder(this)
            .setView(progressView)
            .setCancelable(false)
            .create()
    }



    private fun disconnectPrinter() {
        try {
            if (bluetoothPrinterHelper.isConnected()) {
                bluetoothPrinterHelper.disconnect()
                updatePrinterIndicator()
            }
        } catch (e: Exception) {
            Log.e("DisconnectPrinter", "Error disconnecting printer: ${e.message}")
        }
    }


    private fun disableClearCartAndItemDeletion() {
        binding.clearCartButton.isEnabled = false
        (binding.recyclerviewcart.adapter as? CartAdapter)?.setDeletionEnabled(false)
    }

    private fun enableClearCartAndItemDeletion() {
        binding.clearCartButton.isEnabled = true
        (binding.recyclerviewcart.adapter as? CartAdapter)?.setDeletionEnabled(true)
    }


    private fun showKeyboardForEditText(editText: EditText) {
        editText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

        // If the above doesn't work, try this more aggressive approach
        editText.postDelayed({
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }, 100)
    }


    private fun setupCommentButton() {
        binding.addCommentButton?.setOnClickListener {
            showAddCommentDialog()
        }
    }

    private fun setupCommentHandling() {
        lifecycleScope.launch {
            try {
                cartViewModel.getCartComment(windowId)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting cart comment: ${e.message}", e)
            }
        }

        lifecycleScope.launch {
            try {
                cartViewModel.cartComment.collect { comment ->
                    transactionComment = comment ?: "" // Update local comment to empty if null
                    withContext(Dispatchers.Main) {
                        updateTotalAmountWithComment()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error collecting cart comment: ${e.message}", e)
            }
        }
    }

    private fun showAddCommentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_comment, null)
        val commentEditText = dialogView.findViewById<EditText>(R.id.commentEditText)

        // Pre-fill existing comment if any
        commentEditText.setText(transactionComment)

        // Apply mobile-specific styling to EditText
        if (isMobileLayout) {
            commentEditText.textSize = 16f
        }

        val titleView = TextView(this@Window1)
        titleView.text = "Add Transaction Comment"
        titleView.textSize = if (isMobileLayout) 16f else 18f
        titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
        titleView.setPadding(
            if (isMobileLayout) 50 else 24,  // left
            if (isMobileLayout) 50 else 20,  // top
            if (isMobileLayout) 16 else 24,  // right
            if (isMobileLayout) 8 else 12    // bottom
        )
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTypeface(null, Typeface.BOLD)

        val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
            .setCustomTitle(titleView) // Use setCustomTitle instead of setTitle
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val comment = commentEditText.text.toString().trim()
                lifecycleScope.launch {
                    try {
                        cartViewModel.updateCartComment(windowId, comment)
                        transactionComment = comment // Update the local variable

                        delay(50) // Small delay to ensure update completes

                        withContext(Dispatchers.Main) {
                            updateTotalAmountWithComment() // Refresh total amount
                            Toast.makeText(this@Window1, "Comment saved", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error saving comment: ${e.message}", e)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Window1, "Error saving comment", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        dialog.show()

        // Apply mobile styling after dialog is shown
        applyMobileDialogStyling(dialog)
    }



    private fun setupDeleteCommentButton() {
        binding.deleteCommentButton?.setOnClickListener {
            showDeleteCommentConfirmation()
            binding.deleteCommentButton!!.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                binding.deleteCommentButton!!.isEnabled = true
            }, 100)
        }
    }

    private fun showDeleteCommentConfirmation() {
        if (transactionComment.isEmpty()) {
            Toast.makeText(this, "No comment to delete", Toast.LENGTH_SHORT).show()
            return
        }

        val titleView = TextView(this@Window1)
        titleView.text = "Delete Comment"
        titleView.textSize = if (isMobileLayout) 16f else 18f
        titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
        titleView.setPadding(
            if (isMobileLayout) 50 else 24,  // left
            if (isMobileLayout) 50 else 20,  // top
            if (isMobileLayout) 16 else 24,  // right
            if (isMobileLayout) 8 else 12    // bottom
        )
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTypeface(null, Typeface.BOLD)

        val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
            .setCustomTitle(titleView)
            .setMessage("Are you sure you want to delete the current comment?")
            .setPositiveButton("Yes") { _, _ ->
                deleteComment()  // Delete the comment and refresh UI
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        dialog.show()

        // Apply mobile styling after dialog is shown
        applyMobileDialogStyling(dialog)
    }
    private fun deleteComment() {
        lifecycleScope.launch {
            try {
                cartViewModel.updateCartComment(windowId, null)
                transactionComment = ""

                delay(50) // Small delay to ensure update completes

                cartViewModel.getCartComment(windowId) // Refresh the comment from the ViewModel

                withContext(Dispatchers.Main) {
                    updateTotalAmountWithComment()
                    Toast.makeText(this@Window1, "Comment deleted", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting comment: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Window1, "Error deleting comment", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateCartUI(cartItems: List<CartItem>) {
        // Update your RecyclerView or other UI components with the cart items
    }
//    private suspend fun getPrinterAddress(): String? {
//        // Try to get window-specific printer first
//        val windowPrinter = printerSettingsViewModel.getPrinterForWindow(windowId)
//        if (windowPrinter != null) {
//            return windowPrinter.macAddress
//        }
//
//        // Fall back to default printer
//        val defaultPrinter = printerSettingsViewModel.getDefaultPrinter()
//        return defaultPrinter?.macAddress
//    }
private fun connectToPrinter() {
    if (!checkBluetoothPermissions()) {
        Toast.makeText(this, "Bluetooth permission is required", Toast.LENGTH_LONG).show()
        return
    }

    lifecycleScope.launch {
        try {
            // Get the last connected printer address
            val prefs = getSharedPreferences("BluetoothPrinter", Context.MODE_PRIVATE)
            val lastAddress = prefs.getString("last_printer_address", null)

            if (lastAddress != null) {
                Log.d("Window1", "Attempting to connect to last known printer: $lastAddress")
                val connected = bluetoothPrinterHelper.connect(lastAddress)
                if (connected) {
                    updatePrinterIndicator()
                    return@launch
                }
            }

            // If no last printer or connection failed, show printer settings
            val intent = Intent(this@Window1, Window1::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("Window1", "Error connecting to printer: ${e.message}")
            withContext(Dispatchers.Main) {
                Toast.makeText(this@Window1, "Error connecting to printer: ${e.message}", Toast.LENGTH_SHORT).show()
                updatePrinterIndicator()
            }
        }
    }
}

    fun printReceiptWithBluetoothPrinter(content: String) {
        if (!checkBluetoothPermissions()) {
            Toast.makeText(this, "Bluetooth permission is required to print", Toast.LENGTH_LONG)
                .show()
            return
        }

        try {
            if (!bluetoothPrinterHelper.isConnected()) {
                connectToPrinter()
                if (!bluetoothPrinterHelper.isConnected()) {
                    Toast.makeText(this, "Failed to connect to printer", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            val cutCommand = ""  // GS V 0 for full cut
//            V
            val contentWithCut = "$content\n$cutCommand"

            val printSuccess = bluetoothPrinterHelper.printGenericReceipt(contentWithCut)

            if (printSuccess) {
                Toast.makeText(this, "Receipt printed and cut successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Failed to print receipt", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("PrintReceipt", "Error printing receipt: ${e.message}")
            Toast.makeText(this, "Error printing receipt: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun calculateDiscountedTotal(cartItems: List<CartItem>): Double {
        var gross = 0.0
        var totalDiscount = 0.0

        cartItems.forEach { cartItem ->
            val effectivePrice = cartItem.overriddenPrice ?: cartItem.price
            val itemTotal = effectivePrice * cartItem.quantity
            gross += itemTotal

            when (cartItem.discountType.toUpperCase()) {
                "PERCENTAGE", "PWD", "SC" -> totalDiscount += itemTotal * (cartItem.discount / 100)
                "FIXED" -> totalDiscount += cartItem.discount * cartItem.quantity
                "FIXEDTOTAL" -> totalDiscount += cartItem.discount
            }
        }

        return gross - totalDiscount
    }

    private fun showPartialPaymentDialog() {
        lifecycleScope.launch {
            val cartItems = cartViewModel.getAllCartItems(windowId).first()
            if (cartItems.isEmpty()) {
                Toast.makeText(this@Window1, "Cart is empty", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val dialogView = layoutInflater.inflate(R.layout.dialog_partial_payment, null)
            val amountPaidEditText = dialogView.findViewById<EditText>(R.id.amountPaidEditText)
            val paymentMethodSpinner = dialogView.findViewById<Spinner>(R.id.paymentMethodSpinner)
            val customerAutoComplete =
                dialogView.findViewById<AutoCompleteTextView>(R.id.customerAutoComplete)

            // Set up payment methods spinner with black text
            val paymentMethods = arrayOf("Cash", "Gcash", "Credit Card", "Debit Card")
            val adapter = ArrayAdapter(this@Window1, R.layout.spinner_item, paymentMethods)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            paymentMethodSpinner.adapter = adapter


// Inside your showPartialPaymentDialog function
            val percentageButtons = listOf(
                dialogView.findViewById<Button>(R.id.btn80),
                dialogView.findViewById<Button>(R.id.btn70),
                dialogView.findViewById<Button>(R.id.btn60),
                dialogView.findViewById<Button>(R.id.btn50),
                dialogView.findViewById<Button>(R.id.btn40),
                dialogView.findViewById<Button>(R.id.btn30)
            )

            val percentages = listOf(0.8, 0.7, 0.6, 0.5, 0.4, 0.3)

// Calculate discounted total for percentage calculations
            val discountedTotal = calculateDiscountedTotal(cartItems)

// Set up click listeners for percentage buttons
            percentageButtons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    val percentage = percentages[index]
                    val partialAmount = discountedTotal * percentage
                    amountPaidEditText.setText(String.format("%.2f", partialAmount))
                }
            }
            // Set listener to ensure text color is maintained after selection
            paymentMethodSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        (view as? TextView)?.setTextColor(Color.BLACK)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            // Rest of your existing partial payment dialog code...
            val customers = mutableListOf<Customer>()
            val defaultWalkInCustomer = Customer(accountNum = "WALK-IN", name = "Walk-in Customer")
            customers.add(defaultWalkInCustomer) // Add the Walk-in Customer as initial customer

            customers.add(defaultWalkInCustomer)
            val customerAdapter = ArrayAdapter(
                this@Window1,
                android.R.layout.simple_dropdown_item_1line,
                customers.map { it.name }
            )
            customerAutoComplete.setAdapter(customerAdapter)
//            customerAutoComplete.setText("Walk-in Customer", false)
            var selectedCustomer = customers[0]

// Set up the AutoCompleteTextView
            customerAutoComplete.apply {
                threshold = 1  // Start filtering after 1 character
                setAdapter(customerAdapter)

                // Only set initial text if no customer is selected
                if (text.isEmpty()) {
                    setText(selectedCustomer.name, false)
                }
            }

// Update the OnItemClickListener
            customerAutoComplete.setOnItemClickListener { _, _, position, _ ->
                selectedCustomer = customers[position]
                // Update the text to show the selected customer's name
                customerAutoComplete.setText(selectedCustomer.name, false)

                // Hide keyboard after selection
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(customerAutoComplete.windowToken, 0)

                // Debug log
                Log.d(TAG, "Selected customer: ${selectedCustomer.name}, Account: ${selectedCustomer.accountNum}")
            }


            customerAutoComplete.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    dismissKeyboard(v)
                    true
                } else {
                    false
                }
            }

            val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
                .setTitle("Partial Payment")
                .setView(dialogView)
                .setPositiveButton("Pay") { dialog, _ ->
                    val amountPaid = amountPaidEditText.text.toString().toDoubleOrNull()
                    val paymentMethod = paymentMethodSpinner.selectedItem.toString()

                    if (amountPaid != null) {
                        processPartialPayment(amountPaid, paymentMethod, selectedCustomer)
                    } else {
                        Toast.makeText(
                            this@Window1,
                            "Please enter a valid amount",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            dialog.show()
        }
    }

    // Helper function to dismiss the keyboard
    private fun dismissKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun processPartialPayment(
        amountPaid: Double,
        paymentMethod: String,
        selectedCustomer: Customer
    ) {
        lifecycleScope.launch {
            val cartItems = cartViewModel.getAllCartItems(windowId).first()
            if (cartItems.isEmpty()) {
                Toast.makeText(this@Window1, "Cart is empty", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val vatRate = 1.12 // 12% VAT

            var gross = 0.0
            var pwdScDiscount = 0.0
            var otherDiscount = 0.0
            var vatAmount = 0.0
            var vatExemptAmount = 0.0

            // Check if PWD or SC discount is applied
            val pwdOrScItem = cartItems.find {
                it.discountType.equals(
                    "PWD",
                    ignoreCase = true
                ) || it.discountType.equals("SC", ignoreCase = true)
            }
            val isPWDorSC = pwdOrScItem != null

            // Calculate totals from cart items
            cartItems.forEach { cartItem ->
                val effectivePrice = cartItem.overriddenPrice ?: cartItem.price
                val itemTotal = effectivePrice * cartItem.quantity
                gross += itemTotal

                if (isPWDorSC) {
                    val vatExclusiveAmount = itemTotal / vatRate
                    vatExemptAmount += vatExclusiveAmount
                    val discountPercentage = pwdOrScItem!!.discount / 100
                    pwdScDiscount += vatExclusiveAmount * discountPercentage
                    vatAmount = 0.0
                } else {
                    vatAmount += itemTotal - (itemTotal / vatRate)

                    when (cartItem.discountType.toUpperCase()) {
                        "PERCENTAGE" -> otherDiscount += itemTotal * (cartItem.discount / 100)
                        "FIXED" -> otherDiscount += cartItem.discount * cartItem.quantity
                        "FIXEDTOTAL" -> otherDiscount += cartItem.discount
                    }
                }
            }

            val totalDiscount = pwdScDiscount + otherDiscount
            val discountedSubtotal =
                if (isPWDorSC) vatExemptAmount - pwdScDiscount else gross - totalDiscount

            val currentPartialPayment = cartItems.firstOrNull()?.partialPayment ?: 0.0
            val newPartialPayment = currentPartialPayment + amountPaid
            val currentDateString = formatDateToString(Date())

            if (newPartialPayment > discountedSubtotal) {
                Toast.makeText(
                    this@Window1,
                    "Partial payment cannot exceed the total amount. Please enter a smaller amount.",
                    Toast.LENGTH_LONG
                ).show()
                return@launch
            }

            val remainingBalance = discountedSubtotal - newPartialPayment


            // Update cart items with new partial payment
            cartItems.forEach { cartItem ->
                val updatedCartItem = cartItem.copy(
                    partialPayment = newPartialPayment, // Update partial payment
                    amountPaid = amountPaid,
                    netAmount = discountedSubtotal,
                    grossAmount = gross,
                    discountAmount = totalDiscount,
                    numberOfItems = cartItems.sumOf { it.quantity }.toDouble(),
                    createdDate = Date(),
                    taxIncludedInPrice = vatAmount,
                    gCash = if (paymentMethod == "Gcash") amountPaid else cartItem.gCash,
                    cash = if (paymentMethod == "Cash") amountPaid else cartItem.cash,
                    card = if (paymentMethod == "Credit Card" || paymentMethod == "Debit Card") amountPaid else cartItem.card,
                    totalAmountPaid = newPartialPayment, // Update total paid
                    paymentMethod = paymentMethod,
                    customerName = selectedCustomer.name,
                    customerAccName = selectedCustomer.accountNum,
                    vatAmount = vatAmount,
                    vatExemptAmount = vatExemptAmount
                )
                cartViewModel.update(updatedCartItem)
            }

            // Print receipts for customer and staff copy
            printPartialPaymentReceipt(
                cartItems,
                amountPaid,
                paymentMethod,
                newPartialPayment,
                remainingBalance,
                "Customer Copy"
            )
            printPartialPaymentReceipt(
                cartItems,
                amountPaid,
                paymentMethod,
                newPartialPayment,
                remainingBalance,
                "Staff Copy"
            )
            printWindowNumberReceipt(windowId, newPartialPayment, remainingBalance)

            Toast.makeText(
                this@Window1,
                "Partial payment of P%.2f applied. Remaining: P%.2f".format(
                    amountPaid,
                    remainingBalance
                ),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun printPartialPaymentReceipt(
        cartItems: List<CartItem>,
        amountPaid: Double,
        paymentMethod: String,
        totalPartialPayment: Double,
        remainingBalance: Double,
        copyType: String
    ) {
        val sb = StringBuilder()
        sb.append(0x1B.toChar()) // ESC
        sb.append('!'.toChar())  // Select print mode
        sb.append(0x01.toChar()) // Smallest text size

        // Set minimum line spacing
        sb.append(0x1B.toChar()) // ESC
        sb.append('3'.toChar())  // Select line spacing
        sb.append(50.toChar())

        // Store Header
        sb.appendLine("ELJIN CORP")
        sb.appendLine()
        sb.appendLine("TIN: Your TIN Number")
        sb.appendLine("MIN: Your MIN")
        sb.appendLine("-".repeat(45))

        // Receipt Type Header
        sb.appendLine("PARTIAL PAYMENT - $copyType")

        // Transaction Info
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        sb.appendLine("Date: ${dateFormat.format(Date())}")
        sb.appendLine("Window: ${windowId}")
        sb.appendLine("-".repeat(45))

        // Items Section
        sb.appendLine("Item                    Price   Qty    Total")
        sb.appendLine("-".repeat(45))

        var gross = 0.0
        var totalDiscount = 0.0

        // Process items with compact formatting
        cartItems.forEach { item ->
            val effectivePrice = item.overriddenPrice ?: item.price
            val itemTotal = effectivePrice * item.quantity
            gross += itemTotal

            // Item line with compact format
            sb.appendLine(
                "${item.productName.take(22).padEnd(22)} ${
                    String.format("%7.2f", effectivePrice)
                } ${String.format("%3d", item.quantity)} ${String.format("%9.2f", itemTotal)}"
            )

            // Show price override if needed
            if (item.overriddenPrice != null) {
                sb.appendLine(" Orig:${String.format("%7.2f", item.price)} New:${String.format("%7.2f", item.overriddenPrice)}")
            }

            // Compact discount display
            when (item.discountType.uppercase()) {
                "PERCENTAGE", "PWD", "SC" -> {
                    val discountAmount = itemTotal * (item.discount / 100)
                    if (discountAmount > 0) {
                        totalDiscount += discountAmount
                        sb.appendLine(" Disc(${item.discountType}):${String.format("%22.2f", discountAmount)}")
                    }
                }
                "FIXED", "FIXEDTOTAL" -> {
                    if (item.discount > 0) {
                        totalDiscount += if (item.discountType == "FIXED") item.discount * item.quantity else item.discount
                        sb.appendLine(" Disc:${String.format("%32.2f", item.discount)}")
                    }
                }
            }
        }

        // Calculate totals
        val netAmount = gross - totalDiscount
        val vatAmount = gross * 0.12
        val costAmount = gross - vatAmount

        sb.appendLine("-".repeat(45))

        // Totals Section
        sb.appendLine("Gross Amount:${String.format("%27.2f", gross)}")
        if (totalDiscount > 0) {
            sb.appendLine("Less Discount:${String.format("%26.2f", totalDiscount)}")
        }
        sb.appendLine("Net Amount:${String.format("%29.2f", netAmount)}")

        // Enhanced Payment Details Section
        sb.appendLine("-".repeat(45))
        sb.appendLine("PAYMENT DETAILS")
        sb.appendLine("-".repeat(45))

        // Show current payment method and amount
        sb.appendLine("Payment Method: $paymentMethod")
        sb.appendLine("This Payment:${String.format("%28.2f", amountPaid)}")

        // Check if there are multiple payment methods used in previous payments
        val previousPayments = mutableListOf<Pair<String, Double>>()
        cartItems.firstOrNull()?.let { firstItem ->
            if (firstItem.cash > 0 && paymentMethod != "Cash") previousPayments.add("Cash" to firstItem.cash)
            if (firstItem.gCash > 0 && paymentMethod != "GCash") previousPayments.add("GCash" to firstItem.gCash)
            if (firstItem.card > 0 && paymentMethod != "Credit Card" && paymentMethod != "Debit Card") previousPayments.add("Card" to firstItem.card)
            // Add other payment methods as needed
        }

        if (previousPayments.isNotEmpty()) {
            sb.appendLine("-".repeat(45))
            sb.appendLine("PREVIOUS PAYMENTS:")
            previousPayments.forEach { (method, amount) ->
                sb.appendLine("${method.padEnd(25)} ${String.format("%12.2f", amount)}")
            }
        }

        // Partial Payment Summary
        sb.appendLine("-".repeat(45))
        sb.appendLine("PARTIAL PAYMENT SUMMARY")
        val previousTotal = totalPartialPayment - amountPaid
        if (previousTotal > 0) {
            sb.appendLine("Previous Total:${String.format("%26.2f", previousTotal)}")
        }
        sb.appendLine("This Payment:${String.format("%28.2f", amountPaid)}")
        sb.appendLine("Total Paid:${String.format("%30.2f", totalPartialPayment)}")
        sb.appendLine("Balance Due:${String.format("%29.2f", remainingBalance)}")

        // Show payment completion status
        if (remainingBalance <= 0.01) { // Account for rounding
            sb.appendLine("STATUS: FULLY PAID")
        } else {
            val percentagePaid = (totalPartialPayment / netAmount) * 100
            sb.appendLine("Paid: ${String.format("%.1f", percentagePaid)}% of total")
        }

        // VAT Info
        sb.appendLine("-".repeat(45))
        sb.appendLine("VATable Sales:${String.format("%26.2f", costAmount)}")
        sb.appendLine("VAT Amount:${String.format("%29.2f", vatAmount)}")
        sb.appendLine("VAT Exempt:${String.format("%29.2f", 0.0)}")

        // Customer Info if available
        cartItems.firstOrNull()?.let { firstItem ->
            if (!firstItem.customerName.isNullOrBlank() && firstItem.customerName != "Walk-in Customer") {
                sb.appendLine("-".repeat(45))
                sb.appendLine("Customer: ${firstItem.customerName}")
                if (!firstItem.customerAccName.isNullOrBlank()) {
                    sb.appendLine("Account: ${firstItem.customerAccName}")
                }
            }
        }

        // Footer
        sb.appendLine("-".repeat(45))
        sb.appendLine("Valid for 5 years from PTU date")
        sb.appendLine("POS Provider: IT WARRIORS")
        sb.appendLine("-".repeat(45))

        // Reset text formatting
        sb.append(0x1B.toChar()) // ESC
        sb.append('!'.toChar())  // Select print mode
        sb.append(0x00.toChar()) // Reset to normal size
        sb.append(0x1B.toChar()) // ESC
        sb.append('2'.toChar())

        printReceiptWithBluetoothPrinter(sb.toString().trimEnd())
    }

    // 5. Helper function for return payment methods formatting
    private fun formatReturnPaymentMethodsSection(transaction: TransactionSummary): String {
        val sb = StringBuilder()

        sb.appendLine("-".repeat(45))
        sb.appendLine("REFUND DETAILS")
        sb.appendLine("-".repeat(45))

        val refundMethods = mutableListOf<Pair<String, Double>>()

        // Collect refund methods (negative amounts become positive for display)
        if (transaction.cash < 0) refundMethods.add("Cash Refund" to kotlin.math.abs(transaction.cash))
        if (transaction.gCash < 0) refundMethods.add("GCash Refund" to kotlin.math.abs(transaction.gCash))
        if (transaction.payMaya < 0) refundMethods.add("PayMaya Refund" to kotlin.math.abs(transaction.payMaya))
        if (transaction.card < 0) refundMethods.add("Card Refund" to kotlin.math.abs(transaction.card))
        if (transaction.loyaltyCard < 0) refundMethods.add("Loyalty Refund" to kotlin.math.abs(transaction.loyaltyCard))
        if (transaction.charge < 0) refundMethods.add("Charge Refund" to kotlin.math.abs(transaction.charge))
        if (transaction.foodpanda < 0) refundMethods.add("FoodPanda Refund" to kotlin.math.abs(transaction.foodpanda))
        if (transaction.grabfood < 0) refundMethods.add("GrabFood Refund" to kotlin.math.abs(transaction.grabfood))
        if (transaction.representation < 0) refundMethods.add("Representation Refund" to kotlin.math.abs(transaction.representation))

        if (refundMethods.size > 1) {
            sb.appendLine("SPLIT REFUND:")
            refundMethods.forEach { (method, amount) ->
                sb.appendLine("${method.padEnd(25)} ${String.format("%12.2f", amount)}")
            }
            sb.appendLine("-".repeat(45))
            sb.appendLine("Total Refund:${String.format("%26.2f", refundMethods.sumOf { it.second })}")
        } else if (refundMethods.isNotEmpty()) {
            val (method, amount) = refundMethods.first()
            sb.appendLine("Refund Method: ${method.replace(" Refund", "")}")
            sb.appendLine("Refund Amount:${String.format("%26.2f", amount)}")
        }

        return sb.toString()
    }


    private fun printWindowNumberReceipt(
        windowId: Int,
        totalPartialPayment: Double,
        remainingBalance: Double
    ) {
        val content = StringBuilder()
        content.append(0x1B.toChar()) // ESC
        content.append('!'.toChar())  // Select print mode
        content.append(0x09.toChar()) // Small + Bold (0x08 for bold + 0x01 for small = 0x09)

        // Set line spacing
        content.append(0x1B.toChar()) // ESC
        content.append('3'.toChar())  // Select line spacing
        content.append(28.toChar())
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append("====================\n")
        content.append("    WINDOW $windowId    \n")
        content.append("====================\n")
        content.append("Partial Payment:\n")
        content.append("P${String.format("%.2f", totalPartialPayment)}\n")
        content.append("Remaining:\n")
        content.append("P${String.format("%.2f", remainingBalance)}\n")
        content.append("====================\n")
        content.append("====================\n")
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(" ".repeat(46))
        content.append(0x1B.toChar()) // ESC
        content.append('!'.toChar())  // Select print mode
        content.append(0x00.toChar()) // Reset to normal size

        content.append(0x1B.toChar()) // ESC
        content.append('2'.toChar())

        // Print the small receipt
        printReceiptWithBluetoothPrinter(content.toString())
    }


    private fun setupCartRecyclerView() {
        // Use different RecyclerView based on layout
        val cartRecyclerView = if (isMobileLayout) {
            findViewById<RecyclerView>(R.id.recyclerviewcart) // Mobile cart in bottom sheet
        } else {
            binding.recyclerviewcart // Tablet cart in sidebar
        }

        Log.d(TAG, "Setting up cart RecyclerView - Mobile mode: $isMobileLayout")

        class CartDeleteHelper(private val adapter: CartAdapter) {
            fun deleteBundle(cartItem: CartItem) {
                val bundleItems = adapter.currentList.filter { item ->
                    item.bundleId == cartItem.bundleId && item.mixMatchId == cartItem.mixMatchId
                }
                bundleItems.forEach { bundleItem ->
                    cartViewModel.deleteCartItem(bundleItem)
                }
            }
        }

        lateinit var deleteHelper: CartDeleteHelper

        val adapter = CartAdapter(
            onItemClick = { cartItem ->
                Log.d(TAG, "Cart item clicked: ${cartItem.productName}")
            },
            onDeleteClick = { cartItem ->
                Log.d(TAG, "Delete button clicked for: ${cartItem.productName}")
                if (!partialPaymentApplied) {
                    cartViewModel.deleteCartItem(cartItem)
                    Toast.makeText(
                        this@Window1,
                        "${cartItem.productName} removed from cart",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@Window1,
                        "Cannot delete items when partial payment is applied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            onQuantityChange = { cartItem, newQuantity ->
                Log.d(TAG, "Quantity changed for ${cartItem.productName}: $newQuantity")
                cartViewModel.update(cartItem.copy(quantity = newQuantity))
            },
            onDiscountDoubleTap = { cartItem ->
                Log.d(TAG, "Discount double-tap for: ${cartItem.productName}")
                showDiscountDialog()
            }
        )

        // Initialize delete helper after adapter is created
        deleteHelper = CartDeleteHelper(adapter)

        cartRecyclerView?.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@Window1)
            Log.d(TAG, "✅ Cart RecyclerView configured")
        } ?: run {
            Log.e(TAG, "❌ Cart RecyclerView not found!")
            return
        }

        // Set up swipe-to-delete for BOTH mobile and tablet modes
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                Log.d(TAG, "Swipe detected at position: $position, direction: $direction")

                if (position != RecyclerView.NO_POSITION) {
                    val cartItem = adapter.currentList[position]
                    Log.d(TAG, "Swiping to delete: ${cartItem.productName}")

                    if (!partialPaymentApplied) {
                        if (cartItem.bundleId != null) {
                            Log.d(TAG, "Deleting entire bundle with ID: ${cartItem.bundleId}")
                            deleteHelper.deleteBundle(cartItem)
                            Toast.makeText(
                                this@Window1,
                                "Entire bundle has been removed",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d(TAG, "Deleting single item: ${cartItem.productName}")
                            cartViewModel.deleteCartItem(cartItem)
                            Toast.makeText(
                                this@Window1,
                                "${cartItem.productName} removed from cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Log.w(TAG, "Cannot delete - partial payment applied")
                        adapter.notifyItemChanged(position)
                        Toast.makeText(
                            this@Window1,
                            "Cannot delete items when partial payment is applied",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return if (partialPaymentApplied) {
                    Log.d(TAG, "Swipe disabled - partial payment applied")
                    0 // Disable swiping when partial payment is applied
                } else {
                    Log.d(TAG, "Swipe enabled for cart item")
                    super.getSwipeDirs(recyclerView, viewHolder)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                // Add visual feedback during swipe
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val alpha = 1.0f - abs(dX) / viewHolder.itemView.width.toFloat()
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                // Reset the view state
                viewHolder.itemView.alpha = 1.0f
                viewHolder.itemView.translationX = 0f
            }
        })

        // Attach ItemTouchHelper to RecyclerView for BOTH mobile and tablet
        itemTouchHelper.attachToRecyclerView(cartRecyclerView)
        Log.d(TAG, "✅ Swipe-to-delete enabled for ${if (isMobileLayout) "mobile" else "tablet"} mode")

        // Observe partial payment changes
        lifecycleScope.launch {
            cartViewModel.getPartialPaymentForWindow(windowId).collect { partialPayment ->
                val wasPartialPaymentApplied = partialPaymentApplied
                partialPaymentApplied = partialPayment > 0
                partialPaymentAmount = partialPayment

                if (wasPartialPaymentApplied != partialPaymentApplied) {
                    Log.d(TAG, "Partial payment status changed: $partialPaymentApplied")
                }

                adapter.setPartialPaymentApplied(partialPaymentApplied)
                adapter.setDeletionEnabled(!partialPaymentApplied)

                Log.d(TAG, "Cart state - Partial payment: $partialPaymentApplied, Amount: $partialPaymentAmount")
            }
        }
    }

//    private fun showDiscountDialog() {
//        lifecycleScope.launch {
//            try {
//                val cartItems = cartViewModel.getAllCartItems(windowId).first()
//                if (cartItems.isEmpty()) {
//                    Toast.makeText(
//                        this@Window1,
//                        "No items in cart. Please add items before applying discount.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@launch
//                }
//
//                withContext(Dispatchers.Main) {
//                    val dialogView = layoutInflater.inflate(R.layout.dialog_discount, null)
//                    val discountRecyclerView = dialogView.findViewById<RecyclerView>(R.id.discountRecyclerView)
//                    val cartItemsLayout = dialogView.findViewById<LinearLayout>(R.id.cartItemsLayout)
//                    val cartItemsTitle = dialogView.findViewById<TextView>(R.id.cartItemsTitle)
//                    val searchEditText = dialogView.findViewById<EditText>(R.id.searchDiscounts)
//                    val selectAllCheckbox = dialogView.findViewById<CheckBox>(R.id.selectAllCheckbox)
//
//                    var selectedDiscount: Discount? = null
//                    var adapter: DiscountAdapter? = null
//
//                    val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
//                        .setTitle("Apply Discount")
//                        .setView(dialogView)
//                        .setPositiveButton("Apply") { dialog, _ ->
//                            applySelectedDiscount(dialogView, selectedDiscount)
//                            dialog.dismiss()
//                        }
//                        .setNegativeButton("Cancel") { dialog, _ ->
//                            dialog.cancel()
//                        }
//                        .create()
//
//                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
//
//                    // Show loading state for discount recycler view
//                    showDiscountRecyclerLoading(dialogView, true)
//
//                    // Set up search functionality
//                    searchEditText.addTextChangedListener(object : TextWatcher {
//                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//                        override fun afterTextChanged(s: Editable?) {
//                            adapter?.filter(s.toString())
//                        }
//                    })
//
//                    // Handle "Done" action on keyboard
//                    searchEditText.setOnEditorActionListener { v, actionId, event ->
//                        if (actionId == EditorInfo.IME_ACTION_DONE) {
//                            hideKeyboard(v)
//                            true
//                        } else {
//                            false
//                        }
//                    }
//
//                    // Initialize adapter first
//                    adapter = DiscountAdapter { discount ->
//                        selectedDiscount = discount
//                        updateCartItemsForDiscount(cartItemsLayout, cartItemsTitle, selectAllCheckbox, discount)
//                    }
//                    discountRecyclerView.adapter = adapter
//                    val layoutManager = LinearLayoutManager(this@Window1, LinearLayoutManager.HORIZONTAL, false)
//                    discountRecyclerView.layoutManager = layoutManager
//
//                    // Load discounts from local data first for fast loading
//                    lifecycleScope.launch {
//                        try {
//                            // Get cached discounts first for immediate display
//                            val cachedDiscounts = discountViewModel.discounts.value
//                            if (!cachedDiscounts.isNullOrEmpty()) {
//                                withContext(Dispatchers.Main) {
//                                    adapter?.setDiscounts(cachedDiscounts)
//                                    showDiscountRecyclerLoading(dialogView, false)
//                                }
//                            }
//
//                            // Fetch fresh discounts in background
//                            discountViewModel.fetchDiscounts()
//                        } catch (e: Exception) {
//                            Log.e(TAG, "Error loading discounts: ${e.message}", e)
//                            withContext(Dispatchers.Main) {
//                                showDiscountRecyclerLoading(dialogView, false)
//                                Toast.makeText(
//                                    this@Window1,
//                                    "Error loading discounts",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    }
//
//                    // Observe discount updates
//                    discountViewModel.discounts.observe(this@Window1) { discounts ->
//                        if (!discounts.isNullOrEmpty()) {
//                            adapter?.setDiscounts(discounts)
//                            showDiscountRecyclerLoading(dialogView, false)
//                        }
//                    }
//
//                    // Set up Select All checkbox listener
//                    selectAllCheckbox.setOnCheckedChangeListener { _, isChecked ->
//                        for (i in 0 until cartItemsLayout.childCount) {
//                            val view = cartItemsLayout.getChildAt(i)
//                            if (view is CheckBox && view.isEnabled) {
//                                view.isChecked = isChecked
//                            }
//                        }
//                    }
//
//                    // Populate cart items
//                    cartItems.forEach { cartItem ->
//                        if (cartItem.discount == 0.0) {
//                            val checkBox = CheckBox(this@Window1).apply {
//                                text = "${cartItem.productName} (${cartItem.quantity} x ₱${cartItem.price})"
//                                tag = cartItem.id
//                                setTextColor(Color.BLACK)
//                                buttonTintList = ColorStateList.valueOf(Color.BLACK)
//                            }
//                            cartItemsLayout.addView(checkBox)
//                        }
//                    }
//
//                    dialog.show()
//                }
//            } catch (e: Exception) {
//                Log.e(TAG, "Error showing discount dialog: ${e.message}", e)
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@Window1,
//                        "Error loading discount dialog",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
// try

    private fun showDiscountDialog() {
        lifecycleScope.launch {
            try {
                val cartItems = cartViewModel.getAllCartItems(windowId).first()
                if (cartItems.isEmpty()) {
                    Toast.makeText(
                        this@Window1,
                        "No items in cart. Please add items before applying discount.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                // Get current window information
                val window = windowViewModel.allWindows.first().find { it.id == windowId }
                val windowType = window?.description?.uppercase() ?: ""

                withContext(Dispatchers.Main) {
                    val dialogView = layoutInflater.inflate(R.layout.dialog_discount, null)
                    val discountRecyclerView = dialogView.findViewById<RecyclerView>(R.id.discountRecyclerView)
                    val cartItemsLayout = dialogView.findViewById<LinearLayout>(R.id.cartItemsLayout)
                    val cartItemsTitle = dialogView.findViewById<TextView>(R.id.cartItemsTitle)
                    val searchEditText = dialogView.findViewById<EditText>(R.id.searchDiscounts)
                    val selectAllCheckbox = dialogView.findViewById<CheckBox>(R.id.selectAllCheckbox)

                    // Apply mobile-specific styling to existing UI elements
                    if (isMobileLayout) {
                        searchEditText.textSize = 12f
                        cartItemsTitle.textSize = 9f
                        selectAllCheckbox.textSize = 9f

                        // Adjust other text views in the dialog
                        val selectDiscountText = dialogView.findViewById<TextView>(R.id.selectDiscountText)
                        selectDiscountText?.textSize = 9f
                    }

                    var selectedDiscount: Discount? = null
                    var adapter: DiscountAdapter? = null

                    val titleView = TextView(this@Window1)
                    titleView.text = "Apply Discount - ${getWindowTypeLabel(windowType)}"
                    titleView.textSize = if (isMobileLayout) 16f else 18f
                    titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
                    titleView.setPadding(
                        if (isMobileLayout) 50 else 24,  // left
                        if (isMobileLayout) 50 else 20,  // top
                        if (isMobileLayout) 16 else 24,  // right
                        if (isMobileLayout) 8 else 12    // bottom
                    )
                    titleView.gravity = Gravity.CENTER_VERTICAL
                    titleView.setTypeface(null, Typeface.BOLD)

                    val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
                        .setCustomTitle(titleView)
                        .setView(dialogView)
                        .setPositiveButton("Apply") { _, _ ->
                            applySelectedDiscountWithWindowType(dialogView, selectedDiscount, windowType)
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

                    // Show loading state for discount recycler view
                    showDiscountRecyclerLoading(dialogView, true)

                    // Set up search functionality
                    searchEditText.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        override fun afterTextChanged(s: Editable?) {
                            adapter?.filter(s.toString())
                        }
                    })

                    // Handle "Done" action on keyboard
                    searchEditText.setOnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            hideKeyboard(v)
                            true
                        } else {
                            false
                        }
                    }

                    // Initialize adapter - your existing adapter but with window type awareness
                    adapter = DiscountAdapter { discount ->
                        selectedDiscount = discount
                        updateCartItemsForDiscount(cartItemsLayout, cartItemsTitle, selectAllCheckbox, discount)
                    }

                    // Set the window type so adapter knows which parameters to show
                    adapter.setWindowType(windowType)

                    discountRecyclerView.adapter = adapter
                    val layoutManager = LinearLayoutManager(this@Window1, LinearLayoutManager.HORIZONTAL, false)
                    discountRecyclerView.layoutManager = layoutManager

                    // Load discounts from local data first for fast loading
                    lifecycleScope.launch {
                        try {
                            // Get cached discounts first for immediate display
                            val cachedDiscounts = discountViewModel.discounts.value
                            if (!cachedDiscounts.isNullOrEmpty()) {
                                withContext(Dispatchers.Main) {
                                    adapter?.setDiscounts(cachedDiscounts)
                                    showDiscountRecyclerLoading(dialogView, false)
                                }
                            }

                            // Fetch fresh discounts in background
                            discountViewModel.fetchDiscounts()
                        } catch (e: Exception) {
                            Log.e(TAG, "Error loading discounts: ${e.message}", e)
                            withContext(Dispatchers.Main) {
                                showDiscountRecyclerLoading(dialogView, false)
                                Toast.makeText(
                                    this@Window1,
                                    "Error loading discounts",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    // Observe discount updates
                    discountViewModel.discounts.observe(this@Window1) { discounts ->
                        if (!discounts.isNullOrEmpty()) {
                            adapter?.setDiscounts(discounts)
                            showDiscountRecyclerLoading(dialogView, false)
                        }
                    }

                    // Set up Select All checkbox listener
                    selectAllCheckbox.setOnCheckedChangeListener { _, isChecked ->
                        for (i in 0 until cartItemsLayout.childCount) {
                            val view = cartItemsLayout.getChildAt(i)
                            if (view is CheckBox && view.isEnabled) {
                                view.isChecked = isChecked
                            }
                        }
                    }

                    // Populate cart items
                    cartItems.forEach { cartItem ->
                        if (cartItem.discount == 0.0) {
                            val checkBox = CheckBox(this@Window1).apply {
                                text = "${cartItem.productName} (${cartItem.quantity} x ₱${cartItem.price})"
                                tag = cartItem.id
                                setTextColor(Color.BLACK)
                                buttonTintList = ColorStateList.valueOf(Color.BLACK)

                                // Apply mobile-specific styling to dynamically created checkboxes
                                textSize = if (isMobileLayout) 12f else 14f
                                minHeight = if (isMobileLayout) 28 else 32
                                setPadding(
                                    if (isMobileLayout) 4 else 6,  // left
                                    if (isMobileLayout) 20 else 20,  // top
                                    if (isMobileLayout) 4 else 6,  // right
                                    if (isMobileLayout) 20 else 20   // bottom
                                )
                            }
                            cartItemsLayout.addView(checkBox)
                        }
                    }

                    dialog.show()

                    // Apply mobile styling after dialog is shown
                    applyMobileDialogStyling(dialog)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error showing discount dialog: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error loading discount dialog",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun showDiscountRecyclerLoading(dialogView: View, isLoading: Boolean) {
        val loadingIndicator = dialogView.findViewById<View>(R.id.discountRecyclerLoadingIndicator)
        val discountRecyclerView = dialogView.findViewById<RecyclerView>(R.id.discountRecyclerView)

        loadingIndicator?.visibility = if (isLoading) View.VISIBLE else View.GONE
        discountRecyclerView?.alpha = if (isLoading) 0.5f else 1.0f
    }

    // Add this extension function to hide the keyboard
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun updateCartItemsForDiscount(
        cartItemsLayout: LinearLayout,
        cartItemsTitle: TextView,
        selectAllCheckbox: CheckBox,
        discount: Discount
    ) {
        cartItemsTitle.visibility = View.VISIBLE
        cartItemsLayout.visibility = View.VISIBLE

        val isSCOrPWD = discount.DISCOFFERNAME.contains("SENIOR", ignoreCase = true) ||
                discount.DISCOFFERNAME.contains("PWD", ignoreCase = true)

        // Show/hide select all checkbox based on discount type
        selectAllCheckbox.visibility = if (isSCOrPWD) View.GONE else View.VISIBLE

        // Reset select all checkbox state
        selectAllCheckbox.isChecked = false
//
//        for (i in 0 until cartItemsLayout.childCount) {
//            val view = cartItemsLayout.getChildAt(i)
//            if (view is CheckBox) {
//                view.isChecked = isSCOrPWD
//                view.isEnabled = !isSCOrPWD
//            }
//        }
    }

    private fun applySelectedDiscount(dialogView: View, selectedDiscount: Discount?) {
        val cartItemsLayout = dialogView.findViewById<LinearLayout>(R.id.cartItemsLayout)
        val selectedCartItemIds = mutableListOf<Int>()

        // Show loading state
        showDiscountLoadingState(dialogView, true)

        for (i in 0 until cartItemsLayout.childCount) {
            val view = cartItemsLayout.getChildAt(i)
            if (view is CheckBox && view.isChecked) {
                view.tag?.let { tag ->
                    if (tag is Int) {
                        selectedCartItemIds.add(tag)
                    }
                }
            }
        }

        if (selectedCartItemIds.isNotEmpty() && selectedDiscount != null) {
            lifecycleScope.launch {
                try {
                    val cartItems = cartViewModel.getAllCartItems(windowId).first()
                    val selectedItems = cartItems.filter { it.id in selectedCartItemIds }

                    // Check if items already have discounts
                    val itemsWithExistingDiscounts = selectedItems.filter {
                        it.discountType.isNotBlank() && it.discountType != "FIXEDTOTAL"
                    }

                    if (selectedDiscount.DISCOUNTTYPE.equals("FIXEDTOTAL", ignoreCase = true) &&
                        itemsWithExistingDiscounts.isNotEmpty()
                    ) {
                        withContext(Dispatchers.Main) {
                            showDiscountLoadingState(dialogView, false)
                            // Show warning dialog for items with existing discounts
                            AlertDialog.Builder(this@Window1)
                                .setTitle("Warning")
                                .setMessage("Some selected items already have discounts. Fixed Total discount cannot be combined with other discounts. Remove existing discounts first?")
                                .setPositiveButton("Remove and Apply") { _, _ ->
                                    lifecycleScope.launch {
                                        showDiscountLoadingState(dialogView, true)
                                        try {
                                            // Remove existing discounts and apply FIXEDTOTAL
                                            selectedItems.forEach { item ->
                                                val updatedItem = item.copy(
                                                    discount = 0.0,
                                                    discountType = "",
                                                    discountAmount = 0.0,
                                                    discountName = null
                                                )
                                                cartViewModel.update(updatedItem)
                                            }

                                            delay(100) // Ensure updates complete

                                            applyFixedTotalDiscount(
                                                selectedItems,
                                                selectedDiscount.PARAMETER.toDouble(),
                                                selectedDiscount.DISCOFFERNAME

                                            )

                                            delay(100) // Ensure discount application completes

                                            val updatedCartItems = cartViewModel.getAllCartItems(windowId).first()
                                            withContext(Dispatchers.Main) {
                                                updateTotalAmount(updatedCartItems)
                                                showDiscountLoadingState(dialogView, false)
                                                Toast.makeText(
                                                    this@Window1,
                                                    "Discount applied to ${selectedItems.size} items",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } catch (e: Exception) {
                                            Log.e(TAG, "Error applying discount: ${e.message}", e)
                                            withContext(Dispatchers.Main) {
                                                showDiscountLoadingState(dialogView, false)
                                                Toast.makeText(
                                                    this@Window1,
                                                    "Error applying discount. Please try again.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                                .setNegativeButton("Cancel") { _, _ ->
                                    showDiscountLoadingState(dialogView, false)
                                }
                                .show()
                        }
                    } else {
                        // Apply the discount normally
                        when (selectedDiscount.DISCOUNTTYPE.toUpperCase()) {
                            "FIXEDTOTAL" -> applyFixedTotalDiscount(
                                selectedItems,
                                selectedDiscount.PARAMETER.toDouble(),
                                selectedDiscount.DISCOFFERNAME
                            )

                            "PERCENTAGE" -> {
                                val isVatExempt = selectedDiscount.DISCOFFERNAME.contains(
                                    "SENIOR",
                                    ignoreCase = true
                                ) ||
                                        selectedDiscount.DISCOFFERNAME.contains(
                                            "PWD",
                                            ignoreCase = true
                                        )
                                applyPercentageDiscount(
                                    selectedItems,
                                    selectedDiscount.PARAMETER.toDouble(),
                                    isVatExempt,
                                    selectedDiscount.DISCOFFERNAME
                                )
                            }

                            "FIXED" -> applyFixedDiscount(
                                selectedItems,
                                selectedDiscount.PARAMETER.toDouble(),
                                selectedDiscount.DISCOFFERNAME
                            )
                        }

                        delay(100) // Ensure discount application completes

                        val updatedCartItems = cartViewModel.getAllCartItems(windowId).first()
                        withContext(Dispatchers.Main) {
                            updateTotalAmount(updatedCartItems)
                            showDiscountLoadingState(dialogView, false)
                            Toast.makeText(
                                this@Window1,
                                "Discount applied to ${selectedItems.size} items",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error applying discount: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        showDiscountLoadingState(dialogView, false)
                        Toast.makeText(
                            this@Window1,
                            "Error applying discount. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            showDiscountLoadingState(dialogView, false)
            Toast.makeText(
                this@Window1,
                "Please select both a discount and at least one item",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun showDiscountLoadingState(dialogView: View, isLoading: Boolean) {
        val loadingIndicator = dialogView.findViewById<View>(R.id.discountLoadingIndicator)
        val discountRecyclerView = dialogView.findViewById<RecyclerView>(R.id.discountRecyclerView)
        val cartItemsLayout = dialogView.findViewById<LinearLayout>(R.id.cartItemsLayout)

        loadingIndicator?.visibility = if (isLoading) View.VISIBLE else View.GONE
        discountRecyclerView?.alpha = if (isLoading) 0.5f else 1.0f
        cartItemsLayout?.alpha = if (isLoading) 0.5f else 1.0f

        // Disable/enable interactions
        discountRecyclerView?.isEnabled = !isLoading
        cartItemsLayout?.isEnabled = !isLoading
    }


    // In your Window1.kt
//    private fun applyFixedTotalDiscount(
//        items: List<CartItem>,
//        totalDiscountAmount: Double,
//        discountName: String
//    ) {
//        // Calculate total price of all selected items
//        val totalPrice = items.sumOf { it.price * it.quantity }
//
//        // Calculate discount percentage based on total discount amount
//        val discountPercentage = (totalDiscountAmount / totalPrice).roundToTwoDecimals()
//
//        items.forEach { cartItem ->
//            val itemTotal = cartItem.price * cartItem.quantity
//
//            // Apply the same discount percentage to each item
//            val itemDiscountAmount = (itemTotal * discountPercentage).roundToTwoDecimals()
//
//            Log.d(
//                "Discount", """
//            Applying FIXED TOTAL discount:
//            Item: ${cartItem.productName}
//            Original Price: ${cartItem.price}
//            Quantity: ${cartItem.quantity}
//            Total: $itemTotal
//            Discount Amount: $itemDiscountAmount
//        """.trimIndent()
//            )
//
//            val updatedCartItem = cartItem.copy(
//                discount = totalDiscountAmount,
//                discountType = "FIXEDTOTAL",
//                discountAmount = totalDiscountAmount,
//                discountName = discountName
//            )
//            cartViewModel.update(updatedCartItem)
//        }
//    }
    private fun applyFixedTotalDiscount(
        items: List<CartItem>,
        totalDiscountAmount: Double,
        discountName: String
    ) {
        if (items.isEmpty()) return

        // Calculate total price of all selected items
        val totalPrice = items.sumOf { it.price * it.quantity }

        // If discount is greater than total price, cap it at total price
        val actualDiscountAmount = minOf(totalDiscountAmount, totalPrice)

        Log.d("Discount", """
        Applying FIXED TOTAL discount:
        Items count: ${items.size}
        Total price of selected items: $totalPrice
        Requested discount: $totalDiscountAmount
        Actual discount applied: $actualDiscountAmount
    """.trimIndent())

        if (items.size == 1) {
            // If only one item is selected, apply the full discount to that item
            val cartItem = items.first()
            val updatedCartItem = cartItem.copy(
                discount = actualDiscountAmount,
                discountType = "FIXEDTOTAL",
                discountAmount = actualDiscountAmount,
                discountName = discountName
            )
            cartViewModel.update(updatedCartItem)

            Log.d("Discount", """
            Single item discount:
            Item: ${cartItem.productName}
            Discount Amount: $actualDiscountAmount
        """.trimIndent())
        } else {
            // If multiple items are selected, distribute the discount proportionally
            var remainingDiscount = actualDiscountAmount

            items.forEachIndexed { index, cartItem ->
                val itemTotal = cartItem.price * cartItem.quantity
                val proportion = itemTotal / totalPrice

                val itemDiscountAmount = if (index == items.size - 1) {
                    // For the last item, use remaining discount to avoid rounding errors
                    remainingDiscount.roundToTwoDecimals()
                } else {
                    (actualDiscountAmount * proportion).roundToTwoDecimals()
                }

                remainingDiscount -= itemDiscountAmount

                Log.d("Discount", """
                Proportional discount for item ${index + 1}:
                Item: ${cartItem.productName}
                Item Total: $itemTotal
                Proportion: $proportion
                Item Discount: $itemDiscountAmount
                Remaining Discount: $remainingDiscount
            """.trimIndent())

                val updatedCartItem = cartItem.copy(
                    discount = itemDiscountAmount,
                    discountType = "FIXEDTOTAL",
                    discountAmount = itemDiscountAmount,
                    discountName = discountName
                )
                cartViewModel.update(updatedCartItem)
            }
        }
    }

    // Helper function for rounding


    private fun applyPercentageDiscount(
        items: List<CartItem>,
        discountPercentage: Double,
        isSpecialDiscount: Boolean,
        discountName: String
    ) {
        items.forEach { cartItem ->
            val updatedCartItem = cartItem.copy(
                discount = discountPercentage,
                discountType = if (isSpecialDiscount) {
                    if (discountPercentage == 5.0) "PWD" else "SC"
                } else "PERCENTAGE",
                discountName = discountName
            )
            cartViewModel.update(updatedCartItem)
        }
    }

    private fun applyFixedDiscount(
        items: List<CartItem>,
        discountAmount: Double,
        discountName: String
    ) {
        items.forEach { cartItem ->
            val updatedCartItem = cartItem.copy(
                discount = discountAmount,
                discountType = "FIXED",
                discountName = discountName
            )
            cartViewModel.update(updatedCartItem)
        }
    }



    //community code
    private fun showPriceOverrideDialog() {
        lifecycleScope.launch {
            // Fetch the current window
            val window = windowViewModel.allWindows.first().find { window -> window.id == windowId }

            // Check if the window is specifically a Party Cakes window
            if (window == null || (!window.description.uppercase().contains("PURCHASE") && !window.description.uppercase().contains("PARTYCAKES"))) {
                // If not a Party Cakes window, silently return without showing the dialog
                return@launch
            }

            val cartItems = cartViewModel.getAllCartItems(windowId).first()
            if (cartItems.isEmpty()) {
                Toast.makeText(this@Window1, "Cart is empty", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val dialogView = layoutInflater.inflate(R.layout.dialog_price_override_list, null)
            val recyclerView = dialogView.findViewById<RecyclerView>(R.id.priceOverrideRecyclerView)

            val titleView = TextView(this@Window1)
            titleView.text = "Override Prices"
            titleView.textSize = if (isMobileLayout) 16f else 18f
            titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
            titleView.setPadding(
                if (isMobileLayout) 50 else 24,  // left
                if (isMobileLayout) 50 else 20,  // top
                if (isMobileLayout) 16 else 24,  // right
                if (isMobileLayout) 8 else 12    // bottom
            )
            titleView.gravity = Gravity.CENTER_VERTICAL
            titleView.setTypeface(null, Typeface.BOLD)

            val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
                .setCustomTitle(titleView)
                .setView(dialogView)
                .setPositiveButton("Close") { _, _ ->
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(dialogView.windowToken, 0)
                }
                .create()

            // Configure dialog window
            dialog.window?.apply {
                setBackgroundDrawableResource(R.drawable.dialog_background)
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                // Remove flags that might prevent keyboard
                clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            }

            val priceOverrideAdapter = PriceOverrideAdapter(
                isMobileLayout = isMobileLayout,  // Pass mobile layout flag to adapter
                onPriceOverride = { cartItem, newPrice ->
                    cartViewModel.updatePriceoverride(cartItem.id, newPrice)
                    dialog.dismiss()
                },
                onPriceReset = { cartItem ->
                    cartViewModel.resetPrice(cartItem.id)
                },
                onEditTextClicked = { editText ->
                    // Force show keyboard
                    editText.apply {
                        isFocusableInTouchMode = true
                        isFocusable = true
                        requestFocus()
                        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    }
                }
            )

            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@Window1)
                adapter = priceOverrideAdapter
            }

            priceOverrideAdapter.submitList(cartItems)

            dialog.setOnShowListener { dialogInterface ->
                val alertDialog = dialogInterface as AlertDialog
                alertDialog.window?.clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                )
            }

            dialog.show()

            // Apply mobile styling after dialog is shown
            applyMobileDialogStyling(dialog)

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
    private fun setupPriceOverrideButton() {
        val priceOverrideButton = findViewById<Button>(R.id.priceOverrideButton)

        lifecycleScope.launch {
            val window = windowViewModel.allWindows.first().find { window -> window.id == windowId }

            withContext(Dispatchers.Main) {
                if (window == null || (!window.description.uppercase().contains("PURCHASE") && !window.description.uppercase().contains("PARTYCAKES"))) {
                    // Disable the button and reduce opacity
                    priceOverrideButton.isEnabled = false
                    priceOverrideButton.alpha = 0.3f  // 30% opacity
                } else {
                    // Enable the button with full opacity
                    priceOverrideButton.isEnabled = true
                    priceOverrideButton.alpha = 1.0f  // 100% opacity
                }
            }
        }
    }
    fun EditText.showKeyboard() {
        post {
            requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }
//    private fun updateTotalAmount(cartItems: List<CartItem>) {
//        Log.d(TAG, "Updating total amount for ${cartItems.size} items")
//
//        var subtotal = 0.0
//        var discount = 0.0
//        var vatAmount = 0.0
//        var partialPayment = 0.0
//
//        cartItems.forEach { cartItem ->
//            val effectivePrice = cartItem.overriddenPrice ?: cartItem.price
//            val itemTotal = effectivePrice * cartItem.quantity
//            subtotal += itemTotal
//
//            when (cartItem.discountType.uppercase()) {
//                "PERCENTAGE", "PWD", "SC" -> discount += itemTotal * (cartItem.discount / 100)
//                "FIXED" -> discount += cartItem.discount * cartItem.quantity
//                "FIXEDTOTAL" -> discount += cartItem.discount
//            }
//
//            vatAmount += itemTotal * 0.12 / 1.12
//
//            if (cartItem.partialPayment > 0) {
//                partialPayment = cartItem.partialPayment
//            }
//        }
//
//        val discountedTotal = subtotal - discount
//        val finalTotal = discountedTotal - partialPayment
//
//        Log.d(TAG, "Calculated totals - Subtotal: $subtotal, Discount: $discount, VAT: $vatAmount, Final: $finalTotal")
//
//        // Update UI based on layout type
//        if (isMobileLayout) {
//            // Update mobile cart bottom sheet
//            runOnUiThread {
//                findViewById<TextView>(R.id.totalAmountTextView)?.text = "₱ %.2f".format(subtotal)
//                findViewById<TextView>(R.id.discountAmountText)?.text = "₱ %.2f".format(discount)
//                findViewById<TextView>(R.id.vatAmountText)?.text = "₱ %.2f".format(vatAmount)
//
//                if (partialPayment > 0) {
//                    findViewById<TextView>(R.id.partialLabel)?.visibility = View.VISIBLE
//                    findViewById<TextView>(R.id.partialPaymentTextView)?.apply {
//                        visibility = View.VISIBLE
//                        text = "₱ %.2f".format(partialPayment)
//                    }
//                    findViewById<TextView>(R.id.finalTotalText)?.text = "₱ %.2f".format(finalTotal)
//                } else {
//                    findViewById<TextView>(R.id.partialLabel)?.visibility = View.GONE
//                    findViewById<TextView>(R.id.partialPaymentTextView)?.visibility = View.GONE
//                    findViewById<TextView>(R.id.finalTotalText)?.text = "₱ %.2f".format(discountedTotal)
//                }
//            }
//        } else {
//            // Update tablet sidebar (existing code)
//            runOnUiThread {
//                binding.apply {
//                    totalAmountTextView.text = "₱ %.2f".format(subtotal)
//                    discountAmountText.text = "₱ %.2f".format(discount)
//                    vatAmountText.text = "₱ %.2f".format(vatAmount)
//
//                    if (partialPayment > 0) {
//                        partialLabel.visibility = View.VISIBLE
//                        partialPaymentTextView.visibility = View.VISIBLE
//                        partialPaymentTextView.text = "₱ %.2f".format(partialPayment)
//                        finalTotalText.text = "₱ %.2f".format(finalTotal)
//                    } else {
//                        partialLabel.visibility = View.GONE
//                        partialPaymentTextView.visibility = View.GONE
//                        finalTotalText.text = "₱ %.2f".format(discountedTotal)
//                    }
//                }
//            }
//        }
//
//        // Handle transaction comment (both layouts)
//        val commentText = if (transactionComment.isNotEmpty()) "Note: $transactionComment" else ""
//        runOnUiThread {
//            findViewById<TextView>(R.id.commentView)?.apply {
//                text = commentText
//                visibility = if (commentText.isNotEmpty()) View.VISIBLE else View.GONE
//            }
//        }
//    }

    private fun updateTotalAmount(cartItems: List<CartItem>) {
        Log.d(TAG, "Updating total amount for ${cartItems.size} items")

        var subtotal = 0.0
        var discount = 0.0
        var vatAmount = 0.0
        var partialPayment = 0.0

        cartItems.forEach { cartItem ->
            val effectivePrice = cartItem.overriddenPrice ?: cartItem.price
            val itemTotal = effectivePrice * cartItem.quantity
            subtotal += itemTotal

            when (cartItem.discountType.uppercase()) {
                "PERCENTAGE", "PWD", "SC" -> discount += itemTotal * (cartItem.discount / 100)
                "FIXED" -> discount += cartItem.discount * cartItem.quantity
                "FIXEDTOTAL" -> {
                    // For FIXEDTOTAL, use discountAmount instead of discount
                    discount += cartItem.discountAmount
                }
            }

            vatAmount += itemTotal * 0.12 / 1.12

            if (cartItem.partialPayment > 0) {
                partialPayment = cartItem.partialPayment
            }
        }

        val discountedTotal = subtotal - discount
        val finalTotal = discountedTotal - partialPayment

        Log.d(TAG, "Calculated totals - Subtotal: $subtotal, Discount: $discount, VAT: $vatAmount, Final: $finalTotal")

        // Rest of the updateTotalAmount function remains the same...
        // Update UI based on layout type
        if (isMobileLayout) {
            // Update mobile cart bottom sheet
            runOnUiThread {
                findViewById<TextView>(R.id.totalAmountTextView)?.text = "₱ %.2f".format(subtotal)
                findViewById<TextView>(R.id.discountAmountText)?.text = "₱ %.2f".format(discount)
                findViewById<TextView>(R.id.vatAmountText)?.text = "₱ %.2f".format(vatAmount)

                if (partialPayment > 0) {
                    findViewById<TextView>(R.id.partialLabel)?.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.partialPaymentTextView)?.apply {
                        visibility = View.VISIBLE
                        text = "₱ %.2f".format(partialPayment)
                    }
                    findViewById<TextView>(R.id.finalTotalText)?.text = "₱ %.2f".format(finalTotal)
                } else {
                    findViewById<TextView>(R.id.partialLabel)?.visibility = View.GONE
                    findViewById<TextView>(R.id.partialPaymentTextView)?.visibility = View.GONE
                    findViewById<TextView>(R.id.finalTotalText)?.text = "₱ %.2f".format(discountedTotal)
                }
            }
        } else {
            // Update tablet sidebar (existing code)
            runOnUiThread {
                binding.apply {
                    totalAmountTextView.text = "₱ %.2f".format(subtotal)
                    discountAmountText.text = "₱ %.2f".format(discount)
                    vatAmountText.text = "₱ %.2f".format(vatAmount)

                    if (partialPayment > 0) {
                        partialLabel.visibility = View.VISIBLE
                        partialPaymentTextView.visibility = View.VISIBLE
                        partialPaymentTextView.text = "₱ %.2f".format(partialPayment)
                        finalTotalText.text = "₱ %.2f".format(finalTotal)
                    } else {
                        partialLabel.visibility = View.GONE
                        partialPaymentTextView.visibility = View.GONE
                        finalTotalText.text = "₱ %.2f".format(discountedTotal)
                    }
                }
            }
        }

        // Handle transaction comment (both layouts)
        val commentText = if (transactionComment.isNotEmpty()) "Note: $transactionComment" else ""
        runOnUiThread {
            findViewById<TextView>(R.id.commentView)?.apply {
                text = commentText
                visibility = if (commentText.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
    // Enhanced loading state function with text
    private fun showProductLoadingState(isLoading: Boolean) {
        if (isMobileLayout) {
            // Mobile layout loading indicators
//            findViewById<View>(R.id.productLoadingIndicator)?.visibility = if (isLoading) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.loadingText)?.visibility = if (isLoading) View.VISIBLE else View.GONE
            findViewById<RecyclerView>(R.id.recyclerview)?.alpha = if (isLoading) 0.3f else 1.0f
            findViewById<TextView>(R.id.textView3)?.alpha = if (isLoading) 0.5f else 1.0f
            findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)?.isEnabled = !isLoading
        } else {
            // Tablet layout loading indicators
            binding.apply {
                productLoadingIndicator?.visibility = if (isLoading) View.VISIBLE else View.GONE
//                loadingText?.visibility = if (isLoading) View.VISIBLE else View.GONE
                recyclerview?.alpha = if (isLoading) 0.3f else 1.0f
                textView3?.alpha = if (isLoading) 0.5f else 1.0f
                searchView?.isEnabled = !isLoading
            }
        }
    }

    // Function to update loading text dynamically
    private fun updateLoadingText(message: String) {
        if (isMobileLayout) {
            findViewById<TextView>(R.id.loadingText)?.text = message
        } else {
//            binding.loadingText?.text = message
        }
    }

    // Enhanced loadWindowSpecificProducts with dynamic loading messages
    private fun loadWindowSpecificProducts() {
        lifecycleScope.launch {
            try {
                // Show loading state at the beginning
                withContext(Dispatchers.Main) {
                    showProductLoadingState(true)
                }

                val window = windowViewModel.allWindows.first().find { it.id == windowId }
                if (window != null) {
                    val description = window.description.uppercase()
                    Log.d("Window1", "Loading products for window: $description")

                    // Get visible products instead of all products
                    val allProducts = productViewModel.visibleProducts.value ?: emptyList()
                    Log.d("Window1", "Total visible products: ${allProducts.size}")

                    // Get platform-specific hidden products
                    val platformName = when {
                        description.contains("GRABFOOD")  -> "GRABFOOD"
                        description.contains("FOODPANDA")  -> "FOODPANDA"
                        description.contains("MANILARATE") -> "MANILARATE"
                        description.contains("MALLPRICE") -> "MALLPRICE"
                        description.contains("GRABFOODMALL") -> "GRABFOODMALL"
                        description.contains("FOODPANDAMALL") -> "FOODPANDAMALL"
                        description.contains("PURCHASE") -> "PURCHASE"
                        description.contains("GENERAL") -> "GENERAL"
                        else -> "GENERAL"
                    }

                    // Get hidden products for the specific platform
                    val hiddenProducts = try {
                        productViewModel.getHiddenProductsForPlatform(platformName).map { it.productId }.toSet()
                    } catch (e: Exception) {
                        Log.e("Window1", "Error getting platform-specific hidden products, using general", e)
                        productViewModel.getHiddenProducts().value?.map { it.productId }?.toSet() ?: emptySet()
                    }

                    Log.d("Window1", "Hidden products for $platformName: ${hiddenProducts.size}")

                    // Debug: Log products with zero prices
                    val productsWithZeroPrices = allProducts.filter { product ->
                        when {
                            description.contains("GRABFOOD")  -> product.grabfood == 0.0
                            description.contains("FOODPANDA")  -> product.foodpanda == 0.0
                            description.contains("MANILARATE") -> product.manilaprice == 0.0
                            description.contains("MALLPRICE") -> product.mallprice == 0.0
                            description.contains("GRABFOODMALL") -> product.grabfoodmall == 0.0
                            description.contains("FOODPANDAMALL") -> product.foodpandamall == 0.0
                            description.contains("PURCHASE") -> product.price == 0.0
                            else -> product.price == 0.0
                        }
                    }
                    Log.d("Window1", "Products with zero prices for $description: ${productsWithZeroPrices.size}")

                    // Filter products based on window description AND visibility
                    val windowFilteredProducts = when {
                        description.contains("GRABFOOD") -> {
                            allProducts.filter { product ->
                                product.grabfood > 0.0 && product.id !in hiddenProducts
                            }.map { product ->
                                product.copy(price = product.grabfood)
                            }
                        }
                        description.contains("FOODPANDA")  -> {
                            allProducts.filter { product ->
                                product.foodpanda > 0.0 && product.id !in hiddenProducts
                            }.map { product ->
                                product.copy(price = product.foodpanda)
                            }
                        }
                        description.contains("MANILARATE") -> {
                            allProducts.filter { product ->
                                product.manilaprice > 0.0 && product.id !in hiddenProducts
                            }.map { product ->
                                product.copy(price = product.manilaprice)
                            }
                        }
                        description.contains("MALLPRICE") -> {
                            allProducts.filter { product ->
                                product.mallprice > 0.0 && product.id !in hiddenProducts
                            }.map { product ->
                                product.copy(price = product.mallprice)
                            }
                        }
                        description.contains("GRABFOODMALL") -> {
                            allProducts.filter { product ->
                                product.grabfoodmall > 0.0 && product.id !in hiddenProducts
                            }.map { product ->
                                product.copy(price = product.grabfoodmall)
                            }
                        }
                        description.contains("FOODPANDAMALL") -> {
                            allProducts.filter { product ->
                                product.foodpandamall > 0.0 && product.id !in hiddenProducts
                            }.map { product ->
                                product.copy(price = product.foodpandamall)
                            }
                        }
                        description.contains("PARTYCAKES") -> {
                            allProducts.filter { product ->
                                product.itemGroup.equals("PARTY CAKES", ignoreCase = true) &&
                                        product.price > 0.0 && product.id !in hiddenProducts
                            }
                        }
                        description.contains("PURCHASE") -> {
                            // For PURCHASE window, filter out hidden products
                            val result = allProducts.filter { product ->
                                product.id !in hiddenProducts
                            }
                            Log.d("WindowFilter", "PURCHASE filter: ${result.size} products (filtered by visibility)")
                            result
                        }
                        description.contains("GENERAL") -> {
                            // For GENERAL window, filter out hidden products
                            val result = allProducts.filter { product ->
                                product.id !in hiddenProducts
                            }
                            Log.d("WindowFilter", "GENERAL filter: ${result.size} products (filtered by visibility)")
                            result
                        }
                        else -> {
                            // Default case: only show products with valid price and not hidden
                            allProducts.filter { product ->
                                product.price > 0.0 && product.id !in hiddenProducts
                            }
                        }
                    }

                    Log.d("Window1", "Window filtered products (after visibility check): ${windowFilteredProducts.size}")

                    // Apply current search if exists
                    val searchQuery = productViewModel.persistentSearchQuery.value
                    val searchFilteredProducts = if (!searchQuery.isNullOrBlank()) {
                        windowFilteredProducts.filter { product ->
                            product.itemName.contains(searchQuery, ignoreCase = true) ||
                                    product.itemGroup.contains(searchQuery, ignoreCase = true) ||
                                    product.barcode.toString().contains(searchQuery, ignoreCase = true)
                        }
                    } else {
                        windowFilteredProducts
                    }

                    Log.d("Window1", "Search filtered products: ${searchFilteredProducts.size}")

                    // Apply category filter if exists
                    val selectedCategory = productViewModel.selectedCategory.value
                    val finalProducts = when {
                        selectedCategory == null || selectedCategory.name == "All" -> {
                            searchFilteredProducts
                        }
                        selectedCategory.name == "Mix & Match" -> {
                            // Handle Mix & Match category if needed
                            searchFilteredProducts
                        }
                        else -> {
                            searchFilteredProducts.filter { product ->
                                product.itemGroup.equals(selectedCategory.name, ignoreCase = true)
                            }
                        }
                    }

                    Log.d("Window1", "Final filtered products: ${finalProducts.size}")

                    withContext(Dispatchers.Main) {
                        Log.d("Window1", "Displaying ${finalProducts.size} products")
                        productAdapter.submitList(finalProducts)
                        updateAvailableCategories(finalProducts)
                        findViewById<TextView>(R.id.textView3)?.text =
                            "Products (${finalProducts.size})"

                        // Hide loading state when done
                        showProductLoadingState(false)
                    }
                } else {
                    Log.e("Window1", "Window not found for id: $windowId")
                    withContext(Dispatchers.Main) {
                        showProductLoadingState(false)
                    }
                }
            } catch (e: Exception) {
                Log.e("Window1", "Error loading window-specific products", e)
                withContext(Dispatchers.Main) {
                    showProductLoadingState(false)
                    Toast.makeText(
                        this@Window1,
                        "Error loading products: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


//    private fun showProductLoadingState(isLoading: Boolean) {
//        if (isMobileLayout) {
//            // Mobile layout loading indicators
//            findViewById<View>(R.id.productLoadingIndicator)?.visibility = if (isLoading) View.VISIBLE else View.GONE
//            findViewById<RecyclerView>(R.id.recyclerview)?.alpha = if (isLoading) 0.5f else 1.0f
//            findViewById<TextView>(R.id.textView3)?.alpha = if (isLoading) 0.5f else 1.0f
//            findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView)?.isEnabled = !isLoading
//        } else {
//            // Tablet layout loading indicators
//            binding.apply {
//                productLoadingIndicator?.visibility = if (isLoading) View.VISIBLE else View.GONE
//                recyclerview?.alpha = if (isLoading) 0.5f else 1.0f
//                textView3?.alpha = if (isLoading) 0.5f else 1.0f
//                searchView?.isEnabled = !isLoading
//            }
//        }
//    }

    private fun getWindowTypeLabel(windowType: String): String {
        return when {
            windowType.contains("GRABFOOD") -> "GRABFOOD"
            windowType.contains("FOODPANDA") -> "FOODPANDA"
            windowType.contains("MANILARATE") -> "MANILARATE"
            windowType.contains("GRABFOODMALL") -> "GRABFOODMALL"
            windowType.contains("FOODPANDAMALL") -> "FOODPANDAMALL"
            windowType.contains("MALLPRICE") -> "MALLPRICE"
            else -> "Default"
        }
    }
    private fun onWindowChangedFromString(newWindowIdString: String) {
        try {
            this.windowId = newWindowIdString.toInt()
            loadWindowSpecificProducts()
        } catch (e: NumberFormatException) {
            Log.e("Window1", "Invalid window ID format: $newWindowIdString")
            Toast.makeText(this, "Invalid window ID", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to handle category changes with loading
    private fun onCategoryChanged() {
        loadWindowSpecificProducts()
    }

//    private fun getWindowTypeLabel(windowType: String): String {
//        return when {
//            windowType.contains("GRABFOOD") -> "GRABFOOD"
//            windowType.contains("FOODPANDA") -> "FOODPANDA"
//            windowType.contains("MANILARATE") -> "MANILARATE"
//            else -> "Default"
//        }
//    }



//    private fun addToCart(product: Product) {
//        // First check if transactions are disabled after Z-Read
//        if (isTransactionDisabledAfterZRead) {
//            Toast.makeText(
//                this,
//                "Transactions are disabled. Z-Read completed for today. Please wait until next day.",
//                Toast.LENGTH_LONG
//            ).show()
//            return
//        }
//
//        checkStaffAndProceed {
//            if (currentCashFund <= 0) {
//                Toast.makeText(
//                    this,
//                    "Cannot perform transactions. Please set a cash fund.",
//                    Toast.LENGTH_LONG
//                ).show()
//                return@checkStaffAndProceed
//            }
//
//            // Show loading state immediately
//            showLoadingState(true)
//
//            lifecycleScope.launch {
//                try {
//                    // Check Z-Read status before allowing transaction
//                    val hasZReadToday = hasZReadForToday()
//                    if (hasZReadToday && isAfterMidnight()) {
//                        withContext(Dispatchers.Main) {
//                            showLoadingState(false)
//                            Toast.makeText(
//                                this@Window1,
//                                "Cannot add items. Z-Read completed for today.",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                        return@launch
//                    }
//
//                    // Get fresh cart items data
//                    val existingItems = cartViewModel.getAllCartItems(windowId).first()
//                    val partialPayment = existingItems.firstOrNull()?.partialPayment ?: 0.0
//
//                    // Log the product we're trying to add
//                    Log.d(TAG, "Adding product: ${product.itemName} (${product.itemid})")
//
//                    // Check if there's a matching discount for this product
//                    var matchingDiscount: Discount? = null
//
//                    // Get loaded discounts
//                    val loadedDiscounts = discountViewModel.discounts.value
//
//                    if (!loadedDiscounts.isNullOrEmpty()) {
//                        // Try to find matching discount by comparing product name with discount offer name
//                        matchingDiscount = loadedDiscounts.find { discount ->
//                            discount.DISCOFFERNAME.trim().equals(product.itemName.trim(), ignoreCase = true)
//                        }
//
//                        if (matchingDiscount != null) {
//                            Log.d(TAG, "Found matching discount: ${matchingDiscount.DISCOFFERNAME} for product: ${product.itemName}")
//                        } else {
//                            Log.d(TAG, "No matching discount found for product: ${product.itemName}")
//                        }
//                    } else {
//                        Log.d(TAG, "No discounts loaded")
//                    }
//
//                    // Find matching items in cart
//                    val existingNonDiscountedItem = existingItems.find { item ->
//                        item.productId == product.id &&
//                                item.discount == 0.0 &&
//                                item.discountType.isEmpty() &&
//                                item.bundleId == null
//                    }
//
//                    val existingDiscountedItem = existingItems.find { item ->
//                        item.productId == product.id &&
//                                (item.discount > 0.0 ||
//                                        item.discountType.isNotEmpty() ||
//                                        item.bundleId != null)
//                    }
//
//                    // Perform cart operations
//                    if (matchingDiscount != null) {
//                        Log.d(TAG, "Applying discount: ${matchingDiscount.DISCOFFERNAME} (${matchingDiscount.PARAMETER} ${matchingDiscount.DISCOUNTTYPE})")
//
//                        // Show toast to confirm discount application
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                this@Window1,
//                                "Applied discount: ${matchingDiscount.DISCOFFERNAME} (${matchingDiscount.PARAMETER} ${matchingDiscount.DISCOUNTTYPE})",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//
//                        // Create new item with discount applied
//                        cartViewModel.insert(
//                            CartItem(
//                                productId = product.id,
//                                quantity = 1,
//                                windowId = windowId,
//                                productName = product.itemName,
//                                price = product.price,
//                                partialPayment = partialPayment,
//                                vatAmount = 0.0,
//                                vatExemptAmount = 0.0,
//                                itemGroup = product.itemGroup,
//                                itemId = product.itemid,
//                                discount = matchingDiscount.PARAMETER.toDouble(),
//                                discountType = matchingDiscount.DISCOUNTTYPE,
//                                discountName = matchingDiscount.DISCOFFERNAME
//                            )
//                        )
//
//                    } else when {
//                        // If clicking a non-discounted item
//                        existingNonDiscountedItem != null -> {
//                            // Update quantity of existing non-discounted item
//                            cartViewModel.update(
//                                existingNonDiscountedItem.copy(
//                                    quantity = existingNonDiscountedItem.quantity + 1,
//                                    partialPayment = partialPayment
//                                )
//                            )
//                        }
//                        // If the product exists but only as a discounted item, create new non-discounted entry
//                        existingDiscountedItem != null -> {
//                            // Create new cart item without discount
//                            cartViewModel.insert(
//                                CartItem(
//                                    productId = product.id,
//                                    quantity = 1,
//                                    windowId = windowId,
//                                    productName = product.itemName,
//                                    price = product.price,
//                                    partialPayment = partialPayment,
//                                    vatAmount = 0.0,
//                                    vatExemptAmount = 0.0,
//                                    itemGroup = product.itemGroup,
//                                    itemId = product.itemid,
//                                    discount = 0.0,
//                                    discountType = ""
//                                )
//                            )
//                        }
//                        // If the product doesn't exist in cart at all
//                        else -> {
//                            cartViewModel.insert(
//                                CartItem(
//                                    productId = product.id,
//                                    quantity = 1,
//                                    windowId = windowId,
//                                    productName = product.itemName,
//                                    price = product.price,
//                                    partialPayment = partialPayment,
//                                    vatAmount = 0.0,
//                                    vatExemptAmount = 0.0,
//                                    itemGroup = product.itemGroup,
//                                    itemId = product.itemid,
//                                    discount = 0.0,
//                                    discountType = ""
//                                )
//                            )
//                        }
//                    }
//
//                    Log.d(TAG, "Added/Updated cart item for product ${product.id} in window $windowId")
//
//                    // Wait a bit to ensure database operations complete
//                    delay(100)
//
//                    // Get updated cart items and refresh totals
//                    val updatedCartItems = cartViewModel.getAllCartItems(windowId).first()
//
//                    withContext(Dispatchers.Main) {
//                        updateTotalAmount(updatedCartItems)
//                        showLoadingState(false)
//                    }
//
//                } catch (e: Exception) {
//                    Log.e(TAG, "Error adding to cart: ${e.message}", e)
//                    withContext(Dispatchers.Main) {
//                        showLoadingState(false)
//                        Toast.makeText(
//                            this@Window1,
//                            "Error adding item to cart. Please try again.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//        }
//    } working



//    private fun Double.roundToTwoDecimals(): Double {
//        return (this * 100).roundToInt() / 100.0
//    }
    private fun setupCustomNumberKeyboard(
        dialogView: View,
        amountEditText: EditText,
        buttons: List<Button>,
        backspaceButton: Button
    ) {
        // Disable soft keyboard
        amountEditText.showSoftInputOnFocus = false
        amountEditText.isFocusable = true
        amountEditText.isFocusableInTouchMode = true

        // Prevent system keyboard from showing
        amountEditText.setOnClickListener {
            // Hide system keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(amountEditText.windowToken, 0)
        }

        // Set click listener for number buttons
        val numberButtonClickListener = View.OnClickListener { view ->
            val buttonText = (view as Button).text.toString()
            val currentText = amountEditText.text.toString()

            // Handle dot button with special logic to prevent multiple dots
            if (buttonText == ".") {
                if (!currentText.contains(".")) {
                    amountEditText.setText(currentText + buttonText)
                    amountEditText.setSelection(amountEditText.text.length)
                }
            } else {
                // Limit input to 2 decimal places
                val parts = currentText.split(".")
                if (parts.size == 2 && parts[1].length >= 2) {
                    return@OnClickListener
                }

                amountEditText.setText(currentText + buttonText)
                amountEditText.setSelection(amountEditText.text.length)
            }
        }

        // Apply number button click listener
        buttons.forEach { button ->
            button.setOnClickListener(numberButtonClickListener)
        }

        // Backspace button logic
        backspaceButton.setOnClickListener {
            val currentText = amountEditText.text.toString()
            if (currentText.isNotEmpty()) {
                amountEditText.setText(currentText.substring(0, currentText.length - 1))
                amountEditText.setSelection(amountEditText.text.length)
            }
        }

        // Long press on backspace to clear
        backspaceButton.setOnLongClickListener {
            amountEditText.setText("")
            true
        }
    }



    private fun isChargePayment(paymentMethod: String): Boolean {
        return paymentMethod.equals("CHARGE", ignoreCase = true)
    }


    private fun isValidCustomerForCharge(customer: Customer?): Boolean {
        return customer != null &&
                customer.accountNum != "WALK-IN" &&
                customer.name !in listOf("Walk-in Customer", "Walk-in")
    }


    private var selectedCustomer = Customer(accountNum = "WALK-IN", name = "Walk-in Customer")

    private fun calculateSmartPayAmount(amount: Double): Double {
        val roundedAmount = when {
            amount <= 10 -> ceil(amount)
            amount <= 100 -> ceil(amount / 10) * 10
            amount <= 1000 -> ceil(amount / 100) * 100
            else -> ceil(amount / 1000) * 1000
        }
        return roundedAmount
    }


private fun showPointsRedemptionDialog(
    loyaltyCard: LoyaltyCard,
    totalAmount: Double,
    amountPaidEditText: EditText,
    splitPaymentSwitch: Switch,
    paymentMethodSpinner1: Spinner,
    paymentMethodSpinner2: Spinner?,
    secondPaymentLayout: LinearLayout,
    amountPaidEditText2: EditText
) {
    val dialogView = layoutInflater.inflate(R.layout.dialog_loyalty_points, null)
    val pointsAvailableText = dialogView.findViewById<TextView>(R.id.pointsAvailableText)
    val pointsToUseInput = dialogView.findViewById<EditText>(R.id.pointsToUseInput)
    val equivalentAmountText = dialogView.findViewById<TextView>(R.id.equivalentAmountText)
    val usePointsCheckbox = dialogView.findViewById<CheckBox>(R.id.usePointsCheckbox)

    pointsAvailableText.text = "Available Points: ${loyaltyCard.points}"
    val maxPoints = minOf(loyaltyCard.points, totalAmount.toInt()) // 1 point = 1 peso
    pointsToUseInput.hint = "Max points: $maxPoints"
    pointsToUseInput.isEnabled = false

    usePointsCheckbox.setOnCheckedChangeListener { _, isChecked ->
        pointsToUseInput.isEnabled = isChecked
        if (isChecked) {
            pointsToUseInput.setText(maxPoints.toString())
            equivalentAmountText.text = "Amount to Pay: ₱%.2f".format(maxPoints.toDouble())

            try {
                val adapter = paymentMethodSpinner1.adapter as ArrayAdapter<String>
                val position = adapter.getPosition("LOYALTYCARD")
                paymentMethodSpinner1.setSelection(position)
                paymentMethodSpinner1.isEnabled = false

                if (maxPoints < totalAmount) {
                    val remainingAmount = totalAmount - maxPoints
                    amountPaidEditText.setText(String.format("%.2f", maxPoints.toDouble()))
                    amountPaidEditText.isEnabled = false
                    splitPaymentSwitch.isChecked = true
                    secondPaymentLayout.visibility = View.VISIBLE
                    amountPaidEditText2.setText(String.format("%.2f", remainingAmount))
                    amountPaidEditText2.isEnabled = false

                    paymentMethodSpinner2?.let { spinner2 ->
                        val methods = mutableListOf<String>()
                        val adapter1 = paymentMethodSpinner1.adapter as ArrayAdapter<String>
                        for (i in 0 until adapter1.count) {
                            val method = adapter1.getItem(i)
                            if (method != null && method != "LOYALTYCARD") {
                                methods.add(method)
                            }
                        }

                        val adapter2 = ArrayAdapter(
                            this,
                            android.R.layout.simple_spinner_item,
                            methods
                        ).apply {
                            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        }
                        spinner2.adapter = adapter2
                        spinner2.setSelection(0)
                        spinner2.isEnabled = true
                    }
                } else {
                    amountPaidEditText.setText(String.format("%.2f", maxPoints.toDouble()))
                    amountPaidEditText.isEnabled = false
                    splitPaymentSwitch.isChecked = false
                    secondPaymentLayout.visibility = View.GONE
                }
            } catch (e: Exception) {
                Log.e("Payment", "Error setting payment method", e)
            }
        } else {
            resetPaymentFields(
                pointsToUseInput,
                equivalentAmountText,
                amountPaidEditText,
                paymentMethodSpinner1,
                splitPaymentSwitch,
                secondPaymentLayout,
                totalAmount
            )
        }
    }

    val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle)
        .setTitle("Redeem Points")
        .setView(dialogView)
        .setPositiveButton("Redeem") { _, _ ->
            val pointsToUse = pointsToUseInput.text.toString().toIntOrNull() ?: 0
            if (usePointsCheckbox.isChecked && pointsToUse > 0 && pointsToUse <= maxPoints) {
                try {
                    setupPointsPayment(
                        pointsToUse,
                        totalAmount,
                        paymentMethodSpinner1,
                        amountPaidEditText,
                        splitPaymentSwitch,
                        secondPaymentLayout,
                        amountPaidEditText2
                    )
                } catch (e: Exception) {
                    Log.e("Payment", "Error setting up payment", e)
                    Toast.makeText(
                        this@Window1,
                        "Error setting up payment. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        .create()

    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
    dialog.show()
}

    private fun resetPaymentFields(
        pointsToUseInput: EditText,
        equivalentAmountText: TextView,
        amountPaidEditText: EditText,
        paymentMethodSpinner1: Spinner,
        splitPaymentSwitch: Switch,
        secondPaymentLayout: LinearLayout,
        totalAmount: Double
    ) {
        pointsToUseInput.setText("")
        equivalentAmountText.text = ""
        amountPaidEditText.setText(String.format("%.2f", totalAmount))
        amountPaidEditText.isEnabled = true
        paymentMethodSpinner1.setSelection(0)
        paymentMethodSpinner1.isEnabled = true
        splitPaymentSwitch.isChecked = false
        secondPaymentLayout.visibility = View.GONE
    }

    private fun setupPointsPayment(
        pointsToUse: Int,
        totalAmount: Double,
        paymentMethodSpinner1: Spinner,
        amountPaidEditText: EditText,
        splitPaymentSwitch: Switch,
        secondPaymentLayout: LinearLayout,
        amountPaidEditText2: EditText
    ) {
        val adapter = paymentMethodSpinner1.adapter as ArrayAdapter<String>
        val position = adapter.getPosition("LOYALTYCARD")
        paymentMethodSpinner1.setSelection(position)
        paymentMethodSpinner1.isEnabled = false

        amountPaidEditText.setText(String.format("%.2f", pointsToUse.toDouble()))
        amountPaidEditText.isEnabled = false

        if (pointsToUse < totalAmount) {
            val remainingAmount = totalAmount - pointsToUse
            splitPaymentSwitch.isChecked = true
            secondPaymentLayout.visibility = View.VISIBLE
            amountPaidEditText2.setText(String.format("%.2f", remainingAmount))
            amountPaidEditText2.isEnabled = false
        } else {
            splitPaymentSwitch.isChecked = false
            secondPaymentLayout.visibility = View.GONE
        }
    }
    private fun showPaymentDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_payment, null)
        val amountPaidEditText = dialogView.findViewById<EditText>(R.id.amountPaidEditText1)
        val paymentMethodSpinner = dialogView.findViewById<Spinner>(R.id.paymentMethodSpinner1)
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Add this variable for payment processing state
        var isProcessingPayment = false

        // Purchase History Setup
        val purchaseHistoryContainer = dialogView.findViewById<LinearLayout>(R.id.purchaseHistoryContainer)
        val purchaseHistoryRecyclerView = dialogView.findViewById<RecyclerView>(R.id.purchaseHistoryRecyclerView)
        val frequentlyBoughtHeader = dialogView.findViewById<TextView>(R.id.frequentlyBoughtHeader)

        val purchaseHistoryAdapter = PurchaseHistoryAdapter { product ->
            addToCart(product)
        }

        purchaseHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = purchaseHistoryAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        // Promo Setup
        val promoSuggestionsContainer = dialogView.findViewById<LinearLayout>(R.id.promoSuggestionsContainer)
        val promoSuggestionsRecyclerView = dialogView.findViewById<RecyclerView>(R.id.promoSuggestionsRecyclerView)
        val promoHeader = dialogView.findViewById<TextView>(R.id.promoHeader)

        val promoAdapter = PromoSuggestionAdapter { mixMatch ->
            showMixMatchProductSelection(mixMatch)
        }

        promoSuggestionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = promoAdapter
        }

        // Show initial available promos
        lifecycleScope.launch {
            val availablePromos = mixMatchViewModel.getAvailablePromos()
            promoAdapter.submitList(availablePromos)
            promoSuggestionsContainer.visibility =
                if (availablePromos.isEmpty()) View.GONE else View.VISIBLE
        }

        // Function to update promo suggestions based on cart items
        fun updatePromoSuggestions() {
            lifecycleScope.launch {
                val cartItems = cartViewModel.getAllCartItems(windowId).first()
                val suggestions = mixMatchViewModel.findPromoSuggestionsForCart(cartItems)
                promoAdapter.submitList(suggestions)
                promoSuggestionsContainer.visibility =
                    if (suggestions.isEmpty()) View.GONE else View.VISIBLE
            }
        }

        // Update suggestions when cart changes
        lifecycleScope.launch {
            cartViewModel.getAllCartItems(windowId).collect { cartItems ->
                updatePromoSuggestions()
            }
        }

        fun clearPurchaseHistory() {
            purchaseHistoryAdapter.submitList(emptyList())
            purchaseHistoryContainer.visibility = View.GONE
        }

        if (paymentMethodSpinner == null) {
            Log.e(TAG, "Payment method spinner is null! Check your layout XML.")
            Toast.makeText(this, "Error initializing payment dialog", Toast.LENGTH_SHORT).show()
            return
        }

        // Split Payment Setup
        val paymentMethodSpinner2 = dialogView.findViewById<Spinner>(R.id.paymentMethodSpinner2)
        if (paymentMethodSpinner2 == null) {
            Log.e(TAG, "Second payment method spinner is null!")
        }

        val customerAutoComplete = dialogView.findViewById<AutoCompleteTextView>(R.id.customerAutoComplete)
        val totalAmountTextView = dialogView.findViewById<TextView>(R.id.totalAmountTextView)
        val splitPaymentSwitch = dialogView.findViewById<Switch>(R.id.splitPaymentSwitch)
        val secondPaymentLayout = dialogView.findViewById<LinearLayout>(R.id.secondPaymentLayout)
        val amountPaidEditText2 = dialogView.findViewById<EditText>(R.id.amountPaidEditText2)

        val viewPointsButton = Button(this).apply {
            id = View.generateViewId()
            text = "View Points"
            visibility = View.GONE
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 8
            }
        }

        val scanLoyaltyCardButton = ImageButton(this).apply {
            setImageResource(R.drawable.ic_barcode_scan)
            background = ContextCompat.getDrawable(context, R.drawable.rounded_button_background)
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(40),
                dpToPx(40)
            ).apply {
                marginStart = dpToPx(8)
                gravity = Gravity.CENTER_VERTICAL
            }
            contentDescription = "Scan loyalty card"

            setOnClickListener {
                showLoyaltyCardScanner(customerAutoComplete)
            }
        }

        // Find the parent container of the AutoCompleteTextView
        val customerContainer = customerAutoComplete.parent as ViewGroup
        val index = customerContainer.indexOfChild(customerAutoComplete)

        // Create a horizontal LinearLayout to hold both views
        val horizontalLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = customerAutoComplete.layoutParams
        }

        // Remove AutoCompleteTextView from its parent
        customerContainer.removeView(customerAutoComplete)

        // Add AutoCompleteTextView to the horizontal layout
        horizontalLayout.addView(customerAutoComplete.apply {
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        })

        // Add the scan button to the horizontal layout
        horizontalLayout.addView(scanLoyaltyCardButton)

        // Add the horizontal layout to the original parent at the same position
        customerContainer.addView(horizontalLayout, index)

        // Payment variables setup
        val defaultPaymentMethods = listOf("Cash")
        val paymentMethods = mutableListOf<String>()
        var totalAmount = 0.0
        var discountType = "No Discount"
        var discountValue = 0.0
        var partialPayment = 0.0

        // Quick payment buttons setup
        val quickPaymentButtons = listOf(
            dialogView.findViewById<Button>(R.id.btn1000),
            dialogView.findViewById<Button>(R.id.btn500),
            dialogView.findViewById<Button>(R.id.btn200),
            dialogView.findViewById<Button>(R.id.btn100),
            dialogView.findViewById<Button>(R.id.btn50),
            dialogView.findViewById<Button>(R.id.btn20)
        )
        val smartPayButton = dialogView.findViewById<Button>(R.id.btnSmartPay)
        val quickPaymentScrollView = dialogView.findViewById<HorizontalScrollView>(R.id.quickPaymentScrollView)

        fun updateSmartPayButton(totalAmount: Double) {
            val smartPayAmount = calculateSmartPayAmount(totalAmount)
            smartPayButton.text = "₱${String.format("%.0f", smartPayAmount)}"
            smartPayButton.isEnabled = smartPayAmount >= totalAmount
            smartPayButton.backgroundTintList = ColorStateList.valueOf(
                if (smartPayButton.isEnabled)
                    ContextCompat.getColor(this, R.color.smart_pay_enabled)
                else
                    ContextCompat.getColor(this, R.color.smart_pay_disabled)
            )
        }

        var currentFocusedEditText: EditText = amountPaidEditText

        // EditText setup for decimal keyboard
        fun setupEditText(editText: EditText) {
            editText.apply {
                inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
        }

        setupEditText(amountPaidEditText)
        setupEditText(amountPaidEditText2)

        // Focus change listeners
        amountPaidEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                currentFocusedEditText = amountPaidEditText
                amountPaidEditText.setText("")
            }
        }
        amountPaidEditText2.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                currentFocusedEditText = amountPaidEditText2
            }
        }

        // Text change listener for second payment amount
        amountPaidEditText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (splitPaymentSwitch.isChecked) {
                    val secondAmount = s?.toString()?.toDoubleOrNull() ?: 0.0

                    if (secondAmount > totalAmount) {
                        splitPaymentSwitch.isChecked = false
                        amountPaidEditText.setText(String.format("%.2f", totalAmount))
                        Toast.makeText(
                            this@Window1,
                            "Split payment disabled - amount exceeds total",
                            Toast.LENGTH_SHORT
                        ).show()
                        return
                    }

                    val remainingAmount = totalAmount - secondAmount
                    if (currentFocusedEditText == amountPaidEditText2) {
                        amountPaidEditText.setText(String.format("%.2f", remainingAmount.coerceAtLeast(0.0)))
                    }
                }
            }
        })

        // Payment method spinner listener
        paymentMethodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMethod = paymentMethodSpinner.selectedItem.toString().uppercase()
                if (selectedMethod == "LOYALTYCARD") {
                    if (!selectedCustomer.accountNum.startsWith("LC-")) {
                        Toast.makeText(
                            this@Window1,
                            "Please enter a loyalty card number first",
                            Toast.LENGTH_SHORT
                        ).show()
                        paymentMethodSpinner.setSelection(0)
                        return
                    }

                    val cardNumber = selectedCustomer.accountNum.removePrefix("LC-")
                    lifecycleScope.launch {
                        val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(cardNumber)
                        if (loyaltyCard != null) {
                            val dialogView = layoutInflater.inflate(R.layout.dialog_loyalty_points, null)
                            val pointsAvailableText = dialogView.findViewById<TextView>(R.id.pointsAvailableText)
                            val usePointsCheckbox = dialogView.findViewById<CheckBox>(R.id.usePointsCheckbox)
                            val pointsToUseInput = dialogView.findViewById<EditText>(R.id.pointsToUseInput)
                            val equivalentAmountText = dialogView.findViewById<TextView>(R.id.equivalentAmountText)

                            pointsAvailableText.text = "Available Points: ${loyaltyCard.points}"
                            pointsToUseInput.isEnabled = false
                            usePointsCheckbox.isChecked = false

                            usePointsCheckbox.setOnCheckedChangeListener { _, isChecked ->
                                pointsToUseInput.isEnabled = isChecked
                                if (isChecked) {
                                    pointsToUseInput.hint = "Max points: ${loyaltyCard.points}"
                                }
                            }

                            pointsToUseInput.addTextChangedListener(object : TextWatcher {
                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                                override fun afterTextChanged(s: Editable?) {
                                    val points = s.toString().toIntOrNull() ?: 0
                                    if (points > loyaltyCard.points) {
                                        pointsToUseInput.error = "Insufficient points"
                                        return
                                    }
                                    val equivalent = points.toDouble()
                                    equivalentAmountText.text = "Amount to Pay: ₱%.2f".format(equivalent)
                                }
                            })

                            AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle)
                                .setTitle("Loyalty Card Points")
                                .setView(dialogView)
                                .setPositiveButton("Confirm") { _, _ ->
                                    if (usePointsCheckbox.isChecked) {
                                        val pointsToUse = pointsToUseInput.text.toString().toIntOrNull() ?: 0
                                        if (pointsToUse > loyaltyCard.points) {
                                            Toast.makeText(this@Window1, "Insufficient points", Toast.LENGTH_SHORT).show()
                                            paymentMethodSpinner.setSelection(0)
                                            return@setPositiveButton
                                        }
                                        amountPaidEditText.setText(pointsToUse.toString())
                                        amountPaidEditText.isEnabled = false
                                    } else {
                                        paymentMethodSpinner.setSelection(0)
                                    }
                                }
                                .setNegativeButton("Cancel") { _, _ ->
                                    paymentMethodSpinner.setSelection(0)
                                }
                                .show()
                        } else {
                            Toast.makeText(this@Window1, "Invalid loyalty card", Toast.LENGTH_SHORT).show()
                            paymentMethodSpinner.setSelection(0)
                        }
                    }
                } else {
                    amountPaidEditText.isEnabled = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Split payment switch listener
        splitPaymentSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val currentPaymentMethod = paymentMethodSpinner.selectedItem?.toString() ?: ""

            if (isChecked) {
                if (totalAmount <= 0) {
                    smartPayButton.isEnabled = false
                    buttonView.isChecked = false
                    Toast.makeText(this@Window1, "Split payment not available - no remaining balance", Toast.LENGTH_SHORT).show()
                    return@setOnCheckedChangeListener
                }

                secondPaymentLayout.visibility = View.VISIBLE
                quickPaymentScrollView.visibility = View.GONE
                quickPaymentButtons.forEach { it.isEnabled = false }

                if (currentPaymentMethod == "LOYALTYCARD") {
                    val firstAmount = amountPaidEditText.text.toString().toDoubleOrNull() ?: 0.0
                    val remainingAmount = totalAmount - firstAmount

                    amountPaidEditText2.setText(String.format("%.2f", remainingAmount))
                    amountPaidEditText2.isEnabled = false
                }

                val firstPaymentMethod = paymentMethodSpinner.selectedItem?.toString()
                if (firstPaymentMethod != null) {
                    updateSecondPaymentMethods(firstPaymentMethod, paymentMethodSpinner2, paymentMethods)
                }

                setupEditText(amountPaidEditText2)
                amountPaidEditText2.requestFocus()
            } else {
                updateSmartPayButton(totalAmount)
                secondPaymentLayout.visibility = View.GONE
                amountPaidEditText2.setText("")
                amountPaidEditText.setText(String.format("%.2f", totalAmount))
                quickPaymentScrollView.visibility = View.VISIBLE
                quickPaymentButtons.forEach { it.isEnabled = true }
            }
        }

        // Cart items collection and total calculation
        lifecycleScope.launch {
            cartViewModel.getAllCartItems(windowId).collect { cartItems ->
                var gross = 0.0
                var totalDiscount = 0.0
                var vatAmount = 0.0
                var priceOverrideTotal = 0.0
                var bundleDiscount = 0.0

                cartItems.forEach { cartItem ->
                    val effectivePrice = cartItem.overriddenPrice ?: cartItem.price
                    val itemTotal = effectivePrice * cartItem.quantity
                    gross += itemTotal
                    partialPayment = cartItem.partialPayment

                    when (cartItem.discountType.toUpperCase()) {
                        "PERCENTAGE", "PWD", "SC" -> {
                            totalDiscount += itemTotal * (cartItem.discount / 100)
                            discountType = cartItem.discountType
                            discountValue = cartItem.discount
                        }
                        "FIXED" -> {
                            totalDiscount += cartItem.discount * cartItem.quantity
                            discountType = "FIXED"
                            discountValue = cartItem.discount
                        }
                        "FIXEDTOTAL", "FIXED TOTAL" -> {
                            totalDiscount += cartItem.discount
                            discountType = "FIXEDTOTAL"
                            discountValue = cartItem.discount
                        }
                        "DEAL", "PERCENTAGE", "FIXEDTOTAL" -> {
                            if (cartItem.bundleId != null) {
                                bundleDiscount += when (cartItem.discountType.toUpperCase()) {
                                    "DEAL" -> cartItem.discount
                                    "PERCENTAGE" -> itemTotal * (cartItem.discount / 100)
                                    "FIXEDTOTAL" -> cartItem.discount
                                    else -> 0.0
                                }
                            }
                        }
                    }

                    vatAmount += itemTotal * 0.12 / 1.12
                }

                totalDiscount += bundleDiscount
                val discountedTotal = gross - totalDiscount
                totalAmount = discountedTotal - partialPayment

                amountPaidEditText.setText(String.format("%.2f", totalAmount))
                updateSmartPayButton(totalAmount)

                if (totalAmount <= 0) {
                    splitPaymentSwitch.isChecked = false
                    splitPaymentSwitch.isEnabled = false
                } else {
                    splitPaymentSwitch.isEnabled = true
                }

                val formattedText = StringBuilder().apply {
                    append(String.format("Gross Amount: ₱%.2f", gross))
                    if (priceOverrideTotal != 0.0) {
                        append(String.format("\nPrice Override Adjustment: ₱%.2f", priceOverrideTotal))
                    }
                    append(String.format("\nVAT Amount: ₱%.2f", vatAmount))
                    if (totalDiscount > 0) {
                        append(String.format("\nTotal Discount: ₱%.2f", totalDiscount))
                        if (bundleDiscount > 0) {
                            append(String.format("\n  Bundle Discount: ₱%.2f", bundleDiscount))
                        }
                    }
                    append(String.format("\nDiscounted Total: ₱%.2f", discountedTotal))
                    if (partialPayment > 0) {
                        append(String.format("\nPartial Payment: ₱%.2f", partialPayment))
                        append(String.format("\nRemaining Balance: ₱%.2f", totalAmount))
                    }
                    if (transactionComment.isNotEmpty()) {
                        append("\nComment: $transactionComment")
                    }
                }.toString()

                totalAmountTextView.text = formattedText
            }
        }

        // Payment methods spinner setup
        lifecycleScope.launch {
            arViewModel.arTypes.collectLatest { arTypes ->
                withContext(Dispatchers.Main) {
                    paymentMethods.clear()
                    paymentMethods.addAll(defaultPaymentMethods)
                    if (arTypes.isNotEmpty()) {
                        paymentMethods.addAll(arTypes.map { it.ar })
                    }

                    updatePaymentMethodSpinner(paymentMethodSpinner, paymentMethods, customerAutoComplete)
                    updatePaymentMethodSpinner(paymentMethodSpinner2, paymentMethods, customerAutoComplete)
                }
            }

            launch {
                arViewModel.error.collectLatest { errorMessage ->
                    if (errorMessage != null) {
                        Log.e(TAG, "Error in AR ViewModel: $errorMessage")
                        paymentMethods.clear()
                        paymentMethods.addAll(defaultPaymentMethods)
                        updatePaymentMethodSpinner(paymentMethodSpinner, paymentMethods, customerAutoComplete)
                        updatePaymentMethodSpinner(paymentMethodSpinner2, paymentMethods, customerAutoComplete)
                    }
                }
            }
        }

        arViewModel.refreshARTypes()

        // Customer selection setup
        val customers = mutableListOf<Customer>()
        customers.add(selectedCustomer)

        val customerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            customers.map { it.name }
        )
        customerAutoComplete.setAdapter(customerAdapter)
        customerAutoComplete.setText("Walk-in Customer", false)

        customerAutoComplete.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (customerAutoComplete.text.toString() == "Walk-in Customer") {
                    customerAutoComplete.setText("")
                }
            }
        }

        lifecycleScope.launch {
            try {
                customerViewModel.refreshCustomers()
                customerViewModel.customers.collectLatest { customerList ->
                    updateCustomerList(customers, customerList, customerAdapter)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching customers: ${e.message}")
                updateCustomerList(customers, emptyList(), customerAdapter)
            }
        }

        // Create and show the dialog
        val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle)
            .setTitle("Payment")
            .setView(dialogView)
            .setPositiveButton("Pay", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        dialog.setCanceledOnTouchOutside(false)

        val shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation)

        dialog.window?.decorView?.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.rawX.toInt()
                val y = event.rawY.toInt()
                val dialogBounds = Rect()
                dialog.window?.findViewById<ViewGroup>(android.R.id.content)?.getGlobalVisibleRect(dialogBounds)

                if (!dialogBounds.contains(x, y)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION")
                        vibrator.vibrate(300)
                    }
                    dialogView.startAnimation(shakeAnimation)
                }
            }
            false
        }

        // Helper function to restore pay button
        fun restorePayButton(payButton: Button, originalText: CharSequence) {
            isProcessingPayment = false
            payButton.text = originalText
            payButton.isEnabled = true
        }

        // Handle dialog show and payment processing
        dialog.setOnShowListener {
            val payButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val smartPayCard = dialogView.findViewById<CardView>(R.id.cardSmartPay)
            val initialElevation = smartPayCard.elevation

            // Create animations for smart pay card
            val elevationAnimator = ValueAnimator.ofFloat(initialElevation, initialElevation + 8f).apply {
                duration = 8500
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
            }

            val scaleAnimator = ValueAnimator.ofFloat(1f, 1.05f).apply {
                duration = 800
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
            }

            elevationAnimator.addUpdateListener { animation ->
                smartPayCard.elevation = animation.animatedValue as Float
            }

            scaleAnimator.addUpdateListener { animation ->
                val scale = animation.animatedValue as Float
                smartPayCard.scaleX = scale
                smartPayCard.scaleY = scale
            }

            elevationAnimator.start()
            scaleAnimator.start()

            // Quick payment buttons setup
            val quickPaymentAmounts = listOf(1000.0, 500.0, 200.0, 100.0, 50.0, 20.0)
            quickPaymentButtons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    val amount = quickPaymentAmounts[index]
                    val paymentMethod = paymentMethodSpinner.selectedItem.toString()

                    if (isChargePayment(paymentMethod)) {
                        if (!isValidCustomerForCharge(selectedCustomer)) {
                            Toast.makeText(
                                this@Window1,
                                "Select a valid customer for charge payment",
                                Toast.LENGTH_SHORT
                            ).show()
                            customerAutoComplete.error = "Customer required for charge"
                            return@setOnClickListener
                        }
                    }

                    if (amount < totalAmount) {
                        Toast.makeText(
                            this@Window1,
                            "Insufficient payment amount: ₱$amount. Required: ₱${String.format("%.2f", totalAmount)}",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }

                    amountPaidEditText.setText(String.format("%.2f", amount))

                    val change = amount - totalAmount
                    if (change > 0) {
                        Toast.makeText(
                            this@Window1,
                            "Change: ₱${String.format("%.2f", change)}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    processPayment(
                        amount,
                        paymentMethod,
                        1.12,
                        discountType,
                        discountValue,
                        selectedCustomer,
                        totalAmount
                    )

                    dialog.dismiss()
                }
            }

            // Smart pay button setup
            smartPayButton.setOnClickListener {
                val smartPayAmount = calculateSmartPayAmount(totalAmount)
                val paymentMethod = paymentMethodSpinner.selectedItem.toString()

                if (isChargePayment(paymentMethod)) {
                    if (!isValidCustomerForCharge(selectedCustomer)) {
                        Toast.makeText(
                            this@Window1,
                            "Select a valid customer for charge payment",
                            Toast.LENGTH_SHORT
                        ).show()
                        customerAutoComplete.error = "Customer required for charge"
                        return@setOnClickListener
                    }
                }

                amountPaidEditText.setText(String.format("%.2f", smartPayAmount))

                val change = smartPayAmount - totalAmount
                if (change > 0) {
                    Toast.makeText(
                        this@Window1,
                        "Change: ₱${String.format("%.2f", change)}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                processPayment(
                    smartPayAmount,
                    paymentMethod,
                    1.12,
                    discountType,
                    discountValue,
                    selectedCustomer,
                    totalAmount
                )

                dialog.dismiss()
            }

            // Update spinner initialization and customer selection
            updatePaymentMethodSpinner(paymentMethodSpinner, paymentMethods, customerAutoComplete, payButton)
            updatePaymentMethodSpinner(paymentMethodSpinner2, paymentMethods, customerAutoComplete, payButton)

            setupCustomerSelection(
                customerAutoComplete,
                customers,
                customerAdapter,
                paymentMethodSpinner,
                paymentMethodSpinner2,
                payButton,
                dialogView
            ) { customer ->
                selectedCustomer = customer

                if (customer.accountNum == "WALK-IN") {
                    purchaseHistoryContainer.visibility = View.GONE
                    customerViewModel.clearPurchaseHistory()
                } else {
                    purchaseHistoryContainer.visibility = View.VISIBLE
                    customerViewModel.loadCustomerPurchaseHistory(customer.name)
                    frequentlyBoughtHeader.text = "Frequently Bought by ${customer.name}"
                }
            }

            // Observe purchase history changes
            lifecycleScope.launch {
                customerViewModel.purchaseHistory.collect { history ->
                    purchaseHistoryAdapter.submitList(history)
                    purchaseHistoryContainer.visibility =
                        if (history.isEmpty()) View.GONE else View.VISIBLE
                }
            }

            // Pay button click listener with loading state
            payButton.setOnClickListener {
                // Prevent multiple clicks during processing
                if (isProcessingPayment) {
                    return@setOnClickListener
                }

                // Start loading state
                isProcessingPayment = true
                val originalText = payButton.text
                payButton.text = "Processing..."
                payButton.isEnabled = false

                // Process payment in background
                lifecycleScope.launch {
                    try {
                        val isSplitPayment = splitPaymentSwitch.isChecked

                        // Collect payment methods and amounts
                        val paymentMethods = mutableListOf<String>()
                        val paymentAmounts = mutableListOf<Double>()

                        // First payment method
                        val paymentMethod1 = paymentMethodSpinner?.selectedItem?.toString() ?: run {
                            withContext(Dispatchers.Main) {
                                restorePayButton(payButton, originalText)
                                Toast.makeText(this@Window1, "Payment method not selected", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }

                        // Check if any payment method is CHARGE and validate customer
                        val isFirstCharge = isChargePayment(paymentMethod1)
                        var isSecondCharge = false

                        if (isSplitPayment) {
                            val paymentMethod2 = paymentMethodSpinner2?.selectedItem?.toString() ?: ""
                            isSecondCharge = isChargePayment(paymentMethod2)
                        }

                        val hasChargePayment = isFirstCharge || isSecondCharge

                        // Validate customer for any charge payment
                        if (hasChargePayment && !isValidCustomerForCharge(selectedCustomer)) {
                            withContext(Dispatchers.Main) {
                                restorePayButton(payButton, originalText)
                                Toast.makeText(
                                    this@Window1,
                                    "Please select a valid customer for charge payment",
                                    Toast.LENGTH_SHORT
                                ).show()
                                customerAutoComplete.error = "Customer required for charge"
                            }
                            return@launch
                        }

                        val amountPaid1 = amountPaidEditText?.text.toString().toDoubleOrNull() ?: run {
                            withContext(Dispatchers.Main) {
                                restorePayButton(payButton, originalText)
                                Toast.makeText(this@Window1, "Invalid payment amount", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }
                        paymentMethods.add(paymentMethod1)
                        paymentAmounts.add(amountPaid1)

                        // Second payment method if split payment
                        if (isSplitPayment) {
                            val paymentMethod2 = paymentMethodSpinner2.selectedItem.toString()
                            val amountPaid2 = amountPaidEditText2.text.toString().toDoubleOrNull() ?: 0.0

                            if (amountPaid2 > 0) {
                                paymentMethods.add(paymentMethod2)
                                paymentAmounts.add(amountPaid2)
                            }
                        }

                        // Validate total payment
                        val totalPaid = paymentAmounts.sum()

                        // Check if total paid is sufficient
                        if (totalPaid < totalAmount) {
                            withContext(Dispatchers.Main) {
                                restorePayButton(payButton, originalText)
                                Toast.makeText(this@Window1, "Insufficient payment amount", Toast.LENGTH_SHORT).show()
                            }
                            return@launch
                        }

                        val change = if (totalPaid > totalAmount) totalPaid - totalAmount else 0.0

                        // Add a small delay to simulate processing (optional)
                        delay(500) // 500ms delay - adjust as needed

                        // Process the payment (this is your existing processPayment call)
                        withContext(Dispatchers.IO) {
                            processPayment(
                                paymentAmounts[0],
                                paymentMethods[0],
                                1.12, // VAT rate
                                discountType,
                                discountValue,
                                selectedCustomer,
                                totalAmount,
                                // Pass other payment methods and amounts if split
                                otherPaymentMethods = if (paymentMethods.size > 1) paymentMethods.slice(1 until paymentMethods.size) else emptyList(),
                                otherPaymentAmounts = if (paymentAmounts.size > 1) paymentAmounts.slice(1 until paymentAmounts.size) else emptyList()
                            )
                        }

                        // Switch back to main thread for UI updates
                        withContext(Dispatchers.Main) {
                            // Restore button state
                            restorePayButton(payButton, originalText)

                            // Show change if applicable (only for non-charge payments)
                            if (change > 0 && !hasChargePayment) {
                                Toast.makeText(this@Window1, "Change: ₱${String.format("%.2f", change)}", Toast.LENGTH_SHORT).show()
                            } else if (hasChargePayment) {
                                Toast.makeText(this@Window1, "Payment processed successfully", Toast.LENGTH_SHORT).show()
                            }

                            dialog.dismiss()

                            // Show your change and receipt dialog here if needed
                            // showChangeAndReceiptDialog(...)
                        }

                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing payment", e)
                        withContext(Dispatchers.Main) {
                            restorePayButton(payButton, originalText)
                            Toast.makeText(
                                this@Window1,
                                "Payment processing failed: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        dialog.show()
    }

    private fun showLoyaltyCardScanner(customerAutoComplete: AutoCompleteTextView) {
        val scannerOverlay = findViewById<FrameLayout>(R.id.barcodeScannerOverlay)
        val previewView = findViewById<PreviewView>(R.id.previewView)
        val closeButton = findViewById<ImageButton>(R.id.closeButton)
        val scannerStatus = findViewById<TextView>(R.id.scannerStatus)

        // Reset state
        isProcessingBarcode = false
        lastScannedBarcode = null
        scannerOverlay.visibility = View.VISIBLE
        scannerStatus.text = "Scanning for loyalty card..."

        closeButton.setOnClickListener {
            scannerOverlay.visibility = View.GONE
            try {
                cameraProviderFuture.get().unbindAll()
            } catch (e: Exception) {
                Log.e(TAG, "Error unbinding camera", e)
            }
        }

        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                bindLoyaltyCardCameraPreview(
                    cameraProvider,
                    previewView,
                    scannerOverlay,
                    scannerStatus,
                    customerAutoComplete
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up camera preview", e)
                scannerStatus.text = "Error: ${e.message}"
            }
        }, ContextCompat.getMainExecutor(this))
    }

    // 3. Add the camera binding function specifically for loyalty cards
    private fun bindLoyaltyCardCameraPreview(
        cameraProvider: ProcessCameraProvider,
        previewView: PreviewView,
        overlay: FrameLayout,
        statusText: TextView,
        customerAutoComplete: AutoCompleteTextView
    ) {
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
            processLoyaltyCardImage(imageProxy, overlay, statusText, customerAutoComplete)
        }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysis
            )
            preview.setSurfaceProvider(previewView.surfaceProvider)
        } catch (e: Exception) {
            Log.e(TAG, "Error binding camera preview", e)
            statusText.text = "Camera Error"
        }
    }

    // 4. Add the image processing function for loyalty cards
    private fun processLoyaltyCardImage(
        imageProxy: ImageProxy,
        overlay: FrameLayout,
        statusText: TextView,
        customerAutoComplete: AutoCompleteTextView
    ) {
        if (isProcessingBarcode) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (!isProcessingBarcode && barcodes.isNotEmpty()) {
                        val barcode = barcodes.first().rawValue
                        val currentTime = System.currentTimeMillis()

                        if (barcode != null && barcode != lastScannedBarcode?.toString() &&
                            currentTime - lastScanTime > SCAN_COOLDOWN) {

                            isProcessingBarcode = true
                            lastScannedBarcode = barcode.toLongOrNull()
                            lastScanTime = currentTime

                            // Process the loyalty card barcode
                            handleLoyaltyCardBarcode(barcode, overlay, statusText, customerAutoComplete)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Barcode scanning failed", it)
                    statusText.text = "Scan failed"
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    // 5. Add the handler for loyalty card barcodes
    private fun handleLoyaltyCardBarcode(
        barcode: String,
        overlay: FrameLayout,
        statusText: TextView,
        customerAutoComplete: AutoCompleteTextView
    ) {
        lifecycleScope.launch {
            try {
                // Play sound for successful scan
                mediaPlayer.start()

                // Check if the scanned barcode is a valid loyalty card number
                val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(barcode)

                if (loyaltyCard != null) {
                    // Found a valid loyalty card
                    withContext(Dispatchers.Main) {
                        statusText.text = "Found card: ${loyaltyCard.customerName}"

                        // Set the customer field with the loyalty card information
                        val customerValue = "${loyaltyCard.cardNumber}"
                        customerAutoComplete.setText(customerValue, false)

                        // Create a vibration effect for feedback
                        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator.vibrate(200)
                        }

                        // Update the selected customer to this loyalty card holder
                        selectedCustomer = Customer(
                            accountNum = "LC-${loyaltyCard.cardNumber}",
                            name = loyaltyCard.customerName ?: "Loyalty Card: ${loyaltyCard.cardNumber}"
                        )

                        // Close camera after a delay to confirm to the user
                        Handler(Looper.getMainLooper()).postDelayed({
                            overlay.visibility = View.GONE
                            try {
                                cameraProviderFuture.get().unbindAll()
                            } catch (e: Exception) {
                                Log.e(TAG, "Error unbinding camera", e)
                            }

                            // Show a dialog with loyalty card details
                            showLoyaltyCardInfoDialog(loyaltyCard)
                        }, 1500)
                    }
                } else {
                    // Not a valid loyalty card
                    withContext(Dispatchers.Main) {
                        statusText.text = "Invalid loyalty card: $barcode"
                        Handler(Looper.getMainLooper()).postDelayed({
                            isProcessingBarcode = false
                            statusText.text = "Scanning for loyalty card..."
                        }, 1500)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing loyalty card", e)
                withContext(Dispatchers.Main) {
                    statusText.text = "Error: ${e.message}"
                    isProcessingBarcode = false
                }
            }
        }
    }

    // 6. Add a function to show loyalty card information after scanning
    private fun showLoyaltyCardInfoDialog(loyaltyCard: LoyaltyCard) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_loyalty_card_info, null)

        // Set up views with loyalty card data
        dialogView.findViewById<TextView>(R.id.cardNumberText).text = loyaltyCard.cardNumber
        dialogView.findViewById<TextView>(R.id.customerNameText).text = loyaltyCard.customerName ?: "Unknown"
        dialogView.findViewById<TextView>(R.id.pointsText).text = "${loyaltyCard.points} points"
        dialogView.findViewById<TextView>(R.id.tierText).text = loyaltyCard.tier

        // Format expiry date if available
        val expiryText = dialogView.findViewById<TextView>(R.id.expiryDateText)
        if (!loyaltyCard.expiryDate.isNullOrEmpty()) {
            expiryText.text = loyaltyCard.expiryDate
        } else {
            expiryText.text = "No expiration"
        }

        AlertDialog.Builder(this, R.style.CustomDialogStyle)
            .setTitle("Loyalty Card Details")
            .setView(dialogView)
            .setPositiveButton("Use Card") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .apply {
                window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                show()
            }
    }
    private fun updateSecondPaymentMethods(
        firstPaymentMethod: String,
        paymentMethodSpinner2: Spinner,
        paymentMethods: List<String>
    ) {
        // Allow all payment methods except LOYALTYCARD in second payment
        // Remove the restriction on charge payments
        val filteredMethods = paymentMethods.filter {
            it != firstPaymentMethod && it != "LOYALTYCARD" // Only exclude LOYALTYCARD from second payment
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            filteredMethods
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        paymentMethodSpinner2.adapter = adapter
        paymentMethodSpinner2.setSelection(0) // Default to first available method
    }


    private fun setupCustomerSelection(
        customerAutoComplete: AutoCompleteTextView,
        customers: MutableList<Customer>,
        adapter: ArrayAdapter<String>,
        paymentMethodSpinner: Spinner,
        paymentMethodSpinner2: Spinner?,
        payButton: Button,
        dialogView: View,
        onCustomerSelected: (Customer) -> Unit
    ) {
        // Get references to views
        val viewPointsCard = dialogView.findViewById<CardView>(R.id.cardViewPoints)
        val viewPointsButton = dialogView.findViewById<Button>(R.id.btnViewPoints)
        val amountPaidEditText = dialogView.findViewById<EditText>(R.id.amountPaidEditText1)
        val splitPaymentSwitch = dialogView.findViewById<Switch>(R.id.splitPaymentSwitch)

        payButton.isEnabled = true

        // Reset customer to walk-in and clear purchase history
        selectedCustomer = Customer(accountNum = "WALK-IN", name = "Walk-in Customer")
        customerAutoComplete.setText("Walk-in Customer", false)
        customerAutoComplete.error = null
        customerViewModel.clearPurchaseHistory()

        customerAutoComplete.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && customerAutoComplete.text.toString() == "Walk-in Customer") {
                customerAutoComplete.setText("")
            }
        }

        // Handle keyboard done action
        customerAutoComplete.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else {
                false
            }
        }

        // Handle text changes for customer/loyalty card search
        customerAutoComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                lifecycleScope.launch {
                    try {
                        val searchText = s?.toString() ?: ""

                        if (searchText.length >= 6 && !searchText.contains(" ")) {
                            val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(searchText)
                            if (loyaltyCard != null) {
                                selectedCustomer = Customer(
                                    accountNum = "LC-${loyaltyCard.cardNumber}",
                                    name = "LC-${loyaltyCard.cardNumber}"
                                )
                                customerAutoComplete.error = null
                                viewPointsCard.visibility = View.VISIBLE

                                viewPointsButton.setOnClickListener {
                                    lifecycleScope.launch {
                                        val cartItems = cartViewModel.getAllCartItems(windowId).first()
                                        var totalAmount = 0.0
                                        cartItems.forEach { item ->
                                            totalAmount += (item.overriddenPrice ?: item.price) * item.quantity
                                        }

                                        val paymentMethodSpinner1 = dialogView.findViewById<Spinner>(R.id.paymentMethodSpinner1)
                                        val paymentMethodSpinner2 = dialogView.findViewById<Spinner>(R.id.paymentMethodSpinner2)
                                        val secondPaymentLayout = dialogView.findViewById<LinearLayout>(R.id.secondPaymentLayout)
                                        val amountPaidEditText2 = dialogView.findViewById<EditText>(R.id.amountPaidEditText2)

                                        showPointsRedemptionDialog(
                                            loyaltyCard,
                                            totalAmount,
                                            amountPaidEditText,
                                            splitPaymentSwitch,
                                            paymentMethodSpinner1,
                                            paymentMethodSpinner2,
                                            secondPaymentLayout,
                                            amountPaidEditText2
                                        )
                                    }
                                }

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@Window1,
                                        "Loyalty Card Found! Available Points: ${loyaltyCard.points}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                return@launch
                            } else {
                                viewPointsCard.visibility = View.GONE
                            }
                        } else {
                            viewPointsCard.visibility = View.GONE
                        }

                        // If not a loyalty card or card not found, search for customers
                        if (searchText.length >= 1) {
                            customerViewModel.searchCustomers(searchText)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error searching: ${e.message}")
                    }
                }

                // Handle charge payment validation
                val currentText = s?.toString() ?: ""
                val isCharge1 = isChargePayment(paymentMethodSpinner.selectedItem?.toString() ?: "")
                val isCharge2 = paymentMethodSpinner2?.let {
                    isChargePayment(it.selectedItem?.toString() ?: "")
                } ?: false

                if ((isCharge1 || isCharge2) && (currentText.isEmpty() || currentText == "Walk-in Customer")) {
                    customerAutoComplete.error = "Please select a valid customer for charge payment"
                    payButton.isEnabled = false
                    customerViewModel.clearPurchaseHistory()
                }
            }
        })

        // Handle customer selection
        customerAutoComplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedCustomerName = parent.getItemAtPosition(position).toString()
            val customer = customers.find { it.name == selectedCustomerName }

            customerViewModel.clearPurchaseHistory()

            if (customer == null || customer.name in listOf("Walk-in Customer", "Walk-in")) {
                customerAutoComplete.setText("Walk-in Customer", false)
                customerAutoComplete.error = "Please select a registered customer"
                payButton.isEnabled = false
                selectedCustomer = Customer(accountNum = "WALK-IN", name = "Walk-in Customer")
                viewPointsCard.visibility = View.GONE
            } else {
                customerAutoComplete.setText(customer.name, false)
                customerAutoComplete.error = null
                payButton.isEnabled = true
                onCustomerSelected(customer)
                viewPointsCard.visibility = View.GONE
            }
        }
    }
    private fun updatePaymentMethodSpinner(
        spinner: Spinner?,
        paymentMethods: List<String>,
        customerAutoComplete: AutoCompleteTextView,
        payButton: Button? = null
    ) {
        if (spinner == null) return

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            paymentMethods
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedMethod = spinner.selectedItem.toString()
                val requiresCustomer = isChargePayment(selectedMethod) || selectedMethod.uppercase() == "LOYALTYCARD"

                if (requiresCustomer) {
                    if (selectedCustomer.accountNum == "WALK-IN") {
                        customerAutoComplete.error = when(selectedMethod.uppercase()) {
                            "LOYALTYCARD" -> "Please enter a loyalty card number"
                            else -> "Please select a customer for charge payment"
                        }
                        payButton?.isEnabled = false
                        spinner.setSelection(0) // Reset to Cash
                        return
                    }

                    if (selectedMethod.uppercase() == "LOYALTYCARD" && !selectedCustomer.accountNum.startsWith("LC-")) {
                        Toast.makeText(this@Window1, "Please enter a valid loyalty card number", Toast.LENGTH_SHORT).show()
                        spinner.setSelection(0)
                        return
                    }
                }

                payButton?.isEnabled = when(selectedMethod.uppercase()) {
                    "LOYALTYCARD" -> selectedCustomer.accountNum.startsWith("LC-")
                    "CHARGE" -> isValidCustomerForCharge(selectedCustomer)
                    else -> true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                customerAutoComplete.error = null
                payButton?.isEnabled = true
            }
        }
    }


    private fun updateCustomerList(
        customersList: MutableList<Customer>,
        newCustomers: List<Customer>,
        adapter: ArrayAdapter<String>
    ) {
        customersList.clear()
        // Update the Walk-in customer with a standard account number
        customersList.add(Customer(accountNum = "WALK-IN", name = "Walk-in Customer"))

        // Add all other customers, preserving their full details
        customersList.addAll(newCustomers)

        // Update adapter with customer names
        adapter.clear()
        adapter.addAll(customersList.map { it.name })
        adapter.notifyDataSetChanged()

        // For debugging - log the customer list
        customersList.forEach { customer ->
            Log.d(TAG, "Customer in list: ${customer.name}, Account: ${customer.accountNum}")
        }
    }

    private fun updatePricesForWindow(windowDescription: String) {
        lifecycleScope.launch {
            try {
                val currentProducts = productViewModel.visibleProducts.value ?: return@launch

                // Update prices based on window type
                val updatedProducts = currentProducts.map { product ->
                    when {
                        windowDescription.contains("GRABFOOD") && product.grabfood > 0 -> {
                            product.copy(price = product.grabfood)
                        }
                        windowDescription.contains("FOODPANDA") && product.foodpanda > 0 -> {
                            product.copy(price = product.foodpanda)
                        }
                        windowDescription.contains("MANILARATE") && product.manilaprice > 0 -> {
                            product.copy(price = product.manilaprice)
                        }
                        windowDescription.contains("GRABFOODMALL") && product.grabfoodmall > 0 -> {
                            product.copy(price = product.grabfoodmall)
                        }
                        windowDescription.contains("FOODPANDAMALL") && product.foodpandamall > 0 -> {
                            product.copy(price = product.foodpandamall)
                        }
                        windowDescription.contains("MALLPRICE") && product.mallprice > 0 -> {
                            product.copy(price = product.mallprice)
                        }

                        else -> product
                    }
                }

                // Update the product list with adjusted prices
                withContext(Dispatchers.Main) {
                    productAdapter.submitList(updatedProducts)
                }
            } catch (e: Exception) {
                Log.e("Window1", "Error updating prices for window", e)
            }
        }
    }

    private fun setupBarcodeScanning() {
        barcodeScanner = BarcodeScanning.getClient()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        // Initialize sound
//        mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
        mediaPlayer = MediaPlayer.create(this, R.raw.beep)
    }


//    private fun setupBarcodeScanButton() {
//        val barcodeScanButton = findViewById<ImageButton>(R.id.barcodeScanButton)
//        barcodeScanButton.setOnClickListener {
//            if (checkCameraPermission()) {
//                showBarcodeScannerOverlay()
//            } else {
//                requestCameraPermission()
//            }
//        }
//    }
    private fun setupBarcodeScanButton() {
        val barcodeScanButton = findViewById<ImageButton>(R.id.barcodeScanButton)
        barcodeScanButton?.setOnClickListener {
            openQRScanner()
        } ?: run {
            Log.w(TAG, "Barcode scan button not found in current layout")
        }
    }
    private fun setupDraggableScanner() {
        val scannerOverlay = findViewById<FrameLayout>(R.id.barcodeScannerOverlay)
        var dY = 0f
        var dX = 0f
        var initialY = 0f
        var initialX = 0f
        var isDragging = false

        scannerOverlay.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dY = view.y - event.rawY
                    dX = view.x - event.rawX
                    initialY = view.y
                    initialX = view.x
                    isDragging = false

                    // Consume the touch event to prevent it from reaching underlying views
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaY = abs(event.rawY + dY - initialY)
                    val deltaX = abs(event.rawX + dX - initialX)

                    // Start dragging if moved more than touch slop
                    if (!isDragging && (deltaY > 10 || deltaX > 10)) {
                        isDragging = true
                    }

                    if (isDragging) {
                        val newY = event.rawY + dY
                        val newX = event.rawX + dX

                        // Get screen bounds
                        val displayMetrics = resources.displayMetrics
                        val screenHeight = displayMetrics.heightPixels
                        val screenWidth = displayMetrics.widthPixels
                        val viewHeight = view.height
                        val viewWidth = view.width

                        // Constrain movement to screen bounds
                        val constrainedY = when {
                            newY < 0 -> 0f
                            newY + viewHeight > screenHeight -> (screenHeight - viewHeight).toFloat()
                            else -> newY
                        }

                        val constrainedX = when {
                            newX < 0 -> 0f
                            newX + viewWidth > screenWidth -> (screenWidth - viewWidth).toFloat()
                            else -> newX
                        }

                        if (isMobileLayout) {
                            // Mobile: Only allow vertical movement
                            view.y = constrainedY
                        } else {
                            // Tablet: Allow both horizontal and vertical movement
                            view.x = constrainedX
                            view.y = constrainedY
                        }
                    }

                    // Always consume the event during move
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isDragging = false
                    // Consume the event to prevent click-through
                    true
                }
                else -> false
            }
        }

        // Also set the scanner overlay to be focusable and clickable to intercept touches
        scannerOverlay.isFocusable = true
        scannerOverlay.isClickable = true
    }
//    private fun showBarcodeScannerOverlay() {
//        val scannerOverlay = findViewById<FrameLayout>(R.id.barcodeScannerOverlay)
//        val previewView = findViewById<PreviewView>(R.id.previewView)
//        val closeButton = findViewById<ImageButton>(R.id.closeButton)
//        val scannerStatus = findViewById<TextView>(R.id.scannerStatus)
//
//        isProcessingBarcode = false
//        lastScannedBarcode = null
//        scannerOverlay.visibility = View.VISIBLE
//        scannerStatus.text = "Ready to scan..."
//        setupDraggableScanner()
//
//        closeButton.setOnClickListener {
//            scannerOverlay.visibility = View.GONE
//            try {
//                cameraProviderFuture.get().unbindAll()
//            } catch (e: Exception) {
//                Log.e(TAG, "Error unbinding camera", e)
//            }
//        }
//
//        cameraProviderFuture.addListener({
//            try {
//                val cameraProvider = cameraProviderFuture.get()
//                bindCameraPreview(
//                    cameraProvider,
//                    previewView,
//                    scannerOverlay,
//                    scannerStatus
//                )
//            } catch (e: Exception) {
//                Log.e(TAG, "Error setting up camera preview", e)
//                scannerStatus.text = "Error: ${e.message}"
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
private fun showBarcodeScannerOverlay() {
    try {
        val scannerOverlay = findViewById<FrameLayout>(R.id.barcodeScannerOverlay)
        val previewView = findViewById<PreviewView>(R.id.previewView)
        val closeButton = findViewById<ImageButton>(R.id.closeButton)
        val scannerStatus = findViewById<TextView>(R.id.scannerStatus)

        if (scannerOverlay == null) {
            Toast.makeText(this, "Scanner not available in this layout", Toast.LENGTH_SHORT).show()
            return
        }

        // Reset scanner state
        isProcessingBarcode = false
        lastScannedBarcode = null

        // Ensure the scanner overlay is brought to front and properly configured
        scannerOverlay.apply {
            visibility = View.VISIBLE
            alpha = 0f
            elevation = 100f // High elevation to ensure it's on top
            bringToFront() // Bring to front of parent
            isFocusable = true
            isClickable = true
        }

        // Animate in
        scannerOverlay.animate()
            .alpha(1f)
            .setDuration(300)
            .start()

        scannerStatus.text = "Ready to scan..."

        // Setup draggable functionality
        setupDraggableScanner()

        closeButton.setOnClickListener {
            closeBarcodeScanner(scannerOverlay)
        }

        // Initialize camera
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                bindCameraPreview(
                    cameraProvider,
                    previewView,
                    scannerOverlay,
                    scannerStatus
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error setting up camera preview", e)
                scannerStatus.text = "Camera Error: ${e.message}"
                Toast.makeText(this, "Camera initialization failed", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))

    } catch (e: Exception) {
        Log.e(TAG, "Error showing barcode scanner overlay", e)
        Toast.makeText(this, "Scanner error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

    private fun setCartSwipeEnabled(enabled: Boolean) {
        val cartRecyclerView = if (isMobileLayout) {
            findViewById<RecyclerView>(R.id.recyclerviewcart)
        } else {
            binding.recyclerviewcart
        }

        cartRecyclerView?.let { recyclerView ->
            // Find the ItemTouchHelper attached to this RecyclerView
            val field = RecyclerView::class.java.getDeclaredField("mItemDecorations")
            field.isAccessible = true
            val decorations = field.get(recyclerView) as ArrayList<*>

            decorations.forEach { decoration ->
                if (decoration.toString().contains("ItemTouchHelper")) {
                    try {
                        val itemTouchHelper = decoration as ItemTouchHelper
                        if (enabled) {
                            itemTouchHelper.attachToRecyclerView(recyclerView)
                        } else {
                            itemTouchHelper.attachToRecyclerView(null)
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error toggling cart swipe", e)
                    }
                }
            }
        }
    }

// Update your closeBarcodeScanner method to re-enable cart swiping:

    private fun closeBarcodeScanner(scannerOverlay: FrameLayout) {
        scannerOverlay.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                scannerOverlay.visibility = View.GONE
                scannerOverlay.elevation = 0f // Reset elevation
                try {
                    cameraProviderFuture.get().unbindAll()
                } catch (e: Exception) {
                    Log.e(TAG, "Error unbinding camera", e)
                }
                // Re-enable cart swiping when scanner is closed
                setCartSwipeEnabled(true)
            }
            .start()
    }
    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun bindCameraPreview(
        cameraProvider: ProcessCameraProvider,
        previewView: PreviewView,
        overlay: FrameLayout,
        statusText: TextView
    ) {
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
            processImage(imageProxy, overlay, statusText)
        }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysis
            )
            preview.setSurfaceProvider(previewView.surfaceProvider)
        } catch (e: Exception) {
            Log.e(TAG, "Error binding camera preview", e)
            statusText.text = "Camera Error"
        }
    }


    private fun processImage(
        imageProxy: ImageProxy,
        overlay: FrameLayout,
        statusText: TextView
    ) {
        if (isProcessingBarcode) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (!isProcessingBarcode && barcodes.isNotEmpty()) {
                        val barcode = barcodes.first().rawValue?.toLongOrNull()
                        val currentTime = System.currentTimeMillis()

                        // Check if it's a new barcode or if enough time has passed
                        if (barcode != null &&
                            (barcode != lastScannedBarcode ||
                                    currentTime - lastScanTime > SCAN_COOLDOWN)) {

                            isProcessingBarcode = true
                            lastScannedBarcode = barcode
                            lastScanTime = currentTime
                            handleScannedBarcode(barcode, overlay, statusText)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Barcode scanning failed", it)
                    statusText.text = "Scan failed"
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    private fun handleScannedBarcode(barcode: Long, overlay: FrameLayout, statusText: TextView) {
        lifecycleScope.launch {
            val product = productViewModel.visibleProducts.value?.find { it.barcode == barcode }
            if (product != null) {
                addToCart(product)
                withContext(Dispatchers.Main) {
                    try {
                        mediaPlayer.start()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error playing sound", e)
                    }

                    statusText.text = "Added: ${product.itemName}"

                    // Reset after a short delay to allow for next scan
                    Handler(Looper.getMainLooper()).postDelayed({
                        isProcessingBarcode = false
                        statusText.text = "Ready to scan..."
                    }, 1000)
                }
            } else {
                withContext(Dispatchers.Main) {
                    statusText.text = "Product not found"
                    Handler(Looper.getMainLooper()).postDelayed({
                        isProcessingBarcode = false
                        statusText.text = "Ready to scan..."
                    }, 1500)
                }
            }
        }
    }


//    private fun addToCart(product: Product) {
//        checkStaffAndProceed {
//            if (currentCashFund <= 0) {
//                Toast.makeText(
//                    this,
//                    "Cannot perform transactions. Please set a cash fund.",
//                    Toast.LENGTH_LONG
//                ).show()
//                return@checkStaffAndProceed
//            }
//
//            lifecycleScope.launch {
//                val existingItems = cartViewModel.getAllCartItems(windowId).first()
//                val partialPayment = existingItems.firstOrNull()?.partialPayment ?: 0.0
//
//                // Find matching items, separating discounted and non-discounted
//                val existingNonDiscountedItem = existingItems.find { item ->
//                    item.productId == product.id &&
//                            item.discount == 0.0 &&
//                            item.discountType.isEmpty() &&
//                            item.bundleId == null
//                }
//
//                val existingDiscountedItem = existingItems.find { item ->
//                    item.productId == product.id &&
//                            (item.discount > 0.0 ||
//                                    item.discountType.isNotEmpty() ||
//                                    item.bundleId != null)
//                }
//
//                when {
//                    // If clicking a non-discounted item
//                    existingNonDiscountedItem != null -> {
//                        // Update quantity of existing non-discounted item
//                        cartViewModel.update(
//                            existingNonDiscountedItem.copy(
//                                quantity = existingNonDiscountedItem.quantity + 1,
//                                partialPayment = partialPayment
//                            )
//                        )
//                    }
//                    // If the product exists but only as a discounted item, create new non-discounted entry
//                    existingDiscountedItem != null -> {
//                        // Create new cart item without discount
//                        cartViewModel.insert(
//                            CartItem(
//                                productId = product.id,
//                                quantity = 1,
//                                windowId = windowId,
//                                productName = product.itemName,
//                                price = product.price,
//                                partialPayment = partialPayment,
//                                vatAmount = 0.0,
//                                vatExemptAmount = 0.0,
//                                itemGroup = product.itemGroup,
//                                itemId = product.itemid,
//                                discount = 0.0,
//                                discountType = ""
//                            )
//                        )
//                    }
//                    // If the product doesn't exist in cart at all
//                    else -> {
//                        cartViewModel.insert(
//                            CartItem(
//                                productId = product.id,
//                                quantity = 1,
//                                windowId = windowId,
//                                productName = product.itemName,
//                                price = product.price,
//                                partialPayment = partialPayment,
//                                vatAmount = 0.0,
//                                vatExemptAmount = 0.0,
//                                itemGroup = product.itemGroup,
//                                itemId = product.itemid,
//                                discount = 0.0,
//                                discountType = ""
//                            )
//                        )
//                    }
//                }
//
//                Log.d(TAG, "Added/Updated cart item for product ${product.id} in window $windowId")
//                updateTotalAmount(cartViewModel.getAllCartItems(windowId).first())
//            }
//        }
//    }
private fun loadDiscountsForWindow() {
    lifecycleScope.launch {
        try {
            Log.d(TAG, "Loading discounts for window...")

            // Fetch discounts immediately when window opens
            discountViewModel.fetchDiscounts()

            // Wait a bit for discounts to load
            delay(500)

            val loadedDiscounts = discountViewModel.discounts.value
            Log.d(TAG, "Loaded ${loadedDiscounts?.size ?: 0} discounts")

            // Log each discount for debugging
            loadedDiscounts?.forEach { discount ->
                Log.d(TAG, "Discount: ${discount.DISCOFFERNAME} - Default: ${discount.PARAMETER}, GF: ${discount.GRABFOOD_PARAMETER}, FP: ${discount.FOODPANDA_PARAMETER}, MR: ${discount.MANILAPRICE_PARAMETER}")
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error loading discounts: ${e.message}", e)
        }
    }
}

    // Updated addToCart function with better discount loading
    private fun addToCart(product: Product) {
        // First check if transactions are disabled after Z-Read
        if (isTransactionDisabledAfterZRead) {
            Toast.makeText(
                this,
                "Transactions are disabled. Z-Read completed for today. Please wait until next day.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        checkStaffAndProceed {
            if (currentCashFund <= 0) {
                Toast.makeText(
                    this,
                    "Cannot perform transactions. Please set a cash fund.",
                    Toast.LENGTH_LONG
                ).show()
                return@checkStaffAndProceed
            }

            // Show loading state immediately
            showLoadingState(true)

            lifecycleScope.launch {
                try {
                    // Check Z-Read status before allowing transaction
                    val hasZReadToday = hasZReadForToday()
                    if (hasZReadToday && isAfterMidnight()) {
                        withContext(Dispatchers.Main) {
                            showLoadingState(false)
                            Toast.makeText(
                                this@Window1,
                                "Cannot add items. Z-Read completed for today.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        return@launch
                    }

                    // Get fresh cart items data
                    val existingItems = cartViewModel.getAllCartItems(windowId).first()
                    val partialPayment = existingItems.firstOrNull()?.partialPayment ?: 0.0

                    // Determine current window type for discount parameter selection
                    val window = windowViewModel.allWindows.first().find { it.id == windowId }
                    val windowType = window?.description?.uppercase() ?: ""

                    // Get the correct price based on window type
                    val windowSpecificPrice = when {
                        windowType.contains("GRABFOOD") -> product.grabfood
                        windowType.contains("FOODPANDA") -> product.foodpanda
                        windowType.contains("MANILARATE") -> product.manilaprice
                        windowType.contains("MALLPRICE") -> product.mallprice
                        windowType.contains("GRABFOODMALL") -> product.grabfoodmall
                        windowType.contains("FOODPANDAMALL") -> product.foodpandamall
                        else -> product.price
                    }

                    Log.d(TAG, "Adding product: ${product.itemName} (${product.itemid}) to window: $windowType")
                    Log.d(TAG, "Original price: ${product.price}, Window-specific price: $windowSpecificPrice")

                    // FIXED: Ensure discounts are properly loaded with timeout
                    var loadedDiscounts = discountViewModel.discounts.value

                    if (loadedDiscounts.isNullOrEmpty()) {
                        Log.d(TAG, "No discounts loaded, fetching and waiting...")
                        discountViewModel.fetchDiscounts()

                        // Wait for discounts to load with timeout
                        var attempts = 0
                        while (attempts < 10) { // 5 seconds max wait
                            delay(500)
                            loadedDiscounts = discountViewModel.discounts.value
                            if (!loadedDiscounts.isNullOrEmpty()) {
                                Log.d(TAG, "✅ Discounts loaded after ${attempts * 500}ms")
                                break
                            }
                            attempts++
                        }

                        if (loadedDiscounts.isNullOrEmpty()) {
                            Log.w(TAG, "⚠️ Failed to load discounts after 5 seconds, proceeding without discounts")
                        }
                    }

                    Log.d(TAG, "Final discount count: ${loadedDiscounts?.size ?: 0}")

                    // Check if there's a matching discount for this product
                    var matchingDiscount: Discount? = null
                    var discountParameter: Int? = null

                    if (!loadedDiscounts.isNullOrEmpty()) {
                        Log.d(TAG, "Searching for discount matching product name: ${product.itemName}")

                        // Try to find matching discount by comparing product name with discount offer name
                        matchingDiscount = loadedDiscounts.find { discount ->
                            val match = discount.DISCOFFERNAME.trim().equals(product.itemName.trim(), ignoreCase = true)
                            Log.d(TAG, "Comparing '${discount.DISCOFFERNAME.trim()}' with '${product.itemName.trim()}' = $match")
                            match
                        }

                        if (matchingDiscount != null) {
                            // Select appropriate discount parameter based on window type
                            discountParameter = when {
                                windowType.contains("GRABFOOD") -> {
                                    val param = matchingDiscount.GRABFOOD_PARAMETER ?: matchingDiscount.PARAMETER
                                    Log.d(TAG, "🔥 Using GRABFOOD parameter: $param (specific: ${matchingDiscount.GRABFOOD_PARAMETER}, default: ${matchingDiscount.PARAMETER})")
                                    param
                                }
                                windowType.contains("FOODPANDA") -> {
                                    val param = matchingDiscount.FOODPANDA_PARAMETER ?: matchingDiscount.PARAMETER
                                    Log.d(TAG, "🔥 Using FOODPANDA parameter: $param (specific: ${matchingDiscount.FOODPANDA_PARAMETER}, default: ${matchingDiscount.PARAMETER})")
                                    param
                                }
                                windowType.contains("MANILARATE") -> {
                                    val param = matchingDiscount.MANILAPRICE_PARAMETER ?: matchingDiscount.PARAMETER
                                    Log.d(TAG, "🔥 Using MANILARATE parameter: $param (specific: ${matchingDiscount.MANILAPRICE_PARAMETER}, default: ${matchingDiscount.PARAMETER})")
                                    param
                                }
                                windowType.contains("MALLPRICE") -> {
                                    val param = matchingDiscount.MALLPRICE_PARAMETER ?: matchingDiscount.PARAMETER
                                    Log.d(TAG, "🔥 Using MALLPRICE parameter: $param (specific: ${matchingDiscount.MALLPRICE_PARAMETER}, default: ${matchingDiscount.PARAMETER})")
                                    param
                                }
                                windowType.contains("FOODPANDAMALL") -> {
                                    val param = matchingDiscount.FOODPANDAMALL_PARAMETER ?: matchingDiscount.PARAMETER
                                    Log.d(TAG, "🔥 Using FOODPANDAMALL parameter: $param (specific: ${matchingDiscount.FOODPANDAMALL_PARAMETER}, default: ${matchingDiscount.PARAMETER})")
                                    param
                                }
                                windowType.contains("GRABFOODMALL") -> {
                                    val param = matchingDiscount.GRABFOODMALL_PARAMETER ?: matchingDiscount.PARAMETER
                                    Log.d(TAG, "🔥 Using GRABFOODMALL parameter: $param (specific: ${matchingDiscount.GRABFOODMALL_PARAMETER}, default: ${matchingDiscount.PARAMETER})")
                                    param
                                }
                                else -> {
                                    Log.d(TAG, "🔥 Using default parameter: ${matchingDiscount.PARAMETER}")
                                    matchingDiscount.PARAMETER // Default parameter
                                }
                            }

                            Log.d(TAG, "✅ Found matching discount: ${matchingDiscount.DISCOFFERNAME} for product: ${product.itemName}")
                            Log.d(TAG, "✅ Using discount parameter: $discountParameter for window type: $windowType")
                        } else {
                            Log.d(TAG, "❌ No matching discount found for product: ${product.itemName}")
                            // Log first few available discounts for debugging
                            loadedDiscounts.take(5).forEach { discount ->
                                Log.d(TAG, "Available discount: '${discount.DISCOFFERNAME}'")
                            }
                        }
                    } else {
                        Log.d(TAG, "❌ No discounts loaded at all!")
                    }

                    // Find matching items in cart
                    val existingNonDiscountedItem = existingItems.find { item ->
                        item.productId == product.id &&
                                item.discount == 0.0 &&
                                item.discountType.isEmpty() &&
                                item.bundleId == null
                    }

                    val existingDiscountedItem = existingItems.find { item ->
                        item.productId == product.id &&
                                (item.discount > 0.0 ||
                                        item.discountType.isNotEmpty() ||
                                        item.bundleId != null)
                    }

                    // Perform cart operations
                    if (matchingDiscount != null && discountParameter != null) {
                        Log.d(TAG, "🎯 Applying discount: ${matchingDiscount.DISCOFFERNAME} ($discountParameter ${matchingDiscount.DISCOUNTTYPE})")

                        // Show toast to confirm discount application
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                "Applied discount: ${matchingDiscount.DISCOFFERNAME} ($discountParameter ${matchingDiscount.DISCOUNTTYPE}) for $windowType",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // Create new item with discount applied - USING WINDOW-SPECIFIC PRICE
                        cartViewModel.insert(
                            CartItem(
                                productId = product.id,
                                quantity = 1,
                                windowId = windowId,
                                productName = product.itemName,
                                price = windowSpecificPrice, // ✅ Use window-specific price
                                partialPayment = partialPayment,
                                vatAmount = 0.0,
                                vatExemptAmount = 0.0,
                                itemGroup = product.itemGroup,
                                itemId = product.itemid,
                                discount = discountParameter.toDouble(),
                                discountType = matchingDiscount.DISCOUNTTYPE,
                                discountName = "${matchingDiscount.DISCOFFERNAME} (${getWindowTypeLabel(windowType)})"
                            )
                        )

                    } else when {
                        // If clicking a non-discounted item
                        existingNonDiscountedItem != null -> {
                            Log.d(TAG, "📦 Updating quantity of existing item")
                            // Update quantity of existing non-discounted item
                            cartViewModel.update(
                                existingNonDiscountedItem.copy(
                                    quantity = existingNonDiscountedItem.quantity + 1,
                                    partialPayment = partialPayment
                                )
                            )
                        }
                        // If the product exists but only as a discounted item, create new non-discounted entry
                        existingDiscountedItem != null -> {
                            Log.d(TAG, "📦 Creating new non-discounted item (discounted version exists)")
                            // Create new cart item without discount - USING WINDOW-SPECIFIC PRICE
                            cartViewModel.insert(
                                CartItem(
                                    productId = product.id,
                                    quantity = 1,
                                    windowId = windowId,
                                    productName = product.itemName,
                                    price = windowSpecificPrice, // ✅ Use window-specific price
                                    partialPayment = partialPayment,
                                    vatAmount = 0.0,
                                    vatExemptAmount = 0.0,
                                    itemGroup = product.itemGroup,
                                    itemId = product.itemid,
                                    discount = 0.0,
                                    discountType = ""
                                )
                            )
                        }
                        // If the product doesn't exist in cart at all
                        else -> {
                            Log.d(TAG, "📦 Creating new cart item (no existing versions)")
                            cartViewModel.insert(
                                CartItem(
                                    productId = product.id,
                                    quantity = 1,
                                    windowId = windowId,
                                    productName = product.itemName,
                                    price = windowSpecificPrice, // ✅ Use window-specific price
                                    partialPayment = partialPayment,
                                    vatAmount = 0.0,
                                    vatExemptAmount = 0.0,
                                    itemGroup = product.itemGroup,
                                    itemId = product.itemid,
                                    discount = 0.0,
                                    discountType = ""
                                )
                            )
                        }
                    }

                    Log.d(TAG, "✅ Added/Updated cart item for product ${product.id} in window $windowId with price $windowSpecificPrice")

                    // Wait a bit to ensure database operations complete
                    delay(100)

                    // Get updated cart items and refresh totals
                    val updatedCartItems = cartViewModel.getAllCartItems(windowId).first()

                    withContext(Dispatchers.Main) {
                        updateTotalAmount(updatedCartItems)
                        showLoadingState(false)
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "Error adding to cart: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        showLoadingState(false)
                        Toast.makeText(
                            this@Window1,
                            "Error adding item to cart. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    // Also make sure your discount application functions use the correct parameters
    private fun applySelectedDiscountWithWindowType(
        dialogView: View,
        selectedDiscount: Discount?,
        windowType: String
    ) {
        val cartItemsLayout = dialogView.findViewById<LinearLayout>(R.id.cartItemsLayout)
        val selectedCartItemIds = mutableListOf<Int>()

        // Show loading state
        showDiscountLoadingState(dialogView, true)

        for (i in 0 until cartItemsLayout.childCount) {
            val view = cartItemsLayout.getChildAt(i)
            if (view is CheckBox && view.isChecked) {
                view.tag?.let { tag ->
                    if (tag is Int) {
                        selectedCartItemIds.add(tag)
                    }
                }
            }
        }

        if (selectedCartItemIds.isNotEmpty() && selectedDiscount != null) {
            lifecycleScope.launch {
                try {
                    val cartItems = cartViewModel.getAllCartItems(windowId).first()
                    val selectedItems = cartItems.filter { it.id in selectedCartItemIds }

                    // Get the appropriate parameter for the current window type
                    val discountParameter = when {
                        windowType.contains("GRABFOOD") -> {
                            selectedDiscount.GRABFOOD_PARAMETER ?: selectedDiscount.PARAMETER
                        }
                        windowType.contains("FOODPANDA") -> {
                            selectedDiscount.FOODPANDA_PARAMETER ?: selectedDiscount.PARAMETER
                        }
                        windowType.contains("MANILARATE") -> {
                            selectedDiscount.MANILAPRICE_PARAMETER ?: selectedDiscount.PARAMETER
                        }
                        else -> {
                            selectedDiscount.PARAMETER
                        }
                    }

                    Log.d(TAG, "Applying manual discount with parameter: $discountParameter for window type: $windowType")

                    // Apply the discount with the appropriate parameter
                    when (selectedDiscount.DISCOUNTTYPE.uppercase()) {
                        "FIXEDTOTAL" -> applyFixedTotalDiscount(
                            selectedItems,
                            discountParameter.toDouble(),
                            "${selectedDiscount.DISCOFFERNAME}"
                        )

                        "PERCENTAGE" -> {
                            val isVatExempt = selectedDiscount.DISCOFFERNAME.contains("SENIOR", ignoreCase = true) ||
                                    selectedDiscount.DISCOFFERNAME.contains("PWD", ignoreCase = true)
                            applyPercentageDiscount(
                                selectedItems,
                                discountParameter.toDouble(),
                                isVatExempt,
                                selectedDiscount.DISCOFFERNAME
                            )
                        }

                        "FIXED" -> applyFixedDiscount(
                            selectedItems,
                            discountParameter.toDouble(),
                            selectedDiscount.DISCOFFERNAME
                        )
                    }

                    delay(100) // Ensure discount application completes

                    val updatedCartItems = cartViewModel.getAllCartItems(windowId).first()
                    withContext(Dispatchers.Main) {
                        updateTotalAmount(updatedCartItems)
                        showDiscountLoadingState(dialogView, false)

                        val windowLabel = getWindowTypeLabel(windowType)
                        val parameterUsed = if (discountParameter != selectedDiscount.PARAMETER) {
                            "$discountParameter ($windowLabel)"
                        } else {
                            "$discountParameter (Default)"
                        }

                        Toast.makeText(
                            this@Window1,
                            "Applied ${selectedDiscount.DISCOFFERNAME} ($parameterUsed ${selectedDiscount.DISCOUNTTYPE}) to ${selectedItems.size} items",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Log.e(TAG, "Error applying discount: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        showDiscountLoadingState(dialogView, false)
                        Toast.makeText(
                            this@Window1,
                            "Error applying discount. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            showDiscountLoadingState(dialogView, false)
            Toast.makeText(
                this@Window1,
                "Please select both a discount and at least one item",
                Toast.LENGTH_SHORT
            ).show()
        }
    }




    private fun enableTransactionsForNewDay() {
    // Only enable if cash fund is available
    if (currentCashFund > 0) {
        enableTransactions()
    }

    // Always enable cash management buttons for new day
    binding.cashFundButton?.isEnabled = true
    binding.pulloutCashFundButton?.isEnabled= true
    binding.tenderDeclarationButton?.isEnabled   = true

    // Enable Z-Read and X-Read buttons
    zReadButton.isEnabled = true
    xReadButton.isEnabled = true

    // Reset Z-Read status
    resetCashManagementStatus()
}

private fun disableAllTransactionsAfterZRead() {
    // Disable all transaction buttons
    binding.payButton.isEnabled = false
    binding.clearCartButton.isEnabled = false
    binding.insertButton.isEnabled = false
    binding.priceOverrideButton?.isEnabled = false
    binding.discountButton?.isEnabled   = false
    binding.partialPaymentButton?.isEnabled  = false
    binding.voidPartialPaymentButton?.isEnabled   = false

    // Disable cash management buttons
    binding.cashFundButton?.isEnabled  = false
    binding.pulloutCashFundButton?.isEnabled  = false
    binding.tenderDeclarationButton?.isEnabled   = false

    // Disable Z-Read and X-Read buttons
    zReadButton.isEnabled = false
    xReadButton.isEnabled = false
}

// Automatic Z-Read at midnight
private suspend fun performAutomaticZRead() {
    try {
        Log.d(TAG, "Performing automatic Z-Read at midnight")

        // Get unprocessed transactions
        val transactions = transactionDao.getAllUnprocessedTransactions()

        if (transactions.isEmpty()) {
            Log.d(TAG, "No transactions to process for automatic Z-Read")
            return
        }

        // Generate Z-Report ID
        val zReportId = generateZReportId()
        val storeId = SessionManager.getCurrentUser()?.storeid ?: run {
            Log.e(TAG, "Store ID not found for automatic Z-Read")
            return
        }

        // Get tender declaration
        val currentTenderDeclaration = tenderDeclarationDao.getLatestTenderDeclaration()

        // Create Z-Read record
        val totalAmount = transactions.sumOf { it.totalAmountPaid }
        val zRead = ZRead(
            zReportId = zReportId,
            date = getCurrentDate(),
            time = getCurrentTime(),
            totalTransactions = transactions.size,
            totalAmount = totalAmount
        )

        // Save Z-Read record
        zReadDao.insert(zRead)

        // Create transaction logger for BIR purposes
        val transactionLogger = TransactionLogger(this@Window1)
        transactionLogger.logZRead(
            zReportId = zReportId,
            transactions = transactions,
            tenderDeclaration = currentTenderDeclaration
        )

        // Update transactions with Z-Report ID
        transactionViewModel.updateTransactionsZReport(storeId, zReportId)

        Log.d(TAG, "Automatic Z-Read completed successfully")

        // After automatic Z-Read, enable transactions for new day
        enableTransactionsForNewDay()

    } catch (e: Exception) {
        Log.e(TAG, "Error performing automatic Z-Read", e)
    }
}
//
//    private suspend fun hasZReadForToday(): Boolean {
//        val today = getCurrentDate()
//        return zReadDao.getZReadByDate(today) != null
//    }

    // Add this method to check if it's past midnight
    private fun isAfterMidnight(): Boolean {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"))
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        // Check if it's between 00:01 and 23:59
        return currentHour > 0 || (currentHour == 0 && currentMinute > 0)
    }

    // Modified initialization method - call this in onCreate
    private fun initializeTransactionControl() {
        lifecycleScope.launch {
            checkZReadStatusAndControlTransactions()
            setupMidnightScheduler()
        }
    }

    // Main method to check Z-Read status and control transactions
    private suspend fun checkZReadStatusAndControlTransactions() {
        val hasZReadToday = hasZReadForToday()

        if (hasZReadToday) {
            // Z-Read exists for today
            if (isAfterMidnight()) {
                // It's after midnight (00:01 onwards), disable all transactions
                disableAllTransactionsAfterZRead()
                isTransactionDisabledAfterZRead = true

                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Z-Read completed for today. Transactions disabled until next day.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                // It's exactly midnight (00:00), allow transactions
                enableTransactionsForNewDay()
                isTransactionDisabledAfterZRead = false
            }
        } else {
            // No Z-Read for today
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"))
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

            if (currentHour == 0) {
                // It's midnight and no Z-Read, perform automatic Z-Read
                performAutomaticZRead()
            } else {
                // Normal operation
                enableTransactionsForNewDay()
                isTransactionDisabledAfterZRead = false
            }
        }
    }

    // Setup scheduler to check at midnight
    private fun setupMidnightScheduler() {
        midnightHandler = Handler(Looper.getMainLooper())

        val scheduleNextCheck = {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val timeUntilMidnight = calendar.timeInMillis - System.currentTimeMillis()

            midnightRunnable = Runnable {
                lifecycleScope.launch {
                    checkZReadStatusAndControlTransactions()
                    setupMidnightScheduler() // Schedule next check
                }
            }

            midnightHandler?.postDelayed(midnightRunnable!!, timeUntilMidnight)
        }

        scheduleNextCheck()
    }


    private fun showLoadingState(isLoading: Boolean) {
        if (isMobileLayout) {
            // Mobile layout loading indicators
            findViewById<View>(R.id.cartLoadingIndicator)?.visibility = if (isLoading) View.VISIBLE else View.GONE
            findViewById<TextView>(R.id.totalAmountTextView)?.alpha = if (isLoading) 0.5f else 1.0f
            findViewById<TextView>(R.id.discountAmountText)?.alpha = if (isLoading) 0.5f else 1.0f
            findViewById<TextView>(R.id.vatAmountText)?.alpha = if (isLoading) 0.5f else 1.0f
            findViewById<TextView>(R.id.finalTotalText)?.alpha = if (isLoading) 0.5f else 1.0f
        } else {
            // Tablet layout loading indicators
            binding.apply {
                cartLoadingIndicator?.visibility = if (isLoading) View.VISIBLE else View.GONE
                totalAmountTextView.alpha = if (isLoading) 0.5f else 1.0f
                discountAmountText.alpha = if (isLoading) 0.5f else 1.0f
                vatAmountText.alpha = if (isLoading) 0.5f else 1.0f
                finalTotalText.alpha = if (isLoading) 0.5f else 1.0f
            }
        }
    }



    private fun initializeTransactionNumber() {
        loadLastTransactionNumber()
    }

    // In your ViewModel or Repository
    private suspend fun generateTransactionId(): String {
        val currentStore = SessionManager.getCurrentUser()?.storeid
            ?: throw IllegalStateException("No store ID found in current session")
        return numberSequenceRepository.getNextNumber("TRANSACTION", currentStore)
    }


    private fun loadLastTransactionNumber() {
        val sharedPref = getSharedPreferences("TransactionPrefs", Context.MODE_PRIVATE)
        lastTransactionNumber = sharedPref.getInt("lastTransactionNumber", 0)
    }

private fun initializeSequences() {
    lifecycleScope.launch {
        try {
            val currentStore = SessionManager.getCurrentUser()?.storeid
            if (currentStore != null) {
                // Initialize sequence for the current store
                numberSequenceRepository.initializeSequence(
                    type = "TRANSACTION",
                    storeId = currentStore,
                    startValue = 1,
                    paddingLength = 9
                )

                // Check current sequence value
                val currentValue = numberSequenceRepository.getCurrentValue("TRANSACTION", currentStore)
                Log.d(TAG, "Current sequence for store $currentStore: $currentValue")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing number sequences", e)
        }
    }
}
    // Add this function to save the last transaction number to SharedPreferences
    private fun saveLastTransactionNumber() {
        val sharedPref = getSharedPreferences("TransactionPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("lastTransactionNumber", lastTransactionNumber)
            apply()
        }
    }
    private fun setupSequenceButton() {
        binding.btnSetSequence?.setOnClickListener {
            showSequenceDialog()
        }
    }

    private fun showSequenceDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_sequence_number, null)
        val sequenceInput = dialogView.findViewById<EditText>(R.id.editTextSequence)
        val currentSequence = dialogView.findViewById<TextView>(R.id.textViewCurrentSequence)

        // Load current sequence for the current store
        lifecycleScope.launch {
            val currentStore = SessionManager.getCurrentUser()?.storeid
            if (currentStore != null) {
                val currentValue = numberSequenceRepository.getCurrentValue("TRANSACTION", currentStore)
                currentSequence.text = "Current Sequence: ${currentValue ?: 0}"
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Sequence Number")
            .setView(dialogView)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()

        // Replace the default positive button click listener
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val newValue = sequenceInput.text.toString().toLongOrNull()
            if (newValue == null) {
                sequenceInput.error = "Please enter a valid number"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    numberSequenceRepository.resetSequence("TRANSACTION", newValue.toString())
                    Toast.makeText(
                        this@Window1,
                        "Sequence number updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                } catch (e: Exception) {
                    Toast.makeText(
                        this@Window1,
                        "Error updating sequence: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun debugStaffRole() {
        lifecycleScope.launch {
            try {
                val staffDao = AppDatabase.getDatabase(application).staffDao()
                val currentStaffName = StaffManager.getCurrentStaff()
                val currentStoreId = SessionManager.getCurrentUser()?.storeid

                Log.d("StaffDebug", "Current Staff Name: $currentStaffName")
                Log.d("StaffDebug", "Current Store ID: $currentStoreId")

                if (currentStaffName != null && currentStoreId != null) {
                    val storeStaff = staffDao.getStaffByStore(currentStoreId)
                    val currentStaffMember = storeStaff.find { it.name == currentStaffName }

                    Log.d("StaffDebug", "Current Staff Member: $currentStaffMember")
                    Log.d("StaffDebug", "Staff Role: ${currentStaffMember?.role}")
                    Log.d("StaffDebug", "Is Supervisor: ${currentStaffMember?.role?.uppercase() == "SV"}")
                }
            } catch (e: Exception) {
                Log.e("StaffDebug", "Error debugging staff role", e)
            }
        }
    }
    fun StaffEntity.isSupervisor(): Boolean = this.role.uppercase() in listOf("SV", "CH")

    private suspend fun checkSupervisorAccess(): Boolean {
        return try {
            val staffDao = AppDatabase.getDatabase(application).staffDao()
            val currentStaffName = StaffManager.getCurrentStaff()
            val currentStoreId = SessionManager.getCurrentUser()?.storeid

            if (currentStaffName == null || currentStoreId == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error: Staff or store information not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            // Get all staff for the current store and find the matching staff member
            val storeStaff = staffDao.getStaffByStore(currentStoreId)
            val currentStaffMember = storeStaff.find { it.name == currentStaffName }

            if (currentStaffMember == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error: Current staff not found in database",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return false
            }

            val isSupervisor = currentStaffMember.role.uppercase() in listOf("SV", "CH")

            if (!isSupervisor) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "This action requires supervisor or cluster head access",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            return isSupervisor
        } catch (e: Exception) {
            Log.e("Window1", "Error checking supervisor access", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@Window1,
                    "Error checking staff permissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
            false
        }
    }
    private fun checkStaffAndProceed(action: () -> Unit) {
        if (!StaffManager.hasActiveStaff()) {
            Toast.makeText(
                this,
                "Please select a staff member to proceed",
                Toast.LENGTH_LONG
            ).show()
            showStaffSelectionDialog()
            return
        }
        action()
    }
    private fun setupStaffSelection() {
        val staffSelectorContainer = findViewById<CardView>(R.id.staffSelectorContainer)
        val staffNameTextView = findViewById<TextView>(R.id.staffNameTextView)
        val staffIcon = findViewById<ImageView>(R.id.staffIcon)

        fun updateStaffDisplay(staffName: String?) {
            // Trim the staff name to 20 characters and add ellipsis if needed
            val displayName = staffName?.let { name ->
                if (name.length > 20) {
                    "${name.take(17)}..."
                } else {
                    name
                }
            } ?: "Select Staff"

            staffNameTextView.apply {
                text = displayName
                setTextColor(
                    ContextCompat.getColor(
                        this@Window1,
                        if (staffName == null) R.color.red else R.color.navy
                    )
                )
                // Add ellipsize in case the text still overflows
                ellipsize = TextUtils.TruncateAt.END
                maxLines = 1
            }

            staffIcon.setColorFilter(
                ContextCompat.getColor(
                    this,
                    if (staffName == null) R.color.red else R.color.navy
                )
            )
        }

        // Set initial state
        updateStaffDisplay(StaffManager.getCurrentStaff())

        // Update listener
        StaffManager.setOnStaffChangeListener { newStaffName ->
            runOnUiThread {
                updateStaffDisplay(newStaffName)
            }
        }


        // Continuous floating animation for the card
        val floatingAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                val value = animator.animatedValue as Float
                val translateY = sin(value * Math.PI.toFloat()) * 6
                staffSelectorContainer.translationY = translateY
            }
        }
        floatingAnimation.start()

        // Continuous shadow animation
        val shadowAnimation = ValueAnimator.ofFloat(4f, 12f).apply {
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                staffSelectorContainer.cardElevation = animator.animatedValue as Float
            }
        }
        shadowAnimation.start()

        // Text shimmer effect animation
        val textShimmerAnimation = ObjectAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animator ->
                val value = animator.animatedValue as Float
                val shader = LinearGradient(
                    0f,
                    0f,
                    staffNameTextView.width.toFloat(),
                    0f,
                    intArrayOf(
                        Color.parseColor("#1e3a8a"),  // Navy
                        Color.parseColor("#60a5fa"),  // Bright Blue
                        Color.parseColor("#1e3a8a")   // Navy
                    ),
                    floatArrayOf(
                        (value - 0.2f).coerceIn(0f, 1f),
                        value,
                        (value + 0.2f).coerceIn(0f, 1f)
                    ),
                    Shader.TileMode.CLAMP
                )
                staffNameTextView.paint.shader = shader
                staffNameTextView.invalidate()
            }
        }
        textShimmerAnimation.start()

        // Click animation
        staffSelectorContainer.setOnClickListener { view ->
            view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction {
                    view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .setInterpolator(OvershootInterpolator())
                        .start()
                }
                .start()

            showStaffSelectionDialog()
        }

        // Staff change animation
        StaffManager.setOnStaffChangeListener { newStaffName ->
            runOnUiThread {
                // Fade out and in animation
                staffNameTextView.animate()
                    .alpha(0f)
                    .setDuration(150)
                    .withEndAction {
                        staffNameTextView.text = "Staff: $newStaffName"
                        staffNameTextView.animate()
                            .alpha(1f)
                            .setDuration(150)
                            .start()
                    }
                    .start()
            }
        }

        // Set initial text
        staffNameTextView.text = "Staff: ${StaffManager.getCurrentStaff() ?: "Select Staff"}"
    }
    // Custom TranslationYSpan for wave effect
    private class TranslationYSpan(private val translationY: Float) : ReplacementSpan() {
        override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
            return paint.measureText(text, start, end).roundToInt()
        }

        override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
            canvas.save()
            canvas.translate(0f, translationY)
            canvas.drawText(text, start, end, x, y.toFloat(), paint)
            canvas.restore()
        }
    }

    // Particle effect for text change
    private fun createParticleEffect(view: View) {
        val particleCount = 20
        val container = view.parent as ViewGroup
        val location = IntArray(2)
        view.getLocationInWindow(location)

        repeat(particleCount) {
            val particle = View(view.context).apply {
                setBackgroundResource(R.drawable.particle_dot) // Create a small circular drawable
                alpha = 1f
                scaleX = 0f
                scaleY = 0f
                translationX = location[0] + view.width / 2f
                translationY = location[1] + view.height / 2f
            }

            container.addView(particle, 8, 8)

            val angle = (it * 360f / particleCount)
            val distance = 100f

            particle.animate()
                .translationX(location[0] + view.width / 2f + cos(Math.toRadians(angle.toDouble())).toFloat() * distance)
                .translationY(location[1] + view.height / 2f + sin(Math.toRadians(angle.toDouble())).toFloat() * distance)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(0f)
                .setDuration(500)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { container.removeView(particle) }
                .start()
        }
    }
//    private fun showStaffSelectionDialog() {
//        val dialogView = layoutInflater.inflate(R.layout.dialog_staff_selection, null)
//        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.staffRecyclerView)
//        val passcodeInput = dialogView.findViewById<EditText>(R.id.passcodeInput)
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        val staffAdapter = StaffAdapter()
//        recyclerView.adapter = staffAdapter
//
//        var selectedStaff: StaffEntity? = null
//        staffAdapter.onStaffSelected = { staff ->
//            selectedStaff = staff
//        }
//
//        lifecycleScope.launch {
//            try {
//                val staffDao = AppDatabase.getDatabase(application).staffDao()
//                val currentStoreId = SessionManager.getCurrentUser()?.storeid ?: return@launch
//                val staffList = withContext(Dispatchers.IO) {
//                    staffDao.getStaffByStore(currentStoreId)
//                }
//                staffAdapter.updateStaff(staffList)
//            } catch (e: Exception) {
//                Log.e("Window1", "Error loading staff", e)
//                Toast.makeText(this@Window1, "Error loading staff data", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        val dialog = AlertDialog.Builder(this, R.style.CustomDialogStyle3)
//            .setView(dialogView)
//            .setPositiveButton("Confirm", null)
//            .setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//            }
//            .create()
//
//        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
//
//        dialog.setOnShowListener {
//            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            positiveButton.setOnClickListener {
//                handleStaffLogin(selectedStaff, passcodeInput.text.toString(), dialog)
//            }
//        }
//
//        dialog.show()
//    }
private fun showStaffSelectionDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_staff_selection, null)
    val recyclerView = dialogView.findViewById<RecyclerView>(R.id.staffRecyclerView)
    val passcodeInput = dialogView.findViewById<EditText>(R.id.passcodeInput)
    val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
    val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

    // Apply mobile-specific styling if needed
    if (isMobileLayout) {
        passcodeInput.textSize = 14f
        confirmButton.textSize = 12f
        cancelButton.textSize = 12f
    }

    recyclerView.layoutManager = LinearLayoutManager(this)
    val staffAdapter = StaffAdapter()
    recyclerView.adapter = staffAdapter

    var selectedStaff: StaffEntity? = null
    staffAdapter.onStaffSelected = { staff ->
        selectedStaff = staff
    }

    lifecycleScope.launch {
        try {
            val staffDao = AppDatabase.getDatabase(application).staffDao()
            val currentStoreId = SessionManager.getCurrentUser()?.storeid ?: return@launch
            val staffList = withContext(Dispatchers.IO) {
                staffDao.getStaffByStore(currentStoreId)
            }
            staffAdapter.updateStaff(staffList)
        } catch (e: Exception) {
            Log.e("Window1", "Error loading staff", e)
            Toast.makeText(this@Window1, "Error loading staff data", Toast.LENGTH_SHORT).show()
        }
    }

    val titleView = TextView(this@Window1)
//    titleView.text = "Select Staff"
    titleView.textSize = if (isMobileLayout) 16f else 18f
    titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
    titleView.setPadding(
        if (isMobileLayout) 50 else 24,  // left
        if (isMobileLayout) 50 else 20,  // top
        if (isMobileLayout) 16 else 24,  // right
        if (isMobileLayout) 8 else 12    // bottom
    )
    titleView.gravity = Gravity.CENTER_VERTICAL
    titleView.setTypeface(null, Typeface.BOLD)

    val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//        .setCustomTitle(titleView)
        .setView(dialogView)
        .create()

    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

    // Set up custom button click listeners
    confirmButton.setOnClickListener {
        handleStaffLogin(selectedStaff, passcodeInput.text.toString(), dialog)
    }

    cancelButton.setOnClickListener {
        dialog.dismiss()
    }

    dialog.show()

    // Apply mobile styling after dialog is shown
    applyMobileDialogStyling(dialog)
}

    private fun handleStaffLogin(selectedStaff: StaffEntity?, passcode: String, dialog: AlertDialog) {
        when {
            selectedStaff == null -> {
                Toast.makeText(this, "Please select a staff member", Toast.LENGTH_SHORT).show()
                return
            }
            passcode.isEmpty() -> {
                Toast.makeText(this, "Please enter passcode", Toast.LENGTH_SHORT).show()
                return
            }
            selectedStaff.passcode != passcode -> {
                Toast.makeText(this, "Invalid passcode", Toast.LENGTH_SHORT).show()
                return
            }
            selectedStaff.image.isNullOrEmpty() -> {
                dialog.dismiss()
                showProfilePictureDialog(selectedStaff) { updatedStaff ->
                    lifecycleScope.launch {
                        updateStaffInDatabase(updatedStaff)
                        StaffManager.setCurrentStaff(updatedStaff)
                        Toast.makeText(this@Window1, "Staff changed to: ${updatedStaff.name}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> {
                StaffManager.setCurrentStaff(selectedStaff)
                Toast.makeText(this, "Staff changed to: ${selectedStaff.name}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }


    private fun setupActivityResultLaunchers() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                takePicture.launch(null)
            } else {
                Toast.makeText(this, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show()
            }
        }

        takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                showImageConfirmationDialog(it)
            }
        }

        pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    showImageConfirmationDialog(bitmap)
                } catch (e: Exception) {
                    Log.e("Window1", "Error processing picked image", e)
                    Toast.makeText(this, "Error processing image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showImageConfirmationDialog(bitmap: Bitmap) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_image_confirmation, null)

        // Get references to views
        val previewImageView = dialogView.findViewById<ImageView>(R.id.previewImageView)
        val backButton = dialogView.findViewById<ImageButton>(R.id.backButton)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmButton)
        val retakeButton = dialogView.findViewById<Button>(R.id.retakeButton)

        // Apply mobile-specific styling if needed
        if (isMobileLayout) {
            confirmButton.textSize = 12f
            retakeButton.textSize = 12f
        }

        // Show the captured/selected image
        previewImageView.setImageBitmap(bitmap)

        val titleView = TextView(this@Window1)
//        titleView.text = "Confirm Profile Picture"
        titleView.textSize = if (isMobileLayout) 16f else 18f
        titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
        titleView.setPadding(
            if (isMobileLayout) 50 else 24,  // left
            if (isMobileLayout) 50 else 20,  // top
            if (isMobileLayout) 16 else 24,  // right
            if (isMobileLayout) 8 else 12    // bottom
        )
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTypeface(null, Typeface.BOLD)

        // Create dialog without default buttons since we're using custom ones
        val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//            .setCustomTitle(titleView)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Apply the custom background
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        // Set up button click listeners
        confirmButton.setOnClickListener {
            handleCapturedImage(bitmap, currentImageView) { base64String ->
                currentStaff?.let { staff ->
                    currentDialog?.let { dialog ->
                        currentProfileSetCallback?.let { callback ->
                            updateStaffProfile(staff, base64String, dialog, callback)
                        }
                    }
                }
            }
            dialog.dismiss()
        }

        retakeButton.setOnClickListener {
            dialog.dismiss()
            // Reopen the camera to retake the photo
            requestCameraPermission()
        }

        backButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        // Apply mobile styling after dialog is shown
        applyMobileDialogStyling(dialog)
    }

    private fun showProfilePictureDialog(staff: StaffEntity, onProfileSet: (StaffEntity) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_profile_picture, null)

        // Get references to views
        val imageView = dialogView.findViewById<ImageView>(R.id.profileImageView)
        val backButton = dialogView.findViewById<ImageButton>(R.id.backButton)
        val takePhotoButton = dialogView.findViewById<Button>(R.id.takePhotoButton)
        val chooseGalleryButton = dialogView.findViewById<Button>(R.id.chooseGalleryButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)
        val cancelButtonContainer = dialogView.findViewById<androidx.cardview.widget.CardView>(R.id.cancelButtonContainer)

        // Apply mobile-specific styling if needed
        if (isMobileLayout) {
            takePhotoButton.textSize = 12f
            chooseGalleryButton.textSize = 12f
            cancelButton.textSize = 12f
        }

        // Store references for use in ActivityResultLaunchers
        currentImageView = imageView
        currentStaff = staff
        currentProfileSetCallback = onProfileSet

        val titleView = TextView(this@Window1)
//        titleView.text = "Set Profile Picture"
        titleView.textSize = if (isMobileLayout) 16f else 18f
        titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
        titleView.setPadding(
            if (isMobileLayout) 50 else 24,  // left
            if (isMobileLayout) 50 else 20,  // top
            if (isMobileLayout) 16 else 24,  // right
            if (isMobileLayout) 8 else 12    // bottom
        )
        titleView.gravity = Gravity.CENTER_VERTICAL
        titleView.setTypeface(null, Typeface.BOLD)

        // Create dialog without default buttons since we're using custom ones
        val dialog = AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//            .setCustomTitle(titleView)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Apply the custom background
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        currentDialog = dialog

        // Set up button click listeners
        takePhotoButton.setOnClickListener {
            requestCameraPermission()
        }

        chooseGalleryButton.setOnClickListener {
            pickImage.launch("image/*")
        }

        backButton.setOnClickListener {
            dialog.dismiss()
        }

        // Show cancel button if needed (optional - you can make this visible if required)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        // Optional: Make cancel button visible if you want it
        // cancelButtonContainer.visibility = View.VISIBLE

        dialog.setOnDismissListener {
            currentDialog = null
            currentImageView = null
            currentStaff = null
            currentProfileSetCallback = null
        }

        dialog.show()

        // Apply mobile styling after dialog is shown
        applyMobileDialogStyling(dialog)
    }

//    private fun requestCameraPermission() {
//        when {
//            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
//                takePicture.launch(null)
//            }
//            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
//                AlertDialog.Builder(this)
//                    .setTitle("Camera Permission Required")
//                    .setMessage("We need camera permission to take profile pictures")
//                    .setPositiveButton("Grant") { _, _ ->
//                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//                    }
//                    .setNegativeButton("Cancel", null)
//                    .show()
//            }
//            else -> {
//                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
//            }
//        }
//    }
private fun requestCameraPermission() {
    when {
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED -> {
            // Launch camera to take picture
            takePicture.launch(null)
        }
        shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
            val titleView = TextView(this@Window1)
//            titleView.text = "Camera Permission Required"
            titleView.textSize = if (isMobileLayout) 16f else 18f
            titleView.setTextColor(ContextCompat.getColor(this@Window1, android.R.color.black))
            titleView.setPadding(
                if (isMobileLayout) 50 else 24,  // left
                if (isMobileLayout) 50 else 20,  // top
                if (isMobileLayout) 16 else 24,  // right
                if (isMobileLayout) 8 else 12    // bottom
            )
            titleView.gravity = Gravity.CENTER_VERTICAL
            titleView.setTypeface(null, Typeface.BOLD)

            AlertDialog.Builder(this@Window1, R.style.CustomDialogStyle3)
//                .setCustomTitle(titleView)
                .setMessage("Camera access is needed to take profile pictures for staff members")
                .setPositiveButton("Grant Permission") { _, _ ->
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .apply {
                    window?.setBackgroundDrawableResource(R.drawable.dialog_background)
                    show()
                    applyMobileDialogStyling(this)
                }
        }
        else -> {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}

    private fun handleCapturedImage(bitmap: Bitmap, imageView: ImageView?, onProcessed: (String) -> Unit) {
        lifecycleScope.launch(Dispatchers.Default) {
            val resizedBitmap = resizeImage(bitmap, 500, 500)
            val base64String = bitmapToBase64(resizedBitmap)

            withContext(Dispatchers.Main) {
                imageView?.setImageBitmap(resizedBitmap)
                onProcessed(base64String)
            }
        }
    }
    private suspend fun updateStaffInDatabase(staff: StaffEntity) {
        withContext(Dispatchers.IO) {
            try {
                val staffDao = AppDatabase.getDatabase(application).staffDao()
                staffDao.updateStaff(staff)
            } catch (e: Exception) {
                Log.e("Window1", "Error updating staff in database", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error saving staff data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun updateStaffProfile(
        staff: StaffEntity,
        base64Image: String,
        dialog: AlertDialog,
        onProfileSet: (StaffEntity) -> Unit
    ) {
        lifecycleScope.launch {
            try {
                val staffDao = AppDatabase.getDatabase(application).staffDao()
                val updatedStaff = staff.copy(image = base64Image)

                withContext(Dispatchers.IO) {
                    staffDao.updateStaff(updatedStaff)
                }

                dialog.dismiss()
                onProfileSet(updatedStaff)

                // Refresh the staff list to show updated image
                refreshStaffList()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error updating profile picture",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun refreshStaffList() {
        lifecycleScope.launch {
            try {
                val staffDao = AppDatabase.getDatabase(application).staffDao()
                val currentStoreId = SessionManager.getCurrentUser()?.storeid ?: return@launch
                val staffList = withContext(Dispatchers.IO) {
                    staffDao.getStaffByStore(currentStoreId)
                }
                // Update the RecyclerView adapter
                // This assumes you have a reference to your staffAdapter
                // staffAdapter.updateStaff(staffList)
            } catch (e: Exception) {
                Log.e("Window1", "Error refreshing staff list", e)
            }
        }
    }


    private fun resizeImage(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        var width = image.width
        var height = image.height

        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight

        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }

        return Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun verifyStaffPasscode(passcode: String) {
        lifecycleScope.launch {
            try {
                val staffDao = AppDatabase.getDatabase(application).staffDao()
                val currentStoreId = SessionManager.getCurrentUser()?.storeid ?: return@launch

                val staff = staffDao.getStaffByStoreAndPasscode(currentStoreId, passcode)
                if (staff != null) {
                    StaffManager.setCurrentStaff(staff)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Staff changed to: ${staff.name}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Invalid passcode",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("Window1", "Error verifying staff passcode", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error verifying passcode",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun updateStaffDisplay() {
        // Update any TextViews or UI elements that show the current staff name
        val currentStaff = StaffManager.getCurrentStaff()
        // Example: staffNameTextView.text = currentStaff
    }
       private fun getCurrentStore(): String {
        return SessionManager.getCurrentUser()?.storeid ?: "Unknown Store"
    }

    private fun getCurrentStaff(): String {
        return StaffManager.getCurrentStaff()
            ?: throw IllegalStateException("No staff selected")
    }


    private fun updateHeaderInfo() {
        staffNameTextView = findViewById(R.id.staffNameTextView)
        val storeNameTextView = findViewById<TextView>(R.id.storeNameTextView)

        val currentStaff = StaffManager.getCurrentStaff()
        val currentStore = SessionManager.getCurrentUser()?.storeid ?: "Unknown Store"

        // Trim staff name to 20 characters with ellipsis
        val displayStaffName = currentStaff?.let { name ->
            if (name.length > 20) {
                "${name.take(17)}..."
            } else {
                name
            }
        } ?: ""

        staffNameTextView.text = ": $displayStaffName"
        storeNameTextView.text = "Store: $currentStore"
    }
    private fun processLoyaltyCardPayment(cardNumber: String, points: Int) {
        lifecycleScope.launch {
            try {
                loyaltyCardViewModel.updateCardPoints(cardNumber, points)
            } catch (e: Exception) {
                Log.e("Payment", "Error processing loyalty card payment", e)
                Toast.makeText(
                    this@Window1,
                    "Error processing loyalty card payment: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
//    private fun processPayment(
//        amountPaid: Double,
//        paymentMethod: String,
//        vatRate: Double,
//        discountType: String,
//        discountValue: Double,
//        selectedCustomer: Customer,
//        totalAmountDue: Double,
//        otherPaymentMethods: List<String> = emptyList(),
//        otherPaymentAmounts: List<Double> = emptyList()
//    ) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                var pointsUsed = 0
//                var loyaltyCardUsed: LoyaltyCard? = null
//                val pointsEarned = (totalAmountDue / 200).toInt()
//
//                if (selectedCustomer.accountNum.startsWith("LC-")) {
//                    val cardNumber = selectedCustomer.accountNum.removePrefix("LC-")
//
//                    try {
//                        val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(cardNumber)
//
//                        if (loyaltyCard != null) {
//                            if (paymentMethod.uppercase() == "LOYALTYCARD") {
//                                // Point redemption case
//                                val pointsToDeduct = amountPaid.toInt()
//                                if (pointsToDeduct > loyaltyCard.points) {
//                                    withContext(Dispatchers.Main) {
//                                        Toast.makeText(this@Window1, "Insufficient points", Toast.LENGTH_SHORT).show()
//                                    }
//                                    return@launch
//                                }
//
//                                val finalPoints = loyaltyCard.points - pointsToDeduct + pointsEarned
//                                try {
//                                    loyaltyCardViewModel.updateCardPoints(cardNumber, finalPoints)
//                                    withContext(Dispatchers.Main) {
//                                        Toast.makeText(
//                                            this@Window1,
//                                            "Points updated: -$pointsToDeduct (used) +$pointsEarned (earned) = $finalPoints",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                } catch (e: Exception) {
//                                    Log.e("Payment", "Failed to update loyalty points", e)
//                                }
//                            } else {
//                                // Normal payment case - just add points
//                                val newPoints = loyaltyCard.points + pointsEarned
//                                try {
//                                    loyaltyCardViewModel.updateCardPoints(cardNumber, newPoints)
//                                    withContext(Dispatchers.Main) {
//                                        Toast.makeText(
//                                            this@Window1,
//                                            "Earned $pointsEarned points! New balance: $newPoints",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                } catch (e: Exception) {
//                                    Log.e("Payment", "Failed to add loyalty points", e)
//                                }
//                            }
//                        }
//                    } catch (e: Exception) {
//                        Log.e("Payment", "Error accessing loyalty card", e)
//                    }
//                }
//
//
//                // Your existing payment processing code
//                Log.d("Payment", "Starting payment process...")
//                val cartItems = cartViewModel.getAllCartItems(windowId).first()
//
//                val transactionComment = cartItems.firstOrNull()?.cartComment ?: ""
//
//                if (cartItems.isEmpty()) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "Cannot process payment. Cart is empty.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    return@launch
//                }
//
//                val currentStore = SessionManager.getCurrentUser()?.storeid
//                Log.d("Payment", "Current store ID: $currentStore")
//
//                if (currentStore == null) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "No store ID found in session",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    return@launch
//                }
//
//                try {
//                    Log.d("Payment", "Fetching number sequence for store: $currentStore")
//                    val numberSequenceResult = numberSequenceRemoteRepository.fetchAndUpdateNumberSequence(currentStore)
//
//                    numberSequenceResult.onSuccess { numberSequence ->
//                        Log.d("Payment", "Successfully fetched number sequence: $numberSequence")
//
//                        // All database operations within withContext(Dispatchers.IO)
//                        withContext(Dispatchers.IO) {
//                            val transactionId = numberSequenceRemoteRepository.getNextTransactionNumber(currentStore)
//                            Log.d("Payment", "Generated transaction ID: $transactionId")
//
//                            val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
//
//                            Log.d("Payment", "Generated store key: $storeKey")
//
//
//                            var gross = 0.0
//                            var totalDiscount = 0.0
//                            var bundleDiscount = 0.0
//                            var vatAmount = 0.0
//                            var vatableSales = 0.0
//                            var partialPayment = 0.0
//                            var priceOverrideTotal = 0.0
//                            var hasPriceOverride = false
//
//                            // Get existing discount from first cart item if partial payment exists
//                            val existingPartialPayment = cartItems.firstOrNull()?.partialPayment ?: 0.0
//                            val existingDiscount = if (existingPartialPayment > 0) {
//                                cartItems.firstOrNull()?.discountAmount ?: 0.0
//                            } else null
//
//                            // Group items by bundleId for bundle discount calculation
//                            val bundledItems = cartItems.groupBy { it.bundleId }
//
//                            // Calculate totals from cart items
//                            cartItems.forEach { cartItem ->
//                                val originalPrice = cartItem.price
//                                val effectivePrice = cartItem.overriddenPrice ?: originalPrice
//                                val itemTotal = effectivePrice * cartItem.quantity
//                                gross += itemTotal
//                                partialPayment = cartItem.partialPayment
//
//                                // If we have existing discount from partial payment, use that instead of recalculating
//                                if (existingDiscount != null) {
//                                    totalDiscount = existingDiscount
//                                } else {
//                                    // Calculate regular discounts
//                                    when (cartItem.discountType.uppercase()) {
//                                        "FIXEDTOTAL" -> {
//                                            if (cartItem.bundleId == null) {
//                                                totalDiscount += cartItem.discountAmount
//                                            }
//                                        }
//                                        "PERCENTAGE", "PWD", "SC" -> {
//                                            if (cartItem.bundleId == null) {
//                                                totalDiscount += itemTotal * (cartItem.discount / 100)
//                                            }
//                                        }
//                                        "FIXED" -> {
//                                            if (cartItem.bundleId == null) {
//                                                totalDiscount += cartItem.discount * cartItem.quantity
//                                            }
//                                        }
//                                    }
//
//                                    // Calculate bundle discounts
//                                    if (cartItem.bundleId != null) {
//                                        val bundleItems = bundledItems[cartItem.bundleId]
//                                        if (bundleItems != null) {
//                                            when (cartItem.discountType.uppercase()) {
//                                                "DEAL" -> bundleDiscount += cartItem.discount
//                                                "PERCENTAGE" -> bundleDiscount += itemTotal * (cartItem.discount / 100)
//                                                "FIXEDTOTAL" -> bundleDiscount += cartItem.discount
//                                            }
//                                        }
//                                    }
//                                }
//
//                                vatableSales += itemTotal / vatRate
//                                vatAmount += itemTotal * 0.12 / 1.12
//                            }
//
//                            // Add bundle discount to total discount
//                            totalDiscount += bundleDiscount
//
//                            val discountedSubtotal = gross - totalDiscount
//                            val netSales = discountedSubtotal - partialPayment
//                            val totalAmountDue = netSales.roundToTwoDecimals()
////                val transactionId = cartItems.firstOrNull()?.transactionId ?: generateTransactionId()
//
//
////            val transactionId = numberSequenceRepository.getNextNumber("TRANSACTION", currentStore)
////
////
////            val storeKey = numberSequenceRepository.getCurrentStoreKey("TRANSACTION", currentStore)
//
//                            // Create store-specific sequence
//                            val storeSequence = "$currentStore-${transactionId.split("-").last()}"
//                            // Determine if the payment method is AR
//                            val isAR = paymentMethod != "Cash"
//
////                            val change = if (isAR) 0.0 else (amountPaid - totalAmountDue).roundToTwoDecimals()
//                            val ar = if (isAR) totalAmountDue else 0.0
//
//                            val paymentMethodAmounts = mutableMapOf(
//                                "GCASH" to 0.0,
//                                "PAYMAYA" to 0.0,
//                                "CASH" to 0.0,
//                                "CARD" to 0.0,
//                                "LOYALTYCARD" to 0.0,
//                                "CHARGE" to 0.0,
//                                "FOODPANDA" to 0.0,
//                                "GRABFOOD" to 0.0,
//                                "REPRESENTATION" to 0.0
//                            )
//
//                            val partialPayments = cartItems.firstOrNull()?.let { firstItem ->
//                                mapOf(
//                                    "GCASH" to if (firstItem.paymentMethod?.uppercase() == "GCASH") firstItem.partialPayment else 0.0,
//                                    "PAYMAYA" to if (firstItem.paymentMethod?.uppercase() == "PAYMAYA") firstItem.partialPayment else 0.0,
//                                    "CASH" to if (firstItem.paymentMethod?.uppercase() == "CASH") firstItem.partialPayment else 0.0,
//                                    "CARD" to if (firstItem.paymentMethod?.uppercase() == "CARD") firstItem.partialPayment else 0.0,
//                                    "LOYALTYCARD" to if (firstItem.paymentMethod?.uppercase() == "LOYALTYCARD") firstItem.partialPayment else 0.0,
//                                    "CHARGE" to if (firstItem.paymentMethod?.uppercase() == "CHARGE") firstItem.partialPayment else 0.0,
//                                    "FOODPANDA" to if (firstItem.paymentMethod?.uppercase() == "FOODPANDA") firstItem.partialPayment else 0.0,
//                                    "GRABFOOD" to if (firstItem.paymentMethod?.uppercase() == "GRABFOOD") firstItem.partialPayment else 0.0,
//                                    "REPRESENTATION" to if (firstItem.paymentMethod?.uppercase() == "REPRESENTATION") firstItem.partialPayment else 0.0
//                                )
//                            } ?: emptyMap()
//                            var totalPaidAmount = 0.0
//                            var actualPaymentAmount = totalAmountDue
//
//
//                            paymentMethodAmounts[paymentMethod.uppercase()] = amountPaid
//                            totalPaidAmount += amountPaid
//                            // Set the primary payment method amount
////                            val primaryPaymentKey = paymentMethod.uppercase()
////                            paymentMethodAmounts[primaryPaymentKey] = amountPaid
//
//                            // Distribute remaining payment methods if provided
//                            otherPaymentMethods.forEachIndexed { index, method ->
//                                if (index < otherPaymentAmounts.size) {
//                                    val currentAmount = paymentMethodAmounts[method.uppercase()] ?: 0.0
//                                    paymentMethodAmounts[method.uppercase()] = currentAmount + otherPaymentAmounts[index]
//                                    totalPaidAmount += otherPaymentAmounts[index]
//                                }
//                            }
//                            val change = (totalPaidAmount - totalAmountDue).coerceAtLeast(0.0)
//
//                            // If there's change and the primary payment method is CASH, reduce the CASH amount
//                            if (change > 0 && paymentMethodAmounts["CASH"]!! > 0) {
//                                paymentMethodAmounts["CASH"] = paymentMethodAmounts["CASH"]!! - change
//                            }
//                            // Prepare the transaction summary
//                            val transactionSummary = TransactionSummary(
//                                transactionId = transactionId,
//                                type = if (isAR) 3 else 1, // 3 for AR, 1 for Cash
//                                receiptId = transactionId,
//                                store = getCurrentStore(),
//                                staff = getCurrentStaff(),
//                                storeKey = storeKey,
//                                storeSequence = storeSequence,
//                                customerAccount = selectedCustomer.name,
//                                netAmount = discountedSubtotal,
//                                costAmount = gross - vatAmount,
//                                grossAmount = gross,
//                                partialPayment = partialPayment,
//                                transactionStatus = 1,
//                                discountAmount = totalDiscount,
//                                customerDiscountAmount = totalDiscount,
//                                totalDiscountAmount = totalDiscount,
//                                numberOfItems = cartItems.sumOf { it.quantity }.toDouble(),
//                                refundReceiptId = null,
//                                currency = "PHP",
//                                zReportId = null,
//                                createdDate = Date(),
//                                priceOverride = if (hasPriceOverride) priceOverrideTotal else 0.0,
//                                comment = transactionComment,
//                                receiptEmail = null,
//                                markupAmount = 0.0,
//                                markupDescription = null,
//                                taxIncludedInPrice = vatAmount,
//                                windowNumber = windowId,
//                                // Update these payment fields
//                                gCash = (paymentMethodAmounts["GCASH"] ?: 0.0) + (partialPayments["GCASH"] ?: 0.0),
//                                payMaya = (paymentMethodAmounts["PAYMAYA"] ?: 0.0) + (partialPayments["PAYMAYA"] ?: 0.0),
//                                cash = (paymentMethodAmounts["CASH"] ?: 0.0) + (partialPayments["CASH"] ?: 0.0),
//                                card = (paymentMethodAmounts["CARD"] ?: 0.0) + (partialPayments["CARD"] ?: 0.0),
//                                loyaltyCard = (paymentMethodAmounts["LOYALTYCARD"] ?: 0.0) + (partialPayments["LOYALTYCARD"] ?: 0.0),
//                                charge = (paymentMethodAmounts["CHARGE"] ?: 0.0) + (partialPayments["CHARGE"] ?: 0.0),
//                                foodpanda = (paymentMethodAmounts["FOODPANDA"] ?: 0.0) + (partialPayments["FOODPANDA"] ?: 0.0),
//                                grabfood = (paymentMethodAmounts["GRABFOOD"] ?: 0.0) + (partialPayments["GRABFOOD"] ?: 0.0),
//                                representation = (paymentMethodAmounts["REPRESENTATION"] ?: 0.0) + (partialPayments["REPRESENTATION"] ?: 0.0),
//                                changeGiven = change,
//                                totalAmountPaid = if (isAR) 0.0 else amountPaid,  // Make sure this is set correctly
//                                paymentMethod = paymentMethod,
//                                customerName = selectedCustomer.name,
//                                vatAmount =  (discountedSubtotal / 1.12) * 0.12,
//                                vatExemptAmount = 0.0,
//                                vatableSales = discountedSubtotal / 1.12,
//                                discountType = discountType,
//                                syncStatus = false
//                            )
//
//                            // Insert the transaction summary into the database
//                            transactionDao.insertTransactionSummary(transactionSummary)
//
//                            updateTransactionRecords(
//                                transactionId,
//                                cartItems,
//                                paymentMethod,
//                                ar,
//                                vatRate,
//                                totalDiscount,
//                                netSales,
//                                partialPayment,
//                                discountType
//                            )
//
//                            val transactionRecords = getTransactionRecords(
//                                transactionId,
//                                cartItems,
//                                paymentMethod,
//                                ar,
//                                vatRate,
//                                discountType
//                            )
//                            transactionRecords.forEach { record ->
//                                record.syncStatusRecord = false
//                            }
//
//                            // UI operations moved to Main dispatcher
//                            withContext(Dispatchers.Main) {
//                                // Your existing receipt printing and UI update code
//                                if (isAR) {
//                                    printReceiptWithBluetoothPrinter(
//                                        transactionSummary,
//                                        transactionRecords,
//                                        BluetoothPrinterHelper.ReceiptType.AR,
//                                        isARReceipt = true,
//                                        copyType = "Customer Copy"
//                                    )
//                                    printReceiptWithBluetoothPrinter(
//                                        transactionSummary,
//                                        transactionRecords,
//                                        BluetoothPrinterHelper.ReceiptType.AR,
//                                        isARReceipt = true,
//                                        copyType = "Staff Copy"
//                                    )
//                                } else {
//                                    printReceiptWithBluetoothPrinter(
//                                        transactionSummary,
//                                        transactionRecords,
//                                        BluetoothPrinterHelper.ReceiptType.ORIGINAL
//                                    )
//                                }
//                                handleLoyaltyPoints(
//                                    selectedCustomer,
//                                    paymentMethod,
//                                    totalAmountDue,
//                                    amountPaid
//                                )
//                                showChangeAndReceiptDialog(
//                                    change,
//                                    cartItems,
//                                    transactionId,
//                                    paymentMethod,
//                                    ar,
//                                    vatAmount,
//                                    totalDiscount,
//                                    netSales,
//                                    if (gross > 0) totalDiscount / gross else 0.0,
//                                    transactionComment,
//                                    partialPayment,
//                                    amountPaid
//                                )
//                            }
//
//                            // Back to IO dispatcher for cleanup
//                            transactionDao.updateSyncStatus(transactionId, false)
//                            cartViewModel.deleteAll(windowId)
//                            cartViewModel.clearCartComment(windowId)
//
//                            withContext(Dispatchers.Main) {
//                                updateTotalAmount(emptyList())
//                                SessionManager.setCurrentNumberSequence(numberSequence)
//
//                                val items = withContext(Dispatchers.IO) {
//                                    transactionDao.getTransactionItems(transactionId)
//                                }
//
//                                val transactionLogger = TransactionLogger(this@Window1)
//                                transactionLogger.logPayment(
//                                    transactionSummary = transactionSummary,
//                                    paymentMethod = paymentMethod,
//                                    items = items
//                                )
//
//                            }
//                        }
//                    }.onFailure { error ->
//                        Log.e("Payment", "Failed to fetch number sequence: ${error.message}")
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                this@Window1,
//                                "Failed to generate transaction number: ${error.message}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.e("Payment", "Error in number sequence processing", e)
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "Error in number sequence: ${e.message}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("Payment", "Error during payment processing: ${e.message}")
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@Window1,
//                        "Error processing payment: ${e.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }
//    }

//    private fun processPayment(
//        amountPaid: Double,
//        paymentMethod: String,
//        vatRate: Double,
//        discountType: String,
//        discountValue: Double,
//        selectedCustomer: Customer,
//        totalAmountDue: Double,
//        otherPaymentMethods: List<String> = emptyList(),
//        otherPaymentAmounts: List<Double> = emptyList()
//    ) {
//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                var pointsUsed = 0
//                var loyaltyCardUsed: LoyaltyCard? = null
//                val pointsEarned = (totalAmountDue / 200).toInt()
//
//                if (selectedCustomer.accountNum.startsWith("LC-")) {
//                    val cardNumber = selectedCustomer.accountNum.removePrefix("LC-")
//
//                    try {
//                        val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(cardNumber)
//
//                        if (loyaltyCard != null) {
//                            if (paymentMethod.uppercase() == "LOYALTYCARD") {
//                                // Point redemption case
//                                val pointsToDeduct = amountPaid.toInt()
//                                if (pointsToDeduct > loyaltyCard.points) {
//                                    withContext(Dispatchers.Main) {
//                                        Toast.makeText(this@Window1, "Insufficient points", Toast.LENGTH_SHORT).show()
//                                    }
//                                    return@launch
//                                }
//
//                                val finalPoints = loyaltyCard.points - pointsToDeduct + pointsEarned
//                                try {
//                                    loyaltyCardViewModel.updateCardPoints(cardNumber, finalPoints)
//                                    withContext(Dispatchers.Main) {
//                                        Toast.makeText(
//                                            this@Window1,
//                                            "Points updated: -$pointsToDeduct (used) +$pointsEarned (earned) = $finalPoints",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                } catch (e: Exception) {
//                                    Log.e("Payment", "Failed to update loyalty points", e)
//                                }
//                            } else {
//                                // Normal payment case - just add points
//                                val newPoints = loyaltyCard.points + pointsEarned
//                                try {
//                                    loyaltyCardViewModel.updateCardPoints(cardNumber, newPoints)
//                                    withContext(Dispatchers.Main) {
//                                        Toast.makeText(
//                                            this@Window1,
//                                            "Earned $pointsEarned points! New balance: $newPoints",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                    }
//                                } catch (e: Exception) {
//                                    Log.e("Payment", "Failed to add loyalty points", e)
//                                }
//                            }
//                        }
//                    } catch (e: Exception) {
//                        Log.e("Payment", "Error accessing loyalty card", e)
//                    }
//                }
//
//                // Your existing payment processing code
//                Log.d("Payment", "Starting payment process...")
//                val cartItems = cartViewModel.getAllCartItems(windowId).first()
//
//                val transactionComment = cartItems.firstOrNull()?.cartComment ?: ""
//
//                if (cartItems.isEmpty()) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "Cannot process payment. Cart is empty.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    return@launch
//                }
//
//                val currentStore = SessionManager.getCurrentUser()?.storeid
//                Log.d("Payment", "Current store ID: $currentStore")
//
//                if (currentStore == null) {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "No store ID found in session",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    return@launch
//                }
//
//                try {
//                    Log.d("Payment", "Fetching number sequence for store: $currentStore")
//                    val numberSequenceResult = numberSequenceRemoteRepository.fetchAndUpdateNumberSequence(currentStore)
//
//                    numberSequenceResult.onSuccess { numberSequence ->
//                        Log.d("Payment", "Successfully fetched number sequence: $numberSequence")
//
//                        // All database operations within withContext(Dispatchers.IO)
//                        withContext(Dispatchers.IO) {
//                            val transactionId = numberSequenceRemoteRepository.getNextTransactionNumber(currentStore)
//                            Log.d("Payment", "Generated transaction ID: $transactionId")
//
//                            val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
//                            Log.d("Payment", "Generated store key: $storeKey")
//
//                            // Create store-specific sequence
//                            val storeSequence = "$currentStore-${transactionId.split("-").last()}"
//
//                            // Determine if the payment method is AR
//                            val isAR = paymentMethod != "Cash"
//                            val ar = if (isAR) totalAmountDue else 0.0
//
//                            val paymentMethodAmounts = mutableMapOf(
//                                "GCASH" to 0.0,
//                                "PAYMAYA" to 0.0,
//                                "CASH" to 0.0,
//                                "CARD" to 0.0,
//                                "LOYALTYCARD" to 0.0,
//                                "CHARGE" to 0.0,
//                                "FOODPANDA" to 0.0,
//                                "GRABFOOD" to 0.0,
//                                "REPRESENTATION" to 0.0
//                            )
//
//                            val partialPayments = cartItems.firstOrNull()?.let { firstItem ->
//                                mapOf(
//                                    "GCASH" to if (firstItem.paymentMethod?.uppercase() == "GCASH") firstItem.partialPayment else 0.0,
//                                    "PAYMAYA" to if (firstItem.paymentMethod?.uppercase() == "PAYMAYA") firstItem.partialPayment else 0.0,
//                                    "CASH" to if (firstItem.paymentMethod?.uppercase() == "CASH") firstItem.partialPayment else 0.0,
//                                    "CARD" to if (firstItem.paymentMethod?.uppercase() == "CARD") firstItem.partialPayment else 0.0,
//                                    "LOYALTYCARD" to if (firstItem.paymentMethod?.uppercase() == "LOYALTYCARD") firstItem.partialPayment else 0.0,
//                                    "CHARGE" to if (firstItem.paymentMethod?.uppercase() == "CHARGE") firstItem.partialPayment else 0.0,
//                                    "FOODPANDA" to if (firstItem.paymentMethod?.uppercase() == "FOODPANDA") firstItem.partialPayment else 0.0,
//                                    "GRABFOOD" to if (firstItem.paymentMethod?.uppercase() == "GRABFOOD") firstItem.partialPayment else 0.0,
//                                    "REPRESENTATION" to if (firstItem.paymentMethod?.uppercase() == "REPRESENTATION") firstItem.partialPayment else 0.0
//                                )
//                            } ?: emptyMap()
//
//                            var totalPaidAmount = 0.0
//                            var actualPaymentAmount = totalAmountDue
//
//                            paymentMethodAmounts[paymentMethod.uppercase()] = amountPaid.roundToTwoDecimals()
//                            totalPaidAmount += amountPaid.roundToTwoDecimals()
//
//                            // Distribute remaining payment methods if provided
//                            otherPaymentMethods.forEachIndexed { index, method ->
//                                if (index < otherPaymentAmounts.size) {
//                                    val currentAmount = paymentMethodAmounts[method.uppercase()] ?: 0.0
//                                    val additionalAmount = otherPaymentAmounts[index].roundToTwoDecimals()
//                                    paymentMethodAmounts[method.uppercase()] = (currentAmount + additionalAmount).roundToTwoDecimals()
//                                    totalPaidAmount += additionalAmount
//                                }
//                            }
//                            val change = (totalPaidAmount - totalAmountDue).coerceAtLeast(0.0).roundToTwoDecimals()
//
//                            // If there's change and the primary payment method is CASH, reduce the CASH amount
//                            if (change > 0 && paymentMethodAmounts["CASH"]!! > 0) {
//                                paymentMethodAmounts["CASH"] = (paymentMethodAmounts["CASH"]!! - change).roundToTwoDecimals()
//                            }
//
//                            // First, create and insert transaction records
//                            val transactionRecords = getTransactionRecords(
//                                transactionId,
//                                cartItems,
//                                paymentMethod,
//                                ar,
//                                vatRate,
//                                discountType
//                            )
//
//                            // Insert transaction records first
//                            transactionRecords.forEach { record ->
//                                record.syncStatusRecord = false
//                                transactionDao.insertOrUpdateTransactionRecord(record)
//                            }
//
//                            // Now calculate summary values from the inserted transaction records
//                            val summaryValues = calculateSummaryFromRecords(transactionRecords, cartItems)
//
//                            // Prepare the transaction summary using calculated values
//                            val transactionSummary = TransactionSummary(
//                                transactionId = transactionId,
//                                type = if (isAR) 3 else 1, // 3 for AR, 1 for Cash
//                                receiptId = transactionId,
//                                store = getCurrentStore(),
//                                staff = getCurrentStaff(),
//                                storeKey = storeKey,
//                                storeSequence = storeSequence,
//                                customerAccount = selectedCustomer.name,
//                                netAmount = summaryValues.netAmount,
//                                costAmount = summaryValues.costAmount,
//                                grossAmount = summaryValues.grossAmount,
//                                partialPayment = summaryValues.partialPayment,
//                                transactionStatus = 1,
//                                discountAmount = summaryValues.discountAmount,
//                                customerDiscountAmount = summaryValues.discountAmount,
//                                totalDiscountAmount = summaryValues.discountAmount,
//                                numberOfItems = summaryValues.numberOfItems,
//                                refundReceiptId = null,
//                                currency = "PHP",
//                                zReportId = null,
//                                createdDate = Date(),
//                                priceOverride = summaryValues.priceOverride,
//                                comment = transactionComment,
//                                receiptEmail = null,
//                                markupAmount = 0.0,
//                                markupDescription = null,
//                                taxIncludedInPrice = summaryValues.vatAmount,
//                                windowNumber = windowId,
//                                // Update these payment fields with 2 decimal precision
//                                gCash = ((paymentMethodAmounts["GCASH"] ?: 0.0) + (partialPayments["GCASH"] ?: 0.0)).roundToTwoDecimals(),
//                                payMaya = ((paymentMethodAmounts["PAYMAYA"] ?: 0.0) + (partialPayments["PAYMAYA"] ?: 0.0)).roundToTwoDecimals(),
//                                cash = ((paymentMethodAmounts["CASH"] ?: 0.0) + (partialPayments["CASH"] ?: 0.0)).roundToTwoDecimals(),
//                                card = ((paymentMethodAmounts["CARD"] ?: 0.0) + (partialPayments["CARD"] ?: 0.0)).roundToTwoDecimals(),
//                                loyaltyCard = ((paymentMethodAmounts["LOYALTYCARD"] ?: 0.0) + (partialPayments["LOYALTYCARD"] ?: 0.0)).roundToTwoDecimals(),
//                                charge = ((paymentMethodAmounts["CHARGE"] ?: 0.0) + (partialPayments["CHARGE"] ?: 0.0)).roundToTwoDecimals(),
//                                foodpanda = ((paymentMethodAmounts["FOODPANDA"] ?: 0.0) + (partialPayments["FOODPANDA"] ?: 0.0)).roundToTwoDecimals(),
//                                grabfood = ((paymentMethodAmounts["GRABFOOD"] ?: 0.0) + (partialPayments["GRABFOOD"] ?: 0.0)).roundToTwoDecimals(),
//                                representation = ((paymentMethodAmounts["REPRESENTATION"] ?: 0.0) + (partialPayments["REPRESENTATION"] ?: 0.0)).roundToTwoDecimals(),
//                                changeGiven = change,
//                                totalAmountPaid = if (isAR) 0.0 else amountPaid.roundToTwoDecimals(),
//                                paymentMethod = paymentMethod,
//                                customerName = selectedCustomer.name,
//                                vatAmount = summaryValues.vatAmount,
//                                vatExemptAmount = 0.0,
//                                vatableSales = summaryValues.vatableSales,
//                                discountType = discountType,
//                                syncStatus = false
//                            )
//
//                            // Insert the transaction summary into the database
//                            transactionDao.insertTransactionSummary(transactionSummary)
//
//                            // UI operations moved to Main dispatcher
//                            withContext(Dispatchers.Main) {
//                                // Your existing receipt printing and UI update code
//                                if (isAR) {
//                                    printReceiptWithBluetoothPrinter(
//                                        transactionSummary,
//                                        transactionRecords,
//                                        BluetoothPrinterHelper.ReceiptType.AR,
//                                        isARReceipt = true,
//                                        copyType = "Customer Copy"
//                                    )
//                                    printReceiptWithBluetoothPrinter(
//                                        transactionSummary,
//                                        transactionRecords,
//                                        BluetoothPrinterHelper.ReceiptType.AR,
//                                        isARReceipt = true,
//                                        copyType = "Staff Copy"
//                                    )
//                                } else {
//                                    printReceiptWithBluetoothPrinter(
//                                        transactionSummary,
//                                        transactionRecords,
//                                        BluetoothPrinterHelper.ReceiptType.ORIGINAL
//                                    )
//                                }
//                                handleLoyaltyPoints(
//                                    selectedCustomer,
//                                    paymentMethod,
//                                    totalAmountDue,
//                                    amountPaid
//                                )
//                                showChangeAndReceiptDialog(
//                                    change,
//                                    cartItems,
//                                    transactionId,
//                                    paymentMethod,
//                                    ar,
//                                    summaryValues.vatAmount,
//                                    summaryValues.discountAmount,
//                                    summaryValues.netAmount,
//                                    if (summaryValues.grossAmount > 0) summaryValues.discountAmount / summaryValues.grossAmount else 0.0,
//                                    transactionComment,
//                                    summaryValues.partialPayment,
//                                    amountPaid
//                                )
//                            }
//
//                            // Back to IO dispatcher for cleanup
//                            transactionDao.updateSyncStatus(transactionId, false)
//                            cartViewModel.deleteAll(windowId)
//                            cartViewModel.clearCartComment(windowId)
//
//                            withContext(Dispatchers.Main) {
//                                updateTotalAmount(emptyList())
//                                SessionManager.setCurrentNumberSequence(numberSequence)
//
//                                val items = withContext(Dispatchers.IO) {
//                                    transactionDao.getTransactionItems(transactionId)
//                                }
//
//                                val transactionLogger = TransactionLogger(this@Window1)
//                                transactionLogger.logPayment(
//                                    transactionSummary = transactionSummary,
//                                    paymentMethod = paymentMethod,
//                                    items = items
//                                )
//                            }
//                        }
//                    }.onFailure { error ->
//                        Log.e("Payment", "Failed to fetch number sequence: ${error.message}")
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(
//                                this@Window1,
//                                "Failed to generate transaction number: ${error.message}",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.e("Payment", "Error in number sequence processing", e)
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(
//                            this@Window1,
//                            "Error in number sequence: ${e.message}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("Payment", "Error during payment processing: ${e.message}")
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@Window1,
//                        "Error processing payment: ${e.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }
//    }
//    fun getCurrentDateString(): String {
//        return formatDateToString(Date())
//    }

//    fun String.toTimestamp(): Long {
//        if (this.isEmpty()) return System.currentTimeMillis()
//
//        return try {
//            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
//            val date = format.parse(this)
//            date?.time ?: System.currentTimeMillis()
//        } catch (e: Exception) {
//            System.currentTimeMillis()
//        }
//    }

    private fun formatDateToString(date: Date): String {
        return try {
            // FIXED: Use your exact format 2025-07-17 09:03:35
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val result = format.format(date)
            Log.d("ReportsActivity", "Reports formatDateToString: $date -> '$result'")
            result
        } catch (e: Exception) {
            Log.e("ReportsActivity", "Error formatting date to string: ${e.message}")
            val fallbackFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            fallbackFormat.format(Date())
        }
    }
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

    // For displaying dates in UI
    fun String.toDisplayDate(): String {
        return try {
            if (this.isEmpty()) return ""
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val outputFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.US)
            val date = inputFormat.parse(this)
            outputFormat.format(date ?: return this)
        } catch (e: Exception) {
            this // Return original string if parsing fails
        }
    }

    // For comparing dates
    fun String.toDateForComparison(): Long {
        return this.toTimestamp()
    }
    private fun processPayment(
        amountPaid: Double,
        paymentMethod: String,
        vatRate: Double,
        discountType: String,
        discountValue: Double,
        selectedCustomer: Customer,
        totalAmountDue: Double,
        otherPaymentMethods: List<String> = emptyList(),
        otherPaymentAmounts: List<Double> = emptyList()
    ) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                var pointsUsed = 0
                var loyaltyCardUsed: LoyaltyCard? = null
                val pointsEarned = (totalAmountDue / 200).toInt()

                if (selectedCustomer.accountNum.startsWith("LC-")) {
                    val cardNumber = selectedCustomer.accountNum.removePrefix("LC-")

                    try {
                        val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(cardNumber)

                        if (loyaltyCard != null) {
                            if (paymentMethod.uppercase() == "LOYALTYCARD") {
                                // Point redemption case
                                val pointsToDeduct = amountPaid.toInt()
                                if (pointsToDeduct > loyaltyCard.points) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(this@Window1, "Insufficient points", Toast.LENGTH_SHORT).show()
                                    }
                                    return@launch
                                }

                                val finalPoints = loyaltyCard.points - pointsToDeduct + pointsEarned
                                try {
                                    loyaltyCardViewModel.updateCardPoints(cardNumber, finalPoints)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@Window1,
                                            "Points updated: -$pointsToDeduct (used) +$pointsEarned (earned) = $finalPoints",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Log.e("Payment", "Failed to update loyalty points", e)
                                }
                            } else {
                                // Normal payment case - just add points
                                val newPoints = loyaltyCard.points + pointsEarned
                                try {
                                    loyaltyCardViewModel.updateCardPoints(cardNumber, newPoints)
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            this@Window1,
                                            "Earned $pointsEarned points! New balance: $newPoints",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Log.e("Payment", "Failed to add loyalty points", e)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("Payment", "Error accessing loyalty card", e)
                    }
                }

                // Your existing payment processing code
                Log.d("Payment", "Starting payment process...")
                val cartItems = cartViewModel.getAllCartItems(windowId).first()

                val transactionComment = cartItems.firstOrNull()?.cartComment ?: ""

                if (cartItems.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Cannot process payment. Cart is empty.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                val currentStore = SessionManager.getCurrentUser()?.storeid
                Log.d("Payment", "Current store ID: $currentStore")

                if (currentStore == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "No store ID found in session",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                try {
                    Log.d("Payment", "Fetching number sequence for store: $currentStore")
                    val numberSequenceResult = numberSequenceRemoteRepository.fetchAndUpdateNumberSequence(currentStore)

                    numberSequenceResult.onSuccess { numberSequence ->
                        Log.d("Payment", "Successfully fetched number sequence: $numberSequence")

                        // 🛒 UPDATE CART SEQUENCE ON TRANSACTION COMPLETION
                        try {
                            Log.d("Payment", "🛒 Transaction completed - updating cart sequence...")
                            val cartSequenceResult = numberSequenceRemoteRepository.updateCartSequenceOnTransaction(currentStore)

                            cartSequenceResult.onSuccess {
                                Log.d("Payment", "✅ Cart sequence updated successfully after transaction")
                            }.onFailure { error ->
                                Log.e("Payment", "❌ Failed to update cart sequence: ${error.message}")
                            }
                        } catch (e: Exception) {
                            Log.e("Payment", "❌ Error updating cart sequence after transaction", e)
                        }

                        // All database operations within withContext(Dispatchers.IO)
                        withContext(Dispatchers.IO) {
                            val transactionId = numberSequenceRemoteRepository.getNextTransactionNumber(currentStore)
                            Log.d("Payment", "Generated transaction ID: $transactionId")

                            val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
                            Log.d("Payment", "Generated store key: $storeKey")

                            // Create store-specific sequence
                            val storeSequence = "$currentStore-${transactionId.split("-").last()}"

                            // Determine if the payment method is AR
                            val isAR = paymentMethod != "Cash"
                            val ar = if (isAR) totalAmountDue else 0.0

                            val paymentMethodAmounts = mutableMapOf(
                                "GCASH" to 0.0,
                                "PAYMAYA" to 0.0,
                                "CASH" to 0.0,
                                "CARD" to 0.0,
                                "LOYALTYCARD" to 0.0,
                                "CHARGE" to 0.0,
                                "FOODPANDA" to 0.0,
                                "GRABFOOD" to 0.0,
                                "REPRESENTATION" to 0.0
                            )

                            val partialPayments = cartItems.firstOrNull()?.let { firstItem ->
                                mapOf(
                                    "GCASH" to if (firstItem.paymentMethod?.uppercase() == "GCASH") firstItem.partialPayment else 0.0,
                                    "PAYMAYA" to if (firstItem.paymentMethod?.uppercase() == "PAYMAYA") firstItem.partialPayment else 0.0,
                                    "CASH" to if (firstItem.paymentMethod?.uppercase() == "CASH") firstItem.partialPayment else 0.0,
                                    "CARD" to if (firstItem.paymentMethod?.uppercase() == "CARD") firstItem.partialPayment else 0.0,
                                    "LOYALTYCARD" to if (firstItem.paymentMethod?.uppercase() == "LOYALTYCARD") firstItem.partialPayment else 0.0,
                                    "CHARGE" to if (firstItem.paymentMethod?.uppercase() == "CHARGE") firstItem.partialPayment else 0.0,
                                    "FOODPANDA" to if (firstItem.paymentMethod?.uppercase() == "FOODPANDA") firstItem.partialPayment else 0.0,
                                    "GRABFOOD" to if (firstItem.paymentMethod?.uppercase() == "GRABFOOD") firstItem.partialPayment else 0.0,
                                    "REPRESENTATION" to if (firstItem.paymentMethod?.uppercase() == "REPRESENTATION") firstItem.partialPayment else 0.0
                                )
                            } ?: emptyMap()

                            var totalPaidAmount = 0.0
                            var actualPaymentAmount = totalAmountDue

                            paymentMethodAmounts[paymentMethod.uppercase()] = amountPaid.roundToTwoDecimals()
                            totalPaidAmount += amountPaid.roundToTwoDecimals()

                            // Distribute remaining payment methods if provided
                            otherPaymentMethods.forEachIndexed { index, method ->
                                if (index < otherPaymentAmounts.size) {
                                    val currentAmount = paymentMethodAmounts[method.uppercase()] ?: 0.0
                                    val additionalAmount = otherPaymentAmounts[index].roundToTwoDecimals()
                                    paymentMethodAmounts[method.uppercase()] = (currentAmount + additionalAmount).roundToTwoDecimals()
                                    totalPaidAmount += additionalAmount
                                }
                            }
                            val change = (totalPaidAmount - totalAmountDue).coerceAtLeast(0.0).roundToTwoDecimals()

                            // If there's change and the primary payment method is CASH, reduce the CASH amount
                            if (change > 0 && paymentMethodAmounts["CASH"]!! > 0) {
                                paymentMethodAmounts["CASH"] = (paymentMethodAmounts["CASH"]!! - change).roundToTwoDecimals()
                            }

                            // First, create and insert transaction records
                            val transactionRecords = getTransactionRecords(
                                transactionId,
                                cartItems,
                                paymentMethod,
                                ar,
                                vatRate,
                                discountType
                            )

                            // Insert transaction records first
                            transactionRecords.forEach { record ->
                                record.syncStatusRecord = false
                                transactionDao.insertOrUpdateTransactionRecord(record)
                            }

                            // Now calculate summary values from the inserted transaction records
                            val summaryValues = calculateSummaryFromRecords(transactionRecords, cartItems)
                            val currentDateString = formatDateToString(Date())
                            // Prepare the transaction summary using calculated values
                            val transactionSummary = TransactionSummary(
                                transactionId = transactionId,
                                type = if (isAR) 3 else 1, // 3 for AR, 1 for Cash
                                receiptId = transactionId,
                                store = getCurrentStore(),
                                staff = getCurrentStaff(),
                                storeKey = storeKey,
                                storeSequence = storeSequence,
                                customerAccount = selectedCustomer.name,
                                netAmount = summaryValues.netAmount,
                                costAmount = summaryValues.costAmount,
                                grossAmount = summaryValues.grossAmount,
                                partialPayment = summaryValues.partialPayment,
                                transactionStatus = 1,
                                discountAmount = summaryValues.discountAmount,
                                customerDiscountAmount = summaryValues.discountAmount,
                                totalDiscountAmount = summaryValues.discountAmount,
                                numberOfItems = summaryValues.numberOfItems,
                                refundReceiptId = null,
                                currency = "PHP",
                                zReportId = null,
                                createdDate = currentDateString, // Use string format consistently
                                priceOverride = summaryValues.priceOverride,
                                comment = transactionComment,
                                receiptEmail = null,
                                markupAmount = 0.0,
                                markupDescription = null,
                                taxIncludedInPrice = summaryValues.vatAmount,
                                windowNumber = windowId,
                                // Update these payment fields with 2 decimal precision
                                gCash = ((paymentMethodAmounts["GCASH"] ?: 0.0) + (partialPayments["GCASH"] ?: 0.0)).roundToTwoDecimals(),
                                payMaya = ((paymentMethodAmounts["PAYMAYA"] ?: 0.0) + (partialPayments["PAYMAYA"] ?: 0.0)).roundToTwoDecimals(),
                                cash = ((paymentMethodAmounts["CASH"] ?: 0.0) + (partialPayments["CASH"] ?: 0.0)).roundToTwoDecimals(),
                                card = ((paymentMethodAmounts["CARD"] ?: 0.0) + (partialPayments["CARD"] ?: 0.0)).roundToTwoDecimals(),
                                loyaltyCard = ((paymentMethodAmounts["LOYALTYCARD"] ?: 0.0) + (partialPayments["LOYALTYCARD"] ?: 0.0)).roundToTwoDecimals(),
                                charge = ((paymentMethodAmounts["CHARGE"] ?: 0.0) + (partialPayments["CHARGE"] ?: 0.0)).roundToTwoDecimals(),
                                foodpanda = ((paymentMethodAmounts["FOODPANDA"] ?: 0.0) + (partialPayments["FOODPANDA"] ?: 0.0)).roundToTwoDecimals(),
                                grabfood = ((paymentMethodAmounts["GRABFOOD"] ?: 0.0) + (partialPayments["GRABFOOD"] ?: 0.0)).roundToTwoDecimals(),
                                representation = ((paymentMethodAmounts["REPRESENTATION"] ?: 0.0) + (partialPayments["REPRESENTATION"] ?: 0.0)).roundToTwoDecimals(),
                                changeGiven = change,
                                totalAmountPaid = if (isAR) 0.0 else amountPaid.roundToTwoDecimals(),
                                paymentMethod = paymentMethod,
                                customerName = selectedCustomer.name,
                                vatAmount = summaryValues.vatAmount,
                                vatExemptAmount = 0.0,
                                vatableSales = summaryValues.vatableSales,
                                discountType = discountType,
                                syncStatus = false
                            )

                            // Insert the transaction summary into the database
                            transactionDao.insertTransactionSummary(transactionSummary)

                            // UI operations moved to Main dispatcher
                            withContext(Dispatchers.Main) {
                                // Your existing receipt printing and UI update code
                                if (isAR) {
                                    printReceiptWithBluetoothPrinter(
                                        transactionSummary,
                                        transactionRecords,
                                        BluetoothPrinterHelper.ReceiptType.AR,
                                        isARReceipt = true,
                                        copyType = "Customer Copy"
                                    )
                                    printReceiptWithBluetoothPrinter(
                                        transactionSummary,
                                        transactionRecords,
                                        BluetoothPrinterHelper.ReceiptType.AR,
                                        isARReceipt = true,
                                        copyType = "Staff Copy"
                                    )
                                } else {
                                    printReceiptWithBluetoothPrinter(
                                        transactionSummary,
                                        transactionRecords,
                                        BluetoothPrinterHelper.ReceiptType.ORIGINAL
                                    )
                                }
                                handleLoyaltyPoints(
                                    selectedCustomer,
                                    paymentMethod,
                                    totalAmountDue,
                                    amountPaid
                                )
                                showChangeAndReceiptDialog(
                                    change,
                                    cartItems,
                                    transactionId,
                                    paymentMethod,
                                    ar,
                                    summaryValues.vatAmount,
                                    summaryValues.discountAmount,
                                    summaryValues.netAmount,
                                    if (summaryValues.grossAmount > 0) summaryValues.discountAmount / summaryValues.grossAmount else 0.0,
                                    transactionComment,
                                    summaryValues.partialPayment,
                                    amountPaid
                                )
                            }

                            // Back to IO dispatcher for cleanup
                            transactionDao.updateSyncStatus(transactionId, false)
                            cartViewModel.deleteAll(windowId)
                            cartViewModel.clearCartComment(windowId)

                            withContext(Dispatchers.Main) {
                                updateTotalAmount(emptyList())
                                SessionManager.setCurrentNumberSequence(numberSequence)

                                val items = withContext(Dispatchers.IO) {
                                    transactionDao.getTransactionItems(transactionId)
                                }

                                val transactionLogger = TransactionLogger(this@Window1)
                                transactionLogger.logPayment(
                                    transactionSummary = transactionSummary,
                                    paymentMethod = paymentMethod,
                                    items = items
                                )
                            }
                        }
                    }.onFailure { error ->
                        Log.e("Payment", "Failed to fetch number sequence: ${error.message}")
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                "Failed to generate transaction number: ${error.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Payment", "Error in number sequence processing", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@Window1,
                            "Error in number sequence: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("Payment", "Error during payment processing: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@Window1,
                        "Error processing payment: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    // Add this new data class to hold calculated summary values
    data class SummaryCalculation(
        val grossAmount: Double,
        val netAmount: Double,
        val discountAmount: Double,
        val vatAmount: Double,
        val vatableSales: Double,
        val costAmount: Double,
        val partialPayment: Double,
        val numberOfItems: Double,
        val priceOverride: Double
    )

    // Extension function to round to 2 decimal places
    private fun Double.roundToTwoDecimals(): Double {
        return String.format("%.2f", this).toDouble()
    }

    // Add this helper function to calculate summary values from transaction records
//    private fun calculateSummaryFromRecords(
//        transactionRecords: List<TransactionRecord>,
//        cartItems: List<CartItem>
//    ): SummaryCalculation {
//        var grossAmount = 0.0
//        var netAmount = 0.0
//        var discountAmount = 0.0
//        var vatAmount = 0.0
//        var costAmount = 0.0
//        var partialPayment = 0.0
//        var numberOfItems = 0.0
//        var priceOverride = 0.0
//
//        // Sum up values from transaction records with 2 decimal precision
//        transactionRecords.forEach { record ->
//            grossAmount += (record.grossAmount ?: 0.0).roundToTwoDecimals()
//            netAmount += (record.netAmount ?: 0.0).roundToTwoDecimals()
//            discountAmount += (record.discountAmount ?: 0.0).roundToTwoDecimals()
//            vatAmount += (record.vatAmount ?: 0.0).roundToTwoDecimals()
//            costAmount += (record.costAmount ?: 0.0).roundToTwoDecimals()
//            numberOfItems += record.quantity.toDouble()
//
//            // Handle price override
//            if (record.priceOverride != null && record.priceOverride!! > 0) {
//                priceOverride += ((record.priceOverride ?: 0.0) * record.quantity).roundToTwoDecimals()
//            }
//        }
//
//        // Get partial payment from cart items (only from first item to avoid duplication)
//        partialPayment = (cartItems.firstOrNull()?.partialPayment ?: 0.0).roundToTwoDecimals()
//
//        // Calculate VATable sales (net amount without VAT)
//        val vatableSales = (netAmount / 1.12).roundToTwoDecimals()
//
//        return SummaryCalculation(
//            grossAmount = grossAmount.roundToTwoDecimals(),
//            netAmount = netAmount.roundToTwoDecimals(),
//            discountAmount = discountAmount.roundToTwoDecimals(),
//            vatAmount = vatAmount.roundToTwoDecimals(),
//            vatableSales = vatableSales,
//            costAmount = costAmount.roundToTwoDecimals(),
//            partialPayment = partialPayment,
//            numberOfItems = numberOfItems,
//            priceOverride = priceOverride.roundToTwoDecimals()
//        )
//    }
    private suspend fun handleLoyaltyPoints(
        selectedCustomer: Customer,
        paymentMethod: String,
        totalAmountDue: Double,
        amountPaid: Double
    ) {
        try {
            if (selectedCustomer.accountNum.startsWith("LC-")) {
                val cardNumber = selectedCustomer.accountNum.removePrefix("LC-")
                val loyaltyCard = loyaltyCardViewModel.getLoyaltyCardByNumber(cardNumber) ?: return

                if (paymentMethod.uppercase() == "LOYALTYCARD") {
                    // Handle point redemption
                    val pointsToDeduct = amountPaid.toInt() // 1 point = 1 peso
                    if (pointsToDeduct <= loyaltyCard.points) {
                        // Add the current purchase to cumulative amount before calculating new points
                        val cumulativeAmount = loyaltyCard.cumulativeAmount + totalAmountDue
                        val newPointsEarned = (cumulativeAmount / 200).toInt()
                        val remainingAmount = cumulativeAmount % 200

                        val finalPoints = loyaltyCard.points - pointsToDeduct + newPointsEarned
                        updateLoyaltyCardState(
                            loyaltyCard.id,
                            finalPoints,
                            remainingAmount
                        )

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                "Points redeemed: $pointsToDeduct\nPoints earned: $newPointsEarned\nNew balance: $finalPoints\nProgress to next point: ₱$remainingAmount/₱200",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    // Regular purchase - accumulate amount
                    val newCumulativeAmount = loyaltyCard.cumulativeAmount + totalAmountDue
                    val newPointsEarned = (newCumulativeAmount / 200).toInt()
                    val remainingAmount = newCumulativeAmount % 200

                    if (newPointsEarned > 0) {
                        val finalPoints = loyaltyCard.points + newPointsEarned
                        updateLoyaltyCardState(
                            loyaltyCard.id,
                            finalPoints,
                            remainingAmount
                        )

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                "Points earned: $newPointsEarned\nNew balance: $finalPoints\nProgress to next point: ₱$remainingAmount/₱200",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        updateLoyaltyCardState(
                            loyaltyCard.id,
                            loyaltyCard.points,
                            remainingAmount
                        )

                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@Window1,
                                "Progress to next point: ₱$remainingAmount/₱200",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("LoyaltyCard", "Error in handleLoyaltyPoints", e)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@Window1,
                    "Error updating loyalty points",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private suspend fun updateLoyaltyCardState(
        cardId: Int,
        newPoints: Int,
        cumulativeAmount: Double
    ) {
        try {
            loyaltyCardViewModel.updateLoyaltyCardState(cardId, newPoints, cumulativeAmount)
        } catch (e: Exception) {
            Log.e("LoyaltyCard", "Error updating loyalty card state", e)
            throw e
        }
    }


    private suspend fun updateTransactionRecords(
        transactionId: String,
        cartItems: List<CartItem>,
        paymentMethod: String,
        ar: Double,
        vatRate: Double,
        totalDiscount: Double,
        netSales: Double,
        partialPayment: Double,
        discountType: String,
        otherPaymentMethods: List<String> = emptyList(),
        otherPaymentAmounts: List<Double> = emptyList()
    ) {
        val currentStore = SessionManager.getCurrentUser()?.storeid
            ?: throw IllegalStateException("No store ID found in current session")

        val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
        val storeSequence = "$currentStore-${transactionId.split("-").last()}"

        val existingPartialPayment = (cartItems.firstOrNull()?.partialPayment ?: 0.0).roundToTwoDecimals()
        val existingDiscount = if (existingPartialPayment > 0) {
            (cartItems.firstOrNull()?.discountAmount ?: 0.0).roundToTwoDecimals()
        } else null

        // Group items by bundleId for bundle discount calculation
        val bundledItems = cartItems.groupBy { it.bundleId?.toString() }

        // Calculate bundle discounts with 2 decimal precision
        val bundleDiscounts = mutableMapOf<String?, Double>()
        cartItems.forEach { cartItem ->
            if (cartItem.bundleId != null) {
                val bundleIdStr = cartItem.bundleId.toString()
                val bundle = bundledItems[bundleIdStr]
                if (bundle != null) {
                    when (cartItem.discountType.uppercase()) {
                        "DEAL" -> bundleDiscounts[bundleIdStr] = ((bundleDiscounts[bundleIdStr] ?: 0.0) + cartItem.discount).roundToTwoDecimals()
                        "PERCENTAGE" -> {
                            val itemTotal = ((cartItem.overriddenPrice ?: cartItem.price) * cartItem.quantity).roundToTwoDecimals()
                            val discountAmount = (itemTotal * (cartItem.discount / 100)).roundToTwoDecimals()
                            bundleDiscounts[bundleIdStr] = ((bundleDiscounts[bundleIdStr] ?: 0.0) + discountAmount).roundToTwoDecimals()
                        }
                        "FIXEDTOTAL" -> bundleDiscounts[bundleIdStr] = ((bundleDiscounts[bundleIdStr] ?: 0.0) + cartItem.discount).roundToTwoDecimals()
                    }
                }
            }
        }

        // Combine all payment methods and amounts with 2 decimal precision
        val allPaymentMethods = listOf(paymentMethod) + otherPaymentMethods
        val allPaymentAmounts = listOf(netSales.roundToTwoDecimals()) + otherPaymentAmounts.map { it.roundToTwoDecimals() }
        val currentDateString = formatDateToString(Date())

        cartItems.forEachIndexed { index, cartItem ->
            val effectivePrice = (cartItem.overriddenPrice ?: cartItem.price).roundToTwoDecimals()
            val itemTotal = (effectivePrice * cartItem.quantity).roundToTwoDecimals()

            // Calculate the individual item discount with 2 decimal precision
            var itemDiscount = 0.0

            // Use existing discount if partial payment exists
            if (existingDiscount != null) {
                itemDiscount = if (index == 0) existingDiscount else 0.0 // Apply discount only to first item
            } else if (cartItem.bundleId == null) {
                // Regular individual discount calculation
                itemDiscount = when (cartItem.discountType.uppercase()) {
                    "PERCENTAGE", "PWD", "SC" -> (itemTotal * (cartItem.discount / 100)).roundToTwoDecimals()
                    "FIXED" -> (cartItem.discount * cartItem.quantity).roundToTwoDecimals()
                    "FIXEDTOTAL" -> cartItem.discountAmount.roundToTwoDecimals()
                    else -> 0.0
                }
            } else {
                // For bundled items, apply the bundle discount proportionally
                val bundleIdStr = cartItem.bundleId.toString()
                val bundleTotal = bundledItems[bundleIdStr]?.sumOf {
                    ((it.overriddenPrice ?: it.price) * it.quantity).roundToTwoDecimals()
                } ?: itemTotal

                val bundleDiscount = bundleDiscounts[bundleIdStr] ?: 0.0

                // Apply discount proportionally based on this item's value relative to total bundle value
                itemDiscount = if (bundleTotal > 0) {
                    (bundleDiscount * (itemTotal / bundleTotal)).roundToTwoDecimals()
                } else 0.0
            }

            val itemVatBeforeDiscount = (itemTotal * 0.12 / 1.12).roundToTwoDecimals()
            val itemAfterDiscount = (itemTotal - itemDiscount).roundToTwoDecimals()
            val itemVat = (itemAfterDiscount * 0.12 / 1.12).roundToTwoDecimals()

            // Create a list to track split payments for this item
            val splitPayments = allPaymentMethods.mapIndexed { paymentIndex, method ->
                val totalNetSales = allPaymentAmounts.sum()
                val paymentProportion = if (totalNetSales > 0) (allPaymentAmounts[paymentIndex] / totalNetSales).roundToTwoDecimals() else 1.0
                val splitItemTotal = (itemTotal * paymentProportion).roundToTwoDecimals()
                val splitItemDiscount = (itemDiscount * paymentProportion).roundToTwoDecimals()
                val splitItemAfterDiscount = (splitItemTotal - splitItemDiscount).roundToTwoDecimals()
                val splitItemVat = (splitItemAfterDiscount * 0.12 / 1.12).roundToTwoDecimals()
                val splitCostAmount = (splitItemTotal / vatRate).roundToTwoDecimals()
                val splitNetAmountNotIncludingTax = (splitItemAfterDiscount / 1.12).roundToTwoDecimals()

                TransactionRecord(
                    transactionId = transactionId,
                    name = cartItem.productName,
                    price = cartItem.price.roundToTwoDecimals(),
                    quantity = cartItem.quantity,
                    subtotal = splitItemTotal,
                    vatRate = (vatRate - 1).roundToTwoDecimals(),
                    vatAmount = splitItemVat,
                    discountRate = if (splitItemTotal > 0) (splitItemDiscount / splitItemTotal).roundToTwoDecimals() else 0.0,
                    discountAmount = splitItemDiscount,
                    total = splitItemAfterDiscount,
                    receiptNumber = transactionId,
                    timestamp = System.currentTimeMillis(),
                    paymentMethod = method.uppercase(),
                    ar = if (ar > 0.0) ((effectivePrice * cartItem.quantity) * paymentProportion).roundToTwoDecimals() else 0.0,
                    windowNumber = windowId,
                    partialPaymentAmount = if (index == 0) (partialPayment * paymentProportion).roundToTwoDecimals() else 0.0,
                    comment = cartItem.cartComment ?: "",
                    lineNum = index + 1,
                    receiptId = transactionId,
                    itemId = cartItem.itemId.toString(),
                    itemGroup = cartItem.itemGroup.toString(),
                    netPrice = cartItem.price.roundToTwoDecimals(),
                    costAmount = splitCostAmount,
                    netAmount = splitItemAfterDiscount,
                    grossAmount = splitItemTotal,
                    customerAccount = selectedCustomer.name ,
                    store = getCurrentStore(),
                    priceOverride = (cartItem.overriddenPrice ?: 0.0).roundToTwoDecimals(),
                    staff = getCurrentStaff(),
                    discountOfferId = if (cartItem.mixMatchId != null && cartItem.mixMatchId.isNotEmpty())
                        cartItem.mixMatchId
                    else if (cartItem.discountName != null && cartItem.discountName.isNotEmpty())
                        cartItem.discountName
                    else "",
                    lineDiscountAmount = splitItemDiscount,
                    lineDiscountPercentage = if (splitItemTotal > 0) ((splitItemDiscount / splitItemTotal) * 100).roundToTwoDecimals() else 0.0,
                    customerDiscountAmount = splitItemDiscount,
                    unit = "PCS",
                    unitQuantity = cartItem.quantity.toDouble(),
                    unitPrice = cartItem.price.roundToTwoDecimals(),
                    taxAmount = splitItemVat,
                    createdDate = currentDateString,
                    discountType = when (cartItem.discountType.uppercase()) {
                        "PWD" -> "PWD"
                        "SC" -> "SC"
                        "PERCENTAGE" -> "PERCENTAGE"
                        "FIXED" -> "FIXED"
                        "FIXEDTOTAL", "FIXED TOTAL" -> "FIXEDTOTAL"
                        "DEAL" -> "DEAL"
                        else -> "No Discount"
                    },
                    netAmountNotIncludingTax = splitNetAmountNotIncludingTax,
                    currency = "PHP",
                    storeKey = storeKey,
                    storeSequence = storeSequence,
                    taxIncludedInPrice = splitItemVat
                )
            }

            // Insert each split payment transaction record
            splitPayments.forEach { transactionRecord ->
                transactionDao.insertOrUpdateTransactionRecord(transactionRecord)
            }
        }
    }

//    private suspend fun getTransactionRecords(
//        transactionId: String,
//        cartItems: List<CartItem>,
//        paymentMethod: String,
//        ar: Double,
//        vatRate: Double,
//        discountType: String,
//        otherPaymentMethods: List<String> = emptyList(),
//        otherPaymentAmounts: List<Double> = emptyList()
//    ): List<TransactionRecord> {
//        val currentStore = SessionManager.getCurrentUser()?.storeid
//            ?: throw IllegalStateException("No store ID found in current session")
//
//        val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
//        val storeSequence = "$currentStore-${transactionId.split("-").last()}"
//
//        val existingPartialPayment = (cartItems.firstOrNull()?.partialPayment ?: 0.0).roundToTwoDecimals()
//        val existingDiscount = if (existingPartialPayment > 0) {
//            (cartItems.firstOrNull()?.discountAmount ?: 0.0).roundToTwoDecimals()
//        } else null
//
//        // Group items by bundleId for bundle discount calculation
//        val bundledItems = cartItems.groupBy { it.bundleId?.toString() }
//
//        // Calculate bundle discounts with 2 decimal precision
//        val bundleDiscounts = mutableMapOf<String?, Double>()
//        cartItems.forEach { cartItem ->
//            if (cartItem.bundleId != null) {
//                val bundleIdStr = cartItem.bundleId.toString()
//                val bundle = bundledItems[bundleIdStr]
//                if (bundle != null) {
//                    when (cartItem.discountType.uppercase()) {
//                        "DEAL" -> bundleDiscounts[bundleIdStr] = ((bundleDiscounts[bundleIdStr] ?: 0.0) + cartItem.discount).roundToTwoDecimals()
//                        "PERCENTAGE" -> {
//                            val itemTotal = ((cartItem.overriddenPrice ?: cartItem.price) * cartItem.quantity).roundToTwoDecimals()
//                            val discountAmount = (itemTotal * (cartItem.discount / 100)).roundToTwoDecimals()
//                            bundleDiscounts[bundleIdStr] = ((bundleDiscounts[bundleIdStr] ?: 0.0) + discountAmount).roundToTwoDecimals()
//                        }
//                        "FIXEDTOTAL" -> bundleDiscounts[bundleIdStr] = ((bundleDiscounts[bundleIdStr] ?: 0.0) + cartItem.discount).roundToTwoDecimals()
//                    }
//                }
//            }
//        }
//
//        // Calculate the total net amount after all discounts with 2 decimal precision
//        var totalAmount = 0.0
//        cartItems.forEach { item ->
//            val itemTotal = ((item.overriddenPrice ?: item.price) * item.quantity).roundToTwoDecimals()
//            totalAmount = (totalAmount + itemTotal).roundToTwoDecimals()
//        }
//
//        // Subtract bundle discounts
//        totalAmount = (totalAmount - bundleDiscounts.values.sum()).roundToTwoDecimals()
//
//        // Subtract regular discounts for non-bundled items
//        cartItems.forEach { item ->
//            if (item.bundleId == null) {
//                val itemTotal = ((item.overriddenPrice ?: item.price) * item.quantity).roundToTwoDecimals()
//                val itemDiscount = when (item.discountType.uppercase()) {
//                    "PERCENTAGE", "PWD", "SC" -> (itemTotal * (item.discount / 100)).roundToTwoDecimals()
//                    "FIXED" -> (item.discount * item.quantity).roundToTwoDecimals()
//                    "FIXEDTOTAL" -> item.discountAmount.roundToTwoDecimals()
//                    else -> 0.0
//                }
//                totalAmount = (totalAmount - itemDiscount).roundToTwoDecimals()
//            }
//        }
//
//        // If there's an existing discount from partial payment, use that instead
//        if (existingDiscount != null) {
//            totalAmount = (cartItems.sumOf { ((it.overriddenPrice ?: it.price) * it.quantity).roundToTwoDecimals() } - existingDiscount).roundToTwoDecimals()
//        }
//
//        // Combine all payment methods and amounts with 2 decimal precision
//        val allPaymentMethods = listOf(paymentMethod) + otherPaymentMethods
//        val allPaymentAmounts = listOf(totalAmount) + otherPaymentAmounts.map { it.roundToTwoDecimals() }
//
//        val transactionRecords = mutableListOf<TransactionRecord>()
//        val currentDateString = formatDateToString(Date())
//
//        cartItems.forEachIndexed { index, cartItem ->
//            val effectivePrice = (cartItem.overriddenPrice ?: cartItem.price).roundToTwoDecimals()
//            val itemTotal = (effectivePrice * cartItem.quantity).roundToTwoDecimals()
//
//            // Calculate the individual item discount with 2 decimal precision
//            var itemDiscount = 0.0
//
//            // Use existing discount if partial payment exists
//            if (existingDiscount != null) {
//                itemDiscount = if (index == 0) existingDiscount else 0.0 // Apply discount only to first item
//            } else if (cartItem.bundleId == null) {
//                // Regular individual discount calculation
//                itemDiscount = when (cartItem.discountType.uppercase()) {
//                    "PERCENTAGE", "PWD", "SC" -> (itemTotal * (cartItem.discount / 100)).roundToTwoDecimals()
//                    "FIXED" -> (cartItem.discount * cartItem.quantity).roundToTwoDecimals()
//                    "FIXEDTOTAL" -> cartItem.discountAmount.roundToTwoDecimals()
//                    else -> 0.0
//                }
//            } else {
//                // For bundled items, apply the bundle discount proportionally
//                val bundleIdStr = cartItem.bundleId.toString()
//                val bundleTotal = bundledItems[bundleIdStr]?.sumOf {
//                    ((it.overriddenPrice ?: it.price) * it.quantity).roundToTwoDecimals()
//                } ?: itemTotal
//
//                val bundleDiscount = bundleDiscounts[bundleIdStr] ?: 0.0
//
//                // Apply discount proportionally based on this item's value relative to total bundle value
//                itemDiscount = if (bundleTotal > 0) {
//                    (bundleDiscount * (itemTotal / bundleTotal)).roundToTwoDecimals()
//                } else 0.0
//            }
//
//            val itemAfterDiscount = (itemTotal - itemDiscount).roundToTwoDecimals()
//            val itemVat = (itemAfterDiscount * 0.12 / 1.12).roundToTwoDecimals()
//            val netAmountNotIncludingTax = (itemAfterDiscount / 1.12).roundToTwoDecimals()
//
//            // Create split transaction records for each payment method
//            val splitRecords = allPaymentMethods.mapIndexed { paymentIndex, method ->
//                val totalNetSales = allPaymentAmounts.sum()
//                val paymentProportion = if (totalNetSales > 0) (allPaymentAmounts[paymentIndex] / totalNetSales).roundToTwoDecimals() else 1.0
//                val splitItemTotal = (itemTotal * paymentProportion).roundToTwoDecimals()
//                val splitItemDiscount = (itemDiscount * paymentProportion).roundToTwoDecimals()
//                val splitItemAfterDiscount = (splitItemTotal - splitItemDiscount).roundToTwoDecimals()
//                val splitItemVat = (splitItemAfterDiscount * 0.12 / 1.12).roundToTwoDecimals()
//                val splitCostAmount = (splitItemTotal / vatRate).roundToTwoDecimals()
//                val splitNetAmountNotIncludingTax = (splitItemAfterDiscount / 1.12).roundToTwoDecimals()
//
//                TransactionRecord(
//                    transactionId = transactionId,
//                    name = cartItem.productName,
//                    price = cartItem.price.roundToTwoDecimals(),
//                    quantity = cartItem.quantity,
//                    subtotal = splitItemTotal,
//                    vatRate = (vatRate - 1).roundToTwoDecimals(),
//                    vatAmount = splitItemVat,
//                    discountRate = if (splitItemTotal > 0) (splitItemDiscount / splitItemTotal).roundToTwoDecimals() else 0.0,
//                    discountAmount = splitItemDiscount,
//                    total = splitItemAfterDiscount,
//                    receiptNumber = transactionId,
//                    timestamp = System.currentTimeMillis(),
//                    paymentMethod = method.uppercase(),
//                    ar = if (ar > 0.0) ((effectivePrice * cartItem.quantity) * paymentProportion).roundToTwoDecimals() else 0.0,
//                    windowNumber = windowId,
//                    partialPaymentAmount = if (index == 0) (cartItem.partialPayment * paymentProportion).roundToTwoDecimals() else 0.0,
//                    comment = cartItem.cartComment ?: "",
//                    lineNum = index + 1,
//                    receiptId = transactionId,
//                    itemId = cartItem.itemId.toString(),
//                    itemGroup = cartItem.itemGroup.toString(),
//                    netPrice = cartItem.price.roundToTwoDecimals(),
//                    costAmount = splitCostAmount,
//                    netAmount = splitItemAfterDiscount,
//                    grossAmount = splitItemTotal,
//                    customerAccount = selectedCustomer.name,
//                    store = getCurrentStore(),
//                    priceOverride = (cartItem.overriddenPrice ?: 0.0).roundToTwoDecimals(),
//                    staff = getCurrentStaff(),
//                    discountOfferId = if (cartItem.mixMatchId != null && cartItem.mixMatchId.isNotEmpty())
//                        cartItem.mixMatchId
//                    else if (cartItem.discountName != null && cartItem.discountName.isNotEmpty())
//                        cartItem.discountName
//                    else "",
//                    lineDiscountAmount = splitItemDiscount,
//                    lineDiscountPercentage = if (splitItemTotal > 0) ((splitItemDiscount / splitItemTotal) * 100).roundToTwoDecimals() else 0.0,
//                    customerDiscountAmount = splitItemDiscount,
//                    unit = "PCS",
//                    unitQuantity = cartItem.quantity.toDouble(),
//                    unitPrice = cartItem.price.roundToTwoDecimals(),
//                    taxAmount = splitItemVat,
//                    createdDate = currentDateString,
//                    discountType = when (cartItem.discountType.uppercase()) {
//                        "PWD" -> "PWD"
//                        "SC" -> "SC"
//                        "PERCENTAGE" -> "PERCENTAGE"
//                        "FIXED" -> "FIXED"
//                        "FIXED TOTAL", "FIXEDTOTAL" -> "FIXEDTOTAL"
//                        "DEAL" -> "DEAL"
//                        else -> "No Discount"
//                    },
//                    netAmountNotIncludingTax = splitNetAmountNotIncludingTax,
//                    storeTaxGroup = null,
//                    currency = "PHP",
//                    taxExempt = 0.0,
//                    storeKey = storeKey,
//                    storeSequence = storeSequence,
//                    taxIncludedInPrice = splitItemVat
//                )
//            }
//
//            transactionRecords.addAll(splitRecords)
//        }
//
//        return transactionRecords
//    }
private suspend fun getTransactionRecords(
    transactionId: String,
    cartItems: List<CartItem>,
    paymentMethod: String,
    ar: Double,
    vatRate: Double,
    discountType: String,
    otherPaymentMethods: List<String> = emptyList(),
    otherPaymentAmounts: List<Double> = emptyList()
): List<TransactionRecord> {
    val currentStore = SessionManager.getCurrentUser()?.storeid
        ?: throw IllegalStateException("No store ID found in current session")

    val storeKey = numberSequenceRemoteRepository.getCurrentStoreKey(currentStore)
    val storeSequence = "$currentStore-${transactionId.split("-").last()}"

    val transactionRecords = mutableListOf<TransactionRecord>()
    val currentDateString = formatDateToString(Date())

    cartItems.forEachIndexed { index, cartItem ->
        // FIXED: Use roundToTwoDecimals for ALL price calculations
        val effectivePrice = (cartItem.overriddenPrice ?: cartItem.price)
        val itemTotal = (effectivePrice * cartItem.quantity)

        Log.d("TransactionRecord", "=== ITEM: ${cartItem.productName} ===")
        Log.d("TransactionRecord", "Original Price: ${cartItem.price}")
        Log.d("TransactionRecord", "Overridden Price: ${cartItem.overriddenPrice}")
        Log.d("TransactionRecord", "Effective Price: $effectivePrice")
        Log.d("TransactionRecord", "Quantity: ${cartItem.quantity}")
        Log.d("TransactionRecord", "Item Total: $itemTotal")

        // Calculate discount (unchanged from your logic)
        var itemDiscount = 0.0
        when (cartItem.discountType.uppercase()) {
            "PERCENTAGE", "PWD", "SC" -> itemDiscount = (itemTotal * (cartItem.discount / 100))
            "FIXED" -> itemDiscount = (cartItem.discount * cartItem.quantity)
            "FIXEDTOTAL" -> itemDiscount = cartItem.discountAmount
            else -> itemDiscount = 0.0
        }

        val itemAfterDiscount = (itemTotal - itemDiscount)
        val itemVat = (itemAfterDiscount * 0.12 / 1.12)
        val netAmountNotIncludingTax = (itemAfterDiscount / 1.12)

        // FIXED: Store price override with full decimal precision
        val priceOverrideValue = if (cartItem.overriddenPrice != null && cartItem.overriddenPrice > 0.0) {
            cartItem.overriddenPrice // Keep full precision
        } else {
            0.0
        }

        Log.d("TransactionRecord", "Price Override Value: $priceOverrideValue")
        Log.d("TransactionRecord", "Gross Amount: $itemTotal")
        Log.d("TransactionRecord", "Net Amount: $itemAfterDiscount")

        val record = TransactionRecord(
            transactionId = transactionId,
            name = cartItem.productName,
            price = cartItem.price, // Keep original precision
            quantity = cartItem.quantity,
            subtotal = itemTotal, // Keep full precision
            vatRate = (vatRate - 1),
            vatAmount = itemVat,
            discountRate = if (itemTotal > 0) (itemDiscount / itemTotal) else 0.0,
            discountAmount = itemDiscount,
            total = itemAfterDiscount,
            receiptNumber = transactionId,
            timestamp = System.currentTimeMillis(),
            paymentMethod = paymentMethod.uppercase(),
            ar = if (ar > 0.0) (effectivePrice * cartItem.quantity) else 0.0,
            windowNumber = windowId,
            partialPaymentAmount = if (index == 0) cartItem.partialPayment else 0.0,
            comment = cartItem.cartComment ?: "",
            lineNum = index + 1,
            receiptId = transactionId,
            itemId = cartItem.itemId.toString(),
            itemGroup = cartItem.itemGroup.toString(),
            netPrice = cartItem.price,
            costAmount = (itemTotal / vatRate),
            netAmount = itemAfterDiscount, // Keep full precision
            grossAmount = itemTotal, // Keep full precision
            customerAccount = selectedCustomer.name,
            store = getCurrentStore(),
            priceOverride = priceOverrideValue, // FIXED: Store with full precision
            staff = getCurrentStaff(),
            discountOfferId = if (cartItem.mixMatchId != null && cartItem.mixMatchId.isNotEmpty())
                cartItem.mixMatchId
            else if (cartItem.discountName != null && cartItem.discountName.isNotEmpty())
                cartItem.discountName
            else "",
            lineDiscountAmount = itemDiscount,
            lineDiscountPercentage = if (itemTotal > 0) ((itemDiscount / itemTotal) * 100) else 0.0,
            customerDiscountAmount = itemDiscount,
            unit = "PCS",
            unitQuantity = cartItem.quantity.toDouble(),
            unitPrice = cartItem.price,
            taxAmount = itemVat,
            createdDate = currentDateString,
            discountType = when (cartItem.discountType.uppercase()) {
                "PWD" -> "PWD"
                "SC" -> "SC"
                "PERCENTAGE" -> "PERCENTAGE"
                "FIXED" -> "FIXED"
                "FIXEDTOTAL", "FIXED TOTAL" -> "FIXEDTOTAL"
                "DEAL" -> "DEAL"
                else -> "No Discount"
            },
            netAmountNotIncludingTax = netAmountNotIncludingTax,
            storeTaxGroup = null,
            currency = "PHP",
            taxExempt = 0.0,
            storeKey = storeKey,
            storeSequence = storeSequence,
            taxIncludedInPrice = itemVat
        )

        transactionRecords.add(record)
    }

    return transactionRecords
}
    private fun calculateSummaryFromRecords(
        transactionRecords: List<TransactionRecord>,
        cartItems: List<CartItem>
    ): SummaryCalculation {
        var grossAmount = 0.0
        var netAmount = 0.0
        var discountAmount = 0.0
        var vatAmount = 0.0
        var costAmount = 0.0
        var partialPayment = 0.0
        var numberOfItems = 0.0
        var priceOverride = 0.0

        Log.d("SummaryCalc", "=== CALCULATING SUMMARY FROM RECORDS ===")

        // Sum up values from transaction records
        transactionRecords.forEach { record ->
            Log.d("SummaryCalc", "Record: ${record.name}")
            Log.d("SummaryCalc", "  Gross: ${record.grossAmount}")
            Log.d("SummaryCalc", "  Net: ${record.netAmount}")
            Log.d("SummaryCalc", "  Price Override: ${record.priceOverride}")

            grossAmount += (record.grossAmount ?: 0.0)
            netAmount += (record.netAmount ?: 0.0)
            discountAmount += (record.discountAmount ?: 0.0)
            vatAmount += (record.vatAmount ?: 0.0)
            costAmount += (record.costAmount ?: 0.0)
            numberOfItems += record.quantity.toDouble()

            // FIXED: Only count price override if it was actually used
            if (record.priceOverride != null && record.priceOverride!! > 0) {
                priceOverride += (record.priceOverride!! * record.quantity)
            }
        }

        // Get partial payment from cart items (only from first item to avoid duplication)
        partialPayment = cartItems.firstOrNull()?.partialPayment ?: 0.0

        // Calculate VATable sales (net amount without VAT)
        val vatableSales = (netAmount / 1.12)

        Log.d("SummaryCalc", "=== SUMMARY TOTALS ===")
        Log.d("SummaryCalc", "Gross Amount: $grossAmount")
        Log.d("SummaryCalc", "Net Amount: $netAmount")
        Log.d("SummaryCalc", "Discount Amount: $discountAmount")
        Log.d("SummaryCalc", "Price Override Total: $priceOverride")

        return SummaryCalculation(
            grossAmount = grossAmount,
            netAmount = netAmount,
            discountAmount = discountAmount,
            vatAmount = vatAmount,
            vatableSales = vatableSales,
            costAmount = costAmount,
            partialPayment = partialPayment,
            numberOfItems = numberOfItems,
            priceOverride = priceOverride
        )
    }

    // FIXED: Update the debug to show exact calculations
    private fun debugRNDSampleCalculations() {
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    Log.d("RNDCalc", "=== RND SAMPLE CALCULATION VERIFICATION ===")

                    // Get all RND SAMPLE records
                    val allRecords = transactionDao.getAllTransactionRecords()
                    val rndRecords = allRecords.filter { it.name.contains("RND SAMPLE", ignoreCase = true) }

                    var totalQuantity = 0
                    var totalGrossAmount = 0.0
                    var totalNetAmount = 0.0

                    rndRecords.forEach { record ->
                        Log.d("RNDCalc", "=== RECORD: ${record.transactionId} ===")
                        Log.d("RNDCalc", "Price: ${record.price}")
                        Log.d("RNDCalc", "Price Override: ${record.priceOverride}")
                        Log.d("RNDCalc", "Quantity: ${record.quantity}")
                        Log.d("RNDCalc", "Gross Amount: ${record.grossAmount}")
                        Log.d("RNDCalc", "Net Amount: ${record.netAmount}")

                        // Manual calculation check
                        val expectedGross = if (record.priceOverride != null && record.priceOverride!! > 0) {
                            record.priceOverride!! * record.quantity
                        } else {
                            record.price * record.quantity
                        }

                        Log.d("RNDCalc", "Expected Gross: $expectedGross")
                        Log.d("RNDCalc", "Actual Gross: ${record.grossAmount}")
                        Log.d("RNDCalc", "Match: ${expectedGross == record.grossAmount}")

                        totalQuantity += record.quantity
                        totalGrossAmount += record.grossAmount ?: 0.0
                        totalNetAmount += record.netAmount ?: 0.0
                    }

                    Log.d("RNDCalc", "=== TOTALS ===")
                    Log.d("RNDCalc", "Total Quantity: $totalQuantity")
                    Log.d("RNDCalc", "Total Gross: $totalGrossAmount")
                    Log.d("RNDCalc", "Total Net: $totalNetAmount")
                    Log.d("RNDCalc", "Expected from print: 7 x 249.00 = 1743.00")
                    Log.d("RNDCalc", "Expected from data: 7 x ~260.61 = ~1824.27")
                    Log.d("RNDCalc", "========================")
                }
            } catch (e: Exception) {
                Log.e("RNDCalc", "Error in calculation debug", e)
            }
        }
    }
    private fun showChangeAndReceiptDialog(
        change: Double,
        cartItems: List<CartItem>,
        receiptNumber: String,
        paymentMethod: String,
        ar: Double,
        vatAmount: Double,
        totalDiscount: Double,
        netSales: Double,
        discountRate: Double,
        transactionComment: String,
        partialPaymentAmount: Double,
        finalPaymentAmount: Double
    ) {
        // Create a layout for the dialog with more padding for better spacing
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 64, 48, 64)  // Increased padding
            gravity = Gravity.CENTER
        }

        // Enhanced "Change" label TextView
        val labelTextView = TextView(this).apply {
            text = "Change Amount"  // More descriptive label
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)  // Larger text
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 24)  // More space below the label
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)  // Modern font
        }

        // Create a container for amount display
        val amountContainer = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 16)

            // Optional: Add a background for the amount section
            background = ColorDrawable(ContextCompat.getColor(context, android.R.color.white))
        }

        // Enhanced peso sign TextView
        val pesoSignTextView = TextView(this).apply {
            text = "₱"
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 56f)  // Larger peso sign
            typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
            gravity = Gravity.CENTER
            setPadding(0, 0, 8, 0)  // Add some space between peso sign and amount
        }

        // Enhanced Change amount TextView
        val changeTextView = TextView(this).apply {
            text = "%.2f".format(change)
            setTextColor(ContextCompat.getColor(context, android.R.color.black))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 72f)  // Larger amount
            typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
            gravity = Gravity.CENTER
        }

        // Add views to the amount container
        amountContainer.addView(pesoSignTextView)
        amountContainer.addView(changeTextView)

        // Add a divider for visual separation
        val divider = View(this).apply {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                2  // 2dp height
            ).apply {
                setMargins(32, 32, 32, 32)
            }
        }

        // Transaction details (optional)
        val detailsTextView = TextView(this).apply {
            text = "Receipt #$receiptNumber\n$paymentMethod"
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 0)
        }

        // Add all views to main layout
        layout.apply {
            addView(labelTextView)
            addView(amountContainer)
            addView(divider)
            addView(detailsTextView)
        }

        // Enhanced CardView
        val cardView = CardView(this).apply {
            radius = 16f * resources.displayMetrics.density  // Larger corner radius
            cardElevation = 8f * resources.displayMetrics.density  // More pronounced shadow
            setContentPadding(24, 24, 24, 24)
            setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(48, 48, 48, 48)
            }
            addView(layout)
        }

        // Create and show the enhanced dialog
        AlertDialog.Builder(this)
            .setView(cardView)
            .setPositiveButton("Done") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }

        Log.d(TAG, "Displayed change dialog: $change")
    }
}
