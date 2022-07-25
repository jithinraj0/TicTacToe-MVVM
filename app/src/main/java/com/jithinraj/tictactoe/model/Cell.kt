package com.jithinraj.tictactoe.model

class Cell(var player: Player?) {
    companion object {
        const val INDEX_ZERO = 0
        const val INDEX_ONE = 1
        const val INDEX_TWO = 2
    }

    val isEmptyCell = player == null || player?.value.isNullOrEmpty()

    fun getHorizontalCells(cells: Array<Array<Cell>>, index: Int) = hasCellValuesAreEqual(
        cells[index][INDEX_ZERO],
        cells[index][INDEX_ONE],
        cells[index][INDEX_TWO]
    )

    fun getVerticalCells(cells: Array<Array<Cell>>, index: Int) = hasCellValuesAreEqual(
        cells[INDEX_ZERO][index],
        cells[INDEX_ONE][index],
        cells[INDEX_TWO][index]
    )

    fun getDiagonalCellsFromLeftToRight(cells: Array<Array<Cell>>) = hasCellValuesAreEqual(
        cells[INDEX_ZERO][INDEX_ZERO],
        cells[INDEX_ONE][INDEX_ONE],
        cells[INDEX_TWO][INDEX_TWO]
    )

    fun getDiagonalFromRightToLeft(cells: Array<Array<Cell>>) = hasCellValuesAreEqual(
        cells[INDEX_ZERO][INDEX_TWO],
        cells[INDEX_ONE][INDEX_ONE],
        cells[INDEX_TWO][INDEX_ZERO]
    )

    fun hasCellValuesAreEqual(vararg cells: Cell): Boolean {
        if (isEmptyCell(cells)) return false
        cells.forEach { cell ->
            when {
                isPlayerValueIsEmpty(cell) -> return false
            }
        }
        val comparisonBase = cells[INDEX_ZERO]
        return (INDEX_ONE until cells.size).all { isPlayerValuesAreSame(comparisonBase, cells, it) }
    }

    fun isFull(cells: Array<Array<Cell>>): Boolean {
        cells.forEach { row ->
            row.forEach { cell ->
                when {
                    isPlayerValueEmpty(cell) -> return false
                }
            }
        }
        return true
    }

    private fun isPlayerValueEmpty(cell: Cell) = cell.isEmptyCell
    private fun isPlayerValuesAreSame(comparisonBase: Cell, cells: Array<out Cell>, index: Int) =
        getPlayerValue(comparisonBase).equals(getPlayerValue(cells[index]))

    private fun isPlayerValueIsEmpty(cell: Cell) = getPlayerValue(cell).isNullOrEmpty()
    private fun isEmptyCell(cells: Array<out Cell>) = cells.isEmpty()
    private fun getPlayerValue(comparisonBase: Cell) = comparisonBase.player?.value
}