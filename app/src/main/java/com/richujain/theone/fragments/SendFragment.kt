package com.richujain.theone.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.richujain.theone.R
import com.richujain.theone.utility.UploadUtility
import java.io.FileDescriptor


class SendFragment : Fragment() {
    val READ_RQ = 101
    val WRITE_RQ = 102
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkForPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            "readstorage",
            READ_RQ
        )
        checkForPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"writestorage",WRITE_RQ)
        val buttonLogin = view.findViewById<Button
                >(R.id.buttonSharableLink)
        buttonLogin.setOnClickListener() {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data //The uri with the location of the file
            if(selectedFile != null){
                activity?.let { UploadUtility(it).uploadFile(selectedFile) } // Either Uri, File or String file path
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String){
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(requireContext(),"$name permission refused", Toast.LENGTH_SHORT).show()
            }
            else{
                //Toast.makeText(requireContext(),"$name permission granted", Toast.LENGTH_SHORT).show()
            }
        }
        when (requestCode){
            READ_RQ -> innerCheck("readstorage")
            //WRITE_RQ -> innerCheck("writestorage")
        }
    }
    private fun showDialog(permission: String,name: String,requestCode: Int){
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setMessage("Permission to access your $name is required to use this application.")
            setTitle("Permission Required")
            setPositiveButton("OK"){
                    dialog, which -> ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),requestCode)
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun checkForPermissions(permission: String, name: String, requestCode: Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(requireContext(),permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(requireContext(),"$name permission granted", Toast.LENGTH_SHORT).show()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission, name, requestCode)
                else -> ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission),requestCode)
            }
        }
    }

}