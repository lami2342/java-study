# Python 완벽정리 - 정보처리기사 실기 일타강사 노트

> "Python은 가장 쉬운 득점원입니다. 여기서 틀리면 안 됩니다!"

---

## 출제 패턴 분석

- 매 시험 **1~2문제** 출제 (5~10점)
- 난이도: **낮음** → 반드시 득점해야 하는 파트!
- 최근 9회 시험 기준, 리스트 슬라이싱이 **가장 빈출**
- C/Java보다 문법이 직관적이라 단기간 학습 가능

### 빈출 주제 순위

| 순위 | 주제 | 출제 빈도 | 중요도 |
|------|------|-----------|--------|
| 1 | 리스트 슬라이싱 | 거의 매회 | ★★★ |
| 2 | 딕셔너리 | 자주 | ★★ |
| 3 | 집합(Set) 연산 | 자주 | ★★ |
| 4 | 문자열 처리 | 종종 | ★★ |
| 5 | 함수/타입 | 종종 | ★ |
| 6 | 클래스 | 가끔 | ★ |

---

## 1장. 리스트 완전정복 ★★★

> 리스트는 Python 문제의 **핵심 중의 핵심**입니다!

### 1.1 리스트 기본

```python
# 리스트 선언
a = [1, 2, 3, 4, 5]

# 리스트 접근
print(a[0])   # 1 (첫 번째 원소)
print(a[-1])  # 5 (마지막 원소)
print(a[-2])  # 4 (뒤에서 두 번째)

# 리스트 수정
a[0] = 10     # [10, 2, 3, 4, 5]
a.append(6)   # [10, 2, 3, 4, 5, 6]
a.insert(1, 99)  # [10, 99, 2, 3, 4, 5, 6]
a.pop()       # 마지막 원소 제거
a.pop(0)      # 첫 번째 원소 제거
```

**시험 포인트:**
- 인덱스는 **0부터** 시작! (절대 1부터가 아님)
- 음수 인덱스: -1이 마지막, -2가 뒤에서 두 번째

---

### 1.2 슬라이싱 마스터 ★★★

> 이것만 완벽히 이해하면 Python 문제 절반은 맞힙니다!

**기본 문법: `a[start:end:step]`**

- `start`: 시작 인덱스 (포함)
- `end`: 끝 인덱스 (**미포함!** ← 이것 때문에 틀리는 사람 많음)
- `step`: 간격 (기본값 1)

```python
a = [10, 20, 30, 40, 50, 60, 70, 80]
#     0   1   2   3   4   5   6   7   ← 인덱스

# 기본 슬라이싱
a[:3]     # [10, 20, 30]         → 처음부터 3개
a[4:6]    # [50, 60]             → 인덱스 4~5
a[28:]    # []                   → 범위 초과 시 빈 리스트 (에러 아님!)
a[2:]     # [30, 40, 50, 60, 70, 80]  → 인덱스 2부터 끝까지

# step 활용 ★★★
a[::2]    # [10, 30, 50, 70]     → 짝수 인덱스만 (0, 2, 4, 6)
a[1::2]   # [20, 40, 60, 80]     → 홀수 인덱스만 (1, 3, 5, 7)
a[::-1]   # [80, 70, 60, 50, 40, 30, 20, 10]  → 전체 뒤집기!
```

**핵심 암기표:**

| 표현 | 의미 | 예시 결과 |
|------|------|-----------|
| `a[:n]` | 처음부터 n개 | `a[:3]` → 앞 3개 |
| `a[n:]` | n번째부터 끝까지 | `a[2:]` → 2번부터 끝 |
| `a[::2]` | 짝수 인덱스 | 0, 2, 4, 6번째 |
| `a[1::2]` | 홀수 인덱스 | 1, 3, 5, 7번째 |
| `a[::-1]` | 전체 뒤집기 | 역순 |
| `a[-i-1]` | 뒤에서 접근 | 대칭 위치 |

---

### 1.3 리스트 함수들

