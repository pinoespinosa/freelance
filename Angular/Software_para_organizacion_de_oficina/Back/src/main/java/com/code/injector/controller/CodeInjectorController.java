package com.code.injector.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.injector.service.CodeInjectorService;
import com.code.injector.utils.CompilationResult;

@RestController
public class CodeInjectorController {

	@RequestMapping(method = RequestMethod.POST, value = "/compile")
	@ResponseBody
	public String compileCode(@RequestBody final String code, final HttpServletResponse response)
			throws UnsupportedEncodingException {

		addCorsHeaders(response);
		return CodeInjectorService.compile(code, CompilationResult.DEFAULT_CLASSNAME, System.currentTimeMillis()+"").getResultado();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/run")
	@ResponseBody
	public String runCode(@RequestBody final String code, final HttpServletResponse response) throws Exception {

		addCorsHeaders(response);
		return CodeInjectorService.run(code, CompilationResult.DEFAULT_CLASSNAME).getResultado();
	}

	private void addCorsHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}

}
