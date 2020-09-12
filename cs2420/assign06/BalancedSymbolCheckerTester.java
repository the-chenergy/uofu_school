package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>BalancedSymbolChecker</code> class.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version 2/26/19
 */
class BalancedSymbolCheckerTester
{
	/** The message indicating that all symbols match in the given file. **/
	final String ALL_MATCH = "No errors found. All symbols match.";
	
	/** The message indicating that the file contains an unfinished comment. **/
	final String UNFINISHED_COMMENT = "ERROR: File ended before closing comment.";
	
	/** The message indicating that the file contains an unmatched symbol. **/
	final String UNMATCHED = "ERROR: Unmatched symbol at line %d and column %d. Expected %c, but read %c instead.";
	
	/** The message indicating that the file contains an unmatched symbol at the end. **/
	final String UNMATCHED_AT_EOF = "ERROR: Unmatched symbol at the end of file. Expected %c.";
	
	@Test
	void invalidFileTest()
	{
		assertThrows(FileNotFoundException.class, () -> BalancedSymbolChecker.checkFile("hackathon"));
	}
	
	@Test
	void emptyFileTest() throws FileNotFoundException
	{
		assertEquals(ALL_MATCH, BalancedSymbolChecker.checkFile("src/assign06/EmptyFile.txt"));
	}
	
	@Test
	void codeFileTest() throws FileNotFoundException
	{
		assertEquals(ALL_MATCH, BalancedSymbolChecker.checkFile("src/assign06/BalancedSymbolChecker.java"));
		assertEquals(ALL_MATCH, BalancedSymbolChecker.checkFile("src/assign06/BalancedSymbolCheckerTester.java"));
		// hopefully this code itself passes the test :)
	}
	
	@Test
	void unfinishedCommentTest() throws FileNotFoundException
	{
		assertEquals(UNFINISHED_COMMENT, BalancedSymbolChecker.checkFile("src/assign06/UnfinishedComment.txt"));
	}
	
	@Test
	void unmatchedAtEOFTest() throws FileNotFoundException
	{
		String errMessage = String.format(UNMATCHED_AT_EOF, ')');
		
		assertEquals(errMessage, BalancedSymbolChecker.checkFile("src/assign06/UnmatchedAtEOF.txt"));
	}
	
	@Test
	void unmatchedTest() throws FileNotFoundException
	{
		String errMessage0 = String.format(UNMATCHED, 1, 49, ')', '}');
		String errMessage1 = String.format(UNMATCHED, 11, 21, ']', ')');
		String errMessage2 = String.format(UNMATCHED, 26, 1, '}', ']');
		String errMessage3 = String.format(UNMATCHED, 2, 1, ' ', '}');
		
		assertEquals(errMessage0, BalancedSymbolChecker.checkFile("src/assign06/Unmatched0.txt"));
		assertEquals(errMessage1, BalancedSymbolChecker.checkFile("src/assign06/Unmatched1.txt"));
		assertEquals(errMessage2, BalancedSymbolChecker.checkFile("src/assign06/Unmatched2.txt"));
		assertEquals(errMessage3, BalancedSymbolChecker.checkFile("src/assign06/Unmatched3.txt"));
	}
}
