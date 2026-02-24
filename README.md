import java.util.Scanner;

public class Lab_01 {

    public static char[][][] map2 = {
            {
                    {'#', '#', '#', '#', '_', 'U'},
                    {'#', '_', '_', '_', '_', '#'},
                    {'#', '_', '#', '#', '#', '#'},
                    {'#', '_', '_', '_', '#', '#'},
                    {'P', '_', '#', '#', '#', '#'}
            },
            // === ЭТАЖ 1 (3x8) ===
            {
                    {'_', '_', '_', '_', '_', 'D', '_', '_'},
                    {'#', '_', '#', '#', '#', '#', '#', '+'},
                    {'U', '_', '_', '_', '_', '#', '#', '#'}
            },
            // === ЭТАЖ 2 (4x4) ===
            {
                    {'#', '#', 'F', '#'},
                    {'#', '#', 'X', '#'},
                    {'D', '_', '_', '#'},
                    {'#', '#', '#', '#'}

            }
    };

    static int floor = 0, Y = 4, X = 0, torch = 10000;
    static boolean game = true;
    static final String win = "Ловкость рук и никакого мошенничества. Башня осталась позади.";
    static final String loss = "Башня забрала свою плату. Ваша душа теперь принадлежит ей.";
    static String[] floorNames = {"Обрыв", "Тоннель", "Финал"};

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
                System.out.println(win);
                game = false;
            }

            if (torch <= 0) {
                printStatus(); printMap();
                System.out.println(loss);
                game = false;
            }
        }

        scanner.close();
    }

    private static void printStatus() {
        System.out.println("Статус:");
        System.out.println("Этаж " + floor + ": " + floorNames[floor]);
        System.out.println("Координаты: Y = " + Y + ", X = " + X);
        System.out.println("Заряд факела: " + torch);
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
            case 'e': ladder(scanner); break;
            case 'k': keys(scanner); break;
            default: System.out.println("Неизвестная команда");
        }
    }

    private static void move(int dY, int dX) {
        int newY=Y+dY; int newX=X+dX;

        if (newY<0 || newX<0 || newY>=map2[floor].length || newX>=map2[floor][newY].length ||
                map2[floor][newY][newX]=='X' || map2[floor][newY][newX]=='#') {
            System.out.println("Нельзя сделать шаг"); return;
        }

        if (map2[floor][Y][X] == 'P') {
            map2[floor][Y][X] = '_';
        }

        Y=newY; X=newX;

        if (map2[floor][Y][X]=='+') {
            torch+=10;
            map2[floor][Y][X]='_';
        }

        torch--;
    }

    private static void ladder(Scanner scanner) {
        System.out.print("Введите направление к лестнице: ");
        char direction=scanner.nextLine().toLowerCase().charAt(0);

        int checkY=Y; int checkX=X;

        switch (direction) {
            case 'w': checkY--; break;
            case 's': checkY++; break;
            case 'a': checkX--; break;
            case 'd': checkX++; break;
            default: System.out.println("Неизвестное направление"); return;
        }

        char targetCell=map2[floor][checkY][checkX];

        if (targetCell=='U') {
            for (int y=0; y<map2[floor+1].length; y++) {
                for (int x=0; x<map2[floor+1][y].length; x++) {
                    if (map2[floor+1][y][x]=='D') {
                        floor++;
                        Y=y; X=x;
                        torch--;
                        return;
                    }
                }
            }

        } else if (targetCell=='D') {
            for (int y = 0; y < map2[floor - 1].length; y++) {
                for (int x = 0; x < map2[floor - 1][y].length; x++) {
                    if (map2[floor - 1][y][x] == 'U') {
                        floor--;
                        Y = y;
                        X = x;
                        torch--;
                        return;
                    }
                }
            }
        }
    }

    private static void keys(Scanner scanner) {
        if (torch<5) {
            System.out.println("Недостаточно энергии");
            return;
        }

        System.out.print("Введите направление к замку: ");
        char direction=scanner.nextLine().toLowerCase().charAt(0);

        int targetY =Y; int targetX =X;

        switch (direction) {
            case 'w': targetY--; break;
            case 's': targetY++; break;
            case 'a': targetX--; break;
            case 'd': targetX++; break;
            default: System.out.println("Неизвестное направление"); return;
        }

        if(map2[floor][targetY][targetX]=='X') {
            map2[floor][targetY][targetX]='_';
            torch-=5;
        }
    }
}
