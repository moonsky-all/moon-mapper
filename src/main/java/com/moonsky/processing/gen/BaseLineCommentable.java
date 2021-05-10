package com.moonsky.processing.gen;

import com.moonsky.processing.util.Importer;

/**
 * @author benshaoye
 */
public abstract class BaseLineCommentable extends BaseAnnotatedElement {

    public BaseLineCommentable(
        Importer importer, JavaElementEnum elementEnum
    ) {
        super(importer, elementEnum);
    }
}
