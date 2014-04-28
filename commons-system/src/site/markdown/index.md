# Commons System

## BrowserLauncher Class

TBD

## Exitable Interface and Friends

TBD

## ExitCode Interface

TBD

## OperatingSystem Enum

This enum helps  you to determine the  operating system your JVM  is running on.
It  provides three  eunum  values  for the  major  systems (`WINDOWS`,  `LINUX`,
`MACOS`) and one for unrecogniezed systems (`UNKNOWN`).

The enum  provides a static  factory method which  determines the right  OS from
it's name. Example:

    final String osName = System.getProperty("os.osName", "");
    final OperatingSystem os = OperatingSystem.determine(osName);
