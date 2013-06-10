package kata

/**
 * Created with IntelliJ IDEA.
 * User: doug
 * Date: 6/6/13
 * Time: 6:53 PM
 *
 * This class represents a single digit from an OCR'd document.  It extends the Digits
 * trait which holds a list of string representations of numbers for easy reference.
 *
 */
class OcrDigit(val str: String) extends Digits{

  /**
   * Returns the best initial match for the string-formatted number. If a number
   * can't be matched without further processing this returns "?".  Because of
   * the question mark I'm returning a string.  (I suppose this could be a char too.)
   */
  lazy val value:String = str match {
    case `zero`  => "0"
    case `one`   => "1"
    case `two`   => "2"
    case `three` => "3"
    case `four`  => "4"
    case `five`  => "5"
    case `six`   => "6"
    case `seven` => "7"
    case `eight` => "8"
    case `nine`  => "9"
    case _       => "?"
  }

  /**
   * Returns the original three-line tall, three character wide, string that is supposed 
   * to represent a digit.
   * @return the digit in the original string format.
   */
  override def toString:String = str

  /**
   * Creates a sorted list of valid OcrDigits where only a single bar or pipe is added or removed.
   * For example, if you had a bar to a 0 you can make an 8. Remove a pipe from 9 and you have 5.
   * @return sorted list of potential alternative digits.
   */
  def variations:List[OcrDigit] = {
    val others = 
      for(index <- 0 to 10)
      yield 
        if(str(index) == ' ') 
          List(
            new OcrDigit(str.patch(index, "_", 1)),
            new OcrDigit(str.patch(index, "|", 1))
          )
        else 
          List(new OcrDigit(str.patch(index, " ", 1)))
    
    // flatten our collection and filter out any that are ambiguous 
    val all = others.flatten.filter((x) => x.value != "?").toList
      
    // return the sorted list 
    (all :+ this).sortBy((x) => x.value)
  }
  
}

/**
 * This trait is a simple way to define the various textual digits in one, reusable, place.
 * This trait is used by the OrcDigit to match the provide digit to its numeric value.  It is
 * also used by the KataSuite tests to provide easy reference to the digits in string format.
 */
trait Digits{
  val zero =
    """ _ 
      || |
      ||_|""".stripMargin
  val one =
    """   
      |  |
      |  |""".stripMargin
  val two =
    """ _ 
      | _|
      ||_ """.stripMargin
  val three =
    """ _ 
      | _|
      | _|""".stripMargin
  val four =
    """   
      ||_|
      |  |""".stripMargin
  val five =
    """ _ 
      ||_ 
      | _|""".stripMargin
  val six =
    """ _ 
      ||_ 
      ||_|""".stripMargin
  val seven =
    """ _ 
      |  |
      |  |""".stripMargin
  val eight =
    """ _ 
      ||_|
      ||_|""".stripMargin
  val nine =
    """ _ 
      ||_|
      | _|""".stripMargin
}
