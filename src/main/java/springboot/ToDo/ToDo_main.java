package springboot.ToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDo_main {

	public static void main(String[] args) {
		SpringApplication.run(ToDo_main.class, args);
		System.out.println("  ^^ Before this.... Autorun class should automatically start above this point... public class Autorun_justReference implements CommandLineRunner { 'run' method}");
		System.out.println("> ToDo SpringBoot Application has started...");
		System.out.println(
				" > Go and explore @Controller \n" +
						"   http://localhost:8080        			            http://localhost:8080/actuator \n" +
						"   http://localhost:8080/actuator/health 	   			http://localhost:8080/api/todo/health\n\n" +
						"   http://localhost:8080/api/todo/insert  -> (+)Auto Validation\n"  +
						"   http://localhost:8080/api/todo/insert2 -> (+)Manual\n\n" +

						"   http://localhost:8080/login   -> Spring Security UI\n" +
						"   http://localhost:8080/login1  -> new UI\n" +
						"   http://localhost:8080/login2  -> old UI\n" +
						"   http://localhost:8080/api/todo/insert3 -> (+)SpringDataJPA-SQL <- http://localhost:8080/api/todo/hardcode1 \n" +
						"   http://localhost:8080/api/todo/find                 http://localhost:8080/api/todo/find?i=2  \n" +
						"   http://localhost:8080/api/todo/delete               http://localhost:8080/api/todo/delete?i=1 \n" +
						"   http://localhost:8080/api/todo/update               http://localhost:8080/api/todo/update?i=3 \n" +
						"   http://localhost:8080/api/todo/upload               http://localhost:8080/api/todo/upload?i=1  \n" +
						"   http://localhost:8080/api/todo/list                 http://localhost:8080/api/todo/listjson   \n" +
						"   http://localhost:8080								http://localhost:8080/h2-console\n" +
						"		Hardcoced data coming from scripts (data.sql  + schema.sql)\n" +
						"		H2 login:      	com.mysql.cj.jdbc.Driver        jdbc:mysql://localhost:3306/sumit          root         1029   \n" +
						"		UserDetails user1 =  User.withDefaultPasswordEncoder().username(\"s\").password(\"1\").roles(\"USER\", \"ADMIN\").build();\n" +
						"		UserDetails user1 =  User.withDefaultPasswordEncoder().username(\"d\").password(\"1\").roles(\"ADMIN\").build();\n" +
						"		UserDetails user1 =  User.withDefaultPasswordEncoder().username(\"f\").password(\"1\").roles(\"USER\").build();\n"





		);

	}

}
