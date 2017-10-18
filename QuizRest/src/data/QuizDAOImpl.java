package data;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Question;
import entity.Quiz;

@Transactional
@Repository
public class QuizDAOImpl implements QuizDAO {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<Quiz> showAll() {
		String query = "SELECT q FROM Quiz q";
		return em.createQuery(query, Quiz.class).getResultList();
	}

	@Override
	public Quiz showById(int id) {
		return em.find(Quiz.class, id);
	}

	@Override
	public Quiz update(int id, String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Quiz quizInput = mapper.readValue(json, Quiz.class);
			Quiz quizData = em.find(Quiz.class, id);
			quizData.setName(quizInput.getName());
			return quizData;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Quiz create(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Quiz quiz = mapper.readValue(json, Quiz.class);
			em.persist(quiz);
			em.flush();
			return quiz;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		Quiz quiz = em.find(Quiz.class, id);
		if(quiz != null) {
			em.remove(quiz);
			return true;
		}
		return false;
	}

	@Override
	public List<Question> questionForQuiz(int id) {
		String query = "SELECT q FROM Question q JOIN FETCH q.answers WHERE q.quiz.id = :id";	
		return em.createQuery(query, Question.class)
				.setParameter("id", id).getResultList();
	}

	@Override
	public Question addNewQuestion(String json, int id) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Question question = mapper.readValue(json, Question.class);
			Quiz quiz = em.find(Quiz.class, id);
			question.setQuiz(quiz);
			em.persist(question);
			em.flush();
			return question;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteQuestion(int id) {
		Question q = em.find(Question.class, id);
		if(q != null) {
			em.remove(q);
			return true;
		}
		return false;
	}

}
