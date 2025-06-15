#  Platformă de Pariuri Sportive
Proiect realizat pentru disciplina "Metode de Dezvoltare Software" din cadrul Facultății de Matematică și Informatică, Universitatea București.

## Membrii echipei:

- [Hușanu Bianca](https://github.com/biancaaaq)
- [Baidoc Robert](https://github.com/baidocc)
- [Udriște Andrei](https://github.com/andreiudriste)
- [Chitacu Petrica](https://github.com/picky04)


## Descrierea proiectului

Această aplicație web este o platformă modernă de pariuri sportive, inspirată din soluții reale din industrie. Utilizatorii pot vizualiza meciuri și cote în timp real, plasa bilete individuale sau în grup, gestiona soldul și istoricul pariurilor, și beneficia de o interfață intuitivă care pune accent pe accesibilitate și funcționalitate.

Printre funcționalitățile avansate se numără integrarea cu un API live pentru preluarea cotelor sportive, documentarea automată a API-ului prin Swagger, precum și un sistem propriu de autentificare JWT, care asigură securitatea accesului. Platforma permite interacțiuni multiple și susține fluxuri complexe de pariere, atât individual, cât și colectiv.

Proiectul este dezvoltat pe o arhitectură client-server și utilizează următoarele tehnologii:

- **Frontend**: React + TypeScript
- **Backend**: Spring Boot (Java)
- **Build Tool**: Maven
- **Bază de date**: PostgreSQL
- **Autentificare**: JSON Web Tokens (JWT)
- **Swagger**: pentru testare și documentare REST API
- **Axios**: pentru integrarea frontend-backend
- **API extern**: pentru obținerea cotelor sportive în timp real

Aplicația este scalabilă, sigură și gândită pentru a fi ușor de extins în cazul adăugării de funcționalități viitoare.

##  Demo

- **Live Demo**: [https://demo-live-nu.vercel.app/]
- **Demo video**: [link YouTube sau Google Drive]

##  Backlog & User Stories

Am utilizat GitHub Projects pentru a organiza sarcinile în echipă.  
Link către board: [https://github.com/users/biancaaaq/projects/3]

User stories:
1. Ca utilizator neînregistrat, vreau să pot vedea meciurile live și cotele asociate pe pagina principală, pentru a decide dacă vreau să mă înregistrez și să pariez, având acces la o listă actualizată în timp real. 
2. Ca utilizator neînregistrat, vreau să pot accesa o pagină de login/înregistrare, pentru a-mi crea un cont și a începe să pariez, cu un formular care să includă numele utilizatorului, e-mail și parola. 
3. Ca utilizator înregistrat, vreau să pot selecta cote din meciurile live și să creez un bilet de pariere, pentru a plasa un pariu, cu posibilitatea de a seta o miză personalizată și de a primi o confirmare imediată a biletului creat. 
4. Ca utilizator înregistrat, vreau să pot vizualiza biletele mele create în secțiunea „Biletele Mele”, pentru a verifica starea lor (ACTIVE, WON, LOST), inclusiv detalii precum data plasării și suma pariată. 
5. Ca utilizator înregistrat, vreau să pot crea sau alătura un grup privat de pariere, pentru a paria împreună cu alți utilizatori, cu opțiunea de a invita membri prin numele lor . 
6. Ca utilizator, vreau ca autentificarea să fie sigură și tokenul să fie salvat local, pentru a-mi proteja datele personale și a menține accesul fluid la cont fără a mă autentifica de fiecare dată, cu implementarea unui mecanism JWT securizat care criptează tokenul, limitează durata de valabilitate. 
7. Ca utilizator inregistrat, vreau sa pot sa fac o cerere de autoexcludere, care, dupa ce este aprobata de admin, imi va interzice sa imi accesez contul. La cererea utilizatorului contul poate sa fie reactivat de admin. 
8. Ca utilizator înregistrat, vreau să pot accesa contul meu pentru a vedea soldul, informatii despre contul meu si istoricul tranzacțiilor, pentru a gestiona finanțele mele, cu o interfață care afișează soldul în timp real . 
9. Ca administrator, vreau să pot gestiona cote pentru meciuri (adaugare, editare, ștergere) prin panoul de administrare, pentru a actualiza oferta, cu o interfață care permite încărcarea automată a datelor prin API. 
10. Ca administrator, vreau să pot aproba sau respinge cereri de autoexcludere, pentru a asigura siguranța utilizatorilor, cu o listă de cereri în așteptare și notificări automate către utilizatori după decizie. 
11. Ca administrator, vreau să pot adăuga promoții care apar pe pagina Home, pentru a atrage utilizatorii către oferte speciale și a crește angajamentul, cu o interfață care să permită introducerea titlului, descrierii, imaginii (opțional), datei de început și datei de sfârșit și afișarea automată a promoțiilor active pe pagina principală, 

 

 

##  Diagrame

- [x] Diagrama Workflow UI [`/workflow.jpg`](https://github.com/biancaaaq/BettingApp/blob/main/workflow.jpg)
- [x] Diagrama Model Class (`/docs/modelclass.png`)
- [x] Diagrama UML a componentelor backend [`/uml_controller.jpg`](https://github.com/biancaaaq/BettingApp/blob/main/uml_controller.jpg)


##  Source control cu Git

- Repository organizat pe ramuri `frontend/`, `backend/`
- Branches: https://github.com/biancaaaq/BettingApp/branches
- Commits: https://github.com/biancaaaq/BettingApp/commits/
- Raportare bug:[aici](https://github.com/biancaaaq/BettingApp/issues/5)
- Rezolvare cu pull request: [aici](https://github.com/biancaaaq/BettingApp/pulls?q=is%3Apr+is%3Aclosed)


##  Testare automată

Framework-uri utilizate:
- **JUnit** – teste pentru servicii backend (pariere, validare JWT, gestionare sold)
- **React Testing Library** – teste pentru componente frontend

 Ce teste sunt implementate:

 Backend (JUnit): [teste](https://github.com/biancaaaq/BettingApp/tree/backend/src/test/java/proiect/bet/sportbet)
- Teste unitare pentru serviciile de gestionare a biletelor și balanței
- Validarea token-ului JWT și a logicii de autentificare

 Frontend (React Testing Library): [teste](https://github.com/biancaaaq/BettingApp/tree/frontend/src/__tests__)
- Teste pentru componente importante: `TicketSidebar`, `BileteMele`, `AddTranzactie`, `AddBalanta`
- Verificarea afișării corecte a formularului, mesajelor de succes/eroare și redirecționării după acțiuni
- Simularea apelurilor către API pentru a testa comportamentul aplicației fără backend real

 Acoperire generală:
- Componentele critice din aplicație au fost testate
- Fluxurile principale (ex: pariere, tranzacție, încărcare bilete) sunt verificate automat
- Asigurare minimă că aplicația funcționează corect la introducerea datelor și interacțiuni de bază



##  Design Patterns

În cadrul dezvoltării aplicației, am utilizat următoarele patternuri de design, specifice arhitecturii moderne web:

- **Singleton** – aplicat serviciilor din backend (ex: [service](https://github.com/biancaaaq/BettingApp/tree/backend/src/main/java/proiect/bet/sportbet/service) ), pentru a asigura existența unei singure instanțe a fiecărui serviciu și a facilita injectarea controlată prin Spring.
- **MVC (Model–View–Controller)** – pattern adoptat nativ în Spring Boot, care separă clar logica de business (Service), manipularea datelor (Repository) și interfața cu utilizatorul (Controller).
- **DTOs (Data Transfer Objects)** – folosite pentru a transmite date între backend și frontend într-un mod controlat, evitând expunerea directă a entităților din baza de date.


##  Prompt Engineering

Pe parcursul dezvoltării, am utilizat AI pentru asistență și automatizare: [documentare](https://github.com/biancaaaq/BettingApp/blob/main/Prompt%20Engineering.pdf)








