package org.baeldung.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import csvMarket.ProductFactory;
import csvMarket.DurekaService;
import utils.BD;
import utils.ConfigUtils;
import utils.LOG;

@Controller
public class DurekaController {

	public DurekaController() {
		super();
	}

	@RequestMapping(value = "/loadTiendaCSV", method = RequestMethod.POST)
	@ResponseBody
	public void findById(@RequestParam(required = false) final MultipartFile file1,
			@RequestParam(required = false) final MultipartFile file2,
			@RequestParam(required = false) final MultipartFile file3,
			@RequestParam(required = false) final MultipartFile file4,
			@RequestParam(required = false) final MultipartFile file5,
			@RequestParam(required = false) final MultipartFile file6,
			@RequestParam(required = false) final MultipartFile file7,
			@RequestParam(required = false) final MultipartFile file8,

			final UriComponentsBuilder uriBuilder, final HttpServletResponse response)
			throws SQLException, IOException, ClassNotFoundException {

		

			LOG.log("Starting");
			LOG.log("");
			ConfigUtils.initialize();
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file1));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file2));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file3));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file4));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file5));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file6));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file7));
			DurekaService.rechazadosProcesados.addAll(DurekaService.processFile(file8));

			ProductFactory.crearProductosTiendaFromMatriz(DurekaService.rechazadosProcesados, response);

			LOG.log("Fin del proceso");
			LOG.cleanHistorial();
			BD.closeConnection();



	}

	@RequestMapping(value = "/loadProductValidatedByUser", method = RequestMethod.POST)
	@ResponseBody
	public void loadProductCheckByUser(@RequestParam(required = false) final MultipartFile file1,
			final UriComponentsBuilder uriBuilder, final HttpServletResponse response)
			throws SQLException, NumberFormatException, ClassNotFoundException {

		ConfigUtils.initialize();

		DurekaService.crearProductosValidadosPorUsuario(file1,response);
		LOG.log("Fin del proceso");
		LOG.cleanHistorial();
		BD.closeConnection();

	}

}
