package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller  {
    @FXML
    private ResourceBundle resources;

    @FXML
    private MenuBar menuBar;

    @FXML
    private URL location;

    @FXML
    public Button start;



    @FXML
    public Menu fileMenu;

    @FXML
    public Menu helpMenu;

    @FXML
    public MenuItem Open;

    @FXML
    public MenuItem Exit;

    @FXML
    public GridPane gridPane = null;

    public Integer stroka;
    public Integer stolbets;
    public ArrayList<String> allelements = new ArrayList<>();

    private int startX;
    private int startY;
    private int finishX;
    private int  finishY;



    @FXML
    public MenuItem About;



    @FXML
    private AnchorPane AnchorPane;

    @FXML
    public Label label;

       Logic logic = new Logic();

    @FXML
    public void initialize() throws IOException {
        Exit.setOnAction(event -> Platform.exit());
        About.setOnAction(event -> {
            Parent root1;
            try {
                root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("about.fxml")), resources);
                Stage stage1 = new Stage();
                stage1.setTitle("Rules of the game");
                stage1.setScene(new Scene(root1, 600, 556));
                stage1.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }
    private ArrayList<String> lines = new ArrayList<>();
    private ArrayList<String> all = new ArrayList<>();
    private ArrayList<String> finish = new ArrayList<>();

    public void OpenAction(ActionEvent actionEvent) throws Exception {
        //массив ВСЕХ элементов матрицы для дальнейшего его перебора и удобства

        lines.clear();
        finish.clear();
        all.clear();
        allelements.clear();

        FileChooser fc = new FileChooser();
        fc.setTitle("Select file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt");//Фильтр расширения файла
        fc.getExtensionFilters().add(extensionFilter);
        Stage stage2 = (Stage) AnchorPane.getScene().getWindow();
        File file = fc.showOpenDialog(stage2);
        try {
            FileReader fileReader = new FileReader(file);// разбиение матрицы по строкам и ее чтение для дальнейших действий
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            if (!lines.get(0).matches("\\d+[x]\\d+")) {//проверка ввода размера матрицы
                Parent root1;
                try {
                    root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                    Stage stage1 = new Stage();
                    stage1.setTitle("Error");
                    stage1.setScene(new Scene(root1, 600, 556));
                    stage1.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                throw new Exception();
            }
            String[] line1 = lines.get(0).split("x");
            int stroka = Integer.parseInt(line1[0]);
            int stolbets = Integer.parseInt(line1[1]);




            for (int i = 1; i < lines.size(); i++) { //проверка на ссответствие размера матрицы и введенной ее пользователем
                int k = 0;
                String x = lines.get(i).replace(" ", "");
                if (!lines.get(i).matches("([sf01]\\s)+[01sf]")) {
                    Parent root1;
                    try {
                        root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                        Stage stage1 = new Stage();
                        stage1.setTitle("Error");
                        stage1.setScene(new Scene(root1, 600, 556));
                        stage1.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    throw new Exception();
                }
                if (lines.size() - 1 != stroka) {
                    Parent root1;
                    try {
                        root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                        Stage stage1 = new Stage();
                        stage1.setTitle("Error");
                        stage1.setScene(new Scene(root1, 600, 556));
                        stage1.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    throw new Exception();
                }
                for (int j = 0; j < x.length(); j++) {
                    k++;
                }
                if (k != stolbets) { // проверка на соответсвие заданного значения столбцов в файле
                                     // и количества элементов в строке файла, при несоответствии выбрасываем исключение
                    Parent root1;
                    try {
                        root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                        Stage stage1 = new Stage();
                        stage1.setTitle("Error");
                        stage1.setScene(new Scene(root1, 600, 556));
                        stage1.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    throw new Exception();
                }
                allelements.add(x);
            }


            int cS = 0;
            int cF = 0;
            int countzero = 0;
            int countone = 0;

            for (int l = 0; l < allelements.size(); l++) { //занесение всех элементов в один лист + проверка на индивидуальность элементов S и F
                String[] element = allelements.get(l).split(" ");
                String a = Arrays.toString(element).replace("[", "").replace("]", "");
                String[] b = a.split("");

                for (int i1 = 0; i1 < b.length; i1++) { // в данном цикле подсчитываю количество 0, 1, s, f
                    all.add(b[i1]);                     // и при наличии несоответствий с правилами, выбрасываем исключение.
                    if (b[i1].equals("s")) {
                        cS++;
                        continue;
                    }
                    if (b[i1].equals("f")) {
                        cF++;
                        continue;
                    }
                    if (b[i1].equals("0")) {
                        countzero++;
                    } else if (b[i1].equals("1")) {
                        countone++;
                    } else {
                        Parent root1;
                        try {
                            root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                            Stage stage1 = new Stage();
                            stage1.setTitle("Error");
                            stage1.setScene(new Scene(root1, 600, 556));
                            stage1.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        throw new Exception();
                    }
                }
            }


            if ((cS != 1) || (cF != 1)) { //проверка на индивидуальность старта (s) и финиша (f)
                Parent root1;
                try {
                    root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                    Stage stage1 = new Stage();
                    stage1.setTitle("Error");
                    stage1.setScene(new Scene(root1, 600, 556));
                    stage1.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }//при нескольких значения S/F выбрасывается исключение
                throw new Exception();
            }
            if (countzero == 0) { // проверка на отсутствие нулей
                Parent root1;
                try {
                    root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                    Stage stage1 = new Stage();
                    stage1.setTitle("Error");
                    stage1.setScene(new Scene(root1, 600, 556));
                    stage1.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }  //при нескольких значения S/F выбрасывается исключение

            }

            try { // создание сетки, таблицы и ввод в нее наших данных
                Stage stage = new Stage();
                // GridPane gridPane = new GridPane();
                if (gridPane == null) { // делаю изначально проверку на наличие на основном окне таблицы,
                    // если она есть, то стирается и появляется новая, которую мы создаем в дальнейшем
                    gridPane = new GridPane();
                } else {
                    gridPane.getChildren().clear();
                    gridPane = new GridPane();
                }
                ColumnConstraints cc = new ColumnConstraints();
                cc.setFillWidth(true);
                cc.setHgrow(Priority.ALWAYS);
                for (int h = 0; h < stolbets; h++) {
                    gridPane.getColumnConstraints().add(cc);
                }
                RowConstraints rc = new RowConstraints();
                rc.setFillHeight(true);
                rc.setVgrow(Priority.ALWAYS);

                for (int b = 0; b < stroka; b++) {// создаю столбцы и добавляю новые в матрицу
                    gridPane.getRowConstraints().add(rc);
                }
                gridPane.setHgap(0);// свожу расстояние границ вертикали и горизонтали к нулю
                gridPane.setVgap(0);
                gridPane.setGridLinesVisible(true);// делаю видимыми для пользователя границы матрицы

                int y = 0;
                int[][] arr = new int[stroka][stolbets];


                for (int c = 0; c < arr.length; c++) { // вношу наши данные значения в ячейки матрицы
                    for (int j = 0; j < arr[c].length; j++) {
                        Label lb = new Label(all.get(y));
                        lb.setFont(Font.font(40));
                        gridPane.add(lb, j, c);
                        y++;
                    }
                }
                gridPane.setLayoutX(375); // размещаю в окне матрицу в середине для удобства пользователя
                gridPane.setLayoutY(100);
                gridPane.setMinWidth(500);
                gridPane.setMinHeight(500);
                AnchorPane.getChildren().add(gridPane); // отображаю матрицу на наше основное окно
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (allelements.isEmpty()) {
            Parent root1;
            try {
                root1 = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("error1.fxml")), resources);
                Stage stage1 = new Stage();
                stage1.setTitle("Error");
                stage1.setScene(new Scene(root1, 600, 556));
                stage1.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }




    private ArrayList<String> allel = new ArrayList<>();
    private ArrayList<String> array = new ArrayList<>(); //занесение в лист всех элементов конечного двумерного массива
    private void logicOfGame1() throws Exception {// все данные которые мы задаем изначально -
        // это начальные и конечные координаты финища и старта
         allel.clear();
         array.clear();


        for (String allelement : allelements) {         //занесение всех элементов в один лист + проверка на индивидуальность элементов S и F
            String[] element = allelement.split(" ");
            String a = Arrays.toString(element).replace("[", "").replace("]", "");
            String[] b = a.split("");
            allel.addAll(Arrays.asList(b)); //allel - итоговый список со всеми элементами
        }
        int stolbets = allelements.get(0).length();
        int stroka = allelements.size();

        String[][] Map1 = new String[stroka][stolbets]; //итоговый двумерный массив, который мы будем выводить в конце
        String[][] Map = new String[stroka][stolbets]; //имеем двумерный массив с нашими изначальными данными, то бишь 0, 1 и старт/финиш.
        String[][] cMap = new String[stroka][stolbets];//двумерный массив, в процессе работы мы работаем с ним и его преобразовываем
        int o = 0; // заносим в память координаты точек старта и финиша
        for (int x = 0; x < stroka; x++) {
            for (int y = 0; y < stolbets; y++) {
                Map[x][y] = (allel.get(o));
                cMap[x][y] = "0";
                if (Map[x][y].equals("s")) {
                    startY = x;
                    startX = y;
                }
                if (Map[x][y].equals("f")) {
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
        cMap[finishY][finishX] = String.valueOf(0); //будем в дальнейшем для подсчета кратчайшего пути идти от финиша к старту
        while (add) {
            add = false;
            for (int y = 0; y < stroka; y++) { //начинаем уже само распространение волны, пока есть возможности
                for (int x = 0; x < stolbets; x++) {
                    if (cMap[y][x].equals(String.valueOf(step))) {
                        if (y >= 1 && cMap[y - 1][x].equals("-1")) cMap[y - 1][x] = String.valueOf(step + 1);    //смотрим все соседние клетки по вертикали и горизонтали и распространяем волну
                        if (x >= 1 && cMap[y][x - 1].equals("-1")) cMap[y][x - 1] = String.valueOf(step + 1);
                        if (y < stroka - 1 && cMap[y + 1][x].equals("-1")) cMap[y + 1][x] = String.valueOf(step + 1);
                        if (x < stolbets - 1 && cMap[y][x + 1].equals("-1")) cMap[y][x + 1] = String.valueOf(step + 1);
                    }
                }
            }
            step++;

            add = cMap[startY][startX].equals(String.valueOf(-1)); //решение найдено
            if (step > stroka * stolbets) add = false; //решение не найдено
        }
            for (int y = 0; y < stroka; y++) {
                for (int x = 0; x < stolbets; x++) {
                    if (cMap[y][x].equals(String.valueOf(-1))) Map1[y][x] = String.valueOf(0);
                    else if (cMap[y][x].equals(String.valueOf(-2))) Map1[y][x] = String.valueOf(1);
                    else if (y == startY && x == startX) { //заносим в конечный двумерный массив точки старта и финиша
                        Map1[y][x] = "s";
                    }
                    else if (y == finishY && x == finishX) {
                        Map1[y][x] = "f";
                    }
                    else if (Integer.parseInt(cMap[y][x]) > (-1))  Map1[y][x] = String.valueOf("*" + cMap[y][x] + "*");
                }
            }



            int k = startY;
            int s = startX;

            int stepp = step - 1;
            while (stepp != 0){
                if ((k >= 1 && Map1[k - 1][s].equals("*" + (stepp)+ "*"))) {
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
                stepp--;
            }


        for (int y = 0; y < stroka; y++) {
            array.addAll(Arrays.asList(Map[y]).subList(0, stolbets));
        }

        try { // создание и отрисовка новой таблицы с итоговой матрицей
            Stage stage = new Stage();
            // GridPane gridPane = new GridPane();
            if (gridPane == null) { // делаю изначально проверку на наличие на основном окне таблицы,
                // если она есть, то стирается и появляется новая, которую мы создаем в дальнейшем
                gridPane = new GridPane();
            } else {
                gridPane.getChildren().clear();
                gridPane = new GridPane();
            }
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            for (int h = 0; h < stolbets; h++) {
                gridPane.getColumnConstraints().add(cc);
            }
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);

            for (int b = 0; b < stroka; b++) {// создаю столбцы и добавляю новые в матрицу
                gridPane.getRowConstraints().add(rc);
            }
            gridPane.setHgap(0);// свожу расстояние границ вертикали и горизонтали к нулю
            gridPane.setVgap(0);
            gridPane.setGridLinesVisible(true);// делаю видимыми для пользователя границы матрицы


            int z = 0;

            for (int c = 0; c < Map.length; c++) { // вношу наши данные значения в ячейки матрицы
                for (int j = 0; j < Map[c].length; j++) {
                    Label lb = new Label(String.valueOf(array.get(z)));
                    lb.setFont(Font.font(40));
                    gridPane.add(lb, j, c);
                    z++;
                }
            }
            gridPane.setLayoutX(375); // размещаю в окне матрицу в середине для удобства пользователя
            gridPane.setLayoutY(100);
            gridPane.setMinWidth(500);
            gridPane.setMinHeight(500);
            AnchorPane.getChildren().add(gridPane); // отображаю матрицу на наше основное окно
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void logicOfGame(ActionEvent actionEvent) throws Exception {
        logicOfGame1();
    }




}






