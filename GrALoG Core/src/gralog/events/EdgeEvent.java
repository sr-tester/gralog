/*
 * This file is part of GrALoG FX, Copyright (c) 2016 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later.
 */
package gralog.events;

import gralog.structure.*;
import java.util.EventObject;

/**
 *
 */
public class EdgeEvent<E extends Edge> extends EventObject {
    public EdgeEvent(E source) {
        super(source);
    }
}
