package kr.co.hanbit.product.management;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }

    @Bean
    public ApplicationRunner runner(DataSource dataSource) {
        return args -> {
            // 이 부분에 실행할 코드를 넣으면 된다.
            try (Connection connection = dataSource.getConnection()) {
                // Application startup verification hook.
            } catch (SQLException exception) {
                throw new IllegalStateException("Failed to obtain database connection.", exception);
            }
        };
    }
}
