package com.hussien.qouty.models

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Quote.
 * primary object of the app represent a single quote
 */
@Entity(
    tableName = "Quotes",
    indices = [Index(value = ["lang", "tag", "loved"])]
)
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val author: String,
    val lang: String,
    val tag: String,
    @ColumnInfo(name = "loved") val isLoved: Boolean = false
){
    val content: String
        get() = "”${text}“\n${author}"
}

class QuotesDiffUtil : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Quote, newItem: Quote) = oldItem == newItem
}