# To-Do Manager

Darbvirsmas uzdevumu pārvaldības lietojumprogramma, kas rakstīta Java valodā, izmantojot Swing. 
Izstrādāts kā eksāmena projekts RTU - Liepājas akadēmijā, datorsistēmas 1.kursā.
Projektu veidoja Renārs Gricjus un Jēkabs Kūms.

## Funkcijas
- Izveidot, rediģēt, dzēst uzdevumus
- Atzīmēt uzdevumus kā izpildītus
- Piešķiriet prioritātes un termiņus pēc izvēles
- Filtrēt uzdevumus pēc statusa (ACTIVE / COMPLETED)
- Saglabāt un ielādēt uzdevumus no teksta faila
- Fona atgādinājumi par gaidāmajiem un nokavētajiem uzdevumiem
- Lietotāju saskarne veidota ar Java Swing

 
 ---

## Tehnoloģijas izmantotas
- Java
- Java Swing
- Objektorientēta programmēšana (OOP)
- File I/O
- ScheduledExecutorService priekš fona atgādinājumiem

---

## Struktūra
- 'Task' - Apzīmē vienu uzdevumu
- 'TaskManager' - Pamata loģika un failu apstrāde
- 'TaskUI' - Grafiskā lietotāja saskarne
- 'Reminder' - Fona atgādinājuma loģika
- 'ToDoManager' - Programmatūras palaišanas fails
