package data;

import java.util.List;

import entity.Question;
import entity.Quiz;

public interface QuizDAO {
	public List<Quiz> showAll();
	public Quiz showById(int id);
	public Quiz update(int id, String json);
	public Quiz create(String json);
	public boolean delete(int id);
	public List<Question> questionForQuiz(int id);
	public Question getQuestionById(int id);
	public Question changeQuestion(String json, int id);
	public Question addNewQuestion(String json, int id);
	public Question addAnswerToQuestion(String json, int id);
	public Question updateAnswer(String json, int id);
	public Question removeAnswer(int id);
	public boolean deleteQuestion(int id);
}
