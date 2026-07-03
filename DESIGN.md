# ToadKeep II design

ToadKeep II is an on-screen keyboard for people who have trouble using a physical
one. The user clicks a key, the character lands on the system clipboard, and they
paste it wherever they need it. The interface is a single window showing an ABNT2
(Brazilian Portuguese) layout.

The visual theme is Catppuccin Latte, the light variant of the Catppuccin palette.
This document records the full palette and how each color is assigned inside the
app, so the FXML and any future stylesheet stay consistent.

## Palette: Catppuccin Latte

Reference values from the official Catppuccin specification.

| Name      | Hex     | RGB              |
|-----------|---------|------------------|
| Rosewater | #dc8a78 | 220, 138, 120    |
| Flamingo  | #dd7878 | 221, 120, 120    |
| Pink      | #ea76cb | 234, 118, 203    |
| Mauve     | #8839ef | 136, 57, 239     |
| Red       | #d20f39 | 210, 15, 57      |
| Maroon    | #e64553 | 230, 69, 83      |
| Peach     | #fe640b | 254, 100, 11     |
| Yellow    | #df8e1d | 223, 142, 29     |
| Green     | #40a02b | 64, 160, 43      |
| Teal      | #179299 | 23, 146, 153     |
| Sky       | #04a5e5 | 4, 165, 229      |
| Sapphire  | #209fb5 | 32, 159, 181     |
| Blue      | #1e66f5 | 30, 102, 245     |
| Lavender  | #7287fd | 114, 135, 253    |
| Text      | #4c4f69 | 76, 79, 105      |
| Subtext 1 | #5c5f77 | 92, 95, 119      |
| Subtext 0 | #6c6f85 | 108, 111, 133    |
| Overlay 2 | #7c7f93 | 124, 127, 147    |
| Overlay 1 | #8c8fa1 | 140, 143, 161    |
| Overlay 0 | #9ca0b0 | 156, 160, 176    |
| Surface 2 | #acb0be | 172, 176, 190    |
| Surface 1 | #bcc0cc | 188, 192, 204    |
| Surface 0 | #ccd0da | 204, 208, 218    |
| Base      | #eff1f5 | 239, 241, 245    |
| Mantle    | #e6e9ef | 230, 233, 239    |
| Crust     | #dce0e8 | 220, 224, 232    |

## Role assignments

The keyboard has five kinds of surfaces: the window itself, regular character keys,
the function row, decorative modifier keys (Backspace, Tab, Enter, the physical
Shift pair, Ctrl, Win, Alt, AltGr, Menu; all disabled, present only so the layout
reads as a keyboard), and two special actionable keys (the Shift toggle marked
with an up arrow, and Space).

| Role                        | Color     | Hex     |
|-----------------------------|-----------|---------|
| Window background           | Base      | #eff1f5 |
| Character key background    | Surface 0 | #ccd0da |
| Character key label         | Text      | #4c4f69 |
| Key hover background        | Surface 1 | #bcc0cc |
| Key pressed background      | Surface 2 | #acb0be |
| Function key background     | Surface 0 | #ccd0da |
| Function key label          | Blue      | #1e66f5 |
| Disabled modifier background| Mantle    | #e6e9ef |
| Disabled modifier label     | Overlay 0 | #9ca0b0 |
| Shift toggle, inactive      | Surface 0 bg, Peach label | #ccd0da / #fe640b |
| Shift toggle, active        | Peach bg, Base label      | #fe640b / #eff1f5 |
| Space bar background        | Green     | #40a02b |
| Space bar label             | Base      | #eff1f5 |
| Focus outline               | Lavender  | #7287fd |
| Key border (all keys)       | Crust     | #dce0e8 |

Rationale for the accent choices:

- Peach for Shift keeps the warm tone the current dark theme uses for that key,
  and flipping the background when the toggle is active gives clear on/off state
  without relying on the label alone.
- Green for Space preserves the current theme's green space bar and makes the
  most-used key the easiest to find.
- Blue labels mark the function row as a different key class while staying on the
  same Surface 0 background as ordinary keys.
- Disabled modifiers sink into Mantle with Overlay 0 text so they read as part of
  the keyboard shape but visibly not clickable.

## States

Actionable keys have four states:

1. Rest: role colors from the table above.
2. Hover: background steps one Surface level up (Surface 0 to Surface 1). Accent
   keys (Shift active, Space) darken their accent by 10 percent instead.
3. Pressed: background steps to Surface 2; accent keys darken by 20 percent.
4. Focused: Lavender outline, 2 px, for keyboard or switch-access navigation.

Disabled modifiers have a single state and no hover feedback.

## Typography and spacing

- Key labels use the JavaFX default system font, 13 px, regular weight. Labels on
  accent backgrounds (Space, active Shift) may use medium weight for contrast.
- Key corner radius: 6 px.
- Gap between keys: 6 px; gap between rows: 8 px; window padding: 14 px. These
  match the spacing already in main-view-abnt2.fxml.

## Implementation notes

Colors currently live as inline style attributes in main-view-abnt2.fxml. When
applying this theme, move them into a JavaFX stylesheet (latte.css in the same
resource package) with one class per role: key, key-function, key-modifier,
key-shift, key-space. That turns a future Frappe, Macchiato, or Mocha dark
variant into a stylesheet swap instead of an FXML edit.

Contrast check: Text (#4c4f69) on Surface 0 (#ccd0da) is about 5.6:1, which
passes WCAG AA for normal text. Base on Green (#40a02b) is about 3.2:1, which
passes only for large text; keep the Space label at 13 px medium or bump it if
contrast complaints come up. Overlay 0 on Mantle is intentionally low contrast
because those keys are decorative and disabled.
