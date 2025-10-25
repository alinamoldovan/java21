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

static double area(Shape shape) {
    return switch (shape) {
        case Circle c -> Math.PI * c.radius() * c.radius();
        case Rectangle r -> r.width() * r.height();
        case Triangle t -> t.base() * t.height() / 2;
    };
}
Compilatorul stie ca Shape are doar 3 implementari si nu mai este nevoie de 
default case
Dacă adaugi o nouă clasă Polygon fără să o gestionezi în switch, compilatorul îți va da eroare — ceea ce e foarte valoros pentru mentenanță.

💡 4️⃣ Combinarea cu records
- Record-urile sunt perfecte in combinatie cu sealed types.
- Deoarece sunt data carriers, ele descriu natural forme, eveniment sau rezultate 

Exemplu:
public sealed interface Shape permits Circle, Rectangle, Triangle {}

public record Circle(double radius) implements Shape {}
public record Rectangle(double width, double height) implements Shape {}
public record Triangle(double base, double height) implements Shape {}


➡️ Fiecare subtip e clar, imutabil și sigur.

Acum poți scrie:

Shape s = new Circle(2.5);
System.out.println(area(s));

⚙️ 5️⃣ Cazuri de utilizare (practice)
Caz	Explicație
🧩 Modelare de ierarhii de domeniu	ex: Event permits LoginEvent, LogoutEvent, ErrorEvent
⚙️ Design DDD (domain-driven)	clarifică granițele tipurilor și regulile
🧠 Ierarhii de erori	sealed class AppError permits ValidationError, DatabaseError, NetworkError
🔍 State machines / workflows	State permits Idle, Running, Completed, Failed
📊 Rezultate de operații	sealed interface Result permits Success, Failure

🔹 Exemplu DDD – rezultat al unei operații
public sealed interface OperationResult permits Success, Failure {}

public record Success(String message) implements OperationResult {}
public record Failure(String reason) implements OperationResult {}

static String handle(OperationResult result) {
    return switch (result) {
        case Success s -> "✅ Success: " + s.message();
        case Failure f -> "❌ Failure: " + f.reason();
    };
}


➡️ E clar, exhaustiv și 100% type-safe — fără nevoie de if/else sau excepții.

🔐 6️⃣ Cum funcționează permits

Când definești:

public sealed class Animal permits Dog, Cat {}


👉 Dog și Cat trebuie să fie în același fișier sursă sau pachet
(dacă sunt în alt pachet, trebuie să le exporți explicit cu module-info.java în aplicațiile modulare).

🚫 7️⃣ Limitări & bune practici
Limitare	                                 Explicație
Toate clasele “permise” trebuie declarate	 altfel eroare la compilare

Clasele “permise” trebuie să declare final,  obligatoriu
sealed sau non-sealed	                    

sealed nu merge cu clase anonime sau locale	 doar top-level/nested

Nu funcționează cu reflexie pentru           (dar merge cu Spring/Weld CDI)
instanțierea dinamică fără permis explicit


2️⃣ De ce yield și nu return?

Pentru că yield returnează o valoare dintr-o ramură a unui switch expression,
dar nu părăsește metoda curentă.

👉 return ar opri întreaga metodă.
👉 yield doar “livrează” rezultatul ramurii curente către switch.

3️⃣ În termeni simpli:
Cuvânt cheie	Se aplică la	                    Efect
return	        metoda curentă	                    iese complet din metodă
yield	        doar din ramura switch expression	returnează valoarea acelei ramuri către switch