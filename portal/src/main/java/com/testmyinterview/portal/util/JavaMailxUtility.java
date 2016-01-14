package com.testmyinterview.portal.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class JavaMailxUtility {

	 public static void main(String[]args) throws IOException{
	        email(
	                new ArrayList<String>(){{
	                    add("sreenivasulu@techuva.com");
	                    add("adithya.umakanth@gmail.com");
	                    add("sreenivasulu.x.aluri@gmail.com");
	                }},
	                "Error Message",
	                "Hello World!\r\n This is message"
	        );
	    }

	 public static void email(
	            List<String>toEmailIds,
	            String subject,
	            String msgText
	            ) throws IOException{
	        String toEmails = toString(toEmailIds);
	        String[]args=new String[]{"/bin/sh" , "-c", "mailx -s \""+subject+"\" "+toEmails};
	        System.out.println("The command for bash is: "+args[2]);
	        Process proc= Runtime.getRuntime().exec(args);
	        OutputStream o = proc.getOutputStream();//probable output for text
	        InputStream i = new ByteArrayInputStream(msgText.getBytes());//probable input for message-text
	        read2end(i, o);
	        o.close();
	    }
	 
	 private static String toString(List<String> toEmailIds) {
	        StringBuilder sb= new StringBuilder();
	        for(String toEmailId:toEmailIds){
	            sb.append(toEmailId).append(' ');
	        }
	        return sb.toString();
	    }

	    private static void read2end(InputStream i, OutputStream o) throws IOException {
	        byte[]b=new byte[1000];
	        for(int a=0;(a=i.read(b))>-1;)
	            o.write(b, 0, a);
	        i.close();
	    }
	
}
