package scalafix.interfaces;

import coursierapi.Repository;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import scalafix.internal.interfaces.ScalafixProperties;

/**
 * Wrapper around arguments for fetching artifacts and loading an instance of
 * {@link scalafix.interfaces.ScalafixArguments}.
 * <p>
 * To obtain an instance of Scalafix, use {@link #get}. Instances of
 * ScalafixLoadingArguments are immutable and thread safe.
 *
 * @implNote This interface is not intended for extension, the only
 *           implementation of this interface should live in the Scalafix
 *           repository.
 */
public interface ScalafixLoadingArguments {

    /**
     * @return an instance of {@link scalafix.interfaces.ScalafixArguments} matching
     *         the arguments provided so far.
     * @throws ScalafixException in case of errors during artifact
     *                           resolution/fetching.
     */
    ScalafixArguments load() throws ScalafixException;

    /**
     * @param config Optional path to a <code>.scalafix.conf</code>. If empty,
     *               Scalafix will infer such a file from the working directory or
     *               fallback to the default configuration.
     */
    ScalafixLoadingArguments withConfig(Optional<Path> config);

    /**
     * @param dependencyCoordinates Extra dependencies for classloading and
     *                              compiling external rules. For example
     *                              "com.nequissimus::sort-imports:0.5.2".
     *                              Artifacts will be resolved against the
     *                              Scala version provided with
     *                              {@link #withScalaVersion} and fetched
     *                              using Coursier.
     */
    ScalafixLoadingArguments withDependencyCoordinates(List<String> dependencyCoordinates);

    /**
     * @param dependencyURLs Extra URLs for classloading and compiling external
     *                       rules.
     */
    ScalafixLoadingArguments withDependencyURLs(List<URL> dependencyURLs);

    /**
     * @param repositories Maven/Ivy repositories to fetch the artifacts from.
     */
    ScalafixLoadingArguments withRepositories(List<Repository> repositories);

    /**
     * @param version The major or binary Scala version that the provided files are
     *                targeting, for the full version that was used to compile them
     *                when a classpath is provided. For example "2.12.8" or "2.12"
     *                or "2". To be able to run advanced semantic rules using the
     *                Scala Presentation Compiler (such as ExplicitResultTypes),
     *                the full Scala version must be provided.
     */
    ScalafixLoadingArguments withScalaVersion(String version);

    /**
     * @param path The working directory of where to invoke the command-line
     *             interface. Primarily used to absolutize relative directories
     *             passed via {@link ScalafixArguments#withPaths(List) } and also
     *             to auto-detect the location of <code>.scalafix.conf</code>.
     */
    ScalafixLoadingArguments withWorkingDirectory(Path path);

    // /**
    //  * @return An implementation of the {@link ScalafixLoadingArguments} interface.
    //  */
    // static ScalafixLoadingArguments get() throws ScalafixException {
    //     ScalafixProperties scalafixProperties = new ScalafixProperties(
    //             ScalafixLoadingArguments.class.getClassLoader());
    //     return new ScalafixLoadingArgumentsImpl(scalafixProperties);
    // }
}
