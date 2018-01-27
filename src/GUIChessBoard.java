import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class GUIChessBoard extends JFrame{

    ImageIcon iconKingWhite = new ImageIcon(GUIChessBoard.class.getResource("Images/KingWhite1.png"));
    ImageIcon iconKingBlack = new ImageIcon(GUIChessBoard.class.getResource("Images/KingBlack1.png"));
    ImageIcon iconQueenWhite = new ImageIcon(GUIChessBoard.class.getResource("Images/QueenWhite1.png"));
    ImageIcon iconQueenBlack = new ImageIcon(GUIChessBoard.class.getResource("Images/QueenBlack1.png"));
    ImageIcon iconRookBlack = new ImageIcon(GUIChessBoard.class.getResource("Images/RookBlack1.png"));
    ImageIcon iconRookWhite = new ImageIcon(GUIChessBoard.class.getResource("Images/RookWhite1.png"));
    ImageIcon iconBishopBlack = new ImageIcon(GUIChessBoard.class.getResource("Images/BishopBlack1.png"));
    ImageIcon iconBishopWhite = new ImageIcon(GUIChessBoard.class.getResource("Images/BishopWhite1.png"));
    ImageIcon iconKnightBlack = new ImageIcon(GUIChessBoard.class.getResource("Images/KnightBlack1.png"));
    ImageIcon iconKnightWhite = new ImageIcon(GUIChessBoard.class.getResource("Images/KnightWhite1.png"));
    ImageIcon iconPawnBlack = new ImageIcon(GUIChessBoard.class.getResource("Images/PawnBlack1.png"));
    ImageIcon iconPawnWhite = new ImageIcon(GUIChessBoard.class.getResource("Images/PawnWhite1.png"));

    Color yellow = new Color(255, 219, 88);
    Color brown = new Color(184, 115, 51);

    JButton buttonYellowA8 = new JButton();
    JButton buttonBrownB8 = new JButton();
    JButton buttonYellowC8 = new JButton();
    JButton buttonBrownD8 = new JButton();
    JButton buttonYellowE8 = new JButton();
    JButton buttonBrownF8 = new JButton();
    JButton buttonYellowG8 = new JButton();
    JButton buttonBrownH8 = new JButton();
    JButton buttonBrownA7 = new JButton();
    JButton buttonYellowB7 = new JButton();
    JButton buttonBrownC7 = new JButton();
    JButton buttonYellowD7 = new JButton();
    JButton buttonBrownE7 = new JButton();
    JButton buttonYellowF7 = new JButton();
    JButton buttonBrownG7 = new JButton();
    JButton buttonYellowH7 = new JButton();
    JButton buttonYellowA6 = new JButton();
    JButton buttonBrownB6 = new JButton();
    JButton buttonYellowC6 = new JButton();
    JButton buttonBrownD6 = new JButton();
    JButton buttonYellowE6 = new JButton();
    JButton buttonBrownF6 = new JButton();
    JButton buttonYellowG6 = new JButton();
    JButton buttonBrownH6 = new JButton();
    JButton buttonBrownA5 = new JButton();
    JButton buttonYellowB5 = new JButton();
    JButton buttonBrownC5 = new JButton();
    JButton buttonYellowD5 = new JButton();
    JButton buttonBrownE5 = new JButton();
    JButton buttonYellowF5 = new JButton();
    JButton buttonBrownG5 = new JButton();
    JButton buttonYellowH5 = new JButton();
    JButton buttonYellowA4 = new JButton();
    JButton buttonBrownB4 = new JButton();
    JButton buttonYellowC4 = new JButton();
    JButton buttonBrownD4 = new JButton();
    JButton buttonYellowE4 = new JButton();
    JButton buttonBrownF4 = new JButton();
    JButton buttonYellowG4 = new JButton();
    JButton buttonBrownH4 = new JButton();
    JButton buttonBrownA3 = new JButton();
    JButton buttonYellowB3 = new JButton();
    JButton buttonBrownC3 = new JButton();
    JButton buttonYellowD3 = new JButton();
    JButton buttonBrownE3 = new JButton();
    JButton buttonYellowF3 = new JButton();
    JButton buttonBrownG3 = new JButton();
    JButton buttonYellowH3 = new JButton();
    JButton buttonYellowA2 = new JButton();
    JButton buttonBrownB2 = new JButton();
    JButton buttonYellowC2 = new JButton();
    JButton buttonBrownD2 = new JButton();
    JButton buttonYellowE2 = new JButton();
    JButton buttonBrownF2 = new JButton();
    JButton buttonYellowG2 = new JButton();
    JButton buttonBrownH2 = new JButton();
    JButton buttonBrownA1 = new JButton();
    JButton buttonYellowB1 = new JButton();
    JButton buttonBrownC1 = new JButton();
    JButton buttonYellowD1 = new JButton();
    JButton buttonBrownE1 = new JButton();
    JButton buttonYellowF1 = new JButton();
    JButton buttonBrownG1 = new JButton();
    JButton buttonYellowH1 = new JButton();

    JButton button1 = new JButton("Upsidedown");
    JButton button2 = new JButton("New Game");

    Listener listener = new Listener();
    ListenerButtonUpsidedown listenerButtonUpsidedown = new ListenerButtonUpsidedown();
    ListenerButtonNewGame listenerButtonNewGame = new ListenerButtonNewGame();

    public JButton[][] buttons = {
            {buttonYellowA8, buttonBrownB8, buttonYellowC8, buttonBrownD8, buttonYellowE8, buttonBrownF8, buttonYellowG8, buttonBrownH8},
            {buttonBrownA7, buttonYellowB7, buttonBrownC7, buttonYellowD7, buttonBrownE7, buttonYellowF7, buttonBrownG7, buttonYellowH7},
            {buttonYellowA6, buttonBrownB6, buttonYellowC6, buttonBrownD6, buttonYellowE6, buttonBrownF6, buttonYellowG6, buttonBrownH6},
            {buttonBrownA5, buttonYellowB5, buttonBrownC5, buttonYellowD5, buttonBrownE5, buttonYellowF5, buttonBrownG5, buttonYellowH5},
            {buttonYellowA4, buttonBrownB4, buttonYellowC4, buttonBrownD4, buttonYellowE4, buttonBrownF4, buttonYellowG4, buttonBrownH4},
            {buttonBrownA3, buttonYellowB3, buttonBrownC3, buttonYellowD3, buttonBrownE3, buttonYellowF3, buttonBrownG3, buttonYellowH3},
            {buttonYellowA2, buttonBrownB2, buttonYellowC2, buttonBrownD2, buttonYellowE2, buttonBrownF2, buttonYellowG2, buttonBrownH2},
            {buttonBrownA1, buttonYellowB1, buttonBrownC1, buttonYellowD1, buttonBrownE1, buttonYellowF1, buttonBrownG1, buttonYellowH1}
    };

    public void chessBoard() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon(GUIChessBoard.class.getResource("Images/chess.png"));
        setIconImage(image.getImage());
        setTitle("Chess Game");
        setVisible(true);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        //panel1.setSize(500, 50);
        //panel2.setSize(500, 500);

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new GridLayout(8, 8));

        panel1.add(button1);
        panel1.add(button2);

        panel1.setBackground(brown);
        panel2.setBackground(yellow);
        panel3.setBackground(brown);
        panel4.setBackground(brown);
        panel5.setBackground(brown);

        setLayout(new BorderLayout());

        button1.addActionListener(listenerButtonUpsidedown);
        button2.addActionListener(listenerButtonNewGame);
        button1.setBackground(yellow);
        button2.setBackground(yellow);

        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
        add(panel4, BorderLayout.EAST);
        add(panel5, BorderLayout.WEST);

        addButton(panel2, buttonYellowA8, yellow, listener);
        addButton(panel2, buttonBrownB8, brown, listener);
        addButton(panel2, buttonYellowC8, yellow, listener);
        addButton(panel2, buttonBrownD8, brown, listener);
        addButton(panel2, buttonYellowE8, yellow, listener);
        addButton(panel2, buttonBrownF8, brown, listener);
        addButton(panel2, buttonYellowG8, yellow, listener);
        addButton(panel2, buttonBrownH8, brown, listener);

        addButton(panel2, buttonBrownA7, brown, listener);
        addButton(panel2, buttonYellowB7, yellow, listener);
        addButton(panel2, buttonBrownC7, brown, listener);
        addButton(panel2, buttonYellowD7, yellow, listener);
        addButton(panel2, buttonBrownE7, brown, listener);
        addButton(panel2, buttonYellowF7, yellow, listener);
        addButton(panel2, buttonBrownG7, brown, listener);
        addButton(panel2, buttonYellowH7, yellow, listener);

        addButton(panel2, buttonYellowA6, yellow, listener);
        addButton(panel2, buttonBrownB6, brown, listener);
        addButton(panel2, buttonYellowC6, yellow, listener);
        addButton(panel2, buttonBrownD6, brown, listener);
        addButton(panel2, buttonYellowE6, yellow, listener);
        addButton(panel2, buttonBrownF6, brown, listener);
        addButton(panel2, buttonYellowG6, yellow, listener);
        addButton(panel2, buttonBrownH6, brown, listener);

        addButton(panel2, buttonBrownA5, brown, listener);
        addButton(panel2, buttonYellowB5, yellow, listener);
        addButton(panel2, buttonBrownC5, brown, listener);
        addButton(panel2, buttonYellowD5, yellow, listener);
        addButton(panel2, buttonBrownE5, brown, listener);
        addButton(panel2, buttonYellowF5, yellow, listener);
        addButton(panel2, buttonBrownG5, brown, listener);
        addButton(panel2, buttonYellowH5, yellow, listener);

        addButton(panel2, buttonYellowA4, yellow,listener);
        addButton(panel2, buttonBrownB4, brown, listener);
        addButton(panel2, buttonYellowC4, yellow, listener);
        addButton(panel2, buttonBrownD4, brown, listener);
        addButton(panel2, buttonYellowE4, yellow, listener);
        addButton(panel2, buttonBrownF4, brown, listener);
        addButton(panel2, buttonYellowG4, yellow, listener);
        addButton(panel2, buttonBrownH4, brown, listener);

        addButton(panel2, buttonBrownA3, brown, listener);
        addButton(panel2, buttonYellowB3, yellow, listener);
        addButton(panel2, buttonBrownC3, brown, listener);
        addButton(panel2, buttonYellowD3, yellow, listener);
        addButton(panel2, buttonBrownE3, brown, listener);
        addButton(panel2, buttonYellowF3, yellow, listener);
        addButton(panel2, buttonBrownG3, brown, listener);
        addButton(panel2, buttonYellowH3, yellow, listener);

        addButton(panel2, buttonYellowA2, yellow, listener);
        addButton(panel2, buttonBrownB2, brown, listener);
        addButton(panel2, buttonYellowC2, yellow, listener);
        addButton(panel2, buttonBrownD2, brown, listener);
        addButton(panel2, buttonYellowE2, yellow, listener);
        addButton(panel2, buttonBrownF2, brown, listener);
        addButton(panel2, buttonYellowG2, yellow,listener);
        addButton(panel2, buttonBrownH2, brown, listener);

        addButton(panel2, buttonBrownA1, brown, listener);
        addButton(panel2, buttonYellowB1, yellow, listener);
        addButton(panel2, buttonBrownC1, brown, listener);
        addButton(panel2, buttonYellowD1, yellow, listener);
        addButton(panel2, buttonBrownE1, brown, listener);
        addButton(panel2, buttonYellowF1, yellow, listener);
        addButton(panel2, buttonBrownG1, brown, listener);
        addButton(panel2, buttonYellowH1,yellow, listener);
    }

    public void addButton(JPanel panel, JButton button, Color color, ActionListener listener){
        button.setBackground(color);
        button.addActionListener(listener);
        panel.add(button);
    }

    public void makeLabel(String a){
        new JLabel(a);
    }

}
