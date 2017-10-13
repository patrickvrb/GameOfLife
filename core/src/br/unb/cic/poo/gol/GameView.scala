package br.unb.cic.poo.gol

import scala.io.StdIn.{readInt, readLine}

/**
 * Representa o componente View do GoL
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
object GameView {
  
	private final val LINE = "+-----+"
	private final val DEAD_CELL = "|     |"
	private final val ALIVE_CELL = "|  o  |"
	val Exception_NotFound = "\nRegra nao encontrada!\nUltima regra escolhida foi selecionada.\n"
	val Exception_Empty = "\nLista vazia!\nFinalizando...\n"
	val Exception_404 = "\nArquivo 'Rules' nao encontrado!\nFinalizando...\n"
	val Undo_Exception = "\nNao foi possivel fazer o Undo!\n"
	val Redo_Exception = "\nNao foi possivel fazer o Redo!\n"
	
	private final val INVALID_OPTION = 0
	private final val MAKE_CELL_ALIVE = 1
	private final val NEXT_GENERATION = 2
	private final val HALT = 3
	private final val SELECTION = 4
	private final val MEMENTO = 5
	
  
  
  
  /**
	 * Atualiza o componente view (representado pela classe GameBoard),
	 * possivelmente como uma resposta a uma atualiza��o do jogo.
	 */
	def update {
		printFirstRow
		printLine
		
		for(i <- (0 until GameEngine.height)) {
		  for(j <- (0 until GameEngine.width)) {
		    print(if (GameEngine.isCellAlive(i, j))  ALIVE_CELL else DEAD_CELL);
		  }
		  println("   " + i)
		  printLine
		}
		printOptions
	}
  
  private def printOptions {
	  
	  var option = 0
	  println("\n\n")
	  
	  do{
	    println("Select one of the options: \n \n"); 
			println("[1] Make a cell alive");
			println("[2] Next generation");
			println("[3] Halt");
			println("[4] Rule select");
			println("[5] Undo/Redo");
		
			print("\n \n Option: ");
			
			option = parseOption(readLine)
	  }while(option == 0)
	  
	  option match {
      case MAKE_CELL_ALIVE => makeCellAlive
      case NEXT_GENERATION => nextGeneration
      case HALT => halt
      case SELECTION => RuleMenu
      case MEMENTO => Memento
    }
	}
  
  private def makeCellAlive {
	  
	  var i = 0
	  var j = 0
	  
	  do {
      print("\n Inform the row number (0 - " + (GameEngine.height - 1) + "): ")
      i = readInt
      
      print("\n Inform the column number (0 - " + (GameEngine.width - 1) + "): ")
      j = readInt
      
    } while(!validPosition(i,j))
      
    GameController.makeCellAlive(i, j)
	}

  private def nextGeneration = GameController.nextGeneration
  private def halt = GameController.halt
	
  private def validPosition(i: Int, j: Int): Boolean = {
		println(i);
		println(j);
		i >= 0 && i < GameEngine.height && j >= 0 && j < GameEngine.width
	}
  
	def parseOption(option: String): Int = option match {
    case "1" => MAKE_CELL_ALIVE
    case "2" => NEXT_GENERATION
    case "3" => HALT
    case "4" => SELECTION
    case "5" => MEMENTO
    case _ => INVALID_OPTION
  }
	
  
  /* Imprime uma linha usada como separador das linhas do tabuleiro */
	private def printLine() {
	  for(j <- (0 until GameEngine.width)) {
	    print(LINE)
	  }
	  println()
	}
  
  /*
	 * Imprime os identificadores das colunas na primeira linha do tabuleiro
	 */
	private def printFirstRow {
		println("\n \n");
		
		for(j <- (0 until GameEngine.width)) {
		  print("   " + j + "   ")
		}
		println()
	}
	
	private def RuleMenu {
	  println("Regras: ")
	  for (i <- 0 to  GameController.RulesList.size -1)
	  {
	    printf("[%d] : %s\n", i, GameController.RulesList(i).getClass.getSimpleName)
	  }
	  
	  GameController.RuleSelect(readInt)
	}
	
	private def Memento {
	  println("[0] - Undo")
	  println("[1] - Redo")
		println("[2] - Cancelar")

		//mandar input do usuario para função do Undo/Redo

		readInt match
		{
			case 0 => GameController.Undo()
			case 1 => GameController.Redo()
			case 2 => update
			case _ => {
				println("\nOpcao invalida!")
				update
			}
		}

	}
  
}