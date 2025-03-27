# Symulator Samoobsługowej Myjni Samochodowej

## Opis projektu (PL)
Projekt jest symulatorem samoobsługowej myjni samochodowej, w którym dochodzi do synchronizacji wielu wątków.

### Główne założenia:
- Pojazdy wjeżdżają na myjnię przez jedną z dwóch bram, wybierając bramę z mniejszą kolejką.
- Kontroler naprzemiennie wpuszcza pojazdy na wolne stanowiska.
- Stanowiska posiadają współdzielone myjki do wody i środka czyszczącego.
- Mycie odbywa się w trzech fazach: woda, środek czyszczący, woda.

## Car Wash Simulation

### Project Description (EN)
This project is a self-service car wash simulator that involves the synchronization of multiple threads.

### Main Features:
- Vehicles enter the car wash through one of two gates, choosing the one with shorter queues.
- A controller alternates vehicle entry onto available washing stations.
- Stations share washing equipment: water and cleaning solution sprayers.
- The washing process consists of three phases: water rinse, cleaning solution, water rinse.


