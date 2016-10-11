/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.generator;

import gralog.structure.*;

/**
 *
 */
public abstract class Generator {

    // null means it has no parameters
    public GeneratorParameters getParameters() {
        return null;
    }

    public abstract Structure generate(GeneratorParameters p) throws Exception;

    public GeneratorDescription getDescription() throws Exception {
        if (!this.getClass().isAnnotationPresent(GeneratorDescription.class))
            throw new Exception("class " + this.getClass().getName() + " has no @GeneratorDescription Annotation");
        return this.getClass().getAnnotation(GeneratorDescription.class);
    }
}
