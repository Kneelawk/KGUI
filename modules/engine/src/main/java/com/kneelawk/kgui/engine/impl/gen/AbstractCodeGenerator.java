package com.kneelawk.kgui.engine.impl.gen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.objectweb.asm.Type;

import com.kneelawk.kgui.engine.impl.KGUIConstants;
import com.kneelawk.kgui.engine.impl.KGUILog;
import com.kneelawk.kgui.engine.impl.KGUIPaths;

public abstract class AbstractCodeGenerator<S> {
    private class Loader extends ClassLoader {
        public Loader(String name, ClassLoader parent) {
            super(name, parent);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            S spec = classToKey.get(name);
            if (spec == null) throw new ClassNotFoundException(name);

            String internalName = name.replace('.', '/');
            byte[] bytes = generateClass(spec, Type.getObjectType(internalName));

            if (KGUIConstants.EXPORT_GENERATED_CLASSES) {
                Path classPath = KGUIPaths.EXPORT_GENERATED_PATH.resolve(internalName + ".class");
                Path classInfoPath = KGUIPaths.EXPORT_GENERATED_PATH.resolve(internalName + ".info");

                try {
                    Path parentPath = classPath.getParent();
                    if (!Files.exists(parentPath)) {
                        Files.createDirectories(parentPath);
                    }

                    Files.write(classPath, bytes);
                    Files.writeString(classInfoPath, getInfo(spec));
                } catch (IOException e) {
                    KGUILog.LOGGER.warn("[KGUI] Unable to write exported generated class to {}", classPath, e);
                }
            }

            return defineClass(name, bytes, 0, bytes.length);
        }
    }

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<S, String> keyToClass = new HashMap<>();
    private final Map<String, S> classToKey = new HashMap<>();
    private final AtomicInteger index = new AtomicInteger(0);
    private final String prefix;

    private final Loader loader;

    protected AbstractCodeGenerator(String prefix, String name) {
        this.prefix = prefix;

        loader = new Loader(name, getClass().getClassLoader());
    }

    protected Class<?> getOrCreateClass(S spec) throws ClassNotFoundException {
        String className;
        lock.readLock().lock();
        try {
            className = keyToClass.get(spec);
        } finally {
            lock.readLock().unlock();
        }

        if (className == null) {
            lock.writeLock().lock();
            try {
                className = keyToClass.get(spec);

                if (className == null) {
                    className = prefix + "$" + index.getAndIncrement();
                    keyToClass.put(spec, className);
                    classToKey.put(className, spec);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        return loader.loadClass(className);
    }

    protected abstract byte[] generateClass(S spec, Type beingDefined);

    protected String getInfo(S spec) {
        return spec.toString();
    }
}
