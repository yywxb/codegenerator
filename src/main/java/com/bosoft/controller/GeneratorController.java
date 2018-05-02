package com.bosoft.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bosoft.service.GeneratorService;


@RestController
@RequestMapping("/sys/generator")
public class GeneratorController {
	@Autowired
	private GeneratorService generatorService;
	

	

	@RequestMapping("/code")
	public Map<String, String> code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Map<String, String> ret = new HashMap<String, String>();
		//String sRet =  request.getParameter("table") + "/" +  request.getParameter("part");
		String sRet = generatorService.generatorCode(request.getParameter("table"), 
				request.getParameter("part"), request.getParameter("moduleName"), request.getParameter("tablePrefix"), request.getParameter("tableNameCN"));
		//System.out.println(sRet);
		ret.put("code", sRet);
		return ret;
		
		
	}
	
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	public String test2() {
		return ("test2");
	}
	
	
}
