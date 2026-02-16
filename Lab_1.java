import java.util.Scanner;

public class Lab_1 {

    static char[][][] map2 = {
            {
                    {'P', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'B', 'L', 'B', 'O', 'O'}
            },
            {
                    {'L', 'O', 'O', 'B', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'B', 'O', 'O', 'O'},
                    {'B', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'O', 'O'}
            },
            {
                    {'O', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'B', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'O', 'O'},
                    {'O', 'O', 'O', 'O', 'B', 'O'},
                    {'O', 'B', 'O', 'F', 'O', 'O'}
            },
    };


    static int floor = 0, Y = 0, X = 0;
    static boolean game = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (game) {
            printStatus(); printMap();
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.isEmpty()) {
                continue;
            }

            processInput(input.charAt(0), scanner);

            if (map2[floor][Y][X] == 'F') {
                printStatus(); printMap();
                game = false;
            }

            if (map2[floor][Y][X] == 'L') {
                printStatus(); printMap();
                floor++;
            }
        }

        scanner.close();
    }

    private static void printStatus() {
        System.out.println("Статус:");
        System.out.println("Этаж " + floor);
        System.out.println("Координаты: Y = " + Y + ", X = " + X);
    }

    private static void printMap() {
        for (int y = 0; y < map2[floor].length; y++) {
            for (int x = 0; x < map2[floor][y].length; x++) {
                if (X == x && Y == y) {
                    System.out.print("[P] ");
                } else {
                    char cell = map2[floor][y][x];
                    System.out.print("[" + cell + "] ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void processInput(char command, Scanner scanner) {
        switch(command) {
            case 'w': move(-1,0); break;
            case 's': move(1,0); break;
            case 'a': move(0,-1); break;
            case 'd': move(0,1); break;
            default: System.out.println("Неизвестная команда");
        }
    }

    private static void move(int dY, int dX) {
        int newY=Y+dY; int newX=X+dX;

        if (newY<0 || newX<0 || newY>=map2[floor].length || newX>=map2[floor][newY].length || map2[floor][newY][newX]=='B') {
            System.out.println("Нельзя сделать шаг"); return;
        }

        if (map2[floor][Y][X] == 'P') {
            map2[floor][Y][X] = 'O';
        }

        Y=newY; X=newX;

    }
}