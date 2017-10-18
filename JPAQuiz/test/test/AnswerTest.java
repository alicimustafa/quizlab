package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Answer;
import entity.Question;

public class AnswerTest {


	EntityManagerFactory emf = null;
	EntityManager em = null;
	Answer ans;
	
	@Before
	public void setUp() throws Exception{
		emf = Persistence.createEntityManagerFactory("QuizLab");
		em = emf.createEntityManager();
		ans = em.find(Answer.class,1);
	}
	
	@After
	public void tearDown() throws Exception{
		em.close();
		emf.close();
		ans = null;
	}
	
	@Test
	public void smokeTest() {
		assertEquals(true,true);
	}
	
	@Test
	public void test_Answer_AnswerText_mapped() {
		assertEquals("skyrin", ans.getAnswerText());
	}
	
	@Test
	public void test_Answer_isCorrect_mapped() {
		assertEquals(1, ans.getIsCorrect());
	}
}
