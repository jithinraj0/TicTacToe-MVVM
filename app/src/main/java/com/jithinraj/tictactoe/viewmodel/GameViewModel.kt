package com.jithinraj.tictactoe.viewmodel

import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jithinraj.tictactoe.model.Cell
import com.jithinraj.tictactoe.model.Game

class GameViewModel : ViewModel() {

    var text: ObservableField<String> = ObservableField()
    lateinit var game: Game
    lateinit var cells: ObservableArrayMap<String, String>
    private lateinit var playerOne: String
    private lateinit var playerTwo: String
    private var winner = MutableLiveData<String>()
    private var noWinner = MutableLiveData<String>()

    fun init(playerOne: String, playerTwo: String) {
        this.playerOne = playerOne
        this.playerTwo = playerTwo
        game = Game(playerOne, playerTwo)
        cells = ObservableArrayMap()
        text.set("${game.currentPlayer.name}'s turn")
    }

    fun getWinner(): LiveData<String> = winner
    fun getNoWinner(): LiveData<String> = noWinner

    fun onClickedCellAt(row: Int, column: Int) {
        when {
            isCellEmpty(row, column) -> updatePlayerValueInSelectedCell(row, column)
        }
    }


    private fun updatePlayerValueInSelectedCell(row: Int, column: Int) {
        game.cells[row][column] = notifyCurrentPlayer()
        cells[stringFromNumbers(row, column)] = game.currentPlayer.value
        text.set("${game.oldPlayer.name}'s turn")
        updateGameStatus()
    }

    fun updateGameStatus() = when {
        game.isWinnerAvailable() -> winner.postValue(game.currentPlayer.name)
        game.isFull() -> noWinner.postValue(Game.NO_WINNER_FOUND)
        else -> game.switchPlayer()
    }

    fun stringFromNumbers(vararg numbers: Int): String {
        val sNumbers = StringBuilder()
        numbers.forEach { number -> sNumbers.append(number) }
        return sNumbers.toString()
    }

    fun resetGame() {
        init(playerOne, playerTwo)
        winner = MutableLiveData()
        noWinner = MutableLiveData()
    }

    private fun isCellEmpty(row: Int, column: Int) = game.cells[row][column].isEmptyCell
    private fun notifyCurrentPlayer() = Cell(game.currentPlayer)


}