/**
 * Created with IntelliJ IDEA.
 * User: doug
 * Date: 6/5/13
 * Time: 10:05 PM
 *
 * This is a unit testing file
 *
 */

package kata

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import scala.io.Source

@RunWith(classOf[JUnitRunner])
class KataSuite extends FunSuite {

  trait TestData extends Digits{
    val o0 = new OcrDigit(zero)
    val o1 = new OcrDigit(one)
    val o2 = new OcrDigit(two)
    val o3 = new OcrDigit(three)
    val o4 = new OcrDigit(four)
    val o5 = new OcrDigit(five)
    val o6 = new OcrDigit(six)
    val o7 = new OcrDigit(seven)
    val o8 = new OcrDigit(eight)
    val o9 = new OcrDigit(nine)

    // create some account number strings
    val n000000000 =
      """ _  _  _  _  _  _  _  _  _ 
        || || || || || || || || || |
        ||_||_||_||_||_||_||_||_||_|""".stripMargin
    
    val n111111111 =
      """                           
        |  |  |  |  |  |  |  |  |  |
        |  |  |  |  |  |  |  |  |  |""".stripMargin

    val n222222222 =
      """ _  _  _  _  _  _  _  _  _ 
        | _| _| _| _| _| _| _| _| _|
        ||_ |_ |_ |_ |_ |_ |_ |_ |_ """.stripMargin

    val n333333333 =
      """ _  _  _  _  _  _  _  _  _ 
        | _| _| _| _| _| _| _| _| _|
        | _| _| _| _| _| _| _| _| _|""".stripMargin

    val n444444444 =
      """                           
        ||_||_||_||_||_||_||_||_||_|
        |  |  |  |  |  |  |  |  |  |""".stripMargin

    val n555555555 =
      """ _  _  _  _  _  _  _  _  _ 
        ||_ |_ |_ |_ |_ |_ |_ |_ |_ 
        | _| _| _| _| _| _| _| _| _|""".stripMargin

    val n666666666 =
      """ _  _  _  _  _  _  _  _  _ 
        ||_ |_ |_ |_ |_ |_ |_ |_ |_ 
        ||_||_||_||_||_||_||_||_||_|""".stripMargin

    val n777777777 =
      """ _  _  _  _  _  _  _  _  _ 
        |  |  |  |  |  |  |  |  |  |
        |  |  |  |  |  |  |  |  |  |""".stripMargin

    val n888888888 =
      """ _  _  _  _  _  _  _  _  _ 
        ||_||_||_||_||_||_||_||_||_|
        ||_||_||_||_||_||_||_||_||_|""".stripMargin

    val n999999999 =
      """ _  _  _  _  _  _  _  _  _ 
        ||_||_||_||_||_||_||_||_||_|
        | _| _| _| _| _| _| _| _| _|""".stripMargin

    val n123456789 =
      """    _  _     _  _  _  _  _ 
        |  | _| _||_||_ |_   ||_||_|
        |  ||_  _|  | _||_|  ||_| _|""".stripMargin
    
    val n000000051 =
      """ _  _  _  _  _  _  _  _    
        || || || || || || || ||_   |
        ||_||_||_||_||_||_||_| _|  |""".stripMargin

    val n49006771_ =
      """    _  _  _  _  _  _     _ 
        ||_||_|| || ||_   |  |  | _ 
        |  | _||_||_||_|  |  |  | _|""".stripMargin
    
    val n490067715 = 
      """    _  _  _  _  _  _     _ 
        ||_||_|| || ||_   |  |  ||_ 
        |  | _||_||_||_|  |  |  | _|""".stripMargin
    
    val n1234_678_ =
      """    _  _     _  _  _  _  _ 
        |  | _| _||_| _ |_   ||_||_|
        |  ||_  _|  | _||_|  ||_| _ """.stripMargin
    
    val n200000000 = 
      """ _  _  _  _  _  _  _  _  _ 
        | _|| || || || || || || || |
        ||_ |_||_||_||_||_||_||_||_|""".stripMargin
    
    val n_23456789 =
      """    _  _     _  _  _  _  _ 
        | _| _| _||_||_ |_   ||_||_|
        |  ||_  _|  | _||_|  ||_| _|""".stripMargin
    
    val n49086771_ = 
      """    _  _  _  _  _  _     _ 
        ||_||_|| ||_||_   |  |  | _ 
        |  | _||_||_||_|  |  |  | _|""".stripMargin
       
    // parse the account numbers
    val a000000000 = new AccountNumber(n000000000)
    val a111111111 = new AccountNumber(n111111111)
    val a222222222 = new AccountNumber(n222222222)
    val a333333333 = new AccountNumber(n333333333)
    val a444444444 = new AccountNumber(n444444444)
    val a555555555 = new AccountNumber(n555555555)
    val a666666666 = new AccountNumber(n666666666)
    val a777777777 = new AccountNumber(n777777777)
    val a888888888 = new AccountNumber(n888888888)
    val a999999999 = new AccountNumber(n999999999)
    val a123456789 = new AccountNumber(n123456789)
    val a000000051 = new AccountNumber(n000000051)
    val a49006771_ = new AccountNumber(n49006771_)
    val a490067715 = new AccountNumber(n490067715)
    val a1234_678_ = new AccountNumber(n1234_678_)
    val a200000000 = new AccountNumber(n200000000)
    val a_23456789 = new AccountNumber(n_23456789)
    val a49086771_ = new AccountNumber(n49086771_)
    
    // sample illegible numbers
    val o06 = new OcrDigit(""" _ 
                             ||  
                             ||_|""".stripMargin)

    val o35 = new OcrDigit(""" _ 
                             | _ 
                             | _|""".stripMargin)

