
public class Board {

    EmptyPlace emptyPlace = new EmptyPlace("empty", "empty");

    King bK = new King("bKing", "black");
    King wK = new King("wKing", "white");

    Queen bQ = new Queen("bQueen", "black");
    Queen wQ = new Queen("wQueen", "white");

    Bishop bB1 = new Bishop("bBishop1", "black");
    Bishop bB2 = new Bishop("bBishop2", "black");
    Bishop wB1 = new Bishop("wBishop1", "white");
    Bishop wB2 = new Bishop("wBishop2", "white");

    Knight bKn1 = new Knight("bKnight1", "black");
    Knight bKn2 = new Knight("bKnight2", "black");
    Knight wKn1 = new Knight("wKnight1", "white");
    Knight wKn2 = new Knight("wKnight2", "white");

    Rook bR1 = new Rook("bRook1", "black");
    Rook bR2 = new Rook("bRook2", "black");
    Rook wR1 = new Rook("wRook1", "white");
    Rook wR2 = new Rook("wRook2", "white");

    Pawn bP1 = new Pawn("bPawn1", "black");
    Pawn bP2 = new Pawn("bPawn2", "black");
    Pawn bP3 = new Pawn("bPawn3", "black");
    Pawn bP4 = new Pawn("bPawn4", "black");
    Pawn bP5 = new Pawn("bPawn5", "black");
    Pawn bP6 = new Pawn("bPawn6", "black");
    Pawn bP7 = new Pawn("bPawn7", "black");
    Pawn bP8 = new Pawn("bPawn8", "black");

    Pawn wP1 = new Pawn("wPawn1", "white");
    Pawn wP2 = new Pawn("wPawn2", "white");
    Pawn wP3 = new Pawn("wPawn3", "white");
    Pawn wP4 = new Pawn("wPawn4", "white");
    Pawn wP5 = new Pawn("wPawn5", "white");
    Pawn wP6 = new Pawn("wPawn6", "white");
    Pawn wP7 = new Pawn("wPawn7", "white");
    Pawn wP8 = new Pawn("wPawn8", "white");

    AbstractFigure[][] board = {
            {bR1, bKn1, bB1, bQ, bK, bB2, bKn2, bR2},
            {bP1, bP2, bP3, bP4, bP5, bP6, bP7, bP8},
            {emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace},
            {emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace},
            {emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace},
            {emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace, emptyPlace},
            {wP1, wP2, wP3, wP4, wP5, wP6, wP7, wP8},
            {wR1, wKn1, wB1, wQ, wK, wB2, wKn2, wR2}
    };

}
