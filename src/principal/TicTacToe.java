package principal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicTacToe extends Application
{

   private static final String JOGADOR_UM = "Jogador Um: ";
   private static final String JOGADOR_DOIS = "Jogador Dois: ";
   private static final String SIMBOLO_JOGADOR_UM = "X";
   private static final String SIMBOLO_JOGADOR_DOIS = "O";
   private static final String SIMBOLO_NAO_JOGADO = "#";
   private static final Label tituloCenaPrincipal = new Label("TicTacToe!");
   private final Label hint = new Label();
   private final Label labelJogadorUm = new Label();
   private final Label labelJogadorDois = new Label();
   private int vezJogador = 0;
   private int numeroDeJogadas = 0;
   private DadosJogadores dadosJogadores = new DadosJogadores();
   private Button[][] bts;

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage)
   {

      tituloCenaPrincipal.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

      HBox box = new HBox(10);
      box.setAlignment(Pos.CENTER);
      box.getChildren().add(tituloCenaPrincipal);
      box.setPadding(new Insets(10));

      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(25, 25, 25, 25));

      Label placar = new Label();
      placar.setText("Placar");
      placar.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

      HBox boxLabelPlacar = new HBox(10);
      boxLabelPlacar.setAlignment(Pos.CENTER);
      boxLabelPlacar.getChildren().add(placar);

      labelJogadorUm.setText(JOGADOR_UM + (dadosJogadores.getScoreJogadorUm() != null ?
         dadosJogadores.getScoreJogadorUm().toString() : 0));
      labelJogadorUm.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

      HBox boxPlacarUm = new HBox(5);
      boxPlacarUm.setAlignment(Pos.BASELINE_LEFT);
      boxPlacarUm.getChildren().add(labelJogadorUm);

      labelJogadorDois.setText(JOGADOR_DOIS + (dadosJogadores.getScoreJogadorDois() != null ?
         dadosJogadores.getScoreJogadorDois().toString() :
         0));
      labelJogadorDois.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

      HBox boxPlacarDois = new HBox(5);
      boxPlacarDois.setAlignment(Pos.BASELINE_RIGHT);
      boxPlacarDois.getChildren().add(labelJogadorDois);

      HBox boxPlacar = new HBox(90);
      boxPlacar.setAlignment(Pos.CENTER);
      boxPlacar.getChildren().addAll(boxPlacarUm, boxPlacarDois);

      inicializaBotoes(grid);

      MenuBar menuBar = criaMenuBar(primaryStage);

      hint.setLineSpacing(1);
      hint.setText("Clique em Iniciar para começar um novo jogo, \nou para carregar um jogo salvo.");
      hint.setBackground(Background.EMPTY);
      hint.setTextAlignment(TextAlignment.CENTER);
      hint.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
      hint.setMaxWidth(400);
      hint.setMaxHeight(100);

      HBox boxHint = new HBox();
      boxHint.setAlignment(Pos.CENTER);
      boxHint.getChildren().add(hint);

      Scene scene = new Scene(new VBox(), 600, 600);
      ((VBox) scene.getRoot()).getChildren().addAll(menuBar, box, boxLabelPlacar, boxPlacar, grid, boxHint);

      primaryStage.setTitle("Jogo da Velha");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   private MenuBar criaMenuBar(Stage primaryStage)
   {
      FileChooser fileChooser = new FileChooser();
      Menu menuFile = new Menu("Iniciar");
      MenuItem newGame = new MenuItem("Novo Jogo");
      newGame.setOnAction(event -> criaJanelaNovoJogo(dadosJogadores));

      MenuItem loadGame = new MenuItem("Carregar Jogo");
      loadGame.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
      loadGame.setOnAction(event -> {
         File file = fileChooser.showOpenDialog(primaryStage);
         carregarJogo(file);
      });

      MenuItem saveGame = new MenuItem("Salvar Jogo");
      saveGame.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
      saveGame.setOnAction(event -> {
         File file = fileChooser.showSaveDialog(primaryStage);
         salvarJogo(file);
      });

      MenuItem exit = new MenuItem("Sair");
      exit.setOnAction(event -> System.exit(0));

      menuFile.getItems().addAll(newGame, loadGame, saveGame, exit);

      MenuBar menuBar = new MenuBar();
      menuBar.getMenus().addAll(menuFile);
      return menuBar;
   }

   private void inicializaBotoes(GridPane grid)
   {

      bts = new Button[3][3];
      for (int i = 0; i < 3; i++)
      {
         for (int j = 0; j < 3; j++)
         {
            bts[i][j] = new Button(SIMBOLO_NAO_JOGADO);
            bts[i][j].setFont(new Font("Calibri", 60));
            bts[i][j].setTextAlignment(TextAlignment.CENTER);
            bts[i][j].setDisable(true);
            bts[i][j].setMinWidth(120);
            bts[i][j].setMinHeight(120);
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

      int n = 0;
      int k = 0;
      for (int i = 1; i < 4; i++)
      {
         for (int j = 1; j < 4; j++)
         {
            grid.add(bts[n][k], j, i);
            k++;
         }
         n++;
         k = 0;
      }
   }

   public void realizaJogada(int linha, int coluna)
   {

      if (vezJogador == 0)
      {
         tituloCenaPrincipal.setText("Turno de: " + dadosJogadores.getNomeJogadorDois());
         bts[linha][coluna].setText(SIMBOLO_JOGADOR_UM);
         vezJogador = 1;
      }
      else
      {
         tituloCenaPrincipal.setText("Turno de: " + dadosJogadores.getNomeJogadorUm());
         bts[linha][coluna].setText(SIMBOLO_JOGADOR_DOIS);
         vezJogador = 0;
      }

      bts[linha][coluna].setDisable(true);
      numeroDeJogadas++;

      if (checaVitoria())
      {
         criaJanelaFimJogo(false);
      }
      else if (numeroDeJogadas == 9)
      {
         System.out.println("VELHA");
         criaJanelaFimJogo(true);
      }
   }

   private void criaJanelaFimJogo(boolean isEmpate)
   {

      Scene cenaFimJogo = new Scene(new VBox(), 350, 250);

      GridPane gridFimJogo = new GridPane();
      gridFimJogo.setAlignment(Pos.CENTER);
      gridFimJogo.setHgap(10);
      gridFimJogo.setVgap(10);
      gridFimJogo.setPadding(new Insets(25, 25, 25, 25));

      ImageView imageView = new ImageView();

      try
      {
         if (isEmpate)
         {
            Image imagem = new Image(new FileInputStream("images/draw.jpg"));
            imageView = new ImageView(imagem);
            imageView.setFitHeight(150);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            Label labelEmpate = new Label("Velha zzzzz.");
            labelEmpate.setAlignment(Pos.BASELINE_CENTER);

            gridFimJogo.add(labelEmpate, 1, 0);
            gridFimJogo.add(imageView, 1, 1);

         }
         else
         {
            String nomeGanhador = vezJogador == 0 ? dadosJogadores.getNomeJogadorDois() : dadosJogadores.getNomeJogadorUm();
            if (vezJogador == 0)
            {
               dadosJogadores.incrementaScore(2);
               labelJogadorDois.setText(JOGADOR_DOIS + dadosJogadores.getScoreJogadorDois());
            }
            else
            {
               dadosJogadores.incrementaScore(1);
               labelJogadorUm.setText(JOGADOR_UM + dadosJogadores.getScoreJogadorUm());
            }

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
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }

      Button botaoReset = new Button("Novo Jogo");
      botaoReset.setAlignment(Pos.CENTER);
      botaoReset.setOnAction(event -> {
         reiniciaTabuleiro();
         sortearJogadorInicial();
         numeroDeJogadas = 0;
         cenaFimJogo.getWindow().hide();
      });

      gridFimJogo.add(botaoReset, 1, 2);

      ((VBox) cenaFimJogo.getRoot()).getChildren().addAll(gridFimJogo);

      Stage stageNewGame = new Stage();
      stageNewGame.setTitle("Fim de Jogo");
      stageNewGame.setScene(cenaFimJogo);
      stageNewGame.show();
   }

   private void sortearJogadorInicial()
   {

      Random random = new Random();
      vezJogador = random.nextInt(2);
      preencherLabelTurno(vezJogador);
   }

   private void reiniciaTabuleiro()
   {

      for (int i = 0; i < 3; i++)
      {
         for (int j = 0; j < 3; j++)
         {
            bts[i][j].setText(SIMBOLO_NAO_JOGADO);
            bts[i][j].setFont(new Font("Calibri", 60));
            bts[i][j].setDisable(false);
         }
      }
   }

   private void preencherLabelTurno(int vezJogador)
   {
      if (vezJogador == 0)
      {
         tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorUm());
      }
      else
      {
         tituloCenaPrincipal.setText("Turno do jogador(a): " + dadosJogadores.getNomeJogadorDois());
      }
   }

   private void zerarPlacar()
   {
      dadosJogadores.setScoreJogadorUm(0);
      dadosJogadores.setScoreJogadorDois(0);
      labelJogadorUm.setText(JOGADOR_UM + dadosJogadores.getScoreJogadorUm());
      labelJogadorDois.setText(JOGADOR_DOIS + dadosJogadores.getScoreJogadorDois());

   }

   private void criaJanelaNovoJogo(DadosJogadores dadosJogadores)
   {
      Scene cenaNovoJogo = new Scene(new VBox(), 350, 250);

      GridPane gridNewGame = new GridPane();
      gridNewGame.setAlignment(Pos.CENTER);
      gridNewGame.setHgap(10);
      gridNewGame.setVgap(10);
      gridNewGame.setPadding(new Insets(25, 25, 25, 25));

      Label labelJogadorUmNovoJogo = new Label("Nome Jogador 1:");
      gridNewGame.add(labelJogadorUmNovoJogo, 0, 1);

      TextField textFieldJogadorUm = new TextField();
      gridNewGame.add(textFieldJogadorUm, 1, 1);

      Label labelJogadorDoisNovoJogo = new Label("Nome Jogador 2:");
      gridNewGame.add(labelJogadorDoisNovoJogo, 0, 2);

      TextField textFieldJogadorDois = new TextField();
      gridNewGame.add(textFieldJogadorDois, 1, 2);

      Button button2 = new Button("Submeter");
      button2.setAlignment(Pos.CENTER);
      button2.setOnAction(event -> {
         dadosJogadores.setNomeJogadorUm(textFieldJogadorUm.getText());
         dadosJogadores.setNomeJogadorDois(textFieldJogadorDois.getText());
         sortearJogadorInicial();
         zerarPlacar();
         numeroDeJogadas = 0;
         reiniciaTabuleiro();

         cenaNovoJogo.getWindow().hide();
      });

      HBox hbBt = new HBox(10);
      hbBt.setAlignment(Pos.CENTER);
      hbBt.getChildren().add(button2);
      gridNewGame.add(hbBt, 0, 3);

      Text scenetitle = new Text("Dados dos Jogadores");
      scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
      gridNewGame.add(scenetitle, 0, 0, 2, 1);

      ((VBox) cenaNovoJogo.getRoot()).getChildren().addAll(gridNewGame);

      Stage stageNewGame = new Stage();
      stageNewGame.setTitle("Dados do novo Jogo");
      stageNewGame.setScene(cenaNovoJogo);
      stageNewGame.show();
   }

   public Boolean checaVitoria()
   {

      int jogador1 = 0;
      int jogador2 = 0;

      //verifica Linhas
      for (int i = 0; i < 3; i++)
      {
         for (int j = 0; j < 3; j++)
         {
            if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0)
            {
               jogador1++;
            }
            else if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0)
            {
               jogador2++;
            }
         }

         if (jogador1 == 3 || jogador2 == 3)
         {
            return true;
         }

         jogador1 = 0;
         jogador2 = 0;
      }

      //verifica Colunas
      for (int i = 0; i < 3; i++)
      {
         for (int j = 0; j < 3; j++)
         {
            if (bts[j][i].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0)
            {
               jogador1++;
            }
            else if (bts[j][i].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0)
            {
               jogador2++;
            }
         }

         if (jogador1 == 3 || jogador2 == 3)
         {
            return true;
         }

         jogador1 = 0;
         jogador2 = 0;
      }

      //Verifica Diagonal Direita->Esquerda
      for (int i = 0, j = 0; i < 3; i++, j++)
      {
         if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0)
         {
            jogador1++;
         }
         else if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0)
         {
            jogador2++;
         }
      }

      if (jogador1 == 3 || jogador2 == 3)
      {
         return true;
      }

      jogador1 = 0;
      jogador2 = 0;

      //Verifica Diagonal Esquerda->Direita
      for (int i = 0, j = 2; i < 3; i++, j--)
      {
         if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_UM) == 0)
         {
            jogador1++;
         }
         else if (bts[i][j].getText().compareTo(SIMBOLO_JOGADOR_DOIS) == 0)
         {
            jogador2++;
         }
      }

      return jogador1 == 3 || jogador2 == 3;
   }

   public void salvarJogo(File arquivo)
   {
      GameState jogo = new GameState();
      String[][] matrizJogo = new String[3][3];

      for (int i = 0; i < 3; i++)
      {
         for (int j = 0; j < 3; j++)
         {
            matrizJogo[i][j] = bts[i][j].getText();
         }
      }

      jogo.setMatrizJogo(matrizJogo);
      jogo.setVezJogador(vezJogador);
      jogo.setDadosJogadores(dadosJogadores);
      jogo.setNumeroDeJogadas(numeroDeJogadas);

      try
      {
         FileOutputStream fout = new FileOutputStream(arquivo);
         ObjectOutputStream oos = new ObjectOutputStream(fout);
         oos.writeObject(jogo);
         oos.close();
         fout.close();
         System.out.println("Jogo salvo com sucesso.");
      }
      catch (Exception ex)
      {
         Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
      }

   }

   public void carregarJogo(File arquivo)
   {

      if (arquivo.exists())
      {
         try
         {
            FileInputStream fin = new FileInputStream(arquivo);
            ObjectInputStream ois = new ObjectInputStream(fin);

            GameState jogoCarregado = (GameState) ois.readObject();
            ois.close();
            fin.close();

            String[][] matrizCarregada = jogoCarregado.getMatrizJogo();

            for (int i = 0; i < 3; i++)
            {
               for (int j = 0; j < 3; j++)
               {
                  bts[i][j].setText(matrizCarregada[i][j]);
                  bts[i][j].setDisable(false);
                  if (bts[i][j].getText().compareTo(SIMBOLO_NAO_JOGADO) != 0)
                  {
                     bts[i][j].disarm();
                     bts[i][j].fire();
                  }
               }
            }

            vezJogador = jogoCarregado.getVezJogador();
            dadosJogadores = jogoCarregado.getDadosJogadores();
            numeroDeJogadas = jogoCarregado.getNumeroDeJogadas();
            labelJogadorUm.setText(JOGADOR_UM + dadosJogadores.getScoreJogadorUm());
            labelJogadorDois.setText(JOGADOR_DOIS + dadosJogadores.getScoreJogadorDois());
            preencherLabelTurno(vezJogador);

         }
         catch (Exception ex)
         {
            Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
         }
      }

   }

}