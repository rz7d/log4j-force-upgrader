package com.github.rz7d.tools.log4j;

//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.instrument.ClassFileTransformer;
//import java.lang.instrument.IllegalClassFormatException;
//import java.lang.instrument.Instrumentation;
//import java.security.ProtectionDomain;

public class Agent {

    // 動きません
//    public static void premain(String agentArgs, Instrumentation instrumentation) throws IOException {
//        instrumentation.addTransformer(new ClassFileTransformer() {
//            @Override
//            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
//                if (className.startsWith("org/apache/logging/log4j")) {
//                    System.out.println(className);
//                    InputStream input = Agent.class.getResourceAsStream("/log4j/" + className + ".class");
//                    if (input != null) {
//                        try {
//                            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//                            int read;
//                            while ((read = input.read(classfileBuffer)) != -1) {
//                                buffer.write(classfileBuffer, 0, read);
//                            }
//                            return buffer.toByteArray();
//                        } catch (IOException exception) {
//                            exception.printStackTrace();
//                        }
//                    }
//                }
//                return classfileBuffer;
//            }
//        });
//    }

}
