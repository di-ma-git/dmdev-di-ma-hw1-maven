package com.dima.config;

import com.dima.entity.ActiveSubstance;
import com.dima.entity.Manufacturer;
import com.dima.entity.Order;
import com.dima.entity.Product;
import com.dima.entity.ProductActiveSubstance;
import com.dima.entity.ProductCategory;
import com.dima.entity.ProductInOrder;
import com.dima.entity.User;
import java.lang.reflect.Proxy;
import javax.annotation.PreDestroy;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatabaseConfig.class)
@ComponentScan(basePackages = "com.dima")
public class ApplicationConfiguration {

}
