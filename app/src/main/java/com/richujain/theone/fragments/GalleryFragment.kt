package com.richujain.theone.fragments

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.richujain.theone.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GalleryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GalleryFragment : Fragment() {
    val READ_RQ = 101
    val WRITE_RQ = 102
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
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
                Toast.makeText(requireContext(),"$name permission granted", Toast.LENGTH_SHORT).show()
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        checkForPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            "readstorage",
            READ_RQ
        )
        //checkForPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,"writestorage",READ_RQ)
        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .show()
            openGallery()
        }
    }
    private fun openGallery(){

    }
}