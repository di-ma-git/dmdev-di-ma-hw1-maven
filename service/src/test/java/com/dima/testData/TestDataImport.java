package com.dima.testData;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductCategory;
import com.dima.enums.MedicineType;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

import static com.dima.enums.MedicineType.*;
@UtilityClass
public class TestDataImport {

    public void importData(Session session) {

        ActiveSubstance acetylsalicylic = createActiveSubstance(session, "Acetylsalicylic acid");
        ActiveSubstance ascorbic = createActiveSubstance(session, "Ascorbic acid");
        ActiveSubstance cyanocobalamin = createActiveSubstance(session, "Vitamin B");
        ActiveSubstance activeSubstance4 = createActiveSubstance(session, "Active substance 4");

        Manufacturer pfizer = createManufacturer(session, "Pfizer");
        Manufacturer sinopharm = createManufacturer(session, "Sinopharm");
        Manufacturer bayer = createManufacturer(session, "Bayer");
        Manufacturer pharmacom = createManufacturer(session, "Pharmacom");

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

    private Manufacturer createManufacturer(Session session, String name) {
        var manufacturer = Manufacturer.builder()
                .name(name)
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
