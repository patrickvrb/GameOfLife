package com.mygdx.game

import br.unb.cic.poo.gol.{GameEngine, GameView, Main, Statistics}
import com.badlogic.gdx.{ApplicationAdapter, Gdx, InputMultiplexer, InputProcessor}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, Sprite, SpriteBatch, TextureRegion}
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera, Texture}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport

class GameUI extends ApplicationAdapter with InputProcessor{

	var batch : SpriteBatch = _
	var stage : Stage = _
	var sprite : Sprite = _
	var buttons : GameButtons = _
  var Texts : GameText = _
	var Destroier : TextureRegionDrawable = _
	var Milenium : TextureRegionDrawable = _

	var camera : OrthographicCamera = _
	var cellui : CellUI = _
	var img : Texture = _

  var actionBeginTime : Float = System.nanoTime()
  var elapsedTime : Float = _

	override def create : Unit = {
		img = new Texture("gameoflife.png")
		stage = new Stage(new ScreenViewport)
		batch = new SpriteBatch()
		camera = new OrthographicCamera(630,630)
		cellui = new CellUI()
		buttons = new GameButtons
		Texts = new GameText
		Destroier = new TextureRegionDrawable(new TextureRegion(new Texture ("Dest.png")))
		Milenium = new TextureRegionDrawable(new TextureRegion(new Texture ("Mile.png")))

		cellui.getBatch(batch)
		Texts.GetBatch(batch)

		buttons.GetStage(stage)
		buttons.StartButton()

		Texts.RunText()

		sprite = new Sprite(img)
		sprite.setSize(Gdx.graphics.getWidth, Gdx.graphics.getHeight)

    Gdx.input.setInputProcessor(stage)

	}
   
   override def render() : Unit = {

		 if(Gdx.input.justTouched() && buttons.Action == 0)
		 {
			 var x : Float = Gdx.input.getX()
			 var y : Float = Gdx.input.getY()

				cellui.ClickedCell(x,y)
		 }

		 if(buttons.Action == 1)
     {
       elapsedTime = (System.nanoTime()-actionBeginTime)/1000000000.0f
       if(elapsedTime > 2){
         GameEngine.nextGeneration()
         actionBeginTime = System.nanoTime()
       }
     }

		 batch.begin()

		 sprite.draw(batch)
     if(buttons.Action == 0 || buttons.Action == 1)
     {
         Texts.DrawRunningGameText()
         cellui.getdraw()
     }
		 if(buttons.Action == 2)
			 {
				 Destroier.draw(batch, 40,40,450,269)
				 Milenium.draw(batch, 620,40,400,217)
			 }
     Texts.DrawFixedText()
		 Texts.ErrorMessageUndo()
		 Texts.ErrorMessageRedo()
		 batch.end()
		 stage.act(Gdx.graphics.getDeltaTime)
		 stage.draw()
	}
	
	override def dispose() : Unit = {
		batch.dispose()
		img.dispose()
	}

	override def keyUp(keycode: Int): Boolean = {
		false
	}

	override def keyTyped(character: Char): Boolean = {
		false
	}

	override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
		false
	}

	override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		false
	}

	override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
		false
	}

	override def scrolled(amount: Int): Boolean ={
		false
	}

	override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
		false
	}

	override def keyDown(keycode: Int): Boolean = {
		false
	}
}