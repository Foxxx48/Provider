package com.fox.provider

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.fox.provider.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding = null")

    private var purchaseItemList = mutableListOf<PurchaseItem>()

    private var value = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        thread {
            val cursor = contentResolver.query(
                Uri.parse("content://com.fox.purchasinglist/purchase_list"),
                null,
                null,
                null,
                null
            )
            while (cursor?.moveToNext() == true) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
                val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0

                val purchaseItem = PurchaseItem(
                    id = id,
                    name = name,
                    count = count,
                    enabled = enabled
                )
                purchaseItemList.add(purchaseItem)
                println(purchaseItemList.size)
            }
            cursor?.close()
        }
        binding.tvItemNumber.text = value.toString()
        Log.d ("MainActivity", purchaseItemList.toString())

        binding.btnNext.setOnClickListener {

            if (purchaseItemList.isNotEmpty() && value <= purchaseItemList.size ) {
                purchaseItemList[++value]
                println(value)
                binding.tvItemNumber.text = value.toString()
            } else {
                Toast.makeText(this, "Have no PurchaseItem", Toast.LENGTH_SHORT).show()
            }


        }

        binding.btnShow.setOnClickListener {
                println(value)
            if (purchaseItemList.isNotEmpty() && value <= purchaseItemList.size ) {
                binding.tvId.text = purchaseItemList[value].id.toString()
                binding.tvName.text = purchaseItemList[value].name
                binding.tvCount.text = purchaseItemList[value].count.toString()
                binding.tvEnabled.text = purchaseItemList[value].enabled.toString()
            } else {
                Toast.makeText(this, "Have no PurchaseItem", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnPrevious.setOnClickListener {

            purchaseItemList[--value]
            println(value)
            binding.tvItemNumber.text = value.toString()

        }




//        thread {
//            val cursor = contentResolver.query(
//                Uri.parse("content://com.fox.purchasinglist/purchase_list"),
//                null,
//                null,
//                null,
//                null
//            )
//
//            binding.btnNext.setOnClickListener {
//                if (cursor?.moveToNext() == true) {
//                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
//                    val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
//                    val count = cursor.getInt(cursor.getColumnIndexOrThrow("count"))
//                    val enabled = cursor.getInt(cursor.getColumnIndexOrThrow("enabled")) > 0
//
//                    val purchaseItem = PurchaseItem(
//                        id = id,
//                        name = name,
//                        count = count,
//                        enabled = enabled
//                    )
//                    binding.tvId.text = purchaseItem.id.toString()
//                    binding.tvName.text = purchaseItem.name
//                    binding.tvCount.text = purchaseItem.count.toString()
//                    binding.tvEnabled.text = purchaseItem.enabled.toString()
//
//                    Log.d("MainActivity", purchaseItem.toString())
//
//
//
//                    cursor.move(1)
//                } else {
//                    cursor?.moveToFirst()
//                }
//            }
//
//        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}