# Commons System

This module provides  helpers/utilities to abstract away system  calls with side
effects such  as `System.exit()`. Also  system dependnet  this such as  find out
the operating system or opening the  operatinfgs systems default browser with an
URI. 

## Exitable Interface and Friends

TBD

## ExitCode Interface

This  interface provides  the  ability  to implement  own  exit  code enums  for
exitables  (section  before).  Purpose  is  to provide  exit  code  numbers  (as
integer) for the exitable  to signal a evaluatable return code  the JVM emits on
exit.

Example:

    public enum ExitCodeImpl implements ExitCode {
    
        OK(0), ERROR(1), FATAL(-1);
    
        private final int code;
    
        public ExitCodeImpl(final int code) {
            super();
            this.code = code;
        }
    
        @Override
        public int getCode() [
            return code;
        }
    
    }

## OperatingSystem Enum

This enum helps  you to determine the  operating system your JVM  is running on.
It  provides three  eunum  values  for the  major  systems (`WINDOWS`,  `LINUX`,
`MACOS`) and one for unrecogniezed systems (`UNKNOWN`).

The enum  provides a static  factory method which  determines the right  OS from
it's name. 

Example:

    final String osName = System.getProperty(OperatingSystem.OS_SYSTEM_PROPERTY, "");
    final OperatingSystem os = OperatingSystem.determine(osName);

## BrowserLauncher Class

This class tries to open a given URL with the operaings system's default browser.
Supported operating systems are Windows, Linux and Mac OS X.

Example:

    final BrowserLauncher launcher = new BrowserLauncher();
    launcher.openBrowser("https://www.weltraumschaf.de");

which is a short hand for:

    final String osName = System.getProperty(OperatingSystem.OS_SYSTEM_PROPERTY, "");
    final OperatingSystem os = OperatingSystem.determine(osName);
    final BrowserLauncher launcher = new BrowserLauncher(os);
    launcher.openBrowser("https://www.weltraumschaf.de");
