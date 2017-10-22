package com.code.injector.utils;


public class CompilationResult {

	public static final String WRONG_CLASSNAME = "should be declared in a file named ";
	public static final String DEFAULT_CLASSNAME = "HelloWorld";
	public static final String JUST_METHOD_ERROR = "error: class, interface, or enum expected";

	String resultado;
	String realClassName;

	public CompilationResult() {
		super();
	}

	public CompilationResult(String resultado, String realClassName) {
		super();
		this.resultado = resultado;
		this.realClassName = realClassName;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getRealClassName() {
		return realClassName;
	}

	public void setRealClassName(String realClassName) {
		this.realClassName = realClassName;
	}

}
