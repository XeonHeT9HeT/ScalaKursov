import scala.util.Random

object Main {
  // Глобальные переменные
  var ThrottleValve: Boolean = true
  var CrankshaftPS: Boolean = true
  var CamshaftPS: Boolean = true
  var Immo: Boolean = true
  var OxygenSensor: Boolean = true
  var Tries: int = 0

  def CreateAnError(): Unit = {
    val random = new Random()
    val action = random.nextInt(5)

    action match {
      case 0 => // Неисправность ДПКВ
      CrankshaftPS = false
      case 1 => // Неисправность Кислородника
      OxygenSensor = false
      case 2 => // Неисправность ДроссЗ
      ThrottleValve = false
      case 3 => // Неисправность ДПРВ
      CamshaftPS = false
      case 4 => // Неисправность Иммо
      Immo = false
      case _ => // В случае, если случайное число не попадает в диапазон 0-4
    }
  }

  // Функция для выбора случайного действия
  def chooseRandomAction(): Int = {
    val random = new Random()
    random.nextInt(5) // Возвращает случайное число от 0 до 4
  }

  // Функция для выполнения действия 1
  def action1(): Unit = {
    // Ваш код для действия 1
  }

  // Функция для выполнения действия 2
  def action2(): Unit = {
    // Ваш код для действия 2
  }

  // Функция для выполнения действия 3
  def action3(): Unit = {
    // Ваш код для действия 3
  }

  // Функция для выполнения действия 4
  def action4(): Unit = {
    // Ваш код для действия 4
  }

  // Функция для выполнения действия 5
  def action5(): Unit = {
    // Ваш код для действия 5
  }

  def main(args: Array[String]): Unit = {
    // Выбор случайного действия
    val randomAction = chooseRandomAction()
    // Выполнение соответствующего действия
    randomAction match {
      case 0 => action1()
      case 1 => action2()
      case 2 => action3()
      case 3 => action4()
      case 4 => action5()
      case _ => println("Неизвестное действие!")
    }
  }
}