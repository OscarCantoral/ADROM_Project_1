# Autenticación con Google Sign-In — ChemXR

## Resumen

La app ChemXR implementa inicio de sesión con Google mediante **Android Credential Manager API**. Esta es la API moderna recomendada por Google, que reemplaza al deprecated `Google Sign-In SDK` (`play-services-auth`) y a `Smart Lock for Passwords`.

El flujo maneja dos casos:

- **Auto sign-in** al abrir la app si el usuario ya inició sesión previamente.
- **Sign-in manual** desde el botón Google en la pantalla de login.

---

## Dependencias

Agregadas en `app/build.gradle.kts`:

```kotlin
implementation("androidx.credentials:credentials:1.7.0-alpha02")
implementation("androidx.credentials:credentials-play-services-auth:1.7.0-alpha02")
implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
```

| Librería | Propósito |
|---|---|
| `credentials` | Base del Credential Manager: `CredentialManager`, `GetCredentialRequest`, `CustomCredential`, excepciones |
| `credentials-play-services-auth` | Integración con Google Play Services para mostrar el bottom sheet |
| `googleid` | Parseo del `GoogleIdTokenCredential` a partir de la respuesta del Credential Manager |

---

## Permisos

En `AndroidManifest.xml` se agregó:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

Requerido para que Credential Manager pueda obtener el token ID de Google.

---

## Archivos creados

### `Config.kt`

Contiene las constantes de configuración. Debes reemplazar el valor con tu **Web Client ID** de Google Cloud Console:

```kotlin
package com.example.ch3mxr

object Config {
    const val WEB_CLIENT_ID = "REEMPLAZA_ESTO_CON_TU_WEB_CLIENT_ID"
}
```

### `GoogleAuthManager.kt`

Clase principal que encapsula toda la lógica del Credential Manager.

#### Constructor

```kotlin
class GoogleAuthManager(private val context: Context)
```

Recibe un `Context` (normalmente la `Activity`). Internamente crea:

```kotlin
private val credentialManager = CredentialManager.create(context)
```

#### Métodos

| Método | Descripción |
|---|---|
| `tryAutoSignIn(): GoogleIdTokenCredential?` | Intento silencioso de auto sign-in al abrir la app. Usa `GetGoogleIdOption` con `filterByAuthorizedAccounts=true` y `autoSelectEnabled=true`. Retorna `null` si no hay cuentas autorizadas previas. |
| `signInWithGoogle(): GoogleIdTokenCredential?` | Llamado desde el botón Google. Usa `GetSignInWithGoogleOption` para mostrar el bottom sheet con todas las cuentas disponibles. Retorna `null` si el usuario cancela o hay error. |

Ambos métodos retornan un `GoogleIdTokenCredential?` con los siguientes campos:

| Campo | Tipo | Descripción |
|---|---|---|
| `idToken` | `String` | Token JWT firmado por Google. Debe validarse contra el backend. |
| `id` | `String` | `sub` del token — ID único del usuario en Google |
| `displayName` | `String?` | Nombre del perfil público |
| `profilePictureUri` | `Uri?` | URL de la foto de perfil |
| `email` | `String?` | Correo electrónico |

#### Manejo de errores

| Excepción | Cuándo ocurre | Manejo |
|---|---|---|
| `NoCredentialException` | No hay cuentas autorizadas en el dispositivo (auto sign-in) | Se ignora y se muestra la pantalla de login |
| `GetCredentialException` | Error genérico al obtener credenciales | Se loguea y retorna `null` |
| `GoogleIdTokenParsingException` | El token devuelto no es un Google ID Token válido | Se loguea y retorna `null` |

#### Código completo

