package br.unb.cic.poo.gol

import scala.collection.mutable.ListBuffer

/**
  * Created by Andre on 12/05/2017.
  */

class Memento(var mustRevive: ListBuffer[Cell], var mustKill : ListBuffer[Cell]) {

    var Save_revive : ListBuffer[Cell] = new ListBuffer[Cell]
    var Save_kill : ListBuffer[Cell] = new ListBuffer[Cell]
    for(i <- mustRevive)
    {
      Save_revive += new Cell
      Save_revive.last.alive = i.alive
      Save_revive.last.i = i.i
      Save_revive.last.j = i.j
    }
    for(j <- mustKill)
    {
      Save_kill += new Cell
      Save_kill.last.alive = j.alive
      Save_kill.last.i = j.i
      Save_kill.last.j = j.j
    }

  def getStateRevive: ListBuffer[Cell] = Save_revive
  def getStateKill: ListBuffer[Cell] = Save_kill
}