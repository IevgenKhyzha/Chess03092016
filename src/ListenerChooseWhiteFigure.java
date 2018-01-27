import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerChooseWhiteFigure implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        for (int i = 0; i <= 3; i++ ){
            if (button == ChessBoard.chooseWhiteFigureInsteadThePawn.buttonsWhite[i]){

                ChessBoard.board.board[ChessBoard.hodtoi][ChessBoard.hodtoj] = ChessBoard.chooseWhiteFigureInsteadThePawn.figuresWhite[i];
                ChessBoard.guiBoard.buttons[ChessBoard.hodtoi][ChessBoard.hodtoj].setIcon(button.getIcon());
                ChessBoard.pawnCheking = true;
            }
        }
        ChessBoard.chooseWhiteFigureInsteadThePawn.dispose();//после найти метод, чтобы закрыть окно

    }
}
