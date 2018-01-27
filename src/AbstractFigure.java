

public abstract class AbstractFigure {

    protected String color;
    protected String name;
    protected int i, j;
    protected boolean firstStep = false;// changes 03.04.2017

    AbstractFigure(String name, String color){
           this.name = name;
           this.color = color;
    }

    public abstract boolean move(AbstractFigure object, AbstractFigure[][] board, int i, int j, int toI, int toJ) throws NullPointerException;

    public void setPosition(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getPositionI() {
        return i;
    }

    public int getPositionJ() {
        return j;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public boolean getFirstStep(){
        return firstStep;
    }

    public void changeFirstStep(){
        firstStep = !firstStep;
    }
}
