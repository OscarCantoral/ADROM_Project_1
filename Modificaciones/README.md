# Documentación Técnica - Proyecto ADROM v1.0

## Índice
1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Estructura del Proyecto](#estructura-del-proroyecto)
3. [Archivos Creados/Modificados](#archivos-creadosmodificados)
4. [Configuración de Docker](#configuración-de-docker)
5. [Base de Datos](#base-de-datos)
6. [Problemas Solucionados](#problemas-solucionados)
7. [Comandos de Uso](#comandos-de-uso)

---

## Descripción del Proyecto

**ADROM v1.0** es una aplicación de consola .NET que procesa texto:
1. Lee texto de entrada del usuario
2. Limpia y procesa el texto (remueve acentos, puntuación, espacios)
3. Consulta palabras clave en SQL Server
4. Genera un resultado final combinando coincidencias

**Tecnología:**
- .NET Core 3.1
- SQL Server 2019
- Docker y Docker Compose

---

## Estructura del Proyecto

```
ADROM_Project_1/
├── BusinessLogic/           # Capa de lógica de negocio
│   └── ExpressionService.cs
├── DataAccess/              # Capa de acceso a datos
│   ├── DatabaseConnection.cs
│   ├── ExpressionRepository.cs
│   └── TpplbrRepository.cs
├── Entities/                # Modelos de dominio
│   └── Expression.cs
├── Helpers/                 # Utilidades
│   └── StringHelper.cs
├── Program.cs               # Punto de entrada
├── Dockerfile               # Imagen Docker de la app
├── docker-compose.yml       # Orquestación de contenedores
├── ProyADROMv1.csproj      # Configuración del proyecto
└── ProyADROMv1.sln         # Solución Visual Studio
```

---

## Archivos Creados/Modificados

### 1. Dockerfile

```dockerfile
# Etapa de construcción (build)
FROM mcr.microsoft.com/dotnet/sdk:3.1 AS build
WORKDIR /src
COPY . .
RUN dotnet publish -c Release -o /app

# Etapa de ejecución (runtime)
FROM mcr.microsoft.com/dotnet/runtime:3.1
WORKDIR /app
COPY --from=build /app .
ENTRYPOINT ["dotnet", "ProyADROMv1.dll"]
```

**Explicación:**
- **Primera etapa (build):** Usa el SDK para compilar el proyecto .NET
- **Segunda etapa (runtime):** Solo copia los archivos necesarios para ejecutar, reduciendo el tamaño de la imagen final
- `--from=build`: Copia los archivos compilados de la etapa anterior

---

### 2. docker-compose.yml

```yaml
services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    depends_on:
      - sqlserver
    environment:
      - DB_CONNECTION_STRING=Server=sqlserver;Database=ProyADROM;User Id=sa;Password=Adrom2k7!;TrustServerCertificate=True;
    entrypoint: ["/bin/sh", "-c", "sleep 60 && dotnet ProyADROMv1.dll"]
    restart: unless-stopped

  sqlserver:
    image: mcr.microsoft.com/mssql/server:2019-latest
    environment:
      ACCEPT_EULA: Y
      SA_PASSWORD: Adrom2k7!
    ports:
      - "1433:1433"
    volumes:
      - sqlserver_data:/var/opt/mssql

volumes:
  sqlserver_data:
```

**Explicación:**
- **app:** Servicio de la aplicación .NET
  - `build`: Indica que debe construir la imagen desde el Dockerfile
  - `depends_on`: Establece dependencia con SQL Server
  - `environment`: Variable de entorno con la cadena de conexión
  - `entrypoint`: Espera 60 segundos para que SQL Server esté listo
  - `restart: unless-stopped`: Reinicia automáticamente si falla

- **sqlserver:** Servicio de base de datos
  - `image`: Imagen oficial de SQL Server 2019
  - `environment`: Credenciales y aceptación de licencia
  - `ports`: Expone el puerto 1433 para conexiones externas
  - `volumes`: Persistencia de datos entre reinicios

---

### 3. DatabaseConnection.cs (Modificado)

```csharp
using System;
using Microsoft.Data.SqlClient;

namespace ProyADROMv1.DataAccess
{
    public static class DatabaseConnection
    {
        // Lee la variable de entorno o usa la cadena por defecto
        private static readonly string ConnectionString = 
            Environment.GetEnvironmentVariable("DB_CONNECTION_STRING") 
            ?? "Server=10.10.10.99;Database=ProyADROM;User Id=proyadrom;Password=adrom2k7;TrustServerCertificate=True;";

        public static SqlConnection GetConnection()
        {
            return new SqlConnection(ConnectionString);
        }
    }
}
```

**Cambio realizado:**
- Línea 10: Comenta la cadena hardcodeada original
- Línea 11-12: Ahora lee de variable de entorno `DB_CONNECTION_STRING`
- Si no existe la variable, usa la cadena original por defecto

---

### 4. ExpressionService.cs (Modificado)

```csharp
public string CleanText(string input, List<Expression> excludedExpressions)
{
    // Protección contra null
    if (string.IsNullOrEmpty(input))
        return input ?? string.Empty;
        
    if (excludedExpressions == null)
        return input;
        
    foreach (var expression in excludedExpressions)
    {
        // Protección contra Text null
        if (expression?.Text != null)
            input = input.Replace(expression.Text, "", System.StringComparison.OrdinalIgnoreCase);
    }

    return input.Trim();
}
```

**Cambios realizados:**
- Líneas 33-35: Verifica si `input` es null o vacío
- Líneas 36-37: Verifica si `excludedExpressions` es null
- Líneas 39-41: Verifica si `expression.Text` es null antes de usar

---

## Base de Datos

### Credenciales de Conexión

| Parámetro | Valor |
|-----------|-------|
| Servidor (interno) | `sqlserver` |
| Servidor (externo) | `localhost,1433` |
| Base de datos | `ProyADROM` |
| **Usuario** | `sa` |
| **Contraseña** | `Adrom2k7!` |
| Trust Server Certificate | `True` |

**Connection String completo:**
```
Server=sqlserver;Database=ProyADROM;User Id=sa;Password=Adrom2k7!;TrustServerCertificate=True;
```

### Conexión desde Herramientas Externas

Para conectar con **Azure Data Studio** o **SSMS**:
- **Servidor:** `localhost,1433`
- **Usuario:** `sa`
- **Contraseña:** `Adrom2k7!`

### Estructura de la Base de Datos ProyADROM

```sql
-- Tabla 1: Expresiones excluidas
CREATE TABLE TTEXPS_EXCL (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    DE_EXPS VARCHAR(500)
);

-- Stored Procedure para consultar expresiones
CREATE PROCEDURE SP_TTEXPS_EXCL_Q01
AS
BEGIN
    SELECT DE_EXPS FROM TTEXPS_EXCL;
END;

-- Stored Procedure para insertar expresiones
CREATE PROCEDURE SP_TTEXPS_EXCL_I01
    @ISDE_EXPS VARCHAR(100)
AS
BEGIN
    INSERT INTO TTEXPS_EXCL (DE_EXPS) VALUES (@ISDE_EXPS);
END;

-- Tabla 2: Palabras clave
CREATE TABLE TPPLBR_CLAV (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    DE_PLBR VARCHAR(500)
);

-- Stored Procedure para consultar palabras clave
CREATE PROCEDURE SP_TPPLBR_CLAV_Q01
AS
BEGIN
    SELECT DE_PLBR FROM TPPLBR_CLAV;
END;
```

### Conexión desde Herramientas Externas

Para conectar con Azure Data Studio o SSMS:
- **Servidor:** `localhost,1433`
- **Usuario:** `sa`
- **Contraseña:** `Adrom2k7!`

---

## Problemas Solucionados

### Problema 1: Docker no podía construir imagen con `+`
**Error:**
```
FROM mcr.microsoft.com/mssql/server:2019-latest + .NET SDK
```
**Solución:** Usar `docker-compose` para orquestar múltiples contenedores en lugar de combinar imágenes.

---

### Problema 2: La app iniciaba antes que SQL Server
**Error:**
```
A network-related or instance-specific error occurred while establishing a connection to SQL Server
```
**Solución:** Agregar `sleep 60` en el entrypoint de la app para esperar que SQL Server esté listo.

---

### Problema 3: Base de datos se perdía al reiniciar
**Error:**
```
Cannot open database "ProyADROM" requested by the login
```
**Solución:** Agregar volumen persistente en `docker-compose.yml`:
```yaml
volumes:
  - sqlserver_data:/var/opt/mssql
```

---

### Problema 4: NullReferenceException en CleanText
**Error:**
```
Object reference not set to an instance of an object
```
**Solución:** Agregar verificaciones de null en `ExpressionService.cs` para `input`, `excludedExpressions`, y `expression.Text`.

---

### Problema 5: Variable de entorno con `:` no funcionaba
**Error:** La variable `ConnectionStrings:DefaultConnection` no se leía correctamente.
**Solución:** Cambiar a nombre simple `DB_CONNECTION_STRING` sin `:`.

---

## Comandos de Uso

### Iniciar los servicios
```bash
docker compose up -d
```

### Ver estado de contenedores
```bash
docker ps -a
```

### Ver logs de la aplicación
```bash
docker compose logs app
```

### Ver logs de SQL Server
```bash
docker compose logs sqlserver
```

### Detener los servicios
```bash
docker compose down
```

### Detener y eliminar volúmenes (borra datos)
```bash
docker compose down -v
```

### Reconstruir imagen sin caché
```bash
docker compose build --no-cache app
docker compose up -d app
```

### Probar la aplicación con texto de prueba
```bash
docker compose exec app sh -c 'echo "Hola mundo, esto es una prueba" | dotnet ProyADROMv1.dll'
```

### Conectarse a SQL Server desde terminal
```bash
docker exec -it adrom_project_1-sqlserver-1 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Adrom2k7!' -C -d ProyADROM
```

### Verificar tablas en la base de datos
```bash
docker exec adrom_project_1-sqlserver-1 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Adrom2k7!' -C -d ProyADROM -Q "SELECT name FROM sys.tables"
```

### Ver procedimientos almacenados
```bash
docker exec adrom_project_1-sqlserver-1 /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P 'Adrom2k7!' -C -d ProyADROM -Q "SELECT name FROM sys.procedures"
```

---

## Notas Importantes

1. **Persistencia de datos:** Los datos de SQL Server se guardan en el volumen `sqlserver_data`. Si quieres borrar todo y empezar limpio, usa `docker compose down -v`.

2. **Contraseña de SQL Server:** La contraseña `Adrom2k7!` está configurada en:
   - `docker-compose.yml` (variable `SA_PASSWORD`)
   - `DB_CONNECTION_STRING` (variable de entorno)

3. **Puerto 1433:** Expuesto en el host para permitir conexiones externas (Azure Data Studio, SSMS, etc.)

4. **Network Docker:** Ambos contenedores comparten la red `adrom_project_1_default`, permitiendo que `app` se conecte a `sqlserver` por el hostname `sqlserver`.

---

## Flujo de Ejecución

```
┌─────────────────────────────────────────────────────────────────┐
│                    docker compose up -d                         │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────┐    ┌─────────────────────┐
│   sqlserver     │    │        app          │
│  (SQL Server)   │    │   (.NET Runtime)    │
│                 │    │                     │
│  Puerto: 1433   │◄───┤  sleep 60 segundos  │
│  Hostname:      │    │  espera a SQL       │
│  sqlserver      │    │  Server             │
└─────────────────┘    └─────────────────────┘
                              │
                              ▼
                    ┌─────────────────────┐
                    │   Console.ReadLine  │
                    │   (Espera input)    │
                    └─────────────────────┘
                              │
                              ▼
                    ┌─────────────────────┐
                    │  Procesa texto,     │
                    │  consulta BD,       │
                    │  genera resultado   │
                    └─────────────────────┘
```
