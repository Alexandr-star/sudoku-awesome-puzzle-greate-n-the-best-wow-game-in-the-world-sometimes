package com.app.game.sudoku.ui.gameboard

import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.back.Cell
import com.app.game.sudoku.database.GameStatDatabase
import com.app.game.sudoku.databinding.FragmentGameboardBinding
import com.app.game.sudoku.ui.gameboard.GameboardView.OnTouchListener
import kotlinx.android.synthetic.main.fragment_gameboard.*

class GameboardFragment : Fragment(), OnTouchListener {
    private lateinit var gameboardViewModel: GameboardViewModel

    private lateinit var numberButtons: List<View>

    private lateinit var levelGame: String
    private var modeGame: Int = -1

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentGameboardBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_gameboard, container, false)

        binding.root.findViewById<GameboardView>(R.id.gameBoardView).registerListener(this)

        val chronometer = binding.root.findViewById<Chronometer>(R.id.timerChron)

        val dao = GameStatDatabase.getInstance(inflater.context).getGameStatDatabaseDao()
        val gameboardViewModelFactory = GameboardViewModelFactory(dao)
        gameboardViewModel = ViewModelProvider(this, gameboardViewModelFactory)
            .get(GameboardViewModel::class.java)

        val level = arguments!!.getInt("level")
        val mode = arguments!!.getInt("mode")
        initlevelandmode(level, mode)

        gameboardViewModel.function(levelGame, modeGame)
        gameboardViewModel.game.secondsUntil.observe(viewLifecycleOwner, Observer {secondsUntilEnd ->
            binding.timerChron.text = DateUtils.formatElapsedTime(secondsUntilEnd)
        })
        gameboardViewModel.game.selectedCellLiveData.observe( viewLifecycleOwner, Observer { updateSelectedCellUI(it) })
        gameboardViewModel.game.cellsLiveData.observe(viewLifecycleOwner, Observer { updateCells(it) })
        gameboardViewModel.game.timerOn(chronometer)
        binding.game = gameboardViewModel.game
        gameboardViewModel.game.mistakesCountLiveData.observe(viewLifecycleOwner, Observer { missCount ->
            binding.mistakesTextView.text = missCount
        })


        gameboardViewModel.game.eventGameFinish.observe(viewLifecycleOwner, Observer {finished ->
            if (finished) {
                gameFinished()
            }
        })

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
            button.setOnClickListener{
                gameboardViewModel.game.handleInput(index + 1)
                gameboardViewModel.game.mistakesLiveData.observe(viewLifecycleOwner, Observer { updateCellsWithMistakes(it) })
            }
        }
//        binding.notesButton.setOnClickListener{gameboardViewModel.game.changeNoteTakingState()}
        binding.deleteButton.setOnClickListener {gameboardViewModel.game.deleteNumInCell()}

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun gameFinished() {
        //gameboardViewModel.game.stopTimer()
        gameboardViewModel.onStartTracking()
        val bundleData = Bundle()
        var endGameStatus = if (gameboardViewModel.game.isWinGame.value!!) "win" else "lose"
        bundleData.putString("gameStatus", endGameStatus)
        Log.i("Game Finish", "${endGameStatus}")
        this.findNavController().navigate(
            R.id.action_navigation_gameboard_to_navigation_end,
            bundleData
        )
    }

    private fun gamePause() {

    }

    private fun updateCellsWithMistakes(mistakeCell: Cell) {
        gameBoardView.updateMistakesCells(mistakeCell)
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

    private fun initlevelandmode(level: Int, mode: Int) {
        when(level) {
            1 -> levelGame = "easy"
            2 -> levelGame = "medium"
            3 -> levelGame = "hard"
        }
        modeGame = mode
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("GameBoardFragment", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("GameBoardFragment", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i("GameBoardFragment", "onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.i("GameBoardFragment", "onPause")
        gameboardViewModel.game.pauseGame()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

}
