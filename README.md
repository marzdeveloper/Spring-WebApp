# GESTIONE DISPOSITIVI

## Introduction
La seguente applicazione consente di tenere attiva la tracciabilità dei device all'interno di un'azienda informatica sotto vari punti di vista: collocazione fisica corrente e 
storico delle passate, assegnamento degli stessi a dipendenti o a gruppi di dipendenti, lavori eseguiti su di essi, rapporti con i diversi mittenti dei dispositivi.
L'applicazione ha più livelli di permessi a seconda del tipo di utente che esegua il log in.

## Technologies

*1. JDK 1.8* https://github.com/ojdkbuild/ojdkbuild
2. Eclipse
3. Xampp/MySql
4. Tomcat https://tomcat.apache.org/download-90.cgi
5. Utilizzo asserzioni attive  https://tutoringcenter.cs.usfca.edu/resources/enabling-assertions-in-eclipse.html
6. Per problemi con l'aggiornamento del progetto provare ad eseguirne il "clean" o il "maven update"

## Launch 

1. Scaricare il progetto dalla branch MASTER ed importarlo su Eclipse.
2. Controllare che si scarichino le varie dependecy.
2. Avviare xampp ed il server Tomcat
3. Modificare nelle classi di configurazione (DataServiceConfigWeb, DataServiceConfig, DataServiceConfigTest) la password per l'accesso ed eventualmente username e numero di porta
4. Effettuare il run della classe LoadData come java application
5. Runnare l'intero progetto sul server Tomcat
6. Per il login usare le credenziali: username:"user1" password:"password" per l'utente con ruolo user o username:"user2" password:"password" per l'admin
7. Per effettuare i test cliccare sulla classe del test che si vuole runnare ed eseguirlo con Junit

## Naviagare all'interno dell'applicazione

L'user admin ha i privilegi di modifica, creazione ed eliminazione dei vari dati, mentre l'utente standard può solo visualizzarli.