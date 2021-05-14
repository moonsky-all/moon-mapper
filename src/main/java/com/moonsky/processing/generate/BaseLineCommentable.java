package com.moonsky.processing.generate;

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
