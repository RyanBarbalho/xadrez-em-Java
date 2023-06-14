package app;

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

        while (!chessMatch.getCheckMate()) {// enquanto nao for checkmate
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

                if (chessMatch.getPromoted() != null) {
                    System.out.println("Enter piece for promotion (B/N/R/Q): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.print("Valor invalido, digite novamente (B/N/R/Q): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        Ui.clearScreen();
        Ui.printMatch(chessMatch, captured);
    }

}
