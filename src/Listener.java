import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {
    Color bgOld;
    static boolean firstPlace = false;
    static boolean secondPlace = false;

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();

        if (!ChessBoard.stopListener) {

            if (!ChessBoard.upsidedown) {

                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (button == ChessBoard.guiBoard.buttons[i][j]) {
                            if (!firstPlace) {
                                if (!ChessBoard.board.board[i][j].getName().equals("empty")) {
                                    if (ChessBoard.hod && ChessBoard.board.board[i][j].getColor().equals("white") ||
                                            !ChessBoard.hod && ChessBoard.board.board[i][j].getColor().equals("black")) {
                                        ChessBoard.hodi = i;
                                        ChessBoard.hodj = j;
                                        bgOld = button.getBackground();
                                        button.setBackground(Color.green);
                                    } else return;
                                } else return;

                            }
                            if (firstPlace) {
                                ChessBoard.hodtoi = i;
                                ChessBoard.hodtoj = j;
                                secondPlace = !secondPlace;
                                ChessBoard.guiBoard.buttons[ChessBoard.hodi][ChessBoard.hodj].setBackground(bgOld);
                            }
                        }
                    }
                }
            }

            if (ChessBoard.upsidedown) {
                for (int i = 0; i <= 7; i++) {
                    for (int j = 0; j <= 7; j++) {
                        if (button == ChessBoard.guiBoard.buttons[i][j]) {
                            if (!firstPlace) {
                                if (!ChessBoard.board.board[7 - i][7 - j].getName().equals("empty")) {
                                    if (ChessBoard.hod && ChessBoard.board.board[7 - i][7 - j].getColor().equals("white") ||
                                            !ChessBoard.hod && ChessBoard.board.board[7 - i][7 - j].getColor().equals("black")) {
                                        ChessBoard.hodi = 7 - i;
                                        ChessBoard.hodj = 7 - j;
                                        bgOld = button.getBackground();
                                        button.setBackground(Color.green);
                                    } else return;
                                } else return;

                            }
                            if (firstPlace) {
                                ChessBoard.hodtoi = 7 - i;
                                ChessBoard.hodtoj = 7 - j;
                                secondPlace = !secondPlace;
                                ChessBoard.guiBoard.buttons[7 - ChessBoard.hodi][7 - ChessBoard.hodj].setBackground(bgOld);
                            }
                        }
                    }
                }
            }

            firstPlace = !firstPlace;
        }
    }
}
