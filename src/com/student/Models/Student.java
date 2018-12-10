package com.student.Models;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Student {

	private String sid;
	private String cID;
	private String name;
	private String address;

	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student() {
		super();
		this.course = new Course();
	}

	public Student(String sid, String cID, String name, String address, Course course) {
		super();
		this.sid = sid;
		this.cID = cID;
		this.name = name;
		this.address = address;
		this.course = course;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getcID() {
		return cID;
	}

	public void setcID(String cID) {
		this.cID = cID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}