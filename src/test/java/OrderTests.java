import com.nenazvan.services.Order;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class OrderTests {
  @Test
  public void testOrderDateParse() {
    LocalDateTime dateTime = LocalDateTime.now();
    Order order = new Order(dateTime, LocalDateTime.now(), true, "god", "abc", 23,
            "abc", "abc", true, true, true, true, true, true, true);

    LocalDateTime orderDate = order.getOrderDate();

    assertEquals(getFormattedDate(orderDate), getFormattedDate(dateTime));
  }

  private String getFormattedDate(LocalDateTime date) {
    return date.format((DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
  }
}
