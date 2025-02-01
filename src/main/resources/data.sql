
--- Here we can do any operation once schema is generated at server..., ay data addition etc..
-- Ref : <application.properties  ====>>>  spring.datasource.data=classpath:data.sql

INSERT INTO todoh (id, creation_date, description, done, target_date, username) VALUES
         (FLOOR(RAND() * (1000 - 1 + 1)) + 1, NOW() , "Auto-Added by data.sql", true, DATE_ADD(NOW(), INTERVAL 1 YEAR) , "sumit@sumit.com");


delete from sumit.todoh where id BETWEEN 80 AND 980;
