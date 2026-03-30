# Java 완벽정리 - 정보처리기사 실기 일타강사 노트

> **최종 업데이트:** 2026년 3월
> **범위:** 2023년 1회 ~ 2025년 3회 (총 9회 시험 기출 분석)
> **저자 노트:** Java는 매 시험 3~5문제, 전체의 15~25%를 차지합니다. 이 문서 하나로 Java 만점을 목표로 합니다.

---

## 출제 패턴 요약

| 주제 | 출제 빈도 | 난이도 | 비고 |
|------|-----------|--------|------|
| 상속/다형성 | 매회 1~2문제 | 최상 | **가장 많이 틀리는 영역!** |
| 문자열 처리 | 매회 0~1문제 | 중 | == vs equals() 반복 출제 |
| 예외 처리 | 2~3회당 1문제 | 중 | finally 실행 여부 |
| 배열/참조 | 2~3회당 1문제 | 중상 | 참조 전달 vs 값 전달 |
| static | 2~3회당 1문제 | 중 | 공유 변수, 후위증가 |
| 인터페이스/제네릭 | 비정기 | 중 | 타입 소거 주의 |
| enum | 비정기 | 하 | values(), name() |
| 람다식 | 비정기 | 중 | 함수형 인터페이스 |

### 시험장 전략
1. **상속 문제가 보이면 반드시 추적표를 그려라** (머릿속으로 하면 틀린다)
2. 필드는 정적 바인딩, 메서드는 동적 바인딩 -- 이것만 외워도 2문제는 맞는다
3. 생성자 호출 순서: 항상 부모 먼저!
4. finally는 return이 있어도 실행된다

---

# 1장. 상속과 다형성 - 최다 출제 파트!

> 이 장만 완벽히 이해하면 Java 문제의 절반은 해결됩니다.

## 1.1 상속 기본 개념

### 상속이란?
기존 클래스(부모)의 필드와 메서드를 새로운 클래스(자식)가 물려받는 것.

```java
class Parent {
    int x = 10;

    void display() {
        System.out.println("Parent display");
    }
}

class Child extends Parent {
    int y = 20;

    void show() {
        System.out.println("Child show");
    }
}
```

### 핵심 규칙
- Java는 **단일 상속**만 허용 (extends 하나만)
- 모든 클래스는 암묵적으로 `Object` 클래스를 상속
- `private` 멤버는 상속되지만 직접 접근 불가
- 생성자는 상속되지 않음

### 접근 제어자와 상속

| 접근 제어자 | 같은 클래스 | 같은 패키지 | 자식 클래스 | 전체 |
|------------|:---------:|:---------:|:---------:|:---:|
| private    | O | X | X | X |
| (default)  | O | O | X | X |
| protected  | O | O | O | X |
| public     | O | O | O | O |

---

## 1.2 생성자 호출 순서 (★★★ 최빈출)

### 핵심 원칙: **부모 생성자가 항상 먼저 실행된다!**

자식 객체를 생성하면:
1. 자식 생성자 호출
2. 자식 생성자 첫 줄에서 `super()` 호출 (명시하지 않으면 컴파일러가 자동 삽입)
3. 부모 생성자 실행 완료
4. 자식 생성자 나머지 실행

### 예제 1: 기본 생성자 호출 순서

```java
class A {
    A() {
        System.out.print("A");
    }
}

class B extends A {
    B() {
        // super(); ← 컴파일러가 자동 삽입!
        System.out.print("B");
    }
}

class C extends B {
    C() {
        // super(); ← 컴파일러가 자동 삽입!
        System.out.print("C");
    }
}

public class Main {
    public static void main(String[] args) {
        C obj = new C();
    }
}
```

**출력:** `ABC`

**추적 과정:**
```
new C() 호출
  → C 생성자 진입
    → super() → B 생성자 진입
      → super() → A 생성자 진입
        → super() → Object 생성자 (출력 없음)
        → print("A")    ← A 출력
      → print("B")      ← B 출력
    → print("C")        ← C 출력
```

### 예제 2: 매개변수 있는 생성자 + super()

```java
class Parent {
    int x;

    Parent() {
        System.out.print("P1 ");
    }

    Parent(int x) {
        this.x = x;
        System.out.print("P2 ");
    }
}

class Child extends Parent {
    Child() {
        super(10);  // 매개변수 있는 부모 생성자 호출
        System.out.print("C1 ");
    }

    Child(int a) {
        this();     // 같은 클래스의 다른 생성자 호출
        System.out.print("C2 ");
    }
}

public class Main {
    public static void main(String[] args) {
        Child c = new Child(5);
    }
}
```

**출력:** `P2 C1 C2`

**추적 과정:**
```
new Child(5) 호출
  → Child(int a) 진입
    → this() → Child() 진입
      → super(10) → Parent(int x) 진입
        → print("P2 ")     ← P2 출력
      → print("C1 ")       ← C1 출력
    → print("C2 ")         ← C2 출력
```

### this()와 super() 규칙

| 규칙 | 설명 |
|------|------|
| `super()`는 생성자 첫 줄에만 올 수 있다 | 두 번째 줄에 쓰면 컴파일 에러 |
| `this()`는 생성자 첫 줄에만 올 수 있다 | 두 번째 줄에 쓰면 컴파일 에러 |
| `super()`와 `this()`는 동시에 쓸 수 없다 | 둘 다 첫 줄이어야 하므로 |
| `this()`를 쓰면 `super()`는 생략 | 체이닝된 생성자에서 `super()` 호출 |
| 아무것도 안 쓰면 `super()` 자동 삽입 | 부모에 기본 생성자 없으면 에러! |

### 예제 3: 부모에 기본 생성자가 없는 경우 (킬러!)

```java
class Parent {
    int x;

    Parent(int x) {
        this.x = x;
        System.out.print("P ");
    }
    // 기본 생성자 없음!
}

class Child extends Parent {
    Child() {
        super(100);  // 반드시 명시해야 함! 안 쓰면 컴파일 에러
        System.out.print("C ");
    }
}

public class Main {
    public static void main(String[] args) {
        Child c = new Child();
    }
}
```

**출력:** `P C`

> **함정:** 부모에 매개변수 있는 생성자만 있으면, 기본 생성자는 자동 생성되지 않는다. 자식에서 반드시 `super(값)`을 명시해야 한다.

---

## 1.3 메서드 오버라이딩과 동적 바인딩 (★★★ 최빈출)

### 오버라이딩(Overriding) vs 오버로딩(Overloading)

| 구분 | 오버라이딩 | 오버로딩 |
|------|-----------|---------|
| 정의 | 부모 메서드를 자식이 재정의 | 같은 이름, 다른 매개변수 |
| 메서드 이름 | 동일 | 동일 |
| 매개변수 | 동일 | **다름** |
| 리턴 타입 | 동일 (또는 하위 타입) | 무관 |
| 바인딩 | **동적 바인딩** (런타임) | 정적 바인딩 (컴파일타임) |
| 키워드 | @Override | 없음 |

### 동적 바인딩의 핵심 원리

```
Parent ref = new Child();
ref.method();  // → Child의 method()가 실행된다!
```

**이유:** 메서드 호출은 **실제 객체(new 뒤의 타입)**를 기준으로 결정된다.

### 예제 4: 동적 바인딩 기본

```java
class Animal {
    String name = "동물";

    void sound() {
        System.out.println("...");
    }
}

class Dog extends Animal {
    String name = "강아지";

    @Override
    void sound() {
        System.out.println("멍멍");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.sound();                          // 멍멍 (동적 바인딩 → Dog의 메서드)
        System.out.println(a.name);         // 동물 (정적 바인딩 → Animal의 필드!)
    }
}
```

