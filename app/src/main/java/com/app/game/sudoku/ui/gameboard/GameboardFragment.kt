package com.app.game.sudoku.ui.gameboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.game.sudoku.R
import com.app.game.sudoku.back.Cell
import com.app.game.sudoku.databinding.FragmentGameboardBinding
import com.app.game.sudoku.ui.gameboard.GameboardView.OnTouchListener
import kotlinx.android.synthetic.main.fragment_gameboard.*
import kotlinx.android.synthetic.main.fragment_gameboard.view.*

class GameboardFragment : Fragment(), OnTouchListener {
    private lateinit var gameboardViewModel: GameboardViewModel
    private lateinit var numberButtons: List<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentGameboardBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_gameboard, container, false)

       binding.root.findViewById<GameboardView>(R.id.gameBoardView).registerListener(this)

        gameboardViewModel = ViewModelProvider(this).get(GameboardViewModel::class.java)

        gameboardViewModel.game.selectedCellLiveData.observe( viewLifecycleOwner, Observer { updateSelectedCellUI(it) })
        gameboardViewModel.game.cellsLiveData.observe(viewLifecycleOwner, Observer { updateCells(it) })
//        gameboardViewModel.game.takingNotesLiveData.observe(viewLifecycleOwner, Observer { updateNoteTakingUI(it) })
//        gameboardViewModel.game.highlightedKeysLiveData.observe(viewLifecycleOwner, Observer {updateHighlightedKeys(it) })

        numberButtons = listOf(
            binding.buttonsLayout.get(0),
            binding.buttonsLayout.get(1),
            binding.buttonsLayout.get(2),
            binding.buttonsLayout.get(3),
            binding.buttonsLayout.get(4),
            binding.buttonsLayout.get(5),
            binding.buttonsLayout.get(6),
            binding.buttonsLayout.get(7),
            binding.buttonsLayout.get(8)
        )
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener{gameboardViewModel.game.handleInput(index + 1)}
        }
//        binding.notesButton.setOnClickListener{gameboardViewModel.game.changeNoteTakingState()}
        binding.deleteButton.setOnClickListener {gameboardViewModel.game.deleteNumInCell()}

        return binding.root
    }

//    private fun updateHighlightedKeys(set: Set<Int>?) = set?.let {
//        numberButtons.forEachIndexed { index, button ->
//            val color = if (set.contains(index + 1)) ContextCompat.getColor(button.context, R.color.colorPrimary) else Color.LTGRAY
//            button.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
//        }
//    }
//
//    private fun updateNoteTakingUI(isNoteTaking: Boolean?) = isNoteTaking?.let {
//        val color = if (it) ContextCompat.getColor(notesButton.context, R.color.colorPrimary) else Color.LTGRAY
//        notesButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
//
//    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        gameBoardView.updateCells(cells)

    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        gameBoardView.updateSelectedCellUI(cell.first, cell.second)
    }



    override fun onCellTouched(row: Int, col: Int) {
        gameboardViewModel.game.updateSelectedCell(row, col)
    }

}