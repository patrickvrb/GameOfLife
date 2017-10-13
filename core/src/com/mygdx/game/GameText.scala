package com.mygdx.game

import br.unb.cic.poo.gol.{GameEngine, Statistics}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import sun.awt.image.OffScreenImageSource

/**
  * Created by Andre on 20/05/2017.
  */
class GameText  {

  var font : BitmapFont = _
  var Revived : String = "Revived: 0"
  var Killed : String = "Killed: 0"
  var title : BitmapFont =_
  var batch : SpriteBatch = _
  var count : Int = 0

  def GetBatch(batch : SpriteBatch): Unit ={
    this.batch = batch
  }
  def RunText() : Unit = {

    font = new BitmapFont(Gdx.files.internal("fonte.fnt"), Gdx.files.internal("fonte.png"), false)
    font.setColor(1, 1, 0, 1) //Cor da fonte
    font.getData.setScale(0.5f) //Tamanho da fonte
    title = new BitmapFont(Gdx.files.internal("fonte.fnt"), Gdx.files.internal("fonte.png"), false)
    title.setColor(1, 1, 0, 1)
  }

  def DrawRunningGameText(): Unit = {
    Revived = "Revived: " + Statistics.getRevivedCells
    Killed = "Killed: " + Statistics.getKilledCells

    font.draw(batch, "Cells statistics", 680, 80)
    font.draw(batch, Revived, 680, 50)
    font.draw(batch, Killed, 680, 30)
  }

  def DrawFixedText() : Unit = {
    title.draw(batch, "game of life", 410, 700)
  }

  def ErrorMessageUndo (): Unit ={
    if(GameEngine.UndoError == 1)
      {
        font.draw(batch, "undo not possible", 650,500)
        if(count == 20)
          {
            GameEngine.UndoError = 0
            count = -1
          }
        count+=1
      }
  }

  def ErrorMessageRedo (): Unit ={
    if(GameEngine.RedoError == 1)
    {
      font.draw(batch, "redo not possible", 650, 500)
      if(count == 20)
        {
          GameEngine.RedoError = 0
          count = -1
        }
      count +=1
    }
  }

}
