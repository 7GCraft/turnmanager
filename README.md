# TurnManager

TurnManager is a Minecraft server plugin for managing a cycle of player turns. It provides turn announcements and timers, proximity shields, teleportation to the active player, and a world/player calendar.

## Requirements and installation

- Java 21
- A compatible Paper server (the project compiles against Paper API 1.21.11; the plugin declares API version 1.21)

Build the plugin with the included Gradle wrapper:

```sh
./gradlew build
```

Copy `build/libs/TurnManager-0.4.5.jar` to the server's `plugins/` directory and restart the server. On Windows, use `gradlew.bat build` instead.

## Getting started

Run commands in game; the command handler does not process console senders. Players must be online to be added to the cycle.

```text
/tm cycle add Alice
/tm cycle add Bob
/tm cycle list
/tm turn next
```

The cycle starts at a special `Break` entry. Advancing skips offline players, announces the next available player, and starts a 15-minute timer. On reaching `Break`, the turn is announced without starting a timer. The cycle and timer are not persisted across restarts.

## Commands

Arguments in `<angle brackets>` are required; those in `[square brackets]` are optional.

### Cycle and turns

| Command | Description |
| --- | --- |
| `/tm cycle list` | Show the turn order. |
| `/tm cycle add <player> [position]` | Add an online player, optionally at a 1-based position. |
| `/tm cycle remove <player>` | Remove a player from the cycle. |
| `/tm cycle swap <player1> <player2>` | Swap two entries in the cycle. |
| `/tm turn next` | Advance and announce the turn. |
| `/tm turn announce` | Announce the current turn and order. |
| `/tm timer start [minutes]` | Start or replace the timer; defaults to 15 minutes. |
| `/tm timer stop` | Stop the timer. |
| `/tm timer pause` | Pause the current timer. |
| `/tm timer resume` | Resume the current timer. |
| `/tm teleport` | Teleport to the active player, subject to shields. |

### Shields

Register a shield profile before managing its list. Shield lists identify players blocked from approaching the active player when that player's shield is enabled. Unless an owner is specified, list commands use the current player in the cycle.

| Command | Description |
| --- | --- |
| `/tm shield register <player>` | Create a shield profile. |
| `/tm shield unregister <player>` | Remove a shield profile. |
| `/tm shield add [owner] <player>` | Add a player to a shield list. |
| `/tm shield remove [owner] <player>` | Remove a player from a shield list. |
| `/tm shield all [owner]` | Replace the list with all currently online players. |
| `/tm shield clear [owner]` | Clear a shield list. |
| `/tm shield list [owner]` | Show a shield list. |
| `/tm shield on` / `/tm shield off` | Enable or disable the current player's shield. |

In the current command handler, `/tm shield on <owner>` and `/tm shield off <owner>` still affect the current player, not the named owner.

### Calendar

Register player date profiles before using player-specific date commands. Synced player dates are updated when the world date changes; unsync a player before setting or adding to their date individually.

| Command | Description |
| --- | --- |
| `/tm date register <player>` | Create a player date profile. |
| `/tm date unregister <player>` | Remove a player date profile. |
| `/tm date today` | Show your date. |
| `/tm date today world` | Show the world date. |
| `/tm date today <player>` | Show a player's date. |
| `/tm date add [player] <days>` | Advance the world or a player's date. |
| `/tm date set [player] <day> <month> <year>` | Set the world or a player's date. |
| `/tm date sync <player>` / `/tm date unsync <player>` | Enable or disable syncing with the world date. |
| `/tm date auto on` / `/tm date auto off` | Enable or disable automatic world-date advancement on player turns. |

World-date months use 1-based numbering. The current player-date setter instead uses a zero-based month index; this differs from the world-date command. Syncing marks a profile as synced but does not immediately copy the world date; the copy occurs the next time the world date changes.

## Permissions

Permissions default to server operators. The top-level permissions are `tm.cycle`, `tm.turn`, `tm.timer`, `tm.teleport`, `tm.shield`, and `tm.date`. Command groups check their top-level permission before checking the relevant subcommand permission; consult [`src/plugin.yml`](src/plugin.yml) for the declared nodes and defaults. In particular, granting only a subcommand node is not sufficient to pass the group check.

## Data files

On first startup, the plugin creates `plugins/TurnManager/shields.yml` and `plugins/TurnManager/calendar.yml` from the bundled templates. Shield profiles, calendar profiles, the world date, and calendar auto-advance setting are loaded on enable and saved on disable. Back up these files when moving the plugin to another server. The bundled `config.yml` is not currently used by the plugin.

## Development

Java sources and plugin resources both live under `src/`. The Gradle build excludes Java files from the packaged resources and uses the Paper API as a compile-only dependency.
