package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller  {
    @FXML
    private ResourceBundle resources;


    @FXML
    public Button start;

    @FXML
    public MenuItem Open;

    @FXML
    public MenuItem Exit;

    @FXML
    public GridPane gridPane = null;

    private int stroka;

    private int stolbets;

   private ArrayList<String> allelements = new ArrayList<>(); //список, содержащий все строки в матрице

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


    @FXML
    public void initialize() throws IOException {
        Exit.setOnAction(event -> Platform.exit()); // Закрытие программы из меню
        About.setOnAction(event -> {  //Открытие окошка правил работы программы
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

        lines.clear(); //очищаем все листы от старых данных для заполнения новыми
        finish.clear();
        all.clear();
        allelements.clear();

        FileChooser fc = new FileChooser(); // диалоговое окно для выбора исходного файла
        fc.setTitle("Select file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"); // проверка исходного файла на верность типа
        fc.getExtensionFilters().add(extensionFilter);
        Stage stage2 = (Stage) AnchorPane.getScene().getWindow();
        File file = fc.showOpenDialog(stage2);
        try {
            FileReader fileReader = new FileReader(file);  //чтение входного файла
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            if (!lines.get(0).matches("\\d+[x]\\d+")) { //проверка правильности ввода размеров матрицы
                Parent root1;  //окно ошибки в случае неверного ввода входных данных
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
            String[] line1 = lines.get(0).split("x"); //отделяем цифру количества строк от цифры количества столбцов
            stroka = Integer.parseInt(line1[0]); //присваивание значений размеров матрицы
            stolbets = Integer.parseInt(line1[1]);

            for (int i = 1; i < lines.size(); i++) { //проверка на соответствие размера матрицы и введенной ее пользователем
                int k = 0;
                String x = lines.get(i).replace(" ", "");
                if (!lines.get(i).matches("([sf01]\\s)+[01sf]")) { //проверка на правильность ввода данных в матрице
                    Parent root1; //окно ошибки в случае неверного ввода данных
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
                if (lines.size() - 1 != stroka) { // проверка на соответсвие заданного значения строк в файле
                    Parent root1; //окно ошибки в случае несовпадения количества строк с заданной нами матрицей
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
                    Parent root1; //окно ошибки в случае несовпадения количества столбцов с заданной нами матрицей
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
                allelements.add(x); //добавление всех строк в данный лист
            }


            int cS = 0; //количество "s", содержащихся в матрице
            int cF = 0; //количество "f", содержащихся в матрице
            int countzero = 0; //количество "0", содержащихся в матрице
            int countone = 0; //количество "1", содержащихся в матрице

            for (int l = 0; l < allelements.size(); l++) {
                String[] element = allelements.get(l).split(" "); //преобразования массива для получения итогового списка всех элементов по отдельности
                String a = Arrays.toString(element).replace("[", "").replace("]", "");
                String[] b = a.split(""); //b - итоговый массив всех элементов

                for (int i1 = 0; i1 < b.length; i1++) { // в данном цикле подсчитывается количество 0, 1, s, f
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
                        Parent root1; //окно ошибки в случае наличия в матрице других элементов, помимо 0, 1, s, f
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
                Parent root1; //окно ошибки в случае неиндивидуальности s/f
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
            if (countzero == 0) { // проверка на отсутствие 0, т.е все поле заполнено 1 => путей нет, ибо двери все закрыты
                Parent root1; //окно ошибки в случае отсутствия 0 на поле
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
            try {
                Stage stage = new Stage();
                if (gridPane == null) { // делаю изначально проверку на наличие на основном окне таблицы,
                    // если она есть, то стирается и появляется новая, которую мы создаем в дальнейшем
                    gridPane = new GridPane();
                } else {
                    gridPane.getChildren().clear();
                    gridPane = new GridPane();
                }
                ColumnConstraints cc = new ColumnConstraints(); //отрисовка таблицы
                cc.setFillWidth(true);
                cc.setHgrow(Priority.ALWAYS);
                for (int h = 0; h < stolbets; h++) { // создаю столбцы и добавляю новые в таблицу
                    gridPane.getColumnConstraints().add(cc);
                }
                RowConstraints rc = new RowConstraints();
                rc.setFillHeight(true);
                rc.setVgrow(Priority.ALWAYS);

                for (int b = 0; b < stroka; b++) { // создаю строки и добавляю новые в таблицу
                    gridPane.getRowConstraints().add(rc);
                }
                gridPane.setHgap(0);// свожу расстояние границ вертикали и горизонтали к нулю
                gridPane.setVgap(0);
                gridPane.setGridLinesVisible(true);// делаю видимыми для пользователя границы матрицы

                int y = 0;
                int[][] arr = new int[stroka][stolbets];
                for (int c = 0; c < arr.length; c++) { // ввод наших данных в ячейки итоговой таблицы
                    for (int j = 0; j < arr[c].length; j++) {
                        Label lb = new Label(all.get(y));
                        lb.setFont(Font.font(40));
                        gridPane.add(lb, j, c);
                        y++;
                    }
                }
                //настраиваю характеристики таблицы, т е ее расположение, ее вид
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
        if (allelements.isEmpty()) { //проверка на наличие пустых строк в изначальной заданной нами матрице
            Parent root1; //окно ошибки в случае наличия пустых строк в заданной нами матрице
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

    Logic log = new Logic();
    private void logicOfGame1() throws Exception {
        ArrayList array;
        array = log.logicOfGame(allelements, startX, startY, finishX, finishY);

        try { // создание и отрисовка новой таблицы с итоговой матрицей
            Stage stage = new Stage();
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

            for (int b = 0; b < stroka; b++) {// создаю строки и добавляю новые в таблицу
                gridPane.getRowConstraints().add(rc);
            }
            gridPane.setHgap(0);// свожу расстояние границ вертикали и горизонтали к нулю
            gridPane.setVgap(0);
            gridPane.setGridLinesVisible(true);// делаю видимыми для пользователя границы матрицы


            int z = 0;
            for (int c = 0; c < stroka; c++) { // вношу наши конечные значения в ячейки матрицы
                for (int j = 0; j < stolbets; j++) {
                    Label lb = new Label(String.valueOf(array.get(z)));
                    lb.setFont(Font.font(40));
                    gridPane.add(lb, j, c);
                    z++;
                }
            }
            //настраиваю характеристики таблицы, т е ее расположение, ее вид
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
