package com.jithinraj.tictactoe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jithinraj.tictactoe.R
import com.jithinraj.tictactoe.databinding.ActivityGameBoardBinding
import com.jithinraj.tictactoe.viewmodel.GameViewModel

class GameBoardActivity : AppCompatActivity() {

    private lateinit var activityGameBoardBinding: ActivityGameBoardBinding
    private val gameViewModel: GameViewModel by lazy { ViewModelProvider(this).get(GameViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityGameBoardBinding = DataBindingUtil.setContentView(this, R.layout.activity_game_board)
        gameViewModel.init(getString(R.string.player_one), getString(R.string.player_two))
        activityGameBoardBinding.gameViewModel = gameViewModel
        invalidateGameBoard()
    }

    private fun invalidateGameBoard() {
        activityGameBoardBinding.gameViewModel = gameViewModel
        setUpOnGameEndListener()
    }

    private fun setUpOnGameEndListener() {
        gameViewModel.getWinner().observe(this, Observer { playerName ->
            showGameResult("${getString(R.string.winner_is)} $playerName!")
        })
        gameViewModel.getNoWinner().observe(this, Observer { message ->
            showGameResult(message)
        })
    }

    private fun resetGame() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            gameViewModel.resetGame()
            invalidateGameBoard()
        }, 500)
    }

    private fun showGameResult(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        resetGame()
    }


}