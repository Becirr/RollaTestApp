package com.rolla.testapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rolla.testapp.databinding.ItemClickedButtonBinding
import com.rolla.testapp.model.Button
import com.rolla.testapp.util.findPositionInList
import com.rolla.testapp.util.isInList

class ClickedButtonsAdapter :
    RecyclerView.Adapter<ClickedButtonsAdapter.ClickedButtonViewHolder>() {

    private val buttons: MutableList<Button> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClickedButtonViewHolder {
        val binding =
            ItemClickedButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClickedButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClickedButtonViewHolder, position: Int) {
        holder.bind(buttons[position])
    }

    override fun getItemCount(): Int = buttons.size

    fun addOrRemove(button: Button) {
        if (isInList(button, buttons)) {
            val posToRemove = findPositionInList(button, buttons)
            buttons.removeAt(posToRemove)
            notifyItemRemoved(posToRemove)
        } else {
            buttons.add(button)
            notifyItemInserted(buttons.size - 1)
        }
    }

    fun reset() {
        buttons.clear()
        notifyDataSetChanged()
    }

    inner class ClickedButtonViewHolder(private val binding: ItemClickedButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(button: Button) {
            binding.tvName.text = button.name
        }

    }
}