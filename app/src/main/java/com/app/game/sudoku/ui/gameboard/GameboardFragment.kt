package com.app.game.sudoku.ui.gameboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.back.Board
import com.app.game.sudoku.back.Cell
import com.app.game.sudoku.databinding.FragmentGameboardBinding
import com.app.game.sudoku.statisticStore.StatisticPreference
import com.app.game.sudoku.ui.gameboard.GameboardView.OnTouchListener
import kotlinx.android.synthetic.main.fragment_gameboard.*

class GameboardFragment : Fragment(), OnTouchListener {
    private lateinit var gameboardViewModel: GameboardViewModel
    private var isComplite = false
    private lateinit var numberButtons: List<View>

    private lateinit var levelGame: String
    private var modeGame: Int = -1

    private val CLASSIC_MODE = 1
    private val TIME_MODE = 2

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

        gameboardViewModel = ViewModelProvider(this)
            .get(GameboardViewModel::class.java)

        if (savedInstanceState != null)
            restoreGame(savedInstanceState)
        else {
            val level = arguments!!.getInt("level")
            val mode = arguments!!.getInt("mode")
            initlevelandmode(level, mode)
            gameboardViewModel.function(levelGame, modeGame)
        }

        gameboardViewModel.game._secondsUntil.observe(viewLifecycleOwner, Observer {secondsUntilEnd ->
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
        binding.deleteButton.setOnClickListener {gameboardViewModel.game.deleteNumInCell()}

        setHasOptionsMenu(true)


        return binding.root
    }

    private fun gameFinished() {
        //gameboardViewModel.game.stopTimer()
        saveStaticticInPreference()
        if (gameboardViewModel.game._isWinGame) {
            redirectToEndFragment("won")
        } else {
            redirectToEndFragment("lose")
        }


    }

    private fun saveStaticticInPreference() {
        if (gameboardViewModel.game.mode == CLASSIC_MODE) {
            when(gameboardViewModel.game.level) {
                "easy" -> StatisticPreference.classicModeEasy =
                    addStatisticInPreference(StatisticPreference.classicModeEasy, CLASSIC_MODE)
                "medium" -> StatisticPreference.classicModeMedium =
                    addStatisticInPreference(StatisticPreference.classicModeMedium, CLASSIC_MODE)
                "hard" -> StatisticPreference.classicModeHard =
                    addStatisticInPreference(StatisticPreference.classicModeHard, CLASSIC_MODE)
            }
        } else if (gameboardViewModel.game.mode == TIME_MODE) {
            when(gameboardViewModel.game.level) {
                "easy" -> StatisticPreference.timeModeEasy =
                    addStatisticInPreference(StatisticPreference.timeModeEasy, TIME_MODE)
                "medium" -> StatisticPreference.timeModeMedium =
                    addStatisticInPreference(StatisticPreference.timeModeMedium, TIME_MODE)
                "hard" -> StatisticPreference.timeModeHard =
                    addStatisticInPreference(StatisticPreference.timeModeHard, TIME_MODE)
            }
        }
    }

    private fun addStatisticInPreference(statistic: String, mode: Int): String {
        var stat = statistic
        Log.i("StartString", "${stat}")
        var games = stat.substringBefore("/").toInt()
        var winsStr = stat.substringAfter("/")
        var wins = winsStr.substringBeforeLast("/").toInt()
        var time = stat.substringAfterLast("/").toLong()
        Log.i("games", "${games}")
        Log.i("wins", "${wins}")
        Log.i("time", "${time}")

        games++
        Log.i("games", "${games}")

        if (gameboardViewModel.game._isWinGame) {
            var curTime = 0L
            wins++
            when(mode) {
                TIME_MODE -> {
                    curTime = gameboardViewModel.game.GAME_TIMEDOWN - gameboardViewModel.game._secondsUntil.value!!
                    Log.i("time timeMode", "${curTime}")
                }
                else -> {
                    curTime = gameboardViewModel.game._secondsUntil.value!!
                    Log.i("time", "${curTime}")
                }
            }

            if (curTime <= time || time == 0L) {
                return "${games}/${wins}/${curTime}"
            } else {
                return "${games}/${wins}/${time}"
            }
        } else {
            return "${games}/${wins}/${time}"
        }
    }




    private fun redirectToEndFragment(endGameStatus: String) {
        if (!isComplite) {
            val bundleData = Bundle()
            bundleData.putString("gameStatus", endGameStatus)
            Log.i("Game Finish", "${endGameStatus}")
            this.findNavController().navigate(
                R.id.action_navigation_gameboard_to_navigation_end,
                bundleData
            )
            isComplite = true
        }

    }

    private fun updateCellsWithMistakes(mistakeCell: Cell) {
        gameBoardView.updateMistakesCells(mistakeCell)
    }

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
        gameboardViewModel.game.stopTimer()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("level_key", gameboardViewModel.game.level)
        outState.putInt("mode_key", gameboardViewModel.game.mode)
        outState.putInt("miss_key", gameboardViewModel.game.countMiss)
        outState.putLong("time_kay", gameboardViewModel.game._secondsUntil.value!!)
        val rowArray = IntArray(81)
        val colArray = IntArray(81)
        val valueArray = IntArray(81)
        val startCellArray = BooleanArray(81)
        for ((i, cell) in gameboardViewModel.game.board.cells.withIndex()) {
            rowArray[i] = cell.row
            colArray[i] = cell.col
            valueArray[i] = cell.value
            startCellArray[i] = cell.isStartingCell
        }
        outState.putIntArray("row_array_kay", rowArray)
        outState.putIntArray("col_array_kay", colArray)
        outState.putIntArray("value_array_kay", valueArray)
        outState.putBooleanArray("start_cell_array_kay", startCellArray)
        Log.i("GameBoardFragment", "save instance")


    }

    private fun restoreGame(savedInstanceState: Bundle) {
        gameboardViewModel.function(
            savedInstanceState.getString("level_key")!!,
            savedInstanceState.getInt("mode_key")
        )
        gameboardViewModel.game.countMiss = savedInstanceState.getInt("miss_key")

        val time = savedInstanceState.getLong("time_key")
        gameboardViewModel.game._secondsUntil.value = time
        val cells = List(81) { i ->
            Cell(
                savedInstanceState.getIntArray("row_array_key")!![i],
                savedInstanceState.getIntArray("col_array_key")!![i],
                savedInstanceState.getIntArray("value_array_kay")!![i],
                savedInstanceState.getBooleanArray("start_cell_array_kay")!![i]
            )
        }
        gameboardViewModel.game.board = Board(9, cells)
        Log.i("GameBoardFragment", "restore instance")

    }
}
