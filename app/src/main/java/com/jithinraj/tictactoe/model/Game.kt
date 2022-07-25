package com.jithinraj.tictactoe.model


class Game(playerOne: String,playerTwo: String) {

    private val player1 = Player(playerOne, PLAYER_ONE_VALUE)
    private val player2 = Player(playerTwo, PLAYER_TWO_VALUE)
    var currentPlayer = player1
    var oldPlayer = player2
    var cells = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Cell(null) } }
    private val cell = Cell(currentPlayer)

    companion object {
        const val PLAYER_ONE_VALUE = "X"
        const val PLAYER_TWO_VALUE = "O"
        const val BOARD_SIZE = 3
        const val INDEX_ZERO = 0
        const val NO_WINNER_FOUND = "No winner found! Game drawn."
    }


    fun isWinnerAvailable(): Boolean =
        hasThreeSameHorizontalCells() || hasThreeSameVerticalCells() || hasThreeSameDiagonalCells()

    private fun hasThreeSameDiagonalCells(): Boolean = when {
        compareValuesFromLeft() || compareValuesFromRight() -> true
        else -> false
    }

    private fun hasThreeSameVerticalCells(): Boolean {
        (INDEX_ZERO until BOARD_SIZE).forEach { index ->
            when {
                compareValuesByColumn(index) -> return true
            }
        }
        return false
    }

    private fun hasThreeSameHorizontalCells(): Boolean {
        (INDEX_ZERO until BOARD_SIZE).forEach { index ->
            when {
                compareValuesByRow(index) -> return true
            }
        }
        return false
    }

    private fun compareValuesFromRight() = cell.getDiagonalFromRightToLeft(cells)
    private fun compareValuesFromLeft() = cell.getDiagonalCellsFromLeftToRight(cells)
    private fun compareValuesByRow(index: Int) = cell.getHorizontalCells(cells, index)
    private fun compareValuesByColumn(index: Int) = cell.getVerticalCells(cells, index)


    fun isFull() = cell.isFull(cells)
    fun switchPlayer() = when (currentPlayer) {
        player1 -> {
            currentPlayer = player2
            oldPlayer = player1
        }
        else -> {
            currentPlayer = player1
            oldPlayer = player2
        }
    }
}