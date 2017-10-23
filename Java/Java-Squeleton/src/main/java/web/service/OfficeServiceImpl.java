package web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImpl implements OfficeService {

	@Override
	public String getHelloWorld() {
		return "Holaa";
	}


}
