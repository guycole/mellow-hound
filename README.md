# mellow-hound
Mellow Hound Client (Android)

Periodically collect location and information WiFi access points.

About once a minute, collect location and scan WAP, then write to JSON formatted file.

When "upload" button is pushed, write to AWS S3 bucket and delete local file.
