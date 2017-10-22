package com.code.injector.utils;

import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class JavaSourceFromString extends SimpleJavaFileObject {

	 final String code;

	  public JavaSourceFromString(String name, String code, String folder) {
		    super(URI.create("string:///" + folder +"/"+ name.replace('.','/') + Kind.SOURCE.extension),Kind.SOURCE);
		    this.code = code;
		  }
	  
	  @Override
	  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
	    return code;
	  }

}
