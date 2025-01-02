
--- Here we can do any operation once schema is generated at server..., ay data addition etc..
-- Ref : <application.properties  ====>>>  spring.datasource.data=classpath:data.sql

 INSERT INTO todoh (id, creation_date, description, done, target_date, username) VALUES
         (FLOOR(RAND() * (1000 - 1 + 1)) + 1, NOW() , "data.sql_ADDED", true, DATE_ADD(NOW(), INTERVAL 1 YEAR) , "sumit@sumit.com");

