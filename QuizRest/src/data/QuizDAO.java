package data;

import java.util.List;

import entity.Quiz;

public interface QuizDAO {
	public List<Quiz> showAll();
	public Quiz showById(int id);
	public Quiz update(int id, String json);
	public Quiz create(String json);
	public boolean delete(int id);
}