```python
a = [3, 1, 4, 1, 5, 9, 2, 6]

sum(a)       # 31  → 전체 합
len(a)       # 8   → 길이
max(a)       # 9   → 최대값
min(a)       # 1   → 최소값
sorted(a)    # [1, 1, 2, 3, 4, 5, 6, 9] → 정렬 (원본 유지)
a.sort()     # 원본 자체를 정렬

# range() ★★★
list(range(5))        # [0, 1, 2, 3, 4]
list(range(1, 6))     # [1, 2, 3, 4, 5]
list(range(0, 10, 2)) # [0, 2, 4, 6, 8]
list(range(10, 0, -1))# [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
```

**시험 주의:** `range(5)`는 0~4까지! 5는 포함 안 됨!

---

### 1.4 리스트 컴프리헨션

```python
# 기본형
[i for i in range(5)]           # [0, 1, 2, 3, 4]

# 연산 적용
[i * 2 for i in range(5)]       # [0, 2, 4, 6, 8]

# 조건 필터링
[i for i in range(10) if i % 2 == 0]  # [0, 2, 4, 6, 8]

# 중첩 (가끔 출제)
[i * j for i in range(1, 4) for j in range(1, 4)]
# [1, 2, 3, 2, 4, 6, 3, 6, 9]
```

---

### 1.5 리스트 뒤집기 패턴

> 시험에 자주 나오는 swap 패턴!

```python
lst = [1, 2, 3, 4, 5]
n = len(lst)

for i in range(n // 2):
    lst[i], lst[-i-1] = lst[-i-1], lst[i]

print(lst)  # [5, 4, 3, 2, 1]
```

**동작 원리:**
- i=0: lst[0]과 lst[-1] 교환 → [5, 2, 3, 4, 1]
- i=1: lst[1]과 lst[-2] 교환 → [5, 4, 3, 2, 1]
- `n // 2`만큼만 반복 (절반까지만!)

---

### 슬라이싱 실전 연습 5제

**문제 1:**
```python
a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
print(a[2:8:2])
```
<details>
<summary>정답 보기</summary>
[3, 5, 7] → 인덱스 2부터 7까지, 2칸씩 건너뛰기
</details>

**문제 2:**
```python
a = [10, 20, 30, 40, 50]
print(a[::-2])
```
<details>
<summary>정답 보기</summary>
[50, 30, 10] → 뒤에서부터 2칸씩
</details>

**문제 3:**
```python
a = [1, 2, 3, 4, 5]
b = a[1:4]
b[0] = 99
print(a)
```
<details>
<summary>정답 보기</summary>
[1, 2, 3, 4, 5] → 슬라이싱은 새 리스트를 만들므로 원본 불변!
</details>

**문제 4:**
```python
a = list(range(1, 11))
print(sum(a[::2]))
```
<details>
<summary>정답 보기</summary>
25 → a[::2]는 [1, 3, 5, 7, 9], 합은 25
</details>

**문제 5:**
```python
lst = [10, 20, 30, 40]
for i in range(len(lst) // 2):
    lst[i], lst[-i-1] = lst[-i-1], lst[i]
print(lst)
```
<details>
<summary>정답 보기</summary>
[40, 30, 20, 10] → 리스트 뒤집기 패턴!
</details>

---

## 2장. 딕셔너리 ★★

### 2.1 기본 사용법

```python
# 딕셔너리 선언
d = {"이름": "홍길동", "나이": 25, "학년": 3}

# 접근
print(d["이름"])       # 홍길동
print(d.get("이름"))   # 홍길동
print(d.get("주소", "없음"))  # 없음 (기본값)

# 수정/추가
d["나이"] = 26         # 수정
d["학교"] = "한국대"    # 추가

# 삭제
del d["학년"]
d.pop("나이")

# 주요 메서드
d.keys()    # 키 목록
d.values()  # 값 목록
d.items()   # (키, 값) 쌍 목록
```

---

### 2.2 딕셔너리 컴프리헨션

```python
# 기본형
lst = [1, 2, 3, 4, 5]
d = {i: i * 2 for i in lst}
print(d)  # {1: 2, 2: 4, 3: 6, 4: 8, 5: 10}

# 조건 포함
d = {i: i ** 2 for i in range(1, 6) if i % 2 != 0}
print(d)  # {1: 1, 3: 9, 5: 25}
```

