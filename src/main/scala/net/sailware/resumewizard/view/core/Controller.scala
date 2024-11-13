package net.sailware.resumewizard.view.core

trait Controller[T]:
  def view(): T
