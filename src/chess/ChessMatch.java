package chess;

import chess.pieces.King;
import chess.pieces.Rook;
import tabuleiro.Board;
import tabuleiro.Piece;
import tabuleiro.Position;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentPlayer;

    public ChessMatch() {
        // crio tabuleiro 8 por 8
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        // inicio o setup
        initialSetup();

    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    // funcao responsavel por executar o movimento
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition(); // converte a posicao de origem
        Position target = targetPosition.toPosition(); // converte a posicao de destino
        validateSourcePosition(source); // se nao existir, lança uma excecao
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target); // faz o movimento
        nextTurn();
        return (ChessPiece) capturedPiece;

    }

    // funcao responsavel por mover a peca
    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source); // peca na posicao de origem vai pra posicao de destino
        Piece capturedPiece = board.removePiece(target); // remove a possivel peca da posicao de destino
        board.placePiece(p, target);// coloca a peca na posicao de destino
        return capturedPiece;
    }

    // funcao responsavel por validar a posicao de origem
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("Nao ha nenhuma peca na posiçao de origem");
        } // downcasting de piece para ChessPiece
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("A peca escolhida nao e sua");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("Nao ha movimentos possiveis para a peca escolhida");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("A peca escolhida nao pode se mover para a posicao de destino");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        // coluna e linha do xadrez
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    // funcao responsavel por passar o turno
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initialSetup() {
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
