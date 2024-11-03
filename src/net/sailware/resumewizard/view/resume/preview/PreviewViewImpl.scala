package net.sailware.resumewizard.view.resume.preview

import scalafx.scene.layout.Region
import scalafx.scene.web.WebView

class PreviewViewImpl(val model : PreviewModel) extends PreviewView:

  override def view(): WebView =
    val webView = new WebView()
    webView.getEngine().loadContent("<html><body>Hello World!</body></html>")
    webView
