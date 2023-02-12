package sg.edu.nus.iss.app.assessment.repository;

public class Queries {
    public static String GET_PIZZA_PRICE_SQL = """
            SELECT price FROM pizza_type
            WHERE type = ?
                """;

    public static String GET_PIZZA_SIZE_MULTI_SQL = """
            select multiplier from pizza_size
            WHERE size = ?
                """;

    public static String INSERT_ORDER_SQL = """
            INSERT INTO pizza_order(
                id,
                full_name,
                address,
                phone,
                pizza_cost,
                rush,
                total_cost,
                comments)
                VALUES (?,?,?,?,?,?,?,?)
                """;

    public static String INSERT_PIZZA_SELECTION_SQL = """
            INSERT INTO pizza_selections(
                pizza_order_id,
                pizza_size,
                type,
                quantity)
                VALUES (?,?,?,?)
                """;

    public static String GET_ORDER_SQL = """
            SELECT * FROM pizza_order WHERE id = ?
                """;

    public static String GET_PIZZA_SELECTIONS_SQL = """
            SELECT * FROM pizza_selections WHERE pizza_order_id = ?;
                """;
}
