# Student-Management-System
 Java-based application designed to manage student records for a university. It includes functionality for handling undergraduate and postgraduate students, course enrollments, and administrative tasks.

## Clasa Main
Implementarea începe cu declararea fișierelor `in` și `out`. Dacă la CLI există un argument în `args[]`, numele fișierelor `in` și `out` sunt o concatenare a directorului cu același nume al directorului urmat de sufixele `.in` și `.out`. Metoda `citesteComenzi` parcurge fișierul `.in` și reține într-un `ArrayList` de `String` toate comenzile citite. Se declară un `BufferedWriter` pentru a fi folosit în metodele ce scriu în fișierul `.out`.

Într-un `for` se parcurg toate comenzile din array și în înlănțuirea de `if-else` se verifică fiecare comandă cu cele 9 din enunț. Pentru fiecare comandă se face `split` la `String` pentru a reține toate informațiile într-un array, acestea sunt folosite în apelul metodelor din `Secretariat`.

La final se închide writer-ul și se eliberează colecțiile din secretariat ce rețin studenții și cursurile adăugate.

## Clasele Student, StudentLicenta, StudentMaster
Clasa `Student` conține câte un field pentru nume, medie și o colecție de `String` în care se rețin preferințele fiecărui student. Aceasta implementează interfata `Comparable`, așa că avem metoda `compareTo` ce ordonează studenții descrescător în funcție de medie.
Clasele `StudentLicenta` și `StudentMaster` moștenesc clasa `Student` și au câte un constructor cu parametri.

## Clasa Curs
Clasa `Curs` conține field-uri pentru denumire, capacitate maximă, programul de studii, și o colecție de obiecte de tip `Student` ce reține toți studenții înrolați la cursul respectiv. Mai avem un constructor cu parametri.

## Clasa StudentDejaAdaugat
Această clasă moștenește clasa `Exception` și reprezintă o excepție custom ce va fi aruncată în metoda `adaugaStudent`.

## Clasa Secretariat
Clasa `Secretariat` conține o colecție cu toți studenții adăugați (indiferent de programul de studii la care aceștia sunt) și o colecție de cursuri în care se rețin toate cursurile adăugate. În continuare avem metodele aferente fiecărei comenzi:

- `adaugaStudent` caută în colecția de studenți din secretariat numele studentului ce urmează a fi adăugat. În caz afirmativ, se aruncă excepția `StudentDejaAdaugat`. Excepția este tratată în clasa `Main` într-un bloc `try-catch`, instrucțiunea `catch` printează în fișierul `.out` mesajul corespunzător. Dacă studentul nu este deja adăugat, se verifică ce tip de student este și se apelează constructorul aferent (`StudentLicenta` sau `StudentMaster`). Apoi studentul este adăugat în `ArrayList`-ul de Studenți.

- `citesteMediile` verifică dacă directorul dat există și pentru fiecare fișier din acesta verifică dacă există și dacă începe cu prefixul "note_". În caz afirmativ, se apelează metoda `adaugaMedii`.

- `adaugaMedii` citește fiecare linie și extrage fiecare nume și medie, caută numele studentului în colecția de studenți și asignează media citită studentului găsit.

- `posteazaMedii` parcurge colecția de studenți și printează pe câte o linie numele și media fiecăruia.

- `contestatie` caută studentul dat în colecția de studenți și schimbă media existentă cu cea nouă.

- `adaugaCurs` creează un nou curs cu datele date și îl adaugă în colecția de cursuri.

- `listaPreferinte` preia comanda dată și creează un `ArrayList` de preferințe în care se rețin toate preferințele citite din fișier și returnează colecția nouă.

- `adaugaPreferinte` apelează metoda precedentă și parcurge colecția de studenți. Pentru fiecare student se adaugă tot `ArrayList`-ul de preferințe în `student.preferinte`.

- `repartizeazaStudent` verifică dacă studentul dat este de tip licență sau master și reține în stringul `tipStudent` tipul acestuia. Se parcurge colecția de preferințe ale studentului și se caută cursul în ordinea preferințelor. Dacă acesta este găsit, se verifică dacă `tipStudent` coincide cu `programulDeStudii` din clasa `Curs`. În caz afirmativ, se compară dimensiunea colecției cu capacitatea maximă a cursului și se adaugă studentul dacă aceasta nu a fost depășită. Dacă s-a depășit capacitatea, se compară media studentului de adăugat cu media ultimului student și dacă aceasta este egală, studentul va fi adăugat în curs.

- `repartizeaza` parcurge colecția de studenți și pentru fiecare se apelează metoda `repartizeazaStudent`.

- `posteazaCurs` scrie în fișierul `.out` numele cursului și lista de studenți adăugați în ordine alfabetică (se folosește `Comparator`).

- `gasesteCurs` parcurge cursurile și pentru fiecare curs se caută numele studentului dat. Dacă acesta este găsit, se returnează numele cursului.

- `posteazaStudent` caută numele studentului în colecția de studenți, când este găsit se verifică tipul acestuia folosind `instanceof` și se scrie în formatul cerut.
