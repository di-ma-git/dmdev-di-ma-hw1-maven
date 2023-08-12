package com.dima.config;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductCategory;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import java.lang.reflect.Proxy;
import javax.annotation.PreDestroy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    @Bean
    public Session session(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new org.hibernate.cfg.Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Manufacturer.class);
        configuration.addAnnotatedClass(ActiveSubstance.class);
        configuration.addAnnotatedClass(ProductCategory.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(ProductInOrder.class);
        configuration.addAnnotatedClass(ProductActiveSubstance.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    @Bean(name = "hiberConfiguration")
    public org.hibernate.cfg.Configuration configuration() {
        var configuration = new org.hibernate.cfg.Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(Manufacturer.class);
        configuration.addAnnotatedClass(ActiveSubstance.class);
        configuration.addAnnotatedClass(ProductCategory.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(ProductInOrder.class);
        configuration.addAnnotatedClass(ProductActiveSubstance.class);

        return configuration;
    }

    @PreDestroy
    void destroy() {
        sessionFactory().close();
    }

}
