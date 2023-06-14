package chess.pieces;

import chess.ChessPiece;
import chess.Color;
import tabuleiro.Board;
import tabuleiro.Position;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        // matriz de movimentos possiveis, todos nulos.
        boolean[][] matriz = new boolean[getBoard().getRows()][getBoard().getColumns()];
        // posicao da peca atual
        Position p = new Position(0, 0);
        // noroeste
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }
        // nordeste
        p.setValues(position.getRow() + -1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }
        // sudeste
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }
        // sudoeste
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matriz[p.getRow()][p.getColumn()] = true;
        }

        return matriz;
    }

}
