package com.rolla.testapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rolla.testapp.databinding.ItemButtonBinding
import com.rolla.testapp.model.Button
import com.rolla.testapp.util.findPositionInList
import kotlinx.coroutines.*

class ButtonsAdapter(
    private val buttons: MutableList<Button>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<ButtonsAdapter.ButtonViewHolder>() {

    private val clickedButtonQueue: MutableList<Button> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonViewHolder {
        val binding =
            ItemButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(buttons[position])
    }

    override fun getItemCount(): Int = buttons.size

    inner class ButtonViewHolder(private val binding: ItemButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btn.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    if (buttons[adapterPosition].position == -1) {
                        delay((1000L..3000L).random())
                        invokeReset()
                    } else {
                        val clickedButton = buttons[adapterPosition]
                        clickedButtonQueue.add(clickedButton)
                        delay((1000L..3000L).random()) // non-blocking delay for 1-3 seconds(default time unit is ms)
                        val positionInQueue = findPositionInList(clickedButton, clickedButtonQueue)
                        if (positionInQueue == 0) {
                            invokeClick(clickedButton, positionInQueue)
                        } else {
                            clickedButton.inQueue = true
                        }
                        showQueuedButtons()
                    }
                }
            }
        }

        fun bind(button: Button) {
            binding.btn.text = button.name
        }

        private fun invokeReset() {
            listener.onItemClick(buttons[adapterPosition], adapterPosition)
            clickedButtonQueue.clear()
        }

        private fun showQueuedButtons() {
            val listToRemove = mutableListOf<Button>()
            var show = true
            for (i in 0 until clickedButtonQueue.size) {
                if (show && clickedButtonQueue[i].inQueue) {
                    listener.onItemClick(clickedButtonQueue[i], i)
                    clickedButtonQueue[i].inQueue = false
                    listToRemove.add(clickedButtonQueue[i])
                } else {
                    show = false
                }
            }
            clickedButtonQueue.removeAll(listToRemove)
        }

        private fun invokeClick(button: Button, positionInQueue: Int) {
            listener.onItemClick(buttons[adapterPosition], adapterPosition)
            clickedButtonQueue.removeAt(positionInQueue)
            button.inQueue = false
        }

    }

    interface OnItemClickListener {
        fun onItemClick(button: Button, position: Int)
    }
}