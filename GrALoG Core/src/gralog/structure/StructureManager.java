/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gralog.structure;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import static gralog.plugins.PluginManager.instantiateClass;

/**
 *
 * @author viktor
 */
public class StructureManager {

    // The XNames HashMaps are maps from description-name -> fully qualified class name
    private final static HashMap<String, String> structureNames = new HashMap<>();
    private final static HashMap<String, StructureDescription> structureDescriptions = new HashMap<>();

    public static void registerStructureClass(Class<?> aClass, String className) throws Exception {
        if (Modifier.isAbstract(aClass.getModifiers()))
            return;

        if (!aClass.isAnnotationPresent(StructureDescription.class))
            throw new Exception("class " + aClass.getName() + " has no @StructureDescription annotation");
        StructureDescription descr = aClass.getAnnotation(StructureDescription.class);
        structureNames.put(descr.name(), className);
        structureDescriptions.put(descr.name(), descr);
    }

    public static List<String> getStructureClasses() {
        ArrayList<String> result = new ArrayList<>(structureNames.keySet());
        result.sort(String.CASE_INSENSITIVE_ORDER);
        return result;
    }

    public static Structure instantiateStructure(String identifier) throws Exception {
        String classname = structureNames.get(identifier);
        return (Structure) instantiateClass(classname);
    }

    public static StructureDescription getStructureDescription(String identifier) {
        return structureDescriptions.get(identifier);
    }
}
