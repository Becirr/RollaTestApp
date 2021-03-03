package com.rolla.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rolla.testapp.adapter.ButtonsAdapter
import com.rolla.testapp.adapter.ClickedButtonsAdapter
import com.rolla.testapp.databinding.ActivityMainBinding
import com.rolla.testapp.model.Button

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var buttons: MutableList<Button> = mutableListOf()
    private var buttonsListAdapter = ClickedButtonsAdapter()
    private var buttonListener = object : ButtonsAdapter.OnItemClickListener {
        override fun onItemClick(button: Button, position: Int) {
            if (button.position == -1) {
                buttonsListAdapter.reset()
            } else {
                buttonsListAdapter.addOrRemove(button)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadButtons()
        binding.apply {
            rvButtons.setHasFixedSize(true)
            rvButtons.adapter = ButtonsAdapter(buttons, buttonListener)
            rvButtonsList.setHasFixedSize(true)
            rvButtonsList.adapter = buttonsListAdapter
        }

    }

    private fun loadButtons() {
        for (i in 1 until 9) {
            buttons.add(Button(resources.getString(R.string.button, i), i))
        }
        buttons.add(Button(resources.getString(R.string.reset), -1))
    }
}