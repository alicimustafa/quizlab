package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.QuizDAO;
import entity.Quiz;

@RestController
public class QuizController {

	@Autowired
	QuizDAO dao;
	
	@RequestMapping(path="ping", method= RequestMethod.GET)
	public String ping() {
		return "pong";
	}
	
	@RequestMapping(path="quizes", method= RequestMethod.GET)
	public List<Quiz> index(){
		return dao.showAll();
	}
	
	@RequestMapping(path="quizes/{id}", method= RequestMethod.GET)
	public Quiz show(@PathVariable int id) {
		return dao.showById(id);
	}
}
