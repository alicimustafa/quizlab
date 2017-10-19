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

import entity.Answer;
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

	@Override
	public Question getQuestionById(int id) {
		String query = "SELECT q FROM Question q JOIN FETCH q.answers WHERE q.id = :id";	
		return em.createQuery(query, Question.class)
				.setParameter("id", id).getResultList().get(0);
	}

	@Override
	public Question changeQuestion(String json, int id) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Question nQuestion = mapper.readValue(json, Question.class);	
			Question oQuestion = this.getQuestionById(id);
			oQuestion.setQuestionText(nQuestion.getQuestionText());
			return oQuestion;
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
	public Question addAnswerToQuestion(String json, int id) {
		try {
			Question question = this.getQuestionById(id);
			ObjectMapper mapper = new ObjectMapper();
			Answer ans = mapper.readValue(json, Answer.class);
			ans.setQuestion(question);
			question.getAnswers().add(ans);
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
	public Question updateAnswer(String json, int id) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Answer ans = mapper.readValue(json, Answer.class);
			Answer ans2 = em.find(Answer.class, id);
			ans2.setAnswerText(ans.getAnswerText());
			ans2.setIsCorrect(ans.getIsCorrect());
			return this.getQuestionById(ans2.getQuestion().getId());
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
	public Question removeAnswer(int id) {
		Answer ans = em.find(Answer.class, id);
		int questionId = ans.getQuestion().getId();
		em.remove(ans);
		return this.getQuestionById(questionId);
	}

}
