package com.github.rz7d.tools.log4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;

public final class Upgrader {

    public static URI me() throws URISyntaxException {
        return Upgrader.class.getProtectionDomain().getCodeSource().getLocation().toURI();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: upgrader <jar>");
            return;
        }
        Path serverJar = Paths.get(args[0]);
        try (
            FileSystem srcFS = FileSystems.newFileSystem(URI.create("jar:" + me()), Collections.emptyMap());
            FileSystem dstFS = FileSystems.newFileSystem(URI.create("jar:" + serverJar.toUri()), Collections.emptyMap())
        ) {
            Path srcRoot = srcFS.getPath("/", "log4j");
            Path dstRoot = dstFS.getPath("/");

            System.out.println("Deleting old log4j2 files...");
            Files.walk(dstRoot.resolve("org").resolve("apache").resolve("logging").resolve("log4j"))
                .filter(Files::isRegularFile).forEach(oldFile -> {
                    System.out.println("Delete: " + oldFile);
                    try {
                        Files.deleteIfExists(oldFile);
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });

            System.out.println("Copying new log4j2 files...");
            Iterator<Path> iterator = Files.walk(srcRoot).filter(Files::isRegularFile).iterator();
            while (iterator.hasNext()) {
                Path file = iterator.next();
                Path dst = dstRoot.resolve(srcRoot.relativize(file).toString());
                if (file.getFileName().toString().equals("Log4j2Plugins.dat")) {
                    // Paper Support: dont replace
                    if (Files.exists(dst)) {
                        continue;
                    }
                }

                System.out.println("Copy: " + dst);
                Path parent = dst.getParent();
                if (parent != null && !Files.isDirectory(parent)) {
                    try {
                        Files.createDirectories(parent);
                    } catch (FileAlreadyExistsException ignored) {
                    }
                }
                Files.copy(file, dst, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

}
