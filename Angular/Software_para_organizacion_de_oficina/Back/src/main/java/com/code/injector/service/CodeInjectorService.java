package com.code.injector.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.code.injector.utils.CompilationResult;
import com.code.injector.utils.Generator;
import com.code.injector.utils.JavaSourceFromString;

public class CodeInjectorService {

	/**
	 * Invokes the compilator with the giving code and the base code.
	 * 
	 * @param encodedJavaCode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static CompilationResult compile(String javaCode, String className, String time) {

		JavaFileObject userJavaObj = new JavaSourceFromString(className, javaCode, time);

		// Adding base classes
		JavaFileObject javaAbstractObject = new JavaSourceFromString("AbstractBaseJavaComponent",
				Generator.getCode("AbstractBaseJavaComponent"), time);
		JavaFileObject javaFileObj1 = new JavaSourceFromString("Customer", Generator.getCode("Customer"), time);
		JavaFileObject javaFileObj2 = new JavaSourceFromString("Vendor", Generator.getCode("Vendor"), time);

		// Compiler invoke
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaAbstractObject, javaFileObj1,
				javaFileObj2, userJavaObj);
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		new File(time).mkdirs();
		CompilationTask task = compiler.getTask(null,null , diagnostics, Arrays.asList(new String[] { "-d", time }), null, compilationUnits);

		if (task.call()) {
			return new CompilationResult("SUCESSFULL Compilation", className);
		} else {
			String message = "";
			for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {

				// If the className is not the default, its compile with the name define in code.
				if (diagnostic.toString().contains(CompilationResult.WRONG_CLASSNAME)) {
					String realClassName = diagnostic.toString()
							.split(CompilationResult.WRONG_CLASSNAME)[1].split(".java")[0];
					return compile(javaCode, realClassName, time);
				}

				// If are just methods.
				if (diagnostic.toString().contains(CompilationResult.JUST_METHOD_ERROR)) {
					return compile("public class HelloWorld { " + javaCode + " }", className, time);
				}
				
				
				message += diagnostic.toString() + "\n\n";
			}
			return new CompilationResult(message, className);
		}
	}

	/**
	 * Executes the compilation and runs the program.
	 * 
	 * @param javaCode
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static CompilationResult run(String javaCode, String className) throws IOException {

	String time = System.currentTimeMillis()+"";
		
		CompilationResult compilationResult = compile(javaCode, className, time);

		// Sends console to variable called runtimeLog
		PrintStream oldSyso = System.out;
		ByteArrayOutputStream runtimeLog = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(runtimeLog);
		System.setOut(ps);

		if (compilationResult.getResultado().equals("SUCESSFULL Compilation")) {

			System.out.println("\n-------------------------------------------------------------" + "\nRUNNING ... "
					+ "\n-------------------------------------------------------------");

			URLClassLoader classLoader = null;
			try {
				classLoader = new URLClassLoader(new URL[] { new File("./"+time+"/").toURI().toURL() });
				classLoader.loadClass(compilationResult.getRealClassName())
						.getDeclaredMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { null });

			} catch (MalformedURLException | InvocationTargetException | IllegalAccessException
					| ClassNotFoundException e) {
				System.out.println("Internal Server Error. Executions fails.\n" );
				e.printStackTrace(ps);
				System.err.println("INTERNAL SERVER ERROR: " + e);
			} catch (NoSuchMethodException e) {
				System.out.println("Internal Server Error.\njava.lang.NoSuchMethodException: main ([Ljava.lang.String;)");
				System.err.println("INTERNAL SERVER ERROR: " + e);
			} finally {
				classLoader.close();
			}
		}

		// Returns the control of console
		System.out.flush();
		System.setOut(oldSyso);

		return new CompilationResult(compilationResult.getResultado() + runtimeLog.toString(), className);

	}


}
