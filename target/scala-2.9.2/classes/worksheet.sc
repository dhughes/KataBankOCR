import java.io.File
import kata.{OcrDigit, Digits, AccountNumber}
import scala.io.Source

new Digits{
  val num = zero

  val alt = (for(index <- 0 to 10 if num(index) == ' ') 
  yield List(
      new OcrDigit(num.patch(index, "_", 1)).value,
      new OcrDigit(num.patch(index, "|", 1)).value
  )).flatten.filter((x) => x != "?")
  
  println(alt.mkString("\n\n"))
}









/*Source.fromFile("sample data/accountNumbers.txt").getLines().toList.apply(1)


val n123456789 =
"    _  _     _  _  _  _  _ \n" +
"  | _| _||_||_ |_   ||_||_|\n" +
"  ||_  _|  | _||_|  ||_| _|"



val n = 
" _  _  _  _  _  _  _  _    \n" + 
"| || || || || || || ||_   |\n" + 
"|_||_||_||_||_||_||_| _|  |"





val a = new AccountNumber(n)
a.digits
























a.toString

a.checksum





*/