🧠 1️⃣ Ce este un record în Java?

Un record este un tip de clasă imutabilă care conține doar date (așa-numitele data carriers).
Java 21 le oferă complet stabil — nu mai sunt preview.

Sunt concepute ca o alternativă mai curată pentru clase de tipul:

public class User {
private final String name;
private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
    @Override public String toString() { ... }
}


👉 Cu record, scrii doar:

public record User(String name, int age) {}


Și Java:

generează automat:

private final fields

constructor canonical (User(String name, int age))

equals(), hashCode(), toString()

face obiectul imutabil.

⚙️ 2️⃣ Sintaxă și comportament
✅ Declarare simplă
public record Product(String name, double price) {}

✅ Instanțiere
Product p = new Product("Laptop", 999.99);
System.out.println(p.name());  // getter implicit
System.out.println(p);         // Product[name=Laptop, price=999.99]

✅ Getteri sunt numele câmpurilor:

product.name() în loc de getName()
➡️ se comportă ca metode, dar sunt generate automat.

🧩 3️⃣ Constructori în record-uri

Există 3 tipuri de constructori posibili:

🟢 a) Canonical constructor

Acesta este cel implicit — Java îl generează pentru tine.
Dar poți să-l rescrii dacă ai nevoie de validare:

public record Product(String name, double price) {
public Product {
if (price <= 0)
throw new IllegalArgumentException("Price must be > 0");
}
}


➡️ Acesta este un compact constructor — nu mai e nevoie să scrii parametrii sau atribuiri.

🟡 b) Alternative constructors

Poți crea alți constructori, dar trebuie să apeleze canonical-ul:

public record Product(String name, double price) {
public Product(String name) {
this(name, 1.0); // default price
}
}

🔵 c) Custom methods

Record-urile pot avea metode suplimentare, utile pentru logică derivată:

public record Product(String name, double price) {
public double applyDiscount(double percent) {
return price - (price * percent / 100);
}
}

🧱 4️⃣ Limitări și bune practici
❌ Nu poți face	✅ Poți face
Nu poți face extends altă clasă	Poți implementa interfețe
Nu poți avea mutable fields	Poți avea metode normale
Nu poți folosi @Entity (în Hibernate clasic)	Poți folosi ca DTO sau Value Object
Exemplu:
public interface Discountable {
double applyDiscount(double percent);
}

public record Product(String name, double price) implements Discountable {
@Override
public double applyDiscount(double percent) {
return price - (price * percent / 100);
}
}

💡 5️⃣ De ce să folosești record-uri

✅ Când:

Ai DTO-uri sau value objects care nu au logică complexă

Ai transfer de date între layere (REST, repo etc.)

Ai entități temporare pentru calcule, mapping-uri, results

❌ Nu le folosi:

Pentru entități persistente JPA/Hibernate (nu suportă bine imutabilitatea)

Pentru obiecte care necesită moștenire/clasă de bază abstractă

💻 6️⃣ Exerciții practice
🧩 Exercițiul 1 – Validare simplă

Creează un record User(String username, String email) care:

aruncă IllegalArgumentException dacă email nu conține @

are o metodă domain() care returnează doar domeniul emailului.

Exemplu:

User u = new User("alina", "alina@example.com");
System.out.println(u.domain()); // "example.com"

🧩 Exercițiul 2 – Discount logic

Creează un record Product(String name, double price) cu:

validare: price > 0

metodă discount(double percent) care returnează un nou record cu preț redus

Exemplu:

Product p = new Product("Phone", 1000);
Product discounted = p.discount(10);
System.out.println(discounted.price()); // 900.0

🧩 Exercițiul 3 – Combinație cu Stream API

Creează o listă de produse și filtrează doar cele cu preț sub 100:

List<Product> products = List.of(
new Product("Pen", 2.5),
new Product("Laptop", 1500),
new Product("Book", 25)
);

products.stream()
.filter(p -> p.price() < 100)
.forEach(System.out::println);

📘 7️⃣ Resurse extra pentru aprofundare

🔗 Baeldung – Records in Java
https://www.baeldung.com/java-record-keyword

🔗 Oracle Docs – Record Classes
https://docs.oracle.com/en/java/javase/21/language/index.html

📺 YouTube: Java Brains – Records Explained

📘 Modern Java in Action – capitolul despre imutabilitate

In many cases, this data is immutable, since immutability ensures the validity of the data without synchronization.

