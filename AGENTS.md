## Repository at a glance
- This is a Bukkit/Spigot plugin with a non-standard flat layout: Java sources and plugin resources are both under `src/` (not `src/main/java` / `src/main/resources`).
- Plugin entrypoint is `com.clancraft.turnmanager.TurnManager` in `src/com/clancraft/turnmanager/TurnManager.java`, wired by `src/plugin.yml`.

## High-value code map
- Command routing lives in `src/com/clancraft/turnmanager/TMCommandHandler.java`; nearly all `/tm ...` behaviour changes start there.
- Core state singletons are initialised in `TurnManager.onEnable()`: `Cycle`, `Turn`, `Teleport`, `Shield`, `Calendar`.
- Turn flow logic is in `src/com/clancraft/turnmanager/turn/` (`Cycle` order + `Turn` advancement/timer).
- Shield persistence + periodic position checks are in `src/com/clancraft/turnmanager/shield/Shield.java` (loads/saves `shields.yml`, schedules repeating task).
- Calendar/date persistence + turn-subscriber auto-advance are in `src/com/clancraft/turnmanager/calendar/Calendar.java` (loads/saves `calendar.yml`).

## Config and persistence quirks
- Runtime data files are plugin data-folder files `shields.yml` and `calendar.yml`; template resources are committed at `src/shields.yml` and `src/calendar.yml`.
- `src/config.yml` exists but current code paths shown here do not read it.
- If you add new commands or permission nodes, update both `src/plugin.yml` and `src/com/clancraft/turnmanager/TMPermissions.java` to keep runtime checks and declared permissions aligned.

## Verification reality
- No build script, wrapper, CI workflow, or test harness is checked in at repo root in this snapshot.
- Do not invent Maven/Gradle commands in agent responses; if verification is needed, describe what could not be run locally and why.
