package com.dima.testData;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductCategory;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import com.dima.enums.MedicineType;
import com.dima.enums.OrderStatus;
import com.dima.enums.Payment;
import com.dima.enums.Role;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dima.enums.MedicineType.CAPSULES;
import static com.dima.enums.MedicineType.INJECTIONS;
import static com.dima.enums.OrderStatus.*;
import static com.dima.enums.Role.*;
import static java.time.LocalDateTime.*;

@UtilityClass
public class TestDataImport {

    public void importData(Session session) {

        ActiveSubstance acetylsalicylic = createActiveSubstance(session, "Acetylsalicylic acid");
        ActiveSubstance ascorbic = createActiveSubstance(session, "Ascorbic acid");
        ActiveSubstance cyanocobalamin = createActiveSubstance(session, "Vitamin B");
        ActiveSubstance activeSubstance4 = createActiveSubstance(session, "Active substance 4");

        Manufacturer pfizer = createManufacturer(session, "Pfizer", "USA");
        Manufacturer sinopharm = createManufacturer(session, "Sinopharm", "China");
        Manufacturer bayer = createManufacturer(session, "Bayer", "Germany");
        Manufacturer pharmacom = createManufacturer(session, "Pharmacom", "USA");

        ProductCategory painkillers = createProductCategory(session, "Painkillers");
        ProductCategory vitamins = createProductCategory(session, "Vitamins");
        ProductCategory antiviral = createProductCategory(session, "Antiviral drugs");
        ProductCategory hormones = createProductCategory(session, "Hormonal drugs");
        ProductCategory antibiotics = createProductCategory(session, "Antibiotic");

        Product aspirin1 = createProduct(session, "Aspirin", pfizer, painkillers, 15.46F, CAPSULES);
        Product aspirin2 = createProduct(session, "Aspirin", bayer, painkillers, 16.99F, CAPSULES);
        Product testosterone = createProduct(session, "Testosterone", pharmacom, hormones, 45.00F, INJECTIONS);
        Product vitaminComplex = createProduct(session, "Vitamin complex", sinopharm, vitamins, 125.00F, CAPSULES);
        Product vitaminC = createProduct(session, "Vitamin C", bayer, vitamins, 12.30F, CAPSULES);
        Product boldenone = createProduct(session, "Boldenone", pharmacom, hormones, 56.20F, INJECTIONS);

        ProductActiveSubstance productActiveSubstance1 = createProductActiveSubstance(session, aspirin1, acetylsalicylic);
        ProductActiveSubstance productActiveSubstance2 = createProductActiveSubstance(session, aspirin1, ascorbic);

        ProductActiveSubstance productActiveSubstance3 = createProductActiveSubstance(session, vitaminC, ascorbic);

        ProductActiveSubstance productActiveSubstance4 = createProductActiveSubstance(session, vitaminComplex, ascorbic);
        ProductActiveSubstance productActiveSubstance5 = createProductActiveSubstance(session, vitaminComplex, cyanocobalamin);

        ProductActiveSubstance productActiveSubstance6 = createProductActiveSubstance(session, testosterone, activeSubstance4);

        ProductActiveSubstance productActiveSubstance7 = createProductActiveSubstance(session, aspirin2, ascorbic);
        ProductActiveSubstance productActiveSubstance8 = createProductActiveSubstance(session, aspirin2, acetylsalicylic);

        ProductActiveSubstance productActiveSubstance9 = createProductActiveSubstance(session, boldenone, activeSubstance4);

        User user1 = createUser(session, CUSTOMER, "User1", "test1@gmail.com", "12345", "79876543211");
        User user2 = createUser(session, CUSTOMER, "User2", "test2@gmail.com", "123456", "79876543212");
        User user3 = createUser(session, ADMIN, "User3", "admin@admin.pharmacy.com", "1234567", "79876543213");

        var order1User1 = createOrder(session, user1, PAID,
                of(2023, 1, 10, 9, 0),
                of(2023, 1, 10, 10, 0),
                BigDecimal.valueOf(200), Payment.CARD);
        var order2User1 = createOrder(session, user1, NOT_PAID,
                of(2023, 1, 10, 13, 0),
                null, BigDecimal.valueOf(100), null);

        var order1User2 = createOrder(session, user2, PAID,
                of(2023, 1, 10, 9, 0),
                of(2023, 1, 10, 10, 0),
                BigDecimal.valueOf(200), Payment.CARD);
        var order2User2 = createOrder(session, user2, PAID,
                of(2023, 1, 10, 11, 0),
                of(2023, 1, 10, 12, 0),
                BigDecimal.valueOf(200), Payment.CARD);
        var order3User2 = createOrder(session, user2, NOT_PAID,
                of(2023, 1, 10, 13, 0),
                null, BigDecimal.valueOf(100), null);


        var productInOrder1 = createProductInOrder(session, order1User1, aspirin1, 2);
        var productInOrder2 = createProductInOrder(session, order1User1, testosterone, 10);
        var productInOrder3 = createProductInOrder(session, order1User1, vitaminC, 5);

        var productInOrder4 = createProductInOrder(session, order2User1, boldenone, 1);

        var productInOrder5 = createProductInOrder(session, order1User2, aspirin2, 3);
        var productInOrder6 = createProductInOrder(session, order1User2, boldenone, 3);
        var productInOrder7 = createProductInOrder(session, order1User2, vitaminC, 4);
        var productInOrder8 = createProductInOrder(session, order1User2, vitaminComplex, 4);

        var productInOrder9 = createProductInOrder(session, order2User2, vitaminComplex, 2);
        var productInOrder10 = createProductInOrder(session, order2User2, vitaminC, 2);

        var productInOrder11 = createProductInOrder(session, order3User2, testosterone, 1);

    }

