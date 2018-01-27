import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerButtonUpsidedown implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Listener.firstPlace && !Listener.secondPlace) {

            if (ChessBoard.upsidedownPosibility) {

                System.out.println("upsidedown");

                ChessBoard.upsidedown = !ChessBoard.upsidedown;

                ChessBoard.cleanBoardFromRedColor();
                ChessBoard.printBoard();
                ChessBoard.printChack();
            }
        }
    }
}
