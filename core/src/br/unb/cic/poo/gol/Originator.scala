package br.unb.cic.poo.gol

import scala.collection.mutable.ListBuffer

/**
  * Created by Andre on 12/05/2017.
  */
class Originator {


  var mustRevive : ListBuffer[Cell] = _
  var mustKill : ListBuffer[Cell] = _
  var Originator_Cell : Cell = _

  def setState(mustRevive: ListBuffer[Cell], mustKill : ListBuffer[Cell]): Unit = {
    this.mustRevive = new ListBuffer[Cell]
    this.mustKill = new ListBuffer[Cell]
    for(i <- mustRevive)
      {
        Originator_Cell = new Cell
        Originator_Cell.alive = i.alive
        Originator_Cell.i = i.i
        Originator_Cell.j = i.j
        this.mustRevive += Originator_Cell
      }
    for(j <- mustKill)
      {
        Originator_Cell = new Cell
        Originator_Cell.alive = j.alive
        Originator_Cell.i = j.i
        Originator_Cell.j = j.j
        this.mustKill += Originator_Cell
      }
}

  def saveStateToMemento = new Memento(mustRevive,mustKill)

  def getStateFromMemento(memento: Memento): Unit = {
    mustRevive = memento.getStateRevive
    mustKill = memento.getStateKill
  }
}
