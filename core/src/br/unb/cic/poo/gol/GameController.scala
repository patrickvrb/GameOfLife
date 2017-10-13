package br.unb.cic.poo.gol

import scala.collection.mutable.MutableList

/**
 * Relaciona o componente View com o componente Model. 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameController {
  
  private var classNames : Iterator[String] = _
  var RulesList : MutableList[EstrategiaDeDerivacao] = _
  
  try
  {
    classNames = scala.io.Source.fromFile("Rules").getLines()
    RulesList = LoadRules(classNames)
  }
  catch
  {
    case _: java.io.FileNotFoundException =>
      print(GameView.Exception_404)
      System.exit(0)

  }
  
  def start() : Unit= {
    GameView.update
  }
  
  def halt() {
    //oops, nao muito legal fazer sysout na classe Controller
    println("\n \n")
    Statistics.display
    System.exit(0)
  }

  def makeCellAlive(i: Int, j: Int) {
    try {
			GameEngine.makeCellAlive(i, j)
			GameView.update
		}
		catch {
		  case ex: IllegalArgumentException =>
		    println(ex.getMessage)

		}
  }
  
  def nextGeneration() : Unit = {
    GameEngine.nextGeneration()
    GameView.update
  }
  
  def RuleSelect (sel : Int) {
   
    GameEngine.RuleSet(sel)
    GameView.update
  }
  
  def LoadRules(classNames : Iterator[String]) : MutableList[EstrategiaDeDerivacao] = {

    val res : MutableList[EstrategiaDeDerivacao]  = new MutableList[EstrategiaDeDerivacao]()

    for(c <- classNames) {
      val p  = Class.forName(c).newInstance()
      res += p.asInstanceOf[EstrategiaDeDerivacao]
    }
    if(res.isEmpty)
    {
      print(GameView.Exception_Empty)
      System.exit(0)
    }
    res

  }

  def Undo(): Unit ={
    GameEngine.Undo()
    GameView.update
  }

  def Redo(): Unit ={
    GameEngine.Redo()
    GameView.update
  }

}