package example
import scala.util.Random
import scala.io.StdIn.readLine
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, Button, Label, TextInputDialog}
import scalafx.event.ActionEvent
import scalafx.scene.image.*
import scalafx.scene.control.Alert.AlertType
import scala.collection.immutable.HashMap

import functions.NewError
import functions.ErrorInfo
import functions.Attempts
import functions.Message
import functions.fileContents
import scala.io.Source
import java.io.File
import scala.collection.mutable

class gui {
  object app extends JFXApp3 {

    val generatedError = functions.NewError.getRandomError()
    override def start(): Unit = {
      stage = new JFXApp3.PrimaryStage {
        title = "Car repairer"
        scene = new Scene(800, 400) {

          val img = new Image("file:crash.jpg")
          val view = new ImageView(img)
          view.layoutX = 200

          val imgRep = new Image("file:repaired.jpg")
          val viewRep = new ImageView(imgRep)

          val button = new Button("Wat Do?")
          button.layoutX = 20
          button.layoutY = 20
          button.onAction = _ => {
            val dialog = new TextInputDialog(defaultValue = "Input error") {
              initOwner(stage)
              title = "Text Input Dialog"
              headerText = "Error inputer"
              contentText = "Please enter your error:"
            }

            val result = dialog.showAndWait()

            result match {
              case Some(err) => functions.NewError.repairError(err, generatedError)
                if (Attempts == 0) {
                  content = view
                }
                if (ErrorInfo != "No errors") {
                  new Alert(AlertType.Information) {
                    headerText = Message
                  }.showAndWait()
                }
                else {
                  new Alert(AlertType.Information) {
                    headerText = "Success!"
                  }.showAndWait(); content = List(viewRep)
                }
              case None => println("Dialog was canceled.")
            }

          }

          val button1 = new Button("Start Engine")
          button1.layoutX = 100
          button1.layoutY = 60
          button1.onAction = _ => {
            new Alert(AlertType.Information) {
              headerText = ErrorInfo
            }.showAndWait()
          }

          val ErrorCodeButton = new Button("Scan code")
          ErrorCodeButton.layoutX = 100
          ErrorCodeButton.layoutY = 20
          ErrorCodeButton.onAction = _ => {
            new Alert(AlertType.Information) {
              headerText = ErrorInfo
            }.showAndWait()
          }

          val SetTuneButton = new Button("Wat do?")
          SetTuneButton.layoutX = 20
          SetTuneButton.layoutY = 20
          SetTuneButton.onAction = _ => {
            val dialog = new TextInputDialog(defaultValue = "Input tune setting") {
              initOwner(stage)
              title = "Text Input Dialog"
              headerText = "setting inputer"
              contentText = "Please enter your setting:"
            }

            val result = dialog.showAndWait()

            result match {
              case Some(err) =>
                if (Attempts == 0) {
                  content = view
                }
                if (ErrorInfo != "No errors") {
                  new Alert(AlertType.Information) {
                    headerText = Message
                  }.showAndWait()
                }
                else {
                  new Alert(AlertType.Information) {
                    headerText = "Success!"
                  }.showAndWait(); content = List(viewRep)
                }
              case None => println("Dialog was canceled.")
            }
          }

          val ReturnButton = new Button("Return Button")
          ReturnButton.layoutX = 20
          ReturnButton.layoutY = 200
          ReturnButton.onAction = _ => {
            content = List(button, button1, ErrorCodeButton, TuneButton, Helper, view)
          }

          val TuneButton = new Button("Tune-up")
          TuneButton.layoutX = 20
          TuneButton.layoutY = 60
          TuneButton.onAction = _ => {
            content = List(ErrorCodeButton, SetTuneButton, ReturnButton, Helper, view)
          }

          val Helper = new Button("Help Info")
          Helper.layoutX = 100
          Helper.layoutY = 350
          Helper.onAction = _ => {
            new Alert(AlertType.Information) {
              contentText = fileContents
            }.showAndWait()
          }

          content = List(button, button1, ErrorCodeButton, TuneButton, Helper, view)
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    app.main(args)
  }

}
