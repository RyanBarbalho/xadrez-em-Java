package app;

import tabuleiro.Position;
import tabuleiro.Board;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (true) {
            try {
                Ui.clearScreen();
                Ui.printMatch(chessMatch, captured);
                System.out.println();
                System.out.println("source: ");
                ChessPosition source = Ui.readChessPosition(sc);
                // printar os movimentos possiveis da pessa de origem (source)
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                Ui.clearScreen();
                Ui.printBoard(chessMatch.getPieces(), possibleMoves); // essa funcao sobrecarregada printa os movimentos
                                                                      // possiveis da peca de origem

                System.out.println();
                System.out.println("Target: ");
                ChessPosition target = Ui.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

    }
}
