# compose-animations

Jetpack Compose demo app built with **MVI** architecture and **Clean Architecture** layering.

It pairs two complementary demonstrations:

1. **Physics with Animation** — four Material-motion animations, one page each, each illustrating
   a principle of physics.
2. **Planets — Surface Gravity** — a networked screen that fetches real planetary data from a
   public REST API and shows the *measured* gravity behind those same physics principles.

## Pages

| Page | What it shows | Principle / Source |
|------|---------------|--------------------|
| Scale | Bouncy spring scale-up | Hooke's Law — `F = −kx` (restoring force, damped oscillation) |
| Translation | Ball drop + bounce | Free fall — `s = ½gt²` (constant acceleration, energy loss on impact) |
| Rotation | Spin-up then spin-down | Angular motion — `τ = Iα` (torque, moment of inertia, friction) |
| Alpha | Opacity fade-out | Exponential decay — `N(t) = N₀·e⁻ᵏᵗ` (radioactive decay / RC discharge) |
| Planets | Live list of planets ranked by surface gravity | [Solar System OpenData API](https://api.le-systeme-solaire.net/) (public, no auth) |

Screen transitions use a Material **shared-axis X** motion (slide + fade).

## Architecture

The app combines **MVI** for presentation with a **Clean Architecture** dependency rule across
layers. The arrows point inward — outer layers depend on inner ones, never the reverse.

```
        ┌─────────────────────────── presentation (ui/) ───────────────────────────┐
        │  Screen  ──(Intent)──▶  ViewModel  ──(reduce)──▶  State  ──▶  Screen        │
        │                            │   ▲          \──(Effect)──▶ Screen (one-shot)  │
        └────────────────────────────┼───┼──────────────────────────────────────────┘
                                      │   │ Result<Planet>
        ┌─────────────────────────── domain/ (pure Kotlin) ────────────────────────┐
        │   GetPlanetsUseCase  ──▶  PlanetRepository (interface)  ──▶  Planet model  │
        └────────────────────────────┼─────────────────────────────────────────────┘
                                      │ implements
        ┌─────────────────────────── data/ ────────────────────────────────────────┐
        │  PlanetRepositoryImpl  ──▶  SolarSystemApi (Retrofit)  ──▶  DTO ─toDomain─▶ │
        └───────────────────────────────────────────────────────────────────────────┘
```

### MVI (the presentation pattern)

Unidirectional data flow per screen:

```
View --(Intent)--> ViewModel --(reduce)--> State --> View
                              \--(Effect)--> View (one-shot)
```

- `mvi/Mvi.kt` — `UiState` / `UiIntent` / `UiEffect` markers and the base `MviViewModel`
  (`StateFlow` state + buffered `Channel` effects).
- Each feature under `ui/<feature>/` has a `*Contract` (State/Intent/Effect), a `*ViewModel`
  (the reducer), and a `*Screen` (renders state, fires intents).

### Clean Architecture (the dependency rule)

The Planets feature is layered so the business core never depends on frameworks:

| Layer | Package | Responsibility | Knows about |
|-------|---------|----------------|-------------|
| **Domain** | `domain/planets/` | `Planet` model, `PlanetRepository` interface, `GetPlanetsUseCase` | nothing (pure Kotlin) |
| **Data** | `data/planets/`, `data/network/` | Retrofit `SolarSystemApi`, DTOs, `toDomain()` mapper, `PlanetRepositoryImpl`, `NetworkModule` | domain interfaces + Retrofit |
| **Presentation** | `ui/planets/` | `PlanetsContract`, `PlanetsViewModel`, `PlanetsScreen` | domain use case |
| **Composition root** | `di/ServiceLocator.kt` | Builds the graph and binds impls to interfaces | everything |

Key consequences:

- The domain layer has **no Android/Retrofit imports** — it is plain Kotlin and trivially testable.
- The ViewModel depends on `GetPlanetsUseCase`, not on Retrofit, so it is tested with a fake
  repository and **no network** (`PlanetsViewModelTest`, `GetPlanetsUseCaseTest`).
- Errors surface as `Result<…>` from the repository instead of thrown exceptions, so the UI
  handles success/failure explicitly (loading → list / error + retry).
- DI is a hand-rolled `ServiceLocator` to keep the project framework-free; because every layer
  depends only on interfaces, swapping in **Hilt** or **Koin** would touch only that one file.

## Networking stack (standard libraries)

| Concern | Library |
|---------|---------|
| HTTP / REST | [Retrofit](https://square.github.io/retrofit/) over [OkHttp](https://square.github.io/okhttp/) |
| JSON | [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) (+ Retrofit converter) |
| Async | Kotlin Coroutines (`suspend` + `viewModelScope`) |
| Logging | OkHttp `HttpLoggingInterceptor` (BASIC) |

Requires the `INTERNET` permission (declared in `AndroidManifest.xml`).

## Build & test

```bash
./gradlew :app:assembleDebug      # build APK
./gradlew :app:testDebugUnitTest  # run unit tests
```

Requires an Android SDK with `compileSdk 35`.
