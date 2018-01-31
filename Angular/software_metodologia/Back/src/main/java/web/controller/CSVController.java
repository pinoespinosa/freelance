package web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import datasource.IDataSource;
import io.swagger.annotations.ApiOperation;

@RestController
public class CSVController {
		
	public static String OK = "{\"STATUS\":\"OK\"}"; 

	
	@Autowired
	private IDataSource dataSource;

	@ApiOperation(value = "Get specific Student in the System ", tags = "Import CSV")
	@RequestMapping(value = "/importCSV", method = RequestMethod.POST)
	@ResponseBody
	public String importCSV(@RequestParam(required = true) final MultipartFile file) {
		dataSource.importCSV(file);
		return OK;
	}

	@ApiOperation(value = "Get specific Student in the System ", tags = "Import CSV")
	@RequestMapping(value = "/importJSON", method = RequestMethod.POST)
	@ResponseBody
	public String importJSON(@RequestParam(required = true) final MultipartFile file) throws IOException {
		dataSource.importJSON(file);
		return OK;
	}
	
	@ApiOperation(value = "Get specific Student in the System ", tags = "Import CSV")
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public String initBD() {
		dataSource.initBD();
		return OK;
	}

	@ApiOperation(value = "Get specific Student in the System ", tags = "Import CSV")
	@RequestMapping(value = "/sampleData", method = RequestMethod.POST)
	@ResponseBody
	public String sampleData() {
		dataSource.initBDSampleData();
		return OK;
	}
	
	
	@ApiOperation(value = "Get specific Student in the System ", tags = "Export CSV")
	@RequestMapping(value = "/exportCSV", method = RequestMethod.POST)
	@ResponseBody
	public String exportCSV(final HttpServletResponse servletResponse) {
		
	    servletResponse.setHeader("Content-Disposition", "attachment; filename=BaseDatosExcel.csv");
		
		dataSource.exportCSV(servletResponse);
		return OK;
	}

}
