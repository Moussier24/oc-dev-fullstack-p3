# Portail Application

## Prérequis
- Java 17
- Maven
- MySQL 8.0 ou supérieur

## Installation et lancement du projet

### 1. Base de données

1. Installer MySQL si ce n'est pas déjà fait
   
2. Créer une base de données :
    ```sql
    CREATE DATABASE portail;
    ```

3. Créer un utilisateur et donner les droits :
    ```sql
    CREATE USER 'portail_user'@'localhost' IDENTIFIED BY 'votre_mot_de_passe';
    GRANT ALL PRIVILEGES ON portail. TO 'portail_user'@'localhost';
    FLUSH PRIVILEGES;
    ```

4. Les scripts de migration (`V1__create_initial_schema.sql`) seront exécutés automatiquement au démarrage de l'application via Flyway

### 2. Configuration de l'application

1. Vérifier que les propriétés de connexion à la base de données dans `src/main/resources/application.properties` sont correctes :

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/portail
    spring.datasource.username=portail_user
    spring.datasource.password=votre_mot_de_passe
    ```

### 3. Lancement de l'application

1. Cloner le projet
   
2. Se placer à la racine du projet
   
3. Compiler et lancer l'application :
```shell
mvn clean install
mvn spring-boot:run
```

## Lien vers le Swagger

1. Ouvrir un navigateur et accéder à l'URL suivante :
    ```
    http://localhost:8080/swagger-ui.html
    ```
