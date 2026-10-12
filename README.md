# CamKey

A client-side camera keyframe and playback system for Minecraft. Build a path of
camera keyframes, then play it back with smooth eased interpolation — including
camera **roll**.

- Minecraft 1.21.1
- NeoForge 21.1.259
- Java 21

## Documents

* AGENT-NOTES.md: This contains information that I used to help maintain the state of the project while developing it with the AI agent.
* DEV-NOTES.md: This is contains information to docmement how I developed the entire application.
* IDEAS.md: This contains ideas I had on how to extend this application.

## Architectural Decisions

- Client-side only. Every command is registered through NeoForge's `RegisterClientCommandsEvent` (`Dist.CLIENT`); the mod adds no custom packets or server logic.
- One command class per top-level literal in `com.camkey.command`, each implementing `CamKeyCommand.build()`. `CamKeyCommands` is the single `@EventBusSubscriber`.
- Shared client state lives in `CamKeyState`: the keyframe store, the default tween, playback, and the live camera roll.
- Tweens are a registry. Each easing is its own class implementing `Tween` (`name()` + `apply(progress)`), collected in `Tweens`.
- `Keyframe` is immutable and `Keyframes` is an ordered collection whose order **is** the playback order. `@start` and `@end` are virtual anchors and are never stored.
- Interpolation uses linear interpolation for position and quaternion **slerp** for rotation (JOML).
- Camera roll is applied through NeoForge's `ViewportEvent.ComputeCameraAngles`; the only supported way to set roll, since vanilla Minecraft has none.
- Playback advances on the client tick (20 Hz), but the transform is evaluated every frame from the partial tick, so playback renders at the display frame rate.
- Keyframes store the camera (eye) position. `keyframe load` and `play` move the player so the eye lands on the stored point.
- Persistence is Gson JSON. Paths are centralized in `CamKeyPaths`: config at `mods/camkey/config.json`, saves at `mods/camkey/save/`. Save files carry a `version` and log a warning on mismatch.
- Fail-fast: missing required data throws rather than defaulting, and failing commands report an error and change no data (unix philosophy).

## Known Limitations

- That movement is client-side, so on a multiplayer server the player may rubber-band, and playback can clip through terrain or otherwise fight the world's physics.
- Rotation is sampled per frame, but position and the player's yaw/pitch are interpolated by vanilla between ticks, so the two can be offset by up to one tick (≤ 50 ms).
- Rotation slerp takes the shortest path, so a change greater than 180° between two keyframes rotates the short way around.
- `camkey roll` is runtime only and is never persisted.
- Keyframes are in memory until `camkey save` is run; only the config's default tween auto-persists.
- A version mismatch on load only logs a warning — there is no in-game prompt.
- `play` needs at least one segment after its target, so a single-keyframe setup cannot be played.
- `@end` is not a valid `play` target (only `@start` and real keyframe names).
- Keyframe names may not contain spaces, commas, or `@`.
- Only none, linear and quad tweens are supported.

## Requirements

- NeoForge 21.1.259 (Minecraft 1.21.1)
- JDK 21
- No Gradle install needed — use the included wrapper (`./gradlew`)

## Building

```sh
./gradlew build
```

The mod jar is written to `build/libs/camkey-*.jar`

Run the unit tests with:

```sh
./gradlew test
```

## Installing

Copy `build/libs/camkey-1.0.0.jar` into your Minecraft `mods` folder.

- macOS: `~/Library/Application Support/minecraft/mods/`
- Windows: `%APPDATA%\.minecraft\mods\`
- Linux: `~/.minecraft/mods/`

## Usage

All commands are client-side and start with `camkey`.

### General

| Command | Description |
| --- | --- |
| `camkey version` | Prints the mod version. |
| `camkey roll [angle]` | Sets or shows the current camera roll, in degrees. |

### Tweens

| Command | Description |
| --- | --- |
| `camkey tween list` | Lists available tweens as a comma-separated list. |
| `camkey tween default [tween]` | Sets or shows the default tween used by `keyframe add` when no tween is given. |

### Keyframes

| Command | Description |
| --- | --- |
| `camkey keyframe list` | Lists keyframes as a comma-separated list, or prints `No Keyframes` when empty. |
| `camkey keyframe show {name}` | Prints the keyframe's time and transform, e.g. `1.0 [x,y,z] @ [pitch,yaw,roll]`. |
| `camkey keyframe add {name} {time} [tween]` | Adds a keyframe from the current camera transform. `{time}` is how long it takes to travel here (must be > 0); tween falls back to the default. Appends to the end; fails on a duplicate name. |
| `camkey keyframe move {name} {target-name}` | Moves `{name}` to sit right after `{target-name}`. Target may be `@start` or `@end`. |
| `camkey keyframe remove {name}` | Removes the keyframe. |
| `camkey keyframe rename {name} {new name}` | Renames the keyframe; fails if `{new name}` already exists. |
| `camkey keyframe load {name}` | Teleports the player to the keyframe (position and rotation) and stops playback. |

### Playback

| Command | Description |
| --- | --- |
| `camkey play {name} [time]` | Plays from `{name}` (a keyframe or `@start`), or from `@start` when omitted. Optionally stops after `{time}` seconds. |
| `camkey stop` | Stops playback. |

### Saving and loading

| Command | Description |
| --- | --- |
| `camkey save [filename]` | Writes the current keyframes to a file (default `default.json`). |
| `camkey load [filename]` | Clears the current keyframes and loads them from a file. |

## Files

Save and config files live under `camkey/` inside your `mods` folder.

## Notes

- `@start` and `@end` are reserved, virtual placeholders — not real keyframes.
- Keyframe names may not contain spaces or commas.