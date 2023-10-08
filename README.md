# HackerInside Text Encryption Utility
Applicativo che consente di criptare e decriptare testi utilizzando l'algoritmo AES con chiavi a 256 bit e in modalità GCM (Galois/Counter Mode)

## Informazioni Generali
**Algoritmo Utilizzato:** AES-256-GCM

**Gestione degli IV:** Ogni IV generato casualmente (utilizzando la classe SecureRandom) viene inserito aggiungendo 16 bytes all'inizio dei dati cifrati

**Formato del KeyStore:** BCFKS

**Compressione:** GZip

**ECDH:** ECC-384

## Gestione delle chiavi e KeyStore
Il software per poter criptare e/o decriptare ha bisogno di accedere ad un KeyStore (BCFKS) da cui preleva la chiave scelta.
- Appena avviato il software verifica la presenza di un KeyStore con nome "KeyStore.bcfks", se non trovato provvede a crearne uno chiedendo una password (password generale del KeyStore)
	- è possibile specificare un keystore diverso da "KeyStore.bcfks" avviando il programma nel modo seguente: ```java -jar HackerInsideTextEncryptionUtility.jar <keystore>```
- Per poter aggiungere una chiave ad un KeyStore è possibile utilizzare l'apposita scheda nelle impostazioni (FILL) oppure utilizzare uno dei tanti KeyStore explorer disponibili (consigliato https://keystore-explorer.org/)
- Se non viene specificato un keystore differente il file "KeyStore.bcfks" deve rimanere dentro la stessa cartella dove risiede l'eseguibile

## ENCRYPT
Per criptare un file procedere come segue:
1) Avviare il programma e sbloccare il KeyStore utilizzando la propria password
2) Selezionare dal menù a tendina la chiave che si desidera utilizzare
3) Inserire il testo da criptare (o caricarlo da un file tramite l'apposita funzione nel menù contestuale)
4) Premere il bottone ENCRYPT
Il testo scritto sarà ora criptato, codificato e formattato sulla base delle opzioni da noi scelte.

## DECRYPT
Per criptare un file procedere come segue:
1) Avviare il programma e sbloccare il KeyStore utilizzando la propria password
2) Selezionare dal menù a tendina la chiave che si desidera utilizzare
3) Inserire il testo criptato (o caricarlo da un file tramite l'apposita funzione nel menù contestuale)
4) Premere il bottone DECRYPT

## Impostazioni generali di formattazione del testo e di codifica
- **ENCODING:** Codifica che viene utilizzata per la rappresentazione del testo criptato, sono supportate:
  - Base64
  - Base58
  - Esadecimale (Hex)
  - PGP Word list
  - Base36
  - Base32
  - Base32-C (Versione modificata di Base32 che utilizza il carattere '9' per il padding)
  
- **SPACING:** Aggiunge uno spazio dopo "n" caratteri (Disabilitato con codifica PGP Word list)
- **TextArea Wrapping:** Opzione di "a capo automatico" della textArea
- **GZIP Compression:** abilità / disabilità la compressione dei dati (comprime e poi cripta)

Queste informazioni vengono memorizzate attraverso le "Java Preferences API"


## ECDH Key Agreement
Questa funzionalità presente nelle impostazioni consente a due utenti di "mettersi d'accordo" mediante un mezzo non sicuro su una chiave che si desidera utilizzare.
1) Aprire le impostazioni e selezionare l'apposita scheda (ECDH)
2) Premere il bottone "1 - INIT" per generare la propria coppia di chiavi (pubblica e privata)
3) Inviare al proprio interlocutore la propria chiave pubblica (MY PUBLIC KEY)
4) Copiare e incollare nella casella di testo OTHER PUBLIC KEY la chiave pubblica che il tuo interlocutore ti ha inviato
5) Premre il bottone "2 - CALC" per calcolare la chiave condivisa (KEY)
6) Verificare ora con il proprio interlocutore che i valori KCV (Key Check Value) siano identici da entrambe le parti
7) Premere il bottone "3 - ADD" e seguire la procedura guidata per aggiungere la chiave al proprio KeyStore

**ATTENZIONE!** 
- Ogni qualvolta si preme il bottone "1 - INIT" o si riavvia il programma tutte le informazioni relative alla funzione ECDH vengono resettate, occore quindi sincronizzarsi con il proprio interlocutore per assicurare il corretto scambio delle chiavi!
- È buona norma verificare SEMPRE che i valori KCV siano equivalenti al fine di evitare chiavi corrotte (o semplicemente diverse).
- Verificare sempre l'identità del proprio interlocutore al fine di evitare MiTM

## Zeroize
In caso di emergenza è possibile premere il bottone rosso ZEROIZE che provvederà ad **CORROMPERE PERMANENTEMENTE IL KEYSTORE** (viene sovrascritto con bytes casuali generati sul momento)

**Procedura di Zeroize:** 
- Premere il bottone ZEROIZE.
- Prendere nota del codice che viene generato.
- Inserire il codice ed attendere il messaggio di conferma.


## BEST PRACTICES
- Generare la chiave di 256 bit in modo completamente casuale (possibilmente usando dispositivi RNG hardware e macchine "air-gapped").
- Non archiviare mai la chiave in chiaro e/o su supporti di memoria non sicuri.
- Comunica sempre al tuo interlocutore l'alias della chiave da utilizzare.
- Comunica sempre al tuo interlocutore le tue intenzioni riguardo la codifica da utilizzare e l'eventuale uso della compressione
- Evita di utilizzare la funzione per visulizzare la chiave se non sei in un luogo sicuro con dispositivi sicuri.
- Riavviare il programma dopo aver inserito una nuova chiave.

# Screenshot
![Main Screen](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Main.JPG)
![Impostazioni_Generale](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Impostazioni_Generale.JPG)
![KeyFill](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/KeyFill.JPG)
![Keys](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Keys.JPG)
![Settings_ecdh](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Settings_ecdh.JPG)

# Disclaimer
Questo software è a puro scopo di divertimento, non deve essere preso in considerazione come punto di riferimento.

Non mi assumo nessuna responsabilità di eventuali danni provocati da questo codice o da suoi possibili usi impropri.

# Credits
<a href="https://www.flaticon.com/free-icons/data-encryption" title="data encryption icons">Data encryption icons created by kerismaker - Flaticon</a>

<a href="https://github.com/ZeroAlphaTech/j-pgp-wordlist" title="PGP Word list encoding library">Libreria utilizzata per la codifica PGP Word List</a>



<a href="https://www.bouncycastle.org/java.html" title="BouncyCastle Java library">BouncyCastle</a>

# Sitografia
- https://it.wikipedia.org/wiki/Advanced_Encryption_Standard
- https://en.wikipedia.org/wiki/Galois/Counter_Mode
- https://github.com/multiformats/java-multibase/blob/master/src/main/java/io/ipfs/multibase/Base58.java
- https://neilmadden.blog/2016/05/20/ephemeral-elliptic-curve-diffie-hellman-key-agreement-in-java/
- https://en.wikipedia.org/wiki/Base36
