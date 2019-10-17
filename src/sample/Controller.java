package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
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
    public Menu fileMenu;

    @FXML
    public Menu helpMenu;

    @FXML
    public MenuItem Open;

    @FXML
    public MenuItem Exit;

    public static Integer stroka;
    public static Integer stolbets;

    @FXML
    public MenuItem About;
    private Desktop desktop = Desktop.getDesktop();


    @FXML
    private AnchorPane AnchorPane;

    @FXML
    public Label label;


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

    public void OpenAction(ActionEvent actionEvent) throws IOException {
        ArrayList<String> all = new ArrayList<>(); //массив ВСЕХ элементов матрицы для дальнейшего его перебора и удобства


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
            ArrayList<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            String[] line1 = lines.get(0).split("x");
            Integer stroka = Integer.valueOf(line1[0]);
            Integer stolbets = Integer.valueOf(line1[1]);

            if (!lines.get(0).matches("\\d+[x]\\d+")) {//проверка ввода размера матрицы
                throw new Exception("Incorrect input");
            }
            ArrayList<String> allelements = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) { //проверка на ссответствие размера матрицы и введенной ее пользователем
                int k = 0;
                String x = lines.get(i).replace(" ", "");
                if (!lines.get(i).matches("([sf01]\\s)+[01sf]")) {
                    throw new Exception("Incorrect input");
                }
                if (lines.size() - 1 != stroka) {
                    System.out.println(lines.size() - 1);
                    throw new Exception("Mismatch between the number of line and the number of elements");
                }
                for (int j = 0; j < x.length(); j++) {
                    k++;
                }
                if (k != stolbets)
                    throw new Exception("Mismatch between the number of columns and the number of elements");
                allelements.add(x);
            }

            int cS = 0;
            int cF = 0;

            for (int i = 0; i < allelements.size(); i++) { //занесение всех элементов в один лист + проверка на индивидуальность элементов S и F
                String[] element = allelements.get(i).split(" ");
                String a = Arrays.toString(element).replace("[", "").replace("]", "");
                String[] b = a.split("");

                for (int i1 = 0; i1 < b.length; i1++) {
                    all.add(b[i1]);
                    if (b[i1].equals("s")) {
                        cS++;
                        continue;
                    }
                    if (b[i1].equals("f")) {
                        cF++;
                        continue;
                    }
                    if (b[i1].equals("0")) {
                        continue;
                    } else if (b[i1].equals("1")) {
                        continue;
                    } else throw new Exception("Invalid character value");
                }
            }
            if ((cS != 1) || (cF != 1)) throw new Exception("No element S or F or elements more than needed");//при нескольких значения S/F выбрасывается исключение
            try { // создание сетки, таблицы и ввод в нее наших данных
                Stage stage = new Stage();
                GridPane gridPane = new GridPane();
                ColumnConstraints cc = new ColumnConstraints();
                cc.setFillWidth(true);
                cc.setHgrow(Priority.ALWAYS);
                for (int i = 0; i < stolbets; i++) {
                    gridPane.getColumnConstraints().add(cc);
                }
                RowConstraints rc = new RowConstraints();
                rc.setFillHeight(true);
                rc.setVgrow(Priority.ALWAYS);

                for (int i = 0; i < stroka; i++) {
                    gridPane.getRowConstraints().add(rc);
                }
                gridPane.setHgap(0);
                gridPane.setVgap(0);

                gridPane.setGridLinesVisible(true);

                int y = 0;
                int[][] arr = new int[stroka][stolbets];
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr[i].length; j++) {
                        Label lb = new Label(all.get(y));
                        lb.setFont(Font.font(40));
                        gridPane.add(lb, j, i);
                        y++;
                    }
                }
                Scene scene2 = new Scene(gridPane, 100 * stolbets, 70 * stroka);
                stage.setScene(scene2);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

}






