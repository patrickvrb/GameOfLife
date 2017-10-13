package com.mygdx.game

import br.unb.cic.poo.gol.GameEngine.{height, width}
import br.unb.cic.poo.gol._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureRegion}
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

import scala.collection.mutable.ListBuffer
/**
  * Created by Andre on 19/05/2017.
  */
class CellUI {
  var batch : SpriteBatch = _
  var size : Float = 570/height
  var IsAlive = new TextureRegionDrawable(new TextureRegion(new Texture ("rebel.png")))
  var IsDead = new TextureRegionDrawable(new TextureRegion(new Texture ("empire.png")))
  var IsAlive_Sound : Sound = Gdx.audio.newSound(Gdx.files.internal("ligando.mp3"))
  var IsDead_Sound : Sound = Gdx.audio.newSound(Gdx.files.internal("desligando.mp3"))

  def getBatch(batch : SpriteBatch) : Unit = {
    this.batch = batch
  }

  def getdraw() : Unit = {

    for (i <- 0 until height)
    {
      for(j <- 0 until width)
        {
          if(GameEngine.cells(i)(j).isAlive)
          {
            IsAlive.draw(batch,60 + j *size, 60 + i*size,size,size)
          }
          else
          {
            IsDead.draw(batch,60 + j*size,60 + i*size,size,size)
          }
        }
        }

  }

  def ClickedCell (x: Float, y: Float): Unit ={

    for(i <- 0 until height){
      for(j <- 0 until width){
        if(x >= 60 + j*size && x <= 60 + (j + 1)*size && y >= 685 - (i+1)*size && y <= 685 - i*size)
          {
            if(GameEngine.cells(i)(j).isAlive)
              {
                IsDead_Sound.play()
                GameEngine.cells(i)(j).kill()
                Statistics.recordUndoRevive()
              }
            else
              {
                IsAlive_Sound.play()
                GameEngine.cells(i)(j).revive()
                Statistics.recordRevive()
              }
          }
      }
    }
  }

}
