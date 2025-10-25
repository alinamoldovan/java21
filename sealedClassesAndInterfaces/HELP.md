🧠 1️⃣ Ce sunt Sealed Classes și Sealed Interfaces
In java, pana la versiunea 15+, orice clasa putea fi extinsa 
de oricine( daca nu era final)
Java 17 a introdus sealed classes si sealed interfaces
si in Java 21 sunt complet stabile si integrate cu pattern 
matching
🧩 2️⃣ Sintaxă de bază
public sealed interface Shape
permits Circle, Rectangle, Triangle {}
Acum doar clasele Circle, Rectangle si Triangle pot
implementa clasa Shape

Fiecare dintre ele trebuie sa declare mai departe cum se 
comporta

* final – nu poate fi extinsă mai departe

* sealed – controlează la rândul ei cine o poate extinde

* non-sealed – redeschide ierarhia (oricine o poate extinde)

public final class Circle implements Shape {
    double radius;
    public Circle(double radius) { this.radius = radius; }
}

public final class Rectangle implements Shape {
    double width, height;
    public Rectangle(double width, double height) {
        this.width = width; this.height = height;
    }
}

public non-sealed class Triangle implements Shape {
    double base, height;
    public Triangle(double base, double height) {
        this.base = base; this.height = height;
    }
}
* non-sealed redeschide mostenirea - deci Triangle poate fi extinsa de alte clase(spre deosebire de final)

⚙️ 3️⃣ De ce sunt utile sealed types

🎯 Avantaj major:

 * Sealed types oferă siguranță la compilare — compilatorul știe toate implementările posibile.

Asta permite:

 * switch exhaustiv cu pattern matching
 * design clar (nu mai ai ierarhii "surpriză")
 * cod mai robust în sisteme de tip domain-driven design (DDD)