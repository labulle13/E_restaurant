package fr.isen.hugo.androiderestaurant.model

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.hugo.androiderestaurant.R


class bleAdapter(private var data: ArrayList<ScanResult>, val result: (BluetoothDevice) -> Unit) :
    RecyclerView.Adapter<bleAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var adresse: TextView = view.findViewById(R.id.bleAdress)
        var name: TextView = view.findViewById(R.id.bleName)
        var rssi: TextView = view.findViewById(R.id.bleRssi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val bleView =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_ble, parent, false)
        return MyViewHolder(bleView)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("XXX", "onBindViewHolder")
        val result = data[position]
        holder.adresse.text = result.device.address
        holder.name.text = result.device.name
        holder.name.text = "unknow"
        holder.rssi.text = result.rssi.toString()

        holder.itemView.setOnClickListener {
            result(result.device)
        }
    }

    fun addElement(result: ScanResult) {
        val indexOfResult = data.indexOfFirst {it.device.address == result.device.address}
        if (indexOfResult != -1) {
            data[indexOfResult] = result
        } else {
            data.add(result)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


}