package com.chhavi.tictactoegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity(), View.OnClickListener  {

    private val buttons = Array(3) { arrayOfNulls<Button>(3) }
    private var currentPlayer = 1
    private var movesCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = "button_$i$j"
                val resId = resources.getIdentifier(buttonId, "id", packageName)
                buttons[i][j] = findViewById(resId)
                buttons[i][j]?.setOnClickListener(this)
            }
        }
        val resetButton: Button = findViewById(R.id.resetButton)
        resetButton.setOnClickListener {
            resetGame()
        }
    }

    override fun onClick(view: View) {
        val button = view as Button
        if (button.text.toString().isNotEmpty()) {
            return
        }

        if (currentPlayer == 1) {
            button.text = "X"
        } else {
            button.text = "O"
        }

        movesCount++
        if (checkForWin()) {
            showToast("Player $currentPlayer wins!")
            resetGame()
        } else if (movesCount == 9) {
            showToast("It's a draw!")
            resetGame()
        } else {
            currentPlayer = 3 - currentPlayer // Switch player (1 -> 2, 2 -> 1)
        }
    }

    private fun checkForWin(): Boolean {
        // Check rows, columns, and diagonals for a win
        for (i in 0..2) {
            if (buttons[i][0]?.text == buttons[i][1]?.text && buttons[i][1]?.text == buttons[i][2]?.text && buttons[i][0]?.text?.isNotEmpty() ?: true) {
                return true
            }
            if (buttons[0][i]?.text == buttons[1][i]?.text && buttons[1][i]?.text == buttons[2][i]?.text && buttons[0][i]?.text?.isNotEmpty() ?: true) {
                return true
            }
        }

        if (buttons[0][0]?.text == buttons[1][1]?.text && buttons[1][1]?.text == buttons[2][2]?.text && buttons[0][0]?.text?.isNotEmpty() ?: true) {
            return true
        }
        if (buttons[0][2]?.text == buttons[1][1]?.text && buttons[1][1]?.text == buttons[2][0]?.text && buttons[0][2]?.text?.isNotEmpty() ?: true) {
            return true
        }

        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun resetGame() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]?.text = ""
            }
        }
        currentPlayer = 1
        movesCount = 0
    }
}

