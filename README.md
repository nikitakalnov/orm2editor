## Запуск программы
Для запуска orm2editor как отдельного приложения необходимо установить в локальный репозиторий зависимость **orm2diagram**.
Для этого необходимо выполнить команду:
```
mvn install:install-file -Dfile=<путь к ORM2-Diagram-Model.jar> -DgroupId=org.vstu -DartifactId=orm2diagram -Dversion=0.3 -Dpackaging=jar -DgeneratePom=true
```