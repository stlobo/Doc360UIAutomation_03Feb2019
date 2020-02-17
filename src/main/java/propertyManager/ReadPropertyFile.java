package propertyManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {

	public static Properties prop;
	public static FileInputStream fis;

	public static String readPropertiesFile(String key) throws IOException {
		String propertyFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties";
		prop = new Properties();
		fis = new FileInputStream(propertyFilePath);

		prop.load(fis);
		return prop.getProperty(key);
	}

	public static String readDBPropertiesFile(String key) {
		String propertyFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\db.properties";
		prop = new Properties();
		try {
			fis = new FileInputStream(propertyFilePath);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop.getProperty(key);
	}
	/*
	 * static String propertyFilePath =
	 * System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties";
	 * static Properties prop = null;
	 * 
	 * public static Properties readPropertiesFile(String fileName) throws
	 * IOException { FileInputStream fis = null; propertyFilePath=fileName;
	 * 
	 * try { fis = new FileInputStream(fileName); prop = new Properties();
	 * prop.load(fis); } catch(FileNotFoundException fnfe) { fnfe.printStackTrace();
	 * } catch(IOException ioe) { ioe.printStackTrace(); } finally { fis.close(); }
	 * return prop; }
	 * 
	 * public String readProp(String keyVal) {
	 * System.out.println(prop.getProperty(keyVal)); return
	 * prop.getProperty(keyVal); }
	 */

	/*
	 * public static void main(String args[]) throws IOException { ReadPropertyFile
	 * propFile= new ReadPropertyFile();
	 * System.out.println(propFile.readPropertiesFile("mysqldriver")); //
	 * System.out.println("username: "+ prop.getProperty("dbusername")); //
	 * System.out.println("password: "+ prop.getProperty("dbpassword")); }
	 */

}
