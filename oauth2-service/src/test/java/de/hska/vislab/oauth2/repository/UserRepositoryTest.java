package de.hska.vislab.oauth2.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.hska.vislab.oauth2.OAuth2ServiceApplication;
import de.hska.vislab.oauth2.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void shouldSaveAndFindUserByName() {

		User user = new User();
		user.setUsername("name");
		user.setPassword("password");
		repository.save(user);

		User found = repository.findOne(user.getUsername());
		assertEquals(user.getUsername(), found.getUsername());
		assertEquals(user.getPassword(), found.getPassword());
	}
}