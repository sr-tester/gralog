/*
 * This file is part of GrALoG FX, Copyright (c) 2016 LaS group, TU Berlin.
 * License: https://www.gnu.org/licenses/gpl.html GPL version 3 or later.
 */
package gralog.finitegame.algorithm;

import gralog.finitegame.structure.*;

import java.util.HashMap;
import java.util.HashSet;

import gralog.algorithm.*;
import gralog.progresshandler.ProgressHandler;
import gralog.structure.*;
import java.util.Set;

/**
 *
 */
@AlgorithmDescription(
        name = "Player 1 Winning Region",
        text = "Finds the winning-region of player 1",
        url = ""
)
public class WinningRegionPlayer1 extends WinningRegionPlayer0 {

    @Override
    public Object run(FiniteGame game, AlgorithmParameters ap,
            Set<Object> selection, ProgressHandler onprogress) throws Exception {
        HashMap<FiniteGamePosition, Integer> winningRegions = WinningRegions(game);
        HashSet<Vertex> result = new HashSet<>();
        for (Vertex v : game.getVertices())
            if (winningRegions.containsKey((FiniteGamePosition) v))
                if (winningRegions.get((FiniteGamePosition) v) == 1)
                    result.add(v);
        return result;
    }
}