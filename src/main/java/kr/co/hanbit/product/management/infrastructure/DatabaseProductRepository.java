package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
import kr.co.hanbit.product.management.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class DatabaseProductRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired // jdbc 템플릿 의존성 주입하여 db에 sql쿼리 날릴수 있게 함.. 실무에서는 jpa사용하지만 sql이해를 위해 사용
    public DatabaseProductRepository (NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product add(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate
                .update("INSERT INTO products (name, price, amount) VALUES (:name, :price, :amount)",
                        namedParameter, keyHolder);
        Long genetatedId = keyHolder.getKey().longValue();
        product.setId(genetatedId);

        return product;
    }

    public Product findById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);
        Product product = null;

        try {
            product = namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name, price, amount FROM products WHERE id=:id",
                    namedParameter,
                    new BeanPropertyRowMapper<>(Product.class)
            );
        } catch (EmptyResultDataAccessException exception){
            throw new EntityNotFoundException("Product 를 찾지 못했습니다.");
        }

        return product;
    }

    public List<Product> findAll() {
        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products",
                new BeanPropertyRowMapper<>(Product.class)
        );
        return products;
    }

    public List<Product> findByNameContaining(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");

        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products WHERE name LIKE :name",
                namedParameter,
                new BeanPropertyRowMapper<>(Product.class)
        );

        return products;
    }

    public Product update(Product product) {
        SqlParameterSource namedParameter  = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("UPDATE products SET name=:name, price=:price, amount=:amount WHERE id=:id", namedParameter);

        return product;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update("DELETE FROM products WHERE id=:id", namedParameter);
    }

}
