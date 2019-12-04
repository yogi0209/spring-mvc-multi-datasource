package com.yogendra.controller;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Autowired
	@Qualifier("jdbc1")
	JdbcTemplate jdbcTemplate1;

	@Autowired
	@Qualifier("jdbc2")
	JdbcTemplate jdbcTemplate2;

	@Autowired
	@Qualifier("session2")
	SessionFactory sessionFactory;

	@GetMapping(value = "/test1")
	public void test1() {
		List<String> cities = jdbcTemplate1.queryForList("select city from address", String.class);

		System.out.println("------------ citties ------------ " + cities);
	}

	@GetMapping(value = "/test2")
	@Transactional("tx2")
	public void test2() {
		List<String> cities = sessionFactory.getCurrentSession().createSQLQuery("select city from address")
				.list();
		System.out.println("------------ citties ------------ " + cities);
	}
}
