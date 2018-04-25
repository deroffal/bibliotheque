# MapStruct

MapStruct est un framework Java permettant d'effectuer du mapping entre deux classes. 
Il s'agit d'un *annotation processor* : MapStruct utilise des annotations qui vont être lues et traitées pendant la phase de compilation de l'application pour générer le code de mapping.

## Installation

Procédure d'installation pour une version de java >=8. Ajouter dans le `pom.xml` : 

```
  <properties>
    <mapstruct.version>1.2.0.Final</mapstruct.version>
  </properties>
  ...
  <dependencies>
      <dependency>
        <groupId>org.mapstruct</groupId>
        <artifactId>mapstruct-jdk8</artifactId> <!-- disponible à partir de java 8-->
        <version>${mapstruct.version}</version>
      </dependency>
  <dependencies>
  ...
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId> <!-- Version managée par springboot-->
        <configuration>
          <source>${java.version}</source>
          <target>${java.version}</target>
          <annotationProcessorPaths>
            <path>
              <groupId>org.mapstruct</groupId>
              <artifactId>mapstruct-processor</artifactId>
              <version>${mapstruct.version}</version>
            </path>
          </annotationProcessorPaths>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

## Utilisation

Exemple de l'interface [UserMapper.java](../../master/src/main/java/fr/deroffal/portail/authentification/mapping/UserMapper.java), qui est chargée d'effectuer le mapping entre l'Entity et le DTO représentant un
utilisateur.
 
 L'interface est annotée par `@Mapper`, ce qui signifie qu'une implémentation de celle-ci devra être générée durant la compilation. On peut ajouter la propriété `componentModel = 
 "spring"` pour que la classe générée soit annotée par `@Component` et soit donc injectable par Spring.
