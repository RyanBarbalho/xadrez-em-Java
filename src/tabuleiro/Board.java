package tabuleiro;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Erro ao criar tabuleiro: é necessário que haja pelo menos 1 linha e 1 coluna");
        }
        this.rows = rows;
        this.columns = columns;
        // matriz de pecas
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column) {
        // se a posicao nao existe
        if (!positionExists(row, column)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return pieces[row][column];
    }

    // sobrecarga
    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("Já existe uma peça na posição " + position);
        }
        // a peça na posicao dada vai ser iguala essa peça
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        if (piece(position) == null) {
            return null;
        } // peça que estiver na posiçao
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        // delegando a verificacao para o metodo sobrecarregado
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        // se a posicao nao existe
        if (!positionExists(position)) {
            throw new BoardException("Posição não existe no tabuleiro");
        }
        // se a peca na posicao dada for diferente de nulo
        return piece(position) != null;
    }
}
