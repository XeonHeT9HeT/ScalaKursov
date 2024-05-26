import scala.util.Random
import scala.io.StdIn.readLine
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, Button, Label, TextInputDialog}
import scalafx.event.ActionEvent
import scalafx.scene.image.*
import scalafx.scene.control.Alert.AlertType
import scala.io.Source

import java.io.File

object main {
  // Глобальные переменные
  val fileContents: String = Source.fromFile("ErrorHelper.txt").getLines.mkString
  var ThrottleValve: Boolean = true
  var ThrottleValvePos: Int = 100
  var ABS: String = "Working Good"
  var CrankshaftPS: Boolean = true
  var CamshaftPS: Boolean = true
  var Immo: Boolean = true
  var OxygenSensor: Boolean = true
  var Attempts: Int = 3
  var ErrorInfo: String = "No errors"
  var ErrorCode: String = "No error code"
  var Message: String = "No message"

  def CreateAnError(): Unit = {
    val random = new Random()
    val action = random.nextInt(5)

    action match {
      case 0 => // Неисправность ДПКВ
        CrankshaftPS = false
        ErrorInfo = "Can't start the engine"
        ErrorCode = "P0001"

      case 1 => // Неисправность Кислородника
        OxygenSensor = false
        ErrorInfo = "Too much fuel injection"
        ErrorCode = "P0002"

      case 2 => // Неисправность ДроссЗ
        val rnd = new Random()
        val act = rnd.nextInt(3)
        act match {
          case 0 => ErrorInfo = "Can't start the engine"; ThrottleValve = false;  ErrorCode = "P0003"
          case 1 => ErrorInfo = "Too much fuel injection"; ThrottleValve = false;
          case 2 => ErrorInfo = "Unstable idling"; ThrottleValvePos = 5;
        }

      case 3 => // Неисправность ДПРВ
        CamshaftPS = false
        ErrorInfo = "Can't start the engine"
        ErrorCode = "P0004"

      case 4 => // Неисправность Иммо
        Immo = false
        ErrorInfo = "Can't start the engine"

      case 5 => // Ошибка по АБС
        val rnd = new Random()
        val act = rnd.nextInt(2)
        act match {
          case 0 => ABS = "Error on ABS sensor"
          ErrorInfo = "ABS sensor out of range"
          ErrorCode = "P0005(1)"
          case 1 => ABS = "Not Learned"
          ErrorInfo = "ABS was not learned"
          ErrorCode = "P0005(2)"
        }

      case _ => println("ajajaja") // В случае, если случайное число не попадает в диапазон 0-4
    }
  }

  def SetTune(setting:String): Unit = {
    setting match{
      case "throttle" => ThrottleValvePos = 100
        ErrorInfo = "No errors"
        println("Throttle position adjusted!")
      case "abs" => ABS = "Working Good"
        ErrorInfo = "No errors"
        println("It works")
    }
  }

  def repairError(err1:String): Unit = {
    //while (Attempts != 0) {
      if (Attempts == 0) {
        println("Out of attempts")
        Message = "Out of attempts"
        return
      }
      val err = err1
      err match {
        case "crankshaft" => if (CrankshaftPS == true) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "camshaft" => if (CamshaftPS == true) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "throttle" => if (ThrottleValve == true) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "immo" => if (Immo == true) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "oxygen" => if (OxygenSensor == true) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "abs" =>if (ABS == "Working Good") {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("ABS was replaced, need to learn")
          ABS = "Not Learned"
          ErrorInfo = "ABS was not learned"
          ErrorCode = "P0005(2)"
        }

        case _ => println("Input error, try again")
          Message = "Input error, try again"
      }
    }
  //}

// Графический интерфейс
  object app extends JFXApp3 {
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
            case Some(err) => repairError(err)
              if (Attempts == 0){content = view}
              if (ErrorInfo != "No errors"){new Alert(AlertType.Information) { headerText = Message}.showAndWait()}
              else{new Alert(AlertType.Information) { headerText = "Success!"}.showAndWait(); content = List(viewRep)}
            case None => println("Dialog was canceled.")
          }

        }

        val button1 = new Button("Start Engine")
        button1.layoutX = 100
        button1.layoutY = 60
        button1.onAction = _ => {
          new Alert(AlertType.Information) { headerText = ErrorInfo}.showAndWait()
        }

        val ErrorCodeButton = new Button("Scan code")
        ErrorCodeButton.layoutX = 100
        ErrorCodeButton.layoutY = 20
        ErrorCodeButton.onAction = _ =>{new Alert(AlertType.Information) { headerText = ErrorCode}.showAndWait()}

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
                    case Some(err) => SetTune(err)
                      if (Attempts == 0){content = view}
                      if (ErrorInfo != "No errors"){new Alert(AlertType.Information) { headerText = Message}.showAndWait()}
                      else{new Alert(AlertType.Information) { headerText = "Success!"}.showAndWait(); content = List(viewRep)}
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
        TuneButton.onAction = _ =>{
          content = List(ErrorCodeButton, SetTuneButton, ReturnButton, Helper, view)
        }

        val Helper = new Button("Help Info")
        Helper.layoutX = 100
        Helper.layoutY = 350
        Helper.onAction = _ => {
        new Alert(AlertType.Information) { contentText = fileContents}.showAndWait()
        }

        content = List(button, button1, ErrorCodeButton, TuneButton, Helper, view)
      }
    }
  }
}


  def main(args: Array[String]): Unit = {
    CreateAnError()
    app.main(args)
  }
}
