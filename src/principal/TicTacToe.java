package principal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TicTacToe extends Application {

    private final String SIMBOLO_JOGADOR_UM = "X";
    private final String SIMBOLO_JOGADOR_DOIS = "O";
    private final DadosJogadores dadosJogadores = new DadosJogadores();
    private final Text tituloCenaPrincipal = new Text("Welcome");

    private Button[][] bts;
    public String simbolo = SIMBOLO_JOGADOR_UM;
    public int vezJogador = 0;
    public int numeroDeJogadas;

    @Override
    public void start(Stage primaryStage) {
        numeroDeJogadas = 0;

        tituloCenaPrincipal.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(tituloCenaPrincipal, 1, 0, 3, 1);

        inicializaBotoes(grid);

        FileChooser fileChooser = new FileChooser();
        Menu menuFile = new Menu("File");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(event -> criaJanelaNovoJogo(dadosJogadores));


        MenuItem loadGame = new MenuItem("Load Game");
        loadGame.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        loadGame.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(primaryStage);
        });

        MenuItem saveGame = new MenuItem("Save Game");
        saveGame.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveGame.setOnAction(event -> {
            File file = fileChooser.showSaveDialog(primaryStage);

        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> System.exit(0));

        menuFile.getItems().addAll(newGame, loadGame, saveGame, exit);
        Menu menuEdit = new Menu("Edit");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile, menuEdit);

