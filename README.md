# github-actions-lab

Repositorio de laboratorio para practicar GitHub Actions desde cero
hasta nivel avanzado: CI, CD, Docker, Sonar, Kubernetes, seguridad y
workflows reutilizables.

# 📚 Semana 1 -- Índice de contenidos

> Esta sección ordena todo el material de aprendizaje de GitHub Actions
> por días.

### 📅 Semana 1

1.  **Día 1 -- Crear el primer workflow básico**\
2.  **Día 2 -- Compilar un proyecto Java con Actions**\
3.  **Día 3 -- Cache de dependencias y optimización del pipeline**\
4.  **Día 4 -- Artefactos y outputs**\
5.  **Día 5 -- Matrices y ejecución paralela**

------------------------------------------------------------------------

# 📘 Día 1 -- Introducción a GitHub Actions (Paso a Paso)

## 🎯 Objetivo

Crear el primer workflow básico y entender estructura: `on`, `jobs`,
`steps`.

### ✔ 1. Crear repositorio

-   Nombre: `github-actions-lab`
-   Add README activado

### ✔ 2. Abrir pestaña Actions → "set up a workflow yourself"

Esto crea:

    .github/workflows/ci-basico.yml

### ✔ 3. Crear workflow

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

### ✔ 4. Commit del archivo

### ✔ 5. Disparar workflow editando README

### ✔ 6. Ver ejecución en Actions

------------------------------------------------------------------------

# 📘 Día 2 -- Compilar y ejecutar tests Java (CI Real)

## 🎯 Objetivo

Crear pipeline real que: - Descargue el código - Instale Java 17 -
Compile proyecto Maven - Ejecute pruebas unitarias

------------------------------------------------------------------------

## ✔ 1. Subir proyecto Java

El proyecto mínimo contiene:

    pom.xml
    src/main/java/com/example/HelloWorld.java
    src/test/java/com/example/HelloWorldTest.java

------------------------------------------------------------------------

## ✔ 2. Crear archivo del workflow

Ruta:

    .github/workflows/ci-java.yml

Contenido:

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
      - name: Checkout del código
        uses: actions/checkout@v4

      - name: Configurar Java 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'

      - name: Compilar proyecto con Maven
        run: mvn -B -f java_project/pom.xml compile

      - name: Ejecutar tests
        run: mvn -B -f java_project/pom.xml test
```

------------------------------------------------------------------------

## ✔ 3. Guardar workflow

Click en **Commit changes** → rama `main`.

------------------------------------------------------------------------

## ✔ 4. Disparar workflow

Editar README y hacer commit.

------------------------------------------------------------------------

## ✔ 5. Verificar ejecución

En **Actions** debe aparecer:

-   Workflow: **CI Java - Día 2**
-   Job: `build-and-test`
-   Pasos ejecutados exitosamente:
    -   Checkout
    -   Setup Java 17
    -   Compile
    -   Test

Salida esperada:

    Running com.example.HelloWorldTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
    BUILD SUCCESS

------------------------------------------------------------------------

# 🎉 Resultado del Día 2

Has logrado:

-   Pipeline Java completamente funcional\
-   Ejecución automática de Maven y tests\
-   Uso profesional de `checkout` y `setup-java`

Listo para avanzar al **Día 3: Cache Maven y optimización**.
