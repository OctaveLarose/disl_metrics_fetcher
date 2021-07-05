package disl_metrics_fetcher.helpers;

import ch.usi.dag.disl.marker.AbstractDWRMarker;
import org.objectweb.asm.tree.*;

import java.util.LinkedList;
import java.util.List;

public class VarInsnMarker extends AbstractDWRMarker {

    public List<MarkedRegion> markWithDefaultWeavingReg(MethodNode method) {
        List<MarkedRegion> regions = new LinkedList<MarkedRegion>();

        InsnList instructions = method.instructions;

        for (AbstractInsnNode instruction : instructions.toArray()) {
            if (instruction instanceof VarInsnNode) {
                regions.add(new MarkedRegion(instruction, instruction));
            }
        }
        
        return regions;
    }
}