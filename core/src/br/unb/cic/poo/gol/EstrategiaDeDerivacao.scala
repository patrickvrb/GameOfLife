package br.unb.cic.poo.gol

trait EstrategiaDeDerivacao 
{
  def shouldKeepAlive(i: Int, j: Int) : Boolean
  def shouldRevive(i: Int, j: Int) : Boolean
}