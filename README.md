# Ein Spring Cloud Beispiel Projekt
Dieses Projekt ist für das Labor Verteilte Systeme der Hochschule Karlsruhe - Technik und Wirtschaft der Informatik Fakultät.

## Einführung
Dieses Projekt benutzt den [Spring Cloud Solution Stack](http://projects.spring.io/spring-cloud/) um eine Microservice Landschaft zu erstellen, die einen Web Shop abbildet. Dabei erweitert bzw. verändert sie eine existierende monolithische Web-Anwendung und führt zusätzliche Microservice-Komponenten ein, welche von Spring Cloud angeboten werden.

Die Spring Cloud Microservice Applications sind als einzelne Docker-Container realisiert, welche mit dem [fabric8io docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin) erstellt werden.

Das Projekt enthält neben den fachlichen Microservices, welche für einen Web Shop notwendig sind, die folgenden Komponenten:
* Einen Config-Service mit [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/)
* Einen Discovery-Service mit [Spring Cloud Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/)
* Ein API-Gateway mit [Spring Cloud Netflix Zuul](https://cloud.spring.io/spring-cloud-netflix/)
* Einen OAuth2-Service mit [Spring Cloud Security](https://cloud.spring.io/spring-cloud-security/)
* Ein Hystrix Dashboard, Hystrix Integration für Microservices und einen Turbine Server mit [Spring Cloud Netflix Hystrix](https://cloud.spring.io/spring-cloud-netflix/)
* ...

## Voraussetzungen
Um das Projekt auszuführen, benötigt man:
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)
* [Docker-Compose](https://github.com/docker/compose)
* Als IDE eignet sich bspw. [Spring Tool Suite](https://spring.io/tools/sts/all)

## Ausführen der Microservices
Um die Docker Container mit den enthaltenen Microservices zu erstellen, müssen folgende Vorbereitungen getroffen werden:
* In der Parent-POM im Projekt-Ordner muss die Property 
`<docker.user>[Benutzername]</docker.user>`
mit dem eigenen [Docker Hub](https://hub.docker.com/) Account gesetzt werden.
* Außerdem müssen die Umgebungsvariablen `DOCKER_PWD`und `CONFIG_SERVICE_PASSWORD` gesetzt werden.
* In der `docker-compose.yml` müssen die Namen der Images auf den Wert von `<docker.user>` geändert werden.

Als nächstes müssen die Container mit Maven gebildet werden. Hierfür sollte der Befehl 
```bash 
mvn clean package docker:build
```
 ausgeführt werden.

Um die Container Images auf Docker Hub zu pushen muss 
```bash 
mvn clean package docker:build docker:push
``` 
verwendet werden.

Schließlich muss im Projekt-Verzeichnis noch 
```bash 
docker-compose up -d
``` 
ausgeführt werden, um die in der `docker-compose.yml` aufgelisteten Container zu starten.

## Anlegen eines neuen Microservices
In der Spring Tool Suite (STS) kann ein neuer Microservice bzw. ein Modul zum Parent Projekt hinzugefügt werden, in dem man:
* einen Rechtsklick auf das Parent-Projekt macht
* und dann `New --> Project` wählt.
* Es öffnet sich ein Auswahlfenster. Hier wählt man Maven --> Maven Module
* Man klickt auf `Next`, gibt einen Namen für das Module ein
* und setzt einen Haken bei `Create a simple project (skip archetype selection)`.
* Danach befolgt man den Wizard weiter, setzt möglicherweise noch einen Namen und eine Beschreibung
* und das Modul wird erstellt.

## Erstellen einer Datenbankverbindung für Core-Services mit JPA Data
Dem Projekt liegt ein MySQL Docker-Container bei, der wie folgt in der `docker-compose.yml` definiert ist:
```bash
  mysqldb:
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: webshop
      MYSQL_USER: shop
      MYSQL_PASSWORD: shop
    image: mysql
    restart: always
    ports:
    - 3307:3306
    logging:
      options:
        max-size: 10m
        max-file: '10'
```
Dieser wird im Container-Netzwerk über den Standardport `3306` angesprochen. Vom Docker-Host kann man über `3307` auf die MySQL-Datenbank zugreifen.

Wenn man nun einen Core-Service erstellen will, der auf den MySQL-Container zugreift kann man den `product-core-service` als Referenz nehmen.
In der `product-core-service.yml` im `shared` Ordner des `config-service` ist die Konfiguration definiert. Hier ein Ausschnitt:
```bash
spring:
  datasource:
    url: jdbc:mysql://mysqldb:3306/webshop
    username: shop
    password: shop
    driver-class-name: com.mysql.jdbc.Driver
  jpa:
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    hibernate:
      ddl-auto: validate
```
Mit dieser Konfiguration kann der Core-Service als Container auf die Datenbank zugreifen für den `test/resources` Ordner sollte eine zusätzliche Testkonfiguration angelegt werden. Diese wird in der `application.yml` hinterlegt und sieht wie folgt aus:
```bash
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/webshop
    username: shop
    password: shop
    driver-class-name: com.mysql.jdbc.Driver
  jpa:
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    hibernate:
      ddl-auto: validate
```
Hier wird der MySQL-Container vom Docker-Host aus angesprochen. Auch hier ist eine Beispielkonfiguration im `product-core-service` hinterlegt.

Damit die Datenbankanbindung funktioniert, sollte der Core-Service außerdem folgende Dependencies enthalten:
```bash

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
```

Wenn man die Datenbank mit Testdaten befüllen möchte bietet es sich an, in `main/resources`, eine `import.sql` anzulegen. Dieses Skript wird beim Starten des Core-Services ausgeführt. `import.sql` enthält hierbei normalerweise Tabelleneinträge. Zum Beispiel im `product-core-service`:
```bash
Insert INTO `product` VALUES(1, 1,'Leckeres Toastbrot', 'Toast', 0.49);
```

Danach kann Spring Data JPA genutzt werden. Datenbanktabellen werden hier objektrelational auf Objekte gemappt. Siehe hierzu die `Product.java` Klasse des `product-core-service`. Um Datenbank-Operationen auszuführen kann ein Repository-Interface analog zu `ProductRepository.java` erstellt werden.

## Testen der Anwendung
Die einzelnen Microservices sind wie folgt zu erreichen:
* Der Config-Service ist unter `http://localhost:8888` zu erreichen. Dabei wird eine Authentifizierung mit dem Benutzernamen `user` und dem Passwort `${CONFIG_SERVICE_PASSWORD` erwartet. Auf dem Config-Service sind alle YAML Konfigurationdateien für die anderen Server hinterlegt. Bspw. kann die Konfiguration des API-Gateways unter `http://localhost:8888/api-gateway-service.yml` aufgerufen werden.
* Das Spring Eureka Dashboard ist unter `http://localhost:8761` zu erreichen.
* Das API-Gateway ist unter `http://localhost:4000` zu erreichen. Alle Mappings können unter `http://localhost:4000/routes` eingesehen werden.
* Der OAuth2-Service ist unter `http://localhost:5000/uaa` bzw. `http://localhost:4000/oauth2-service/uaa` zu erreichen. Allerdings sollte folgende Meldung erscheinen: `Full authentication is required to access this resource`.
* Das Hystrix Dashboard ist unter `http://localhost:9000/hystrix` zu erreichen.
* Der Turbine Stream ist unter `http://localhost:8989/turbine.stream` zu erreichen. 

## Funktionen von Aufgabe 3
- [] Core Microservices können auf den entsprechenden Komponenten der monolithischen An-
wendung aufbauen oder neu implementiert werden. (User-Core-Service fehlt noch)
- [] Composite Microservices und API-Gateways sollen als REST-Clients der Core Microservices 
realisiert werden. Die Bindung kann (zunächst noch) statisch erfolgen, indem die URLs der Mi-
croservices fest kodiert werden. (User-Composite-Service fehlt noch) 
- [x] Als Basis der Infrastruktur soll ein Eureka Server als Spring Boot Anwendung realisiert werden. 
Alle implementierten Microservices sollen so erweitert werden, dass sie sich als Discovery 
Clients registrieren.
- [x] Composite Microservices sollen zu Ribbon-Clients erweitert werden, die die verwendeten Core 
Microservices dynamisch Binden. Der Zugriff auf die Core Microservices soll per Hystrix 
gesichert werden. (Feign übernimmt die Sicherung per Hystrix)
- [x] Es soll ein Zuul Edge Server als Spring Boot Anwendung realisiert werden. Der Edge Server 
soll eine öffentliche API bereitstellen, die die Client Aufrufe auf die implementierten API Gate-
ways leitet. (Möglicherweise Core-Services aus API entfernen, aber zum testen ganz gut)
- [] Es soll ein Hystrix Dashboard als Spring Boot Anwendung (oder als Teil einer geeigneten an-
deren Komponente) realisiert werden. Auf dem Dashboard sollen die Aktivitäten des Edge 
Servers und der Hystrix-gesicherten Komponenten beobachtet werden.  (Geht Prinzipiell, aber Threads werden nicht angezeigt)
- [] Erstellen  Sie  ein  Dokument  mit  einer  Skizze  der  implementierten  Systemarchitektur  (sowohl  Microservices als auch Middleware Services). Das Deckblatt soll den Gruppennamen und die Namen der Gruppenmitglieder mit Matrikelnummer und Email enthalten. Bitte laden Sie eine Kopie 
des PDF und die Sourcen der Projekte als Datei im ILIAS hoch. 
