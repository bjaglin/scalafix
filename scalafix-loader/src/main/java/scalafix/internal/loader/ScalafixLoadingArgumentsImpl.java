package scalafix.internal.loader;

import com.typesafe.config.*;

import coursierapi.Repository;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import scalafix.interfaces.*;
import scalafix.internal.interfaces.ScalafixInterfacesClassloader;
import scalafix.internal.interfaces.ScalafixCoursier;
import scalafix.internal.interfaces.ScalafixProperties;

public class ScalafixLoadingArgumentsImpl implements ScalafixLoadingArguments {

    private final ScalafixProperties properties;

    private final Optional<Path> config;
    private final List<String> dependencyCoordinates;
    private final List<URL> dependencyURLs;
    private final List<Repository> repositories;
    private final String scalaVersion;
    private final Path workingDirectory;

    public ScalafixLoadingArgumentsImpl(ScalafixProperties properties) {
        this.properties = properties;

        this.config = Optional.empty();
        this.dependencyCoordinates = List.of();
        this.dependencyURLs = List.of();
        this.repositories = Repository.defaults();
        this.scalaVersion = "3";
        this.workingDirectory = Paths.get(System.getProperty("user.dir"));
    }

    public ScalafixLoadingArgumentsImpl(
            ScalafixProperties properties,
            Optional<Path> config,
            List<String> dependencyCoordinates,
            List<URL> dependencyURLs,
            List<Repository> repositories,
            String scalaVersion,
            Path workingDirectory) {
        this.properties = properties;

        this.config = config;
        this.dependencyCoordinates = dependencyCoordinates;
        this.dependencyURLs = dependencyURLs;
        this.repositories = repositories;
        this.scalaVersion = scalaVersion;
        this.workingDirectory = workingDirectory;
    }

    @Override
    public ScalafixArguments load() throws ScalafixException {
        String scalafixVersion = configOrLatestScalafixVersion();

        String scalafixScalaVersion;
        if (scalafixVersion == properties.scalafixVersion()) {
            scalafixScalaVersion = properties.fullScalaVersion(scalaVersion);
        } else {
            List<URL> propertiesJars = ScalafixCoursier.scalafixPropertiesJars(repositories, scalafixVersion);
            ClassLoader propertiesClassLoader = new URLClassLoader(propertiesJars.stream().toArray(URL[]::new), null);
            ScalafixProperties fetchedProperties = new ScalafixProperties(propertiesClassLoader);
            scalafixScalaVersion = fetchedProperties.fullScalaVersion(scalaVersion);
        }

        List<URL> cliJars = ScalafixCoursier.scalafixCliJars(repositories, scalafixVersion, scalafixScalaVersion);
        ClassLoader parent = new ScalafixInterfacesClassloader(ScalafixLoadingArguments.class.getClassLoader());
        ClassLoader classLoader = new URLClassLoader(cliJars.stream().toArray(URL[]::new), parent);

        return classLoadScalafixArguments(classLoader)
                .withConfig(config)
                .withDependencyCoordinates(dependencyCoordinates)
                .withDependencyURLs(dependencyURLs)
                .withRepositories(repositories)
                .withScalaVersion(scalaVersion)
                .withWorkingDirectory(workingDirectory);
    }

    @Override
    public ScalafixLoadingArguments withConfig(Optional<Path> config) {
        return new ScalafixLoadingArgumentsImpl(
                properties,
                config,
                dependencyCoordinates,
                dependencyURLs,
                repositories,
                scalaVersion,
                workingDirectory);
    }

    @Override
    public ScalafixLoadingArguments withDependencyCoordinates(List<String> dependencyCoordinates) {
        return new ScalafixLoadingArgumentsImpl(
                properties,
                config,
                dependencyCoordinates,
                dependencyURLs,
                repositories,
                scalaVersion,
                workingDirectory);
    }

    @Override
    public ScalafixLoadingArguments withDependencyURLs(List<URL> dependencyURLs) {
        return new ScalafixLoadingArgumentsImpl(
                properties,
                config,
                dependencyCoordinates,
                dependencyURLs,
                repositories,
                scalaVersion,
                workingDirectory);
    }

    @Override
    public ScalafixLoadingArguments withRepositories(List<Repository> repositories) {
        return new ScalafixLoadingArgumentsImpl(
                properties,
                config,
                dependencyCoordinates,
                dependencyURLs,
                repositories,
                scalaVersion,
                workingDirectory);
    }

    @Override
    public ScalafixLoadingArguments withScalaVersion(String version) {
        return new ScalafixLoadingArgumentsImpl(
                properties,
                config,
                dependencyCoordinates,
                dependencyURLs,
                repositories,
                scalaVersion,
                workingDirectory);
    }

    @Override
    public ScalafixLoadingArguments withWorkingDirectory(Path path) {
        return new ScalafixLoadingArgumentsImpl(
                properties,
                config,
                dependencyCoordinates,
                dependencyURLs,
                repositories,
                scalaVersion,
                workingDirectory);
    }

    private String configOrLatestScalafixVersion() throws ScalafixException {
        final String key = "version";

        File configFile = config.orElse(workingDirectory.resolve(".scalafix.conf")).toFile();
        try {
            Config typesafeConfig = ConfigFactory.parseFile(configFile);
            if (typesafeConfig.hasPath(key)) {
                return typesafeConfig.getString(key);
            }
        } catch (ConfigException e) {
        }

        return ScalafixCoursier.latestScalafixProperties(repositories);
    }

    private ScalafixArguments classLoadScalafixArguments(ClassLoader classLoader) throws ScalafixException {
        try {
            Class<?> cls = classLoader.loadClass("scalafix.internal.interfaces.ScalafixArgumentsImpl");
            Constructor<?> ctor = cls.getDeclaredConstructor();
            ctor.setAccessible(true);
            return (ScalafixArguments) ctor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | InstantiationException ex) {
            throw new ScalafixException(
                    "Failed to reflectively load Scalafix with classloader " + classLoader.toString(), ex);
        }
    }

}
