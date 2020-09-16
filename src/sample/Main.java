package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {
    public String simbolo = "X";
    public int vezJogador = 0;
    Button[] bts;
    private DadosJogadores dadosJogadores = new DadosJogadores();
    private Text tituloCenaPrincipal = new Text("Welcome");

    @Override
    public void start(Stage primaryStage) {

        tituloCenaPrincipal.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(tituloCenaPrincipal, 1, 0, 3, 1);

        bts = new Button[9];
        for (int i = 0; i < 9; i++) {
            bts[i] = new Button("V");
            bts[i].setFont(new Font("Calibri", 60));
        }
        bts[0].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(0);
            }
        });
        bts[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(1);
            }
        });
        bts[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(2);
            }
        });
        bts[3].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(3);
            }
        });
        bts[4].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(4);
            }
        });
        bts[5].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(5);
            }
        });
        bts[6].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(6);
                /* chama rotina para ver jogada no tabuleiro */
            }
        });
        bts[7].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(7);
            }
        });
        bts[8].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Jogada(8);
            }
        });

        int n = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                grid.add(bts[n], j, i);
                n++;
            }
        }

        FileChooser fileChooser = new FileChooser();
        Menu menuFile = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                CriaTelaNovoJogo(dadosJogadores);
            }
        });


        MenuItem loadGame = new MenuItem("Load Game");
        loadGame.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        loadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
            }
        });

        MenuItem saveGame = new MenuItem("Save Game");
        saveGame.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showSaveDialog(primaryStage);

            }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        menuFile.getItems().addAll(newGame, loadGame, saveGame, exit);
        Menu menuEdit = new Menu("Edit");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile, menuEdit);

        Label status = new Label();
        status.setLineSpacing(2);
        status.setText("Click File to begin");
        status.setAlignment(Pos.CENTER);
        status.setBackground(Background.EMPTY);

        Scene scene = new Scene(new VBox(), 500, 500);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, grid, status);


        primaryStage.setTitle("Jogo da Velha");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void CriaTelaNovoJogo(DadosJogadores dadosJogadores) {
        Scene cenaNovoJogo = new Scene(new VBox(), 350, 250);

        GridPane gridNewGame = new GridPane();
        gridNewGame.setAlignment(Pos.CENTER);
        gridNewGame.setHgap(10);
        gridNewGame.setVgap(10);
        gridNewGame.setPadding(new Insets(25, 25, 25, 25));

        Label labelJogadorUm = new Label("Nome Jogador 1:");
        gridNewGame.add(labelJogadorUm, 0, 1);

        TextField textoJogadorUm = new TextField();
        gridNewGame.add(textoJogadorUm, 1, 1);

        Label labelJogadorDois = new Label("Nome Jogador 2:");
        gridNewGame.add(labelJogadorDois, 0, 2);

        TextField textoJogadorDois = new TextField();
        gridNewGame.add(textoJogadorDois, 1, 2);

        Button button2 = new Button("Submeter");
        button2.setAlignment(Pos.CENTER);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dadosJogadores.setNomeJogadorUm(textoJogadorUm.getText());
                dadosJogadores.setNomeJogadorDois(textoJogadorDois.getText());
                cenaNovoJogo.getWindow().hide();
            }
        });

        HBox hbBt = new HBox(10);
        hbBt.setAlignment(Pos.CENTER);
        hbBt.getChildren().add(button2);
        gridNewGame.add(hbBt, 0, 3);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridNewGame.add(scenetitle, 0, 0, 2, 1);


        ((VBox) cenaNovoJogo.getRoot()).getChildren().addAll(gridNewGame);


        Stage stageNewGame = new Stage();
        stageNewGame.setTitle("Dados do novo Jogo");
        stageNewGame.setScene(cenaNovoJogo);
        stageNewGame.show();
    }

    public void Jogada(int simbol) {
        if (vezJogador == 0) {
            tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorUm());
            simbolo = "X";
            vezJogador = 1;
        } else {
            tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorDois());
            simbolo = "O";
            vezJogador = 0;
        }
        bts[simbol].setText(simbolo);
        bts[simbol].setDisable(true);
        //chamada para jogar no tabuleiro (tabuleiro.jogada(simbol))
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}