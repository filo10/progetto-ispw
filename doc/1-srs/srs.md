# Software Requirement Specification

## Introduction

### Aim of the document

Lo scopo di questo documento è descrivere le funzionalità e i requisiti per Ubergata.

### Overview of the defined system

Ubergata è un sistema riservato agli studenti dell'Università di Roma Tor Vergata per la pubblicazione e ricerca di passaggi in auto.

Ogni studente immatricolato potrà registrarsi e cercare passaggi offerti da altri studenti. Per offrire passaggi sarà necessario verificare la propria patente presso uno dei punti autorizzati.

I passeggeri potranno valutare gli autisti con cui hanno condiviso un viaggio, e viceversa, per aiutare altri studenti a trovare compagni di viaggio affidabili.

### Operational setting

Il sistema utilizza il linguaggio Java (versione 15) ed il framework JavaFX (versione 15) per la parte grafica.

### Related systems, Pros and Cons

Due sistemi simili a Ubergata sono Uber e BlaBlaCar

Uber

- Pro: Servizio di passaggio a richiesta.
- Contro: Prezzo elevato. Non adatto per proporre passaggi.

BlaBlaCar

- Pro: Ampia utenza.
- Contro: Servizio generalmente usato per la condivisione di viaggi tra città.

## User Stories

1. As a student without a car, I want to find a ride, so that I can reduce my commute time. [FB]
2. As a user, I want to know if a driver is recommendable, so that I feel safe going with her/him. [FB]
3. As a student that goes to the University by car all alone, I want to share my trip with collegues, so that I can do my part in reducing carbon emission. [FB]
4. As a Passenger, I want to delete a seat I reserved, so that it can be available again for someone else. [FG]
5. As a System Administrator, I want to ban a bad user, so that the community can always be safe. [FG]
6. As a driver, I want to see passengers' rating, so that I can decide to accept or not their request for a ride. [FG]

## Functional Requirements

1. The system shall provide an user rating function. [FB]
2. The system shall list available rides to a destination ordered by increasing distance from the start point. [FB]
3. The system shall permit the publication of rides that have one of the University's Departments as a start or finish point. [FB]
4. The system shall **allow** Drivers to delete their ride. [FG]
5. The system shall provide the staff with a function to ban users. [FG]
6. The system shall provide the driver with passengers' phone number and viceversa. [FG]

## Use Cases: Overview Diagram

