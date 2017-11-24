package web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import web.service.OfficeService;

@RestController
public class CSVController {
		
	
	@Autowired
	private OfficeService officeService;

	@ApiOperation(value = "Get specific Student in the System ", tags = "Import CSV")
	@RequestMapping(value = "/importCSV", method = RequestMethod.POST)
	@ResponseBody
	public String importCSV(@RequestParam(required = true) final MultipartFile file) {
		officeService.importCSV(file);
		return OfficeController.OK;
	}

	@ApiOperation(value = "Get specific Student in the System ", tags = "Export CSV")
	@RequestMapping(value = "/exportCSV", method = RequestMethod.POST)
	@ResponseBody
	public String exportCSV(final HttpServletResponse servletResponse) {
		
	    servletResponse.setHeader("Content-Disposition", "attachment; filename=BaseDatosExcel.csv");
		
		officeService.exportCSV(servletResponse);
		return OfficeController.OK;
	}

}
