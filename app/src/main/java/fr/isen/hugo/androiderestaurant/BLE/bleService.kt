package fr.isen.hugo.androiderestaurant.BLE

import android.bluetooth.BluetoothGattCharacteristic
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class bleService(val name: String, val characteristics: MutableList<BluetoothGattCharacteristic>) :
    ExpandableGroup<BluetoothGattCharacteristic>(name, characteristics)