Records are immutable data classes that require only the type and name of fields.
We also receive public getters methods, whose names match the name of our field, for free.
As with regular Java classes, we can also include static variables and methods in our records.


This method returns true if the supplied object is of the same type and the values of all of its fields match:

declaring a compact constructor and one with an argument list matching the generated constructor results in a compilation error.

Implementations of the equals and hashCode methods, which specify that two record classes are equal if they are of the same type and contain equal component values.

Perfect 👏 — hai să vedem un exemplu complet, profesionist, de integrare între o entitate JPA clasică (@Entity) și un record Java 21 (ProductDto) folosit ca data transfer object (DTO).

Scenariul e exact ca într-un proiect enterprise (Spring/Jakarta + Hibernate/JPA).

🧩 1️⃣ Entitatea JPA (mutabilă, gestionată de Hibernate)
package com.example.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String category;

    public Product() {
        // Hibernate cere un constructor fără argumente
    }

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getteri și setteri
    public Long getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
}


🔹 Important:

Hibernate cere constructorul gol pentru instanțierea din DB.

Câmpurile sunt mutabile.

Obiectele Product sunt gestionate în contextul de persistență (când se face EntityManager.persist() sau find()).

🧠 2️⃣ Record-ul Java 21 – DTO (imutabil)
package com.example.dto;

public record ProductDto(Long id, String name, double price, String category) {
}


🔹 Ce aduce record-ul:

Fără boilerplate (equals, hashCode, toString generate automat)

Imutabil → sigur de folosit în REST APIs

Ideal pentru transfer (nu e gestionat de Hibernate)

🔄 3️⃣ Mapper simplu între Entity ↔ DTO
Varianta clasică (static factory methods):
package com.example.mapper;

import com.example.domain.Product;
import com.example.dto.ProductDto;

public class ProductMapper {

    public static ProductDto toDto(Product entity) {
        if (entity == null) return null;
        return new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getCategory());
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;
        Product product = new Product(dto.name(), dto.price(), dto.category());
        // id-ul e gestionat de Hibernate, deci nu îl setăm manual
        return product;
    }
}


💡 Alternativ: poți folosi MapStruct pentru a genera automat conversiile.

🌐 4️⃣ Service Layer – Folosirea record-ului în business logic
package com.example.service;

import com.example.domain.Product;
import com.example.dto.ProductDto;
import com.example.mapper.ProductMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ProductDto create(ProductDto dto) {
        Product entity = ProductMapper.toEntity(dto);
        em.persist(entity);
        return ProductMapper.toDto(entity);
    }

    public List<ProductDto> findAll() {
        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class)
                                   .getResultList();
        return products.stream()
                       .map(ProductMapper::toDto)
                       .collect(Collectors.toList());
    }
}


🔹 Aici:

Product e mutabil și gestionat de JPA.

ProductDto e imutabil și perfect pentru returnare spre API/Frontend.

Conversia e explicită și sigură.

🧾 5️⃣ Controller REST (exemplu cu Jakarta REST)
package com.example.api;

import com.example.dto.ProductDto;
import com.example.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    private ProductService service;

    @POST
    public ProductDto create(ProductDto dto) {
        return service.create(dto);
    }

    @GET
    public List<ProductDto> list() {
        return service.findAll();
    }
}


➡️ REST API expune doar record ProductDto către clientul web — codul e mai curat și mai sigur.

🧠 6️⃣ Avantajele clare ale acestei separări
Aspect	Entity (@Entity)	Record (DTO)
Gestionat de Hibernate	✅ Da	❌ Nu
Imutabil	❌ Nu	✅ Da
Constructor fără argumente	✅ Da (obligatoriu)	✅ Implicit
Ideal pentru	Persistență	Transfer (REST/API)
Thread-safe	❌ Nu	✅ Da
Cod boilerplate	Mare	Minim
🧩 7️⃣ Extensie practică (bonus)

Dacă folosești Spring Boot 3.2+ (Java 21-ready), poți returna direct record-uri în @RestController:

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductDto> all() {
        return service.findAll();
    }
}


➡️ Spring serializează automat record-ul în JSON (ex. { "id": 1, "name": "Phone", ... }).

🧩 Rezumatul logicii
[DB] ⇄ Product (Entity) ⇄ ProductMapper ⇄ ProductDto (Record) ⇄ [REST/API]
Hibernate manipulează entitatea.

Record-ul oferă un view curat și sigur al datelor.

Mapper-ul e podul între cele două lumi (mutable ↔ immutable).

