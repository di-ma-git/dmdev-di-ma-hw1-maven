package com.dima.testdata;

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
import javax.persistence.EntityManager;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.dima.enums.MedicineType.CAPSULES;
import static com.dima.enums.MedicineType.INJECTIONS;
import static com.dima.enums.OrderStatus.*;
import static com.dima.enums.Role.*;
import static java.time.LocalDateTime.*;

@UtilityClass
public class TestDataImport {

    public void importData(EntityManager entityManager) {

        ActiveSubstance acetylsalicylic = createActiveSubstance(entityManager, "Acetylsalicylic acid");
        ActiveSubstance ascorbic = createActiveSubstance(entityManager, "Ascorbic acid");
        ActiveSubstance cyanocobalamin = createActiveSubstance(entityManager, "Vitamin B");
        ActiveSubstance activeSubstance4 = createActiveSubstance(entityManager, "Active substance 4");

        Manufacturer pfizer = createManufacturer(entityManager, "Pfizer", "USA");
        Manufacturer sinopharm = createManufacturer(entityManager, "Sinopharm", "China");
        Manufacturer bayer = createManufacturer(entityManager, "Bayer", "Germany");
        Manufacturer pharmacom = createManufacturer(entityManager, "Pharmacom", "USA");

        ProductCategory painkillers = createProductCategory(entityManager, "Painkillers");
        ProductCategory vitamins = createProductCategory(entityManager, "Vitamins");
        ProductCategory antiviral = createProductCategory(entityManager, "Antiviral drugs");
        ProductCategory hormones = createProductCategory(entityManager, "Hormonal drugs");
        ProductCategory antibiotics = createProductCategory(entityManager, "Antibiotic");

        Product aspirin1 = createProduct(entityManager, "Aspirin", pfizer, painkillers, 15.46F, CAPSULES);
        Product aspirin2 = createProduct(entityManager, "Aspirin", bayer, painkillers, 16.99F, CAPSULES);
        Product testosterone = createProduct(entityManager, "Testosterone", pharmacom, hormones, 45.00F, INJECTIONS);
        Product vitaminComplex = createProduct(entityManager, "Vitamin complex", sinopharm, vitamins, 125.00F, CAPSULES);
        Product vitaminC = createProduct(entityManager, "Vitamin C", bayer, vitamins, 12.30F, CAPSULES);
        Product boldenone = createProduct(entityManager, "Boldenone", pharmacom, hormones, 56.20F, INJECTIONS);

        ProductActiveSubstance productActiveSubstance1 = createProductActiveSubstance(entityManager, aspirin1, acetylsalicylic);
        ProductActiveSubstance productActiveSubstance2 = createProductActiveSubstance(entityManager, aspirin1, ascorbic);

        ProductActiveSubstance productActiveSubstance3 = createProductActiveSubstance(entityManager, vitaminC, ascorbic);

        ProductActiveSubstance productActiveSubstance4 = createProductActiveSubstance(entityManager, vitaminComplex, ascorbic);
        ProductActiveSubstance productActiveSubstance5 = createProductActiveSubstance(entityManager, vitaminComplex, cyanocobalamin);

        ProductActiveSubstance productActiveSubstance6 = createProductActiveSubstance(entityManager, testosterone, activeSubstance4);

        ProductActiveSubstance productActiveSubstance7 = createProductActiveSubstance(entityManager, aspirin2, ascorbic);
        ProductActiveSubstance productActiveSubstance8 = createProductActiveSubstance(entityManager, aspirin2, acetylsalicylic);

        ProductActiveSubstance productActiveSubstance9 = createProductActiveSubstance(entityManager, boldenone, activeSubstance4);

        User user1 = createUser(entityManager, CUSTOMER, "User1", "test1@gmail.com", "12345", "79876543211");
        User user2 = createUser(entityManager, CUSTOMER, "User2", "test32@gmail.com", "123456", "79876543212");
        User user3 = createUser(entityManager, ADMIN, "admin1", "admin@admin.pharmacy.com", "1234567", "79876543213");

        var order1User1 = createOrder(entityManager, user1, PAID,
                of(2023, 1, 10, 9, 0),
                of(2023, 1, 10, 10, 0),
                BigDecimal.valueOf(200), Payment.CARD);
        var order2User1 = createOrder(entityManager, user1, NOT_PAID,
                of(2023, 1, 10, 13, 0),
                null, BigDecimal.valueOf(100), null);

        var order1User2 = createOrder(entityManager, user2, PAID,
                of(2023, 1, 10, 9, 0),
                of(2023, 1, 10, 10, 0),
                BigDecimal.valueOf(200), Payment.CARD);
        var order2User2 = createOrder(entityManager, user2, PAID,
                of(2023, 1, 10, 11, 0),
                of(2023, 1, 10, 12, 0),
                BigDecimal.valueOf(200), Payment.CARD);
        var order3User2 = createOrder(entityManager, user2, NOT_PAID,
                of(2023, 1, 10, 13, 0),
                null, BigDecimal.valueOf(100), null);


        var productInOrder1 = createProductInOrder(entityManager, order1User1, aspirin1, 2);
        var productInOrder2 = createProductInOrder(entityManager, order1User1, testosterone, 10);
        var productInOrder3 = createProductInOrder(entityManager, order1User1, vitaminC, 5);

        var productInOrder4 = createProductInOrder(entityManager, order2User1, boldenone, 1);

        var productInOrder5 = createProductInOrder(entityManager, order1User2, aspirin2, 3);
        var productInOrder6 = createProductInOrder(entityManager, order1User2, boldenone, 3);
        var productInOrder7 = createProductInOrder(entityManager, order1User2, vitaminC, 4);
        var productInOrder8 = createProductInOrder(entityManager, order1User2, vitaminComplex, 4);

        var productInOrder9 = createProductInOrder(entityManager, order2User2, vitaminComplex, 2);
        var productInOrder10 = createProductInOrder(entityManager, order2User2, vitaminC, 2);

        var productInOrder11 = createProductInOrder(entityManager, order3User2, testosterone, 1);

    }

