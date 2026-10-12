* `camkey version`
* `camkey tween list`: lists available tweens as list using commas.
* `camkey tween default [tween]`: sets or shows the default tween
* `camkey keyframe list`: lists current keyframes as list using commas. (100_tree,200_house) 0 frames = should print "No Keyframes"
* `camkey keyframe show {name}`: List that specific keyframe's time and transform info. (e.g. it only prints `1.0 [x,y,z] @ [pitch,yaw,roll]` with one decimal of precision)
* `camkey keyframe move {name} {target-name}`. {name} is moved right after {target-name}. Target can be `@start` and `@end` as well, and those should be considered invalid keynames otherwise.
* `camkey keyframe add {name} {time} [tween]`: Using the current player's transform, add a keyframe with name {name} for time {time}. Time is how long it takes to travel to this location. tween is optional and will fall back to tween default. Fails on duplicate. Appends to end by default. Time must be >0.
* `camkey keyframe remove {name}`: removes the keyframe
* `camkey keyframe rename {name} {new name}`: renames the keyframe; fails if the {new name} already exists.
* `camkey keyframe load {name}`: Teleports the actual minecraft player to specific keyframe (rpy & position). Stops playback.
* `camkey roll [angle]`: sets or shows the current camera roll.
* `camkey play {name} [time]`: plays the current configuration; apply the initial transformation information, and begin tween to next keyframe. Target can be `@start`. Optionally runs for {time}. Interpolate with slerp, applying the resulting quat to the camera. Shortcut `camkey play` to `camkey play @start`
* `camkey stop`: Stops playing the file.
* `camkey save [filename]`: saves to file. data is stored in memory until this command is run.
* `camkey load [filename]`: clears current data, and loads from file.

## Notes:

* Do not modify this file unless specifically requested
* `@start` and `@end` are reserved keyframe names.
* Keyframe names may not have spaces or commas in them.
* We follow the unix philosophy here; if your command executes, and information is not expected, we provide no output. If there is an error or warning, we provide output and we do not make any data changes.
* Mod folder on MacOS is '/Users/seppi/Library/Application Support/minecraft/mods'
* rotations are in degrees
* keyframe positions store the camera (eye) position, not the player's feet position
* When saving or loading, we use the directory: camkey/save/ and the default filename is default.json (e.g. camkey/save/default.json). This should be right in the mod folder. Warns if the version does not match.
* Commands are split one class per top-level literal (`version`, `tween`, `keyframe`, `roll`, `play`, `stop`, `save`, `load`) in the `com.camkey.command` package. Each implements a `CamKeyCommand` interface exposing `build()`, and `CamKeyCommands` is the single `@EventBusSubscriber` that assembles the root `camkey` literal from them.
* Shared client-side state (keyframes, default tween, playback) lives in its own class that the commands read and mutate.
* The order in which the data is stored is the playback order
* @start and @end are virtual placeholders - not real keyframe names.
* move {name} @start = become first, @end = become last
* Tween never has a fallback, unless it's being treated as optional. If the default is unsupported, an error should print. If a player uses an invalid tween, the command should fail.
* Don't implement things in @IDEAS.md

json file structures (comments will not be supported, they are just here to enlighten you):

## JSON save/load:

```json
{
  "version": [1,0,0], // what version of the mod was used to generate this file
  "keyframes":[
    {
      "name": "100_tree",
      "time": 1.0, // this value is ignored for the first element, but needs to have a value in case something is reordered.
      "position": [0.0, 64.0, 0.0], // x, y, z
      "rotation": [0.0, 0.0, 0.0], // pitch, yaw, roll in degrees
      "tween":"linear" // this value is ignored for the first element, but needs to have a value in case something is reordered.
    },
    {
      "name": "200_house",
      "time": 3.5, // relative time in seconds.
      "position": [10.5, 70.0, -15.2],
      "rotation": [15.0, 90.0, 0.0],
      "tween":"linear"
    }
  ]
}
```

## JSON Config:

this is in the mods folder and is located at camkey/config.json. This file is written on first use if it doesn't exist.

```json
{
  "version": [1,0,0], // what version of the mod was used to generate this file
  "tween-default":"linear"
}
```

Supported tweens:

* none (instantly moves you to the next position. Change happens at end of segment.)
* linear
* in-quad
* out-quad
* in-out-quad

Later we will add more (https://easings.net/en)