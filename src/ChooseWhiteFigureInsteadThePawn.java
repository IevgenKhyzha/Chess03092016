import javax.swing.*;
import java.awt.*;

public class ChooseWhiteFigureInsteadThePawn extends JFrame {

    ImageIcon iconQueenWhite = new ImageIcon(ChooseWhiteFigureInsteadThePawn.class.getResource("Images/QueenWhite1.png"));
    ImageIcon iconRookWhite = new ImageIcon(ChooseWhiteFigureInsteadThePawn.class.getResource("Images/RookWhite1.png"));
    ImageIcon iconKnightWhite = new ImageIcon(ChooseWhiteFigureInsteadThePawn.class.getResource("Images/KnightWhite1.png"));
    ImageIcon iconBishopWhite = new ImageIcon(ChooseWhiteFigureInsteadThePawn.class.getResource("Images/BishopWhite1.png"));

    JButton buttonQueenWhite = new JButton();
    JButton buttonRookWhite = new JButton();
    JButton buttonKnightWhite = new JButton();
    JButton buttonBishopWhite = new JButton();

    Queen wQ = new Queen("wQueen", "white");
    Rook wR = new Rook("wRook", "white");
    Knight wKn = new Knight("wKnight", "white");
    Bishop wB = new Bishop("wBishop", "white");

    AbstractFigure[] figuresWhite = {wQ, wR, wKn, wB};

    JButton[] buttonsWhite = {buttonQueenWhite, buttonRookWhite, buttonKnightWhite, buttonBishopWhite};

    ListenerChooseWhiteFigure listenerChooseWhiteFigure = new ListenerChooseWhiteFigure();

    Color yellow = new Color(255, 219, 88);
    Color brown = new Color(184, 115, 51);

    public void chooseWhiteFigureFromTheWindow() {

        buttonQueenWhite.setBackground(brown);
        buttonQueenWhite.setIcon(iconQueenWhite);
        buttonQueenWhite.addActionListener(listenerChooseWhiteFigure);

        buttonRookWhite.setBackground(yellow);
        buttonRookWhite.setIcon(iconRookWhite);
        buttonRookWhite.addActionListener(listenerChooseWhiteFigure);

        buttonKnightWhite.setBackground(brown);
        buttonKnightWhite.setIcon(iconKnightWhite);
        buttonKnightWhite.addActionListener(listenerChooseWhiteFigure);

        buttonBishopWhite.setBackground(yellow);
        buttonBishopWhite.setIcon(iconBishopWhite);
        buttonBishopWhite.addActionListener(listenerChooseWhiteFigure);

        add(buttonQueenWhite);
        add(buttonRookWhite);
        add(buttonKnightWhite);
        add(buttonBishopWhite);

        setLayout(new FlowLayout());
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(300, 100);
        setTitle("Choose White Figure");
        setLocationRelativeTo(ChessBoard.guiBoard);
        ImageIcon image = new ImageIcon(ChooseWhiteFigureInsteadThePawn.class.getResource("Images/chess.png"));
        setIconImage(image.getImage());
        setVisible(true);
    }
}
