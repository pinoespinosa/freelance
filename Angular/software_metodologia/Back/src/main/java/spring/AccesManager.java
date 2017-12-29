package spring;

import org.springframework.beans.factory.annotation.Autowired;

import datasource.IDataSource;

public class AccesManager {

	@Autowired
	IDataSource datasource;


}
