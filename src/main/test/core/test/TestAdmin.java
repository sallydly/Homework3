package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.impl.Admin;

public class TestAdmin {

	 private IAdmin admin;

	    @Before
	    public void setup() {
	        this.admin = new Admin();
	    }

	    @Test
	    public void testMakeClass() {
	        this.admin.createClass("Test", 2017, "Instructor", 15);
	        assertTrue(this.admin.classExists("Test", 2017));
	    }

	    @Test
	    public void testMakeClass2() {
	        this.admin.createClass("Test", 2016, "Instructor", 15);
	        assertFalse(this.admin.classExists("Test", 2016));
	    }
	    
	    @Test
	    public void testMakeClass3() {
	    	//capacity must be >0
	    	this.admin.createClass("Test", 2017, "Instructor", 0);
	    	assertFalse(this.admin.classExists("Test", 2017));
	    }

	    @Test
	    public void testMakeClass4() {
	    	this.admin.createClass("Test1", 2017, "Instructor", 15);
	    	this.admin.createClass("Test2", 2017, "Instructor", 15);
	    	assertTrue(this.admin.classExists("Test2", 2017));
	    }
	    
	    @Test
	    public void testMakeClass5() {
	    	//instructor cannot have more than 2 classes
	    	this.admin.createClass("Test1", 2017, "Instructor", 15);
	    	this.admin.createClass("Test2", 2017, "Instructor", 15);
	    	this.admin.createClass("Test3", 2017, "Instructor", 15);
	    	assertFalse(this.admin.classExists("Test3", 2017));
	    }
	    
	    @Test
	    public void testUniqueClassYearPair() {
	    	//class needs to be unique class and year
	    	this.admin.createClass("Test", 2017, "Instructor1", 15);
	    	this.admin.createClass("Test", 2017, "Instructor2", 15);
	    	assertTrue(this.admin.getClassInstructor("Test", 2017) == "Instructor1");
	    }
	    
	    @Test
	    public void testChangeCapacity() {
	    	this.admin.createClass("Test", 2017, "Instructor", 2);
	    	this.admin.changeCapacity("Test", 2017, 1);
	    	assertEquals(this.admin.getClassCapacity("Test", 2017), 1);
	    }
	    
	    @Test
	    public void testChangeCapacity2() {
	    	this.admin.createClass("Test", 2017, "Instructor", 2);
	    	this.admin.changeCapacity("Test", 2017, -1);
	    	assertEquals(this.admin.getClassCapacity("Test", 2017), 2);
	    }
	    
	    @Test
	    public void testChangeCapacity3() {
	    	this.admin.createClass("Test", 2017, "Instructor", 2);
	    	this.admin.changeCapacity("Test1", 2017, 4);
	    	assertEquals(this.admin.getClassCapacity("Test", 2017), 2);
	    }
	    
	    @Test
	    public void testChangeCapacity4() {
	    	this.admin.createClass("Test", 2017, "Instructor", 2);
	    	this.admin.changeCapacity("Test", 2017, 0);
	    	assertEquals(this.admin.getClassCapacity("Test", 2017), 2);
	    }
	    
	    
	    
	    
}
