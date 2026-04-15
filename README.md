![image](https://github.com/user-attachments/assets/ba8b3dda-b7dc-489c-ac94-18369a9b58b8)
# Proyecto ADROM v1.0

## Propuesta de arquitectura SISTEMA DE SIMULACION DE REACCIONES QUIMICAS

==============================
ARQUITECTURA DE ALTO NIVEL
==============================

        [ CLIENTE DESKTOP ]
        (JavaFX / Compose MP)
                |
                |  HTTPS
                v
        [ BACKEND - SPRING BOOT ]
                |
                v
        [ POSTGRESQL (JSONB) ]


        [ CLIENTE MÓVIL ]
     (Android - Jetpack Compose)
                |
                |  HTTPS
                v
        [ BACKEND (ENFOCADO EN LECTURA) ]


==============================
ESTRUCTURA DEL BACKEND
==============================

Aplicación modular única:

- chem-core
  Lógica de dominio para átomos, enlaces y moléculas
  Modelado de grafos y validaciones

- reaction-engine
  Reglas de transformación sobre grafos moleculares
  Lógica de ejecución de reacciones

- protein-builder
  Ensamblaje de estructuras complejas (cadenas, proteínas)
  Lógica de composición estructural

- simulation-engine
  Procesos computacionales (energía, interacciones)
  Tareas asíncronas / de larga duración

- persistence
  Capa de acceso a datos
  Manejo y mapeo de JSONB

- api
  Punto de entrada para los clientes
  Capa de orquestación


==============================
RESPONSABILIDADES DE CLIENTES
==============================

[ CLIENTE DESKTOP ]

- Creación de moléculas (editor basado en grafos)
- Construcción de reacciones
- Visualización avanzada (2D / 3D)
- Envío de modelos estructurados al backend


[ CLIENTE MÓVIL ]

- Solo visualización
- Renderizado en AR (Sceneform / ARCore)
- Consumo de datos procesados/simplificados


==============================
CARACTERÍSTICAS DE LOS DATOS
==============================

- Estructuras moleculares almacenadas como grafos flexibles (JSONB)
- Reacciones representadas como transformaciones
- Proteínas como composiciones jerárquicas
- Las simulaciones generan datos derivados, no estáticos

- El sistema prioriza:
  Flexibilidad > esquema rígido
  Computación en backend > lógica en base de datos


==============================
RESUMEN DEL FLUJO
==============================

- Desktop crea → Backend valida y procesa → Se almacena en BD
- Backend calcula → Resultados almacenados → Clientes visualizan
- Móvil consume → Solo datos preprocesados


==============================
PRINCIPIOS DE DISEÑO
==============================

- Separación clara:
  Creación (Desktop) vs Visualización (Móvil)

- El backend controla:
  Lógica, validación, computación

- La base de datos almacena:
  Estado, no comportamiento
