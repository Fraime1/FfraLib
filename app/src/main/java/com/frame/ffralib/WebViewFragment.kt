package com.frame.ffralib

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.frame.ffralib.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WebViewFragment : Fragment() {
    private val dataStore by viewModels<BigFishGoldDataStore>()
    private lateinit var bigFishGoldPhoto: Uri
    private lateinit var bigFishGoldFilePathFromChrome: ValueCallback<Array<Uri>>
    private lateinit var abigFishGoldRequestFromChrome: PermissionRequest
    private lateinit var webview: FfraW


//    val bigFishGoldTakeFile = registerForActivityResult(ActivityResultContracts.GetContent()) {
//        helpGetFile(uri = it, filePathFromChrom = bigFishGoldFilePathFromChrome)
//    }
//
//    val bigFishGoldTakePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
//        if (it == true) {
//            helpTakePhoto(photo = bigFishGoldPhoto, filePathFromChrom = bigFishGoldFilePathFromChrome)
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CookieManager.getInstance().setAcceptCookie(true)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (webview.canGoBack()) {
                        webview.goBack()
                    } else if (dataStore.bigFishGoldFFWSeVi.isNotEmpty()) {
                        dataStore.bigFishGoldFFWSeVi.removeLast()
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }

            })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dataStore.bigFishGoldFFWSeVi.isNotEmpty()) {
            webview = dataStore.bigFishGoldFFWSeVi.last()
        } else {
            webview = FfraW(requireContext())
            webview.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webview.fLoad("https://lk.nsq.market/ru/tools/testing")
        }
        return webview
    }

//    override fun onPermissionRequest(request: PermissionRequest?) {
//        if (request != null) {
//            abigFishGoldRequestFromChrome = request
//        }
//        Dexter.withContext(requireActivity())
//            .withPermission(Manifest.permission.CAMERA)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    helpTakePhotoFromChromPermission(requestFromChrome = abigFishGoldRequestFromChrome)
//                }
//
//                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    p0: com.karumi.dexter.listener.PermissionRequest?,
//                    p1: PermissionToken?
//                ) {
//
//                }
//
//            })
//            .check()
//    }
//
//    override fun onShowFileChooser(
//        webView: WebView?,
//        filePathCallback: ValueCallback<Array<Uri>>?,
//        fileChooserParams: WebChromeClient.FileChooserParams?
//    ) {
//        if (filePathCallback != null) {
//            bigFishGoldFilePathFromChrome = filePathCallback
//        }
//        val listItems: Array<out String> =
//            arrayOf("Select from file", "To make a photo")
//        val listener = DialogInterface.OnClickListener { _, which ->
//            when (which) {
//                0 -> {
//                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
//                        takeFilePermissionFromAndroid13()
//                    } else {
//                        takeFilePermissionFromAndroid()
//                    }
//                }
//                1 -> {
//                    Dexter.withContext(requireActivity())
//                        .withPermission(Manifest.permission.CAMERA)
//                        .withListener(object : PermissionListener {
//                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                                bigFishGoldPhoto = bigFishGoldSavePhoto()
//                                bigFishGoldTakePhoto.launch(bigFishGoldPhoto)
//                            }
//
//                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//
//                            }
//
//                            override fun onPermissionRationaleShouldBeShown(
//                                p0: com.karumi.dexter.listener.PermissionRequest?,
//                                p1: PermissionToken?
//                            ) {
//
//                            }
//
//                        })
//                        .check()
//                }
//            }
//        }
//        AlertDialog.Builder(requireActivity())
//            .setTitle("Choose a method")
//            .setItems(listItems, listener)
//            .setCancelable(true)
//            .setOnCancelListener {
//                filePathCallback?.onReceiveValue(arrayOf(Uri.EMPTY))
//            }
//            .create()
//            .show()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
//    private fun takeFilePermissionFromAndroid13() {
//        Dexter.withContext(requireActivity())
//            .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    bigFishGoldTakeFile.launch("image/*")
//                }
//
//                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    p0: com.karumi.dexter.listener.PermissionRequest?,
//                    p1: PermissionToken?
//                ) {
//
//                }
//
//            })
//            .check()
//    }
//
//    private fun takeFilePermissionFromAndroid() {
//        Dexter.withContext(requireActivity())
//            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//            .withListener(object : PermissionListener {
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    bigFishGoldTakeFile.launch("image/*")
//                }
//
//                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    p0: com.karumi.dexter.listener.PermissionRequest?,
//                    p1: PermissionToken?
//                ) {
//
//                }
//
//            })
//            .check()
//    }
//
//    override fun onCreateWindow(
//        view: WebView?,
//        isDialog: Boolean,
//        isUserGesture: Boolean,
//        resultMsg: Message?
//    ) {
//        bigFishGoldHandleCreateWebWindowRequest(resultMsg)
//    }
//
//    fun helpGetFile(uri: Uri?, filePathFromChrom: ValueCallback<Array<Uri>>) {
//        filePathFromChrom.onReceiveValue(arrayOf(uri ?: Uri.EMPTY))
//    }
//
//    fun helpTakePhoto(photo: Uri, filePathFromChrom: ValueCallback<Array<Uri>>) {
//        filePathFromChrom.onReceiveValue(arrayOf(photo))
//    }
//
//    fun helpTakePhotoFromChromPermission(requestFromChrome: PermissionRequest) {
//        requestFromChrome.grant(requestFromChrome.resources)
//    }
//
//    fun bigFishGoldSavePhoto() : Uri {
//        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
//        val df = sdf.format(Date())
//        val dir = requireActivity().filesDir.absoluteFile
//        if (!dir.exists()) {
//            dir.mkdir()
//        }
//        return FileProvider.getUriForFile(
//            requireActivity(),
//            "net.fishol.bigold",
//            File(dir, "/$df.jpg")
//        )
//    }
//
//    private fun bigFishGoldHandleCreateWebWindowRequest(resultMsg: Message?) {
//        if (resultMsg == null) return
//        if (resultMsg.obj != null && resultMsg.obj is WebView.WebViewTransport) {
//            val transport = resultMsg.obj as WebView.WebViewTransport
//            val bigFishGoldWindowWebView = FfraW(requireContext())
//            bigFishGoldWindowWebView.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            dataStore.bigFishGoldFFWSeVi.add(bigFishGoldWindowWebView)
//            transport.webView = bigFishGoldWindowWebView
//            resultMsg.sendToTarget()
//            findNavController().navigate(R.id.action_webViewFragment_self)
//        }
//    }
}