package com.example.possystembw.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.possystembw.R
import com.example.possystembw.database.MixMatchWithDetails

//class MixMatchAdapter(
//    private val mixMatches: List<MixMatchWithDetails>,
//    private val onMixMatchSelected: (MixMatchWithDetails) -> Unit
//) : BaseAdapter() {
//    override fun getCount(): Int = mixMatches.size
//    override fun getItem(position: Int): MixMatchWithDetails = mixMatches[position]
//    override fun getItemId(position: Int): Long = position.toLong()
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val context = parent?.context
//        val view = convertView ?: LayoutInflater.from(context)
//            .inflate(android.R.layout.simple_list_item_2, parent, false)
//
//        val mixMatch = getItem(position)
//        view.findViewById<TextView>(android.R.id.text1).text = mixMatch.mixMatch.description
//
//        // Format discount description
//        val discountText = when (mixMatch.mixMatch.discountType) {
//            0 -> "Deal Price: P${mixMatch.mixMatch.dealPriceValue}"
//            1 -> "Discount: ${mixMatch.mixMatch.discountPctValue}%"
//            2 -> "Discount: P${mixMatch.mixMatch.discountAmountValue}"
//            else -> "Special Offer"
//        }
//        view.findViewById<TextView>(android.R.id.text2).text = discountText
//
//        // Set click listener
//        view.setOnClickListener {
//            onMixMatchSelected(mixMatch)
//        }
//
//        return view
//    }
//}
class MixMatchAdapter(
    private val mixMatches: List<MixMatchWithDetails>,
    private val onMixMatchSelected: (MixMatchWithDetails) -> Unit
) : BaseAdapter() {
    override fun getCount(): Int = mixMatches.size
    override fun getItem(position: Int): MixMatchWithDetails = mixMatches[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.mix_match_card_item, parent, false)

        val mixMatch = getItem(position)

        view.findViewById<TextView>(R.id.tvDescription).text = mixMatch.mixMatch.description

        // Format discount description with icon
        val discountText = when (mixMatch.mixMatch.discountType) {
            0 -> "💰 Deal Price: P${mixMatch.mixMatch.dealPriceValue}"
            1 -> "% Discount: ${mixMatch.mixMatch.discountPctValue}%"
            2 -> "₱ Discount: P${mixMatch.mixMatch.discountAmountValue}"
            else -> "🎉 Special Offer"
        }
        view.findViewById<TextView>(R.id.tvDiscount).text = discountText

        // Add item count information
        val itemCount = mixMatch.lineGroups.size
        view.findViewById<TextView>(R.id.tvItemCount).text =
            "$itemCount ${if (itemCount == 1) "item" else "items"} in this offer"

        // Set ripple effect for card click
        view.setOnClickListener {
            onMixMatchSelected(mixMatch)
        }

        return view
    }
}