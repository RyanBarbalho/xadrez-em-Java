package chess.pieces;

import chess.ChessPiece;
import chess.Color;
import tabuleiro.Board;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];

        return matriz;
    }

}
