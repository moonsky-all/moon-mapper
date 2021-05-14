package com.moonsky.processing.generate;

import com.moonsky.processing.util.Importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author benshaoye
 */
public class JavaTypeComments extends AbstractImportable{

    private final String classname;
    private List<String> comments;

    public JavaTypeComments(Importer importer, String classname) {
        super(importer);
        this.classname = classname;
    }

    public void commentsOf(String... comments) {
        if (comments != null) {
            this.comments = new ArrayList<>(Arrays.asList(comments));
        }
    }
}
