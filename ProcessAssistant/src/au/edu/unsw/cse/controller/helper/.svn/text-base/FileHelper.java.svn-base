package au.edu.unsw.cse.controller.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import android.os.Environment;
import android.util.Log;

public class FileHelper {

	private static final String tag = "FileHelper";

	public static boolean saveFile(List<String> fileContents, String filename) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// can use the external storage
			Log.i(tag, "sanity check");

			String directoryStr = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Thesis";
			File directory = new File(directoryStr);
			directory.mkdir();
			File outFile = new File(directory, filename);
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(outFile));

				Log.i(tag, directory.getAbsolutePath());

				for (String string : fileContents) {
					out.write(string);
					Log.i(tag, "WriteContents");

					Log.i(tag, string);

				}
				out.close();

				FileInputStream fis = new FileInputStream(outFile);
				DataInputStream dis = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						dis));
				String strLine;
				Log.i(tag, "ReadContents");

				while ((strLine = br.readLine()) != null) {

					Log.i(tag, strLine);

				}

			} catch (Exception e) {
				// 
				return false;
			}

		}
		return true;
	}

	public static boolean saveFile(String fileContents, String filename) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// can use the external storage
			Log.i(tag, "sanity check");

			String directoryStr = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Thesis";
			File directory = new File(directoryStr);
			directory.mkdir();
			File outFile = new File(directory, filename);
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(outFile));

				Log.i(tag, directory.getAbsolutePath());

				out.write(fileContents);
				Log.i(tag, "WriteContents");

				Log.i(tag, fileContents);

				out.close();

				FileInputStream fis = new FileInputStream(outFile);
				DataInputStream dis = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						dis));
				String strLine;
				Log.i(tag, "ReadContents");

				while ((strLine = br.readLine()) != null) {

					Log.i(tag, strLine);

				}

			} catch (Exception e) {
				// 
				return false;
			}

		}
		return true;
	}

	boolean clearDirectory() {
		
			String directoryStr = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Thesis";
			File directory = new File(directoryStr);
			// directory.mkdir();

			Log.i(tag, directory.getAbsolutePath());

			//Log.i(tag, outFile.getAbsolutePath());
			
			try {
				delete(directory);
				
			} catch (Exception e) {
				// 
				return false;
			}
			return true;
			
		
	}

	boolean deleteFile(String filename) {

		if (fileExists(filename)){
			String directoryStr = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Thesis";
			File directory = new File(directoryStr);
			// directory.mkdir();
			File outFile = new File(directory, filename);

			Log.i(tag, directory.getAbsolutePath());

			Log.i(tag, outFile.getAbsolutePath());
			
			return outFile.delete();
		}
		
		else return false;
	}

	boolean fileExists(String filename) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// can use the external storage
			Log.i(tag, "sanity check");

			String directoryStr = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/Thesis";
			File directory = new File(directoryStr);
			// directory.mkdir();
			File outFile = new File(directory, filename);

			Log.i(tag, directory.getAbsolutePath());

			Log.i(tag, outFile.getAbsolutePath());

			return outFile.exists();
		}
		else return false;
	}
	
	private static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   Log.i(tag,"Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	           	  Log.i(tag,"Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		Log.i(tag, "File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
	
}
