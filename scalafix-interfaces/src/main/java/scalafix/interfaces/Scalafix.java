package scalafix.interfaces;

import coursierapi.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

import scalafix.internal.interfaces.ScalafixCoursier;
import scalafix.internal.interfaces.ScalafixInterfacesClassloader;
import scalafix.internal.interfaces.ScalafixProperties;

@Deprecated
public interface Scalafix {

    @Deprecated
    ScalafixArguments newArguments();

    @Deprecated
    String mainHelp(int screenWidth);

    @Deprecated
    String scalaVersion();

    @Deprecated
    String scalafixVersion();

    @Deprecated
    String scalametaVersion();

    @Deprecated
    String[] supportedScalaVersions();

    @Deprecated
    String scala211();

    @Deprecated
    String scala212();

    @Deprecated
    String scala213();

    @Deprecated
    String scala33();

    @Deprecated
    String scala35();

    @Deprecated
    String scala36();

    @Deprecated
    String scala3LTS();

    @Deprecated
    String scala3Next();

    @Deprecated
    static Scalafix fetchAndClassloadInstance(String requestedScalaVersion) throws ScalafixException {
        return fetchAndClassloadInstance(requestedScalaVersion, Repository.defaults());
    }

    @Deprecated
    static Scalafix fetchAndClassloadInstance(String requestedScalaVersion, List<Repository> repositories)
            throws ScalafixException {

        ScalafixProperties scalafixProperties = new ScalafixProperties(Scalafix.class.getClassLoader());
        String scalafixVersion = scalafixProperties.scalafixVersion();
        String scalaVersion = scalafixProperties.fullScalaVersion(requestedScalaVersion);

        List<URL> jars = ScalafixCoursier.scalafixCliJars(repositories, scalafixVersion, scalaVersion);
        ClassLoader parent = new ScalafixInterfacesClassloader(Scalafix.class.getClassLoader());
        return classloadInstance(new URLClassLoader(jars.stream().toArray(URL[]::new), parent));
    }

    @Deprecated
    static Scalafix classloadInstance(ClassLoader classLoader) throws ScalafixException {
        try {
            Class<?> cls = classLoader.loadClass("scalafix.internal.interfaces.ScalafixImpl");
            Constructor<?> ctor = cls.getDeclaredConstructor();
            ctor.setAccessible(true);
            return (Scalafix) ctor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException |
                InstantiationException ex) {
            throw new ScalafixException(
                    "Failed to reflectively load Scalafix with classloader " + classLoader.toString(), ex);
        }
    }
}
