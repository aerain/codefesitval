package io.gitlab.sslab.codefestival;

import io.gitlab.sslab.codefestival.dao.UserDao;
import io.gitlab.sslab.codefestival.service.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.sql.SQLException;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BaseApplication {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(BaseApplication.class, args);

		UserDao userDao = new UserDao();
		User user = userDao.get("최원범_3");
		System.out.println(user.getId());
		System.out.println(user.getPassword());
		System.out.println(user.getName());
		System.out.println(user.getGrade());
		System.out.println(user.getScoreGet());
		System.out.println(user.getFirstBool());
		System.out.println(user.getSecondBool());
		System.out.println(user.getThirdBool());
	}
}
