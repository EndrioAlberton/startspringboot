package com.javapb.startspringboot.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.javapb.startspringboot.entities.Category;
import com.javapb.startspringboot.entities.Order;
import com.javapb.startspringboot.entities.OrderItem;
import com.javapb.startspringboot.entities.Payment;
import com.javapb.startspringboot.entities.Product;
import com.javapb.startspringboot.entities.User;
import com.javapb.startspringboot.entities.enums.OrderStatus;
import com.javapb.startspringboot.repositories.CategoryRepository;
import com.javapb.startspringboot.repositories.OrderItemRepository;
import com.javapb.startspringboot.repositories.OrderRepository;
import com.javapb.startspringboot.repositories.ProductRepository;
import com.javapb.startspringboot.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category(null, "Eletronicos");
		Category cat2 = new Category(null, "Livros");
		Category cat3 = new Category(null, "Computadores");
		
		Product p1 = new Product(null, "O Senhor dos Anéis", "Uma aventura épica em uma terra de fantasia.", 90.5, "");
		Product p2 = new Product(null, "Smart TV", "Experiência imersiva de entretenimento em alta definição.", 2190.0, "");
		Product p3 = new Product(null, "Macbook Pro", "Potência e elegância para suas necessidades profissionais.", 1250.0, "");
		Product p4 = new Product(null, "PC Gamer", "Desempenho excepcional para os melhores jogos.", 1200.0, "");
		Product p5 = new Product(null, "Rails para Iniciantes", "Um guia passo a passo para aprender desenvolvimento web.", 100.99, ""); 

		categoryRepository.saveAll(Arrays.asList(cat1,cat2,cat3));
		productRepository.saveAll(Arrays.asList(p1,p2,p3,p4, p5));
		
		p1.getCategories().add(cat2);
		p2.getCategories().add(cat3);
		p2.getCategories().add(cat1);
		p3.getCategories().add(cat3);
		p4.getCategories().add(cat3);
		p5.getCategories().add(cat2);
		
		productRepository.saveAll(Arrays.asList(p1,p2,p3,p4, p5));

		User u1 = new User(null, "Justin", "justin@gmail.com", "988888888", "123456");
		User u2 = new User(null, "Eduardo", "edu@gmail.com", "977777777", "123456"); 
		
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WATTING_PAYMENT,u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.CANCELED, u1); 
		
		userRepository.saveAll(Arrays.asList(u1,u2));
		orderRepository.saveAll(Arrays.asList(o1,o2,o3));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());
		
		orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3,oi4));

		Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
		o1.setPayment(pay1);

		orderRepository.save(o1);

	}
	
	
}
