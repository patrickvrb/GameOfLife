
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.mygdx.game.{CellUI, GameUI}

/**
 * This is the Game Launcher, according to the 
 * libgdx game engine. It is basically a refactoring 
 * Java => Scala of the original implementation. 
 * 
 * Learning Unit: a Scala application can be implemented 
 * by a singleton (an object definition) having the 
 * trait scala.App. A singleton is a class that 
 * has only a single instance. Another interesting 
 * finding here is that Scala and Java can iteroperate 
 * quite well.  
 * 
 * Running this application in eclipse is 
 * straightforward. Just click with the right 
 * button in the file, and select the run as 
 * option (Scala Application). 
 */
object Launcher extends scala.App {
  val config = new LwjglApplicationConfiguration()
  val game = new  GameUI
  config.width = 1080
  config.height = 750
  config.resizable = false
  val app = new LwjglApplication(game, config)

}