package edu.pitt.cs;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RentACatTest {

	/**
	 * The test fixture for this JUnit test. Test fixture: a fixed state of a set of
	 * objects used as a baseline for running tests. The test fixture is initialized
	 * using the @Before setUp method which runs before every test case. The test
	 * fixture is removed using the @After tearDown method which runs after each
	 * test case.
	 */

	RentACat r; // Object to test
	Cat c1; // First cat object
	Cat c2; // Second cat object
	Cat c3; // Third cat object

	@Before
	public void setUp() throws Exception {
		// Turn on automatic bug injection in the Cat class, to emulate a buggy Cat.
		// Your unit tests should work regardless of these bugs.
		Cat.bugInjectionOn = true;

		// 1. Create a new RentACat object and assign to r
		r = RentACat.createInstance();

		// 2. Create an unrented Cat with ID 1 and name "Jennyanydots", assign to c1
        c1 = Mockito.mock(Cat.class);
        Mockito.when(c1.getName()).thenReturn("Jennyanydots");
        Mockito.when(c1.getId()).thenReturn(1);

		// 3. Create an unrented Cat with ID 2 and name "Old Deuteronomy", assign to c2
        c2 = Mockito.mock(Cat.class);
        Mockito.when(c2.getName()).thenReturn("Old Deuteronomy");
        Mockito.when(c2.getId()).thenReturn(2);

		// 4. Create an unrented Cat with ID 3 and name "Mistoffelees", assign to c3
        c3 = Mockito.mock(Cat.class);
        Mockito.when(c3.getName()).thenReturn("Mistoffelees");
        Mockito.when(c3.getId()).thenReturn(3);
    }

	@After
	public void tearDown() throws Exception {
		// Not necessary strictly speaking since the references will be overwritten in
		// the next setUp call anyway and Java has automatic garbage collection.
		r = null;
		c1 = null;
		c2 = null;
		c3 = null;
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is null.
	 * </pre>
	 */

	@Test
	public void testGetCatNullNumCats0() {
        assertNull("r.getCat(2) does not return null when r has no cats",r.getCat(2));
	}

	/**
	 * Test case for Cat getCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call getCat(2).
	 * Postconditions: Return value is not null.
	 *                 Returned cat has an ID of 2.
	 * </pre>
	 */

	@Test
	public void testGetCatNumCats3() {
		r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);

        Cat res = r.getCat(2);
        assertNotNull("r.getCat(2) returns null when it should return a cat with id 2", res);
        assertEquals("The returned cat does not have an id of 2", 2, res.getId());
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats0() {
		assertFalse("catAvailable(2) does not return false when RentACat has no cats", r.catAvailable(2));
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c3 is rented.
	 *                c1 and c2 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatAvailableTrueNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		Mockito.when(c1.getRented()).thenReturn(false);
        Mockito.when(c2.getRented()).thenReturn(false);
        Mockito.when(c3.getRented()).thenReturn(true);
		assertTrue("catAvailable(2) does not return true when cat 2 is available", r.catAvailable(2));
	}

	/**
	 * Test case for boolean catAvailable(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 *                c1 and c3 are not rented.
	 * Execution steps: Call catAvailable(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatAvailableFalseNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		
        Mockito.when(c1.getRented()).thenReturn(false);
        Mockito.when(c2.getRented()).thenReturn(true);
        Mockito.when(c3.getRented()).thenReturn(false);

		assertFalse("catAvailable(2) does not return false when cat 2 is already rented", r.catAvailable(2));
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testCatExistsFalseNumCats0() {
		assertFalse("catExists(2) does not return false when RentACat has no cats", r.catExists(2));
	}

	/**
	 * Test case for boolean catExists(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call catExists(2).
	 * Postconditions: Return value is true.
	 * </pre>
	 */

	@Test
	public void testCatExistsTrueNumCats3() {
		r.addCat(c1);
		r.addCat(c2);
		r.addCat(c3);
		assertTrue("catExists(2) does not return true when cat 2 exists", r.catExists(2));
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats0() {
        assertEquals("r.listCats does not return the empty string when r has no cats", "", r.listCats());
	}

	/**
	 * Test case for String listCats().
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 * Execution steps: Call listCats().
	 * Postconditions: Return value is "ID 1. Jennyanydots\nID 2. Old
	 *                 Deuteronomy\nID 3. Mistoffelees\n".
	 * </pre>
	 */

	@Test
	public void testListCatsNumCats3() {
        String expected = "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n";
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
        
        Mockito.when(c1.toString()).thenReturn("ID 1. Jennyanydots");
        Mockito.when(c2.toString()).thenReturn("ID 2. Old Deuteronomy");
        Mockito.when(c3.toString()).thenReturn("ID 3. Mistoffelees");

        assertEquals("r.catList() does not return the proper list of cats", expected, r.listCats());
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testRentCatFailureNumCats0() {
        assertFalse("r.rentCat(2) returns true when it should return false", r.rentCat(2));
	}

	/**
	 * Test case for boolean rentCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call rentCat(2).
	 * Postconditions: Return value is false.
	 *                 c1.rentCat(), c2.rentCat(), c3.rentCat() are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testRentCatFailureNumCats3() {
        r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);

        Mockito.when(c1.getRented()).thenReturn(false);
        Mockito.when(c2.getRented()).thenReturn(true);
        Mockito.when(c3.getRented()).thenReturn(false);

        assertFalse("r.rentCat(2) returned true when it should return false", r.rentCat(2));

        Mockito.verify(c1, Mockito.times(0)).rentCat();
        Mockito.verify(c2, Mockito.times(0)).rentCat();
        Mockito.verify(c3, Mockito.times(0)).rentCat();
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: r has no cats.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is false.
	 * </pre>
	 */

	@Test
	public void testReturnCatFailureNumCats0() {
		assertFalse("returnCat(2) does not return false when RentACat has no cats", r.returnCat(2));
	}

	/**
	 * Test case for boolean returnCat(int id).
	 * 
	 * <pre>
	 * Preconditions: c1, c2, and c3 are added to r using addCat(Cat c).
	 *                c2 is rented.
	 * Execution steps: Call returnCat(2).
	 * Postconditions: Return value is true.
	 *                 c2.returnCat() is called exactly once.
	 *                 c1.returnCat() and c3.returnCat are never called.
	 * </pre>
	 * 
	 * Hint: See sample_code/mockito_example/NoogieTest.java in the course
	 * repository for an example of behavior verification. Refer to the
	 * testBadgerPlayCalled method.
	 */

	@Test
	public void testReturnCatNumCats3() {
		r.addCat(c1);
        r.addCat(c2);
        r.addCat(c3);
		Mockito.when(c1.getRented()).thenReturn(false);
        Mockito.when(c2.getRented()).thenReturn(true);
        Mockito.when(c3.getRented()).thenReturn(false);
		assertTrue("returnCat(2) does not return true after cat 2 is rented", r.returnCat(2));
		Mockito.verify(c2, Mockito.times(1)).returnCat();
		Mockito.verify(c1, Mockito.times(0)).returnCat();
		Mockito.verify(c3, Mockito.times(0)).returnCat();
	}
}
