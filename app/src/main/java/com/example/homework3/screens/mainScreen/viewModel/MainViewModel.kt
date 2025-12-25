package com.example.homework3.viewModel

import android.content.Context
import com.example.homework2.R

interface  IMainViewModel {

    val imageUrlId: Int
    val catMessageIds: List<Int>
    fun onCatImageViewClicked(context: Context, catId: Int, showToast: (String) -> Unit)
}

class MainViewModel: IMainViewModel {

    override val imageUrlId = R.string.imageUrl
    override val catMessageIds = listOf(
        R.string.catMessage_1,
        R.string.catMessage_2,
        R.string.catMessage_3,
        R.string.catMessage_4,
        R.string.catMessage_5,
        R.string.catMessage_6,
        R.string.catMessage_7,
        R.string.catMessage_8,
        R.string.catMessage_9,
        R.string.catMessage_10,
    )

    override fun onCatImageViewClicked(
        context: Context,
        catId: Int,
        showToast: (String) -> Unit
    ) {
        val messageId = catMessageIds.random()
        val message = context.getString(messageId)
        showToast("Котик #$catId: $message")
    }
}