**출력:**
```
멍멍
동물
```

### 예제 5: 부모 생성자에서 오버라이딩된 메서드 호출 (★★★ 킬러 문제!)

```java
class Parent {
    int x = 10;

    Parent() {
        display();   // 이 시점에 자식 객체가 만들어지는 중이라면?
    }

    void display() {
        System.out.println("Parent x = " + x);
    }
}

class Child extends Parent {
    int x = 20;

    Child() {
        // super(); ← 자동 삽입, 부모 생성자 먼저 실행
        display();
    }

    @Override
    void display() {
        System.out.println("Child x = " + x);
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
    }
}
```

**출력:**
```
Child x = 0
Child x = 20
```

**왜 이런 결과?**

| 단계 | 설명 |
|------|------|
| 1 | `new Child()` → Child 생성자 진입 |
| 2 | `super()` → Parent 생성자 진입 |
| 3 | Parent 생성자에서 `display()` 호출 |
| 4 | **동적 바인딩!** 실제 객체는 Child이므로 Child의 `display()` 실행 |
| 5 | 그런데 Child의 `int x = 20;`은 아직 초기화 안 됨! (부모 생성자 실행 중) |
| 6 | int의 기본값인 **0** 출력 → `Child x = 0` |
| 7 | Parent 생성자 완료, Child의 필드 초기화 (`x = 20`) |
| 8 | Child 생성자에서 `display()` 호출 → `Child x = 20` |

> **이것이 시험에서 가장 많이 틀리는 패턴입니다.** 부모 생성자에서 호출된 메서드도 동적 바인딩이 적용되며, 이때 자식의 필드는 아직 기본값(0, null, false)입니다.

---

## 1.4 필드 숨김과 정적 바인딩 (★★★ 최빈출)

### 핵심 원칙: **필드는 선언 타입(참조 변수 타입)을 따른다!**

```
Parent ref = new Child();
ref.x      → Parent의 x  (정적 바인딩!)
ref.method() → Child의 method()  (동적 바인딩!)
```

이것을 **필드 숨김(Field Hiding)**이라 한다. 자식 클래스에서 부모와 같은 이름의 필드를 선언하면, 부모의 필드를 "숨기는" 것이지 오버라이딩이 아니다.

### 예제 6: 필드 숨김 vs 메서드 오버라이딩 비교

```java
class Parent {
    int x = 100;

    int getX() {
        return x;
    }
}

class Child extends Parent {
    int x = 200;

    @Override
    int getX() {
        return x;
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        System.out.println(p.x);        // 100 (정적 바인딩 → Parent의 필드)
        System.out.println(p.getX());    // 200 (동적 바인딩 → Child의 메서드)
    }
}
```

**출력:**
```
100
200
```

### 바인딩 규칙 정리 (반드시 암기!)

| 대상 | 바인딩 | 기준 | 예시 |
|------|--------|------|------|
| **필드** | 정적 바인딩 | 선언 타입 (= 왼쪽) | `Parent p = new Child(); p.x → Parent의 x` |
| **인스턴스 메서드** | 동적 바인딩 | 실제 타입 (= 오른쪽) | `Parent p = new Child(); p.method() → Child의 method()` |
| **static 메서드** | 정적 바인딩 | 선언 타입 (= 왼쪽) | `Parent p = new Child(); p.staticMethod() → Parent의 것` |

### 예제 7: 복합 문제 (필드 + 메서드 혼합)

```java
class A {
    int x = 1;

    void print() {
        System.out.println("A: " + x);
    }
}

class B extends A {
    int x = 2;

    @Override
    void print() {
        System.out.println("B: " + x);
    }
}

class C extends B {
    int x = 3;

    @Override
    void print() {
        System.out.println("C: " + x);
    }
}

public class Main {
    public static void main(String[] args) {
        A obj1 = new C();
        B obj2 = new C();
        C obj3 = new C();

        System.out.println(obj1.x);  // 1 (A 타입 → A의 x)
        System.out.println(obj2.x);  // 2 (B 타입 → B의 x)
        System.out.println(obj3.x);  // 3 (C 타입 → C의 x)

        obj1.print();  // C: 3 (실제 객체 C → C의 print())
        obj2.print();  // C: 3 (실제 객체 C → C의 print())
        obj3.print();  // C: 3 (실제 객체 C → C의 print())
    }
}
```

**출력:**
```
1
2
3
C: 3
C: 3
C: 3
```

---

## 1.5 abstract 클래스

### 기본 개념

```java
abstract class Shape {
    String color = "red";

    // 추상 메서드: 구현부 없음 (자식이 반드시 구현)
    abstract double area();

    // 일반 메서드: 구현부 있음
    void display() {
        System.out.println("색상: " + color);
    }
}

class Circle extends Shape {
    double radius;

    Circle(double r) {
        this.radius = r;
    }

    @Override
    double area() {
        return 3.14 * radius * radius;
    }
}

public class Main {
    public static void main(String[] args) {
        // Shape s = new Shape(); ← 컴파일 에러! 추상 클래스는 인스턴스화 불가
        Shape s = new Circle(5);  // 부모 타입으로 참조 가능
        System.out.println(s.area());  // 78.5
        s.display();                    // 색상: red
    }
}
```

### abstract 핵심 규칙
- `abstract` 클래스는 `new`로 직접 생성 불가
- `abstract` 메서드는 구현부(`{}`)가 없어야 한다
- `abstract` 메서드가 하나라도 있으면 클래스도 `abstract`여야 한다
- 자식 클래스가 모든 추상 메서드를 구현하지 않으면 자식도 `abstract`여야 한다
- `abstract`와 `final`은 함께 쓸 수 없다 (모순!)
- `abstract`와 `private`은 함께 쓸 수 없다 (구현할 수 없으므로)

---

## 1.6 상속/다형성 킬러 문제 패턴 5선

### 킬러 1: 생성자 + 오버라이딩 + 필드 숨김 종합

```java
class Parent {
    int a = 10;

    Parent() {
        show();
    }

    void show() {
        System.out.println("Parent a=" + a);
    }
}

class Child extends Parent {
    int a = 20;
    int b = 30;

    Child() {
        show();
    }

    @Override
    void show() {
        System.out.println("Child a=" + a + " b=" + b);
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        System.out.println("p.a=" + p.a);
    }
}
```

**추적표:**

| 단계 | 위치 | 동작 | Child.a | Child.b | Parent.a |
|------|------|------|:-------:|:-------:|:--------:|
| 1 | new Child() | Child 생성자 진입 | 0 | 0 | 0 |
| 2 | super() | Parent 생성자 진입, Parent.a=10 초기화 | 0 | 0 | 10 |
| 3 | Parent() 안 show() | 동적 바인딩 → Child.show() | 0 | 0 | 10 |
| 4 | 출력 | `Child a=0 b=0` | 0 | 0 | 10 |
| 5 | Child 필드 초기화 | a=20, b=30 | 20 | 30 | 10 |
| 6 | Child() 안 show() | Child.show() | 20 | 30 | 10 |
| 7 | 출력 | `Child a=20 b=30` | 20 | 30 | 10 |
| 8 | p.a | 정적 바인딩 → Parent.a | - | - | 10 |
| 9 | 출력 | `p.a=10` | - | - | - |

**최종 출력:**
```
Child a=0 b=0
Child a=20 b=30
p.a=10
```

### 킬러 2: 캐스팅과 다형성

