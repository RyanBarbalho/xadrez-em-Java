package chess.pieces;

import chess.ChessPiece;
import chess.Color;
import tabuleiro.Board;
import tabuleiro.Position;

public class Pawn extends ChessPiece {

    public Pawn(Board board, chess.Color color) {
        super(board, color);
        // TODO Auto-generated constructor stub
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
        }
        return matriz;
    }

    @Override
    public String toString() {
        return "P";
    }
}
