package pw.testoprog.bookingo.benchmarks;

import org.junit.Test;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

abstract public class BaseBenchmark {
    @Test
    public void executeJmhRunner() throws RunnerException {
        Options options = new OptionsBuilder()
                .forks(0)
                .measurementIterations(15)
                .threads(5)
                .warmupIterations(5)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
}