    val o9_ = new OcrDigit(""" _ 
                             ||_|
                             |  |""".stripMargin)

    val oNan = new OcrDigit("""    
                             |   
                             |   """.stripMargin)
    
  }
  
  test("account number variations"){
    new TestData{
      assert(a000000000.toString === "000000000")
      assert(a111111111.toString === "711111111") // changed to 711111111 because this was the only valid alternative
      assert(a222222222.toString === "222222222 ERR") // not a valid checksum
      assert(a333333333.toString === "333393333") // changed to 333393333 because this was the only valid alternative
      assert(a444444444.toString === "444444444 ERR") // not a valid checksum
      assert(a555555555.toString === "555555555 AMB ['555655555', '559555555']") // was ambiguous with two valid variations
      assert(a666666666.toString === "666666666 AMB ['666566666', '686666666']") // was ambiguous with two valid variations
      assert(a777777777.toString === "777777177") // changed to 777777177 because this was the only valid alternative
      assert(a888888888.toString === "888888888 AMB ['888886888', '888888880', '888888988']") // three valid alternatives
      assert(a999999999.toString === "999999999 AMB ['899999999', '993999999', '999959999']") // three valid alternatives
      assert(a123456789.toString === "123456789")
      assert(a000000051.toString === "000000051")
      assert(a49006771_.toString === "49006771? ILL")
      assert(a1234_678_.toString === "1234?678? ILL")
      assert(a200000000.toString === "200800000")
      assert(a490067715.toString === "490067715 AMB ['490067115', '490067719', '490867715']")
      assert(a_23456789.toString === "123456789")
      assert(a49086771_.toString === "490867715")
    }
  }
  
  test("illegible digits"){
    new TestData{
      assert(o06.variations.map((x) => x.value) === List("0", "6", "?"))
      assert(o35.variations.map((x) => x.value) === List("3", "5", "?"))
      assert(o9_.variations.map((x) => x.value) === List("4", "9", "?"))
      assert(oNan.variations.map((x) => x.value) === List("?"))
    }
  }
  
  test("alternate digits"){
    new TestData{
      assert(o0.variations.map((x) => x.value) === List("0", "8"))
      assert(o1.variations.map((x) => x.value) === List("1", "7"))
      assert(o2.variations.map((x) => x.value) === List("2"))
      assert(o3.variations.map((x) => x.value) === List("3", "9"))
      assert(o4.variations.map((x) => x.value) === List("4"))
      assert(o5.variations.map((x) => x.value) === List("5", "6", "9"))
      assert(o6.variations.map((x) => x.value) === List("5", "6", "8"))
      assert(o7.variations.map((x) => x.value) === List("1", "7"))
      assert(o8.variations.map((x) => x.value) === List("0", "6", "8", "9"))
      assert(o9.variations.map((x) => x.value) === List("3", "5", "8", "9"))
    }
  }
  
  test("parseAccountNumbers"){
    new TestData{
      // read the account numbers from the file
      val accountNumbers = Source.fromFile("sample data/accountNumbers.txt").getLines().toList

      // output the parsed account numbers to the console
      val parsedNumbers = KataBankOcr.parseAccountNumbers(accountNumbers)
      
      // make sure we ended up with the right number of account numbers
      assert(parsedNumbers.length === 27)
    }
  }
  
  test("main"){
    new TestData{
      // test that the main method doesn't blow up or something
      // I'm honestly not sure how to best test this with STDIO.
      
      // this tests a valid file
      KataBankOcr.main(Array("sample data/accountNumbers.txt"));

      // this tests an invalid file
      KataBankOcr.main(Array("sample data/blah.txt"));

      // this tests invalid arguments
      KataBankOcr.main(Array());
    }
  }
  
  test("isIllegible"){
    new TestData {
      assert(a49006771_.isIllegible === true)
      assert(a000000000.isIllegible === false)
    }
  }
  
  test("checksum"){
    new TestData {
      assert(a49006771_.checksum === -1)
      assert(a000000051.checksum === 0)
      assert(a111111111.checksum != 0)
    }
  }
  
  test("illegible number"){
    new TestData {
      assert(a49006771_.mkString === "49006771?")
    }
  }
  
  test("basic account number parsing"){
    new TestData {
      assert(a000000000.mkString === "000000000")
      assert(a111111111.mkString === "111111111")
      assert(a222222222.mkString === "222222222")
      assert(a333333333.mkString === "333333333")
      assert(a444444444.mkString === "444444444")
      assert(a555555555.mkString === "555555555")
      assert(a666666666.mkString === "666666666")
      assert(a777777777.mkString === "777777777")
      assert(a888888888.mkString === "888888888")
      assert(a999999999.mkString === "999999999")
      assert(a123456789.mkString === "123456789")
    }
  }
  
  test("invalid digits") {
    new TestData {
      val bad = new OcrDigit(" _ \n _ \n _|")
      assert(bad.value === "?")
    }
  }
  
  test("digit toString"){
    new TestData{
      assert(o0.toString() === zero);
      assert(o1.toString() === one);
      assert(o2.toString() === two);
      assert(o3.toString() === three);
      assert(o4.toString() === four);
      assert(o5.toString() === five);
      assert(o6.toString() === six);
      assert(o7.toString() === seven);
      assert(o8.toString() === eight);
      assert(o9.toString() === nine);
    }
  }

  test("valid digits") {
    new TestData{
      assert(o0.value === "0")
      assert(o1.value === "1")
      assert(o2.value === "2")
      assert(o3.value === "3")
      assert(o4.value === "4")
      assert(o5.value === "5")
      assert(o6.value === "6")
      assert(o7.value === "7")
      assert(o8.value === "8")
      assert(o9.value === "9")
    }
  }
}
