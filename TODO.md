# ToadKeep II to-do

## Key Completer fixes

- [ ] Make neighbor order irrelevant: push() only accepts first.col == last.col - 2,
      so Q then E summons W but E then Q does nothing. Compare absolute column
      distance instead.
- [ ] Normalize case in keymap lookups: keyMap snapshots uppercase labels at init,
      but after a shift on/off cycle button texts are lowercase, so
      push(button.text) misses and the completer silently stops working.
      Uppercase both sides when comparing.
- [ ] Remove or fix the getPosition(Button, VBox) overload in Keymap.kt: the
      condition is inverted (returns a position when col == -1, that is, when the
      key was NOT found). Currently unused dead code.
- [ ] Decide whether the first neighbor click should copy its own character.
      Today summoning W via Q, E puts q on the clipboard before w.

## Next features

- [ ] Key listening: capture real keystrokes from the physical keyboard so the
      app knows what the user typed, instead of reacting only to on-screen
      clicks. Stack (already in Gradle): JNativeHook 2.2.2 for X11 sessions;
      XDG portal GlobalShortcuts over DBus (dbus-java 5.1.1) for Wayland.
- [ ] Key insertion: send the summoned character directly into the focused
      application (synthesize a key event) instead of only copying it to the
      clipboard, removing the manual paste step. X11: java.awt.Robot or
      JNativeHook postNativeEvent. Wayland: XDG portal RemoteDesktop
      (NotifyKeyboardKeycode) over the same dbus-java stack; needs a one-time
      user consent dialog from the portal.
- [ ] Runtime session detection: pick X11 or Wayland backend from
      XDG_SESSION_TYPE / WAYLAND_DISPLAY env vars behind a common
      KeyBackend interface.

## Backlog

- [ ] Port TK1 features still missing: compose-text box, kaomoji/symbol tab.
- [ ] git init plus first commit (CI workflow inert until pushed to GitHub).
- [ ] Fix window title typo: ToadKepp.
- [ ] Drop unused template dependencies (ControlsFX, FormsFX, ValidatorFX,
      Ikonli, TilesFX) from build.gradle.kts and module-info.java.
- [ ] Align Kotlin versions: 2.1.20 in build.gradle.kts vs 2.4.0 in
      settings.gradle.kts.
