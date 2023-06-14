package chess.pieces;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;
import tabuleiro.Board;
import tabuleiro.Position;

public class Pawn extends ChessPiece {

    private ChessMatch chessmatch;

    public Pawn(Board board, chess.Color color, ChessMatch chessmatch) {
        super(board, color);
        this.chessmatch = chessmatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                matriz[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                matriz[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matriz[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matriz[p.getRow()][p.getColumn()] = true;
            }

            // #specialmove en passant white
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessmatch.getEnPassantVulnerable()) {
                    matriz[left.getRow() - 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessmatch.getEnPassantVulnerable()) {
                    matriz[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        } else {
            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                matriz[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 2, position.getColumn());
            Position p2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p)
                    && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                matriz[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matriz[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                matriz[p.getRow()][p.getColumn()] = true;
            }

            // #specialmove en passant black
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left)
                        && getBoard().piece(left) == chessmatch.getEnPassantVulnerable()) {
                    matriz[left.getRow() + 1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right)
                        && getBoard().piece(right) == chessmatch.getEnPassantVulnerable()) {
                    matriz[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }
}
