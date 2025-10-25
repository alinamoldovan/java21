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



Implementations of the equals and hashCode methods, which specify that two record classes are equal if they are of the same type and contain equal component values.