```java
class A {
    void m() { System.out.print("A "); }
}

class B extends A {
    void m() { System.out.print("B "); }
    void n() { System.out.print("Bn "); }
}

class C extends B {
    void m() { System.out.print("C "); }
}

public class Main {
    public static void main(String[] args) {
        A a = new C();
        a.m();              // C (동적 바인딩)
        // a.n();           // 컴파일 에러! A 타입에 n() 없음

        B b = (B) a;        // 다운캐스팅 (실제 C니까 안전)
        b.m();              // C (여전히 실제 객체는 C)
        b.n();              // Bn (B에 정의된 n())

        // A a2 = new A();
        // B b2 = (B) a2;   // ClassCastException! 실제 A는 B가 아님
    }
}
```

**출력:** `C C Bn`

### 킬러 3: super 키워드로 부모 메서드 호출

```java
class Parent {
    int x = 10;

    void display() {
        System.out.println("Parent: " + x);
    }
}

class Child extends Parent {
    int x = 20;

    @Override
    void display() {
        super.display();                    // 부모의 display() 호출
        System.out.println("Child: " + x);  // 자기 x
        System.out.println("Super.x: " + super.x);  // 부모의 x
    }
}

public class Main {
    public static void main(String[] args) {
        Child c = new Child();
        c.display();
    }
}
```

**출력:**
```
Parent: 10
Child: 20
Super.x: 10
```

### 킬러 4: 다단계 상속에서 생성자 체이닝

```java
class A {
    A(int x) {
        System.out.print(x + " ");
    }
}

class B extends A {
    B() {
        this(10);
        System.out.print("B() ");
    }

    B(int x) {
        super(x + 1);
        System.out.print("B(x) ");
    }
}

class C extends B {
    C() {
        System.out.print("C() ");
    }
}

public class Main {
    public static void main(String[] args) {
        C c = new C();
    }
}
```

**추적:**
```
new C()
  → C() 진입
    → super() → B() 진입
      → this(10) → B(10) 진입
        → super(11) → A(11) 진입
          → print("11 ")
        → print("B(x) ")
      → print("B() ")
    → print("C() ")
```

**출력:** `11 B(x) B() C()`

### 킬러 5: 인터페이스 + 추상 클래스 + 다형성

```java
interface Printable {
    void print();
}

abstract class Base implements Printable {
    int value = 10;

    abstract void show();

    void display() {
        System.out.println("Base display: " + value);
    }
}

class Derived extends Base {
    int value = 20;

    @Override
    public void print() {
        System.out.println("Print: " + value);
    }

    @Override
    void show() {
        System.out.println("Show: " + value);
    }

    @Override
    void display() {
        System.out.println("Derived display: " + value);
    }
}

public class Main {
    public static void main(String[] args) {
        Base b = new Derived();
        b.print();      // Print: 20 (동적 바인딩)
        b.show();       // Show: 20  (동적 바인딩)
        b.display();    // Derived display: 20 (동적 바인딩)
        System.out.println(b.value);  // 10 (정적 바인딩 → Base의 value)
    }
}
```

**출력:**
```
Print: 20
Show: 20
Derived display: 20
10
```

---

# 2장. 문자열 처리

## 2.1 String Pool과 == vs equals() (★★★)

### String 생성 방식 2가지

```java
// 방식 1: 리터럴 (String Pool 사용)
String s1 = "hello";
String s2 = "hello";
// s1과 s2는 Pool에서 같은 객체를 참조 → s1 == s2 → true

// 방식 2: new 연산자 (Heap에 별도 객체 생성)
String s3 = new String("hello");
String s4 = new String("hello");
// s3과 s4는 각각 다른 객체 → s3 == s4 → false
```

### 메모리 구조 이해

```
String Pool (상수 풀):
  ┌─────────┐
  │ "hello"  │ ← s1, s2 둘 다 여기를 가리킴
  └─────────┘

Heap:
  ┌─────────┐
  │ "hello"  │ ← s3이 가리킴
  └─────────┘
  ┌─────────┐
  │ "hello"  │ ← s4가 가리킴
  └─────────┘
```

### 비교 연산 완벽 정리

```java
String a = "hello";
String b = "hello";
String c = new String("hello");
String d = new String("hello");

// == : 참조(주소) 비교
System.out.println(a == b);      // true  (같은 Pool 객체)
System.out.println(a == c);      // false (Pool vs Heap)
System.out.println(c == d);      // false (서로 다른 Heap 객체)

// equals() : 내용(값) 비교
System.out.println(a.equals(b)); // true
System.out.println(a.equals(c)); // true
System.out.println(c.equals(d)); // true
```

### 시험에 자주 나오는 변형

```java
String s1 = "ab" + "c";   // 컴파일 타임에 "abc"로 최적화 → Pool
String s2 = "abc";
System.out.println(s1 == s2);  // true (둘 다 Pool의 "abc")

String x = "ab";
String s3 = x + "c";      // 변수 + 리터럴은 런타임에 새 객체 생성
System.out.println(s2 == s3);  // false
System.out.println(s2.equals(s3));  // true
```

---

## 2.2 split() 메서드

```java
String str = "apple,banana,cherry";
String[] arr = str.split(",");
// arr[0] = "apple", arr[1] = "banana", arr[2] = "cherry"
System.out.println(arr.length);  // 3

// 구분자가 여러 문자일 때
String str2 = "a-b--c";
String[] arr2 = str2.split("-");
// arr2 = {"a", "b", "", "c"}  ← 빈 문자열 주의!
System.out.println(arr2.length);  // 4

// limit 매개변수
String str3 = "1:2:3:4:5";
String[] arr3 = str3.split(":", 3);
// arr3 = {"1", "2", "3:4:5"}  ← 최대 3개로 분할
```

### 정규식 주의 (. 은 모든 문자를 의미)

```java
String ip = "192.168.0.1";
// 잘못된 방법:
String[] wrong = ip.split(".");   // 빈 배열! (.은 정규식에서 "아무 문자")
// 올바른 방법:
String[] right = ip.split("\\.");  // {"192", "168", "0", "1"}
```

---

## 2.3 Integer.valueOf()와 parseInt()

```java
// parseInt: String → int (기본형)
int a = Integer.parseInt("123");
System.out.println(a + 1);  // 124

// valueOf: String → Integer (래퍼 객체)
Integer b = Integer.valueOf("123");
System.out.println(b + 1);  // 124 (오토 언박싱)

// valueOf의 캐싱 (-128 ~ 127)
Integer x = Integer.valueOf(100);
Integer y = Integer.valueOf(100);
System.out.println(x == y);  // true (캐시 범위 내)

Integer p = Integer.valueOf(200);
Integer q = Integer.valueOf(200);
System.out.println(p == q);  // false (캐시 범위 밖)
System.out.println(p.equals(q));  // true
```

---

## 2.4 문자열 연결과 타입 변환

### 핵심: 문자열 + 정수 = 문자열 (왼쪽에서 오른쪽으로 평가)

```java
System.out.println("결과: " + 3 + 4);   // "결과: 34" (문자열 연결)
System.out.println("결과: " + (3 + 4)); // "결과: 7"  (괄호 먼저)
System.out.println(3 + 4 + "결과");     // "7결과"    (3+4=7 먼저, 그 후 문자열 연결)
System.out.println(1 + 2 + "a" + 3 + 4); // "3a34"
```

**평가 과정 (`1 + 2 + "a" + 3 + 4`):**
```
1 + 2 = 3           (int + int = int)
3 + "a" = "3a"      (int + String = String)
"3a" + 3 = "3a3"    (String + int = String)
"3a3" + 4 = "3a34"  (String + int = String)
```

### 문자열의 불변성 (Immutability)

