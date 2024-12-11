package utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

public class ParsableAvlSet<E extends Parsable<E>> extends AvlSet<E> implements ParsableSortedSet<E> {
    private final Function<String, E> createFunction;

    public ParsableAvlSet(Function<String, E> createFunction) {
        this.createFunction = createFunction;
    }

    public ParsableAvlSet(Function<String, E> createFunction, Comparator<? super E> c) {
        super(c);
        this.createFunction = createFunction;
    }

    public void add(String dataString) {
        this.add(this.createElement(dataString));
    }

    public void load(String filePath) {
        if (filePath != null && filePath.length() != 0) {
            this.clear();

            try {
                BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset());

                try {
                    fReader.lines().map(String::trim).filter((line) -> {
                        return !line.isEmpty();
                    }).forEach(this::add);
                } catch (Throwable var6) {
                    if (fReader != null) {
                        try {
                            fReader.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }

                    throw var6;
                }

                if (fReader != null) {
                    fReader.close();
                }
            } catch (FileNotFoundException var7) {
                Ks.ern("Data file " + filePath + " not found");
            } catch (IOException var8) {
                Ks.ern("File " + filePath + " reading error");
            }

        }
    }

    protected E createElement(String data) {
        return Optional.ofNullable(createFunction)
                .map(f -> f.apply(data))
                .orElseThrow(() -> new IllegalStateException("Set element creation function is missing"));
    }
}

