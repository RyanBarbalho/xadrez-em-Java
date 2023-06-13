package app;

import tabuleiro.Position;
import tabuleiro.Board;
import chess.ChessMatch;

public class App {
    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();
        Ui.printBoard(chessMatch.getPieces());
    }
}