---

### 2.3 enumerate()

```python
fruits = ["사과", "바나나", "딸기"]

for idx, fruit in enumerate(fruits):
    print(f"{idx}: {fruit}")
# 0: 사과
# 1: 바나나
# 2: 딸기

# 시작 번호 지정
for idx, fruit in enumerate(fruits, 1):
    print(f"{idx}: {fruit}")
# 1: 사과
# 2: 바나나
# 3: 딸기

# 딕셔너리로 변환
d = dict(enumerate(fruits))
print(d)  # {0: '사과', 1: '바나나', 2: '딸기'}
```

---

### 딕셔너리 출제 패턴

**패턴 1: items() 활용**
```python
d = {"a": 1, "b": 2, "c": 3}
for key, value in d.items():
    print(key, value)
```

**패턴 2: 딕셔너리 컴프리헨션 + 조건**
```python
scores = {"국어": 90, "영어": 75, "수학": 60, "과학": 85}
passed = {k: v for k, v in scores.items() if v >= 80}
print(passed)  # {'국어': 90, '과학': 85}
```

**패턴 3: enumerate + 딕셔너리 생성**
```python
lst = ["a", "b", "c"]
result = {v: i for i, v in enumerate(lst)}
print(result)  # {'a': 0, 'b': 1, 'c': 2}
```

---

## 3장. 집합(Set) ★★

### 3.1 add, remove, update

```python
s = {1, 2, 3}

s.add(4)          # {1, 2, 3, 4}
s.remove(2)       # {1, 3, 4}    → 없으면 에러!
s.discard(99)     # 에러 없이 무시
s.update([5, 6])  # {1, 3, 4, 5, 6}
```

**주의:** `remove()`는 없는 원소 삭제 시 **에러 발생**, `discard()`는 에러 없음!

---

### 3.2 집합 연산 (&, |, -)

```python
a = {1, 2, 3, 4, 5}
b = {3, 4, 5, 6, 7}

# 교집합
print(a & b)     # {3, 4, 5}
print(a.intersection(b))  # 동일

# 합집합
print(a | b)     # {1, 2, 3, 4, 5, 6, 7}
print(a.union(b))         # 동일

# 차집합
print(a - b)     # {1, 2}
print(b - a)     # {6, 7}
print(a.difference(b))    # 동일

# 대칭 차집합 (합집합 - 교집합)
print(a ^ b)     # {1, 2, 6, 7}
```

---

### 3.3 set() 변환

```python
# 리스트 → 집합 (중복 제거!)
lst = [1, 2, 2, 3, 3, 3]
s = set(lst)
print(s)        # {1, 2, 3}

# 문자열 → 집합
s = set("hello")
print(s)        # {'h', 'e', 'l', 'o'}  ← 중복 'l' 제거!

# 집합 → 리스트
lst = list(s)
```

**시험 포인트:** set()으로 중복 제거 → 다시 list()로 변환하는 패턴 빈출!

---

### 집합 연산 정리표

| 연산 | 기호 | 메서드 | 의미 |
|------|------|--------|------|
| 교집합 | `&` | `intersection()` | 공통 원소 |
| 합집합 | `\|` | `union()` | 모든 원소 |
| 차집합 | `-` | `difference()` | A에만 있는 것 |
| 대칭차 | `^` | `symmetric_difference()` | 한쪽에만 있는 것 |

---

## 4장. 문자열 처리 ★★

### 4.1 슬라이싱

```python
s = "Hello World"
#    0123456789...

print(s[:3])    # "Hel"
print(s[4:6])   # "o "
print(s[::-1])  # "dlroW olleH"
print(s[::2])   # "HloWrd"
```

**리스트 슬라이싱과 완전히 동일한 규칙!**

---

### 4.2 split()

```python
s = "Hello World Python"
result = s.split()         # ["Hello", "World", "Python"]
result = s.split(" ")      # 동일

s = "2024-01-15"
result = s.split("-")      # ["2024", "01", "15"]

s = "a,b,c,d"
result = s.split(",")      # ["a", "b", "c", "d"]
```

