package principal;

import java.io.Serializable;

public class GameState implements Serializable
{

   private static final long serialVersionUID = -7766359136023721033L;

   private String[][] matrizJogo;
   private int vezJogador;
   private DadosJogadores dadosJogadores;
   private int numeroDeJogadas;

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

   public DadosJogadores getDadosJogadores()
   {
      return dadosJogadores;
   }

   public void setDadosJogadores(DadosJogadores dadosJogadores)
   {
      this.dadosJogadores = dadosJogadores;
   }

   public int getNumeroDeJogadas()
   {
      return numeroDeJogadas;
   }

   public void setNumeroDeJogadas(int numeroDeJogadas)
   {
      this.numeroDeJogadas = numeroDeJogadas;
   }
}
