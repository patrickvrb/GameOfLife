package br.unb.cic.poo.gol

/**
 * Rerepsentacao de uma celula do GoL 
 * 
 * @author Breno Xavier (baseado na implementacao Java de rbonifacio@unb.br
 */
class Cell {
  
  var alive = false
  var i : Int = _
  var j : Int = _
  
  def isAlive : Boolean = alive
  
  def kill() : Unit = alive = false
  def revive() : Unit = alive = true
}