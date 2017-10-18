package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Question;

public class QuestionTest {

	EntityManagerFactory emf = null;
	EntityManager em = null;
	Question q;
	
	@Before
	public void setUp() throws Exception{
		emf = Persistence.createEntityManagerFactory("QuizLab");
		em = emf.createEntityManager();
		q = em.find(Question.class,1);
	}
	
	@After
	public void tearDown() throws Exception{
		em.close();
		emf.close();
		q = null;
	}
	
	@Test
	public void smokeTest() {
		assertEquals(true,true);
	}
	
	@Test
	public void test_Quiz_questionText_mapped() {
		assertEquals("name of eldar Scroll 5", q.getQuestionText());
	}
	
}
