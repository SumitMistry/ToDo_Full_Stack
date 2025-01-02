
--- Here we can do any operation once schema is generated at server..., ay data addition etc..
-- Ref : <application.properties  ====>>>  spring.datasource.data=classpath:data.sql

 INSERT INTO todoh (id, creation_date, description, done, target_date, username) VALUES
            (FLOOR(RAND() * (1000 - 1 + 1)) + 1, "2012-01-20", "data.sql_ADDED", true, "2099-05-04", "sumit@sumit.com");
