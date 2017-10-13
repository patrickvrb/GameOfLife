package br.unb.cic.poo.gol

import scala.collection.mutable.ListBuffer

/**
  * Created by Andre on 12/05/2017.
  */
class CareTaker {
  var mementoList = new ListBuffer[Memento]

  def add(mustDo: Memento){
    mementoList += mustDo
  }

  def get(index: Int): Memento = mementoList(index)
}
