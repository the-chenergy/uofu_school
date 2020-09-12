package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Provides test cases for the <code>LinkedListStack</code> class.
 * 
 * @author Brandon Walton and Qianlang Chen
 * @version 2/22/19
 */
class LinkedListStackTester
{
	@Test
	void clearTest()
	{
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		stack.push(13);
		stack.push(26);
		stack.push(52);
		stack.clear();
		
		assertTrue(stack.isEmpty());
	}
	
	@Test
	void isEmptyTest()
	{
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertTrue(stack.isEmpty());
		
		stack.push(13);
		
		assertFalse(stack.isEmpty());
	}
	
	@Test
	void sizeTest()
	{
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertEquals(0, stack.size());
		
		stack.push(13);
		assertEquals(1, stack.size());
		
		stack.push(26);
		assertEquals(2, stack.size());
	}
	
	@Test
	void peekTest()
	{
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertThrows(NoSuchElementException.class, () -> stack.peek());
		
		stack.push(13);
		assertEquals(13, stack.peek().intValue());
		
		stack.push(26);
		assertEquals(26, stack.peek().intValue());
	}
	
	@Test
	void popTest()
	{
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		assertThrows(NoSuchElementException.class, () -> stack.pop());
		
		stack.push(13);
		stack.push(26);
		
		assertEquals(26, stack.pop().intValue());
		assertEquals(13, stack.pop().intValue());
	}
	
	@Test
	void pushTest()
	{
		LinkedListStack<Integer> stack = new LinkedListStack<Integer>();
		
		Integer[] control = {13, 26, 1, 7, 13, 52};
		
		for (Integer i : control)
			stack.push(i);
		
		while (!stack.isEmpty())
			assertEquals(control[stack.size() - 1], stack.pop()); // all values should be in reversed order
	}
}
