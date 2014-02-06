package com.omnispear.example.assets

import org.scalatra._
import scalate.ScalateSupport

class MyScalatraServlet extends MyExampleAssetsStack {

  before(){
  	contentType = "text/html"
  }
  
  get("/") {
    ssp("default")
  }
  
}
