package fr.isen.hugo.androiderestaurant.BLE

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.apply
import android.widget.Adapter
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat.apply
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.hugo.androiderestaurant.R
import fr.isen.hugo.androiderestaurant.databinding.ActivityBlescanBinding
import fr.isen.hugo.androiderestaurant.model.bleAdapter
import java.util.jar.Manifest

class BLEScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlescanBinding

    private val ENABLE_BLUETOOTH_REQUEST_CODE = 1
    private val ALL_PERMISSION_REQUEST_CODE = 100
    private var isScanning = false

    private var adapter: bleAdapter?=null
    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlescanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            bluetoothAdapter?.isEnabled == true ->
                startLeScanWithPermission(true)
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayBLEUnAvailable()
            }
        }
        binding.bleScanStateImg.setOnClickListener {
            val test = isScanning

            startLeScanWithPermission(!isScanning)
        }
        binding.bleScanStateTitle.setOnClickListener {
            startLeScanWithPermission(!isScanning)
        }

        adapter = bleAdapter(arrayListOf()){
            val intent = Intent(this, bleDevice::class.java)
            intent.putExtra("device", it)
            startActivity(intent)
        }
        binding.bleScanList.layoutManager = LinearLayoutManager(this)
        binding.bleScanList.adapter
    }



    override fun onStop(){
        super.onStop()
        startLeScanWithPermission(false)
    }

    private fun startLeScanWithPermission(enable: Boolean){
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            startLeScanBLE(enable)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
                /*Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN*/
            ), ALL_PERMISSION_REQUEST_CODE)
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {

        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if(enable) {
                isScanning = true
                startScan(scanCallback)
            } else {
                isScanning = false
                stopScan(scanCallback)
            }
            handlePlayPauseAction()
        }
    }



    private val scanCallback = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("BLEScanActivity", "result: ${result?.device?.address}, rssi : ${result?.rssi}")
            adapter?.apply{
                addElement(result)
                notifyDataSetChanged()
            }
        }
    }

    private fun displayBLEUnAvailable() {
        binding.bleScanStateImg.isVisible = true
        binding.bleScanStateTitle.text = getString(R.string.ble_scan_device_error)
        binding.bleScanProgression.isVisible = false
    }

    private fun askBluetoothPermission(){
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    private fun handlePlayPauseAction() {

        if(isScanning) {
            binding.bleScanStateImg.setImageResource(R.drawable.ic_pause)
            binding.bleScanStateTitle.text = getString(R.string.ble_scan_pause)
            binding.bleScanProgression.isIndeterminate = true
        }
        else {
            binding.bleScanStateImg.setImageResource(R.drawable.ic_play)
            binding.bleScanStateTitle.text = getString(R.string.ble_scan_play)
            binding.bleScanProgression.isIndeterminate = false
        }


    }
    companion object{
        const val DEVICE_KEY = "device"
    }

}