```java
String s = "Hello";
s.concat(" World");
System.out.println(s);  // "Hello" ← 원본 안 바뀜!

s = s.concat(" World");
System.out.println(s);  // "Hello World" ← 새 객체를 다시 할당해야 함
```

---

## 2.5 문자열 출제 패턴

```java
// charAt()
String s = "Hello";
System.out.println(s.charAt(1));  // 'e'

// substring()
System.out.println(s.substring(1, 3));  // "el" (인덱스 1~2)

// length()
System.out.println(s.length());  // 5

// toUpperCase(), toLowerCase()
System.out.println("hello".toUpperCase());  // "HELLO"

// trim()
System.out.println("  hi  ".trim());  // "hi"

// replace()
System.out.println("aabba".replace("a", "x"));  // "xxbbx"

// contains()
System.out.println("Hello World".contains("World"));  // true

// indexOf()
System.out.println("Hello".indexOf('l'));  // 2 (처음 나오는 위치)
```

---

# 3장. 예외 처리

## 3.1 try-catch-finally 구조

```java
try {
    // 예외가 발생할 수 있는 코드
    int result = 10 / 0;
    System.out.println("이 줄은 실행 안 됨");
} catch (ArithmeticException e) {
    // 예외 처리
    System.out.println("0으로 나눌 수 없습니다");
} finally {
    // 예외 발생 여부와 관계없이 항상 실행
    System.out.println("finally 실행!");
}
```

**출력:**
```
0으로 나눌 수 없습니다
finally 실행!
```

### 다중 catch

```java
try {
    int[] arr = new int[3];
    arr[5] = 10;  // ArrayIndexOutOfBoundsException
} catch (ArithmeticException e) {
    System.out.println("산술 예외");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("배열 인덱스 예외");
} catch (Exception e) {
    System.out.println("기타 예외");
} finally {
    System.out.println("종료");
}
```

**출력:**
```
배열 인덱스 예외
종료
```

> **주의:** 상위 예외 클래스(`Exception`)를 먼저 catch하면 컴파일 에러! 반드시 하위 → 상위 순서로 작성해야 한다.

---

## 3.2 finally는 무조건 실행! (★★★)

### return이 있어도 finally가 실행된다!

```java
public class Main {
    static int test() {
        try {
            System.out.println("try");
            return 1;
        } catch (Exception e) {
            System.out.println("catch");
            return 2;
        } finally {
            System.out.println("finally");
            // return 3;  ← 이렇게 쓰면 try/catch의 return을 덮어씀 (비권장)
        }
    }

    public static void main(String[] args) {
        int result = test();
        System.out.println("result = " + result);
    }
}
```

**출력:**
```
try
finally
result = 1
```

**순서:** try 블록 실행 → return 값(1) 준비 → finally 실행 → return 1 반환

### finally에서 return하면? (킬러!)

```java
static int test() {
    try {
        return 1;
    } finally {
        return 3;  // try의 return 1을 덮어씀!
    }
}
// test() → 3
```

### 예외 발생 + finally

```java
public class Main {
    public static void main(String[] args) {
        int a = 0;
        try {
            a = 1;
            int b = 10 / 0;    // ArithmeticException!
            a = 2;              // 실행 안 됨
        } catch (ArithmeticException e) {
            a = 3;
        } finally {
            a = 4;
        }
        System.out.println(a);  // 4
    }
}
```

**추적:**
```
a = 0
try: a = 1
예외 발생 → catch로 이동
catch: a = 3
finally: a = 4
출력: 4
```

---

## 3.3 예외 클래스 계층도

```
Throwable
├── Error (시스템 레벨, 복구 불가)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── ...
└── Exception
    ├── RuntimeException (Unchecked Exception - 컴파일러 체크 안 함)
    │   ├── ArithmeticException (0으로 나누기)
    │   ├── NullPointerException (null 참조)
    │   ├── ArrayIndexOutOfBoundsException (배열 인덱스 초과)
    │   ├── ClassCastException (잘못된 캐스팅)
    │   ├── NumberFormatException (숫자 변환 실패)
    │   └── StringIndexOutOfBoundsException
    └── Checked Exception (컴파일러가 체크 - 반드시 처리해야 함)
        ├── IOException
        ├── FileNotFoundException
        ├── SQLException
        └── ClassNotFoundException
```

### 시험에 자주 나오는 예외

| 예외 | 발생 상황 | 예시 |
|------|----------|------|
| `ArithmeticException` | 0으로 나누기 | `10 / 0` |
| `NullPointerException` | null 객체의 메서드 호출 | `null.length()` |
| `ArrayIndexOutOfBoundsException` | 배열 범위 초과 | `arr[10]` (크기 5) |
| `ClassCastException` | 잘못된 다운캐스팅 | `(Child) new Parent()` |
| `NumberFormatException` | 문자열→숫자 변환 실패 | `Integer.parseInt("abc")` |
| `StackOverflowError` | 무한 재귀 | 종료 조건 없는 재귀 |

---

## 3.4 throw와 throws

### throw: 예외를 직접 발생시킴

```java
void checkAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("나이는 음수일 수 없습니다");
    }
    System.out.println("나이: " + age);
}
```

### throws: 메서드가 예외를 던질 수 있음을 선언

```java
void readFile(String path) throws IOException {
    // IOException이 발생할 수 있는 코드
    // 이 메서드를 호출하는 쪽에서 처리해야 함
}
```

### throw와 throws 비교

| 구분 | throw | throws |
|------|-------|--------|
| 위치 | 메서드 내부 | 메서드 선언부 |
| 역할 | 예외 발생 | 예외 전파 선언 |
| 개수 | 하나의 예외 | 여러 예외 가능 (콤마 구분) |
| 예시 | `throw new Exception()` | `void m() throws IOException, SQLException` |

---

## 3.5 사용자 정의 예외

```java
class AgeException extends Exception {
    AgeException(String message) {
        super(message);
    }
}

class Person {
    void setAge(int age) throws AgeException {
        if (age < 0) {
            throw new AgeException("음수 나이: " + age);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        try {
            p.setAge(-5);
        } catch (AgeException e) {
            System.out.println(e.getMessage());  // 음수 나이: -5
        }
    }
}
```

---

## 3.6 예외처리 출제 패턴

### 패턴 1: try-catch-finally 실행 순서 추적

```java
public class Main {
    public static void main(String[] args) {
        System.out.print("1 ");
        try {
            System.out.print("2 ");
            int x = 10 / 0;
            System.out.print("3 ");
        } catch (ArithmeticException e) {
            System.out.print("4 ");
        } catch (Exception e) {
            System.out.print("5 ");
        } finally {
            System.out.print("6 ");
        }
        System.out.print("7 ");
    }
}
```

**출력:** `1 2 4 6 7`

### 패턴 2: 메서드 호출 중 예외

```java
public class Main {
    static int calc() {
        int a = 10;
        try {
            a += 10;         // a = 20
            return a;        // 20을 반환 준비
        } finally {
            a += 100;        // a = 120이 되지만...
            System.out.print("finally ");
        }
    }

    public static void main(String[] args) {
        System.out.print(calc());
    }
}
```

**출력:** `finally 20`

> **주의:** finally에서 `a`를 변경해도, return에서 이미 준비된 값(20)이 반환된다. 단, `a`가 객체(참조 타입)라면 내용이 변경될 수 있다.

---

# 4장. 배열과 참조 전달

## 4.1 배열은 참조 타입

