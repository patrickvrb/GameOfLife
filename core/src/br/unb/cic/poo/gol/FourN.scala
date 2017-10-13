package br.unb.cic.poo.gol

/**
  * Created by Andre on 21/05/2017.
  */
class FourN extends EstrategiaDeDerivacao{

  /* verifica se uma celula deve ser mantida viva */
  override def shouldKeepAlive(i: Int, j: Int): Boolean = {
    GameEngine.cells(i)(j).isAlive &&
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 4)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(i: Int, j: Int): Boolean = {
    (!GameEngine.cells(i)(j).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 1)
  }
}
