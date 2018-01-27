

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

public class ChessBoard {

    static boolean hod = true;
    static int hodi;
    static int hodj;
    static int hodtoi;
    static int hodtoj;
    static int countChack;
    static Color kingPlaceColor;
    static boolean pawnCheking = false;
    static boolean stopListener = false;
    static boolean upsidedown = false;
    static boolean upsidedownPosibility = true;
    static Board board = new Board();
    static GUIChessBoard guiBoard = new GUIChessBoard();
    static ChackingFigure checkingFigure = new ChackingFigure();
    static ChooseWhiteFigureInsteadThePawn chooseWhiteFigureInsteadThePawn = new ChooseWhiteFigureInsteadThePawn();
    static ChooseBlackFigureInsteadThePawn chooseBlackFigureInsteadThePawn = new ChooseBlackFigureInsteadThePawn();

    public static void main(String[] args) throws IOException, NullPointerException {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                guiBoard.chessBoard();
            }
        });

        while (!board.wK.mat && !board.bK.mat) {
            printBoard();
            whatColorStep();
            checkChakh();
            printChack();
            checkPat();
            checkMat();
            if (board.wK.mat || board.bK.mat || board.wK.pat || board.bK.pat){
                break;
            }
            enterStepFromGui();

            changeUpsidedownPosibility();

            checkAndStep();

            changeUpsidedownPosibility();

            //
        }
        System.out.println("Leave While");
    }

    public static void checkAndStep() throws NullPointerException {
        if (board.board[hodi][hodj].getName().equals("empty")) {
            System.out.println("Вы ввели неправильное значение, ячейка пуста, попробуйте еще раз");
            return;
        }
        if (!checkColorBeforeStep()){
            System.out.println("Сейчас ходят фигуры другого цвета");
            return;
        }
        if (board.board[hodtoi][hodtoj].getName().equals("wKing") || board.board[hodtoi][hodtoj].getName().equals("bKing")) {
            System.out.println("Нельзя убить короля");
            return;
        }
        if (board.board[hodi][hodj].getColor().equals(board.board[hodtoi][hodtoj].getColor())){
            System.out.println("Фигура не может побить свою фигуру");
        }
        else {
            if(board.board[hodi][hodj].move(board.board[hodi][hodj], board.board, hodi,hodj,hodtoi,hodtoj) ){
                if (!board.board[hodtoi][hodtoj].equals(board.emptyPlace)) {
                    AbstractFigure figure = board.board[hodtoi][hodtoj];
                    board.board[hodtoi][hodtoj] = board.board[hodi][hodj];
                    board.board[hodi][hodj] = new EmptyPlace("empty","empty");
                    checkChakh();

                    if (returnChackValue()){
                        board.board[hodi][hodj] = board.board[hodtoi][hodtoj];
                        board.board[hodtoi][hodtoj] = figure;
                        return;
                    }
                    if (!returnChackValue()){
                        //Icon icon = guiBoard.buttons[hodi][hodj].getIcon();
                        //guiBoard.buttons[hodtoi][hodtoj].setIcon(icon);
                        //guiBoard.buttons[hodi][hodj].setIcon(null);
                        printBoard();
                        System.out.println("Фигура " + board.board[hodtoi][hodtoj].getName() + " " + board.board[hodtoi][hodtoj].getColor() + " побила фигуру " + board.board[hodi][hodj].getName() + " " + board.board[hodi][hodj].getColor());
                        checkTheEndOfTheWayOfThePawn();
                        changeVariableFirstStepsAfterEveryStep();
                    }
                    hod = !hod;
                    return;

                }
                if (board.board[hodtoi][hodtoj].equals(board.emptyPlace)){
                    //метод проверки, является ли ход рокировкой
                    //если не является, значит применять стандартные действия
                    //если является применить метод в соответствии с цветом и типом рокировки, проверки всех условий
                    //применяем метод checkChakh();
                    //если проходит проверку меняем местами на доске и меняем переменные на противоположные
                    if (!checkCastle()) {//если ход не рокировка
                        System.out.println("Check castle " + checkCastle());

                        AbstractFigure object = board.board[hodtoi][hodtoj];
                        board.board[hodtoi][hodtoj] = board.board[hodi][hodj];
                        board.board[hodi][hodj] = object;
                        checkChakh();

                        if (returnChackValue()) {
                            board.board[hodi][hodj] = board.board[hodtoi][hodtoj];
                            board.board[hodtoi][hodtoj] = object;
                            return;
                        }
                        if (!returnChackValue()) {
                            //Icon icon1 = guiBoard.buttons[hodi][hodj].getIcon();
                            //Icon icon2 = guiBoard.buttons[hodtoi][hodtoj].getIcon();
                            //guiBoard.buttons[hodtoi][hodtoj].setIcon(icon1);
                            //guiBoard.buttons[hodi][hodj].setIcon(icon2);
                            printBoard();
                            System.out.println(board.board[hodtoi][hodtoj] + " походил на " + board.board[hodi][hodj]);
                            checkTheEndOfTheWayOfThePawn();
                            changeVariableFirstStepsAfterEveryStep();
                        }
                        hod = !hod;
                        return;
                    }

                    if (checkCastle()){//если ход рокировка
                        System.out.println("Check castle " + checkCastle());

                        if (checkCastleConditions()){
                            AbstractFigure object = board.board[hodtoi][hodtoj];
                            board.board[hodtoi][hodtoj] = board.board[hodi][hodj];
                            board.board[hodi][hodj] = object;
                            System.out.println("Местами поменяло");
                            checkChakh();

                            if (returnChackValue()) {
                                board.board[hodi][hodj] = board.board[hodtoi][hodtoj];
                                board.board[hodtoi][hodtoj] = object;
                                return;
                            }
                            if (!returnChackValue()) {
                                //Icon icon1 = guiBoard.buttons[hodi][hodj].getIcon();
                                //Icon icon2 = guiBoard.buttons[hodtoi][hodtoj].getIcon();
                                //guiBoard.buttons[hodtoi][hodtoj].setIcon(icon1);
                                //guiBoard.buttons[hodi][hodj].setIcon(icon2);

                                moveRookForCastle();

                                changeVariablesKingAndRook();
                                printBoard();

                                System.out.println(board.board[hodi][hodj]);
                                System.out.println(board.board[hodtoi][hodtoj]);
                                System.out.println("Рокировка " + board.board[hodtoi][hodtoj] + " походил на " + board.board[hodi][hodj]);
                            }
                        }
                        else return;
                        hod = !hod;
                    }
                }

            }
            else {System.out.println("Эта фигура не может так походить");}
        }
        printChack();
    }

    public static void enterStep() throws IOException {
        System.out.println("Введите команду через тире");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String read = reader.readLine();
        String[] array = read.split(" ");
        if (array[0].equals("a")) {
            hodj = 0;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("b")) {
            hodj = 1;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("c")) {
            hodj = 2;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("d")) {
            hodj = 3;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("e")) {
            hodj = 4;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("f")) {
            hodj = 5;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("g")) {
            hodj = 6;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }
        if (array[0].equals("h")) {
            hodj = 7;
            hodi = Integer.parseInt(array[1]) - 1;
            if (array[2].equals("a")) {
                hodtoj = 0;
            }
            if (array[2].equals("b")) {
                hodtoj = 1;
            }
            if (array[2].equals("c")) {
                hodtoj = 2;
            }
            if (array[2].equals("d")) {
                hodtoj = 3;
            }
            if (array[2].equals("e")) {
                hodtoj = 4;
            }
            if (array[2].equals("f")) {
                hodtoj = 5;
            }
            if (array[2].equals("g")) {
                hodtoj = 6;
            }
            if (array[2].equals("h")) {
                hodtoj = 7;
            }
            hodtoi = Integer.parseInt(array[3]) - 1;
        }

    }

    public static void printBoard() {
        if (!upsidedown) {
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    if (board.board[i][j].getClass().getName().equals("Pawn")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            //System.out.print("[ WPawn ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconPawnWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            //System.out.print("[ BPawn ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconPawnBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("King")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            //System.out.print("[ WKing ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconKingWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconKingBlack);
                            //System.out.print("[ BKing ]");
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Queen")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            //System.out.print("[WQueen ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconQueenWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            //System.out.print("[BQueen ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconQueenBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Bishop")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconBishopWhite);
                            //System.out.print("[WBishop]");
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            //System.out.print("[BBishop]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconBishopBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Knight")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            //System.out.print("[WKnight]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconKnightWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            //System.out.print("[BKnight]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconKnightBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Rook")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            //System.out.print("[ WRook ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconRookWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            //System.out.print("[ BRook ]");
                            guiBoard.buttons[i][j].setIcon(guiBoard.iconRookBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("EmptyPlace")) {
                        //System.out.print("[       ]");
                        guiBoard.buttons[i][j].setIcon(null);
                    }
                }
                //System.out.println();
            }
        }
        if (upsidedown) {
            for (int i = 0; i <= 7; i++) {
                for (int j = 0; j <= 7; j++) {
                    if (board.board[i][j].getClass().getName().equals("Pawn")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconPawnWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconPawnBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("King")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconKingWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconKingBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Queen")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconQueenWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconQueenBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Bishop")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconBishopWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[7 -  i][7 - j].setIcon(guiBoard.iconBishopBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Knight")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconKnightWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[7 -  i][7 - j].setIcon(guiBoard.iconKnightBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("Rook")) {
                        if (board.board[i][j].getColor().equals("white")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconRookWhite);
                        }
                        if (board.board[i][j].getColor().equals("black")) {
                            guiBoard.buttons[7 - i][7 - j].setIcon(guiBoard.iconRookBlack);
                        }
                    }
                    if (board.board[i][j].getClass().getName().equals("EmptyPlace")) {
                        guiBoard.buttons[7 - i][7 - j].setIcon(null);
                    }
                }
            }
        }
    }

    public  static void changeUpsidedownPosibility(){
        upsidedownPosibility = !upsidedownPosibility;
    }

    public static boolean checkColorBeforeStep(){
        if (hod & board.board[hodi][hodj].getColor().equals("white")){
            return true;
        }
        if (!hod & board.board[hodi][hodj].getColor().equals("black")){
            return true;
        }
        if (board.board[hodi][hodj].getColor().equals("empty")){
            return false;
        }
        return false;
    }

    public static void enterStepFromGui(){

        while (true) {
           if (!Listener.secondPlace) {
               try {
                   Thread.sleep(100);
               } catch (InterruptedException e) {
               }
           }
           else {
               Listener.secondPlace = false;
               return;
           }
       }
    }

    public static void whatColorStep(){
        if (hod){
            System.out.println("Ходят белые фигуры");
        }
        if (!hod){
            System.out.println("Ходят черные фигуры");
        }
    }

    public static void checkChakh(){
        if(hod){
            findWhiteKing();
            checkWhiteKingChakh();
        }
        if(!hod){
            findBlackKing();
            checkBlackKingChakh();
        }
    }

    public static void checkMat(){
        if (board.wK.shah){
            checkWhiteKingMat();
        }
        if (board.bK.shah){
            checkBlackKingMat();
        }
    }

    public static void findWhiteKing(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.board[i][j].getName().equals("wKing")){
                    board.wK.setPosition(i, j);
                    return;
                }
            }
        }
    }

    public static void findBlackKing(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (board.board[i][j].getName().equals("bKing")){
                    board.bK.setPosition(i, j);
                    return;
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void checkWhiteKingChakh(){

        for (int i = board.wK.getPositionI() - 1; i >= 0; i--){

            int j = board.wK.getPositionJ();

            if (i == board.wK.getPositionI() - 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("black") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Белому королю шах от черного короля вверху");
                board.wK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Белому королю шах от фигуры прямо");
                board.wK.shah = true;
                System.out.println("Координаты фигуры " + i + " " + j);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        for (int j = board.wK.getPositionJ() + 1; j <= 7; j++){

            int i = board.wK.getPositionI();

            if (j == board.wK.getPositionJ() + 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("black") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Белому королю шах от черного короля справа");
                board.wK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                 !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))   ){
                break;
            }

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Белому королю шах от фигуры справа");
                board.wK.shah = true;
                System.out.println("Координаты фигуры " + i + " " + j);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        for (int i = board.wK.getPositionI() + 1; i <= 7; i++){

            int j = board.wK.getPositionJ();

            if (i == board.wK.getPositionI() + 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("black") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Белому королю шах от черного короля сзади");
                board.wK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Белому королю шах от фигуры сзади");
                board.wK.shah = true;
                System.out.println("Координаты фигуры " + i + " " + j);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        for (int j = board.wK.getPositionJ() - 1; j >= 0; j--){

            int i = board.wK.getPositionI();

            if (j == board.wK.getPositionJ() - 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("black") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Белому королю шах от черного короля слева");
                board.wK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Белому королю шах от фигуры слева");
                board.wK.shah = true;
                System.out.println("Координаты фигуры " + i + " " + j);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        //if (i > toI & j < toJ) {
        for (int r = 1; r < 8 - board.wK.getPositionJ() ; r++) {
            if (board.wK.getPositionI() - r >= 0 && board.wK.getPositionJ() + r <= 7){
                if (r == 1 && !board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("Pawn")){
                    System.out.println("Белому королю шах от черной пешки справа вверху");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() - r, board.wK.getPositionJ() + r);
                    return;
                }
                if (r == 1 && !board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("King")){
                    System.out.println("Белому королю шах от черного короля справа вверху");
                    board.wK.shah = true;
                    return;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Белому королю шах от фигуры справа вверху");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() - r, board.wK.getPositionJ() + r);
                    return;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j < toJ)
        for (int r = 1; r < 8 - board.wK.getPositionJ() ; r++) {
            if (board.wK.getPositionI() + r <= 7 && board.wK.getPositionJ() + r <= 7){
                if (r == 1 && !board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getClass().getName().equals("King")){
                    System.out.println("Белому королю шах от черного короля справа внизу");
                    board.wK.shah = true;
                    return;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Белому королю шах от фигуры справа внизу");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() + r, board.wK.getPositionJ() + r);
                    return;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j > toJ)
        for (int r = 1; r <= board.wK.getPositionJ(); r++) {
            if (board.wK.getPositionI() + r <= 7 && board.wK.getPositionJ() - r >= 0){
                if (r == 1 && !board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getClass().getName().equals("King")){
                    System.out.println("Белому королю шах от черного короля слева внизу");
                    board.wK.shah = true;
                    return;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Белому королю шах от фигуры слева внизу");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() + r, board.wK.getPositionJ() - r);
                    return;
                }
            }
            else{
                break;
            }
        }

        //if (i > toI & j > toJ) {
        for (int r = 1; r <= board.wK.getPositionJ() ; r++) {
            if (board.wK.getPositionI() - r >= 0 && board.wK.getPositionJ() - r >= 0){
                if (r == 1 && !board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("Pawn")){
                    System.out.println("Шах от черной пешки слева вверху");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() - r, board.wK.getPositionJ() - r);
                    return;
                }
                if (r == 1 && !board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("King")){
                    System.out.println("Белому королю шах от черного короля слева вверху");
                    board.wK.shah = true;
                    return;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") && board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") && board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Белому королю шах от фигуры слева вверху");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() - r, board.wK.getPositionJ() - r);
                    return;

                }
            }
            else{
                break;
            }
        }

        if (board.wK.getPositionI() - 2 >= 0 && board.wK.getPositionI() - 2 <= 7 && board.wK.getPositionJ() + 1 >= 0 && board.wK.getPositionJ() + 1 <= 7){
            if (!board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() + 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() + 1].getClass().getName().equals("Knight")){
                System.out.println("Белому королю шах от коня сверху справа");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() - 2, board.wK.getPositionJ() + 1);
                return;
            }
        }

        if (board.wK.getPositionI() - 1 >= 0 && board.wK.getPositionI() - 1 <= 7 && board.wK.getPositionJ() + 2 >= 0 && board.wK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() + 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня справа вверх");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() - 1, board.wK.getPositionJ() + 2);
                return;
            }
        }

        if (board.wK.getPositionI() + 1 >= 0 && board.wK.getPositionI() + 1 <= 7 && board.wK.getPositionJ() + 2 >= 0 && board.wK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() + 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня справа низ");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() + 1, board.wK.getPositionJ() + 2);
                return;
            }
        }

        if (board.wK.getPositionI() + 2 >= 0 && board.wK.getPositionI() + 2 <= 7 && board.wK.getPositionJ() + 1 >= 0 && board.wK.getPositionJ() + 1 <= 7) {
            if (!board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() + 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() + 1].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня снизу справа");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() + 2, board.wK.getPositionJ() + 1);
                return;
            }
        }

        if (board.wK.getPositionI() + 2 >= 0 && board.wK.getPositionI() + 2 <= 7 && board.wK.getPositionJ() - 1 >= 0 && board.wK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() - 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня снизу лево");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() + 2, board.wK.getPositionJ() - 1);
                return;
            }
        }

        if (board.wK.getPositionI() + 1 >= 0 && board.wK.getPositionI() + 1 <= 7 && board.wK.getPositionJ() - 2 >= 0 && board.wK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() - 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня слева низ");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() + 1, board.wK.getPositionJ() - 2);
                return;
            }
        }

        if (board.wK.getPositionI() - 1 >= 0 && board.wK.getPositionI() - 1 <= 7 && board.wK.getPositionJ() - 2 >= 0 && board.wK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() - 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня слева верх");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() - 1, board.wK.getPositionJ() - 2);
                return;
            }
        }

        if (board.wK.getPositionI() - 2 >= 0 && board.wK.getPositionI() - 2 <= 7 && board.wK.getPositionJ() - 1 >= 0 && board.wK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() - 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Белому королю шах от коня сверху слева");
                board.wK.shah = true;
                checkingFigure.setPosition(board.wK.getPositionI() - 2, board.wK.getPositionJ() - 1);
                return;
            }
        }
        board.wK.shah = false;
    }//=

    public static boolean checkWhiteKingMatByKingStep(int x, int y){

        if (0 <= x && x <= 7 && 0 <= y && y <= 7) {

            if (!board.board[x][y].getName().equals("empty") && board.board[x][y].getColor().equals("white")){
                System.out.println("    Клетка занята своей фигурой");
                return true;
            }

            for (int i = x - 1; i >= 0; i--){

                if (i == x - 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("black") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Белому королю шах от черного короля вверху");
                    return true;
                }
                if (x > board.wK.getPositionI() && board.wK.getPositionI() > checkingFigure.getI() &&
                        y == board.wK.getPositionJ() && board.wK.getPositionJ() == checkingFigure.getJ() &&
                        x - board.wK.getPositionI() == 1){
                    System.out.println("Шах вверху");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        !(board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        (board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("Белому королю шах от фигуры прямо");
                    return true;
                }
            }

            for (int j = y + 1; j <= 7; j++){

                if (j == y + 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("black") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Белому королю шах от черного короля справа");
                    return true;
                }
                if (y < board.wK.getPositionJ() && board.wK.getPositionJ() < checkingFigure.getJ() &&
                        x == board.wK.getPositionI() && board.wK.getPositionI() == checkingFigure.getI() &&
                        board.wK.getPositionJ() - y == 1){
                    System.out.println("Шах справа");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        !(board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))   ){
                    break;
                }

                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        (board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Белому королю шах от фигуры справа");
                    return true;
                }
            }

            for (int i = x + 1; i <= 7; i++){

                if (i == x + 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("black") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Белому королю шах от черного короля сзади");
                    return true;
                }
                if (x < board.wK.getPositionI() && board.wK.getPositionI() < checkingFigure.getI() &&
                        y == board.wK.getPositionJ() && board.wK.getPositionJ() == checkingFigure.getJ() &&
                        board.wK.getPositionI() - x == 1){
                    System.out.println("Шах сзади");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        !(board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        (board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Белому королю шах от фигуры сзади");
                    return true;
                }
            }

            for (int j = y - 1; j >= 0; j--){

                if (j == y - 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("black") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Белому королю шах от черного короля слева");
                    return true;
                }
                if (y > board.wK.getPositionJ() && board.wK.getPositionJ() > checkingFigure.getJ() &&
                        x == board.wK.getPositionI() && board.wK.getPositionI() == checkingFigure.getI() &&
                        y - board.wK.getPositionJ() == 1){
                    System.out.println("Шах слева");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        !(board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        (board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Белому королю шах от фигуры слева");
                    return true;
                }
            }

            for (int r = 1; r < 8 - y ; r++) {
                if (x - r >= 0 && y + r <= 7){
                    if (r == 1 && !board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            board.board[x - r][y + r].getClass().getName().equals("Pawn")){
                        System.out.println("    Белому королю шах от черной пешки справа вверху");
                        return true;
                    }
                    if (r == 1 && !board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            board.board[x - r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Белому королю шах от черного короля справа вверху");
                        return true;
                    }
                    if (x > board.wK.getPositionI() && board.wK.getPositionI() > checkingFigure.getI() &&
                            y < board.wK.getPositionJ() && board.wK.getPositionJ() < checkingFigure.getJ() &&
                            x - board.wK.getPositionI() == 1 && board.wK.getPositionJ() - y == 1){
                        System.out.println("Шах справа вверху");
                        return true;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            !(board.board[x - r][y + r].getName().equals("bQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            (board.board[x - r][y + r].getName().equals("bQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Белому королю шах от фигуры справа вверху");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            for (int r = 1; r < 8 - y ; r++) {
                if (x + r <= 7 && y + r <= 7){
                    if (r == 1 && !board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black") &&
                            board.board[x + r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Белому королю шах от черного короля справа внизу");
                        return true;
                    }
                    if (x < board.wK.getPositionI() && board.wK.getPositionI() < checkingFigure.getI() &&
                            y < board.wK.getPositionJ() && board.wK.getPositionJ() < checkingFigure.getJ() &&
                            board.wK.getPositionI() - x == 1 && board.wK.getPositionJ() - y == 1){
                        System.out.println("Шах справа внизу");
                        return true;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black") &&
                            !(board.board[x + r][y + r].getName().equals("bQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black") &&
                            (board.board[x + r][y + r].getName().equals("bQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Белому королю шах от фигуры справа внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            for (int r = 1; r <= y; r++) {
                if (x + r <= 7 && y - r >= 0){
                    if (r == 1 && !board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black") &&
                            board.board[x + r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Белому королю шах от черного короля слева внизу");
                        return true;
                    }
                    if (x < board.wK.getPositionI() && board.wK.getPositionI() < checkingFigure.getI() &&
                            y > board.wK.getPositionJ() && board.wK.getPositionJ() > checkingFigure.getJ() &&
                            board.wK.getPositionI() - x == 1 && y - board.wK.getPositionJ() == 1){
                        System.out.println("Шах слева внизу");
                        return true;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black") &&
                            !(board.board[x + r][y - r].getName().equals("bQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black") &&
                            (board.board[x + r][y - r].getName().equals("bQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Белому королю шах от фигуры слева внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            for (int r = 1; r <= y ; r++) {
                if (x - r >= 0 && y - r >= 0){
                    if (r == 1 && !board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black") &&
                            board.board[x - r][y - r].getClass().getName().equals("Pawn")){
                        System.out.println("    Шах от черной пешки слева вверху");
                        return true;
                    }
                    if (r == 1 && !board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black") &&
                            board.board[x - r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Белому королю шах от черного короля слева вверху");
                        return true;
                    }
                    if (x > board.wK.getPositionI() && board.wK.getPositionI() > checkingFigure.getI() &&
                            y > board.wK.getPositionJ() && board.wK.getPositionJ() > checkingFigure.getJ() &&
                            x - board.wK.getPositionI() == 1 && y - board.wK.getPositionJ() == 1){
                        System.out.println("Шах слева вверху");
                        return true;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") && board.board[x - r][y - r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") && board.board[x - r][y - r].getColor().equals("black") &&
                            !(board.board[x - r][y - r].getName().equals("bQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black") &&
                            (board.board[x - r][y - r].getName().equals("bQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Белому королю шах от фигуры слева вверху");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y + 1 >= 0 && y + 1 <= 7){
                if (!board.board[x - 2][y + 1].getName().equals("empty") &&
                        board.board[x - 2][y + 1].getColor().equals("black") &&
                        board.board[x - 2][y + 1].getClass().getName().equals("Knight")){
                    System.out.println("    Белому королю шах от коня сверху справа");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x - 1][y + 2].getName().equals("empty") &&
                        board.board[x - 1][y + 2].getColor().equals("black") &&
                        board.board[x - 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня справа вверх");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x + 1][y + 2].getName().equals("empty") &&
                        board.board[x + 1][y + 2].getColor().equals("black") &&
                        board.board[x + 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня справа низ");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y + 1 >= 0 && y + 1 <= 7) {
                if (!board.board[x + 2][y + 1].getName().equals("empty") &&
                        board.board[x + 2][y + 1].getColor().equals("black") &&
                        board.board[x + 2][y + 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня снизу справа");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x + 2][y - 1].getName().equals("empty") &&
                        board.board[x + 2][y - 1].getColor().equals("black") &&
                        board.board[x + 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня снизу лево");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x + 1][y - 2].getName().equals("empty") &&
                        board.board[x + 1][y - 2].getColor().equals("black") &&
                        board.board[x + 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня слева низ");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x - 1][y - 2].getName().equals("empty") &&
                        board.board[x - 1][y - 2].getColor().equals("black") &&
                        board.board[x - 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня слева верх");
                    return true;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x - 2][y - 1].getName().equals("empty") &&
                        board.board[x - 2][y - 1].getColor().equals("black") &&
                        board.board[x - 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Белому королю шах от коня сверху слева");
                    return true;
                }
            }

        }
        else {
            System.out.println("    Выход за пределы доски");
            return true;
        }
        return false;
    }//=

    public static boolean checkWhiteKingMatByStopWay(int x, int y, int xF, int yF){

        if ((x > xF && y == yF) ||
                (x < xF && y == yF) ||
                (x == xF && y < yF) ||
                (x == xF && y > yF) ||
                (x > xF && y < yF && x - xF == yF - y) ||
                (x < xF && y < yF && xF - x == yF - y) ||
                (x < xF && y > yF && xF - x == y - yF) ||
                (x > xF && y > yF && x - xF == y - yF)) {

            int newX = 0;
            int newY = 0;
            int sc = 1;
            int r;


            if (x > xF && y == yF) {
                sc = x - xF - 1;
            }
            if (x < xF && y == yF) {
                sc = xF - x - 1;
            }
            if (x == xF && y < yF) {
                sc = yF - y - 1;
            }
            if (x == xF && y > yF) {
                sc = y - yF - 1;
            }
            if (x > xF && y < yF) {
                sc = yF - y - 1;
            }
            if (x < xF && y < yF) {
                sc = yF - y - 1;
            }
            if (x < xF && y > yF) {
                sc = xF - x - 1;
            }
            if (x > xF && y > yF) {
                sc = y - yF - 1;
            }

            for (r = 1; r <= sc; r++) { //шах от короля до фигуры


                if (x > xF && y == yF) {
                    newX = x - r;
                    newY = y;
                }
                if (x < xF && y == yF) {
                    newX = x + r;
                    newY = y;
                }
                if (x == xF && y < yF) {
                    newX = x;
                    newY = y + r;
                }
                if (x == xF && y > yF) {
                    newX = x;
                    newY = y - r;
                }
                if (x > xF && y < yF) {
                    newX = x - r;
                    newY = y + r;
                }
                if (x < xF && y < yF) {
                    newX = x + r;
                    newY = y + r;
                }
                if (x < xF && y > yF) {
                    newX = x + r;
                    newY = y - r;
                }
                if (x > xF && y > yF) {
                    newX = x - r;
                    newY = y - r;
                }

                if (newX <= 5) {//закрыться пешкой на клетку внизу
                    if (board.board[newX][newY].getName().equals("empty") &&
                            board.board[newX + 1][newY].getColor().equals("white") &&
                            (board.board[newX + 1][newY].getClass().getName().equals("Pawn"))) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + 1, newY)) {
                            System.out.println("    От шаха можно закрыться пешкой снизу на клетку");
                            return false;
                        }
                    }
                }

                //закрыться пешкой на 2 клетку внизу
                if (newX <= 4) {
                    if (board.board[newX][newY].getName().equals("empty") &&
                            board.board[newX + 1][newY].getName().equals("empty") &&
                            board.board[newX + 2][newY].getColor().equals("white") &&
                            board.board[newX + 2][newY].getClass().getName().equals("Pawn") &&
                            !board.board[newX + 2][newY].getFirstStep()) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + 2, newY)) {
                            System.out.println("    От шаха можно закрыться пешкой снизу на 2 клетки");
                            return false;
                        }
                    }
                }
                for (int a = 1; a <= x; a++) {// проверка сверху
                    if (newX - a >= 0) {
                        if (!board.board[newX - a][newY].getName().equals("empty") &&
                                board.board[newX - a][newY].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX - a][newY].getName().equals("empty") &&
                                board.board[newX - a][newY].getColor().equals("white") &&
                                !(board.board[newX - a][newY].getClass().getName().equals("Queen") || board.board[newX - a][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX - a][newY].getName().equals("empty") &&
                                board.board[newX - a][newY].getColor().equals("white") &&
                                (board.board[newX - a][newY].getClass().getName().equals("Queen") || board.board[newX - a][newY].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - a, newY)) {
                                System.out.println("    От шаха можно закрыться фигурой сверху");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - x; a++) { //проверка снизу
                    if (newX + a <= 7) {
                        if (!board.board[newX + a][newY].getName().equals("empty") &&
                                board.board[newX + a][newY].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX + a][newY].getName().equals("empty") &&
                                board.board[newX + a][newY].getColor().equals("white") &&
                                !(board.board[newX + a][newY].getClass().getName().equals("Queen") || board.board[newX + a][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX + a][newY].getName().equals("empty") &&
                                board.board[newX + a][newY].getColor().equals("white") &&
                                (board.board[newX + a][newY].getClass().getName().equals("Queen") || board.board[newX + a][newY].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + a, newY)) {
                                System.out.println("    От шаха можно закрыться фигурой снизу");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) {// проверка справа
                    if (newY + a <= 7) {
                        if (!board.board[newX][newY + a].getName().equals("empty") &&
                                board.board[newX][newY + a].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX][newY + a].getName().equals("empty") &&
                                board.board[newX][newY + a].getColor().equals("white") &&
                                !(board.board[newX][newY + a].getClass().getName().equals("Queen") || board.board[newX][newY + a].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX][newY + a].getName().equals("empty") &&
                                board.board[newX][newY + a].getColor().equals("white") &&
                                (board.board[newX][newY + a].getClass().getName().equals("Queen") || board.board[newX][newY + a].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX, newY + a)) {
                                System.out.println("    От шаха можно закрыться фигурой справа");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a <= y; a++) { //проверка слева
                    if (newY - a >= 0) {
                        if (!board.board[newX][newY - a].getName().equals("empty") &&
                                board.board[newX][newY - a].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX][newY - a].getName().equals("empty") &&
                                board.board[newX][newY - a].getColor().equals("white") &&
                                !(board.board[newX][newY - a].getClass().getName().equals("Queen") || board.board[newX][newY - a].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX][newY - a].getName().equals("empty") &&
                                board.board[newX][newY - a].getColor().equals("white") &&
                                (board.board[newX][newY - a].getClass().getName().equals("Queen") || board.board[newX][newY - a].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX, newY - a)) {
                                System.out.println("    От шаха можно закрыться фигурой слева");
                                return false;
                            }
                        }
                    }
                }

                for (int a = 1; a < 8 - y; a++) { //клетки которые проверяем вверх вправо
                    if (newX - a >= 0 && newY + a <= 7) { //ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX - a][newY + a].getName().equals("empty") &&
                                board.board[newX - a][newY + a].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX - a][newY + a].getName().equals("empty") &&
                                board.board[newX - a][newY + a].getColor().equals("white") &&
                                !(board.board[newX - a][newY + a].getClass().getName().equals("Queen") || board.board[newX - a][newY + a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX - a][newY + a].getName().equals("empty") &&
                                board.board[newX - a][newY + a].getColor().equals("white") &&
                                (board.board[newX - a][newY + a].getClass().getName().equals("Queen") || board.board[newX - a][newY + a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - a, newY + a)) {
                                System.out.println("    От шаха можно закрыться фигурой справа вверху");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) { //клетки, которые проверяем вниз вправо
                    if (newX + a <= 7 && newY + a <= 7) {//ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX + a][newY + a].getName().equals("empty") &&
                                board.board[newX + a][newY + a].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX + a][newY + a].getName().equals("empty") &&
                                board.board[newX + a][newY + a].getColor().equals("white") &&
                                !(board.board[newX + a][newY + a].getClass().getName().equals("Queen") || board.board[newX + a][newY + a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX + a][newY + a].getName().equals("empty") &&
                                board.board[newX + a][newY + a].getColor().equals("white") &&
                                (board.board[newX + a][newY + a].getClass().getName().equals("Queen") || board.board[newX + a][newY + a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + a, newY + a)) {
                                System.out.println("    От шаха можно закрыться фигурой справа внизу");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) {  //клетки, которые проверяем вниз влеаво
                    if (newX + a <= 7 && newY - a >= 0) { //ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX + a][newY - a].getName().equals("empty") &&
                                board.board[newX + a][newY - a].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX + a][newY - a].getName().equals("empty") &&
                                board.board[newX + a][newY - a].getColor().equals("white") &&
                                !(board.board[newX + a][newY - a].getClass().getName().equals("Queen") || board.board[newX + a][newY - a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX + a][newY - a].getName().equals("empty") &&
                                board.board[newX + a][newY - a].getColor().equals("white") &&
                                (board.board[newX + a][newY - a].getClass().getName().equals("Queen") || board.board[newX + a][newY - a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + a, newY - a)) {
                                System.out.println("    От шаха можно закрыться фигурой слева внизу");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) {  //клетки, которые проверяем вверх влево
                    if (newX - a >= 0 && newY - a >= 0) {//ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX - a][newY - a].getName().equals("empty") &&
                                board.board[newX - a][newY - a].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[newX - a][newY - a].getName().equals("empty") &&
                                board.board[newX - a][newY - a].getColor().equals("white") &&
                                !(board.board[newX - a][newY - a].getClass().getName().equals("Queen") || board.board[newX - a][newY - a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX - a][newY - a].getName().equals("empty") &&
                                board.board[newX - a][newY - a].getColor().equals("white") &&
                                (board.board[newX - a][newY - a].getClass().getName().equals("Queen") || board.board[newX - a][newY - a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - a, newY - a)) {
                                System.out.println("    От шаха можно закрыться фигурой слева вверху");
                                return false;
                            }
                        }
                    }
                }
                if (newX - 2 >= 0 && newX - 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7) {
                    if (!board.board[newX - 2][newY + 1].getName().equals("empty") &&
                            board.board[newX - 2][newY + 1].getColor().equals("white") &&
                            board.board[newX - 2][newY + 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - 2, newY + 1)) {
                            System.out.println("    Можно закрыться конем сверху справа");
                            return false;
                        }
                    }
                }

                if (newX - 1 >= 0 && newX - 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7) {
                    if (!board.board[newX - 1][newY + 2].getName().equals("empty") &&
                            board.board[newX - 1][newY + 2].getColor().equals("white") &&
                            board.board[newX - 1][newY + 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - 1, newY + 2)) {
                            System.out.println("    Можно зхакрыться конем справа вверх");
                            return false;
                        }
                    }
                }

                if (newX + 1 >= 0 && newX + 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7) {
                    if (!board.board[newX + 1][newY + 2].getName().equals("empty") &&
                            board.board[newX + 1][newY + 2].getColor().equals("white") &&
                            board.board[newX + 1][newY + 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + 1, newY + 2)) {
                            System.out.println("    Можно закрыться конем справа низ");
                            return false;
                        }
                    }
                }

                if (newX + 2 >= 0 && newX + 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7) {
                    if (!board.board[newX + 2][newY + 1].getName().equals("empty") &&
                            board.board[newX + 2][newY + 1].getColor().equals("white") &&
                            board.board[newX + 2][newY + 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + 2, newY + 1)) {
                            System.out.println("    Можно закрыться конем снизу справа");
                            return false;
                        }
                    }
                }

                if (newX + 2 >= 0 && newX + 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7) {
                    if (!board.board[newX + 2][newY - 1].getName().equals("empty") &&
                            board.board[newX + 2][newY - 1].getColor().equals("white") &&
                            board.board[newX + 2][newY - 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + 2, newY - 1)) {
                            System.out.println("    Можно закрыться конем снизу лево");
                            return false;
                        }
                    }
                }

                if (newX + 1 >= 0 && newX + 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7) {
                    if (!board.board[newX + 1][newY - 2].getName().equals("empty") &&
                            board.board[newX + 1][newY - 2].getColor().equals("white") &&
                            board.board[newX + 1][newY - 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX + 1, newY - 2)) {
                            System.out.println("    Можно закрыться конем слева низ");
                            return false;
                        }
                    }
                }

                if (newX - 1 >= 0 && newX - 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7) {
                    if (!board.board[newX - 1][newY - 2].getName().equals("empty") &&
                            board.board[newX - 1][newY - 2].getColor().equals("white") &&
                            board.board[newX - 1][newY - 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - 1, newY - 2)) {
                            System.out.println("    можно закрыться конем слева верх");
                            return false;
                        }
                    }
                }

                if (newX - 2 >= 0 && newX - 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7) {
                    if (!board.board[newX - 2][newY - 1].getName().equals("empty") &&
                            board.board[newX - 2][newY - 1].getColor().equals("white") &&
                            board.board[newX - 2][newY - 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterWhiteFigureStopTheWay(x, y, newX - 2, newY - 1)) {
                            System.out.println("    Можно закрыться конем сверху слева");
                            return false;
                        }
                    }
                }

            }
        }
        System.out.println("Закрыться невозможно");
        return true;
    }//=

    public static boolean findWhiteFigureWhoKilledBlackChackingFigure(){

        int x = board.wK.getPositionI();
        int y = board.wK.getPositionJ();

        for (int i = checkingFigure.getI() - 1; i >= 0; i--){

            int j = checkingFigure.getJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, i, j)) {
                    System.out.println("    Черную шахующую фигуру может побить фигура прямо. Мата нет.");
                    return false;
                }
            }
        }

        for (int j = checkingFigure.getJ() + 1; j <= 7; j++){

            int i = checkingFigure.getI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))   ){
                break;
            }

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, i, j)) {
                    System.out.println("    Черную фигуру может побить фигура справа. Мата нет.");
                    return false;
                }
            }
        }

        for (int i = checkingFigure.getI() + 1; i <= 7; i++){

            int j = checkingFigure.getJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, i, j)) {
                    System.out.println("    Черную фигуру может побить фигура сзади. Мата нет.");
                    return false;
                }
            }
        }

        for (int j = checkingFigure.getJ() - 1; j >= 0; j--){

            int i = checkingFigure.getI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){

                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, i, j)) {
                    System.out.println("    Черную фигуру может побить фигура слева");
                    return false;
                }
            }
        }

        for (int r = 1; r < 8 - checkingFigure.getJ() ; r++) {
            if (checkingFigure.getI() - r >= 0 && checkingFigure.getJ() + r <= 7){

                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("black")){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("white") &&
                        !(board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("wQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("white") &&
                        (board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("wQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() - r, checkingFigure.getJ() + r)) {
                        System.out.println("   Черную фигуру может побить фигура справа вверху");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        for (int r = 1; r < 8 - checkingFigure.getJ() ; r++) {
            if (checkingFigure.getI() + r <= 7 && checkingFigure.getJ() + r <= 7){
                if (r == 1 && !board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("white") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getClass().getName().equals("Pawn")){
                    if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + r, checkingFigure.getJ() + r)) {
                        System.out.println("    Черную фигуру может побить пешка справа внизу");
                        return false;
                    }
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("black")){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("white") &&
                        !(board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("wQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("white") &&
                        (board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("wQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + r, checkingFigure.getJ() + r)) {
                        System.out.println("    Черную фигуру может побить фигура справа внизу");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        for (int r = 1; r <= checkingFigure.getJ(); r++) {
            if (checkingFigure.getI() + r <= 7 && checkingFigure.getJ() - r >= 0){
                if (r == 1 && !board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("white") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getClass().getName().equals("Pawn")){
                    if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + r, checkingFigure.getJ() - r)) {
                        System.out.println("    Черную фигуру может побить пешка слева внизу");
                        return false;
                    }
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("black")){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("white") &&
                        !(board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("wQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("white") &&
                        (board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("wQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + r, checkingFigure.getJ() - r)) {
                        System.out.println("    Черную фигуру может побить фигура слева внизу");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        for (int r = 1; r <= checkingFigure.getJ(); r++) {
            if (checkingFigure.getI() - r >= 0 && checkingFigure.getJ() - r >= 0){

                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("black")){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("white") &&
                        !(board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("wQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("white") &&
                        (board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("wQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() - r, checkingFigure.getJ() - r)) {
                        System.out.println("    Черную фигуру может побить фигура слева вверху. Мата нет.");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        if (checkingFigure.getI() - 2 >= 0 && checkingFigure.getI() - 2 <= 7 && checkingFigure.getJ() + 1 >= 0 && checkingFigure.getJ() + 1 <= 7){
            if (!board.board[checkingFigure.getI() - 2][checkingFigure.getJ() + 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() + 1].getColor().equals("white") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() + 1].getClass().getName().equals("Knight")){
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() - 2, checkingFigure.getJ() + 1)) {
                    System.out.println("    Черная фигура может быть побита конем сверху справа");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() - 1 >= 0 && checkingFigure.getI() - 1 <= 7 && checkingFigure.getJ() + 2 >= 0 && checkingFigure.getJ() + 2 <= 7) {
            if (!board.board[checkingFigure.getI() - 1][checkingFigure.getJ() + 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() + 2].getColor().equals("white") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() + 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() - 1, checkingFigure.getJ() + 2)) {
                    System.out.println("    Черная фигура может быть побита конем справа вверху");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 1 >= 0 && checkingFigure.getI() + 1 <= 7 && checkingFigure.getJ() + 2 >= 0 && checkingFigure.getJ() + 2 <= 7) {
            if (!board.board[checkingFigure.getI() + 1][checkingFigure.getJ() + 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() + 2].getColor().equals("white") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() + 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + 1, checkingFigure.getJ() + 2)) {
                    System.out.println("    Черную фигуру можно побить конем справа низу");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 2 >= 0 && checkingFigure.getI() + 2 <= 7 && checkingFigure.getJ() + 1 >= 0 && checkingFigure.getJ() + 1 <= 7) {
            if (!board.board[checkingFigure.getI() + 2][checkingFigure.getJ() + 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() + 1].getColor().equals("white") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() + 1].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + 2, checkingFigure.getJ() + 1)) {
                    System.out.println("    Черную фигуру можно побить конем снизу справа");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 2 >= 0 && checkingFigure.getI() + 2 <= 7 && checkingFigure.getJ() - 1 >= 0 && checkingFigure.getJ() - 1 <= 7) {
            if (!board.board[checkingFigure.getI() + 2][checkingFigure.getJ() - 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() - 1].getColor().equals("white") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() - 1].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + 2, checkingFigure.getJ() - 1)) {
                    System.out.println("    Черную фигуру можно побить конем снизу лево");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 1 >= 0 && checkingFigure.getI() + 1 <= 7 && checkingFigure.getJ() - 2 >= 0 && checkingFigure.getJ() - 2 <= 7) {
            if (!board.board[checkingFigure.getI() + 1][checkingFigure.getJ() - 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() - 2].getColor().equals("white") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() - 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() + 1, checkingFigure.getJ() - 2)) {
                    System.out.println("    Черную фигуру можно побить конем слева низ");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() - 1 >= 0 && checkingFigure.getI() - 1 <= 7 && checkingFigure.getJ() - 2 >= 0 && checkingFigure.getJ() - 2 <= 7) {
            if (!board.board[checkingFigure.getI() - 1][checkingFigure.getJ() - 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() - 2].getColor().equals("white") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() - 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() - 1, checkingFigure.getJ() - 2)) {
                    System.out.println("    Черную фигуру можно побить конем слева верх");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() - 2 >= 0 && checkingFigure.getI() - 2 <= 7 && checkingFigure.getJ() - 1 >= 0 && checkingFigure.getJ() - 1 <= 7) {
            if (!board.board[checkingFigure.getI() - 2][checkingFigure.getJ() - 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() - 1].getColor().equals("white") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() - 1].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterWhiteFigureStopTheWay(x, y, checkingFigure.getI() - 2, checkingFigure.getJ() - 1)) {
                    System.out.println("    Черную фигуру можно побить конем сверху слева");
                    return false;
                }
            }
        }
        System.out.println("Нет возможности побить шахующую фигуру");
        return true;
    }//=

    public static boolean checkingChachAfterWhiteFigureStopTheWay(int x, int y, int xwF, int ywF){
        if (x > xwF && y == ywF){

            for (int a = 1; a <= xwF; a++){// проверка сверху
                if (!board.board[xwF - a][ywF].getName().equals("empty") &&
                        board.board[xwF - a][ywF].getColor().equals("white")){
                    break;
                }
                /*if (!board.board[xwF - a][ywF].getName().equals("empty") &&
                        (board.board[xwF - a][ywF].getClass().getName().equals("Pawn") || board.board[xwF - a][ywF].getClass().getName().equals("King") ||
                                board.board[xwF - a][ywF].getClass().getName().equals("Biship") || board.board[xwF - a][ywF].getClass().getName().equals("Knight"))){
                    break;
                }*/
                if (!board.board[xwF - a][ywF].getName().equals("empty") &&
                        board.board[xwF - a][ywF].getColor().equals("black") &&
                        !(board.board[xwF - a][ywF].getClass().getName().equals("Queen") || board.board[xwF - a][ywF].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xwF - a][ywF].getName().equals("empty") &&
                        board.board[xwF - a][ywF].getColor().equals("black") &&
                        (board.board[xwF - a][ywF].getClass().getName().equals("Queen") || board.board[xwF - a][ywF].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура сверху");
                    return true;
                }
            }

        }
        if (x == xwF && y < ywF){

            for (int a = 1; a < 8 - ywF; a++){// проверка справа
                if (!board.board[xwF][ywF + a].getName().equals("empty") &&
                        board.board[xwF][ywF + a].getColor().equals("white")){
                    break;
                }
                if (!board.board[xwF][ywF + a].getName().equals("empty") &&
                        board.board[xwF][ywF + a].getColor().equals("black") &&
                        !(board.board[xwF][ywF + a].getClass().getName().equals("Queen") || board.board[xwF][ywF + a].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xwF][ywF + a].getName().equals("empty") &&
                        board.board[xwF][ywF + a].getColor().equals("black") &&
                        (board.board[xwF][ywF + a].getClass().getName().equals("Queen") || board.board[xwF][ywF + a].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура справа");
                    return true;
                }
            }

        }
        if (x < xwF && y == ywF){

            for (int a = 1; a < 8 - xwF; a++){// проверка снизу
                if (!board.board[xwF + a][ywF].getName().equals("empty") &&
                        board.board[xwF + a][ywF].getColor().equals("white")){
                    break;
                }
                if (!board.board[xwF + a][ywF].getName().equals("empty") &&
                        board.board[xwF + a][ywF].getColor().equals("black") &&
                        !(board.board[xwF + a][ywF].getClass().getName().equals("Queen") || board.board[xwF + a][ywF].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xwF + a][ywF].getName().equals("empty") &&
                        board.board[xwF + a][ywF].getColor().equals("black") &&
                        (board.board[xwF + a][ywF].getClass().getName().equals("Queen") || board.board[xwF + a][ywF].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура снизу");
                    return true;
                }
            }

        }
        if (x == xwF && y > ywF){

            for (int a = 1; a <= ywF; a++){// проверка слева
                if (!board.board[xwF][ywF - a].getName().equals("empty") &&
                        board.board[xwF][ywF - a].getColor().equals("white")){
                    break;
                }
                if (!board.board[xwF][ywF - a].getName().equals("empty") &&
                        board.board[xwF][ywF - a].getColor().equals("black") &&
                        !(board.board[xwF][ywF - a].getClass().getName().equals("Queen") || board.board[xwF][ywF - a].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xwF][ywF - a].getName().equals("empty") &&
                        board.board[xwF][ywF - a].getColor().equals("black") &&
                        (board.board[xwF][ywF - a].getClass().getName().equals("Queen") || board.board[xwF][ywF - a].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура слева");
                    return true;
                }
            }

        }
        if (x > xwF && y < ywF && x - xwF == ywF - y) {
            for (int a = 1; a < 8 - ywF; a++) { //клетки которые проверяем вверх вправо
                if (xwF - a >= 0 && ywF + a <= 7) { //ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xwF - a][ywF + a].getName().equals("empty") &&
                            board.board[xwF - a][ywF + a].getColor().equals("white")) {
                        break;
                    }
                    if (!board.board[xwF - a][ywF + a].getName().equals("empty") &&
                            board.board[xwF - a][ywF + a].getColor().equals("black") &&
                            !(board.board[xwF - a][ywF + a].getClass().getName().equals("Queen") || board.board[xwF - a][ywF + a].getClass().getName().equals("Bishop"))) {
                        break;
                    }
                    if (!board.board[xwF - a][ywF + a].getName().equals("empty") &&
                            board.board[xwF - a][ywF + a].getColor().equals("black") &&
                            (board.board[xwF - a][ywF + a].getClass().getName().equals("Queen") || board.board[xwF - a][ywF + a].getClass().getName().equals("Bishop"))) {
                        System.out.println("    Закрыться фигурой справа вверху нельзя");
                        return true;
                    }
                }
            }
        }
        if (x < xwF && y < ywF && xwF - x == ywF - y) {
            for (int a = 1; a < 8 - ywF; a++){ //клетки, которые проверяем вниз вправо
                if (xwF + a <= 7 && ywF + a <= 7){//ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xwF + a][ywF + a].getName().equals("empty") &&
                            board.board[xwF + a][ywF + a].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[xwF + a][ywF + a].getName().equals("empty") &&
                            board.board[xwF + a][ywF + a].getColor().equals("black") &&
                            !(board.board[xwF + a][ywF + a].getClass().getName().equals("Queen") || board.board[xwF + a][ywF + a].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[xwF + a][ywF + a].getName().equals("empty") &&
                            board.board[xwF + a][ywF + a].getColor().equals("black") &&
                            (board.board[xwF + a][ywF + a].getClass().getName().equals("Queen") || board.board[xwF + a][ywF + a].getClass().getName().equals("Bishop"))){
                        System.out.println("    От шаха нельзя закрыться фигурой справа внизу");
                        return false;
                    }
                }
            }
        }
        if (x < xwF && y > ywF && xwF - x == y - ywF) {
            for (int a = 1; a <= ywF; a++) {  //клетки, которые проверяем вниз влеаво
                if (xwF + a <= 7 && ywF - a >= 0) { //ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xwF + a][ywF - a].getName().equals("empty") &&
                            board.board[xwF + a][ywF - a].getColor().equals("white")) {
                        break;
                    }
                    if (!board.board[xwF + a][ywF - a].getName().equals("empty") &&
                            board.board[xwF + a][ywF - a].getColor().equals("black") &&
                            !(board.board[xwF + a][ywF - a].getClass().getName().equals("Queen") || board.board[xwF + a][ywF - a].getClass().getName().equals("Bishop"))) {
                        break;
                    }
                    if (!board.board[xwF + a][ywF - a].getName().equals("empty") &&
                            board.board[xwF + a][ywF - a].getColor().equals("black") &&
                            (board.board[xwF + a][ywF - a].getClass().getName().equals("Queen") || board.board[xwF + a][ywF - a].getClass().getName().equals("Bishop"))) {
                        System.out.println("    От шаха нельзя закрыться фигурой слева внизу");
                        return false;
                    }
                }
            }
        }
        if (x > xwF && y > ywF && x - xwF == y - ywF) {
            for (int a = 1; a <= ywF; a++) {  //клетки, которые проверяем вверх влево
                if (xwF - a >= 0 && ywF - a >= 0) {//ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xwF - a][ywF - a].getName().equals("empty") &&
                            board.board[xwF - a][ywF - a].getColor().equals("white")) {
                        break;
                    }
                    if (!board.board[xwF - a][ywF - a].getName().equals("empty") &&
                            board.board[xwF - a][ywF - a].getColor().equals("black") &&
                            !(board.board[xwF - a][ywF - a].getClass().getName().equals("Queen") || board.board[xwF - a][ywF - a].getClass().getName().equals("Bishop"))) {
                        break;
                    }
                    if (!board.board[xwF - a][ywF - a].getName().equals("empty") &&
                            board.board[xwF - a][ywF - a].getColor().equals("black") &&
                            (board.board[xwF - a][ywF - a].getClass().getName().equals("Queen") || board.board[xwF - a][ywF - a].getClass().getName().equals("Bishop"))) {
                        System.out.println("    От шаха нельзя закрыться фигурой слева вверху");
                        return false;
                    }
                }
            }
        }
       return false;
    }//=

    public static void checkWhiteKingMat(){
        checkDoubleWhiteKingChakh();
        if (checkWhiteKingMatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ()) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ() + 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI(), board.wK.getPositionJ() + 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ() + 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ()) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ() - 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI(), board.wK.getPositionJ() - 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ() - 1) &&
                countChack >= 2){
            System.out.println("White king ChachMat");
            board.wK.mat = true;
            stopListener = true;
            whiteKingMatFrame();
            return;
        }
        if (checkWhiteKingMatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ()) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ() + 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI(), board.wK.getPositionJ() + 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ() + 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ()) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ() - 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI(), board.wK.getPositionJ() - 1) &&
                checkWhiteKingMatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ() - 1) &&
                findWhiteFigureWhoKilledBlackChackingFigure() &&
                checkWhiteKingMatByStopWay(board.wK.getPositionI(), board.wK.getPositionJ(), checkingFigure.getI(), checkingFigure.getJ())){
            System.out.println("White king ChachMat");
            board.wK.mat = true;
            stopListener = true;
            whiteKingMatFrame();
        }
        else{
            System.out.println("Белому королю мата нет");
        }
    }//=
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void checkBlackKingChakh(){

        for (int i = board.bK.getPositionI() - 1; i >= 0; i--){

            int j = board.bK.getPositionJ();

            if (i == board.bK.getPositionI() - 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("white") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Черному королю шах от белого короля вверху");
                board.bK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Черному королю шах от фигуры прямо");
                board.bK.shah = true;
                System.out.println("Координаты фигуры " + i + 1 + " " + j + 1);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        for (int j = board.bK.getPositionJ() + 1; j <= 7; j++){

            int i = board.bK.getPositionI();

            if (j == board.bK.getPositionJ() + 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("white") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Черному королю шах от белого короля справа");
                board.bK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))   ){
                break;
            }

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Черному королю шах от фигуры справа");
                board.bK.shah = true;
                System.out.println("Координаты фигуры " + i + 1 + " " + j + 1);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        for (int i = board.bK.getPositionI() + 1; i <= 7; i++){

            int j = board.bK.getPositionJ();

            if (i == board.bK.getPositionI() + 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("white") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Черному королю шах от белого короля внизу");
                board.bK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Черному королю шах от фигуры внизу");
                board.bK.shah = true;
                System.out.println("Координаты фигуры " + i + 1 + " " + j + 1);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        for (int j = board.bK.getPositionJ() - 1; j >= 0; j--){

            int i = board.bK.getPositionI();

            if (j == board.bK.getPositionJ() - 1 && !board.board[i][j].getName().equals("empty") &&
                    board.board[i][j].getColor().equals("white") && board.board[i][j].getClass().getName().equals("King")){
                System.out.println("Черному королю шах от белого короля слева");
                board.bK.shah = true;
                return;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Черному королю шах от фигуры слева");
                board.bK.shah = true;
                System.out.println("Координаты фигуры " + i + 1 + " " + j + 1);
                checkingFigure.setPosition(i, j);
                return;
            }
        }

        //if (i > toI & j < toJ) {
        for (int r = 1; r < 8 - board.bK.getPositionJ() ; r++) {
            if (board.bK.getPositionI() - r >= 0 && board.bK.getPositionJ() + r <= 7){

                if (r == 1 && !board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getClass().getName().equals("King")){
                    System.out.println("Черному королю шах от белого короля справа вверху");
                    board.bK.shah = true;
                    return;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Черному королю шах от фигуры справа вверху");
                    board.bK.shah = true;
                    checkingFigure.setPosition(board.bK.getPositionI() - r, board.bK.getPositionJ() + r);
                    return;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j < toJ)
        for (int r = 1; r < 8 - board.bK.getPositionJ() ; r++) {
            if (board.bK.getPositionI() + r <= 7 && board.bK.getPositionJ() + r <= 7){
                if (r == 1 && !board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("Pawn")){
                    System.out.println("Шах от белой пешки справа внизу");
                    board.bK.shah = true;
                    checkingFigure.setPosition(board.bK.getPositionI() + r, board.bK.getPositionJ() + r);
                    return;
                }
                if (r == 1 && !board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("King")){
                    System.out.println("Черному королю шах от белого короля справа внизу");
                    board.bK.shah = true;
                    return;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Черному королю шах от фигуры справа внизу");
                    board.bK.shah = true;
                    checkingFigure.setPosition(board.bK.getPositionI() + r, board.bK.getPositionJ() + r);
                    return;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j > toJ)
        for (int r = 1; r <= board.bK.getPositionJ(); r++) {
            if (board.bK.getPositionI() + r <= 7 && board.bK.getPositionJ() - r >= 0){
                if (r == 1 && !board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("Pawn")){
                    System.out.println("Шах от белой пешки слева внизу");
                    board.bK.shah = true;
                    checkingFigure.setPosition(board.bK.getPositionI() + r, board.bK.getPositionJ() - r);
                    return;
                }
                if (r == 1 && !board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("King")){
                    System.out.println("Черному королю шах от белого короля слева внизу");
                    board.bK.shah = true;
                    return;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Черному шах от фигуры слева внизу");
                    board.bK.shah = true;
                    checkingFigure.setPosition(board.bK.getPositionI() + r, board.bK.getPositionJ() - r);
                    return;
                }
            }
            else{
                break;
            }
        }

        //if (i > toI & j > toJ) {
        for (int r = 1; r <= board.bK.getPositionJ() ; r++) {
            if (board.bK.getPositionI() - r >= 0 && board.bK.getPositionJ() - r >= 0){

                if (r == 1 && !board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getClass().getName().equals("King")){
                    System.out.println("Черному королю шах от белого короля слева вверху");
                    board.bK.shah = true;
                    return;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Черному королю шах от фигуры слева вверху");
                    board.bK.shah = true;
                    checkingFigure.setPosition(board.bK.getPositionI() - r, board.bK.getPositionJ() - r);
                    return;

                }
            }
            else{
                break;
            }
        }

        if (board.bK.getPositionI() - 2 >= 0 && board.bK.getPositionI() - 2 <= 7 && board.bK.getPositionJ() + 1 >= 0 && board.bK.getPositionJ() + 1 <= 7){
            if (!board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() + 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() + 1].getClass().getName().equals("Knight")){
                System.out.println("Черному королю шах от коня сверху справа");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() - 2, board.bK.getPositionJ() + 1);
                return;
            }
        }

        if (board.bK.getPositionI() - 1 >= 0 && board.bK.getPositionI() - 1 <= 7 && board.bK.getPositionJ() + 2 >= 0 && board.bK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() + 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня справа вверх");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() - 1, board.bK.getPositionJ() + 2);
                return;
            }
        }

        if (board.bK.getPositionI() + 1 >= 0 && board.bK.getPositionI() + 1 <= 7 && board.bK.getPositionJ() + 2 >= 0 && board.bK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() + 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня справа низ");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() + 1, board.bK.getPositionJ() + 2);
                return;
            }
        }

        if (board.bK.getPositionI() + 2 >= 0 && board.bK.getPositionI() + 2 <= 7 && board.bK.getPositionJ() + 1 >= 0 && board.bK.getPositionJ() + 1 <= 7) {
            if (!board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() + 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() + 1].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня снизу справа");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() + 2, board.bK.getPositionJ() + 1);
                return;
            }
        }

        if (board.bK.getPositionI() + 2 >= 0 && board.bK.getPositionI() + 2 <= 7 && board.bK.getPositionJ() - 1 >= 0 && board.bK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() - 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня снизу лево");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() + 2, board.bK.getPositionJ() - 1);
                return;
            }
        }

        if (board.bK.getPositionI() + 1 >= 0 && board.bK.getPositionI() + 1 <= 7 && board.bK.getPositionJ() - 2 >= 0 && board.bK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() - 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня слева низ");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() + 1, board.bK.getPositionJ() - 2);
                return;
            }
        }

        if (board.bK.getPositionI() - 1 >= 0 && board.bK.getPositionI() - 1 <= 7 && board.bK.getPositionJ() - 2 >= 0 && board.bK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() - 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня слева верх");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() - 1, board.bK.getPositionJ() - 2);
                return;
            }
        }

        if (board.bK.getPositionI() - 2 >= 0 && board.bK.getPositionI() - 2 <= 7 && board.bK.getPositionJ() - 1 >= 0 && board.bK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() - 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Черному королю шах от коня сверху слева");
                board.bK.shah = true;
                checkingFigure.setPosition(board.bK.getPositionI() - 2, board.bK.getPositionJ() - 1);
                return;
            }
        }
        board.bK.shah = false;
    }//=

    public static boolean checkBlackKingMatByKingStep(int x, int y){
        if (0 <= x && x <= 7 && y >=0 && y <=7){

            if (!board.board[x][y].getName().equals("empty") && board.board[x][y].getColor().equals("black")){
                System.out.println("    Своя фигура");
                return true;
            }

            for (int i = x - 1; i >= 0; i--){
                if (i == x - 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("white") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Черному королю шах от белого короля вверху");
                    return true;
                }
                if (x > board.bK.getPositionI() && board.bK.getPositionI() > checkingFigure.getI() &&
                        y == board.bK.getPositionJ() && board.bK.getPositionJ() == checkingFigure.getJ() &&
                        x - board.bK.getPositionI() == 1){
                    System.out.println("Шах вверху");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        !(board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        (board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Черному королю шах от фигуры прямо");
                    return true;
                }
            }

            for (int j = y + 1; j <= 7; j++){
                if (j == y + 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("white") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Черному королю шах от белого короля справа");
                    return true;
                }
                if (y < board.bK.getPositionJ() && board.bK.getPositionJ() < checkingFigure.getJ() &&
                        x == board.bK.getPositionI() && board.bK.getPositionI() == checkingFigure.getI() &&
                        board.bK.getPositionJ() - y == 1){
                    System.out.println("Шах справа");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        !(board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))   ){
                    break;
                }

                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        (board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Черному королю шах от фигуры справа");
                    return true;
                }
            }

            for (int i = x + 1; i <= 7; i++){
                if (i == x + 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("white") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Черному королю шах от белого короля внизу");
                    return true;
                }
                if (x < board.bK.getPositionI() && board.bK.getPositionI() < checkingFigure.getI() &&
                        y == board.bK.getPositionJ() && board.bK.getPositionJ() == checkingFigure.getJ() &&
                        board.bK.getPositionI() - x == 1){
                    System.out.println("Шах сзади");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        !(board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        (board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Черному королю шах от фигуры внизу");
                    return true;
                }
            }

            for (int j = y - 1; j >= 0; j--){
                if (j == y - 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("white") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Черному королю шах от белого короля слева");
                    return true;
                }
                if (y > board.bK.getPositionJ() && board.bK.getPositionJ() > checkingFigure.getJ() &&
                        x == board.bK.getPositionI() && board.bK.getPositionI() == checkingFigure.getI() &&
                        y - board.bK.getPositionJ() == 1){
                    System.out.println("Шах слева");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        !(board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        (board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Черному королю шах от фигуры слева");
                    return true;
                }
            }

            //if (i > toI & j < toJ) {
            for (int r = 1; r < 8 - y ; r++) {
                if (x - r >= 0 && y + r <= 7){

                    if (r == 1 && !board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white") &&
                            board.board[x - r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Черному королю шах от белого короля справа вверху");
                        return true;
                    }
                    if (x > board.bK.getPositionI() && board.bK.getPositionI() > checkingFigure.getI() &&
                            y < board.bK.getPositionJ() && board.bK.getPositionJ() < checkingFigure.getJ() &&
                            x - board.bK.getPositionI() == 1 && board.bK.getPositionJ() - y == 1){
                        System.out.println("Шах справа вверху");
                        return true;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white") &&
                            !(board.board[x - r][y + r].getName().equals("wQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white") &&
                            (board.board[x - r][y + r].getName().equals("wQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Черному королю шах от фигуры справа вверху");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            //if (i < toI & j < toJ)
            for (int r = 1; r < 8 - y ; r++) {
                if (x + r <= 7 && y + r <= 7){
                    if (r == 1 && !board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            board.board[x + r][y + r].getClass().getName().equals("Pawn")){
                        System.out.println("    Шах от белой пешки справа внизу");
                        return true;
                    }
                    if (r == 1 && !board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            board.board[x + r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Черному королю шах от белого короля справа внизу");
                        return true;
                    }
                    if (x < board.bK.getPositionI() && board.bK.getPositionI() < checkingFigure.getI() &&
                            y < board.bK.getPositionJ() && board.bK.getPositionJ() < checkingFigure.getJ() &&
                            board.bK.getPositionI() - x == 1 && board.bK.getPositionJ() - y == 1){
                        System.out.println("Шах справа внизу");
                        return true;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            !(board.board[x + r][y + r].getName().equals("wQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            (board.board[x + r][y + r].getName().equals("wQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Черному королю шах от фигуры справа внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            //if (i < toI & j > toJ)
            for (int r = 1; r <= y; r++) {
                if (x + r <= 7 && y - r >= 0){
                    if (r == 1 && !board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            board.board[x + r][y - r].getClass().getName().equals("Pawn")){
                        System.out.println("    Шах от белой пешки слева внизу");
                        return true;
                    }
                    if (r == 1 && !board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            board.board[x + r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Черному королю шах от белого короля слева внизу");
                        return true;
                    }
                    if (x < board.bK.getPositionI() && board.bK.getPositionI() < checkingFigure.getI() &&
                            y > board.bK.getPositionJ() && board.bK.getPositionJ() > checkingFigure.getJ() &&
                            board.bK.getPositionI() - x == 1 && y - board.bK.getPositionJ() == 1){
                        System.out.println("Шах слева внизу");
                        return true;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            !(board.board[x + r][y - r].getName().equals("wQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            (board.board[x + r][y - r].getName().equals("wQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Черному шах от фигуры слева внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            //if (i > toI & j > toJ) {
            for (int r = 1; r <= y ; r++) {
                if (x - r >= 0 && y - r >= 0){

                    if (r == 1 && !board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("white") &&
                            board.board[x - r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Черному королю шах от белого короля слева вверху");
                        return true;
                    }
                    if (x > board.bK.getPositionI() && board.bK.getPositionI() > checkingFigure.getI() &&
                            y > board.bK.getPositionJ() && board.bK.getPositionJ() > checkingFigure.getJ() &&
                            x - board.bK.getPositionI() == 1 && y - board.bK.getPositionJ() == 1){
                        System.out.println("Шах слева вверху");
                        return true;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("white") &&
                            !(board.board[x - r][y - r].getName().equals("wQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("white") &&
                            (board.board[x - r][y - r].getName().equals("wQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Черному королю шах от фигуры слева вверху");
                        return true;

                    }
                }
                else{
                    break;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y + 1 >= 0 && y + 1 <= 7){
                if (!board.board[x - 2][y + 1].getName().equals("empty") &&
                        board.board[x - 2][y + 1].getColor().equals("white") &&
                        board.board[x - 2][y + 1].getClass().getName().equals("Knight")){
                    System.out.println("    Черному королю шах от коня сверху справа");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x - 1][y + 2].getName().equals("empty") &&
                        board.board[x - 1][y + 2].getColor().equals("white") &&
                        board.board[x - 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня справа вверх");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x + 1][y + 2].getName().equals("empty") &&
                        board.board[x + 1][y + 2].getColor().equals("white") &&
                        board.board[x + 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня справа низ");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y + 1 >= 0 && y + 1 <= 7) {
                if (!board.board[x + 2][y + 1].getName().equals("empty") &&
                        board.board[x + 2][y + 1].getColor().equals("white") &&
                        board.board[x + 2][y + 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня снизу справа");
                     return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x + 2][y - 1].getName().equals("empty") &&
                        board.board[x + 2][y - 1].getColor().equals("white") &&
                        board.board[x + 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня снизу лево");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x + 1][y - 2].getName().equals("empty") &&
                        board.board[x + 1][y - 2].getColor().equals("white") &&
                        board.board[x + 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня слева низ");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x - 1][y - 2].getName().equals("empty") &&
                        board.board[x - 1][y - 2].getColor().equals("white") &&
                        board.board[x - 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня слева верх");
                    return true;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x - 2][y - 1].getName().equals("empty") &&
                        board.board[x - 2][y - 1].getColor().equals("white") &&
                        board.board[x - 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Черному королю шах от коня сверху слева");
                    return true;
                }
            }
        }
        else{
            System.out.println("Выход за пределы доски");
            return true;
        }
        return false;
        //для методов мат добавить для короля все клетки
    }//=

    public static boolean checkBlackKingMatByStopWay(int x, int y, int xF, int yF){

        if ((x > xF && y == yF) ||
                (x < xF && y == yF) ||
                (x == xF && y < yF) ||
                (x == xF && y > yF) ||
                (x > xF && y < yF && x - xF == yF - y) ||
                (x < xF && y < yF && xF - x == yF - y) ||
                (x < xF && y > yF && xF - x == y - yF) ||
                (x > xF && y > yF && x - xF == y - yF)) {

            int newX = 0;
            int newY = 0;
            int sc = 1;
            int r;


            if (x > xF && y == yF) {
                sc = x - xF - 1;
            }
            if (x < xF && y == yF) {
                sc = xF - x - 1;
            }
            if (x == xF && y < yF) {
                sc = yF - y - 1;
            }
            if (x == xF && y > yF) {
                sc = y - yF - 1;
            }
            if (x > xF && y < yF) {
                sc = yF - y - 1;
            }
            if (x < xF && y < yF) {
                sc = yF - y - 1;
            }
            if (x < xF && y > yF) {
                sc = xF - x - 1;
            }
            if (x > xF && y > yF) {
                sc = y - yF - 1;
            }

            for (r = 1; r <= sc; r++) { //шах от короля до фигуры


                if (x > xF && y == yF) {
                    newX = x - r;
                    newY = y;
                }
                if (x < xF && y == yF) {
                    newX = x + r;
                    newY = y;
                }
                if (x == xF && y < yF) {
                    newX = x;
                    newY = y + r;
                }
                if (x == xF && y > yF) {
                    newX = x;
                    newY = y - r;
                }
                if (x > xF && y < yF) {
                    newX = x - r;
                    newY = y + r;
                }
                if (x < xF && y < yF) {
                    newX = x + r;
                    newY = y + r;
                }
                if (x < xF && y > yF) {
                    newX = x + r;
                    newY = y - r;
                }
                if (x > xF && y > yF) {
                    newX = x - r;
                    newY = y - r;
                }

                if (newX >= 2) {//закрыться пешкой на клетку внизу
                    if (board.board[newX][newY].getName().equals("empty") &&
                            board.board[newX - 1][newY].getColor().equals("black") &&
                            (board.board[newX - 1][newY].getClass().getName().equals("Pawn"))) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - 1, newY)) {
                            System.out.println("    От шаха можно закрыться пешкой сверху на клетку");
                            return false;
                        }
                    }
                }

                //закрыться пешкой на 2 клетку внизу
                if (newX >= 3) {
                    if (board.board[newX][newY].getName().equals("empty") &&
                            board.board[newX - 1][newY].getName().equals("empty") &&
                            board.board[newX - 2][newY].getColor().equals("black") &&
                            board.board[newX - 2][newY].getClass().getName().equals("Pawn") &&
                            !board.board[newX - 2][newY].getFirstStep()) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - 2, newY)) {
                            System.out.println("    От шаха можно закрыться пешкой сверху на 2 клетки");
                            return false;
                        }
                    }
                }
                for (int a = 1; a <= x; a++) {// проверка сверху
                    if (newX - a >= 0) {
                        if (!board.board[newX - a][newY].getName().equals("empty") &&
                                board.board[newX - a][newY].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX - a][newY].getName().equals("empty") &&
                                board.board[newX - a][newY].getColor().equals("black") &&
                                !(board.board[newX - a][newY].getClass().getName().equals("Queen") || board.board[newX - a][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX - a][newY].getName().equals("empty") &&
                                board.board[newX - a][newY].getColor().equals("black") &&
                                (board.board[newX - a][newY].getClass().getName().equals("Queen") || board.board[newX - a][newY].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - a, newY)) {
                                System.out.println("    От шаха можно закрыться фигурой сверху");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - x; a++) { //проверка снизу
                    if (newX + a <= 7) {
                        if (!board.board[newX + a][newY].getName().equals("empty") &&
                                board.board[newX + a][newY].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX + a][newY].getName().equals("empty") &&
                                board.board[newX + a][newY].getColor().equals("black") &&
                                !(board.board[newX + a][newY].getClass().getName().equals("Queen") || board.board[newX + a][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX + a][newY].getName().equals("empty") &&
                                board.board[newX + a][newY].getColor().equals("black") &&
                                (board.board[newX + a][newY].getClass().getName().equals("Queen") || board.board[newX + a][newY].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + a, newY)) {
                                System.out.println("    От шаха можно закрыться фигурой снизу");
                                return false;
                            }
                        }
                    }
                }

                for (int a = 1; a < 8 - y; a++) {// проверка справа
                    if (newY + a <= 7) {
                        if (!board.board[newX][newY + a].getName().equals("empty") &&
                                board.board[newX][newY + a].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX][newY + a].getName().equals("empty") &&
                                board.board[newX][newY + a].getColor().equals("black") &&
                                !(board.board[newX][newY + a].getClass().getName().equals("Queen") || board.board[newX][newY + a].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX][newY + a].getName().equals("empty") &&
                                board.board[newX][newY + a].getColor().equals("black") &&
                                (board.board[newX][newY + a].getClass().getName().equals("Queen") || board.board[newX][newY + a].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX, newY + a)) {
                                System.out.println("    От шаха можно закрыться фигурой справа");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a <= y; a++) { //проверка слева
                    if (newY - a >= 0) {
                        if (!board.board[newX][newY - a].getName().equals("empty") &&
                                board.board[newX][newY - a].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX][newY - a].getName().equals("empty") &&
                                board.board[newX][newY - a].getColor().equals("black") &&
                                !(board.board[newX][newY - a].getClass().getName().equals("Queen") || board.board[newX][newY - a].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[newX][newY - a].getName().equals("empty") &&
                                board.board[newX][newY - a].getColor().equals("black") &&
                                (board.board[newX][newY - a].getClass().getName().equals("Queen") || board.board[newX][newY - a].getClass().getName().equals("Rook"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX, newY - a)) {
                                System.out.println("    От шаха можно закрыться фигурой слева");
                                return false;
                            }
                        }
                    }
                }

                for (int a = 1; a < 8 - y; a++) { //клетки которые проверяем вверх вправо
                    if (newX - a >= 0 && newY + a <= 7) { //ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX - a][newY + a].getName().equals("empty") &&
                                board.board[newX - a][newY + a].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX - a][newY + a].getName().equals("empty") &&
                                board.board[newX - a][newY + a].getColor().equals("black") &&
                                !(board.board[newX - a][newY + a].getClass().getName().equals("Queen") || board.board[newX - a][newY + a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX - a][newY + a].getName().equals("empty") &&
                                board.board[newX - a][newY + a].getColor().equals("black") &&
                                (board.board[newX - a][newY + a].getClass().getName().equals("Queen") || board.board[newX - a][newY + a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - a, newY + a)) {
                                System.out.println("    От шаха можно закрыться фигурой справа вверху");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) { //клетки, которые проверяем вниз вправо
                    if (newX + a <= 7 && newY + a <= 7) {//ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX + a][newY + a].getName().equals("empty") &&
                                board.board[newX + a][newY + a].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX + a][newY + a].getName().equals("empty") &&
                                board.board[newX + a][newY + a].getColor().equals("black") &&
                                !(board.board[newX + a][newY + a].getClass().getName().equals("Queen") || board.board[newX + a][newY + a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX + a][newY + a].getName().equals("empty") &&
                                board.board[newX + a][newY + a].getColor().equals("black") &&
                                (board.board[newX + a][newY + a].getClass().getName().equals("Queen") || board.board[newX + a][newY + a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + a, newY + a)) {
                                System.out.println("    От шаха можно закрыться фигурой справа внизу");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) {  //клетки, которые проверяем вниз влеаво
                    if (newX + a <= 7 && newY - a >= 0) { //ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX + a][newY - a].getName().equals("empty") &&
                                board.board[newX + a][newY - a].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX + a][newY - a].getName().equals("empty") &&
                                board.board[newX + a][newY - a].getColor().equals("black") &&
                                !(board.board[newX + a][newY - a].getClass().getName().equals("Queen") || board.board[newX + a][newY - a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX + a][newY - a].getName().equals("empty") &&
                                board.board[newX + a][newY - a].getColor().equals("black") &&
                                (board.board[newX + a][newY - a].getClass().getName().equals("Queen") || board.board[newX + a][newY - a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + a, newY - a)) {
                                System.out.println("    От шаха можно закрыться фигурой слева внизу");
                                return false;
                            }
                        }
                    }
                }
                for (int a = 1; a < 8 - y; a++) {  //клетки, которые проверяем вверх влево
                    if (newX - a >= 0 && newY - a >= 0) {//ограничения, чтобы не выйти за пределы доски
                        if (!board.board[newX - a][newY - a].getName().equals("empty") &&
                                board.board[newX - a][newY - a].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[newX - a][newY - a].getName().equals("empty") &&
                                board.board[newX - a][newY - a].getColor().equals("black") &&
                                !(board.board[newX - a][newY - a].getClass().getName().equals("Queen") || board.board[newX - a][newY - a].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[newX - a][newY - a].getName().equals("empty") &&
                                board.board[newX - a][newY - a].getColor().equals("black") &&
                                (board.board[newX - a][newY - a].getClass().getName().equals("Queen") || board.board[newX - a][newY - a].getClass().getName().equals("Bishop"))) {
                            if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - a, newY - a)) {
                                System.out.println("    От шаха можно закрыться фигурой слева вверху");
                                return false;
                            }
                        }
                    }
                }
                if (newX - 2 >= 0 && newX - 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7) {
                    if (!board.board[newX - 2][newY + 1].getName().equals("empty") &&
                            board.board[newX - 2][newY + 1].getColor().equals("black") &&
                            board.board[newX - 2][newY + 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - 2, newY + 1)) {
                            System.out.println("    Можно закрыться конем сверху справа");
                            return false;
                        }
                    }
                }

                if (newX - 1 >= 0 && newX - 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7) {
                    if (!board.board[newX - 1][newY + 2].getName().equals("empty") &&
                            board.board[newX - 1][newY + 2].getColor().equals("black") &&
                            board.board[newX - 1][newY + 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - 1, newY + 2)) {
                            System.out.println("    Можно зхакрыться конем справа вверх");
                            return false;
                        }
                    }
                }

                if (newX + 1 >= 0 && newX + 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7) {
                    if (!board.board[newX + 1][newY + 2].getName().equals("empty") &&
                            board.board[newX + 1][newY + 2].getColor().equals("black") &&
                            board.board[newX + 1][newY + 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + 1, newY + 2)) {
                            System.out.println("    Можно закрыться конем справа низ");
                            return false;
                        }
                    }
                }

                if (newX + 2 >= 0 && newX + 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7) {
                    if (!board.board[newX + 2][newY + 1].getName().equals("empty") &&
                            board.board[newX + 2][newY + 1].getColor().equals("black") &&
                            board.board[newX + 2][newY + 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + 2, newY + 1)) {
                            System.out.println("    Можно закрыться конем снизу справа");
                            return false;
                        }
                    }
                }

                if (newX + 2 >= 0 && newX + 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7) {
                    if (!board.board[newX + 2][newY - 1].getName().equals("empty") &&
                            board.board[newX + 2][newY - 1].getColor().equals("black") &&
                            board.board[newX + 2][newY - 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + 2, newY - 1)) {
                            System.out.println("    Можно закрыться конем снизу лево");
                            return false;
                        }
                    }
                }

                if (newX + 1 >= 0 && newX + 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7) {
                    if (!board.board[newX + 1][newY - 2].getName().equals("empty") &&
                            board.board[newX + 1][newY - 2].getColor().equals("black") &&
                            board.board[newX + 1][newY - 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX + 1, newY - 2)) {
                            System.out.println("    Можно закрыться конем слева низ");
                            return false;
                        }
                    }
                }

                if (newX - 1 >= 0 && newX - 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7) {
                    if (!board.board[newX - 1][newY - 2].getName().equals("empty") &&
                            board.board[newX - 1][newY - 2].getColor().equals("black") &&
                            board.board[newX - 1][newY - 2].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - 1, newY - 2)) {
                            System.out.println("    можно закрыться конем слева верх");
                            return false;
                        }
                    }
                }

                if (newX - 2 >= 0 && newX - 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7) {
                    if (!board.board[newX - 2][newY - 1].getName().equals("empty") &&
                            board.board[newX - 2][newY - 1].getColor().equals("black") &&
                            board.board[newX - 2][newY - 1].getClass().getName().equals("Knight")) {
                        if (!checkingChachAfterBlackFigureTryStopChack(x, y, newX - 2, newY - 1)) {
                            System.out.println("    Можно закрыться конем сверху слева");
                            return false;
                        }
                    }
                }

            }
        }
        System.out.println("Закрыться невозможно");
        return true;
    }//=

    public static boolean findBlackFigureWhoKilledWhiteChackingFigure(){

        int x = board.bK.getPositionI();
        int y = board.bK.getPositionJ();

        int m = checkingFigure.getI();
        int n = checkingFigure.getI();

        for (int i = checkingFigure.getI() - 1; i >= 0; i--){

            int j = checkingFigure.getJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, i, j)) {
                    System.out.println("    Белую шахующую фигуру может побить фигура прямо. Мата нет.");
                    return false;
                }
            }
        }

        for (int j = checkingFigure.getJ() + 1; j <= 7; j++){

            int i = checkingFigure.getI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))   ){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, i, j)) {
                    System.out.println("    Белую фигуру может побить фигура справа. Мата нет.");
                    return false;
                }
            }
        }

        for (int i = checkingFigure.getI() + 1; i <= 7; i++){

            int j = checkingFigure.getJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, i, j)) {
                    System.out.println("    Белую фигуру может побить фигура сзади. Мата нет.");
                    return false;
                }
            }
        }

        for (int j = checkingFigure.getJ() - 1; j >= 0; j--){

            int i = checkingFigure.getI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, i, j)) {
                    System.out.println("    Белую фигуру может побить фигура слева");
                    return false;
                }
            }
        }

        for (int r = 1; r < 8 - checkingFigure.getJ() ; r++) {
            if (checkingFigure.getI() - r >= 0 && checkingFigure.getJ() + r <= 7){

                if (r == 1 && !board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("black") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getClass().getName().equals("Pawn")){
                    if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - r, checkingFigure.getJ() + r)) {
                        System.out.println("    Белую фигуру может побить пешка справа вверху");
                        return false;
                    }
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("white")){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("black") &&
                        !(board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("bQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getColor().equals("black") &&
                        (board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getName().equals("bQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - r, checkingFigure.getJ() + r)) {
                        System.out.println("   Белую фигуру может побить фигура справа вверху");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        for (int r = 1; r < 8 - checkingFigure.getJ() ; r++) {
            if (checkingFigure.getI() + r <= 7 && checkingFigure.getJ() + r <= 7){

                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("white")){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("black") &&
                        !(board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("bQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getColor().equals("black") &&
                        (board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getName().equals("bQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() + r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() + r, checkingFigure.getJ() + r)) {
                        System.out.println("    Белую фигуру может побить фигура справа внизу");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        for (int r = 1; r <= checkingFigure.getJ(); r++) {
            if (checkingFigure.getI() + r <= 7 && checkingFigure.getJ() - r >= 0){

                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("white")){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("black") &&
                        !(board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("bQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getColor().equals("black") &&
                        (board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getName().equals("bQueen") || board.board[checkingFigure.getI() + r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() + r, checkingFigure.getJ() - r)) {
                        System.out.println("    Белую фигуру может побить фигура слева внизу");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        for (int r = 1; r <= checkingFigure.getJ(); r++) {
            if (checkingFigure.getI() - r >= 0 && checkingFigure.getJ() - r >= 0){

                if (r == 1 && !board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("black") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getClass().getName().equals("Pawn")){
                    if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - r, checkingFigure.getJ() - r)) {
                        System.out.println("    Белую фигуру может побить пешка слева вверху");
                        return false;
                    }
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("white")){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("black") &&
                        !(board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("bQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("empty") &&
                        board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getColor().equals("black") &&
                        (board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getName().equals("bQueen") || board.board[checkingFigure.getI() - r][checkingFigure.getJ() - r].getClass().getName().equals("Bishop"))){
                    if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - r, checkingFigure.getJ() - r)) {
                        System.out.println("    Белую фигуру может побить фигура слева вверху. Мата нет.");
                        return false;
                    }
                }
            }
            else{
                break;
            }
        }

        if (checkingFigure.getI() - 2 >= 0 && checkingFigure.getI() - 2 <= 7 && checkingFigure.getJ() + 1 >= 0 && checkingFigure.getJ() + 1 <= 7){
            if (!board.board[checkingFigure.getI() - 2][checkingFigure.getJ() + 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() + 1].getColor().equals("black") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() + 1].getClass().getName().equals("Knight")){
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - 2, checkingFigure.getJ() + 1)) {
                    System.out.println("    Белая фигура может быть побита конем сверху справа");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() - 1 >= 0 && checkingFigure.getI() - 1 <= 7 && checkingFigure.getJ() + 2 >= 0 && checkingFigure.getJ() + 2 <= 7) {
            if (!board.board[checkingFigure.getI() - 1][checkingFigure.getJ() + 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() + 2].getColor().equals("black") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() + 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - 1, checkingFigure.getJ() + 2)) {
                    System.out.println("    Белая фигура может быть побита конем справа вверху");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 1 >= 0 && checkingFigure.getI() + 1 <= 7 && checkingFigure.getJ() + 2 >= 0 && checkingFigure.getJ() + 2 <= 7) {
            if (!board.board[checkingFigure.getI() + 1][checkingFigure.getJ() + 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() + 2].getColor().equals("black") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() + 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() + 1, checkingFigure.getJ() + 2)) {
                    System.out.println("    Белую фигуру можно побить конем справа низу");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 2 >= 0 && checkingFigure.getI() + 2 <= 7 && checkingFigure.getJ() + 1 >= 0 && checkingFigure.getJ() + 1 <= 7) {
            if (!board.board[checkingFigure.getI() + 2][checkingFigure.getJ() + 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() + 1].getColor().equals("black") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() + 1].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() + 2, checkingFigure.getJ() + 1)) {
                    System.out.println("    Белую фигуру можно побить конем снизу справа");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 2 >= 0 && checkingFigure.getI() + 2 <= 7 && checkingFigure.getJ() - 1 >= 0 && checkingFigure.getJ() - 1 <= 7) {
            if (!board.board[checkingFigure.getI() + 2][checkingFigure.getJ() - 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() - 1].getColor().equals("black") &&
                    board.board[checkingFigure.getI() + 2][checkingFigure.getJ() - 1].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() + 2, checkingFigure.getJ() - 1)) {
                    System.out.println("    Белую фигуру можно побить конем снизу лево");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() + 1 >= 0 && checkingFigure.getI() + 1 <= 7 && checkingFigure.getJ() - 2 >= 0 && checkingFigure.getJ() - 2 <= 7) {
            if (!board.board[checkingFigure.getI() + 1][checkingFigure.getJ() - 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() - 2].getColor().equals("black") &&
                    board.board[checkingFigure.getI() + 1][checkingFigure.getJ() - 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() + 1, checkingFigure.getJ() - 2)) {
                    System.out.println("    белую фигуру можно побить конем слева низ");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() - 1 >= 0 && checkingFigure.getI() - 1 <= 7 && checkingFigure.getJ() - 2 >= 0 && checkingFigure.getJ() - 2 <= 7) {
            if (!board.board[checkingFigure.getI() - 1][checkingFigure.getJ() - 2].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() - 2].getColor().equals("black") &&
                    board.board[checkingFigure.getI() - 1][checkingFigure.getJ() - 2].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - 1, checkingFigure.getJ() - 2)) {
                    System.out.println("    Белую фигуру можно побить конем слева верх");
                    return false;
                }
            }
        }

        if (checkingFigure.getI() - 2 >= 0 && checkingFigure.getI() - 2 <= 7 && checkingFigure.getJ() - 1 >= 0 && checkingFigure.getJ() - 1 <= 7) {
            if (!board.board[checkingFigure.getI() - 2][checkingFigure.getJ() - 1].getName().equals("empty") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() - 1].getColor().equals("black") &&
                    board.board[checkingFigure.getI() - 2][checkingFigure.getJ() - 1].getClass().getName().equals("Knight")) {
                if (!checkingChachAfterBlackFigureTryStopChack(x, y, checkingFigure.getI() - 2, checkingFigure.getJ() - 1)){
                    System.out.println("    Белую фигуру можно побить конем сверху слева");
                    return false;
                }
            }
        }
        System.out.println("Нет возможности побить шахующую фигуру");
        return true;
    }//=

    public static boolean checkingChachAfterBlackFigureTryStopChack(int x, int y, int xbF, int ybF){
        if (x > xbF && y == ybF){

            for (int a = 1; a <= xbF; a++){// проверка сверху
                if (!board.board[xbF - a][ybF].getName().equals("empty") &&
                        board.board[xbF - a][ybF].getColor().equals("black")){
                    break;
                }
                if (!board.board[xbF - a][ybF].getName().equals("empty") &&
                        board.board[xbF - a][ybF].getColor().equals("white") &&
                        !(board.board[xbF - a][ybF].getClass().getName().equals("Queen") || board.board[xbF - a][ybF].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xbF - a][ybF].getName().equals("empty") &&
                        board.board[xbF - a][ybF].getColor().equals("white") &&
                        (board.board[xbF - a][ybF].getClass().getName().equals("Queen") || board.board[xbF - a][ybF].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура сверху");
                    return true;
                }
            }

        }
        if (x == xbF && y < ybF){

            for (int a = 1; a < 8 - ybF; a++){// проверка справа
                if (!board.board[xbF][ybF + a].getName().equals("empty") &&
                        board.board[xbF][ybF + a].getColor().equals("black")){
                    break;
                }
                if (!board.board[xbF][ybF + a].getName().equals("empty") &&
                        board.board[xbF][ybF + a].getColor().equals("white") &&
                        !(board.board[xbF][ybF + a].getClass().getName().equals("Queen") || board.board[xbF][ybF + a].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xbF][ybF + a].getName().equals("empty") &&
                        board.board[xbF][ybF + a].getColor().equals("white") &&
                        (board.board[xbF][ybF + a].getClass().getName().equals("Queen") || board.board[xbF][ybF + a].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура справа");
                    return true;
                }
            }

        }
        if (x < xbF && y == ybF){

            for (int a = 1; a < 8 - xbF; a++){// проверка снизу
                if (!board.board[xbF + a][ybF].getName().equals("empty") &&
                        board.board[xbF + a][ybF].getColor().equals("black")){
                    break;
                }
                if (!board.board[xbF + a][ybF].getName().equals("empty") &&
                        board.board[xbF + a][ybF].getColor().equals("white") &&
                        !(board.board[xbF + a][ybF].getClass().getName().equals("Queen") || board.board[xbF + a][ybF].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xbF + a][ybF].getName().equals("empty") &&
                        board.board[xbF + a][ybF].getColor().equals("white") &&
                        (board.board[xbF + a][ybF].getClass().getName().equals("Queen") || board.board[xbF + a][ybF].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура снизу");
                    return true;
                }
            }

        }
        if (x == xbF && y > ybF){

            for (int a = 1; a <= ybF; a++){// проверка слева
                if (!board.board[xbF][ybF - a].getName().equals("empty") &&
                        board.board[xbF][ybF - a].getColor().equals("black")){
                    break;
                }
                if (!board.board[xbF][ybF - a].getName().equals("empty") &&
                        board.board[xbF][ybF - a].getColor().equals("white") &&
                        !(board.board[xbF][ybF - a].getClass().getName().equals("Queen") || board.board[xbF][ybF - a].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[xbF][ybF - a].getName().equals("empty") &&
                        board.board[xbF][ybF - a].getColor().equals("white") &&
                        (board.board[xbF][ybF - a].getClass().getName().equals("Queen") || board.board[xbF][ybF - a].getClass().getName().equals("Rook"))){
                    System.out.println("    Фигуру перемещать нельзя шахует фигура слева");
                    return true;
                }
            }

        }
        if (x > xbF && y < ybF && x - xbF == ybF - y) {
            for (int a = 1; a < 8 - ybF; a++) { //клетки которые проверяем вверх вправо
                if (xbF - a >= 0 && ybF + a <= 7) { //ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xbF - a][ybF + a].getName().equals("empty") &&
                            board.board[xbF - a][ybF + a].getColor().equals("black")) {
                        break;
                    }
                    if (!board.board[xbF - a][ybF + a].getName().equals("empty") &&
                            board.board[xbF - a][ybF + a].getColor().equals("white") &&
                            !(board.board[xbF - a][ybF + a].getClass().getName().equals("Queen") || board.board[xbF - a][ybF + a].getClass().getName().equals("Bishop"))) {
                        break;
                    }
                    if (!board.board[xbF - a][ybF + a].getName().equals("empty") &&
                            board.board[xbF - a][ybF + a].getColor().equals("white") &&
                            (board.board[xbF - a][ybF + a].getClass().getName().equals("Queen") || board.board[xbF - a][ybF + a].getClass().getName().equals("Bishop"))) {
                        System.out.println("    Закрыться фигурой справа вверху нельзя");
                        return true;
                    }
                }
            }
        }
        if (x < xbF && y < ybF && xbF - x == ybF - y) {
            for (int a = 1; a < 8 - ybF; a++){ //клетки, которые проверяем вниз вправо
                if (xbF + a <= 7 && ybF + a <= 7){//ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xbF + a][ybF + a].getName().equals("empty") &&
                            board.board[xbF + a][ybF + a].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[xbF + a][ybF + a].getName().equals("empty") &&
                            board.board[xbF + a][ybF + a].getColor().equals("white") &&
                            !(board.board[xbF + a][ybF + a].getClass().getName().equals("Queen") || board.board[xbF + a][ybF + a].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[xbF + a][ybF + a].getName().equals("empty") &&
                            board.board[xbF + a][ybF + a].getColor().equals("white") &&
                            (board.board[xbF + a][ybF + a].getClass().getName().equals("Queen") || board.board[xbF + a][ybF + a].getClass().getName().equals("Bishop"))){
                        System.out.println("    От шаха нельзя закрыться фигурой справа внизу");
                        return false;
                    }
                }
            }
        }
        if (x < xbF && y > ybF && xbF - x == y - ybF) {
            for (int a = 1; a <= ybF; a++) {  //клетки, которые проверяем вниз влеаво
                if (xbF + a <= 7 && ybF - a >= 0) { //ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xbF + a][ybF - a].getName().equals("empty") &&
                            board.board[xbF + a][ybF - a].getColor().equals("black")) {
                        break;
                    }
                    if (!board.board[xbF + a][ybF - a].getName().equals("empty") &&
                            board.board[xbF + a][ybF - a].getColor().equals("white") &&
                            !(board.board[xbF + a][ybF - a].getClass().getName().equals("Queen") || board.board[xbF + a][ybF - a].getClass().getName().equals("Bishop"))) {
                        break;
                    }
                    if (!board.board[xbF + a][ybF - a].getName().equals("empty") &&
                            board.board[xbF + a][ybF - a].getColor().equals("white") &&
                            (board.board[xbF + a][ybF - a].getClass().getName().equals("Queen") || board.board[xbF + a][ybF - a].getClass().getName().equals("Bishop"))) {
                        System.out.println("    От шаха нельзя закрыться фигурой слева внизу");
                        return false;
                    }
                }
            }
        }
        if (x > xbF && y > ybF && x - xbF == y - ybF) {
            for (int a = 1; a <= ybF; a++) {  //клетки, которые проверяем вверх влево
                if (xbF - a >= 0 && ybF - a >= 0) {//ограничения, чтобы не выйти за пределы доски
                    if (!board.board[xbF - a][ybF - a].getName().equals("empty") &&
                            board.board[xbF - a][ybF - a].getColor().equals("black")) {
                        break;
                    }
                    if (!board.board[xbF - a][ybF - a].getName().equals("empty") &&
                            board.board[xbF - a][ybF - a].getColor().equals("white") &&
                            !(board.board[xbF - a][ybF - a].getClass().getName().equals("Queen") || board.board[xbF - a][ybF - a].getClass().getName().equals("Bishop"))) {
                        break;
                    }
                    if (!board.board[xbF - a][ybF - a].getName().equals("empty") &&
                            board.board[xbF - a][ybF - a].getColor().equals("white") &&
                            (board.board[xbF - a][ybF - a].getClass().getName().equals("Queen") || board.board[xbF - a][ybF - a].getClass().getName().equals("Bishop"))) {
                        System.out.println("    От шаха нельзя закрыться фигурой слева вверху");
                        return false;
                    }
                }
            }
        }
        return false;
    }//=

    public static void checkBlackKingMat(){
        checkDoubleBlackKingChakh();
        if (checkBlackKingMatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ()) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ() + 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI(), board.bK.getPositionJ() + 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ() + 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ()) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ() - 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI(), board.bK.getPositionJ() - 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ() - 1) &&
                countChack >= 2){
            board.bK.mat = true;
            stopListener = true;
            blackKingMatFrame();
            System.out.println("Black king Chachmat если кыйкт");
            return;
        }
        if (checkBlackKingMatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ()) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ() + 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI(), board.bK.getPositionJ() + 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ() + 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ()) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ() - 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI(), board.bK.getPositionJ() - 1) &&
                checkBlackKingMatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ() - 1) &&
                findBlackFigureWhoKilledWhiteChackingFigure() &&
                checkBlackKingMatByStopWay(board.bK.getPositionI(), board.bK.getPositionJ(), checkingFigure.getI(), checkingFigure.getJ())){
            board.bK.mat = true;
            stopListener = true;
            blackKingMatFrame();
            System.out.println("Black king Chachmat");
    }
        else{
            System.out.println("Черному королю мата нет");
        }

    }//=
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void printChack(){

        if (!upsidedown) {
            if (hod && board.wK.shah && !guiBoard.buttons[board.wK.getPositionI()][board.wK.getPositionJ()].getBackground().equals(Color.red)) {
                kingPlaceColor = guiBoard.buttons[board.wK.getPositionI()][board.wK.getPositionJ()].getBackground();
                guiBoard.buttons[board.wK.getPositionI()][board.wK.getPositionJ()].setBackground(Color.red);
                System.out.println("Change white king place color for red");
                System.out.println("PR Белому королю шах");
            }
            if (hod && !board.wK.shah) {
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (guiBoard.buttons[i][j].getBackground().equals(Color.red)) {
                            guiBoard.buttons[i][j].setBackground(kingPlaceColor);
                        }
                    }
                }
                System.out.println("upsidedown false: Another if for white");
            }
            if (!hod && board.bK.shah && !guiBoard.buttons[board.bK.getPositionI()][board.bK.getPositionJ()].getBackground().equals(Color.red)) {
                kingPlaceColor = guiBoard.buttons[board.bK.getPositionI()][board.bK.getPositionJ()].getBackground();
                guiBoard.buttons[board.bK.getPositionI()][board.bK.getPositionJ()].setBackground(Color.red);
                System.out.println("false Change black king place color for red");
                System.out.println("PR Черному королю шах");
            }
            if (!hod && !board.bK.shah) {
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (guiBoard.buttons[i][j].getBackground().equals(Color.red)) {
                            guiBoard.buttons[i][j].setBackground(kingPlaceColor);
                        }
                    }
                }
                System.out.println("upsidedown false: Another if for black");
            }
        }

        if (upsidedown) {
            if (hod && board.wK.shah && !guiBoard.buttons[7 - board.wK.getPositionI()][7 - board.wK.getPositionJ()].getBackground().equals(Color.red)) {
                kingPlaceColor = guiBoard.buttons[7 - board.wK.getPositionI()][7 - board.wK.getPositionJ()].getBackground();
                guiBoard.buttons[7 - board.wK.getPositionI()][7 - board.wK.getPositionJ()].setBackground(Color.red);
                System.out.println("Change white king place color for red");
                System.out.println("PR Белому королю шах");
            }
            if (hod && !board.wK.shah) {
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (guiBoard.buttons[7 - i][7 - j].getBackground().equals(Color.red)) {
                            guiBoard.buttons[7 - i][7 - j].setBackground(kingPlaceColor);
                        }
                    }
                }
                System.out.println("upsidedown true: Another if for white");
            }
            if (!hod && board.bK.shah && !guiBoard.buttons[7 - board.bK.getPositionI()][7 - board.bK.getPositionJ()].getBackground().equals(Color.red)) {
                kingPlaceColor = guiBoard.buttons[7 - board.bK.getPositionI()][7 - board.bK.getPositionJ()].getBackground();
                guiBoard.buttons[7 - board.bK.getPositionI()][7 - board.bK.getPositionJ()].setBackground(Color.red);
                System.out.println("true Change black king place color for red");
                System.out.println("PR Черному королю шах");
            }
            if (!hod && !board.bK.shah) {
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (guiBoard.buttons[7 - i][7 - j].getBackground().equals(Color.red)) {
                            guiBoard.buttons[7 - i][7 - j].setBackground(kingPlaceColor);
                        }
                    }
                }
                System.out.println("upsidedown true: Another if for black");
            }
        }
    }

    public static  void cleanBoardFromRedColor(){
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (guiBoard.buttons[i][j].getBackground().equals(Color.red)) {
                    guiBoard.buttons[i][j].setBackground(kingPlaceColor);
                }
            }
        }
        System.out.println("Clean board from red color");
    }

    public static boolean returnChackValue(){
        if (hod && board.wK.shah){
            return true;
        }
        if (!hod && board.bK.shah){
            return true;
        }
        return false;
    }

    public static void checkTheEndOfTheWayOfThePawn() {

        if (hodtoi == 0 && board.board[hodtoi][hodtoj].getClass().getName().equals("Pawn") && board.board[hodtoi][hodtoj].getColor().equals("white")){

            stopListener = !stopListener;
            chooseWhiteFigureInsteadThePawn.chooseWhiteFigureFromTheWindow();

            while(!pawnCheking){
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e){}
            }

            pawnCheking = !pawnCheking;
            stopListener = !stopListener;
        }

        if (hodtoi == 7 && board.board[hodtoi][hodtoj].getClass().getName().equals("Pawn") && board.board[hodtoi][hodtoj].getColor().equals("black")){

            stopListener = !stopListener;
            chooseBlackFigureInsteadThePawn.chooseBlackFigureFromTheWindow();

            while(!pawnCheking){
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e){}
            }

            pawnCheking = !pawnCheking;
            stopListener = !stopListener;
        }
    }

    public static void checkDoubleWhiteKingChakh(){

        countChack = 0;

        for (int i = board.wK.getPositionI() - 1; i >= 0; i--){

            int j = board.wK.getPositionJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Белому королю шах от фигуры прямо" + i + " " + j);
                countChack++;
                break;
            }
        }

        for (int j = board.wK.getPositionJ() + 1; j <= 7; j++){

            int i = board.wK.getPositionI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))   ){
                break;
            }

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Белому королю шах от фигуры справа"  + i + " " + j);
                countChack++;
                break;
            }
        }

        for (int i = board.wK.getPositionI() + 1; i <= 7; i++){

            int j = board.wK.getPositionJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Белому королю шах от фигуры сзади"  + i + " " + j);
                countChack++;
                break;
            }
        }

        for (int j = board.wK.getPositionJ() - 1; j >= 0; j--){

            int i = board.wK.getPositionI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    !(board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black") &&
                    (board.board[i][j].getName().equals("bQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Белому королю шах от фигуры слева"  + i + " " + j);
                countChack++;
                break;
            }
        }

        //if (i > toI & j < toJ) {
        for (int r = 1; r < 8 - board.wK.getPositionJ() ; r++) {
            if (board.wK.getPositionI() - r >= 0 && board.wK.getPositionJ() + r <= 7){
                if (r == 1 && !board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("Pawn")){
                    System.out.println("Double: Белому королю шах от черной пешки справа вверху");
                    countChack++;
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Белому королю шах от фигуры справа вверху");
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j < toJ)
        for (int r = 1; r < 8 - board.wK.getPositionJ() ; r++) {
            if (board.wK.getPositionI() + r <= 7 && board.wK.getPositionJ() + r <= 7){
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Белому королю шах от фигуры справа внизу");
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j > toJ)
        for (int r = 1; r <= board.wK.getPositionJ(); r++) {
            if (board.wK.getPositionI() + r <= 7 && board.wK.getPositionJ() - r >= 0){
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() + r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Белому королю шах от фигуры слева внизу");
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        //if (i > toI & j > toJ) {
        for (int r = 1; r <= board.wK.getPositionJ() ; r++) {
            if (board.wK.getPositionI() - r >= 0 && board.wK.getPositionJ() - r >= 0){
                if (r == 1 && !board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("Pawn")){
                    System.out.println("Double: Шах от черной пешки слева вверху");
                    countChack++;
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") && board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("white")){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") && board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        !(board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getColor().equals("black") &&
                        (board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getName().equals("bQueen") || board.board[board.wK.getPositionI() - r][board.wK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Белому королю шах от фигуры слева вверху");
                    board.wK.shah = true;
                    checkingFigure.setPosition(board.wK.getPositionI() - r, board.wK.getPositionJ() - r);
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        if (board.wK.getPositionI() - 2 >= 0 && board.wK.getPositionI() - 2 <= 7 && board.wK.getPositionJ() + 1 >= 0 && board.wK.getPositionJ() + 1 <= 7){
            if (!board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() + 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() + 1].getClass().getName().equals("Knight")){
                System.out.println("Double: Белому королю шах от коня сверху справа");
                countChack++;
            }
        }

        if (board.wK.getPositionI() - 1 >= 0 && board.wK.getPositionI() - 1 <= 7 && board.wK.getPositionJ() + 2 >= 0 && board.wK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() + 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня справа вверх");
                countChack++;
            }
        }

        if (board.wK.getPositionI() + 1 >= 0 && board.wK.getPositionI() + 1 <= 7 && board.wK.getPositionJ() + 2 >= 0 && board.wK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() + 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня справа низ");
                countChack++;
            }
        }

        if (board.wK.getPositionI() + 2 >= 0 && board.wK.getPositionI() + 2 <= 7 && board.wK.getPositionJ() + 1 >= 0 && board.wK.getPositionJ() + 1 <= 7) {
            if (!board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() + 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() + 1].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня снизу справа");
                countChack++;
            }
        }

        if (board.wK.getPositionI() + 2 >= 0 && board.wK.getPositionI() + 2 <= 7 && board.wK.getPositionJ() - 1 >= 0 && board.wK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() - 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 2][board.wK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня снизу лево");
                countChack++;
            }
        }

        if (board.wK.getPositionI() + 1 >= 0 && board.wK.getPositionI() + 1 <= 7 && board.wK.getPositionJ() - 2 >= 0 && board.wK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() - 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() + 1][board.wK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня слева низ");
                countChack++;
            }
        }

        if (board.wK.getPositionI() - 1 >= 0 && board.wK.getPositionI() - 1 <= 7 && board.wK.getPositionJ() - 2 >= 0 && board.wK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() - 2].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 1][board.wK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня слева верх");
                countChack++;
            }
        }

        if (board.wK.getPositionI() - 2 >= 0 && board.wK.getPositionI() - 2 <= 7 && board.wK.getPositionJ() - 1 >= 0 && board.wK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() - 1].getColor().equals("black") &&
                    board.board[board.wK.getPositionI() - 2][board.wK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Double: Белому королю шах от коня сверху слева");
                countChack++;
            }
        }
    }//=

    public static void checkDoubleBlackKingChakh(){

        countChack = 0;

        for (int i = board.bK.getPositionI() - 1; i >= 0; i--){

            int j = board.bK.getPositionJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Черному королю шах от фигуры прямо" + i + 1 + " " + j + 1);
                countChack++;
                break;
            }
        }

        for (int j = board.bK.getPositionJ() + 1; j <= 7; j++){

            int i = board.bK.getPositionI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))   ){
                break;
            }

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Черному королю шах от фигуры справа" + i + 1 + " " + j + 1);
                countChack++;
                break;
            }
        }

        for (int i = board.bK.getPositionI() + 1; i <= 7; i++){

            int j = board.bK.getPositionJ();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Черному королю шах от фигуры внизу" + i + 1 + " " + j + 1);
                countChack++;
                break;
            }
        }

        for (int j = board.bK.getPositionJ() - 1; j >= 0; j--){

            int i = board.bK.getPositionI();

            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("black")){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    !(board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                break;
            }
            if (!board.board[i][j].getName().equals("empty") && board.board[i][j].getColor().equals("white") &&
                    (board.board[i][j].getName().equals("wQueen") || board.board[i][j].getClass().getName().equals("Rook"))){
                System.out.println("Double: Черному королю шах от фигуры слева" + i + 1 + " " + j + 1);
                countChack++;
                break;
            }
        }

        //if (i > toI & j < toJ) {
        for (int r = 1; r < 8 - board.bK.getPositionJ() ; r++) {
            if (board.bK.getPositionI() - r >= 0 && board.bK.getPositionJ() + r <= 7){

                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Черному королю шах от фигуры справа вверху");
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j < toJ)
        for (int r = 1; r < 8 - board.bK.getPositionJ() ; r++) {
            if (board.bK.getPositionI() + r <= 7 && board.bK.getPositionJ() + r <= 7){
                if (r == 1 && !board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("Pawn")){
                    System.out.println("Double: Шах от белой пешки справа внизу");
                    countChack++;
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() + r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Черному королю шах от фигуры справа внизу");
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        //if (i < toI & j > toJ)
        for (int r = 1; r <= board.bK.getPositionJ(); r++) {
            if (board.bK.getPositionI() + r <= 7 && board.bK.getPositionJ() - r >= 0){
                if (r == 1 && !board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("Pawn")){
                    System.out.println("Double: Шах от белой пешки слева внизу");
                    countChack++;
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() + r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Черному шах от фигуры слева внизу");
                    countChack++;
                    break;
                }
            }
            else{
                break;
            }
        }

        //if (i > toI & j > toJ) {
        for (int r = 1; r <= board.bK.getPositionJ() ; r++) {
            if (board.bK.getPositionI() - r >= 0 && board.bK.getPositionJ() - r >= 0){

                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("black")){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        !(board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    break;
                }
                if (!board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("empty") &&
                        board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getColor().equals("white") &&
                        (board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getName().equals("wQueen") || board.board[board.bK.getPositionI() - r][board.bK.getPositionJ() - r].getClass().getName().equals("Bishop"))){
                    System.out.println("Double: Черному королю шах от фигуры слева вверху");
                    countChack++;
                    break;

                }
            }
            else{
                break;
            }
        }

        if (board.bK.getPositionI() - 2 >= 0 && board.bK.getPositionI() - 2 <= 7 && board.bK.getPositionJ() + 1 >= 0 && board.bK.getPositionJ() + 1 <= 7){
            if (!board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() + 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() + 1].getClass().getName().equals("Knight")){
                System.out.println("Double: Черному королю шах от коня сверху справа");
                countChack++;
            }
        }

        if (board.bK.getPositionI() - 1 >= 0 && board.bK.getPositionI() - 1 <= 7 && board.bK.getPositionJ() + 2 >= 0 && board.bK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() + 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня справа вверх");
                countChack++;
            }
        }

        if (board.bK.getPositionI() + 1 >= 0 && board.bK.getPositionI() + 1 <= 7 && board.bK.getPositionJ() + 2 >= 0 && board.bK.getPositionJ() + 2 <= 7) {
            if (!board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() + 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() + 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() + 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня справа низ");
                countChack++;
            }
        }

        if (board.bK.getPositionI() + 2 >= 0 && board.bK.getPositionI() + 2 <= 7 && board.bK.getPositionJ() + 1 >= 0 && board.bK.getPositionJ() + 1 <= 7) {
            if (!board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() + 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() + 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() + 1].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня снизу справа");
                countChack++;
            }
        }

        if (board.bK.getPositionI() + 2 >= 0 && board.bK.getPositionI() + 2 <= 7 && board.bK.getPositionJ() - 1 >= 0 && board.bK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() - 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 2][board.bK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня снизу лево");
                countChack++;
            }
        }

        if (board.bK.getPositionI() + 1 >= 0 && board.bK.getPositionI() + 1 <= 7 && board.bK.getPositionJ() - 2 >= 0 && board.bK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() - 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() + 1][board.bK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня слева низ");
                countChack++;
            }
        }

        if (board.bK.getPositionI() - 1 >= 0 && board.bK.getPositionI() - 1 <= 7 && board.bK.getPositionJ() - 2 >= 0 && board.bK.getPositionJ() - 2 <= 7) {
            if (!board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() - 2].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() - 2].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 1][board.bK.getPositionJ() - 2].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня слева верх");
                countChack++;
            }
        }

        if (board.bK.getPositionI() - 2 >= 0 && board.bK.getPositionI() - 2 <= 7 && board.bK.getPositionJ() - 1 >= 0 && board.bK.getPositionJ() - 1 <= 7) {
            if (!board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() - 1].getName().equals("empty") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() - 1].getColor().equals("white") &&
                    board.board[board.bK.getPositionI() - 2][board.bK.getPositionJ() - 1].getClass().getName().equals("Knight")) {
                System.out.println("Double: Черному королю шах от коня сверху слева");
                countChack++;
            }
        }
    }//=
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////фреймы пата и мата
    public static void whiteKingMatFrame(){
        JFrame frame = new JFrame("White King Checkmate");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(guiBoard);
        frame.setBackground(Color.red);
        JLabel label = new JLabel("White King Checkmate");
        //JButton button = new JButton("New Game");
        //button.addActionListener(new ListenerButtonNewGame());
        frame.setLayout(new GridBagLayout());
        frame.add(label);
        //frame.add(button);
        ImageIcon image = new ImageIcon(ChessBoard.class.getResource("Images/chess.png"));
        frame.setIconImage(image.getImage());
        frame.setVisible(true);
    }

    public static void blackKingMatFrame(){
        JFrame frame = new JFrame("Black King Checkmate");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(guiBoard);
        frame.setBackground(Color.red);
        JLabel label = new JLabel("Black King Checkmate");
        //JButton button = new JButton("New Game");
        //button.addActionListener(new ListenerButtonNewGame());
        frame.setLayout(new GridBagLayout());
        frame.add(label);
        //frame.add(button);
        ImageIcon image = new ImageIcon(ChessBoard.class.getResource("Images/chess.png"));
        frame.setIconImage(image.getImage());
        frame.setVisible(true);
    }

    public static void whiteKingPatFrame(){
        JFrame frame = new JFrame("White King Pat");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(guiBoard);
        frame.setBackground(Color.red);
        JLabel label = new JLabel("White King Pat");
        //JButton button = new JButton("New Game");
        //button.addActionListener(new ListenerButtonNewGame());
        frame.setLayout(new GridBagLayout());
        frame.add(label);
        //frame.add(button);
        ImageIcon image = new ImageIcon(ChessBoard.class.getResource("Images/chess.png"));
        frame.setIconImage(image.getImage());
        frame.setVisible(true);
    }

    public static void blackKingPatFrame(){
        JFrame frame = new JFrame("Black King Pat");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(guiBoard);
        frame.setBackground(Color.red);
        JLabel label = new JLabel("Black King Pat");
        //JButton button = new JButton("New Game");
        //button.addActionListener(new ListenerButtonNewGame());
        frame.setLayout(new GridBagLayout());
        frame.add(label);
        //frame.add(button);
        ImageIcon image = new ImageIcon(ChessBoard.class.getResource("Images/chess.png"));
        frame.setIconImage(image.getImage());
        frame.setVisible(true);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////проверка пата
    public static void checkPat(){
        if (hod && !board.wK.shah){
            checkWhiteKingPat();
        }
        if (!hod && !board.bK.shah){
            checkBlackKingPat();
        }
    }

    public static boolean checkWhiteKingPat(){
        if (!board.wK.shah &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ()) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ() + 1) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI(), board.wK.getPositionJ() + 1) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ() + 1) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ()) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI() + 1, board.wK.getPositionJ() - 1) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI(), board.wK.getPositionJ() - 1) &&
                checkWhiteKingPatByKingStep(board.wK.getPositionI() - 1, board.wK.getPositionJ() - 1)
                && !findWhiteFigureOnTheBoardPAT()){
            whiteKingPatFrame();
            board.wK.pat = true;
            System.out.println("White King Pat");
            return true;
        }
        System.out.println("Пата нет");
        return false;
    }//=

    public static boolean checkBlackKingPat(){
        if (!board.bK.shah &&
                checkBlackKingPatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ()) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ() + 1) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI(), board.bK.getPositionJ() + 1) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ() + 1) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ()) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI() + 1, board.bK.getPositionJ() - 1) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI(), board.bK.getPositionJ() - 1) &&
                checkBlackKingPatByKingStep(board.bK.getPositionI() - 1, board.bK.getPositionJ() - 1) &&
                !findBlackFigureOnTheBoardPAT()){
            blackKingPatFrame();
            board.bK.pat = true;
            System.out.println("Black King Pat");
            return true;
        }
        System.out.println("Пата нет");
        return false;
    }//=

    public static boolean checkWhiteKingPatByKingStep(int x, int y){

        if (0 <= x && x <= 7 && 0 <= y && y <= 7) {

            if (!board.board[x][y].getName().equals("empty") && board.board[x][y].getColor().equals("white")){
                System.out.println("    Пат: Клетка занята своей фигурой");
                return true;
            }

            for (int i = x - 1; i >= 0; i--){

                if (i == x - 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("black") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Пат: Белому королю шах от черного короля вверху");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        !(board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        (board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Белому королю шах от фигуры прямо");
                    return true;
                }
            }

            for (int j = y + 1; j <= 7; j++){

                if (j == y + 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("black") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Пат: Белому королю шах от черного короля справа");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        !(board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))   ){
                    break;
                }

                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        (board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Белому королю шах от фигуры справа");
                    return true;
                }
            }

            for (int i = x + 1; i <= 7; i++){

                if (i == x + 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("black") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Пат: Белому королю шах от черного короля сзади");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        !(board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black") &&
                        (board.board[i][y].getName().equals("bQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Белому королю шах от фигуры сзади");
                    return true;
                }
            }

            for (int j = y - 1; j >= 0; j--){

                if (j == y - 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("black") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Пат: Белому королю шах от черного короля слева");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        !(board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black") &&
                        (board.board[x][j].getName().equals("bQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Белому королю шах от фигуры слева");
                    return true;
                }
            }

            for (int r = 1; r < 8 - y ; r++) {
                if (x - r >= 0 && y + r <= 7){
                    if (r == 1 && !board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            board.board[x - r][y + r].getClass().getName().equals("Pawn")){
                        System.out.println("    Пат: Белому королю шах от черной пешки справа вверху");
                        return true;
                    }
                    if (r == 1 && !board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            board.board[x - r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Белому королю шах от черного короля справа вверху");
                        return true;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            !(board.board[x - r][y + r].getName().equals("bQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black") &&
                            (board.board[x - r][y + r].getName().equals("bQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Белому королю шах от фигуры справа вверху");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            for (int r = 1; r < 8 - y ; r++) {
                if (x + r <= 7 && y + r <= 7){
                    if (r == 1 && !board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black") &&
                            board.board[x + r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Белому королю шах от черного короля справа внизу");
                        return true;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black") &&
                            !(board.board[x + r][y + r].getName().equals("bQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black") &&
                            (board.board[x + r][y + r].getName().equals("bQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Белому королю шах от фигуры справа внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            for (int r = 1; r <= y; r++) {
                if (x + r <= 7 && y - r >= 0){
                    if (r == 1 && !board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black") &&
                            board.board[x + r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Белому королю шах от черного короля слева внизу");
                        return true;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black") &&
                            !(board.board[x + r][y - r].getName().equals("bQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black") &&
                            (board.board[x + r][y - r].getName().equals("bQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Белому королю шах от фигуры слева внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            for (int r = 1; r <= y ; r++) {
                if (x - r >= 0 && y - r >= 0){
                    if (r == 1 && !board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black") &&
                            board.board[x - r][y - r].getClass().getName().equals("Pawn")){
                        System.out.println("    Пат: Шах от черной пешки слева вверху");
                        return true;
                    }
                    if (r == 1 && !board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black") &&
                            board.board[x - r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Белому королю шах от черного короля слева вверху");
                        return true;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") && board.board[x - r][y - r].getColor().equals("white")){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") && board.board[x - r][y - r].getColor().equals("black") &&
                            !(board.board[x - r][y - r].getName().equals("bQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black") &&
                            (board.board[x - r][y - r].getName().equals("bQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Белому королю шах от фигуры слева вверху");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y + 1 >= 0 && y + 1 <= 7){
                if (!board.board[x - 2][y + 1].getName().equals("empty") &&
                        board.board[x - 2][y + 1].getColor().equals("black") &&
                        board.board[x - 2][y + 1].getClass().getName().equals("Knight")){
                    System.out.println("    Пат: Белому королю шах от коня сверху справа");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x - 1][y + 2].getName().equals("empty") &&
                        board.board[x - 1][y + 2].getColor().equals("black") &&
                        board.board[x - 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня справа вверх");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x + 1][y + 2].getName().equals("empty") &&
                        board.board[x + 1][y + 2].getColor().equals("black") &&
                        board.board[x + 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня справа низ");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y + 1 >= 0 && y + 1 <= 7) {
                if (!board.board[x + 2][y + 1].getName().equals("empty") &&
                        board.board[x + 2][y + 1].getColor().equals("black") &&
                        board.board[x + 2][y + 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня снизу справа");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x + 2][y - 1].getName().equals("empty") &&
                        board.board[x + 2][y - 1].getColor().equals("black") &&
                        board.board[x + 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня снизу лево");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x + 1][y - 2].getName().equals("empty") &&
                        board.board[x + 1][y - 2].getColor().equals("black") &&
                        board.board[x + 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня слева низ");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x - 1][y - 2].getName().equals("empty") &&
                        board.board[x - 1][y - 2].getColor().equals("black") &&
                        board.board[x - 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня слева верх");
                    return true;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x - 2][y - 1].getName().equals("empty") &&
                        board.board[x - 2][y - 1].getColor().equals("black") &&
                        board.board[x - 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Белому королю шах от коня сверху слева");
                    return true;
                }
            }

        }
        else {
            System.out.println("    Пат: Выход за пределы доски");
            return true;
        }
        return false;
    }//=

    public static boolean checkBlackKingPatByKingStep(int x, int y){
        if (0 <= x && x <= 7 && y >=0 && y <=7){

            if (!board.board[x][y].getName().equals("empty") && board.board[x][y].getColor().equals("black")){
                System.out.println("    Пат: Своя фигура");
                return true;
            }

            for (int i = x - 1; i >= 0; i--){
                if (i == x - 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("white") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Пат: Черному королю шах от белого короля вверху");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        !(board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        (board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Черному королю шах от фигуры прямо");
                    return true;
                }
            }

            for (int j = y + 1; j <= 7; j++){
                if (j == y + 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("white") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Пат: Черному королю шах от белого короля справа");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        !(board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))   ){
                    break;
                }

                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        (board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Черному королю шах от фигуры справа");
                    return true;
                }
            }

            for (int i = x + 1; i <= 7; i++){
                if (i == x + 1 && !board.board[i][y].getName().equals("empty") &&
                        board.board[i][y].getColor().equals("white") && board.board[i][y].getClass().getName().equals("King")){
                    System.out.println("    Пат: Черному королю шах от белого короля внизу");
                    return true;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("black")){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        !(board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[i][y].getName().equals("empty") && board.board[i][y].getColor().equals("white") &&
                        (board.board[i][y].getName().equals("wQueen") || board.board[i][y].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Черному королю шах от фигуры внизу");
                    return true;
                }
            }

            for (int j = y - 1; j >= 0; j--){
                if (j == y - 1 && !board.board[x][j].getName().equals("empty") &&
                        board.board[x][j].getColor().equals("white") && board.board[x][j].getClass().getName().equals("King")){
                    System.out.println("    Пат: Черному королю шах от белого короля слева");
                    return true;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("black")){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        !(board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    break;
                }
                if (!board.board[x][j].getName().equals("empty") && board.board[x][j].getColor().equals("white") &&
                        (board.board[x][j].getName().equals("wQueen") || board.board[x][j].getClass().getName().equals("Rook"))){
                    System.out.println("    Пат: Черному королю шах от фигуры слева");
                    return true;
                }
            }

            //if (i > toI & j < toJ) {
            for (int r = 1; r < 8 - y ; r++) {
                if (x - r >= 0 && y + r <= 7){

                    if (r == 1 && !board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white") &&
                            board.board[x - r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Черному королю шах от белого короля справа вверху");
                        return true;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white") &&
                            !(board.board[x - r][y + r].getName().equals("wQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y + r].getName().equals("empty") &&
                            board.board[x - r][y + r].getColor().equals("white") &&
                            (board.board[x - r][y + r].getName().equals("wQueen") || board.board[x - r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Черному королю шах от фигуры справа вверху");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            //if (i < toI & j < toJ)
            for (int r = 1; r < 8 - y ; r++) {
                if (x + r <= 7 && y + r <= 7){
                    if (r == 1 && !board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            board.board[x + r][y + r].getClass().getName().equals("Pawn")){
                        System.out.println("    Пат: Шах от белой пешки справа внизу");
                        return true;
                    }
                    if (r == 1 && !board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            board.board[x + r][y + r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Черному королю шах от белого короля справа внизу");
                        return true;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            !(board.board[x + r][y + r].getName().equals("wQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y + r].getName().equals("empty") &&
                            board.board[x + r][y + r].getColor().equals("white") &&
                            (board.board[x + r][y + r].getName().equals("wQueen") || board.board[x + r][y + r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Черному королю шах от фигуры справа внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            //if (i < toI & j > toJ)
            for (int r = 1; r <= y; r++) {
                if (x + r <= 7 && y - r >= 0){
                    if (r == 1 && !board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            board.board[x + r][y - r].getClass().getName().equals("Pawn")){
                        System.out.println("    Пат: Шах от белой пешки слева внизу");
                        return true;
                    }
                    if (r == 1 && !board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            board.board[x + r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Черному королю шах от белого короля слева внизу");
                        return true;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            !(board.board[x + r][y - r].getName().equals("wQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x + r][y - r].getName().equals("empty") &&
                            board.board[x + r][y - r].getColor().equals("white") &&
                            (board.board[x + r][y - r].getName().equals("wQueen") || board.board[x + r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Черному шах от фигуры слева внизу");
                        return true;
                    }
                }
                else{
                    break;
                }
            }

            //if (i > toI & j > toJ) {
            for (int r = 1; r <= y ; r++) {
                if (x - r >= 0 && y - r >= 0){

                    if (r == 1 && !board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("white") &&
                            board.board[x - r][y - r].getClass().getName().equals("King")){
                        System.out.println("    Пат: Черному королю шах от белого короля слева вверху");
                        return true;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("black")){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("white") &&
                            !(board.board[x - r][y - r].getName().equals("wQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        break;
                    }
                    if (!board.board[x - r][y - r].getName().equals("empty") &&
                            board.board[x - r][y - r].getColor().equals("white") &&
                            (board.board[x - r][y - r].getName().equals("wQueen") || board.board[x - r][y - r].getClass().getName().equals("Bishop"))){
                        System.out.println("    Пат: Черному королю шах от фигуры слева вверху");
                        return true;

                    }
                }
                else{
                    break;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y + 1 >= 0 && y + 1 <= 7){
                if (!board.board[x - 2][y + 1].getName().equals("empty") &&
                        board.board[x - 2][y + 1].getColor().equals("white") &&
                        board.board[x - 2][y + 1].getClass().getName().equals("Knight")){
                    System.out.println("    Пат: Черному королю шах от коня сверху справа");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x - 1][y + 2].getName().equals("empty") &&
                        board.board[x - 1][y + 2].getColor().equals("white") &&
                        board.board[x - 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня справа вверх");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y + 2 >= 0 && y + 2 <= 7) {
                if (!board.board[x + 1][y + 2].getName().equals("empty") &&
                        board.board[x + 1][y + 2].getColor().equals("white") &&
                        board.board[x + 1][y + 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня справа низ");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y + 1 >= 0 && y + 1 <= 7) {
                if (!board.board[x + 2][y + 1].getName().equals("empty") &&
                        board.board[x + 2][y + 1].getColor().equals("white") &&
                        board.board[x + 2][y + 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня снизу справа");
                    return true;
                }
            }

            if (x + 2 >= 0 && x + 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x + 2][y - 1].getName().equals("empty") &&
                        board.board[x + 2][y - 1].getColor().equals("white") &&
                        board.board[x + 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня снизу лево");
                    return true;
                }
            }

            if (x + 1 >= 0 && x + 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x + 1][y - 2].getName().equals("empty") &&
                        board.board[x + 1][y - 2].getColor().equals("white") &&
                        board.board[x + 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня слева низ");
                    return true;
                }
            }

            if (x - 1 >= 0 && x - 1 <= 7 && y - 2 >= 0 && y - 2 <= 7) {
                if (!board.board[x - 1][y - 2].getName().equals("empty") &&
                        board.board[x - 1][y - 2].getColor().equals("white") &&
                        board.board[x - 1][y - 2].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня слева верх");
                    return true;
                }
            }

            if (x - 2 >= 0 && x - 2 <= 7 && y - 1 >= 0 && y - 1 <= 7) {
                if (!board.board[x - 2][y - 1].getName().equals("empty") &&
                        board.board[x - 2][y - 1].getColor().equals("white") &&
                        board.board[x - 2][y - 1].getClass().getName().equals("Knight")) {
                    System.out.println("    Пат: Черному королю шах от коня сверху слева");
                    return true;
                }
            }
        }
        else{
            System.out.println("    Пат: Выход за пределы доски");
            return true;
        }
        return false;
        //для методов мат добавить для короля все клетки
    }//=

    public static boolean findWhiteFigureOnTheBoardPAT(){
        int x = board.wK.getPositionI();
        int y = board.wK.getPositionJ();

        for (int i = 0; i <= 7; i++){
            for (int j = 0; j <= 7; j++){
                 if (board.board[i][j].getColor().equals("white") &&
                         board.board[i][j].getClass().getName().equals("Pawn") &&
                         !checkingChachAfterWhiteFigureStopTheWay(x, y, i, j) &&
                         board.board[i - 1][j].getName().equals("empty")){
                     return true;
                 }
                if (board.board[i][j].getColor().equals("white") &&
                        !board.board[i][j].getClass().getName().equals("Pawn") &&
                        !board.board[i][j].getClass().getName().equals("King") &&
                        !conditionsPAT(x, y, i, j) &&
                        findStepForWhiteFigure(i, j)//имеет свободные клетки для хода
                        ){
                    return true;
                }
                if (board.board[i][j].getColor().equals("white") &&
                        !board.board[i][j].getClass().getName().equals("Pawn") &&
                        !board.board[i][j].getClass().getName().equals("King") &&
                        findStepForWhiteFigure(i, j) &&//имеет свободные клетки для хода
                        conditionsPAT(x, y, i, j) &&
                        !checkingChachAfterWhiteFigureStopTheWay(x, y, i, j)// нет фигуры за спиной
                        ){
                    return true;
                }
                if (board.board[i][j].getColor().equals("white") &&
                        !board.board[i][j].getClass().getName().equals("Pawn") &&
                        !board.board[i][j].getClass().getName().equals("King") &&
                        findStepForWhiteFigure(i, j) &&// фигура имеет клетки для хода
                        conditionsPAT(x, y, i, j) &&//условия выполнились
                        checkingChachAfterWhiteFigureStopTheWay(x, y, i, j) &&//фигура есть
                        findEmptyPlaceBetweenKingAndFigure(x, y, i, j)){
                    return true;//пата нет
                }

            }
        }
        return false;
    }//=

    public static boolean findBlackFigureOnTheBoardPAT(){
        int x = board.bK.getPositionI();
        int y = board.bK.getPositionJ();

        for (int i = 0; i <= 7; i++){
            for (int j = 0; j <= 7; j++){
                if (board.board[i][j].getColor().equals("black") &&
                        board.board[i][j].getClass().getName().equals("Pawn") &&
                        !checkingChachAfterBlackFigureTryStopChack(x, y, i, j) &&
                        board.board[i + 1][j].getName().equals("empty")){
                    return true;
                }
                if (board.board[i][j].getColor().equals("black") &&
                        !board.board[i][j].getClass().getName().equals("Pawn") &&
                        !board.board[i][j].getClass().getName().equals("King") &&
                        !conditionsPAT(x, y, i, j) &&
                        findStepForBlackFigure(i, j)//имеет свободные клетки для хода
                        ){
                    return true;
                }

                if (board.board[i][j].getColor().equals("black") &&
                        !board.board[i][j].getClass().getName().equals("Pawn") &&
                        !board.board[i][j].getClass().getName().equals("King") &&
                        findStepForBlackFigure(i, j) &&//имеет свободные клетки для хода
                        conditionsPAT(x, y, i, j) &&
                        !checkingChachAfterBlackFigureTryStopChack(x, y, i, j)){
                    return true;
                }
                if (board.board[i][j].getColor().equals("black") &&
                        !board.board[i][j].getClass().getName().equals("Pawn") &&
                        !board.board[i][j].getName().equals("King") &&
                        findStepForBlackFigure(i, j) &&// фигура имеет клетки для хода
                        conditionsPAT(x, y, i, j) &&
                        checkingChachAfterBlackFigureTryStopChack(x, y, i, j) &&
                        findEmptyPlaceBetweenKingAndFigure(x, y, i, j)){
                    return true;
                    //написать метод и добавить, который вычисляет, может ли фигура ходить на пустые клетки
                    //написать и добавить метод чтобы вычисляло промежутки между королем и фигурой которую здесь нашло как белую
                }

            }
        }
        return false;
    }//=

    public static boolean findEmptyPlaceBetweenKingAndFigure(int x, int y , int xF, int yF){
        if (x > xF && y == yF) {//проверка если фигура сверху
            for (int a = 1; a < x - xF; a++) {
                if (!board.board[x - a][y].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (x < xF && y == yF) {//проверка еслли фигура снизу
            for (int a = 1; a < xF - x; a++) {
                if (!board.board[x + a][y].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (y < yF && x == xF) {//проверка если фигура справа
            for (int a = 1; a < yF - y; a++) {
                if (!board.board[x][y + a].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (y > yF && x == xF) {//проверка если фигура слева
            for (int a = 1; a < x - xF; a++) {
                if (!board.board[x][y - a].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (x > xF && y < yF && x - xF == yF - y) {//проверка если фигура справа вверху
            for (int a = 1; a < x - xF; a++) {
                if (!board.board[x - a][y + a].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (x < xF && y < yF && xF - x == yF - y) {//проверка если фигура справа внизу
            for (int a = 1; a < xF - x; a++) {
                if (!board.board[x + a][y + a].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (x < xF && y > yF && xF - x == y - yF) {//проверка если фигура слева внизу
            for (int a = 1; a < xF - x; a++) {
                if (!board.board[x + a][y - a].getName().equals("empty")){
                    return true;
                }

            }
        }
        if (x > xF && y > yF && x - xF == y - yF) {//проверка если фигура слева вверху
            for (int a = 1; a < x - xF; a++) {
                if (!board.board[x - a][y - a].getName().equals("empty")){
                    return true;
                }

            }
        }
        return false;
    }

    public static boolean conditionsPAT(int x, int y, int xF, int yF){
        if (x > xF && y == yF) {//проверка если фигура сверху
            return true;
        }
        if (x < xF && y == yF) {//проверка еслли фигура снизу
            return true;
        }
        if (y < yF && x == xF){//проверка если фигура справа
            return true;
        }
        if (y > yF && x == xF) {//проверка если фигура слева
            return true;
        }
        if (x > xF && y < yF && x - xF == yF - y) {//проверка если фигура справа вверху
            return false;
        }
        if (x < xF && y < yF && xF - x == yF - y) {//проверка если фигура справа внизу
            return true;
        }
        if (x < xF && y > yF && xF - x == y - yF) {//проверка если фигура слева внизу
            return true;
        }
        if (x > xF && y > yF && x - xF == y - yF) {//проверка если фигура слева вверху
            return true;
        }
       return false;
    }

    public static boolean findStepForWhiteFigure(int i, int j){
        if (board.board[i][j].getColor().equals("white") &&
                board.board[i][j].getClass().getName().equals("Queen")){
            if (i - 1 >= 0) {
                if (board.board[i - 1][j].getName().equals("empty") ||
                        board.board[i - 1][j].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j + 1 <= 7) {
                if (board.board[i - 1][j + 1].getColor().equals("black") ||
                        board.board[i - 1][j + 1].getName().equals("empty")){
                    return true;
                }
            }
            if (j + 1 <= 7){
                if (board.board[i][j + 1].getName().equals("empty") ||
                        board.board[i][j + 1].getColor().equals("black")){
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 1 <= 7) {
                if (board.board[i + 1][j + 1].getName().equals("empty") ||
                        board.board[i + 1][j + 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i + 1 <= 7) {
                if (board.board[i + 1][j].getName().equals("empty") ||
                        board.board[i + 1][j].getColor().equals("black")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j - 1 >= 0) {
                if (board.board[i + 1][j - 1].getName().equals("empty") ||
                        board.board[i + 1][j - 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (j - 1 >= 0) {
                if (board.board[i][j - 1].getName().equals("empty") ||
                        board.board[i][j - 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j - 1 >= 0){
                if (board.board[i - 1][j - 1].getName().equals("empty") ||
                        board.board[i - 1][j - 1].getColor().equals("black")){
                    return true;
                }
            }
        }
        if (board.board[i][j].getColor().equals("white") &&
                board.board[i][j].getClass().getName().equals("Rook")){
            if (i - 1 >= 0) {
                if (board.board[i - 1][j].getName().equals("empty") ||
                        board.board[i - 1][j].getColor().equals("black")) {
                    return true;
                }
            }
            if (j + 1 <= 7) {
                if (board.board[i][j + 1].getName().equals("empty") ||
                        board.board[i][j + 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i + 1 <= 7) {
                if (board.board[i + 1][j].getName().equals("empty") ||
                        board.board[i + 1][j].getColor().equals("black")) {
                    return true;
                }
            }
            if (j - 1 >= 0){
                if (board.board[i][j - 1].getName().equals("empty") ||
                    board.board[i][j - 1].getColor().equals("black")) {
                    return  true;
                }
            }
        }
        if (board.board[i][j].getColor().equals("white") &&
                board.board[i][j].getClass().getName().equals("Bishop")){
            if (i - 1 >= 0 && j + 1 <= 7) {
                if (board.board[i - 1][j + 1].getColor().equals("black") ||
                        board.board[i - 1][j + 1].getName().equals("empty")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 1 <= 7) {
                if (board.board[i + 1][j + 1].getName().equals("empty") ||
                        board.board[i + 1][j + 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j - 1 >= 0) {
                if (board.board[i + 1][j - 1].getName().equals("empty") ||
                        board.board[i + 1][j - 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j - 1 >= 0) {
                if (board.board[i - 1][j - 1].getName().equals("empty") ||
                        board.board[i - 1][j - 1].getColor().equals("black")) {
                    return true;
                }
            }

        }
        if (board.board[i][j].getColor().equals("white") &&
                board.board[i][j].getClass().getName().equals("Knight")){
            if (i - 2 >= 0 && j + 1 <= 7) {
                if (board.board[i - 2][j + 1].getName().equals("empty") ||
                        board.board[i - 2][j + 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 2 >= 0 && j - 1 >= 0) {
                if (board.board[i - 2][j - 1].getColor().equals("black") ||
                        board.board[i - 2][j - 1].getName().equals("empty")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j + 2 <= 7) {
                if (board.board[i - 1][j + 2].getName().equals("empty") ||
                        board.board[i - 1][j + 2].getColor().equals("black")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 2 <= 7) {
                if (board.board[i + 1][j + 2].getName().equals("empty") ||
                        board.board[i + 1][j + 2].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 2 >= 0 && j - 1 >= 0) {
                if (board.board[i - 2][j - 1].getName().equals("empty") ||
                        board.board[i - 2][j - 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 2 >= 0 && j + 1 <= 7){
                if (board.board[i - 2][j + 1].getName().equals("empty") ||
                    board.board[i - 2][j + 1].getColor().equals("black")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j - 2 >= 0) {
                if (board.board[i + 1][j - 2].getName().equals("empty") ||
                        board.board[i + 1][j - 2].getColor().equals("black")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j - 2 >- 0){
                if (board.board[i - 1][j - 2].getName().equals("empty") ||
                    board.board[i - 1][j - 2].getColor().equals("black")) {
                    return true;
                }
            }
        }
        return false;
    }//=

    public static boolean findStepForBlackFigure(int i, int j){
        if (board.board[i][j].getColor().equals("black") &&
                board.board[i][j].getClass().getName().equals("Queen")){
            if (i - 1 >= 0) {
                if (board.board[i - 1][j].getName().equals("empty") ||
                        board.board[i - 1][j].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j + 1 <= 7) {
                if (board.board[i - 1][j + 1].getColor().equals("white") ||
                        board.board[i - 1][j + 1].getName().equals("empty")){
                    return true;
                }
            }
            if (j + 1 <= 7){
                if (board.board[i][j + 1].getName().equals("empty") ||
                        board.board[i][j + 1].getColor().equals("white")){
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 1 <= 7) {
                if (board.board[i + 1][j + 1].getName().equals("empty") ||
                        board.board[i + 1][j + 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i + 1 <= 7) {
                if (board.board[i + 1][j].getName().equals("empty") ||
                        board.board[i + 1][j].getColor().equals("white")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j - 1 >= 0) {
                if (board.board[i + 1][j - 1].getName().equals("empty") ||
                        board.board[i + 1][j - 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (j - 1 >= 0) {
                if (board.board[i][j - 1].getName().equals("empty") ||
                        board.board[i][j - 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j - 1 >= 0){
                if (board.board[i - 1][j - 1].getName().equals("empty") ||
                        board.board[i - 1][j - 1].getColor().equals("white")){
                    return true;
                }
            }
        }
        if (board.board[i][j].getColor().equals("black") &&
                board.board[i][j].getClass().getName().equals("Rook")){
            if (i - 1 >= 0) {
                if (board.board[i - 1][j].getName().equals("empty") ||
                        board.board[i - 1][j].getColor().equals("white")) {
                    return true;
                }
            }
            if (j + 1 <= 7) {
                if (board.board[i][j + 1].getName().equals("empty") ||
                        board.board[i][j + 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i + 1 <= 7) {
                if (board.board[i + 1][j].getName().equals("empty") ||
                        board.board[i + 1][j].getColor().equals("white")) {
                    return true;
                }
            }
            if (j - 1 >= 0){
                if (board.board[i][j - 1].getName().equals("empty") ||
                        board.board[i][j - 1].getColor().equals("white")) {
                    return  true;
                }
            }
        }
        if (board.board[i][j].getColor().equals("black") &&
                board.board[i][j].getClass().getName().equals("Bishop")){
            if (i - 1 >= 0 && j + 1 <= 7) {
                if (board.board[i - 1][j + 1].getColor().equals("white") ||
                        board.board[i - 1][j + 1].getName().equals("empty")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 1 <= 7) {
                if (board.board[i + 1][j + 1].getName().equals("empty") ||
                        board.board[i + 1][j + 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j - 1 >= 0) {
                if (board.board[i + 1][j - 1].getName().equals("empty") ||
                        board.board[i + 1][j - 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j - 1 >= 0) {
                if (board.board[i - 1][j - 1].getName().equals("empty") ||
                        board.board[i - 1][j - 1].getColor().equals("white")) {
                    return true;
                }
            }

        }
        if (board.board[i][j].getColor().equals("black") &&
                board.board[i][j].getClass().getName().equals("Knight")){
            if (i - 2 >= 0 && j + 1 <= 7) {
                if (board.board[i - 2][j + 1].getName().equals("empty") ||
                        board.board[i - 2][j + 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 2 >= 0 && j - 1 >= 0) {
                if (board.board[i - 2][j - 1].getColor().equals("white") ||
                        board.board[i - 2][j - 1].getName().equals("empty")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j + 2 <= 7) {
                if (board.board[i - 1][j + 2].getName().equals("empty") ||
                        board.board[i - 1][j + 2].getColor().equals("white")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j + 2 <= 7) {
                if (board.board[i + 1][j + 2].getName().equals("empty") ||
                        board.board[i + 1][j + 2].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 2 >= 0 && j - 1 >= 0) {
                if (board.board[i - 2][j - 1].getName().equals("empty") ||
                        board.board[i - 2][j - 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 2 >= 0 && j + 1 <= 7){
                if (board.board[i - 2][j + 1].getName().equals("empty") ||
                        board.board[i - 2][j + 1].getColor().equals("white")) {
                    return true;
                }
            }
            if (i + 1 <= 7 && j - 2 >= 0) {
                if (board.board[i + 1][j - 2].getName().equals("empty") ||
                        board.board[i + 1][j - 2].getColor().equals("white")) {
                    return true;
                }
            }
            if (i - 1 >= 0 && j - 2 >- 0){
                if (board.board[i - 1][j - 2].getName().equals("empty") ||
                        board.board[i - 1][j - 2].getColor().equals("white")) {
                    return true;
                }
            }
        }
        return false;
    }//=
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////рокировка
    public static boolean checkCastle(){
        return board.board[hodi][hodj].getClass().getName().equals("King") && ((hodi == hodtoi && hodj == hodtoj - 2) || (hodi == hodtoi && hodj == hodtoj + 2));
    }

    public static boolean checkCastleConditions(){
        if (hod && !board.wK.shah && !board.wK.firstStep && !checkForWhiteKingLine(hodi, hodj, hodtoi, hodtoj) && checkWhiteRook(hodi, hodj, hodtoi, hodtoj)){
            return true;
        }
        if (!hod && !board.bK.shah && !board.bK.firstStep && !checkForBlackKingLine(hodi, hodj, hodtoi, hodtoj) && checkBlackRook(hodi, hodj, hodtoi, hodtoj)){
            return true;
        }
        return false;
    }

    public static boolean checkForWhiteKingLine(int x, int y, int toX, int toY){
        if (x == toX && toY - y == 2){//проверка условия король идет вправо

            for (int i = 1; i <= 2; i++){//количество клеток от короля вправо которые проверяем

                int newY = y + i;//новая координата по которой идем вправо

                if (!board.board[x][y + i].getName().equals("empty")){
                    System.out.println("Рокировка: клетка не пуста");
                    return true;
                }/*проверка что нет фигуры на клетке*/
                for (int j = 1; j <= 6; j++){//количество клеток, которые проверяем влево вверх
                    if (x - j >= 0 && newY - j >= 0) {
                        if (j == 1 && !board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("black") &&
                                board.board[x - j][newY - j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: Шах от пешки влево вверх");
                            return true;
                        }
                        if (!board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("black") &&
                                !(board.board[x - j][newY - j].getClass().getName().equals("Queen") || board.board[x - j][newY - j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("black") &&
                                (board.board[x - j][newY - j].getClass().getName().equals("Queen") || board.board[x - j][newY - j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры слева вверху");
                            return true;
                        }
                    }
                }

                for (int j = 1; j <= 7; j++){//количество клеток которые проверяем прямо
                    if (x - j >= 0) {
                        if (j == 1 && !board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("black") &&
                                board.board[x - j][newY].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки прямо");
                            return true;
                        }
                        if (!board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("black") &&
                                !(board.board[x - j][newY].getClass().getName().equals("Queen") || board.board[x - j][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("black") &&
                                (board.board[x - j][newY].getClass().getName().equals("Queen") || board.board[x - j][newY].getClass().getName().equals("Rook"))) {
                            System.out.println("Рокировка: шах от фигуры прямо");
                            return true;
                        }
                    }
                }

                for (int j = 1; j <= 2; j++){//количество клеток, которые проверяем вправо вверх
                    if (x - j >= 0 && newY + j <= 7) {
                        if (j == 1 && !board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("black") &&
                                board.board[x - j][newY + j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от фигуры вправо вверху");
                            return true;
                        }
                        if (!board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("black") &&
                                !(board.board[x - j][newY + j].getClass().getName().equals("Queen") || board.board[x - j][newY + j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("black") &&
                                (board.board[x - j][newY + j].getClass().getName().equals("Queen") || board.board[x - j][newY + j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры вправо вверху");
                            return true;
                        }
                    }
                }
                if (x - 1 >= 0 && x - 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7){
                    if (!board.board[x - 1][newY - 2].getName().equals("empty") &&
                            board.board[x - 1][newY - 2].getColor().equals("black") &&
                            board.board[x - 1][newY - 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня влево вверху");
                        return true;
                    }
                }
                if (x - 2 >= 0 && x - 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7){
                    if (!board.board[x - 2][newY - 1].getName().equals("empty") &&
                            board.board[x - 2][newY - 1].getColor().equals("black") &&
                            board.board[x - 2][newY - 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вверху влево");
                        return true;
                    }
                }
                if (x - 2 >= 0 && x - 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7){
                    if (!board.board[x - 2][newY + 1].getName().equals("empty") &&
                            board.board[x - 2][newY + 1].getColor().equals("black") &&
                            board.board[x - 2][newY + 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вверху вправо");
                        return true;
                    }
                }
                if (x - 1 >= 0 && x - 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7){
                    if (!board.board[x - 1][newY + 2].getName().equals("empty") &&
                            board.board[x - 1][newY + 2].getColor().equals("black") &&
                            board.board[x - 1][newY + 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вправо вверху");
                        return true;
                    }
                }

            }
        }

        if (x == toX && y - toY == 2){//проверка условия король идет влево
            //
            for (int i = 1; i <= 2; i++){//количество клеток от короля вправо которые проверяем

                int newY = y - i;
                /*проверка что нет фигуры на клетке*/
                if (!board.board[x][newY].getName().equals("empty")){
                    System.out.println("Рокировка: клетка не пуста");
                    return true;
                }

                for (int j = 1; j <= 3; j++){//количество клеток, которые проверяем влево вверх
                    if (x - j >= 0 && newY - j >= 0) {
                        if (j == 1 && !board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("black") &&
                                board.board[x - j][newY - j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки влево вверх");
                            return true;
                        }
                        if (!board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("black") &&
                                !(board.board[x - j][newY - j].getClass().getName().equals("Queen") || board.board[x - j][newY - j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x - j][newY - j].getName().equals("empty") &&
                                board.board[x - j][newY - j].getColor().equals("black") &&
                                (board.board[x - j][newY - j].getClass().getName().equals("Queen") || board.board[x - j][newY - j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры влево вверху");
                            return true;
                        }
                    }

                }

                for (int j = 1; j <= 7; j++){//количество клеток которые проверяем прямо
                    if (x - j >= 0) {
                        if (j == 1 && !board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("black") &&
                                board.board[x - j][newY].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки которая прямо");
                            return true;
                        }
                        if (!board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("black") &&
                                !(board.board[x - j][newY].getClass().getName().equals("Queen") || board.board[x - j][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[x - j][newY].getName().equals("empty") &&
                                board.board[x - j][newY].getColor().equals("black") &&
                                (board.board[x - j][newY].getClass().getName().equals("Queen") || board.board[x - j][newY].getClass().getName().equals("Rook"))) {
                            System.out.println("Рокировка: шах от фигуры прямо");
                            return true;
                        }
                    }

                }

                for (int j = 1; j <= 5; j++){//количество клеток, которые проверяем вправо вверх
                    if (x - j >= 0 && newY + j <= 7) {
                        if (j == 1 && !board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("black") &&
                                board.board[x - j][newY + j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки которая вправо вверх");
                            return true;
                        }
                        if (!board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("white")) {
                            break;
                        }
                        if (!board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("black") &&
                                !(board.board[x - j][newY + j].getClass().getName().equals("Queen") || board.board[x - j][newY + j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x - j][newY + j].getName().equals("empty") &&
                                board.board[x - j][newY + j].getColor().equals("black") &&
                                (board.board[x - j][newY + j].getClass().getName().equals("Queen") || board.board[x - j][newY + j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры вправо вверх");
                            return true;
                        }
                    }
                }
                if (x - 1 >= 0 && x - 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7){
                    if (!board.board[x - 1][newY - 2].getName().equals("empty") &&
                            board.board[x - 1][newY - 2].getColor().equals("black") &&
                            board.board[x - 1][newY - 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня влево вверху");
                        return true;
                    }
                }
                if (x - 2 >= 0 && x - 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7){
                    if (!board.board[x - 2][newY - 1].getName().equals("empty") &&
                            board.board[x - 2][newY - 1].getColor().equals("black") &&
                            board.board[x - 2][newY - 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вверху влево");
                        return true;
                    }
                }
                if (x - 2 >= 0 && x - 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7){
                    if (!board.board[x - 2][newY + 1].getName().equals("empty") &&
                            board.board[x - 2][newY + 1].getColor().equals("black") &&
                            board.board[x - 2][newY + 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вверху вправо");
                        return true;
                    }
                }
                if (x - 1 >= 0 && x - 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7){
                    if (!board.board[x - 1][newY + 2].getName().equals("empty") &&
                            board.board[x - 1][newY + 2].getColor().equals("black") &&
                            board.board[x - 1][newY + 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вправо вверху");
                        return true;
                    }
                }
            }

            if (!board.board[x][y - 3].getName().equals("empty")){
                System.out.println("Рокировка: клетка не пуста");
                return true;
            }/*проверка что нет фигуры на 3 клетке*/
        }
      return false;
    }

    public static boolean checkForBlackKingLine(int x, int y, int toX, int toY){
        if (x == toX && toY - y == 2){//проверка условия король идет вправо

            for (int i = 1; i <= 2; i++){//количество клеток от короля вправо которые проверяем

                int newY = y + i;//новая координата по которой идем вправо

                if (!board.board[x][y + i].getName().equals("empty")){
                    System.out.println("Рокировка: клетка не пуста");
                    return true;
                }/*проверка что нет фигуры на клетке*/
                for (int j = 1; j <= 6; j++){//количество клеток, которые проверяем влево вниз
                    if (x + j <= 7 && newY - j >= 0) {
                        if (j == 1 && !board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("white") &&
                                board.board[x + j][newY - j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: Шах от пешки влево вниз");
                            return true;
                        }
                        if (!board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("white") &&
                                !(board.board[x + j][newY - j].getClass().getName().equals("Queen") || board.board[x + j][newY - j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("white") &&
                                (board.board[x + j][newY - j].getClass().getName().equals("Queen") || board.board[x + j][newY - j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры слева внизу");
                            return true;
                        }
                    }
                }

                for (int j = 1; j <= 7; j++){//количество клеток которые проверяем вниз
                    if (x + j <= 7) {
                        if (j == 1 && !board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("white") &&
                                board.board[x + j][newY].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки вниз");
                            return true;
                        }
                        if (!board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("white") &&
                                !(board.board[x + j][newY].getClass().getName().equals("Queen") || board.board[x + j][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("white") &&
                                (board.board[x + j][newY].getClass().getName().equals("Queen") || board.board[x + j][newY].getClass().getName().equals("Rook"))) {
                            System.out.println("Рокировка: шах от фигуры вниз");
                            return true;
                        }
                    }
                }

                for (int j = 1; j <= 2; j++){//количество клеток, которые проверяем вправо вниз
                    if (x + j <= 7 && newY + j <= 7) {
                        if (j == 1 && !board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("white") &&
                                board.board[x + j][newY + j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от фигуры вправо внизу");
                            return true;
                        }
                        if (!board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("white") &&
                                !(board.board[x + j][newY + j].getClass().getName().equals("Queen") || board.board[x + j][newY + j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("white") &&
                                (board.board[x + j][newY + j].getClass().getName().equals("Queen") || board.board[x + j][newY + j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры вправо внизу");
                            return true;
                        }
                    }
                }
                if (x + 1 >= 0 && x + 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7){
                    if (!board.board[x + 1][newY - 2].getName().equals("empty") &&
                            board.board[x + 1][newY - 2].getColor().equals("white") &&
                            board.board[x + 1][newY - 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня влево внизу");
                        return true;
                    }
                }
                if (x + 2 >= 0 && x + 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7){
                    if (!board.board[x + 2][newY - 1].getName().equals("empty") &&
                            board.board[x + 2][newY - 1].getColor().equals("white") &&
                            board.board[x + 2][newY - 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня внизу влево");
                        return true;
                    }
                }
                if (x + 2 >= 0 && x + 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7){
                    if (!board.board[x + 2][newY + 1].getName().equals("empty") &&
                            board.board[x + 2][newY + 1].getColor().equals("white") &&
                            board.board[x + 2][newY + 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня внизу вправо");
                        return true;
                    }
                }
                if (x + 1 >= 0 && x + 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7){
                    if (!board.board[x + 1][newY + 2].getName().equals("empty") &&
                            board.board[x + 1][newY + 2].getColor().equals("white") &&
                            board.board[x + 1][newY + 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вправо внизу");
                        return true;
                    }
                }
            }
        }

        if (x == toX && y - toY == 2){//проверка условия король идет влево
            //
            for (int i = 1; i <= 2; i++){//количество клеток от короля вправо которые проверяем

                int newY = y - i;
                /*проверка что нет фигуры на клетке*/
                if (!board.board[x][newY].getName().equals("empty")){
                    System.out.println("Рокировка: клетка не пуста");
                    return true;
                }

                for (int j = 1; j <= 3; j++){//количество клеток, которые проверяем влево вниз
                    if (x + j <= 7 && newY - j >= 0) {
                        if (j == 1 && !board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("white") &&
                                board.board[x + j][newY - j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки влево вниз");
                            return true;
                        }
                        if (!board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("white") &&
                                !(board.board[x + j][newY - j].getClass().getName().equals("Queen") || board.board[x + j][newY - j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x + j][newY - j].getName().equals("empty") &&
                                board.board[x + j][newY - j].getColor().equals("white") &&
                                (board.board[x + j][newY - j].getClass().getName().equals("Queen") || board.board[x + j][newY - j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры влево внизу");
                            return true;
                        }
                    }

                }

                for (int j = 1; j <= 7; j++){//количество клеток которые проверяем вниз
                    if (x + j <= 7) {
                        if (j == 1 && !board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("white") &&
                                board.board[x + j][newY].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки которая вниз");
                            return true;
                        }
                        if (!board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("white") &&
                                !(board.board[x + j][newY].getClass().getName().equals("Queen") || board.board[x + j][newY].getClass().getName().equals("Rook"))) {
                            break;
                        }
                        if (!board.board[x + j][newY].getName().equals("empty") &&
                                board.board[x + j][newY].getColor().equals("white") &&
                                (board.board[x + j][newY].getClass().getName().equals("Queen") || board.board[x + j][newY].getClass().getName().equals("Rook"))) {
                            System.out.println("Рокировка: шах от фигуры вниз");
                            return true;
                        }
                    }

                }

                for (int j = 1; j <= 5; j++){//количество клеток, которые проверяем вправо вниз
                    if (x + j <= 7 && newY + j <= 7) {
                        if (j == 1 && !board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("white") &&
                                board.board[x + j][newY + j].getClass().getName().equals("Pawn")) {
                            System.out.println("Рокировка: шах от пешки которая вправо вниз");
                            return true;
                        }
                        if (!board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("black")) {
                            break;
                        }
                        if (!board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("white") &&
                                !(board.board[x + j][newY + j].getClass().getName().equals("Queen") || board.board[x + j][newY + j].getClass().getName().equals("Bishop"))) {
                            break;
                        }
                        if (!board.board[x + j][newY + j].getName().equals("empty") &&
                                board.board[x + j][newY + j].getColor().equals("white") &&
                                (board.board[x + j][newY + j].getClass().getName().equals("Queen") || board.board[x + j][newY + j].getClass().getName().equals("Bishop"))) {
                            System.out.println("Рокировка: шах от фигуры вправо внизу");
                            return true;
                        }
                    }
                }
                if (x + 1 >= 0 && x + 1 <= 7 && newY - 2 >= 0 && newY - 2 <= 7){
                    if (!board.board[x + 1][newY - 2].getName().equals("empty") &&
                            board.board[x + 1][newY - 2].getColor().equals("white") &&
                            board.board[x + 1][newY - 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня влево внизу");
                        return true;
                    }
                }
                if (x + 2 >= 0 && x + 2 <= 7 && newY - 1 >= 0 && newY - 1 <= 7){
                    if (!board.board[x + 2][newY - 1].getName().equals("empty") &&
                            board.board[x + 2][newY - 1].getColor().equals("white") &&
                            board.board[x + 2][newY - 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня внизу влево");
                        return true;
                    }
                }
                if (x + 2 >= 0 && x + 2 <= 7 && newY + 1 >= 0 && newY + 1 <= 7){
                    if (!board.board[x + 2][newY + 1].getName().equals("empty") &&
                            board.board[x + 2][newY + 1].getColor().equals("white") &&
                            board.board[x + 2][newY + 1].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня внизу вправо");
                        return true;
                    }
                }
                if (x + 1 >= 0 && x + 1 <= 7 && newY + 2 >= 0 && newY + 2 <= 7){
                    if (!board.board[x + 1][newY + 2].getName().equals("empty") &&
                            board.board[x + 1][newY + 2].getColor().equals("white") &&
                            board.board[x + 1][newY + 2].getClass().getName().equals("Knight")){
                        System.out.println("Рокировка: шах от коня вправо внизу");
                        return true;
                    }
                }
            }

            if (!board.board[x][y - 3].getName().equals("empty")){
                System.out.println("Рокировка: клетка не пуста");
                return true;
            }/*проверка что нет фигуры на 3 клетке*/
        }
        return false;
    }

    public static boolean checkWhiteRook(int x, int y, int toX, int toY){
        if (x == toX && toY - y == 2){
            if (board.board[x][y + 3].getClass().getName().equals("Rook") &&
                    board.board[x][y + 3].getColor().equals("white") &&
                    !board.board[x][y + 3].getFirstStep()){
                return true;
            }
            else {
                System.out.println("проверку не прошло условие с ладьей");
            }
        }
        if (x == toX && y - toY == 2){
            if (board.board[x][y - 4].getClass().getName().equals("Rook") &&
                    board.board[x][y - 4].getColor().equals("white") &&
                    !board.board[x][y - 4].getFirstStep()){
                return true;
            }
            else {
                System.out.println("проверку не прошло условие с ладьей");
            }
        }
        return false;
    }

    public static boolean checkBlackRook(int x, int y, int toX, int toY){
        if (x == toX && toY - y == 2){
            if (board.board[x][y + 3].getClass().getName().equals("Rook") &&
                    board.board[x][y + 3].getColor().equals("black") &&
                    !board.board[x][y + 3].getFirstStep()){
                return true;
            }
            else {
                System.out.println("проверку не прошло условие с ладьей");
            }
        }
        if (x == toX && y - toY == 2){
            if (board.board[x][y - 4].getClass().getName().equals("Rook") &&
                    board.board[x][y - 4].getColor().equals("black") &&
                    !board.board[x][y - 4].getFirstStep()){
                return true;
            }
            else {
                System.out.println("проверку не прошло условие с ладьей");
            }
        }
        return false;
    }

    public static void  changeVariablesKingAndRook(){
        if (hod && hodj < hodtoj){
            board.wK.changeFirstStep();
            board.wK.changeCastle();
            board.board[7][5].changeFirstStep();
            System.out.println("Переменные ходов установлены");
        }
        if (hod && hodj > hodtoj){
            board.wK.changeFirstStep();
            board.wK.changeCastle();
            board.board[7][3].changeFirstStep();
            System.out.println("Переменные ходов установлены");
        }
        if (!hod && hodj < hodtoj){
            board.bK.changeFirstStep();
            board.bK.changeCastle();
            board.board[0][5].changeFirstStep();
            System.out.println("Переменные ходов установлены");
        }
        if (!hod && hodj > hodtoj){
            board.bK.changeFirstStep();
            board.bK.changeCastle();
            board.board[0][3].changeFirstStep();
            System.out.println("Переменные ходов установлены");
        }

    }

    public static void changeVariableFirstStepsAfterEveryStep(){
        if (!board.board[hodtoi][hodtoj].getFirstStep() && (board.board[hodtoi][hodtoj].getClass().getName().equals("King") || board.board[hodtoi][hodtoj].getClass().getName().equals("Rook"))){
            board.board[hodtoi][hodtoj].changeFirstStep();
        }
    }

    public static void moveRookForCastle(){
        //для белой ладьи
        if (hod && hodj < hodtoj){
            AbstractFigure object = board.board[7][7];
            board.board[7][7] = board.board[7][5];
            board.board[7][5] = object;

            System.out.println("Местами ладью поменяло");
            System.out.println(board.board[7][5]);
            System.out.println(board.board[7][7]);

            //Icon icon1 = guiBoard.buttons[7][7].getIcon();
            //Icon icon2 = guiBoard.buttons[7][5].getIcon();
            //guiBoard.buttons[7][5].setIcon(icon1);
            //guiBoard.buttons[7][7].setIcon(icon2);
        }
        if (hod && hodj > hodtoj){
            AbstractFigure object = board.board[7][0];
            board.board[7][0] = board.board[7][3];
            board.board[7][3] = object;

            System.out.println("Местами ладью поменяло");
            System.out.println(board.board[7][3]);
            System.out.println(board.board[7][0]);

            //Icon icon1 = guiBoard.buttons[7][0].getIcon();
            //Icon icon2 = guiBoard.buttons[7][3].getIcon();
            //guiBoard.buttons[7][3].setIcon(icon1);
            //guiBoard.buttons[7][0].setIcon(icon2);
        }
        //для черной ладьи
        if (!hod && hodj < hodtoj){
            AbstractFigure object = board.board[0][7];
            board.board[0][7] = board.board[0][5];
            board.board[0][5] = object;

            System.out.println("Местами ладью поменяло");
            System.out.println(board.board[0][5]);
            System.out.println(board.board[0][7]);

            //Icon icon1 = guiBoard.buttons[0][7].getIcon();
            //Icon icon2 = guiBoard.buttons[0][5].getIcon();
            //guiBoard.buttons[0][5].setIcon(icon1);
            //guiBoard.buttons[0][7].setIcon(icon2);
        }
        if (!hod && hodj > hodtoj){
            AbstractFigure object = board.board[0][0];
            board.board[0][0] = board.board[0][3];
            board.board[0][3] = object;

            System.out.println("Местами ладью поменяло");
            System.out.println(board.board[0][3]);
            System.out.println(board.board[0][0]);

            //Icon icon1 = guiBoard.buttons[0][0].getIcon();
            //Icon icon2 = guiBoard.buttons[0][3].getIcon();
            //guiBoard.buttons[0][3].setIcon(icon1);
            //guiBoard.buttons[0][0].setIcon(icon2);
        }



    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}

