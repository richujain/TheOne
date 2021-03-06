package com.richujain.theone.utility

import android.app.Activity
import android.app.ProgressDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadUtility(activity: Activity) {

    var activity = activity;
    var dialog: ProgressDialog? = null
    var serverURL: String = "http://192.168.0.10/uploadFile.php"
    var serverUploadDirectoryPath: String = "http://192.168.0.10/uploads/"
    val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }

    fun uploadFile(sourceFileUri: Uri, uploadedFileName: String? = null) {
        val pathFromUri = URIPathHelper().getPath(activity,sourceFileUri)
        uploadFile(File(pathFromUri.toString()), uploadedFileName)
    }

    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile.path);
            try {
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = if (uploadedFileName == null)  sourceFile.name else uploadedFileName
            //val fileName: String = uploadedFileName ?: sourceFile.name34-38
            toggleProgressDialog(true)

                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", fileName,sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("Response",""+response.toString())
                    Log.d("Request",""+request.toString())
                    copyText("$serverUploadDirectoryPath$fileName")
                    Log.d("File upload","success, path: $serverUploadDirectoryPath$fileName")
                    //showToast("File uploaded successfully at $serverUploadDirectoryPath$fileName")
                    showToast("Link Copied To Clipboard")
                    response.close();
                } else {
                    Log.e("File upload", "failed")
                    showToast("File uploading failed")
                    response.close();
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                showToast("File uploading failed")
            }
            toggleProgressDialog(false)
        }.start()
    }
    fun copyText(text:String){
        val myClipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val myClip: ClipData = ClipData.newPlainText("Label", text)
        myClipboard.setPrimaryClip(myClip)
    }

    // url = file path or whatever suitable URL you want.
//    fun getMimeType(file: File): String? {
//        var type: String? = null
//        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
//        if (extension != null) {
//            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
//        }
//        return type
//    }
    fun getMimeType(path: String): String {
        var type = "video/mp4" // Default Value
        val extension = MimeTypeMap.getFileExtensionFromUrl(path);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension).toString()
        }
        return type
    }

    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText( activity, message, Toast.LENGTH_LONG ).show()
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        activity.runOnUiThread {
            if (show) {
                dialog = ProgressDialog.show(activity, "", "Uploading file...", true);
            } else {
                dialog?.dismiss();
            }
        }
    }

}