package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import web.service.OfficeService;

@RestController
public class OfficeController {
	@Autowired
	private OfficeService officeService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String getCustomerById() {
		return officeService.getHelloWorld();
	}
}