```java
int[] a = {1, 2, 3};
int[] b = a;          // 같은 배열을 참조!
b[0] = 100;
System.out.println(a[0]);  // 100 (a와 b는 같은 배열)
System.out.println(a == b); // true (같은 참조)

int[] c = {1, 2, 3};
int[] d = {1, 2, 3};
System.out.println(c == d); // false (다른 배열 객체)
```

---

## 4.2 함수에 배열 전달 -> 원본 변경됨

```java
public class Main {
    static void modify(int[] arr) {
        arr[0] = 999;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        modify(nums);
        System.out.println(nums[0]);  // 999 (원본이 변경됨!)
    }
}
```

**이유:** 배열은 참조 타입이므로, 메서드에 전달할 때 참조(주소)가 복사된다. 메서드 내에서 해당 주소의 내용을 변경하면 원본도 변경된다.

### 배열 참조 자체를 변경하는 경우

```java
static void reassign(int[] arr) {
    arr = new int[]{100, 200, 300};  // 새 배열로 재할당 (지역 변수만 변경)
}

public static void main(String[] args) {
    int[] nums = {1, 2, 3};
    reassign(nums);
    System.out.println(nums[0]);  // 1 (원본 안 바뀜!)
}
```

> **핵심:** 배열의 **내용**을 변경하면 원본에 반영되지만, 배열 **참조 자체**를 재할당하면 원본에 영향 없음.

---

## 4.3 함수에 String 전달 -> 원본 변경 안됨

```java
static void modify(String s) {
    s = "변경됨";  // 새 String 객체를 지역 변수에 할당
}

public static void main(String[] args) {
    String str = "원본";
    modify(str);
    System.out.println(str);  // "원본" (변경 안 됨!)
}
```

**이유:** String은 불변(immutable) 객체. `s = "변경됨"`은 새로운 String 객체를 지역 변수 `s`에 할당하는 것이지, 기존 객체를 변경하는 것이 아니다.

---

## 4.4 참조 변수 교환의 함정

```java
static void swap(int[] a, int[] b) {
    int[] temp = a;
    a = b;
    b = temp;
    // 지역 변수만 교환됨! 원본에 영향 없음
}

public static void main(String[] args) {
    int[] x = {1, 2};
    int[] y = {3, 4};
    swap(x, y);
    System.out.println(x[0] + " " + y[0]);  // 1 3 (교환 안 됨!)
}
```

### 실제로 내용을 교환하려면?

```java
static void swapContent(int[] a, int[] b) {
    int temp = a[0];
    a[0] = b[0];
    b[0] = temp;
}

public static void main(String[] args) {
    int[] x = {1, 2};
    int[] y = {3, 4};
    swapContent(x, y);
    System.out.println(x[0] + " " + y[0]);  // 3 1 (내용이 교환됨!)
}
```

---

# 5장. static 키워드

## 5.1 static 변수 (클래스 변수)

```java
class Counter {
    static int count = 0;  // 모든 인스턴스가 공유
    int id;

    Counter() {
        count++;
        id = count;
    }
}

public class Main {
    public static void main(String[] args) {
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();

        System.out.println(Counter.count);  // 3
        System.out.println(c1.count);       // 3 (같은 변수)
        System.out.println(c1.id);          // 1
        System.out.println(c2.id);          // 2
        System.out.println(c3.id);          // 3
    }
}
```

### 메모리 구조

```
메서드 영역 (공유):
  Counter.count = 3

힙 영역:
  c1 → { id: 1 }
  c2 → { id: 2 }
  c3 → { id: 3 }
```

---

## 5.2 static 메서드

```java
class MathUtil {
    static int add(int a, int b) {
        return a + b;
    }

    int value = 10;

    static void test() {
        // System.out.println(value);  ← 컴파일 에러! static에서 인스턴스 변수 접근 불가
        // display();                   ← 컴파일 에러! static에서 인스턴스 메서드 호출 불가
        System.out.println("static method");
    }

    void display() {
        System.out.println(value);      // OK
        System.out.println(add(1, 2));  // OK (인스턴스에서 static 접근 가능)
    }
}
```

### static 메서드 규칙

| 접근 방향 | 가능 여부 |
|-----------|-----------|
| static → static 변수/메서드 | O |
| static → 인스턴스 변수/메서드 | **X (컴파일 에러!)** |
| 인스턴스 → static 변수/메서드 | O |
| 인스턴스 → 인스턴스 변수/메서드 | O |

---

## 5.3 후위/전위 증감과 static (★★)

```java
class Test {
    static int a = 0;
    static int b = 0;
}

public class Main {
    public static void main(String[] args) {
        Test.a = 5;
        Test.b = Test.a++;  // 후위 증가: b에 5 대입 후, a를 6으로 증가
        System.out.println("a=" + Test.a + " b=" + Test.b);  // a=6 b=5

        Test.a = 5;
        Test.b = ++Test.a;  // 전위 증가: a를 6으로 증가 후, b에 6 대입
        System.out.println("a=" + Test.a + " b=" + Test.b);  // a=6 b=6
    }
}
```

### 후위/전위 증감 정리

| 연산 | 동작 | 예시 (x=5) | 결과 |
|------|------|-----------|------|
| `y = x++` | 대입 먼저, 증가 나중 | y에 5 대입, x는 6 | x=6, y=5 |
| `y = ++x` | 증가 먼저, 대입 나중 | x를 6으로, y에 6 대입 | x=6, y=6 |
| `y = x--` | 대입 먼저, 감소 나중 | y에 5 대입, x는 4 | x=4, y=5 |
| `y = --x` | 감소 먼저, 대입 나중 | x를 4로, y에 4 대입 | x=4, y=4 |

### 복합 예제

```java
class Num {
    static int x = 10;
}

public class Main {
    public static void main(String[] args) {
        Num.x = 10;
        int a = Num.x++ + Num.x++;
        // 첫 번째 Num.x++: 값 10 사용, x→11
        // 두 번째 Num.x++: 값 11 사용, x→12
        // a = 10 + 11 = 21
        System.out.println("a=" + a + " x=" + Num.x);  // a=21 x=12
    }
}
```

---

## 5.4 static 메서드에서 인스턴스 변수 접근 불가

```java
class Example {
    int instanceVar = 10;
    static int staticVar = 20;

    // 컴파일 에러 발생!
    // static void errorMethod() {
    //     System.out.println(instanceVar);  // 에러!
    //     instanceMethod();                  // 에러!
    // }

    static void staticMethod() {
        System.out.println(staticVar);  // OK
        // 인스턴스 변수에 접근하려면 객체를 생성해야 함
        Example obj = new Example();
        System.out.println(obj.instanceVar);  // OK
    }

    void instanceMethod() {
        System.out.println(instanceVar);  // OK
        System.out.println(staticVar);    // OK
    }
}
```

---

# 6장. 인터페이스와 제네릭

## 6.1 인터페이스 기본

```java
interface Flyable {
    // 상수 (public static final 자동)
    int MAX_HEIGHT = 10000;

    // 추상 메서드 (public abstract 자동)
    void fly();

    // default 메서드 (Java 8+)
    default void land() {
        System.out.println("착륙");
    }

    // static 메서드 (Java 8+)
    static void info() {
        System.out.println("비행 인터페이스");
    }
}

class Bird implements Flyable {
    @Override
    public void fly() {  // 반드시 public!
        System.out.println("새가 날아갑니다");
    }
}

public class Main {
    public static void main(String[] args) {
        Bird b = new Bird();
        b.fly();     // 새가 날아갑니다
        b.land();    // 착륙 (default 메서드)
        Flyable.info();  // 비행 인터페이스 (static 메서드)
        System.out.println(Flyable.MAX_HEIGHT);  // 10000
    }
}
```

---

