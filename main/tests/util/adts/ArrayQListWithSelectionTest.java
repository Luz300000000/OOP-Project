package util.adts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArrayQListWithSelectionTest {
	
	private QListWithSelection<String> list;
	
	@BeforeEach
	public void init() {
		list = new ArrayQListWithSelection<>();
	}
	
	@Test
	@DisplayName("Test 'add' method")
	public void testAdd() {
		list.add("a");
		list.add("b");
		list.add("c");
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Test 'remove' method")
	public void testRemove() {
		list.add("a");
		list.add("b");
		list.add("c");
		list.select(1);
		list.remove();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Test 'remove' method: if !someSelected")
	public void testRemoveNoneSelected() {
		list.add("a");
		list.add("b");
		list.add("c");
		list.remove();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Test 'size' method")
	public void testSize() {
		assertEquals(0, list.size());
		list.add("a");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Test 'get' method")
	public void testGet() {
		list.add("a");
		list.add("b");
		list.add("c");
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
	}
	
	@Test
	@DisplayName("Test 'iterator' method")
	public void testIterator() {
		assertTrue(list.iterator() instanceof Iterator);
	}
	
	@Test
	@DisplayName("Test 'select' method")
	public void testSelect() {
		list.add("a");
		list.add("b");
		list.select(1);
		int selectedIndex = list.getIndexSelected();
		assertEquals(list.get(1), list.get(selectedIndex));
	}
	
	@Test
	@DisplayName("Test 'someSelected' method")
	public void testSomeSelected() {
		list.add("a");
		assertEquals(-1, list.getIndexSelected());
	}
	
	@Test
	@DisplayName("Test 'getIndexSelected' method")
	public void testGetIndexSelected() {
		list.add("a");
		list.add("b");
		list.select(1);
		assertEquals(1, list.getIndexSelected());
	}
	
	
	@Test
	@DisplayName("Test 'next' method")
	public void testNext() {
		list.add("a");
		list.add("b");
		list.select(0);
		list.next();
		assertEquals(list.get(1), list.getSelected());
	}
	
	@Test
	@DisplayName("Test 'next' method: if !getIndexSelected()<size()-1")
	public void testInvalidNext() {
		list.add("a");
		list.add("b");
		list.select(1);
		list.next();
		assertFalse(list.someSelected());
	}
	
	@Test
	@DisplayName("Test 'previous' method")
	public void testPrevious() {
		list.add("a");
		list.add("b");
		list.select(1);
		list.previous();
		assertEquals(list.get(0), list.getSelected());
	}
	
	@Test
	@DisplayName("Test 'previous' method: if !getIndexSelected()>0")
	public void testInvalidPrevious() {
		list.add("a");
		list.add("b");
		list.select(0);
		list.previous();
		assertFalse(list.someSelected());
	}
	
}

