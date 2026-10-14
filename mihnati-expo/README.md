# Mihnati — Expo UI Prototype

This directory contains an isolated React Native + Expo + TypeScript UI prototype based on `Mihnati_Initial_UI_Build_Prompt.md`.

The existing native Android/Jetpack Compose project remains untouched. This prototype uses local demo data only; it does not connect to a backend, database, authentication provider, GPS, uploads, or push notifications.

## Run

```bash
cd mihnati-expo
npm install
npx expo start
```

Scan the QR code with Expo Go. Requires a Node.js version supported by the installed Expo SDK.

## Notes

- Language and appearance settings are kept in memory for this prototype.
- Switching Arabic/English updates translated UI and layout direction at the React component level. Native RTL direction changes may require a reload on some devices.
- All sample professionals, ratings, offers, requests, appointments, and notifications are demo content.
- This branch has not yet been run in an Expo environment; dependency installation and typecheck must be verified before treating it as release-ready.
