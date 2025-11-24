# github-actions-lab

Repositorio de laboratorio para practicar GitHub Actions desde cero
hasta nivel avanzado: CI, CD, Docker, Sonar, Kubernetes, seguridad y
workflows reutilizables.

# 📚 Semana 1 -- Índice de contenidos

### 📅 Semana 1

1.  **Día 1 -- Crear el primer workflow básico**
2.  **Día 2 -- Compilar un proyecto Java con Actions**
3.  **Día 3 -- Cache de dependencias y optimización**
4.  **Día 4 -- Artefactos y outputs**
5.  **Día 5 -- Matrices y ejecución paralela**

------------------------------------------------------------------------

# 📘 Día 1 -- Introducción a GitHub Actions

## 🎯 Objetivo

Crear primer workflow básico y entender estructura.

### ✔ Pasos:

-   Crear repo\
-   Abrir Actions → "set up a workflow yourself"\
-   Crear archivo `.github/workflows/ci-basico.yml`

``` yaml
name: CI basico
on:
  push:
    branches: [ "main" ]
  pull_request:
jobs:
  say-hello:
    runs-on: ubuntu-latest
    steps:
      - name: Mostrar mensaje
        run: echo "Hola desde GitHub Actions 🚀"
```

------------------------------------------------------------------------

# 📘 Día 2 -- Compilar y ejecutar tests Java

## 🎯 Objetivo

Pipeline real que usa Java y Maven.

### ✔ Proyecto Java mínimo

Incluye `pom.xml`, clase `HelloWorld`, test `HelloWorldTest`.

### ✔ Workflow

Archivo: `.github/workflows/ci-java.yml`

``` yaml
name: CI Java - Día 2
on:
  push:
    branches: [ "main" ]
  pull_request:
jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
      - run: mvn -B -f java_project/pom.xml compile
      - run: mvn -B -f java_project/pom.xml test
```

------------------------------------------------------------------------

# 📘 Día 3 -- Cache Maven y Optimización

## 🎯 Objetivo

Implementar cache para acelerar builds Maven.

### ✔ Workflow Día 3

Archivo: `.github/workflows/ci-java-cache.yml`

``` yaml
name: CI Java - Día 3 (Cache Maven)

on:
  push:
    branches: [ "main" ]
  pull_request:

jobs:
  build-test-cache:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Setup Java 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Configurar cache de Maven
        uses: actions/cache@v4
        with:
          path: ~/.m2/repository
          key: maven-${{ hashFiles('java_project/pom.xml') }}
          restore-keys: |
            maven-

      - name: Compilar proyecto
        run: mvn -B -f java_project/pom.xml compile

      - name: Ejecutar tests
        run: mvn -B -f java_project/pom.xml test
```

------------------------------------------------------------------------

# 📘 Día 4 -- Artefactos y Outputs

## 🎯 Objetivo

Aprender a subir y descargar artifacts, usar múltiples jobs y control de
dependencias entre jobs.

------------------------------------------------------------------------

## ✔ Workflow Día 4

Archivo: `.github/workflows/ci-java-artifacts.yml`

``` yaml
name: CI Java - Día 4 (Artifacts y Outputs)

on:
  push:
    branches: [ "main" ]
  pull_request:

jobs:

  build:
    name: Build y generar JAR
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Setup Java 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Compilar proyecto y generar JAR
        run: mvn -B -f java_project/pom.xml clean package

      - name: Subir JAR como artifact
        uses: actions/upload-artifact@v4
        with:
          name: java-jar
          path: java_project/target/*.jar

  test:
    name: Descargar artifact y ejecutar pruebas de validación
    needs: build
    runs-on: ubuntu-latest

    steps:
      - name: Descargar artifact del job build
        uses: actions/download-artifact@v4
        with:
          name: java-jar

      - name: Listar archivos descargados
        run: ls -R .

      - name: Validación simple del artifact
        run: |
          echo "Artifact recibido correctamente."
          echo "Listo para usar en despliegue o dockerización."
```

------------------------------------------------------------------------

# 🎉 Estado del laboratorio

Días completados: 
- \[x\] Día 1\
- \[x\] Día 2\
- \[x\] Día 3\
- \[x\] Día 4

Siguiente paso: 👉 **Día 5 -- Matrices y ejecución paralela (nivel
avanzado)**
