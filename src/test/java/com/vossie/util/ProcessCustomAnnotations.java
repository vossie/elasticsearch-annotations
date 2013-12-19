package com.vossie.util;

import com.vossie.elasticsearch.annotations.util.ElasticSearchDocumentAnnotationProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rpatadia on 19/12/2013.
 */
public class ProcessCustomAnnotations {

    private static ElasticSearchDocumentAnnotationProcessor annotationProcessor = new ElasticSearchDocumentAnnotationProcessor();

    private static File sandBoxDir = new File("target");

    public static JavaCompiler.CompilationTask processAnnotations(String[] testSourceFileNames) throws URISyntaxException {
        // Get an instance of java compiler
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // Get a new instance of the standard file manager implementation
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        // Get the list of java file objects, in this case we have only
        // one file, TestClass.java
        // http://stackoverflow.com/a/676102/693752
        List<File> listSourceFiles = new ArrayList<>();
        for (String sourceFileName : testSourceFileNames) {
            URL filename = ClassLoader.getSystemResource(sourceFileName);
            listSourceFiles.add(new File(filename.toURI()));
        }
        Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(listSourceFiles);

        // Create the compilation task
        List<String> options = new ArrayList<>(Arrays.asList("-d", sandBoxDir.getAbsolutePath()));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, compilationUnits1);

        if (annotationProcessor != null) {
            // Create a list to hold annotation processors
            LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();

            // Add an annotation processor to the list
            processors.add(annotationProcessor);
            // Set the annotation processor to the compiler task
            task.setProcessors(processors);
        }

        return task;
    }
}