```kotlin
package com.example.ch3mxr

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import java.security.SecureRandom

class GoogleAuthManager(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    suspend fun tryAutoSignIn(): GoogleIdTokenCredential? {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(Config.WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .setNonce(generateSecureRandomNonce())
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            handleSignInResponse(result)
        } catch (e: NoCredentialException) {
            Log.d("GoogleAuth", "No authorized accounts found", e)
            null
        } catch (e: GetCredentialException) {
            Log.e("GoogleAuth", "Get credential failed", e)
            null
        }
    }

    suspend fun signInWithGoogle(): GoogleIdTokenCredential? {
        val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(
            serverClientId = Config.WEB_CLIENT_ID
        )
            .setNonce(generateSecureRandomNonce())
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            handleSignInResponse(result)
        } catch (e: GetCredentialException) {
            Log.e("GoogleAuth", "Sign in failed", e)
            null
        }
    }

    private fun handleSignInResponse(result: GetCredentialResponse): GoogleIdTokenCredential? {
        val credential = result.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            return try {
                GoogleIdTokenCredential.createFrom(credential.data)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("GoogleAuth", "Failed to parse Google ID token", e)
                null
            }
        }
        return null
    }

    private fun generateSecureRandomNonce(): String {
        val random = SecureRandom()
        val bytes = ByteArray(32)
        random.nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.NO_PADDING or Base64.NO_WRAP or Base64.URL_SAFE)
    }
}
```

---

## Archivos modificados

### `MainActivity.kt`

Cambios realizados:

1. Se crea `GoogleAuthManager(this)` al inicio de `onCreate`.
2. Se agrega estado `"loading"` como pantalla inicial.
3. `LaunchedEffect(Unit)` dentro del caso `"loading"` ejecuta `tryAutoSignIn()`:
   - **Éxito** → `currentScreen = "home"` (navega directamente al Home)
   - **Fracaso** → `currentScreen = "login"` (muestra pantalla de login)
4. Se pasa `googleAuthManager` a `LoginScreenImproved`.

```kotlin
val googleAuthManager = GoogleAuthManager(this)

setContent {
    Ch3mxrTheme {
        var currentScreen by remember { mutableStateOf("loading") }
        var selectedGroup by remember { mutableStateOf("") }

        when (currentScreen) {
            "loading" -> {
                LaunchedEffect(Unit) {
                    val credential = googleAuthManager.tryAutoSignIn()
                    currentScreen = if (credential != null) "home" else "login"
                }
            }
            "login" -> {
                LoginScreenImproved(
                    onLoginSuccess = { currentScreen = "home" },
                    onRegisterClick = { currentScreen = "register" },
                    googleAuthManager = googleAuthManager
                )
            }
            // ... resto de pantallas
        }
    }
}
```

### `LoginScreen.kt`

Cambios realizados:

1. Se agrega el parámetro `googleAuthManager: GoogleAuthManager`.
2. El `onClick = { }` del botón Google ahora ejecuta:

```kotlin
onClick = {
    scope.launch {
        isLoading = true
        errorState = ""
        val credential = googleAuthManager.signInWithGoogle()
        isLoading = false
        if (credential != null) {
            onLoginSuccess()
        } else {
            errorState = "Error al iniciar sesión con Google"
        }
    }
}
```

---

## Flujo completo de autenticación

```
App abre
    │
    ├── installSplashScreen() (splash nativo de Android)
    │
    ├── setContent { "loading" }
    │       │
    │       └── LaunchedEffect(Unit)
    │               │
    │               └── googleAuthManager.tryAutoSignIn()
    │                       │
    │                       ├── Éxito → currentScreen = "home" (salta el login)
    │                       │
    │                       └── NoCredentialException → currentScreen = "login"
    │                                                             │
    │                      ┌──────────────────────────────────────┘
    │                      ▼
    │              LoginScreenImproved
    │                      │
    │                      │  Usuario toca botón Google
    │                      ▼
    │              googleAuthManager.signInWithGoogle()
    │                      │
    │                      │  credentialManager.getCredential()
    │                      ▼
    │              Bottom sheet de Google (sistema)
    │              Usuario selecciona cuenta
    │                      │
    │                      │  GoogleIdTokenCredential creado
    │                      ▼
    │              handleSignInResponse()
    │                      │
    │                      ├── Éxito → onLoginSuccess() → "home"
    │                      │
    │                      └── Error → errorState = "Error al iniciar..."
    │
    └── "home" → HomeScreen
```

