
public class EmptyPlace extends AbstractFigure {


    EmptyPlace(String name, String color) {
        super(name, color);
    }

    @Override
    public boolean move(AbstractFigure object, AbstractFigure[][] board, int i, int j, int toI, int toJ) {
        return false;
    }

    @Override
    public void setPosition(int i, int j) {

    }

    @Override
    public int getPositionI() {
        return 0;
    }

    @Override
    public int getPositionJ() {
        return 0;
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
