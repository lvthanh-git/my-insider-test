package thanhle.insider.customazation;

public class DriverManager {
	
	private static ThreadLocal<Driver> drivers = new ThreadLocal<Driver>();

	public static void createDriver(String browser, int explicitWaitTimeout) throws Exception {
		setDriver(new Driver(browser, explicitWaitTimeout));
	}
	
	public static Driver getDriver() {
		return drivers.get();
	}
	
	public static void setDriver(Driver driver) {
		drivers.set(driver);
	}
	
	public static void removeDriver() {
		drivers.remove();
	}
}
