# github-actions-lab
Repositorio de laboratorio para practicar GitHub Actions desde cero
hasta nivel avanzado: CI, CD, Docker, Sonar, Kubernetes, seguridad y
workflows reutilizables.

# 📚 **Semana 1 -- Índice de contenidos**

> Esta sección ordena todo el material de aprendizaje de GitHub Actions
> por días, siguiendo un plan progresivo desde nivel básico hasta
> intermedio.

### **📅 Semana 1**

1.  **Día 1 -- Crear el primer workflow básico**\
    *Estructura de GitHub Actions, ejecución por push y logs básicos.*

2.  **Día 2 -- Compilar un proyecto Java con Actions**\
    *Uso de `checkout`, `setup-java`, Maven/Gradle y ejecución de
    tests.*

3.  **Día 3 -- Cache de dependencias y optimización del pipeline**\
    *Implementar `actions/cache` para Maven/Gradle y comprender claves
    de caché.*

4.  **Día 4 -- Artefactos y outputs**\
    *Subir el `.jar` generado y entender cómo compartir artefactos entre
    jobs.*

5.  **Día 5 -- Matrices y ejecución paralela**\
    *Tests en múltiples versiones de Java (17, 21) o sistemas
    operativos.*

------------------------------------------------------------------------

# 📘 **Día 1 -- Introducción a GitHub Actions (Paso a Paso)**

## 🎯 Objetivo del día

Crear el primer workflow de GitHub Actions y entender la estructura
básica:\
`on`, `jobs`, `runs-on`, `steps`.

------------------------------------------------------------------------

## ✅ **1. Crear el repositorio**

1.  Ir a GitHub → **New repository**
2.  Nombre: `github-actions-lab`
3.  Activar **Add README**
4.  Crear el repositorio

------------------------------------------------------------------------

## ✅ **2. Abrir la pestaña Actions**

1.  En el repositorio, ir a la pestaña **Actions**
2.  Seleccionar la opción:\
    **"set up a workflow yourself"**

Esto abrirá un archivo en la ruta:

    .github/workflows/ci-basico.yml

------------------------------------------------------------------------

## ✅ **3. Crear el workflow básico**

Pegar el siguiente contenido:

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

## ✅ **4. Guardar el workflow**

1.  Hacer clic en **Commit changes**
2.  Confirmar el commit en la rama `main`

------------------------------------------------------------------------

## ✅ **5. Disparar el workflow**

1.  Editar el archivo `README.md`\
2.  Agregar una línea nueva:

```{=html}
<!-- -->
```
    Probando GitHub Actions - Día 1

3.  Hacer **Commit changes**

Al hacer push → GitHub Actions ejecuta el workflow.

------------------------------------------------------------------------

## ✅ **6. Revisar la ejecución**

1.  Ir a la pestaña **Actions**
2.  Abrir el workflow **CI basico**
3.  Ver el job **say-hello**
4.  Confirmar que aparece el mensaje:

```{=html}
<!-- -->
```
    Hola desde GitHub Actions 🚀

------------------------------------------------------------------------

## 🎉 **Resultado del Día 1**

-   Primer workflow funcionando\
-   Comprensión de la estructura básica\
-   Repositorio listo para continuar con el Día 2

