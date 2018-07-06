package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		
		Long count = jdbcTemplate.queryForObject("select count(*) from databasechangelog;", Long.class);
		
		return String.valueOf(count);
	}
}
