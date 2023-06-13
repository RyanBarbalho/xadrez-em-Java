package app;

import tabuleiro.Position;
import tabuleiro.Board;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true) {
            Ui.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("source: ");
            ChessPosition source = Ui.readChessPosition(sc);
            System.out.println();
            System.out.println("Target: ");
            ChessPosition target = Ui.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }

    }
}
