package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Quiz;

public class QuizTest {
	
	EntityManagerFactory emf = null;
	EntityManager em = null;
	Quiz q;
	
	@Before
	public void setUp() throws Exception{
		emf = Persistence.createEntityManagerFactory("QuizLab");
		em = emf.createEntityManager();
		q = em.find(Quiz.class,1);
	}
	
	@After
	public void tearDown() throws Exception{
		em.close();
		emf.close();
		q = null;
	}
	
	@Test
	public void somokeTest() {
		assertEquals(true,true);
	}
}
