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


import scala.io.Source
import java.io.File
import scala.collection.mutable

object main {
  import gui._
//старый код глобальных переменных
  // Глобальные переменные
  val fileContents: String = Source.fromFile("ErrorHelper.txt").getLines.mkString
//  var ThrottleValve: Boolean = true
//  var ThrottleValvePos: Int = 100
//  var ABS: String = "Working Good"
//  var CrankshaftPS: Boolean = true
//  var CamshaftPS: Boolean = true
//  var Immo: Boolean = true
//  var OxygenSensor: Boolean = true
  var Attempts: Int = 3
  var ErrorInfo: String = "SMTH"
//  var ErrorCode: String = "No error code"
  var Message: String = "No message"

  sealed trait NewError
  final object ThrottleValve extends NewError
  final object ThrottleValvePos extends NewError
  final object ABS extends NewError
  final object CrankshaftPS extends NewError
  final object CamshaftPS extends NewError
  final object Immo extends NewError
  final object OxygenSensor extends NewError

  object NewError{
    val errorMap = HashMap[Int, NewError](
      1 -> ThrottleValve,
      2 -> ThrottleValvePos,
      3 -> ABS,
      4 -> CrankshaftPS,
      5 -> CamshaftPS,
      6 -> Immo,
      7 -> OxygenSensor
    )


    def getRandomError():NewError = {
      return errorMap((new Random()).nextInt(errorMap.keySet.max)+1)
    }

    def repairError(err1:String, test: NewError):Unit = {
      if (Attempts == 0) {
        println("Out of attempts")
        Message = "Out of attempts"
        return
      }
      val err = err1
      err match {
        case "crankshaft" => if (test == NewError.errorMap(4)) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
          }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "camshaft" => if (test == NewError.errorMap(5)) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "throttle" => if (test == NewError.errorMap(1)) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "immo" => if (test == NewError.errorMap(6)) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
          ErrorInfo = "No errors"
        }

        case "oxygen" => if (test == NewError.errorMap(7)) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("The error was successfully repaired")
        }

        case "abs" => if (test == NewError.errorMap(3)) {
          println("Wrong error, - 1 attempt");
          Message = "Wrong error, - 1 attempt"
          Attempts = Attempts - 1
        }
        else {
          println("ABS was replaced, need to learn")
          ErrorInfo = "No errors"
        }

        case _ => println("Input error, try again")
          Message = "Input error, try again"
      }
    }
  }

//Старый код с ошибками
//  def CreateAnError(): Unit = {
//    val random = new Random()
//    val action = random.nextInt(5)
//
//    action match {
//      case 0 => // Неисправность ДПКВ
//        CrankshaftPS = false
//        ErrorInfo = "Can't start the engine"
//        ErrorCode = "P0001"
//
//      case 1 => // Неисправность Кислородника
//        OxygenSensor = false
//        ErrorInfo = "Too much fuel injection"
//        ErrorCode = "P0002"
//
//      case 2 => // Неисправность ДроссЗ
//        val rnd = new Random()
//        val act = rnd.nextInt(3)
//        act match {
//          case 0 => ErrorInfo = "Can't start the engine"; ThrottleValve = false;  ErrorCode = "P0003"
//          case 1 => ErrorInfo = "Too much fuel injection"; ThrottleValve = false;
//          case 2 => ErrorInfo = "Unstable idling"; ThrottleValvePos = 5;
//        }
//
//      case 3 => // Неисправность ДПРВ
//        CamshaftPS = false
//        ErrorInfo = "Can't start the engine"
//        ErrorCode = "P0004"
//
//      case 4 => // Неисправность Иммо
//        Immo = false
//        ErrorInfo = "Can't start the engine"
//
//      case 5 => // Ошибка по АБС
//        val rnd = new Random()
//        val act = rnd.nextInt(2)
//        act match {
//          case 0 => ABS = "Error on ABS sensor"
//          ErrorInfo = "ABS sensor out of range"
//          ErrorCode = "P0005(1)"
//          case 1 => ABS = "Not Learned"
//          ErrorInfo = "ABS was not learned"
//          ErrorCode = "P0005(2)"
//        }
//
//      case _ => println("ajajaja") // В случае, если случайное число не попадает в диапазон 0-4
//    }
//  }


// Графический интерфейс


  def main(args: Array[String]): Unit = {
    val generatedError = NewError.getRandomError()
    val graphical = gui()
    graphical.app.main(args)
  }
}
