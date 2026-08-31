# 🍕 Spring Boot & Hibernate: Guida Pratica CRUD

**Progetto:** `spring-la-mia-pizzeria-crud`

---

## 1. Introduzione & Obiettivi del Progetto

Repository dedicata all'implementazione delle basi di un'applicazione web MVC (Model-View-Controller) con **Spring Boot**, **Spring Data JPA (Hibernate)** e **Thymeleaf** per la gestione di un menù di una pizzeria tramite operazioni di lettura (Read) persistenti su database MySQL.

### Requisiti della Consegna:

* **Step 1:** Definizione dell'Entity `Pizzeria`, database MySQL, repository e vista `index` con l'elenco delle pizze o un messaggio dedicato in caso di lista vuota. Gestione dei componenti riutilizzabili con Thymeleaf Fragments.


* **Step 2:** Rotta di dettaglio `/{id}` con recupero dell'entità tramite ID e resa grafica della vista dedicata.


* **Step 3 (Bonus):** Filtro di ricerca per nome lato server tramite form e metodo dedicato nel controller.



---

## 2. Architettura e Struttura Progettuale

L'applicazione segue l'architettura a strati (Layered Architecture), dove ogni cartella e package ha una responsabilità precisa ed esclusiva (Principio di Separazione delle Responsabilità - SoC):

```text
spring-la-mia-pizzeria-crud/
├── src/main/java/org/lessons/java/spring_la_mia_pizzeria_crud/
│   │
│   ├── model/                          <-- [MODEL / ENTITY]
│   │   └── Pizzeria.java               <-- Mappa la tabella 'pizzas' del database
│   │
│   ├── repository/                     <-- [DATA ACCESS LAYER / JPA]
│   │   └── PizzeriaRepository.java     <-- Interfaccia per la gestione delle query SQL/JPA
│   │
│   ├── controller/                     <-- [CONTROLLER / ROUTING]
│   │   └── PizzeriaController.java     <-- Gestisce le richieste HTTP e i dati per il Model
│   │
│   └── SpringLaMiaPizzeriaCrudApplication.java  <-- Entry point dell'applicazione
│
└── src/main/resources/
    ├── application.properties          <-- Configurazione connessione MySQL e parametri Hibernate
    ├── import.sql                      <-- Script per il popolamento automatico all'avvio
    └── templates/                      <-- [VIEW / THYMELEAF]
        ├── index.html                  <-- Homepage dell'applicazione
        ├── fragments/
        │   └── fragments.html          <-- Componenti HTML riutilizzabili (Navbar, Barra di ricerca, ecc.)
        └── pizzas/
            ├── index.html              <-- Elenco delle pizze e form di ricerca
            └── pizzaDetail.html        <-- Scheda di dettaglio della singola pizza

```

---

## 3. Ruoli dei Componenti nell'Architettura

* **Model (`model/Pizzeria.java`):** Rappresenta la struttura dei dati applicativi. Grazie ad Hibernate, la classe viene mappata su una tabella relazionale MySQL. Contiene i vincoli di validazione delle proprietà (es. campi non vuoti, range del prezzo).


* **Repository (`repository/PizzeriaRepository.java`):** Interfaccia che estende `JpaRepository`. Rappresenta il livello di accesso ai dati (DAO) ed evita la scrittura di SQL manuale fornendo metodi CRUD nativi e query derivate.


* **Controller (`controller/PizzeriaController.java`):** Intercetta le chiamate HTTP dal browser, comunica con la Repository per estrarre o filtrare i dati, valorizza l'oggetto `Model` e sceglie la pagina HTML da restituire all'utente.


* **View (`templates/`):** Pagine HTML dinamiche renderizzate lato server tramite Thymeleaf. Mostrano i dati elaborati dal Controller e gestiscono la logica di presentazione (come i messaggi in caso di lista vuota).



---

## 4. Concetti Teorici Fondamentali

### A. Object-Relational Mapping (ORM) & Hibernate

L'ORM è la tecnica che converte la struttura ad oggetti di Java nel formato relazionale di SQL. **Hibernate** è il motore concreto (implementazione di JPA) che trasforma automaticamente le classi annotate in tabelle del database e gli attributi in colonne.

### B. Derived Query Methods in Spring Data JPA

Spring Data JPA analizza il nome dei metodi definiti nell'interfaccia della Repository per generare automaticamente le relative query SQL. Ad esempio, dichiarando un metodo con un nome come `findByNameContaining`, il framework genera autonomamente la clausola SQL `WHERE name LIKE %...%` senza dover scrivere la sintassi manuale.

### C. Dependency Injection (DI) & Inversion of Control (IoC)

Invece di istanziare manualmente la Repository dentro il Controller usando l'operatore `new`, si affida a Spring Boot la gestione del ciclo di vita degli oggetti. Il framework "inietta" automaticamente la dipendenza della Repository all'interno del costruttore del Controller.

### D. Inizializzazione Dati (`import.sql`)

Un meccanismo integrato in Spring/Hibernate che legge ed esegue automaticamente lo script `import.sql` posizionato nelle `resources` ad ogni avvio dell'applicazione, permettendo di inserire dati di prova all'interno della tabella MySQL.

### E. Thymeleaf Fragments

Strategia di templating per evitare la duplicazione del codice HTML. Permette di estrapolare blocchi di interfaccia ripetitivi (come la barra di navigazione, i form di ricerca o le schede di presentazione) in un file centralizzato (`fragments.html`) per poi re-includerli dinamicamente in qualsiasi vista del progetto.