    private User createUser(Session session,
                            Role role,
                            String name,
                            String email,
                            String password,
                            String phone) {
        var user = User.builder()
                .role(role)
                .name(name)
                .email(email)
                .password(password)
                .phoneNumber(phone)
                .build();

        session.save(user);
        return user;
    }

    private Order createOrder(Session session,
                              User user,
                              OrderStatus status,
                              LocalDateTime orderDate,
                              LocalDateTime payDate,
                              BigDecimal totalSum,
                              Payment payment) {
        var order = Order.builder()
                .user(user)
                .orderStatus(status)
                .orderDate(orderDate)
                .payDate(payDate)
                .totalSum(totalSum)
                .payment(payment)
                .build();
        session.save(order);

        return order;
    }

    private ProductInOrder createProductInOrder(Session session,
                                                Order order,
                                                Product product,
                                                Integer quantity) {
        var productInOrder = ProductInOrder.builder()
                .product(product)
                .order(order)
                .quantity(quantity)
                .build();
        session.save(productInOrder);

        return productInOrder;
    }

    private Product createProduct(Session session,
                                  String name,
                                  Manufacturer manufacturer,
                                  ProductCategory productCategory,
                                  Float price, MedicineType type) {
        var product = Product.builder()
                .name(name)
                .manufacturer(manufacturer)
                .productCategory(productCategory)
                .price(price)
                .medicineType(type)
                .build();
        session.save(product);

        return product;
    }

    private Manufacturer createManufacturer(Session session, String name, String country) {
        var manufacturer = Manufacturer.builder()
                .name(name)
                .country(country)
                .build();
        session.save(manufacturer);

        return manufacturer;
    }

    private ProductActiveSubstance createProductActiveSubstance(Session session,
                                                                Product product,
                                                                ActiveSubstance activeSubstance) {
        var productActiveSubstance = ProductActiveSubstance.builder()
                .product(product)
                .activeSubstance(activeSubstance)
                .build();
        session.save(productActiveSubstance);

        return productActiveSubstance;
    }

    private ProductCategory createProductCategory(Session session, String name) {
        var productCategory = ProductCategory.builder()
                .name(name)
                .build();
        session.save(productCategory);

        return productCategory;
    }

    private ActiveSubstance createActiveSubstance(Session session, String name) {
        var activeSubstance = ActiveSubstance.builder()
                .name(name)
                .build();
        session.save(activeSubstance);

        return activeSubstance;
    }

}