---

## Configuración previa en Google Cloud Console

Sigue estos pasos para obtener el `WEB_CLIENT_ID`:

1. Ve a [Google Cloud Console](https://console.cloud.google.com/).
2. Crea un proyecto nuevo (o selecciona uno existente).
3. Navega a **APIs & Services → OAuth consent screen**.
   - User Type: **External** (aunque tu app esté en desarrollo).
   - Llena los campos obligatorios:
     - App name: `ChemXR` (o el nombre de tu app)
     - User support email: tu correo
     - Developer contact information: tu correo
   - Scopes: puedes dejarlos por defecto (solo `email`, `profile`, `openid`).
   - Test users: agrega los correos que usarás para probar.
4. Ve a **Credentials → Create Credentials → OAuth client ID**.
   - Application type: **Web application**
   - Name: `ChemXR Web Client`
   - Authorized JavaScript origins: dejar vacío
   - Authorized redirect URIs: dejar vacío
   - Haz clic en **Create**.
5. En el modal que aparece, copia el **Client ID**.
6. Pega ese valor en `Config.kt`:

```kotlin
object Config {
    const val WEB_CLIENT_ID = "123456789-xxxxx.apps.googleusercontent.com"
}
```

> **Nota importante**: Aunque la app es Android, el Credential Manager requiere el **Web Client ID** (no el Android Client ID) para `setServerClientId()`. El Android Client ID se usa solo si integraras Firebase Auth.

---

## Prueba de la funcionalidad

Para probar que la autenticación funciona:

1. Asegúrate de tener el `WEB_CLIENT_ID` correcto en `Config.kt`.
2. Compila e instala la app en un dispositivo/emulador con una cuenta de Google configurada.
3. Abre la app:
   - Si es la primera vez, verás la pantalla de login.
   - Toca el botón **Google**.
   - Debería aparecer el bottom sheet de Credential Manager.
   - Selecciona una cuenta.
4. Si todo funciona, la app navegará al Home.
5. Cierra y vuelve a abrir la app:
   - Debería hacer auto sign-in y navegar directamente al Home sin mostrar el login.

---

## Próximos pasos (Fase B — AuthorizationClient)

Cuando necesites acceder a los datos de Google del usuario (ej: Google Drive para guardar/leer archivos), se integrará `AuthorizationClient` siguiendo la documentación de [developer.android.com/identity/authorization](https://developer.android.com/identity/authorization?hl=es-419).

La diferencia clave:

| API | Propósito |
|---|---|
| **Credential Manager** | Autenticación ("¿Quién eres?") — obtener perfil del usuario |
| **AuthorizationClient** | Autorización ("¿A qué tienes permiso?") — acceder a Drive, Calendar, etc. |

El flujo combinado será:

1. El usuario inicia sesión con Google (Credential Manager) → obtienes `idToken`.
2. Cuando el usuario realice una acción que requiera Drive (ej: guardar un archivo), llamas a `AuthorizationClient.authorize()` con los scopes necesarios (`DriveScopes.DRIVE_FILE`).

---

## Referencias

- [About Sign in with Google — Android Developers](https://developer.android.com/identity/sign-in/credential-manager-siwg)
- [Implement Sign in with Google — Android Developers](https://developer.android.com/identity/sign-in/credential-manager-siwg-implementation)
- [Credential Manager API reference](https://developer.android.com/reference/kotlin/androidx/credentials/CredentialManager)
- [Google Identity Authorization — Android Developers](https://developer.android.com/identity/authorization?hl=es-419)
- [Sign in with Google Codelab](https://codelabs.developers.google.com/sign-in-with-google-android)
