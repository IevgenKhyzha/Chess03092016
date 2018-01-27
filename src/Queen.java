
public class Queen extends AbstractFigure {

    Queen(String name, String color) {
        super(name, color);
    }

    @Override
    public boolean move(AbstractFigure object, AbstractFigure[][] board, int i, int j, int toI, int toJ) {

            for (int y = 1; y <= 7; y++){
                if ((i == toI - y && j == toJ || i == toI - y && j == toJ + y || i == toI && j == toJ + y || i == toI + y && j == toJ + y || i == toI + y && j == toJ || i == toI + y && j == toJ - y || i == toI && j == toJ - y || i == toI - y && j == toJ - y)) {
                    if (i > toI && j < toJ) {
                        boolean find = false;
                        for (int r = 1; r < i - toI; r++) {
                            if (!board[i - r][j + r].getName().equals("empty")){
                                find = true;
                                break;
                            }
                        }
                        return !find;
                    }
                    if (i < toI && j < toJ) {
                        boolean find = false;
                        for (int r = 1; r < toI - i; r++) {
                            if (!board[i + r][j + r].getName().equals("empty")){
                                find = true;
                                break;
                            }
                        }
                        return !find;
                    }
                    if (i < toI && j > toJ) {
                        boolean find = false;
                        for (int r = 1; r < toI - i; r++) {
                            if (!board[i + r][j - r].getName().equals("empty")){
                                find = true;
                                break;
                            }
                        }
                        return !find;
                    }
                    if (i > toI && j > toJ) {
                        boolean find = false;
                        for (int r = 1; r < i - toI; r++) {
                            if (!board[i - r][j - r].getName().equals("empty")){
                                find = true;
                                break;
                            }
                        }
                        return !find;
                    }
                    if (i - toI == 0) {

                        if (j > toJ){
                            boolean find = false;
                            for (int r = j - 1; r > toJ; r--) {
                                if (!board[i][r].getName().equals("empty")){
                                    find = true;
                                    break;
                                }
                            }
                            return !find;
                        }
                        if (j < toJ){
                            boolean find = false;
                            for (int r = j + 1; r < toJ; r++) {
                                if (!board[i][r].getName().equals("empty")){
                                    find = true;
                                    break;
                                }
                            }
                            return !find;
                        }
                    }
                    if (j - toJ == 0) {
                        if (i > toI){
                            boolean find = false;
                            for (int r = i - 1; r > toI; r--) {//прохождение по списку
                                if (!board[r][j].getName().equals("empty")){//запись элементов пустая или нет
                                    find = true;
                                    break;
                                }
                            }
                            return !find;
                        }
                        if (i < toI){
                            boolean find = false;
                            for (int r = i + 1; r < toI; r++) {
                                if (!board[r][j].getName().equals("empty")){
                                    find = true;
                                    break;
                                }
                            }
                            return !find;
                        }
                    }
                }
            }
        return false;
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
    public String getColor() { return color; }

    @Override
    public boolean getFirstStep() {
        return false;
    }

    @Override
    public void changeFirstStep() {

    }
}
