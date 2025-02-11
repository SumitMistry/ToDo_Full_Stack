package springboot.ToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // added this to handle "@CreatedDate" automatically in entity/database entry. so local creation date will be aut handled by App
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
						"   http://localhost:8080/api/todo/insert3 -> (+)SpringDataJPA-SQL <- http://localhost:8080/api/todo/hardcode1 \n" +

						"   http://localhost:8080/login   -> Spring Security UI\n" +
						"   http://localhost:8080/login1  -> new UI\n" +

						"   http://localhost:8080/api/todo/find                 http://localhost:8080/api/todo/find?u=2  \n" +
						"   http://localhost:8080/searchAPI						http://localhost:8080/api/todo/searchAPI?searchKey=sumit\n" +
						"   http://localhost:8080/api/todo/delete               http://localhost:8080/api/todo/delete?u=1 \n" +
						"   http://localhost:8080/api/todo/update               http://localhost:8080/api/todo/update?u=3 \n" +
						"   http://localhost:8080/api/todo/upload               http://localhost:8080/api/todo/upload?u=1  \n" +
						"   http://localhost:8080/api/todo/findById             http://localhost:8080/api/todo/findById?u=1  \n" +
						"   http://localhost:8080/api/todo/findByUID            http://localhost:8080/api/todo/findByUID?u=1  \n" +
						"   http://localhost:8080/api/todo/findByUser           http://localhost:8080/api/todo/findByUser?user=vraj@yyz.com  \n" +
						"   http://localhost:8080/api/todo/existbyuid			http://localhost:8080/api/todo/existbyuid?u=1\n" +

						"   http://localhost:8080/api/todo/list (USER specific) http://localhost:8080/api/todo/listjson   \n" +
						"   http://localhost:8080/api/todo/listall				http://localhost:8080/h2-console\n" +
						"   http://localhost:8080/login1						http://localhost:8080/signup					\n" +
						"   http://localhost:8080								\n" +

						"		Hardcoced data coming from scripts (data.sql  + schema.sql  +  Autorun_justReference_DataInit.java)\n" +
						"		H2 login:      	com.mysql.cj.jdbc.Driver        jdbc:mysql://localhost:3306/sumit          root         1029   \n" +
						"		UserDetails user1 =  UserAuth.withDefaultPasswordEncoder().username(\"sumit@bofa.com\").password(\"1\").roles(\"ADMIN\", \"DEVELOPER\").build();\n" +
						"		UserDetails user2 =  UserAuth.withDefaultPasswordEncoder().username(\"vraj@yyz.com\").password(\"1\").roles(\"GUEST\", \"USER\").build();\n" +
						"		UserDetails user3 =  UserAuth.withDefaultPasswordEncoder().username(\"f\").password(\"1\").roles(\"DEVELOPER\").build();\n"


		);

	}

}
