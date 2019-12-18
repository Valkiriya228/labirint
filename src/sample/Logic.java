package sample;

import java.util.ArrayList;
import java.util.Arrays;




public class Logic {

    public  ArrayList<String> logicOfGame(ArrayList<String> allelements, int startX, int startY, int finishX, int finishY) throws Exception {
        ArrayList<String> allel = new ArrayList<>(); //лист всех итоговых элементов
        ArrayList<String> array = new ArrayList<>(); //лист всех элементов конечного двумерного массива
        for (String allelement : allelements) { //занесение всех элементов в один лист
            String[] element = allelement.split(" ");
            String a = Arrays.toString(element).replace("[", "").replace("]", "");
            String[] b = a.split("");
            allel.addAll(Arrays.asList(b)); //allel - итоговый список со всеми элементами
        }
        int stolbets = allelements.get(0).length(); //присваивание значений столбцу
        int stroka = allelements.size(); //присваивание значений строке

        String[][] Map1 = new String[stroka][stolbets]; //итоговый двумерный массив, который мы будем выводить в конце
        String[][] Map = new String[stroka][stolbets];  //имеем двумерный массив с нашими изначальными данными, то бишь 0, 1 и s/f
        String[][] cMap = new String[stroka][stolbets]; //двумерный массив, в процессе работы мы работаем с ним и его преобразовываем (ставим индикаторы и распространяем волну)
        int o = 0;
        for (int x = 0; x < stroka; x++) {
            for (int y = 0; y < stolbets; y++) {
                Map[x][y] = (allel.get(o));
                cMap[x][y] = "0";
                if (Map[x][y].equals("s")) { // заносим в память координаты точек старта (s)
                    startY = x;
                    startX = y;
                }
                if (Map[x][y].equals("f")) { // заносим в память координаты точек финиша (f)
                    finishY = x;
                    finishX = y;
                }
                o++;
            }
        }
        boolean add = true;
        int step = 0;
        for (int y = 0; y < stroka; y++) {
            for (int x = 0; x < stolbets; x++) {
                if ("1".equals(Map[y][x])) {
                    cMap[y][x] = "-2"; //индикатор стены (блокирует проход)
                } else {
                    cMap[y][x] = "-1"; // индикатор еще не ступал сюда
                }
            }
        }
        cMap[finishY][finishX] = String.valueOf(0); //инициализация точки финиша как "0"
                                                    //будем в дальнейшем для подсчета кратчайшего пути идти от финиша к старту
        while (add) {
            add = false;
            for (int y = 0; y < stroka; y++) { //начинаем уже само распространение волны, пока есть возможности
                for (int x = 0; x < stolbets; x++) {
                    if (cMap[y][x].equals(String.valueOf(step))) {  //смотрим все соседние клетки по вертикали и горизонтали и распространяем волну
                        if (y >= 1 && cMap[y - 1][x].equals("-1")) cMap[y - 1][x] = String.valueOf(step + 1);
                        if (x >= 1 && cMap[y][x - 1].equals("-1")) cMap[y][x - 1] = String.valueOf(step + 1);
                        if (y < stroka - 1 && cMap[y + 1][x].equals("-1")) cMap[y + 1][x] = String.valueOf(step + 1);
                        if (x < stolbets - 1 && cMap[y][x + 1].equals("-1")) cMap[y][x + 1] = String.valueOf(step + 1);
                    }
                }
            }
            step++; //увеличение шага для дальнейшего хода

            if (cMap[startY][startX].equals(String.valueOf(-1))) add = true; //если волна все еще не распространилась на стартовую точку, то продолжаем искать путь дальше
            else add = false;  //решение найдено, выходим из цикла while
            if (step > stroka * stolbets) add = false; //решение не найдено => решений нет, выходим из цикла while
        }

        for (int y = 0; y < stroka; y++) { //заносим в конечный двумерный массив точки старта и финиша а также закрытые двери (1)
            for (int x = 0; x < stolbets; x++) { // и двери, в которые индикатор не ступал и не будет ступать (0)
                if (cMap[y][x].equals(String.valueOf(-1))) Map1[y][x] = String.valueOf(0);
                else if (cMap[y][x].equals(String.valueOf(-2))) Map1[y][x] = String.valueOf(1);
                else if (y == startY && x == startX) {
                    Map1[y][x] = "s";
                }
                else if (y == finishY && x == finishX) {
                    Map1[y][x] = "f";
                }
                else if (Integer.parseInt(cMap[y][x]) > (-1))  Map1[y][x] = ("*" + cMap[y][x] + "*"); //ввод в матрицу ход со значением шага (step)
            }
        }

        int k = startY;
        int s = startX;
        int stepp = step - 1;
        while (stepp != 0) { // идем обратно от финиша к старту и оставляем только единственный наикратчайший путь
                             // (откидываем все остальные лишние шаги и пути, оставляем только один наикратчайший)
            if ((k >= 1 && Map1[k - 1][s].equals("*" + (stepp)+ "*"))) { //итоговый путь будет обозначен знаком "●"
                Map[k - 1][s] = "●";
                k = k - 1;
            } else if ((s >= 1 && Map1[k][s - 1].equals("*" + (stepp)+ "*"))) {
                Map[k][s - 1] = "●";
                s = s - 1;
            } else if ((k < stroka - 1 && Map1[k + 1][s].equals("*" + (stepp)+ "*"))) {
                Map[k + 1][s] = "●";
                k = k + 1;
            } else if ((s < stolbets - 1 && Map1[k][s + 1].equals("*" + (stepp)+ "*"))) {
                Map[k][s + 1] = "●";
                s = s + 1;
            }
            stepp--; //уменьшение шага для дальнейшего поиска
        }
        for (int y = 0; y < stroka; y++) { //занесение в итоговый список элементов значения
            array.addAll(Arrays.asList(Map[y]).subList(0, stolbets));
        }
        return array;
    }




}