## 6.2 인터페이스 다중 구현

```java
interface Swimmable {
    void swim();
}

interface Flyable {
    void fly();
}

class Duck implements Swimmable, Flyable {
    @Override
    public void swim() {
        System.out.println("수영");
    }

    @Override
    public void fly() {
        System.out.println("비행");
    }
}

public class Main {
    public static void main(String[] args) {
        Duck d = new Duck();
        d.swim();  // 수영
        d.fly();   // 비행

        // 인터페이스 타입으로 참조 가능
        Swimmable s = new Duck();
        s.swim();
        // s.fly();  ← 컴파일 에러! Swimmable에 fly() 없음
    }
}
```

### 인터페이스 vs 추상 클래스

| 구분 | 인터페이스 | 추상 클래스 |
|------|-----------|------------|
| 키워드 | interface | abstract class |
| 다중 상속 | 가능 (implements 여러 개) | 불가 (extends 하나만) |
| 생성자 | 없음 | 있음 |
| 변수 | public static final만 | 모든 종류 가능 |
| 메서드 | public abstract (기본) | 모든 접근 제어자 |
| 인스턴스화 | 불가 | 불가 |

---

## 6.3 제네릭과 타입 소거 (★★)

### 타입 소거(Type Erasure)란?

Java 제네릭은 **컴파일 시에만** 존재하고, 런타임에는 제거된다. `T`는 `Object`로 대체된다.

```java
class Box<T> {
    T value;

    void set(T value) {
        this.value = value;
    }

    T get() {
        return value;
    }
}

// 컴파일 후 (타입 소거):
// class Box {
//     Object value;
//     void set(Object value) { this.value = value; }
//     Object get() { return value; }
// }
```

### 시험 출제 포인트: 오버로딩과 타입 소거

```java
class Printer {
    void print(Object obj) {
        System.out.println("Object: " + obj);
    }

    void print(String str) {
        System.out.println("String: " + str);
    }

    void print(Integer num) {
        System.out.println("Integer: " + num);
    }
}

class GenericTest<T> {
    Printer printer = new Printer();

    void test(T value) {
        printer.print(value);  // T는 Object로 소거됨!
    }
}

public class Main {
    public static void main(String[] args) {
        GenericTest<String> gt = new GenericTest<>();
        gt.test("hello");
        // 출력: Object: hello
        // (String 버전이 아닌 Object 버전이 호출됨!)
    }
}
```

> **핵심:** 제네릭 타입 T는 컴파일 시 Object로 처리되므로, 오버로딩된 메서드 중 `Object` 매개변수 버전이 호출된다.

---

# 7장. enum (열거형)

## 7.1 enum 선언과 사용

```java
enum Season {
    SPRING, SUMMER, FALL, WINTER
}

public class Main {
    public static void main(String[] args) {
        Season s = Season.SUMMER;
        System.out.println(s);           // SUMMER
        System.out.println(s.name());    // SUMMER (문자열 반환)
        System.out.println(s.ordinal()); // 1 (인덱스, 0부터 시작)
    }
}
```

---

## 7.2 values(), name(), ordinal()

```java
enum Color {
    RED, GREEN, BLUE
}

public class Main {
    public static void main(String[] args) {
        // values(): 모든 열거 상수를 배열로 반환
        Color[] colors = Color.values();
        for (Color c : colors) {
            System.out.println(c.name() + " - " + c.ordinal());
        }
        // RED - 0
        // GREEN - 1
        // BLUE - 2

        System.out.println(colors.length);  // 3

        // valueOf(): 문자열로 열거 상수 찾기
        Color c = Color.valueOf("RED");
        System.out.println(c);  // RED
    }
}
```

---

## 7.3 생성자가 있는 enum

```java
enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6);

    private final double mass;
    private final double radius;

    // enum 생성자는 private (생략 가능, 기본이 private)
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    double getMass() { return mass; }
    double getRadius() { return radius; }
}

public class Main {
    public static void main(String[] args) {
        Planet p = Planet.EARTH;
        System.out.println(p.name());       // EARTH
        System.out.println(p.getMass());     // 5.976E24
        System.out.println(p.ordinal());    // 2
    }
}
```

### enum에서 자주 나오는 패턴

```java
enum Direction {
    UP("위"), DOWN("아래"), LEFT("왼"), RIGHT("오른");

    private String korean;

    Direction(String korean) {
        this.korean = korean;
    }

    String getKorean() { return korean; }
}

public class Main {
    public static void main(String[] args) {
        for (Direction d : Direction.values()) {
            System.out.println(d.name() + " = " + d.getKorean());
        }
        // UP = 위
        // DOWN = 아래
        // LEFT = 왼
        // RIGHT = 오른
    }
}
```

---

# 8장. 람다식과 함수형 인터페이스

## 8.1 함수형 인터페이스 (@FunctionalInterface)

**함수형 인터페이스:** 추상 메서드가 **딱 하나**인 인터페이스

```java
@FunctionalInterface
interface Calculator {
    int calc(int a, int b);
    // int another(int x);  ← 추가하면 컴파일 에러! (추상 메서드 2개)
}
```

### 주요 내장 함수형 인터페이스

| 인터페이스 | 메서드 | 설명 |
|-----------|--------|------|
| `Runnable` | `void run()` | 매개변수 없음, 반환값 없음 |
| `Supplier<T>` | `T get()` | 매개변수 없음, 반환값 있음 |
| `Consumer<T>` | `void accept(T)` | 매개변수 있음, 반환값 없음 |
| `Function<T,R>` | `R apply(T)` | 매개변수 있음, 반환값 있음 |
| `Predicate<T>` | `boolean test(T)` | 매개변수 있음, boolean 반환 |

---

## 8.2 람다 표현식 문법

### 기본 문법

```java
// 기존 익명 클래스 방식
Calculator c1 = new Calculator() {
    @Override
    public int calc(int a, int b) {
        return a + b;
    }
};

// 람다식 방식
Calculator c2 = (a, b) -> a + b;

// 사용
System.out.println(c1.calc(3, 4));  // 7
System.out.println(c2.calc(3, 4));  // 7
```

### 람다식 변형

```java
@FunctionalInterface
interface Greeting {
    void greet(String name);
}

// 매개변수 1개일 때 괄호 생략 가능
Greeting g1 = name -> System.out.println("Hello, " + name);

// 본문이 여러 줄일 때 중괄호 사용
Greeting g2 = name -> {
    String msg = "Hello, " + name;
    System.out.println(msg);
};

// 매개변수 없을 때
Runnable r = () -> System.out.println("실행!");
r.run();  // 실행!
```

### 반환값이 있는 람다

```java
@FunctionalInterface
interface StringFunc {
    String process(String s);
}

// 한 줄이면 return 생략 가능
StringFunc upper = s -> s.toUpperCase();

// 여러 줄이면 return 명시
StringFunc reverse = s -> {
    StringBuilder sb = new StringBuilder(s);
    return sb.reverse().toString();
};

System.out.println(upper.process("hello"));    // HELLO
System.out.println(reverse.process("hello"));  // olleh
```

---

## 8.3 예외가 포함된 람다

```java
@FunctionalInterface
interface Divider {
    int divide(int a, int b);
}

public class Main {
    public static void main(String[] args) {
        Divider d = (a, b) -> {
            if (b == 0) throw new ArithmeticException("0으로 나눌 수 없음");
            return a / b;
        };

        try {
            System.out.println(d.divide(10, 2));  // 5
            System.out.println(d.divide(10, 0));  // 예외 발생!
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());    // 0으로 나눌 수 없음
        }
    }
}
```

