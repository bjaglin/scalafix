package scalafix.internal.interfaces;

import java.util.Properties;

public class ScalafixProperties {

    public static final String PROPERTIES_PATH = "scalafix-interfaces.properties";

    private final Properties properties;
    
    public ScalafixProperties(Properties properties) {
        this.properties = properties;
    }

    public String scalafixVersion() {
        return properties.getProperty("scalafixVersion");
    }

    public String fullScalaVersion(String requestedScalaVersion) {
        String requestedScalaMajorMinorOrMajorVersion =
            requestedScalaVersion.replaceAll("^(\\d+\\.\\d+).*", "$1");

        String scalaVersionKey;
        if (requestedScalaMajorMinorOrMajorVersion.equals("2.12")) {
            scalaVersionKey = "scala212";
        } else if (requestedScalaMajorMinorOrMajorVersion.equals("2.13") ||
            requestedScalaMajorMinorOrMajorVersion.equals("2")) {
            scalaVersionKey = "scala213";
        } else if (requestedScalaMajorMinorOrMajorVersion.equals("3.0") ||
            requestedScalaMajorMinorOrMajorVersion.equals("3.1") ||
            requestedScalaMajorMinorOrMajorVersion.equals("3.2") ||
            requestedScalaMajorMinorOrMajorVersion.equals("3.3")) {
            scalaVersionKey = "scala33";
        } else if (requestedScalaMajorMinorOrMajorVersion.equals("3.5")) {
            scalaVersionKey = "scala35";
        } else if (requestedScalaMajorMinorOrMajorVersion.equals("3.6")) {
            scalaVersionKey = "scala36";
        } else if (requestedScalaMajorMinorOrMajorVersion.startsWith("3")) {
            scalaVersionKey = "scala3Next";
        } else {
            throw new IllegalArgumentException("Unsupported scala version " + requestedScalaVersion);
        }

        return properties.getProperty(scalaVersionKey);
    }

}