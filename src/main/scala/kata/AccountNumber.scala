package kata

/**
 * Created with IntelliJ IDEA.
 * User: doug
 * Date: 6/6/13
 * Time: 7:52 PM
 * 
 * This class represents an account number which is made up of nine OcrDigits.  This object can 
 * either be constructed from a List of nine OcrDigit objects or from a three line, 83 character
 * string formatted number.
 */
class AccountNumber(val digits:List[OcrDigit]) {
  
  // insure that the digits list is always has nine values
  assert(digits.length == 9)

  /**
   * This is an alternate constructor to build an account number from a three line, 81 character, 
   * string of digits formatted as text.  This constructor will parse the string and create the 9
   * OcrDigit objects required by this object.
   * @param str Three line, 83-character, string formatted account number 
   * @return The constructed AccountNumber object
   */
  def this(str: String) = this({
    // cleanup the input data
    val strArr = str.split("\n")

    // string should be three lines long
    assert(strArr.length == 3)

    // this parses the number out of the string of digits
    {
      // get the three lines of three digits
      val line0 = strArr(0).grouped(3).toList
      val line1 = strArr(1).grouped(3).toList
      val line2 = strArr(2).grouped(3).toList

      /*
       Loop over the nine digits in the provided string and create a new OcrDigit instance for each number 
       we find.  This last block of code is automatically passed into the default constructor above to create 
       and return an instance of an AccountNumber object.
      */
      {
        for(index <- 0 to 8)
        yield {
          val ns0 = line0(index).take(3).padTo(3, " ").mkString
          val ns1 = line1(index).take(3).padTo(3, " ").mkString
          val ns2 = line2(index).take(3).padTo(3, " ").mkString
          new OcrDigit(ns0 + "\n" + ns1  + "\n" + ns2)
        }
      }.toList
    }
  })

  /**
   * This is a List of all valid account number variations (plus our original) where only a single bar or pipe is added or removed.
   * Note that this is not a method, it's a value.  This value, variations, is lazy and is therefor only evaluated the first
   * time it's requested.  Since the AccountNumber object is immutable these variations will never change.  
   */
  lazy val variations:List[AccountNumber] = {
    /**
     * This is a helper function to recursively accumulate account number variations.  Not all of the variations returned will be valid.
     * This is why this is a helper function inside a value.
     * @param accNumbers The list of AccountNumbers we're accumulating
     * @param i An index of which number we're currently working with
     * @return An unfiltered list of all possible account number variations
     */
    def accVariations(accNumbers:List[AccountNumber], i:Int):List[AccountNumber] =
      // if we don't have any more digits to process then return the list we've accumulated
      if(digits.length <= i) accNumbers
      else {
        // create new account number variations for each of this digits possible variations (that are different from the default)
        val newVariations = 
          // this for expression iterates over the possible variations for the current digit, filtering out the default digit
          for(v <- digits(i).variations if v.value != digits(i).value)
          // each variation is used to create a new instance of an AccountNumber.  The set of these are returned as a List.
          yield new AccountNumber((digits.take(i) :+ v) ++: digits.drop(i+1))
        
        // recurse and process the next digit
        accVariations(accNumbers ++ newVariations, i+1)
      }
  
    // calls the accumulator function above.  The list of variations is pre-populated with the original number and invalid numbers are filtered out
    accVariations(List[AccountNumber](this), 0).filter((x) => !x.isValid)
  }

  /**
   * This creates a string of single-character numbers (1?3456789, etc) that represent this account number.  This function does
   * not return the variations or validations, just the default representation.
   * @return A string of numbers or question marks that represents this AccountNumber
   */
  def mkString:String = {
    // iterate over the digits
    for(d:OcrDigit <- digits) 
    // return a list of the values of the digits
    yield d.value
  }.mkString

  /**
   * Returns a complete string representation of this AccountNumber, complete with validation details (ERR, ILL, AMB, etc)
   * @return This AccountNumber as a string for reporting purposes.
   */
  override def toString:String = {
    // if there is only one valid variation, then just return this number
    if(variations.length == 1) variations(0).mkString
    // if we have multiple valid variations then return an ambiguous result
    else if(variations.length > 0) this.mkString + " AMB ['" + (for(v <- variations.sortBy((x) => x.mkString) if v != this) yield v.mkString).mkString("', '") + "']" //  
    // if this is illegible output that
    else if(isIllegible) this.mkString + " ILL"
    // if we get here then there must be a checksum error
    else this.mkString + " ERR"
    
  }

  /**
   * This is the checksum of the account number (not any of the variations).  Note that this is evaluated lazily.
   * Since the AccountNumber object is immutable the checksum will never change.
   */
  lazy val checksum:Int = 
    if (isIllegible) -1
    else {
      // a function to accumulate the reversed and multiplied digits
      def accCheckSum(acc:List[Int], digit:List[OcrDigit]):List[Int] = 
        if(digit.length == 0) acc
        else accCheckSum(acc :+ digit.head.value.toInt * (acc.length + 1), digit.tail)
      
      accCheckSum(List[Int](), digits.reverse).reduceLeft((res, value) => res + value) % 11
    }

  /**
   * Indicates if the number is illegible.
   */
  val isIllegible:Boolean = digits.exists((x) => x.value == "?")

  /**
   * Indicates if the number is valid in that it is legible and the checksum is 0
   */
  val isValid:Boolean = isIllegible == true || checksum != 0
  
}
