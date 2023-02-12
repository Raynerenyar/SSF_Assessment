package sg.edu.nus.iss.app.assessment.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.assessment.model.Order;
import sg.edu.nus.iss.app.assessment.model.Pizza;

import static sg.edu.nus.iss.app.assessment.repository.Queries.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class PizzaOrderRepoImpl implements PizzaOrderRepo {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Integer getPizzaPrice(String type) {
        return jdbc.query(GET_PIZZA_PRICE_SQL, new ResultSetExtractor<Integer>() {

            @Override
            @Nullable
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getInt("price");
            }
        }, type);
    }

    @Override
    public Double getPizzaMultiplier(String size) {
        return jdbc.query(GET_PIZZA_SIZE_MULTI_SQL, new ResultSetExtractor<Double>() {

            @Override
            @Nullable
            public Double extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getDouble("multiplier");
            }
        }, size);
    }

    @Override
    public Integer insertSelectionOfPizzas(Pizza pizza, Order order) {
        Object[] args = new Object[] {
                order.getId(),
                pizza.getPizzaSize(),
                pizza.getType(),
                pizza.getQuantity() };
        int[] argTypes = new int[] {
                Types.VARCHAR,
                Types.CHAR,
                Types.VARCHAR,
                Types.INTEGER };

        return jdbc.update(INSERT_PIZZA_SELECTION_SQL, args, argTypes);
    }

    @Override
    public Integer insertOrder(Order order) {
        Object[] args = new Object[] {
                order.getId(),
                order.getFullName(),
                order.getAddress(),
                order.getPhone(),
                order.getPizzaCost(),
                order.getRush(),
                order.getTotalCost(),
                order.getComments() };
        int[] argTypes = new int[] {
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.NUMERIC,
                Types.DOUBLE,
                Types.BOOLEAN,
                Types.DOUBLE,
                Types.VARCHAR };

        return jdbc.update(INSERT_ORDER_SQL, args, argTypes);
    }

    @Override
    public Order getOrder(String id) {

        return jdbc.queryForObject(GET_ORDER_SQL, BeanPropertyRowMapper.newInstance(Order.class), id);
    }

    @Override
    public List<Pizza> getPizzaSelections(String id) throws DataAccessException {
        // jdbc.queryforlist
        try (Stream<Pizza> result = jdbc.queryForStream(GET_PIZZA_SELECTIONS_SQL,
                BeanPropertyRowMapper.newInstance(Pizza.class), id)) {
            List<Pizza> listof = result.toList();
            return listof;
        }
    }
}
