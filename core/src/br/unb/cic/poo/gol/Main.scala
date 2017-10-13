package br.unb.cic.poo.gol


/**
 * Programa principal do GoL.
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object Main {
  
  val height : Int = 10
  val width : Int = 10
  
  def main(args: Array[String]){
    GameController.start()
  }
}