---

### 4.3 f-string

```python
name = "홍길동"
age = 25

# f-string 포맷팅
print(f"이름: {name}, 나이: {age}")
# 이름: 홍길동, 나이: 25

# 표현식 사용 가능
print(f"내년 나이: {age + 1}")
# 내년 나이: 26

# 복합 예시
var = "ab"
var2 = "cd"
result = f"ab{var}ca{var2}"
print(result)  # "ababcacd"
```

---

### 4.4 인덱싱

```python
s = "Python"
#    012345

print(s[0])    # "P"
print(s[1])    # "y"
print(s[-1])   # "n"
print(s[-2])   # "o"
```

---

## 5장. 함수와 타입

### 5.1 type() 함수 비교

```python
print(type(100))      # <class 'int'>
print(type("hello"))  # <class 'str'>
print(type(100.0))    # <class 'float'>
print(type([1,2]))    # <class 'list'>
print(type((1,2)))    # <class 'tuple'>
print(type({1,2}))    # <class 'set'>
print(type({"a":1}))  # <class 'dict'>
print(type(True))     # <class 'bool'>

# 타입 비교 (시험 출제!)
print(type(100) == int)    # True
print(type("") == str)     # True
print(type(100.0) == float)# True
```

**시험 암기:**

| 값 | type() 결과 |
|----|-------------|
| `100` | `int` |
| `100.0` | `float` |
| `"hello"` | `str` |
| `[1,2]` | `list` |
| `(1,2)` | `tuple` |
| `{1,2}` | `set` |
| `{"a":1}` | `dict` |
| `True` | `bool` |

---

### 5.2 재귀 함수

```python
# 팩토리얼 (가장 기본!)
def factorial(n):
    if n <= 1:
        return 1
    return n * factorial(n - 1)

print(factorial(5))  # 120

# 피보나치 (자주 출제!)
def fib(n):
    if n <= 1:
        return n
    return fib(n-1) + fib(n-2)

print(fib(6))  # 8
# fib(0)=0, fib(1)=1, fib(2)=1, fib(3)=2, fib(4)=3, fib(5)=5, fib(6)=8
```

**재귀 함수 트레이싱 팁:**
1. 종료 조건(base case)을 먼저 파악
2. 작은 값부터 하나씩 대입해서 추적
3. 표를 그려서 정리 (시험지 여백 활용!)

```
factorial(4) 추적:
factorial(4) = 4 * factorial(3)
             = 4 * 3 * factorial(2)
             = 4 * 3 * 2 * factorial(1)
             = 4 * 3 * 2 * 1
             = 24
```

**매개변수 전달:**
```python
# 기본값 매개변수
def greet(name, msg="안녕하세요"):
    print(f"{name}님, {msg}")

greet("홍길동")           # 홍길동님, 안녕하세요
greet("홍길동", "반갑습니다")  # 홍길동님, 반갑습니다

# 가변 매개변수
def add(*args):
    return sum(args)

print(add(1, 2, 3))    # 6
print(add(1, 2, 3, 4)) # 10
```

---

## 6장. 클래스 기초

### __init__, self, 트리 구현

```python
# 기본 클래스
class Student:
    def __init__(self, name, score):
        self.name = name
        self.score = score

    def get_grade(self):
        if self.score >= 90:
            return "A"
        elif self.score >= 80:
            return "B"
        else:
            return "C"

s = Student("홍길동", 85)
print(s.name)        # 홍길동
print(s.get_grade()) # B
```

**핵심 포인트:**
- `__init__`: 생성자 (객체 생성 시 자동 호출)
- `self`: 자기 자신을 가리킴 (Java의 `this`와 같음)
- 메서드의 첫 번째 매개변수는 항상 `self`

