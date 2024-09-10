import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Minesweeper minesweeper = new Minesweeper();
        
        boolean end = true;
        boolean endSquared;
        while(end){
            Minesweeper.Board board = minesweeper.new Board(scanner);
            board.firstMove( scanner);
            board.otherMoves(scanner);
            endSquared = true;
            while(endSquared){
                System.out.println("Would you like to start another game if you would type Y if you like to stop type N");
                String yOrN = scanner.nextLine();
                yOrN.toLowerCase();
                if(yOrN == "y"){
                    endSquared = false;
                }
                if(yOrN == "y"){
                    endSquared = false;
                    end = false;
                }
                else{
                    System.out.println("Please input either Y to start a new game or N to stop playing");
                }
            }
            
        }
        
    }

    private class Board {
        private static int[][] boardHidden;
        private static int[][] boardVisible;
        private static boolean[][] bombLoc;
        private static boolean[][] flagLoc;
        private static int tiles;
        private static int bombAmount;
        private static int flagAmount;
        private static int uncoveredTiles = 0;
        private static int flagUsed = 0;
        private static int size;
        private static int sizeX;
        private static int sizeY;
        private static String line;

        private Board(Scanner scanner){
            size = selectDifficulty(scanner);
            switch(size){
                case 0:
                    sizeX = 8;
                    sizeY = 8;
                    tiles = 54;
                    bombAmount = 10;
                    flagAmount = 10;
                    int[][] tempHidden0 = {
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}
                    };
                    boardHidden = tempHidden0;
                    int[][] tempVisible0 = {
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1}                 
                    };
                    boardVisible = tempVisible0;
                    boolean[][] bombLoc0 = {
                        {false, false, false, false, false, false, false, false}, 
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false}
                    };
                    bombLoc = bombLoc0;
                    boolean[][] flagLoc0 = {
                        {false, false, false, false, false, false, false, false}, 
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false}
                    };
                    flagLoc = flagLoc0;
                    line = "----------------------------------------";
                    break;
                case 1:
                    sizeX = 16;
                    sizeY = 16;
                    tiles = 216;
                    bombAmount = 40;
                    flagAmount = 40;
                    int[][] tempHidden1 = {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                    };
                    boardHidden = tempHidden1;
                    int[][] tempVisible1 = {
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
                    };
                    boardVisible = tempVisible1;
                    boolean[][] bombLoc1 = {
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
                    bombLoc = bombLoc1;
                    boolean[][] flagLoc1 = {
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
                    flagLoc = flagLoc1;
                    line = "--------------------------------------------------------------------------------";
                    break;
                default:
                    sizeX = 16;
                    sizeY = 30;
                    tiles = 381;
                    bombAmount = 99;
                    flagAmount = 99;
                    int[][] tempHidden2 = {
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    };
                    boardHidden = tempHidden2;
                    int[][] tempVisible2 = {
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}                   
                    };
                    boardVisible = tempVisible2;
                    boolean[][] bombLoc2 = {
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
                    bombLoc = bombLoc2;
                    boolean[][] flagLoc2 = {
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                        {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}
                    };
                    flagLoc = flagLoc2;
                    line = "------------------------------------------------------------------------------------------------------------------------------------------------------";
                    break;                
            }
        ;}
    // -----
    // |0,0|
    // | B |
    // -----
    private int selectDifficulty(Scanner scanner){
        while(true){
            try {
                System.out.println("1: Beginner \n2: Intermediate \n3: Expert \n\nPlease input your desired difficulty (Input the asscoiated digit)");
                int difficulty = scanner.nextInt();
                if(difficulty < 4 && difficulty > 0){
                    return difficulty - 1;
                }
                else{
                    System.out.println("Please input the desired digit for your difficulty");
                }
                    
            }
            catch (Exception e){
                System.out.println("Please input the desired digit for your difficulty");
            }
        }
    }

    private static void firstMove(Scanner scanner){
        boolean outer = true;
        boolean inner1 = true;
        boolean inner2 = true;
        int x = -1;
        int y = -1;
        while(outer){
            printBoard();
            while(inner1){
                try {
                    System.out.println("Please input your the x cordinate");
                    int cordinateX = scanner.nextInt();
                    if(0 <= cordinateX && cordinateX < sizeX - 1){
                        x = cordinateX;
                        inner1 = false;
                        inner2 = true;
                    }
                    else{
                        System.out.println("Please input an x cordinate between 0 and " + (sizeX - 1));
                    }
                }
                catch (Exception e){
                    System.out.println("Please input an x cordinate between 0 and " + (sizeX - 1));
                }
            }
            while(inner2){
                try {
                    System.out.println("Please input your the y cordinate if you would like to go back type -1");
                    int cordinateY = scanner.nextInt();
                    if(cordinateY == -1){
                        inner2 = false;
                    }
                    else if(0 <= cordinateY && cordinateY < sizeY - 1){
                        y = cordinateY;
                        inner2 = false;
                        outer = false;
                    }
                    else{
                        System.out.println("Please input an y cordinate between 0 and " + (sizeY - 1));
                    }
                }
                catch (Exception e){
                    System.out.println("Please input an y cordinate between 0 and " + (sizeY - 1));
                }
            }
        }
        mapBombs(x, y);
        clearSpaces(x,y);
    }

    private static void otherMoves(Scanner scanner){
        boolean end = true;
        while(end){
            printBoard();
            int clearFlag = clearOrFlag(scanner);
            if(clearFlag == 2){
                end = false;
            }
            printBoard();
            if(otherCordinates(scanner,clearFlag == 0)){
                end = false;
            }
        }
        
    }

    public static int clearOrFlag(Scanner scanner){
        while(true){
            System.out.println("C: Clear Tile\nF: Flag Tile\nE: End");
            String clearOrFlag = scanner.nextLine();
            clearOrFlag.toLowerCase();
            if(clearOrFlag == "f"){
                return 0;
            }
            else if(clearOrFlag == "c"){
                return 1;
            }
            else if(clearOrFlag == "e"){
                return 2;
            }
            else{
                System.out.println("Please either input C for Clearing a tile or F for flagging a tile");
            }
        }
    }


    private static boolean otherCordinates(Scanner scanner, boolean clearFlag){
        boolean outer = true;
        boolean inner1 = true;
        boolean inner2 = true;
        int x = -1;
        int y = -1;
        while(outer){
            while(inner1){
                try {
                    System.out.println("Please input your the x cordinate or input -1 to end game");
                    int cordinateX = scanner.nextInt();
                    if(cordinateX == -1){
                        return true;
                    }
                    else if(0 <= cordinateX && cordinateX < sizeX - 1){
                        x = cordinateX;
                        inner1 = false;
                        inner2 = true;
                    }
                    else{
                        System.out.println("Please input an x cordinate between 0 and " + (sizeX - 1));
                    }
                }
                catch (Exception e){
                    System.out.println("Please input an x cordinate between 0 and " + (sizeX - 1));
                }
            }
            while(inner2){
                try {
                    System.out.println("Please input your the y cordinate or input -1 to go back");
                    int cordinateY = scanner.nextInt();
                    if(cordinateY == -1){
                        inner2 = false;
                        inner1 = true;
                    }
                    else if(0 <= cordinateY && cordinateY < sizeY - 1){
                        y = cordinateY;
                        inner2 = false;
                    }
                    else{
                        System.out.println("Please input an y cordinate between 0 and " + (sizeY - 1));
                    }
                }
                catch (Exception e){
                    System.out.println("Please input an y cordinate between 0 and " + (sizeY - 1));
                }
            }
            if(x != -1 && y != -1){
                if(clearFlag){
                    flag(x, y);
                }
                else{
                    clear(x, y);
                }
                outer = false;
            }
        }
        return false;
    }

    private static void flag(int x, int y){
        if(flagLoc[y][x]){
            flagUsed--;
            flagLoc[y][x] = false;
        }
        else{
            if(flagUsed < flagAmount){
                flagLoc[y][x] = true;
                flagUsed++;
            }
            else{
                System.out.println("All flags have been used. A flag must be removed from another tile to use another.");
            }
        }
    }

    private static boolean clear(int x, int y){
        if(checkBomb(x, y)){
            lost();
            return false;
        }
        else{
            clearSpaces(x,y);
            if(checkVictory()){
                victory();
                return false;
            }
            return true;
        }
    }

    

    private static boolean checkBomb(int x, int y){
        if(bombLoc[y][x]){
            return false;
        }
        else{
            return true;
        }
    }

    private static void clearSpaces(int x, int y){
        if(boardVisible[y][x] != -1){
            return;
        }
        else if(boardHidden[y][x] != 0){
            boardVisible[y][x] = boardHidden[y][x];
        }
        else{
            boardVisible[y][x] = boardHidden[y][x];
            uncoveredTiles++;
            clearSpaces(y + 1, x);
            clearSpaces(y - 1, x);
            clearSpaces(y, x + 1);
            clearSpaces(y, x - 1);
            clearSpaces(y + 1, x + 1);
            clearSpaces(y + 1, x - 1);
            clearSpaces(y - 1, x - 1);
            clearSpaces(y - 1, x - 1);
        }
    }

    private static boolean checkVictory(){
        return uncoveredTiles == tiles - bombAmount;
    }

    private static void victory(){
        System.out.println("You have won!!!");
    }

    private static void lost(){
        for(int i = 0; i < sizeY; i++){
            for(int j =0; j < sizeX; j++){
                if(bombLoc[i][j]){
                    boardVisible[i][j] =  10;
                }
            }
        }
        printBoard();
        System.out.println("You have lost");
    }

    private static void mapBombs(int initialX, int initialY){
        Random random = new Random();
        int x;
        int y;
        for(int i = 0; i < bombAmount; i++){
            x = random.nextInt(sizeX);
            y = random.nextInt(sizeY);
            if(!bombLoc[y][x] && (x != initialX && y != initialY)){
                bombLoc[y][x] = true;
                boardHidden[y][x] += 1;
                if(x == 0 || x == sizeX -1 || y == 0 || y == sizeY - 1){
                    if(x == 0 || x == sizeX -1){
                        if(x == 0){
                            if(y == 0 || y == sizeY - 1){
                                if(y == 0){
                                    boardHidden[y + 1][x] += 1;
                                    boardHidden[y][x + 1] += 1;
                                    boardHidden[y + 1][x + 1] += 1;
                                }
                                else{
                                    boardHidden[y + 1][x] += 1;
                                    boardHidden[y][x - 1] += 1;
                                    boardHidden[y + 1][x - 1] += 1;
                                }
                            }
                            else{
                                boardHidden[y + 1][x] += 1;
                                boardHidden[y][x + 1] += 1;
                                boardHidden[y][x - 1] += 1;
                                boardHidden[y + 1][x + 1] += 1;
                                boardHidden[y + 1][x - 1] += 1;
                            }
                        }
                        else{
                            if(y == 0 || y == sizeY - 1){
                                if(y == 0){
                                    boardHidden[y][x + 1] += 1;
                                    boardHidden[y - 1][x] += 1;
                                    boardHidden[y - 1][x + 1] += 1;
                                }
                                else{
                                    boardHidden[y - 1][x] += 1;
                                    boardHidden[y][x - 1] += 1;
                                    boardHidden[y - 1][x - 1] += 1;
                                }
                            }
                            else{
                                boardHidden[y][x + 1] += 1;
                                boardHidden[y - 1][x] += 1;
                                boardHidden[y][x - 1] += 1;
                                boardHidden[y - 1][x + 1] += 1;
                                boardHidden[y - 1][x - 1] += 1;
                            }
                        }
                    }
                    else{
                        if(y == 0){
                            boardHidden[y + 1][x] += 1;
                            boardHidden[y][x + 1] += 1;
                            boardHidden[y - 1][x] += 1;
                            boardHidden[y + 1][x + 1] += 1;
                            boardHidden[y - 1][x + 1] += 1;
                        }
                        else{
                            boardHidden[y + 1][x] += 1;
                            boardHidden[y - 1][x] += 1;
                            boardHidden[y][x - 1] += 1;
                            boardHidden[y + 1][x - 1] += 1;
                            boardHidden[y - 1][x - 1] += 1;
                        }
                    }
                }
                else{
                    boardHidden[y + 1][x] += 1;
                    boardHidden[y][x + 1] += 1;
                    boardHidden[y - 1][x] += 1;
                    boardHidden[y][x - 1] += 1;
                    boardHidden[y + 1][x + 1] += 1;
                    boardHidden[y + 1][x - 1] += 1;
                    boardHidden[y - 1][x + 1] += 1;
                    boardHidden[y - 1][x - 1] += 1;
                }
            }
            else{
                i--;
            }
        }
    }

    private static void printBoard(){
        StringBuilder upper;
        StringBuilder lower;
        for(int i = 0; i < sizeX; i++){
            upper = new StringBuilder();
            lower = new StringBuilder();
            System.out.println(line);
            for(int j = 0; j < sizeY; j++){
                upper.append("|" + j + "," + i +  "|");
                if(boardVisible[i][j] == -1 || boardVisible[i][j] == 10 || boardVisible[i][j] == 11){
                    if(boardVisible[i][j] == -1){
                        lower.append("| " + "?" + " |");
                    }
                    else if(boardVisible[i][j] == 11){
                        lower.append("| " + "F" + " |");
                    }
                    else{
                        lower.append("| " + "B" + " |");
                    }
                }
                else{
                    lower.append("| " + boardVisible[i][j] + " |");
                }
            }
            System.out.println(upper.toString());
            System.out.println(lower.toString());
        }
        System.out.println(line);
    }

    // 0 no bombs
    // 1 - 9 there is a bombs in the sounding tiles
    // 10 bomb in sqaure 
    // 11 flag in square
    }
}