package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import chess.pieces.King;
import chess.pieces.Rook;
import tabuleiro.Board;
import tabuleiro.Piece;
import tabuleiro.Position;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;

    private List<ChessPiece> piecesOnTheBoard;
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        // crio tabuleiro 8 por 8
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnTheBoard = new ArrayList<>();
        check = false;
        // inicio o setup
        initialSetup();

    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
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

    // funcao responsavel por executar o movimento (verifica se a posicao de origem
    // e destino sao validas)
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition(); // converte a posicao de origem
        Position target = targetPosition.toPosition(); // converte a posicao de destino
        validateSourcePosition(source); // se nao existir, lança uma excecao
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target); // faz o movimento

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Voce nao pode se colocar em check");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false; // verifica se a partida esta em check
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;

    }

    // funcao responsavel por mover a peca
    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source); // removo peca na posicao de origem
        p.increaseMoveCount(); // incremento o contador de movimentos da peca
        Piece capturedPiece = board.removePiece(target); // remove a possivel peca da posicao de destino
        board.placePiece(p, target);// coloca a peca na posicao de destino

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target); // remove a peca da posicao de destino
        board.placePiece(p, source); // coloca a peca na posicao de origem
        p.decreaseMoveCount(); // decrementa o contador de movimentos da peca
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target); // coloca a peca capturada na posicao de destino
            capturedPieces.remove(capturedPiece); // remove a peca da lista de pecas capturadas
            piecesOnTheBoard.add((ChessPiece) capturedPiece); // adiciona a peca na lista de pecas do tabuleiro
        }
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

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {// funcao responsavel por retornar o rei da cor
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("Nao existe o rei da cor " + color + " no tabuleiro");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == opponent(color))
                .collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {// se a posicao do rei estiver na matriz de
                                                                       // movimentos possiveis
                return true; // (rei em check)
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {// se for um movimento possivel
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);// pega a posicao de destino
                        Piece capturedPiece = makeMove(source, target);// movimento feito para testar se continuara em
                                                                       // check
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);// desfaz o movimento feito para TESTAR
                        if (!testCheck) {// tirou o rei do check?
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        // coluna e linha do xadrez
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        // adiciona a peca na lista de pecas do tabuleiro
        piecesOnTheBoard.add(piece);
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
