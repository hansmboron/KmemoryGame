package site.hansmboron.mymemory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import site.hansmboron.mymemory.models.BoardSize
import site.hansmboron.mymemory.models.MemoryCard
import site.hansmboron.mymemory.models.MemoryGame
import site.hansmboron.mymemory.utils.DEFAULT_ICONS

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var rvBoard: RecyclerView
    private lateinit var clRoot: ConstraintLayout
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView
    private lateinit var memoryGame: MemoryGame
    private lateinit var adapter: MemoryBoardAdapter

    private var boardSize: BoardSize = BoardSize.EASY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        clRoot = findViewById(R.id.clRoot)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)

        memoryGame = MemoryGame(boardSize)

         adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener {
            override fun onCardClicked(position: Int) {
                updateGameWithFlip(position)
            }
        })

        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth() )
    }

    private fun updateGameWithFlip(position: Int) {
        // error checking
        if (memoryGame.haveWonGame()) {
            Snackbar.make(clRoot, "Você já ganhou!", Snackbar.LENGTH_LONG).show()
            return
        }
        if (memoryGame.isCardFaceUp(position)) {
            Snackbar.make(clRoot, "Movimento Inválido!", Snackbar.LENGTH_LONG).show()
            return
        }


        if (memoryGame.flipCard(position)) {
            Snackbar.make(clRoot, "Encontrado!!!  Pares: ${memoryGame.numPairsFound}", Snackbar.LENGTH_SHORT).show()
        }
        adapter.notifyDataSetChanged()
    }
}