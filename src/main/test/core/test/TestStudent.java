package core.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestStudent {
	private IStudent student;
	private IAdmin admin;
	private IInstructor instructor;
	
	@Before
	public void setup() {
		this.admin = new Admin();
        this.student = new Student();
        this.instructor = new Instructor();
	}
	
	@Test
	public void testRegisterForClass() {
		this.admin.createClass("Test", 2017, "Instructor", 16);
		this.student.registerForClass("Student", "Test", 2017);
		assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	@Test
	public void testRegisterForClass2() {
		//class not created
		this.student.registerForClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	@Test
	public void testRegisterForClass3() {
		this.admin.createClass("Test", 2017, "Instructor", 16);
		this.student.registerForClass("Student", "Test2", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	@Test
	public void testRegisterForClass4() {
		//not enough capacity
		this.admin.createClass("Test", 2017, "Instructor", 1);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.registerForClass("Student2", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student2", "Test", 2017));
	}
	
	@Test 
	public void testDropClass() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.dropClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	@Test 
	public void testDropClass2() {
		//class does not exist
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.dropClass("Student", "Test2", 2017);
		assertTrue(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	@Test 
	public void testDropClass3() {
		//student drops despite not registering
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.dropClass("Student", "Test", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Test", 2017));
	}
	
	@Test 
	public void testDropClass4() {
		//student drops from diff class
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.createClass("Test2", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.registerForClass("Student", "Test2", 2017);
		this.student.dropClass("Student", "Test2", 2017);
		assertTrue(this.student.isRegisteredFor("Student", "Test", 2017) && !this.student.isRegisteredFor("Student","Test2", 2017));
	}
	
	@Test
	public void testSubmitHomework() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
		assertTrue(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
	
	@Test
	public void testSubmitHomework2() {
		//homework does not exist
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student", "HW2", "Answer", "Test", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
	
	@Test
	public void testSubmitHomework3() {
		//student not registered
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
	
	@Test
	public void testSubmitHomework4() {
		//submit to wrong class
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student", "HW1", "Answer", "Test2", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
	
	@Test
	public void testSubmitHomework5() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.registerForClass("Student2", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student2", "HW1", "Answer", "Test", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
	
	@Test
	public void testSubmitHomework6() {
		//submitting before hw is added
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student", "Test", 2017);
		this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
	
	@Test
	public void testSubmitHomework7() {
		//submit before registering
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student", "HW1", "Answer", "Test", 2017);
		this.student.registerForClass("Student", "Test", 2017);
		assertFalse(this.student.hasSubmitted("Student", "HW1", "Test", 2017));
	}
}
