import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nenazvan.services.Order;
import com.nenazvan.services.db.dbHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderDaoSpec {
  static Dao<Order, String> orderDao;
  static ConnectionSource connectionSource;

  @BeforeClass
  public static void setUpDatabaseLayer() {
    try {
      connectionSource = new dbHelper().getConnectionSource();
      TableUtils.createTableIfNotExists(connectionSource, Order.class);

      orderDao = DaoManager.createDao(connectionSource, Order.class);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @Before
  public void clearPupils() {
    try {
      TableUtils.clearTable(connectionSource, Order.class);
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @Test
  public void orderCanBeStoredInDB(){
    Order order1 = new Order(LocalDateTime.now(), LocalDateTime.now(), true, "god", "abc", 23,
            "abc", "abc", true, true, true, true, true, true, true);
    Order order2 = new Order(LocalDateTime.now(), LocalDateTime.now(), true, "Art", "det", 23,
            "det", "det", true, true, true, true, true, true, true);
    Order readOrder1 = null;
    Order readOrder2 = null;


    try {
      orderDao.create(order1);
      orderDao.create(order2);

      readOrder1 = orderDao.queryForId(order1.getId().toString());
      readOrder2 = orderDao.queryForId(order2.getId().toString());
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertNotNull(readOrder1);
    assertNotNull(readOrder2);

    assertEquals(readOrder1.getId(), order1.getId());
    assertEquals(readOrder2.getId(), order2.getId());
  }
}
