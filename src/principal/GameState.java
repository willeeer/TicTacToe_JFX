package principal;

import java.io.Serializable;

public class GameState implements Serializable
{

   private static final long serialVersionUID = -7766359136023721033L;

   private String[][] matrizJogo;
   private int vezJogador;
   private int scoreJogadorDois;
   private int scoreJogadorUm;
   private String nomeJogadorUm;
   private String nomeJogadorDois;

   public String[][] getMatrizJogo()
   {
      return matrizJogo;
   }

   public void setMatrizJogo(String[][] matrizJogo)
   {
      this.matrizJogo = matrizJogo;
   }

   public int getVezJogador()
   {
      return vezJogador;
   }

   public void setVezJogador(int vezJogador)
   {
      this.vezJogador = vezJogador;
   }

   public int getScoreJogadorDois()
   {
      return scoreJogadorDois;
   }

   public void setScoreJogadorDois(int scoreJogadorDois)
   {
      this.scoreJogadorDois = scoreJogadorDois;
   }

   public int getScoreJogadorUm()
   {
      return scoreJogadorUm;
   }

   public void setScoreJogadorUm(int scoreJogadorUm)
   {
      this.scoreJogadorUm = scoreJogadorUm;
   }

   public String getNomeJogadorUm()
   {
      return nomeJogadorUm;
   }

   public void setNomeJogadorUm(String nomeJogadorUm)
   {
      this.nomeJogadorUm = nomeJogadorUm;
   }

   public String getNomeJogadorDois()
   {
      return nomeJogadorDois;
   }

   public void setNomeJogadorDois(String nomeJogadorDois)
   {
      this.nomeJogadorDois = nomeJogadorDois;
   }
}
