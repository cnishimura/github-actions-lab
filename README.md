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

## Objetivo

Crear primer workflow básico y entender estructura.

### Pasos:

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

## Objetivo

Pipeline real que usa Java y Maven.

### Proyecto Java mínimo

Incluye `pom.xml`, clase `HelloWorld`, test `HelloWorldTest`.

### Workflow

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

## Objetivo

Implementar cache para acelerar builds Maven.

### ¿Por qué usar cache?

-   Reduce tiempo de compilación\
-   Evita descargar dependencias\
-   Optimiza el uso del runner

### Workflow Día 3

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

### Resultado esperado

En la primera ejecución:

    Cache not found

En ejecuciones siguientes:

    Cache restored successfully

------------------------------------------------------------------------

# 🎉 Estado actual del laboratorio

Días completados: - \[x\] Día 1\
- \[x\] Día 2\
- \[x\] Día 3

Siguiente paso: 👉 **Día 4 -- Artefactos y outputs**
