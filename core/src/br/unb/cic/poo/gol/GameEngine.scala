package br.unb.cic.poo.gol

import com.sun.javaws.exceptions.InvalidArgumentException

import scala.collection.mutable.ListBuffer

/**
 * Representa a Game Engine do GoL 
 * 
 * @author Andre Garrido, Rafael chianca e Patrick Beal (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameEngine {
  
  val height : Int = Main.height
  val width : Int = Main.width

  var Number_Undo : Int = 0
  var Number_Redo : Int = 0
  
  var cells : Array[Array[Cell]] = Array.ofDim[Cell](height, width)

  var originator : Originator = new Originator
  var caretaker : CareTaker = new CareTaker
  var UndoError : Int = 0
  var RedoError : Int = 0
  
  var Rule: EstrategiaDeDerivacao = GameController.RulesList.head
  
  def RuleSet(arg : Int){
    try 
    {
      Rule = GameController.RulesList(arg)
    }
    catch
    {
      case _: IndexOutOfBoundsException => print(GameView.Exception_NotFound)
    }
  }
  
  
  
  for(i <- 0 until height) {
    for(j <- 0 until width) {
      cells(i)(j) = new Cell
      cells(i)(j).i = i
      cells(i)(j).j = j
    }
  }

  def DeleteMemento (): Unit ={
    mustDelete(caretaker.mementoList.size)
  }
  
  /**
	 * Calcula uma nova geracao do ambiente. Essa implementacao utiliza o
	 * algoritmo do Conway, ou seja:
	 * 
	 * a) uma celula morta com exatamente tres celulas vizinhas vivas se torna
    * uma celula viva.
    *
    * b) uma celula viva com duas ou tres celulas vizinhas vivas permanece
    * viva.
	 * 
	 * c) em todos os outros casos a celula morre ou continua morta.
	 */
  
  def nextGeneration() : Unit ={
    
    val mustRevive = new ListBuffer[Cell]
    val mustKill = new ListBuffer[Cell]

    mustDelete(Number_Undo)
    Number_Undo = 0
    Number_Redo = 0

    for(i <- 0 until height) {
      for(j <- 0 until width) {
        if(Rule.shouldRevive(i, j)) {
          mustRevive += cells(i)(j)
        }
        else if((!Rule.shouldKeepAlive(i, j)) && cells(i)(j).isAlive) {
          mustKill += cells(i)(j)
        }
      }
    }

    originator.setState(mustRevive,mustKill)
    caretaker.add(originator.saveStateToMemento)
    if(caretaker.mementoList.size == 11)
      {
        caretaker.mementoList.remove(0)
      }

    for(cell <- mustRevive) {
      cell.revive()
      Statistics.recordRevive()
    }
    
    for(cell <- mustKill) {
      cell.kill()
      Statistics.recordKill()
    }
    
    
  }

  /*
	 * Verifica se uma posicao (a, b) referencia uma celula valida no tabuleiro.
	 */
  private def validPosition(i: Int, j: Int) = 
    i >= 0 && i < height && j >= 0 && j < width

  
  /**
	 * Torna a celula de posicao (i, j) viva
	 * 
	 * @param i posicao vertical da celula
	 * @param j posicao horizontal da celula
	 * 
	 * @throws InvalidArgumentException caso a posicao (i, j) nao seja valida.
	 */
  @throws(classOf[IllegalArgumentException])
  def makeCellAlive(i: Int, j: Int): Unit = {
    if(validPosition(i, j)){
      cells(i)(j).revive()
      Statistics.recordRevive()
    } else {
      throw new IllegalArgumentException
    }
  }
  
  /**
	 * Verifica se uma celula na posicao (i, j) estah viva.
	 * 
	 * @param i Posicao vertical da celula
	 * @param j Posicao horizontal da celula
	 * @return Verdadeiro caso a celula de posicao (i,j) esteja viva.
	 * 
	 * @throws InvalidArgumentException caso a posicao (i,j) nao seja valida.
	 */
  @throws(classOf[IllegalArgumentException])
  def isCellAlive(i: Int, j: Int): Boolean = {
    if(validPosition(i, j)) {
      cells(i)(j).isAlive
    } else {
      throw new IllegalArgumentException
    }
  }
  
  
  /**
	 * Retorna o numero de celulas vivas no ambiente. 
	 * Esse metodo eh particularmente util para o calculo de 
	 * estatisticas e para melhorar a testabilidade.
	 * 
	 * @return  numero de celulas vivas.
	 */
  def numberOfAliveCells() : Unit = {
    var aliveCells = 0
    for(i <- 0 until height) {
      for(j <- 0 until width) {
        if(isCellAlive(i, j)) aliveCells += 1
      }
    }
  }


  /*
	 * Computa o numero de celulas vizinhas vivas, dada uma posicao no ambiente
	 * de referencia identificada pelos argumentos (i,j).
	 */
  def numberOfNeighborhoodAliveCells(i: Int, j: Int): Int = {
    var alive = 0
    var auxA = 0
    var auxB = 0
    for(a <- i - 1 to i + 1) {
      for(b <- j - 1 to j + 1) {
        if (validPosition(a, b)  && (!(a==i && b == j)) && cells(a)(b).isAlive) {
					alive += 1
				}
        else if((!(a==i && b == j)) && !validPosition(a, b))
          {
            if((a > height-1 ||  a < 0) && (b > width-1 || b < 0))
            {
              auxA = height - Math.abs(a)
              auxB = width - Math.abs(b)
               if(cells(auxA)(auxB).isAlive)
                alive += 1 
            }
            if((a > height-1 ||  a < 0) && (b <= width - 1 && b >=0))
            {
              auxA = height - Math.abs(a)
              if(cells(auxA)(b).isAlive)
                alive += 1
            }
            if((b > width-1 || b < 0) && (a <= height - 1 && a >=0))
            {
              auxB = width - Math.abs(b)
              if(cells(a)(auxB).isAlive)
                alive += 1
            }
          }        
      }
    }
    alive
  }

  def Undo (): Unit ={

    if(caretaker.mementoList.nonEmpty && caretaker.mementoList.size -1 >= Number_Undo)
    {
      val Memento_select = caretaker.get(caretaker.mementoList.size -1 -Number_Undo)

      for(i <- Memento_select.getStateKill.indices) {
        cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j) = new Cell
        cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j).i = Memento_select.getStateKill(i).i
        cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j).j = Memento_select.getStateKill(i).j
        cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j).revive()
        Statistics.recordUndoKill()
      }

      for(j <- Memento_select.getStateRevive.indices) {
        cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j) = new Cell
        cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j).i = Memento_select.getStateRevive(j).i
        cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j).j = Memento_select.getStateRevive(j).j
        cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j).kill()
        Statistics.recordUndoRevive()
      }

      Number_Redo += 1
      Number_Undo += 1

    }
    else
    {
      UndoError = 1
    }
  }

  def Redo() : Unit = {
    if(Number_Redo > 0)
      {
        val Memento_select = caretaker.get(caretaker.mementoList.size -Number_Undo)

        for(i <- Memento_select.getStateKill.indices) {
          cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j) = new Cell
          cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j).i = Memento_select.getStateKill(i).i
          cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j).j = Memento_select.getStateKill(i).j
          cells(Memento_select.getStateKill(i).i)(Memento_select.getStateKill(i).j).kill()
          Statistics.recordKill()
        }

        for(j <- Memento_select.getStateRevive.indices) {
          cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j) = new Cell
          cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j).i = Memento_select.getStateRevive(j).i
          cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j).j = Memento_select.getStateRevive(j).j
          cells(Memento_select.getStateRevive(j).i)(Memento_select.getStateRevive(j).j).revive()
          Statistics.recordRevive()
        }
        Number_Redo -= 1
        Number_Undo -= 1
      }
    else
      {
        RedoError = 1
      }

  }

  def mustDelete(n : Int) : Unit = {
    if(n > 0 && caretaker.mementoList.nonEmpty)
      {
        for (_ <- 0 until n)
        {
          caretaker.mementoList.remove(caretaker.mementoList.size -1)
        }
      }
  }

}