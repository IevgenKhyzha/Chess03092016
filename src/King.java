
public class King extends AbstractFigure {

    boolean shah = false;
    boolean mat = false;
    boolean pat = false;
    boolean firstStep = false;
    boolean firstChack = false;
    boolean castle = false;

    King(String name, String color) {
        super(name, color);
    }

    @Override
    public boolean move(AbstractFigure object, AbstractFigure[][] board, int i, int j, int toI, int toJ) {
        if (i == toI - 1 && j == toJ || i == toI - 1 && j == toJ + 1 || i == toI && j == toJ + 1 || i == toI + 1 && j == toJ + 1 || i == toI + 1 && j == toJ || i == toI + 1 && j == toJ - 1 || i == toI && j == toJ - 1 || i == toI - 1 && j == toJ - 1){
            return true;
        }
        if (i == toI && j == toJ - 2 && !castle){
            return true;
        }
        if (i == toI && j == toJ + 2 && !castle){
            return true;
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
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
    public String getColor() {
        return color;
    }

    public void changeFirstStep(){
        firstStep = !firstStep;
    }

    public void changeFirstChack(){
        firstChack = !firstChack;
    }

    public void changeCastle(){
        castle = !castle;
    }

    @Override
    public boolean getFirstStep() {
        return firstStep;
    }
}


