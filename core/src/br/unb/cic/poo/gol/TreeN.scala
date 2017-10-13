package br.unb.cic.poo.gol

/**
  * Created by Andre on 20/05/2017.
  */
class TreeN extends EstrategiaDeDerivacao {
  /* verifica se uma celula deve ser mantida viva */
  override def shouldKeepAlive(i: Int, j: Int): Boolean = {
    GameEngine.cells(i)(j).isAlive &&
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 3)
  }

  /* verifica se uma celula deve (re)nascer */
  override def shouldRevive(i: Int, j: Int): Boolean = {
    (!GameEngine.cells(i)(j).isAlive) &&
      (GameEngine.numberOfNeighborhoodAliveCells(i, j) == 1)
  }
}