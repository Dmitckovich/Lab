import java.util.Scanner;

public class Lab {
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

    static int floor=0, Y=4, X=0, torch=30;
    static boolean game=true;
    static final String win="Победа";
    static final String loss="Поражение";
    static String[] floorNames={"Низ", "Середина", "Верх"};

    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);

        while (game){
            printStatus(); printMap();
            System.out.print("Введите команду: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.isEmpty()){
                continue;
            }

            System.out.println();
            moves(input.charAt(0), scanner);

            if (map2[floor][Y][X] == 'F'){
                printStatus(); printMap();
                System.out.println(win);
                game=false;
            }

            if (torch<=0){
                printStatus(); printMap();
                System.out.println(loss);
                game=false;
            }
        }

        scanner.close();
    }

    public static void printStatus(){
        System.out.println("Статус: ");
        System.out.println("Этаж " + floor + ":" + floorNames[floor]);
        System.out.println("Координаты: Y=" + Y + ", X=" + X);
        System.out.println("Заряд факела:" + torch);
    }

    public static void printMap(){
        for(int y=0; y<map2[floor].length; y++){
            for(int x=0; x<map2[floor][y].length; x++){
                if(X==x & Y==y){
                    System.out.print("[P] ");
                } else{
                    System.out.print("[" + map2[floor][y][x] + "] ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void moves(char command, Scanner scanner){
        switch(command){
            case 'w': move(-1,0); break;
            case 's': move(1,0); break;
            case 'a': move(0,-1); break;
            case 'd': move(0,1); break;
            case 'e': ladder(scanner); break;
            case 'k': keys(scanner); break;
            default: System.out.println("Неизвестное направление");
        }
    }

    public static void move(int dY, int dX){
        int newY=Y+dY; int newX=X+dX;

        if(newY<0 || newX<0 || newY>map2[floor].length || newX>map2[floor][newY].length ||
                map2[floor][newY][newX]=='X' || map2[floor][newY][newX]=='#'){
            System.out.println("Нельзя сделать шаг"); return;
        }

        if(map2[floor][Y][X]=='P'){
            map2[floor][Y][X]='_';
        }

        Y=newY; X=newX;

        if(map2[floor][Y][X]=='+'){
            torch+=10;
            map2[floor][Y][X]='_';
        }

        torch--;
    }

    public static void ladder(Scanner scanner){
        System.out.print("Введите направление к лестнице: ");
        char lad=scanner.nextLine().toLowerCase().charAt(0);
        int ladY=Y; int ladX=X;

        switch(lad){
            case 'w': ladY--; break;
            case 's': ladY++; break;
            case 'a': ladX--; break;
            case 'd': ladX++; break;
            default: System.out.println("Неизвестное направление"); return;
        }

        if (ladY < 0 || ladY >= map2[floor].length || ladX < 0 || ladX >= map2[floor][ladY].length) {
            System.out.println("Там нет лестницы (выход за границы карты)");
            return;
        }

        if(map2[floor][ladY][ladX]=='U') {
            for (int y = 0; y < map2[floor + 1].length; y++) {
                for (int x = 0; x < map2[floor + 1][y].length; x++) {
                    if (map2[floor + 1][y][x] == 'D') {
                        floor++;
                        Y = y;
                        X = x;
                        torch--;
                        return;
                    }
                }
            }
        } else if(map2[floor][ladY][ladX]=='D'){
            for(int y=0; y<map2[floor-1].length; y++){
                for(int x=0; x<map2[floor-1][y].length; x++){
                    if(map2[floor-1][y][x]=='U'){
                        floor--;
                        Y=y; X=x;
                        torch--;
                        return;
                    }
                }
            }
        }
    }

    public static void keys(Scanner scanner){
        if(torch<5){
            System.out.println("Недостаточно энергии");
            return;
        }

        System.out.print("Введите направление к замку: ");
        char door=scanner.nextLine().toLowerCase().charAt(0);
        int doorY=Y; int doorX=X;

        switch(door){
            case 'w': doorY--; break;
            case 's': doorY++; break;
            case 'a': doorX--; break;
            case 'd': doorX++; break;
            default: System.out.println("Неизвестное направление"); return;
        }

        if(map2[floor][doorY][doorX]=='X'){
            map2[floor][doorY][doorX]='_';
            torch-=5;
        }
    }
}