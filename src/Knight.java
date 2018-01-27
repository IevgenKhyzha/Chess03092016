
public class Knight extends AbstractFigure{

    Knight(String name, String color) {
        super(name, color);
    }

    @Override
    public boolean move(AbstractFigure object, AbstractFigure[][] board, int i, int j, int toI, int toJ) {
        return i == toI - 2 && j == toJ - 1 || i == toI - 2 && j == toJ + 1 || i == toI - 1 && j == toJ + 2 || i == toI + 1 && j == toJ + 2 || i == toI + 2 && j == toJ + 1 || i == toI + 2 && j == toJ - 1 || i == toI + 1 && j == toJ - 2 || i == toI - 1 && j == toJ - 2;
    }

    @Override
    public void setPosition(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public int getPositionI() {
        return i;
    }

    @Override
    public int getPositionJ() {
        return j;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean getFirstStep() {
        return false;
    }

    @Override
    public void changeFirstStep() {

    }
}
