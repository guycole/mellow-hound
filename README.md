# mellow-hound
Mellow Hound Client (Android)

Periodically collect location and information WiFi access points.

About once a minute, collect location and scan WAP, then write to JSON formatted file.

When "upload" button is pushed, write to AWS S3 bucket and delete local file.

You will need to create Personality.java w/your specific content.  This has the form

```
public class Personality {
    public static LocationResult locationResult;

    public static String AwsAccessKey = "bogus";
    public static String AwsSecretKey = "bogus";
    public static String AwsRegion = "us-east-1";
    public static String AwsBucketName = "bogus";
}
```
