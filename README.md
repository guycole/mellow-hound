# mellow-hound-android
Mellow Hound Client (Android)

Periodically collect information about cellular systems and WiFi access points.

Every kilometer (or whenever Android feels like it) write to SQLite:
* Current geographic location w/time stamp
* Cellular parameters (from Telephony Manager)
* WiFi access points (from Network Manager)

Write to server.
