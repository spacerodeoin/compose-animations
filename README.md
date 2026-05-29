# compose-animations

Jetpack Compose demo app: **Physics with Animation**.

Four Material-motion animations, one page each, used to explain a principle of physics.
Built with **MVI** architecture.

## Pages

| Page | Animation | Physics principle |
|------|-----------|-------------------|
| Scale | Bouncy spring scale-up | Hooke's Law — `F = −kx` (restoring force, damped oscillation) |
| Translation | Ball drop + bounce | Free fall — `s = ½gt²` (constant acceleration, energy loss on impact) |
| Rotation | Spin-up then spin-down | Angular motion — `τ = Iα` (torque, moment of inertia, friction) |
| Alpha | Opacity fade-out | Exponential decay — `N(t) = N₀·e⁻ᵏᵗ` (radioactive decay / RC discharge) |

Screen transitions use a Material **shared-axis X** motion (slide + fade).

## Architecture (MVI)

Unidirectional data flow per screen:

```
View --(Intent)--> ViewModel --(reduce)--> State --> View
                              \--(Effect)--> View (one-shot)
```

- `mvi/Mvi.kt` — `UiState` / `UiIntent` / `UiEffect` markers and the base `MviViewModel`
  (`StateFlow` state + buffered `Channel` effects).
- Each feature under `ui/<feature>/` has a `*Contract` (State/Intent/Effect), a `*ViewModel`
  (reducer), and a `*Screen` (renders state, drives the `Animatable`).
- `navigation/` — `Destination` routes and the `AppNavHost`.

## Build & test

```bash
./gradlew :app:assembleDebug      # build APK
./gradlew :app:testDebugUnitTest  # run unit tests
```

Requires an Android SDK with `compileSdk 35`.
