package com.mygdx.game

import br.unb.cic.poo.gol.{GameController, GameEngine, GameView, Statistics}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.{Music, Sound}
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, TextureAtlas}
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage, Touchable}
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, Table, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align

import scala.collection.mutable.ListBuffer

/**
  * Created by Andre on 20/05/2017.
  */
class GameButtons {

  var table : Table = _
  var RuleTable : Table = _
  var start : TextButton = _
  var pausa : TextButton = _
  var undo : TextButton = _
  var redo : TextButton = _
  var stop : TextButton = _
  var NewGame : TextButton = _
  var skin : Skin = _
  var stage : Stage = _
  var Rules : ListBuffer[TextButton] = new ListBuffer[TextButton]
  var Action : Int = 2
  var Count_Rule : Int = 0
  var Menu_music : Music = Gdx.audio.newMusic(Gdx.files.internal("NewGame.mp3"))
  var StartGame_Music : Music = Gdx.audio.newMusic(Gdx.files.internal("Ingame.mp3"))
  var PauseGame_Music : Music = Gdx.audio.newMusic(Gdx.files.internal("breathing.mp3"))
  var Button_Sound : Sound = Gdx.audio.newSound(Gdx.files.internal("beep.mp3"))

  skin = new Skin()
  val atlas = new TextureAtlas("star-soldier-ui.atlas")
  skin.addRegions(atlas)
  skin.load(Gdx.files.internal("star-soldier-ui.json"))


  for(i <-0 until GameController.RulesList.size) {
    Rules += new TextButton(GameController.RulesList(i).getClass.getSimpleName, skin)
  }

  def GetStage(stage : Stage): Unit =
  {
    this.stage = stage
  }

  def Running_buttons() : Unit =  {
    table = new Table()
    table.setWidth(Align.center | Align.top)
    table.setPosition(850, 550)

    start = new TextButton("start", skin)
    pausa = new TextButton("pause", skin)
    stop = new TextButton("stop", skin)
    undo = new TextButton("undo", skin)
    redo = new TextButton("redo", skin)

    start.setColor(1, 1, 0, 1)
    pausa.setColor(1, 1, 0, 1)
    stop.setColor(1, 1, 0, 1)
    undo.setColor(1, 1, 0, 1)
    redo.setColor(1, 1, 0, 1)

    start.getLabel.setFontScale(0.75f)
    pausa.getLabel.setFontScale(0.75f)
    stop.getLabel.setFontScale(0.75f)
    undo.getLabel.setFontScale(0.75f)
    redo.getLabel.setFontScale(0.75f)


    table.add(start).height(20)
    table.add(pausa).height(20)
    table.add(stop).height(20)
    table.row()
    table.add(undo).padTop(20).height(20)
    table.add(redo).padTop(20).height(20)

    stage.addActor(table)

    pausa.setTouchable(Touchable.disabled) //- desativa o botao
    pausa.setColor(1, 1, 1, 1) //- mudar a cor

    start.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        Button_Sound.play()
        StartGame_Music.play()
        StartGame_Music.setLooping(true)
        PauseGame_Music.stop()
        Action = 1
        start.setTouchable(Touchable.disabled) //- desativa o botao
        start.setColor(1, 1, 1, 1) //- mudar a cor
        pausa.setTouchable(Touchable.enabled) //- desativa o botao
        pausa.setColor(1, 1, 0, 1) //- mudar a cor
        undo.setTouchable(Touchable.disabled) //- desativa o botao
        undo.setColor(1, 1, 1, 1) //- mudar a cor
        redo.setTouchable(Touchable.disabled) //- desativa o botao
        redo.setColor(1, 1, 1, 1) //- mudar a cor
      }
    })

    pausa.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        Button_Sound.play()
        StartGame_Music.pause()
        PauseGame_Music.play()
        Action = 0
        start.setTouchable(Touchable.enabled) //- desativa o botao
        start.setColor(1, 1, 0, 1) //- mudar a cor
        pausa.setTouchable(Touchable.disabled) //- desativa o botao
        pausa.setColor(1, 1, 1, 1) //- mudar a cor
        undo.setTouchable(Touchable.enabled) //- desativa o botao
        undo.setColor(1, 1, 0, 1) //- mudar a cor
        redo.setTouchable(Touchable.enabled) //- desativa o botao
        redo.setColor(1, 1, 0, 1) //- mudar a cor
      }
    })

    undo.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        Button_Sound.play()
        GameEngine.Undo()
      }
    })

    redo.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        Button_Sound.play()
        GameEngine.Redo()
      }
    })

    stop.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        Button_Sound.play()
        StartGame_Music.stop()
        PauseGame_Music.stop()
        DeleteButtons()
        RuleTable.remove()
        GameEngine.Rule = GameController.RulesList(0)
        StartButton()
        GameEngine.DeleteMemento()

        for(i <- 0 until GameEngine.height)
          {
            for(j <- 0 until GameEngine.width)
              {
                GameEngine.cells(i)(j).kill()
              }
          }

        Statistics.Delete()

        Action = 2
      }
    })

  }

  def StartButton(): Unit = {

    Menu_music.play()
    Menu_music.setLooping(true)

    table = new Table()
    table.setWidth(Align.center | Align.top)
    table.setPosition(540,400)

    NewGame = new TextButton("New Game", skin)

    NewGame.setColor(1, 1, 0, 1)

    table.add(NewGame)
    stage.addActor(table)

    NewGame.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        Button_Sound.play()
        DeleteButtons()
        Running_buttons()
        RuleButtons()
        Action = 0
        Menu_music.stop()
        PauseGame_Music.setVolume(0.1f)
        PauseGame_Music.play()
        PauseGame_Music.setLooping(true)
      }
    })

  }

  def DeleteButtons(): Unit ={
    table.remove()
  }

  def RuleButtons(): Unit ={
    RuleTable = new Table()
    RuleTable.setPosition(850, 400)

    for(i <-0 until GameController.RulesList.size)
      {
        if(Count_Rule == 3)
          {
            RuleTable.row()
            Count_Rule = 0
          }
        Count_Rule += 1
        Rules(i).setColor(1, 1, 0, 1)
        RuleTable.add(Rules(i))
        Rules(i).getLabel.setFontScale(0.75f)
      }
    Count_Rule = 0
    stage.addActor(RuleTable)

    Rules(0).setColor(1, 1, 1, 1) //- mudar a cor

    for(j <-0 until GameController.RulesList.size)
      {
        Rules(j).addListener(new ClickListener() {
          override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
            Button_Sound.play()
            for(l <- 0 until GameController.RulesList.size)
              {
                Rules(l).setColor(1, 1, 0, 1) //- mudar a cor
              }
            Rules(j).setColor(1, 1, 1, 1) //- mudar a cor

            GameEngine.Rule = GameController.RulesList(j)
          }
        })
      }
  }

}
