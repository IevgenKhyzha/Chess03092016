
public class Pawn extends AbstractFigure {

    Pawn(String name, String color) {
        super(name, color);
    }

    @Override
    public boolean move(AbstractFigure object, AbstractFigure[][] board, int i, int j, int toI, int toJ) throws NullPointerException {

        if (object.getColor().equals("white") && board[toI][toJ].getName().equals("empty") && i == toI + 1 && j == toJ) {
            if (!firstStep) {
                changeFirstStep();
            }
            return true;
        }
        if (object.getColor().equals("white") && board[toI][toJ].getName().equals("empty") && i == toI + 2 & j == toJ && !firstStep && board[toI + 1][j].getName().equals("empty")) {//пешка попыталась побить в конце доски белую фигуру
            changeFirstStep();
            return true;
        }
        if (object.getColor().equals("black") && board[toI][toJ].getName().equals("empty") && i == toI - 1 && j == toJ) {
            if (!firstStep) {
                changeFirstStep();
            }
            return true;
        }
        if (object.getColor().equals("black") && board[toI][toJ].getName().equals("empty") && i == toI - 2 && j == toJ && !firstStep && board[toI - 1][j].getName().equals("empty")) {
            changeFirstStep();
            return true;
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (object.getColor().equals("white") && !board[toI][toJ].getName().equals("empty") && i == toI + 1 && j == toJ + 1){
            if (!firstStep) {
                changeFirstStep();
            }
            return true;
        }
        if (object.getColor().equals("white") && !board[toI][toJ].getName().equals("empty") && i == toI + 1 && j == toJ - 1){
            if (!firstStep) {
                changeFirstStep();
            }
            return true;
        }
        if (object.getColor().equals("black") && !board[toI][toJ].getName().equals("empty") && i == toI - 1 && j == toJ + 1) {
            if (!firstStep) {
                changeFirstStep();
            }
            return true;
        }
        if (object.getColor().equals("black") && !board[toI][toJ].getName().equals("empty") && i == toI - 1 && j == toJ - 1) {
            if (!firstStep) {
                changeFirstStep();
            }
            return true;
        }
        return false;
    }
}
