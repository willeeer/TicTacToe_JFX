package principal;

public class DadosJogadores
{

   private String nomeJogadorUm;
   private String nomeJogadorDois;
   private Integer scoreJogadorDois = 0;
   private Integer scoreJogadorUm = 0;

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

   public Integer getScoreJogadorDois()
   {
      return scoreJogadorDois;
   }

   public void setScoreJogadorDois(Integer scoreJogadorDois)
   {
      this.scoreJogadorDois = scoreJogadorDois;
   }

   public Integer getScoreJogadorUm()
   {
      return scoreJogadorUm;
   }

   public void setScoreJogadorUm(Integer scoreJogadorUm)
   {
      this.scoreJogadorUm = scoreJogadorUm;
   }

   public void incrementaScore(Integer numeroJogador){
      if(numeroJogador.equals(1)){
         scoreJogadorUm++;
      }else{
         scoreJogadorDois++;
      }
   }
}