### Checked 예외를 던지는 함수형 인터페이스

```java
// 직접 정의해야 함 (기본 함수형 인터페이스는 checked 예외 불가)
@FunctionalInterface
interface FileReader {
    String read(String path) throws IOException;
}
```

---

# 9장. 실전 코드 트레이싱 비법

## 9.1 상속 문제 풀이 4단계

### 단계 1: 클래스 계층 파악
문제의 코드를 보고 상속 관계를 그림으로 그린다.

```
A (부모)
 └── B (자식)
      └── C (손자)
```

### 단계 2: 생성자 호출 순서 추적
`new`가 보이면 생성자 호출 체인을 따라간다.
- `super()`가 있으면 해당 부모 생성자로
- `this()`가 있으면 같은 클래스 다른 생성자로
- 아무것도 없으면 `super()` 자동 삽입

### 단계 3: 동적/정적 바인딩 구분

```
변수 접근 (a.x)     → 선언 타입! (왼쪽)
메서드 호출 (a.m())  → 실제 타입! (오른쪽)
```

이 규칙을 코드 옆에 적어가며 풀어라.

### 단계 4: 변수 추적표 작성

| 단계 | 코드 | 변수A | 변수B | 출력 |
|------|------|-------|-------|------|
| 1 | ... | 0 | 0 | |
| 2 | ... | 10 | 0 | |
| ... | | | | |

> **절대로 머릿속으로만 추적하지 말 것!** 반드시 종이에 표를 그려라.

---

## 9.2 static 변수 추적법

static 변수는 **하나만 존재**한다. 모든 객체가 공유한다는 점을 기억하며 추적한다.

```java
class Obj {
    static int s = 0;
    int n = 0;

    Obj() {
        s++;
        n++;
        System.out.println("s=" + s + " n=" + n);
    }
}

public class Main {
    public static void main(String[] args) {
        Obj a = new Obj();  // s=1 n=1
        Obj b = new Obj();  // s=2 n=1
        Obj c = new Obj();  // s=3 n=1
    }
}
```

**추적표:**

| 단계 | Obj.s (공유) | a.n | b.n | c.n |
|------|:-----------:|:---:|:---:|:---:|
| 초기 | 0 | - | - | - |
| new Obj() (a) | 1 | 1 | - | - |
| new Obj() (b) | 2 | 1 | 1 | - |
| new Obj() (c) | 3 | 1 | 1 | 1 |

---

## 9.3 참조 변수 그림 그리기

참조 문제가 나오면 반드시 메모리 그림을 그려라.

```java
int[] a = {10, 20, 30};
int[] b = a;
b[1] = 99;

// 그림:
// a ──┐
//     ├──→ [10, 99, 30]
// b ──┘

int[] c = {10, 20, 30};
int[] d = {10, 20, 30};

// 그림:
// c ──→ [10, 20, 30]  (주소 100)
// d ──→ [10, 20, 30]  (주소 200)
// c == d → false (주소가 다름!)
```

---

# 10장. 예상 문제 10선

---

## 문제 1. 생성자 호출 순서 (난이도: 중)

다음 코드의 출력 결과를 쓰시오.

```java
class A {
    A() { System.out.print("A "); }
    A(int x) { System.out.print("A" + x + " "); }
}

class B extends A {
    B() {
        this(10);
        System.out.print("B ");
    }
    B(int x) {
        super(x);
        System.out.print("B" + x + " ");
    }
}

public class Main {
    public static void main(String[] args) {
        B b = new B();
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:** `A10 B10 B`

**풀이:**
1. `new B()` → `B()` 진입
2. `this(10)` → `B(10)` 진입
3. `super(10)` → `A(10)` 진입 → 출력: `A10 `
4. `B(10)` 나머지 → 출력: `B10 `
5. `B()` 나머지 → 출력: `B `
</details>

---

## 문제 2. 동적 바인딩 + 필드 숨김 (난이도: 상)

다음 코드의 출력 결과를 쓰시오.

```java
class Parent {
    int x = 10;
    void display() {
        System.out.println(x);
    }
}

