package controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import data.QuizDAO;
import entity.Question;
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
	
	@RequestMapping(path="quizes", method = RequestMethod.POST)
	public Quiz create(@RequestBody String json, HttpServletResponse response) {
		Quiz quiz = dao.create(json);
		if(quiz != null) {
			response.setStatus(201);
		}
		return quiz;
	}
	
	@RequestMapping(path="quizes/{id}", method = RequestMethod.DELETE)
	public String destroy(@PathVariable int id) {
		if(dao.delete(id)) {
			return "quiz deleted";
		} else {
			return "there was a problem";
		}
	}
	
	@RequestMapping(path="quizes/{id}", method = RequestMethod.PUT)
	public Quiz update(@PathVariable int id, @RequestBody String json) {
		return dao.update(id, json);
	}
	
	@RequestMapping(path="quizes/{id}/questions", method = RequestMethod.GET)
	public List<Question> questionsIndex(@PathVariable int id){
		return dao.questionForQuiz(id);
	}
	
	@RequestMapping(path="quizes/{qid}/questions/{id}", method = RequestMethod.GET)
	public Question showQuestion(@PathVariable int id) {
		return dao.getQuestionById(id);
	}

	@RequestMapping(path="quizes/{id}/questions", method = RequestMethod.POST)
	public Question questionCreate(@PathVariable int id, @RequestBody String json) {
		return dao.addNewQuestion(json, id);
	}
	
	@RequestMapping(path="quizes/{qid}/questions/{id}", method = RequestMethod.DELETE)
	public String questionDestroy(@PathVariable int id) {
		if(dao.deleteQuestion(id)) {
			return "question deleted";
		}
		return "some thing went wrong";
	}
	
	@RequestMapping(path="quizes/{qid}/questions/{id}", method = RequestMethod.PUT)
	public Question questionUpdate(@PathVariable int id, @RequestBody String json) {
		return dao.changeQuestion(json, id);
	}
	
	@RequestMapping(path="quizes/{qid}/questions/{id}/answers", method = RequestMethod.POST)
	public Question createAnswer(@PathVariable int id, @RequestBody String json) {
		return dao.addAnswerToQuestion(json, id);
	}
	
	@RequestMapping(path="quizes/{qid}/questions/{quid}/answers/{id}", method = RequestMethod.PUT)
	public Question updateAnswer(@PathVariable int id, @RequestBody String json) {
		return dao.updateAnswer(json, id);
	}
	@RequestMapping(path="quizes/{qid}/questions/{quid}/answers/{id}", method = RequestMethod.DELETE)
	public Question destroyAnswer(@PathVariable int id) {
		return dao.removeAnswer(id);
	}
}
