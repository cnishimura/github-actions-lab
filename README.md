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

# 📘 Día 5 -- Matrices y Ejecución Paralela

## 🎯 Objetivo

Ejecutar tests en diferentes versiones de Java de forma paralela
mediante `strategy.matrix`.

------------------------------------------------------------------------

## ✔ Workflow Día 5

Archivo: `.github/workflows/ci-java-matrix.yml`

``` yaml
name: CI Java - Día 5 (Matrices y Ejecución Paralela)

on:
  push:
    branches: [ "main" ]
  pull_request:

jobs:
  test-matrix:
    name: Test con matriz de versiones Java
    runs-on: ubuntu-latest

    strategy:
      matrix:
        java: [ '17', '21' ]

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Configurar Java ${{ matrix.java }}
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: ${{ matrix.java }}

      - name: Compilar proyecto con Maven
        run: mvn -B -f java_project/pom.xml compile

      - name: Ejecutar tests en Java ${{ matrix.java }}
        run: mvn -B -f java_project/pom.xml test
```

------------------------------------------------------------------------

# 🎉 Estado del laboratorio

Días completados: - \[x\] Día 1\
- \[x\] Día 2\
- \[x\] Día 3\
- \[x\] Día 4\
- \[x\] Día 5

Semana 1 COMPLETA.\
Próxima semana → **Workflows avanzados, Reusables, OIDC, Variables y
Secrets, Docker y Deploy.**


# Día 6 – Uso de Workflows Reutilizables en GitHub Actions

En este día aprenderás a crear **workflows reutilizables**, una funcionalidad avanzada que permite centralizar lógicas CI/CD y reutilizarlas desde otros workflows.

---

## 🎯 Objetivo del Día 6
- Crear un **workflow reutilizable** (`reusable-java-build.yml`)
- Consumirlo desde un workflow caller (`ci-java-call-reusable.yml`)
- Aprender a usar `workflow_call`, `inputs`, `secrets` y parámetros.
- Validar la ejecución en GitHub Actions.

---

# 🧩 1. Crear workflow reutilizable

Archivo: `.github/workflows/reusable-java-build.yml`

```yaml
name: Java Build Reusable Workflow

on:
  workflow_call:
    inputs:
      java-version:
        required: true
        type: string
      build-command:
        required: true
        type: string

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          distribution: "temurin"
          java-version: ${{ inputs.java-version }}

      - name: Build project
        run: ${{ inputs.build-command }}
```

✔ Este workflow **no se ejecuta directamente**  
✔ Solo puede ser llamado desde otro workflow

---

# 🧲 2. Crear workflow caller

Archivo: `.github/workflows/ci-java-call-reusable.yml`

```yaml
name: CI Java Call Reusable

on:
  push:
    branches:
      - main

jobs:
  call-reusable:
    uses: ./.github/workflows/reusable-java-build.yml
    with:
      java-version: "17"
      build-command: "mvn -B package"
```

✔ Llama al workflow reutilizable  
✔ Envía los parámetros necesarios  
✔ Construye el proyecto usando Maven

---

# 📌 3. Ejecución correcta

Una ejecución exitosa muestra:

- Setup Java (temurin 17)
- Build del proyecto con Maven
- Uso del workflow llamado (caller)
- Status: **Success**

---

# 🧯 4. Principales errores solucionados

### ❌ Error: "Unrecognized named-value: 'secrets'"
Ocurre si se declara `secrets:` en el reusable workflow sin usar `secret:`.

Solución: eliminar el parámetro opcional o declararlo correctamente.

### ❌ Error: ruta del workflow
Debes usar:

```
uses: ./.github/workflows/reusable-java-build.yml
```

---

# ✅ 5. Resultado final del Día 6

- Tienes workflows **modulares**
- Separación clara entre caller y reusable
- Mejor mantenimiento y escalabilidad
- Preparado para pipelines avanzados

---

# Día 7: Variables, Secrets y Environments

## 1. Introducción
En este día aprendimos a usar Variables, Secrets y Environments en GitHub Actions.

## 2. Variables
- Variables a nivel de repositorio
- Variables a nivel de organización
- Variables por environment

## 3. Secrets
- Secrets del repositorio
- Secrets por environment
- Buenas prácticas

## 4. Environments
- Creación de ambientes: dev, qa, prod
- Reglas de protección
- Aprobaciones manuales
- Uso real en CI/CD

## 5. Workflow de ejemplo
```yaml
name: CI/CD con Environments (Día 7)

on:
  workflow_dispatch:
    inputs:
      environment:
        description: "Ambiente de despliegue"
        required: true
        type: choice
        options:
          - dev
          - qa
          - prod

jobs:
  deploy:
    runs-on: ubuntu-latest
    environment:
      name: ${{ inputs.environment }}

    steps:
      - name: Print environment info
        run: |
          echo "Desplegando en: ${{ inputs.environment }}"

      - name: Usar secrets del environment
        run: echo "Secret cargado correctamente"
        env:
          API_KEY: ${{ secrets.API_KEY }}
```

## 6. Resultado esperado
- dev ejecuta inmediatamente
- qa requiere aprobación si se activa
- prod puede tener reglas más estrictas

## 7. Conclusión
Este día marca el inicio de la seguridad y control profesional en CI/CD.


