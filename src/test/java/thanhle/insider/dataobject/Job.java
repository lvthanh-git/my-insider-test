package thanhle.insider.dataobject;

public class Job {
	private String position;
	private String department;
	private String location;

	public Job(String position, String department, String location) {
		this.position = position;
		this.department = department;
		this.location = location;
	}
	
	public String getPosition() {
		return position;
	}

	public String getDepartment() {
		return department;
	}

	public String getLocation() {
		return location;
	}

}
