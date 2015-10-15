package com.iyonger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.Arrays;

/**
 * Hello world!
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(App.class.getSimpleName());

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class, args);

      /*  System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }*/
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("update tables");
        jdbcTemplate.execute("show tables");
        String sql = "insert into trade(name,credit_card_no,mark,bill_id) values(?,?,?,?) ";


        Object[] objects = new Object[4];
        objects[0] = "it's name";
        objects[1] = "123456789";
        objects[2] = "mark mark mark";
        objects[3] = "123";
        int sum = 0;
        int types[] = {Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.INTEGER};


        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            jdbcTemplate.update(sql, objects, types);
            long end = System.currentTimeMillis();

            log.info("update with type cost :{} ms", end - start);
            sum += (end - start);
        }

        log.info("update with type avg cost:{} ms", sum / 10);


        sum = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            jdbcTemplate.update(sql, objects);
            long end = System.currentTimeMillis();

            log.info("update cost :{} ms", end - start);
            sum += (end - start);
        }

        log.info("update avg cost:{} ms", sum / 10);

    }
}