**트리 구조 구현 (가끔 출제):**
```python
class Node:
    def __init__(self, data):
        self.data = data
        self.left = None
        self.right = None

# 트리 생성
root = Node(1)
root.left = Node(2)
root.right = Node(3)
root.left.left = Node(4)
root.left.right = Node(5)

#       1
#      / \
#     2   3
#    / \
#   4   5

# 전위 순회 (Pre-order): 루트 → 왼쪽 → 오른쪽
def preorder(node):
    if node is None:
        return
    print(node.data, end=" ")
    preorder(node.left)
    preorder(node.right)

preorder(root)  # 1 2 4 5 3

# 중위 순회 (In-order): 왼쪽 → 루트 → 오른쪽
def inorder(node):
    if node is None:
        return
    inorder(node.left)
    print(node.data, end=" ")
    inorder(node.right)

inorder(root)   # 4 2 5 1 3

# 후위 순회 (Post-order): 왼쪽 → 오른쪽 → 루트
def postorder(node):
    if node is None:
        return
    postorder(node.left)
    postorder(node.right)
    print(node.data, end=" ")

postorder(root) # 4 5 2 3 1
```

**트리 순회 암기법:**
- **전위(Pre):** **루**왼오 → "루트 먼저!"
- **중위(In):** 왼**루**오 → "루트 가운데!"
- **후위(Post):** 왼오**루** → "루트 마지막!"

---

## 7장. 예상 문제 5선

### 예상 문제 1: 리스트 슬라이싱

```python
a = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
b = a[1::3]
print(sum(b))
```

<details>
<summary>풀이 및 정답</summary>

- a[1::3]: 인덱스 1부터 3칸씩 → [2, 5, 8]
- sum([2, 5, 8]) = **15**

</details>

---

### 예상 문제 2: 딕셔너리 컴프리헨션

```python
lst = [3, 1, 4, 1, 5]
d = {i: lst[i] * 2 for i in range(len(lst))}
print(d[2] + d[4])
```

<details>
<summary>풀이 및 정답</summary>

- d = {0: 6, 1: 2, 2: 8, 3: 2, 4: 10}
- d[2] + d[4] = 8 + 10 = **18**

</details>

---

### 예상 문제 3: 집합 연산

```python
a = {1, 2, 3, 4, 5}
b = {3, 4, 5, 6, 7}
c = (a | b) - (a & b)
print(len(c))
```

<details>
<summary>풀이 및 정답</summary>

- a | b = {1, 2, 3, 4, 5, 6, 7}
- a & b = {3, 4, 5}
- c = {1, 2, 6, 7} (= 대칭차집합 a ^ b와 동일!)
- len(c) = **4**

</details>

---

### 예상 문제 4: 재귀 함수

```python
def func(n):
    if n <= 0:
        return 0
    return n + func(n - 2)

print(func(7))
```

<details>
<summary>풀이 및 정답</summary>

- func(7) = 7 + func(5) = 7 + 5 + func(3) = 7 + 5 + 3 + func(1) = 7 + 5 + 3 + 1 + func(-1) = 7 + 5 + 3 + 1 + 0 = **16**

</details>

---

### 예상 문제 5: 복합 문제

```python
class Counter:
    def __init__(self):
        self.count = 0

    def add(self, n):
        self.count += n
        return self

c = Counter()
c.add(3).add(5).add(2)
print(c.count)
```

<details>
<summary>풀이 및 정답</summary>

- add() 메서드가 self를 반환 → 메서드 체이닝 가능
- 3 + 5 + 2 = **10**

</details>

---

## 최종 정리: 시험 직전 체크리스트

- [ ] 리스트 슬라이싱 `a[start:end:step]` 완벽 이해
- [ ] `a[::2]`, `a[1::2]`, `a[::-1]` 즉시 해석 가능
- [ ] 리스트 뒤집기 swap 패턴 이해
- [ ] 딕셔너리 컴프리헨션 작성 가능
- [ ] enumerate() 활용법 숙지
- [ ] 집합 연산 기호 (`&`, `|`, `-`, `^`) 암기
- [ ] set()으로 중복 제거 패턴 이해
- [ ] type() 함수 결과값 암기
- [ ] 재귀 함수 트레이싱 연습 완료
- [ ] 클래스 __init__, self 이해

> **Python은 가장 확실한 득점 파트입니다. 위 체크리스트를 모두 완료하면 Python 문제는 100% 맞출 수 있습니다!**
