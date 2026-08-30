# ChemXR App (Android) — README de desarrollo

Aplicación Android en **Jetpack Compose** (Kotlin) que consume la API de ChemXR
(Retrofit) y guarda la sesión localmente con DataStore.

> Este documento describe el estado real del proyecto a fecha de desarrollo.
> El `README.md` raíz se mantiene intacto con la descripción general del proyecto.

## Stack
- Kotlin / Jetpack Compose (Material 3)
- Hilt (inyección de dependencias)
- Retrofit 2 + Gson
- AndroidX DataStore
- Navigation Compose
- minSdk 26 / targetSdk 36

## Conexión con el backend
La URL base se define en `app/src/main/java/com/example/ch3mxr/di/RemoteModule.kt`:

```kotlin
private const val BASE_URL = "http://192.168.18.23:8080/"
```

- Se permite tráfico HTTP (cleartext) solo hacia esa IP en
  `app/src/main/res/xml/network_security_config.xml`.
- Si cambia la IP/máquina del backend, actualizar **ambos** archivos:
  `RemoteModule.kt` y `network_security_config.xml`.
- El backend debe estar corriendo y accesible desde el dispositivo/emulador
  (PC y teléfono en la misma red).

## Funcionalidades implementadas

### Login (autenticación manual)
- `POST /auth/login` con `LoginRequest` -> `LoginResponse`.
- Muestra el error real del servidor (`401`, sin conexión, etc.).
- Al éxito construye un `SessionData` real (id, usuario, correo, nombre, apellido)
  y navega al gráfico principal.
- Archivos: `data/remote/AuthApiService.kt`, `RemoteRepository.kt`,
  `ui/features/SessionViewModel.kt`, `ui/features/auth/LoginScreen.kt`.

### Registro
- `POST /auth/register` con `RegisterRequest` -> `RegisterResponse`.
- Validación en pantalla: campos obligatorios y correo con `@`.
- En éxito muestra "Registro exitoso, ahora puedes iniciar sesión" (verde)
  y vuelve al login.
- Errores del backend (`409` usuario/correo duplicado, `400`, `401`, sin red)
  se muestran en rojo con el mensaje del servidor.
- Archivos: `data/domain/dto/RegisterRequest.kt`, `RegisterResponse.kt`,
  `data/remote/AuthApiService.kt`, `RemoteRepository.kt`,
  `ui/features/SessionViewModel.kt`, `ui/features/auth/RegisterScreen.kt`,
  `ui/routes/AuthGraph.kt`.

### Sesión local (DataStore)
- `LocalData.saveSessionData()` guarda `SessionData` en JSON.
- `SessionViewModel.makeLogin` / `registerUser` delegan al repositorio.

## Arquitectura de red
```
Ui (Compose)            -> LoginScreen / RegisterScreen / MainGraph
ViewModel               -> SessionViewModel
Repository              -> RemoteRepository
API service (Retrofit)  -> AuthApiService (@POST auth/login, auth/register)
DI (Hilt)               -> RemoteModule (provee Retrofit + BaseUrl)
```

## DTOs
- `LoginRequest(username, password)`
- `LoginResponse(id, username, email, displayName, firstName, lastName)`
- `RegisterRequest(username, password, firstName, lastName, email, phone?)`
- `RegisterResponse(id, username, email, status)`

## Cómo compilar / ejecutar
```bash
./gradlew :app:assembleDebug
```

El APK queda en:

```
app/build/outputs/apk/debug/app-debug.apk
```

O abre el proyecto en Android Studio y pulsa Run.

## Credenciales de prueba
| Usuario | Contraseña |
|---|---|
| `admin` | `1234` |

> El formulario de registro de la app también crea usuarios reales en la BD.

## Flujos y estados
- Sin sesión -> `AuthGraph` (Login / Registro).
- Con sesión -> `MainGraph` (Home con cursos, Grupos, etc.).
- Cerrar sesión -> `clearSession()` + vuelve a Auth.

## Notas
- Login/registro de **Google y Facebook** aún crean sesión local sin pasar por la API.
- Cursos y manuales (PDF) siguen hardcodeados en `ui/model/Course.kt` y `assets/manuals/`.
- Grupos: datos en memoria, sin persistencia aún.
- `TokenResponse` quedó sin uso (el login devuelve datos de usuario, no token OAuth2 aún).

## Pendientes
- [ ] Persistir token OAuth2 y proteger endpoints fuera de `/auth/**`
- [ ] Consultar cursos/grupos desde la API
- [ ] Sincronizar Google/Facebook con el backend