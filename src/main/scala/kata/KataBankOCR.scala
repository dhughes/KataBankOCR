package kata

import scala.io.Source
import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: doug
 * Date: 6/7/13
 * Time: 8:14 AM
 * 
 * This application is used to read a file full of 'scanned' account numbers and process them.  You could run this 
 * by calling java -jar KataBankOCR.jar "path/to/accounts.txt".
 * 
 * This object is a singleton.
 */
object KataBankOcr{

  /**
   * Represents the height of a line of an account number in the 'scanned' document.  This includes the blank line
   */
  val blockLen = 4
  /**
   * Represents the width of a single digit in an account number string.
   */
  val numLen = 3

  /**
   * The main function for this application.  This is what's run from the command line.  It accepts a single argument,
   * the path to a text file listing accounts to process.
   * @param args An array of arguments provided by stdio
   */
  def main(args: Array[String]){
    // make sure we only have one argument
    if(args.length != 1){
      println("Usage:")
      println("KataBankOCR <pathToFile>")

    // make sure the file exists
    } else if(!new File(args(0)).exists()){
      
      println("File not found")
    
    // all good, let's read that file!
    } else {
      // read the account numbers from the file. I could probably buffer this for better performance, but for the use case, this is sufficient
      val accountNumbers = Source.fromFile(args(0)).getLines().toList
      
      // get the parsed account numbers
      val parsedNumbers = parseAccountNumbers(accountNumbers)
      
      // print the parsed account number to the console via stdio
      println(parsedNumbers mkString("\n"))
    }    
  }

  /**
   * This method does the heavy lifting for the application.  It takes a list of lines of text and parses
   * them.  The resulting list of AccountNumber objects is returned.  The numbers must start on line 1. IE:
   * 
   * Line 1: top row of digits as text
   * Line 2: middle row of digits as text
   * Line 3: bottom row of digits as text
   * Line 4: blank line
   * .....
   * 
   * @param accountNumbers A string-based list of 'scanned' account numbers
   * @return A list of parsed AccountNumber objects
   */
  def parseAccountNumbers(accountNumbers:List[String]):List[AccountNumber] = {
    /**
     * This is a helper function to accumulate parsed AccountNumbers.  
     * @param acc A List that will accumulate AccountNumbers
     * @param accountNumbers A string of text-formatted account numbers
     * @return The accumulated list of AccountNumber objects
     */
    def accumulateAccountNumbers(acc:List[AccountNumber], accountNumbers:List[String]):List[AccountNumber] = {
      // get the lines of text that make up this account number
      val strAccNum = accountNumbers.take(blockLen).take(numLen);
      
      // if we're out of lines, return the accumulated AccountNumber objects
      if(strAccNum.length != numLen) acc 
      else {
        // grab the first account number data in the list
        val accNum = new AccountNumber(strAccNum.mkString("\n"))

        // recurse across the remaining numbers
        accumulateAccountNumbers(acc :+ accNum, accountNumbers.drop(4))
      }
       
    }
    
    // parse the account numbers and return the resulting collection of AccountNumber objects
    accumulateAccountNumbers(List[AccountNumber](), accountNumbers)
  }
  
}
