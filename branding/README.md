# Tracer Branding

This directory serves as the central repository for the **Tracer** brand identity, design assets, and visual guidelines. It is the "Source of Truth" for maintaining visual consistency across the Android application, marketing materials, and technical documentation.

---

## 📂 Directory Structure

| Folder | Description |
| :--- | :--- |
| `logo/` | Contains the official Tracer logo in various formats (SVG, PNG) and variations. |
| `palette/` | Visual color swatches and design system color definitions. |
| `screenshots/` | High-fidelity screenshots of the latest stable build for documentation and store listings. |
| `mockups/` | UI/UX design mockups and wireframes representing the intended user experience. |
| `assets/` | General purpose design assets including icons, illustrations, and background textures. |

---

## 🏗️ Repository Organization

Tracer follows a Clean Architecture and MVVM pattern. The branding folder is kept separate from the `app/` module to ensure that design documentation remains platform-agnostic while being easily accessible to developers during the implementation of Jetpack Compose components.

---

## 🤝 Contribution Guidelines

To ensure the brand remains consistent:
1. **Source Files**: Always provide vector formats (SVG) for logos and icons when contributing.
2. **Quality**: Screenshots should be captured from an emulator or physical device with at least 440dpi density.
3. **Consistency**: All new UI designs must adhere to the rules defined in [Brand_Guide.md](./Brand_Guide.md).

---

## 🏷️ Asset Naming Conventions

All assets should follow lower-snake-case naming:
- `logo_primary.svg`
- `screenshot_home_v1.png`
- `icon_ble_connected.svg`

---

## 📈 Versioning Policy

We use **Semantic Versioning** for branding updates:
- **Major**: Brand pivot or complete redesign.
- **Minor**: New UI components or significant palette adjustments.
- **Patch**: Icon fixes or documentation clarifications.

---

## 🚀 Future Expansion

The branding directory will evolve to include:
- Wear OS design specifications.
- AR navigation visual cues and markers.
- Dark theme specific asset variations.
