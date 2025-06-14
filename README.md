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

- **Live Demo**: [link demo live, ex. Railway/Vercel]
- **Demo video**: [link YouTube sau Google Drive]

##  Backlog & User Stories

Am utilizat GitHub Projects pentru a organiza sarcinile în echipă.  
Link către board: [https://github.com/users/biancaaaq/projects/3]

User stories:
1. Ca utilizator neînregistrat, vreau să pot vedea lista meciurilor și cotele disponibile.
2. Ca utilizator înregistrat, vreau să pot paria pe un meci, individual sau în grup.
3. Ca utilizator, vreau să îmi văd soldul actual și istoricul biletelor.
4. Ca utilizator, vreau să pot solicita autoexcluderea temporară.
5. Ca administrator, vreau să pot aproba sau respinge cererile de autoexcludere.
6. Ca administrator, vreau să pot edita și bloca cotele pentru anumite meciuri.
7. Ca utilizator, vreau să primesc un mesaj de eroare clar dacă JWT-ul meu expiră.
8. Ca utilizator, vreau ca autentificarea să fie sigură și tokenul să fie salvat local.
9. Ca utilizator, vreau să pot crea un grup privat pentru a paria în echipă.
10. Ca administrator, vreau să pot adăuga promoții care apar pe pagina Home.

##  Diagrame

- [x] Diagrama Use Case (`/docs/use_case.png`)
- [x] Diagrama UML a componentelor backend (`/docs/uml_backend.png`)
- [x] Diagrama Workflow UI (`/docs/workflow_ui.png`)


##  Source control cu Git

- Repository organizat pe ramuri `frontend/`, `backend/`
- Branches: https://github.com/biancaaaq/BettingApp/branches
- Commits: https://github.com/biancaaaq/BettingApp/commits/


##  Testare automată

Framework-uri utilizate:
- **JUnit** – teste pentru servicii backend (pariere, validare JWT, gestionare sold)
- **React Testing Library** – teste pentru componente frontend

Testele acoperă:
- Validarea datelor introduse
- Comportamentul componentelor vizuale
- Fluxul de autentificare


##  Design Patterns

În cadrul dezvoltării aplicației, am utilizat următoarele patternuri de design, specifice arhitecturii moderne web:

- **Singleton** – aplicat serviciilor din backend (ex: [service](https://github.com/biancaaaq/BettingApp/tree/backend/src/main/java/proiect/bet/sportbet/service) ), pentru a asigura existența unei singure instanțe a fiecărui serviciu și a facilita injectarea controlată prin Spring.
- **MVC (Model–View–Controller)** – pattern adoptat nativ în Spring Boot, care separă clar logica de business (Service), manipularea datelor (Repository) și interfața cu utilizatorul (Controller).
- **DTOs (Data Transfer Objects)** – folosite pentru a transmite date între backend și frontend într-un mod controlat, evitând expunerea directă a entităților din baza de date.


##  Prompt Engineering

Pe parcursul dezvoltării, am utilizat AI pentru asistență și automatizare: 








