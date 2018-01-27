import javax.swing.*;
import java.awt.*;

public class ChooseBlackFigureInsteadThePawn extends JFrame {

    ImageIcon iconQueenBlack = new ImageIcon(ChooseBlackFigureInsteadThePawn.class.getResource("Images/QueenBlack1.png"));
    ImageIcon iconRookBlack = new ImageIcon(ChooseBlackFigureInsteadThePawn.class.getResource("Images/RookBlack1.png"));
    ImageIcon iconKnightBlack = new ImageIcon(ChooseBlackFigureInsteadThePawn.class.getResource("Images/KnightBlack1.png"));
    ImageIcon iconBishopBlack = new ImageIcon(ChooseBlackFigureInsteadThePawn.class.getResource("Images/BishopBlack1.png"));

    JButton buttonQueenBlack = new JButton();
    JButton buttonRookBlack = new JButton();
    JButton buttonKnightBlack = new JButton();
    JButton buttonBishopBlack = new JButton();

    Queen bQ = new Queen("bQueen", "black");
    Rook bR = new Rook("bRook", "black");
    Knight bKn = new Knight("bKnight", "black");
    Bishop bB = new Bishop("bBishop", "black");

    AbstractFigure[] figuresBlack = {bQ, bR, bKn, bB};

    JButton[] buttonsBlack = {buttonQueenBlack, buttonRookBlack, buttonKnightBlack, buttonBishopBlack};

    ListenerChooseBlackFigure listenerChooseBlackFigure = new ListenerChooseBlackFigure();

    Color yellow = new Color(255, 219, 88);
    Color brown = new Color(184, 115, 51);

    public void chooseBlackFigureFromTheWindow(){
        buttonQueenBlack.setBackground(yellow);
        buttonQueenBlack.setIcon(iconQueenBlack);
        buttonQueenBlack.addActionListener(listenerChooseBlackFigure);

        buttonRookBlack.setBackground(brown);
        buttonRookBlack.setIcon(iconRookBlack);
        buttonRookBlack.addActionListener(listenerChooseBlackFigure);

        buttonKnightBlack.setBackground(yellow);
        buttonKnightBlack.setIcon(iconKnightBlack);
        buttonKnightBlack.addActionListener(listenerChooseBlackFigure);

        buttonBishopBlack.setBackground(brown);
        buttonBishopBlack.setIcon(iconBishopBlack);
        buttonBishopBlack.addActionListener(listenerChooseBlackFigure);

        add(buttonQueenBlack);
        add(buttonRookBlack);
        add(buttonKnightBlack);
        add(buttonBishopBlack);

        setLayout(new FlowLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(300, 100);
        setTitle("Choose Black Figure");
        setLocationRelativeTo(ChessBoard.guiBoard);
        ImageIcon image = new ImageIcon(ChooseBlackFigureInsteadThePawn.class.getResource("Images/chess.png"));
        setIconImage(image.getImage());
        setVisible(true);
    }

}
