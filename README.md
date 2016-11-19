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
* Ein Hystrix Dashboard, Hystrix Integration für Microservicesund einen Turbine Server mit [Spring Cloud Netflix Hystrix](https://cloud.spring.io/spring-cloud-netflix/)
* ...

## Voraussetzungen
Um das Projekt auszuführen, benötigt man:
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)
* [Docker-Compose](https://github.com/docker/compose)
* ???

## Ausführen der Microservices
Um die Docker Container mit den enthaltenen Microservices zu erstellen, müssen folgende Vorbereitungen getroffen werden:
* In der Parent-POM im Projekt-Ordner muss die Property 
`<docker.user>[Benutzername]</docker.user>`
mit dem eigenen [Docker Hub](https://hub.docker.com/) Account gesetzt werden.
* Außerdem müssen die Umgebungsvariablen `DOCKER_PWD`und `CONFIG_SERVICE_PASSWORD` gesetzt werden.
* In der `docker-compose.yml` müssen die Namen der Images auf den Wert von `<docker.user>` geändert werden.

Als nächstes müssen die Container mit Maven gebildet werden. Hierfür sollte der Befehl 
``` bash mvn clean package docker:build```
 ausgeführt werden.

Um die Container Images auf Docker Hub zu pushen muss 
``` bash mvn clean package docker:build docker:push``` 
verwendet werden.

Schließlich muss im Projekt-Verzeichnis noch 
``` bash docker-compose up -d``` 
ausgeführt werden, um die in der `docker-compose.yml` aufgelisteten Container zu starten.

## Testen der Anwendung
Die einzelnen Microservices sind wie folgt zu erreichen:
* Der Config-Service ist unter `http://localhost:8888` zu erreichen. Dabei wird eine Authentifizierung mit dem Benutzernamen `user` und dem Passwort `${CONFIG_SERVICE_PASSWORD` erwartet. Auf dem Config-Service sind alle YAML Konfigurationdateien für die anderen Server hinterlegt. Bspw. kann die Konfiguration des API-Gateways unter `http://localhost:8888/api-gateway-service.yml` aufgerufen werden.
* Das Spring Eureka Dashboard ist unter `http://localhost:8761` zu erreichen.
* Das API-Gateway ist unter `http://localhost:4000` zu erreichen. Alle Mappings können unter `http://localhost:4000/routes` eingesehen werden.
* Der OAuth2-Service ist unter `http://localhost:5000/uaa` bzw. `http://localhost:4000/oauth2-service/uaa` zu erreichen. Allerdings sollte folgende Meldung erscheinen: `Full authentication is required to access this resource`.
* Das Hystrix Dashboard ist unter `http://localhost:9000/hystrix` zu erreichen.
* Der Turbine Stream ist unter `http://localhost:8989/turbine.stream` zu erreichen. 