    private User createUser(EntityManager entityManager,
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
                .deliveryAddress("test address for all users")
                .build();

        entityManager.persist(user);
        return user;
    }

    private Order createOrder(EntityManager entityManager,
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
        entityManager.persist(order);

        return order;
    }

    private ProductInOrder createProductInOrder(EntityManager entityManager,
                                                Order order,
                                                Product product,
                                                Integer quantity) {
        var productInOrder = ProductInOrder.builder()
                .product(product)
                .order(order)
                .quantity(quantity)
                .build();
        entityManager.persist(productInOrder);

        return productInOrder;
    }

    private Product createProduct(EntityManager entityManager,
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
        entityManager.persist(product);

        return product;
    }

    private Manufacturer createManufacturer(EntityManager entityManager, String name, String country) {
        var manufacturer = Manufacturer.builder()
                .name(name)
                .country(country)
                .build();
        entityManager.persist(manufacturer);

        return manufacturer;
    }

    private ProductActiveSubstance createProductActiveSubstance(EntityManager entityManager,
                                                                Product product,
                                                                ActiveSubstance activeSubstance) {
        var productActiveSubstance = ProductActiveSubstance.builder()
                .product(product)
                .activeSubstance(activeSubstance)
                .build();
        entityManager.persist(productActiveSubstance);

        return productActiveSubstance;
    }

    private ProductCategory createProductCategory(EntityManager entityManager, String name) {
        var productCategory = ProductCategory.builder()
                .name(name)
                .build();
        entityManager.persist(productCategory);

        return productCategory;
    }

    private ActiveSubstance createActiveSubstance(EntityManager entityManager, String name) {
        var activeSubstance = ActiveSubstance.builder()
                .name(name)
                .build();
        entityManager.persist(activeSubstance);

        return activeSubstance;
    }

}
