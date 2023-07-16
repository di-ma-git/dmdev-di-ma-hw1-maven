package testData;

import com.dmdev.dima.entity.ActiveSubstance;
import com.dmdev.dima.entity.Manufacturer;
import com.dmdev.dima.entity.Order;
import com.dmdev.dima.entity.Product;
import com.dmdev.dima.entity.ProductCategory;
import com.dmdev.dima.entity.User;
import com.dmdev.dima.entity.enums.OrderStatus;
import com.dmdev.dima.entity.enums.Role;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestData {

    public User getSimpleTestUser() {
        return User.builder()
                .name("someuser2")
                .email("test2@gmail.com")
                .password("12345")
                .phoneNumber("+79876543211")
                .role(Role.CUSTOMER)
                .build();
    }

    public Order getSimpleTestOrder() {
        return Order.builder()
                .orderStatus(OrderStatus.NOT_PAID)
                .orderDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();
    }

    public ProductCategory getSimpleTestProductCategory() {
        return ProductCategory.builder()
                .name("Painkillers")
                .description("Some description")
                .build();
    }

    public Product getSimpleTestProduct() {
        return Product.builder()
                .name("Aspirine")
                .price(20.33F)
                .quantityPerPackaging(60L)
                .quantityPerDose(300D)
                .description("Some description of product")
                .build();
    }

    public Manufacturer getSimpleTestManufacturer() {
        return Manufacturer.builder()
                .name("Pfizer")
                .country("USA")
                .description("Some description of manufacturer")
                .build();
    }

    public ActiveSubstance getSimpleTestActiveSubstance() {
        return ActiveSubstance.builder()
                .name("Fluoxetine")
                .description("Some description")
                .build();
    }


}
