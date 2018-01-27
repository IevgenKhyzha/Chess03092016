import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ListenerChooseBlackFigure implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        for (int i = 0; i <= 3; i++ ){
            if (button == ChessBoard.chooseBlackFigureInsteadThePawn.buttonsBlack[i]){

                ChessBoard.board.board[ChessBoard.hodtoi][ChessBoard.hodtoj] = ChessBoard.chooseBlackFigureInsteadThePawn.figuresBlack[i];
                //ChessBoard.guiBoard.buttons[ChessBoard.hodtoi][ChessBoard.hodtoj].setIcon(button.getIcon());
                ChessBoard.pawnCheking = true;
            }
        }
        ChessBoard.chooseBlackFigureInsteadThePawn.dispose();
    }
}
