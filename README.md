# HackerInside Text Encryption Utility
Applicativo che consente di criptare e decriptare testi utilizzando l'algoritmo AES con chiavi a 256 bit e in modalità GCM (Galois/Counter Mode)

# Informazioni Generali
**Algoritmo Utilizzato:** AES-256-GCM

**Gestione degli IV:** Ogni IV viene generato casualmente (utilizzando la classe SecureRandom) e concatenati ai dati cifrati

**Formato del KeyStore:** JCEKS

# Gestione delle chiavi e KeyStore
Il software per poter criptare e/o decriptare ha bisogno di accedere ad un KeyStore (JCEKS) da cui preleva la chiave scelta.
- Appena avviato il software verifica la presenza di un KeyStore con nome "KeyStore.jks", se non trovato provvede a crearne uno chiedendo una password (password generale del KeyStore)
- Per poter aggiungere una chiave ad un KeyStore è possibile utilizzare l'apposita scheda nelle impostazioni (FILL) oppure utilizzare uno dei tanti KeyStore explorer disponibili (consigliato https://keystore-explorer.org/)
- Il file "KeyStore.jks" deve rimanere dentro la stessa cartella dove risiede l'eseguibile

# ENCRYPT
Per criptare un file procedere come segue:
1) Avviare il programma e sbloccare il KeyStore utilizzando la propria password
2) Selezionare dal menù a tendina la chiave che si desidera utilizzare
3) Inserire il testo da criptare (o caricarlo da un file con l'apposita funzione nel menù contestuale)
4) Premere il bottone ENCRYPT
Il testo scritto sarà ora criptato, codificato e formattato sulla base delle opzioni da noi scelte.

# DECRYPT
Per criptare un file procedere come segue:
1) Avviare il programma e sbloccare il KeyStore utilizzando la propria password
2) Selezionare dal menù a tendina la chiave che si desidera utilizzare
3) Inserire il testo criptato (o caricarlo da un file con l'apposita funzione nel menù contestuale)
4) Premere il bottone DECRYPT

# Impostazioni generali di formattazione del testo e di codifica
- **ENCODING:** Codifica che viene utilizzata per la rappresentazione del testo criptato, sono supportate:
  - Base64
  - Base58
  - Esadecimale (Hex)

- **SPACING:** Aggiunge uno spazio dopo "n" caratteri
- **TextArea Wrapping:** Opzione di "a capo automatico" della textArea
Queste informazioni vengono memorizzate attraverso le "Java Preferences API"

# Zeroize
In caso di emergenza è possibile premere il bottone rosso ZEROIZE che provvederà ad **CORROMPERE PERMANENTEMENTE IL KEYSTORE** (viene sovrascritto con bytes casuali generati sul momento)

**Procedura di Zeroize:** 
- Premere il bottone ZEROIZE.
- Prendere nota del codice che viene generato.
- Inserire il codice ed attendere il messaggio di conferma.

# BEST PRACTICES
- Generare la chiave di 256 bit in modo completamente casuale (possibilmente usando dispositivi RNG hardware e macchine "air-gapped").
- Non archiviare mai la chiave in chiaro e/o su supporti di memoria non sicuri.
- Comunica sempre al tuo interlocutore l'alias della chiave utilizzare.
- Evita di utilizzare la funzione per visulizzare la chiave se non sei in un luogo sicuro con dispositivi sicuri.
- Riavviare il programma dopo aver inserito una nuova chiave.

# Screenshot
![Main Screen](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Main.JPG)
![Impostazioni_Generale](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Impostazioni_Generale.JPG)
![KeyFill](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/KeyFill.JPG)
![Keys](https://github.com/FrancescoValentini/HackerInsideTextEncryptionUtility/blob/master/screenshot/Keys.JPG)

# Disclaimer
Questo software è a puro scopo di divertimento, non deve essere preso in considerazione come punto di riferimento.

Non mi assumo nessuna responsabilità di eventuali danni provocati da questo codice o da suoi possibili usi impropri.

# Credits
<a href="https://www.flaticon.com/free-icons/data-encryption" title="data encryption icons">Data encryption icons created by kerismaker - Flaticon</a>

# Sitografia
- https://it.wikipedia.org/wiki/Advanced_Encryption_Standard
- https://en.wikipedia.org/wiki/Galois/Counter_Mode
- https://github.com/multiformats/java-multibase/blob/master/src/main/java/io/ipfs/multibase/Base58.java