//        Label status = new Label();
//        status.setLineSpacing(2);
//        status.setText("Click File to begin");
//        status.setAlignment(Pos.CENTER);
//        status.setBackground(Background.EMPTY);

        Scene scene = new Scene(new VBox(), 500, 500);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar, grid);


        primaryStage.setTitle("Jogo da Velha");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void inicializaBotoes(GridPane grid) {

        bts = new Button[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                bts[i][j] = new Button("V");
                bts[i][j].setFont(new Font("Calibri", 60));
            }
        }

        bts[0][0].setOnAction(event -> realizaJogada(0, 0));
        bts[0][1].setOnAction(event -> realizaJogada(0, 1));
        bts[0][2].setOnAction(event -> realizaJogada(0, 2));
        bts[1][0].setOnAction(event -> realizaJogada(1, 0));
        bts[1][1].setOnAction(event -> realizaJogada(1, 1));
        bts[1][2].setOnAction(event -> realizaJogada(1, 2));
        bts[2][0].setOnAction(event -> realizaJogada(2, 0));
        bts[2][1].setOnAction(event -> realizaJogada(2, 1));
        bts[2][2].setOnAction(event -> realizaJogada(2, 2));
        numeroDeJogadas = 0;

        int n = 0;
        int k = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                grid.add(bts[n][k], j, i);
                k++;
            }
            n++;
            k = 0;
        }
    }

    public void realizaJogada(int linha, int coluna) {

        if (vezJogador == 0) {
            tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorUm());
            simbolo = SIMBOLO_JOGADOR_UM;
            vezJogador = 1;
        } else {
            tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorDois());
            simbolo = SIMBOLO_JOGADOR_DOIS;
            vezJogador = 0;
        }

        bts[linha][coluna].setText(simbolo);
        bts[linha][coluna].setDisable(true);
        numeroDeJogadas++;

        if (checaVitoria()) {
            criaJanelaFimJogo(false);
        } else if (numeroDeJogadas == 9) {
            System.out.println("EMPATOU");
            criaJanelaFimJogo(true);
        }
    }

    private void criaJanelaFimJogo(boolean isEmpate) {

        Scene cenaFimJogo = new Scene(new VBox(), 350, 250);

        GridPane gridFimJogo = new GridPane();
        gridFimJogo.setAlignment(Pos.CENTER);
        gridFimJogo.setHgap(10);
        gridFimJogo.setVgap(10);
        gridFimJogo.setPadding(new Insets(25, 25, 25, 25));

        ImageView imageView = new ImageView();

        try {
            if (isEmpate) {
                Image imagem = new Image(new FileInputStream("images/draw.jpg"));
                imageView = new ImageView(imagem);
                imageView.setFitHeight(150);
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);

                Label labelEmpate = new Label("Parabéns, você empataram.");
                labelEmpate.setAlignment(Pos.BASELINE_CENTER);

                gridFimJogo.add(labelEmpate, 1, 0);
                gridFimJogo.add(imageView, 1, 1);

            } else {
                String nomeGanhador = vezJogador == 0 ? dadosJogadores.getNomeJogadorDois() : dadosJogadores.getNomeJogadorUm();
                Label labelVencedor = new Label("O vencedor é: " + nomeGanhador);
                labelVencedor.setAlignment(Pos.BASELINE_CENTER);


                Image imagem = new Image(new FileInputStream("images/win.jpg"));
                imageView = new ImageView(imagem);
                imageView.setFitHeight(200);
                imageView.setFitWidth(250);
                imageView.setPreserveRatio(true);



                gridFimJogo.add(labelVencedor, 1, 0);
                gridFimJogo.add(imageView, 1, 1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Button botaoReset = new Button("Novo Jogo");
        botaoReset.setAlignment(Pos.CENTER);
        botaoReset.setOnAction(event -> {
            reiniciaJogo();
            cenaFimJogo.getWindow().hide();
        });

        gridFimJogo.add(botaoReset, 1, 2);

        ((VBox) cenaFimJogo.getRoot()).getChildren().addAll(gridFimJogo);


        Stage stageNewGame = new Stage();
        stageNewGame.setTitle("Fim de Jogo");
        stageNewGame.setScene(cenaFimJogo);
        stageNewGame.show();
    }

    private void reiniciaJogo() {
    }

    private void criaJanelaNovoJogo(DadosJogadores dadosJogadores) {
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
        button2.setOnAction(event -> {
            dadosJogadores.setNomeJogadorUm(textoJogadorUm.getText());
            dadosJogadores.setNomeJogadorDois(textoJogadorDois.getText());
            tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorUm());
            cenaNovoJogo.getWindow().hide();
        });

        HBox hbBt = new HBox(10);
        hbBt.setAlignment(Pos.CENTER);
        hbBt.getChildren().add(button2);
        gridNewGame.add(hbBt, 0, 3);

        Text scenetitle = new Text("Dados dos Jogadores");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridNewGame.add(scenetitle, 0, 0, 2, 1);

        numeroDeJogadas = 0;

        ((VBox) cenaNovoJogo.getRoot()).getChildren().addAll(gridNewGame);


        Stage stageNewGame = new Stage();
        stageNewGame.setTitle("Dados do novo Jogo");
        stageNewGame.setScene(cenaNovoJogo);
        stageNewGame.show();
    }

    public Boolean checaVitoria() {

        int jogador1 = 0;
        int jogador2 = 0;

        //verifica Linhas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0) {
                    jogador1++;
                } else if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0) {
                    jogador2++;
                }
            }

            if (jogador1 == 3 || jogador2 == 3) {
                return true;
            }

            jogador1 = 0;
            jogador2 = 0;
        }

        //verifica Colunas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (bts[j][i].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0) {
                    jogador1++;
                } else if (bts[j][i].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0) {
                    jogador2++;
                }
            }

            if (jogador1 == 3 || jogador2 == 3) {
                return true;
            }

            jogador1 = 0;
            jogador2 = 0;
        }

        //Verifica Diagonal Direita->Esquerda
        for (int i = 0, j = 0; i < 3; i++, j++) {
            if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0) {
                jogador1++;
            } else if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0) {
                jogador2++;
            }
        }

        if (jogador1 == 3 || jogador2 == 3) {
            return true;
        }

        jogador1 = 0;
        jogador2 = 0;

        //Verifica Diagonal Esquerda->Direita
        for (int i = 0, j = 2; i < 3; i++, j--) {
            if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0) {
                jogador1++;
            } else if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0) {
                jogador2++;
            }
        }

        if (jogador1 == 3 || jogador2 == 3) {
            return true;
        }

        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}