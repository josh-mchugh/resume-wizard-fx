package net.sailware.resumewizard.view.core

import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.layout.HBox
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text

object ComponentUtil:

  def createContentPage(content: List[Node]): VBox =
    new VBox(5):
      styleClass = List("page")
      maxWidth = 1020
      children = content

  def createPageHeader(title: String, content: List[Node]): VBox =
    val titleBox = new HBox:
      styleClass = List("page-header__content")
      alignment = Pos.Center
      children = new Text(title):
        styleClass = List("header")

    new VBox:
      styleClass = List("page-header")
      children = titleBox :: content
