# scripts-jenkins
Mes scipts de lancement Jenkins

## Utilisation :
### Lancement
```
cd ~/dev/jenkins
bash jenkins.sh start
```
### Arrêt
```
cd ~/dev/jenkins
bash jenkins.sh stop
```

## Arborescence des fichiers :
```
~/dev
    |-- home/
        |-- ...
    |-- jenkins.war
    |-- jenkins.sh
    |-- launch.sh
    |-- stop.sh
```
En utilisant la variable d'environnement *JENKINS_HOME* comme ci-dessous dans le script de lancement, on va déployer le war dans le dossier de notre choix plutôt que dans le /home.

```
export JENKINS_HOME="~/dev/jenkins/home/"
```