class Child extends Parent {
    int x = 20;
    void display() {
        System.out.println(x);
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        System.out.println(p.x);
        p.display();

        Child c = (Child) p;
        System.out.println(c.x);
        c.display();
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:**
```
10
20
20
20
```

**풀이:**
- `p.x` → 정적 바인딩, Parent 타입이므로 Parent.x = **10**
- `p.display()` → 동적 바인딩, 실제 객체 Child의 display() → Child.x = **20**
- `c.x` → 정적 바인딩, Child 타입이므로 Child.x = **20**
- `c.display()` → 동적 바인딩, 실제 객체 Child의 display() → Child.x = **20**
</details>

---

## 문제 3. try-catch-finally (난이도: 중)

다음 코드의 출력 결과를 쓰시오.

```java
public class Main {
    static String test() {
        String result = "";
        try {
            result += "T";
            int x = 10 / 0;
            result += "R";
        } catch (ArithmeticException e) {
            result += "C";
            return result;
        } finally {
            result += "F";
        }
        result += "E";
        return result;
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:** `TC`

**풀이:**
1. try: result = "T", 예외 발생
2. catch: result = "TC", `return result` 실행 준비 (반환값 "TC" 저장)
3. finally: result = "TCF" (지역 변수가 변경되지만, 이미 반환할 값은 "TC"로 결정됨)
4. catch의 return이 실행 → **"TC"** 반환

> **주의:** String은 불변 객체이므로 `result += "F"`는 새로운 String 객체를 만든다. catch에서 return 시 이미 "TC" 값이 반환 대상으로 저장되어 있으므로 finally에서의 변경이 반영되지 않는다.
</details>

---

## 문제 4. static 변수 추적 (난이도: 중)

다음 코드의 출력 결과를 쓰시오.

```java
class Num {
    static int a = 10;
    static int b = 20;
}

public class Main {
    public static void main(String[] args) {
        Num n1 = new Num();
        Num n2 = new Num();

        n1.a += 10;
        n2.a += 20;
        n1.b = n2.b++ + ++n1.a;

        System.out.println(Num.a + " " + Num.b);
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:** `41 60`

**풀이:**

| 단계 | Num.a | Num.b | 설명 |
|------|:-----:|:-----:|------|
| 초기 | 10 | 20 | |
| n1.a += 10 | 20 | 20 | a = 10 + 10 |
| n2.a += 20 | 40 | 20 | a = 20 + 20 |
| n2.b++ | 40 | 20→21 | 후위: 값 20 사용, 그 후 b=21 |
| ++n1.a | 41 | 21 | 전위: a=41, 값 41 사용 |
| n1.b = 20 + 41 | 41 | 61 | b = 61... |

잠깐, 다시 추적하겠습니다.

`n1.b = n2.b++ + ++n1.a;` 를 단계적으로:
1. `n2.b++`: 현재 Num.b=20, 값 20을 사용하고 Num.b를 21로 증가
2. `++n1.a`: 현재 Num.a=40, Num.a를 41로 증가하고 값 41을 사용
3. `n1.b = 20 + 41 = 61`: Num.b = 61 (21은 즉시 61로 덮어씌워짐)

최종: Num.a = **41**, Num.b = **61**

**출력:** `41 61`
</details>

---

## 문제 5. 배열 참조 전달 (난이도: 중)

다음 코드의 출력 결과를 쓰시오.

```java
public class Main {
    static void change(int[] arr, int val) {
        arr[0] = 100;
        val = 200;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        int x = 10;
        change(nums, x);
        System.out.println(nums[0] + " " + x);
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:** `100 10`

**풀이:**
- `arr[0] = 100`: 배열은 참조 전달이므로 원본 변경 → nums[0] = **100**
- `val = 200`: 기본형(int)은 값 복사이므로 원본 x에 영향 없음 → x = **10**
</details>

---

## 문제 6. String == vs equals() (난이도: 중)

다음 코드의 출력 결과를 쓰시오.

```java
public class Main {
    public static void main(String[] args) {
        String a = "Java";
        String b = "Java";
        String c = new String("Java");
        String d = c.intern();

        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a == d);
        System.out.println(a.equals(c));
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:**
```
true
false
true
true
```

**풀이:**
- `a == b`: 둘 다 String Pool의 "Java" → **true**
- `a == c`: a는 Pool, c는 Heap 별도 객체 → **false**
- `a == d`: `intern()`은 Pool의 객체를 반환 → **true**
- `a.equals(c)`: 내용 비교 → **true**
</details>

---

## 문제 7. 부모 생성자에서의 동적 바인딩 (난이도: 최상 - 킬러!)

다음 코드의 출력 결과를 쓰시오.

```java
class Base {
    int val = 100;

    Base() {
        calc();
    }

    void calc() {
        val += 10;
    }
}

class Sub extends Base {
    int val = 200;

    Sub() {
        calc();
    }

    @Override
    void calc() {
        val += 20;
    }
}

public class Main {
    public static void main(String[] args) {
        Base obj = new Sub();
        System.out.println(obj.val);
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:** `100`

**풀이:**

| 단계 | Base.val | Sub.val | 설명 |
|------|:--------:|:-------:|------|
| 1 | 0 | 0 | 객체 메모리 할당, 기본값 |
| 2 | 100 | 0 | Base 필드 초기화: Base.val = 100 |
| 3 | 100 | 0 | Base() 생성자에서 calc() 호출 → 동적 바인딩 → Sub.calc() |
| 4 | 100 | 20 | Sub.calc(): Sub.val = 0 + 20 = 20 |
| 5 | 100 | 200 | Sub 필드 초기화: Sub.val = 200 |
| 6 | 100 | 220 | Sub() 생성자에서 calc() → Sub.calc(): Sub.val = 200 + 20 = 220 |
| 7 | 100 | 220 | `obj.val` → 정적 바인딩 → Base 타입이므로 Base.val = **100** |

</details>

---

## 문제 8. enum 기본 (난이도: 하)

다음 코드의 출력 결과를 쓰시오.

```java
enum Grade {
    A(4.5), B(3.5), C(2.5);

    private double point;

    Grade(double point) {
        this.point = point;
    }

    double getPoint() { return point; }
}

public class Main {
    public static void main(String[] args) {
        Grade g = Grade.B;
        System.out.println(g.name() + " " + g.getPoint() + " " + g.ordinal());
        System.out.println(Grade.values().length);
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:**
```
B 3.5 1
3
```

**풀이:**
- `g.name()` → "B"
- `g.getPoint()` → 3.5
- `g.ordinal()` → 1 (0부터 시작: A=0, B=1, C=2)
- `Grade.values().length` → 3 (A, B, C 총 3개)
</details>

---

## 문제 9. 람다식 + 함수형 인터페이스 (난이도: 중)

다음 코드의 출력 결과를 쓰시오.

```java
@FunctionalInterface
interface Calc {
    int operate(int a, int b);
}

public class Main {
    static void execute(int x, int y, Calc c) {
        System.out.println(c.operate(x, y));
    }

    public static void main(String[] args) {
        Calc add = (a, b) -> a + b;
        Calc mul = (a, b) -> a * b;

        execute(3, 4, add);
        execute(3, 4, mul);
        execute(10, 3, (a, b) -> a % b);
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:**
```
7
12
1
```

**풀이:**
- `add.operate(3, 4)` → 3 + 4 = **7**
- `mul.operate(3, 4)` → 3 * 4 = **12**
- 인라인 람다 `(a, b) -> a % b` → 10 % 3 = **1**
</details>

---

## 문제 10. 종합 문제 - 상속 + static + 생성자 (난이도: 최상)

다음 코드의 출력 결과를 쓰시오.

```java
class Parent {
    static int count = 0;
    int id;

    Parent() {
        count++;
        id = count;
        System.out.println("P" + id);
    }

    void show() {
        System.out.println("Parent id=" + id);
    }
}

class Child extends Parent {
    int id;

    Child() {
        count++;
        id = count;
        System.out.println("C" + id);
    }

    @Override
    void show() {
        System.out.println("Child id=" + id);
    }
}

public class Main {
    public static void main(String[] args) {
        Parent a = new Child();
        Parent b = new Child();

        a.show();
        b.show();
        System.out.println(a.id + " " + b.id);
        System.out.println(Parent.count);
    }
}
```

<details>
<summary>정답 보기</summary>

**출력:**
```
P1
C2
P3
C4
Child id=2
Child id=4
1 3
4
```

**풀이:**

| 단계 | count | Parent.id | Child.id | 출력 |
|------|:-----:|:---------:|:--------:|------|
| new Child() #1 - Parent() | 1 | 1 | 0 | P1 |
| new Child() #1 - Child() | 2 | 1 | 2 | C2 |
| new Child() #2 - Parent() | 3 | 3 | 0 | P3 |
| new Child() #2 - Child() | 4 | 3 | 4 | C4 |

- `a.show()` → 동적 바인딩 → Child.show() → Child.id = **2**
- `b.show()` → 동적 바인딩 → Child.show() → Child.id = **4**
- `a.id` → 정적 바인딩 → Parent 타입이므로 Parent.id = **1**
- `b.id` → 정적 바인딩 → Parent 타입이므로 Parent.id = **3**
- `Parent.count` → **4**
</details>

---

# 부록: 시험 직전 최종 체크리스트

## 반드시 외울 것 5가지

1. **필드는 정적 바인딩(선언 타입), 메서드는 동적 바인딩(실제 타입)**
2. **생성자 호출 순서: 부모 먼저! (super() 자동 삽입)**
3. **부모 생성자에서 오버라이딩된 메서드 호출 시 자식 것 실행 + 자식 필드는 기본값(0/null)**
4. **String 리터럴은 Pool 공유(==가 true), new String()은 별도 객체(==가 false)**
5. **finally는 return이 있어도 실행 (단, return 값을 바꾸지는 못함 - 기본형 기준)**

## 자주 틀리는 함정 Top 5

| 순위 | 함정 | 대응법 |
|:----:|------|--------|
| 1 | `Parent p = new Child(); p.x` 에서 Child의 x를 답으로 씀 | 필드는 **선언 타입**! Parent.x가 정답 |
| 2 | 부모 생성자에서 호출된 메서드를 부모 것으로 추적 | **동적 바인딩!** 자식 메서드가 실행됨 |
| 3 | 부모 생성자 실행 중 자식 필드가 초기화됐다고 가정 | 자식 필드는 아직 **기본값(0)**! |
| 4 | finally에서 변수 변경이 return에 반영된다고 생각 | 기본형은 반영 안 됨! (값이 이미 복사됨) |
| 5 | 후위증가(`x++`)에서 증가된 값을 사용 | 후위는 **현재 값** 사용 후 증가! |

## 시간 배분 전략
- Java 문제 1개당 **3~5분** 할당
- 상속 문제는 반드시 **추적표 작성** (1~2분 추가 투자 가치 있음)
- 모르겠으면 일단 넘기고, 나중에 여유 시간에 풀기

---

> **이 문서를 3번 정독하고, 예상 문제 10선을 직접 풀어본다면 Java는 만점입니다. 화이팅!**
