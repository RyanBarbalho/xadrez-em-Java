package tabuleiro;

public abstract class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        // nao foi colocada no tabuleiro ainda
        position = null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    public boolean possibleMove(Position position) {
        // hook method
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            // se houver pelo menos um movimento possivel
            // para a peca, retorna true
            for (int j = 0; j < mat.length; j++) {
                // se houver pelo menos um movimento possivel para a peca
                if (mat[i][j]) {
                    return true;
                }
            }

        }
        return false;
    }

}
