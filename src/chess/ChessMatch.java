package chess;

import chess.pieces.King;
import chess.pieces.Rook;
import tabuleiro.Board;
import tabuleiro.Position;

public class ChessMatch {
    private Board board;

    public ChessMatch() {
        // crio tabuleiro 8 por 8
        board = new Board(8, 8);
        // inicio o setup
        initialSetup();

    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] matriz = new ChessPiece[board.getRows()][board.getColumns()];
        // percorre a matriz de pecas do tabuleiro
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                // downcasting de Piece para ChessPiece
                matriz[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        // retorna a matriz de pecas do xadrez
        return matriz;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        // coluna e linha do xadrez
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('b', 6, new Rook(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
    }
}
