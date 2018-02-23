package com.sunyard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;
/**
 * 
 * @author amorsueno
 * @description 
 * 	分割大文件，指定分割后文件行数
 */
public class FileTest {
	//待分割文件
	private String fileName = "E:\\XYD\\BS_IMAGE_TB\\BS_IMAGE_TB_2.sql";
	//分割后文件行数
	private int lineSize = 50000;
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");  
	
	@Test
	public void split() {
		long start = System.currentTimeMillis();
		File srcFile = new File(fileName);//源文件  
		File folder = new File(srcFile.getAbsolutePath().substring(0,srcFile.getAbsolutePath().length()-4));
		String folderPath = folder.getAbsolutePath();
		String fileName = srcFile.getName().split("[.]")[0];
		String fileSuffix = srcFile.getName().split("[.]")[1];
		if(!folder.exists()) {
			folder.mkdirs();
		}
	    FileInputStream fis;
	    BufferedReader br = null;
		try {
			fis = new FileInputStream(srcFile);
			br = new BufferedReader(new InputStreamReader(fis));  
			   
		    String line = null;  
		    int index = 0;
		    int batch = 1;
		     
		    while ((line = br.readLine()) != null) {  
		    	File sonFile = new File(folderPath+FILE_SEPARATOR+fileName+"_"+batch+"."+fileSuffix);
		    	if(!sonFile.exists()) {
		    		folder.mkdirs();
		    		System.out.println("创建文件："+sonFile.getAbsolutePath());
		    	}
		    	FileWriter out = new FileWriter(sonFile, true);
		        index ++;
		        out.write(line);
		        out.write(LINE_SEPARATOR);
		        
		        if(index % lineSize ==0) {
		        	 long end = System.currentTimeMillis();
		        	 System.out.println(index+" -----   耗时："+mill2TimeStr(end-start));
		        	 batch ++;
		        }
		        out.flush();
		        out.close();
		    }  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			 try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	   System.out.println("DONE!");
	}
	
	 public static String mill2TimeStr(long mill) {
    	 SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");//初始化Formatter的转换格式。      
         formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
         String hms = formatter.format(mill);  //time为转化的毫秒数，long类型的
         return hms;
    }
}
