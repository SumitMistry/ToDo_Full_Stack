package springboot.ToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDo_main {

	public static void main(String[] args) {
		SpringApplication.run(ToDo_main.class, args);
		System.out.println("> ToDo SpringBoot Application has started...");
		System.out.println(" > Go and explore @Controller \n   http://localhost:8080        			           http://localhost:8080/actuator \n" +
				"   http://localhost:8080/actuator/health 	   			http://localhost:8080/api/todo/health\n\n" +
				"  														http://localhost:8080/api/todo/login\n" +
				"   http://localhost:8080/api/todo/login2\n" +
				"   http://localhost:8080/api/todo/list  \n" +
				"   http://localhost:8080/api/todo/insert \n"  +
				"   http://localhost:8080/api/todo/insert2 \n"


		);

	}

}
