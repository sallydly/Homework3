package core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import core.api.IInstructor;
import core.api.IStudent;
import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

public class TestInstructor {
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	@Before
    public void setup() {
		this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
	
	@Test
	public void testAddHomework() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
	}

	@Test
	public void testAddHomework2() {
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
	}
	
	@Test
	public void testAddHomework3() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor2", "Test", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1"));
	}
	
	@Test
	public void testAddHomework4() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test2", 2017, "HW1");
		assertFalse(this.instructor.homeworkExists("Test2", 2017, "HW1"));
	}
	
	@Test
	public void testAddHomework5() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.instructor.addHomework("Instructor", "Test", 2017, "HW2");
		assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1"));
	}
	
	@Test
	public void testAddHomework6() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.instructor.addHomework("Instructor", "Test", 2017, "HW2");
		assertTrue(this.instructor.homeworkExists("Test", 2017, "HW2"));
	}
	
	@Test
	public void testAssignGrade() {
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", 5);
		assertEquals(this.instructor.getGrade("Test", 2017, "HW1", "Student1"), Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade2() {
		//assign grade to wrong class
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor", "Test1", 2017, "HW1", "Student1", 5);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade3() {
		//Instructor not assigned to class
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor2", "Test", 2017, "HW1", "Student1", 5);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade4() {
		//Student submits nothing
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", 5);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade5() {
		//student not registered for class
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", 5);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade6() {
		//student submits to diff hw
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.instructor.addHomework("Instructor", "Test", 2017, "HW2");
		this.student.submitHomework("Student1", "HW2", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", 5);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade7() {
		//homework not assigned
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.student.submitHomework("Student1", "HW1", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", 5);
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(5));
	}
	
	@Test
	public void testAssignGrade8() {
		//negative percent is not a thing
		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.student.registerForClass("Student1", "Test", 2017);
		this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
		this.student.submitHomework("Student1", "HW1", "Answer", "Test", 2017);
		this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "Student1", -10);;
		assertFalse(this.instructor.getGrade("Test", 2017, "HW1", "Student1") == Integer.valueOf(-10));
	}
}
